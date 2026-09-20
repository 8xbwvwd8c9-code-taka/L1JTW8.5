package l1r.ap;

import l1r.ao.MobSkillsTable;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_KeeperPack;
import l1r.be.S_ProtoBuffers;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Point;

public class L1AttackerInstance extends L1MonsterInstance {
   private int y = 0;
   private L1TowerInstance z = null;

   @Override
   public boolean a() {
      this.y = L1CastleLocation.a(this);

      for (L1Object var1 : L1World.a().b()) {
         if (var1 instanceof L1TowerInstance) {
            L1TowerInstance var3 = (L1TowerInstance)var1;
            if (L1CastleLocation.a(this.y, var3)) {
               this.z = var3;
               break;
            }
         }
      }

      if (this.z == null) {
         return true;
      }

      this.m = this.z;
      return false;
   }

   public L1AttackerInstance(L1Npc var1) {
      super(var1);
      h = 100;
      this.q();
   }

   @Override
   public void b() {
      this.w = true;
      L1Character var1 = this.m;
      if (L1CastleLocation.a(this) <= 0) {
         this.a(this.X(), this.Y(), 1);
         this.s();
      } else {
         if (this.c(var1.fs(), var1.ft(), this.C())) {
            int var2 = MobSkillsTable.a().a(this, var1);
            if (var2 > 0) {
               this.v(this.f(var2, 2));
            } else {
               this.ct(this.h(var1.fs(), var1.ft()));
               this.b(var1);
            }
         } else {
            int var4 = MobSkillsTable.a().a(this, var1);
            if (var4 > 0) {
               this.v(this.f(var4, 2));
               return;
            }

            if (this.N() <= 0) {
               this.s();
               return;
            }

            if (this.m instanceof L1PcInstance && this.e((L1Object)this.m) >= 5.0) {
               this.s();
               return;
            }

            int var3 = this.a(var1.fs(), var1.ft());
            if (var3 == -1) {
               this.c();
            } else {
               this.g(var3);
               this.v(this.f(this.N(), 0));
            }
         }
      }
   }

   @Override
   protected int a(int var1, int var2) {
      double var3 = this.fu().b(new Point(var1, var2));
      if (this.bB(40) && var3 >= 2.0) {
         return -1;
      }

      if (var3 > 100.0) {
         return -1;
      }

      if (var3 > h) {
         return this.a(this.fs(), this.ft(), this.fp(), this.h(var1, var2));
      }

      int var5 = this.d(var1, var2);
      if (var5 == -1) {
         var5 = this.h(var1, var2);
         if (!this.h(var5)) {
            var5 = this.a(this.fs(), this.ft(), this.fp(), var5);
         }
      }

      return var5;
   }

   @Override
   public void c() {
      L1Character var1 = null;
      L1Character var2 = null;
      if (this.m != null && (this.m instanceof L1PcInstance || this.m instanceof L1DoorInstance)) {
         var1 = this.m;
         this.s();
      }

      for (L1Object var3 : L1World.a().e(this)) {
         if (var3 instanceof L1DoorInstance) {
            L1DoorInstance var5 = (L1DoorInstance)var3;
            if (var5 != var1 && var5.o() != 28 && !var5.eX() && var5.ab_() >= 2) {
               var2 = var5;
            }
         }
      }

      if (var2 != null) {
         this.n.a(var2, 0);
         this.m = var2;
      } else {
         this.m = this.z;
      }
   }

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 > 0 && !this.eX()) {
         this.c((L1Character)var1, var2);
         this.Z_();
         int var3 = this.eb() - var2;
         if (var3 < 0) {
            var3 = 0;
         }

         this.i_(var3);
      }
   }

   @Override
   public void b(L1Character var1, int var2) {
      if (this.ea() > 0 && !this.eX()) {
         if (this.ac() == 1 || this.ac() == 2) {
            return;
         }

         if (var2 >= 0 && !(var1 instanceof L1EffectInstance)) {
            this.c((L1Character)var1, var2);
         }

         if (var2 > 0) {
            this.bz(66);
            this.bz(153);
         }

         this.Z_();
         if (var1 instanceof L1PcInstance && var2 > 0) {
            L1PcInstance var6 = (L1PcInstance)var1;
            var6.a(this);
         }

         int var7 = this.ea() - var2;
         if (var7 <= 0 && !this.eX()) {
            int var4 = this.U_().ab();
            if (var4 == -1) {
               L1MonsterInstance.L1R_a var5 = new L1MonsterInstance.L1R_a(var1);
               GeneralThreadPool.a().a(var5);
            } else {
               this.g_(var4);
            }
         }

         if (var7 > 0) {
            this.a(var7);
         }
      } else if (!this.eX()) {
         this.X(true);
         this.cq(8);
         L1MonsterInstance.L1R_a var3 = new L1MonsterInstance.L1R_a(var1);
         GeneralThreadPool.a().a(var3);
      }
   }

   @Override
   public void a(L1PcInstance var1) {
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_KeeperPack(this));
      var1.a(new S_ProtoBuffers(65, this.fr(), 1));
      this.Z_();
   }
}
