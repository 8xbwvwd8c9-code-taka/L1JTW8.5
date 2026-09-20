package l1r.al;

import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.be.S_DoActionGFX;
import l1r.be.S_SystemMessage;

public class L1Action implements L1CommandExecutor {
   private L1Action() {
   }

   public static L1CommandExecutor a() {
      return new L1Action();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         int var5 = Integer.parseInt(var4.nextToken(), 10);
         var1.a(new S_DoActionGFX(var1.fr(), var5));
      } catch (Exception var6) {
         var1.a(new S_SystemMessage("請輸入 " + var2 + " actid。"));
      }
   }
}
