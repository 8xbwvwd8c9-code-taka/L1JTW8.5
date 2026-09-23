using System;
using System.IO;
using System.Text;

namespace L1JTW850Launcher
{
    internal static class AutoFeatureMatrix
    {
        public static string Refresh(string appDir)
        {
            var sb = new StringBuilder();
            sb.AppendLine("TIME=" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            sb.AppendLine("MODE=AUTO_FEATURE_DEPENDENCY_MATRIX");
            sb.AppendLine("MEMORY_WRITE=NO");
            sb.AppendLine();

            var hpRaw = Contains(appDir, "runtime_hpmp_guard_evidence.txt", "RAW_MAP_ALLOWED=1");
            var inventorySeed = Contains(appDir, "auto_inventory_seedless_evidence.txt", "STATUS=PASS_CANDIDATES");
            var inventoryHistory =
                Contains(appDir, "auto_inventory_history_evidence.txt", "STATUS=PASS_DYNAMIC_CANDIDATES") ||
                Contains(appDir, "auto_inventory_history_evidence.txt", "STATUS=PASS_CANDIDATES");
            var inventoryMapPresent = File.Exists(Path.Combine(appDir, "inventory-map.ini"));
            var buffMarkers = ReadIntValue(appDir, "auto_buff_receive_evidence.txt", "BUFF_MARKERS=");

            string inventoryState;
            if (inventoryMapPresent)
                inventoryState = "MAP_PRESENT_NOT_YET_SEMANTICALLY_PROVEN";
            else if (inventorySeed && inventoryHistory)
                inventoryState = "DYNAMIC_CANDIDATES_ONLY";
            else
                inventoryState = "DISCOVERY_RUNNING";

            sb.AppendLine("[FOUNDATION]");
            sb.AppendLine("HPMP_TRACKING=" + (hpRaw ? "READY_RAW_MAP" : "DISCOVERY_RUNNING"));
            sb.AppendLine("INVENTORY_TRACKING=" + inventoryState);
            sb.AppendLine("BUFF_STATE_TRACKING=" + (buffMarkers > 0 ? "RECV_MARKER_CANDIDATE" : "DISCOVERY_RUNNING"));
            sb.AppendLine("PLAYER_IDENTITY=WAIT_WP3");
            sb.AppendLine("ITEM_USE_BRIDGE=UNMAPPED");
            sb.AppendLine("SKILL_USE_BRIDGE=UNMAPPED");
            sb.AppendLine();

            sb.AppendLine("[FEATURES]");
            sb.AppendLine("AUTO_CAST=WAIT_BUFF_STATE+SKILL_USE+PLAYER_IDENTITY");
            sb.AppendLine("AUTO_DELETE=WAIT_FORMAL_INVENTORY+DELETE_SEND_BRIDGE");
            sb.AppendLine("AUTO_DISSOLVE=WAIT_FORMAL_INVENTORY+ITEM_USE_BRIDGE+RESOLVENT_POLICY");
            sb.AppendLine("MAGIC_DOLL_MAINTAIN=WAIT_DOLL_ACTIVE_STATE+FORMAL_INVENTORY+ITEM_USE_BRIDGE");
            sb.AppendLine("PET_HP_MAINTAIN=WAIT_PET_ENTITY_STATE+PET_HP_PERCENT+ACTION_BRIDGE");
            sb.AppendLine("SUMMON_MAINTAIN=WAIT_SUMMON_ENTITY_STATE+SKILL_USE_BRIDGE");
            sb.AppendLine("ELF_SPIRIT_MAINTAIN=WAIT_SUMMON_ENTITY_STATE+SKILL_USE_BRIDGE");
            sb.AppendLine();

            sb.AppendLine("[RECOVERED_850_PROTOCOL_FACTS]");
            sb.AppendLine("C_USE_SKILL_OPCODE=128");
            sb.AppendLine("C_DELETE_INVENTORY_ITEM_OPCODE=145");
            sb.AppendLine("C_DELETE_INVENTORY_ITEM_SHAPE=count + repeated(objectId,deleteCount)");
            sb.AppendLine("S_PET_PACK=entity pack with owner-visible HP percent");
            sb.AppendLine("S_SUMMON_PACK=entity pack with owner-visible HP percent");
            sb.AppendLine("S_DOLL_PACK=entity pack with master identity");
            sb.AppendLine("RESOLVENT_TABLE=SERVER_SIDE_PRESENT");
            sb.AppendLine();

            sb.AppendLine("ACTION_POLICY=DISCOVER_AND_VALIDATE_IN_PARALLEL; DYNAMIC_CANDIDATES_ARE_NOT_FORMAL_INVENTORY; DO_NOT_ENABLE_DESTRUCTIVE_OR_CAST_ACTIONS_UNTIL_BRIDGES_ARE_PROVEN");

            var text = sb.ToString();
            try
            {
                File.WriteAllText(
                    Path.Combine(appDir, "auto_feature_matrix_evidence.txt"),
                    text,
                    new UTF8Encoding(false));
            }
            catch
            {
            }
            return text;
        }

        private static bool Contains(string appDir, string fileName, string token)
        {
            try
            {
                var path = Path.Combine(appDir, fileName);
                return File.Exists(path) &&
                    File.ReadAllText(path).IndexOf(token, StringComparison.OrdinalIgnoreCase) >= 0;
            }
            catch
            {
                return false;
            }
        }

        private static int ReadIntValue(string appDir, string fileName, string prefix)
        {
            try
            {
                var path = Path.Combine(appDir, fileName);
                if (!File.Exists(path)) return 0;
                foreach (var raw in File.ReadAllLines(path))
                {
                    var line = raw.Trim();
                    if (!line.StartsWith(prefix, StringComparison.OrdinalIgnoreCase)) continue;
                    int value;
                    return int.TryParse(line.Substring(prefix.Length).Trim(), out value) ? value : 0;
                }
            }
            catch
            {
            }
            return 0;
        }
    }
}
