package l1r.ap;

import l1r.ao.MobSkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1World;
import l1r.be.S_KeeperPack;
import l1r.be.S_ProtoBuffers;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Point;

public class L1KeeperInstance extends L1MonsterInstance {
   @Override
   public boolean a() {
      return L1World.a().f(this).isEmpty();
   }

   public L1KeeperInstance(L1Npc var1) {
      super(var1);
      this.q();
   }

   @Override
   public void b() {
      this.w = true;
      this.i.clear();
      this.j = null;
      L1Character var1 = this.m;
      if (this.fu().c(new Point(this.X(), this.Y())) > 15) {
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
   public void c() {
      L1PcInstance var1 = null;
      L1PcInstance var2 = null;
      if (this.m != null && this.m instanceof L1PcInstance) {
         var1 = (L1PcInstance)this.m;
         this.s();
      }

      for (L1PcInstance var3 : L1World.a().f(this)) {
         if (var3 != var1 && var3.ea() > 0 && !var3.eX() && !var3.bN()) {
            var2 = var3;
         }
      }

      if (var2 != null) {
         this.n.a(var2, 0);
         this.m = var2;
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
               L1MonsterInstance.a var5 = new L1MonsterInstance.a(var1);
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
         L1MonsterInstance.a var3 = new L1MonsterInstance.a(var1);
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
      var1.a(new S_ProtoBuffers(65, this.fr(), 2));
      this.Z_();
   }
}
