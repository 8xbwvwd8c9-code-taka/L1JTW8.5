package l1r.al;

import java.util.StringTokenizer;
import l1r.ao.NpcTable;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Npc;

public class L1Summon implements L1CommandExecutor {
   private L1Summon() {
   }

   public static L1Summon a() {
      return new L1Summon();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         String var5 = var4.nextToken();
         int var6 = 0;

         try {
            var6 = Integer.parseInt(var5);
         } catch (NumberFormatException var11) {
            var6 = NpcTable.a().a(var5);
            if (var6 == 0) {
               var1.a(new S_SystemMessage("找不到符合條件的NPC。"));
               return;
            }
         }

         int var7 = 1;
         if (var4.hasMoreTokens()) {
            var7 = Integer.parseInt(var4.nextToken());
         }

         L1Npc var8 = NpcTable.a().a(var6);

         for (int var9 = 0; var9 < var7; var9++) {
            L1SummonInstance var10 = new L1SummonInstance(var8, var1);
            var10.o(0);
         }

         var5 = NpcTable.a().a(var6).c();
         var1.a(new S_SystemMessage(var5 + "(ID:" + var6 + ") (" + var7 + ") 召喚了。"));
      } catch (Exception var12) {
         var1.a(new S_SystemMessage("請輸入" + var2 + " npcid|name [數量] 。"));
      }
   }
}
