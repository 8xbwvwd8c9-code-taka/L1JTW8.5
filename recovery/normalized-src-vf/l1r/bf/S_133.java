package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_OwnCharAttrDef;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;

public class S_133 extends L1SkillExecutor {
   private final int a = 133;
   private final L1Skills b = SkillsTable.a().a(133);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var8 = (L1PcInstance)var1;
            L1Magic var9 = new L1Magic(var1, var7);
            boolean var10 = var9.a(133);
            if (var10 && !var7.bB(133)) {
               int var11 = -50;
               if (var8.bC() == 1) {
                  var7.cb(-50);
               } else if (var8.bC() == 2) {
                  var7.ca(-50);
               } else if (var8.bC() == 4) {
                  var7.bZ(-50);
               } else {
                  if (var8.bC() != 8) {
                     var8.a(new S_ServerMessage(79));
                     return;
                  }

                  var7.bY(-50);
               }

               var7.cc(var8.bC());
               var7.j(133, this.b.v() * 1000);
               if (var7 instanceof L1PcInstance) {
                  L1PcInstance var12 = (L1PcInstance)var7;
                  var12.a(new S_OwnCharAttrDef(var12));
               }

               this.a(var7, this.b);
            } else {
               var8.a(new S_ServerMessage(280));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      int var2 = 50;
      if (var1.eJ() == 1) {
         var1.cb(50);
      } else if (var1.eJ() == 2) {
         var1.ca(50);
      } else if (var1.eJ() == 4) {
         var1.bZ(50);
      } else if (var1.eJ() == 8) {
         var1.bY(50);
      }

      var1.cc(0);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_OwnCharAttrDef(var3));
      }
   }
}
