using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class NativeCallGraphControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe =
            new RuntimeMemoryProbe();

        private NumericUpDown _depth;
        private Button _scan;
        private Button _clear;
        private Label _status;
        private ListView _functions;
        private ListView _edges;

        public NativeCallGraphControl(string appDir)
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
                Height = 82,
                Padding = new Padding(8)
            };
            Controls.Add(top);

            top.Controls.Add(new Label
            {
                Text = "追蹤深度",
                Left = 12,
                Top = 15,
                Width = 60
            });

            _depth = new NumericUpDown
            {
                Left = 78,
                Top = 11,
                Width = 60,
                Minimum = 1,
                Maximum = 5,
                Value = 3
            };
            top.Controls.Add(_depth);

            _scan = new Button
            {
                Text = "追蹤 Send Caller",
                Left = 154,
                Top = 9,
                Width = 125
            };
            _clear = new Button
            {
                Text = "清除",
                Left = 288,
                Top = 9,
                Width = 75
            };
            top.Controls.Add(_scan);
            top.Controls.Add(_clear);

            _status = new Label
            {
                Left = 380,
                Top = 14,
                Width = 350,
                Height = 48,
                Text = "從 send/WSASend xref 往上追相對 CALL caller。0x5E 只做弱候選標記。"
            };
            top.Controls.Add(_status);

            var split = new SplitContainer
            {
                Dock = DockStyle.Fill,
                Orientation = Orientation.Horizontal,
                SplitterDistance = 210
            };
            Controls.Add(split);
            split.BringToFront();

            _functions = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _functions.Columns.Add("Depth", 55);
            _functions.Columns.Add("Function RVA", 110);
            _functions.Columns.Add("Trigger RVA", 110);
            _functions.Columns.Add("來源", 220);
            _functions.Columns.Add("0x5E 標記", 150);
            _functions.Columns.Add("SHA256(64B)", 150);
            split.Panel1.Controls.Add(_functions);

            _edges = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };
            _edges.Columns.Add("Depth", 55);
            _edges.Columns.Add("Caller Function", 120);
            _edges.Columns.Add("CALL RVA", 110);
            _edges.Columns.Add("Target Function", 120);
            _edges.Columns.Add("0x5E 標記", 160);
            split.Panel2.Controls.Add(_edges);

            _scan.Click += delegate { Scan(); };
            _clear.Click += delegate
            {
                _functions.Items.Clear();
                _edges.Items.Clear();
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
            if (!_probe.Attach(
                runtime.ProcessId,
                out error))
            {
                _status.Text = "程序連接失敗：" + error;
                return;
            }

            string xrefStatus;
            var xrefs = NativeSendXrefScanner.Scan(
                _probe,
                runtime,
                image,
                out xrefStatus);

            if (xrefs.Count == 0)
            {
                _status.Text = xrefStatus;
                return;
            }

            var graph = NativeCallGraphScanner.Build(
                _probe,
                runtime,
                image,
                xrefs,
                (int)_depth.Value);

            ShowGraph(graph, runtime);
            _status.Text = graph.Status;
            SaveEvidence(runtime, xrefs, graph);
        }

        private void ShowGraph(
            NativeCallGraphResult graph,
            RuntimeSnapshot runtime)
        {
            _functions.BeginUpdate();
            _functions.Items.Clear();

            foreach (var node in graph.Functions)
            {
                var fingerprint = NativeCodeWindow.Read(
                    _probe,
                    runtime,
                    node.FunctionRva,
                    64);

                _functions.Items.Add(
                    new ListViewItem(new[]
                    {
                        node.Depth.ToString(),
                        "0x" + node.FunctionRva.ToString("X8"),
                        "0x" + node.TriggerRva.ToString("X8"),
                        node.Source,
                        node.StrongOpcode5EMarker
                            ? node.MarkerKind
                            : "",
                        ShortHash(fingerprint.Sha256)
                    }));
            }

            _functions.EndUpdate();

            _edges.BeginUpdate();
            _edges.Items.Clear();

            foreach (var edge in graph.Edges)
            {
                _edges.Items.Add(
                    new ListViewItem(new[]
                    {
                        edge.Depth.ToString(),
                        "0x" + edge.CallerFunctionRva.ToString("X8"),
                        "0x" + edge.CallerInstructionRva.ToString("X8"),
                        "0x" + edge.TargetFunctionRva.ToString("X8"),
                        edge.CallerStrongOpcode5EMarker
                            ? edge.MarkerKind
                            : ""
                    }));
            }

            _edges.EndUpdate();
        }

        private void SaveEvidence(
            RuntimeSnapshot runtime,
            IList<NativeImportXref> roots,
            NativeCallGraphResult graph)
        {
            try
            {
                var path = Path.Combine(
                    _appDir,
                    "native_call_graph_evidence.txt");

                var sb = new StringBuilder();

                sb.AppendLine(
                    "TIME=" +
                    DateTime.Now.ToString(
                        "yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine(
                    "PID=" +
                    runtime.ProcessId);
                sb.AppendLine(
                    "MODULE_BASE=0x" +
                    runtime.ModuleBase.ToInt64().ToString("X8"));
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
                    "ROOT_SEND_XREFS=" +
                    roots.Count);
                sb.AppendLine(
                    "FUNCTIONS=" +
                    graph.Functions.Count);
                sb.AppendLine(
                    "EDGES=" +
                    graph.Edges.Count);
                sb.AppendLine(
                    "MEMORY_WRITE=NO");
                sb.AppendLine(
                    "OPCODE_5E_MARKER=HEURISTIC_ONLY");
                sb.AppendLine();

                foreach (var node in graph.Functions)
                {
                    var fingerprint = NativeCodeWindow.Read(
                        _probe,
                        runtime,
                        node.FunctionRva,
                        64);

                    sb.AppendLine(
                        "FUNC depth=" + node.Depth +
                        " rva=0x" +
                        node.FunctionRva.ToString("X8") +
                        " trigger=0x" +
                        node.TriggerRva.ToString("X8") +
                        " marker=" +
                        (node.StrongOpcode5EMarker
                            ? node.MarkerKind
                            : "") +
                        " sha256_64=" +
                        fingerprint.Sha256 +
                        " bytes=" +
                        fingerprint.BytesRead +
                        " source=" +
                        node.Source);
                }

                sb.AppendLine();

                foreach (var edge in graph.Edges)
                {
                    sb.AppendLine(
                        "EDGE depth=" +
                        edge.Depth +
                        " caller_func=0x" +
                        edge.CallerFunctionRva.ToString("X8") +
                        " call_rva=0x" +
                        edge.CallerInstructionRva.ToString("X8") +
                        " target_func=0x" +
                        edge.TargetFunctionRva.ToString("X8") +
                        " marker=" +
                        (edge.CallerStrongOpcode5EMarker
                            ? edge.MarkerKind
                            : ""));
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


        private static string ShortHash(string value)
        {
            if (string.IsNullOrEmpty(value))
                return "";

            return value.Length <= 16
                ? value
                : value.Substring(0, 16);
        }

        protected override void Dispose(
            bool disposing)
        {
            if (disposing)
                _probe.Dispose();

            base.Dispose(disposing);
        }
    }
}
