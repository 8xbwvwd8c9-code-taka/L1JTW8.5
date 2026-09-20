package l1r.aj;

import l1r.bj.ClientThread;

public class C_KeepALIVE extends ClientBasePacket {
   private static final String a = "[C] C_KeepALIVE";

   public C_KeepALIVE(byte[] var1, ClientThread var2) {
      super(var1);
   }

   @Override
   public String a() {
      return "[C] C_KeepALIVE";
   }
}
