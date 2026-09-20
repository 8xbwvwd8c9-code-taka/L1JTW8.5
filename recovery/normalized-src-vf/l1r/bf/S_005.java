package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Location;
import l1r.aq.L1Teleport;
import l1r.be.S_Paralysis;
import l1r.be.S_ServerMessage;
import l1r.bh.L1BookMark;
import l1r.bh.L1Skills;

public class S_005 extends L1SkillExecutor {
   private final int a = 5;
   private final L1Skills b = SkillsTable.a().a(5);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         boolean var7 = false;
         if (var6.fp() >= 101 && var6.fp() <= 110) {
            if (var6.j().f(var6.fp() + 640361)) {
               var7 = true;
            } else if (var6.j().f(640472)) {
               var7 = true;
            }
         }

         if (!var6.fq().i() && !var7 || var6.bB(230)) {
            this.b(var1, this.b);
            var6.a(new S_ServerMessage(276));
            var6.a(new S_Paralysis(7, true));
            return;
         }

         L1BookMark var8 = var6.l(var2);
         int var9 = 0;
         if (var8 != null) {
            var3 = var8.d();
            var4 = var8.e();
            var9 = var8.h();
         } else {
            L1Location var10 = var6.fu().a(200, true);
            var3 = var10.f();
            var4 = var10.g();
            var9 = var10.b();
         }

         L1Teleport.a(var6, var3, var4, var9, 5, true);
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
