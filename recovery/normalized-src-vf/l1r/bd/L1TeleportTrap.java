package l1r.bd;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;

public class L1TeleportTrap extends L1Trap__obf_i {
   private final L1Location a;

   public L1TeleportTrap(TrapStorage var1) {
      super(var1);
      int var2 = var1.b("teleportX");
      int var3 = var1.b("teleportY");
      int var4 = var1.b("teleportMapId");
      this.a = new L1Location(var2, var3, var4);
   }

   public L1TeleportTrap(int var1, int var2, L1Location var3) {
      super(var1, var2, false);
      this.a = var3;
   }

   @Override
   public void a(L1PcInstance var1, L1Object var2) {
      this.a(var2);
      if (this.a.f() != 0 && this.a.g() != 0) {
         L1Teleport.a(var1, this.a.f(), this.a.g(), this.a.b(), 5, true, false);
      } else {
         L1Teleport.a(var1, 50);
      }
   }
}
