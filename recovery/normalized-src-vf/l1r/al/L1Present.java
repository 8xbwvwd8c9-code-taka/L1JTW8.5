package l1r.al;

import java.util.StringTokenizer;
import l1r.ao.ItemTable;
import l1r.ap.L1PcInstance;
import l1r.au.L1AccountInventory;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Item;

public class L1Present implements L1CommandExecutor {
   private L1Present() {
   }

   public static L1CommandExecutor a() {
      return new L1Present();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         String var5 = var4.nextToken();
         int var6 = Integer.parseInt(var4.nextToken(), 10);
         int var7 = Integer.parseInt(var4.nextToken(), 10);
         int var8 = Integer.parseInt(var4.nextToken(), 10);
         L1Item var9 = ItemTable.a().a(var6);
         if (var9 == null) {
            var1.a(new S_SystemMessage("不存在的道具編號。"));
            return;
         }

         L1AccountInventory.a(var5, var6, var7, var8);
         var1.a(new S_SystemMessage(var9.j() + "數量" + var8 + "個發送出去了。", true));
      } catch (Exception var10) {
         var1.a(new S_SystemMessage("請輸入 : .present 帳號 道具編號  強化等級 數量。（* 等於所有帳號）"));
      }
   }
}
