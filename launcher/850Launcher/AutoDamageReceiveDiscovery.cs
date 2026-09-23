using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoDamageReceiveDiscovery
    {
        private sealed class RuntimeProcessHit
        {
            public int Pid;
            public string MainName = "";
            public string MainPath = "";
            public bool IsPinnedClient;
            public readonly List<string> LocalModules = new List<string>();
        }

        private sealed class Xref
        {
            public string FileName = "";
            public uint CallRva;
            public uint IatRva;
            public string RuntimeRelevance = "STATIC_ONLY";
            public string RuntimePids = "";
            public readonly List<uint> NearbyDirectCalls = new List<uint>();
            public readonly List<string> CompareHints = new List<string>();
            public string HexWindow = "";
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private int _pid;
        private bool _running;
        private bool _done;
        private string _status = "WAITING_CLIENT";

        public AutoDamageReceiveDiscovery(string appDir)
        {
            _appDir = appDir;
        }

        public string Status
        {
            get { lock (_sync) return _running ? "RUNNING" : _status; }
        }

        public void EnsureRunning(RuntimeSnapshot runtime)
        {
            if (runtime == null ||
                !runtime.Connected ||
                !runtime.ClientHashAuthoritative ||
                runtime.ProcessId <= 0)
            {
                lock (_sync) _status = "WAITING_CLIENT";
                return;
            }

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
            var runtimeProcesses = DiscoverAppDirProcesses(runtime);
            var candidates = new[] { "Lineage.exe", "chigamec.dll", "aud32.dll" };
            var all = new List<Xref>();
            var scanned = 0;

            foreach (var name in candidates)
            {
                var path = Path.Combine(_appDir, name);
                if (!File.Exists(path)) continue;
                scanned++;

                var image = PeImportParser.Parse(path);
                var recvImports = new List<PeImportEntry>();
                foreach (var entry in image.Imports)
                {
                    var resolved = ResolveSocketName(entry);
                    if (resolved == "recv" ||
                        resolved == "recvfrom" ||
                        resolved == "wsarecv")
                    {
                        recvImports.Add(entry);
                    }
                }

                if (recvImports.Count == 0) continue;

                string relevance;
                string pids;
                ResolveRuntimeRelevance(
                    name,
                    runtimeProcesses,
                    out relevance,
                    out pids);

                ScanFile(
                    path,
                    image,
                    recvImports,
                    relevance,
                    pids,
                    all);
            }

            var runtimeXrefs = 0;
            foreach (var x in all)
            {
                if (!string.Equals(
                    x.RuntimeRelevance,
                    "STATIC_ONLY",
                    StringComparison.OrdinalIgnoreCase))
                {
                    runtimeXrefs++;
                }
            }

            var sb = Header(runtime, "AUTO_DAMAGE_RECEIVE_DISCOVERY");
            sb.AppendLine("FILES_SCANNED=" + scanned);
            sb.AppendLine("APPDIR_RUNTIME_PROCESSES=" + runtimeProcesses.Count);
            sb.AppendLine("RECV_XREFS=" + all.Count);
            sb.AppendLine("RUNTIME_RECV_XREFS=" + runtimeXrefs);
            sb.AppendLine("DAMAGE_CLASSIFICATION=HEURISTIC_ONLY");
            sb.AppendLine("ACCOUNTING_RULE=PHYSICAL_AND_MAGIC_SEPARATE");
            sb.AppendLine("MAGIC_WEAPON_PROC=COUNT_MAGIC_ONLY");
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            sb.AppendLine("[APPDIR_RUNTIME_PROCESSES]");
            foreach (var hit in runtimeProcesses)
            {
                sb.AppendLine(
                    "PID=" + hit.Pid +
                    " MAIN=" + Sanitize(hit.MainName) +
                    " PINNED_CLIENT=" + (hit.IsPinnedClient ? 1 : 0) +
                    " PATH=" + Sanitize(hit.MainPath) +
                    " LOCAL_MODULES=" + hit.LocalModules.Count);

                foreach (var module in hit.LocalModules)
                    sb.AppendLine("  MODULE=" + Sanitize(module));
            }
            sb.AppendLine();

            foreach (var x in all)
            {
                sb.AppendLine("[RECV_XREF]");
                sb.AppendLine("FILE=" + x.FileName);
                sb.AppendLine("RUNTIME_RELEVANCE=" + x.RuntimeRelevance);
                sb.AppendLine("RUNTIME_PIDS=" + x.RuntimePids);
                sb.AppendLine("CALL_RVA=0x" + x.CallRva.ToString("X8"));
                sb.AppendLine("IAT_RVA=0x" + x.IatRva.ToString("X8"));
                sb.AppendLine("NEARBY_DIRECT_CALLS=" + x.NearbyDirectCalls.Count);
                foreach (var target in x.NearbyDirectCalls)
                    sb.AppendLine("  CALL_TARGET_RVA=0x" + target.ToString("X8"));
                sb.AppendLine("COMPARE_HINTS=" + x.CompareHints.Count);
                foreach (var hint in x.CompareHints)
                    sb.AppendLine("  " + hint);
                sb.AppendLine("HEX_WINDOW=" + x.HexWindow);
                sb.AppendLine();
            }

            string status;
            if (runtimeXrefs > 0)
            {
                status =
                    "PASS_RUNTIME_RECV_WRAPPERS runtime=" +
                    runtimeXrefs +
                    " total=" +
                    all.Count;
            }
            else if (all.Count > 0)
            {
                status =
                    "STATIC_RECV_WRAPPERS_NOT_RUNTIME_PROVEN total=" +
                    all.Count;
            }
            else
            {
                status = "NO_GAME_RECV_XREFS";
            }

            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(_appDir, "auto_damage_receive_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));

            lock (_sync) _status = status;
        }

        private List<RuntimeProcessHit> DiscoverAppDirProcesses(
            RuntimeSnapshot runtime)
        {
            var result = new List<RuntimeProcessHit>();
            var root = EnsureTrailingSeparator(
                Path.GetFullPath(_appDir));

            foreach (var process in Process.GetProcesses())
            {
                try
                {
                    if (process.HasExited) continue;
                    var main = process.MainModule;
                    if (main == null) continue;

                    var mainPath = Path.GetFullPath(main.FileName);
                    if (!IsUnderRoot(mainPath, root)) continue;

                    var hit = new RuntimeProcessHit
                    {
                        Pid = process.Id,
                        MainName = Path.GetFileName(mainPath),
                        MainPath = mainPath,
                        IsPinnedClient =
                            runtime != null &&
                            process.Id == runtime.ProcessId
                    };

                    try
                    {
                        foreach (ProcessModule module in process.Modules)
                        {
                            try
                            {
                                var modulePath = module.FileName;
                                if (string.IsNullOrEmpty(modulePath))
                                    continue;

                                var full = Path.GetFullPath(modulePath);
                                if (!IsUnderRoot(full, root))
                                    continue;

                                var moduleName =
                                    module.ModuleName ??
                                    Path.GetFileName(full);

                                if (!ContainsIgnoreCase(
                                    hit.LocalModules,
                                    moduleName))
                                {
                                    hit.LocalModules.Add(moduleName);
                                }
                            }
                            catch
                            {
                            }
                        }
                    }
                    catch
                    {
                    }

                    result.Add(hit);
                }
                catch
                {
                    // Protected/transient processes are irrelevant to app-dir ownership.
                }
                finally
                {
                    try { process.Dispose(); } catch { }
                }
            }

            result.Sort(delegate(RuntimeProcessHit a, RuntimeProcessHit b)
            {
                if (a.IsPinnedClient != b.IsPinnedClient)
                    return a.IsPinnedClient ? -1 : 1;
                return a.Pid.CompareTo(b.Pid);
            });

            return result;
        }

        private static void ResolveRuntimeRelevance(
            string candidateName,
            List<RuntimeProcessHit> processes,
            out string relevance,
            out string pids)
        {
            var kinds = new List<string>();
            var pidList = new List<int>();

            foreach (var hit in processes)
            {
                var kind = "";

                if (string.Equals(
                    hit.MainName,
                    candidateName,
                    StringComparison.OrdinalIgnoreCase))
                {
                    kind = hit.IsPinnedClient
                        ? "PINNED_MAIN"
                        : "COMPANION_MAIN";
                }
                else if (ContainsIgnoreCase(
                    hit.LocalModules,
                    candidateName))
                {
                    kind = hit.IsPinnedClient
                        ? "PINNED_MODULE"
                        : "COMPANION_MODULE";
                }

                if (kind.Length == 0) continue;

                if (!ContainsIgnoreCase(kinds, kind))
                    kinds.Add(kind);

                if (!pidList.Contains(hit.Pid))
                    pidList.Add(hit.Pid);
            }

            relevance = kinds.Count == 0
                ? "STATIC_ONLY"
                : string.Join("+", kinds.ToArray());

            if (pidList.Count == 0)
            {
                pids = "";
            }
            else
            {
                var text = new string[pidList.Count];
                for (var i = 0; i < pidList.Count; i++)
                    text[i] = pidList[i].ToString();
                pids = string.Join(",", text);
            }
        }

        private static bool IsUnderRoot(
            string path,
            string rootWithSeparator)
        {
            return !string.IsNullOrEmpty(path) &&
                   path.StartsWith(
                       rootWithSeparator,
                       StringComparison.OrdinalIgnoreCase);
        }

        private static string EnsureTrailingSeparator(string path)
        {
            if (string.IsNullOrEmpty(path)) return "";
            if (path.EndsWith(
                    Path.DirectorySeparatorChar.ToString(),
                    StringComparison.Ordinal))
                return path;

            return path + Path.DirectorySeparatorChar;
        }

        private static bool ContainsIgnoreCase(
            List<string> values,
            string wanted)
        {
            foreach (var value in values)
            {
                if (string.Equals(
                    value,
                    wanted,
                    StringComparison.OrdinalIgnoreCase))
                    return true;
            }
            return false;
        }

        private static void ScanFile(
            string path,
            PeImageInfo image,
            List<PeImportEntry> recvImports,
            string runtimeRelevance,
            string runtimePids,
            List<Xref> output)
        {
            var data = File.ReadAllBytes(path);
            foreach (var section in image.Sections)
            {
                if (!section.IsExecutable || section.RawSize == 0)
                    continue;

                var rawStart = (long)section.RawAddress;
                var rawEnd = Math.Min(
                    (long)data.Length,
                    rawStart + section.RawSize);

                if (rawStart < 0 || rawStart >= rawEnd)
                    continue;

                foreach (var import in recvImports)
                {
                    var target =
                        (ulong)image.ImageBase +
                        import.IatRva;

                    if (target > uint.MaxValue)
                        continue;

                    var iatVa = (uint)target;
                    var b0 = (byte)(iatVa & 0xFF);
                    var b1 = (byte)((iatVa >> 8) & 0xFF);
                    var b2 = (byte)((iatVa >> 16) & 0xFF);
                    var b3 = (byte)((iatVa >> 24) & 0xFF);

                    for (long pos = rawStart;
                         pos + 6 <= rawEnd;
                         pos++)
                    {
                        var i = (int)pos;
                        if (data[i] != 0xFF ||
                            data[i + 1] != 0x15)
                            continue;

                        if (data[i + 2] != b0 ||
                            data[i + 3] != b1 ||
                            data[i + 4] != b2 ||
                            data[i + 5] != b3)
                            continue;

                        var delta = pos - rawStart;
                        var callRva =
                            section.VirtualAddress +
                            (uint)delta;

                        var x = new Xref
                        {
                            FileName = Path.GetFileName(path),
                            CallRva = callRva,
                            IatRva = import.IatRva,
                            RuntimeRelevance = runtimeRelevance,
                            RuntimePids = runtimePids
                        };

                        AnalyzeWindow(
                            data,
                            section,
                            pos,
                            image.SizeOfImage,
                            x);

                        output.Add(x);
                        if (output.Count >= 256)
                            return;
                    }
                }
            }
        }

        private static void AnalyzeWindow(
            byte[] data,
            PeSectionInfo section,
            long callRaw,
            uint sizeOfImage,
            Xref x)
        {
            var sectionStart = (long)section.RawAddress;
            var sectionEnd = Math.Min(
                (long)data.Length,
                sectionStart + section.RawSize);

            var start = Math.Max(
                sectionStart,
                callRaw - 0x180);

            var end = Math.Min(
                sectionEnd,
                callRaw + 0x180);

            var seenCalls = new HashSet<uint>();

            for (long pos = start;
                 pos + 5 <= end;
                 pos++)
            {
                var i = (int)pos;

                if (data[i] != 0xE8)
                    continue;

                var rel =
                    BitConverter.ToInt32(
                        data,
                        i + 1);

                var instrRva =
                    section.VirtualAddress +
                    (uint)(pos - sectionStart);

                var targetLong =
                    (long)instrRva +
                    5L +
                    rel;

                if (targetLong < 0 ||
                    targetLong >= sizeOfImage)
                    continue;

                var target = (uint)targetLong;

                if (seenCalls.Add(target))
                    x.NearbyDirectCalls.Add(target);

                if (x.NearbyDirectCalls.Count >= 64)
                    break;
            }

            for (long pos = start;
                 pos + 3 <= end &&
                 x.CompareHints.Count < 96;
                 pos++)
            {
                var i = (int)pos;
                var rva =
                    section.VirtualAddress +
                    (uint)(pos - sectionStart);

                if (data[i] == 0x3C)
                {
                    x.CompareHints.Add(
                        "CMP_AL_IMM8 RVA=0x" +
                        rva.ToString("X8") +
                        " IMM=" +
                        data[i + 1]);

                    continue;
                }

                if (data[i] == 0x83 &&
                    i + 2 < data.Length)
                {
                    var modrm = data[i + 1];

                    if ((modrm & 0x38) == 0x38)
                    {
                        x.CompareHints.Add(
                            "CMP_RM32_IMM8 RVA=0x" +
                            rva.ToString("X8") +
                            " IMM=" +
                            data[i + 2]);
                    }

                    continue;
                }

                if (data[i] == 0x80 &&
                    i + 2 < data.Length)
                {
                    var modrm = data[i + 1];

                    if ((modrm & 0x38) == 0x38)
                    {
                        x.CompareHints.Add(
                            "CMP_RM8_IMM8 RVA=0x" +
                            rva.ToString("X8") +
                            " IMM=" +
                            data[i + 2]);
                    }
                }
            }

            var hexStart = Math.Max(
                sectionStart,
                callRaw - 32);

            var hexEnd = Math.Min(
                sectionEnd,
                callRaw + 64);

            var hex = new StringBuilder();

            for (long p = hexStart;
                 p < hexEnd;
                 p++)
            {
                if (hex.Length > 0)
                    hex.Append(' ');

                hex.Append(
                    data[(int)p].ToString(
                        "X2",
                        CultureInfo.InvariantCulture));
            }

            x.HexWindow = hex.ToString();
        }

        private static string ResolveSocketName(
            PeImportEntry entry)
        {
            if (entry == null) return "";

            if (!entry.ByOrdinal)
                return (entry.Name ?? "").ToLowerInvariant();

            var dll =
                (entry.Dll ?? "").ToLowerInvariant();

            if (!dll.Contains("ws2_32") &&
                !dll.Contains("wsock32"))
                return "";

            switch (entry.Ordinal)
            {
                case 16: return "recv";
                case 17: return "recvfrom";
                default: return "";
            }
        }

        private static string Sanitize(string value)
        {
            if (string.IsNullOrEmpty(value))
                return "";

            return value
                .Replace("\r", " ")
                .Replace("\n", " ")
                .Replace("|", "/");
        }

        private static StringBuilder Header(
            RuntimeSnapshot runtime,
            string mode)
        {
            var sb = new StringBuilder();
            sb.AppendLine(
                "TIME=" +
                DateTime.Now.ToString(
                    "yyyy-MM-dd HH:mm:ss"));

            sb.AppendLine("MODE=" + mode);
            sb.AppendLine(
                "PID=" +
                (runtime == null
                    ? 0
                    : runtime.ProcessId));

            sb.AppendLine(
                "CLIENT_SHA256=" +
                (runtime == null
                    ? ""
                    : runtime.ClientSha256 ?? ""));

            sb.AppendLine(
                "CLIENT_AUTHORITY=" +
                (runtime != null &&
                 runtime.ClientHashAuthoritative
                    ? 1
                    : 0));

            return sb;
        }

        private void SaveError(
            RuntimeSnapshot runtime,
            Exception ex)
        {
            try
            {
                var sb = Header(
                    runtime,
                    "AUTO_DAMAGE_RECEIVE_DISCOVERY");

                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine(
                    "ERROR=" +
                    ex.GetType().Name +
                    ": " +
                    ex.Message);

                sb.AppendLine("MEMORY_WRITE=NO");

                File.WriteAllText(
                    Path.Combine(
                        _appDir,
                        "auto_damage_receive_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
