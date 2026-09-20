package l1r.az;

import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_PacketBox;
import l1r.bi.GeneralThreadPool;

public class L1DamagePoison extends L1Poison {
   private Thread a;
   private final L1Character b;
   private final L1Character c;
   private final int d;
   private final int e;

   private L1DamagePoison(L1Character var1, L1Character var2, int var3, int var4, int var5) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.a(var5);
   }

   private boolean b(L1Character var1) {
      return var1 instanceof L1PcInstance || var1 instanceof L1MonsterInstance;
   }

   private void a(int var1) {
      this.c.j(11, var1 * 1000);
      this.c.y(1);
      if (this.c instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)this.c;
         var2.a(new S_PacketBox(161, 1, var1));
      }

      if (this.b(this.c)) {
         this.a = new L1DamagePoison.a(null);
         GeneralThreadPool.a().a(this.a);
      }
   }

   public static boolean a(L1Character var0, L1Character var1, int var2, int var3, int var4) {
      if (!a(var1)) {
         return false;
      }

      var1.a(new L1DamagePoison(var0, var1, var2, var3, var4));
      return true;
   }

   @Override
   public int a() {
      return 1;
   }

   @Override
   public void b() {
      if (this.a != null) {
         this.a.interrupt();
      }

      this.c.y(0);
      this.c.bA(11);
      this.c.a((L1Poison)null);
      if (this.c instanceof L1PcInstance) {
         L1PcInstance var1 = (L1PcInstance)this.c;
         var1.a(new S_PacketBox(161, 1, 0));
      }
   }

   private class a extends Thread {
      private a() {
      }

      @Override
      public void run() {
         while (true) {
            try {
               Thread.sleep(L1DamagePoison.this.d);
            } catch (InterruptedException var2) {
               break;
            }

            if (L1DamagePoison.this.c.bB(11)) {
               if (!(L1DamagePoison.this.c instanceof L1PcInstance)) {
                  if (!(L1DamagePoison.this.c instanceof L1MonsterInstance)) {
                     continue;
                  }

                  L1MonsterInstance var3 = (L1MonsterInstance)L1DamagePoison.this.c;
                  var3.b(L1DamagePoison.this.b, L1DamagePoison.this.e);
                  if (!var3.eX()) {
                     continue;
                  }

                  return;
               } else {
                  L1PcInstance var1 = (L1PcInstance)L1DamagePoison.this.c;
                  var1.a(L1DamagePoison.this.b, L1DamagePoison.this.e, false);
                  if (!var1.eX()) {
                     continue;
                  }
               }
            }
            break;
         }

         L1DamagePoison.this.b();
      }

      // $VF: synthetic method
      a(L1DamagePoison.a var2) {
         this();
      }
   }
}
