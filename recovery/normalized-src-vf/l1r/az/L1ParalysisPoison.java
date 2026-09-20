package l1r.az;

import java.util.concurrent.ScheduledFuture;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_Paralysis;
import l1r.bi.GeneralThreadPool;

public class L1ParalysisPoison extends L1Poison {
   private final L1Character a;
   private final int b;
   private final int c;
   private ScheduledFuture<?> d;

   private L1ParalysisPoison(L1Character var1, int var2, int var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.c();
   }

   public static boolean a(L1Character var0, int var1, int var2) {
      if (!L1Poison.a(var0)) {
         return false;
      }

      var0.a(new L1ParalysisPoison(var0, var1, var2));
      return true;
   }

   private void c() {
      a(this.a, 212);
      this.a.y(1);
      if (this.a instanceof L1PcInstance) {
         this.a.j(1008, 0);
         this.d = GeneralThreadPool.a().a(new L1ParalysisPoison.a(null), this.b);
      }
   }

   @Override
   public void b() {
      if (this.d != null) {
         this.d.cancel(true);
         this.d = null;
      }

      this.a.bA(1009);
      if (!this.a.eX()) {
         if (this.a instanceof L1PcInstance) {
            L1PcInstance var1 = (L1PcInstance)this.a;
            var1.a(new S_Paralysis(1, false));
         }

         this.a.y(0);
         this.a.a((L1Poison)null);
      }
   }

   @Override
   public int a() {
      return 1;
   }

   private class a implements Runnable {
      private a() {
      }

      @Override
      public void run() {
         L1ParalysisPoison.this.a.y(2);
         L1ParalysisPoison.this.a.bA(1008);
         if (!L1ParalysisPoison.this.a.eX()) {
            L1ParalysisPoison.this.a.j(1009, 0);
            if (L1ParalysisPoison.this.a instanceof L1PcInstance) {
               L1PcInstance var1 = (L1PcInstance)L1ParalysisPoison.this.a;
               var1.a(new S_Paralysis(1, true));
               L1ParalysisPoison.this.d = GeneralThreadPool.a().a(L1ParalysisPoison.this.new b(null), L1ParalysisPoison.this.c);
            }
         }
      }

      // $VF: synthetic method
      a(L1ParalysisPoison.a var2) {
         this();
      }
   }

   private class b implements Runnable {
      private b() {
      }

      @Override
      public void run() {
         L1ParalysisPoison.this.b();
      }

      // $VF: synthetic method
      b(L1ParalysisPoison.b var2) {
         this();
      }
   }
}
