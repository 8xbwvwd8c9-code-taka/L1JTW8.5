package l1r.al;

import l1r.ao.TrapSpawnTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;

public class L1ReloadTrap implements L1CommandExecutor {
   private L1ReloadTrap() {
   }

   public static L1CommandExecutor a() {
      return new L1ReloadTrap();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      TrapSpawnTable.b();
      var1.a(new S_SystemMessage("已重新讀取陷阱資料"));
   }
}
