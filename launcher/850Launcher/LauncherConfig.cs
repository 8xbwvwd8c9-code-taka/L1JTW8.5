using System;
using System.IO;

namespace L1JTW850Launcher
{
    internal sealed class LauncherConfig
    {
        public string ServerName = "850 Test Server";
        public string ServerIp = "127.0.0.1";
        public int ServerPort = 2000;
        public string ClientBinary = "Lin.bin2";
        public string LoginBinary = "LoginWithoutUI.exe";
        public bool UseLegacyLogin = true;
        public bool RequestElevation = true;
        public bool HelperEnabled = true;

        public static LauncherConfig Load(string path)
        {
            var ini = IniDocument.Load(path);
            var c = new LauncherConfig();
            c.ServerName = ini.Get("Server", "Name", c.ServerName);
            c.ServerIp = ini.Get("Server", "IP", c.ServerIp);
            c.ServerPort = ini.GetInt("Server", "Port", c.ServerPort);
            c.ClientBinary = ini.Get("Client", "Binary", c.ClientBinary);
            c.LoginBinary = ini.Get("Client", "LoginBinary", c.LoginBinary);
            c.UseLegacyLogin = ini.GetBool("Client", "UseLegacyLogin", c.UseLegacyLogin);
            c.RequestElevation = ini.GetBool("Client", "RequestElevation", c.RequestElevation);
            c.HelperEnabled = ini.GetBool("Helper", "Enabled", c.HelperEnabled);
            return c;
        }

        public void Save(string path)
        {
            var ini = new IniDocument();
            ini.Set("Server", "Name", ServerName);
            ini.Set("Server", "IP", ServerIp);
            ini.Set("Server", "Port", ServerPort);
            ini.Set("Client", "Binary", ClientBinary);
            ini.Set("Client", "LoginBinary", LoginBinary);
            ini.Set("Client", "UseLegacyLogin", UseLegacyLogin ? 1 : 0);
            ini.Set("Client", "RequestElevation", RequestElevation ? 1 : 0);
            ini.Set("Helper", "Enabled", HelperEnabled ? 1 : 0);
            ini.Save(path);
        }

        public void Validate(string appDir)
        {
            if (string.IsNullOrWhiteSpace(ServerIp)) throw new InvalidDataException("Server IP is empty.");
            if (ServerPort < 1 || ServerPort > 65535) throw new InvalidDataException("Server port is invalid.");
            var target = Path.Combine(appDir, UseLegacyLogin ? LoginBinary : ClientBinary);
            if (!File.Exists(target)) throw new FileNotFoundException("Required client file not found.", target);
        }
    }
}
