package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.be.S_Invis;
import l1r.be.S_RemoveObject;
import l1r.be.S_SystemMessage;

public class L1Invisible implements L1CommandExecutor {
   private L1Invisible() {
   }

   public static L1CommandExecutor a() {
      return new L1Invisible();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         var1.b(true);
         var1.a(new S_Invis(var1.fr(), 1));
         var1.b(new S_RemoveObject(var1));
         var1.a(new S_SystemMessage("現在是隱身狀態。"));
      } catch (Exception var5) {
         var1.a(new S_SystemMessage(var2 + " 指令錯誤"));
      }
   }
}
