package l1r.aj;

import l1r.bj.ClientThread;

public class C_Disconnect extends ClientBasePacket {
   public C_Disconnect(byte[] var1, ClientThread var2) {
      super(var1);
      var2.c();
   }

   @Override
   public String a() {
      return "C_Disconnect";
   }
}
