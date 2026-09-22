using System;
using System.IO;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class PointerEvidenceCompareControl :
        UserControl
    {
        private readonly string _appDir;

        private NumericUpDown _sessions;
        private NumericUpDown _processes;
        private Button _compare;
        private Button _copy;
        private Label _status;
        private ListView _results;

        public PointerEvidenceCompareControl(
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
                Height = 86,
                Padding = new Padding(8)
            };

            Controls.Add(top);

            top.Controls.Add(
                new Label
                {
                    Text = "最少 sessions",
                    Left = 12,
                    Top = 16,
                    Width = 80
                });

            _sessions = new NumericUpDown
            {
                Left = 96,
                Top = 12,
                Width = 58,
                Minimum = 2,
                Maximum = 20,
                Value = 3
            };

            top.Controls.Add(_sessions);

            top.Controls.Add(
                new Label
                {
                    Text = "最少 client instances",
                    Left = 174,
                    Top = 16,
                    Width = 120
                });

            _processes = new NumericUpDown
            {
                Left = 298,
                Top = 12,
                Width = 58,
                Minimum = 1,
                Maximum = 10,
                Value = 2
            };

            top.Controls.Add(_processes);

            _compare = new Button
            {
                Text = "比對映射",
                Left = 376,
                Top = 10,
                Width = 100
            };

            _copy = new Button
            {
                Text = "複製選取",
                Left = 484,
                Top = 10,
                Width = 100,
                Enabled = false
            };

            top.Controls.Add(_compare);
            top.Controls.Add(_copy);

            _status = new Label
            {
                Left = 12,
                Top = 48,
                Width = 710,
                Height = 30,
                Text =
                    "只比較同 FIELD 的 pointer expression；跨 client restart 穩定才標 RESTART_STABLE。"
            };

            top.Controls.Add(_status);

            _results = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                MultiSelect = false,
                GridLines = true
            };

            _results.Columns.Add(
                "欄位",
                110);

            _results.Columns.Add(
                "Expression",
                260);

            _results.Columns.Add(
                "Sessions",
                70);

            _results.Columns.Add(
                "Processes",
                70);

            _results.Columns.Add(
                "Depth",
                55);

            _results.Columns.Add(
                "狀態",
                120);

            Controls.Add(_results);
            _results.BringToFront();

            _compare.Click +=
                delegate
                {
                    Compare();
                };

            _copy.Click +=
                delegate
                {
                    CopySelected();
                };

            _results.SelectedIndexChanged +=
                delegate
                {
                    _copy.Enabled =
                        _results.SelectedItems.Count >
                        0;
                };
        }

        private void Compare()
        {
            var path =
                Path.Combine(
                    _appDir,
                    "pointer_probe_evidence.txt");

            var list =
                PointerEvidenceComparer.Compare(
                    path,
                    (int)_sessions.Value,
                    (int)_processes.Value);

            _results.BeginUpdate();
            _results.Items.Clear();

            foreach (var candidate in list)
            {
                var row =
                    new ListViewItem(
                        new[]
                        {
                            candidate.Field,
                            candidate.Expression,
                            candidate.SessionCount
                                .ToString(),
                            candidate.ProcessInstanceCount
                                .ToString(),
                            candidate.Depth
                                .ToString(),
                            candidate.Status
                        });

                row.Tag =
                    candidate.Expression;

                _results.Items.Add(row);
            }

            _results.EndUpdate();

            var stable = 0;
            foreach (var candidate in list)
            {
                if (candidate.Status ==
                    "RESTART_STABLE")
                {
                    stable++;
                }
            }

            _status.Text =
                "候選=" +
                list.Count +
                "，RESTART_STABLE=" +
                stable +
                "。穩定仍不等於 WP3/WP4 PASS，目標數值/移動驗證必須先成立。";
        }

        private void CopySelected()
        {
            if (_results.SelectedItems.Count ==
                0)
                return;

            var expression =
                Convert.ToString(
                    _results.SelectedItems[0].Tag);

            if (string.IsNullOrWhiteSpace(
                expression))
                return;

            Clipboard.SetText(
                expression);

            _status.Text =
                "已複製：" +
                expression;
        }
    }
}
