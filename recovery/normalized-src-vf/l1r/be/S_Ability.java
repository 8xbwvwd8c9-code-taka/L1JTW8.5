package l1r.be;

public class S_Ability extends ServerBasePacket {
   public S_Ability(int var1, boolean var2) {
      this.a(var1, var2);
   }

   private void a(int var1, boolean var2) {
      this.c(11);
      this.c(var1);
      if (var2) {
         this.c(1);
      } else {
         this.c(0);
      }
   }

   public S_Ability(int var1, int var2) {
      this.c(11);
      this.c(var1);
      this.c(var2);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Ability";
   }
}
