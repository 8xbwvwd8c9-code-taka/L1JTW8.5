package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.be.S_PacketBox;

public class L1Patrol implements L1CommandExecutor {
   private L1Patrol() {
   }

   public static L1CommandExecutor a() {
      return new L1Patrol();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      var1.a(new S_PacketBox(45));
   }
}
