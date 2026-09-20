package l1r.be;

public class S_Deposit extends ServerBasePacket {
   public S_Deposit(int var1) {
      this.c(196);
      this.a(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Deposit";
   }
}
