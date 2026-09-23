using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoLoadedModuleNetworkDiscovery
    {
        private sealed class ModuleHit
        {
            public string ModuleName = "";
            public string Path = "";
            public long Base;
            public int Size;
            public readonly List<PeImportEntry> Imports = new List<PeImportEntry>();
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private bool _done;
        private string _status = "WAITING";

        public AutoLoadedModuleNetworkDiscovery(string appDir)
        {
            _appDir = appDir;
        }

        public string Status
        {
            get { lock (_sync) return _running ? "RUNNING" : _status; }
        }

        public void EnsureRunning(RuntimeSnapshot runtime)
        {
            if (runtime == null || !runtime.Connected || !runtime.ClientHashAuthoritative || runtime.ProcessId <= 0)
                return;

            lock (_sync)
            {
                if (_pid != runtime.ProcessId)
                {
                    _pid = runtime.ProcessId;
                    _running = false;
                    _done = false;
                    _status = "READY";
                }

                if (_running || _done) return;
                _running = true;
            }

            ThreadPool.QueueUserWorkItem(delegate
            {
                try
                {
                    Run(runtime);
                }
                catch (Exception ex)
                {
                    SaveError(runtime, ex);
                    lock (_sync) _status = "ERROR";
                }
                finally
                {
                    lock (_sync)
                    {
                        _running = false;
                        _done = true;
                    }
                }
            });
        }

        private void Run(RuntimeSnapshot runtime)
        {
            var loaded = new List<ModuleHit>();
            var owners = new List<ModuleHit>();
            var failures = new List<string>();
            var winsockLoaded = false;

            using (var process = Process.GetProcessById(runtime.ProcessId))
            {
                foreach (ProcessModule module in process.Modules)
                {
                    var hit = new ModuleHit();
                    try
                    {
                        hit.ModuleName = module.ModuleName ?? "";
                        hit.Path = module.FileName ?? "";
                        hit.Base = module.BaseAddress.ToInt64();
                        hit.Size = module.ModuleMemorySize;
                    }
                    catch (Exception ex)
                    {
                        failures.Add("MODULE_METADATA_ERROR=" + ex.GetType().Name + ":" + ex.Message);
                        continue;
                    }

                    loaded.Add(hit);
                    var lowerName = hit.ModuleName.ToLowerInvariant();
                    if (lowerName == "ws2_32.dll" || lowerName == "wsock32.dll")
                        winsockLoaded = true;

                    if (string.IsNullOrEmpty(hit.Path) || !File.Exists(hit.Path))
                        continue;

                    try
                    {
                        var image = PeImportParser.Parse(hit.Path);
                        foreach (var entry in image.Imports)
                        {
                            if (IsNetworkImport(entry))
                                hit.Imports.Add(entry);
                        }
                        if (hit.Imports.Count > 0)
                            owners.Add(hit);
                    }
                    catch (Exception ex)
                    {
                        // Managed/system/non-PE32 modules or access-restricted files are not fatal.
                        if (failures.Count < 40)
                            failures.Add(
                                "PARSE_SKIP MODULE=" + Sanitize(hit.ModuleName) +
                                " ERROR=" + ex.GetType().Name + ":" + Sanitize(ex.Message));
                    }
                }
            }

            var sb = Header(runtime, "AUTO_LOADED_MODULE_NETWORK_DISCOVERY");
            sb.AppendLine("LOADED_MODULES=" + loaded.Count);
            sb.AppendLine("WINSOCK_MODULE_LOADED=" + (winsockLoaded ? 1 : 0));
            sb.AppendLine("NETWORK_IMPORT_OWNER_MODULES=" + owners.Count);
            sb.AppendLine("PARSE_SKIPS=" + failures.Count);
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            sb.AppendLine("[LOADED_MODULES]");
            foreach (var module in loaded)
            {
                sb.AppendLine(
                    "MODULE=" + Sanitize(module.ModuleName) +
                    " BASE=0x" + module.Base.ToString("X8") +
                    " SIZE=" + module.Size +
                    " PATH=" + Sanitize(module.Path));
            }
            sb.AppendLine();

            sb.AppendLine("[NETWORK_IMPORT_OWNERS]");
            foreach (var owner in owners)
            {
                sb.AppendLine(
                    "OWNER=" + Sanitize(owner.ModuleName) +
                    " BASE=0x" + owner.Base.ToString("X8") +
                    " PATH=" + Sanitize(owner.Path) +
                    " IMPORTS=" + owner.Imports.Count);

                foreach (var entry in owner.Imports)
                {
                    sb.AppendLine(
                        "  DLL=" + Sanitize(entry.Dll) +
                        " NAME=" + Sanitize(entry.Name) +
                        " ORDINAL=" + (entry.ByOrdinal ? entry.Ordinal.ToString() : "") +
                        " IAT_RVA=0x" + entry.IatRva.ToString("X8"));
                }
            }
            sb.AppendLine();

            if (failures.Count > 0)
            {
                sb.AppendLine("[PARSE_SKIPS]");
                for (var i = 0; i < Math.Min(40, failures.Count); i++)
                    sb.AppendLine(failures[i]);
                sb.AppendLine();
            }

            string status;
            if (owners.Count > 0)
                status = "PASS_MODULE_NETWORK_OWNERS count=" + owners.Count;
            else if (winsockLoaded)
                status = "WINSOCK_LOADED_OWNER_UNKNOWN";
            else
                status = "NO_WINSOCK_MODULE_VISIBLE";

            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(_appDir, "auto_loaded_module_network_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));

            lock (_sync) _status = status;
        }

        private static bool IsNetworkImport(PeImportEntry entry)
        {
            if (entry == null) return false;
            var dll = (entry.Dll ?? "").ToLowerInvariant();
            var name = (entry.Name ?? "").ToLowerInvariant();

            if (dll.Contains("ws2_32") || dll.Contains("wsock32") ||
                dll.Contains("wininet") || dll.Contains("winhttp"))
                return true;

            if (name == "send" || name == "sendto" || name == "recv" || name == "recvfrom" ||
                name == "socket" || name == "connect" || name == "select" ||
                name == "ioctlsocket" || name == "closesocket" || name == "shutdown" ||
                name == "gethostbyname" || name == "inet_addr" || name == "htons" ||
                name == "ntohs" || name.StartsWith("wsa"))
                return true;

            return false;
        }

        private static string Sanitize(string value)
        {
            if (string.IsNullOrEmpty(value)) return "";
            return value.Replace("\r", " ").Replace("\n", " ").Replace("|", "/");
        }

        private static StringBuilder Header(RuntimeSnapshot runtime, string mode)
        {
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=" + mode);
            sb.AppendLine("PID=" + runtime.ProcessId);
            sb.AppendLine("PROCESS_START_UTC=" + (runtime.ProcessStartTimeUtc.HasValue ? runtime.ProcessStartTimeUtc.Value.ToString("o") : ""));
            sb.AppendLine("CLIENT_SHA256=" + (runtime.ClientSha256 ?? ""));
            sb.AppendLine("CLIENT_AUTHORITY=" + (runtime.ClientHashAuthoritative ? 1 : 0));
            sb.AppendLine("MODULE_BASE=0x" + runtime.ModuleBase.ToInt64().ToString("X8"));
            return sb;
        }

        private void SaveError(RuntimeSnapshot runtime, Exception ex)
        {
            try
            {
                var sb = Header(runtime, "AUTO_LOADED_MODULE_NETWORK_DISCOVERY");
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "auto_loaded_module_network_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
