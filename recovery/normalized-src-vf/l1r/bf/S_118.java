package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;

public class S_118 extends L1SkillExecutor {
   private final int a = 118;
   private final L1Skills b = SkillsTable.a().a(118);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         L1PcInstance var7 = L1World.a().a(var5);
         if (var7 == null) {
            var6.a(new S_ServerMessage(73, var5));
         } else if (var6.aF() != var7.aF()) {
            var6.a(new S_ServerMessage(414));
         } else if (!var6.fq().j()) {
            var6.a(new S_ServerMessage(647));
         } else {
            boolean var8 = L1CastleLocation.b(var7.fs(), var7.ft(), var7.fp());
            if (var7.fq().h() && !var8) {
               L1Teleport.a(var6, var7.fs(), var7.ft(), var7.fp(), 5, true);
            } else {
               var6.a(new S_ServerMessage(79));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
