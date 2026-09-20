package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class S_229 extends L1SkillExecutor {
   private final int a = 229;
   private final L1Skills b = SkillsTable.a().a(229);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         var7.j(229, this.b.v() * 1000);
         this.b(var1, var7, this.b, 1);
         new S_229.L1R_a(var1, var7, this.b.v(), this.b.u()).a();
      }
   }

   @Override
   public void a(L1Character var1) {
   }

   private class L1R_a extends Thread {
      private final L1Character b;
      private final L1Character c;
      private final int d;
      private final int e;

      public L1R_a(L1Character var2, L1Character var3, int var4, int var5) {
         this.b = var3;
         this.d = var4;
         this.c = var2;
         this.e = var5;
      }

      @Override
      public void run() {
         int var1 = this.c.ev() * 2 / this.d;

         for (int var2 = 0; var2 < this.d; var2++) {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var4) {
               return;
            }

            if (!this.b.bB(229)) {
               return;
            }

            if (this.b.eX()) {
               return;
            }

            var1 += Random.a(10);
            if (this.b instanceof L1PcInstance) {
               L1PcInstance var3 = (L1PcInstance)this.b;
               var3.a(this.c, var1, false);
               var3.a(new S_SkillSound(var3.fr(), this.e));
            } else if (this.b instanceof L1MonsterInstance) {
               L1MonsterInstance var5 = (L1MonsterInstance)this.b;
               var5.b(this.c, var1);
            }

            this.b.b(new S_SkillSound(this.b.fr(), this.e));
         }
      }

      public void a() {
         GeneralThreadPool.a().a(this);
      }
   }
}
