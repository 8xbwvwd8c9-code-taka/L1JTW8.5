package l1r.be;

import l1r.am.ListSprReader__obf_c;
import l1r.an.PBMessageALL2;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.bi.LineageUtil;
import l1r.l1j.server.Config;

public class S_OtherCharPacks extends ServerBasePacket {
   public S_OtherCharPacks(L1PcInstance var1) {
      this.a(var1, false);
   }

   public S_OtherCharPacks(L1PcInstance var1, boolean var2) {
      this.a(var1, var2);
   }

   private void a(L1PcInstance var1, boolean var2) {
      this.c(1);
      this.b(119);
      PBMessageALL2.L1R_e.L1R_a var3 = PBMessageALL2.L1R_e.aO();
      var3.a((var1.ft() << 16) + var1.fs());
      var3.b(var1.fr());
      int var4 = var1.eX() ? var1.cj() : var1.fe();
      int var5 = var1.eX() ? var1.eY() : var1.k();
      if (var1.cq()) {
         var5 = 71;
      } else if (var1.aX()) {
         var5 = 70;
      }

      if (!ListSprReader__obf_c.a().b(var4, var5)) {
         var5 = 0;
      }

      var3.c(var4);
      var3.d(var5);
      var3.e(var1.fb());
      var3.f(var1.fi());
      var3.g(var1.fc());
      var3.h(var1.fa());
      var3.e(LineageUtil.a(var1.et()));
      var3.f(LineageUtil.a(var1.eZ()));
      var3.i(var1.fc());
      var3.j(var1.fd());
      var3.k(var1.L() ? 8 : (var1.aS() ? 1 : 0));
      var3.l(var2 ? 0 : (var1.ff() ? 1 : 0));
      var3.m(var1.fn() == 2 ? 1 : 0);
      var3.n(1);
      var3.o(var1.bN() ? 1 : 0);
      var3.p(var1.fn() == 1 ? 1 : 0);
      L1Clan var6 = ClanTable.a().a(var1.aF());
      var3.q(var6 == null ? 0 : var6.i());
      var3.g(LineageUtil.a(var1.aG()));
      var3.h(LineageUtil.a(""));
      var3.r(0);
      var3.s(-1);
      var3.t(0);
      var3.i(LineageUtil.a(""));
      var3.u(-1);
      var3.v(0);
      var3.w(var1.aq());
      var3.y(-1);
      var3.A(Config.a);
      if (var1.dX() > 0) {
         var3.B(var1.dX());
      }

      this.a(var3.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_OtherCharPacks";
   }
}
