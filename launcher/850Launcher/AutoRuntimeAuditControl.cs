using System;
using System.Diagnostics;
using System.IO;
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
        private readonly AutoHpMpCrossCheckProbe _crossCheck;
        private readonly AutoHpMpPointerDiscovery _pointerDiscovery;
        private readonly AutoParallelDiscovery _parallelDiscovery;
        private readonly AutoNetworkSurfaceDiscovery _networkSurface;
        private readonly AutoLoadedModuleNetworkDiscovery _loadedModuleNetwork;
        private readonly AutoAppDirNetworkDiscovery _appDirNetwork;
        private readonly AutoInventoryHistoryDiscovery _inventoryHistory;

        private int _pinnedPid;
        private DateTime? _pinnedStartUtc;
        private string _pinStatus = "UNPINNED";

        public AutoRuntimeAuditControl(string appDir)
        {
            _appDir = appDir;
            _bridge = new ProcessRuntimeBridge(appDir);
            _broadProbe = new AutoHpMpBroadProbe(appDir);
            _semanticRefiner = new AutoHpMpSemanticRefiner(appDir);
            _crossCheck = new AutoHpMpCrossCheckProbe(appDir);
            _pointerDiscovery = new AutoHpMpPointerDiscovery(appDir);
            _parallelDiscovery = new AutoParallelDiscovery(appDir);
            _networkSurface = new AutoNetworkSurfaceDiscovery(appDir);
            _loadedModuleNetwork = new AutoLoadedModuleNetworkDiscovery(appDir);
            _appDirNetwork = new AutoAppDirNetworkDiscovery(appDir);
            _inventoryHistory = new AutoInventoryHistoryDiscovery(appDir);
            Dock = DockStyle.Fill;

            _status = new Label
            {
                Dock = DockStyle.Top,
                Height = 48,
                Padding = new Padding(8),
                Text = "全自動稽核安全模式：只綁定單一 850 client；停用重複窄掃描。"
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

            _timer = new Timer { Interval = 1000 };
            _timer.Tick += delegate { TickAudit(); };
            Load += delegate { TickAudit(); _timer.Start(); };
        }

        private void TickAudit()
        {
            RuntimeSnapshot runtime = _bridge.Read();
            var probeAllowed = false;

            if (runtime.Connected && runtime.ClientHashAuthoritative)
            {
                if (_pinnedPid == 0 || !PinnedProcessStillAlive())
                {
                    Pin(runtime);
                }

                if (runtime.ProcessId == _pinnedPid && SamePinnedStart(runtime))
                {
                    probeAllowed = true;
                    _pinStatus = "PINNED";
                }
                else
                {
                    _pinStatus = "HOLD_OTHER_PID current=" + runtime.ProcessId;
                }
            }
            else if (_pinnedPid != 0 && !PinnedProcessStillAlive())
            {
                _pinStatus = "PINNED_PROCESS_EXITED";
                _pinnedPid = 0;
                _pinnedStartUtc = null;
            }

            if (probeAllowed)
            {
                _broadProbe.EnsureRunning(runtime);
                _semanticRefiner.EnsureRunning(runtime);
                _crossCheck.EnsureRunning(runtime);
                _pointerDiscovery.EnsureRunning(runtime);
                _parallelDiscovery.EnsureRunning(runtime);
                _networkSurface.EnsureRunning(runtime);
                _loadedModuleNetwork.EnsureRunning(runtime);
                _appDirNetwork.EnsureRunning(runtime);
                _inventoryHistory.EnsureRunning(runtime);
            }

            var dashboard = RuntimeValidationDashboard.Evaluate(_appDir);
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=FULL_AUTO_PARALLEL_SAFE_PIN");
            sb.AppendLine("CLIENT_CONNECTED=" + (runtime.Connected ? 1 : 0));
            sb.AppendLine("PID=" + runtime.ProcessId);
            sb.AppendLine("CLIENT_SHA256=" + (runtime.ClientSha256 ?? ""));
            sb.AppendLine("CLIENT_AUTHORITY=" + (runtime.ClientHashAuthoritative ? 1 : 0));
            sb.AppendLine("MODULE_BASE=0x" + runtime.ModuleBase.ToInt64().ToString("X8"));
            sb.AppendLine("MODULE_SIZE=" + runtime.ModuleSize);
            sb.AppendLine("RUNTIME_STATUS=" + (runtime.Status ?? ""));
            sb.AppendLine("AUDIT_PINNED_PID=" + _pinnedPid);
            sb.AppendLine("AUDIT_PINNED_START_UTC=" + (_pinnedStartUtc.HasValue ? _pinnedStartUtc.Value.ToString("o") : ""));
            sb.AppendLine("AUDIT_PIN_STATUS=" + _pinStatus);
            sb.AppendLine("AUTO_DYNAMIC_PROBE=DISABLED_REDUNDANT_SAFE_MODE");
            sb.AppendLine("AUTO_BROAD_HPMP=" + _broadProbe.Status);
            sb.AppendLine("AUTO_SEMANTIC_HPMP=" + _semanticRefiner.Status);
            sb.AppendLine("AUTO_HPMP_CROSSCHECK=" + _crossCheck.Status);
            sb.AppendLine("AUTO_HPMP_POINTER=" + _pointerDiscovery.Status);
            sb.AppendLine("AUTO_INVENTORY=" + _parallelDiscovery.InventoryStatus);
            sb.AppendLine("AUTO_INVENTORY_HISTORY=" + _inventoryHistory.Status);
            sb.AppendLine("AUTO_BUFF_RECV=" + _parallelDiscovery.BuffStatus);
            sb.AppendLine("AUTO_SEND=" + _parallelDiscovery.SendStatus);
            sb.AppendLine("AUTO_NETWORK_SURFACE=" + _networkSurface.Status);
            sb.AppendLine("AUTO_LOADED_MODULE_NETWORK=" + _loadedModuleNetwork.Status);
            sb.AppendLine("AUTO_APPDIR_NETWORK=" + _appDirNetwork.Status);
            sb.AppendLine();
            sb.AppendLine("[GATES]");
            foreach (var row in dashboard.Rows)
            {
                sb.AppendLine(row.WorkPackage + "=" + row.State + " | " + row.Evidence + " | NEXT=" + row.Next);
            }
            sb.AppendLine();
            AppendFile(sb, "runtime_dynamic_broad_probe_evidence.txt");
            AppendFile(sb, "runtime_hpmp_semantic_refine_evidence.txt");
            AppendFile(sb, "runtime_hpmp_crosscheck_evidence.txt");
            AppendFile(sb, "runtime_hpmp_crosscheck_history.txt");
            AppendFile(sb, "runtime_hpmp_pointer_evidence.txt");
            AppendFile(sb, "runtime_hpmp_pointer_history.txt");
            AppendFile(sb, "auto_inventory_discovery_evidence.txt");
            AppendFile(sb, "auto_inventory_history_evidence.txt");
            AppendFile(sb, "auto_buff_receive_evidence.txt");
            AppendFile(sb, "auto_send_discovery_evidence.txt");
            AppendFile(sb, "auto_network_surface_evidence.txt");
            AppendFile(sb, "auto_loaded_module_network_evidence.txt");
            AppendFile(sb, "auto_appdir_network_evidence.txt");
            AppendFile(sb, "runtime_probe_evidence.txt");
            AppendFile(sb, "inventory_probe_evidence.txt");
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
            _status.Text = probeAllowed
                ? "全自動稽核安全模式：已固定 PID=" + _pinnedPid + "；只執行必要採集。"
                : runtime.Connected
                    ? "全自動稽核安全模式：偵測到其他 Lin.bin2 PID，暫停附加採集。"
                    : "全自動稽核安全模式：等待 850 client。";

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

        private void Pin(RuntimeSnapshot runtime)
        {
            _pinnedPid = runtime.ProcessId;
            _pinnedStartUtc = runtime.ProcessStartTimeUtc;
            _pinStatus = "PINNED_NEW";
        }

        private bool SamePinnedStart(RuntimeSnapshot runtime)
        {
            if (!_pinnedStartUtc.HasValue || !runtime.ProcessStartTimeUtc.HasValue)
                return true;

            return _pinnedStartUtc.Value == runtime.ProcessStartTimeUtc.Value;
        }

        private bool PinnedProcessStillAlive()
        {
            if (_pinnedPid <= 0)
                return false;

            try
            {
                var process = Process.GetProcessById(_pinnedPid);
                if (process.HasExited)
                    return false;

                if (_pinnedStartUtc.HasValue)
                {
                    DateTime actualStart;
                    try
                    {
                        actualStart = process.StartTime.ToUniversalTime();
                    }
                    catch
                    {
                        actualStart = DateTime.MinValue;
                    }

                    if (actualStart != DateTime.MinValue && actualStart != _pinnedStartUtc.Value)
                        return false;
                }

                return true;
            }
            catch
            {
                return false;
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
