package l1r.be;

public class S_TradeStatus extends ServerBasePacket {
   public S_TradeStatus(int var1) {
      this.c(170);
      this.c(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_TradeStatus";
   }
}
