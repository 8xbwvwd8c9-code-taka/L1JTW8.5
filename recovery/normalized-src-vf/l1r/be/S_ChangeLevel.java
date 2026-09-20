package l1r.be;

import l1r.ap.L1PcInstance;

public class S_ChangeLevel extends ServerBasePacket {
   public S_ChangeLevel(L1PcInstance var1) {
      this.c(108);
      this.a(var1.fr());
      this.c(var1.aq());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ChangeLevel";
   }
}
