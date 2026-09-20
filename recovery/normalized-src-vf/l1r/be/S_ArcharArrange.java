package l1r.be;

import l1r.bh.L1Castle;

public class S_ArcharArrange extends ServerBasePacket {
   public S_ArcharArrange(L1Castle var1) {
      this.c(216);
      this.a(var1.a());
      this.b(var1.a());
      this.b(12);
      this.b(var1.l().size());

      for (int var2 = 0; var2 < var1.l().size(); var2++) {
         this.b(var2);
         this.a(var1.l().get(var2).b);
      }

      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ArcharArrange";
   }
}
