using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal static class AutoHpMpStableRvaMultiVerifier
    {
        private sealed class PairProbe
        {
            public long HpRva;
            public int HpWidth;
            public long MpRva;
            public int MpWidth;
            public readonly HashSet<int> Pids = new HashSet<int>();

            public int HpMin = int.MaxValue;
            public int HpMax = int.MinValue;
            public int MpMin = int.MaxValue;
            public int MpMax = int.MinValue;
            public int HpChanges;
            public int MpChanges;
            public int HpValid;
            public int MpValid;
            public int HpInvalid;
            public int MpInvalid;
            public int HpLast;
            public int MpLast;
            public bool HpHasLast;
            public bool MpHasLast;
            public readonly List<int> NearMaxHpOffsets = new List<int>();
            public readonly List<int> NearMaxMpOffsets = new List<int>();
        }

        private static readonly object Sync = new object();
        private static bool _running;
        private static DateTime _retryAfterUtc = DateTime.MinValue;
        private static string _status = "WAITING_STABLE_RVA_HISTORY";

        public static string Refresh(string appDir)
        {
            lock (Sync)
            {
                if (_running) return "RUNNING";
                if (DateTime.UtcNow < _retryAfterUtc) return _status;
                _running = true;
            }

            ThreadPool.QueueUserWorkItem(delegate
            {
                string status;
                try { status = Run(appDir); }
                catch (Exception ex)
                {
                    status = "ERROR=" + ex.GetType().Name;
                    SaveError(appDir, ex);
                }

                lock (Sync)
                {
                    _status = status;
                    _running = false;
                    _retryAfterUtc = DateTime.UtcNow.AddSeconds(30);
                }
            });

            return "RUNNING";
        }

        private static string Run(string appDir)
        {
            var pointerHistory = Path.Combine(appDir, "runtime_hpmp_pointer_history.txt");
            var semanticPath = Path.Combine(appDir, "runtime_hpmp_semantic_refine_evidence.txt");
            var crossHistory = Path.Combine(appDir, "runtime_hpmp_crosscheck_history.txt");
            if (!File.Exists(pointerHistory) || !File.Exists(semanticPath))
                return SaveWaiting(appDir, "missing pointer history or semantic evidence");

            int pid;
            int maxHp;
            int maxMp;
            bool authority;
            ParseSemanticHeader(semanticPath, out pid, out maxHp, out maxMp, out authority);
            if (!authority || pid <= 0)
                return SaveWaiting(appDir, "current runtime semantic evidence is not authoritative");

            var process = Process.GetProcessById(pid);
            if (process.HasExited)
                throw new InvalidOperationException("Lin.bin2 process exited");

            long moduleBase;
            try { moduleBase = process.MainModule.BaseAddress.ToInt64(); }
            catch (Exception ex)
            {
                return SaveWaiting(appDir, "cannot resolve current Lin.bin2 module base: " + ex.GetType().Name);
            }
            if (moduleBase <= 0)
                return SaveWaiting(appDir, "current Lin.bin2 module base is unavailable");

            var pairs = ParsePointerHistory(pointerHistory);
            RejectKnownGaugePairs(pairs, crossHistory, moduleBase);
            pairs.RemoveAll(delegate(PairProbe p) { return p.Pids.Count < 2; });
            pairs.Sort(delegate(PairProbe a, PairProbe b)
            {
                var c = b.Pids.Count.CompareTo(a.Pids.Count);
                if (c != 0) return c;
                c = Math.Abs(a.HpRva - a.MpRva).CompareTo(Math.Abs(b.HpRva - b.MpRva));
                if (c != 0) return c;
                return a.HpRva.CompareTo(b.HpRva);
            });
            if (pairs.Count > 8) pairs.RemoveRange(8, pairs.Count - 8);

            if (pairs.Count == 0)
                return SaveWaiting(appDir, "no non-gauge direct-rva pair across at least two pids");

            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(pid, out error))
                    throw new InvalidOperationException(error);

                const int samples = 60;
                const int intervalMs = 1000;
                for (var sample = 0; sample < samples; sample++)
                {
                    foreach (var p in pairs)
                    {
                        Observe(probe, moduleBase + p.HpRva, p.HpWidth, maxHp,
                            ref p.HpMin, ref p.HpMax, ref p.HpChanges, ref p.HpValid, ref p.HpInvalid,
                            ref p.HpLast, ref p.HpHasLast);
                        Observe(probe, moduleBase + p.MpRva, p.MpWidth, maxMp,
                            ref p.MpMin, ref p.MpMax, ref p.MpChanges, ref p.MpValid, ref p.MpInvalid,
                            ref p.MpLast, ref p.MpHasLast);
                    }
                    if (sample + 1 < samples) Thread.Sleep(intervalMs);
                }

                foreach (var p in pairs)
                {
                    FindNearbyMaxima(
                        probe,
                        Math.Min(moduleBase + p.HpRva, moduleBase + p.MpRva),
                        maxHp,
                        maxMp,
                        p.NearMaxHpOffsets,
                        p.NearMaxMpOffsets);
                }
            }

            var dynamicWithMax = 0;
            var dynamic = 0;
            var wait = 0;
            var staticReject = 0;

            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=AUTO_HPMP_STABLE_RVA_MULTI_VERIFY");
            sb.AppendLine("PID=" + pid);
            sb.AppendLine("MODULE_BASE_SOURCE=PROCESS_MAIN_MODULE");
            sb.AppendLine("MODULE_BASE=0x" + moduleBase.ToString("X8"));
            sb.AppendLine("MAX_HP=" + maxHp);
            sb.AppendLine("MAX_MP=" + maxMp);
            sb.AppendLine("CANDIDATE_PAIRS=" + pairs.Count);
            sb.AppendLine("SAMPLE_SECONDS=60");
            sb.AppendLine("RAW_MAP_ALLOWED=0");
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            for (var i = 0; i < pairs.Count; i++)
            {
                var p = pairs[i];
                var hpPct = Percent(p.HpValid, p.HpValid + p.HpInvalid);
                var mpPct = Percent(p.MpValid, p.MpValid + p.MpInvalid);
                var isDynamic = p.HpChanges > 0 && p.MpChanges > 0 && hpPct >= 95 && mpPct >= 95;
                var nearbyBoth = p.NearMaxHpOffsets.Count > 0 && p.NearMaxMpOffsets.Count > 0;
                string classification;
                if (isDynamic && nearbyBoth)
                {
                    classification = "DYNAMIC_WITH_MAX_NEIGHBORS";
                    dynamicWithMax++;
                }
                else if (isDynamic)
                {
                    classification = "DYNAMIC_CANDIDATE";
                    dynamic++;
                }
                else if (p.HpChanges == 0 && p.MpChanges == 0 && !nearbyBoth)
                {
                    classification = "STATIC_NO_MAX_REJECT";
                    staticReject++;
                }
                else
                {
                    classification = "WAIT_ACTIVITY";
                    wait++;
                }

                sb.AppendLine("[PAIR " + (i + 1) + "]");
                sb.AppendLine("DISTINCT_PIDS=" + p.Pids.Count);
                sb.AppendLine("HP_RVA=0x" + p.HpRva.ToString("X8"));
                sb.AppendLine("HP_ADDR=0x" + (moduleBase + p.HpRva).ToString("X8"));
                sb.AppendLine("HP_WIDTH=" + p.HpWidth);
                sb.AppendLine("HP_MIN=" + (p.HpValid > 0 ? p.HpMin.ToString() : "NA"));
                sb.AppendLine("HP_MAX_OBS=" + (p.HpValid > 0 ? p.HpMax.ToString() : "NA"));
                sb.AppendLine("HP_CHANGES=" + p.HpChanges);
                sb.AppendLine("HP_VALID_PCT=" + hpPct);
                sb.AppendLine("MP_RVA=0x" + p.MpRva.ToString("X8"));
                sb.AppendLine("MP_ADDR=0x" + (moduleBase + p.MpRva).ToString("X8"));
                sb.AppendLine("MP_WIDTH=" + p.MpWidth);
                sb.AppendLine("MP_MIN=" + (p.MpValid > 0 ? p.MpMin.ToString() : "NA"));
                sb.AppendLine("MP_MAX_OBS=" + (p.MpValid > 0 ? p.MpMax.ToString() : "NA"));
                sb.AppendLine("MP_CHANGES=" + p.MpChanges);
                sb.AppendLine("MP_VALID_PCT=" + mpPct);
                sb.AppendLine("PAIR_DISTANCE=0x" + Math.Abs(p.HpRva - p.MpRva).ToString("X"));
                sb.AppendLine("NEARBY_MAX_HP_OFFSETS=" + JoinOffsets(p.NearMaxHpOffsets));
                sb.AppendLine("NEARBY_MAX_MP_OFFSETS=" + JoinOffsets(p.NearMaxMpOffsets));
                sb.AppendLine("NEARBY_BOTH_MAXIMA=" + (nearbyBoth ? 1 : 0));
                sb.AppendLine("CLASSIFICATION=" + classification);
                sb.AppendLine();
            }

            sb.AppendLine("DYNAMIC_WITH_MAX_NEIGHBORS=" + dynamicWithMax);
            sb.AppendLine("DYNAMIC_CANDIDATES=" + dynamic);
            sb.AppendLine("WAIT_ACTIVITY=" + wait);
            sb.AppendLine("STATIC_NO_MAX_REJECTED=" + staticReject);

            string status;
            if (dynamicWithMax > 0)
                status = "MULTI_STABLE_RVA_DYNAMIC_WITH_MAX_NEIGHBORS count=" + dynamicWithMax;
            else if (dynamic > 0)
                status = "MULTI_STABLE_RVA_DYNAMIC_CANDIDATES count=" + dynamic;
            else if (wait > 0)
                status = "MULTI_STABLE_RVA_WAIT_ACTIVITY count=" + wait;
            else
                status = "ALL_STABLE_RVA_CANDIDATES_REJECTED_STATIC_NO_MAX";
            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(appDir, "runtime_hpmp_stable_rva_multi_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
            return status;
        }

        private static void Observe(
            RuntimeMemoryProbe probe,
            long address,
            int width,
            int ceiling,
            ref int min,
            ref int max,
            ref int changes,
            ref int valid,
            ref int invalid,
            ref int last,
            ref bool hasLast)
        {
            int value;
            if (!TryRead(probe, address, width, out value) || value < 0 || value > ceiling)
            {
                invalid++;
                return;
            }

            valid++;
            if (value < min) min = value;
            if (value > max) max = value;
            if (hasLast && value != last) changes++;
            last = value;
            hasLast = true;
        }

        private static void ParseSemanticHeader(string path, out int pid, out int maxHp, out int maxMp, out bool authority)
        {
            pid = 0;
            maxHp = 675;
            maxMp = 142;
            authority = false;
            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                int n;
                if (line.StartsWith("PID=") && int.TryParse(line.Substring(4), out n)) pid = n;
                else if (line.StartsWith("MAX_HP=") && int.TryParse(line.Substring(7), out n) && n > 0) maxHp = n;
                else if (line.StartsWith("MAX_MP=") && int.TryParse(line.Substring(7), out n) && n > 0) maxMp = n;
                else if (line.StartsWith("CLIENT_AUTHORITY=") && int.TryParse(line.Substring(17), out n)) authority = n == 1;
            }
        }

        private static List<PairProbe> ParsePointerHistory(string path)
        {
            var map = new Dictionary<string, PairProbe>(StringComparer.OrdinalIgnoreCase);
            foreach (var raw in File.ReadAllLines(path))
            {
                var tokens = ParseTokens(raw);
                string pidText, hpText, hpWidthText, mpText, mpWidthText;
                int pid, hpWidth, mpWidth;
                long hpRva, mpRva;
                if (!tokens.TryGetValue("PID", out pidText) || !int.TryParse(pidText, out pid) || pid <= 0) continue;
                if (!tokens.TryGetValue("HP_RVA", out hpText) || !TryHex(hpText, out hpRva)) continue;
                if (!tokens.TryGetValue("MP_RVA", out mpText) || !TryHex(mpText, out mpRva)) continue;
                if (!tokens.TryGetValue("HP_WIDTH", out hpWidthText) || !int.TryParse(hpWidthText, out hpWidth)) continue;
                if (!tokens.TryGetValue("MP_WIDTH", out mpWidthText) || !int.TryParse(mpWidthText, out mpWidth)) continue;

                var key = hpRva.ToString("X") + "/" + hpWidth + ":" + mpRva.ToString("X") + "/" + mpWidth;
                PairProbe p;
                if (!map.TryGetValue(key, out p))
                {
                    p = new PairProbe { HpRva = hpRva, HpWidth = hpWidth, MpRva = mpRva, MpWidth = mpWidth };
                    map.Add(key, p);
                }
                p.Pids.Add(pid);
            }
            return new List<PairProbe>(map.Values);
        }

        private static void RejectKnownGaugePairs(List<PairProbe> pairs, string crossHistoryPath, long moduleBase)
        {
            if (!File.Exists(crossHistoryPath)) return;
            var gaugeKeys = new HashSet<string>(StringComparer.OrdinalIgnoreCase);
            foreach (var raw in File.ReadAllLines(crossHistoryPath))
            {
                var tokens = ParseTokens(raw);
                string hpText, mpText, hpMaxText, mpMaxText;
                long hpAddr, mpAddr;
                int hpMax, mpMax;
                if (!tokens.TryGetValue("BEST_HP", out hpText) || !TryHistoryAddress(hpText, out hpAddr)) continue;
                if (!tokens.TryGetValue("BEST_MP", out mpText) || !TryHistoryAddress(mpText, out mpAddr)) continue;
                if (!tokens.TryGetValue("HP_MAX", out hpMaxText) || !int.TryParse(hpMaxText, out hpMax)) continue;
                if (!tokens.TryGetValue("MP_MAX", out mpMaxText) || !int.TryParse(mpMaxText, out mpMax)) continue;
                if (hpMax < 254 || hpMax > 257 || mpMax < 126 || mpMax > 129) continue;
                gaugeKeys.Add((hpAddr - moduleBase).ToString("X") + ":" + (mpAddr - moduleBase).ToString("X"));
            }

            pairs.RemoveAll(delegate(PairProbe p)
            {
                return gaugeKeys.Contains(p.HpRva.ToString("X") + ":" + p.MpRva.ToString("X"));
            });
        }

        private static bool TryHistoryAddress(string text, out long address)
        {
            address = 0;
            var slash = text.IndexOf('/');
            if (slash > 0) text = text.Substring(0, slash);
            return TryHex(text, out address);
        }

        private static void FindNearbyMaxima(RuntimeMemoryProbe probe, long anchor, int maxHp, int maxMp, List<int> hpOffsets, List<int> mpOffsets)
        {
            var start = anchor - 0x100;
            byte[] data;
            string error;
            if (!probe.TryReadBytes(new IntPtr(start), 0x200, out data, out error) || data == null) return;

            for (var i = 0; i + 2 <= data.Length; i += 2)
            {
                var v16 = (int)BitConverter.ToUInt16(data, i);
                if (v16 == maxHp && hpOffsets.Count < 16) hpOffsets.Add(i - 0x100);
                if (v16 == maxMp && mpOffsets.Count < 16) mpOffsets.Add(i - 0x100);
            }
            for (var i = 0; i + 4 <= data.Length; i += 4)
            {
                var v32 = BitConverter.ToInt32(data, i);
                if (v32 == maxHp && hpOffsets.Count < 16) hpOffsets.Add(i - 0x100);
                if (v32 == maxMp && mpOffsets.Count < 16) mpOffsets.Add(i - 0x100);
            }
        }

        private static bool TryRead(RuntimeMemoryProbe probe, long address, int width, out int value)
        {
            value = 0;
            byte[] data;
            string error;
            var size = width == 16 ? 2 : 4;
            if (!probe.TryReadBytes(new IntPtr(address), size, out data, out error)) return false;
            if (data == null || data.Length < size) return false;
            value = width == 16 ? (int)BitConverter.ToUInt16(data, 0) : BitConverter.ToInt32(data, 0);
            return true;
        }

        private static int Percent(int value, int total)
        {
            return total <= 0 ? 0 : (value * 100) / total;
        }

        private static string JoinOffsets(List<int> offsets)
        {
            if (offsets.Count == 0) return "NONE";
            var sb = new StringBuilder();
            for (var i = 0; i < offsets.Count; i++)
            {
                if (i > 0) sb.Append(',');
                sb.Append(offsets[i] >= 0 ? "+0x" : "-0x");
                sb.Append(Math.Abs(offsets[i]).ToString("X"));
            }
            return sb.ToString();
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
            if (string.IsNullOrEmpty(text) || string.Equals(text, "NA", StringComparison.OrdinalIgnoreCase)) return false;
            text = text.Trim();
            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase)) text = text.Substring(2);
            return long.TryParse(text, NumberStyles.HexNumber, CultureInfo.InvariantCulture, out value);
        }

        private static string SaveWaiting(string appDir, string reason)
        {
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=AUTO_HPMP_STABLE_RVA_MULTI_VERIFY");
            sb.AppendLine("STATUS=WAITING_STABLE_RVA_HISTORY");
            sb.AppendLine("REASON=" + reason);
            sb.AppendLine("RAW_MAP_ALLOWED=0");
            sb.AppendLine("MEMORY_WRITE=NO");
            File.WriteAllText(Path.Combine(appDir, "runtime_hpmp_stable_rva_multi_evidence.txt"), sb.ToString(), new UTF8Encoding(false));
            return "WAITING_STABLE_RVA_HISTORY";
        }

        private static void SaveError(string appDir, Exception ex)
        {
            try
            {
                File.WriteAllText(
                    Path.Combine(appDir, "runtime_hpmp_stable_rva_multi_evidence.txt"),
                    "TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + Environment.NewLine +
                    "MODE=AUTO_HPMP_STABLE_RVA_MULTI_VERIFY" + Environment.NewLine +
                    "RAW_MAP_ALLOWED=0" + Environment.NewLine +
                    "MEMORY_WRITE=NO" + Environment.NewLine +
                    "STATUS=ERROR" + Environment.NewLine +
                    "ERROR=" + ex.GetType().Name + ": " + ex.Message + Environment.NewLine,
                    new UTF8Encoding(false));
            }
            catch { }
        }
    }
}
