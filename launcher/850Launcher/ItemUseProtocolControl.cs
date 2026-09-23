using System;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class ItemUseProtocolControl : UserControl
    {
        private readonly string _appDir;
        private TextBox _objectId;
        private TextBox _payload;
        private Label _status;

        public ItemUseProtocolControl(string appDir)
        {
            _appDir = appDir;
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            Controls.Add(new Label
            {
                Left = 20,
                Top = 20,
                Width = 690,
                Height = 40,
                Text = "850 recovered server contract: C_ItemUSe opcode=94 (0x5E), first field=objectId(D). 此頁只產生解密後邏輯 payload，不會送包。"
            });

            Controls.Add(new Label
            {
                Left = 20,
                Top = 76,
                Width = 80,
                Text = "ObjectId"
            });

            _objectId = new TextBox
            {
                Left = 108,
                Top = 72,
                Width = 150
            };
            Controls.Add(_objectId);

            var build = new Button
            {
                Text = "產生邏輯封包",
                Left = 274,
                Top = 70,
                Width = 120
            };
            build.Click += delegate { BuildPayload(); };
            Controls.Add(build);

            var behavior = new Button
            {
                Text = "UseItem 行為驗證",
                Left = 410,
                Top = 70,
                Width = 130
            };
            behavior.Click += delegate { OpenBehaviorVerifier(); };
            Controls.Add(behavior);

            Controls.Add(new Label
            {
                Left = 20,
                Top = 118,
                Width = 100,
                Text = "Payload"
            });

            _payload = new TextBox
            {
                Left = 108,
                Top = 114,
                Width = 410,
                ReadOnly = true
            };
            Controls.Add(_payload);

            var copy = new Button
            {
                Text = "複製",
                Left = 532,
                Top = 112,
                Width = 80
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
                Top = 162,
                Width = 690,
                Height = 140,
                Text =
                    "驗證邊界：protocol proof 只證明 server contract。WP7 仍要 850 Lin.bin2 runtime behavior / native send 證據。『UseItem 行為驗證』只讀取遊戲程序記憶體，對照無操作基線與手動使用期間是否出現 0x5E+ObjectId logical pattern；不送包、不寫遊戲記憶體。"
            };
            Controls.Add(_status);
        }

        private void BuildPayload()
        {
            uint objectId;
            if (!ItemUseProtocol.TryParseObjectId(_objectId.Text, out objectId) ||
                objectId == 0)
            {
                _status.Text = "ObjectId 格式錯誤或為 0。";
                return;
            }

            var payload = ItemUseProtocol.BuildNormalItemLogicalPayload(objectId);
            _payload.Text = ItemUseProtocol.ToHex(payload);

            _status.Text =
                "邏輯 payload 已產生：opcode=0x5E，objectId=" + objectId +
                "。未送出任何資料。";

            SaveEvidence(objectId, payload);
        }

        private void OpenBehaviorVerifier()
        {
            var form = new Form
            {
                Text = "WP7 UseItem 行為驗證",
                Width = 780,
                Height = 560,
                StartPosition = FormStartPosition.CenterParent
            };

            form.Controls.Add(
                new ItemUseBehaviorCorrelationControl(
                    _appDir));

            form.ShowDialog(this);
        }

        private void SaveEvidence(uint objectId, byte[] payload)
        {
            try
            {
                var path = Path.Combine(_appDir, "itemuse_protocol_evidence.txt");
                var sb = new StringBuilder();

                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("OBJECT_ID=" + objectId);
                sb.AppendLine("OPCODE_DEC=94");
                sb.AppendLine("OPCODE_HEX=0x5E");
                sb.AppendLine("LOGICAL_PAYLOAD=" + ItemUseProtocol.ToHex(payload));
                sb.AppendLine("SEND=NO");
                sb.AppendLine();

                File.AppendAllText(path, sb.ToString(), new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
