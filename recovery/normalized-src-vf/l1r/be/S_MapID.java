package l1r.be;

import l1r.an.PBMessageALL;
import l1r.l1j.server.Config;

public class S_MapID extends ServerBasePacket {
   public S_MapID(int var1, boolean var2) {
      this.c(1);
      this.b(118);
      PBMessageALL.a.a var3 = PBMessageALL.a.aa();
      var3.a(var1);
      var3.b(Config.a);
      var3.c(var2 ? 1 : 0);
      var3.d(0);
      var3.e(0);
      var3.f(0);
      this.a(var3.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_MapID";
   }
}
