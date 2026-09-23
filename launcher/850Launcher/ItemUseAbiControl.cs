using System;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class ItemUseAbiControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;

        private ComboBox _candidate;
        private Button _refresh;
        private Button _analyze;
        private Label _status;
        private ListView _details;
        private ListView _callsites;

        public ItemUseAbiControl(
            string appDir)
        {
            _appDir = appDir;
            _bridge =
                new ProcessRuntimeBridge(appDir);

            Dock = DockStyle.Fill;
            BuildUi();
            RefreshCandidates();
        }

        private void BuildUi()
        {
            var top = new Panel
            {
                Dock = DockStyle.Top,
                Height = 96,
                Padding = new Padding(8)
            };

            Controls.Add(top);

            _refresh = new Button
            {
                Text = "刷新候選",
                Left = 12,
                Top = 10,
                Width = 90
            };
            top.Controls.Add(_refresh);

            _candidate = new ComboBox
            {
                Left = 116,
                Top = 11,
                Width = 390,
                DropDownStyle =
                    ComboBoxStyle.DropDownList
            };
            top.Controls.Add(_candidate);

            _analyze = new Button
            {
                Text = "ABI 分析",
                Left = 520,
                Top = 10,
                Width = 90
            };
            top.Controls.Add(_analyze);

            _status = new Label
            {
                Left = 12,
                Top = 48,
                Width = 710,
                Height = 42,
                Text =
                    "只讀分析；不呼叫候選函式。ConventionCandidate 是 heuristic，不可直接拿來啟用 ItemUseBridge。"
            };
            top.Controls.Add(_status);

            var split = new SplitContainer
            {
                Dock = DockStyle.Fill,
                Orientation =
                    Orientation.Horizontal,
                SplitterDistance = 210
            };

            Controls.Add(split);
            split.BringToFront();

            _details = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _details.Columns.Add("欄位", 170);
            _details.Columns.Add("值", 420);
            split.Panel1.Controls.Add(_details);

            _callsites = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _callsites.Columns.Add("Call RVA", 140);
            _callsites.Columns.Add("Caller cleanup bytes", 170);
            _callsites.Columns.Add("說明", 300);
            split.Panel2.Controls.Add(_callsites);

            _refresh.Click +=
                delegate
                {
                    RefreshCandidates();
                };

            _analyze.Click +=
                delegate
                {
                    AnalyzeSelected();
                };
        }

        private void RefreshCandidates()
        {
            _candidate.Items.Clear();

            var summary =
                ItemUseNativeCorrelationEvidenceComparer
                .CompareLatest(
                    Path.Combine(
                        _appDir,
                        "itemuse_native_correlation_evidence.txt"),
                    2);

            foreach (var stable in
                     summary.StableCandidates)
            {
                _candidate.Items.Add(
                    new CandidateChoice(stable));
            }

            if (_candidate.Items.Count > 0)
                _candidate.SelectedIndex = 0;

            _analyze.Enabled =
                summary.RestartStable &&
                _candidate.Items.Count > 0;

            _status.Text =
                summary.RestartStable
                    ? "NATIVE_BEHAVIOR_STABLE：" +
                      summary.StableCandidates.Count +
                      " 個 stable candidate。選一筆做唯讀 ABI 觀測。"
                    : "尚未達 NATIVE_BEHAVIOR_STABLE：" +
                      summary.Status;
        }

        private void AnalyzeSelected()
        {
            var choice =
                _candidate.SelectedItem as
                CandidateChoice;

            if (choice == null)
            {
                _status.Text =
                    "沒有選到 stable candidate。";
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

            PeImageInfo image;

            try
            {
                image = PeImportParser.Parse(
                    Path.Combine(
                        _appDir,
                        "Lin.bin2"));
            }
            catch (Exception ex)
            {
                _status.Text =
                    "Lin.bin2 PE 解析失敗：" +
                    ex.Message;
                return;
            }

            NativeAbiAnalysisResult analysis;
            string error;

            using (var probe =
                   new RuntimeMemoryProbe())
            {
                if (!probe.Attach(
                    runtime.ProcessId,
                    out error))
                {
                    _status.Text = error;
                    return;
                }

                analysis =
                    NativeFunctionAbiAnalyzer.Analyze(
                        probe,
                        runtime,
                        image,
                        choice.Candidate.FunctionRva);
            }

            var fingerprintMatch =
                string.Equals(
                    analysis.FunctionSha256,
                    choice.Candidate.FunctionSha256,
                    StringComparison.OrdinalIgnoreCase);

            ShowAnalysis(
                choice.Candidate,
                analysis,
                fingerprintMatch);

            SaveEvidence(
                runtime,
                choice.Candidate,
                analysis,
                fingerprintMatch);

            _status.Text =
                fingerprintMatch
                    ? analysis.Status +
                      " 此結果仍是 ABI heuristic；CALL_EXECUTED=NO。"
                    : "FAIL：目前 function fingerprint 與 native behavior stable evidence 不一致。";
        }

        private void ShowAnalysis(
            ItemUseNativeStableCandidate candidate,
            NativeAbiAnalysisResult analysis,
            bool fingerprintMatch)
        {
            _details.BeginUpdate();
            _details.Items.Clear();

            AddDetail(
                "Root RVA",
                "0x" +
                candidate.RootRva.ToString("X8"));
            AddDetail(
                "Function RVA",
                "0x" +
                candidate.FunctionRva.ToString("X8"));
            AddDetail(
                "Fingerprint",
                fingerprintMatch
                    ? "MATCH"
                    : "MISMATCH");
            AddDetail(
                "SHA256 first64",
                analysis.FunctionSha256);
            AddDetail(
                "Prologue",
                analysis.Prologue);
            AddDetail(
                "Early ECX heuristic",
                analysis.EarlyEcxHeuristic
                    ? "YES"
                    : "NO");
            AddDetail(
                "Plain RET",
                analysis.PlainRetSeen
                    ? "YES"
                    : "NO");
            AddDetail(
                "RET pop bytes",
                JoinInts(
                    analysis.RetPopBytes));
            AddDetail(
                "Direct callsites",
                analysis.CallSites.Count
                    .ToString());
            AddDetail(
                "Convention candidate",
                analysis.ConventionCandidate);

            _details.EndUpdate();

            _callsites.BeginUpdate();
            _callsites.Items.Clear();

            foreach (var site in
                     analysis.CallSites)
            {
                _callsites.Items.Add(
                    new ListViewItem(
                        new[]
                        {
                            "0x" +
                            site.CallRva.ToString("X8"),

                            site.CallerCleanupBytes
                                .ToString(),

                            site.CallerCleanupBytes > 0
                                ? "caller cleanup observed"
                                : "no immediate caller cleanup observed"
                        }));
            }

            _callsites.EndUpdate();
        }

        private void SaveEvidence(
            RuntimeSnapshot runtime,
            ItemUseNativeStableCandidate candidate,
            NativeAbiAnalysisResult analysis,
            bool fingerprintMatch)
        {
            try
            {
                var path =
                    Path.Combine(
                        _appDir,
                        "itemuse_abi_evidence.txt");

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
                    "ROOT_RVA=0x" +
                    candidate.RootRva.ToString("X8"));
                sb.AppendLine(
                    "FUNCTION_RVA=0x" +
                    candidate.FunctionRva.ToString("X8"));
                sb.AppendLine(
                    "EXPECTED_SHA256_64=" +
                    candidate.FunctionSha256);
                sb.AppendLine(
                    "ACTUAL_SHA256_64=" +
                    analysis.FunctionSha256);
                sb.AppendLine(
                    "FINGERPRINT_MATCH=" +
                    (fingerprintMatch ? 1 : 0));
                sb.AppendLine(
                    "PROLOGUE=" +
                    analysis.Prologue);
                sb.AppendLine(
                    "EARLY_ECX=" +
                    (analysis.EarlyEcxHeuristic ? 1 : 0));
                sb.AppendLine(
                    "PLAIN_RET=" +
                    (analysis.PlainRetSeen ? 1 : 0));
                sb.AppendLine(
                    "RET_POP_BYTES=" +
                    JoinInts(
                        analysis.RetPopBytes));
                sb.AppendLine(
                    "CALLSITES=" +
                    analysis.CallSites.Count);
                sb.AppendLine(
                    "CONVENTION_CANDIDATE=" +
                    analysis.ConventionCandidate);
                sb.AppendLine(
                    "MEMORY_WRITE=NO");
                sb.AppendLine(
                    "CALL_EXECUTED=NO");

                foreach (var site in
                         analysis.CallSites)
                {
                    sb.AppendLine(
                        "CALLSITE rva=0x" +
                        site.CallRva.ToString("X8") +
                        " caller_cleanup=" +
                        site.CallerCleanupBytes);
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

        private void AddDetail(
            string name,
            string value)
        {
            _details.Items.Add(
                new ListViewItem(
                    new[]
                    {
                        name,
                        value
                    }));
        }

        private static string JoinInts(
            System.Collections.Generic.IList<int> values)
        {
            if (values == null ||
                values.Count == 0)
                return "-";

            var parts =
                new string[values.Count];

            for (var i = 0;
                 i < values.Count;
                 i++)
            {
                parts[i] =
                    values[i].ToString();
            }

            return string.Join(
                ",",
                parts);
        }

        private sealed class CandidateChoice
        {
            public readonly ItemUseNativeStableCandidate Candidate;

            public CandidateChoice(
                ItemUseNativeStableCandidate candidate)
            {
                Candidate = candidate;
            }

            public override string ToString()
            {
                return "Function=0x" +
                       Candidate.FunctionRva.ToString("X8") +
                       " Root=0x" +
                       Candidate.RootRva.ToString("X8") +
                       " SHA=" +
                       ShortHash(
                           Candidate.FunctionSha256);
            }

            private static string ShortHash(
                string value)
            {
                if (string.IsNullOrEmpty(value))
                    return "";

                return value.Length <= 12
                    ? value
                    : value.Substring(0, 12);
            }
        }
    }
}
