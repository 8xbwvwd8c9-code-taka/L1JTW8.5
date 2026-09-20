package l1r.be;

import l1r.aq.L1Location;

public class S_EffectLocation extends ServerBasePacket {
   public S_EffectLocation(L1Location var1, int var2) {
      this(var1.f(), var1.g(), var2);
   }

   public S_EffectLocation(int var1, int var2, int var3) {
      this.c(83);
      this.b(var1);
      this.b(var2);
      this.b(var3);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_EffectLocation";
   }
}
