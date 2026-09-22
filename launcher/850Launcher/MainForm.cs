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
        private readonly IRuntimeBridge _runtime = new UnmappedRuntimeBridge();

        private TextBox _serverName, _ip, _port;
        private Label _runtimeState, _hpmp;
        private CheckBox _autoPotion, _autoBuff, _autoTransform, _autoAntidote, _autoRepair, _autoFood, _showClock, _showDamage;
        private NumericUpDown _hpPercent, _timerSeconds;
        private ListView _inventory;

        public MainForm(string appDir, LauncherConfig config, HelperSettings helper)
        {
            _appDir = appDir;
            _config = config;
            _helper = helper;

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

            _runtimeState = new Label { Left = 12, Top = 50, Width = 520, Text = "執行狀態：尚未連接" };
            _hpmp = new Label { Left = 540, Top = 50, Width = 190, TextAlign = ContentAlignment.MiddleRight, Text = "HP --/--  MP --/--" };
            top.Controls.Add(_runtimeState);
            top.Controls.Add(_hpmp);

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
            p.Controls.Add(new Label { Text = "HP 低於等於", Left = 24, Top = 66, Width = 110 });
            _hpPercent = new NumericUpDown { Left = 140, Top = 62, Minimum = 1, Maximum = 100, Width = 70 };
            p.Controls.Add(_hpPercent);
            p.Controls.Add(new Label { Text = "%", Left = 214, Top = 66, Width = 20 });
            p.Controls.Add(new Label
            {
                Left = 24, Top = 108, Width = 620, Height = 70,
                Text = "藥水道具綁定尚未啟用，需先完成 850 背包 objectId / item 身分驗證。"
            });
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

        private TabPage BuildItemTab()
        {
            var p = NewPage("物品");
            _inventory = new ListView { Dock = DockStyle.Fill, View = View.Details, FullRowSelect = true };
            _inventory.Columns.Add("物件ID", 100);
            _inventory.Columns.Add("道具ID", 80);
            _inventory.Columns.Add("名稱", 220);
            _inventory.Columns.Add("數量", 90);
            _inventory.Columns.Add("強化", 70);
            _inventory.Columns.Add("裝備中", 80);
            p.Controls.Add(_inventory);
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
            _hpPercent.Value = Math.Max(_hpPercent.Minimum, Math.Min(_hpPercent.Maximum, _helper.PotionHpPercent));
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
            _helper.PotionHpPercent = (int)_hpPercent.Value;
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
            _hpmp.Text = "HP " + F(s.CurrentHp) + "/" + F(s.MaxHp) + "  MP " + F(s.CurrentMp) + "/" + F(s.MaxMp);

            _inventory.BeginUpdate();
            _inventory.Items.Clear();
            foreach (var item in s.Items)
            {
                _inventory.Items.Add(new ListViewItem(new[]
                {
                    item.ObjectId.ToString(), item.ItemId.ToString(), item.Name, item.Count.ToString(),
                    item.Enchant.HasValue ? item.Enchant.Value.ToString() : "",
                    item.Equipped.HasValue ? item.Equipped.Value.ToString() : ""
                }));
            }
            _inventory.EndUpdate();
        }

        private static string F(int? value) { return value.HasValue ? value.Value.ToString() : "--"; }
    }
}
