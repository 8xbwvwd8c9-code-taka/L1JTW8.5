using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class InventoryProbeControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe = new RuntimeMemoryProbe();

        private TextBox _count;
        private Button _firstScan;
        private Button _refine;
        private Button _clear;
        private Label _status;
        private ListView _candidatesView;
        private TextBox _nearby;

        private Dictionary<string, List<IntPtr>> _candidates =
            new Dictionary<string, List<IntPtr>>(StringComparer.OrdinalIgnoreCase);

        private int _scanPid;
        private IntPtr _moduleBase = IntPtr.Zero;
        private int _moduleSize;

        public InventoryProbeControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel { Dock = DockStyle.Top, Height = 82, Padding = new Padding(8) };
            Controls.Add(top);

            top.Controls.Add(new Label { Text = "目前道具數量", Left = 12, Top = 15, Width = 90 });
            _count = new TextBox { Left = 108, Top = 11, Width = 90 };
            top.Controls.Add(_count);

            _firstScan = new Button { Text = "首次掃描", Left = 212, Top = 9, Width = 100 };
            _refine = new Button { Text = "再次篩選", Left = 320, Top = 9, Width = 100, Enabled = false };
            _clear = new Button { Text = "清除結果", Left = 428, Top = 9, Width = 100 };
            top.Controls.Add(_firstScan);
            top.Controls.Add(_refine);
            top.Controls.Add(_clear);

            _status = new Label
            {
                Left = 12, Top = 49, Width = 700,
                Text = "選一個可堆疊道具；輸入目前數量後掃描。"
            };
            top.Controls.Add(_status);

            var split = new SplitContainer
            {
                Dock = DockStyle.Fill,
                Orientation = Orientation.Vertical,
                SplitterDistance = 320
            };
            Controls.Add(split);
            split.BringToFront();

            _candidatesView = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _candidatesView.Columns.Add("候選位址", 115);
            _candidatesView.Columns.Add("Lin.bin2 RVA", 120);
            _candidatesView.Columns.Add("序號", 60);
            split.Panel1.Controls.Add(_candidatesView);

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

            _firstScan.Click += delegate { BeginFirstScan(); };
            _refine.Click += delegate { BeginRefine(); };
            _clear.Click += delegate { ClearResults(); };
            _candidatesView.SelectedIndexChanged += delegate { ShowSelectedNearby(); };
        }

        private int ReadCount()
        {
            int value;
            if (!int.TryParse(_count.Text.Trim(), out value) || value <= 0)
                throw new InvalidDataException("道具數量必須是大於 0 的整數。");
            return value;
        }

        private void BeginFirstScan()
        {
            int count;
            try { count = ReadCount(); }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "輸入錯誤", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            var runtime = _bridge.Read();
            if (!runtime.Connected)
            {
                MessageBox.Show(runtime.Status, "尚未連接遊戲", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            var values = new Dictionary<string, int> { { "ItemCount", count } };
            _scanPid = runtime.ProcessId;
            _moduleBase = runtime.ModuleBase;
            _moduleSize = runtime.ModuleSize;
            SetBusy(true, "正在唯讀掃描道具數量候選...");

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
                SetBusy(false, "");
                if (e.Error != null)
                {
                    _status.Text = "掃描失敗：" + e.Error.Message;
                    return;
                }

                var result = (ProbeResult)e.Result;
                _candidates = result.Candidates;
                _refine.Enabled = true;
                RefreshCandidates();
                _status.Text = result.Status + " 候選=" + CountCandidates();
                SaveEvidence("FIRST", count, result);
            };
            worker.RunWorkerAsync();
        }

        private void BeginRefine()
        {
            if (_candidates.Count == 0)
            {
                MessageBox.Show("請先執行首次掃描。");
                return;
            }

            int count;
            try { count = ReadCount(); }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "輸入錯誤", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            var runtime = _bridge.Read();
            if (!runtime.Connected || runtime.ProcessId != _scanPid)
            {
                MessageBox.Show("遊戲程序已變更，請重新首次掃描。", "程序已變更");
                return;
            }

            var values = new Dictionary<string, int> { { "ItemCount", count } };
            SetBusy(true, "正在依照新數量篩選候選...");

            var worker = new BackgroundWorker();
            worker.DoWork += delegate(object sender, DoWorkEventArgs e)
            {
                e.Result = _probe.Refine(values, _candidates);
            };
            worker.RunWorkerCompleted += delegate(object sender, RunWorkerCompletedEventArgs e)
            {
                SetBusy(false, "");
                if (e.Error != null)
                {
                    _status.Text = "篩選失敗：" + e.Error.Message;
                    return;
                }

                var result = (ProbeResult)e.Result;
                _candidates = result.Candidates;
                RefreshCandidates();
                _status.Text = result.Status + " 候選=" + CountCandidates();
                SaveEvidence("REFINE", count, result);
            };
            worker.RunWorkerAsync();
        }

        private void RefreshCandidates()
        {
            _candidatesView.BeginUpdate();
            _candidatesView.Items.Clear();

            List<IntPtr> list;
            if (_candidates.TryGetValue("ItemCount", out list))
            {
                var shown = Math.Min(500, list.Count);
                for (var i = 0; i < shown; i++)
                {
                    var address = list[i].ToInt64();
                    _candidatesView.Items.Add(new ListViewItem(new[]
                    {
                        "0x" + address.ToString("X8"),
                        ToRva(address),
                        (i + 1).ToString()
                    }) { Tag = list[i] });
                }
            }

            _candidatesView.EndUpdate();
            _nearby.Clear();
        }

        private void ShowSelectedNearby()
        {
            if (_candidatesView.SelectedItems.Count == 0) return;
            var tag = _candidatesView.SelectedItems[0].Tag;
            if (!(tag is IntPtr)) return;

            var center = (IntPtr)tag;
            var rows = _probe.ReadDwords(center, 0x40, 0x44);
            var sb = new StringBuilder();
            sb.AppendLine("中心位址 = 0x" + center.ToInt64().ToString("X8"));
            sb.AppendLine("中心 RVA = " + ToRva(center.ToInt64()));
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();
            sb.AppendLine("Offset   Address      Dec           Hex");
            sb.AppendLine("------   --------     -----------   ----------");

            foreach (var row in rows)
            {
                var offset = row.Address.ToInt64() - center.ToInt64();
                sb.AppendLine(
                    offset.ToString("+0000;-0000; 0000").PadRight(9) +
                    ("0x" + row.Address.ToInt64().ToString("X8")).PadRight(13) +
                    row.Value.ToString().PadRight(14) +
                    "0x" + unchecked((uint)row.Value).ToString("X8"));
            }

            _nearby.Text = sb.ToString();
        }

        private int CountCandidates()
        {
            List<IntPtr> list;
            return _candidates.TryGetValue("ItemCount", out list) ? list.Count : 0;
        }

        private string ToRva(long address)
        {
            if (_moduleBase == IntPtr.Zero || _moduleSize <= 0) return "";
            var start = _moduleBase.ToInt64();
            if (address < start || address >= start + _moduleSize) return "非主模組";
            return "0x" + (address - start).ToString("X8");
        }

        private void SaveEvidence(string stage, int count, ProbeResult result)
        {
            try
            {
                var path = Path.Combine(_appDir, "inventory_probe_evidence.txt");
                var sb = new StringBuilder();
                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("STAGE=" + stage);
                sb.AppendLine("PID=" + _scanPid);
                sb.AppendLine("MODULE_BASE=0x" + _moduleBase.ToInt64().ToString("X8"));
                sb.AppendLine("ITEM_COUNT=" + count);
                sb.AppendLine("CANDIDATES=" + CountCandidates());
                sb.AppendLine("STATUS=" + result.Status);
                sb.AppendLine("MEMORY_WRITE=NO");
                sb.AppendLine();

                List<IntPtr> list;
                if (_candidates.TryGetValue("ItemCount", out list))
                {
                    var shown = Math.Min(20, list.Count);
                    for (var i = 0; i < shown; i++)
                    {
                        var center = list[i];
                        sb.AppendLine("[CANDIDATE " + (i + 1) + "]");
                        sb.AppendLine("ADDR=0x" + center.ToInt64().ToString("X8"));
                        sb.AppendLine("RVA=" + ToRva(center.ToInt64()));

                        var rows = _probe.ReadDwords(center, 0x20, 0x24);
                        foreach (var row in rows)
                        {
                            var offset = row.Address.ToInt64() - center.ToInt64();
                            sb.AppendLine(
                                "OFF=" + offset +
                                " ADDR=0x" + row.Address.ToInt64().ToString("X8") +
                                " DEC=" + row.Value +
                                " HEX=0x" + unchecked((uint)row.Value).ToString("X8"));
                        }
                        sb.AppendLine();
                    }
                }

                File.AppendAllText(path, sb.ToString(), new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        private void ClearResults()
        {
            _probe.Detach();
            _scanPid = 0;
            _moduleBase = IntPtr.Zero;
            _moduleSize = 0;
            _candidates.Clear();
            _candidatesView.Items.Clear();
            _nearby.Clear();
            _refine.Enabled = false;
            _status.Text = "結果已清除。";
        }

        private void SetBusy(bool busy, string text)
        {
            _firstScan.Enabled = !busy;
            _refine.Enabled = !busy && _candidates.Count > 0;
            _clear.Enabled = !busy;
            if (text.Length > 0) _status.Text = text;
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing) _probe.Dispose();
            base.Dispose(disposing);
        }
    }
}
