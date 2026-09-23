using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeDynamicProbeControl : UserControl
    {
        private sealed class Window
        {
            public long Start;
            public long End;
        }

        private sealed class Stat
        {
            public long Address;
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
            public List<long> MaxHpAnchors = new List<long>();
            public List<long> MaxMpAnchors = new List<long>();
            public List<Stat> Hp = new List<Stat>();
            public List<Stat> Mp = new List<Stat>();
            public int WindowCount;
        }

        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe = new RuntimeMemoryProbe();
        private NumericUpDown _maxHp;
        private NumericUpDown _maxMp;
        private NumericUpDown _seconds;
        private NumericUpDown _interval;
        private Button _start;
        private Label _status;
        private ListView _results;

        public RuntimeDynamicProbeControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel { Dock = DockStyle.Top, Height = 92, Padding = new Padding(8) };
            Controls.Add(top);

            top.Controls.Add(new Label { Text = "MaxHP", Left = 12, Top = 16, Width = 48 });
            _maxHp = NewNumber(64, 12, 1, 2000000000, 675);
            top.Controls.Add(_maxHp);

            top.Controls.Add(new Label { Text = "MaxMP", Left = 184, Top = 16, Width = 48 });
            _maxMp = NewNumber(236, 12, 1, 2000000000, 142);
            top.Controls.Add(_maxMp);

            top.Controls.Add(new Label { Text = "秒", Left = 356, Top = 16, Width = 24 });
            _seconds = NewNumber(382, 12, 3, 60, 10);
            _seconds.Width = 58;
            top.Controls.Add(_seconds);

            top.Controls.Add(new Label { Text = "間隔ms", Left = 452, Top = 16, Width = 52 });
            _interval = NewNumber(508, 12, 50, 1000, 100);
            _interval.Width = 70;
            top.Controls.Add(_interval);

            _start = new Button { Text = "開始自動採樣", Left = 596, Top = 10, Width = 120 };
            top.Controls.Add(_start);

            _status = new Label
            {
                Left = 12,
                Top = 50,
                Width = 705,
                Height = 34,
                Text = "只需填 MaxHP / MaxMP；採樣期間讓角色自然受傷、回血、耗魔、回魔。"
            };
            top.Controls.Add(_status);

            _results = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _results.Columns.Add("類型", 70);
            _results.Columns.Add("位址", 110);
            _results.Columns.Add("RVA", 110);
            _results.Columns.Add("Min", 70);
            _results.Columns.Add("Max", 70);
            _results.Columns.Add("變化", 60);
            _results.Columns.Add("近MaxHP", 90);
            _results.Columns.Add("近MaxMP", 90);
            Controls.Add(_results);
            _results.BringToFront();

            _start.Click += delegate { BeginSample(); };
        }

        private static NumericUpDown NewNumber(int left, int top, decimal min, decimal max, decimal value)
        {
            return new NumericUpDown
            {
                Left = left,
                Top = top,
                Width = 100,
                Minimum = min,
                Maximum = max,
                Value = value
            };
        }

        private void BeginSample()
        {
            var runtime = _bridge.Read();
            if (!runtime.Connected)
            {
                MessageBox.Show(runtime.Status, "尚未連接遊戲");
                return;
            }

            if (!runtime.ClientHashAuthoritative)
            {
                MessageBox.Show("目前程序不是權威 850 Lin.bin2。", "CLIENT_AUTHORITY=0");
                return;
            }

            var maxHp = (int)_maxHp.Value;
            var maxMp = (int)_maxMp.Value;
            var seconds = (int)_seconds.Value;
            var interval = (int)_interval.Value;

            _start.Enabled = false;
            _status.Text = "正在找 MaxHP/MaxMP 錨點並自動採樣；不需輸入瞬間 HP/MP...";
            _results.Items.Clear();

            var worker = new BackgroundWorker();
            worker.DoWork += delegate(object sender, DoWorkEventArgs e)
            {
                string error;
                if (!_probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                var first = _probe.FirstScan(new Dictionary<string, int>
                {
                    { "MaxHP", maxHp },
                    { "MaxMP", maxMp }
                });

                var hpAnchors = ToLongs(first.Candidates["MaxHP"]);
                var mpAnchors = ToLongs(first.Candidates["MaxMP"]);
                var windows = BuildWindows(hpAnchors, mpAnchors, 0x200, 0x100, 4000);
                if (windows.Count == 0)
                    throw new InvalidOperationException("找不到彼此鄰近的 MaxHP / MaxMP 錨點。請確認最大 HP/MP。 ");

                var stats = new Dictionary<long, Stat>();
                var sampleCount = Math.Max(1, (seconds * 1000) / interval);
                for (var sample = 0; sample < sampleCount; sample++)
                {
                    foreach (var window in windows)
                    {
                        var sizeLong = window.End - window.Start;
                        if (sizeLong <= 0 || sizeLong > 1024 * 1024) continue;

                        byte[] data;
                        if (!_probe.TryReadBytes(new IntPtr(window.Start), (int)sizeLong, out data, out error))
                            continue;

                        for (var offset = 0; offset <= data.Length - 4; offset += 4)
                        {
                            var value = BitConverter.ToInt32(data, offset);
                            if (value < 0 || value > Math.Max(maxHp, maxMp)) continue;

                            var address = window.Start + offset;
                            Stat stat;
                            if (!stats.TryGetValue(address, out stat))
                            {
                                stat = new Stat { Address = address };
                                stats.Add(address, stat);
                            }

                            if (value < stat.Min) stat.Min = value;
                            if (value > stat.Max) stat.Max = value;
                            if (stat.HasLast && value != stat.Last) stat.Changes++;
                            stat.Last = value;
                            stat.HasLast = true;
                            stat.Samples++;
                        }
                    }
                    if (sample + 1 < sampleCount) Thread.Sleep(interval);
                }

                var result = new Result
                {
                    Runtime = runtime,
                    MaxHp = maxHp,
                    MaxMp = maxMp,
                    Samples = sampleCount,
                    MaxHpAnchors = hpAnchors,
                    MaxMpAnchors = mpAnchors,
                    WindowCount = windows.Count
                };

                foreach (var stat in stats.Values)
                {
                    if (stat.Changes <= 0) continue;
                    if (stat.Min >= 0 && stat.Max <= maxHp &&
                        (NearestDistance(stat.Address, hpAnchors) <= 0x200 ||
                         NearestDistance(stat.Address, mpAnchors) <= 0x200))
                        result.Hp.Add(stat);
                    if (stat.Min >= 0 && stat.Max <= maxMp &&
                        (NearestDistance(stat.Address, mpAnchors) <= 0x200 ||
                         NearestDistance(stat.Address, hpAnchors) <= 0x200))
                        result.Mp.Add(stat);
                }

                SortStats(result.Hp, hpAnchors, mpAnchors);
                SortStats(result.Mp, mpAnchors, hpAnchors);
                e.Result = result;
            };

            worker.RunWorkerCompleted += delegate(object sender, RunWorkerCompletedEventArgs e)
            {
                _start.Enabled = true;
                if (e.Error != null)
                {
                    _status.Text = "動態採樣失敗：" + e.Error.Message;
                    return;
                }

                var result = (Result)e.Result;
                Render(result);
                SaveEvidence(result);
                _status.Text = "完成：HP動態候選 " + result.Hp.Count +
                    " / MP動態候選 " + result.Mp.Count +
                    "；完整結果已輸出 runtime_dynamic_probe_evidence.txt";
            };

            worker.RunWorkerAsync();
        }

        private static List<long> ToLongs(List<IntPtr> values)
        {
            var result = new List<long>();
            foreach (var value in values) result.Add(value.ToInt64());
            result.Sort();
            return result;
        }

        private static List<Window> BuildWindows(
            List<long> hp,
            List<long> mp,
            long pairDistance,
            long margin,
            int pairLimit)
        {
            var raw = new List<Window>();
            var pairs = 0;
            var j0 = 0;
            foreach (var h in hp)
            {
                while (j0 < mp.Count && mp[j0] < h - pairDistance) j0++;
                for (var j = j0; j < mp.Count && mp[j] <= h + pairDistance; j++)
                {
                    var start = Math.Min(h, mp[j]) - margin;
                    if (start < 0x10000) start = 0x10000;
                    var end = Math.Max(h, mp[j]) + margin + 4;
                    raw.Add(new Window { Start = start & ~3L, End = (end + 3) & ~3L });
                    pairs++;
                    if (pairs >= pairLimit) break;
                }
                if (pairs >= pairLimit) break;
            }

            raw.Sort(delegate(Window a, Window b) { return a.Start.CompareTo(b.Start); });
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
            return merged;
        }

        private static long NearestDistance(long address, List<long> anchors)
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

        private static void SortStats(List<Stat> list, List<long> primary, List<long> secondary)
        {
            list.Sort(delegate(Stat a, Stat b)
            {
                var change = b.Changes.CompareTo(a.Changes);
                if (change != 0) return change;
                var ad = Math.Min(NearestDistance(a.Address, primary), NearestDistance(a.Address, secondary));
                var bd = Math.Min(NearestDistance(b.Address, primary), NearestDistance(b.Address, secondary));
                return ad.CompareTo(bd);
            });
        }

        private void Render(Result result)
        {
            _results.BeginUpdate();
            _results.Items.Clear();
            AddRows("HP", result.Hp, result, 100);
            AddRows("MP", result.Mp, result, 100);
            _results.EndUpdate();
        }

        private void AddRows(string kind, List<Stat> list, Result result, int limit)
        {
            var shown = Math.Min(limit, list.Count);
            for (var i = 0; i < shown; i++)
            {
                var stat = list[i];
                _results.Items.Add(new ListViewItem(new[]
                {
                    kind,
                    "0x" + stat.Address.ToString("X8"),
                    ToRva(stat.Address, result.Runtime),
                    stat.Min.ToString(),
                    stat.Max.ToString(),
                    stat.Changes.ToString(),
                    FormatDistance(NearestDistance(stat.Address, result.MaxHpAnchors)),
                    FormatDistance(NearestDistance(stat.Address, result.MaxMpAnchors))
                }));
            }
        }

        private static string FormatDistance(long value)
        {
            return value == long.MaxValue ? "--" : "0x" + value.ToString("X");
        }

        private static string ToRva(long address, RuntimeSnapshot runtime)
        {
            if (runtime == null || runtime.ModuleBase == IntPtr.Zero || runtime.ModuleSize <= 0) return "";
            var start = runtime.ModuleBase.ToInt64();
            var end = start + runtime.ModuleSize;
            return address >= start && address < end
                ? "0x" + (address - start).ToString("X8")
                : "非主模組";
        }

        private void SaveEvidence(Result result)
        {
            try
            {
                var sb = new StringBuilder();
                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("PID=" + result.Runtime.ProcessId);
                sb.AppendLine("PROCESS_START_UTC=" + (result.Runtime.ProcessStartTimeUtc.HasValue ? result.Runtime.ProcessStartTimeUtc.Value.ToString("o") : ""));
                sb.AppendLine("CLIENT_SHA256=" + result.Runtime.ClientSha256);
                sb.AppendLine("CLIENT_AUTHORITY=" + (result.Runtime.ClientHashAuthoritative ? 1 : 0));
                sb.AppendLine("MODULE_BASE=0x" + result.Runtime.ModuleBase.ToInt64().ToString("X8"));
                sb.AppendLine("MAX_HP=" + result.MaxHp);
                sb.AppendLine("MAX_MP=" + result.MaxMp);
                sb.AppendLine("SAMPLES=" + result.Samples);
                sb.AppendLine("WINDOWS=" + result.WindowCount);
                sb.AppendLine("MAX_HP_ANCHORS=" + result.MaxHpAnchors.Count);
                sb.AppendLine("MAX_MP_ANCHORS=" + result.MaxMpAnchors.Count);
                sb.AppendLine("HP_DYNAMIC_CANDIDATES=" + result.Hp.Count);
                sb.AppendLine("MP_DYNAMIC_CANDIDATES=" + result.Mp.Count);
                sb.AppendLine("MEMORY_WRITE=NO");
                sb.AppendLine();
                AppendEvidence(sb, "HP_CANDIDATES", result.Hp, result);
                AppendEvidence(sb, "MP_CANDIDATES", result.Mp, result);
                File.WriteAllText(Path.Combine(_appDir, "runtime_dynamic_probe_evidence.txt"), sb.ToString(), new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        private static void AppendEvidence(StringBuilder sb, string name, List<Stat> list, Result result)
        {
            sb.AppendLine("[" + name + "]");
            foreach (var stat in list)
            {
                sb.AppendLine(
                    "ADDR=0x" + stat.Address.ToString("X8") +
                    " RVA=" + ToRva(stat.Address, result.Runtime) +
                    " MIN=" + stat.Min +
                    " MAX=" + stat.Max +
                    " CHANGES=" + stat.Changes +
                    " NEAR_MAXHP=" + FormatDistance(NearestDistance(stat.Address, result.MaxHpAnchors)) +
                    " NEAR_MAXMP=" + FormatDistance(NearestDistance(stat.Address, result.MaxMpAnchors)));
            }
            sb.AppendLine();
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing) _probe.Dispose();
            base.Dispose(disposing);
        }
    }
}
