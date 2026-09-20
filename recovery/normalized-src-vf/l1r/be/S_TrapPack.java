package l1r.be;

import l1r.an.PBMessageALL2;
import l1r.ap.L1TrapInstance;
import l1r.bi.LineageUtil;

public class S_TrapPack extends ServerBasePacket {
   public S_TrapPack(L1TrapInstance var1, String var2) {
      this.c(1);
      this.b(119);
      PBMessageALL2.L1R_e.L1R_a var3 = PBMessageALL2.L1R_e.aO();
      var3.a((var1.ft() << 16) + var1.fs());
      var3.b(var1.fr());
      var3.c(var1.g() ? var1.h() : 7);
      var3.d(0);
      var3.e(0);
      var3.f(0);
      var3.g(0);
      var3.h(0);
      var3.e(LineageUtil.a(var2));
      var3.f(LineageUtil.a(""));
      var3.i(0);
      var3.j(0);
      var3.k(0);
      var3.l(0);
      var3.m(0);
      var3.n(0);
      var3.o(0);
      var3.p(0);
      var3.q(0);
      var3.g(LineageUtil.a(""));
      var3.h(LineageUtil.a(""));
      var3.r(0);
      var3.s(-1);
      var3.t(1);
      var3.u(-1);
      var3.v(0);
      var3.w(0);
      var3.y(-1);
      var3.A(0);
      this.a(var3.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Trap";
   }
}
