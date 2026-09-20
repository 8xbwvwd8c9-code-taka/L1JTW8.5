package l1r.ay;

import l1r.ap.L1PcInstance;

public class L1PcInvisDelay extends L1PcMonitor {
   public L1PcInvisDelay(int var1) {
      super(var1);
   }

   @Override
   public void a(L1PcInstance var1) {
      var1.w(-1);
   }
}
