package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1World;
import l1r.be.S_Message_YN;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;
import l1r.bi.Random;

public class S_067 extends L1SkillExecutor {
   private final int a = 67;
   private final L1Skills b = SkillsTable.a().a(67);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         this.a(var7, this.b);
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(67);
         if (var7 instanceof L1PcInstance && var1 instanceof L1PcInstance) {
            L1PcInstance var10 = (L1PcInstance)var1;
            L1PcInstance var11 = (L1PcInstance)var7;
            if (var11.fr() == var10.fr()) {
               var9 = true;
            }

            if (var11.aF() != 0 && var11.aF() == var10.aF()) {
               var9 = true;
            }
         }

         if (var9) {
            int var13 = Random.a(L1PolyMorph.f.length);
            int var14 = L1PolyMorph.f[var13];
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var12 = (L1PcInstance)var7;
               if (var12.j().h(20281)) {
                  var12.a(new S_Message_YN(180, ""));
                  var12.t(true);
               } else {
                  L1PolyMorph.a(var12, var14, this.b.v(), 1);
               }

               if (var12.fr() != var1.fr()) {
                  var12.a(new S_ServerMessage(241, var1.et()));
               }
            } else if (var7 instanceof L1MonsterInstance) {
               L1MonsterInstance var15 = (L1MonsterInstance)var7;
               if (var15.ev() > 60) {
                  this.b(var1, 79);
                  return;
               }

               L1PolyMorph.a(var15, var14, this.b.v(), 1);
            }
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      L1PolyMorph.b(var1);
   }
}
