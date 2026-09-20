package l1r.ap;

import l1r.ao.MobSkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_RemoveObject;
import l1r.bh.L1Npc;
import l1r.bi.Point;

public class L1DotaInstance extends L1MonsterInstance {
   public L1DotaInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   protected int a(int var1, int var2) {
      double var3 = this.fu().b(new Point(var1, var2));
      if (this.bB(40) && var3 >= 2.0) {
         return -1;
      }

      if (var3 > 30.0) {
         return -1;
      }

      if (var3 > h) {
         return this.a(this.fs(), this.ft(), this.fp(), this.h(var1, var2));
      }

      int var5 = this.d(var1, var2);
      if (var5 == -1) {
         var5 = this.h(var1, var2);
      }

      return var5;
   }

   @Override
   public void b() {
      this.w = true;
      this.i.clear();
      this.j = null;
      L1Character var1 = this.m;
      if (this.c(var1.fs(), var1.ft(), this.C())) {
         int var2 = MobSkillsTable.a().a(this, var1);
         if (var2 > 0) {
            this.v(this.f(var2, 2));
         } else {
            this.ct(this.h(var1.fs(), var1.ft()));
            this.b(var1);
         }
      } else {
         int var6 = MobSkillsTable.a().a(this, var1);
         if (var6 > 0) {
            this.v(this.f(var6, 2));
            return;
         }

         int var3 = this.a(var1.fs(), var1.ft());
         if (var3 == -1) {
            this.v(3000);

            for (L1PcInstance var4 : L1World.a().f(this)) {
               var4.a(new S_RemoveObject(this));
               var4.d(this);
            }

            this.cG(this.X());
            this.cH(this.Y());
            this.ct(this.fb());
         } else {
            this.g(var3);
            this.v(this.f(this.N(), 0));
         }
      }
   }

   @Override
   public void c() {
      for (L1Object var1 : L1World.a().e(this)) {
         if (var1 instanceof L1NpcInstance) {
            L1NpcInstance var3 = (L1NpcInstance)var1;
            if (var3.z() == 190114) {
               if (this.ew() > 2000) {
                  this.n.a(var3, 1);
               } else {
                  this.n.a(var3, this.ew());
               }

               this.m = var3;
               return;
            }
         }
      }
   }

   @Override
   public void d() {
      if (this.m == null || this.m.fp() != this.fp() || this.m.eX() || this.m.ff() && !this.V() && !this.n.a(this.m) || this.m.f(this) > 30) {
         this.s();
         if (!this.n.b()) {
            this.m = this.n.c();
            this.d();
         }
      }
   }
}
