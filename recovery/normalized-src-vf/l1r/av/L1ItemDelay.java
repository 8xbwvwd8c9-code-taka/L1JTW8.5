package l1r.av;

import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.bi.GeneralThreadPool;
import l1r.bj.ClientThread;

public class L1ItemDelay {
   private L1ItemDelay() {
   }

   public static void a(ClientThread var0, L1ItemInstance var1) {
      int var2 = 0;
      int var3 = 0;
      L1PcInstance var4 = var0.f();
      if (var1.f()) {
         var2 = var1.a().aJ();
         var3 = var1.a().aK();
      } else {
         if (var1.g()) {
            return;
         }

         if (var1.h()) {
            if (var1.N() != 20077 && var1.N() != 20062 && var1.N() != 120077) {
               return;
            }

            if (var1.D() && !var4.ff()) {
               var4.O();
            }
         }
      }

      L1ItemDelay.a var5 = new L1ItemDelay.a(var4, var2, null);
      var4.a(var2, var5);
      GeneralThreadPool.a().a(var5, var3);
   }

   public static class a implements Runnable {
      private final int a;
      private final L1Character b;

      private a(L1Character var1, int var2) {
         this.b = var1;
         this.a = var2;
      }

      @Override
      public void run() {
         this.a(this.a);
      }

      private void a(int var1) {
         this.b.bE(var1);
      }

      // $VF: synthetic method
      a(L1Character var1, int var2, L1ItemDelay.a var3) {
         this(var1, var2);
      }
   }
}
