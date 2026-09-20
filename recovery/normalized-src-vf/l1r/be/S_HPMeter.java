package l1r.be;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;

public class S_HPMeter extends ServerBasePacket {
   public S_HPMeter(int var1, int var2, int var3) {
      this.a(var1, var2, var3);
   }

   public S_HPMeter(L1Character var1) {
      int var2 = var1.fr();
      int var3 = var1.ea() <= 0 ? 255 : 100 * var1.ea() / var1.ew();
      int var4 = var1.ex() == 0 ? 255 : 100 * var1.eb() / var1.ex();
      if (!(var1 instanceof L1PcInstance)) {
         var4 = 255;
      }

      this.a(var2, var3, var4);
   }

   private void a(int var1, int var2, int var3) {
      this.c(79);
      this.a(var1);
      this.c(var2);
      this.c(var3);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_HPMeter";
   }
}
