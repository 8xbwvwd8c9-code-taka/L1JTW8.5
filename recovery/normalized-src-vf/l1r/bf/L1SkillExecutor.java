package l1r.bf;

import java.util.ArrayList;
import l1r.ao.PolyTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1DotaInstance;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1World;
import l1r.be.S_AttackPacket;
import l1r.be.S_DoActionGFX;
import l1r.be.S_Paralysis;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_RangeSkill;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bg.L1SkillDelay;
import l1r.bh.L1Skills;
import l1r.bi.CalcStat;

public abstract class L1SkillExecutor {
   private static final int[] a = new int[]{
      1,
      2,
      3,
      5,
      8,
      9,
      12,
      13,
      14,
      19,
      21,
      26,
      31,
      32,
      35,
      37,
      42,
      43,
      44,
      48,
      49,
      52,
      54,
      55,
      57,
      60,
      61,
      63,
      67,
      68,
      69,
      72,
      73,
      75,
      78,
      79,
      88,
      89,
      90,
      91,
      97,
      98,
      99,
      100,
      101,
      102,
      104,
      105,
      106,
      107,
      109,
      110,
      111,
      113,
      114,
      115,
      116,
      117,
      118,
      129,
      130,
      131,
      133,
      134,
      137,
      138,
      146,
      147,
      148,
      149,
      150,
      151,
      155,
      156,
      158,
      159,
      163,
      164,
      165,
      166,
      168,
      169,
      170,
      171,
      175,
      176,
      181,
      185,
      190,
      195,
      201,
      204,
      209,
      211,
      214,
      216,
      219,
      233
   };

   public abstract void a(L1Character var1, int var2, int var3, int var4, String var5);

   public abstract void a(L1Character var1, int var2);

   public abstract void a(L1Character var1);

   public void a(L1Character var1, int var2, int var3, int var4, int var5, String var6) {
      if (this.b(var1, var2, var3)) {
         this.a(var1, var2, var4, var5, var6);
         this.g(var1, var3);
      } else {
         this.d(var1, var3);
      }
   }

   private void d(L1Character var1, int var2) {
      if ((var2 == 5 || var2 == 69 || var2 == 131) && var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_Paralysis(7, false));
      }
   }

   public boolean a(L1Character var1, int var2, int var3) {
      L1PcInstance var4 = (L1PcInstance)var1;
      if (!var4.aR() && !var4.ed()) {
         return (var4.ff() || var4.N()) && !this.a(var3) ? false : this.c(var1, var2, var3);
      } else {
         return false;
      }
   }

   private boolean b(L1Character var1, int var2, int var3) {
      if (!var1.ed() && !var1.ej()) {
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var4 = (L1PcInstance)var1;
            if (var4.aR() || !this.f(var1, var3)) {
               var4.a(new S_ServerMessage(281));
               return false;
            }

            if ((var4.ff() || var4.N()) && !this.a(var3)) {
               var4.a(new S_ServerMessage(1003));
               return false;
            }

            if (var4.j().h() > 82) {
               var4.a(new S_ServerMessage(316));
               return false;
            }

            L1PolyMorph var5 = PolyTable.a().a(var4.fe());
            if (var5 != null && !var5.e()) {
               var4.a(new S_ServerMessage(285));
               return false;
            }

            if (var3 == 205 || var3 == 210 || var3 == 215 || var3 == 220) {
               for (L1Object var6 : L1World.a().b(var4, 3)) {
                  if (var6 instanceof L1EffectInstance) {
                     L1EffectInstance var8 = (L1EffectInstance)var6;
                     if (var8.h() == var3) {
                        var4.a(new S_ServerMessage(1412));
                        return false;
                     }
                  }
               }
            }
         }

         if (var1.bB(64) || var1.bB(161) || var1.bB(1007) || var1.bB(202)) {
            this.b(var1, 280);
            return false;
         } else {
            return !this.c(var1, var2, var3) ? false : this.e(var1, var3);
         }
      } else {
         return false;
      }
   }

   private boolean c(L1Character var1, int var2, int var3) {
      L1Skills var4 = SkillsTable.a().a(var3);
      int var5 = var4.p();
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (var5 < 0) {
            if (!var1.fu().e(var7.fu())) {
               return false;
            }
         } else if (var3 != 116 && var3 != 118) {
            if (var3 == 51) {
               if (!var1.fq().o()) {
                  this.b(var1, 79);
                  return false;
               }
            } else if (var1.fu().c(var7.fu()) > var5 || !var1.i(var7.fs(), var7.ft())) {
               this.b(var1, 280);
               return false;
            }
         } else if (!var1.fq().h()) {
            return false;
         }
      }

      return true;
   }

   private boolean e(L1Character var1, int var2) {
      L1Skills var3 = SkillsTable.a().a(var2);
      int var4 = var3.d();
      int var5 = var3.e();
      int var6 = var1.eb();
      int var7 = var1.ea();
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var14 = (L1PcInstance)var1;
         var4 = this.a(var14, var3);
         if (var7 - var5 < 1) {
            var14.a(new S_ServerMessage(279));
            return false;
         }

         if (var6 < var4) {
            var14.a(new S_ServerMessage(278));
            return false;
         }

         var14.a(var14.ea() - var5);
         var14.i_(var14.eb() - var4);
         int var15 = var14.fa() + var3.o();
         var15 = Math.min(var15, 32767);
         var15 = Math.max(var15, -32767);
         var14.cr(var15);
         int var18 = var3.f();
         int var11 = var3.g();
         if (var18 == 0) {
            return true;
         }

         int var12 = var18;
         if (var18 == 40318) {
            var12 = 640735;
         } else if (var18 == 40319) {
            var12 = 640736;
         } else if (var18 == 40321) {
            var12 = 640737;
         } else if (var18 == 49158) {
            var12 = 640731;
         } else if (var18 == 49157) {
            var12 = 640734;
         } else if (var18 == 49156) {
            var12 = 640738;
         } else if (var18 == 40068) {
            var12 = 640730;
         }

         return var14.ev() < 55 && var14.j().b(var12, var11) ? true : var14.j().b(var18, var11);
      } else {
         if (var1 instanceof L1NpcInstance) {
            L1NpcInstance var8 = (L1NpcInstance)var1;
            if (var7 - var5 < 1 || var6 < var4) {
               return false;
            }

            int var9 = var8.ea() - var5;
            var8.a(var9);
            int var10 = var8.eb() - var4;
            var8.i_(var10);
         }

         return true;
      }
   }

   private int a(L1PcInstance var1, L1Skills var2) {
      int var3 = var2.a();
      double var4 = var2.d();
      if (var3 == 26 && var1.j().h(20013)) {
         var4 /= 2.0;
      } else if (var3 == 43 && var1.j().h(20013)) {
         var4 /= 2.0;
      } else if (var3 == 1 && var1.j().h(20014)) {
         var4 /= 2.0;
      } else if (var3 == 19 && var1.j().h(20014)) {
         var4 /= 2.0;
      } else if (var3 == 12 && var1.j().h(20015)) {
         var4 /= 2.0;
      } else if (var3 == 13 && var1.j().h(20015)) {
         var4 /= 2.0;
      } else if (var3 == 42 && var1.j().h(20015)) {
         var4 /= 2.0;
      } else if (var3 == 43 && var1.j().h(20008)) {
         var4 /= 2.0;
      } else if (var3 == 43 && var1.j().h(20023)) {
         var4 = 25.0;
      } else if (var3 == 54 && var1.j().h(20023)) {
         var4 /= 2.0;
      }

      var4 -= var4 * CalcStat.d(var1.eD()) / 100.0;
      if (var2.d() > 0) {
         var4 = Math.max(var4, 1.0);
      }

      return (int)Math.round(var4);
   }

   private boolean f(L1Character var1, int var2) {
      L1Skills var3 = SkillsTable.a().a(var2);
      int var4 = var3.n();
      if (var1 instanceof L1NpcInstance) {
         return true;
      }

      L1PcInstance var5 = (L1PcInstance)var1;
      return var3.c() < 17 || var3.c() > 22 || var4 == 0 || var4 == var5.bC() || var5.l();
   }

   private boolean a(int var1) {
      int[] var5 = a;
      int var4 = a.length;

      for (int var3 = 0; var3 < var4; var3++) {
         int var2 = var5[var3];
         if (var2 == var1) {
            return true;
         }
      }

      return false;
   }

   private void g(L1Character var1, int var2) {
      L1Skills var3 = SkillsTable.a().a(var2);
      if (var3.h() > 0) {
         L1SkillDelay.a(var1, var3.h());
      }
   }

   public ArrayList<L1Character> a(L1Character var1, L1Character var2, int var3) {
      ArrayList var4 = new ArrayList<>();
      if (var1.fr() != var2.fr() && !(var2 instanceof L1EffectInstance)) {
         var4.add(var2);
      }

      new ArrayList();
      ArrayList var5;
      if (var3 == -2) {
         var5 = L1World.a().a(var1, var2);
      } else {
         var5 = L1World.a().b(var2, var3);
      }

      for (L1Object var6 : var5) {
         if (var1.fr() != var6.fr() && var1.i(var6.fs(), var6.ft())) {
            if (var6 instanceof L1PcInstance) {
               L1PcInstance var12 = (L1PcInstance)var6;
               if (!var12.bN() && !var12.aA() && !var12.eX()) {
                  L1PcInstance var13 = null;
                  if (var1 instanceof L1PcInstance) {
                     var13 = (L1PcInstance)var1;
                  } else if (var1 instanceof L1SummonInstance || var1 instanceof L1PetInstance) {
                     L1NpcInstance var15 = (L1NpcInstance)var1;
                     if (var15.M() != null) {
                        var13 = (L1PcInstance)var15.M();
                        if (var12.fr() == var13.fr()) {
                           continue;
                        }
                     }
                  } else if (var1 instanceof L1MonsterInstance) {
                     var4.add(var12);
                  }

                  if (var13 != null && !var13.a(var13, var12, true)) {
                     var4.add(var12);
                  }
               }
            } else if (var6 instanceof L1MonsterInstance) {
               L1MonsterInstance var11 = (L1MonsterInstance)var6;
               if (!var11.eX() && var11.ac() != 1 && var11.ac() != 2 && !(var1 instanceof L1MonsterInstance)) {
                  var4.add(var11);
               }
            } else if (var6 instanceof L1SummonInstance || var6 instanceof L1PetInstance) {
               L1NpcInstance var8 = (L1NpcInstance)var6;
               L1PcInstance var9 = null;
               if (var1 instanceof L1PcInstance) {
                  var9 = (L1PcInstance)var1;
               } else if (!(var1 instanceof L1SummonInstance) && !(var1 instanceof L1PetInstance)) {
                  if (var1 instanceof L1MonsterInstance) {
                     var4.add(var8);
                     continue;
                  }
               } else {
                  L1NpcInstance var10 = (L1NpcInstance)var1;
                  if (var10.M() != null) {
                     var9 = (L1PcInstance)var10.M();
                  }
               }

               L1PcInstance var14 = (L1PcInstance)var8.M();
               if (var14 != null && var9 != null && var14.fr() != var9.fr() && !var14.a(var9, var14, true)) {
                  var4.add(var8);
               }
            } else if (var6 instanceof L1NpcInstance && var1 instanceof L1DotaInstance) {
               var4.add((L1NpcInstance)var6);
            }
         }
      }

      return var4;
   }

   protected void b(L1Character var1, int var2) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_ServerMessage(var2));
      }
   }

   protected void a(L1Character var1, L1Skills var2) {
      this.c(var1, var2.t());
   }

   protected void c(L1Character var1, int var2) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_SkillSound(var1.fr(), var2));
         var3.a(new S_ProtoBuffers(485, var3));
      }

      var1.b(new S_SkillSound(var1.fr(), var2));
   }

   protected void b(L1Character var1, L1Skills var2) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_DoActionGFX(var3.fr(), var2.s()));
      }

      var1.b(new S_DoActionGFX(var1.fr(), var2.s()));
   }

   protected void a(L1Character var1, L1Skills var2, ArrayList<L1Character> var3) {
      int var4 = var2.t();
      int var5 = var2.s();
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.a(new S_RangeSkill(var6, var3, var4, var5, 8));
      }

      var1.b(new S_RangeSkill(var1, var3, var4, var5, 8));
   }

   protected void a(L1Character var1, L1Character var2, L1Skills var3, int var4) {
      var1.ct(var1.a(var2));
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var5 = (L1PcInstance)var1;
         var5.a(new S_AttackPacket(var5, var2.fr(), var3.s(), var4, 0));
      }

      var1.b(new S_AttackPacket(var1, var2.fr(), var3.s(), var4, 0));
   }

   protected void b(L1Character var1, L1Character var2, L1Skills var3, int var4) {
      var1.ct(var1.a(var2));
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var5 = (L1PcInstance)var1;
         var5.a(new S_AttackPacket(var1, var2, var3.s(), var3.t(), var4, 6, 0));
      }

      var1.b(new S_AttackPacket(var1, var2, var3.s(), var3.t(), var4, 6, 0));
      if (var4 > 0) {
         var2.a(new S_DoActionGFX(var2.fr(), 2), var1);
      }
   }

   protected void a(L1Character var1, L1Character var2, int var3, int var4, int var5) {
      var1.ct(var1.a(var2));
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.a(new S_AttackPacket(var1, var2, var3, var4, var5, 6, 0));
      }

      var1.b(new S_AttackPacket(var1, var2, var3, var4, var5, 6, 0));
      if (var5 > 0) {
         var2.a(new S_DoActionGFX(var2.fr(), 2), var1);
      }
   }
}
