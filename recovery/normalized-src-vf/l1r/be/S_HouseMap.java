package l1r.be;

public class S_HouseMap extends ServerBasePacket {
   public S_HouseMap(int var1, String var2) {
      this.a(var1, var2);
   }

   private void a(int var1, String var2) {
      int var3 = Integer.valueOf(var2);
      this.c(78);
      this.a(var1);
      this.a(var3);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_HouseMap";
   }
}
