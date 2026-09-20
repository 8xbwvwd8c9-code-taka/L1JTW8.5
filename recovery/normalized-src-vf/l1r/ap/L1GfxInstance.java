package l1r.ap;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aq.L1Character;
import l1r.aq.L1World;
import l1r.be.S_NPCPack;
import l1r.be.S_SkillSound;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;

public class L1GfxInstance extends L1NpcInstance {
   private static final Logger y = Logger.getLogger(L1GfxInstance.class.getName());
   private boolean z = false;
   private int A = 0;

   public L1GfxInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void c(L1PcInstance var1) {
      var1.a(new S_SkillSound(var1.fr(), this.A));
      L1Character var2 = new L1Character();
      var2.cG(var1.fs() + 5);
      var2.cH(var1.ft() + 5);
      var1.ct(var1.a(var2));
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_NPCPack(this));
      var1.a(new S_SkillSound(this.fr(), this.A));
      if (!this.z) {
         this.z = true;
         new L1GfxInstance.L1R_a(null).a();
      }
   }

   private void h() {
      for (L1PcInstance var1 : L1World.a().f(this)) {
         var1.a(new S_SkillSound(this.fr(), this.A));
      }
   }

   public int f() {
      return this.A;
   }

   public void b(int var1) {
      this.A = var1;
   }

   private class L1R_a implements Runnable {
      private L1R_a() {
      }

      public void a() {
         GeneralThreadPool.a().a(this);
      }

      @Override
      public void run() {
         try {
            while (L1GfxInstance.this.z) {
               L1GfxInstance.this.h();
               Thread.sleep(2000L);
            }
         } catch (Exception var2) {
            L1GfxInstance.y.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      // $VF: synthetic method
      L1R_a(L1GfxInstance.L1R_a var2) {
         this();
      }
   }
}
