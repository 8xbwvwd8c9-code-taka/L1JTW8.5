package l1r.bd;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.az.L1DamagePoison;
import l1r.az.L1ParalysisPoison;
import l1r.az.L1SilencePoison;

public class L1PoisonTrap extends L1Trap__obf_i {
   private final String a;
   private final int b;
   private final int c;
   private final int d;

   public L1PoisonTrap(TrapStorage var1) {
      super(var1);
      this.a = var1.a("poisonType");
      this.b = var1.b("poisonDelay");
      this.c = var1.b("poisonTime");
      this.d = var1.b("poisonDamage");
   }

   @Override
   public void a(L1PcInstance var1, L1Object var2) {
      this.a(var2);
      if (this.a.equals("d")) {
         L1DamagePoison.a(var1, var1, this.c, this.d, 30);
      } else if (this.a.equals("s")) {
         L1SilencePoison.b(var1, 120);
      } else if (this.a.equals("p")) {
         L1ParalysisPoison.a(var1, this.b, this.c);
      }
   }
}
