package l1r.be;

public class S_Key extends ServerBasePacket {
   public S_Key(int var1) {
      this.c(165);
      this.a(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Key";
   }
}
