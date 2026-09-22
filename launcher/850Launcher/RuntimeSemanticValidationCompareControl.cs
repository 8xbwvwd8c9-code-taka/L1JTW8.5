using System;
using System.IO;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class RuntimeSemanticValidationCompareControl : UserControl
    {
        private readonly string _appDir;

        private NumericUpDown _sessions;
        private Button _compare;
        private Label _status;
        private ListView _rows;

        public RuntimeSemanticValidationCompareControl(string appDir)
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

            top.Controls.Add(new Label
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
                Text = "比對語意證據",
                Left = 198,
                Top = 10,
                Width = 115
            };
            top.Controls.Add(_compare);

            _status = new Label
            {
                Left = 330,
                Top = 14,
                Width = 390,
                Height = 52,
                Text = "每次使用當下真值驗證；至少 3 次，且至少 2 個不同 client instance。"
            };
            top.Controls.Add(_status);

            _rows = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _rows.Columns.Add("Gate", 120);
            _rows.Columns.Add("Checked", 80);
            _rows.Columns.Add("PASS", 80);
            _rows.Columns.Add("Processes", 85);
            _rows.Columns.Add("Result", 150);
            Controls.Add(_rows);
            _rows.BringToFront();

            _compare.Click += delegate { Compare(); };
        }

        private void Compare()
        {
            var path = Path.Combine(
                _appDir,
                "runtime_semantic_validation_evidence.txt");

            var summary =
                RuntimeSemanticValidationEvidenceComparer.CompareLatest(
                    path,
                    (int)_sessions.Value);

            _rows.BeginUpdate();
            _rows.Items.Clear();

            _rows.Items.Add(
                new ListViewItem(new[]
                {
                    "WP4 HP/MP",
                    summary.HpMpCheckedSessions.ToString(),
                    summary.HpMpPassSessions.ToString(),
                    summary.DistinctProcessInstances.ToString(),
                    summary.HpMpRestartPass ? "PASS" : "NOT_YET"
                }));

            _rows.Items.Add(
                new ListViewItem(new[]
                {
                    "WP3 Player",
                    summary.PlayerCheckedSessions.ToString(),
                    summary.PlayerPassSessions.ToString(),
                    summary.DistinctProcessInstances.ToString(),
                    summary.PlayerRestartPass ? "PASS" : "NOT_YET"
                }));

            _rows.EndUpdate();
            _status.Text = summary.Status;
        }
    }
}
