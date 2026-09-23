using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoStaticGameNetworkDiscovery
    {
        private sealed class CoreImport
        {
            public PeImportEntry Entry;
            public string ResolvedName = "";
        }

        private sealed class StaticXref
        {
            public string Name = "";
            public uint ImportRva;
            public uint InstructionRva;
            public string Kind = "";
        }

        private sealed class FileResult
        {
            public string Path = "";
            public string Name = "";
            public uint ImageBase;
            public int Score;
            public readonly List<CoreImport> Imports = new List<CoreImport>();
            public readonly List<StaticXref> Xrefs = new List<StaticXref>();
        }

        private readonly string _appDir;
        private readonly object _sync = new object();
        private bool _running;
        private bool _done;
        private string _status = "WAITING";

        public AutoStaticGameNetworkDiscovery(string appDir)
        {
            _appDir = appDir;
        }

        public string Status
        {
            get { lock (_sync) return _running ? "RUNNING" : _status; }
        }

        public void EnsureRunning(RuntimeSnapshot runtime)
        {
            lock (_sync)
            {
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
            var files = new List<string>();
            foreach (var path in Directory.GetFiles(_appDir, "*", SearchOption.TopDirectoryOnly))
            {
                var ext = Path.GetExtension(path).ToLowerInvariant();
                if (ext == ".exe" || ext == ".dll" || ext == ".asi" || ext == ".bin" || ext == ".bin2")
                    files.Add(path);
            }
            files.Sort(StringComparer.OrdinalIgnoreCase);
            if (files.Count > 250) files.RemoveRange(250, files.Count - 250);

            var results = new List<FileResult>();
            var skips = new List<string>();
            var totalXrefs = 0;

            foreach (var path in files)
            {
                try
                {
                    var info = new FileInfo(path);
                    if (info.Length <= 0 || info.Length > 256L * 1024L * 1024L)
                        continue;

                    var image = PeImportParser.Parse(path);
                    var core = new List<CoreImport>();
                    foreach (var entry in image.Imports)
                    {
                        var resolved = ResolveSocketName(entry);
                        if (!IsCoreSocketName(resolved)) continue;
                        core.Add(new CoreImport { Entry = entry, ResolvedName = resolved });
                    }
                    if (core.Count == 0) continue;

                    var result = new FileResult
                    {
                        Path = path,
                        Name = Path.GetFileName(path),
                        ImageBase = image.ImageBase
                    };
                    result.Imports.AddRange(core);
                    ScanStaticXrefs(path, image, core, result.Xrefs);
                    result.Score = Score(result);
                    totalXrefs += result.Xrefs.Count;
                    results.Add(result);
                }
                catch (Exception ex)
                {
                    if (skips.Count < 40)
                        skips.Add(Path.GetFileName(path) + ": " + ex.GetType().Name + ": " + Sanitize(ex.Message));
                }
            }

            results.Sort(delegate(FileResult a, FileResult b)
            {
                var c = b.Score.CompareTo(a.Score);
                if (c != 0) return c;
                c = b.Xrefs.Count.CompareTo(a.Xrefs.Count);
                if (c != 0) return c;
                return string.Compare(a.Name, b.Name, StringComparison.OrdinalIgnoreCase);
            });

            var sb = Header(runtime, "AUTO_STATIC_GAME_NETWORK_DISCOVERY");
            sb.AppendLine("FILES_SCANNED=" + files.Count);
            sb.AppendLine("CORE_SOCKET_FILES=" + results.Count);
            sb.AppendLine("CORE_SOCKET_XREFS=" + totalXrefs);
            sb.AppendLine("PARSE_SKIPS=" + skips.Count);
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            sb.AppendLine("[RANKED_FILES]");
            foreach (var result in results)
            {
                sb.AppendLine(
                    "FILE=" + Sanitize(result.Name) +
                    " SCORE=" + result.Score +
                    " IMAGE_BASE=0x" + result.ImageBase.ToString("X8") +
                    " CORE_IMPORTS=" + result.Imports.Count +
                    " XREFS=" + result.Xrefs.Count);
            }
            sb.AppendLine();

            foreach (var result in results)
            {
                sb.AppendLine("[FILE]");
                sb.AppendLine("NAME=" + Sanitize(result.Name));
                sb.AppendLine("PATH=" + Sanitize(result.Path));
                sb.AppendLine("IMAGE_BASE=0x" + result.ImageBase.ToString("X8"));
                sb.AppendLine("LIKELY_GAME_SCORE=" + result.Score);
                sb.AppendLine("CORE_IMPORTS=" + result.Imports.Count);
                sb.AppendLine("STATIC_IAT_XREFS=" + result.Xrefs.Count);

                foreach (var import in result.Imports)
                {
                    sb.AppendLine(
                        "CORE_IMPORT DLL=" + Sanitize(import.Entry.Dll) +
                        " RAW_NAME=" + Sanitize(import.Entry.Name) +
                        " ORDINAL=" + (import.Entry.ByOrdinal ? import.Entry.Ordinal.ToString() : "") +
                        " RESOLVED=" + import.ResolvedName +
                        " IAT_RVA=0x" + import.Entry.IatRva.ToString("X8"));
                }

                foreach (var xref in result.Xrefs)
                {
                    sb.AppendLine(
                        "CORE_XREF NAME=" + xref.Name +
                        " IAT_RVA=0x" + xref.ImportRva.ToString("X8") +
                        " CALL_RVA=0x" + xref.InstructionRva.ToString("X8") +
                        " KIND=" + xref.Kind);
                }
                sb.AppendLine();
            }

            if (skips.Count > 0)
            {
                sb.AppendLine("[PARSE_SKIPS]");
                foreach (var skip in skips) sb.AppendLine(skip);
                sb.AppendLine();
            }

            var status = totalXrefs > 0
                ? "PASS_STATIC_SOCKET_XREFS files=" + results.Count + " xrefs=" + totalXrefs
                : results.Count > 0
                    ? "PASS_CORE_SOCKET_IMPORTS files=" + results.Count
                    : "NO_CORE_SOCKET_IMPORTS";
            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(_appDir, "auto_static_game_network_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));

            lock (_sync) _status = status;
        }

        private static void ScanStaticXrefs(
            string path,
            PeImageInfo image,
            List<CoreImport> imports,
            List<StaticXref> output)
        {
            var data = File.ReadAllBytes(path);
            foreach (var section in image.Sections)
            {
                if (!section.IsExecutable || section.RawSize == 0) continue;
                var rawStart = (long)section.RawAddress;
                var rawEnd = Math.Min((long)data.Length, rawStart + section.RawSize);
                if (rawStart < 0 || rawStart >= rawEnd) continue;

                foreach (var import in imports)
                {
                    var target = (ulong)image.ImageBase + import.Entry.IatRva;
                    if (target > uint.MaxValue) continue;
                    var value = (uint)target;
                    var b0 = (byte)(value & 0xFF);
                    var b1 = (byte)((value >> 8) & 0xFF);
                    var b2 = (byte)((value >> 16) & 0xFF);
                    var b3 = (byte)((value >> 24) & 0xFF);

                    for (long pos = rawStart; pos + 6 <= rawEnd; pos++)
                    {
                        var i = (int)pos;
                        if (data[i] != 0xFF) continue;
                        if (data[i + 1] != 0x15 && data[i + 1] != 0x25) continue;
                        if (data[i + 2] != b0 || data[i + 3] != b1 || data[i + 4] != b2 || data[i + 5] != b3)
                            continue;

                        var delta = pos - rawStart;
                        var callRvaLong = (long)section.VirtualAddress + delta;
                        if (callRvaLong < 0 || callRvaLong > uint.MaxValue) continue;
                        output.Add(new StaticXref
                        {
                            Name = import.ResolvedName,
                            ImportRva = import.Entry.IatRva,
                            InstructionRva = (uint)callRvaLong,
                            Kind = data[i + 1] == 0x15 ? "CALL [IAT]" : "JMP [IAT]"
                        });
                        if (output.Count >= 2000) return;
                    }
                }
            }
        }

        private static int Score(FileResult result)
        {
            var score = 0;
            var lower = (result.Name ?? "").ToLowerInvariant();
            if (lower == "lineage.exe") score += 180;
            if (lower.Contains("chigame")) score += 140;
            if (lower == "aud32.dll") score += 80;
            if (lower.Contains("awesomium") || lower.Contains("libcef") || lower.Contains("avcodec")) score -= 80;
            if (lower.Contains("update") || lower.StartsWith("unins")) score -= 100;

            var hasSend = false;
            var hasRecv = false;
            var hasConnect = false;
            var hasSocket = false;
            foreach (var import in result.Imports)
            {
                var name = import.ResolvedName;
                if (name == "send" || name == "sendto") hasSend = true;
                if (name == "recv" || name == "recvfrom") hasRecv = true;
                if (name == "connect") hasConnect = true;
                if (name == "socket") hasSocket = true;
            }
            if (hasSend) score += 60;
            if (hasRecv) score += 60;
            if (hasConnect) score += 30;
            if (hasSocket) score += 30;
            score += Math.Min(100, result.Xrefs.Count * 3);
            return score;
        }

        private static bool IsCoreSocketName(string name)
        {
            return name == "send" || name == "sendto" ||
                   name == "recv" || name == "recvfrom" ||
                   name == "connect" || name == "socket" ||
                   name == "select" || name == "closesocket" ||
                   name == "shutdown" || name == "ioctlsocket";
        }

        private static string ResolveSocketName(PeImportEntry entry)
        {
            if (entry == null) return "";
            if (!entry.ByOrdinal) return (entry.Name ?? "").ToLowerInvariant();

            var dll = (entry.Dll ?? "").ToLowerInvariant();
            if (!dll.Contains("ws2_32") && !dll.Contains("wsock32")) return "";

            switch (entry.Ordinal)
            {
                case 1: return "accept";
                case 2: return "bind";
                case 3: return "closesocket";
                case 4: return "connect";
                case 5: return "getpeername";
                case 6: return "getsockname";
                case 7: return "getsockopt";
                case 8: return "htonl";
                case 9: return "htons";
                case 10: return "inet_addr";
                case 11: return "inet_ntoa";
                case 12: return "ioctlsocket";
                case 13: return "listen";
                case 14: return "ntohl";
                case 15: return "ntohs";
                case 16: return "recv";
                case 17: return "recvfrom";
                case 18: return "select";
                case 19: return "send";
                case 20: return "sendto";
                case 21: return "setsockopt";
                case 22: return "shutdown";
                case 23: return "socket";
                default: return "";
            }
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
            sb.AppendLine("PID=" + (runtime == null ? 0 : runtime.ProcessId));
            sb.AppendLine("CLIENT_SHA256=" + (runtime == null ? "" : runtime.ClientSha256 ?? ""));
            sb.AppendLine("CLIENT_AUTHORITY=" + (runtime != null && runtime.ClientHashAuthoritative ? 1 : 0));
            return sb;
        }

        private void SaveError(RuntimeSnapshot runtime, Exception ex)
        {
            try
            {
                var sb = Header(runtime, "AUTO_STATIC_GAME_NETWORK_DISCOVERY");
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "auto_static_game_network_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
