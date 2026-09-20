package l1r.al;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import l1r.ao.NpcTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Npc;

public class L1SpawnCmd implements L1CommandExecutor {
   private L1SpawnCmd() {
   }

   public static L1CommandExecutor a() {
      return new L1SpawnCmd();
   }

   private void a(L1PcInstance var1, String var2) {
      String var3 = "請輸入: " + var2 + " npcid|name [數量] [範圍] 。";
      var1.a(new S_SystemMessage(var3));
   }

   private int a(String var1) {
      int var2 = 0;

      try {
         var2 = Integer.parseInt(var1);
      } catch (NumberFormatException var4) {
         var2 = NpcTable.a().a(var1);
      }

      return var2;
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         String var5 = var4.nextToken();
         int var6 = 1;
         if (var4.hasMoreTokens()) {
            var6 = Integer.parseInt(var4.nextToken());
         }

         int var7 = 0;
         if (var4.hasMoreTokens()) {
            var7 = Integer.parseInt(var4.nextToken(), 10);
         }

         int var8 = this.a(var5);
         L1Npc var9 = NpcTable.a().a(var8);
         if (var9 == null) {
            var1.a(new S_SystemMessage("找不到符合條件的NPC。"));
            return;
         }

         for (int var10 = 0; var10 < var6; var10++) {
            SpawnTable.a(var8, var1, var7, 0L);
         }

         String var14 = String.format("%s(%d) (%d) 召喚了。 (範圍:%d)", var9.c(), var8, var6, var7);
         var1.a(new S_SystemMessage(var14));
      } catch (NoSuchElementException var11) {
         this.a(var1, var2);
      } catch (NumberFormatException var12) {
         this.a(var1, var2);
      } catch (Exception var13) {
         var1.a(new S_SystemMessage(var2 + " 内部錯誤。"));
      }
   }
}
