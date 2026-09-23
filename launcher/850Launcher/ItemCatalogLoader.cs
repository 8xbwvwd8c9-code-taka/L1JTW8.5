using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
using System.Text;

namespace L1JTW850Launcher
{
    internal static class ItemCatalogLoader
    {
        public static Dictionary<int, string> Load(string appDir)
        {
            var result = new Dictionary<int, string>();
            var externalPath = Path.Combine(appDir ?? "", "item-names.csv");

            if (File.Exists(externalPath))
            {
                try
                {
                    ParseLines(File.ReadAllLines(externalPath, Encoding.UTF8), result);
                }
                catch
                {
                }
            }

            if (result.Count > 0)
                return result;

            ParseEmbedded(result);
            return result;
        }

        public static bool EnsureExternalCatalog(string appDir)
        {
            try
            {
                var path = Path.Combine(appDir ?? "", "item-names.csv");
                if (File.Exists(path) && new FileInfo(path).Length > 16)
                    return true;

                var lines = ReadEmbeddedLines();
                if (lines.Count == 0)
                    return false;

                File.WriteAllLines(path, lines.ToArray(), new UTF8Encoding(false));
                return File.Exists(path) && new FileInfo(path).Length > 16;
            }
            catch
            {
                return false;
            }
        }

        private static void ParseEmbedded(Dictionary<int, string> result)
        {
            ParseLines(ReadEmbeddedLines(), result);
        }

        private static List<string> ReadEmbeddedLines()
        {
            var lines = new List<string>();
            try
            {
                var assembly = Assembly.GetExecutingAssembly();
                string resourceName = null;
                foreach (var name in assembly.GetManifestResourceNames())
                {
                    if (name.EndsWith(".item-names.csv", StringComparison.OrdinalIgnoreCase) ||
                        string.Equals(name, "item-names.csv", StringComparison.OrdinalIgnoreCase))
                    {
                        resourceName = name;
                        break;
                    }
                }

                if (string.IsNullOrEmpty(resourceName))
                    return lines;

                using (var stream = assembly.GetManifestResourceStream(resourceName))
                using (var reader = stream == null ? null : new StreamReader(stream, Encoding.UTF8, true))
                {
                    if (reader == null)
                        return lines;

                    string line;
                    while ((line = reader.ReadLine()) != null)
                        lines.Add(line);
                }
            }
            catch
            {
            }
            return lines;
        }

        private static void ParseLines(IEnumerable<string> lines, Dictionary<int, string> result)
        {
            foreach (var raw in lines)
            {
                var line = (raw ?? "").Trim();
                if (line.Length == 0 || line.StartsWith("item_id", StringComparison.OrdinalIgnoreCase))
                    continue;

                var comma = line.IndexOf(',');
                if (comma <= 0) continue;

                int id;
                if (!int.TryParse(line.Substring(0, comma).Trim(), out id) || id <= 0)
                    continue;

                var name = line.Substring(comma + 1).Trim();
                if (name.Length >= 2 && name[0] == '"' && name[name.Length - 1] == '"')
                    name = name.Substring(1, name.Length - 2).Replace("\"\"", "\"");

                if (!result.ContainsKey(id))
                    result.Add(id, name);
            }
        }
    }
}
