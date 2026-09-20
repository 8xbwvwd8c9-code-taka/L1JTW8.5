package l1r.bf;

import java.util.ArrayList;
import l1r.ao.MobSkillsTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_AttackPacket;
import l1r.be.S_DoActionGFX;
import l1r.be.S_Paralysis;
import l1r.be.S_RangeSkill;
import l1r.be.S_SkillSound;
import l1r.bi.LineageUtil;
import l1r.bi.Random;

public class S_000 extends L1SkillExecutor {
   @Override
   public void a(L1Character var1, int var2) {
   }

   public void a(L1NpcInstance var1, L1Character var2, MobSkillsTable.L1R_b var3) {
      L1Character var4 = var3.g ? var2 : var1;
      ArrayList var5 = new ArrayList<>();
      if (var3.e == 0) {
         var5.add(var4);
      } else {
         var5 = this.a(var1, var4, var3.e);
      }

      if (var3.n > 0) {
         SpawnTable.a(var3.n, var1, 5, var2);
      } else if (var3.s) {
         this.b(var2);
      } else {
         for (L1Character var6 : var5) {
            if (var3.q) {
               this.c(var6);
            } else if (var3.r && var6.e(var1) > 5.0) {
               L1Teleport.a(var6, var1, 1);
            } else if (var3.o > 0) {
               this.b(var1, var6, var3.o);
            } else if (var3.p > 0) {
               this.d(var6, var3.p);
            } else {
               int var8 = var3.b + Random.a(var3.c);
               var8 = (int)L1Magic.a(var1, var2, var8, var3.m);
               var6.cD(var8);
               if (var6 instanceof L1PcInstance) {
                  L1PcInstance var9 = (L1PcInstance)var6;
                  var9.a(var1, var8, true);
               } else if (var6 instanceof L1NpcInstance) {
                  L1NpcInstance var15 = (L1NpcInstance)var6;
                  var15.b(var1, var8);
               }
            }
         }
      }

      if (var3.e != 0) {
         if (var3.g) {
            var1.b(new S_RangeSkill(var1, var5, var3.f, var3.i, 8));
         } else {
            var1.b(new S_RangeSkill(var1, var5, var3.f, var3.i, 0));

            for (int var10 = 1; var10 < var5.size(); var10++) {
               L1Character var12 = var5.get(var10);
               if (var12.fo() != 0) {
                  var12.b(new S_DoActionGFX(var12.fr(), 2));
                  if (var12 instanceof L1PcInstance) {
                     L1PcInstance var14 = (L1PcInstance)var12;
                     var14.a(new S_DoActionGFX(var14.fr(), 2));
                  }
               }
            }
         }
      } else if (var3.g) {
         if (var3.b <= 0) {
            var1.b(new S_SkillSound(var2.fr(), var3.f));
            var1.b(new S_DoActionGFX(var1.fr(), var3.i));
            return;
         }

         int var11 = var3.h ? 0 : 6;
         var1.ct(var1.a(var2));
         var1.b(new S_AttackPacket(var1, var2, var3.i, var3.f, var2.fo(), var11, 0));
         if (var2.fo() > 0) {
            var2.a(new S_DoActionGFX(var2.fr(), 2), var1);
         }
      } else {
         var1.b(new S_SkillSound(var1.fr(), var3.f));
         var1.b(new S_DoActionGFX(var1.fr(), var3.i));
      }
   }

   private void d(L1Character var1, int var2) {
      if (Random.a(127) > var1.W_()) {
         LineageUtil.a(var2).a(var1, -1);
      }
   }

   private void b(L1Character var1, L1Character var2, int var3) {
      if (Random.a(127) > var2.W_()) {
         var1.b(new S_SkillSound(var2.fr(), 230));
         L1PolyMorph.a(var2, var3, 300, 4);
      }
   }

   private void b(L1Character var1) {
      int var2 = var1.fs();
      int var3 = var1.ft();
      int[][] var4 = new int[][]{{0, -2}, {1, -1}, {2, 0}, {-1, -1}, new int[2], {1, 1}, {-2, 0}, {-1, 1}, {0, 2}};
      int[][] var8 = var4;
      int var7 = var4.length;

      for (int var6 = 0; var6 < var7; var6++) {
         int[] var5 = var8[var6];
         L1EffectInstance var9 = L1SpawnEffect.a().a(1263, 9000, var2 + var5[0], var3 + var5[1], var1.fp());

         for (L1EffectInstance var10 : L1World.a().d().values()) {
            if (!var10.equals(var9) && var10.fq() == var9.fq() && var9.fu().f(var10.fu()) && var10.fe() == 1263) {
               var10.aa_();
            }
         }
      }
   }

   private void c(L1Character var1) {
      if (Random.a(127) > var1.W_()) {
         int var2 = 8000;
         if (!L1Magic.a(var1)) {
            L1SpawnEffect.a().a(4184, 8000, var1.fs(), var1.ft(), var1.fp());
            if (var1 instanceof L1PcInstance) {
               L1PcInstance var3 = (L1PcInstance)var1;
               var3.j(1028, 8000);
               var3.a(new S_Paralysis(6, true));
            } else if (var1 instanceof L1NpcInstance) {
               L1NpcInstance var4 = (L1NpcInstance)var1;
               var4.j(1028, 8000);
               var4.n(true);
            }
         }
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
   }

   @Override
   public void a(L1Character var1) {
   }
}
