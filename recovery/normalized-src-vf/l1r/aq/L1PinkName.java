package l1r.aq;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1GuardInstance;
import l1r.ap.L1PcInstance;
import l1r.as.L1CastleWar;
import l1r.be.S_PinkName;
import l1r.bi.GeneralThreadPool;

public class L1PinkName {
   private static final Logger a = Logger.getLogger(L1PinkName.class.getName());

   private L1PinkName() {
   }

   public static void a(L1PcInstance var0, L1Character var1) {
      if (var0 != null && var1 != null) {
         L1PcInstance var2 = (L1PcInstance)var1;
         if (var0.fr() != var2.fr()) {
            if (var2.cp() != var0.fr()) {
               boolean var3 = L1CastleWar.a().a((L1Character)var0);
               if (var0.aF() > 0 && var2.aF() > 0) {
                  L1War var4 = L1World.a().c(var0.aG());
                  if (var4 != null && var4.c(var0.aG(), var2.aG())) {
                     var3 = true;
                  }
               }

               if (var0.fa() >= 0 && !var0.aT() && var2.fa() >= 0 && !var2.aT() && var0.ep() == 0 && var2.ep() == 0 && !var3) {
                  var2.f(true);
                  var2.a(new S_PinkName(var2.fr(), 180));
                  if (!var2.aA()) {
                     var2.b(new S_PinkName(var2.fr(), 180));
                  }

                  GeneralThreadPool.a().b(new L1PinkName.L1R_a(var2, null));
               }

               if (var2.aT()) {
                  for (L1Object var7 : L1World.a().e(var2)) {
                     if (var7 instanceof L1GuardInstance) {
                        L1GuardInstance var6 = (L1GuardInstance)var7;
                        var6.d(var2);
                     }
                  }
               }
            }
         }
      }
   }

   private static class L1R_a implements Runnable {
      private L1PcInstance a = null;

      private L1R_a(L1PcInstance var1) {
         this.a = var1;
      }

      @Override
      public void run() {
         for (int var1 = 0; var1 < 180; var1++) {
            try {
               Thread.sleep(1000L);
               if (this.a.eX()) {
                  break;
               }

               if (this.a.fa() < 0) {
                  this.a.f(false);
                  break;
               }
            } catch (Exception var3) {
               L1PinkName.a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
               break;
            }
         }

         this.a(this.a);
      }

      private void a(L1PcInstance var1) {
         var1.a(new S_PinkName(var1.fr(), 0));
         var1.b(new S_PinkName(var1.fr(), 0));
         var1.f(false);
      }

      // $VF: synthetic method
      L1R_a(L1PcInstance var1, L1PinkName.L1R_a var2) {
         this(var1);
      }
   }
}
