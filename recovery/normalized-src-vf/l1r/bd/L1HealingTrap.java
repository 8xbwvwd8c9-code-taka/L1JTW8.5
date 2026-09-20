package l1r.bd;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;

public class L1HealingTrap extends L1Trap__obf_i {
   private final L1Trap__obf_a a;
   private final int b;
   private final int c;

   public L1HealingTrap(TrapStorage var1) {
      super(var1);
      this.a = new L1Trap__obf_a(var1.b("dice"));
      this.b = var1.b("base");
      this.c = var1.b("diceCount");
   }

   @Override
   public void a(L1PcInstance var1, L1Object var2) {
      this.a(var2);
      int var3 = this.a.a(this.c) + this.b;
      var1.z(var3);
   }
}
