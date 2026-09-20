package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;

public class L1ToPC implements L1CommandExecutor {
   private L1ToPC() {
   }

   public static L1CommandExecutor a() {
      return new L1ToPC();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         L1PcInstance var4 = L1World.a().a(var3);
         if (var4 != null) {
            L1Teleport.a(var1, var4.fs(), var4.ft(), var4.fp(), 5, true);
            var1.a(new S_SystemMessage(var3 + "移動到玩家身邊。"));
         } else {
            var1.a(new S_SystemMessage(var3 + "不在線上。"));
         }
      } catch (Exception var5) {
         var1.a(new S_SystemMessage("請輸入: " + var2 + " 玩家名稱 。"));
      }
   }
}
