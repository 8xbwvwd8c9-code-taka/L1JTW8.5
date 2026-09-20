package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_Light;
import l1r.be.S_Sound;
import l1r.bh.L1Skills;

public class S_002 extends L1SkillExecutor {
   private final int a = 2;
   private final L1Skills b = SkillsTable.a().a(2);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      var1.j(2, var2 * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_Light(var3.fr(), 14));
         var3.a(new S_Sound(145));
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      var1.j(2, this.b.v() * 1000);
      this.a(var1, this.b);
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.a(new S_Light(var6.fr(), 14));
         var6.a(new S_Sound(145));
      }

      var1.b(new S_Light(var1.fr(), 14));
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance && !var1.ff()) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.fg();
      }
   }
}
