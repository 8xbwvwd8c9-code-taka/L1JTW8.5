package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1DollInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1World;
import l1r.bc.WeaponChaserTimer;
import l1r.be.S_AttackPacket;
import l1r.be.S_DoActionGFX;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_Paralysis;
import l1r.be.S_SkillSound;
import l1r.bf.S_018;
import l1r.bf.S_050;
import l1r.bf.S_056;
import l1r.bf.S_207;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class WeaponSkillTable {
   private static final Logger a = Logger.getLogger(WeaponSkillTable.class.getName());
   private static WeaponSkillTable b;
   private final HashMap<Integer, WeaponSkillTable.a> c = new HashMap<>();

   public static WeaponSkillTable a() {
      if (b == null) {
         b = new WeaponSkillTable();
      }

      return b;
   }

   private WeaponSkillTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM weapon_skill");
         var3 = var2.executeQuery();
         this.a(var3);
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(L1ItemInstance var1) {
      if (this.c.containsKey(var1.N())) {
         var1.b(this.c.get(var1.N()).c);
      }
   }

   private void a(ResultSet var1) throws SQLException {
      while (var1.next()) {
         WeaponSkillTable.a var2 = new WeaponSkillTable.a(null);
         var2.b = var1.getInt("weapon_id");
         var2.c = var1.getString("skill_name");
         var2.d = var1.getInt("probability");
         var2.e = var1.getInt("prob_every_enchant");
         var2.f = var1.getInt("base_damage");
         var2.g = var1.getInt("random_damage");
         var2.h = var1.getInt("area");
         var2.i = var1.getInt("effect_id");
         var2.j = var1.getBoolean("is_target_effect");
         var2.k = var1.getBoolean("is_arrow");
         var2.l = var1.getInt("attr");
         var2.m = var1.getDouble("berserkers_rate");
         var2.n = var1.getInt("leech_hp");
         var2.o = var1.getInt("leech_mp");
         var2.p = var1.getInt("leech_every_enchant");
         var2.q = var1.getInt("chaser_attack_gfxid");
         var2.r = var1.getBoolean("is_caotic");
         var2.s = var1.getBoolean("is_skill_freeze");
         var2.t = var1.getBoolean("is_skill_disease");
         var2.u = var1.getBoolean("is_skill_bind");
         var2.v = var1.getBoolean("is_skill_turn_undead");
         var2.w = var1.getBoolean("is_skill_mind_break");
         this.c.put(var2.b, var2);
      }
   }

   public double a(L1PcInstance var1, L1Character var2, int var3) {
      if (var2 instanceof L1DollInstance) {
         return 0.0;
      }

      if (!this.c.containsKey(var3)) {
         return 0.0;
      }

      if (var3 == 310) {
         if (var1.fp() < 2600 || var1.fp() > 2698) {
            return 0.0;
         }
      } else if (var3 == 413 && var1.fp() != 6311) {
         return 0.0;
      }

      WeaponSkillTable.a var4 = this.c.get(var3);
      if (Random.a(100) > var4.d + var4.e * var1.v().G()) {
         return 0.0;
      }

      L1Character var5 = var4.j ? var2 : var1;
      int var6 = var4.i;
      if (var6 > 0) {
         if (var4.k) {
            L1Character var7 = new L1Character();
            if (var1.v().a().aO() == 20) {
               var7.cG(var1.fs());
               var7.cH(var1.ft());
            } else {
               var7.cG(var2.fs());
               var7.cH(var2.ft());
            }

            var1.a(new S_AttackPacket(var7, var2, 1, var6, 0, 6, 0));
            var1.b(new S_AttackPacket(var7, var2, 1, var6, 0, 6, 0));
         } else {
            var1.a(new S_SkillSound(var5.fr(), var6));
            var1.b(new S_SkillSound(var5.fr(), var6));
         }
      }

      if (var4.u) {
         this.d(var2);
      }

      if (var4.t) {
         this.b(var2);
      }

      if (var4.v) {
         this.a(var2);
      }

      if (var4.w) {
         this.a(var1, var2);
      }

      if (var4.s) {
         this.c(var2);
      }

      if (var4.q > 0) {
         new WeaponChaserTimer(var1, var2, var4.q).a();
      }

      double var15 = var4.f;
      if (var4.g != 0) {
         var15 += Random.a(var4.g);
      }

      if (var4.r && var1.fa() < 0) {
         var15 += Random.a(var1.fa() / -300);
      }

      if (var4.n > 0) {
         int var9 = var4.n + Random.a(var4.p * var1.v().G());
         var9 -= var2.W_() / 2;
         if (var9 < 0) {
            var9 = 0;
         }

         var1.a(var1.ea() + var9);
      }

      if (var4.o > 0) {
         int var17 = Random.a(var4.o + var4.p * var1.v().G());
         if (var2 instanceof L1PcInstance) {
            L1PcInstance var10 = (L1PcInstance)var2;
            var17 = var17 > var10.eb() ? var10.eb() : var17;
            var10.i_(var10.eb() - var17);
         } else if (var2 instanceof L1NpcInstance) {
            L1NpcInstance var19 = (L1NpcInstance)var2;
            var17 = var19.i(var17);
            var19.by(var19.eb() - var17);
         }

         var1.i_(var1.eb() + var17);
      }

      if (var4.m > 0.0) {
         var15 *= var4.m;
      }

      if (var4.l != -1) {
         var15 = L1Magic.a(var1, var2, var15, var4.l);
      }

      if (var4.h > 0 || var4.h == -1) {
         for (L1Object var18 : L1World.a().b(var5, var4.h)) {
            if (var18 instanceof L1Character && var18.fr() != var1.fr() && var18.fr() != var2.fr()) {
               double var11 = var4.f;
               if (var4.g != 0) {
                  var11 += Random.a(var4.g);
               }

               if (var18 instanceof L1PcInstance) {
                  L1PcInstance var13 = (L1PcInstance)var18;
                  if (var13.bN() || var13.aA() || var13.eX() || var1.a(var1, var13, true)) {
                     continue;
                  }
               } else if (var18 instanceof L1MonsterInstance) {
                  L1MonsterInstance var21 = (L1MonsterInstance)var18;
                  if (var21.eX() || var21.ac() == 1 || var21.ac() == 2) {
                     continue;
                  }
               } else {
                  if (!(var18 instanceof L1SummonInstance) && !(var18 instanceof L1PetInstance)) {
                     continue;
                  }

                  L1NpcInstance var22 = (L1NpcInstance)var18;
                  L1PcInstance var14 = (L1PcInstance)var22.M();
                  if (var14 != null) {
                     if (var14.bE() > 0) {
                        var11 /= 8.0;
                     }

                     if (var14.fr() == var1.fr() || var14.a(var1, var14, true)) {
                        continue;
                     }
                  }
               }

               if (var4.u && Random.a(100) < 45) {
                  this.d((L1Character)var18);
               }

               if (var4.t && Random.a(100) < 45) {
                  this.b((L1Character)var18);
               }

               if (var4.v && Random.a(100) < 45) {
                  this.a((L1Character)var18);
               }

               if (var4.s && Random.a(100) < 45) {
                  this.c((L1Character)var18);
               }

               if (var4.w && Random.a(100) < 45) {
                  this.a(var1, (L1Character)var18);
               }

               if (var4.q > 0) {
                  new WeaponChaserTimer(var1, (L1Character)var18, var4.q).a();
               }

               if (var4.n > 0) {
                  int var23 = var4.n + Random.a(var4.p * var1.v().G());
                  var23 -= ((L1Character)var18).W_() / 2;
                  if (var23 < 0) {
                     var23 = 0;
                  }

                  var1.a(var1.ea() + var23);
               }

               if (var4.o > 0) {
                  int var25 = Random.a(var4.o + var4.p * var1.v().G());
                  if (var18 instanceof L1PcInstance) {
                     L1PcInstance var29 = (L1PcInstance)var18;
                     var25 = var25 > var29.eb() ? var29.eb() : var25;
                     var29.i_(var29.eb() - var25);
                  } else if (var18 instanceof L1NpcInstance) {
                     L1NpcInstance var30 = (L1NpcInstance)var18;
                     var25 = var30.i(var25);
                     var30.by(var30.eb() - var25);
                  }

                  var1.i_(var1.eb() + var25);
               }

               if (var4.l != -1) {
                  var11 = L1Magic.a(var1, (L1Character)var18, var11, var4.l);
               }

               if (Config.S && var1.l()) {
                  L1Character var26 = new L1Character();
                  var26.cF(var18.fr());
                  var26.cG(var18.fs());
                  var26.cH(var18.ft() - 1);
                  var1.a(new S_NpcChatPacket(var26, "\\\\fRf4↓ 魔傷\\\\fRfM (" + var11 + ")"));
               }

               if (!(var11 <= 0.0)) {
                  if (var18 instanceof L1PcInstance) {
                     L1PcInstance var27 = (L1PcInstance)var18;
                     var27.a(new S_DoActionGFX(var27.fr(), 2));
                     var27.b(new S_DoActionGFX(var27.fr(), 2));
                     var27.a(var1, (int)var11, false);
                  } else if (var18 instanceof L1NpcInstance) {
                     L1NpcInstance var28 = (L1NpcInstance)var18;
                     var28.b(new S_DoActionGFX(var28.fr(), 2));
                     var28.b(var1, (int)var11);
                  }
               }
            }
         }
      }

      return var15;
   }

   private void a(L1PcInstance var1, L1Character var2) {
      if (Random.a(100) > var2.W_()) {
         new S_207().b(var1, var2, -1);
      }
   }

   private void a(L1Character var1) {
      if (Random.a(100) > var1.W_()) {
         new S_018().a(var1, -1);
      }
   }

   private void b(L1Character var1) {
      if (Random.a(100) > var1.W_()) {
         new S_056().a(var1, -1);
      }
   }

   private void c(L1Character var1) {
      if (Random.a(100) > var1.W_()) {
         new S_050().a(var1, -1);
      }
   }

   private void d(L1Character var1) {
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

   public double a(L1PcInstance var1, L1Character var2, int var3, int var4, double var5) {
      if (var2 instanceof L1DollInstance) {
         return 0.0;
      }

      var1.a(new S_SkillSound(var2.fr(), var3));
      var1.b(new S_SkillSound(var2.fr(), var3));

      for (L1Object var7 : L1World.a().b(var2, var4)) {
         if (var7 instanceof L1Character && var7.fr() != var1.fr() && var7.fr() != var2.fr()) {
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var9 = (L1PcInstance)var7;
               if (var9.bN() || var9.aA() || var9.eX() || var1.a(var1, var9, true)) {
                  continue;
               }
            } else if (var7 instanceof L1MonsterInstance) {
               L1MonsterInstance var12 = (L1MonsterInstance)var7;
               if (var12.eX() || var12.ac() == 1 || var12.ac() == 2) {
                  continue;
               }
            } else {
               if (!(var7 instanceof L1SummonInstance) && !(var7 instanceof L1PetInstance)) {
                  continue;
               }

               L1NpcInstance var13 = (L1NpcInstance)var7;
               L1PcInstance var10 = (L1PcInstance)var13.M();
               if (var10 != null && (var10.fr() == var1.fr() || var10.a(var1, var10, true))) {
                  continue;
               }
            }

            double var14 = L1Magic.a(var1, (L1Character)var7, var5, 0);
            if (!(var14 <= 0.0)) {
               if (var7 instanceof L1PcInstance) {
                  L1PcInstance var11 = (L1PcInstance)var7;
                  var11.a(new S_DoActionGFX(var11.fr(), 2));
                  var11.b(new S_DoActionGFX(var11.fr(), 2));
                  var11.a(var1, (int)var14, false);
               } else if (var7 instanceof L1NpcInstance) {
                  L1NpcInstance var15 = (L1NpcInstance)var7;
                  var15.b(new S_DoActionGFX(var15.fr(), 2));
                  var15.b(var1, (int)var14);
               }
            }
         }
      }

      return L1Magic.a(var1, var2, var5, 0);
   }

   private class a {
      private int b;
      private String c;
      private int d;
      private int e;
      private int f;
      private int g;
      private int h;
      private int i;
      private boolean j;
      private boolean k;
      private int l;
      private double m;
      private int n;
      private int o;
      private int p;
      private int q;
      private boolean r;
      private boolean s;
      private boolean t;
      private boolean u;
      private boolean v;
      private boolean w;

      private a() {
      }

      // $VF: synthetic method
      a(WeaponSkillTable.a var2) {
         this();
      }
   }
}
