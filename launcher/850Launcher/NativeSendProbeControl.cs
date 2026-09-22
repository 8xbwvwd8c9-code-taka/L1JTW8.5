using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class NativeSendProbeControl : UserControl
    {
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
                Text = "讀取 Lin.bin2 PE import table，並在實際載入模組中尋找 send/WSASend IAT 呼叫點。"
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

            ShowResults(xrefs);
            _status.Text = status;
            SaveEvidence(runtime, image, xrefs);
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
            IList<NativeImportXref> xrefs)
        {
            try
            {
                var path = Path.Combine(
                    _appDir,
                    "native_send_xref_evidence.txt");

                var sb = new StringBuilder();

                sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                sb.AppendLine("PID=" + runtime.ProcessId);
                sb.AppendLine("MODULE_BASE=0x" + runtime.ModuleBase.ToInt64().ToString("X8"));
                sb.AppendLine("PE_IMAGE_BASE=0x" + image.ImageBase.ToString("X8"));
                sb.AppendLine("PE_SIZE_OF_IMAGE=0x" + image.SizeOfImage.ToString("X8"));
                sb.AppendLine("XREFS=" + xrefs.Count);
                sb.AppendLine("MEMORY_WRITE=NO");
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
