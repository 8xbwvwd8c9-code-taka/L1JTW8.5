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
        private readonly AutoInventorySeedlessDiscovery _seedless;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private DateTime _retryAfterUtc = DateTime.MinValue;
        private string _status = "WAITING";

        public AutoInventoryHistoryDiscovery(string appDir)
        {
            _appDir = appDir;
            _seedless = new AutoInventorySeedlessDiscovery(appDir);
        }

        public string Status
        {
            get
            {
                lock (_sync)
                {
                    if (_running) return "RUNNING";
                    return _status + " | SEEDLESS=" + _seedless.Status;
                }
            }
        }

        public void EnsureRunning(RuntimeSnapshot runtime)
        {
            if (runtime == null || !runtime.Connected || !runtime.ClientHashAuthoritative || runtime.ProcessId <= 0)
                return;

            _seedless.EnsureRunning(runtime);

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
            string historyPath;
            string source;
            List<long> centers;
            if (!TryChooseSource(out historyPath, out source, out centers))
            {
                SaveWaiting(runtime, "waiting for non-catalog manual or seedless inventory candidates");
                lock (_sync) _status = "WAITING_CANDIDATES";
                return;
            }

            var names = ItemCatalogLoader.Load(_appDir);
            if (names.Count == 0)
                throw new InvalidDataException("item catalog has no usable item IDs");

            if (centers.Count > 120)
                centers.RemoveRange(120, centers.Count - 120);

            var offsetHits = new Dictionary<int, int>();
            var distinctCentersByOffset = new Dictionary<int, HashSet<long>>();
            var itemHits = 0;
            var sb = Header(runtime, "AUTO_INVENTORY_HISTORY_DISCOVERY");
            sb.AppendLine("SOURCE=" + source);
            sb.AppendLine("SOURCE_FILE=" + Path.GetFileName(historyPath));
            sb.AppendLine("HISTORY_CENTERS=" + centers.Count);
            sb.AppendLine("CATALOG_ITEMS=" + names.Count);
            sb.AppendLine("CATALOG_MIRROR_FILTER=ON");
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
                status = "NO_CATALOG_NEAR_CANDIDATES";
            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(_appDir, "auto_inventory_history_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));

            lock (_sync) _status = status;
        }

        private bool TryChooseSource(out string path, out string source, out List<long> centers)
        {
            path = Path.Combine(_appDir, "inventory_probe_evidence.txt");
            source = "MANUAL_HISTORY";
            centers = File.Exists(path) ? LoadLatestCandidateCenters(path) : new List<long>();
            if (centers.Count > 0) return true;

            path = Path.Combine(_appDir, "auto_inventory_seedless_evidence.txt");
            source = "AUTO_SEEDLESS";
            centers = File.Exists(path) ? LoadLatestCandidateCenters(path) : new List<long>();
            return centers.Count > 0;
        }

        private static List<long> LoadLatestCandidateCenters(string path)
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
            var candidateAddress = 0L;
            var catalogLike = false;

            for (var i = start; i < lines.Length; i++)
            {
                var line = lines[i].Trim();
                if (line.StartsWith("[CANDIDATE ", StringComparison.OrdinalIgnoreCase))
                {
                    AddCandidate(result, seen, candidateAddress, catalogLike);
                    inCandidate = true;
                    candidateAddress = 0;
                    catalogLike = false;
                    continue;
                }

                if (line.StartsWith("[", StringComparison.Ordinal) &&
                    !line.StartsWith("[CANDIDATE ", StringComparison.OrdinalIgnoreCase))
                {
                    AddCandidate(result, seen, candidateAddress, catalogLike);
                    inCandidate = false;
                    candidateAddress = 0;
                    catalogLike = false;
                    continue;
                }

                if (!inCandidate) continue;

                if (line.StartsWith("ADDR=0x", StringComparison.OrdinalIgnoreCase))
                {
                    var text = line.Substring(7).Trim();
                    var space = text.IndexOf(' ');
                    if (space >= 0) text = text.Substring(0, space);
                    long value;
                    if (long.TryParse(
                        text,
                        System.Globalization.NumberStyles.HexNumber,
                        System.Globalization.CultureInfo.InvariantCulture,
                        out value) && value > 0)
                    {
                        candidateAddress = value;
                    }
                }
                else if (line.StartsWith("CATALOG_LIKE=", StringComparison.OrdinalIgnoreCase))
                {
                    int flag;
                    if (int.TryParse(line.Substring(13).Trim(), out flag))
                        catalogLike = flag == 1;
                }
            }

            AddCandidate(result, seen, candidateAddress, catalogLike);
            return result;
        }

        private static void AddCandidate(List<long> result, HashSet<long> seen, long address, bool catalogLike)
        {
            if (catalogLike || address <= 0) return;
            if (seen.Add(address)) result.Add(address);
        }

        private static string Sanitize(string value)
        {
            return (value ?? "").Replace("\r", " ").Replace("\n", " ");
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
                sb.AppendLine("STATUS=WAITING_CANDIDATES");
                sb.AppendLine("REASON=" + reason);
                sb.AppendLine("SEEDLESS_STATUS=" + _seedless.Status);
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
