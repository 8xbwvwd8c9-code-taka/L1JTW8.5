package l1r.be;

import a.g;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL9;
import l1r.ap.L1ItemInstance;
import l1r.bi.LineageUtil;

public class S_AddItem extends ServerBasePacket {
   public S_AddItem(L1ItemInstance var1) {
      this.c(1);
      this.b(588);
      PBMessageALL3.g.a var2 = PBMessageALL3.g.aa();
      PBMessageALL9.c.a var3 = PBMessageALL9.c.ak();
      var3.a(var1.fr());
      var3.b(var1.m());
      var3.c(var1.fr());
      var3.d(var1.E());
      var3.e(var1.a().U());
      var3.g(var1.e());
      var3.h(var1.F());
      var3.i(var1.v());
      var3.j(8);
      var3.k(0);
      if (var1.a().aP() == 28) {
         var3.f(var1.a().V() - 1);
         var3.m(var1.a().V() - 1);
         if (var1.a().V() == 1) {
            var3.f(18);
            var3.m(18);
         }
      } else {
         var3.f(var1.I());
         var3.m(var1.G());
      }

      var3.n(var1.F() >= 128 ? 3 : (var1.a().s() ? 7 : 2));
      var3.e(LineageUtil.a(var1.r()));
      if (var1.C()) {
         var3.f(g.a(var1.t()));
      }

      var2.e(var3.M().f());
      this.a(var2.M().g());
      this.b(0);
   }

   public S_AddItem(L1ItemInstance var1, byte var2) {
      this.c(231);
      this.a(var1.fr());
      this.b(var1.m());
      this.c(var1.a().U());
      if (var1.a().aP() == 28) {
         this.c(var1.a().V() - 1);
      } else {
         this.c(var1.I());
      }

      this.b(var1.e());
      this.c(var1.F());
      this.a(var1.E());
      this.c(var1.v());
      this.a(var1.r());
      if (!var1.C()) {
         this.c(0);
      } else {
         byte[] var3 = var1.t();
         if (var3.length > 127) {
            this.c(0);
         } else {
            this.c(var3.length);
            byte[] var7 = var3;
            int var6 = var3.length;

            for (int var5 = 0; var5 < var6; var5++) {
               byte var4 = var7[var5];
               this.c(var4);
            }
         }
      }

      this.c(24);
      this.b(0);
      this.c(0);
      this.b(0);
      if (var1.a().aP() == 28) {
         this.c(var1.a().V() - 1);
      } else {
         this.c(var1.G());
      }

      this.a(var1.fr());
      this.a(8);
      this.a(0);
      this.c(var1.F() >= 128 ? 3 : (var1.a().s() ? 7 : 2));
      if (var1.a().aP() == 15) {
         this.a(7738);
      } else if (var1.a().aP() == 18) {
         this.a(7739);
      } else if (var1.a().aP() == 16) {
         this.a(7740);
      } else if (var1.G() > 0) {
         int var8 = var1.G() + 8042;
         this.a(Math.max(8042, Math.min(var8, 8056)));
      } else {
         this.a(0);
      }

      this.c(0);
   }

   public S_AddItem(L1ItemInstance var1, int var2) {
      this.c(1);
      this.b(588);
      PBMessageALL3.g.a var3 = PBMessageALL3.g.aa();
      PBMessageALL9.c.a var4 = PBMessageALL9.c.ak();
      var4.a(var2);
      var4.b(1000 + var2);
      var4.c(var2);
      var4.d(var2);
      var4.e(var2);
      var4.g(7490);
      var4.h(var1.F());
      var4.i(var1.v());
      var4.j(8);
      var4.k(0);
      if (var1.a().aP() == 28) {
         var4.f(var1.a().V() - 1);
         var4.m(var1.a().V() - 1);
         if (var1.a().V() == 1) {
            var4.f(18);
            var4.m(18);
         }
      } else {
         var4.f(var1.I());
         var4.m(var1.G());
      }

      var4.n(var1.F() >= 128 ? 3 : (var1.a().s() ? 7 : 2));
      var4.e(LineageUtil.a("[" + var2 + "]"));
      if (var1.C()) {
         var4.f(g.a(var1.t()));
      }

      var3.e(var4.M().f());
      this.a(var3.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_AddItem";
   }
}
