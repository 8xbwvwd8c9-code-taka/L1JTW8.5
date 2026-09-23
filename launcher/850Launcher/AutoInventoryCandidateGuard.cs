using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal static class AutoInventoryCandidateGuard
    {
        private sealed class ItemHit
        {
            public long Address;
            public int ItemId;
        }

        private sealed class Candidate
        {
            public int Index;
            public long Address;
            public int Distinct;
            public int ItemIdSpan = -1;
            public int GapGcd;
            public readonly List<ItemHit> Hits = new List<ItemHit>();
        }

        private static readonly object Sync = new object();
        private static DateTime _lastWriteUtc = DateTime.MinValue;
        private static string _lastStatus = "WAITING_SEEDLESS";

        public static string Refresh(string appDir)
        {
            var path = Path.Combine(appDir, "auto_inventory_seedless_evidence.txt");
            if (!File.Exists(path))
                return "WAITING_SEEDLESS";

            DateTime writeUtc;
            try { writeUtc = File.GetLastWriteTimeUtc(path); }
            catch { return "SEEDLESS_TIME_ERROR"; }

            lock (Sync)
            {
                if (writeUtc <= _lastWriteUtc)
                    return _lastStatus;
            }

            string status;
            try
            {
                status = Analyze(appDir, path);
            }
            catch (Exception ex)
            {
                status = "ERROR=" + ex.GetType().Name;
            }

            lock (Sync)
            {
                _lastWriteUtc = writeUtc;
                _lastStatus = status;
            }
            return status;
        }

        private static string Analyze(string appDir, string path)
        {
            var candidates = ParseCandidates(path);
            var rejected = 0;
            var survivors = 0;
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=AUTO_INVENTORY_CANDIDATE_GUARD");
            sb.AppendLine("SOURCE=auto_inventory_seedless_evidence.txt");
            sb.AppendLine("CANDIDATES=" + candidates.Count);
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            foreach (var c in candidates)
            {
                var edgeCount = Math.Max(0, c.Hits.Count - 1);
                var linearEdges = 0;
                var plusOneEdges = 0;
                var gapCounts = new Dictionary<int, int>();

                for (var i = 1; i < c.Hits.Count; i++)
                {
                    var prev = c.Hits[i - 1];
                    var cur = c.Hits[i];
                    var gapLong = cur.Address - prev.Address;
                    if (gapLong <= 0 || gapLong > 0x100 || gapLong > int.MaxValue)
                        continue;

                    var gap = (int)gapLong;
                    int n;
                    gapCounts.TryGetValue(gap, out n);
                    gapCounts[gap] = n + 1;

                    var idDiff = cur.ItemId - prev.ItemId;
                    if ((gap % 4) == 0 && Math.Abs(idDiff) <= 4)
                        linearEdges++;
                    if ((gap % 4) == 0 && idDiff == 1)
                        plusOneEdges++;
                }

                var dominantGap = 0;
                var dominantGapHits = 0;
                foreach (var kv in gapCounts)
                {
                    if (kv.Value > dominantGapHits)
                    {
                        dominantGap = kv.Key;
                        dominantGapHits = kv.Value;
                    }
                }

                var plusOneCatalog =
                    c.Distinct >= 16 && edgeCount >= 8 &&
                    plusOneEdges >= 8 && plusOneEdges * 100 >= edgeCount * 45;

                var dominantStrideCatalog =
                    c.Distinct >= 24 && edgeCount >= 8 &&
                    linearEdges * 100 >= edgeCount * 65 &&
                    dominantGapHits * 100 >= edgeCount * 50;

                var narrowDenseCatalog =
                    c.Distinct >= 64 &&
                    c.ItemIdSpan >= 0 && c.ItemIdSpan <= 1024 &&
                    c.GapGcd > 0 && c.GapGcd <= 0x20;

                var catalog = plusOneCatalog || dominantStrideCatalog || narrowDenseCatalog;
                var reason = plusOneCatalog
                    ? "MONOTONIC_ITEM_ID_RUN"
                    : dominantStrideCatalog
                        ? "DOMINANT_FIXED_STRIDE_ITEM_FAMILY"
                        : narrowDenseCatalog
                            ? "DENSE_NARROW_ITEM_FAMILY"
                            : "";

                if (catalog) rejected++;
                else survivors++;

                sb.AppendLine("[CANDIDATE " + c.Index + "]");
                sb.AppendLine("ADDR=0x" + c.Address.ToString("X8"));
                sb.AppendLine("DISTINCT=" + c.Distinct);
                sb.AppendLine("ITEM_ID_SPAN=" + c.ItemIdSpan);
                sb.AppendLine("GAP_GCD=0x" + c.GapGcd.ToString("X"));
                sb.AppendLine("ITEM_HITS_PARSED=" + c.Hits.Count);
                sb.AppendLine("EDGE_COUNT=" + edgeCount);
                sb.AppendLine("LINEAR_EDGES=" + linearEdges);
                sb.AppendLine("PLUS_ONE_EDGES=" + plusOneEdges);
                sb.AppendLine("DOMINANT_GAP=0x" + dominantGap.ToString("X"));
                sb.AppendLine("DOMINANT_GAP_HITS=" + dominantGapHits);
                sb.AppendLine("CATALOG_REJECT=" + (catalog ? 1 : 0));
                sb.AppendLine("REASON=" + reason);
                sb.AppendLine();
            }

            sb.AppendLine("REJECTED_CATALOG=" + rejected);
            sb.AppendLine("SURVIVING_CANDIDATES=" + survivors);

            string status;
            if (candidates.Count == 0)
                status = "NO_DYNAMIC_CANDIDATES";
            else if (survivors == 0)
                status = "ALL_DYNAMIC_CANDIDATES_REJECTED_CATALOG";
            else
                status = "SURVIVORS=" + survivors;

            sb.AppendLine("STATUS=" + status);
            File.WriteAllText(
                Path.Combine(appDir, "auto_inventory_candidate_guard_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
            return status;
        }

        private static List<Candidate> ParseCandidates(string path)
        {
            var result = new List<Candidate>();
            Candidate current = null;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                if (line.StartsWith("[REJECTED_", StringComparison.OrdinalIgnoreCase))
                {
                    if (current != null) result.Add(current);
                    current = null;
                    break;
                }

                if (line.StartsWith("[CANDIDATE ", StringComparison.OrdinalIgnoreCase))
                {
                    if (current != null) result.Add(current);
                    current = new Candidate();
                    var close = line.IndexOf(']');
                    int index;
                    if (close > 11 && int.TryParse(line.Substring(11, close - 11).Trim(), out index))
                        current.Index = index;
                    continue;
                }

                if (current == null) continue;

                if (line.StartsWith("ADDR=0x", StringComparison.OrdinalIgnoreCase))
                {
                    TryHex(line.Substring(7), out current.Address);
                }
                else if (line.StartsWith("DISTINCT_ITEMS=", StringComparison.OrdinalIgnoreCase))
                {
                    int.TryParse(line.Substring(15).Trim(), out current.Distinct);
                }
                else if (line.StartsWith("ITEM_ID_SPAN=", StringComparison.OrdinalIgnoreCase))
                {
                    int.TryParse(line.Substring(13).Trim(), out current.ItemIdSpan);
                }
                else if (line.StartsWith("ADDRESS_GAP_GCD=0x", StringComparison.OrdinalIgnoreCase))
                {
                    var text = line.Substring(18).Trim();
                    int.TryParse(text, NumberStyles.HexNumber, CultureInfo.InvariantCulture, out current.GapGcd);
                }
                else if (line.StartsWith("ITEM_HIT ", StringComparison.OrdinalIgnoreCase))
                {
                    var tokens = ParseTokens(line);
                    string addressText;
                    string itemText;
                    long address;
                    int itemId;
                    if (tokens.TryGetValue("ADDR", out addressText) &&
                        tokens.TryGetValue("ITEM_ID", out itemText) &&
                        TryHex(addressText, out address) &&
                        int.TryParse(itemText, out itemId))
                    {
                        current.Hits.Add(new ItemHit { Address = address, ItemId = itemId });
                    }
                }
            }

            if (current != null) result.Add(current);
            return result;
        }

        private static Dictionary<string, string> ParseTokens(string line)
        {
            var map = new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
            foreach (var token in line.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries))
            {
                var eq = token.IndexOf('=');
                if (eq <= 0 || eq >= token.Length - 1) continue;
                map[token.Substring(0, eq)] = token.Substring(eq + 1);
            }
            return map;
        }

        private static bool TryHex(string text, out long value)
        {
            value = 0;
            if (string.IsNullOrEmpty(text)) return false;
            text = text.Trim();
            var space = text.IndexOf(' ');
            if (space >= 0) text = text.Substring(0, space);
            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase))
                text = text.Substring(2);
            return long.TryParse(text, NumberStyles.HexNumber, CultureInfo.InvariantCulture, out value);
        }
    }
}
