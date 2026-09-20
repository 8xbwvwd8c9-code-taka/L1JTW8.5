package l1r.al;

import java.util.StringTokenizer;
import l1r.ao.ExpTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;
import l1r.bi.IntRange;

public class L1Level implements L1CommandExecutor {
   private L1Level() {
   }

   public static L1CommandExecutor a() {
      return new L1Level();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         int var5 = Integer.parseInt(var4.nextToken());
         if (var5 == var1.ev()) {
            return;
         }

         if (!IntRange.a(var5, 1, 99)) {
            var1.a(new S_SystemMessage("請在1-99範圍內指定"));
            return;
         }

         var1.k(ExpTable.a(var5 - 1) + 1);
      } catch (Exception var6) {
         var1.a(new S_SystemMessage("請輸入 : " + var2 + " lv "));
      }
   }
}
