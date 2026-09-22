using System;
using System.IO;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class InventoryValidationCompareControl :
        UserControl
    {
        private readonly string _appDir;

        private NumericUpDown _sessions;
        private Button _compare;
        private Label _status;
        private ListView _details;

        public InventoryValidationCompareControl(
            string appDir)
        {
            _appDir = appDir;
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel
            {
                Dock = DockStyle.Top,
                Height = 82,
                Padding = new Padding(8)
            };

            Controls.Add(top);

            top.Controls.Add(
                new Label
                {
                    Text = "比對最近 sessions",
                    Left = 12,
                    Top = 16,
                    Width = 105
                });

            _sessions = new NumericUpDown
            {
                Left = 122,
                Top = 12,
                Width = 60,
                Minimum = 3,
                Maximum = 20,
                Value = 3
            };

            top.Controls.Add(_sessions);

            _compare = new Button
            {
                Text = "比對 WP6",
                Left = 198,
                Top = 10,
                Width = 100
            };

            top.Controls.Add(_compare);

            _status = new Label
            {
                Left = 314,
                Top = 14,
                Width = 410,
                Height = 52,
                Text =
                    "建議 3 次：同角色登入 → 重登 → 完整關閉 Lin.bin2 後重新啟動。"
            };

            top.Controls.Add(_status);

            _details = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };

            _details.Columns.Add(
                "項目",
                180);

            _details.Columns.Add(
                "值",
                180);

            _details.Columns.Add(
                "判定",
                260);

            Controls.Add(_details);
            _details.BringToFront();

            _compare.Click +=
                delegate
                {
                    Compare();
                };
        }

        private void Compare()
        {
            var path =
                Path.Combine(
                    _appDir,
                    "inventory_validation_evidence.txt");

            var summary =
                InventoryValidationEvidenceComparer
                .CompareLatest(
                    path,
                    (int)_sessions.Value);

            _details.BeginUpdate();
            _details.Items.Clear();

            Add(
                "Sessions",
                summary.Sessions.ToString(),
                summary.Sessions >= 3
                    ? "PASS"
                    : "需要至少 3 次");

            Add(
                "PASS sessions",
                summary.PassSessions.ToString(),
                summary.PassSessions ==
                    summary.Sessions &&
                summary.Sessions > 0
                    ? "PASS"
                    : "有 session 未通過");

            Add(
                "Client instances",
                summary.DistinctProcessInstances
                    .ToString(),
                summary.DistinctProcessInstances >= 2
                    ? "完整重啟證據 PASS"
                    : "尚無跨 client restart");

            Add(
                "Expectation sets",
                summary.DistinctExpectationSets
                    .ToString(),
                summary.DistinctExpectationSets == 1
                    ? "同一組已知背包資料"
                    : "比對條件不同，不能合併");

            Add(
                "WP6 restart gate",
                summary.RestartStablePass
                    ? "PASS"
                    : "NOT_YET",
                summary.RestartStablePass
                    ? "正式背包列舉已跨 restart 重複吻合"
                    : "尚未達 WP6 restart gate");

            _details.EndUpdate();

            _status.Text =
                summary.Status;
        }

        private void Add(
            string name,
            string value,
            string verdict)
        {
            _details.Items.Add(
                new ListViewItem(
                    new[]
                    {
                        name,
                        value,
                        verdict
                    }));
        }
    }
}
