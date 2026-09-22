using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Globalization;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class InventoryCollectionProbeControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe = new RuntimeMemoryProbe();

        private TextBox _recordA;
        private TextBox _recordB;
        private NumericUpDown _maxDistance;
        private Button _scan;
        private Button _clear;
        private Label _status;
        private ListView _pairs;
        private TextBox _nearby;

        private RuntimeSnapshot _runtime;
        private Dictionary<string, List<IntPtr>> _references =
            new Dictionary<string, List<IntPtr>>(StringComparer.OrdinalIgnoreCase);

        public InventoryCollectionProbeControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel { Dock = DockStyle.Top, Height = 88, Padding = new Padding(8) };
            Controls.Add(top);

            top.Controls.Add(new Label { Text = "Record A", Left = 12, Top = 15, Width = 58 });
            _recordA = new TextBox { Left = 74, Top = 11, Width = 120 };
            top.Controls.Add(_recordA);

            top.Controls.Add(new Label { Text = "Record B", Left = 210, Top = 15, Width = 58 });
            _recordB = new TextBox { Left = 272, Top = 11, Width = 120 };
            top.Controls.Add(_recordB);

            top.Controls.Add(new Label { Text = "最大間距", Left = 408, Top = 15, Width = 60 });
            _maxDistance = new NumericUpDown
            {
                Left = 472,
                Top = 11,
                Width = 90,
                Minimum = 16,
                Maximum = 65536,
                Increment = 16,
                Value = 1024
            };
            top.Controls.Add(_maxDistance);

            _scan = new Button { Text = "搜尋引用", Left = 578, Top = 9, Width = 90 };
            _clear = new Button { Text = "清除", Left = 676, Top = 9, Width = 70 };
            top.Controls.Add(_scan);
            top.Controls.Add(_clear);

            _status = new Label
            {
                Left = 12,
                Top = 50,
                Width = 730,
                Height = 28,
                Text = "輸入兩個已確認的 item record 位址；找出同時引用它們的鄰近記憶體區塊。"
            };
            top.Controls.Add(_status);

            var split = new SplitContainer
            {
                Dock = DockStyle.Fill,
                Orientation = Orientation.Vertical,
                SplitterDistance = 470
            };
            Controls.Add(split);
            split.BringToFront();

            _pairs = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _pairs.Columns.Add("A 引用位址", 105);
            _pairs.Columns.Add("B 引用位址", 105);
            _pairs.Columns.Add("間距", 70);
            _pairs.Columns.Add("A RVA", 90);
            _pairs.Columns.Add("B RVA", 90);
            split.Panel1.Controls.Add(_pairs);

            _nearby = new TextBox
            {
                Dock = DockStyle.Fill,
                Multiline = true,
                ReadOnly = true,
                ScrollBars = ScrollBars.Both,
                WordWrap = false,
                Font = new System.Drawing.Font("Consolas", 9.0f)
            };
            split.Panel2.Controls.Add(_nearby);

            _scan.Click += delegate { BeginScan(); };
            _clear.Click += delegate { ClearResults(); };
            _pairs.SelectedIndexChanged += delegate { ShowSelectedNeighborhood(); };
        }

        private void BeginScan()
        {
            long a;
            long b;

            try
            {
                a = ParseAddress(_recordA.Text, "Record A");
                b = ParseAddress(_recordB.Text, "Record B");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "輸入錯誤", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            if (a == b)
            {
                MessageBox.Show("Record A 與 Record B 必須是不同道具記錄。");
                return;
            }

            var runtime = _bridge.Read();
            if (!runtime.Connected)
            {
                MessageBox.Show(runtime.Status, "尚未連接遊戲", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            _scan.Enabled = false;
            _status.Text = "正在唯讀搜尋兩個 item record 的 pointer 引用...";

            var values = new Dictionary<string, int>
            {
                { "RecordA", unchecked((int)(uint)a) },
                { "RecordB", unchecked((int)(uint)b) }
            };

            var worker = new BackgroundWorker();
            worker.DoWork += delegate(object sender, DoWorkEventArgs e)
            {
                string error;
                if (!_probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                e.Result = _probe.FirstScan(values);
            };
            worker.RunWorkerCompleted += delegate(object sender, RunWorkerCompletedEventArgs e)
            {
                _scan.Enabled = true;

                if (e.Error != null)
                {
                    _status.Text = "搜尋失敗：" + e.Error.Message;
                    return;
                }

                _runtime = runtime;

                var result = (ProbeResult)e.Result;
                _references = result.Candidates;

                List<IntPtr> refsA;
                List<IntPtr> refsB;
                if (!_references.TryGetValue("RecordA", out refsA))
                    refsA = new List<IntPtr>();
                if (!_references.TryGetValue("RecordB", out refsB))
                    refsB = new List<IntPtr>();

                var pairs = InventoryCollectionAnalyzer.Correlate(
                    refsA,
                    refsB,
                    (long)_maxDistance.Value,
                    300);

                ShowPairs(pairs);
                SaveEvidence(a, b, refsA, refsB, pairs);

                _status.Text =
                    "引用搜尋完成：A=" + refsA.Count +
                    "，B=" + refsB.Count +
                    "，鄰近組合=" + pairs.Count + "。";
            };
            worker.RunWorkerAsync();
        }

        private void ShowPairs(IList<CollectionReferencePair> pairs)
        {
            _pairs.BeginUpdate();
            _pairs.Items.Clear();

            foreach (var pair in pairs)
            {
                var row = new ListViewItem(new[]
                {
                    "0x" + pair.RecordAReference.ToInt64().ToString("X8"),
                    "0x" + pair.RecordBReference.ToInt64().ToString("X8"),
                    "0x" + pair.Distance.ToString("X"),
                    ToRva(pair.RecordAReference.ToInt64()),
                    ToRva(pair.RecordBReference.ToInt64())
                });

                row.Tag = pair;
                _pairs.Items.Add(row);
            }

            _pairs.EndUpdate();
            _nearby.Clear();
        }

        private void ShowSelectedNeighborhood()
        {
            if (_pairs.SelectedItems.Count == 0)
                return;

            var pair = _pairs.SelectedItems[0].Tag as CollectionReferencePair;
            if (pair == null)
                return;

            var center = pair.ClusterStart +
                         ((pair.ClusterEnd - pair.ClusterStart) / 2);

            var rows = _probe.ReadDwords(new IntPtr(center), 0x80, 0x84);
            var sb = new StringBuilder();

            sb.AppendLine("A_REF=0x" + pair.RecordAReference.ToInt64().ToString("X8"));
            sb.AppendLine("B_REF=0x" + pair.RecordBReference.ToInt64().ToString("X8"));
            sb.AppendLine("DISTANCE=0x" + pair.Distance.ToString("X"));
            sb.AppendLine();
            sb.AppendLine("Address      Dec           Hex");
            sb.AppendLine("--------     -----------   ----------");

            foreach (var row in rows)
            {
                sb.AppendLine(
                    ("0x" + row.Address.ToInt64().ToString("X8")).PadRight(13) +
                    row.Value.ToString().PadRight(14) +
                    "0x" + unchecked((uint)row.Value).ToString("X8"));
            }

            _nearby.Text = sb.ToString();
        }

        private void SaveEvidence(
            long recordA,
            long recordB,
            IList<IntPtr> refsA,
            IList<IntPtr> refsB,
            IList<CollectionReferencePair> pairs)
        {
            try
            {
                var path = Path.Combine(_appDir, "inventory_collection_evidence.txt");
                var sb = new StringBuilder();

                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("PID=" + (_runtime == null ? 0 : _runtime.ProcessId));
                sb.AppendLine("PROCESS_START_UTC=" + (_runtime != null && _runtime.ProcessStartTimeUtc.HasValue ? _runtime.ProcessStartTimeUtc.Value.ToString("o") : ""));
                sb.AppendLine("CLIENT_SHA256=" + (_runtime == null ? "" : (_runtime.ClientSha256 ?? "")));
                sb.AppendLine("CLIENT_AUTHORITY=" + (_runtime != null && _runtime.ClientHashAuthoritative ? 1 : 0));
                sb.AppendLine("MODULE_BASE=" +
                    (_runtime == null ? "" : "0x" + _runtime.ModuleBase.ToInt64().ToString("X8")));
                sb.AppendLine("RECORD_A=0x" + recordA.ToString("X8"));
                sb.AppendLine("RECORD_B=0x" + recordB.ToString("X8"));
                sb.AppendLine("A_REFERENCES=" + refsA.Count);
                sb.AppendLine("B_REFERENCES=" + refsB.Count);
                sb.AppendLine("NEAR_PAIRS=" + pairs.Count);
                sb.AppendLine("MAX_DISTANCE=0x" + ((long)_maxDistance.Value).ToString("X"));
                sb.AppendLine("MEMORY_WRITE=NO");
                sb.AppendLine();

                var shown = Math.Min(100, pairs.Count);
                for (var i = 0; i < shown; i++)
                {
                    var pair = pairs[i];
                    sb.AppendLine(
                        "A_REF=0x" + pair.RecordAReference.ToInt64().ToString("X8") +
                        " B_REF=0x" + pair.RecordBReference.ToInt64().ToString("X8") +
                        " DIST=0x" + pair.Distance.ToString("X") +
                        " A_RVA=" + ToRva(pair.RecordAReference.ToInt64()) +
                        " B_RVA=" + ToRva(pair.RecordBReference.ToInt64()));
                }

                sb.AppendLine();
                File.AppendAllText(path, sb.ToString(), new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        private string ToRva(long address)
        {
            if (_runtime == null ||
                _runtime.ModuleBase == IntPtr.Zero ||
                _runtime.ModuleSize <= 0)
                return "";

            var start = _runtime.ModuleBase.ToInt64();
            var end = start + _runtime.ModuleSize;

            if (address < start || address >= end)
                return "非主模組";

            return "0x" + (address - start).ToString("X8");
        }

        private void ClearResults()
        {
            _probe.Detach();
            _runtime = null;
            _references.Clear();
            _pairs.Items.Clear();
            _nearby.Clear();
            _status.Text = "結果已清除。";
        }

        private static long ParseAddress(string text, string name)
        {
            text = text.Trim();
            long value;

            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase))
            {
                if (!long.TryParse(
                    text.Substring(2),
                    NumberStyles.HexNumber,
                    CultureInfo.InvariantCulture,
                    out value))
                    throw new InvalidDataException(name + " 格式錯誤。");
            }
            else
            {
                if (!long.TryParse(text, out value))
                    throw new InvalidDataException(name + " 格式錯誤。");
            }

            if (value < 0x10000 || value > uint.MaxValue)
                throw new InvalidDataException(name + " 超出 x86 位址範圍。");

            return value;
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing) _probe.Dispose();
            base.Dispose(disposing);
        }
    }
}
