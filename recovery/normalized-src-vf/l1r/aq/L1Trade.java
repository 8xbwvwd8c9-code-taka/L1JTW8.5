package l1r.aq;

import l1r.ao.HistoryTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_TradeAddItem;
import l1r.be.S_TradeStatus;

public class L1Trade {
   public static void a(L1PcInstance var0, int var1, int var2) {
      if (var0.aO() != 0) {
         L1PcInstance var3 = (L1PcInstance)L1World.a().a(var0.aO());
         if (var3 == null) {
            b(var0);
         } else {
            L1ItemInstance var4 = var0.j().e(var1);
            if (var4 != null) {
               if (!var4.D()) {
                  var2 = Math.abs(var2);
                  var2 = Math.min(var2, var4.E());
                  if (var2 >= 0 && var2 <= 2000000000 && var4.E() >= 0 && var4.E() >= var2) {
                     if (var4.E() >= var2 && var2 >= 0) {
                        var0.j().a(var4, var2, var0.ax());
                        var0.a(new S_TradeAddItem(var4, var2, 0));
                        var3.a(new S_TradeAddItem(var4, var2, 1));
                     } else {
                        var0.a(new S_TradeStatus(1));
                        var3.a(new S_TradeStatus(1));
                        var0.c(false);
                        var3.c(false);
                        var0.al(0);
                        var3.al(0);
                     }
                  }
               }
            }
         }
      }
   }

   public static void a(L1PcInstance var0) {
      if (var0.aO() != 0) {
         L1PcInstance var1 = (L1PcInstance)L1World.a().a(var0.aO());
         if (var1 == null) {
            b(var0);
         } else {
            boolean var2 = true;

            for (L1ItemInstance var3 : var0.ax().d()) {
               if (var3 == null || var3.E() <= 0) {
                  var2 = false;
                  break;
               }
            }

            for (L1ItemInstance var5 : var1.ax().d()) {
               if (var5 == null || var5.E() <= 0) {
                  var2 = false;
                  break;
               }
            }

            if (!var2) {
               b(var0);
               b(var1);
            } else {
               for (L1ItemInstance var6 : var0.ax().d()) {
                  var0.ax().a(var6, var6.E(), var1.j());
                  HistoryTable.a().b(var0, "交易給(" + var1.eu() + ")", var6, var6.E());
               }

               for (L1ItemInstance var7 : var1.ax().d()) {
                  var1.ax().a(var7, var7.E(), var0.j());
                  HistoryTable.a().b(var1, "交易給(" + var0.eu() + ")", var7, var7.E());
               }

               var0.a(new S_TradeStatus(0));
               var1.a(new S_TradeStatus(0));
               var0.c(false);
               var1.c(false);
               var0.al(0);
               var1.al(0);
               var0.fg();
               var1.fg();
            }
         }
      }
   }

   public static void b(L1PcInstance var0) {
      if (var0.aO() != 0) {
         for (L1ItemInstance var1 : var0.ax().d()) {
            var0.ax().a(var1, var1.E(), var0.j());
         }

         var0.a(new S_TradeStatus(1));
         L1PcInstance var4 = (L1PcInstance)L1World.a().a(var0.aO());
         if (var4 != null) {
            for (L1ItemInstance var5 : var4.ax().d()) {
               var4.ax().a(var5, var5.E(), var4.j());
            }

            var4.a(new S_TradeStatus(1));
            var4.c(false);
            var4.al(0);
         }

         var0.c(false);
         var0.al(0);
      }
   }
}
