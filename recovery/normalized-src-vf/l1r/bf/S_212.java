package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_Paralysis;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;

public class S_212 extends L1SkillExecutor {
   private final int a = 212;
   private final L1Skills b = SkillsTable.a().a(212);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         L1Object var7 = L1World.a().a(var2);
         if (!(var7 instanceof L1Character)) {
            var6.a(new S_ServerMessage(79));
            return;
         }

         L1Character var8 = (L1Character)var7;
         L1Magic var9 = new L1Magic(var1, var8);
         int var10 = var9.b(212);
         var9.a(var10, 0);
         this.a(var1, var8, this.b, var10);
         var6.a(new S_SkillSound(var8.fr(), this.b.u()));
         var6.b(new S_SkillSound(var8.fr(), this.b.u()));
         if (var8.bB(66)) {
            return;
         }

         boolean var11 = var9.a(212);
         if (var11) {
            this.a(var8, this.b);
            var8.j(66, this.b.v() * 1000);
            if (var8 instanceof L1PcInstance) {
               L1PcInstance var12 = (L1PcInstance)var8;
               var12.a(new S_Paralysis(3, true));
            } else if (var8 instanceof L1NpcInstance) {
               L1NpcInstance var13 = (L1NpcInstance)var8;
               var13.U(true);
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
