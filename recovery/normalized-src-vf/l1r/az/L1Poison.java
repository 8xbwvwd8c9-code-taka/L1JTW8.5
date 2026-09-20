package l1r.az;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_ServerMessage;

public abstract class L1Poison {
   protected static boolean a(L1Character var0) {
      if (var0 == null) {
         return false;
      }

      if (var0.eo() != null) {
         return false;
      }

      if (!(var0 instanceof L1PcInstance)) {
         return true;
      }

      L1PcInstance var1 = (L1PcInstance)var0;
      return !var1.dL() && !var1.bB(104);
   }

   protected static void a(L1Character var0, int var1) {
      if (var0 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var0;
         var2.a(new S_ServerMessage(var1));
      }
   }

   public abstract int a();

   public abstract void b();
}
