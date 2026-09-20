package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.ap.L1TrapInstance;
import l1r.aq.L1Object;
import l1r.be.S_RemoveObject;
import l1r.be.S_SystemMessage;

public class L1ShowTrap implements L1CommandExecutor {
   private L1ShowTrap() {
   }

   public static L1CommandExecutor a() {
      return new L1ShowTrap();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      if (var3.equalsIgnoreCase("on")) {
         var1.j(26002, 0);
      } else if (var3.equalsIgnoreCase("off")) {
         var1.bz(26002);

         for (L1Object var4 : var1.eq()) {
            if (var4 instanceof L1TrapInstance) {
               var1.d(var4);
               var1.a(new S_RemoveObject(var4));
            }
         }
      } else {
         var1.a(new S_SystemMessage("請輸入: " + var2 + " on|off 。"));
      }
   }
}
