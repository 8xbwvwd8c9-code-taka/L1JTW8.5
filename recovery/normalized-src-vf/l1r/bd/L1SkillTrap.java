package l1r.bd;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.bf.L1SkillExecutor;
import l1r.bi.LineageUtil;

public class L1SkillTrap extends L1Trap__obf_i {
   private static final Logger a = Logger.getLogger(L1SkillTrap.class.getName());
   private final int b;
   private final int c;

   public L1SkillTrap(TrapStorage var1) {
      super(var1);
      this.b = var1.b("skillId");
      this.c = var1.b("skillTimeSeconds");
   }

   @Override
   public void a(L1PcInstance var1, L1Object var2) {
      this.a(var2);

      try {
         L1SkillExecutor var3 = LineageUtil.a(this.b);
         var3.a(var1, this.c);
      } catch (Exception var4) {
         a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
      }
   }
}
