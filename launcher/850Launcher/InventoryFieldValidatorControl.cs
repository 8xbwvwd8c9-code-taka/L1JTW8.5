using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class InventoryFieldValidatorControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe = new RuntimeMemoryProbe();

        private TextBox _recordBase;
        private TextBox _objectIdOffset;
        private TextBox _itemIdOffset;
        private TextBox _countOffset;
        private TextBox _enchantOffset;
        private TextBox _equippedOffset;

        private ComboBox _countWidth;
        private ComboBox _enchantWidth;
        private ComboBox _equippedWidth;

        private Button _read;
        private Button _saveSnapshot;
        private Label _status;
        private ListView _values;

        private RuntimeSnapshot _lastRuntime;
        private readonly Dictionary<string, long> _lastValues =
            new Dictionary<string, long>(StringComparer.OrdinalIgnoreCase);

        public InventoryFieldValidatorControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel { Dock = DockStyle.Top, Height = 150, Padding = new Padding(8) };
            Controls.Add(top);

            top.Controls.Add(new Label { Text = "Record Base", Left = 12, Top = 15, Width = 75 });
            _recordBase = new TextBox { Left = 90, Top = 11, Width = 120 };
            top.Controls.Add(_recordBase);

            AddOffsetField(top, "ObjectId", 12, 48, out _objectIdOffset);
            AddOffsetField(top, "ItemId", 178, 48, out _itemIdOffset);
            AddOffsetField(top, "Count", 344, 48, out _countOffset);
            AddOffsetField(top, "Enchant", 510, 48, out _enchantOffset);

            top.Controls.Add(new Label { Text = "Equipped", Left = 12, Top = 82, Width = 58 });
            _equippedOffset = new TextBox { Left = 74, Top = 78, Width = 74 };
            top.Controls.Add(_equippedOffset);

            top.Controls.Add(new Label { Text = "Count 寬度", Left = 178, Top = 82, Width = 68 });
            _countWidth = NewWidthCombo(250, 78, new[] { "4", "8" }, "4");
            top.Controls.Add(_countWidth);

            top.Controls.Add(new Label { Text = "Enchant 寬度", Left = 344, Top = 82, Width = 76 });
            _enchantWidth = NewWidthCombo(424, 78, new[] { "1", "2", "4" }, "2");
            top.Controls.Add(_enchantWidth);

            top.Controls.Add(new Label { Text = "Equipped 寬度", Left = 510, Top = 82, Width = 82 });
            _equippedWidth = NewWidthCombo(596, 78, new[] { "1", "4" }, "1");
            top.Controls.Add(_equippedWidth);

            _read = new Button { Text = "讀取欄位", Left = 12, Top = 112, Width = 100 };
            _saveSnapshot = new Button { Text = "保存快照", Left = 120, Top = 112, Width = 100, Enabled = false };
            top.Controls.Add(_read);
            top.Controls.Add(_saveSnapshot);

            _status = new Label
            {
                Left = 236,
                Top = 116,
                Width = 480,
                Text = "offset 可用十六進位，例如 +0x10、0x24，也可用負值 -0x08。"
            };
            top.Controls.Add(_status);

            _values = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _values.Columns.Add("欄位", 90);
            _values.Columns.Add("Offset", 80);
            _values.Columns.Add("位址", 110);
            _values.Columns.Add("十進位值", 130);
            _values.Columns.Add("Hex", 130);
            _values.Columns.Add("用途", 180);
            Controls.Add(_values);
            _values.BringToFront();

            _read.Click += delegate { ReadFields(); };
            _saveSnapshot.Click += delegate { SaveSnapshot(); };
        }

        private static void AddOffsetField(Control parent, string name, int left, int top, out TextBox box)
        {
            parent.Controls.Add(new Label { Text = name, Left = left, Top = top + 4, Width = 56 });
            box = new TextBox { Left = left + 60, Top = top, Width = 90 };
            parent.Controls.Add(box);
        }

        private static ComboBox NewWidthCombo(
            int left,
            int top,
            string[] values,
            string selected)
        {
            var box = new ComboBox
            {
                Left = left,
                Top = top,
                Width = 60,
                DropDownStyle = ComboBoxStyle.DropDownList
            };

            foreach (var value in values)
                box.Items.Add(value);

            box.SelectedItem = selected;
            return box;
        }

        private void ReadFields()
        {
            long recordBase;
            try
            {
                recordBase = ParseAddress(_recordBase.Text);
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

            var rows = new List<FieldRow>();

            try
            {
                rows.Add(ReadField("ObjectId", recordBase, _objectIdOffset.Text, 4, "背包物件唯一識別"));
                rows.Add(ReadField("ItemId", recordBase, _itemIdOffset.Text, 4, "道具樣板 ID"));
                rows.Add(ReadField("Count", recordBase, _countOffset.Text, SelectedWidth(_countWidth), "堆疊數量"));
                rows.Add(ReadField("Enchant", recordBase, _enchantOffset.Text, SelectedWidth(_enchantWidth), "強化值"));
                rows.Add(ReadField("Equipped", recordBase, _equippedOffset.Text, SelectedWidth(_equippedWidth), "裝備狀態"));
            }
            catch (Exception ex)
            {
                _status.Text = "讀取失敗：" + ex.Message;
                return;
            }

            _lastValues.Clear();
            foreach (var row in rows)
                _lastValues[row.Name] = row.Value;

            _lastRuntime = runtime;
            ShowRows(rows);
            _saveSnapshot.Enabled = true;
            _status.Text =
                "欄位已讀取。請用換裝、增減數量、換不同 item 等操作驗證每個 offset，不可單次命中就判 PASS。";
        }

        private FieldRow ReadField(
            string name,
            long recordBase,
            string offsetText,
            int width,
            string purpose)
        {
            if (string.IsNullOrWhiteSpace(offsetText))
                throw new InvalidDataException(name + " offset 尚未填寫。");

            var offset = ParseOffset(offsetText);
            var address = new IntPtr(recordBase + offset);

            long value;
            string error;

            if (width == 1)
            {
                byte v;
                if (!_probe.TryReadByte(address, out v, out error))
                    throw new InvalidOperationException(name + ": " + error);
                value = v;
            }
            else if (width == 2)
            {
                short v;
                if (!_probe.TryReadInt16(address, out v, out error))
                    throw new InvalidOperationException(name + ": " + error);
                value = v;
            }
            else if (width == 4)
            {
                int v;
                if (!_probe.TryReadInt32(address, out v, out error))
                    throw new InvalidOperationException(name + ": " + error);
                value = v;
            }
            else if (width == 8)
            {
                long v;
                if (!_probe.TryReadInt64(address, out v, out error))
                    throw new InvalidOperationException(name + ": " + error);
                value = v;
            }
            else
            {
                throw new InvalidDataException(name + " 欄位寬度不支援：" + width);
            }

            return new FieldRow
            {
                Name = name,
                Offset = offset,
                Address = address,
                Value = value,
                Width = width,
                Purpose = purpose
            };
        }

        private void ShowRows(IList<FieldRow> rows)
        {
            _values.BeginUpdate();
            _values.Items.Clear();

            foreach (var row in rows)
            {
                _values.Items.Add(new ListViewItem(new[]
                {
                    row.Name,
                    FormatOffset(row.Offset),
                    "0x" + row.Address.ToInt64().ToString("X8"),
                    row.Value.ToString(),
                    FormatHex(row.Value, row.Width),
                    row.Purpose
                }));
            }

            _values.EndUpdate();
        }

        private void SaveSnapshot()
        {
            if (_lastRuntime == null || _lastValues.Count == 0)
                return;

            try
            {
                var path = Path.Combine(_appDir, "inventory_field_validation.txt");
                var sb = new StringBuilder();

                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("PID=" + _lastRuntime.ProcessId);
                sb.AppendLine("PROCESS_START_UTC=" + (_lastRuntime.ProcessStartTimeUtc.HasValue ? _lastRuntime.ProcessStartTimeUtc.Value.ToString("o") : ""));
                sb.AppendLine("CLIENT_SHA256=" + (_lastRuntime.ClientSha256 ?? ""));
                sb.AppendLine("CLIENT_AUTHORITY=" + (_lastRuntime.ClientHashAuthoritative ? 1 : 0));
                sb.AppendLine("MODULE_BASE=0x" + _lastRuntime.ModuleBase.ToInt64().ToString("X8"));
                sb.AppendLine("RECORD_BASE=" + _recordBase.Text.Trim());
                sb.AppendLine("OBJECT_ID_OFFSET=" + _objectIdOffset.Text.Trim());
                sb.AppendLine("ITEM_ID_OFFSET=" + _itemIdOffset.Text.Trim());
                sb.AppendLine("COUNT_OFFSET=" + _countOffset.Text.Trim());
                sb.AppendLine("COUNT_WIDTH=" + SelectedWidth(_countWidth));
                sb.AppendLine("ENCHANT_OFFSET=" + _enchantOffset.Text.Trim());
                sb.AppendLine("ENCHANT_WIDTH=" + SelectedWidth(_enchantWidth));
                sb.AppendLine("EQUIPPED_OFFSET=" + _equippedOffset.Text.Trim());
                sb.AppendLine("EQUIPPED_WIDTH=" + SelectedWidth(_equippedWidth));
                sb.AppendLine("MEMORY_WRITE=NO");

                foreach (var kv in _lastValues)
                    sb.AppendLine(kv.Key.ToUpperInvariant() + "=" + kv.Value);

                sb.AppendLine();
                File.AppendAllText(path, sb.ToString(), new UTF8Encoding(false));
                _status.Text = "快照已寫入 inventory_field_validation.txt。";
            }
            catch (Exception ex)
            {
                _status.Text = "保存失敗：" + ex.Message;
            }
        }

        private static int SelectedWidth(ComboBox box)
        {
            return int.Parse(Convert.ToString(box.SelectedItem));
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
                    throw new InvalidDataException("Record Base 格式錯誤。");
            }
            else
            {
                if (!long.TryParse(text, out value))
                    throw new InvalidDataException("Record Base 格式錯誤。");
            }

            if (value < 0x10000 || value > uint.MaxValue)
                throw new InvalidDataException("Record Base 超出 x86 位址範圍。");

            return value;
        }

        private static int ParseOffset(string text)
        {
            text = text.Trim().Replace("+", "");

            var negative = text.StartsWith("-");
            if (negative)
                text = text.Substring(1);

            int value;
            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase))
            {
                if (!int.TryParse(
                    text.Substring(2),
                    NumberStyles.HexNumber,
                    CultureInfo.InvariantCulture,
                    out value))
                    throw new InvalidDataException("Offset 格式錯誤。");
            }
            else
            {
                if (!int.TryParse(text, out value))
                    throw new InvalidDataException("Offset 格式錯誤。");
            }

            return negative ? -value : value;
        }

        private static string FormatOffset(long offset)
        {
            if (offset == 0) return "+0x0";
            return offset > 0
                ? "+0x" + offset.ToString("X")
                : "-0x" + (-offset).ToString("X");
        }

        private static string FormatHex(long value, int width)
        {
            if (width == 1)
                return "0x" + unchecked((byte)value).ToString("X2");
            if (width == 2)
                return "0x" + unchecked((ushort)value).ToString("X4");
            if (width == 4)
                return "0x" + unchecked((uint)value).ToString("X8");
            return "0x" + unchecked((ulong)value).ToString("X16");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing) _probe.Dispose();
            base.Dispose(disposing);
        }

        private sealed class FieldRow
        {
            public string Name;
            public int Offset;
            public IntPtr Address;
            public long Value;
            public int Width;
            public string Purpose;
        }
    }
}
