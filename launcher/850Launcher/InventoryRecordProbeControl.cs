using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class InventoryRecordProbeControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe = new RuntimeMemoryProbe();

        private TextBox _centerAddress;
        private TextBox _knownItemId;
        private TextBox _currentCount;
        private NumericUpDown _range;
        private Button _captureA;
        private Button _compareB;
        private Button _clear;
        private Label _status;
        private ListView _grid;

        private List<ProbeDword> _baseline = new List<ProbeDword>();
        private IntPtr _baselineCenter = IntPtr.Zero;
        private int _baselinePid;

        public InventoryRecordProbeControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel { Dock = DockStyle.Top, Height = 112, Padding = new Padding(8) };
            Controls.Add(top);

            top.Controls.Add(new Label { Text = "Count 候選位址", Left = 12, Top = 15, Width = 90 });
            _centerAddress = new TextBox { Left = 108, Top = 11, Width = 120 };
            top.Controls.Add(_centerAddress);

            top.Controls.Add(new Label { Text = "已知 ItemId", Left = 244, Top = 15, Width = 70 });
            _knownItemId = new TextBox { Left = 320, Top = 11, Width = 90 };
            top.Controls.Add(_knownItemId);

            top.Controls.Add(new Label { Text = "目前數量", Left = 426, Top = 15, Width = 60 });
            _currentCount = new TextBox { Left = 490, Top = 11, Width = 80 };
            top.Controls.Add(_currentCount);

            top.Controls.Add(new Label { Text = "範圍", Left = 586, Top = 15, Width = 36 });
            _range = new NumericUpDown
            {
                Left = 626,
                Top = 11,
                Width = 90,
                Minimum = 32,
                Maximum = 1024,
                Increment = 16,
                Value = 128
            };
            top.Controls.Add(_range);

            _captureA = new Button { Text = "擷取 A", Left = 12, Top = 48, Width = 90 };
            _compareB = new Button { Text = "擷取 B / 比對", Left = 110, Top = 48, Width = 110, Enabled = false };
            _clear = new Button { Text = "清除", Left = 228, Top = 48, Width = 90 };
            top.Controls.Add(_captureA);
            top.Controls.Add(_compareB);
            top.Controls.Add(_clear);

            _status = new Label
            {
                Left = 336,
                Top = 52,
                Width = 380,
                Height = 42,
                Text = "先從「物品偵測」取得 count 候選位址，再做 A/B 結構比對。"
            };
            top.Controls.Add(_status);

            _grid = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _grid.Columns.Add("Offset", 75);
            _grid.Columns.Add("位址", 110);
            _grid.Columns.Add("A", 110);
            _grid.Columns.Add("B", 110);
            _grid.Columns.Add("變化", 60);
            _grid.Columns.Add("提示", 220);
            Controls.Add(_grid);
            _grid.BringToFront();

            _captureA.Click += delegate { CaptureBaseline(); };
            _compareB.Click += delegate { CompareCurrent(); };
            _clear.Click += delegate { ClearAll(); };
        }

        private void CaptureBaseline()
        {
            long center;
            int currentCount;
            int? knownItemId;

            try
            {
                center = ParseAddress(_centerAddress.Text);
                currentCount = ParsePositive(_currentCount.Text, "目前數量");
                knownItemId = ParseOptionalPositive(_knownItemId.Text, "ItemId");
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

            string error;
            if (!_probe.Attach(runtime.ProcessId, out error))
            {
                _status.Text = "連接失敗：" + error;
                return;
            }

            var range = (int)_range.Value;
            _baseline = _probe.ReadDwords(new IntPtr(center), range, range);
            _baselineCenter = new IntPtr(center);
            _baselinePid = runtime.ProcessId;

            if (_baseline.Count == 0)
            {
                _status.Text = "A 擷取失敗：沒有可讀資料。";
                _compareB.Enabled = false;
                return;
            }

            ShowSingleSnapshot(_baseline, knownItemId, currentCount);
            _compareB.Enabled = true;
            _status.Text =
                "A 已擷取 " + _baseline.Count +
                " 個 32-bit 欄位。現在改變該道具狀態/數量後再擷取 B。";

            SaveEvidence("A", runtime, currentCount, knownItemId, null);
        }

        private void CompareCurrent()
        {
            if (_baseline.Count == 0 || _baselineCenter == IntPtr.Zero)
            {
                MessageBox.Show("請先擷取 A。");
                return;
            }

            int currentCount;
            int? knownItemId;

            try
            {
                currentCount = ParsePositive(_currentCount.Text, "目前數量");
                knownItemId = ParseOptionalPositive(_knownItemId.Text, "ItemId");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "輸入錯誤", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            var runtime = _bridge.Read();
            if (!runtime.Connected || runtime.ProcessId != _baselinePid)
            {
                MessageBox.Show("遊戲程序已變更，請重新擷取 A。", "程序已變更");
                return;
            }

            string error;
            if (!_probe.Attach(runtime.ProcessId, out error))
            {
                _status.Text = "連接失敗：" + error;
                return;
            }

            var range = (int)_range.Value;
            var current = _probe.ReadDwords(_baselineCenter, range, range);

            var analyzed = InventoryRecordAnalyzer.Compare(
                _baselineCenter,
                _baseline,
                current,
                knownItemId,
                currentCount);

            ShowComparison(analyzed);

            var changed = 0;
            var itemIdMatches = 0;
            var countMatches = 0;

            foreach (var row in analyzed)
            {
                if (row.Changed) changed++;
                if (row.MatchesKnownItemId) itemIdMatches++;
                if (row.MatchesCurrentCount) countMatches++;
            }

            _status.Text =
                "B 比對完成：變化欄位=" + changed +
                "，ItemId 命中=" + itemIdMatches +
                "，目前數量命中=" + countMatches + "。";

            SaveEvidence("B", runtime, currentCount, knownItemId, analyzed);
        }

        private void ShowSingleSnapshot(
            IList<ProbeDword> rows,
            int? knownItemId,
            int currentCount)
        {
            _grid.BeginUpdate();
            _grid.Items.Clear();

            foreach (var row in rows)
            {
                var offset = row.Address.ToInt64() - _baselineCenter.ToInt64();
                var hints = new List<string>();

                if (knownItemId.HasValue && row.Value == knownItemId.Value)
                    hints.Add("ItemId?");
                if (row.Value == currentCount)
                    hints.Add("Count?");

                _grid.Items.Add(new ListViewItem(new[]
                {
                    FormatOffset(offset),
                    "0x" + row.Address.ToInt64().ToString("X8"),
                    row.Value.ToString(),
                    "",
                    "",
                    string.Join(", ", hints.ToArray())
                }));
            }

            _grid.EndUpdate();
        }

        private void ShowComparison(IList<InventoryRecordCell> rows)
        {
            _grid.BeginUpdate();
            _grid.Items.Clear();

            foreach (var row in rows)
            {
                var hints = new List<string>();

                if (row.MatchesKnownItemId)
                    hints.Add("ItemId?");
                if (row.MatchesCurrentCount)
                    hints.Add("Count?");
                if (!row.Changed && !row.MatchesKnownItemId && row.CurrentValue > 0)
                    hints.Add("穩定欄位");

                _grid.Items.Add(new ListViewItem(new[]
                {
                    FormatOffset(row.Offset),
                    "0x" + row.Address.ToInt64().ToString("X8"),
                    row.BaselineValue.ToString(),
                    row.CurrentValue.ToString(),
                    row.Changed ? "YES" : "",
                    string.Join(", ", hints.ToArray())
                }));
            }

            _grid.EndUpdate();
        }

        private void SaveEvidence(
            string stage,
            RuntimeSnapshot runtime,
            int currentCount,
            int? knownItemId,
            IList<InventoryRecordCell> analyzed)
        {
            try
            {
                var path = Path.Combine(_appDir, "inventory_record_evidence.txt");
                var sb = new StringBuilder();

                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("STAGE=" + stage);
                sb.AppendLine("PID=" + runtime.ProcessId);
                sb.AppendLine("PROCESS_START_UTC=" + (runtime.ProcessStartTimeUtc.HasValue ? runtime.ProcessStartTimeUtc.Value.ToString("o") : ""));
                sb.AppendLine("CLIENT_SHA256=" + (runtime.ClientSha256 ?? ""));
                sb.AppendLine("CLIENT_AUTHORITY=" + (runtime.ClientHashAuthoritative ? 1 : 0));
                sb.AppendLine("MODULE_BASE=0x" + runtime.ModuleBase.ToInt64().ToString("X8"));
                sb.AppendLine("CENTER=0x" + _baselineCenter.ToInt64().ToString("X8"));
                sb.AppendLine("CURRENT_COUNT=" + currentCount);
                sb.AppendLine("KNOWN_ITEM_ID=" + (knownItemId.HasValue ? knownItemId.Value.ToString() : ""));
                sb.AppendLine("RANGE=0x" + ((int)_range.Value).ToString("X"));
                sb.AppendLine("MEMORY_WRITE=NO");

                if (analyzed != null)
                {
                    sb.AppendLine();
                    foreach (var row in analyzed)
                    {
                        sb.AppendLine(
                            "OFF=" + row.Offset +
                            " ADDR=0x" + row.Address.ToInt64().ToString("X8") +
                            " A=" + row.BaselineValue +
                            " B=" + row.CurrentValue +
                            " CHANGED=" + (row.Changed ? 1 : 0) +
                            " ITEMID_MATCH=" + (row.MatchesKnownItemId ? 1 : 0) +
                            " COUNT_MATCH=" + (row.MatchesCurrentCount ? 1 : 0));
                    }
                }

                sb.AppendLine();
                File.AppendAllText(path, sb.ToString(), new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        private void ClearAll()
        {
            _probe.Detach();
            _baseline.Clear();
            _baselineCenter = IntPtr.Zero;
            _baselinePid = 0;
            _grid.Items.Clear();
            _compareB.Enabled = false;
            _status.Text = "結果已清除。";
        }

        private static string FormatOffset(long offset)
        {
            if (offset == 0) return "+0x0";
            return offset > 0
                ? "+0x" + offset.ToString("X")
                : "-0x" + (-offset).ToString("X");
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
                    throw new InvalidDataException("候選位址格式錯誤。");
            }
            else
            {
                if (!long.TryParse(text, out value))
                    throw new InvalidDataException("候選位址格式錯誤。");
            }

            if (value < 0x10000 || value > uint.MaxValue)
                throw new InvalidDataException("候選位址超出 x86 範圍。");

            return value;
        }

        private static int ParsePositive(string text, string name)
        {
            int value;
            if (!int.TryParse(text.Trim(), out value) || value <= 0)
                throw new InvalidDataException(name + " 必須是大於 0 的整數。");
            return value;
        }

        private static int? ParseOptionalPositive(string text, string name)
        {
            if (string.IsNullOrWhiteSpace(text))
                return null;

            return ParsePositive(text, name);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing) _probe.Dispose();
            base.Dispose(disposing);
        }
    }
}
