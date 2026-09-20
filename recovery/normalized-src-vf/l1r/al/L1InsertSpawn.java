package l1r.al;

import java.util.StringTokenizer;
import l1r.ao.NpcSpawnTable;
import l1r.ao.NpcTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Npc;

public class L1InsertSpawn implements L1CommandExecutor {
   private L1InsertSpawn() {
   }

   public static L1CommandExecutor a() {
      return new L1InsertSpawn();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      String var4 = null;

      try {
         try {
            StringTokenizer var5 = new StringTokenizer(var3);
            String var6 = var5.nextToken();
            int var7 = Integer.parseInt(var5.nextToken().trim());
            L1Npc var8 = NpcTable.a().a(var7);
            if (var8 == null) {
               var4 = "找不到符合條件的NPC。";
               return;
            }

            if (var6.equalsIgnoreCase("mob")) {
               if (!var8.d().equals("L1Monster")) {
                  var4 = "指定的NPC不是L1Monster類型。";
                  return;
               }

               SpawnTable.a(var1, var8);
            } else if (var6.equalsIgnoreCase("npc")) {
               NpcSpawnTable.a().a(var1, var8);
            }

            SpawnTable.a(var7, var1, 0, 0L);
            var4 = var8.c() + " (" + var7 + ") " + "新增到資料庫中。";
         } catch (Exception var12) {
            var4 = "請輸入 : " + var2 + " mob|npc NPCID 。";
         }
      } finally {
         if (var4 != null) {
            var1.a(new S_SystemMessage(var4));
         }
      }
   }
}
