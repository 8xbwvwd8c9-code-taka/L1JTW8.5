package l1r.az;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_PacketBox;

public class L1SilencePoison extends L1Poison {
   private final L1Character a;

   public static boolean b(L1Character var0, int var1) {
      if (!L1Poison.a(var0)) {
         return false;
      }

      var0.a(new L1SilencePoison(var0, var1));
      return true;
   }

   private L1SilencePoison(L1Character var1, int var2) {
      this.a = var1;
      this.a(var2);
   }

   private void a(int var1) {
      this.a.y(1);
      a(this.a, 310);
      this.a.j(1007, var1 * 1000);
      if (this.a instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)this.a;
         var2.a(new S_PacketBox(161, 6, var1));
      }
   }

   @Override
   public int a() {
      return 1;
   }

   @Override
   public void b() {
      this.a.bz(1007);
   }
}
