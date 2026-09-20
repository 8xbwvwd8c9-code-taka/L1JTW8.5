package l1r.al;

import java.util.ArrayList;
import java.util.StringTokenizer;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_RetrieveList;
import l1r.be.S_SystemMessage;

public class L1GfxInvList implements L1CommandExecutor {
   private L1GfxInvList() {
   }

   public static L1CommandExecutor a() {
      return new L1GfxInvList();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         int var5 = Integer.parseInt(var4.nextToken(), 10);
         int var6 = 250;
         ArrayList var7 = new ArrayList<>();

         for (int var8 = 0; var8 < var6; var8++) {
            L1ItemInstance var9 = ItemTable.a().b(40005);
            var9.e(var5 + var8);
            var9.f(1);
            var7.add(var9);
         }

         var1.a(new S_RetrieveList(var7));
      } catch (Exception var10) {
         var1.a(new S_SystemMessage(var2 + " 請輸入 id 出現的數量。"));
      }
   }
}
