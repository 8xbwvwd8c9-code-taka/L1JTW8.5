package l1r.al;

import java.util.ArrayList;
import java.util.Collection;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;

public class L1Recall implements L1CommandExecutor {
   private L1Recall() {
   }

   public static L1CommandExecutor a() {
      return new L1Recall();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         Collection var4 = null;
         if (var3.equalsIgnoreCase("all")) {
            var4 = L1World.a().c();
         } else {
            var4 = new ArrayList<>();
            L1PcInstance var5 = L1World.a().a(var3);
            if (var5 == null) {
               var1.a(new S_SystemMessage("ID不存在。"));
               return;
            }

            var4.add(var5);
         }

         int var10 = 0;

         for (L1PcInstance var6 : var4) {
            if (var6.fr() != var1.fr()) {
               L1Teleport.a(var6, var1, 10);
               var1.a(new S_SystemMessage(var6.et() + "成功被您召喚回來。"));
               var6.a(new S_SystemMessage("您被召喚到GM身邊。"));
            }
         }
      } catch (Exception var8) {
         var1.a(new S_SystemMessage("請輸入: " + var2 + " all|玩家名稱。"));
      }
   }
}
