package l1r.al;

import l1r.ap.L1AttackerInstance;
import l1r.ap.L1KeeperInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.ap.L1TowerInstance;
import l1r.aq.L1Object;
import l1r.be.S_HPMeter;
import l1r.be.S_SystemMessage;

public class L1HpBar implements L1CommandExecutor {
   private L1HpBar() {
   }

   public static L1CommandExecutor a() {
      return new L1HpBar();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      if (var3.equalsIgnoreCase("on")) {
         var1.j(26001, 0);
      } else if (var3.equalsIgnoreCase("off")) {
         var1.bz(26001);

         for (L1Object var4 : var1.eq()) {
            if (a(var4)) {
               var1.a(new S_HPMeter(var4.fr(), 255, 255));
            }
         }
      } else {
         var1.a(new S_SystemMessage("請輸入 : " + var2 + " on|off 。"));
      }
   }

   public static boolean a(L1Object var0) {
      if (var0 instanceof L1MonsterInstance) {
         return true;
      } else if (var0 instanceof L1PcInstance) {
         return true;
      } else if (var0 instanceof L1SummonInstance) {
         return true;
      } else if (var0 instanceof L1PetInstance) {
         return true;
      } else if (var0 instanceof L1TowerInstance) {
         return true;
      } else {
         return var0 instanceof L1AttackerInstance ? true : var0 instanceof L1KeeperInstance;
      }
   }
}
