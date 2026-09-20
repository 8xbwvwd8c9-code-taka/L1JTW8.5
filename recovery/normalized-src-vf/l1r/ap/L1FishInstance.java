package l1r.ap;

import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import l1r.be.S_ChangeHeading;
import l1r.be.S_DoActionGFX;
import l1r.be.S_NPCPack;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1FishInstance extends L1NpcInstance {
   private final ScheduledFuture<?> y = GeneralThreadPool.a().a(new L1FishInstance.L1R_a(null), 1000L, (30 + Random.a(30)) * 1000);

   public L1FishInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_NPCPack(this));
   }

   private class L1R_a extends TimerTask {
      private L1R_a() {
      }

      @Override
      public void run() {
         if (L1FishInstance.this.ah()) {
            L1FishInstance.this.y.cancel(true);
         }

         L1FishInstance.this.ct(Random.a(8));
         L1FishInstance.this.b(new S_ChangeHeading(L1FishInstance.this));
         L1FishInstance.this.b(new S_DoActionGFX(L1FishInstance.this.fr(), 0));
      }

      // $VF: synthetic method
      L1R_a(L1FishInstance.L1R_a var2) {
         this();
      }
   }
}
