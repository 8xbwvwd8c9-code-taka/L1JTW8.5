package l1r.be;

import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.bh.L1Castle;

public class S_MercenaryArrange extends ServerBasePacket {
   public S_MercenaryArrange(L1PcInstance var1, L1Castle var2) {
      this.c(72);
      this.a(var1.fr());
      this.b(var2.a());
      int var3 = var2.i();
      this.b(var3);
      this.b(0);
      this.a(var1.et());
      this.a(var1.fr());
      int var4 = 0;

      for (L1NpcInstance var5 : var1.ek().values()) {
         var4 += var5.Q();
      }

      int var7 = var1.eC() + 6 - var4;
      int var8 = Math.min(var7 / 6, 5);
      this.b(Math.min(var8, var3));
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_MercenaryArrange";
   }
}
