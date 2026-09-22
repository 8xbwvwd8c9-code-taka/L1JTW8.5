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

            Text = "L1JTW 8.50 Launcher + Helper";
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

            top.Controls.Add(new Label { Text = "Server", Left = 12, Top = 14, Width = 48 });
            _serverName = new TextBox { Left = 64, Top = 10, Width = 170 };
            top.Controls.Add(_serverName);
            top.Controls.Add(new Label { Text = "IP", Left = 246, Top = 14, Width = 24 });
            _ip = new TextBox { Left = 274, Top = 10, Width = 150 };
            top.Controls.Add(_ip);
            top.Controls.Add(new Label { Text = "Port", Left = 436, Top = 14, Width = 32 });
            _port = new TextBox { Left = 470, Top = 10, Width = 70 };
            top.Controls.Add(_port);

            var save = new Button { Text = "Save", Left = 554, Top = 8, Width = 80 };
            save.Click += delegate { SaveAll(); };
            top.Controls.Add(save);

            var launch = new Button { Text = "Launch 850", Left = 642, Top = 8, Width = 92 };
            launch.Click += delegate { Launch850(); };
            top.Controls.Add(launch);

            _runtimeState = new Label { Left = 12, Top = 50, Width = 520, Text = "Runtime: UNMAPPED" };
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
            var p = NewPage("Extend");
            _autoFood = AddCheck(p, "Auto food", 24, 24);
            _autoRepair = AddCheck(p, "Auto repair", 24, 56);
            _showClock = AddCheck(p, "Show game clock", 24, 88);
            _showDamage = AddCheck(p, "Show accumulated damage", 24, 120);
            AddCheck(p, "Daylight mode (UI reserved)", 260, 24).Enabled = false;
            AddCheck(p, "Underwater assist (UI reserved)", 260, 56).Enabled = false;
            AddCheck(p, "Reduce CPU (UI reserved)", 260, 88).Enabled = false;
            return p;
        }

        private TabPage BuildPotionTab()
        {
            var p = NewPage("Potion");
            _autoPotion = AddCheck(p, "Enable auto potion", 24, 24);
            p.Controls.Add(new Label { Text = "Use when HP <=", Left = 24, Top = 66, Width = 110 });
            _hpPercent = new NumericUpDown { Left = 140, Top = 62, Minimum = 1, Maximum = 100, Width = 70 };
            p.Controls.Add(_hpPercent);
            p.Controls.Add(new Label { Text = "%", Left = 214, Top = 66, Width = 20 });
            p.Controls.Add(new Label
            {
                Left = 24, Top = 108, Width = 620, Height = 70,
                Text = "Potion item binding is intentionally disabled until WP5/WP6 proves 850 inventory objectId/item identity."
            });
            return p;
        }

        private TabPage BuildStateTab()
        {
            var p = NewPage("State");
            _autoBuff = AddCheck(p, "Auto maintain selected buffs", 24, 24);
            p.Controls.Add(new Label { Text = "Buff list: UNMAPPED (WP9)", Left = 24, Top = 70, Width = 400 });
            return p;
        }

        private TabPage BuildSpecialTab()
        {
            var p = NewPage("Special");
            _autoTransform = AddCheck(p, "Auto transform", 24, 24);
            _autoAntidote = AddCheck(p, "Auto antidote", 24, 56);
            AddCheck(p, "Auto doll (UI reserved)", 24, 88).Enabled = false;
            AddCheck(p, "Magic stone refine (UI reserved)", 24, 120).Enabled = false;
            return p;
        }

        private TabPage BuildItemTab()
        {
            var p = NewPage("Items");
            _inventory = new ListView { Dock = DockStyle.Fill, View = View.Details, FullRowSelect = true };
            _inventory.Columns.Add("ObjectId", 100);
            _inventory.Columns.Add("ItemId", 80);
            _inventory.Columns.Add("Name", 220);
            _inventory.Columns.Add("Count", 90);
            _inventory.Columns.Add("Enchant", 70);
            _inventory.Columns.Add("Equipped", 80);
            p.Controls.Add(_inventory);
            return p;
        }

        private TabPage BuildHotkeyTab()
        {
            var p = NewPage("Hotkeys");
            p.Controls.Add(new Label { Text = "F1-F4 command groups reserved; runtime dispatch stays disabled until bridge validation.", Left = 24, Top = 24, Width = 620 });
            return p;
        }

        private TabPage BuildTimerTab()
        {
            var p = NewPage("Timer");
            p.Controls.Add(new Label { Text = "Interval (seconds)", Left = 24, Top = 30, Width = 110 });
            _timerSeconds = new NumericUpDown { Left = 140, Top = 26, Minimum = 1, Maximum = 86400, Width = 90 };
            p.Controls.Add(_timerSeconds);
            p.Controls.Add(new Label { Text = "Action binding disabled until ItemUse/Skill bridge is proven.", Left = 24, Top = 72, Width = 500 });
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

        private void SaveAll()
        {
            int port;
            if (!int.TryParse(_port.Text.Trim(), out port) || port < 1 || port > 65535)
            {
                MessageBox.Show("Invalid port.");
                return;
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
            _runtimeState.Text = "Settings saved.";
        }

        private void Launch850()
        {
            try
            {
                SaveAll();
                var process = new ClientLauncher(_appDir).Launch(_config);
                _runtimeState.Text = process == null ? "Launch returned no process." : "Launcher PID " + process.Id + " started.";
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Launch failed", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void RefreshRuntime()
        {
            var s = _runtime.Read();
            _runtimeState.Text = "Runtime: " + s.Status;
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
