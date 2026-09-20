package l1r.aq;

import java.util.concurrent.ScheduledFuture;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_PacketBox;
import l1r.be.S_Paralysis;
import l1r.be.S_ServerMessage;
import l1r.bi.GeneralThreadPool;

public class L1Paralysis {
   private final L1Character a;
   private final int b;
   private final int c;
   private ScheduledFuture<?> d;

   private L1Paralysis(L1Character var1, int var2, int var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.c();
   }

   private void c() {
      if (this.a instanceof L1PcInstance) {
         L1PcInstance var1 = (L1PcInstance)this.a;
         var1.a(new S_ServerMessage(212));
      }

      this.a.y(2);
      this.a.j(1010, 0);
      this.d = GeneralThreadPool.a().a(new L1Paralysis.a(null), this.b);
   }

   public void a() {
      if (this.d != null) {
         this.d.cancel(true);
         this.d = null;
      }

      this.a.bA(1010);
      this.a.bA(1011);
      this.a.V(false);
      this.a.y(0);
      this.a.a((L1Paralysis)null);
      if (this.a instanceof L1PcInstance) {
         L1PcInstance var1 = (L1PcInstance)this.a;
         var1.a(new S_PacketBox(161, 2, 0));
         var1.a(new S_Paralysis(1, false));
      }
   }

   public static boolean a(L1Character var0, int var1, int var2) {
      if (!(var0 instanceof L1PcInstance) && !(var0 instanceof L1MonsterInstance)) {
         return false;
      } else if (!var0.bB(1010) && !var0.bB(1011)) {
         var0.a(new L1Paralysis(var0, var1, var2));
         return true;
      } else {
         return false;
      }
   }

   public int b() {
      return 2;
   }

   private class a implements Runnable {
      private a() {
      }

      @Override
      public void run() {
         if (L1Paralysis.this.a instanceof L1PcInstance) {
            L1PcInstance var1 = (L1PcInstance)L1Paralysis.this.a;
            if (!var1.eX()) {
               var1.a(new S_Paralysis(1, true));
               var1.a(new S_PacketBox(161, 2, L1Paralysis.this.c / 1000));
            }
         }

         L1Paralysis.this.a.V(true);
         L1Paralysis.this.a.bA(1010);
         L1Paralysis.this.a.j(1011, 0);
         L1Paralysis.this.d = GeneralThreadPool.a().a(L1Paralysis.this.new b(null), L1Paralysis.this.c);
      }

      // $VF: synthetic method
      a(L1Paralysis.a var2) {
         this();
      }
   }

   private class b implements Runnable {
      private b() {
      }

      @Override
      public void run() {
         L1Paralysis.this.a();
      }

      // $VF: synthetic method
      b(L1Paralysis.b var2) {
         this();
      }
   }
}
