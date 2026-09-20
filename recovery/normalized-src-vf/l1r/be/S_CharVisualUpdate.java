package l1r.be;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;

public class S_CharVisualUpdate extends ServerBasePacket {
   public S_CharVisualUpdate(L1PcInstance var1) {
      this.c(5);
      this.a(var1.fr());
      this.c(var1.k());
      this.b(65535);
      this.b(0);
   }

   public S_CharVisualUpdate(L1Character var1, int var2) {
      this.c(5);
      this.a(var1.fr());
      this.c(var2);
      this.b(65535);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CharVisualUpdate";
   }
}
