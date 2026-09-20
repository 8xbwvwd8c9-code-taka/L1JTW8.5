package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;

public class S_021 extends L1SkillExecutor {
   private final int a = 21;
   private final L1Skills b = SkillsTable.a().a(21);

   @Override
   public void a(L1Character var1, int var2) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         L1ItemInstance var4 = var3.j().j(2);
         if (var4 == null) {
            var3.a(new S_ServerMessage(79));
            return;
         }

         this.a(var3, this.b);
         var4.b(var3, this.b.v() * 1000);
         var3.a(new S_ServerMessage(161, var4.s(), "$245", "$247"));
         if (var4.D()) {
            var3.a(new S_PacketBox(154, 748, this.b.v()));
         }
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         L1ItemInstance var7 = var6.j().e(var2);
         if (var7 != null && var7.h() && var7.a().aP() == 2) {
            this.a(var1, this.b);
            var7.b(var6, this.b.v() * 1000);
            var6.a(new S_ServerMessage(161, var7.s(), "$245", "$247"));
            if (var7.D()) {
               var6.a(new S_PacketBox(154, 748, this.b.v()));
            }
         } else {
            var6.a(new S_ServerMessage(79));
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
