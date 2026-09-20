package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_RemoveObject;
import l1r.be.S_SystemMessage;

public class L1FindInvis implements L1CommandExecutor {
   private L1FindInvis() {
   }

   public static L1CommandExecutor a() {
      return new L1FindInvis();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      if (var3.equalsIgnoreCase("on")) {
         var1.j(26003, 0);
         var1.es();
         var1.h();
      } else if (var3.equalsIgnoreCase("off")) {
         var1.bz(26003);

         for (L1PcInstance var4 : L1World.a().f(var1)) {
            if (var4.ff()) {
               var1.a(new S_RemoveObject(var4));
            }
         }
      } else {
         var1.a(new S_SystemMessage(var2 + "請輸入  on|off 。"));
      }
   }
}
