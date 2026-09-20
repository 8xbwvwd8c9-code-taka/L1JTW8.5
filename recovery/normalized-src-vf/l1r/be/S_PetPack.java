package l1r.be;

import l1r.an.PBMessageALL2;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.bi.LineageUtil;

public class S_PetPack extends ServerBasePacket {
   public S_PetPack(L1PetInstance var1, L1PcInstance var2) {
      this.c(1);
      this.b(119);
      PBMessageALL2.e.a var3 = PBMessageALL2.e.aO();
      var3.a((var1.ft() << 16) + var1.fs());
      var3.b(var1.fr());
      var3.c(var1.fe());
      var3.d(var1.eY());
      var3.e(var1.fb());
      var3.f(var1.fh());
      var3.g(var1.fc());
      var3.h(0);
      var3.e(LineageUtil.a(var1.et()));
      var3.f(LineageUtil.a(""));
      var3.i(var1.fc());
      var3.j(var1.fd());
      var3.k(0);
      var3.l(0);
      var3.m(var1.fn() == 2 ? 1 : 0);
      var3.n(0);
      var3.o(0);
      var3.p(var1.fn() == 1 ? 1 : 0);
      var3.q(0);
      var3.g(LineageUtil.a(""));
      String var4 = var1.M() == null ? "" : var1.M().et();
      var3.h(LineageUtil.a(var4));
      var3.r(0);
      if (var1.M() != null && var1.M().fr() == var2.fr()) {
         int var5 = var1.ew() != 0 ? 100 * var1.ea() / var1.ew() : 100;
         var3.s(var5);
      } else {
         var3.s(255);
      }

      var3.t(var1.ev());
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
      return "S_PetPack";
   }
}
