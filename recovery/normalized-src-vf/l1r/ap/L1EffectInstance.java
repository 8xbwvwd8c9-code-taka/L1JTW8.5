package l1r.ap;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.as.L1CastleWar;
import l1r.az.L1DamagePoison;
import l1r.bb.CubeEffectTimer;
import l1r.be.S_DoActionGFX;
import l1r.be.S_OwnCharAttrDef;
import l1r.be.S_RemoveObject;
import l1r.be.S_SkillSound;
import l1r.bf.L1SkillExecutor;
import l1r.bf.S_058;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;

public class L1EffectInstance extends L1NpcInstance {
   private static final int y = 1000;
   private static final int z = 500;
   private static final int A = 8000;
   private static final int B = 1000;
   private L1PcInstance C;
   private int D;

   public L1EffectInstance(L1Npc var1) {
      super(var1);
   }

   public void f() {
      if (this.fe() == 168) {
         GeneralThreadPool.a().a(new L1EffectInstance.b(this, null), 0L);
      } else if (this.fe() == 6706 || this.fe() == 6712 || this.fe() == 6718 || this.fe() == 6724) {
         GeneralThreadPool.a().a(new L1EffectInstance.a(this, null), 0L);
      } else if (this.fe() == 1263) {
         GeneralThreadPool.a().a(new L1EffectInstance.c(this, null), 0L);
      }
   }

   @Override
   public void aa_() {
      this.k(true);
      if (this.y() != null) {
         this.y().g();
      }

      this.t();
      this.k = null;
      L1World.a().d(this);
      L1World.a().b(this);

      for (L1PcInstance var1 : L1World.a().f(this)) {
         var1.d(this);
         var1.a(new S_RemoveObject(this));
      }

      this.es();
   }

   private void a(L1Character var1, L1Character var2) {
      int var3 = SkillsTable.a().a(this.h()).t();
      L1PcInstance var4 = null;
      if (this.fe() == 6706) {
         if (!var1.bB(1018)) {
            var1.ca(30);
            if (var1 instanceof L1PcInstance) {
               var4 = (L1PcInstance)var1;
               var4.a(new S_OwnCharAttrDef(var4));
               var4.a(new S_SkillSound(var4.fr(), var3));
            }

            var1.b(new S_SkillSound(var1.fr(), var3));
            var1.j(1018, 8000);
         }
      } else if (this.fe() == 6712) {
         if (!var1.bB(1020)) {
            var1.cb(30);
            if (var1 instanceof L1PcInstance) {
               var4 = (L1PcInstance)var1;
               var4.a(new S_OwnCharAttrDef(var4));
               var4.a(new S_SkillSound(var4.fr(), var3));
            }

            var1.b(new S_SkillSound(var1.fr(), var3));
            var1.j(1020, 8000);
         }
      } else if (this.fe() == 6718) {
         if (!var1.bB(1022)) {
            var1.bY(30);
            if (var1 instanceof L1PcInstance) {
               var4 = (L1PcInstance)var1;
               var4.a(new S_OwnCharAttrDef(var4));
               var4.a(new S_SkillSound(var4.fr(), var3));
            }

            var1.b(new S_SkillSound(var1.fr(), var3));
            var1.j(1022, 8000);
         }
      } else if (this.fe() == 6724 && !var1.bB(1025)) {
         if (var1 instanceof L1PcInstance) {
            var4 = (L1PcInstance)var1;
            var4.a(new S_SkillSound(var4.fr(), var3));
         }

         var1.b(new S_SkillSound(var1.fr(), var3));
         var1.j(1025, 8000);
         CubeEffectTimer var5 = new CubeEffectTimer(var2, var1, 1025);
         var5.a();
      }
   }

   private void b(L1Character var1, L1Character var2) {
      int var3 = SkillsTable.a().a(this.h()).u();
      L1PcInstance var4 = null;
      if (this.fe() == 6706) {
         if (!var1.bB(1019)) {
            if (var1 instanceof L1PcInstance) {
               var4 = (L1PcInstance)var1;
               var4.a(new S_SkillSound(var4.fr(), var3));
            }

            var1.b(new S_SkillSound(var1.fr(), var3));
            var1.j(1019, 8000);
            CubeEffectTimer var5 = new CubeEffectTimer(var2, var1, 1019);
            var5.a();
         }
      } else if (this.fe() == 6712) {
         if (!var1.bB(1021)) {
            if (var1 instanceof L1PcInstance) {
               var4 = (L1PcInstance)var1;
               var4.a(new S_SkillSound(var4.fr(), var3));
            }

            var1.b(new S_SkillSound(var1.fr(), var3));
            var1.j(1021, 8000);
            CubeEffectTimer var10 = new CubeEffectTimer(var2, var1, 1021);
            var10.a();
         }
      } else if (this.fe() == 6718) {
         if (!var1.bB(1023)) {
            if (var1 instanceof L1PcInstance) {
               var4 = (L1PcInstance)var1;
               var4.a(new S_SkillSound(var4.fr(), var3));
            }

            var1.b(new S_SkillSound(var1.fr(), var3));
            var1.j(1023, 8000);
            CubeEffectTimer var11 = new CubeEffectTimer(var2, var1, 1023);
            var11.a();
         }
      } else if (this.fe() == 6724 && !var1.bB(1025)) {
         if (var1 instanceof L1PcInstance) {
            var4 = (L1PcInstance)var1;
            var4.a(new S_SkillSound(var4.fr(), var3));
         }

         var1.b(new S_SkillSound(var1.fr(), var3));
         var1.j(1025, 8000);
         CubeEffectTimer var12 = new CubeEffectTimer(var2, var1, 1025);
         var12.a();
      }
   }

   public void d(L1PcInstance var1) {
      this.C = var1;
   }

   public L1PcInstance g() {
      return this.C;
   }

   public void h_(int var1) {
      this.D = var1;
   }

   public int h() {
      return this.D;
   }

   private class a implements Runnable {
      private final L1EffectInstance b;

      private a(L1EffectInstance var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         while (!L1EffectInstance.this.ah()) {
            try {
               for (L1Object var1 : L1World.a().b(this.b, 3)) {
                  if (var1 instanceof L1PcInstance) {
                     L1PcInstance var3 = (L1PcInstance)var1;
                     if (!var3.eX()) {
                        L1PcInstance var4 = L1EffectInstance.this.g();
                        if (var3.fr() == var4.fr()) {
                           L1EffectInstance.this.a(var3, this.b);
                        } else if (var3.aF() != 0 && var4.aF() == var3.aF()) {
                           L1EffectInstance.this.a(var3, this.b);
                        } else if (var3.q() && var3.aL().d(var4)) {
                           L1EffectInstance.this.a(var3, this.b);
                        } else if (var3.ep() == 1) {
                           if (L1CastleWar.a().a((L1Character)var3)) {
                              L1EffectInstance.this.b(var3, this.b);
                           }
                        } else {
                           L1EffectInstance.this.b(var3, this.b);
                        }
                     }
                  } else if (var1 instanceof L1MonsterInstance) {
                     L1MonsterInstance var6 = (L1MonsterInstance)var1;
                     if (!var6.eX()) {
                        L1EffectInstance.this.b(var6, this.b);
                     }
                  }
               }

               Thread.sleep(500L);
            } catch (InterruptedException var5) {
            }
         }
      }

      // $VF: synthetic method
      a(L1EffectInstance var2, L1EffectInstance.a var3) {
         this(var2);
      }
   }

   private class b implements Runnable {
      private final L1EffectInstance b;
      private final L1SkillExecutor c;

      private b(L1EffectInstance var2) {
         this.b = var2;
         this.c = new S_058();
      }

      @Override
      public void run() {
         while (!L1EffectInstance.this.ah()) {
            try {
               if (this.b.g() != null && this.b != null) {
                  for (L1Character var1 : this.c.a(this.b.g(), this.b, 1)) {
                     L1Magic var3 = new L1Magic(this.b.g(), var1);
                     int var4 = var3.a();
                     var3.a(var4, 0);
                     if (var4 > 0) {
                        if (var1 instanceof L1PcInstance) {
                           L1PcInstance var5 = (L1PcInstance)var1;
                           var5.a(new S_DoActionGFX(var5.fr(), 2));
                        }

                        var1.b(new S_DoActionGFX(var1.fr(), 2));
                     }
                  }

                  Thread.sleep(1000L);
               }
            } catch (InterruptedException var6) {
            }
         }
      }

      // $VF: synthetic method
      b(L1EffectInstance var2, L1EffectInstance.b var3) {
         this(var2);
      }
   }

   private class c implements Runnable {
      private final L1EffectInstance b;

      private c(L1EffectInstance var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         while (!L1EffectInstance.this.ah()) {
            try {
               for (L1Object var1 : L1World.a().b(this.b, 0)) {
                  if (var1 instanceof L1Character) {
                     L1Character var3 = (L1Character)var1;
                     if (!(var3 instanceof L1MonsterInstance)) {
                        L1DamagePoison.a(this.b, var3, 3000, 20, 30);
                     }
                  }
               }

               Thread.sleep(1000L);
            } catch (InterruptedException var4) {
            }
         }
      }

      // $VF: synthetic method
      c(L1EffectInstance var2, L1EffectInstance.c var3) {
         this(var2);
      }
   }
}
