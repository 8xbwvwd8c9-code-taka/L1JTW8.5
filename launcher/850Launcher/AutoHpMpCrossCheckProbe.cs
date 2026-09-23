using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoHpMpCrossCheckProbe
    {
        private sealed class Candidate
        {
            public string Kind = "";
            public long Address;
            public int Width;
            public int SeedScore;
            public int Min = int.MaxValue;
            public int Max = int.MinValue;
            public int Last;
            public int Changes;
            public int Valid;
            public int Invalid;
            public bool HasLast;
            public int Score;
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private DateTime _retryAfterUtc = DateTime.MinValue;
        private string _status = "WAITING_SEMANTIC";

        public AutoHpMpCrossCheckProbe(string appDir)
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

            var path = Path.Combine(_appDir, "runtime_hpmp_semantic_refine_evidence.txt");
            if (!File.Exists(path))
            {
                lock (_sync) _status = "WAITING_SEMANTIC";
                return;
            }

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
                if (_status.StartsWith("HIGH", StringComparison.Ordinal)) return;
                _running = true;
            }

            ThreadPool.QueueUserWorkItem(delegate
            {
                try
                {
                    var status = Run(runtime, path);
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
                        _retryAfterUtc = DateTime.UtcNow.AddSeconds(30);
                    }
                }
            });
        }

        private string Run(RuntimeSnapshot runtime, string path)
        {
            int maxHp;
            int maxMp;
            var hp = new List<Candidate>();
            var mp = new List<Candidate>();
            Parse(path, out maxHp, out maxMp, hp, mp);
            if (hp.Count == 0 || mp.Count == 0)
                throw new InvalidDataException("semantic evidence has no refined candidates");

            hp.Sort(delegate(Candidate a, Candidate b) { return b.SeedScore.CompareTo(a.SeedScore); });
            mp.Sort(delegate(Candidate a, Candidate b) { return b.SeedScore.CompareTo(a.SeedScore); });
            Trim(hp, 32);
            Trim(mp, 32);

            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                const int samples = 90;
                const int intervalMs = 1000;
                for (var sample = 0; sample < samples; sample++)
                {
                    ObserveList(probe, hp, maxHp);
                    ObserveList(probe, mp, maxMp);
                    if (sample + 1 < samples) Thread.Sleep(intervalMs);
                }
            }

            foreach (var c in hp) Score(c, maxHp, maxMp, true);
            foreach (var c in mp) Score(c, maxMp, maxMp, false);
            hp.Sort(delegate(Candidate a, Candidate b) { return b.Score.CompareTo(a.Score); });
            mp.Sort(delegate(Candidate a, Candidate b) { return b.Score.CompareTo(a.Score); });

            Candidate bestHp = null;
            Candidate bestMp = null;
            var bestPairScore = int.MinValue;
            foreach (var h in hp)
            {
                foreach (var m in mp)
                {
                    var distance = Math.Abs(h.Address - m.Address);
                    var pair = h.Score + m.Score + Proximity(distance);
                    if (pair > bestPairScore)
                    {
                        bestPairScore = pair;
                        bestHp = h;
                        bestMp = m;
                    }
                }
            }

            var confidence = "LOW";
            if (bestHp != null && bestMp != null)
            {
                var hpPct = Percent(bestHp.Valid, bestHp.Valid + bestHp.Invalid);
                var mpPct = Percent(bestMp.Valid, bestMp.Valid + bestMp.Invalid);
                var distance = Math.Abs(bestHp.Address - bestMp.Address);
                var hpStrong = bestHp.Valid > 0 && bestHp.Changes > 0 && bestHp.Max >= (maxHp * 7) / 10;
                var mpStrong = bestMp.Valid > 0 && bestMp.Changes > 0 && bestMp.Max >= (maxMp * 9) / 10;
                if (hpPct >= 95 && mpPct >= 95 && hpStrong && mpStrong && distance <= 0x100)
                    confidence = "HIGH";
                else if (hpPct >= 85 && mpPct >= 85 && distance <= 0x200)
                    confidence = "MEDIUM";
            }

            var sb = Header(runtime, "AUTO_HPMP_CROSSCHECK");
            sb.AppendLine("MAX_HP=" + maxHp);
            sb.AppendLine("MAX_MP=" + maxMp);
            sb.AppendLine("SAMPLES=90");
            sb.AppendLine("CONFIDENCE=" + confidence);
            sb.AppendLine("MEMORY_WRITE=NO");
            if (bestHp != null) sb.AppendLine("BEST_HP=" + Format(bestHp));
            if (bestMp != null) sb.AppendLine("BEST_MP=" + Format(bestMp));
            if (bestHp != null && bestMp != null)
                sb.AppendLine("PAIR_DISTANCE=0x" + Math.Abs(bestHp.Address - bestMp.Address).ToString("X"));
            sb.AppendLine();
            Append(sb, "HP", hp, 20);
            Append(sb, "MP", mp, 20);

            File.WriteAllText(
                Path.Combine(_appDir, "runtime_hpmp_crosscheck_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));

            try
            {
                var history = new StringBuilder();
                history.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") +
                    " PID=" + runtime.ProcessId + " CONFIDENCE=" + confidence +
                    " BEST_HP=" + (bestHp == null ? "NONE" : "0x" + bestHp.Address.ToString("X8") + "/" + bestHp.Width) +
                    " BEST_MP=" + (bestMp == null ? "NONE" : "0x" + bestMp.Address.ToString("X8") + "/" + bestMp.Width) +
                    " PAIR=" + (bestHp == null || bestMp == null ? "NA" : "0x" + Math.Abs(bestHp.Address - bestMp.Address).ToString("X")));
                File.AppendAllText(
                    Path.Combine(_appDir, "runtime_hpmp_crosscheck_history.txt"),
                    history.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }

            return confidence + "_CANDIDATE";
        }

        private static void ObserveList(RuntimeMemoryProbe probe, List<Candidate> list, int ceiling)
        {
            foreach (var c in list)
            {
                int value;
                if (!TryRead(probe, c, out value) || value < 0 || value > ceiling)
                {
                    c.Invalid++;
                    continue;
                }

                c.Valid++;
                if (value < c.Min) c.Min = value;
                if (value > c.Max) c.Max = value;
                if (c.HasLast && value != c.Last) c.Changes++;
                c.Last = value;
                c.HasLast = true;
            }
        }

        private static bool TryRead(RuntimeMemoryProbe probe, Candidate c, out int value)
        {
            value = 0;
            byte[] data;
            string error;
            var size = c.Width == 16 ? 2 : 4;
            if (!probe.TryReadBytes(new IntPtr(c.Address), size, out data, out error)) return false;
            if (data == null || data.Length < size) return false;
            value = c.Width == 16 ? (int)BitConverter.ToUInt16(data, 0) : BitConverter.ToInt32(data, 0);
            return true;
        }

        private static void Score(Candidate c, int ceiling, int maxMp, bool hp)
        {
            var total = c.Valid + c.Invalid;
            var pct = Percent(c.Valid, total);
            var score = pct * 4;
            score += Math.Min(160, c.Changes * 4);
            if (c.Valid > 0)
            {
                var range = Math.Max(0, c.Max - c.Min);
                score += Math.Min(120, range / (hp ? 2 : 1));
                if (c.Max == ceiling) score += 260;
                else if (c.Max >= (ceiling * 9) / 10) score += 140;
                else if (c.Max >= (ceiling * 7) / 10) score += 70;
            }
            if (hp && c.Max <= maxMp) score -= 180;
            if (c.Invalid > 0) score -= Math.Min(400, c.Invalid * 8);
            if (pct < 80) score -= 250;
            c.Score = score;
        }

        private static int Proximity(long distance)
        {
            if (distance <= 0x10) return 200;
            if (distance <= 0x20) return 160;
            if (distance <= 0x40) return 120;
            if (distance <= 0x80) return 80;
            if (distance <= 0x100) return 50;
            if (distance <= 0x200) return 20;
            return 0;
        }

        private static int Percent(int value, int total)
        {
            return total <= 0 ? 0 : (value * 100) / total;
        }

        private static void Trim(List<Candidate> list, int limit)
        {
            if (list.Count > limit) list.RemoveRange(limit, list.Count - limit);
        }

        private static string Format(Candidate c)
        {
            return "ADDR=0x" + c.Address.ToString("X8") +
                   " WIDTH=" + c.Width +
                   " MIN=" + (c.Valid > 0 ? c.Min.ToString() : "NA") +
                   " MAX=" + (c.Valid > 0 ? c.Max.ToString() : "NA") +
                   " CHANGES=" + c.Changes +
                   " VALID=" + c.Valid +
                   " INVALID=" + c.Invalid +
                   " VALID_PCT=" + Percent(c.Valid, c.Valid + c.Invalid) +
                   " SCORE=" + c.Score;
        }

        private static void Append(StringBuilder sb, string name, List<Candidate> list, int limit)
        {
            sb.AppendLine("[" + name + "_CANDIDATES]");
            for (var i = 0; i < Math.Min(limit, list.Count); i++)
                sb.AppendLine(Format(list[i]));
            sb.AppendLine();
        }

        private static void Parse(string path, out int maxHp, out int maxMp, List<Candidate> hp, List<Candidate> mp)
        {
            maxHp = 675;
            maxMp = 142;
            var section = "";
            foreach (var raw in File.ReadAllLines(path))
            {
                var line = raw.Trim();
                int n;
                if (line.StartsWith("MAX_HP=") && int.TryParse(line.Substring(7), out n) && n > 0)
                {
                    maxHp = n;
                    continue;
                }
                if (line.StartsWith("MAX_MP=") && int.TryParse(line.Substring(7), out n) && n > 0)
                {
                    maxMp = n;
                    continue;
                }
                if (line == "[HP_REFINED]") { section = "HP"; continue; }
                if (line == "[MP_REFINED]") { section = "MP"; continue; }
                if (line.StartsWith("[", StringComparison.Ordinal)) { section = ""; continue; }
                if ((section != "HP" && section != "MP") || !line.StartsWith("ADDR=0x")) continue;

                Candidate c;
                if (!TryParse(line, section, out c)) continue;
                if (section == "HP") hp.Add(c); else mp.Add(c);
            }
        }

        private static bool TryParse(string line, string kind, out Candidate c)
        {
            c = null;
            var map = new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
            foreach (var token in line.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries))
            {
                var eq = token.IndexOf('=');
                if (eq <= 0 || eq >= token.Length - 1) continue;
                map[token.Substring(0, eq)] = token.Substring(eq + 1);
            }

            string addr;
            string width;
            string score;
            if (!map.TryGetValue("ADDR", out addr) || !map.TryGetValue("WIDTH", out width)) return false;
            map.TryGetValue("SCORE", out score);
            if (addr.StartsWith("0x", StringComparison.OrdinalIgnoreCase)) addr = addr.Substring(2);

            long address;
            int w;
            int s = 0;
            if (!long.TryParse(addr, NumberStyles.HexNumber, CultureInfo.InvariantCulture, out address)) return false;
            if (!int.TryParse(width, out w) || (w != 16 && w != 32)) return false;
            if (!string.IsNullOrEmpty(score)) int.TryParse(score, out s);

            c = new Candidate { Kind = kind, Address = address, Width = w, SeedScore = s };
            return true;
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
                var sb = Header(runtime, "AUTO_HPMP_CROSSCHECK");
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "runtime_hpmp_crosscheck_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
