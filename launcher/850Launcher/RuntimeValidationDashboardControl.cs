using System;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeValidationDashboardControl :
        UserControl
    {
        private readonly string _appDir;

        private Button _refresh;
        private Label _client;
        private Label _next;
        private ListView _rows;

        public RuntimeValidationDashboardControl(
            string appDir)
        {
            _appDir = appDir;
            Dock = DockStyle.Fill;
            BuildUi();
            RefreshState();
        }

        private void BuildUi()
        {
            var top = new Panel
            {
                Dock = DockStyle.Top,
                Height = 106,
                Padding = new Padding(8)
            };

            Controls.Add(top);

            _refresh = new Button
            {
                Text = "刷新總覽",
                Left = 12,
                Top = 10,
                Width = 100
            };

            top.Controls.Add(_refresh);

            _client = new Label
            {
                Left = 128,
                Top = 14,
                Width = 590,
                Height = 28,
                Text = "Client：尚未檢查"
            };

            top.Controls.Add(_client);

            _next = new Label
            {
                Left = 12,
                Top = 50,
                Width = 706,
                Height = 44,
                Text = "NEXT：--"
            };

            top.Controls.Add(_next);

            _rows = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };

            _rows.Columns.Add(
                "工作包",
                110);

            _rows.Columns.Add(
                "狀態",
                110);

            _rows.Columns.Add(
                "證據",
                285);

            _rows.Columns.Add(
                "下一步",
                220);

            Controls.Add(_rows);
            _rows.BringToFront();

            _refresh.Click +=
                delegate
                {
                    RefreshState();
                };
        }

        private void RefreshState()
        {
            RuntimeValidationDashboardState state;

            try
            {
                state =
                    RuntimeValidationDashboard
                    .Evaluate(_appDir);
            }
            catch (Exception ex)
            {
                _client.Text =
                    "Client：總覽失敗";

                _next.Text =
                    "NEXT：" + ex.Message;

                return;
            }

            _client.Text =
                "Client：" +
                (state.ClientConnected
                    ? "CONNECTED"
                    : "NOT_CONNECTED") +
                " / " +
                state.ClientStatus;

            _next.Text =
                "NEXT：" +
                state.NextAction;

            _rows.BeginUpdate();
            _rows.Items.Clear();

            foreach (var row in state.Rows)
            {
                _rows.Items.Add(
                    new ListViewItem(
                        new[]
                        {
                            row.WorkPackage,
                            row.State,
                            row.Evidence,
                            row.Next
                        }));
            }

            _rows.EndUpdate();
        }
    }
}
