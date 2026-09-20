package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_OwnCharAttrDef;
import l1r.bh.L1Skills;

public class S_214 extends L1SkillExecutor {
   private final int a = 214;
   private final L1Skills b = SkillsTable.a().a(214);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (!var1.bB(214)) {
         var1.bL(-8);
      }

      var1.j(214, var2 * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_OwnCharAttrDef(var3));
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (!var7.bB(214)) {
            var7.bL(-8);
         }

         var7.j(214, this.b.v() * 1000);
         this.b(var1, this.b);
         this.a(var7, this.b);
         if (var7 instanceof L1PcInstance) {
            L1PcInstance var8 = (L1PcInstance)var7;
            var8.a(new S_OwnCharAttrDef(var8));
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.bL(8);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_OwnCharAttrDef(var2));
      }
   }
}
