using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal sealed class SkillCatalogEntry
    {
        public int SkillId;
        public string Name = "";
        public int BuffDuration;
        public int HpConsume;
        public int MpConsume;
        public int ReuseDelay;
    }

    internal sealed class SkillCatalog
    {
        private readonly List<SkillCatalogEntry> _all =
            new List<SkillCatalogEntry>();

        private readonly Dictionary<int, SkillCatalogEntry> _byId =
            new Dictionary<int, SkillCatalogEntry>();

        public IList<SkillCatalogEntry> All
        {
            get { return _all.AsReadOnly(); }
        }

        public SkillCatalog(string appDir)
        {
            Load(Path.Combine(appDir, "skill-names.csv"));
        }

        public string Resolve(int skillId)
        {
            SkillCatalogEntry entry;
            return _byId.TryGetValue(skillId, out entry)
                ? entry.Name
                : "Skill#" + skillId;
        }

        public SkillCatalogEntry Get(int skillId)
        {
            SkillCatalogEntry entry;
            return _byId.TryGetValue(skillId, out entry)
                ? entry
                : null;
        }

        private void Load(string path)
        {
            if (!File.Exists(path))
                return;

            foreach (var raw in File.ReadAllLines(path, Encoding.UTF8))
            {
                var line = raw.Trim();
                if (line.Length == 0 ||
                    line.StartsWith("skill_id,", StringComparison.OrdinalIgnoreCase))
                    continue;

                var fields = ParseCsv(line);
                if (fields.Count < 6)
                    continue;

                int skillId;
                int buffDuration;
                int hpConsume;
                int mpConsume;
                int reuseDelay;

                if (!int.TryParse(fields[0], out skillId) ||
                    !int.TryParse(fields[2], out buffDuration) ||
                    !int.TryParse(fields[3], out hpConsume) ||
                    !int.TryParse(fields[4], out mpConsume) ||
                    !int.TryParse(fields[5], out reuseDelay))
                    continue;

                var entry = new SkillCatalogEntry
                {
                    SkillId = skillId,
                    Name = fields[1],
                    BuffDuration = buffDuration,
                    HpConsume = hpConsume,
                    MpConsume = mpConsume,
                    ReuseDelay = reuseDelay
                };

                if (_byId.ContainsKey(skillId))
                    continue;

                _byId.Add(skillId, entry);
                _all.Add(entry);
            }

            _all.Sort(
                delegate(
                    SkillCatalogEntry a,
                    SkillCatalogEntry b)
                {
                    return a.SkillId.CompareTo(b.SkillId);
                });
        }

        private static List<string> ParseCsv(string line)
        {
            var result = new List<string>();
            var current = new StringBuilder();
            var quoted = false;

            for (var i = 0; i < line.Length; i++)
            {
                var ch = line[i];

                if (quoted)
                {
                    if (ch == '"')
                    {
                        if (i + 1 < line.Length &&
                            line[i + 1] == '"')
                        {
                            current.Append('"');
                            i++;
                        }
                        else
                        {
                            quoted = false;
                        }
                    }
                    else
                    {
                        current.Append(ch);
                    }

                    continue;
                }

                if (ch == '"')
                {
                    quoted = true;
                    continue;
                }

                if (ch == ',')
                {
                    result.Add(current.ToString());
                    current.Length = 0;
                    continue;
                }

                current.Append(ch);
            }

            result.Add(current.ToString());
            return result;
        }
    }
}
