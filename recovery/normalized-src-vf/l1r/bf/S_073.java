package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_PacketBox;
import l1r.bh.L1Skills;

public class S_073 extends L1SkillExecutor {
   private final int a = 73;
   private final L1Skills b = SkillsTable.a().a(73);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         L1Magic var8 = new L1Magic(var1, var7);
         if (var8.a(73)) {
            var7.j(73, this.b.v() * 1000);
            this.a(var7, this.b);
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var9 = (L1PcInstance)var7;
               var9.a(new S_PacketBox(86, 92, this.b.v() + 1));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_PacketBox(86, 92, 0));
      }
   }
}
