package l1r.ap;

import l1r.ao.ClanTable;
import l1r.ao.HouseTable;
import l1r.aq.L1Clan;
import l1r.be.S_Html;
import l1r.bh.L1House;
import l1r.bh.L1Npc;

public class L1HousekeeperInstance extends L1NpcInstance {
   public L1HousekeeperInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void a(L1PcInstance var1) {
      int var2 = this.fr();
      int var3 = this.U_().b();
      String var4 = null;
      String[] var5 = null;
      boolean var6 = false;
      L1Clan var7 = ClanTable.a().a(var1.aF());
      if (var7 != null) {
         int var8 = var7.n();
         if (var8 != 0) {
            L1House var9 = HouseTable.a().a(var8);
            if (var3 == var9.f()) {
               var6 = true;
            }
         }
      }

      if (!var6) {
         L1House var14 = null;

         for (L1House var15 : HouseTable.a().c().values()) {
            if (var3 == var15.f()) {
               var14 = var15;
               break;
            }
         }

         if (var14 == null) {
            System.out.println("L1Housekeeper has some error ! npcid=" + var3);
            return;
         }

         boolean var16 = false;
         String var17 = null;
         String var11 = null;

         for (L1Clan var12 : ClanTable.a().b().values()) {
            if (var14.b() == var12.n()) {
               var16 = true;
               var17 = var12.f();
               var11 = var12.l();
               break;
            }
         }

         if (var16) {
            var4 = "agname";
            var5 = new String[]{var17, var11, var14.c()};
         } else {
            var4 = "agnoname";
            var5 = new String[]{var14.c()};
         }
      }

      if (var4 != null) {
         var1.a(new S_Html(var2, var4, var5));
      } else {
         if (this.E().length() > 0 && var1.fa() < -1000) {
            var1.a(new S_Html(var2, this.E()));
         } else if (this.D().length() > 0) {
            var1.a(new S_Html(var2, this.D()));
         }
      }
   }
}
