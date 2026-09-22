using System;
using System.IO;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class NativeEvidenceCompareControl : UserControl
    {
        private readonly string _appDir;

        private NumericUpDown _sessions;
        private Button _compare;
        private Label _status;
        private ListView _functions;

        public NativeEvidenceCompareControl(string appDir)
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
                Height = 76,
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
                Minimum = 2,
                Maximum = 10,
                Value = 3
            };
            top.Controls.Add(_sessions);

            _compare = new Button
            {
                Text = "比對證據",
                Left = 198,
                Top = 10,
                Width = 100
            };
            top.Controls.Add(_compare);

            _status = new Label
            {
                Left = 314,
                Top = 15,
                Width = 410,
                Height = 44,
                Text = "建議順序：登入掃一次 → 重登掃一次 → 完整關閉客戶端重開再掃一次。"
            };
            top.Controls.Add(_status);

            _functions = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _functions.Columns.Add("Function RVA", 115);
            _functions.Columns.Add("Sessions", 70);
            _functions.Columns.Add("Stable Edges", 90);
            _functions.Columns.Add("Depth", 60);
            _functions.Columns.Add("0x5E", 135);
            _functions.Columns.Add("SHA256(64B)", 180);
            Controls.Add(_functions);
            _functions.BringToFront();

            _compare.Click += delegate { Compare(); };
        }

        private void Compare()
        {
            var path = Path.Combine(
                _appDir,
                "native_call_graph_evidence.txt");

            var result =
                NativeCallGraphEvidenceComparer.CompareLatest(
                    path,
                    (int)_sessions.Value);

            _functions.BeginUpdate();
            _functions.Items.Clear();

            foreach (var item in result.StableFunctions)
            {
                _functions.Items.Add(
                    new ListViewItem(new[]
                    {
                        "0x" + item.Rva.ToString("X8"),
                        item.SessionCount.ToString(),
                        item.StableIncidentEdges.ToString(),
                        item.MinDepth.ToString(),
                        item.Marker,
                        ShortHash(item.Sha256)
                    }));
            }

            _functions.EndUpdate();
            _status.Text =
                result.Status +
                (result.DistinctProcessInstances >= 2
                    ? " 跨完整客戶端重啟條件=PASS；仍需正常喝水行為關聯。"
                    : " 跨完整客戶端重啟條件=NOT_YET。");
        }

        private static string ShortHash(string value)
        {
            if (string.IsNullOrEmpty(value))
                return "";

            return value.Length <= 24
                ? value
                : value.Substring(0, 24);
        }
    }
}
