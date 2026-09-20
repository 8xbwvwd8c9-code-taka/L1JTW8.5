package l1r.ap;

import l1r.aq.L1Character;
import l1r.be.S_ChangeHeading;
import l1r.bh.L1Npc;
import l1r.bi.CalcExp;

public class L1ScarecrowInstance extends L1NpcInstance {
   public L1ScarecrowInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void b(L1Character var1, int var2) {
      if (this.ea() > 0 && !this.eX() && var2 > 0) {
         if (this.fb() < 7) {
            this.ct(this.fb() + 1);
         } else {
            this.ct(0);
         }

         this.b(new S_ChangeHeading(this));
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            var3.a(this);
            if (var3.ev() < 5) {
               this.n.a(var3, 1);
               CalcExp.a(var3, this, this.n);
            }
         }
      }

      this.n.a();
   }
}
