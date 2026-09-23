using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoInventoryHistoryDiscovery
    {
        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private DateTime _retryAfterUtc = DateTime.MinValue;
        private string _status = "WAITING";

        public AutoInventoryHistoryDiscovery(string appDir)
        {
            _appDir = appDir;
        }

        public string Status
        {
            get { lock (_sync) return _running ? "RUNNING" : _status; }
        }

        public void EnsureRunning(RuntimeSnapshot runtime)
        {
            if (runtime == null || !runtime.Connected || !runtime.ClientHashAuthoritative || runtime.ProcessId <= 0)
                return;

            lock (_sync)
            {
                if (_pid != runtime.ProcessId)
                {
                    _pid = runtime.ProcessId;
                    _running = false;
                    _retryAfterUtc = DateTime.MinValue;
                    _status = "READY";
                }

                if (_running || DateTime.UtcNow < _retryAfterUtc) return;
                _running = true;
            }

            ThreadPool.QueueUserWorkItem(delegate
            {
                try
                {
                    Run(runtime);
                }
                catch (Exception ex)
                {
                    SaveError(runtime, ex);
                    lock (_sync) _status = "ERROR_RETRY";
                }
                finally
                {
                    lock (_sync)
                    {
                        _running = false;
                        _retryAfterUtc = DateTime.UtcNow.AddSeconds(45);
                    }
                }
            });
        }

        private void Run(RuntimeSnapshot runtime)
        {
            var historyPath = Path.Combine(_appDir, "inventory_probe_evidence.txt");
            if (!File.Exists(historyPath))
            {
                SaveWaiting(runtime, "inventory_probe_evidence.txt missing");
                lock (_sync) _status = "WAITING_HISTORY";
                return;
            }

            var centers = LoadLatestCandidateCenters(historyPath);
            if (centers.Count == 0)
            {
                SaveWaiting(runtime, "latest inventory probe block has no candidate addresses");
                lock (_sync) _status = "WAITING_HISTORY";
                return;
            }

            var names = LoadItemCatalog();
            if (names.Count == 0)
                throw new InvalidDataException("item-names.csv has no usable item IDs");

            if (centers.Count > 120)
                centers.RemoveRange(120, centers.Count - 120);

            var offsetHits = new Dictionary<int, int>();
            var distinctCentersByOffset = new Dictionary<int, HashSet<long>>();
            var itemHits = 0;
            var sb = Header(runtime, "AUTO_INVENTORY_HISTORY_DISCOVERY");
            sb.AppendLine("HISTORY_CENTERS=" + centers.Count);
            sb.AppendLine("CATALOG_ITEMS=" + names.Count);
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                for (var i = 0; i < centers.Count; i++)
                {
                    var center = new IntPtr(centers[i]);
                    var rows = probe.ReadDwords(center, 0x100, 0x104);
                    var currentCenterValue = 0;
                    var centerRead = false;

                    foreach (var row in rows)
                    {
                        if (row.Address.ToInt64() == center.ToInt64())
                        {
                            currentCenterValue = row.Value;
                            centerRead = true;
                            break;
                        }
                    }

                    sb.AppendLine("[CENTER " + (i + 1) + "]");
                    sb.AppendLine("ADDR=0x" + center.ToInt64().ToString("X8"));
                    sb.AppendLine("CURRENT_VALUE=" + (centerRead ? currentCenterValue.ToString() : "UNREADABLE"));

                    var shown = 0;
                    foreach (var row in rows)
                    {
                        if (row.Address.ToInt64() == center.ToInt64()) continue;
                        string name;
                        if (!names.TryGetValue(row.Value, out name)) continue;
                        if (row.Value < 1000) continue;

                        var offsetLong = row.Address.ToInt64() - center.ToInt64();
                        if (offsetLong < int.MinValue || offsetLong > int.MaxValue) continue;
                        var offset = (int)offsetLong;

                        int count;
                        offsetHits.TryGetValue(offset, out count);
                        offsetHits[offset] = count + 1;

                        HashSet<long> centerSet;
                        if (!distinctCentersByOffset.TryGetValue(offset, out centerSet))
                        {
                            centerSet = new HashSet<long>();
                            distinctCentersByOffset.Add(offset, centerSet);
                        }
                        centerSet.Add(center.ToInt64());

                        itemHits++;
                        if (shown < 24)
                        {
                            sb.AppendLine(
                                "ITEM_HIT OFFSET=" + offset +
                                " ITEM_ADDR=0x" + row.Address.ToInt64().ToString("X8") +
                                " ITEM_ID=" + row.Value +
                                " NAME=" + Sanitize(name));
                            shown++;
                        }
                    }
                    sb.AppendLine("ITEM_HITS_SHOWN=" + shown);
                    sb.AppendLine();
                }
            }

            var ranked = new List<KeyValuePair<int, int>>(offsetHits);
            ranked.Sort(delegate(KeyValuePair<int, int> a, KeyValuePair<int, int> b)
            {
                var ac = distinctCentersByOffset.ContainsKey(a.Key) ? distinctCentersByOffset[a.Key].Count : 0;
                var bc = distinctCentersByOffset.ContainsKey(b.Key) ? distinctCentersByOffset[b.Key].Count : 0;
                var c = bc.CompareTo(ac);
                if (c != 0) return c;
                c = b.Value.CompareTo(a.Value);
                return c != 0 ? c : a.Key.CompareTo(b.Key);
            });

            sb.AppendLine("[ITEM_OFFSET_HISTOGRAM]");
            for (var i = 0; i < Math.Min(40, ranked.Count); i++)
            {
                var offset = ranked[i].Key;
                var centersHit = distinctCentersByOffset.ContainsKey(offset)
                    ? distinctCentersByOffset[offset].Count
                    : 0;
                sb.AppendLine(
                    "OFFSET=" + offset +
                    " HITS=" + ranked[i].Value +
                    " DISTINCT_CENTERS=" + centersHit);
            }

            var bestOffset = 0;
            var bestCenters = 0;
            var bestHits = 0;
            if (ranked.Count > 0)
            {
                bestOffset = ranked[0].Key;
                bestHits = ranked[0].Value;
                bestCenters = distinctCentersByOffset.ContainsKey(bestOffset)
                    ? distinctCentersByOffset[bestOffset].Count
                    : 0;
            }

            sb.AppendLine();
            sb.AppendLine("TOTAL_ITEM_HITS=" + itemHits);
            sb.AppendLine("BEST_ITEM_OFFSET=" + bestOffset);
            sb.AppendLine("BEST_ITEM_OFFSET_HITS=" + bestHits);
            sb.AppendLine("BEST_ITEM_OFFSET_DISTINCT_CENTERS=" + bestCenters);

            string status;
            if (bestCenters >= 3)
                status = "PASS_CANDIDATES offset=" + bestOffset + " centers=" + bestCenters;
            else if (itemHits > 0)
                status = "ITEM_HINTS hits=" + itemHits + " bestCenters=" + bestCenters;
            else
                status = "NO_CATALOG_NEAR_HISTORY";
            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(_appDir, "auto_inventory_history_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));

            lock (_sync) _status = status;
        }

        private List<long> LoadLatestCandidateCenters(string path)
        {
            var lines = File.ReadAllLines(path);
            var start = 0;
            for (var i = 0; i < lines.Length; i++)
            {
                if (lines[i].Trim().StartsWith("TIME=", StringComparison.OrdinalIgnoreCase))
                    start = i;
            }

            var result = new List<long>();
            var seen = new HashSet<long>();
            var inCandidate = false;
            for (var i = start; i < lines.Length; i++)
            {
                var line = lines[i].Trim();
                if (line.StartsWith("[CANDIDATE ", StringComparison.OrdinalIgnoreCase))
                {
                    inCandidate = true;
                    continue;
                }
                if (line.StartsWith("[", StringComparison.Ordinal) &&
                    !line.StartsWith("[CANDIDATE ", StringComparison.OrdinalIgnoreCase))
                {
                    inCandidate = false;
                    continue;
                }
                if (!inCandidate || !line.StartsWith("ADDR=0x", StringComparison.OrdinalIgnoreCase))
                    continue;

                var text = line.Substring(7).Trim();
                var space = text.IndexOf(' ');
                if (space >= 0) text = text.Substring(0, space);
                long value;
                if (long.TryParse(text, System.Globalization.NumberStyles.HexNumber,
                    System.Globalization.CultureInfo.InvariantCulture, out value) && value > 0)
                {
                    if (seen.Add(value)) result.Add(value);
                }
            }
            return result;
        }

        private Dictionary<int, string> LoadItemCatalog()
        {
            var path = Path.Combine(_appDir, "item-names.csv");
            var result = new Dictionary<int, string>();
            if (!File.Exists(path)) return result;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                if (line.Length == 0 || line.StartsWith("item_id", StringComparison.OrdinalIgnoreCase))
                    continue;
                var comma = line.IndexOf(',');
                if (comma <= 0) continue;
                int id;
                if (!int.TryParse(line.Substring(0, comma).Trim(), out id) || id <= 0)
                    continue;

                var name = line.Substring(comma + 1).Trim();
                if (name.Length >= 2 && name[0] == '"' && name[name.Length - 1] == '"')
                    name = name.Substring(1, name.Length - 2).Replace("\"\"", "\"");
                if (!result.ContainsKey(id)) result.Add(id, name);
            }
            return result;
        }

        private static string Sanitize(string value)
        {
            if (value == null) return "";
            return value.Replace("\r", " ").Replace("\n", " ");
        }

        private static StringBuilder Header(RuntimeSnapshot runtime, string mode)
        {
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=" + mode);
            sb.AppendLine("PID=" + runtime.ProcessId);
            sb.AppendLine("PROCESS_START_UTC=" + (runtime.ProcessStartTimeUtc.HasValue ? runtime.ProcessStartTimeUtc.Value.ToString("o") : ""));
            sb.AppendLine("CLIENT_SHA256=" + (runtime.ClientSha256 ?? ""));
            sb.AppendLine("CLIENT_AUTHORITY=" + (runtime.ClientHashAuthoritative ? 1 : 0));
            sb.AppendLine("MODULE_BASE=0x" + runtime.ModuleBase.ToInt64().ToString("X8"));
            return sb;
        }

        private void SaveWaiting(RuntimeSnapshot runtime, string reason)
        {
            try
            {
                var sb = Header(runtime, "AUTO_INVENTORY_HISTORY_DISCOVERY");
                sb.AppendLine("STATUS=WAITING_HISTORY");
                sb.AppendLine("REASON=" + reason);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "auto_inventory_history_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        private void SaveError(RuntimeSnapshot runtime, Exception ex)
        {
            try
            {
                var sb = Header(runtime, "AUTO_INVENTORY_HISTORY_DISCOVERY");
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "auto_inventory_history_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
