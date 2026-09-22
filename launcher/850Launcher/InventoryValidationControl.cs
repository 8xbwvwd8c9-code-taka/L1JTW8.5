using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class InventoryValidationControl :
        UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _runtimeBridge;
        private readonly MappedInventoryBridge _inventoryBridge;

        private TextBox _expected;
        private Button _validate;
        private Button _loadExample;
        private Button _clear;
        private Label _status;
        private ListView _inventory;
        private ListView _mismatches;

        public InventoryValidationControl(
            string appDir)
        {
            _appDir = appDir;
            _runtimeBridge =
                new ProcessRuntimeBridge(
                    appDir);

            _inventoryBridge =
                new MappedInventoryBridge(
                    appDir);

            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var left = new Panel
            {
                Dock = DockStyle.Left,
                Width = 240,
                Padding = new Padding(8)
            };

            Controls.Add(left);

            left.Controls.Add(
                new Label
                {
                    Text = "已知背包資料（itemId=count）",
                    Left = 8,
                    Top = 8,
                    Width = 210
                });

            _expected = new TextBox
            {
                Left = 8,
                Top = 32,
                Width = 216,
                Height = 190,
                Multiline = true,
                ScrollBars = ScrollBars.Vertical,
                Font =
                    new System.Drawing.Font(
                        "Consolas",
                        9.0f)
            };

            left.Controls.Add(_expected);

            _validate = new Button
            {
                Text = "驗證背包",
                Left = 8,
                Top = 232,
                Width = 100
            };

            _loadExample = new Button
            {
                Text = "範例",
                Left = 116,
                Top = 232,
                Width = 60
            };

            _clear = new Button
            {
                Text = "清除",
                Left = 182,
                Top = 232,
                Width = 42
            };

            left.Controls.Add(_validate);
            left.Controls.Add(_loadExample);
            left.Controls.Add(_clear);

            _status = new Label
            {
                Left = 8,
                Top = 274,
                Width = 216,
                Height = 150,
                Text =
                    "至少填 2~3 個目前背包中可確認的 stack item，再驗證正式 InventoryBridge。"
            };

            left.Controls.Add(_status);

            var split = new SplitContainer
            {
                Dock = DockStyle.Fill,
                Orientation =
                    Orientation.Horizontal,
                SplitterDistance = 245
            };

            Controls.Add(split);
            split.BringToFront();

            _inventory = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };

            _inventory.Columns.Add(
                "ObjectId",
                95);

            _inventory.Columns.Add(
                "ItemId",
                75);

            _inventory.Columns.Add(
                "名稱",
                180);

            _inventory.Columns.Add(
                "Count",
                80);

            _inventory.Columns.Add(
                "Enchant",
                70);

            _inventory.Columns.Add(
                "Equipped",
                70);

            split.Panel1.Controls.Add(
                _inventory);

            _mismatches = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };

            _mismatches.Columns.Add(
                "ItemId",
                90);

            _mismatches.Columns.Add(
                "Expected",
                100);

            _mismatches.Columns.Add(
                "Actual",
                100);

            _mismatches.Columns.Add(
                "結果",
                140);

            split.Panel2.Controls.Add(
                _mismatches);

            _validate.Click +=
                delegate
                {
                    ValidateInventory();
                };

            _loadExample.Click +=
                delegate
                {
                    _expected.Text =
                        "40010=155\r\n" +
                        "40011=87\r\n" +
                        "40100=23";
                };

            _clear.Click +=
                delegate
                {
                    _expected.Clear();
                    _inventory.Items.Clear();
                    _mismatches.Items.Clear();

                    _status.Text =
                        "結果已清除。";
                };
        }

        private void ValidateInventory()
        {
            List<InventoryExpectation> expected;

            try
            {
                expected =
                    InventoryRuntimeValidator
                    .ParseExpectations(
                        _expected.Text);
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

            var runtime =
                _runtimeBridge.Read();

            if (!runtime.Connected)
            {
                _status.Text =
                    runtime.Status;

                return;
            }

            var inventory =
                _inventoryBridge.Read(
                    runtime);

            var result =
                InventoryRuntimeValidator.Validate(
                    runtime,
                    inventory,
                    expected);

            ShowInventory(
                inventory);

            ShowMismatches(
                result);

            _status.Text =
                result.Status +
                "\r\nRecords=" +
                result.RecordCount +
                " / UniqueObjectId=" +
                result.UniqueObjectIds +
                "\r\n此結果只代表本 session；WP6 還要重登與完整重啟重複 PASS。";

            SaveEvidence(
                runtime,
                inventory,
                expected,
                result);
        }

        private void ShowInventory(
            InventoryReadResult inventory)
        {
            _inventory.BeginUpdate();
            _inventory.Items.Clear();

            if (inventory != null)
            {
                foreach (var item in inventory.Items)
                {
                    _inventory.Items.Add(
                        new ListViewItem(
                            new[]
                            {
                                item.ObjectId
                                    .ToString(),

                                item.ItemId
                                    .ToString(),

                                item.Name,

                                item.Count
                                    .ToString(),

                                item.Enchant.HasValue
                                    ? item.Enchant.Value
                                      .ToString()
                                    : "",

                                item.Equipped.HasValue
                                    ? item.Equipped.Value
                                      .ToString()
                                    : ""
                            }));
                }
            }

            _inventory.EndUpdate();
        }

        private void ShowMismatches(
            InventoryValidationResult result)
        {
            _mismatches.BeginUpdate();
            _mismatches.Items.Clear();

            if (result.Passed)
            {
                _mismatches.Items.Add(
                    new ListViewItem(
                        new[]
                        {
                            "",
                            "",
                            "",
                            "PASS"
                        }));
            }
            else
            {
                foreach (var mismatch in
                         result.Mismatches)
                {
                    _mismatches.Items.Add(
                        new ListViewItem(
                            new[]
                            {
                                mismatch.ItemId
                                    .ToString(),

                                mismatch.Expected
                                    .ToString(),

                                mismatch.Actual
                                    .ToString(),

                                "MISMATCH"
                            }));
                }
            }

            _mismatches.EndUpdate();
        }

        private void SaveEvidence(
            RuntimeSnapshot runtime,
            InventoryReadResult inventory,
            IList<InventoryExpectation> expected,
            InventoryValidationResult result)
        {
            try
            {
                var path =
                    Path.Combine(
                        _appDir,
                        "inventory_validation_evidence.txt");

                var sb =
                    new StringBuilder();

                sb.AppendLine(
                    "TIME=" +
                    DateTime.Now.ToString(
                        "yyyy-MM-dd HH:mm:ss"));

                sb.AppendLine(
                    "PID=" +
                    runtime.ProcessId);

                sb.AppendLine(
                    "PROCESS_START_UTC=" +
                    (runtime.ProcessStartTimeUtc.HasValue
                        ? runtime.ProcessStartTimeUtc.Value
                          .ToString("o")
                        : ""));

                sb.AppendLine(
                    "CLIENT_SHA256=" +
                    (runtime.ClientSha256 ?? ""));

                sb.AppendLine(
                    "CLIENT_AUTHORITY=" +
                    (runtime.ClientHashAuthoritative ? 1 : 0));

                sb.AppendLine(
                    "MODULE_BASE=0x" +
                    runtime.ModuleBase.ToInt64()
                    .ToString("X8"));

                sb.AppendLine(
                    "INVENTORY_MAPPED=" +
                    (result.InventoryMapped
                        ? 1
                        : 0));

                sb.AppendLine(
                    "PASS=" +
                    (result.Passed
                        ? 1
                        : 0));

                sb.AppendLine(
                    "RECORD_COUNT=" +
                    result.RecordCount);

                sb.AppendLine(
                    "UNIQUE_OBJECT_IDS=" +
                    result.UniqueObjectIds);

                sb.AppendLine(
                    "INVENTORY_STATUS=" +
                    (inventory == null
                        ? ""
                        : inventory.Status));

                sb.AppendLine(
                    "MEMORY_WRITE=NO");

                sb.AppendLine();

                sb.AppendLine(
                    "[EXPECTED]");

                foreach (var item in expected)
                {
                    sb.AppendLine(
                        item.ItemId +
                        "=" +
                        item.Count);
                }

                sb.AppendLine();

                sb.AppendLine(
                    "[MISMATCH]");

                foreach (var mismatch in
                         result.Mismatches)
                {
                    sb.AppendLine(
                        "ITEM_ID=" +
                        mismatch.ItemId +
                        " EXPECTED=" +
                        mismatch.Expected +
                        " ACTUAL=" +
                        mismatch.Actual);
                }

                sb.AppendLine();

                sb.AppendLine(
                    "[INVENTORY]");

                if (inventory != null)
                {
                    foreach (var item in
                             inventory.Items)
                    {
                        sb.AppendLine(
                            "OBJECT_ID=" +
                            item.ObjectId +
                            " ITEM_ID=" +
                            item.ItemId +
                            " COUNT=" +
                            item.Count +
                            " ENCHANT=" +
                            (item.Enchant.HasValue
                                ? item.Enchant.Value
                                  .ToString()
                                : "") +
                            " EQUIPPED=" +
                            (item.Equipped.HasValue
                                ? item.Equipped.Value
                                  .ToString()
                                : "") +
                            " NAME=" +
                            item.Name);
                    }
                }

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
    }
}
