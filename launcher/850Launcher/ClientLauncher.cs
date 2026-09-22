using System;
using System.Diagnostics;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal sealed class ClientLauncher
    {
        private readonly string _appDir;

        public ClientLauncher(string appDir) { _appDir = appDir; }

        public Process Launch(LauncherConfig config)
        {
            config.Validate(_appDir);

            if (config.UseLegacyLogin)
            {
                WriteLegacyIpIni(config);
                var psi = new ProcessStartInfo
                {
                    FileName = Path.Combine(_appDir, config.LoginBinary),
                    WorkingDirectory = _appDir,
                    UseShellExecute = true
                };
                if (config.RequestElevation) psi.Verb = "runas";
                return Process.Start(psi);
            }

            return Process.Start(new ProcessStartInfo
            {
                FileName = Path.Combine(_appDir, config.ClientBinary),
                WorkingDirectory = _appDir,
                Arguments = "2130706433",
                UseShellExecute = true
            });
        }

        private void WriteLegacyIpIni(LauncherConfig config)
        {
            // Existing LoginWithoutUI authority: line 1 IP, line 2 PORT.
            var text = config.ServerIp + Environment.NewLine + config.ServerPort + Environment.NewLine;
            File.WriteAllText(Path.Combine(_appDir, "ip.ini"), text, Encoding.ASCII);
        }
    }
}
