using System;
using System.IO;
using System.Reflection;
using System.Text;
using System.Windows.Forms;

namespace L1JTW850Launcher
{
    internal sealed class AutoRuntimeAuditControl : UserControl
    {
        private readonly string _appDir;
        private readonly ProcessRuntimeBridge _bridge;
        private readonly Timer _timer;
        private readonly TextBox _report;
        private readonly Label _status;
        private readonly AutoHpMpBroadProbe _broadProbe;
        private readonly AutoHpMpSemanticRefiner _semanticRefiner;
        private RuntimeDynamicProbeControl _dynamicProbe;
        private int _probePid;

        public AutoRuntimeAuditControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            _broadProbe = new AutoHpMpBroadProbe(appDir);
            _semanticRefiner = new AutoHpMpSemanticRefiner(appDir);
            Dock = DockStyle.Fill;

            _status = new Label
            {
                Dock = DockStyle.Top,
                Height = 48,
                Padding = new Padding(8),
                Text = "全自動稽核：等待 850 client；不需要操作開發者分頁。"
            };
            Controls.Add(_status);

            _report = new TextBox
            {
                Dock = DockStyle.Fill,
                Multiline = true,
                ReadOnly = true,
                ScrollBars = ScrollBars.Both,
                WordWrap = false
            };
            Controls.Add(_report);
            _report.BringToFront();

            _dynamicProbe = new RuntimeDynamicProbeControl(appDir);
            _dynamicProbe.Visible = false;
            Controls.Add(_dynamicProbe);

            _timer = new Timer { Interval = 1000 };
            _timer.Tick += delegate { TickAudit(); };
            Load += delegate { TickAudit(); _timer.Start(); };
        }

        private void TickAudit()
        {
            RuntimeSnapshot runtime = _bridge.Read();
            if (runtime.Connected && runtime.ClientHashAuthoritative)
            {
                _broadProbe.EnsureRunning(runtime);
                _semanticRefiner.EnsureRunning(runtime);

                if (runtime.ProcessId != _probePid)
                {
                    _probePid = runtime.ProcessId;
                    StartDynamicProbe();
                }
            }

            var dashboard = RuntimeValidationDashboard.Evaluate(_appDir);
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=FULL_AUTO");
            sb.AppendLine("CLIENT_CONNECTED=" + (runtime.Connected ? 1 : 0));
            sb.AppendLine("PID=" + runtime.ProcessId);
            sb.AppendLine("CLIENT_SHA256=" + (runtime.ClientSha256 ?? ""));
            sb.AppendLine("CLIENT_AUTHORITY=" + (runtime.ClientHashAuthoritative ? 1 : 0));
            sb.AppendLine("MODULE_BASE=0x" + runtime.ModuleBase.ToInt64().ToString("X8"));
            sb.AppendLine("MODULE_SIZE=" + runtime.ModuleSize);
            sb.AppendLine("RUNTIME_STATUS=" + (runtime.Status ?? ""));
            sb.AppendLine("AUTO_DYNAMIC_PROBE=" + (_probePid == runtime.ProcessId && _probePid != 0 ? "STARTED" : "WAITING"));
            sb.AppendLine("AUTO_BROAD_HPMP=" + _broadProbe.Status);
            sb.AppendLine("AUTO_SEMANTIC_HPMP=" + _semanticRefiner.Status);
            sb.AppendLine();
            sb.AppendLine("[GATES]");
            foreach (var row in dashboard.Rows)
            {
                sb.AppendLine(row.WorkPackage + "=" + row.State + " | " + row.Evidence + " | NEXT=" + row.Next);
            }
            sb.AppendLine();
            AppendFile(sb, "runtime_dynamic_broad_probe_evidence.txt");
            AppendFile(sb, "runtime_hpmp_semantic_refine_evidence.txt");
            AppendFile(sb, "runtime_dynamic_probe_evidence.txt");
            AppendFile(sb, "runtime_probe_evidence.txt");
            AppendFile(sb, "pointer_probe_evidence.txt");
            AppendFile(sb, "runtime_semantic_validation_evidence.txt");
            AppendFile(sb, "inventory_validation_evidence.txt");
            AppendFile(sb, "runtime-map.ini");
            AppendFile(sb, "inventory-map.ini");
            sb.AppendLine();
            sb.AppendLine("NEXT=" + dashboard.NextAction);
            sb.AppendLine("MEMORY_WRITE=NO");

            var text = sb.ToString();
            _report.Text = text;
            _status.Text = runtime.Connected
                ? "全自動稽核執行中；背景會自動縮小 HP/MP 候選並更新報告。"
                : "全自動稽核：等待 850 client。";

            try
            {
                File.WriteAllText(
                    Path.Combine(_appDir, "auto_runtime_audit_report.txt"),
                    text,
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }

        private void StartDynamicProbe()
        {
            try
            {
                var method = typeof(RuntimeDynamicProbeControl).GetMethod(
                    "BeginSample",
                    BindingFlags.Instance | BindingFlags.NonPublic);
                if (method != null)
                    method.Invoke(_dynamicProbe, null);
            }
            catch
            {
            }
        }

        private void AppendFile(StringBuilder sb, string name)
        {
            var path = Path.Combine(_appDir, name);
            if (!File.Exists(path))
            {
                sb.AppendLine(name + "=MISSING");
                return;
            }
            try
            {
                var info = new FileInfo(path);
                sb.AppendLine(name + "=PRESENT | SIZE=" + info.Length + " | UPDATED=" + info.LastWriteTime.ToString("yyyy-MM-dd HH:mm:ss"));
            }
            catch
            {
                sb.AppendLine(name + "=PRESENT");
            }
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing && _timer != null) _timer.Dispose();
            base.Dispose(disposing);
        }
    }
}
