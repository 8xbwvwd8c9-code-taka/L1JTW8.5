using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoHpMpEvidenceGuard
    {
        private sealed class PairRecord
        {
            public int Pid;
            public long Hp;
            public int HpWidth;
            public int HpChanges;
            public int HpMax = -1;
            public long Mp;
            public int MpWidth;
            public int MpChanges;
            public int MpMax = -1;
            public bool DynamicPair;
            public string Confidence = "";
        }

        private sealed class PointerRecord
        {
            public int Pid;
            public long Hp;
            public int HpWidth;
            public long Mp;
            public int MpWidth;
            public int StableSessions;
            public string Status = "";
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private bool _running;
        private DateTime _lastCrossWriteUtc = DateTime.MinValue;
        private string _status = "WAITING_CROSSCHECK";

        public AutoHpMpEvidenceGuard(string appDir)
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

            var crossPath = Path.Combine(_appDir, "runtime_hpmp_crosscheck_evidence.txt");
            if (!File.Exists(crossPath))
            {
                lock (_sync) _status = "WAITING_CROSSCHECK";
                return;
            }

            DateTime writeUtc;
            try { writeUtc = File.GetLastWriteTimeUtc(crossPath); }
            catch { return; }

            lock (_sync)
            {
                if (_running || writeUtc <= _lastCrossWriteUtc) return;
                _running = true;
            }

            ThreadPool.QueueUserWorkItem(delegate
            {
                try
                {
                    var status = Run(runtime, crossPath);
                    lock (_sync)
                    {
                        _status = status;
                        _lastCrossWriteUtc = writeUtc;
                    }
                }
                catch (Exception ex)
                {
                    SaveError(runtime, ex);
                    lock (_sync) _status = "ERROR";
                }
                finally
                {
                    lock (_sync) _running = false;
                }
            });
        }

        private string Run(RuntimeSnapshot runtime, string crossPath)
        {
            var current = ParseEvidence(crossPath);
            if (current == null)
                return SaveNoPair(runtime, crossPath);

            var history = ParseHistory(Path.Combine(_appDir, "runtime_hpmp_crosscheck_history.txt"));
            var pointer = ParsePointer(Path.Combine(_appDir, "runtime_hpmp_pointer_evidence.txt"));

            var currentPairPids = DistinctPairPids(history, current, false);
            var currentGaugePids = DistinctPairPids(history, current, true);
            if (current.Pid > 0 && current.DynamicPair) currentPairPids.Add(current.Pid);
            if (current.Pid > 0 && current.DynamicPair && IsGaugeLike(current)) currentGaugePids.Add(current.Pid);

            var pointerGaugePids = new HashSet<int>();
            if (pointer != null)
            {
                var probe = new PairRecord
                {
                    Hp = pointer.Hp,
                    HpWidth = pointer.HpWidth,
                    Mp = pointer.Mp,
                    MpWidth = pointer.MpWidth
                };
                pointerGaugePids = DistinctPairPids(history, probe, true);
            }

            var moduleBase = runtime.ModuleBase.ToInt64();
            var moduleEnd = moduleBase + runtime.ModuleSize;
            var hpRva = current.Hp >= moduleBase && current.Hp < moduleEnd ? current.Hp - moduleBase : -1;
            var mpRva = current.Mp >= moduleBase && current.Mp < moduleEnd ? current.Mp - moduleBase : -1;

            var currentGaugeLike = IsGaugeLike(current) || currentGaugePids.Count >= 2;
            string classification;
            if (current.Pid != runtime.ProcessId)
                classification = "STALE_CROSSCHECK";
            else if (!current.DynamicPair)
                classification = "NO_DYNAMIC_PAIR";
            else if (currentGaugeLike)
                classification = "UI_GAUGE_MIRROR_CANDIDATE";
            else if (string.Equals(current.Confidence, "HIGH", StringComparison.OrdinalIgnoreCase))
                classification = "RAW_HPMP_HIGH_CANDIDATE";
            else if (string.Equals(current.Confidence, "MEDIUM", StringComparison.OrdinalIgnoreCase))
                classification = "RAW_HPMP_MEDIUM_CANDIDATE";
            else
                classification = "RAW_HPMP_LOW_CANDIDATE";

            var pointerClassification = "NONE";
            var pointerMatchesCurrent = false;
            if (pointer != null)
            {
                pointerMatchesCurrent = pointer.Hp == current.Hp && pointer.Mp == current.Mp &&
                    pointer.HpWidth == current.HpWidth && pointer.MpWidth == current.MpWidth;
                if (pointerGaugePids.Count >= 2)
                    pointerClassification = "STABLE_UI_GAUGE_MIRROR";
                else if (pointer.StableSessions >= 2 && pointerMatchesCurrent)
                    pointerClassification = "STABLE_CURRENT_PAIR";
                else if (pointer.StableSessions >= 2)
                    pointerClassification = "STALE_STABLE_PAIR";
                else
                    pointerClassification = "UNSTABLE_POINTER_PAIR";
            }

            var rawMapAllowed = classification == "RAW_HPMP_HIGH_CANDIDATE" &&
                pointer != null && pointer.StableSessions >= 2 && pointerMatchesCurrent &&
                pointerGaugePids.Count == 0;

            var sb = Header(runtime, "AUTO_HPMP_EVIDENCE_GUARD");
            sb.AppendLine("CROSSCHECK_PID=" + current.Pid);
            sb.AppendLine("CROSSCHECK_CONFIDENCE=" + current.Confidence);
            sb.AppendLine("DYNAMIC_PAIR=" + (current.DynamicPair ? 1 : 0));
            sb.AppendLine("HP_ADDR=0x" + current.Hp.ToString("X8"));
            sb.AppendLine("HP_RVA=" + (hpRva >= 0 ? "0x" + hpRva.ToString("X8") : "NA"));
            sb.AppendLine("HP_WIDTH=" + current.HpWidth);
            sb.AppendLine("HP_CHANGES=" + current.HpChanges);
            sb.AppendLine("HP_OBS_MAX=" + current.HpMax);
            sb.AppendLine("MP_ADDR=0x" + current.Mp.ToString("X8"));
            sb.AppendLine("MP_RVA=" + (mpRva >= 0 ? "0x" + mpRva.ToString("X8") : "NA"));
            sb.AppendLine("MP_WIDTH=" + current.MpWidth);
            sb.AppendLine("MP_CHANGES=" + current.MpChanges);
            sb.AppendLine("MP_OBS_MAX=" + current.MpMax);
            sb.AppendLine("PAIR_DISTANCE=0x" + Math.Abs(current.Hp - current.Mp).ToString("X"));
            sb.AppendLine("PAIR_DISTINCT_PIDS=" + currentPairPids.Count);
            sb.AppendLine("GAUGE_LIKE_PIDS=" + currentGaugePids.Count);
            sb.AppendLine("GAUGE_SIGNATURE=HP_255_256_AND_MP_127_128");
            sb.AppendLine("CLASSIFICATION=" + classification);

            if (pointer != null)
            {
                sb.AppendLine("POINTER_PID=" + pointer.Pid);
                sb.AppendLine("POINTER_HP=0x" + pointer.Hp.ToString("X8") + "/" + pointer.HpWidth);
                sb.AppendLine("POINTER_MP=0x" + pointer.Mp.ToString("X8") + "/" + pointer.MpWidth);
                sb.AppendLine("POINTER_STABLE_SESSIONS=" + pointer.StableSessions);
                sb.AppendLine("POINTER_GAUGE_LIKE_PIDS=" + pointerGaugePids.Count);
                sb.AppendLine("POINTER_MATCHES_CURRENT=" + (pointerMatchesCurrent ? 1 : 0));
                sb.AppendLine("POINTER_CLASSIFICATION=" + pointerClassification);
                sb.AppendLine("POINTER_SOURCE_STATUS=" + pointer.Status);
            }
            else
            {
                sb.AppendLine("POINTER_CLASSIFICATION=MISSING");
            }

            sb.AppendLine("RAW_MAP_ALLOWED=" + (rawMapAllowed ? 1 : 0));
            sb.AppendLine("MEMORY_WRITE=NO");

            var status = rawMapAllowed
                ? "RAW_MAP_READY"
                : pointerClassification == "STABLE_UI_GAUGE_MIRROR"
                    ? "GAUGE_MIRROR_REJECT_RAW_MAP"
                    : classification;

            sb.AppendLine("STATUS=" + status);
            File.WriteAllText(
                Path.Combine(_appDir, "runtime_hpmp_guard_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
            return status;
        }

        private string SaveNoPair(RuntimeSnapshot runtime, string crossPath)
        {
            var pid = 0;
            var confidence = "";
            var dynamicPair = 0;
            try
            {
                foreach (var raw in File.ReadAllLines(crossPath))
                {
                    var line = raw.Trim();
                    int n;
                    if (line.StartsWith("PID=", StringComparison.OrdinalIgnoreCase) &&
                        int.TryParse(line.Substring(4), out n))
                        pid = n;
                    else if (line.StartsWith("CONFIDENCE=", StringComparison.OrdinalIgnoreCase))
                        confidence = line.Substring(11).Trim();
                    else if (line.StartsWith("DYNAMIC_PAIR=", StringComparison.OrdinalIgnoreCase) &&
                        int.TryParse(line.Substring(13), out n))
                        dynamicPair = n;
                }
            }
            catch
            {
            }

            var pointer = ParsePointer(Path.Combine(_appDir, "runtime_hpmp_pointer_evidence.txt"));
            var sb = Header(runtime, "AUTO_HPMP_EVIDENCE_GUARD");
            sb.AppendLine("CROSSCHECK_PID=" + pid);
            sb.AppendLine("CROSSCHECK_CONFIDENCE=" + confidence);
            sb.AppendLine("DYNAMIC_PAIR=" + dynamicPair);
            sb.AppendLine("CROSSCHECK_HAS_BEST_PAIR=0");
            if (pointer != null)
            {
                sb.AppendLine("LAST_POINTER_PID=" + pointer.Pid);
                sb.AppendLine("LAST_POINTER_HP=0x" + pointer.Hp.ToString("X8") + "/" + pointer.HpWidth);
                sb.AppendLine("LAST_POINTER_MP=0x" + pointer.Mp.ToString("X8") + "/" + pointer.MpWidth);
                sb.AppendLine("LAST_POINTER_SOURCE_STATUS=" + pointer.Status);
            }
            sb.AppendLine("RAW_MAP_ALLOWED=0");
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine("STATUS=WAITING_DYNAMIC_PAIR");
            File.WriteAllText(
                Path.Combine(_appDir, "runtime_hpmp_guard_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
            return "WAITING_DYNAMIC_PAIR";
        }

        private static HashSet<int> DistinctPairPids(List<PairRecord> history, PairRecord pair, bool gaugeOnly)
        {
            var pids = new HashSet<int>();
            if (pair == null) return pids;
            foreach (var r in history)
            {
                if (r.Hp != pair.Hp || r.Mp != pair.Mp ||
                    r.HpWidth != pair.HpWidth || r.MpWidth != pair.MpWidth)
                    continue;
                if (r.Pid <= 0 || !r.DynamicPair) continue;
                if (gaugeOnly && !IsGaugeLike(r)) continue;
                pids.Add(r.Pid);
            }
            return pids;
        }

        private static bool IsGaugeLike(PairRecord r)
        {
            if (r == null) return false;
            var hpGauge = r.HpMax >= 254 && r.HpMax <= 257;
            var mpGauge = r.MpMax >= 126 && r.MpMax <= 129;
            return hpGauge && mpGauge;
        }

        private static PairRecord ParseEvidence(string path)
        {
            var r = new PairRecord();
            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                int n;
                if (line.StartsWith("PID=") && int.TryParse(line.Substring(4), out n)) r.Pid = n;
                else if (line.StartsWith("CONFIDENCE=")) r.Confidence = line.Substring(11).Trim();
                else if (line.StartsWith("DYNAMIC_PAIR=") && int.TryParse(line.Substring(13), out n)) r.DynamicPair = n == 1;
                else if (line.StartsWith("BEST_HP=")) ParseBest(line.Substring(8), true, r);
                else if (line.StartsWith("BEST_MP=")) ParseBest(line.Substring(8), false, r);
            }
            return r.Hp > 0 && r.Mp > 0 ? r : null;
        }

        private static List<PairRecord> ParseHistory(string path)
        {
            var list = new List<PairRecord>();
            if (!File.Exists(path)) return list;
            foreach (var raw in File.ReadAllLines(path))
            {
                var map = ParseTokens(raw);
                var r = new PairRecord();
                int n;
                string text;
                if (map.TryGetValue("PID", out text)) int.TryParse(text, out r.Pid);
                if (map.TryGetValue("CONFIDENCE", out text)) r.Confidence = text;
                if (map.TryGetValue("DYNAMIC_PAIR", out text) && int.TryParse(text, out n)) r.DynamicPair = n == 1;
                if (map.TryGetValue("BEST_HP", out text)) ParseHistoryBest(text, true, r);
                if (map.TryGetValue("BEST_MP", out text)) ParseHistoryBest(text, false, r);
                if (map.TryGetValue("HP_CHANGES", out text)) int.TryParse(text, out r.HpChanges);
                if (map.TryGetValue("MP_CHANGES", out text)) int.TryParse(text, out r.MpChanges);
                if (map.TryGetValue("HP_MAX", out text)) int.TryParse(text, out r.HpMax);
                if (map.TryGetValue("MP_MAX", out text)) int.TryParse(text, out r.MpMax);
                if (r.Hp > 0 && r.Mp > 0) list.Add(r);
            }
            return list;
        }

        private static PointerRecord ParsePointer(string path)
        {
            if (!File.Exists(path)) return null;
            var r = new PointerRecord();
            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                int n;
                if (line.StartsWith("PID=") && int.TryParse(line.Substring(4), out n)) r.Pid = n;
                else if (line.StartsWith("HP_ADDR=0x")) TryHex(line.Substring(8), out r.Hp);
                else if (line.StartsWith("HP_WIDTH=") && int.TryParse(line.Substring(9), out n)) r.HpWidth = n;
                else if (line.StartsWith("MP_ADDR=0x")) TryHex(line.Substring(8), out r.Mp);
                else if (line.StartsWith("MP_WIDTH=") && int.TryParse(line.Substring(9), out n)) r.MpWidth = n;
                else if (line.StartsWith("DIRECT_RVA_STABLE_SESSIONS=") && int.TryParse(line.Substring(27), out n)) r.StableSessions = n;
                else if (line.StartsWith("STATUS=")) r.Status = line.Substring(7).Trim();
            }
            return r.Hp > 0 && r.Mp > 0 ? r : null;
        }

        private static void ParseBest(string text, bool hp, PairRecord r)
        {
            var map = ParseTokens(text);
            string s;
            long addr;
            int width = 0;
            int changes = 0;
            int max = -1;
            if (!map.TryGetValue("ADDR", out s) || !TryHex(s, out addr)) return;
            if (map.TryGetValue("WIDTH", out s)) int.TryParse(s, out width);
            if (map.TryGetValue("CHANGES", out s)) int.TryParse(s, out changes);
            if (map.TryGetValue("MAX", out s)) int.TryParse(s, out max);
            if (hp)
            {
                r.Hp = addr; r.HpWidth = width; r.HpChanges = changes; r.HpMax = max;
            }
            else
            {
                r.Mp = addr; r.MpWidth = width; r.MpChanges = changes; r.MpMax = max;
            }
        }

        private static void ParseHistoryBest(string text, bool hp, PairRecord r)
        {
            var slash = text.IndexOf('/');
            if (slash <= 0) return;
            long addr;
            int width;
            if (!TryHex(text.Substring(0, slash), out addr) || !int.TryParse(text.Substring(slash + 1), out width)) return;
            if (hp) { r.Hp = addr; r.HpWidth = width; }
            else { r.Mp = addr; r.MpWidth = width; }
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
            if (string.IsNullOrEmpty(text) || text == "NONE") return false;
            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase)) text = text.Substring(2);
            return long.TryParse(text, NumberStyles.HexNumber, CultureInfo.InvariantCulture, out value);
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
                var sb = Header(runtime, "AUTO_HPMP_EVIDENCE_GUARD");
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(Path.Combine(_appDir, "runtime_hpmp_guard_evidence.txt"), sb.ToString(), new UTF8Encoding(false));
            }
            catch { }
        }
    }
}
