package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_FixWeaponList;
import l1r.bj.ClientThread;

public class C_FixWeaponList extends ClientBasePacket {
   private static final String a = "[C] C_FixWeaponList";

   public C_FixWeaponList(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         var3.a(new S_FixWeaponList(var3));
      }
   }

   @Override
   public String a() {
      return "[C] C_FixWeaponList";
   }
}
