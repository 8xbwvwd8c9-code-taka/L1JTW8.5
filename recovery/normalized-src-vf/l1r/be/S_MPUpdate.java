package l1r.be;

public class S_MPUpdate extends ServerBasePacket {
   public S_MPUpdate(int var1, int var2) {
      this.c(48);
      if (var1 < 0) {
         this.b(0);
      } else if (var1 > 32767) {
         this.b(32767);
      } else {
         this.b(var1);
      }

      if (var2 < 1) {
         this.b(1);
      } else if (var2 > 32767) {
         this.b(32767);
      } else {
         this.b(var2);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_MPUpdate";
   }
}
