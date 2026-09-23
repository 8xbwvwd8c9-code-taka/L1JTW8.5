using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoHpMpBroadProbe
    {
        private sealed class Window
        {
            public long Start;
            public long End;
        }

        private sealed class Stat
        {
            public long Address;
            public int Width;
            public int Min = int.MaxValue;
            public int Max = int.MinValue;
            public int Last;
            public int Changes;
            public int Samples;
            public bool HasLast;
        }

        private sealed class Result
        {
            public RuntimeSnapshot Runtime;
            public int MaxHp;
            public int MaxMp;
            public int Samples;
            public long WindowBytes;
            public List<long> HpAnchors = new List<long>();
            public List<long> MpAnchors = new List<long>();
            public List<Stat> Hp = new List<Stat>();
            public List<Stat> Mp = new List<Stat>();
            public int WindowCount;
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private DateTime _retryAfterUtc = DateTime.MinValue;
        private int _lastHpCount;
        private int _lastMpCount;

        public AutoHpMpBroadProbe(string appDir)
        {
            _appDir = appDir;
        }

        public string Status
        {
            get
            {
                lock (_sync)
                {
                    if (_running) return "RUNNING";
                    if (_lastHpCount > 0 && _lastMpCount > 0)
                        return "PASS_CANDIDATES HP=" + _lastHpCount + " MP=" + _lastMpCount;
                    if (_pid == 0) return "WAITING";
                    return "RETRY_PENDING HP=" + _lastHpCount + " MP=" + _lastMpCount;
                }
            }
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
                    _lastHpCount = 0;
                    _lastMpCount = 0;
                    _retryAfterUtc = DateTime.MinValue;
                }

                if (_running) return;
                if (_lastHpCount > 0 && _lastMpCount > 0) return;
                if (DateTime.UtcNow < _retryAfterUtc) return;
                _running = true;
            }

            ThreadPool.QueueUserWorkItem(delegate
            {
                Result result = null;
                try
                {
                    result = Run(runtime);
                    Save(result);
                }
                catch (Exception ex)
                {
                    SaveError(runtime, ex);
                }
                finally
                {
                    lock (_sync)
                    {
                        if (result != null)
                        {
                            _lastHpCount = result.Hp.Count;
                            _lastMpCount = result.Mp.Count;
                        }
                        _running = false;
                        _retryAfterUtc = DateTime.UtcNow.AddSeconds(10);
                    }
                }
            });
        }

        private Result Run(RuntimeSnapshot runtime)
        {
            int maxHp;
            int maxMp;
            LoadKnownMax(out maxHp, out maxMp);

            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                var first32 = probe.FirstScan(new Dictionary<string, int>
                {
                    { "MaxHP", maxHp },
                    { "MaxMP", maxMp }
                });

                var first16 = probe.FirstScanUInt16(new Dictionary<string, ushort>
                {
                    { "MaxHP", (ushort)Math.Min(ushort.MaxValue, maxHp) },
                    { "MaxMP", (ushort)Math.Min(ushort.MaxValue, maxMp) }
                });

                var hpAnchors = MergeAddresses(
                    first32.Candidates["MaxHP"],
                    first16.Candidates["MaxHP"]);
                var mpAnchors = MergeAddresses(
                    first32.Candidates["MaxMP"],
                    first16.Candidates["MaxMP"]);

                var allAnchors = new List<long>();
                allAnchors.AddRange(hpAnchors);
                allAnchors.AddRange(mpAnchors);
                allAnchors.Sort();

                var windows = BuildAnchorWindows(allAnchors, 0x400, 6000, 16L * 1024L * 1024L);
                if (windows.Count == 0)
                    throw new InvalidOperationException("找不到 MaxHP/MaxMP 錨點視窗。");

                var stats16 = new Dictionary<long, Stat>();
                var stats32 = new Dictionary<long, Stat>();
                const int intervalMs = 500;
                const int seconds = 30;
                var sampleCount = (seconds * 1000) / intervalMs;
                long windowBytes = 0;
                foreach (var w in windows) windowBytes += w.End - w.Start;

                for (var sample = 0; sample < sampleCount; sample++)
                {
                    foreach (var window in windows)
                    {
                        var sizeLong = window.End - window.Start;
                        if (sizeLong <= 0 || sizeLong > 1024 * 1024) continue;

                        byte[] data;
                        if (!probe.TryReadBytes(new IntPtr(window.Start), (int)sizeLong, out data, out error))
                            continue;

                        for (var offset = 0; offset <= data.Length - 2; offset += 2)
                        {
                            var address = window.Start + offset;
                            var value16 = (int)BitConverter.ToUInt16(data, offset);
                            if (value16 <= maxHp)
                                Track(stats16, address, 16, value16);

                            if ((address & 3L) == 0 && offset <= data.Length - 4)
                            {
                                var value32 = BitConverter.ToInt32(data, offset);
                                if (value32 >= 0 && value32 <= maxHp)
                                    Track(stats32, address, 32, value32);
                            }
                        }
                    }

                    if (sample + 1 < sampleCount)
                        Thread.Sleep(intervalMs);
                }

                var result = new Result
                {
                    Runtime = runtime,
                    MaxHp = maxHp,
                    MaxMp = maxMp,
                    Samples = sampleCount,
                    WindowCount = windows.Count,
                    WindowBytes = windowBytes,
                    HpAnchors = hpAnchors,
                    MpAnchors = mpAnchors
                };

                AddChanging(stats16, result);
                AddChanging(stats32, result);
                Sort(result.Hp, hpAnchors, mpAnchors);
                Sort(result.Mp, mpAnchors, hpAnchors);
                return result;
            }
        }

        private static void Track(Dictionary<long, Stat> stats, long address, int width, int value)
        {
            Stat stat;
            if (!stats.TryGetValue(address, out stat))
            {
                stat = new Stat { Address = address, Width = width };
                stats.Add(address, stat);
            }

            if (value < stat.Min) stat.Min = value;
            if (value > stat.Max) stat.Max = value;
            if (stat.HasLast && value != stat.Last) stat.Changes++;
            stat.Last = value;
            stat.HasLast = true;
            stat.Samples++;
        }

        private static void AddChanging(Dictionary<long, Stat> stats, Result result)
        {
            foreach (var stat in stats.Values)
            {
                if (stat.Changes <= 0) continue;
                if (stat.Min < 0) continue;

                if (stat.Max <= result.MaxHp)
                    result.Hp.Add(stat);
                if (stat.Max <= result.MaxMp)
                    result.Mp.Add(stat);
            }
        }

        private static List<long> MergeAddresses(List<IntPtr> a, List<IntPtr> b)
        {
            var set = new HashSet<long>();
            foreach (var value in a) set.Add(value.ToInt64());
            foreach (var value in b) set.Add(value.ToInt64());
            var list = new List<long>(set);
            list.Sort();
            return list;
        }

        private static List<Window> BuildAnchorWindows(
            List<long> anchors,
            long margin,
            int anchorLimit,
            long maxTotalBytes)
        {
            var raw = new List<Window>();
            var used = 0;
            foreach (var anchor in anchors)
            {
                if (used >= anchorLimit) break;
                var start = anchor - margin;
                if (start < 0x10000) start = 0x10000;
                var end = anchor + margin + 4;
                raw.Add(new Window
                {
                    Start = start & ~3L,
                    End = (end + 3) & ~3L
                });
                used++;
            }

            raw.Sort(delegate(Window x, Window y) { return x.Start.CompareTo(y.Start); });
            var merged = new List<Window>();
            foreach (var window in raw)
            {
                if (merged.Count == 0 || window.Start > merged[merged.Count - 1].End)
                {
                    merged.Add(new Window { Start = window.Start, End = window.End });
                }
                else if (window.End > merged[merged.Count - 1].End)
                {
                    merged[merged.Count - 1].End = window.End;
                }
            }

            var limited = new List<Window>();
            long total = 0;
            foreach (var window in merged)
            {
                var size = window.End - window.Start;
                if (size <= 0) continue;
                if (total + size > maxTotalBytes) break;
                limited.Add(window);
                total += size;
            }
            return limited;
        }

        private static long Distance(long address, List<long> anchors)
        {
            if (anchors.Count == 0) return long.MaxValue;
            var index = anchors.BinarySearch(address);
            if (index >= 0) return 0;
            index = ~index;
            var best = long.MaxValue;
            if (index < anchors.Count) best = Math.Abs(anchors[index] - address);
            if (index > 0) best = Math.Min(best, Math.Abs(anchors[index - 1] - address));
            return best;
        }

        private static void Sort(List<Stat> list, List<long> primary, List<long> secondary)
        {
            list.Sort(delegate(Stat x, Stat y)
            {
                var change = y.Changes.CompareTo(x.Changes);
                if (change != 0) return change;
                var xd = Math.Min(Distance(x.Address, primary), Distance(x.Address, secondary));
                var yd = Math.Min(Distance(y.Address, primary), Distance(y.Address, secondary));
                return xd.CompareTo(yd);
            });
        }

        private void LoadKnownMax(out int maxHp, out int maxMp)
        {
            maxHp = 675;
            maxMp = 142;
            var path = Path.Combine(_appDir, "runtime_probe_evidence.txt");
            if (!File.Exists(path)) return;

            try
            {
                foreach (var raw in File.ReadAllLines(path))
                {
                    var line = raw.Trim();
                    int value;
                    if (line.StartsWith("MAX_HP=") && int.TryParse(line.Substring(7), out value) && value > 0)
                        maxHp = value;
                    else if (line.StartsWith("MAX_MP=") && int.TryParse(line.Substring(7), out value) && value > 0)
                        maxMp = value;
                }
            }
            catch
            {
            }
        }

        private void Save(Result result)
        {
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=AUTO_BROAD_DIFFERENTIAL");
            sb.AppendLine("PID=" + result.Runtime.ProcessId);
            sb.AppendLine("PROCESS_START_UTC=" + (result.Runtime.ProcessStartTimeUtc.HasValue ? result.Runtime.ProcessStartTimeUtc.Value.ToString("o") : ""));
            sb.AppendLine("CLIENT_SHA256=" + result.Runtime.ClientSha256);
            sb.AppendLine("CLIENT_AUTHORITY=" + (result.Runtime.ClientHashAuthoritative ? 1 : 0));
            sb.AppendLine("MODULE_BASE=0x" + result.Runtime.ModuleBase.ToInt64().ToString("X8"));
            sb.AppendLine("MAX_HP=" + result.MaxHp);
            sb.AppendLine("MAX_MP=" + result.MaxMp);
            sb.AppendLine("SAMPLES=" + result.Samples);
            sb.AppendLine("WINDOWS=" + result.WindowCount);
            sb.AppendLine("WINDOW_BYTES=" + result.WindowBytes);
            sb.AppendLine("HP_ANCHORS_16_32=" + result.HpAnchors.Count);
            sb.AppendLine("MP_ANCHORS_16_32=" + result.MpAnchors.Count);
            sb.AppendLine("HP_DYNAMIC_CANDIDATES=" + result.Hp.Count);
            sb.AppendLine("MP_DYNAMIC_CANDIDATES=" + result.Mp.Count);
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();
            AppendStats(sb, "HP_CANDIDATES", result.Hp, result, 500);
            AppendStats(sb, "MP_CANDIDATES", result.Mp, result, 500);

            File.WriteAllText(
                Path.Combine(_appDir, "runtime_dynamic_broad_probe_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));
        }

        private static void AppendStats(StringBuilder sb, string name, List<Stat> list, Result result, int limit)
        {
            sb.AppendLine("[" + name + "]");
            var shown = Math.Min(limit, list.Count);
            for (var i = 0; i < shown; i++)
            {
                var stat = list[i];
                sb.AppendLine(
                    "ADDR=0x" + stat.Address.ToString("X8") +
                    " WIDTH=" + stat.Width +
                    " RVA=" + ToRva(stat.Address, result.Runtime) +
                    " MIN=" + stat.Min +
                    " MAX=" + stat.Max +
                    " CHANGES=" + stat.Changes +
                    " HP_DIST=" + FormatDistance(Distance(stat.Address, result.HpAnchors)) +
                    " MP_DIST=" + FormatDistance(Distance(stat.Address, result.MpAnchors)));
            }
            sb.AppendLine();
        }

        private static string FormatDistance(long value)
        {
            return value == long.MaxValue ? "--" : "0x" + value.ToString("X");
        }

        private static string ToRva(long address, RuntimeSnapshot runtime)
        {
            if (runtime == null || runtime.ModuleBase == IntPtr.Zero || runtime.ModuleSize <= 0)
                return "";
            var start = runtime.ModuleBase.ToInt64();
            var end = start + runtime.ModuleSize;
            return address >= start && address < end
                ? "0x" + (address - start).ToString("X8")
                : "非主模組";
        }

        private void SaveError(RuntimeSnapshot runtime, Exception ex)
        {
            try
            {
                var sb = new StringBuilder();
                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("MODE=AUTO_BROAD_DIFFERENTIAL");
                sb.AppendLine("PID=" + (runtime == null ? 0 : runtime.ProcessId));
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "runtime_dynamic_broad_probe_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
