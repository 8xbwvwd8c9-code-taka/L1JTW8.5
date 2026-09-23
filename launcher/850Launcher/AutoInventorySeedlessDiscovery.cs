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

        private sealed class SeedGroup
        {
            public int ItemId;
            public string Name = "";
            public List<IntPtr> Addresses = new List<IntPtr>();
        }

        private sealed class Cluster
        {
            public long Start;
            public long End;
            public long Anchor;
            public int Hits;
            public int DistinctItems;
            public int ItemIdMin;
            public int ItemIdMax;
            public int ItemIdSpan;
            public int AddressGapGcd;
            public int SequentialEdges;
            public int AdjacentEdges;
            public bool CatalogLike;
            public string CatalogReason = "";
            public int Score;
            public int DynamicEvents;
            public int DynamicWordChanges;
            public int DynamicWordsCompared;
            public bool DynamicReadable;
            public long SampleStart;
            public byte[] Previous;
            public readonly List<int> SensitiveOffsets = new List<int>();
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
                        _retryAfterUtc = DateTime.UtcNow.AddSeconds(20);
                    }
                }
            });
        }

        private string Run(RuntimeSnapshot runtime)
        {
            var names = ItemCatalogLoader.Load(_appDir);
            if (names.Count == 0)
                throw new InvalidDataException("item catalog has no usable item IDs");

            var fields = new Dictionary<string, int>(StringComparer.OrdinalIgnoreCase);
            var fieldToId = new Dictionary<string, int>(StringComparer.OrdinalIgnoreCase);
            foreach (var kv in names)
            {
                var key = "ITEM_" + kv.Key;
                fields[key] = kv.Key;
                fieldToId[key] = kv.Key;
            }

            var moduleEnd = runtime.ModuleBase.ToInt64() + runtime.ModuleSize;
            ProbeResult scan;
            using (var scanner = new PrivateWritableMemoryScanner())
            {
                string error;
                if (!scanner.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);
                scan = scanner.Scan(fields, moduleEnd);
            }

            var seedGroups = new List<SeedGroup>();
            foreach (var kv in scan.Candidates)
            {
                int itemId;
                if (!fieldToId.TryGetValue(kv.Key, out itemId)) continue;
                if (kv.Value == null || kv.Value.Count == 0) continue;
                string name;
                names.TryGetValue(itemId, out name);
                seedGroups.Add(new SeedGroup
                {
                    ItemId = itemId,
                    Name = name ?? "",
                    Addresses = kv.Value
                });
            }

            seedGroups.Sort(delegate(SeedGroup a, SeedGroup b)
            {
                var c = a.Addresses.Count.CompareTo(b.Addresses.Count);
                if (c != 0) return c;
                return b.ItemId.CompareTo(a.ItemId);
            });

            var hits = new List<Hit>();
            const int maxPerItem = 48;
            const int maxHits = 120000;
            foreach (var group in seedGroups)
            {
                var list = group.Addresses;
                var take = Math.Min(maxPerItem, list.Count);
                for (var sample = 0; sample < take; sample++)
                {
                    var index = take <= 1
                        ? 0
                        : (int)(((long)sample * (list.Count - 1)) / (take - 1));
                    hits.Add(new Hit
                    {
                        Address = list[index].ToInt64(),
                        ItemId = group.ItemId,
                        Name = group.Name
                    });
                    if (hits.Count >= maxHits) break;
                }
                if (hits.Count >= maxHits) break;
            }

            hits.Sort(delegate(Hit a, Hit b) { return a.Address.CompareTo(b.Address); });
            var clusters = BuildClusters(hits);

            const int maxWatchedClusters = 320;
            const int sampleRounds = 12;
            const int sampleIntervalMs = 5000;
            var watched = 0;

            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                foreach (var c in clusters)
                {
                    if (c.CatalogLike) continue;
                    if (watched >= maxWatchedClusters) break;

                    var start = Math.Max(moduleEnd, c.Start - 0x40);
                    var end = c.End + 0x40;
                    var sizeLong = end - start + 4;
                    if (sizeLong < 64) sizeLong = 64;
                    if (sizeLong > 0x1000) sizeLong = 0x1000;

                    byte[] baseline;
                    if (!probe.TryReadBytes(new IntPtr(start), (int)sizeLong, out baseline, out error) ||
                        baseline == null || baseline.Length < 4)
                        continue;

                    c.SampleStart = start;
                    c.Previous = baseline;
                    BuildSensitiveOffsets(c, hits, baseline.Length);
                    if (c.SensitiveOffsets.Count == 0)
                        continue;

                    c.DynamicReadable = true;
                    watched++;
                }

                for (var round = 1; round < sampleRounds && watched > 0; round++)
                {
                    Thread.Sleep(sampleIntervalMs);
                    foreach (var c in clusters)
                    {
                        if (!c.DynamicReadable || c.Previous == null || c.SensitiveOffsets.Count == 0)
                            continue;

                        byte[] current;
                        if (!probe.TryReadBytes(new IntPtr(c.SampleStart), c.Previous.Length, out current, out error) ||
                            current == null || current.Length < 4)
                        {
                            c.DynamicReadable = false;
                            continue;
                        }

                        var changed = 0;
                        foreach (var offset in c.SensitiveOffsets)
                        {
                            if (offset < 0 || offset + 4 > c.Previous.Length || offset + 4 > current.Length)
                                continue;
                            if (BitConverter.ToInt32(c.Previous, offset) != BitConverter.ToInt32(current, offset))
                                changed++;
                        }

                        c.DynamicWordsCompared += c.SensitiveOffsets.Count;
                        if (changed > 0)
                        {
                            c.DynamicEvents++;
                            c.DynamicWordChanges += changed;
                        }
                        c.Previous = current;
                    }
                }
            }

            clusters.Sort(delegate(Cluster a, Cluster b)
            {
                var ad = a.DynamicReadable && !a.CatalogLike && a.DynamicEvents > 0;
                var bd = b.DynamicReadable && !b.CatalogLike && b.DynamicEvents > 0;
                var c = bd.CompareTo(ad);
                if (c != 0) return c;
                c = b.DynamicEvents.CompareTo(a.DynamicEvents);
                if (c != 0) return c;
                c = b.DynamicWordChanges.CompareTo(a.DynamicWordChanges);
                if (c != 0) return c;
                c = a.CatalogLike.CompareTo(b.CatalogLike);
                if (c != 0) return c;
                c = b.Score.CompareTo(a.Score);
                if (c != 0) return c;
                return a.Start.CompareTo(b.Start);
            });

            var dynamicClusters = new List<Cluster>();
            foreach (var c in clusters)
            {
                if (!c.CatalogLike && c.DynamicReadable && c.DynamicEvents > 0)
                    dynamicClusters.Add(c);
            }

            var sb = Header(runtime, "AUTO_INVENTORY_SEEDLESS_DISCOVERY_V3");
            sb.AppendLine("CATALOG_ITEMS=" + names.Count);
            sb.AppendLine("SCAN_ITEM_IDS=" + fields.Count);
            sb.AppendLine("MEMORY_SCOPE=MEM_PRIVATE_WRITABLE_ONLY");
            sb.AppendLine("MIN_ADDRESS=0x" + moduleEnd.ToString("X8"));
            sb.AppendLine("BYTES_SCANNED=" + scan.BytesScanned);
            sb.AppendLine("RAW_PRIVATE_HITS=" + hits.Count);
            sb.AppendLine("MAX_PER_ITEM=" + maxPerItem);
            sb.AppendLine("MAX_HITS=" + maxHits);
            sb.AppendLine("CANDIDATE_FIELD_CAP_REACHED=" + (scan.CandidateLimitReached ? 1 : 0));
            sb.AppendLine("SCAN_STATUS=" + (scan.Status ?? ""));
            sb.AppendLine("CLUSTERS=" + clusters.Count);
            sb.AppendLine("CLUSTER_RETAIN_CAP=640");
            sb.AppendLine("WATCH_CAP=" + maxWatchedClusters);
            sb.AppendLine("WATCHED_CLUSTERS=" + watched);
            sb.AppendLine("DYNAMIC_CLUSTERS=" + dynamicClusters.Count);
            sb.AppendLine("DYNAMIC_SAMPLE_ROUNDS=" + sampleRounds);
            sb.AppendLine("DYNAMIC_SAMPLE_INTERVAL_MS=" + sampleIntervalMs);
            sb.AppendLine("DYNAMIC_WINDOW_MS=" + ((sampleRounds - 1) * sampleIntervalMs));
            sb.AppendLine("FILTER=PRIVATE_WRITABLE+CATALOG_STRIDE_REJECT+EXPANDED_ITEM_ADJACENT_TEMPORAL_CHANGE");
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            var shownDynamic = Math.Min(60, dynamicClusters.Count);
            for (var i = 0; i < shownDynamic; i++)
                AppendCluster(sb, "CANDIDATE", i + 1, dynamicClusters[i], hits);

            sb.AppendLine("[REJECTED_STABLE_OR_CATALOG_SAMPLE]");
            var rejectedShown = 0;
            foreach (var c in clusters)
            {
                if (dynamicClusters.Contains(c)) continue;
                sb.AppendLine(
                    "ADDR=0x" + c.Anchor.ToString("X8") +
                    " DISTINCT=" + c.DistinctItems +
                    " ID_SPAN=" + c.ItemIdSpan +
                    " GAP_GCD=0x" + c.AddressGapGcd.ToString("X") +
                    " CATALOG_LIKE=" + (c.CatalogLike ? 1 : 0) +
                    " CATALOG_REASON=" + c.CatalogReason +
                    " SENSITIVE_WORDS=" + c.SensitiveOffsets.Count +
                    " DYNAMIC_READABLE=" + (c.DynamicReadable ? 1 : 0) +
                    " DYNAMIC_EVENTS=" + c.DynamicEvents +
                    " CHANGED_WORDS=" + c.DynamicWordChanges +
                    " COMPARED_WORDS=" + c.DynamicWordsCompared);
                rejectedShown++;
                if (rejectedShown >= 60) break;
            }
            sb.AppendLine();

            string status;
            if (dynamicClusters.Count > 0)
            {
                var best = dynamicClusters[0];
                status = "PASS_CANDIDATES dynamic=" + dynamicClusters.Count +
                    " bestEvents=" + best.DynamicEvents +
                    " bestChanges=" + best.DynamicWordChanges +
                    " bestDistinct=" + best.DistinctItems;
            }
            else if (clusters.Count > 0)
            {
                status = "WAITING_INVENTORY_ACTIVITY watched=" + watched + " retained=" + clusters.Count;
            }
            else if (hits.Count > 0)
            {
                status = "ITEM_HITS_NO_DENSE_CLUSTER hits=" + hits.Count;
            }
            else
            {
                status = "NO_PRIVATE_WRITABLE_ITEM_HITS";
            }

            sb.AppendLine("STATUS=" + status);
            File.WriteAllText(
                Path.Combine(_appDir, "auto_inventory_seedless_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
            return status;
        }

        private static void BuildSensitiveOffsets(Cluster c, List<Hit> hits, int bufferLength)
        {
            var seen = new HashSet<int>();
            foreach (var hit in hits)
            {
                if (hit.Address < c.Start) continue;
                if (hit.Address > c.End) break;

                for (var delta = -16; delta <= 16; delta += 4)
                {
                    var absolute = hit.Address + delta;
                    var offsetLong = absolute - c.SampleStart;
                    if (offsetLong < 0 || offsetLong > int.MaxValue) continue;
                    var offset = (int)offsetLong;
                    if (offset + 4 > bufferLength) continue;
                    if (seen.Add(offset)) c.SensitiveOffsets.Add(offset);
                }
            }
            c.SensitiveOffsets.Sort();
        }

        private static void AppendCluster(StringBuilder sb, string kind, int index, Cluster c, List<Hit> hits)
        {
            sb.AppendLine("[" + kind + " " + index + "]");
            sb.AppendLine("ADDR=0x" + c.Anchor.ToString("X8"));
            sb.AppendLine("START=0x" + c.Start.ToString("X8"));
            sb.AppendLine("END=0x" + c.End.ToString("X8"));
            sb.AppendLine("HITS=" + c.Hits);
            sb.AppendLine("DISTINCT_ITEMS=" + c.DistinctItems);
            sb.AppendLine("ITEM_ID_MIN=" + c.ItemIdMin);
            sb.AppendLine("ITEM_ID_MAX=" + c.ItemIdMax);
            sb.AppendLine("ITEM_ID_SPAN=" + c.ItemIdSpan);
            sb.AppendLine("ADDRESS_GAP_GCD=0x" + c.AddressGapGcd.ToString("X"));
            sb.AppendLine("ADJACENT_EDGES=" + c.AdjacentEdges);
            sb.AppendLine("SEQUENTIAL_EDGES=" + c.SequentialEdges);
            sb.AppendLine("CATALOG_LIKE=" + (c.CatalogLike ? 1 : 0));
            sb.AppendLine("CATALOG_REASON=" + c.CatalogReason);
            sb.AppendLine("SENSITIVE_WORDS=" + c.SensitiveOffsets.Count);
            sb.AppendLine("DYNAMIC_READABLE=" + (c.DynamicReadable ? 1 : 0));
            sb.AppendLine("DYNAMIC_EVENTS=" + c.DynamicEvents);
            sb.AppendLine("DYNAMIC_WORD_CHANGES=" + c.DynamicWordChanges);
            sb.AppendLine("DYNAMIC_WORDS_COMPARED=" + c.DynamicWordsCompared);
            sb.AppendLine("SCORE=" + c.Score);

            var displayed = 0;
            for (var h = 0; h < hits.Count && displayed < 20; h++)
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
                    var sequentialEdges = 0;
                    var adjacentEdges = 0;
                    var gapGcd = 0;
                    var minItem = int.MaxValue;
                    var maxItem = int.MinValue;

                    for (var i = left; i < right; i++)
                    {
                        if (hits[i].ItemId < minItem) minItem = hits[i].ItemId;
                        if (hits[i].ItemId > maxItem) maxItem = hits[i].ItemId;

                        if (i == left) continue;
                        var prev = hits[i - 1];
                        var cur = hits[i];
                        var gapLong = cur.Address - prev.Address;
                        if (gapLong > 0 && gapLong <= int.MaxValue)
                            gapGcd = Gcd(gapGcd, (int)gapLong);

                        if (gapLong == 4)
                        {
                            adjacentEdges++;
                            if (Math.Abs(cur.ItemId - prev.ItemId) <= 4)
                                sequentialEdges++;
                        }
                    }

                    var edgeBase = Math.Max(1, total - 1);
                    var itemSpan = maxItem >= minItem ? maxItem - minItem : int.MaxValue;
                    var oldCatalogPattern =
                        (total >= 10 && sequentialEdges * 100 >= edgeBase * 35) ||
                        (distinct >= 24 && adjacentEdges * 100 >= edgeBase * 65);
                    var stridedCatalogPattern =
                        distinct >= 16 &&
                        itemSpan >= 0 && itemSpan <= 1024 &&
                        gapGcd >= 0x20 && gapGcd <= 0x100 &&
                        (gapGcd % 0x10) == 0;

                    var catalogLike = oldCatalogPattern || stridedCatalogPattern;
                    var catalogReason = oldCatalogPattern
                        ? "ADJACENT_OR_SEQUENTIAL_TABLE"
                        : stridedCatalogPattern
                            ? "FIXED_STRIDE_NARROW_ITEM_FAMILY"
                            : "";

                    var end = hits[right - 1].Address;
                    var score = distinct * 100 + Math.Min(99, total);
                    score -= sequentialEdges * 80;
                    if (catalogLike) score -= 100000;

                    result.Add(new Cluster
                    {
                        Start = hits[left].Address,
                        End = end,
                        Anchor = hits[left].Address,
                        Hits = total,
                        DistinctItems = distinct,
                        ItemIdMin = minItem,
                        ItemIdMax = maxItem,
                        ItemIdSpan = itemSpan,
                        AddressGapGcd = gapGcd,
                        SequentialEdges = sequentialEdges,
                        AdjacentEdges = adjacentEdges,
                        CatalogLike = catalogLike,
                        CatalogReason = catalogReason,
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
                var c = a.CatalogLike.CompareTo(b.CatalogLike);
                if (c != 0) return c;
                c = b.Score.CompareTo(a.Score);
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
                if (dedup.Count >= 640) break;
            }
            return dedup;
        }

        private static int Gcd(int a, int b)
        {
            if (a < 0) a = -a;
            if (b < 0) b = -b;
            if (a == 0) return b;
            if (b == 0) return a;
            while (b != 0)
            {
                var t = a % b;
                a = b;
                b = t;
            }
            return a;
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
                var sb = Header(runtime, "AUTO_INVENTORY_SEEDLESS_DISCOVERY_V3");
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
