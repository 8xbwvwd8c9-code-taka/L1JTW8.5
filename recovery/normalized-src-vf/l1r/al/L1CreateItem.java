package l1r.al;

import java.util.StringTokenizer;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Item;

public class L1CreateItem implements L1CommandExecutor {
   private L1CreateItem() {
   }

   public static L1CommandExecutor a() {
      return new L1CreateItem();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         String var5 = var4.nextToken();
         int var6 = 1;
         if (var4.hasMoreTokens()) {
            var6 = Integer.parseInt(var4.nextToken());
         }

         int var7 = 0;
         if (var4.hasMoreTokens()) {
            var7 = Integer.parseInt(var4.nextToken());
         }

         int var8 = 1;
         if (var4.hasMoreTokens()) {
            var8 = Integer.parseInt(var4.nextToken());
         }

         String var9 = "";
         if (var4.hasMoreTokens()) {
            var9 = var4.nextToken();
         }

         int var10 = 0;
         if (var4.hasMoreTokens()) {
            var10 = Integer.parseInt(var4.nextToken());
         }

         int var11 = 0;

         try {
            var11 = Integer.parseInt(var5);
         } catch (NumberFormatException var15) {
            var11 = ItemTable.a().b(var5);
            if (var11 == 0) {
               var1.a(new S_SystemMessage("找不到符合條件項目。"));
               return;
            }
         }

         L1Item var12 = ItemTable.a().a(var11);
         if (var12 != null) {
            if (var12.aF()) {
               L1ItemInstance var13 = ItemTable.a().b(var11);
               var13.a(0);
               var13.e(var6);
               var13.f(var8);
               var13.a(true);
               if (var1.j().a(var13, var6) == 0) {
                  var1.j().d(var13);
                  var1.a(new S_ServerMessage(403, var13.s() + "(ID:" + var11 + ")"));
               }
            } else {
               L1ItemInstance var18 = null;

               int var14;
               for (var14 = 0; var14 < var6; var14++) {
                  var18 = ItemTable.a().b(var11);
                  var18.a(var7);
                  var18.f(var8);
                  var18.n();
                  if (var18.g()) {
                     if (!var9.equalsIgnoreCase("地") && !var9.equalsIgnoreCase("1")) {
                        if (!var9.equalsIgnoreCase("火") && !var9.equalsIgnoreCase("2")) {
                           if (!var9.equalsIgnoreCase("水") && !var9.equalsIgnoreCase("4")) {
                              if ((var9.equalsIgnoreCase("風") || var9.equalsIgnoreCase("8")) && var10 > 0 && var10 <= 3) {
                                 var18.h(8);
                                 var18.i(var10);
                              }
                           } else if (var10 > 0 && var10 <= 3) {
                              var18.h(4);
                              var18.i(var10);
                           }
                        } else if (var10 > 0 && var10 <= 3) {
                           var18.h(2);
                           var18.i(var10);
                        }
                     } else if (var10 > 0 && var10 <= 3) {
                        var18.h(1);
                        var18.i(var10);
                     }
                  }

                  var18.a(true);
                  if (var1.j().a(var18, 1) != 0) {
                     break;
                  }

                  var1.j().d(var18);
               }

               if (var14 > 0 && var18 != null) {
                  var1.a(new S_ServerMessage(403, var18.s() + "(ID:" + var11 + ")"));
               }
            }
         } else {
            var1.a(new S_SystemMessage("指定的道具編號不存在"));
         }
      } catch (Exception var16) {
         var1.a(new S_SystemMessage("請輸入 .item itemid|name [數量] [強化等級] [鑑定狀態] [武器屬性] [屬性等級]。"));
      }
   }
}
