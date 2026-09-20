package l1r.al;

import java.util.StringTokenizer;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;

public class L1InvGfxId implements L1CommandExecutor {
   private L1InvGfxId() {
   }

   public static L1CommandExecutor a() {
      return new L1InvGfxId();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         int var5 = Integer.parseInt(var4.nextToken(), 10);
         int var6 = Integer.parseInt(var4.nextToken(), 10);

         for (int var7 = 0; var7 < var6; var7++) {
            L1ItemInstance var8 = ItemTable.a().b(40005);
            var8.a().e(var5 + var7);
            var8.a().a(String.valueOf(var5 + var7));
            var1.j().d(var8);
         }
      } catch (Exception var9) {
         var1.a(new S_SystemMessage(var2 + " 請輸入 id 出現的數量。"));
      }
   }
}
