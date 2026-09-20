package l1r.be;

import l1r.an.PBMessageALL2;
import l1r.ap.L1DoorInstance;
import l1r.bi.LineageUtil;

public class S_DoorPack extends ServerBasePacket {
   public S_DoorPack(L1DoorInstance var1) {
      this.c(1);
      this.b(119);
      PBMessageALL2.L1R_e.L1R_a var2 = PBMessageALL2.L1R_e.aO();
      var2.a((var1.ft() << 16) + var1.fs());
      var2.b(var1.fr());
      var2.c(var1.fe());
      int var3 = var1.eY();
      int var4 = var1.o();
      if (var1.eX()) {
         var2.d(var3);
      } else if (var4 == 28) {
         var2.d(var4);
      } else if (var1.ew() > 1 && var3 != 0) {
         var2.d(var3);
      } else {
         var2.d(var4);
      }

      var2.e(0);
      var2.f(0);
      var2.g(1);
      var2.h(0);
      var2.e(LineageUtil.a(""));
      var2.f(LineageUtil.a(""));
      var2.i(0);
      var2.j(0);
      var2.k(0);
      var2.l(0);
      var2.m(0);
      var2.n(0);
      var2.o(0);
      var2.p(0);
      var2.q(0);
      var2.g(LineageUtil.a(""));
      var2.h(LineageUtil.a(""));
      var2.r(0);
      var2.s(-1);
      var2.t(4);
      var2.u(-1);
      var2.v(0);
      var2.w(0);
      var2.y(-1);
      var2.A(0);
      this.a(var2.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_DoorPack";
   }
}
