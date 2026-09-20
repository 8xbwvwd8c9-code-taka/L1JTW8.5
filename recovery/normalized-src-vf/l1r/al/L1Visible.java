package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.be.S_Invis;
import l1r.be.S_OtherCharPacks;
import l1r.be.S_SystemMessage;

public class L1Visible implements L1CommandExecutor {
   private L1Visible() {
   }

   public static L1CommandExecutor a() {
      return new L1Visible();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         var1.b(false);
         var1.a(new S_Invis(var1.fr(), 0));
         var1.b(new S_OtherCharPacks(var1));
         var1.a(new S_SystemMessage("隱形狀態解除。"));
      } catch (Exception var5) {
         var1.a(new S_SystemMessage(var2 + " 玩家名稱"));
      }
   }
}
