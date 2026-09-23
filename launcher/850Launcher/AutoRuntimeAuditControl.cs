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
        private readonly AutoHpMpEvidenceGuard _hpmpGuard;
        private readonly AutoParallelDiscovery _parallelDiscovery;
        private readonly AutoNetworkSurfaceDiscovery _networkSurface;
        private readonly AutoLoadedModuleNetworkDiscovery _loadedModuleNetwork;
        private readonly AutoAppDirNetworkDiscovery _appDirNetwork;
        private readonly AutoStaticGameNetworkDiscovery _staticGameNetwork;
        private readonly AutoDamageReceiveDiscovery _damageReceive;
        private readonly AutoInventoryHistoryDiscovery _inventoryHistory;
        private readonly DateTime _auditStartedUtc;

        private int _pinnedPid;
        private DateTime? _pinnedStartUtc;
        private string _pinStatus = "UNPINNED";
        private string _hpmpPipeline = "WAIT_BROAD";

        public AutoRuntimeAuditControl(string appDir)
        {
            _appDir = appDir;
            _auditStartedUtc = DateTime.UtcNow;
            _bridge = new ProcessRuntimeBridge(appDir);
            _broadProbe = new AutoHpMpBroadProbe(appDir);
            _semanticRefiner = new AutoHpMpSemanticRefiner(appDir);
            _crossCheck = new AutoHpMpCrossCheckProbe(appDir);
            _pointerDiscovery = new AutoHpMpPointerDiscovery(appDir);
            _hpmpGuard = new AutoHpMpEvidenceGuard(appDir);
            _parallelDiscovery = new AutoParallelDiscovery(appDir);
            _networkSurface = new AutoNetworkSurfaceDiscovery(appDir);
            _loadedModuleNetwork = new AutoLoadedModuleNetworkDiscovery(appDir);
            _appDirNetwork = new AutoAppDirNetworkDiscovery(appDir);
            _staticGameNetwork = new AutoStaticGameNetworkDiscovery(appDir);
            _damageReceive = new AutoDamageReceiveDiscovery(appDir);
            _inventoryHistory = new AutoInventoryHistoryDiscovery(appDir);
            Dock = DockStyle.Fill;

            _status = new Label
            {
                Dock = DockStyle.Top,
                Height = 48,
                Padding = new Padding(8),
                Text = "全自動稽核安全模式：只綁定單一 850 client；HP/MP 採序列證據鏈。"
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
                RunHpMpPipeline(runtime);

                // Independent read-only lanes may run in parallel.
                _parallelDiscovery.EnsureRunning(runtime);
                _networkSurface.EnsureRunning(runtime);
                _loadedModuleNetwork.EnsureRunning(runtime);
                _appDirNetwork.EnsureRunning(runtime);
                _staticGameNetwork.EnsureRunning(runtime);

                // GM_AttackMessage was proven as the source of the observed damage text,
                // so the old generic damage-receive hunt is intentionally no longer started.
                // Keep the class/evidence available for historical comparison only.

                // This lane owns the seedless private-writable inventory scanner internally,
                // then refines fresh dynamic candidates. Do not start a duplicate scanner here.
                _inventoryHistory.EnsureRunning(runtime);
            }

            AutoFeatureMatrix.Refresh(_appDir);

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
            sb.AppendLine("HPMP_PIPELINE=" + _hpmpPipeline);
            sb.AppendLine("AUTO_DYNAMIC_PROBE=DISABLED_REDUNDANT_SAFE_MODE");
            sb.AppendLine("AUTO_BROAD_HPMP=" + _broadProbe.Status);
            sb.AppendLine("AUTO_SEMANTIC_HPMP=" + _semanticRefiner.Status);
            sb.AppendLine("AUTO_HPMP_CROSSCHECK=" + _crossCheck.Status);
            sb.AppendLine("AUTO_HPMP_POINTER=" + _pointerDiscovery.Status);
            sb.AppendLine("AUTO_HPMP_GUARD=" + _hpmpGuard.Status);
            sb.AppendLine("AUTO_INVENTORY=" + _parallelDiscovery.InventoryStatus);
            sb.AppendLine("AUTO_INVENTORY_HISTORY=" + _inventoryHistory.Status);
            sb.AppendLine("AUTO_BUFF_RECV=" + _parallelDiscovery.BuffStatus);
            sb.AppendLine("AUTO_SEND=" + _parallelDiscovery.SendStatus);
            sb.AppendLine("AUTO_NETWORK_SURFACE=" + _networkSurface.Status);
            sb.AppendLine("AUTO_LOADED_MODULE_NETWORK=" + _loadedModuleNetwork.Status);
            sb.AppendLine("AUTO_APPDIR_NETWORK=" + _appDirNetwork.Status);
            sb.AppendLine("AUTO_STATIC_GAME_NETWORK=" + _staticGameNetwork.Status);
            sb.AppendLine("AUTO_DAMAGE_RECV=DISABLED_ROOT_CAUSE_GM_ATTACK_MESSAGE");
            sb.AppendLine("AUTO_FEATURE_MATRIX=ACTIVE");
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
            AppendFile(sb, "runtime_hpmp_guard_evidence.txt");
            AppendFile(sb, "auto_inventory_discovery_evidence.txt");
            AppendFile(sb, "auto_inventory_seedless_evidence.txt");
            AppendFile(sb, "auto_inventory_history_evidence.txt");
            AppendFile(sb, "auto_buff_receive_evidence.txt");
            AppendFile(sb, "auto_send_discovery_evidence.txt");
            AppendFile(sb, "auto_network_surface_evidence.txt");
            AppendFile(sb, "auto_loaded_module_network_evidence.txt");
            AppendFile(sb, "auto_appdir_network_evidence.txt");
            AppendFile(sb, "auto_static_game_network_evidence.txt");
            AppendFile(sb, "auto_damage_receive_evidence.txt");
            AppendFile(sb, "auto_damage_accounting_evidence.txt");
            AppendFile(sb, "auto_feature_matrix_evidence.txt");
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
                ? "全自動稽核安全模式：PID=" + _pinnedPid + "；HP/MP=" + _hpmpPipeline
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

        private void RunHpMpPipeline(RuntimeSnapshot runtime)
        {
            _broadProbe.EnsureRunning(runtime);

            var broadPath = Path.Combine(_appDir, "runtime_dynamic_broad_probe_evidence.txt");
            var semanticPath = Path.Combine(_appDir, "runtime_hpmp_semantic_refine_evidence.txt");
            var crossPath = Path.Combine(_appDir, "runtime_hpmp_crosscheck_evidence.txt");
            var pointerPath = Path.Combine(_appDir, "runtime_hpmp_pointer_evidence.txt");

            if (!EvidenceReady(broadPath, runtime.ProcessId, _auditStartedUtc))
            {
                _hpmpPipeline = "WAIT_BROAD_CURRENT_RUN";
                return;
            }

            _semanticRefiner.EnsureRunning(runtime);
            var broadTime = SafeWriteTimeUtc(broadPath);
            if (!EvidenceReady(semanticPath, runtime.ProcessId, broadTime))
            {
                _hpmpPipeline = "WAIT_SEMANTIC_AFTER_BROAD";
                return;
            }

            _crossCheck.EnsureRunning(runtime);
            if (string.Equals(
                _crossCheck.Status,
                "RUNNING",
                StringComparison.OrdinalIgnoreCase))
            {
                _hpmpPipeline = "WAIT_CROSSCHECK_RUNNING";
                return;
            }

            var semanticTime = SafeWriteTimeUtc(semanticPath);
            if (!EvidenceReady(crossPath, runtime.ProcessId, semanticTime))
            {
                _hpmpPipeline = "WAIT_CROSSCHECK_AFTER_SEMANTIC";
                return;
            }

            _pointerDiscovery.EnsureRunning(runtime);
            if (string.Equals(
                _pointerDiscovery.Status,
                "RUNNING",
                StringComparison.OrdinalIgnoreCase))
            {
                _hpmpPipeline = "WAIT_POINTER_RUNNING";
                return;
            }

            var crossTime = SafeWriteTimeUtc(crossPath);
            if (!EvidenceReady(pointerPath, runtime.ProcessId, crossTime))
            {
                _hpmpPipeline = "WAIT_POINTER_AFTER_CROSSCHECK";
                return;
            }

            // Only classify when the pointer evidence belongs to this exact cross-check run.
            // This prevents a fast guard tick from consuming the prior pointer file.
            _hpmpGuard.EnsureRunning(runtime);
            _hpmpPipeline = "POINTER_GATE_READY";
        }

        private static DateTime SafeWriteTimeUtc(string path)
        {
            try
            {
                return File.Exists(path) ? File.GetLastWriteTimeUtc(path) : DateTime.MaxValue;
            }
            catch
            {
                return DateTime.MaxValue;
            }
        }

        private static bool EvidenceReady(string path, int expectedPid, DateTime notBeforeUtc)
        {
            try
            {
                if (!File.Exists(path)) return false;
                var writeUtc = File.GetLastWriteTimeUtc(path);
                if (writeUtc < notBeforeUtc) return false;

                var lines = File.ReadAllLines(path);
                for (var i = 0; i < Math.Min(16, lines.Length); i++)
                {
                    var line = lines[i].Trim();
                    if (!line.StartsWith("PID=", StringComparison.OrdinalIgnoreCase)) continue;
                    int pid;
                    return int.TryParse(line.Substring(4), out pid) && pid == expectedPid;
                }
                return false;
            }
            catch
            {
                return false;
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
