package l1r.be;

import l1r.an.PBMessageALL5;
import l1r.ap.L1PcInstance;
import l1r.bi.LineageUtil;

public class S_ClanName extends ServerBasePacket {
   public S_ClanName(L1PcInstance var1, boolean var2) {
      this.c(248);
      this.a(var1.fr());
      this.a(var2 ? var1.aG() : "");
      this.a(0);
      this.c(0);
      this.c(var2 ? 10 : 11);
      this.b(0);
   }

   public S_ClanName(L1PcInstance var1) {
      this.c(1);
      this.b(537);
      PBMessageALL5.c.a var2 = PBMessageALL5.c.aa();
      var2.e(LineageUtil.a(var1.aG()));
      var2.a(var1.aH());
      this.a(var2.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ClanName";
   }
}
