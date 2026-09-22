using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal sealed class IniDocument
    {
        private readonly Dictionary<string, Dictionary<string, string>> _data =
            new Dictionary<string, Dictionary<string, string>>(StringComparer.OrdinalIgnoreCase);

        public static IniDocument Load(string path)
        {
            var doc = new IniDocument();
            if (!File.Exists(path)) return doc;

            var section = "";
            foreach (var raw in File.ReadAllLines(path, Encoding.UTF8))
            {
                var line = raw.Trim();
                if (line.Length == 0 || line.StartsWith(";") || line.StartsWith("#")) continue;
                if (line.StartsWith("[") && line.EndsWith("]"))
                {
                    section = line.Substring(1, line.Length - 2).Trim();
                    continue;
                }

                var eq = line.IndexOf('=');
                if (eq <= 0) continue;
                doc.Set(section, line.Substring(0, eq).Trim(), line.Substring(eq + 1).Trim());
            }
            return doc;
        }

        public string Get(string section, string key, string fallback)
        {
            Dictionary<string, string> s;
            string value;
            return _data.TryGetValue(section, out s) && s.TryGetValue(key, out value) ? value : fallback;
        }

        public bool GetBool(string section, string key, bool fallback)
        {
            var value = Get(section, key, fallback ? "1" : "0");
            return value == "1" || value.Equals("true", StringComparison.OrdinalIgnoreCase) ||
                   value.Equals("yes", StringComparison.OrdinalIgnoreCase);
        }

        public int GetInt(string section, string key, int fallback)
        {
            int n;
            return int.TryParse(Get(section, key, fallback.ToString()), out n) ? n : fallback;
        }

        public void Set(string section, string key, object value)
        {
            Dictionary<string, string> s;
            if (!_data.TryGetValue(section, out s))
            {
                s = new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
                _data[section] = s;
            }
            s[key] = Convert.ToString(value);
        }

        public void Save(string path)
        {
            var sb = new StringBuilder();
            foreach (var section in _data)
            {
                if (section.Key.Length > 0) sb.AppendLine("[" + section.Key + "]");
                foreach (var kv in section.Value) sb.AppendLine(kv.Key + "=" + kv.Value);
                sb.AppendLine();
            }
            File.WriteAllText(path, sb.ToString(), new UTF8Encoding(false));
        }
    }
}
