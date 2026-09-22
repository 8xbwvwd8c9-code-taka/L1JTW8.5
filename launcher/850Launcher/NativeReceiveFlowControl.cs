using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class NativeReceiveFlowControl :
        UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly RuntimeMemoryProbe _probe =
            new RuntimeMemoryProbe();

        private NumericUpDown _depth;
        private Button _scan;
        private Button _clear;
        private Label _status;
        private ListView _results;

        public NativeReceiveFlowControl(
            string appDir)
        {
            _appDir = appDir;
            _bridge =
                new ProcessRuntimeBridge(
                    appDir);

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
                Text = "追蹤 Recv Flow",
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
                Text =
                    "從 recv/WSARecv xref 往 direct callees 追。Buff opcode 只做弱標記。"
            };

            top.Controls.Add(_status);

            _results = new ListView
            {
                Dock = DockStyle.Fill,
                View = View.Details,
                FullRowSelect = true,
                GridLines = true
            };

            _results.Columns.Add(
                "Depth",
                55);

            _results.Columns.Add(
                "Function RVA",
                115);

            _results.Columns.Add(
                "Parent RVA",
                110);

            _results.Columns.Add(
                "CALL/Xref RVA",
                115);

            _results.Columns.Add(
                "Buff opcode 標記",
                190);

            _results.Columns.Add(
                "SHA256(64B)",
                150);

            Controls.Add(_results);
            _results.BringToFront();

            _scan.Click +=
                delegate
                {
                    Scan();
                };

            _clear.Click +=
                delegate
                {
                    _results.Items.Clear();
                    _status.Text =
                        "結果已清除。";
                };
        }

        private void Scan()
        {
            var runtime =
                _bridge.Read();

            if (!runtime.Connected)
            {
                MessageBox.Show(
                    runtime.Status,
                    "尚未連接遊戲",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Information);

                return;
            }

            var path =
                Path.Combine(
                    _appDir,
                    "Lin.bin2");

            if (!File.Exists(path))
            {
                _status.Text =
                    "找不到 Lin.bin2。";

                return;
            }

            PeImageInfo image;

            try
            {
                image =
                    PeImportParser.Parse(
                        path);
            }
            catch (Exception ex)
            {
                _status.Text =
                    "PE 解析失敗：" +
                    ex.Message;

                return;
            }

            string error;

            if (!_probe.Attach(
                runtime.ProcessId,
                out error))
            {
                _status.Text =
                    "程序連接失敗：" +
                    error;

                return;
            }

            string rootStatus;

            var roots =
                NativeReceiveXrefScanner.Scan(
                    _probe,
                    runtime,
                    image,
                    out rootStatus);

            if (roots.Count == 0)
            {
                _status.Text =
                    rootStatus;

                return;
            }

            var flow =
                NativeReceiveFlowScanner.Build(
                    _probe,
                    runtime,
                    image,
                    roots,
                    (int)_depth.Value);

            ShowResults(
                runtime,
                flow);

            _status.Text =
                rootStatus +
                " " +
                flow.Status;

            SaveEvidence(
                runtime,
                roots,
                flow);
        }

        private void ShowResults(
            RuntimeSnapshot runtime,
            NativeReceiveFlowResult flow)
        {
            _results.BeginUpdate();
            _results.Items.Clear();

            foreach (var node in flow.Nodes)
            {
                var fingerprint =
                    NativeCodeWindow.Read(
                        _probe,
                        runtime,
                        node.FunctionRva,
                        64);

                _results.Items.Add(
                    new ListViewItem(
                        new[]
                        {
                            node.Depth.ToString(),

                            "0x" +
                            node.FunctionRva
                                .ToString("X8"),

                            node.ParentFunctionRva == 0
                                ? ""
                                : "0x" +
                                  node.ParentFunctionRva
                                      .ToString("X8"),

                            "0x" +
                            node.TriggerCallRva
                                .ToString("X8"),

                            node.BuffOpcodeMarker,

                            ShortHash(
                                fingerprint.Sha256)
                        }));
            }

            _results.EndUpdate();
        }

        private void SaveEvidence(
            RuntimeSnapshot runtime,
            IList<NativeImportXref> roots,
            NativeReceiveFlowResult flow)
        {
            try
            {
                var path =
                    Path.Combine(
                        _appDir,
                        "native_receive_flow_evidence.txt");

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
                    "MODULE_BASE=0x" +
                    runtime.ModuleBase.ToInt64()
                        .ToString("X8"));

                sb.AppendLine(
                    "RECV_ROOT_XREFS=" +
                    roots.Count);

                sb.AppendLine(
                    "FLOW_NODES=" +
                    flow.Nodes.Count);

                sb.AppendLine(
                    "MEMORY_WRITE=NO");

                sb.AppendLine(
                    "BUFF_OPCODE_MARKER=HEURISTIC_ONLY");

                sb.AppendLine();

                foreach (var root in roots)
                {
                    sb.AppendLine(
                        "ROOT import=" +
                        root.ImportName +
                        " iat_rva=0x" +
                        root.IatRva
                            .ToString("X8") +
                        " call_rva=0x" +
                        root.InstructionRva
                            .ToString("X8") +
                        " kind=" +
                        root.Kind);
                }

                sb.AppendLine();

                foreach (var node in flow.Nodes)
                {
                    var fingerprint =
                        NativeCodeWindow.Read(
                            _probe,
                            runtime,
                            node.FunctionRva,
                            64);

                    sb.AppendLine(
                        "NODE depth=" +
                        node.Depth +
                        " func_rva=0x" +
                        node.FunctionRva
                            .ToString("X8") +
                        " parent_rva=0x" +
                        node.ParentFunctionRva
                            .ToString("X8") +
                        " trigger_rva=0x" +
                        node.TriggerCallRva
                            .ToString("X8") +
                        " marker=" +
                        node.BuffOpcodeMarker +
                        " sha256_64=" +
                        fingerprint.Sha256 +
                        " source=" +
                        node.Source);
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

        private static string ShortHash(
            string value)
        {
            if (string.IsNullOrEmpty(value))
                return "";

            return value.Length <= 16
                ? value
                : value.Substring(
                    0,
                    16);
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
