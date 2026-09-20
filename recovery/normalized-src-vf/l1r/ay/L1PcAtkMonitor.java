package l1r.ay;

import l1r.aj.C_Attack;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1SpeedChecker;
import l1r.aq.L1World;
import l1r.bi.BinaryOutputStream;

public class L1PcAtkMonitor extends L1PcMonitor {
   public L1PcAtkMonitor(int var1) {
      super(var1);
   }

   @Override
   public void a(L1PcInstance var1) {
      try {
         while (var1.cK() != 0) {
            L1Object var2 = L1World.a().a(var1.cK());
            if (!(var2 instanceof L1Character)) {
               return;
            }

            L1Character var3 = (L1Character)L1World.a().a(var1.cK());
            if (var3 == null || var1.eX()) {
               return;
            }

            BinaryOutputStream var4 = new BinaryOutputStream();
            var4.c(111);
            var4.a(var1.cK());
            var4.b(var3.fs());
            var4.b(var3.ft());
            new C_Attack(var4.b(), var1.aK());
            var4.close();
            int var5 = var1.ce().b(L1SpeedChecker.a.b);
            Thread.sleep(var5);
         }
      } catch (Exception var6) {
      }
   }
}
