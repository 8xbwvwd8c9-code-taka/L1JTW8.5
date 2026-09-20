package l1r.be;

import l1r.am.ListSprReader__obf_c;
import l1r.an.PBMessageALL2;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.bi.LineageUtil;
import l1r.l1j.server.Config;

public class S_OwnCharPack extends ServerBasePacket {
   public S_OwnCharPack(L1PcInstance var1) {
      this.c(1);
      this.b(119);
      PBMessageALL2.L1R_e.L1R_a var2 = PBMessageALL2.L1R_e.aO();
      var2.a((var1.ft() << 16) + var1.fs());
      var2.b(var1.fr());
      int var3 = var1.eX() ? var1.cj() : var1.fe();
      int var4 = var1.eX() ? var1.eY() : var1.k();
      if (!ListSprReader__obf_c.a().b(var3, var4)) {
         var4 = 0;
      }

      var2.c(var3);
      var2.d(var4);
      var2.e(var1.fb());
      var2.f(var1.fi());
      var2.g(1);
      var2.h(var1.fa());
      var2.e(LineageUtil.a(var1.et()));
      var2.f(LineageUtil.a(var1.eZ()));
      var2.i(var1.fc());
      var2.j(var1.fd());
      var2.k(var1.L() ? 8 : (var1.aS() ? 1 : 0));
      var2.l(0);
      var2.m(var1.fn() == 2 ? 1 : 0);
      var2.n(1);
      var2.o(var1.bN() ? 1 : 0);
      var2.p(var1.fn() == 1 ? 1 : 0);
      L1Clan var5 = ClanTable.a().a(var1.aF());
      var2.q(var5 == null ? 0 : var5.i());
      var2.g(LineageUtil.a(var1.aG()));
      var2.h(LineageUtil.a(""));
      var2.r(0);
      var2.s(var1.q() ? 100 * var1.ea() / var1.ew() : -1);
      var2.t(0);
      var2.i(LineageUtil.a(""));
      var2.u(-1);
      var2.v(0);
      var2.w(var1.aq());
      var2.y(-1);
      var2.A(Config.a);
      if (var1.dX() > 0) {
         var2.B(var1.dX());
      }

      this.a(var2.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_OwnCharPack";
   }
}
