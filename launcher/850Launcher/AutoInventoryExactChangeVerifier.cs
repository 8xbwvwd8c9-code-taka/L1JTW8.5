using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal static class AutoInventoryExactChangeVerifier
    {
        private sealed class Candidate
        {
            public int Index;
            public long Center;
            public readonly List<long> ItemAddresses = new List<long>();
            public int ExactChanges;
            public int CatalogTransitions;
            public int ZeroTransitions;
            public int ReadFailures;
        }

        private static readonly object Sync = new object();
        private static bool _running;
        private static DateTime _lastSourceWriteUtc = DateTime.MinValue;
        private static string _status = "WAITING_SEEDLESS";

        public static string Refresh(string appDir)
        {
            var path = Path.Combine(appDir, "auto_inventory_seedless_evidence.txt");
            if (!File.Exists(path)) return "WAITING_SEEDLESS";

            DateTime writeUtc;
            try { writeUtc = File.GetLastWriteTimeUtc(path); }
            catch { return "SEEDLESS_TIME_ERROR"; }

            lock (Sync)
            {
                if (_running) return "RUNNING";
                if (writeUtc <= _lastSourceWriteUtc) return _status;
                _running = true;
            }

            ThreadPool.QueueUserWorkItem(delegate
            {
                string status;
                try
                {
                    status = Run(appDir, path);
                }
                catch (Exception ex)
                {
                    status = "ERROR=" + ex.GetType().Name;
                    SaveError(appDir, ex);
                }

                lock (Sync)
                {
                    _status = status;
                    _lastSourceWriteUtc = writeUtc;
                    _running = false;
                }
            });

            return "RUNNING";
        }

        private static string Run(string appDir, string path)
        {
            int pid;
            bool authority;
            var candidates = Parse(path, out pid, out authority);
            if (!authority || pid <= 0)
                throw new InvalidDataException("seedless evidence is not authoritative");

            if (candidates.Count == 0)
            {
                SaveNoCandidates(appDir, pid);
                return "NO_DYNAMIC_CANDIDATES";
            }

            var process = Process.GetProcessById(pid);
            if (process.HasExited)
                throw new InvalidOperationException("Lin.bin2 process exited");

            var names = ItemCatalogLoader.Load(appDir);
            var last = new Dictionary<long, int>();
            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(pid, out error))
                    throw new InvalidOperationException(error);

                foreach (var c in candidates)
                {
                    foreach (var address in c.ItemAddresses)
                    {
                        int value;
                        if (TryReadInt32(probe, address, out value))
                            last[address] = value;
                        else
                            c.ReadFailures++;
                    }
                }

                const int rounds = 12;
                const int intervalMs = 5000;
                for (var round = 1; round < rounds; round++)
                {
                    Thread.Sleep(intervalMs);
                    foreach (var c in candidates)
                    {
                        foreach (var address in c.ItemAddresses)
                        {
                            int value;
                            if (!TryReadInt32(probe, address, out value))
                            {
                                c.ReadFailures++;
                                continue;
                            }

                            int previous;
                            if (!last.TryGetValue(address, out previous))
                            {
                                last[address] = value;
                                continue;
                            }

                            if (value != previous)
                            {
                                c.ExactChanges++;
                                if (value == 0) c.ZeroTransitions++;
                                if (names.ContainsKey(value)) c.CatalogTransitions++;
                                last[address] = value;
                            }
                        }
                    }
                }
            }

            var changedCandidates = 0;
            var totalChanges = 0;
            var totalCatalogTransitions = 0;
            foreach (var c in candidates)
            {
                if (c.ExactChanges > 0) changedCandidates++;
                totalChanges += c.ExactChanges;
                totalCatalogTransitions += c.CatalogTransitions;
            }

            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=AUTO_INVENTORY_EXACT_ITEM_WORD_VERIFY");
            sb.AppendLine("PID=" + pid);
            sb.AppendLine("SOURCE=auto_inventory_seedless_evidence.txt");
            sb.AppendLine("CANDIDATES=" + candidates.Count);
            sb.AppendLine("SAMPLE_ROUNDS=12");
            sb.AppendLine("SAMPLE_INTERVAL_MS=5000");
            sb.AppendLine("SAMPLE_WINDOW_MS=55000");
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            foreach (var c in candidates)
            {
                sb.AppendLine("[CANDIDATE " + c.Index + "]");
                sb.AppendLine("CENTER=0x" + c.Center.ToString("X8"));
                sb.AppendLine("EXACT_ITEM_WORDS=" + c.ItemAddresses.Count);
                sb.AppendLine("EXACT_ITEM_WORD_CHANGES=" + c.ExactChanges);
                sb.AppendLine("CATALOG_VALUE_TRANSITIONS=" + c.CatalogTransitions);
                sb.AppendLine("ZERO_TRANSITIONS=" + c.ZeroTransitions);
                sb.AppendLine("READ_FAILURES=" + c.ReadFailures);
                sb.AppendLine();
            }

            sb.AppendLine("CHANGED_CANDIDATES=" + changedCandidates);
            sb.AppendLine("TOTAL_EXACT_ITEM_WORD_CHANGES=" + totalChanges);
            sb.AppendLine("TOTAL_CATALOG_VALUE_TRANSITIONS=" + totalCatalogTransitions);

            var status = changedCandidates > 0
                ? "EXACT_ITEM_WORD_CHANGES candidates=" + changedCandidates + " changes=" + totalChanges
                : "NO_EXACT_ITEM_WORD_CHANGE";
            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(appDir, "auto_inventory_exact_change_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
            return status;
        }

        private static bool TryReadInt32(RuntimeMemoryProbe probe, long address, out int value)
        {
            value = 0;
            byte[] data;
            string error;
            if (!probe.TryReadBytes(new IntPtr(address), 4, out data, out error)) return false;
            if (data == null || data.Length < 4) return false;
            value = BitConverter.ToInt32(data, 0);
            return true;
        }

        private static List<Candidate> Parse(string path, out int pid, out bool authority)
        {
            pid = 0;
            authority = false;
            var result = new List<Candidate>();
            Candidate current = null;

            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                int n;
                if (line.StartsWith("PID=", StringComparison.OrdinalIgnoreCase) && int.TryParse(line.Substring(4), out n))
                {
                    pid = n;
                    continue;
                }
                if (line.StartsWith("CLIENT_AUTHORITY=", StringComparison.OrdinalIgnoreCase) && int.TryParse(line.Substring(17), out n))
                {
                    authority = n == 1;
                    continue;
                }

                if (line.StartsWith("[REJECTED_", StringComparison.OrdinalIgnoreCase) ||
                    line.StartsWith("[WEAK_", StringComparison.OrdinalIgnoreCase))
                {
                    if (current != null) result.Add(current);
                    current = null;
                    continue;
                }

                if (line.StartsWith("[CANDIDATE ", StringComparison.OrdinalIgnoreCase))
                {
                    if (current != null) result.Add(current);
                    current = new Candidate();
                    var close = line.IndexOf(']');
                    if (close > 11) int.TryParse(line.Substring(11, close - 11).Trim(), out current.Index);
                    continue;
                }

                if (current == null) continue;
                if (line.StartsWith("ADDR=0x", StringComparison.OrdinalIgnoreCase))
                {
                    TryHex(line.Substring(7), out current.Center);
                    continue;
                }
                if (!line.StartsWith("ITEM_HIT ", StringComparison.OrdinalIgnoreCase)) continue;

                var tokens = ParseTokens(line);
                string text;
                long address;
                if (tokens.TryGetValue("ADDR", out text) && TryHex(text, out address) && address > 0)
                {
                    if (!current.ItemAddresses.Contains(address)) current.ItemAddresses.Add(address);
                }
            }

            if (current != null) result.Add(current);
            result.RemoveAll(delegate(Candidate c) { return c.Center <= 0 || c.ItemAddresses.Count == 0; });
            return result;
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

        private static bool TryHex(string text, out long value)
        {
            value = 0;
            if (string.IsNullOrEmpty(text)) return false;
            text = text.Trim();
            var space = text.IndexOf(' ');
            if (space >= 0) text = text.Substring(0, space);
            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase)) text = text.Substring(2);
            return long.TryParse(text, NumberStyles.HexNumber, CultureInfo.InvariantCulture, out value);
        }

        private static void SaveNoCandidates(string appDir, int pid)
        {
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=AUTO_INVENTORY_EXACT_ITEM_WORD_VERIFY");
            sb.AppendLine("PID=" + pid);
            sb.AppendLine("CANDIDATES=0");
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine("STATUS=NO_DYNAMIC_CANDIDATES");
            File.WriteAllText(Path.Combine(appDir, "auto_inventory_exact_change_evidence.txt"), sb.ToString(), new UTF8Encoding(false));
        }

        private static void SaveError(string appDir, Exception ex)
        {
            try
            {
                File.WriteAllText(
                    Path.Combine(appDir, "auto_inventory_exact_change_evidence.txt"),
                    "TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + Environment.NewLine +
                    "MODE=AUTO_INVENTORY_EXACT_ITEM_WORD_VERIFY" + Environment.NewLine +
                    "MEMORY_WRITE=NO" + Environment.NewLine +
                    "STATUS=ERROR" + Environment.NewLine +
                    "ERROR=" + ex.GetType().Name + ": " + ex.Message + Environment.NewLine,
                    new UTF8Encoding(false));
            }
            catch { }
        }
    }
}
