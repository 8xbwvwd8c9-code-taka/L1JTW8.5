package l1r.be;

import l1r.an.PBMessageALL2;
import l1r.ap.L1SignboardInstance;
import l1r.bi.LineageUtil;

public class S_SignboardPack extends ServerBasePacket {
   public S_SignboardPack(L1SignboardInstance var1) {
      this.c(1);
      this.b(119);
      PBMessageALL2.e.a var2 = PBMessageALL2.e.aO();
      var2.a((var1.ft() << 16) + var1.fs());
      var2.b(var1.fr());
      var2.c(var1.fe());
      var2.d(0);
      var2.e(this.e(var1.fb()));
      var2.f(0);
      var2.g(0);
      var2.h(0);
      var2.e(LineageUtil.a(""));
      var2.f(LineageUtil.a(var1.T()));
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
      var2.t(1);
      var2.u(-1);
      var2.v(0);
      var2.w(0);
      var2.y(-1);
      var2.A(0);
      this.a(var2.M().g());
      this.b(0);
   }

   private int e(int var1) {
      int var2 = 0;
      switch (var1) {
         case 2:
            var2 = 1;
            break;
         case 3:
            var2 = 2;
            break;
         case 4:
            var2 = 3;
         case 5:
         default:
            break;
         case 6:
            var2 = 4;
            break;
         case 7:
            var2 = 5;
      }

      return var2;
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SignboardPack";
   }
}
