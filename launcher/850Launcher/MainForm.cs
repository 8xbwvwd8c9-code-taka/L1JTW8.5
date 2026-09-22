using System;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class MainForm : Form
    {
        private readonly string _appDir;
        private readonly LauncherConfig _config;
        private readonly HelperSettings _helper;
        private readonly IRuntimeBridge _runtime;
        private readonly IItemUseBridge _itemUse;
        private readonly AutoPotionController _autoPotionController;

        private TextBox _serverName, _ip, _port;
        private Label _runtimeState;
        private CheckBox _autoPotion, _potionUsePercent, _autoBuff, _autoTransform, _autoAntidote, _autoRepair, _autoFood, _showClock, _showDamage;
        private NumericUpDown _hpPercent, _hpExact, _potionCooldown, _timerSeconds;
        private TextBox _potionItemIds;
        private Label _potionStatus;
        private ListView _inventory;

        public MainForm(string appDir, LauncherConfig config, HelperSettings helper)
        {
            _appDir = appDir;
            _config = config;
            _helper = helper;
            _runtime = new ProcessRuntimeBridge(appDir);
            _itemUse = new UnmappedItemUseBridge();
            _autoPotionController = new AutoPotionController(_helper, _itemUse);

            Text = "L1JTW 8.50 登入器 + 輔助";
            Width = 760;
            Height = 560;
            MinimumSize = new Size(700, 500);
            StartPosition = FormStartPosition.CenterScreen;

            BuildUi();
            LoadValues();

            var poll = new Timer { Interval = 1000 };
            poll.Tick += delegate { RefreshRuntime(); };
            poll.Start();
        }

        private void BuildUi()
        {
            var top = new Panel { Dock = DockStyle.Top, Height = 92, Padding = new Padding(10) };
            Controls.Add(top);

            top.Controls.Add(new Label { Text = "伺服器", Left = 12, Top = 14, Width = 48 });
            _serverName = new TextBox { Left = 64, Top = 10, Width = 170 };
            top.Controls.Add(_serverName);
            top.Controls.Add(new Label { Text = "IP", Left = 246, Top = 14, Width = 24 });
            _ip = new TextBox { Left = 274, Top = 10, Width = 150 };
            top.Controls.Add(_ip);
            top.Controls.Add(new Label { Text = "連接埠", Left = 436, Top = 14, Width = 32 });
            _port = new TextBox { Left = 470, Top = 10, Width = 70 };
            top.Controls.Add(_port);

            var save = new Button { Text = "儲存", Left = 554, Top = 8, Width = 80 };
            save.Click += delegate { SaveAll(); };
            top.Controls.Add(save);

            var launch = new Button { Text = "啟動 850", Left = 642, Top = 8, Width = 92 };
            launch.Click += delegate { Launch850(); };
            top.Controls.Add(launch);

            _runtimeState = new Label { Left = 12, Top = 50, Width = 710, Text = "執行狀態：尚未連接" };
            top.Controls.Add(_runtimeState);

            var tabs = new TabControl { Dock = DockStyle.Fill };
            Controls.Add(tabs);
            tabs.BringToFront();

            tabs.TabPages.Add(BuildExtendTab());
            tabs.TabPages.Add(BuildPotionTab());
            tabs.TabPages.Add(BuildStateTab());
            tabs.TabPages.Add(BuildSpecialTab());
            tabs.TabPages.Add(BuildItemTab());
            tabs.TabPages.Add(BuildHotkeyTab());
            tabs.TabPages.Add(BuildTimerTab());

            if (_config.DeveloperMode)
            {
                tabs.TabPages.Add(BuildProbeTab());
                tabs.TabPages.Add(BuildInventoryProbeTab());
                tabs.TabPages.Add(BuildInventoryRecordProbeTab());
                tabs.TabPages.Add(BuildInventoryFieldValidatorTab());
                tabs.TabPages.Add(BuildInventoryCollectionProbeTab());
                tabs.TabPages.Add(BuildInventoryMapTab());
                tabs.TabPages.Add(BuildPointerProbeTab());
                tabs.TabPages.Add(BuildRuntimeMapTab());
                tabs.TabPages.Add(BuildItemUseProtocolTab());
            }
        }

        private TabPage BuildExtendTab()
        {
            var p = NewPage("擴充");
            _autoFood = AddCheck(p, "自動吃食物", 24, 24);
            _autoRepair = AddCheck(p, "自動修理", 24, 56);
            _showClock = AddCheck(p, "顯示遊戲時間", 24, 88);
            _showDamage = AddCheck(p, "顯示累積傷害", 24, 120);
            AddCheck(p, "全日白天（待接核心）", 260, 24).Enabled = false;
            AddCheck(p, "水下輔助（待接核心）", 260, 56).Enabled = false;
            AddCheck(p, "降低 CPU 使用（待接核心）", 260, 88).Enabled = false;
            return p;
        }

        private TabPage BuildPotionTab()
        {
            var p = NewPage("藥水");
            _autoPotion = AddCheck(p, "啟用自動喝水", 24, 24);

            _potionUsePercent = AddCheck(p, "使用生命值百分比判斷", 24, 58);

            p.Controls.Add(new Label { Text = "百分比門檻", Left = 24, Top = 98, Width = 90 });
            _hpPercent = new NumericUpDown
            {
                Left = 120,
                Top = 94,
                Minimum = 1,
                Maximum = 100,
                Width = 70
            };
            p.Controls.Add(_hpPercent);
            p.Controls.Add(new Label { Text = "%", Left = 194, Top = 98, Width = 20 });

            p.Controls.Add(new Label { Text = "精準 HP 門檻", Left = 260, Top = 98, Width = 90 });
            _hpExact = new NumericUpDown
            {
                Left = 356,
                Top = 94,
                Minimum = 1,
                Maximum = 2000000000,
                Width = 110
            };
            p.Controls.Add(_hpExact);

            p.Controls.Add(new Label
            {
                Text = "喝水道具 ID（優先順序）",
                Left = 24,
                Top = 140,
                Width = 150
            });
            _potionItemIds = new TextBox
            {
                Left = 180,
                Top = 136,
                Width = 330
            };
            p.Controls.Add(_potionItemIds);

            p.Controls.Add(new Label
            {
                Text = "冷卻(ms)",
                Left = 528,
                Top = 140,
                Width = 65
            });
            _potionCooldown = new NumericUpDown
            {
                Left = 596,
                Top = 136,
                Minimum = 50,
                Maximum = 10000,
                Increment = 50,
                Width = 100
            };
            p.Controls.Add(_potionCooldown);

            p.Controls.Add(new Label
            {
                Left = 24,
                Top = 176,
                Width = 675,
                Height = 44,
                Text = "可在「物品」頁選取背包道具後加入喝水清單。HP/MP 只供內部判斷，不在一般輔助頁面顯示。"
            });

            _potionStatus = new Label
            {
                Left = 24,
                Top = 224,
                Width = 675,
                Height = 64,
                Text = "喝水狀態：等待 HP / 背包 / UseItem 映射。"
            };
            p.Controls.Add(_potionStatus);

            _potionUsePercent.CheckedChanged += delegate
            {
                _hpPercent.Enabled = _potionUsePercent.Checked;
                _hpExact.Enabled = !_potionUsePercent.Checked;
            };

            return p;
        }

        private TabPage BuildStateTab()
        {
            var p = NewPage("狀態");
            _autoBuff = AddCheck(p, "自動維持選定狀態", 24, 24);
            p.Controls.Add(new Label { Text = "狀態清單：尚未接入（WP9）", Left = 24, Top = 70, Width = 400 });
            return p;
        }

        private TabPage BuildSpecialTab()
        {
            var p = NewPage("特殊");
            _autoTransform = AddCheck(p, "自動變身", 24, 24);
            _autoAntidote = AddCheck(p, "自動解毒", 24, 56);
            AddCheck(p, "自動娃娃（待接核心）", 24, 88).Enabled = false;
            AddCheck(p, "自動精煉魔法石（待接核心）", 24, 120).Enabled = false;
            return p;
        }

        private TabPage BuildProbeTab()
        {
            var p = NewPage("偵測");
            p.Controls.Add(new RuntimeProbeControl(_appDir));
            return p;
        }

        private TabPage BuildInventoryProbeTab()
        {
            var p = NewPage("物品偵測");
            p.Controls.Add(new InventoryProbeControl(_appDir));
            return p;
        }

        private TabPage BuildInventoryRecordProbeTab()
        {
            var p = NewPage("物品結構");
            p.Controls.Add(new InventoryRecordProbeControl(_appDir));
            return p;
        }

        private TabPage BuildInventoryFieldValidatorTab()
        {
            var p = NewPage("物品欄位");
            p.Controls.Add(new InventoryFieldValidatorControl(_appDir));
            return p;
        }

        private TabPage BuildInventoryCollectionProbeTab()
        {
            var p = NewPage("背包容器");
            p.Controls.Add(new InventoryCollectionProbeControl(_appDir));
            return p;
        }

        private TabPage BuildInventoryMapTab()
        {
            var p = NewPage("背包映射");
            p.Controls.Add(new InventoryMapControl(_appDir));
            return p;
        }

        private TabPage BuildPointerProbeTab()
        {
            var p = NewPage("指標鏈");
            p.Controls.Add(new PointerProbeControl(_appDir));
            return p;
        }

        private TabPage BuildRuntimeMapTab()
        {
            var p = NewPage("映射");
            p.Controls.Add(new RuntimeMapControl(_appDir));
            return p;
        }

        private TabPage BuildItemUseProtocolTab()
        {
            var p = NewPage("UseItem協定");
            p.Controls.Add(new ItemUseProtocolControl(_appDir));
            return p;
        }

        private TabPage BuildItemTab()
        {
            var p = NewPage("物品");

            var actions = new Panel
            {
                Dock = DockStyle.Bottom,
                Height = 44
            };

            var addPotion = new Button
            {
                Text = "加入喝水清單",
                Left = 8,
                Top = 8,
                Width = 120
            };
            addPotion.Click += delegate { AddSelectedInventoryItemToPotionList(); };
            actions.Controls.Add(addPotion);

            actions.Controls.Add(new Label
            {
                Text = "刪除 / 溶解動作尚未接入，先完成 WP5-WP7。",
                Left = 144,
                Top = 13,
                Width = 420
            });

            p.Controls.Add(actions);

            _inventory = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                MultiSelect = false,
                GridLines = true
            };
            _inventory.Columns.Add("物件ID", 100);
            _inventory.Columns.Add("道具ID", 80);
            _inventory.Columns.Add("名稱", 220);
            _inventory.Columns.Add("數量", 90);
            _inventory.Columns.Add("強化", 70);
            _inventory.Columns.Add("裝備中", 80);
            p.Controls.Add(_inventory);
            _inventory.BringToFront();

            return p;
        }

        private TabPage BuildHotkeyTab()
        {
            var p = NewPage("熱鍵");
            p.Controls.Add(new Label { Text = "F1-F4 指令群組介面已保留，待 runtime bridge 驗證後啟用。", Left = 24, Top = 24, Width = 620 });
            return p;
        }

        private TabPage BuildTimerTab()
        {
            var p = NewPage("定時");
            p.Controls.Add(new Label { Text = "間隔（秒）", Left = 24, Top = 30, Width = 110 });
            _timerSeconds = new NumericUpDown { Left = 140, Top = 26, Minimum = 1, Maximum = 86400, Width = 90 };
            p.Controls.Add(_timerSeconds);
            p.Controls.Add(new Label { Text = "ItemUse / Skill bridge 尚未驗證，定時動作暫不啟用。", Left = 24, Top = 72, Width = 500 });
            return p;
        }

        private static TabPage NewPage(string name) { return new TabPage(name) { Padding = new Padding(8) }; }

        private static CheckBox AddCheck(Control parent, string text, int x, int y)
        {
            var c = new CheckBox { Text = text, Left = x, Top = y, Width = 220 };
            parent.Controls.Add(c);
            return c;
        }

        private void LoadValues()
        {
            _serverName.Text = _config.ServerName;
            _ip.Text = _config.ServerIp;
            _port.Text = _config.ServerPort.ToString();
            _autoPotion.Checked = _helper.AutoPotion;
            _potionUsePercent.Checked = _helper.PotionUsePercent;
            _hpPercent.Value = Math.Max(_hpPercent.Minimum, Math.Min(_hpPercent.Maximum, _helper.PotionHpPercent));
            _hpExact.Value = Math.Max(_hpExact.Minimum, Math.Min(_hpExact.Maximum, _helper.PotionHpExact));
            _potionItemIds.Text = _helper.PotionItemIds ?? "";
            _potionCooldown.Value = Math.Max(
                _potionCooldown.Minimum,
                Math.Min(_potionCooldown.Maximum, _helper.PotionCooldownMs));
            _hpPercent.Enabled = _potionUsePercent.Checked;
            _hpExact.Enabled = !_potionUsePercent.Checked;
            _autoBuff.Checked = _helper.AutoBuff;
            _autoTransform.Checked = _helper.AutoTransform;
            _autoAntidote.Checked = _helper.AutoAntidote;
            _autoRepair.Checked = _helper.AutoRepair;
            _autoFood.Checked = _helper.AutoFood;
            _showClock.Checked = _helper.ShowClock;
            _showDamage.Checked = _helper.ShowDamage;
            _timerSeconds.Value = Math.Max(_timerSeconds.Minimum, Math.Min(_timerSeconds.Maximum, _helper.TimerSeconds));
        }

        private bool SaveAll()
        {
            int port;
            if (!int.TryParse(_port.Text.Trim(), out port) || port < 1 || port > 65535)
            {
                MessageBox.Show("連接埠格式錯誤。");
                return false;
            }

            _config.ServerName = _serverName.Text.Trim();
            _config.ServerIp = _ip.Text.Trim();
            _config.ServerPort = port;

            _helper.AutoPotion = _autoPotion.Checked;
            _helper.PotionUsePercent = _potionUsePercent.Checked;
            _helper.PotionHpPercent = (int)_hpPercent.Value;
            _helper.PotionHpExact = (int)_hpExact.Value;
            _helper.PotionItemIds = _potionItemIds.Text.Trim();
            _helper.PotionCooldownMs = (int)_potionCooldown.Value;
            _helper.AutoBuff = _autoBuff.Checked;
            _helper.AutoTransform = _autoTransform.Checked;
            _helper.AutoAntidote = _autoAntidote.Checked;
            _helper.AutoRepair = _autoRepair.Checked;
            _helper.AutoFood = _autoFood.Checked;
            _helper.ShowClock = _showClock.Checked;
            _helper.ShowDamage = _showDamage.Checked;
            _helper.TimerSeconds = (int)_timerSeconds.Value;

            _config.Save(Path.Combine(_appDir, "launcher.ini"));
            _helper.Save(Path.Combine(_appDir, "helper.ini"));
            _runtimeState.Text = "設定已儲存。";
            return true;
        }

        private void Launch850()
        {
            try
            {
                if (!SaveAll()) return;
                var process = new ClientLauncher(_appDir).Launch(_config);
                _runtimeState.Text = process == null ? "啟動失敗：未取得程序。" : "登入器已啟動，PID=" + process.Id;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "啟動失敗", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void RefreshRuntime()
        {
            var s = _runtime.Read();
            _runtimeState.Text = "執行狀態：" + s.Status;

            _inventory.BeginUpdate();
            _inventory.Items.Clear();
            foreach (var item in s.Items)
            {
                var row = new ListViewItem(new[]
                {
                    item.ObjectId.ToString(), item.ItemId.ToString(), item.Name, item.Count.ToString(),
                    item.Enchant.HasValue ? item.Enchant.Value.ToString() : "",
                    item.Equipped.HasValue ? item.Equipped.Value.ToString() : ""
                });
                row.Tag = item;
                _inventory.Items.Add(row);
            }
            _inventory.EndUpdate();

            var potion = _autoPotionController.Tick(s);
            _potionStatus.Text = "喝水狀態：" + potion.Status;
        }

        private void AddSelectedInventoryItemToPotionList()
        {
            if (_inventory.SelectedItems.Count == 0)
            {
                MessageBox.Show("請先從背包清單選一個道具。");
                return;
            }

            var item = _inventory.SelectedItems[0].Tag as InventoryItem;
            if (item == null || item.ItemId <= 0)
            {
                MessageBox.Show("目前選取項目沒有可用的 ItemId。");
                return;
            }

            var ids = _helper.GetPotionItemIds();
            if (!ids.Contains(item.ItemId))
                ids.Add(item.ItemId);

            _helper.PotionItemIds = string.Join(",", ids.ToArray());
            _potionItemIds.Text = _helper.PotionItemIds;
            _potionStatus.Text =
                "喝水狀態：已加入 ItemId=" + item.ItemId +
                "；請按「儲存」保存設定。";
        }

    }
}
