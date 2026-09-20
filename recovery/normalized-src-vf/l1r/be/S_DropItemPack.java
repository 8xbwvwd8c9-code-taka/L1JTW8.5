package l1r.be;

import l1r.an.PBMessageALL2;
import l1r.ao.InnTable;
import l1r.ap.L1ItemInstance;
import l1r.bi.LineageUtil;
import l1r.l1j.server.Config;

public class S_DropItemPack extends ServerBasePacket {
   public S_DropItemPack(L1ItemInstance var1) {
      String var2 = var1.a().i();
      int var3 = var1.C() ? 1 : 0;
      if (var3 == 1) {
         var2 = var1.a().j();
      }

      this.c(1);
      this.b(119);
      PBMessageALL2.e.a var4 = PBMessageALL2.e.aO();
      var4.a((var1.ft() << 16) + var1.fs());
      var4.b(var1.fr());
      var4.c(var1.a().n());
      var4.d(0);
      var4.e(0);
      var4.f(var1.T() ? var1.a().c() : 0);
      var4.g(var1.E());
      var4.h(0);
      String var5 = "";
      if (var1.E() > 1) {
         if (var1.N() == 40312 && var1.M() != 0) {
            var5 = var2 + InnTable.a(var1) + " (" + var1.E() + ")";
         } else {
            var5 = var2 + " (" + var1.E() + ")";
         }
      } else {
         int var6 = var1.N();
         if (var6 == 20383 && var3 == 1) {
            var5 = var2 + " [" + var1.I() + "]";
         } else if (var1.I() != 0 && var3 == 1) {
            var5 = var2 + " (" + var1.I() + ")";
         } else if (var1.a().c() != 0 && var1.T()) {
            var5 = var2 + " ($10)";
         } else if (var1.N() == 40312 && var1.M() != 0) {
            var5 = var2 + InnTable.a(var1);
         } else {
            var5 = var2;
         }
      }

      var4.e(LineageUtil.a(var5));
      var4.f(LineageUtil.a(""));
      var4.i(0);
      var4.j(0);
      var4.k(0);
      var4.l(0);
      var4.m(0);
      var4.n(0);
      var4.o(0);
      var4.p(0);
      var4.q(0);
      var4.g(LineageUtil.a(""));
      var4.h(LineageUtil.a(""));
      var4.r(0);
      var4.s(-1);
      var4.t(0);
      var4.u(-1);
      var4.v(0);
      var4.w(0);
      var4.y(-1);
      var4.A(Config.a);
      this.a(var4.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_DropItem";
   }
}
