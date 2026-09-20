package l1r.bf;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_PacketBox;
import l1r.be.S_SPMR;

public class S_121 extends L1SkillExecutor {
   private final int a = 121;

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var1.bz(121);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            if (var3.q()) {
               int var4 = var3.aL().b();
               if (var4 >= 1 && var4 <= 4) {
                  var3.co(8);
                  var3.bt(1);
                  var3.a(new S_PacketBox(180, 477, 3430, 1));
               } else if (var4 >= 5 && var4 <= 6) {
                  var3.co(9);
                  var3.cd(2);
                  var3.bt(2);
                  var3.a(new S_PacketBox(180, 478, 3431, 1));
               } else if (var4 >= 7) {
                  var3.co(10);
                  var3.cd(2);
                  var3.ch(2);
                  var3.bt(3);
                  var3.a(new S_PacketBox(180, 479, 3432, 1));
               }

               var3.a(new S_SPMR(var3));
               var1.j(121, 86400000);
            }
         }
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         if (var2.dP() == 1) {
            var2.co(-8);
            var2.a(new S_PacketBox(180, 477, 3430, 0));
         } else if (var2.dP() == 2) {
            var2.co(-9);
            var2.cd(-2);
            var2.a(new S_PacketBox(180, 478, 3431, 0));
         } else if (var2.dP() == 3) {
            var2.co(-10);
            var2.cd(-2);
            var2.ch(-2);
            var2.a(new S_PacketBox(180, 479, 3432, 0));
         }

         var2.bt(0);
         var2.a(new S_SPMR(var2));
      }
   }
}
