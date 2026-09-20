package l1r.be;

import l1r.bh.L1BoardTopic;

public class S_BoardRead extends ServerBasePacket {
   public S_BoardRead(int var1) {
      this.e(var1);
   }

   private void e(int var1) {
      L1BoardTopic var2 = L1BoardTopic.a(var1);
      this.c(138);
      this.a(var1);
      this.a(var2.b());
      this.a(var2.d());
      this.a(var2.c());
      this.a(var2.e());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_BoardRead";
   }
}
