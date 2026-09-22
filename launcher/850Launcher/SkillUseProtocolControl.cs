using System;
using System.Globalization;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class SkillUseProtocolControl : UserControl
    {
        private readonly string _appDir;
        private readonly SkillCatalog _catalog;

        private TextBox _skillId;
        private TextBox _targetId;
        private TextBox _x;
        private TextBox _y;
        private ComboBox _mode;
        private TextBox _payload;
        private Label _status;

        public SkillUseProtocolControl(string appDir)
        {
            _appDir = appDir;
            _catalog = new SkillCatalog(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            Controls.Add(new Label
            {
                Left = 20,
                Top = 18,
                Width = 690,
                Height = 42,
                Text =
                    "850 recovered server contract: C_UseSkill opcode=128 (0x80), " +
                    "skillId = row*8 + column + 1。此頁只產生解密後 logical payload，不送包。"
            });

            AddLabel("SkillId", 20, 76);
            _skillId = AddBox(90, 72, 90);

            AddLabel("模式", 200, 76);
            _mode = new ComboBox
            {
                Left = 250,
                Top = 72,
                Width = 160,
                DropDownStyle = ComboBoxStyle.DropDownList
            };
            _mode.Items.Add("一般 targetId+X+Y");
            _mode.Items.Add("XY-only (58/63)");
            _mode.Items.Add("書籤 map+X+Y (5/69)");
            _mode.SelectedIndex = 0;
            Controls.Add(_mode);

            AddLabel("TargetId/Map", 20, 116);
            _targetId = AddBox(110, 112, 110);

            AddLabel("X", 240, 116);
            _x = AddBox(264, 112, 80);

            AddLabel("Y", 364, 116);
            _y = AddBox(388, 112, 80);

            var build = new Button
            {
                Text = "產生邏輯封包",
                Left = 490,
                Top = 110,
                Width = 120
            };
            build.Click += delegate { BuildPayload(); };
            Controls.Add(build);

            AddLabel("Payload", 20, 164);
            _payload = new TextBox
            {
                Left = 90,
                Top = 160,
                Width = 500,
                ReadOnly = true
            };
            Controls.Add(_payload);

            var copy = new Button
            {
                Text = "複製",
                Left = 600,
                Top = 158,
                Width = 70
            };
            copy.Click += delegate
            {
                if (!string.IsNullOrEmpty(_payload.Text))
                    Clipboard.SetText(_payload.Text);
            };
            Controls.Add(copy);

            _status = new Label
            {
                Left = 20,
                Top = 208,
                Width = 680,
                Height = 130,
                Text =
                    "WP9 gate：server protocol proof != client native SkillUse PASS。" +
                    "Lin.bin2 session framing/encryption 與 Buff 狀態來源仍需 runtime 證據。"
            };
            Controls.Add(_status);
        }

        private void BuildPayload()
        {
            int skillId;
            if (!int.TryParse(
                _skillId.Text.Trim(),
                out skillId) ||
                skillId <= 0)
            {
                _status.Text = "SkillId 格式錯誤。";
                return;
            }

            try
            {
                byte[] payload;

                if (_mode.SelectedIndex == 1)
                {
                    var x = ParseU16(_x.Text, "X");
                    var y = ParseU16(_y.Text, "Y");

                    payload =
                        SkillUseProtocol.BuildXyOnlyLogicalPayload(
                            skillId,
                            x,
                            y);
                }
                else if (_mode.SelectedIndex == 2)
                {
                    var mapId =
                        ParseU16(
                            _targetId.Text,
                            "MapId");
                    var x = ParseU16(_x.Text, "X");
                    var y = ParseU16(_y.Text, "Y");

                    payload =
                        SkillUseProtocol.BuildBookmarkLogicalPayload(
                            skillId,
                            mapId,
                            x,
                            y);
                }
                else
                {
                    var targetId =
                        ParseU32(
                            _targetId.Text,
                            "TargetId");
                    var x = ParseU16(_x.Text, "X");
                    var y = ParseU16(_y.Text, "Y");

                    payload =
                        SkillUseProtocol.BuildGeneralLogicalPayload(
                            skillId,
                            targetId,
                            x,
                            y);
                }

                byte row;
                byte column;

                SkillUseProtocol.GetRowColumn(
                    skillId,
                    out row,
                    out column);

                _payload.Text =
                    ItemUseProtocol.ToHex(payload);

                var name =
                    _catalog.Resolve(skillId);

                _status.Text =
                    "Skill=" + name +
                    "，row=" + row +
                    "，column=" + column +
                    "。logical payload 已產生；SEND=NO。";

                SaveEvidence(
                    skillId,
                    row,
                    column,
                    payload);
            }
            catch (Exception ex)
            {
                _status.Text =
                    "產生失敗：" + ex.Message;
            }
        }

        private void SaveEvidence(
            int skillId,
            byte row,
            byte column,
            byte[] payload)
        {
            try
            {
                var path = Path.Combine(
                    _appDir,
                    "skilluse_protocol_evidence.txt");

                var sb = new StringBuilder();

                sb.AppendLine(
                    "TIME=" +
                    DateTime.Now.ToString(
                        "yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine(
                    "SKILL_ID=" +
                    skillId);
                sb.AppendLine(
                    "SKILL_NAME=" +
                    _catalog.Resolve(skillId));
                sb.AppendLine(
                    "ROW=" + row);
                sb.AppendLine(
                    "COLUMN=" + column);
                sb.AppendLine(
                    "OPCODE_DEC=128");
                sb.AppendLine(
                    "OPCODE_HEX=0x80");
                sb.AppendLine(
                    "LOGICAL_PAYLOAD=" +
                    ItemUseProtocol.ToHex(payload));
                sb.AppendLine(
                    "SEND=NO");
                sb.AppendLine();

                File.AppendAllText(
                    path,
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        private void AddLabel(
            string text,
            int left,
            int top)
        {
            Controls.Add(new Label
            {
                Text = text,
                Left = left,
                Top = top,
                Width = 90
            });
        }

        private TextBox AddBox(
            int left,
            int top,
            int width)
        {
            var box = new TextBox
            {
                Left = left,
                Top = top,
                Width = width
            };
            Controls.Add(box);
            return box;
        }

        private static ushort ParseU16(
            string text,
            string name)
        {
            uint value =
                ParseUnsigned(text, name);

            if (value > ushort.MaxValue)
                throw new InvalidDataException(
                    name + " 超出 0..65535。");

            return (ushort)value;
        }

        private static uint ParseU32(
            string text,
            string name)
        {
            return ParseUnsigned(
                text,
                name);
        }

        private static uint ParseUnsigned(
            string text,
            string name)
        {
            text = (text ?? "").Trim();

            uint value;

            if (text.StartsWith(
                "0x",
                StringComparison.OrdinalIgnoreCase))
            {
                if (!uint.TryParse(
                    text.Substring(2),
                    NumberStyles.HexNumber,
                    CultureInfo.InvariantCulture,
                    out value))
                {
                    throw new InvalidDataException(
                        name + " 格式錯誤。");
                }

                return value;
            }

            if (!uint.TryParse(
                text,
                out value))
            {
                throw new InvalidDataException(
                    name + " 格式錯誤。");
            }

            return value;
        }
    }
}
