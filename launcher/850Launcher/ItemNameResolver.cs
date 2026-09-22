using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal sealed class ItemNameResolver
    {
        private readonly Dictionary<int, string> _names =
            new Dictionary<int, string>();

        public ItemNameResolver(string appDir)
        {
            Load(Path.Combine(appDir, "item-names.csv"));
        }

        public string Resolve(int itemId)
        {
            string name;
            return _names.TryGetValue(itemId, out name)
                ? name
                : "Item#" + itemId;
        }

        private void Load(string path)
        {
            if (!File.Exists(path))
                return;

            foreach (var raw in File.ReadAllLines(path, Encoding.UTF8))
            {
                var line = raw.Trim();
                if (line.Length == 0 || line.StartsWith("item_id,"))
                    continue;

                var comma = line.IndexOf(',');
                if (comma <= 0)
                    continue;

                int itemId;
                if (!int.TryParse(line.Substring(0, comma), out itemId))
                    continue;

                var name = line.Substring(comma + 1).Trim();
                if (name.StartsWith("\"") && name.EndsWith("\"") && name.Length >= 2)
                {
                    name = name.Substring(1, name.Length - 2).Replace("\"\"", "\"");
                }

                if (!_names.ContainsKey(itemId))
                    _names.Add(itemId, name);
            }
        }
    }
}
