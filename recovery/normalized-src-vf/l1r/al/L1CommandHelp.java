package l1r.al;

import java.util.List;
import l1r.ak.L1Commands;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Command;

public class L1CommandHelp implements L1CommandExecutor {
   private L1CommandHelp() {
   }

   public static L1CommandExecutor a() {
      return new L1CommandHelp();
   }

   private String a(List<L1Command> var1, String var2) {
      StringBuilder var3 = new StringBuilder();

      for (L1Command var4 : var1) {
         if (var3.length() > 0) {
            var3.append(var2);
         }

         var3.append(var4.a());
      }

      return var3.toString();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      List var4 = L1Commands.a(var1.az());
      var1.a(new S_SystemMessage(this.a(var4, ", ")));
   }
}
