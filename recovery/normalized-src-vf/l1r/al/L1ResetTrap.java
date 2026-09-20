package l1r.al;

import l1r.ao.TrapSpawnTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;

public class L1ResetTrap implements L1CommandExecutor {
   private L1ResetTrap() {
   }

   public static L1CommandExecutor a() {
      return new L1ResetTrap();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      TrapSpawnTable.a().c();
      var1.a(new S_SystemMessage("陷阱已被重新分配"));
   }
}
