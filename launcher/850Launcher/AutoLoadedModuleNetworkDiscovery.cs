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
            public PeImageInfo Image;
            public readonly List<PeImportEntry> Imports = new List<PeImportEntry>();
        }

        private sealed class ModuleXref
        {
            public ModuleHit Owner;
            public PeImportEntry Import;
            public uint InstructionRva;
            public long InstructionAddress;
            public string Kind = "";
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private bool _done;
        private DateTime _retryAfterUtc = DateTime.MinValue;
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
                    _retryAfterUtc = DateTime.MinValue;
                    _status = "READY";
                }

                if (_running) return;
                if (_done) return;
                if (DateTime.UtcNow < _retryAfterUtc) return;
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
                    lock (_sync)
                    {
                        _status = "ERROR_RETRY";
                        _retryAfterUtc = DateTime.UtcNow.AddSeconds(30);
                    }
                }
                finally
                {
                    lock (_sync)
                    {
                        _running = false;
                        if (_status == "NO_WINSOCK_MODULE_VISIBLE" ||
                            _status == "WINSOCK_LOADED_OWNER_UNKNOWN" ||
                            _status == "ERROR_RETRY")
                        {
                            _done = false;
                            if (_retryAfterUtc < DateTime.UtcNow)
                                _retryAfterUtc = DateTime.UtcNow.AddSeconds(15);
                        }
                        else
                        {
                            _done = true;
                        }
                    }
                }
            });
        }

        private void Run(RuntimeSnapshot runtime)
        {
            var loaded = new List<ModuleHit>();
            var owners = new List<ModuleHit>();
            var failures = new List<string>();
            var xrefs = new List<ModuleXref>();
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
                        hit.Image = PeImportParser.Parse(hit.Path);
                        foreach (var entry in hit.Image.Imports)
                        {
                            if (IsNetworkImport(entry))
                                hit.Imports.Add(entry);
                        }
                        if (hit.Imports.Count > 0)
                            owners.Add(hit);
                    }
                    catch (Exception ex)
                    {
                        if (failures.Count < 40)
                            failures.Add(
                                "PARSE_SKIP MODULE=" + Sanitize(hit.ModuleName) +
                                " ERROR=" + ex.GetType().Name + ":" + Sanitize(ex.Message));
                    }
                }
            }

            if (owners.Count > 0)
            {
                using (var probe = new RuntimeMemoryProbe())
                {
                    string error;
                    if (probe.Attach(runtime.ProcessId, out error))
                    {
                        foreach (var owner in owners)
                            ScanOwnerXrefs(probe, owner, xrefs);
                    }
                    else
                    {
                        failures.Add("XREF_ATTACH_ERROR=" + Sanitize(error));
                    }
                }
            }

            var sb = Header(runtime, "AUTO_LOADED_MODULE_NETWORK_DISCOVERY");
            sb.AppendLine("LOADED_MODULES=" + loaded.Count);
            sb.AppendLine("WINSOCK_MODULE_LOADED=" + (winsockLoaded ? 1 : 0));
            sb.AppendLine("NETWORK_IMPORT_OWNER_MODULES=" + owners.Count);
            sb.AppendLine("NETWORK_MODULE_IAT_XREFS=" + xrefs.Count);
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

            sb.AppendLine("[NETWORK_MODULE_IAT_XREFS]");
            foreach (var xref in xrefs)
            {
                sb.AppendLine(
                    "OWNER=" + Sanitize(xref.Owner.ModuleName) +
                    " DLL=" + Sanitize(xref.Import.Dll) +
                    " NAME=" + Sanitize(xref.Import.Name) +
                    " IAT_RVA=0x" + xref.Import.IatRva.ToString("X8") +
                    " CALL_RVA=0x" + xref.InstructionRva.ToString("X8") +
                    " CALL_VA=0x" + xref.InstructionAddress.ToString("X8") +
                    " KIND=" + xref.Kind);
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
            if (xrefs.Count > 0)
                status = "PASS_MODULE_NETWORK_XREFS owners=" + owners.Count + " xrefs=" + xrefs.Count;
            else if (owners.Count > 0)
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

            lock (_sync)
            {
                _status = status;
                if (status == "NO_WINSOCK_MODULE_VISIBLE" || status == "WINSOCK_LOADED_OWNER_UNKNOWN")
                    _retryAfterUtc = DateTime.UtcNow.AddSeconds(15);
            }
        }

        private static void ScanOwnerXrefs(
            RuntimeMemoryProbe probe,
            ModuleHit owner,
            List<ModuleXref> output)
        {
            if (owner == null || owner.Image == null || owner.Imports.Count == 0)
                return;

            var iatMap = new Dictionary<uint, PeImportEntry>();
            foreach (var entry in owner.Imports)
            {
                var absolute = owner.Base + entry.IatRva;
                if (absolute <= 0 || absolute > uint.MaxValue) continue;
                var key = (uint)absolute;
                if (!iatMap.ContainsKey(key)) iatMap.Add(key, entry);
            }

            foreach (var section in owner.Image.Sections)
            {
                if (!section.IsExecutable) continue;
                var size = (int)Math.Min(
                    (long)Math.Max(section.VirtualSize, section.RawSize),
                    16L * 1024L * 1024L);
                if (size <= 0) continue;

                byte[] bytes;
                string error;
                var sectionAddress = new IntPtr(owner.Base + section.VirtualAddress);
                if (!probe.TryReadBytes(sectionAddress, size, out bytes, out error) || bytes == null)
                    continue;

                for (var i = 0; i <= bytes.Length - 6; i++)
                {
                    if (bytes[i] != 0xFF) continue;
                    if (bytes[i + 1] != 0x15 && bytes[i + 1] != 0x25) continue;

                    var operand = BitConverter.ToUInt32(bytes, i + 2);
                    PeImportEntry entry;
                    if (!iatMap.TryGetValue(operand, out entry)) continue;

                    output.Add(new ModuleXref
                    {
                        Owner = owner,
                        Import = entry,
                        InstructionRva = section.VirtualAddress + (uint)i,
                        InstructionAddress = sectionAddress.ToInt64() + i,
                        Kind = bytes[i + 1] == 0x15 ? "CALL [IAT]" : "JMP [IAT]"
                    });

                    if (output.Count >= 2000) return;
                }
            }
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
