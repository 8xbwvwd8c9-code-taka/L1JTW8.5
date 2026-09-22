using System;
using System.IO;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeMapControl : UserControl
    {
        private readonly string _path;

        private TextBox _currentHp;
        private TextBox _maxHp;
        private TextBox _currentMp;
        private TextBox _maxMp;
        private Label _status;

        public RuntimeMapControl(string appDir)
        {
            _path = Path.Combine(appDir, "runtime-map.ini");
            Dock = DockStyle.Fill;
            BuildUi();
            LoadValues();
        }

        private void BuildUi()
        {
            Controls.Add(new Label
            {
                Left = 20,
                Top = 18,
                Width = 680,
                Height = 40,
                Text = "只在候選經過數值變化、重登與完整重啟驗證後才儲存。支援 RVA:0x... 或 PTR:baseRVA|offset..."
            });

            AddField("目前 HP", 72, out _currentHp);
            AddField("最大 HP", 112, out _maxHp);
            AddField("目前 MP", 152, out _currentMp);
            AddField("最大 MP", 192, out _maxMp);

            var validate = new Button { Text = "驗證格式", Left = 122, Top = 240, Width = 110 };
            var save = new Button { Text = "儲存映射", Left = 242, Top = 240, Width = 110 };
            var reload = new Button { Text = "重新載入", Left = 362, Top = 240, Width = 110 };
            var clear = new Button { Text = "全部清空", Left = 482, Top = 240, Width = 110 };

            Controls.Add(validate);
            Controls.Add(save);
            Controls.Add(reload);
            Controls.Add(clear);

            _status = new Label { Left = 122, Top = 286, Width = 520, Height = 48 };
            Controls.Add(_status);

            validate.Click += delegate { ValidateOnly(); };
            save.Click += delegate { SaveValues(); };
            reload.Click += delegate { LoadValues(); };
            clear.Click += delegate
            {
                _currentHp.Clear();
                _maxHp.Clear();
                _currentMp.Clear();
                _maxMp.Clear();
                _status.Text = "欄位已清空，尚未寫入檔案。";
            };
        }

        private void AddField(string label, int top, out TextBox box)
        {
            Controls.Add(new Label { Text = label, Left = 20, Top = top + 4, Width = 90 });
            box = new TextBox { Left = 122, Top = top, Width = 470 };
            Controls.Add(box);
        }

        private void LoadValues()
        {
            try
            {
                var ini = IniDocument.Load(_path);
                _currentHp.Text = ini.Get("HPMP", "CurrentHP", "");
                _maxHp.Text = ini.Get("HPMP", "MaxHP", "");
                _currentMp.Text = ini.Get("HPMP", "CurrentMP", "");
                _maxMp.Text = ini.Get("HPMP", "MaxMP", "");
                _status.Text = File.Exists(_path)
                    ? "已載入 runtime-map.ini。"
                    : "runtime-map.ini 尚不存在；部署時會建立空白範本。";
            }
            catch (Exception ex)
            {
                _status.Text = "載入失敗：" + ex.Message;
            }
        }

        private bool ValidateFields(out string error)
        {
            error = "";

            try
            {
                var fields = new[]
                {
                    new[] { "CurrentHP", _currentHp.Text },
                    new[] { "MaxHP", _maxHp.Text },
                    new[] { "CurrentMP", _currentMp.Text },
                    new[] { "MaxMP", _maxMp.Text }
                };

                foreach (var field in fields)
                {
                    if (string.IsNullOrWhiteSpace(field[1]))
                        continue;

                    RuntimeFieldMap.Parse(field[0], field[1]);
                }

                return true;
            }
            catch (Exception ex)
            {
                error = ex.Message;
                return false;
            }
        }

        private void ValidateOnly()
        {
            string error;
            if (!ValidateFields(out error))
            {
                _status.Text = "格式錯誤：" + error;
                return;
            }

            _status.Text =
                "格式 PASS。注意：格式正確不代表 WP3/WP4 已通過，仍需重登/重啟實測。";
        }

        private void SaveValues()
        {
            string error;
            if (!ValidateFields(out error))
            {
                _status.Text = "無法儲存：" + error;
                return;
            }

            try
            {
                var ini = new IniDocument();
                ini.Set("HPMP", "CurrentHP", _currentHp.Text.Trim());
                ini.Set("HPMP", "MaxHP", _maxHp.Text.Trim());
                ini.Set("HPMP", "CurrentMP", _currentMp.Text.Trim());
                ini.Set("HPMP", "MaxMP", _maxMp.Text.Trim());
                ini.Save(_path);

                _status.Text =
                    "runtime-map.ini 已儲存。主畫面每秒會重新載入映射值。";
            }
            catch (Exception ex)
            {
                _status.Text = "儲存失敗：" + ex.Message;
            }
        }
    }
}
