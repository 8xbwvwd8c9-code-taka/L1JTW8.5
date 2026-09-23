using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoInventorySeedlessDiscovery
    {
        private sealed class Hit
        {
            public long Address;
            public int ItemId;
            public string Name = "";
        }

        private sealed class Cluster
        {
            public long Start;
            public long End;
            public long Anchor;
            public int Hits;
            public int DistinctItems;
            public int Score;
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private DateTime _retryAfterUtc = DateTime.MinValue;
        private string _status = "WAITING_CLIENT";

        public AutoInventorySeedlessDiscovery(string appDir)
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
                    var status = Run(runtime);
                    lock (_sync) _status = status;
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
                        _retryAfterUtc = DateTime.UtcNow.AddMinutes(2);
                    }
                }
            });
        }

        private string Run(RuntimeSnapshot runtime)
        {
            var names = LoadItemCatalog();
            if (names.Count == 0)
                throw new InvalidDataException("item-names.csv has no usable item IDs");

            var fields = new Dictionary<string, int>(StringComparer.OrdinalIgnoreCase);
            var fieldToId = new Dictionary<string, int>(StringComparer.OrdinalIgnoreCase);
            foreach (var kv in names)
            {
                // Start with less-noisy IDs. Low IDs are recovered later around candidate clusters.
                if (kv.Key < 1000) continue;
                var key = "ITEM_" + kv.Key;
                fields[key] = kv.Key;
                fieldToId[key] = kv.Key;
            }

            if (fields.Count == 0)
                throw new InvalidDataException("item catalog has no seedless scan IDs >= 1000");

            ProbeResult scan;
            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);
                scan = probe.FirstScan(fields);
            }

            var hits = new List<Hit>();
            const int maxPerItem = 48;
            const int maxHits = 30000;
            foreach (var kv in scan.Candidates)
            {
                int itemId;
                if (!fieldToId.TryGetValue(kv.Key, out itemId)) continue;
                string name;
                names.TryGetValue(itemId, out name);
                var list = kv.Value;
                if (list == null) continue;
                for (var i = 0; i < Math.Min(maxPerItem, list.Count); i++)
                {
                    hits.Add(new Hit
                    {
                        Address = list[i].ToInt64(),
                        ItemId = itemId,
                        Name = name ?? ""
                    });
                    if (hits.Count >= maxHits) break;
                }
                if (hits.Count >= maxHits) break;
            }

            hits.Sort(delegate(Hit a, Hit b) { return a.Address.CompareTo(b.Address); });
            var clusters = BuildClusters(hits);

            var sb = Header(runtime, "AUTO_INVENTORY_SEEDLESS_DISCOVERY");
            sb.AppendLine("CATALOG_ITEMS=" + names.Count);
            sb.AppendLine("SCAN_ITEM_IDS=" + fields.Count);
            sb.AppendLine("BYTES_SCANNED=" + scan.BytesScanned);
            sb.AppendLine("RAW_HITS=" + hits.Count);
            sb.AppendLine("CANDIDATE_LIMIT_REACHED=" + (scan.CandidateLimitReached ? 1 : 0));
            sb.AppendLine("SCAN_STATUS=" + (scan.Status ?? ""));
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            var shownClusters = Math.Min(60, clusters.Count);
            for (var i = 0; i < shownClusters; i++)
            {
                var c = clusters[i];
                sb.AppendLine("[CANDIDATE " + (i + 1) + "]");
                sb.AppendLine("ADDR=0x" + c.Anchor.ToString("X8"));
                sb.AppendLine("START=0x" + c.Start.ToString("X8"));
                sb.AppendLine("END=0x" + c.End.ToString("X8"));
                sb.AppendLine("HITS=" + c.Hits);
                sb.AppendLine("DISTINCT_ITEMS=" + c.DistinctItems);
                sb.AppendLine("SCORE=" + c.Score);

                var displayed = 0;
                for (var h = 0; h < hits.Count && displayed < 16; h++)
                {
                    if (hits[h].Address < c.Start) continue;
                    if (hits[h].Address > c.End) break;
                    sb.AppendLine(
                        "ITEM_HIT ADDR=0x" + hits[h].Address.ToString("X8") +
                        " ITEM_ID=" + hits[h].ItemId +
                        " NAME=" + Sanitize(hits[h].Name));
                    displayed++;
                }
                sb.AppendLine();
            }

            string status;
            if (clusters.Count > 0 && clusters[0].DistinctItems >= 4)
                status = "PASS_CANDIDATES clusters=" + clusters.Count + " bestDistinct=" + clusters[0].DistinctItems;
            else if (hits.Count > 0)
                status = "ITEM_HITS_NO_DENSE_CLUSTER hits=" + hits.Count;
            else
                status = "NO_SEEDLESS_ITEM_HITS";

            sb.AppendLine("STATUS=" + status);
            File.WriteAllText(
                Path.Combine(_appDir, "auto_inventory_seedless_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
            return status;
        }

        private static List<Cluster> BuildClusters(List<Hit> hits)
        {
            var result = new List<Cluster>();
            if (hits == null || hits.Count == 0) return result;

            const long window = 0x800;
            var counts = new Dictionary<int, int>();
            var right = 0;
            for (var left = 0; left < hits.Count; left++)
            {
                if (right < left) right = left;
                while (right < hits.Count && hits[right].Address - hits[left].Address <= window)
                {
                    int n;
                    counts.TryGetValue(hits[right].ItemId, out n);
                    counts[hits[right].ItemId] = n + 1;
                    right++;
                }

                var total = right - left;
                var distinct = counts.Count;
                if (distinct >= 3 && total >= 3)
                {
                    var end = hits[right - 1].Address;
                    var score = distinct * 100 + Math.Min(99, total);
                    result.Add(new Cluster
                    {
                        Start = hits[left].Address,
                        End = end,
                        Anchor = hits[left].Address,
                        Hits = total,
                        DistinctItems = distinct,
                        Score = score
                    });
                }

                int old;
                if (counts.TryGetValue(hits[left].ItemId, out old))
                {
                    if (old <= 1) counts.Remove(hits[left].ItemId);
                    else counts[hits[left].ItemId] = old - 1;
                }
            }

            result.Sort(delegate(Cluster a, Cluster b)
            {
                var c = b.Score.CompareTo(a.Score);
                if (c != 0) return c;
                c = b.DistinctItems.CompareTo(a.DistinctItems);
                if (c != 0) return c;
                return a.Start.CompareTo(b.Start);
            });

            var dedup = new List<Cluster>();
            foreach (var c in result)
            {
                var near = false;
                foreach (var keep in dedup)
                {
                    if (Math.Abs(keep.Anchor - c.Anchor) <= 0x400)
                    {
                        near = true;
                        break;
                    }
                }
                if (near) continue;
                dedup.Add(c);
                if (dedup.Count >= 120) break;
            }
            return dedup;
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

        private void SaveError(RuntimeSnapshot runtime, Exception ex)
        {
            try
            {
                var sb = Header(runtime, "AUTO_INVENTORY_SEEDLESS_DISCOVERY");
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "auto_inventory_seedless_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
