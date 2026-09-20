package l1r.ap;

import l1r.ao.NpcTable;
import l1r.be.S_Html;
import l1r.bh.L1Npc;

public class L1QuestInstance extends L1NpcInstance {
   public L1QuestInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void Z_() {
      int var1 = this.U_().b();
      if (!this.ae()) {
         if (var1 != 71075 && var1 != 70957 && var1 != 81209) {
            this.w = false;
            this.q();
         }
      }
   }

   @Override
   public void a(L1PcInstance var1, String var2) {
      if (var2.equalsIgnoreCase("start")) {
         int var3 = this.U_().b();
         if ((var3 == 71092 || var3 == 71093) && var1.z() && var1.bb().a(3) == 4) {
            L1Npc var7 = NpcTable.a().a(71093);
            new L1FollowerInstance(var7, this, var1);
            var1.a(new S_Html(this.fr(), ""));
         } else if (var3 == 71094 && var1.C() && var1.bb().a(4) == 2) {
            L1Npc var6 = NpcTable.a().a(71094);
            new L1FollowerInstance(var6, this, var1);
            var1.a(new S_Html(this.fr(), ""));
         } else if (var3 == 70957 || var3 == 81209) {
            L1Npc var5 = NpcTable.a().a(70957);
            new L1FollowerInstance(var5, this, var1);
            var1.a(new S_Html(this.fr(), ""));
         } else if (var3 == 81350 && var1.bb().a(4) == 3) {
            L1Npc var4 = NpcTable.a().a(81350);
            new L1FollowerInstance(var4, this, var1);
            var1.a(new S_Html(this.fr(), ""));
         }
      }
   }
}
