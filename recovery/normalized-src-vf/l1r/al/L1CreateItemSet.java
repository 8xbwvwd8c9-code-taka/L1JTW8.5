package l1r.al;

import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;

public class L1CreateItemSet implements L1CommandExecutor {
   private L1CreateItemSet() {
   }

   public static L1CommandExecutor a() {
      return new L1CreateItemSet();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         String var4 = new StringTokenizer(var3).nextToken();
         var1.a(new S_SystemMessage(var4 + " 是未定義的套裝。"));
      } catch (Exception var5) {
         var1.a(new S_SystemMessage("請輸入 .itemset 套裝名稱。"));
      }
   }
}
