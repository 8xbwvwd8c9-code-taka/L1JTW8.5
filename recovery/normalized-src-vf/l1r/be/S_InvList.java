package l1r.be;

import a.g;
import java.util.List;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL9;
import l1r.ap.L1ItemInstance;
import l1r.bi.LineageUtil;

public class S_InvList extends ServerBasePacket {
   public S_InvList(List<L1ItemInstance> var1) {
      this.c(1);
      this.b(588);
      PBMessageALL3.L1R_g.L1R_a var2 = PBMessageALL3.L1R_g.aa();

      for (L1ItemInstance var3 : var1) {
         PBMessageALL9.L1R_c.L1R_a var5 = PBMessageALL9.L1R_c.ak();
         var5.a(var3.fr());
         var5.b(var3.m());
         var5.c(var3.fr());
         var5.d(var3.E());
         var5.e(var3.a().U());
         var5.g(var3.e());
         var5.h(var3.F());
         var5.i(var3.v());
         var5.j(8);
         var5.k(0);
         if (var3.a().aP() == 28) {
            var5.f(var3.a().V() - 1);
            var5.m(var3.a().V() - 1);
            if (var3.a().V() == 1) {
               var5.f(18);
               var5.m(18);
            }
         } else {
            var5.f(var3.I());
            var5.m(var3.G());
         }

         var5.n(var3.F() >= 128 ? 3 : (var3.a().s() ? 7 : 2));
         var5.e(LineageUtil.a(var3.r()));
         if (var3.C()) {
            var5.f(g.a(var3.t()));
         }

         var2.e(var5.M().f());
      }

      var2.b(1);
      this.a(var2.M().g());
      this.b(0);
   }

   public S_InvList(List<L1ItemInstance> var1, int var2) {
      this.c(237);
      this.c(var1.size());

      for (L1ItemInstance var3 : var1) {
         this.a(var3.fr());
         this.b(var3.m());
         this.c(var3.a().U());
         if (var3.a().aP() == 28) {
            this.c(var3.a().V() - 1);
         } else {
            this.c(var3.I());
         }

         this.b(var3.e());
         this.c(var3.F());
         this.a(var3.E());
         this.c(var3.v());
         this.a(var3.r());
         if (!var3.C()) {
            this.c(0);
         } else {
            byte[] var5 = var3.t();
            this.c(var5.length);
            byte[] var9 = var5;
            int var8 = var5.length;

            for (int var7 = 0; var7 < var8; var7++) {
               byte var6 = var9[var7];
               this.c(var6);
            }
         }

         this.c(24);
         this.c(0);
         this.b(0);
         this.b(0);
         if (var3.a().aP() == 28) {
            this.c(var3.a().V() - 1);
         } else {
            this.c(var3.G());
         }

         this.a(var3.fr());
         this.a(8);
         this.a(0);
         this.c(var3.F() >= 128 ? 3 : (var3.a().s() ? 7 : 2));
         if (var3.a().aP() == 15) {
            this.a(7738);
         } else if (var3.a().aP() == 18) {
            this.a(7739);
         } else if (var3.a().aP() == 16) {
            this.a(7740);
         } else if (var3.G() > 0) {
            int var10 = var3.G() + 8042;
            this.a(Math.max(8042, Math.min(var10, 8056)));
         } else {
            this.a(0);
         }

         this.c(0);
      }
   }

   @Override
   public byte[] a() {
      return this.aW.toByteArray();
   }

   @Override
   public String b() {
      return "S_InvList";
   }
}
