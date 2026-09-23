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
                throw new InvalidDataException("crosscheck evidence missing BEST pair metadata");

            var historyPath = Path.Combine(_appDir, "runtime_hpmp_crosscheck_history.txt");
            var history = ParseHistory(historyPath);

            var pairPids = new HashSet<int>();
            var gaugePids = new HashSet<int>();
            foreach (var r in history)
            {
                if (r.Hp != current.Hp || r.Mp != current.Mp ||
                    r.HpWidth != current.HpWidth || r.MpWidth != current.MpWidth)
                    continue;

                if (r.Pid > 0 && r.DynamicPair)
                    pairPids.Add(r.Pid);
                if (r.Pid > 0 && r.DynamicPair && IsGaugeLike(r))
                    gaugePids.Add(r.Pid);
            }

            if (current.Pid > 0 && current.DynamicPair) pairPids.Add(current.Pid);
            if (current.Pid > 0 && current.DynamicPair && IsGaugeLike(current)) gaugePids.Add(current.Pid);

            var moduleBase = runtime.ModuleBase.ToInt64();
            var moduleEnd = moduleBase + runtime.ModuleSize;
            var hpRva = current.Hp >= moduleBase && current.Hp < moduleEnd ? current.Hp - moduleBase : -1;
            var mpRva = current.Mp >= moduleBase && current.Mp < moduleEnd ? current.Mp - moduleBase : -1;

            var gaugeLike = IsGaugeLike(current) || gaugePids.Count >= 2;
            string classification;
            if (current.Pid != runtime.ProcessId)
                classification = "STALE_CROSSCHECK";
            else if (!current.DynamicPair)
                classification = "NO_DYNAMIC_PAIR";
            else if (gaugeLike)
                classification = "UI_GAUGE_MIRROR_CANDIDATE";
            else if (string.Equals(current.Confidence, "HIGH", StringComparison.OrdinalIgnoreCase))
                classification = "RAW_HPMP_HIGH_CANDIDATE";
            else if (string.Equals(current.Confidence, "MEDIUM", StringComparison.OrdinalIgnoreCase))
                classification = "RAW_HPMP_MEDIUM_CANDIDATE";
            else
                classification = "RAW_HPMP_LOW_CANDIDATE";

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
            sb.AppendLine("PAIR_DISTINCT_PIDS=" + pairPids.Count);
            sb.AppendLine("GAUGE_LIKE_PIDS=" + gaugePids.Count);
            sb.AppendLine("GAUGE_SIGNATURE=HP_255_256_AND_MP_127_128");
            sb.AppendLine("CLASSIFICATION=" + classification);
            sb.AppendLine("RAW_MAP_ALLOWED=" + ((classification == "RAW_HPMP_HIGH_CANDIDATE") ? 1 : 0));
            sb.AppendLine("MEMORY_WRITE=NO");

            var status = classification;
            File.WriteAllText(
                Path.Combine(_appDir, "runtime_hpmp_guard_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
            return status;
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

        private static void ParseBest(string text, bool hp, PairRecord r)
        {
            var map = ParseTokens(text);
            string s;
            long addr;
            int width;
            int changes;
            int max;
            if (!map.TryGetValue("ADDR", out s) || !TryHex(s, out addr)) return;
            width = map.TryGetValue("WIDTH", out s) && int.TryParse(s, out width) ? width : 0;
            changes = map.TryGetValue("CHANGES", out s) && int.TryParse(s, out changes) ? changes : 0;
            max = map.TryGetValue("MAX", out s) && int.TryParse(s, out max) ? max : -1;
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
