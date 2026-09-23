using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal static class AutoInventoryQualifiedDynamicsGuard
    {
        private sealed class Candidate
        {
            public int Index;
            public long Center;
            public int ExactChanges;
            public bool Qualified;
            public readonly Dictionary<long, int> AddressChanges = new Dictionary<long, int>();
            public readonly Dictionary<long, HashSet<int>> AddressNewValues = new Dictionary<long, HashSet<int>>();
            public int SampleTransitions;
            public int LowIdTransitions;
        }

        private static readonly object Sync = new object();
        private static DateTime _lastWriteUtc = DateTime.MinValue;
        private static string _lastStatus = "WAITING_EXACT_CHANGE";

        public static string Refresh(string appDir)
        {
            var path = Path.Combine(appDir, "auto_inventory_exact_change_evidence.txt");
            if (!File.Exists(path)) return "WAITING_EXACT_CHANGE";

            DateTime writeUtc;
            try { writeUtc = File.GetLastWriteTimeUtc(path); }
            catch { return "EXACT_CHANGE_TIME_ERROR"; }

            lock (Sync)
            {
                if (writeUtc <= _lastWriteUtc) return _lastStatus;
            }

            string status;
            try { status = Analyze(appDir, path); }
            catch (Exception ex) { status = "ERROR=" + ex.GetType().Name; }

            lock (Sync)
            {
                _lastWriteUtc = writeUtc;
                _lastStatus = status;
            }
            return status;
        }

        private static string Analyze(string appDir, string path)
        {
            var candidates = Parse(path);
            var qualified = 0;
            var rejectedIterator = 0;
            var rejectedLowReset = 0;
            var survivors = 0;

            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=AUTO_INVENTORY_QUALIFIED_DYNAMICS_GUARD");
            sb.AppendLine("SOURCE=auto_inventory_exact_change_evidence.txt");
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            foreach (var c in candidates)
            {
                if (!c.Qualified) continue;
                qualified++;

                var maxAddressChanges = 0;
                var maxAddressUniqueNew = 0;
                foreach (var kv in c.AddressChanges)
                {
                    if (kv.Value > maxAddressChanges) maxAddressChanges = kv.Value;
                    HashSet<int> values;
                    if (c.AddressNewValues.TryGetValue(kv.Key, out values) && values.Count > maxAddressUniqueNew)
                        maxAddressUniqueNew = values.Count;
                }

                var changedAddresses = c.AddressChanges.Count;
                var iteratorLike =
                    c.SampleTransitions >= 4 &&
                    maxAddressChanges >= 4 &&
                    maxAddressUniqueNew >= 4 &&
                    maxAddressChanges * 100 >= c.SampleTransitions * 50;

                var lowResetLike =
                    c.SampleTransitions >= 4 &&
                    changedAddresses >= 3 &&
                    c.LowIdTransitions * 100 >= c.SampleTransitions * 60;

                string reason;
                if (iteratorLike)
                {
                    rejectedIterator++;
                    reason = "SINGLE_WORD_CATALOG_ITERATOR";
                }
                else if (lowResetLike)
                {
                    rejectedLowReset++;
                    reason = "MULTI_WORD_LOW_ID_RESET_CACHE";
                }
                else
                {
                    survivors++;
                    reason = "";
                }

                sb.AppendLine("[CANDIDATE " + c.Index + "]");
                sb.AppendLine("CENTER=0x" + c.Center.ToString("X8"));
                sb.AppendLine("EXACT_ITEM_WORD_CHANGES=" + c.ExactChanges);
                sb.AppendLine("TRANSITION_SAMPLES=" + c.SampleTransitions);
                sb.AppendLine("CHANGED_ADDRESSES=" + changedAddresses);
                sb.AppendLine("MAX_ADDRESS_CHANGES=" + maxAddressChanges);
                sb.AppendLine("MAX_ADDRESS_UNIQUE_NEW_VALUES=" + maxAddressUniqueNew);
                sb.AppendLine("LOW_ID_0_1_2_TRANSITIONS=" + c.LowIdTransitions);
                sb.AppendLine("ITERATOR_LIKE=" + (iteratorLike ? 1 : 0));
                sb.AppendLine("LOW_ID_RESET_LIKE=" + (lowResetLike ? 1 : 0));
                sb.AppendLine("POST_GUARD_SURVIVOR=" + (!iteratorLike && !lowResetLike ? 1 : 0));
                sb.AppendLine("REJECT_REASON=" + reason);
                sb.AppendLine();
            }

            sb.AppendLine("QUALIFIED_INPUT_CANDIDATES=" + qualified);
            sb.AppendLine("REJECTED_ITERATOR=" + rejectedIterator);
            sb.AppendLine("REJECTED_LOW_ID_RESET_CACHE=" + rejectedLowReset);
            sb.AppendLine("SURVIVING_CANDIDATES=" + survivors);

            string status;
            if (qualified == 0)
                status = "NO_QUALIFIED_INPUT";
            else if (survivors == 0)
                status = "ALL_QUALIFIED_DYNAMICS_REJECTED_CACHE_PATTERNS";
            else
                status = "SURVIVORS=" + survivors;
            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(appDir, "auto_inventory_qualified_guard_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
            return status;
        }

        private static List<Candidate> Parse(string path)
        {
            var result = new List<Candidate>();
            Candidate current = null;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                if (line.StartsWith("[CANDIDATE ", StringComparison.OrdinalIgnoreCase))
                {
                    if (current != null) result.Add(current);
                    current = new Candidate();
                    var close = line.IndexOf(']');
                    if (close > 11) int.TryParse(line.Substring(11, close - 11).Trim(), out current.Index);
                    continue;
                }

                if (line.StartsWith("[", StringComparison.Ordinal) &&
                    !line.StartsWith("[CANDIDATE ", StringComparison.OrdinalIgnoreCase))
                {
                    if (current != null) result.Add(current);
                    current = null;
                    continue;
                }

                if (current == null) continue;

                int n;
                if (line.StartsWith("CENTER=0x", StringComparison.OrdinalIgnoreCase))
                {
                    TryHex(line.Substring(7), out current.Center);
                }
                else if (line.StartsWith("EXACT_ITEM_WORD_CHANGES=", StringComparison.OrdinalIgnoreCase))
                {
                    int.TryParse(line.Substring(24).Trim(), out current.ExactChanges);
                }
                else if (line.StartsWith("QUALIFIED_ITEM_ID_DYNAMICS=", StringComparison.OrdinalIgnoreCase))
                {
                    if (int.TryParse(line.Substring(27).Trim(), out n)) current.Qualified = n == 1;
                }
                else if (line.StartsWith("TRANSITION ", StringComparison.OrdinalIgnoreCase))
                {
                    ParseTransition(line, current);
                }
            }

            if (current != null) result.Add(current);
            return result;
        }

        private static void ParseTransition(string line, Candidate c)
        {
            var tokens = ParseTokens(line);
            string addressText;
            string newText;
            long address;
            int newValue;
            if (!tokens.TryGetValue("ADDR", out addressText) || !TryHex(addressText, out address)) return;
            if (!tokens.TryGetValue("NEW", out newText) || !TryLeadingInt(newText, out newValue)) return;

            c.SampleTransitions++;
            int n;
            c.AddressChanges.TryGetValue(address, out n);
            c.AddressChanges[address] = n + 1;

            HashSet<int> values;
            if (!c.AddressNewValues.TryGetValue(address, out values))
            {
                values = new HashSet<int>();
                c.AddressNewValues.Add(address, values);
            }
            values.Add(newValue);

            if (newValue == 0 || newValue == 1 || newValue == 2)
                c.LowIdTransitions++;
        }

        private static Dictionary<string, string> ParseTokens(string line)
        {
            var result = new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
            foreach (var token in line.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries))
            {
                var eq = token.IndexOf('=');
                if (eq <= 0 || eq >= token.Length - 1) continue;
                result[token.Substring(0, eq)] = token.Substring(eq + 1);
            }
            return result;
        }

        private static bool TryLeadingInt(string text, out int value)
        {
            value = 0;
            if (string.IsNullOrEmpty(text)) return false;
            var end = text.IndexOf('(');
            if (end > 0) text = text.Substring(0, end);
            return int.TryParse(text.Trim(), out value);
        }

        private static bool TryHex(string text, out long value)
        {
            value = 0;
            if (string.IsNullOrEmpty(text)) return false;
            text = text.Trim();
            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase)) text = text.Substring(2);
            var space = text.IndexOf(' ');
            if (space >= 0) text = text.Substring(0, space);
            return long.TryParse(text, NumberStyles.HexNumber, CultureInfo.InvariantCulture, out value);
        }
    }
}
