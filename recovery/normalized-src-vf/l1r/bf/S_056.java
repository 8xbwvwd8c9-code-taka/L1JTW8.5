package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_OwnCharAttrDef;
import l1r.bh.L1Skills;

public class S_056 extends L1SkillExecutor {
   private final int a = 56;
   private final L1Skills b = SkillsTable.a().a(56);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         this.a(var1, this.b);
         var2 = this.b.v();
      } else if (var2 == 0) {
         return;
      }

      if (!var1.bB(56)) {
         var1.ck(-6);
         var1.bL(12);
      }

      var1.j(56, var2 * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_OwnCharAttrDef(var3));
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         if (var8.a(56)) {
            if (!var7.bB(56)) {
               var7.ck(-6);
               var7.bL(12);
            }

            var7.j(56, this.b.v() * 1000);
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var9 = (L1PcInstance)var7;
               var9.a(new S_OwnCharAttrDef(var9));
            }

            this.a(var7, this.b);
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.ck(6);
      var1.bL(-12);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_OwnCharAttrDef(var2));
      }
   }
}
