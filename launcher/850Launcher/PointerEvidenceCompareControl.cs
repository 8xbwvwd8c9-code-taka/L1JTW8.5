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
        private Button _apply;
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

            _apply = new Button
            {
                Text = "套用映射",
                Left = 592,
                Top = 10,
                Width = 100,
                Enabled = false
            };

            top.Controls.Add(_compare);
            top.Controls.Add(_copy);
            top.Controls.Add(_apply);

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

            _apply.Click +=
                delegate
                {
                    ApplySelected();
                };

            _results.SelectedIndexChanged +=
                delegate
                {
                    UpdateSelectionState();
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
                    candidate;

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

        private void UpdateSelectionState()
        {
            var hasSelection =
                _results.SelectedItems.Count > 0;

            _copy.Enabled =
                hasSelection;

            if (!hasSelection)
            {
                _apply.Enabled = false;
                return;
            }

            var candidate =
                _results.SelectedItems[0].Tag
                as StablePointerCandidate;

            _apply.Enabled =
                candidate != null &&
                candidate.Status ==
                    "RESTART_STABLE" &&
                IsRuntimeMapField(
                    candidate.Field);
        }

        private void ApplySelected()
        {
            if (_results.SelectedItems.Count == 0)
                return;

            var candidate =
                _results.SelectedItems[0].Tag
                as StablePointerCandidate;

            if (candidate == null ||
                candidate.Status !=
                    "RESTART_STABLE")
            {
                _status.Text =
                    "只有 RESTART_STABLE 候選可以套用。";
                return;
            }

            string section;
            string key;

            if (!TryGetRuntimeMapKey(
                candidate.Field,
                out section,
                out key))
            {
                _status.Text =
                    "此欄位不屬於 runtime-map.ini：" +
                    candidate.Field;
                return;
            }

            try
            {
                var path =
                    Path.Combine(
                        _appDir,
                        "runtime-map.ini");

                var ini =
                    IniDocument.Load(path);

                ini.Set(
                    section,
                    key,
                    candidate.Expression);

                ini.Save(path);

                _status.Text =
                    "已套用 " +
                    candidate.Field +
                    " = " +
                    candidate.Expression +
                    "；下一步執行「映射驗證」。";
            }
            catch (Exception ex)
            {
                _status.Text =
                    "套用失敗：" +
                    ex.Message;
            }
        }

        private static bool IsRuntimeMapField(
            string field)
        {
            string section;
            string key;

            return TryGetRuntimeMapKey(
                field,
                out section,
                out key);
        }

        private static bool TryGetRuntimeMapKey(
            string field,
            out string section,
            out string key)
        {
            section = "";
            key = "";

            if (string.Equals(
                field,
                "CurrentHP",
                StringComparison.OrdinalIgnoreCase))
            {
                section = "HPMP";
                key = "CurrentHP";
                return true;
            }

            if (string.Equals(
                field,
                "MaxHP",
                StringComparison.OrdinalIgnoreCase))
            {
                section = "HPMP";
                key = "MaxHP";
                return true;
            }

            if (string.Equals(
                field,
                "CurrentMP",
                StringComparison.OrdinalIgnoreCase))
            {
                section = "HPMP";
                key = "CurrentMP";
                return true;
            }

            if (string.Equals(
                field,
                "MaxMP",
                StringComparison.OrdinalIgnoreCase))
            {
                section = "HPMP";
                key = "MaxMP";
                return true;
            }

            if (string.Equals(
                field,
                "PlayerObjectId",
                StringComparison.OrdinalIgnoreCase))
            {
                section = "Player";
                key = "ObjectId";
                return true;
            }

            if (string.Equals(
                field,
                "PlayerX",
                StringComparison.OrdinalIgnoreCase))
            {
                section = "Player";
                key = "X";
                return true;
            }

            if (string.Equals(
                field,
                "PlayerY",
                StringComparison.OrdinalIgnoreCase))
            {
                section = "Player";
                key = "Y";
                return true;
            }

            return false;
        }

        private void CopySelected()
        {
            if (_results.SelectedItems.Count ==
                0)
                return;

            var candidate =
                _results.SelectedItems[0].Tag
                as StablePointerCandidate;

            if (candidate == null ||
                string.IsNullOrWhiteSpace(
                    candidate.Expression))
                return;

            Clipboard.SetText(
                candidate.Expression);

            _status.Text =
                "已複製：" +
                candidate.Expression;
        }
    }
}
