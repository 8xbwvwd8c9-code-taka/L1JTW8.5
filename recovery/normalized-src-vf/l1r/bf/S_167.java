package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_PacketBox;
import l1r.bh.L1Skills;

public class S_167 extends L1SkillExecutor {
   private final int a = 167;
   private final L1Skills b = SkillsTable.a().a(167);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      var1.j(167, var2 * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_PacketBox(44, var3.fr(), var2));
      }

      var1.b(new S_PacketBox(44, var1.fr(), var2));
      this.b(var1, 1001);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(167);
         if (var9) {
            var7.j(167, this.b.v() * 1000);
            this.a(var7, this.b);
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var10 = (L1PcInstance)var7;
               var10.a(new S_PacketBox(44, var10.fr(), this.b.v()));
            }

            var7.b(new S_PacketBox(44, var7.fr(), this.b.v()));
            this.b(var7, 1001);
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_PacketBox(44, var2.fr(), 0));
         var2.b(new S_PacketBox(44, var2.fr(), 0));
      }
   }
}
