package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;
import l1r.bi.Random;

public class S_027 extends L1SkillExecutor {
   private final int a = 27;
   private final L1Skills b = SkillsTable.a().a(27);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         this.a(var1, this.b);
      } else if (var2 == 0) {
         return;
      }

      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         L1ItemInstance var4 = var3.v();
         if (var4 != null) {
            int var5 = Random.a(3) + 1;
            var3.a(new S_ServerMessage(268, var4.s()));
            var3.j().c(var4, var5);
         }
      } else if (var1 instanceof L1NpcInstance) {
         L1NpcInstance var6 = (L1NpcInstance)var1;
         var6.h(true);
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(27);
         if (var9) {
            this.a(var7, this.b);
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var10 = (L1PcInstance)var7;
               L1ItemInstance var11 = var10.v();
               if (var11 != null) {
                  int var12 = Random.a(var1.eD() / 3) + 1;
                  var10.a(new S_ServerMessage(268, var11.s()));
                  var10.j().c(var11, var12);
               }
            } else if (var7 instanceof L1NpcInstance) {
               L1NpcInstance var13 = (L1NpcInstance)var7;
               var13.h(true);
               var13.j(27, 3000);
            }
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1NpcInstance) {
         L1NpcInstance var2 = (L1NpcInstance)var1;
         var2.h(false);
      }
   }
}
