package l1r.bg;

import l1r.aq.L1Character;
import l1r.bi.GeneralThreadPool;

public class L1SkillDelay {
   private L1SkillDelay() {
   }

   public static void a(L1Character var0, int var1) {
      var0.W(true);
      GeneralThreadPool.a().a(new L1SkillDelay.a(var0, null), var1);
   }

   private static class a implements Runnable {
      private final L1Character a;

      private a(L1Character var1) {
         this.a = var1;
      }

      @Override
      public void run() {
         this.a.W(false);
      }

      // $VF: synthetic method
      a(L1Character var1, L1SkillDelay.a var2) {
         this(var1);
      }
   }
}
