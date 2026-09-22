using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Globalization;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class PointerProbeControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly PointerChainScanner _scanner = new PointerChainScanner();

        private TextBox _target;
        private TextBox _maxOffset;
        private CheckBox _depth2;
        private Button _scan;
        private Button _copy;
        private Label _status;
        private ListView _results;

        public PointerProbeControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel { Dock = DockStyle.Top, Height = 84, Padding = new Padding(8) };
            Controls.Add(top);

            top.Controls.Add(new Label { Text = "目標位址", Left = 12, Top = 15, Width = 62 });
            _target = new TextBox { Left = 80, Top = 11, Width = 130 };
            top.Controls.Add(_target);

            top.Controls.Add(new Label { Text = "最大 Offset", Left = 226, Top = 15, Width = 72 });
            _maxOffset = new TextBox { Left = 302, Top = 11, Width = 82, Text = "0x400" };
            top.Controls.Add(_maxOffset);

            _depth2 = new CheckBox { Left = 400, Top = 13, Width = 110, Text = "包含 2 層", Checked = true };
            top.Controls.Add(_depth2);

            _scan = new Button { Text = "搜尋指標鏈", Left = 520, Top = 9, Width = 105 };
            _copy = new Button { Text = "複製設定", Left = 633, Top = 9, Width = 90, Enabled = false };
            top.Controls.Add(_scan);
            top.Controls.Add(_copy);

            _status = new Label
            {
                Left = 12, Top = 50, Width = 710,
                Text = "將已驗證的 HP/MP 候選絕對位址貼入後搜尋。"
            };
            top.Controls.Add(_status);

            _results = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _results.Columns.Add("層數", 55);
            _results.Columns.Add("runtime-map 表達式", 250);
            _results.Columns.Add("Root 位址", 105);
            _results.Columns.Add("Root RVA", 105);
            _results.Columns.Add("中間節點", 110);
            Controls.Add(_results);
            _results.BringToFront();

            _scan.Click += delegate { BeginScan(); };
            _copy.Click += delegate { CopySelected(); };
            _results.SelectedIndexChanged += delegate
            {
                _copy.Enabled = _results.SelectedItems.Count > 0;
            };
        }

        private void BeginScan()
        {
            long target;
            int maxOffset;

            try
            {
                target = ParseAddress(_target.Text);
                maxOffset = ParseOffset(_maxOffset.Text);
            }
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

            _scan.Enabled = false;
            _copy.Enabled = false;
            _status.Text = "正在唯讀搜尋 Lin.bin2 靜態 root 與 pointer chain...";

            var worker = new BackgroundWorker();
            worker.DoWork += delegate(object sender, DoWorkEventArgs e)
            {
                string error;
                if (!_scanner.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                string status;
                var list = _scanner.Find(
                    target,
                    runtime.ModuleBase.ToInt64(),
                    runtime.ModuleSize,
                    maxOffset,
                    300,
                    _depth2.Checked,
                    out status);

                e.Result = new object[] { list, status, runtime, target, maxOffset };
            };
            worker.RunWorkerCompleted += delegate(object sender, RunWorkerCompletedEventArgs e)
            {
                _scan.Enabled = true;

                if (e.Error != null)
                {
                    _status.Text = "搜尋失敗：" + e.Error.Message;
                    return;
                }

                var data = (object[])e.Result;
                var list = (List<PointerChainCandidate>)data[0];
                var status = (string)data[1];
                var rt = (RuntimeSnapshot)data[2];
                var targetValue = (long)data[3];
                var offsetValue = (int)data[4];

                ShowResults(list, rt.ModuleBase.ToInt64());
                _status.Text = status;
                SaveEvidence(list, rt, targetValue, offsetValue);
            };
            worker.RunWorkerAsync();
        }

        private void ShowResults(List<PointerChainCandidate> list, long moduleBase)
        {
            _results.BeginUpdate();
            _results.Items.Clear();

            foreach (var item in list)
            {
                var row = new ListViewItem(new[]
                {
                    item.Depth.ToString(),
                    item.Expression,
                    "0x" + item.RootAddress.ToString("X8"),
                    "0x" + item.BaseRva.ToString("X8"),
                    item.IntermediateAddress == 0
                        ? ""
                        : "0x" + item.IntermediateAddress.ToString("X8")
                });
                row.Tag = item.Expression;
                _results.Items.Add(row);
            }

            _results.EndUpdate();
        }

        private void CopySelected()
        {
            if (_results.SelectedItems.Count == 0) return;
            var expression = Convert.ToString(_results.SelectedItems[0].Tag);
            if (string.IsNullOrEmpty(expression)) return;

            Clipboard.SetText(expression);
            _status.Text = "已複製：" + expression;
        }

        private void SaveEvidence(
            List<PointerChainCandidate> list,
            RuntimeSnapshot runtime,
            long target,
            int maxOffset)
        {
            try
            {
                var path = Path.Combine(_appDir, "pointer_probe_evidence.txt");
                var sb = new StringBuilder();

                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("PID=" + runtime.ProcessId);
                sb.AppendLine("MODULE_BASE=0x" + runtime.ModuleBase.ToInt64().ToString("X8"));
                sb.AppendLine("MODULE_SIZE=" + runtime.ModuleSize);
                sb.AppendLine("TARGET=0x" + target.ToString("X8"));
                sb.AppendLine("MAX_OFFSET=0x" + maxOffset.ToString("X"));
                sb.AppendLine("RESULTS=" + list.Count);
                sb.AppendLine("MEMORY_WRITE=NO");
                sb.AppendLine();

                var shown = Math.Min(100, list.Count);
                for (var i = 0; i < shown; i++)
                {
                    var item = list[i];
                    sb.AppendLine(
                        "DEPTH=" + item.Depth +
                        " EXPR=" + item.Expression +
                        " ROOT=0x" + item.RootAddress.ToString("X8") +
                        " ROOT_RVA=0x" + item.BaseRva.ToString("X8") +
                        (item.IntermediateAddress == 0
                            ? ""
                            : " INNER=0x" + item.IntermediateAddress.ToString("X8")));
                }

                sb.AppendLine();
                File.AppendAllText(path, sb.ToString(), new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        private static long ParseAddress(string text)
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
                    throw new InvalidDataException("目標位址格式錯誤。");
            }
            else
            {
                if (!long.TryParse(text, out value))
                    throw new InvalidDataException("目標位址格式錯誤。");
            }

            if (value < 0x10000 || value > uint.MaxValue)
                throw new InvalidDataException("目標位址超出 x86 使用範圍。");

            return value;
        }

        private static int ParseOffset(string text)
        {
            text = text.Trim();
            int value;

            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase))
            {
                if (!int.TryParse(
                    text.Substring(2),
                    NumberStyles.HexNumber,
                    CultureInfo.InvariantCulture,
                    out value))
                    throw new InvalidDataException("最大 Offset 格式錯誤。");
            }
            else
            {
                if (!int.TryParse(text, out value))
                    throw new InvalidDataException("最大 Offset 格式錯誤。");
            }

            if (value < 0 || value > 0x10000)
                throw new InvalidDataException("最大 Offset 必須介於 0 與 0x10000。");

            return value;
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing) _scanner.Dispose();
            base.Dispose(disposing);
        }
    }
}
