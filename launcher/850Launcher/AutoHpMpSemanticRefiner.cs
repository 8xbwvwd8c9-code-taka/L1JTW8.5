using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoHpMpSemanticRefiner
    {
        private sealed class Candidate
        {
            public string Kind;
            public long Address;
            public int Width;
            public int SeedMin;
            public int SeedMax;
            public int SeedChanges;
            public int Min = int.MaxValue;
            public int Max = int.MinValue;
            public int Last;
            public int Changes;
            public int Samples;
            public int InvalidSamples;
            public bool HasLast;
            public int Score;
            public long OppositeDistance = long.MaxValue;
        }

        private sealed class Result
        {
            public RuntimeSnapshot Runtime;
            public int MaxHp;
            public int MaxMp;
            public int Samples;
            public List<Candidate> Hp = new List<Candidate>();
            public List<Candidate> Mp = new List<Candidate>();
            public Candidate BestHp;
            public Candidate BestMp;
            public string Confidence = "NONE";
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private DateTime _retryAfterUtc = DateTime.MinValue;
        private string _status = "WAITING_BROAD";

        public AutoHpMpSemanticRefiner(string appDir)
        {
            _appDir = appDir;
        }

        public string Status
        {
            get
            {
                lock (_sync)
                {
                    return _running ? "RUNNING" : _status;
                }
            }
        }

        public void EnsureRunning(RuntimeSnapshot runtime)
        {
            if (runtime == null || !runtime.Connected || !runtime.ClientHashAuthoritative || runtime.ProcessId <= 0)
                return;

            var broadPath = Path.Combine(_appDir, "runtime_dynamic_broad_probe_evidence.txt");
            if (!File.Exists(broadPath))
            {
                lock (_sync) _status = "WAITING_BROAD";
                return;
            }

            lock (_sync)
            {
                if (_pid != runtime.ProcessId)
                {
                    _pid = runtime.ProcessId;
                    _status = "READY";
                    _retryAfterUtc = DateTime.MinValue;
                }

                if (_running) return;
                if (_status.StartsWith("PASS_", StringComparison.Ordinal)) return;
                if (DateTime.UtcNow < _retryAfterUtc) return;
                _running = true;
            }

            ThreadPool.QueueUserWorkItem(delegate
            {
                Result result = null;
                try
                {
                    result = Run(runtime, broadPath);
                    Save(result);
                    lock (_sync)
                    {
                        _status = result.BestHp != null && result.BestMp != null
                            ? "PASS_CANDIDATES " + result.Confidence
                            : "RETRY_PENDING";
                    }
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
                        _retryAfterUtc = DateTime.UtcNow.AddSeconds(15);
                    }
                }
            });
        }

        private Result Run(RuntimeSnapshot runtime, string broadPath)
        {
            int maxHp;
            int maxMp;
            var hpAll = new List<Candidate>();
            var mpAll = new List<Candidate>();
            ParseBroad(broadPath, out maxHp, out maxMp, hpAll, mpAll);

            if (hpAll.Count == 0 || mpAll.Count == 0)
                throw new InvalidOperationException("broad probe 沒有足夠 HP/MP candidates。");

            var hpStrong = new List<Candidate>();
            foreach (var c in hpAll)
            {
                if (c.SeedMax > maxMp && c.SeedMax <= maxHp)
                    hpStrong.Add(c);
            }
            if (hpStrong.Count == 0) hpStrong.AddRange(hpAll);

            var selected = new Dictionary<string, Candidate>(StringComparer.OrdinalIgnoreCase);

            foreach (var hp in hpStrong)
            {
                Candidate nearest;
                var distance = Nearest(hp.Address, mpAll, out nearest);
                if (nearest != null && distance <= 0x100)
                {
                    AddSelected(selected, hp);
                    AddSelected(selected, nearest);
                }
            }

            foreach (var mp in mpAll)
            {
                Candidate nearest;
                var distance = Nearest(mp.Address, hpStrong, out nearest);
                if (nearest != null && distance <= 0x100)
                {
                    AddSelected(selected, mp);
                    AddSelected(selected, nearest);
                }
            }

            if (selected.Count < 4)
            {
                SortSeed(hpStrong);
                SortSeed(mpAll);
                for (var i = 0; i < Math.Min(16, hpStrong.Count); i++) AddSelected(selected, hpStrong[i]);
                for (var i = 0; i < Math.Min(16, mpAll.Count); i++) AddSelected(selected, mpAll[i]);
            }

            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                const int intervalMs = 500;
                const int seconds = 60;
                var sampleCount = (seconds * 1000) / intervalMs;

                for (var sample = 0; sample < sampleCount; sample++)
                {
                    foreach (var c in selected.Values)
                    {
                        int value;
                        if (!TryRead(probe, c, out value, out error))
                        {
                            c.InvalidSamples++;
                            continue;
                        }

                        var ceiling = string.Equals(c.Kind, "MP", StringComparison.OrdinalIgnoreCase)
                            ? maxMp
                            : maxHp;
                        if (value < 0 || value > ceiling)
                        {
                            c.InvalidSamples++;
                            continue;
                        }

                        if (value < c.Min) c.Min = value;
                        if (value > c.Max) c.Max = value;
                        if (c.HasLast && value != c.Last) c.Changes++;
                        c.Last = value;
                        c.HasLast = true;
                        c.Samples++;
                    }

                    if (sample + 1 < sampleCount) Thread.Sleep(intervalMs);
                }
            }

            var result = new Result
            {
                Runtime = runtime,
                MaxHp = maxHp,
                MaxMp = maxMp,
                Samples = 120
            };

            foreach (var c in selected.Values)
            {
                if (string.Equals(c.Kind, "HP", StringComparison.OrdinalIgnoreCase))
                {
                    Candidate nearest;
                    c.OppositeDistance = Nearest(c.Address, mpAll, out nearest);
                    ScoreHp(c, maxHp, maxMp);
                    result.Hp.Add(c);
                }
                else
                {
                    Candidate nearest;
                    c.OppositeDistance = Nearest(c.Address, hpStrong, out nearest);
                    ScoreMp(c, maxMp);
                    result.Mp.Add(c);
                }
            }

            result.Hp.Sort(delegate(Candidate a, Candidate b) { return b.Score.CompareTo(a.Score); });
            result.Mp.Sort(delegate(Candidate a, Candidate b) { return b.Score.CompareTo(a.Score); });
            if (result.Hp.Count > 0) result.BestHp = result.Hp[0];
            if (result.Mp.Count > 0) result.BestMp = result.Mp[0];

            if (result.BestHp != null && result.BestMp != null)
            {
                var pairDistance = Math.Abs(result.BestHp.Address - result.BestMp.Address);
                var hpHitMax = result.BestHp.Max == maxHp;
                var mpHitMax = result.BestMp.Max == maxMp;
                if (pairDistance <= 0x80 && hpHitMax && mpHitMax)
                    result.Confidence = "HIGH_CANDIDATE";
                else if (pairDistance <= 0x100 || hpHitMax || mpHitMax)
                    result.Confidence = "MEDIUM_CANDIDATE";
                else
                    result.Confidence = "LOW_CANDIDATE";
            }

            return result;
        }

        private static void ScoreHp(Candidate c, int maxHp, int maxMp)
        {
            var score = 0;
            if (c.SeedMax > maxMp) score += 80;
            score += Math.Min(60, c.SeedChanges * 4);
            if (c.Samples > 0)
            {
                var range = Math.Max(0, c.Max - c.Min);
                score += Math.Min(100, range / 2);
                score += Math.Min(80, c.Changes * 3);
                if (c.Max == maxHp) score += 250;
                else if (c.Max >= (maxHp * 8) / 10) score += 70;
                else if (c.Max >= (maxHp * 5) / 10) score += 30;
                if (c.Min > 0) score += 10;
            }
            score += ProximityScore(c.OppositeDistance);
            score -= Math.Min(120, c.InvalidSamples * 10);
            c.Score = score;
        }

        private static void ScoreMp(Candidate c, int maxMp)
        {
            var score = 0;
            score += Math.Min(60, c.SeedChanges * 4);
            if (c.Samples > 0)
            {
                var range = Math.Max(0, c.Max - c.Min);
                score += Math.Min(80, range * 4);
                score += Math.Min(80, c.Changes * 3);
                if (c.Max == maxMp) score += 250;
                else
                {
                    var delta = Math.Max(0, maxMp - c.Max);
                    score += Math.Max(0, 100 - delta * 8);
                }
                if (c.Min > 0) score += 20;
            }
            score += ProximityScore(c.OppositeDistance);
            score -= Math.Min(120, c.InvalidSamples * 10);
            c.Score = score;
        }

        private static int ProximityScore(long distance)
        {
            if (distance <= 0x10) return 120;
            if (distance <= 0x20) return 100;
            if (distance <= 0x40) return 80;
            if (distance <= 0x80) return 60;
            if (distance <= 0x100) return 40;
            return 0;
        }

        private static void AddSelected(Dictionary<string, Candidate> selected, Candidate c)
        {
            var key = c.Kind + ":" + c.Width + ":" + c.Address.ToString("X");
            if (!selected.ContainsKey(key)) selected.Add(key, CloneSeed(c));
        }

        private static Candidate CloneSeed(Candidate c)
        {
            return new Candidate
            {
                Kind = c.Kind,
                Address = c.Address,
                Width = c.Width,
                SeedMin = c.SeedMin,
                SeedMax = c.SeedMax,
                SeedChanges = c.SeedChanges
            };
        }

        private static void SortSeed(List<Candidate> list)
        {
            list.Sort(delegate(Candidate a, Candidate b)
            {
                var change = b.SeedChanges.CompareTo(a.SeedChanges);
                if (change != 0) return change;
                var ar = a.SeedMax - a.SeedMin;
                var br = b.SeedMax - b.SeedMin;
                return br.CompareTo(ar);
            });
        }

        private static long Nearest(long address, List<Candidate> list, out Candidate nearest)
        {
            nearest = null;
            var best = long.MaxValue;
            foreach (var c in list)
            {
                var distance = Math.Abs(c.Address - address);
                if (distance < best)
                {
                    best = distance;
                    nearest = c;
                }
            }
            return best;
        }

        private static bool TryRead(RuntimeMemoryProbe probe, Candidate c, out int value, out string error)
        {
            value = 0;
            byte[] data;
            var size = c.Width == 16 ? 2 : 4;
            if (!probe.TryReadBytes(new IntPtr(c.Address), size, out data, out error)) return false;
            if (data == null || data.Length < size)
            {
                error = "short read";
                return false;
            }
            value = c.Width == 16
                ? (int)BitConverter.ToUInt16(data, 0)
                : BitConverter.ToInt32(data, 0);
            return true;
        }

        private static void ParseBroad(
            string path,
            out int maxHp,
            out int maxMp,
            List<Candidate> hp,
            List<Candidate> mp)
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
                if (line == "[HP_CANDIDATES]")
                {
                    section = "HP";
                    continue;
                }
                if (line == "[MP_CANDIDATES]")
                {
                    section = "MP";
                    continue;
                }
                if (!line.StartsWith("ADDR=", StringComparison.OrdinalIgnoreCase)) continue;
                if (section != "HP" && section != "MP") continue;

                Candidate c;
                if (!TryParseCandidate(line, section, out c)) continue;
                if (section == "HP") hp.Add(c); else mp.Add(c);
            }
        }

        private static bool TryParseCandidate(string line, string kind, out Candidate candidate)
        {
            candidate = null;
            var values = new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
            foreach (var token in line.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries))
            {
                var eq = token.IndexOf('=');
                if (eq <= 0 || eq >= token.Length - 1) continue;
                values[token.Substring(0, eq)] = token.Substring(eq + 1);
            }

            string addrText;
            string widthText;
            string minText;
            string maxText;
            string changesText;
            if (!values.TryGetValue("ADDR", out addrText) ||
                !values.TryGetValue("WIDTH", out widthText) ||
                !values.TryGetValue("MIN", out minText) ||
                !values.TryGetValue("MAX", out maxText) ||
                !values.TryGetValue("CHANGES", out changesText))
                return false;

            if (addrText.StartsWith("0x", StringComparison.OrdinalIgnoreCase)) addrText = addrText.Substring(2);
            long address;
            int width;
            int min;
            int max;
            int changes;
            if (!long.TryParse(addrText, NumberStyles.HexNumber, CultureInfo.InvariantCulture, out address)) return false;
            if (!int.TryParse(widthText, out width)) return false;
            if (!int.TryParse(minText, out min)) return false;
            if (!int.TryParse(maxText, out max)) return false;
            if (!int.TryParse(changesText, out changes)) return false;
            if (width != 16 && width != 32) return false;

            candidate = new Candidate
            {
                Kind = kind,
                Address = address,
                Width = width,
                SeedMin = min,
                SeedMax = max,
                SeedChanges = changes
            };
            return true;
        }

        private void Save(Result result)
        {
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=AUTO_SEMANTIC_REFINE");
            sb.AppendLine("PID=" + result.Runtime.ProcessId);
            sb.AppendLine("PROCESS_START_UTC=" + (result.Runtime.ProcessStartTimeUtc.HasValue ? result.Runtime.ProcessStartTimeUtc.Value.ToString("o") : ""));
            sb.AppendLine("CLIENT_SHA256=" + result.Runtime.ClientSha256);
            sb.AppendLine("CLIENT_AUTHORITY=" + (result.Runtime.ClientHashAuthoritative ? 1 : 0));
            sb.AppendLine("MAX_HP=" + result.MaxHp);
            sb.AppendLine("MAX_MP=" + result.MaxMp);
            sb.AppendLine("SAMPLES=" + result.Samples);
            sb.AppendLine("CONFIDENCE=" + result.Confidence);
            sb.AppendLine("MEMORY_WRITE=NO");
            if (result.BestHp != null) sb.AppendLine("BEST_HP=" + FormatCandidate(result.BestHp));
            if (result.BestMp != null) sb.AppendLine("BEST_MP=" + FormatCandidate(result.BestMp));
            sb.AppendLine();
            AppendCandidates(sb, "HP_REFINED", result.Hp, 100);
            AppendCandidates(sb, "MP_REFINED", result.Mp, 100);

            File.WriteAllText(
                Path.Combine(_appDir, "runtime_hpmp_semantic_refine_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
        }

        private static void AppendCandidates(StringBuilder sb, string name, List<Candidate> list, int limit)
        {
            sb.AppendLine("[" + name + "]");
            var shown = Math.Min(limit, list.Count);
            for (var i = 0; i < shown; i++) sb.AppendLine(FormatCandidate(list[i]));
        }

        private static string FormatCandidate(Candidate c)
        {
            return "ADDR=0x" + c.Address.ToString("X8") +
                   " WIDTH=" + c.Width +
                   " SEED_MIN=" + c.SeedMin +
                   " SEED_MAX=" + c.SeedMax +
                   " SEED_CHANGES=" + c.SeedChanges +
                   " OBS_MIN=" + (c.Samples > 0 ? c.Min.ToString() : "NA") +
                   " OBS_MAX=" + (c.Samples > 0 ? c.Max.ToString() : "NA") +
                   " OBS_CHANGES=" + c.Changes +
                   " SAMPLES=" + c.Samples +
                   " INVALID=" + c.InvalidSamples +
                   " OPP_DIST=" + (c.OppositeDistance == long.MaxValue ? "NA" : "0x" + c.OppositeDistance.ToString("X")) +
                   " SCORE=" + c.Score;
        }

        private void SaveError(RuntimeSnapshot runtime, Exception ex)
        {
            try
            {
                var sb = new StringBuilder();
                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("MODE=AUTO_SEMANTIC_REFINE");
                sb.AppendLine("PID=" + (runtime == null ? 0 : runtime.ProcessId));
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "runtime_hpmp_semantic_refine_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
