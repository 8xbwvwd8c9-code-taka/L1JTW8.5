package l1r.be;

import l1r.an.PBMessageALL2;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;
import l1r.bi.LineageUtil;

public class S_SummonPack extends ServerBasePacket {
   public S_SummonPack(L1SummonInstance var1, L1PcInstance var2) {
      this.a(var1, var2, true);
   }

   public S_SummonPack(L1SummonInstance var1, L1PcInstance var2, boolean var3) {
      this.a(var1, var2, var3);
   }

   private void a(L1SummonInstance var1, L1PcInstance var2, boolean var3) {
      this.c(1);
      this.b(119);
      PBMessageALL2.e.a var4 = PBMessageALL2.e.aO();
      var4.a((var1.ft() << 16) + var1.fs());
      var4.b(var1.fr());
      var4.c(var1.fe());
      var4.d(var1.eY());
      var4.e(var1.fb());
      var4.f(var1.fh());
      var4.g(var1.fc());
      var4.h(0);
      var4.e(LineageUtil.a(var1.T()));
      var4.f(LineageUtil.a(""));
      var4.i(var1.fc());
      var4.j(var1.fd());
      var4.k(0);
      var4.l(0);
      var4.m(var1.fn() == 2 ? 1 : 0);
      var4.n(0);
      var4.o(0);
      var4.p(var1.fn() == 1 ? 1 : 0);
      var4.q(0);
      var4.g(LineageUtil.a(""));
      String var5 = "";
      if (var3 && var1.L()) {
         var5 = var1.M().et();
      }

      var4.h(LineageUtil.a(var5));
      var4.r(0);
      if (var1.M() != null && var1.M().fr() == var2.fr()) {
         int var6 = var1.ew() != 0 ? 100 * var1.ea() / var1.ew() : 100;
         var4.s(var6);
      } else {
         var4.s(255);
      }

      var4.t(var1.ev());
      var4.u(-1);
      var4.v(0);
      var4.w(0);
      var4.y(-1);
      var4.A(0);
      this.a(var4.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SummonPack";
   }
}
