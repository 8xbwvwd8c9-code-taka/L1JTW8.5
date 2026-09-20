package l1r.be;

import l1r.aq.L1Character;

public class S_ChangeHeading extends ServerBasePacket {
   public S_ChangeHeading(L1Character var1) {
      this.c(40);
      this.a(var1.fr());
      this.c(var1.fb());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ChangeHeading";
   }
}
