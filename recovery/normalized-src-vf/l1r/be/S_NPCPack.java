package l1r.be;

import l1r.an.PBMessageALL2;
import l1r.ap.L1NpcInstance;
import l1r.bi.LineageUtil;

public class S_NPCPack extends ServerBasePacket {
   public S_NPCPack(L1NpcInstance var1) {
      this.c(1);
      this.b(119);
      PBMessageALL2.L1R_e.L1R_a var2 = PBMessageALL2.L1R_e.aO();
      var2.a((var1.ft() << 16) + var1.fs());
      var2.b(var1.fr());
      var2.c(var1.fe());
      var2.d(var1.eY());
      var2.e(var1.fb());
      var2.f(var1.fh());
      var2.g(var1.fc());
      var2.h(var1.fa());
      var2.e(LineageUtil.a(var1.T()));
      var2.f(LineageUtil.a(var1.eZ()));
      var2.i(var1.fc());
      var2.j(var1.fd());
      var2.k(0);
      var2.l(0);
      var2.m(var1.fn() == 2 ? 1 : 0);
      var2.n(var1.U_().X() ? 1 : 0);
      var2.o(0);
      var2.p(var1.fn() == 1 ? 1 : 0);
      var2.q(0);
      var2.g(LineageUtil.a(""));
      var2.h(LineageUtil.a(""));
      var2.r(var1.ac());
      var2.s(-1);
      var2.t(var1.ev());
      var2.u(-1);
      var2.v(0);
      var2.w(0);
      var2.y(-1);
      var2.A(0);
      var2.C(12);
      this.a(var2.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_NPCPack";
   }
}
