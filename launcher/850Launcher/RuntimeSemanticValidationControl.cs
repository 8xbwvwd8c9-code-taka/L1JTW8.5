using System;
using System.Globalization;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeSemanticValidationControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;

        private CheckBox _checkHpMp;
        private TextBox _currentHp;
        private TextBox _maxHp;
        private TextBox _currentMp;
        private TextBox _maxMp;

        private CheckBox _checkPlayer;
        private TextBox _objectId;
        private TextBox _x;
        private TextBox _y;

        private Button _validate;
        private Label _status;
        private ListView _result;

        public RuntimeSemanticValidationControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel
            {
                Dock = DockStyle.Top,
                Height = 178,
                Padding = new Padding(8)
            };
            Controls.Add(top);

            _checkHpMp = new CheckBox
            {
                Text = "驗證 HP/MP",
                Left = 12,
                Top = 12,
                Width = 110,
                Checked = true
            };
            top.Controls.Add(_checkHpMp);

            AddLabel(top, "目前 HP", 28, 48);
            _currentHp = AddBox(top, 92, 44, 88);

            AddLabel(top, "最大 HP", 194, 48);
            _maxHp = AddBox(top, 258, 44, 88);

            AddLabel(top, "目前 MP", 360, 48);
            _currentMp = AddBox(top, 424, 44, 88);

            AddLabel(top, "最大 MP", 526, 48);
            _maxMp = AddBox(top, 590, 44, 88);

            _checkPlayer = new CheckBox
            {
                Text = "驗證 Player",
                Left = 12,
                Top = 82,
                Width = 110,
                Checked = true
            };
            top.Controls.Add(_checkPlayer);

            AddLabel(top, "ObjectId", 28, 118);
            _objectId = AddBox(top, 92, 114, 110);

            AddLabel(top, "X", 220, 118);
            _x = AddBox(top, 244, 114, 80);

            AddLabel(top, "Y", 344, 118);
            _y = AddBox(top, 368, 114, 80);

            _validate = new Button
            {
                Text = "執行語意驗證",
                Left = 482,
                Top = 110,
                Width = 120
            };
            top.Controls.Add(_validate);

            _status = new Label
            {
                Left = 12,
                Top = 148,
                Width = 700,
                Height = 24,
                Text = "輸入遊戲當下可確認的真值；本頁只讀，不修改遊戲記憶體。"
            };
            top.Controls.Add(_status);

            _result = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _result.Columns.Add("Gate", 110);
            _result.Columns.Add("Expected", 220);
            _result.Columns.Add("Actual", 220);
            _result.Columns.Add("Result", 130);

            Controls.Add(_result);
            _result.BringToFront();

            _validate.Click += delegate { ValidateNow(); };
        }

        private void ValidateNow()
        {
            RuntimeSemanticExpectation expected;

            try
            {
                expected = ReadExpectation();
            }
            catch (Exception ex)
            {
                MessageBox.Show(
                    ex.Message,
                    "輸入錯誤",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Warning);
                return;
            }

            var runtime = _bridge.Read();

            var result = RuntimeSemanticValidator.Validate(
                runtime,
                expected);

            ShowResult(
                runtime,
                expected,
                result);

            SaveEvidence(
                runtime,
                expected,
                result);
        }

        private RuntimeSemanticExpectation ReadExpectation()
        {
            var expected = new RuntimeSemanticExpectation
            {
                CheckHpMp = _checkHpMp.Checked,
                CheckPlayer = _checkPlayer.Checked
            };

            if (!expected.CheckHpMp &&
                !expected.CheckPlayer)
            {
                throw new InvalidDataException(
                    "至少選擇一個驗證區塊。");
            }

            if (expected.CheckHpMp)
            {
                expected.CurrentHp = ParseNonNegativeInt(_currentHp.Text, "目前 HP");
                expected.MaxHp = ParsePositiveInt(_maxHp.Text, "最大 HP");
                expected.CurrentMp = ParseNonNegativeInt(_currentMp.Text, "目前 MP");
                expected.MaxMp = ParseNonNegativeInt(_maxMp.Text, "最大 MP");

                if (expected.CurrentHp > expected.MaxHp)
                    throw new InvalidDataException("目前 HP 不可大於最大 HP。");

                if (expected.CurrentMp > expected.MaxMp)
                    throw new InvalidDataException("目前 MP 不可大於最大 MP。");
            }

            if (expected.CheckPlayer)
            {
                expected.PlayerObjectId = ParsePositiveUInt(_objectId.Text, "ObjectId");
                expected.PlayerX = ParseU16(_x.Text, "X");
                expected.PlayerY = ParseU16(_y.Text, "Y");
            }

            return expected;
        }

        private void ShowResult(
            RuntimeSnapshot runtime,
            RuntimeSemanticExpectation expected,
            RuntimeSemanticValidationResult result)
        {
            _result.BeginUpdate();
            _result.Items.Clear();

            if (expected.CheckHpMp)
            {
                var expectedText =
                    expected.CurrentHp + "/" + expected.MaxHp +
                    "  " +
                    expected.CurrentMp + "/" + expected.MaxMp;

                var actualText =
                    runtime != null &&
                    runtime.CurrentHp.HasValue &&
                    runtime.MaxHp.HasValue &&
                    runtime.CurrentMp.HasValue &&
                    runtime.MaxMp.HasValue
                        ? runtime.CurrentHp.Value + "/" +
                          runtime.MaxHp.Value + "  " +
                          runtime.CurrentMp.Value + "/" +
                          runtime.MaxMp.Value
                        : "--";

                _result.Items.Add(
                    new ListViewItem(new[]
                    {
                        "WP4 HP/MP",
                        expectedText,
                        actualText,
                        result.HpMpPass ? "PASS" : "FAIL"
                    }));
            }

            if (expected.CheckPlayer)
            {
                var expectedText =
                    expected.PlayerObjectId +
                    " @ " +
                    expected.PlayerX + "," + expected.PlayerY;

                var actualText =
                    runtime != null &&
                    runtime.PlayerObjectId.HasValue &&
                    runtime.PlayerX.HasValue &&
                    runtime.PlayerY.HasValue
                        ? runtime.PlayerObjectId.Value +
                          " @ " +
                          runtime.PlayerX.Value + "," +
                          runtime.PlayerY.Value
                        : "--";

                _result.Items.Add(
                    new ListViewItem(new[]
                    {
                        "WP3 Player",
                        expectedText,
                        actualText,
                        result.PlayerPass ? "PASS" : "FAIL"
                    }));
            }

            _result.EndUpdate();

            _status.Text =
                (expected.CheckHpMp
                    ? result.HpMpStatus + " "
                    : "") +
                (expected.CheckPlayer
                    ? result.PlayerStatus
                    : "");
        }

        private void SaveEvidence(
            RuntimeSnapshot runtime,
            RuntimeSemanticExpectation expected,
            RuntimeSemanticValidationResult result)
        {
            try
            {
                var path = Path.Combine(
                    _appDir,
                    "runtime_semantic_validation_evidence.txt");

                var sb = new StringBuilder();

                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("PID=" + (runtime == null ? 0 : runtime.ProcessId));
                sb.AppendLine(
                    "PROCESS_START_UTC=" +
                    (runtime != null && runtime.ProcessStartTimeUtc.HasValue
                        ? runtime.ProcessStartTimeUtc.Value.ToString("o")
                        : ""));
                sb.AppendLine(
                    "MODULE_BASE=" +
                    (runtime != null
                        ? "0x" + runtime.ModuleBase.ToInt64().ToString("X8")
                        : ""));
                sb.AppendLine("CHECK_HPMP=" + (expected.CheckHpMp ? 1 : 0));
                sb.AppendLine("CHECK_PLAYER=" + (expected.CheckPlayer ? 1 : 0));

                if (expected.CheckHpMp)
                {
                    sb.AppendLine("EXPECTED_CURRENT_HP=" + expected.CurrentHp);
                    sb.AppendLine("EXPECTED_MAX_HP=" + expected.MaxHp);
                    sb.AppendLine("EXPECTED_CURRENT_MP=" + expected.CurrentMp);
                    sb.AppendLine("EXPECTED_MAX_MP=" + expected.MaxMp);
                    sb.AppendLine("ACTUAL_CURRENT_HP=" + Value(runtime == null ? null : runtime.CurrentHp));
                    sb.AppendLine("ACTUAL_MAX_HP=" + Value(runtime == null ? null : runtime.MaxHp));
                    sb.AppendLine("ACTUAL_CURRENT_MP=" + Value(runtime == null ? null : runtime.CurrentMp));
                    sb.AppendLine("ACTUAL_MAX_MP=" + Value(runtime == null ? null : runtime.MaxMp));
                    sb.AppendLine("HPMP_PASS=" + (result.HpMpPass ? 1 : 0));
                }

                if (expected.CheckPlayer)
                {
                    sb.AppendLine("EXPECTED_OBJECT_ID=" + expected.PlayerObjectId);
                    sb.AppendLine("EXPECTED_X=" + expected.PlayerX);
                    sb.AppendLine("EXPECTED_Y=" + expected.PlayerY);
                    sb.AppendLine("ACTUAL_OBJECT_ID=" + (runtime != null && runtime.PlayerObjectId.HasValue ? runtime.PlayerObjectId.Value.ToString() : ""));
                    sb.AppendLine("ACTUAL_X=" + (runtime != null && runtime.PlayerX.HasValue ? runtime.PlayerX.Value.ToString() : ""));
                    sb.AppendLine("ACTUAL_Y=" + (runtime != null && runtime.PlayerY.HasValue ? runtime.PlayerY.Value.ToString() : ""));
                    sb.AppendLine("PLAYER_PASS=" + (result.PlayerPass ? 1 : 0));
                }

                sb.AppendLine("MEMORY_WRITE=NO");
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

        private static string Value(int? value)
        {
            return value.HasValue
                ? value.Value.ToString()
                : "";
        }

        private static int ParsePositiveInt(string text, string name)
        {
            int value;
            if (!int.TryParse((text ?? "").Trim(), out value) || value <= 0)
                throw new InvalidDataException(name + " 必須是大於 0 的整數。");
            return value;
        }

        private static int ParseNonNegativeInt(string text, string name)
        {
            int value;
            if (!int.TryParse((text ?? "").Trim(), out value) || value < 0)
                throw new InvalidDataException(name + " 必須是大於等於 0 的整數。");
            return value;
        }

        private static uint ParsePositiveUInt(string text, string name)
        {
            text = (text ?? "").Trim();
            uint value;

            if (text.StartsWith("0x", StringComparison.OrdinalIgnoreCase))
            {
                if (!uint.TryParse(
                    text.Substring(2),
                    NumberStyles.HexNumber,
                    CultureInfo.InvariantCulture,
                    out value))
                    throw new InvalidDataException(name + " 格式錯誤。");
            }
            else if (!uint.TryParse(text, out value))
            {
                throw new InvalidDataException(name + " 格式錯誤。");
            }

            if (value == 0)
                throw new InvalidDataException(name + " 必須大於 0。");

            return value;
        }

        private static ushort ParseU16(string text, string name)
        {
            var value = ParsePositiveUInt(text, name);

            if (value > ushort.MaxValue)
                throw new InvalidDataException(name + " 超出 0..65535。");

            return (ushort)value;
        }

        private static Label AddLabel(
            Control parent,
            string text,
            int left,
            int top)
        {
            var label = new Label
            {
                Text = text,
                Left = left,
                Top = top,
                Width = 60
            };
            parent.Controls.Add(label);
            return label;
        }

        private static TextBox AddBox(
            Control parent,
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
            parent.Controls.Add(box);
            return box;
        }
    }
}
