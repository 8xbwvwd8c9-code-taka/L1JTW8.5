package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;

public class L1Describe implements L1CommandExecutor {
   private L1Describe() {
   }

   public static L1CommandExecutor a() {
      return new L1Describe();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringBuilder var4 = new StringBuilder();
         var1.a(new S_SystemMessage("-- describe: " + var1.et() + " --"));
         int var5 = var1.ar() + var1.j().l();
         int var6 = var1.as() + var1.j().m();
         var4.append("Dmg: +" + var1.eR() + " / ");
         var4.append("Hit: +" + var1.eT() + " / ");
         var4.append("MR: " + var1.W_() + " / ");
         var4.append("HPR: " + var5 + " / ");
         var4.append("MPR: " + var6 + " / ");
         var4.append("Karma: " + var1.P() + " / ");
         var4.append("Item: " + var1.j().c() + " / ");
         var1.a(new S_SystemMessage(var4.toString()));
      } catch (Exception var7) {
         var1.a(new S_SystemMessage(var2 + " 指令錯誤"));
      }
   }
}
