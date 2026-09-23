using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoNetworkSurfaceDiscovery
    {
        private sealed class Xref
        {
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
        private string _status = "WAITING";

        public AutoNetworkSurfaceDiscovery(string appDir)
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
            var client = Path.Combine(_appDir, "Lin.bin2");
            if (!File.Exists(client))
                throw new FileNotFoundException("Lin.bin2 not found", client);

            var image = PeImportParser.Parse(client);
            var selected = new List<PeImportEntry>();
            var iatMap = new Dictionary<uint, PeImportEntry>();

            foreach (var entry in image.Imports)
            {
                if (!IsInteresting(entry)) continue;
                selected.Add(entry);
                var va = unchecked((uint)(runtime.ModuleBase.ToInt64() + entry.IatRva));
                if (!iatMap.ContainsKey(va)) iatMap.Add(va, entry);
            }

            var xrefs = new List<Xref>();
            using (var probe = new RuntimeMemoryProbe())
            {
                string error;
                if (!probe.Attach(runtime.ProcessId, out error))
                    throw new InvalidOperationException(error);

                foreach (var section in image.Sections)
                {
                    if (!section.IsExecutable) continue;
                    var size = (int)Math.Min(
                        (long)Math.Max(section.VirtualSize, section.RawSize),
                        32L * 1024L * 1024L);
                    if (size <= 0) continue;

                    byte[] bytes;
                    var sectionAddress = new IntPtr(
                        runtime.ModuleBase.ToInt64() + section.VirtualAddress);
                    if (!probe.TryReadBytes(sectionAddress, size, out bytes, out error))
                        continue;

                    for (var i = 0; i <= bytes.Length - 6; i++)
                    {
                        if (bytes[i] != 0xFF) continue;
                        if (bytes[i + 1] != 0x15 && bytes[i + 1] != 0x25) continue;

                        var operand = BitConverter.ToUInt32(bytes, i + 2);
                        PeImportEntry entry;
                        if (!iatMap.TryGetValue(operand, out entry)) continue;

                        xrefs.Add(new Xref
                        {
                            Import = entry,
                            InstructionRva = section.VirtualAddress + (uint)i,
                            InstructionAddress = sectionAddress.ToInt64() + i,
                            Kind = bytes[i + 1] == 0x15 ? "CALL [IAT]" : "JMP [IAT]"
                        });
                    }
                }
            }

            var raw = File.ReadAllBytes(client);
            var keywords = new[]
            {
                "ws2_32.dll", "wsock32.dll", "wininet.dll", "winhttp.dll",
                "send", "recv", "WSASend", "WSARecv", "socket", "connect",
                "GetProcAddress", "LoadLibraryA", "LoadLibraryW"
            };

            var sb = Header(runtime, "AUTO_NETWORK_SURFACE_DISCOVERY");
            sb.AppendLine("INTERESTING_IMPORTS=" + selected.Count);
            sb.AppendLine("IAT_XREFS=" + xrefs.Count);
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();
            sb.AppendLine("[IMPORTS]");
            foreach (var entry in selected)
            {
                sb.AppendLine(
                    "DLL=" + entry.Dll +
                    " NAME=" + entry.Name +
                    " ORDINAL=" + (entry.ByOrdinal ? entry.Ordinal.ToString() : "") +
                    " IAT_RVA=0x" + entry.IatRva.ToString("X8") +
                    " CLASS=" + ImportClass(entry));
            }

            sb.AppendLine();
            sb.AppendLine("[IAT_XREFS]");
            foreach (var xref in xrefs)
            {
                sb.AppendLine(
                    "DLL=" + xref.Import.Dll +
                    " NAME=" + xref.Import.Name +
                    " IAT_RVA=0x" + xref.Import.IatRva.ToString("X8") +
                    " CALL_RVA=0x" + xref.InstructionRva.ToString("X8") +
                    " CALL_VA=0x" + xref.InstructionAddress.ToString("X8") +
                    " KIND=" + xref.Kind +
                    " CLASS=" + ImportClass(xref.Import));
            }

            var stringHits = 0;
            sb.AppendLine();
            sb.AppendLine("[RAW_STRING_HINTS]");
            foreach (var keyword in keywords)
            {
                var hits = FindAscii(raw, keyword, 20);
                stringHits += hits.Count;
                sb.AppendLine("TEXT=" + keyword + " HITS=" + hits.Count +
                    (hits.Count > 0 ? " OFFSETS=" + JoinHex(hits) : ""));
            }

            var dynamicResolver = 0;
            foreach (var entry in selected)
            {
                var name = entry.Name ?? "";
                if (name.Equals("GetProcAddress", StringComparison.OrdinalIgnoreCase) ||
                    name.StartsWith("LoadLibrary", StringComparison.OrdinalIgnoreCase))
                    dynamicResolver++;
            }

            sb.AppendLine();
            sb.AppendLine("DYNAMIC_RESOLVER_IMPORTS=" + dynamicResolver);
            sb.AppendLine("RAW_STRING_HINT_TOTAL=" + stringHits);

            var networkImports = 0;
            foreach (var entry in selected)
                if (ImportClass(entry) == "NETWORK") networkImports++;

            string status;
            if (networkImports > 0 && xrefs.Count > 0)
                status = "PASS_NETWORK_SURFACE imports=" + networkImports + " xrefs=" + xrefs.Count;
            else if (networkImports > 0)
                status = "NETWORK_IMPORTS_NO_DIRECT_XREF imports=" + networkImports;
            else if (dynamicResolver > 0 || stringHits > 0)
                status = "DYNAMIC_RESOLUTION_SUSPECTED resolver=" + dynamicResolver + " strings=" + stringHits;
            else
                status = "NO_NETWORK_SURFACE";

            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(_appDir, "auto_network_surface_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));

            lock (_sync) _status = status;
        }

        private static bool IsInteresting(PeImportEntry entry)
        {
            if (entry == null) return false;
            var dll = (entry.Dll ?? "").ToLowerInvariant();
            var name = entry.Name ?? "";

            if (dll.Contains("ws2_32") || dll.Contains("wsock32") ||
                dll.Contains("wininet") || dll.Contains("winhttp"))
                return true;

            if (name.Equals("GetProcAddress", StringComparison.OrdinalIgnoreCase) ||
                name.StartsWith("LoadLibrary", StringComparison.OrdinalIgnoreCase))
                return true;

            return IsNetworkName(name);
        }

        private static string ImportClass(PeImportEntry entry)
        {
            var dll = (entry.Dll ?? "").ToLowerInvariant();
            if (dll.Contains("ws2_32") || dll.Contains("wsock32") ||
                dll.Contains("wininet") || dll.Contains("winhttp") ||
                IsNetworkName(entry.Name ?? ""))
                return "NETWORK";
            return "RESOLVER";
        }

        private static bool IsNetworkName(string name)
        {
            if (string.IsNullOrEmpty(name)) return false;
            var n = name.ToLowerInvariant();
            return n == "send" || n == "sendto" || n == "recv" || n == "recvfrom" ||
                   n == "socket" || n == "connect" || n == "select" ||
                   n == "ioctlsocket" || n == "closesocket" || n == "shutdown" ||
                   n == "gethostbyname" || n == "inet_addr" || n == "htons" ||
                   n == "ntohs" || n.StartsWith("wsa");
        }

        private static List<int> FindAscii(byte[] data, string text, int limit)
        {
            var result = new List<int>();
            var needle = Encoding.ASCII.GetBytes(text);
            if (needle.Length == 0) return result;

            for (var i = 0; i <= data.Length - needle.Length && result.Count < limit; i++)
            {
                var ok = true;
                for (var j = 0; j < needle.Length; j++)
                {
                    if (data[i + j] != needle[j])
                    {
                        ok = false;
                        break;
                    }
                }
                if (ok) result.Add(i);
            }
            return result;
        }

        private static string JoinHex(List<int> values)
        {
            var sb = new StringBuilder();
            for (var i = 0; i < values.Count; i++)
            {
                if (i > 0) sb.Append(',');
                sb.Append("0x");
                sb.Append(values[i].ToString("X"));
            }
            return sb.ToString();
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
                var sb = Header(runtime, "AUTO_NETWORK_SURFACE_DISCOVERY");
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "auto_network_surface_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
