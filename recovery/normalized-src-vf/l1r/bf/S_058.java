package l1r.bf;

import l1r.ai.IdFactory;
import l1r.ao.NpcTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1World;
import l1r.bh.L1Npc;
import l1r.bh.L1Skills;

public class S_058 extends L1SkillExecutor {
   private final int a = 58;
   private final L1Skills b = SkillsTable.a().a(58);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         L1NpcInstance var7 = this.a(var3, var4, var6.fp());
         this.b(var1, var7, this.b, 0);
         L1SpawnEffect.a().a(var6, var3, var4);
      }
   }

   private L1NpcInstance a(int var1, int var2, int var3) {
      L1NpcInstance var4 = null;
      L1Npc var5 = NpcTable.a().a(45001);
      if (var5 != null) {
         var4 = new L1NpcInstance(var5);
         var4.cw(2510);
         var4.cF(IdFactory.a().c());
         var4.cG(var1);
         var4.cH(var2);
         var4.cE(var3);
         L1World.a().a(var4);
         L1World.a().c(var4);
         var4.a(1000L);
      }

      return var4;
   }

   @Override
   public void a(L1Character var1) {
   }
}
