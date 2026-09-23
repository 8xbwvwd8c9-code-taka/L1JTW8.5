using System;
using System.ComponentModel;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class ItemUseNativeCorrelationControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;

        private NumericUpDown _depth;
        private Button _scan;
        private Button _refresh;
        private Label _behavior;
        private Label _status;
        private Label _summary;
        private ListView _results;

        public ItemUseNativeCorrelationControl(
            string appDir)
        {
            _appDir = appDir;
            _bridge =
                new ProcessRuntimeBridge(appDir);

            Dock = DockStyle.Fill;
            BuildUi();
            RefreshBehavior();
        }

        private void BuildUi()
        {
            var top = new Panel
            {
                Dock = DockStyle.Top,
                Height = 142,
                Padding = new Padding(8)
            };

            Controls.Add(top);

            _refresh = new Button
            {
                Text = "刷新行為證據",
                Left = 12,
                Top = 10,
                Width = 110
            };
            top.Controls.Add(_refresh);

            top.Controls.Add(
                new Label
                {
                    Text = "Backref 深度",
                    Left = 138,
                    Top = 16,
                    Width = 78
                });

            _depth = new NumericUpDown
            {
                Left = 220,
                Top = 12,
                Width = 52,
                Minimum = 1,
                Maximum = 2,
                Value = 2
            };
            top.Controls.Add(_depth);

            _scan = new Button
            {
                Text = "反查 Native",
                Left = 290,
                Top = 10,
                Width = 100
            };
            top.Controls.Add(_scan);

            _behavior = new Label
            {
                Left = 12,
                Top = 48,
                Width = 710,
                Height = 30,
                Text = "Behavior：--"
            };
            top.Controls.Add(_behavior);

            _status = new Label
            {
                Left = 12,
                Top = 78,
                Width = 710,
                Height = 30,
                Text =
                    "唯讀：NEW_HIT → pointer backref → module root → executable xref → function candidate。"
            };
            top.Controls.Add(_status);

            _summary = new Label
            {
                Left = 12,
                Top = 108,
                Width = 710,
                Height = 24,
                Text = "Native behavior evidence：尚未比對。"
            };
            top.Controls.Add(_summary);

            _results = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };

            _results.Columns.Add(
                "Behavior Hit",
                110);
            _results.Columns.Add(
                "Depth",
                50);
            _results.Columns.Add(
                "Root RVA",
                95);
            _results.Columns.Add(
                "Code Xref RVA",
                105);
            _results.Columns.Add(
                "Function RVA",
                105);
            _results.Columns.Add(
                "SHA256(64B)",
                150);

            Controls.Add(_results);
            _results.BringToFront();

            _refresh.Click +=
                delegate
                {
                    RefreshBehavior();
                };

            _scan.Click +=
                delegate
                {
                    BeginScan();
                };
        }

        private void RefreshBehavior()
        {
            var behavior =
                ItemUseBehaviorEvidenceComparer
                .LatestAuthoritativeCorrelated(
                    Path.Combine(
                        _appDir,
                        "itemuse_behavior_evidence.txt"));

            if (behavior == null)
            {
                _behavior.Text =
                    "Behavior：沒有可用的 authoritative correlated session。";
                return;
            }

            _behavior.Text =
                "Behavior：PID=" +
                behavior.Pid +
                " / ItemId=" +
                behavior.ItemId +
                " / ObjectId=" +
                behavior.ObjectId +
                " / NEW_HIT=" +
                behavior.NewHitAddresses.Count +
                " / " +
                behavior.Time.ToString(
                    "yyyy-MM-dd HH:mm:ss");
        }

        private void BeginScan()
        {
            var behaviorPath =
                Path.Combine(
                    _appDir,
                    "itemuse_behavior_evidence.txt");

            var behavior =
                ItemUseBehaviorEvidenceComparer
                .LatestAuthoritativeCorrelated(
                    behaviorPath);

            if (behavior == null)
            {
                _status.Text =
                    "沒有可用的 UseItem behavior evidence；請先做「UseItem 行為驗證」。";
                return;
            }

            var runtime =
                _bridge.Read();

            if (!runtime.Connected ||
                !runtime.ClientHashAuthoritative)
            {
                _status.Text =
                    runtime.Status;
                return;
            }

            if (runtime.ProcessId != behavior.Pid ||
                runtime.ProcessStartTimeUtc !=
                    behavior.ProcessStartUtc)
            {
                _status.Text =
                    "最新 behavior evidence 不屬於目前 client instance；請重新做行為驗證。";
                return;
            }

            SetBusy(
                true,
                "正在唯讀反查 pointer root / native xref...這一步可能需要一些時間。");

            var depth =
                (int)_depth.Value;

            var worker =
                new BackgroundWorker();

            worker.DoWork +=
                delegate(
                    object sender,
                    DoWorkEventArgs e)
                {
                    e.Result =
                        ItemUseNativeCorrelationScanner
                        .Scan(
                            _appDir,
                            runtime,
                            behavior,
                            depth);
                };

            worker.RunWorkerCompleted +=
                delegate(
                    object sender,
                    RunWorkerCompletedEventArgs e)
                {
                    SetBusy(false, "");

                    if (e.Error != null)
                    {
                        _status.Text =
                            "Native 關聯失敗：" +
                            e.Error.Message;
                        return;
                    }

                    var result =
                        (ItemUseNativeCorrelationResult)e.Result;

                    ShowResult(result);
                    _status.Text =
                        result.Status;

                    SaveEvidence(
                        runtime,
                        behavior,
                        result);

                    var summary =
                        ItemUseNativeCorrelationEvidenceComparer
                        .CompareLatest(
                            Path.Combine(
                                _appDir,
                                "itemuse_native_correlation_evidence.txt"),
                            2);

                    _summary.Text =
                        "Native behavior evidence：" +
                        summary.Status;
                };

            worker.RunWorkerAsync();
        }

        private void ShowResult(
            ItemUseNativeCorrelationResult result)
        {
            _results.BeginUpdate();
            _results.Items.Clear();

            foreach (var candidate in
                     result.Candidates)
            {
                _results.Items.Add(
                    new ListViewItem(
                        new[]
                        {
                            "0x" +
                            candidate.BehaviorHitAddress
                                .ToString("X8"),

                            candidate.BackrefDepth
                                .ToString(),

                            "0x" +
                            candidate.ModuleRootRva
                                .ToString("X8"),

                            "0x" +
                            candidate.CodeImmediateRva
                                .ToString("X8"),

                            "0x" +
                            candidate.FunctionRva
                                .ToString("X8"),

                            ShortHash(
                                candidate.FunctionSha256)
                        }));
            }

            _results.EndUpdate();
        }

        private void SaveEvidence(
            RuntimeSnapshot runtime,
            ItemUseBehaviorEvidenceSession behavior,
            ItemUseNativeCorrelationResult result)
        {
            try
            {
                var path =
                    Path.Combine(
                        _appDir,
                        "itemuse_native_correlation_evidence.txt");

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
                        ? runtime.ProcessStartTimeUtc.Value.ToString("o")
                        : ""));
                sb.AppendLine(
                    "CLIENT_SHA256=" +
                    (runtime.ClientSha256 ?? ""));
                sb.AppendLine(
                    "CLIENT_AUTHORITY=" +
                    (runtime.ClientHashAuthoritative ? 1 : 0));
                sb.AppendLine(
                    "OBJECT_ID=" +
                    behavior.ObjectId);
                sb.AppendLine(
                    "ITEM_ID=" +
                    behavior.ItemId);
                sb.AppendLine(
                    "BEHAVIOR_TIME=" +
                    behavior.Time.ToString(
                        "yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine(
                    "BEHAVIOR_NEW_HITS=" +
                    behavior.NewHitAddresses.Count);
                sb.AppendLine(
                    "BACKREF_NODES=" +
                    result.BackrefNodes);
                sb.AppendLine(
                    "MODULE_ROOTS=" +
                    result.ModuleRoots);
                sb.AppendLine(
                    "NATIVE_XREFS=" +
                    result.NativeXrefs);
                sb.AppendLine(
                    "CANDIDATES=" +
                    result.Candidates.Count);
                sb.AppendLine(
                    "CANDIDATE_LIMIT=" +
                    (result.CandidateLimitReached ? 1 : 0));
                sb.AppendLine(
                    "MEMORY_WRITE=NO");
                sb.AppendLine(
                    "PROOF_LEVEL=HEURISTIC_NATIVE_CORRELATION");

                foreach (var candidate in
                         result.Candidates)
                {
                    sb.AppendLine(
                        "CANDIDATE hit=0x" +
                        candidate.BehaviorHitAddress
                            .ToString("X8") +
                        " depth=" +
                        candidate.BackrefDepth +
                        " root_rva=0x" +
                        candidate.ModuleRootRva
                            .ToString("X8") +
                        " xref_rva=0x" +
                        candidate.CodeImmediateRva
                            .ToString("X8") +
                        " func_rva=0x" +
                        candidate.FunctionRva
                            .ToString("X8") +
                        " sha256_64=" +
                        candidate.FunctionSha256);
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

        private void SetBusy(
            bool busy,
            string status)
        {
            _scan.Enabled = !busy;
            _refresh.Enabled = !busy;
            _depth.Enabled = !busy;

            if (!string.IsNullOrEmpty(status))
                _status.Text = status;
        }

        private static string ShortHash(
            string value)
        {
            if (string.IsNullOrEmpty(value))
                return "";

            return value.Length <= 16
                ? value
                : value.Substring(0, 16);
        }
    }
}
