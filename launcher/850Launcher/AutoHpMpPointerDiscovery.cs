using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoHpMpPointerDiscovery
    {
        private sealed class Seed
        {
            public long Hp;
            public int HpWidth;
            public int HpChanges;
            public int HpValidPct;
            public long Mp;
            public int MpWidth;
            public int MpChanges;
            public int MpValidPct;
            public string Confidence = "";
            public int EvidencePid;
            public bool DynamicPair;
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private DateTime _lastCrossWriteUtc = DateTime.MinValue;
        private string _status = "WAITING_CROSSCHECK";

        public AutoHpMpPointerDiscovery(string appDir)
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

            var path = Path.Combine(_appDir, "runtime_hpmp_crosscheck_evidence.txt");
            if (!File.Exists(path))
            {
                lock (_sync) _status = "WAITING_CROSSCHECK";
                return;
            }

            DateTime writeUtc;
            try { writeUtc = File.GetLastWriteTimeUtc(path); }
            catch { return; }

            lock (_sync)
            {
                if (_pid != runtime.ProcessId)
                {
                    _pid = runtime.ProcessId;
                    _running = false;
                    _lastCrossWriteUtc = DateTime.MinValue;
                    _status = "WAITING_CROSSCHECK";
                }
                if (_running || writeUtc <= _lastCrossWriteUtc) return;
            }

            Seed seed;
            if (!TryLoadSeed(path, out seed))
            {
                lock (_sync) _status = "WAITING_CROSSCHECK";
                return;
            }

            if (seed.EvidencePid != runtime.ProcessId)
            {
                lock (_sync) _status = "WAITING_CURRENT_PID_CROSSCHECK";
                return;
            }

            if (!seed.DynamicPair || seed.HpChanges <= 0 || seed.MpChanges <= 0 ||
                seed.HpValidPct < 95 || seed.MpValidPct < 95)
            {
                lock (_sync)
                {
                    _status = "WAITING_DYNAMIC_CROSSCHECK";
                    _lastCrossWriteUtc = writeUtc;
                }
                return;
            }

            var confidenceAccepted =
                string.Equals(seed.Confidence, "HIGH", StringComparison.OrdinalIgnoreCase) ||
                string.Equals(seed.Confidence, "MEDIUM", StringComparison.OrdinalIgnoreCase);

            // LOW may still be a real current-HP/current-MP pair if both values are highly readable,
            // dynamic, and adjacent. Let pointer topology decide; the evidence guard still blocks maps.
            var pairDistance = Math.Abs(seed.Hp - seed.Mp);
            var lowAdjacentAccepted =
                string.Equals(seed.Confidence, "LOW", StringComparison.OrdinalIgnoreCase) &&
                pairDistance <= 0x20;

            if (!confidenceAccepted && !lowAdjacentAccepted)
            {
                lock (_sync)
                {
                    _status = "WAITING_POINTER_WORTHY_PAIR";
                    _lastCrossWriteUtc = writeUtc;
                }
                return;
            }

            lock (_sync)
            {
                if (_running || writeUtc <= _lastCrossWriteUtc) return;
                _running = true;
            }

            ThreadPool.QueueUserWorkItem(delegate
            {
                try
                {
                    Run(runtime, seed);
                    lock (_sync) _lastCrossWriteUtc = writeUtc;
                }
                catch (Exception ex)
                {
                    SaveError(runtime, ex);
                    lock (_sync)
                    {
                        _status = "ERROR_RETRY_NEW_CROSSCHECK";
                        _lastCrossWriteUtc = writeUtc;
                    }
                }
                finally
                {
                    lock (_sync) _running = false;
                }
            });
        }

        private void Run(RuntimeSnapshot runtime, Seed seed)
        {
            List<PointerChainCandidate> hpChains;
            List<PointerChainCandidate> mpChains;
            string hpStatus;
            string mpStatus;

            using (var scanner = new PointerChainScanner())
            {
                string error;
                if (!scanner.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                hpChains = scanner.Find(
                    seed.Hp,
                    runtime.ModuleBase.ToInt64(),
                    runtime.ModuleSize,
                    0x1000,
                    200,
                    false,
                    out hpStatus);

                mpChains = scanner.Find(
                    seed.Mp,
                    runtime.ModuleBase.ToInt64(),
                    runtime.ModuleSize,
                    0x1000,
                    200,
                    false,
                    out mpStatus);
            }

            var shared = new List<string>();
            foreach (var h in hpChains)
            {
                foreach (var m in mpChains)
                {
                    if (h.Depth != 1 || m.Depth != 1) continue;
                    if (h.BaseRva != m.BaseRva) continue;
                    shared.Add(
                        "BASE_RVA=0x" + h.BaseRva.ToString("X8") +
                        " HP_OFF=0x" + h.Offset1.ToString("X") +
                        " MP_OFF=0x" + m.Offset1.ToString("X") +
                        " OFF_DELTA=0x" + Math.Abs(h.Offset1 - m.Offset1).ToString("X"));
                }
            }

            var moduleBase = runtime.ModuleBase.ToInt64();
            var moduleEnd = moduleBase + runtime.ModuleSize;
            var hpInMain = seed.Hp >= moduleBase && seed.Hp < moduleEnd;
            var mpInMain = seed.Mp >= moduleBase && seed.Mp < moduleEnd;
            var hpRva = hpInMain ? seed.Hp - moduleBase : -1;
            var mpRva = mpInMain ? seed.Mp - moduleBase : -1;

            var stableSessions = CountPriorStableSessions(
                hpRva,
                seed.HpWidth,
                mpRva,
                seed.MpWidth,
                runtime.ProcessId);
            if (hpRva >= 0 && mpRva >= 0) stableSessions++;

            var sb = Header(runtime, "AUTO_HPMP_POINTER_DISCOVERY");
            sb.AppendLine("SEED_CONFIDENCE=" + seed.Confidence);
            sb.AppendLine("SEED_DYNAMIC_PAIR=" + (seed.DynamicPair ? 1 : 0));
            sb.AppendLine("HP_ADDR=0x" + seed.Hp.ToString("X8"));
            sb.AppendLine("HP_WIDTH=" + seed.HpWidth);
            sb.AppendLine("HP_CHANGES=" + seed.HpChanges);
            sb.AppendLine("HP_VALID_PCT=" + seed.HpValidPct);
            sb.AppendLine("MP_ADDR=0x" + seed.Mp.ToString("X8"));
            sb.AppendLine("MP_WIDTH=" + seed.MpWidth);
            sb.AppendLine("MP_CHANGES=" + seed.MpChanges);
            sb.AppendLine("MP_VALID_PCT=" + seed.MpValidPct);
            sb.AppendLine("PAIR_DISTANCE=0x" + Math.Abs(seed.Hp - seed.Mp).ToString("X"));
            sb.AppendLine("HP_IN_MAIN_MODULE=" + (hpInMain ? 1 : 0));
            sb.AppendLine("MP_IN_MAIN_MODULE=" + (mpInMain ? 1 : 0));
            sb.AppendLine("HP_DIRECT_RVA=" + (hpRva >= 0 ? "0x" + hpRva.ToString("X8") : "NA"));
            sb.AppendLine("MP_DIRECT_RVA=" + (mpRva >= 0 ? "0x" + mpRva.ToString("X8") : "NA"));
            sb.AppendLine("DIRECT_RVA_STABLE_SESSIONS=" + stableSessions);
            sb.AppendLine("HP_DEPTH1_CHAINS=" + hpChains.Count);
            sb.AppendLine("MP_DEPTH1_CHAINS=" + mpChains.Count);
            sb.AppendLine("SHARED_DEPTH1_ROOTS=" + shared.Count);
            sb.AppendLine("HP_SCAN_STATUS=" + hpStatus);
            sb.AppendLine("MP_SCAN_STATUS=" + mpStatus);
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            sb.AppendLine("[SHARED_ROOTS]");
            for (var i = 0; i < Math.Min(50, shared.Count); i++) sb.AppendLine(shared[i]);
            sb.AppendLine();

            sb.AppendLine("[HP_CHAINS]");
            for (var i = 0; i < Math.Min(50, hpChains.Count); i++) sb.AppendLine(hpChains[i].Expression);
            sb.AppendLine();

            sb.AppendLine("[MP_CHAINS]");
            for (var i = 0; i < Math.Min(50, mpChains.Count); i++) sb.AppendLine(mpChains[i].Expression);
            sb.AppendLine();

            string status;
            if (stableSessions >= 2 && hpInMain && mpInMain)
                status = "PASS_STABLE_DIRECT_RVA sessions=" + stableSessions + " sharedRoots=" + shared.Count;
            else if (shared.Count > 0)
                status = "PASS_SHARED_ROOTS count=" + shared.Count;
            else if (hpInMain && mpInMain)
                status = "DIRECT_RVA_CANDIDATE sessions=" + stableSessions;
            else
                status = "NO_STABLE_POINTER_YET";

            sb.AppendLine("STATUS=" + status);
            File.WriteAllText(
                Path.Combine(_appDir, "runtime_hpmp_pointer_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));

            AppendHistory(runtime, seed, hpRva, mpRva, shared.Count, status);
            lock (_sync) _status = status;
        }

        private int CountPriorStableSessions(long hpRva, int hpWidth, long mpRva, int mpWidth, int currentPid)
        {
            if (hpRva < 0 || mpRva < 0) return 0;
            var path = Path.Combine(_appDir, "runtime_hpmp_pointer_history.txt");
            if (!File.Exists(path)) return 0;

            var pids = new HashSet<int>();
            foreach (var raw in File.ReadAllLines(path))
            {
                var map = ParseTokens(raw);
                int pid;
                long oldHpRva;
                long oldMpRva;
                int oldHpWidth;
                int oldMpWidth;
                if (!TryInt(map, "PID", out pid) || pid <= 0 || pid == currentPid) continue;
                if (!TryHex(map, "HP_RVA", out oldHpRva) || !TryHex(map, "MP_RVA", out oldMpRva)) continue;
                if (!TryInt(map, "HP_WIDTH", out oldHpWidth) || !TryInt(map, "MP_WIDTH", out oldMpWidth)) continue;
                if (oldHpRva == hpRva && oldMpRva == mpRva && oldHpWidth == hpWidth && oldMpWidth == mpWidth)
                    pids.Add(pid);
            }
            return pids.Count;
        }

        private void AppendHistory(RuntimeSnapshot runtime, Seed seed, long hpRva, long mpRva, int sharedRoots, string status)
        {
            try
            {
                var line =
                    "TIME=" + DateTime.Now.ToString("yyyy-MM-dd_HH:mm:ss") +
                    " PID=" + runtime.ProcessId +
                    " START=" + (runtime.ProcessStartTimeUtc.HasValue ? runtime.ProcessStartTimeUtc.Value.ToString("o") : "") +
                    " CONF=" + seed.Confidence +
                    " HP_RVA=" + (hpRva >= 0 ? "0x" + hpRva.ToString("X8") : "NA") +
                    " HP_WIDTH=" + seed.HpWidth +
                    " MP_RVA=" + (mpRva >= 0 ? "0x" + mpRva.ToString("X8") : "NA") +
                    " MP_WIDTH=" + seed.MpWidth +
                    " SHARED_ROOTS=" + sharedRoots +
                    " STATUS=" + status.Replace(' ', '_') +
                    Environment.NewLine;
                File.AppendAllText(
                    Path.Combine(_appDir, "runtime_hpmp_pointer_history.txt"),
                    line,
                    new UTF8Encoding(false));
            }
            catch { }
        }

        private static bool TryLoadSeed(string path, out Seed seed)
        {
            seed = null;
            if (!File.Exists(path)) return false;

            var candidate = new Seed();
            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                int pid;
                int flag;
                if (line.StartsWith("PID=", StringComparison.OrdinalIgnoreCase) && int.TryParse(line.Substring(4), out pid))
                    candidate.EvidencePid = pid;
                else if (line.StartsWith("CONFIDENCE=", StringComparison.OrdinalIgnoreCase))
                    candidate.Confidence = line.Substring(11).Trim();
                else if (line.StartsWith("DYNAMIC_PAIR=", StringComparison.OrdinalIgnoreCase) && int.TryParse(line.Substring(13), out flag))
                    candidate.DynamicPair = flag == 1;
                else if (line.StartsWith("BEST_HP=", StringComparison.OrdinalIgnoreCase))
                {
                    long address;
                    int width;
                    int changes;
                    int validPct;
                    if (TryParseBest(line.Substring(8), out address, out width, out changes, out validPct))
                    {
                        candidate.Hp = address;
                        candidate.HpWidth = width;
                        candidate.HpChanges = changes;
                        candidate.HpValidPct = validPct;
                    }
                }
                else if (line.StartsWith("BEST_MP=", StringComparison.OrdinalIgnoreCase))
                {
                    long address;
                    int width;
                    int changes;
                    int validPct;
                    if (TryParseBest(line.Substring(8), out address, out width, out changes, out validPct))
                    {
                        candidate.Mp = address;
                        candidate.MpWidth = width;
                        candidate.MpChanges = changes;
                        candidate.MpValidPct = validPct;
                    }
                }
            }

            if (candidate.EvidencePid <= 0 || candidate.Hp <= 0 || candidate.Mp <= 0 ||
                (candidate.HpWidth != 16 && candidate.HpWidth != 32) ||
                (candidate.MpWidth != 16 && candidate.MpWidth != 32))
                return false;

            seed = candidate;
            return true;
        }

        private static bool TryParseBest(string text, out long address, out int width, out int changes, out int validPct)
        {
            address = 0;
            width = 0;
            changes = 0;
            validPct = 0;
            var map = ParseTokens(text);
            string addr;
            string widthText;
            string changesText;
            string validPctText;
            if (!map.TryGetValue("ADDR", out addr) || !map.TryGetValue("WIDTH", out widthText)) return false;
            map.TryGetValue("CHANGES", out changesText);
            map.TryGetValue("VALID_PCT", out validPctText);
            if (addr.StartsWith("0x", StringComparison.OrdinalIgnoreCase)) addr = addr.Substring(2);
            if (!long.TryParse(addr, NumberStyles.HexNumber, CultureInfo.InvariantCulture, out address)) return false;
            if (!int.TryParse(widthText, out width)) return false;
            if (!string.IsNullOrEmpty(changesText)) int.TryParse(changesText, out changes);
            if (!string.IsNullOrEmpty(validPctText)) int.TryParse(validPctText, out validPct);
            return true;
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

        private static bool TryInt(Dictionary<string, string> map, string key, out int value)
        {
            value = 0;
            string text;
            return map.TryGetValue(key, out text) && int.TryParse(text, out value);
        }

        private static bool TryHex(Dictionary<string, string> map, string key, out long value)
        {
            value = -1;
            string text;
            if (!map.TryGetValue(key, out text) || string.Equals(text, "NA", StringComparison.OrdinalIgnoreCase)) return false;
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
                var sb = Header(runtime, "AUTO_HPMP_POINTER_DISCOVERY");
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "runtime_hpmp_pointer_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch { }
        }
    }
}
