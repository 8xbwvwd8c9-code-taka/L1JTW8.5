using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;

namespace L1JTW850Launcher
{
    internal sealed class AutoAppDirNetworkDiscovery
    {
        private readonly string _appDir;
        private readonly object _sync = new object();
        private bool _running;
        private bool _done;
        private string _status = "WAITING";

        public AutoAppDirNetworkDiscovery(string appDir)
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

            var sb = Header(runtime, "AUTO_APPDIR_NETWORK_DISCOVERY");
            sb.AppendLine("FILES_SCANNED=" + files.Count);
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            var networkFiles = 0;
            var resolverFiles = 0;
            var parseSkips = 0;

            foreach (var path in files)
            {
                try
                {
                    var info = new FileInfo(path);
                    if (info.Length <= 0 || info.Length > 256L * 1024L * 1024L)
                        continue;

                    var image = PeImportParser.Parse(path);
                    var network = new List<PeImportEntry>();
                    var resolver = new List<PeImportEntry>();
                    foreach (var entry in image.Imports)
                    {
                        if (IsNetwork(entry)) network.Add(entry);
                        else if (IsResolver(entry)) resolver.Add(entry);
                    }

                    if (network.Count == 0 && resolver.Count == 0)
                        continue;

                    if (network.Count > 0) networkFiles++;
                    if (resolver.Count > 0) resolverFiles++;

                    sb.AppendLine("[FILE]");
                    sb.AppendLine("NAME=" + Sanitize(Path.GetFileName(path)));
                    sb.AppendLine("SIZE=" + info.Length);
                    sb.AppendLine("NETWORK_IMPORTS=" + network.Count);
                    sb.AppendLine("RESOLVER_IMPORTS=" + resolver.Count);

                    foreach (var entry in network)
                    {
                        sb.AppendLine(
                            "NETWORK DLL=" + Sanitize(entry.Dll) +
                            " NAME=" + Sanitize(entry.Name) +
                            " ORDINAL=" + (entry.ByOrdinal ? entry.Ordinal.ToString() : "") +
                            " IAT_RVA=0x" + entry.IatRva.ToString("X8"));
                    }
                    foreach (var entry in resolver)
                    {
                        sb.AppendLine(
                            "RESOLVER DLL=" + Sanitize(entry.Dll) +
                            " NAME=" + Sanitize(entry.Name) +
                            " IAT_RVA=0x" + entry.IatRva.ToString("X8"));
                    }
                    sb.AppendLine();
                }
                catch
                {
                    parseSkips++;
                }
            }

            sb.AppendLine("NETWORK_FILES=" + networkFiles);
            sb.AppendLine("RESOLVER_FILES=" + resolverFiles);
            sb.AppendLine("PARSE_SKIPS=" + parseSkips);
            var status = networkFiles > 0
                ? "PASS_APPDIR_NETWORK_FILES count=" + networkFiles
                : resolverFiles > 0
                    ? "RESOLVER_ONLY files=" + resolverFiles
                    : "NO_APPDIR_NETWORK_IMPORTS";
            sb.AppendLine("STATUS=" + status);

            File.WriteAllText(
                Path.Combine(_appDir, "auto_appdir_network_evidence.txt"),
                sb.ToString(),
                new UTF8Encoding(false));

            lock (_sync) _status = status;
        }

        private static bool IsNetwork(PeImportEntry entry)
        {
            if (entry == null) return false;
            var dll = (entry.Dll ?? "").ToLowerInvariant();
            var name = (entry.Name ?? "").ToLowerInvariant();
            if (dll.Contains("ws2_32") || dll.Contains("wsock32") ||
                dll.Contains("wininet") || dll.Contains("winhttp"))
                return true;
            return name == "send" || name == "sendto" || name == "recv" || name == "recvfrom" ||
                   name == "socket" || name == "connect" || name == "select" ||
                   name == "ioctlsocket" || name == "closesocket" || name == "shutdown" ||
                   name.StartsWith("wsa");
        }

        private static bool IsResolver(PeImportEntry entry)
        {
            if (entry == null) return false;
            var name = entry.Name ?? "";
            return name.Equals("GetProcAddress", StringComparison.OrdinalIgnoreCase) ||
                   name.StartsWith("LoadLibrary", StringComparison.OrdinalIgnoreCase);
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
                var sb = Header(runtime, "AUTO_APPDIR_NETWORK_DISCOVERY");
                sb.AppendLine("STATUS=ERROR");
                sb.AppendLine("ERROR=" + ex.GetType().Name + ": " + ex.Message);
                sb.AppendLine("MEMORY_WRITE=NO");
                File.WriteAllText(
                    Path.Combine(_appDir, "auto_appdir_network_evidence.txt"),
                    sb.ToString(),
                    new UTF8Encoding(false));
            }
            catch
            {
            }
        }
    }
}
