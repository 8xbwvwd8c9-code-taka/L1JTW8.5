package l1r.be;

import l1r.bh.L1Castle;

public class S_MercenaryEmpoly extends ServerBasePacket {
   public S_MercenaryEmpoly(L1Castle var1) {
      this.c(71);
      this.a(var1.a());
      this.a(var1.f());
      this.b(var1.k().size());

      for (int var2 = 0; var2 < var1.k().size(); var2++) {
         this.b(var2);
         this.a(var1.k().get(var2).b);
         this.b(4000);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_MercenaryEmpoly";
   }
}
