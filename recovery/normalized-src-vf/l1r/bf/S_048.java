package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;

public class S_048 extends L1SkillExecutor {
   private final int a = 48;
   private final L1Skills b = SkillsTable.a().a(48);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            L1ItemInstance var4 = var3.v();
            if (var4 != null && var4.g()) {
               var4.a(var3, 48, this.b.v() * 1000);
               var3.a(new S_ServerMessage(161, var4.s(), "$245", "$247"));
               if (var4.D()) {
                  var3.a(new S_PacketBox(154, 2176, this.b.v()));
               }
            } else {
               var3.a(new S_ServerMessage(79));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         if (var7 instanceof L1PcInstance) {
            L1PcInstance var8 = (L1PcInstance)var7;
            L1ItemInstance var9 = var8.v();
            if (var9 != null && var9.g()) {
               var9.a(var8, 48, this.b.v() * 1000);
               var8.a(new S_ServerMessage(161, var9.s(), "$245", "$247"));
               if (var9.D()) {
                  var8.a(new S_PacketBox(154, 2176, this.b.v()));
               }

               this.a(var7, this.b);
            } else {
               var8.a(new S_ServerMessage(79));
            }
         } else {
            this.a(var7, this.b);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
