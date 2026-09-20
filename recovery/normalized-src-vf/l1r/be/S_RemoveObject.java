package l1r.be;

import l1r.aq.L1Object;

public class S_RemoveObject extends ServerBasePacket {
   public S_RemoveObject(L1Object var1) {
      this.c(52);
      this.a(var1.fr());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_RemoveObject";
   }
}
