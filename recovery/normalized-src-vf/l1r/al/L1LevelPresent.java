package l1r.al;

import java.util.StringTokenizer;
import l1r.ao.ItemTable;
import l1r.ap.L1PcInstance;
import l1r.au.L1AccountInventory;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Item;

public class L1LevelPresent implements L1CommandExecutor {
   private L1LevelPresent() {
   }

   public static L1CommandExecutor a() {
      return new L1LevelPresent();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         int var5 = Integer.parseInt(var4.nextToken(), 10);
         int var6 = Integer.parseInt(var4.nextToken(), 10);
         int var7 = Integer.parseInt(var4.nextToken(), 10);
         int var8 = Integer.parseInt(var4.nextToken(), 10);
         int var9 = Integer.parseInt(var4.nextToken(), 10);
         L1Item var10 = ItemTable.a().a(var7);
         if (var10 == null) {
            var1.a(new S_SystemMessage("不存在的道具編號。"));
            return;
         }

         L1AccountInventory.a(var5, var6, var7, var8, var9);
         var1.a(new S_SystemMessage(var10.h() + "數量" + var9 + "個發送出去了。(Lv" + var5 + "～" + var6 + ")"));
      } catch (Exception var11) {
         var1.a(new S_SystemMessage("請輸入 .lvpresent minlvl maxlvl 道具編號  強化等級 數量。"));
      }
   }
}
