using System;
using System.Collections.Generic;
using System.IO;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class InventoryMapControl : UserControl
    {
        private readonly string _path;

        private ComboBox _mode;
        private TextBox _root;
        private TextBox _maxItems;

        private readonly Dictionary<string, TextBox> _collection =
            new Dictionary<string, TextBox>(StringComparer.OrdinalIgnoreCase);

        private readonly Dictionary<string, TextBox> _record =
            new Dictionary<string, TextBox>(StringComparer.OrdinalIgnoreCase);

        private Label _status;

        public InventoryMapControl(string appDir)
        {
            _path = Path.Combine(appDir, "inventory-map.ini");
            Dock = DockStyle.Fill;
            AutoScroll = true;
            BuildUi();
            LoadValues();
        }

        private void BuildUi()
        {
            var panel = new Panel
            {
                Dock = DockStyle.Top,
                Height = 720,
                AutoScroll = true
            };
            Controls.Add(panel);

            panel.Controls.Add(new Label
            {
                Left = 16,
                Top = 12,
                Width = 700,
                Height = 36,
                Text = "僅在 WP5/WP6 證據完成後設定。Mode=UNMAPPED 時正式 InventoryBridge 保持停用。"
            });

            panel.Controls.Add(new Label { Text = "Mode", Left = 16, Top = 58, Width = 80 });
            _mode = new ComboBox
            {
                Left = 110,
                Top = 54,
                Width = 170,
                DropDownStyle = ComboBoxStyle.DropDownList
            };
            _mode.Items.Add("UNMAPPED");
            _mode.Items.Add("POINTER_ARRAY");
            _mode.Items.Add("LINKED_LIST");
            _mode.Items.Add("CONTIGUOUS");
            panel.Controls.Add(_mode);

            panel.Controls.Add(new Label { Text = "Root", Left = 310, Top = 58, Width = 60 });
            _root = new TextBox { Left = 370, Top = 54, Width = 330 };
            panel.Controls.Add(_root);

            panel.Controls.Add(new Label { Text = "MaxItems", Left = 16, Top = 92, Width = 80 });
            _maxItems = new TextBox { Left = 110, Top = 88, Width = 100 };
            panel.Controls.Add(_maxItems);

            var y = 132;
            AddSectionTitle(panel, "Collection", y);
            y += 30;

            AddField(panel, _collection, "CountOffset", "CountOffset", 16, y);
            AddField(panel, _collection, "DataPointerOffset", "DataPointerOffset", 360, y);
            y += 34;

            AddField(panel, _collection, "EntryStride", "EntryStride", 16, y);
            AddField(panel, _collection, "EntryPointerOffset", "EntryPointerOffset", 360, y);
            y += 34;

            AddField(panel, _collection, "HeadPointerOffset", "HeadPointerOffset", 16, y);
            AddField(panel, _collection, "NextPointerOffset", "NextPointerOffset", 360, y);
            y += 34;

            AddField(panel, _collection, "RecordPointerOffset", "RecordPointerOffset", 16, y);
            AddField(panel, _collection, "RecordInline", "RecordInline 0/1", 360, y);
            y += 34;

            AddField(panel, _collection, "RecordStride", "RecordStride", 16, y);
            y += 46;

            AddSectionTitle(panel, "Record Fields", y);
            y += 30;

            AddField(panel, _record, "ObjectIdOffset", "ObjectIdOffset", 16, y);
            AddField(panel, _record, "ObjectIdWidth", "ObjectIdWidth", 360, y);
            y += 34;

            AddField(panel, _record, "ItemIdOffset", "ItemIdOffset", 16, y);
            AddField(panel, _record, "ItemIdWidth", "ItemIdWidth", 360, y);
            y += 34;

            AddField(panel, _record, "CountOffset", "CountOffset", 16, y);
            AddField(panel, _record, "CountWidth", "CountWidth", 360, y);
            y += 34;

            AddField(panel, _record, "EnchantOffset", "EnchantOffset", 16, y);
            AddField(panel, _record, "EnchantWidth", "EnchantWidth", 360, y);
            y += 34;

            AddField(panel, _record, "EquippedOffset", "EquippedOffset", 16, y);
            AddField(panel, _record, "EquippedWidth", "EquippedWidth", 360, y);
            y += 48;

            var validate = new Button { Text = "驗證格式", Left = 16, Top = y, Width = 100 };
            var save = new Button { Text = "儲存映射", Left = 124, Top = y, Width = 100 };
            var reload = new Button { Text = "重新載入", Left = 232, Top = y, Width = 100 };
            var disable = new Button { Text = "停用映射", Left = 340, Top = y, Width = 100 };

            panel.Controls.Add(validate);
            panel.Controls.Add(save);
            panel.Controls.Add(reload);
            panel.Controls.Add(disable);

            _status = new Label
            {
                Left = 16,
                Top = y + 42,
                Width = 690,
                Height = 64
            };
            panel.Controls.Add(_status);

            validate.Click += delegate { ValidateOnly(); };
            save.Click += delegate { SaveValues(); };
            reload.Click += delegate { LoadValues(); };
            disable.Click += delegate
            {
                _mode.SelectedItem = "UNMAPPED";
                SaveValues();
            };
        }

        private static void AddSectionTitle(Control parent, string text, int top)
        {
            parent.Controls.Add(new Label
            {
                Text = text,
                Left = 16,
                Top = top,
                Width = 220,
                Font = new System.Drawing.Font(
                    System.Drawing.SystemFonts.DefaultFont,
                    System.Drawing.FontStyle.Bold)
            });
        }

        private static void AddField(
            Control parent,
            Dictionary<string, TextBox> target,
            string key,
            string label,
            int left,
            int top)
        {
            parent.Controls.Add(new Label
            {
                Text = label,
                Left = left,
                Top = top + 4,
                Width = 130
            });

            var box = new TextBox
            {
                Left = left + 136,
                Top = top,
                Width = 160
            };

            parent.Controls.Add(box);
            target[key] = box;
        }

        private void LoadValues()
        {
            var ini = IniDocument.Load(_path);

            SelectMode(ini.Get("Collection", "Mode", "UNMAPPED"));
            _root.Text = ini.Get("Collection", "Root", "");
            _maxItems.Text = ini.Get("Collection", "MaxItems", "500");

            Set(_collection, "CountOffset", ini.Get("Collection", "CountOffset", "0"));
            Set(_collection, "DataPointerOffset", ini.Get("Collection", "DataPointerOffset", "0"));
            Set(_collection, "EntryStride", ini.Get("Collection", "EntryStride", "4"));
            Set(_collection, "EntryPointerOffset", ini.Get("Collection", "EntryPointerOffset", "0"));
            Set(_collection, "HeadPointerOffset", ini.Get("Collection", "HeadPointerOffset", "0"));
            Set(_collection, "NextPointerOffset", ini.Get("Collection", "NextPointerOffset", "0"));
            Set(_collection, "RecordPointerOffset", ini.Get("Collection", "RecordPointerOffset", "0"));
            Set(_collection, "RecordInline", ini.Get("Collection", "RecordInline", "0"));
            Set(_collection, "RecordStride", ini.Get("Collection", "RecordStride", "0"));

            Set(_record, "ObjectIdOffset", ini.Get("Record", "ObjectIdOffset", "0"));
            Set(_record, "ObjectIdWidth", ini.Get("Record", "ObjectIdWidth", "4"));
            Set(_record, "ItemIdOffset", ini.Get("Record", "ItemIdOffset", "0"));
            Set(_record, "ItemIdWidth", ini.Get("Record", "ItemIdWidth", "4"));
            Set(_record, "CountOffset", ini.Get("Record", "CountOffset", "0"));
            Set(_record, "CountWidth", ini.Get("Record", "CountWidth", "4"));
            Set(_record, "EnchantOffset", ini.Get("Record", "EnchantOffset", "0"));
            Set(_record, "EnchantWidth", ini.Get("Record", "EnchantWidth", "2"));
            Set(_record, "EquippedOffset", ini.Get("Record", "EquippedOffset", "0"));
            Set(_record, "EquippedWidth", ini.Get("Record", "EquippedWidth", "1"));

            _status.Text = File.Exists(_path)
                ? "已載入 inventory-map.ini。"
                : "inventory-map.ini 尚不存在；部署時會建立 UNMAPPED 範本。";
        }

        private void ValidateOnly()
        {
            var temp = Path.Combine(
                Path.GetTempPath(),
                "inventory-map-validate-" + Guid.NewGuid().ToString("N") + ".ini");

            try
            {
                WriteIni(temp);
                InventoryMap.Load(temp);
                _status.Text =
                    "格式 PASS。注意：格式正確不代表 WP5/WP6 PASS，仍需背包列舉與重登/重啟驗證。";
            }
            catch (Exception ex)
            {
                _status.Text = "格式 FAIL：" + ex.Message;
            }
            finally
            {
                try
                {
                    if (File.Exists(temp))
                        File.Delete(temp);
                }
                catch
                {
                }
            }
        }

        private void SaveValues()
        {
            try
            {
                WriteIni(_path);
                InventoryMap.Load(_path);
                _status.Text =
                    "inventory-map.ini 已儲存。Mode 非 UNMAPPED 時主程式會嘗試列舉背包。";
            }
            catch (Exception ex)
            {
                _status.Text = "儲存/驗證失敗：" + ex.Message;
            }
        }

        private void WriteIni(string path)
        {
            var ini = new IniDocument();

            ini.Set("Collection", "Mode", Convert.ToString(_mode.SelectedItem));
            ini.Set("Collection", "Root", _root.Text.Trim());
            ini.Set("Collection", "MaxItems", _maxItems.Text.Trim());

            foreach (var kv in _collection)
                ini.Set("Collection", kv.Key, kv.Value.Text.Trim());

            foreach (var kv in _record)
                ini.Set("Record", kv.Key, kv.Value.Text.Trim());

            ini.Save(path);
        }

        private void SelectMode(string mode)
        {
            for (var i = 0; i < _mode.Items.Count; i++)
            {
                if (string.Equals(
                    Convert.ToString(_mode.Items[i]),
                    mode,
                    StringComparison.OrdinalIgnoreCase))
                {
                    _mode.SelectedIndex = i;
                    return;
                }
            }

            _mode.SelectedIndex = 0;
        }

        private static void Set(
            Dictionary<string, TextBox> fields,
            string key,
            string value)
        {
            TextBox box;
            if (fields.TryGetValue(key, out box))
                box.Text = value;
        }
    }
}
