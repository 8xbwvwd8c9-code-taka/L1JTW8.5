using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class NativeSendProbeControl : UserControl
    {
        // User-reported Agent2 live-memory evidence on 2026-09-24.
        // Candidate only until the RVA is reproduced across a fresh client restart.
        private const uint KnownRuntimeSendIatCandidateRva = 0x00EA5898U;
        private const string KnownRuntimeSendIatCandidateSource =
            "AGENT2_LIVE_MEMORY_20260924";

        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe = new RuntimeMemoryProbe();

        private Button _scan;
        private Button _clear;
        private Label _status;
        private ListView _results;

        public NativeSendProbeControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            Dock = DockStyle.Fill;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = new Panel
            {
                Dock = DockStyle.Top,
                Height = 84,
                Padding = new Padding(8)
            };
            Controls.Add(top);

            _scan = new Button
            {
                Text = "掃描 send xref",
                Left = 12,
                Top = 10,
                Width = 120
            };
            _clear = new Button
            {
                Text = "清除",
                Left = 140,
                Top = 10,
                Width = 80
            };
            top.Controls.Add(_scan);
            top.Controls.Add(_clear);

            _status = new Label
            {
                Left = 236,
                Top = 15,
                Width = 490,
                Height = 52,
                Text = "先讀 PE imports；packed 850 若為空，僅回退到已觀測 live send IAT 候選。"
            };
            top.Controls.Add(_status);

            _results = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _results.Columns.Add("Import", 210);
            _results.Columns.Add("IAT RVA", 100);
            _results.Columns.Add("呼叫 RVA", 110);
            _results.Columns.Add("實際位址", 115);
            _results.Columns.Add("型態", 100);
            Controls.Add(_results);
            _results.BringToFront();

            _scan.Click += delegate { Scan(); };
            _clear.Click += delegate
            {
                _results.Items.Clear();
                _status.Text = "結果已清除。";
            };
        }

        private void Scan()
        {
            var runtime = _bridge.Read();
            if (!runtime.Connected)
            {
                MessageBox.Show(
                    runtime.Status,
                    "尚未連接遊戲",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Information);
                return;
            }

            var path = Path.Combine(_appDir, "Lin.bin2");
            if (!File.Exists(path))
            {
                _status.Text = "找不到 Lin.bin2。";
                return;
            }

            PeImageInfo image;
            try
            {
                image = PeImportParser.Parse(path);
            }
            catch (Exception ex)
            {
                _status.Text = "PE 解析失敗：" + ex.Message;
                return;
            }

            string error;
            if (!_probe.Attach(runtime.ProcessId, out error))
            {
                _status.Text = "程序連接失敗：" + error;
                return;
            }

            string status;
            var xrefs = NativeSendXrefScanner.Scan(
                _probe,
                runtime,
                image,
                out status);

            var usedLiveIatCandidate = false;
            string liveIatStatus = "";

            if (xrefs.Count == 0 &&
                runtime.ClientHashAuthoritative)
            {
                var liveXrefs =
                    NativeSendXrefScanner.ScanKnownRuntimeIat(
                        _probe,
                        runtime,
                        image,
                        "WS2_32!send",
                        KnownRuntimeSendIatCandidateRva,
                        out liveIatStatus);

                if (liveXrefs.Count > 0)
                {
                    xrefs = liveXrefs;
                    usedLiveIatCandidate = true;
                }
            }

            NativeCallGraphResult graph = null;
            if (xrefs.Count > 0)
            {
                graph = NativeCallGraphScanner.Build(
                    _probe,
                    runtime,
                    image,
                    xrefs,
                    1);
            }

            ShowResults(xrefs);

            if (usedLiveIatCandidate)
            {
                _status.Text =
                    "LIVE_IAT_CANDIDATE：" +
                    liveIatStatus +
                    "；restart 尚未證明。" +
                    (graph != null ? " " + graph.Status : "");
            }
            else if (!string.IsNullOrEmpty(liveIatStatus))
            {
                _status.Text =
                    status +
                    "；live candidate：" +
                    liveIatStatus;
            }
            else
            {
                _status.Text =
                    status +
                    (graph != null ? " " + graph.Status : "");
            }

            SaveEvidence(
                runtime,
                image,
                xrefs,
                graph,
                usedLiveIatCandidate,
                liveIatStatus);
        }

        private void ShowResults(IList<NativeImportXref> xrefs)
        {
            _results.BeginUpdate();
            _results.Items.Clear();

            foreach (var xref in xrefs)
            {
                _results.Items.Add(new ListViewItem(new[]
                {
                    xref.ImportName,
                    "0x" + xref.IatRva.ToString("X8"),
                    "0x" + xref.InstructionRva.ToString("X8"),
                    "0x" + xref.InstructionAddress.ToInt64().ToString("X8"),
                    xref.Kind
                }));
            }

            _results.EndUpdate();
        }

        private void SaveEvidence(
            RuntimeSnapshot runtime,
            PeImageInfo image,
            IList<NativeImportXref> xrefs,
            NativeCallGraphResult graph,
            bool usedLiveIatCandidate,
            string liveIatStatus)
        {
            try
            {
                var path = Path.Combine(
                    _appDir,
                    "native_send_xref_evidence.txt");

                var sb = new StringBuilder();

                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("PID=" + runtime.ProcessId);
                sb.AppendLine("PROCESS_START_UTC=" + (runtime.ProcessStartTimeUtc.HasValue ? runtime.ProcessStartTimeUtc.Value.ToString("o") : ""));
                sb.AppendLine("CLIENT_SHA256=" + (runtime.ClientSha256 ?? ""));
                sb.AppendLine("CLIENT_AUTHORITY=" + (runtime.ClientHashAuthoritative ? 1 : 0));
                sb.AppendLine("MODULE_BASE=0x" + runtime.ModuleBase.ToInt64().ToString("X8"));
                sb.AppendLine("PE_IMAGE_BASE=0x" + image.ImageBase.ToString("X8"));
                sb.AppendLine("PE_SIZE_OF_IMAGE=0x" + image.SizeOfImage.ToString("X8"));
                sb.AppendLine("XREFS=" + xrefs.Count);
                sb.AppendLine("SCAN_SOURCE=" + (usedLiveIatCandidate ? "LIVE_IAT_CANDIDATE" : "STATIC_PE_IMPORT"));
                sb.AppendLine("LIVE_IAT_CANDIDATE=" + (usedLiveIatCandidate ? 1 : 0));
                sb.AppendLine("LIVE_IAT_CANDIDATE_SOURCE=" + (usedLiveIatCandidate ? KnownRuntimeSendIatCandidateSource : ""));
                sb.AppendLine("KNOWN_RUNTIME_SEND_IAT_RVA=0x" + KnownRuntimeSendIatCandidateRva.ToString("X8"));
                sb.AppendLine("LIVE_IAT_STATUS=" + (liveIatStatus ?? ""));
                sb.AppendLine("LIVE_IAT_RESTART_STABLE=NO");
                sb.AppendLine("ITEM_SPECIFIC_ACTION_PROVEN=NO");
                sb.AppendLine("WP7_NATIVE_USEITEM_PASS=NO");
                sb.AppendLine("MEMORY_WRITE=NO");
                sb.AppendLine("PACKET_SEND=NO");
                sb.AppendLine();

                foreach (var xref in xrefs)
                {
                    sb.AppendLine(
                        "IMPORT=" + xref.ImportName +
                        " IAT_RVA=0x" + xref.IatRva.ToString("X8") +
                        " CALL_RVA=0x" + xref.InstructionRva.ToString("X8") +
                        " CALL_VA=0x" + xref.InstructionAddress.ToInt64().ToString("X8") +
                        " KIND=" + xref.Kind);
                }

                if (graph != null)
                {
                    sb.AppendLine();
                    sb.AppendLine("CALL_GRAPH_FUNCTIONS=" + graph.Functions.Count);
                    sb.AppendLine("CALL_GRAPH_EDGES=" + graph.Edges.Count);
                    sb.AppendLine("CALL_GRAPH_STATUS=" + graph.Status);

                    var ranked =
                        new List<NativeFunctionCandidate>(
                            graph.Functions);

                    ranked.Sort(
                        delegate(
                            NativeFunctionCandidate a,
                            NativeFunctionCandidate b)
                        {
                            if (a.StrongOpcode5EMarker !=
                                b.StrongOpcode5EMarker)
                            {
                                return a.StrongOpcode5EMarker ? -1 : 1;
                            }

                            var depth =
                                a.Depth.CompareTo(b.Depth);

                            if (depth != 0)
                                return depth;

                            return a.FunctionRva.CompareTo(
                                b.FunctionRva);
                        });

                    var limit = Math.Min(3, ranked.Count);
                    for (var i = 0; i < limit; i++)
                    {
                        var node = ranked[i];
                        sb.AppendLine(
                            "EXACT_TARGET=" + (i + 1) +
                            " FUNCTION_RVA=0x" + node.FunctionRva.ToString("X8") +
                            " TRIGGER_RVA=0x" + node.TriggerRva.ToString("X8") +
                            " DEPTH=" + node.Depth +
                            " STRONG_0x5E=" + (node.StrongOpcode5EMarker ? 1 : 0) +
                            " MARKER=" + (node.MarkerKind ?? "") +
                            " SOURCE=" + (node.Source ?? ""));
                    }
                }

                sb.AppendLine();
                File.AppendAllText(path, sb.ToString(), new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
                _probe.Dispose();

            base.Dispose(disposing);
        }
    }
}
