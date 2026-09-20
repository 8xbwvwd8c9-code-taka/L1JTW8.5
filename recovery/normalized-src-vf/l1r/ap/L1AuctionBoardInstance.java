package l1r.ap;

import java.util.ArrayList;
import l1r.ao.HouseTable;
import l1r.aq.L1TownLocation;
import l1r.be.S_AuctionBoard;
import l1r.be.S_Html;
import l1r.bh.L1House;
import l1r.bh.L1Npc;

public class L1AuctionBoardInstance extends L1NpcInstance {
   public L1AuctionBoardInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void c(L1PcInstance var1) {
      ArrayList var2 = new ArrayList<>();

      for (L1House var3 : HouseTable.a().c().values()) {
         if (var3.g()) {
            int var5 = var3.b();
            if (L1TownLocation.a(this) == 7) {
               if (var5 >= 262145 && var5 <= 262189) {
                  var2.add(var3);
               }
            } else if (L1TownLocation.a(this) == 8) {
               if (var5 >= 327681 && var5 <= 327691) {
                  var2.add(var3);
               }
            } else if (L1TownLocation.a(this) == 12) {
               if (var5 >= 458753 && var5 <= 458819) {
                  var2.add(var3);
               }
            } else if (L1TownLocation.a(this) == 3 && var5 >= 65537 && var5 <= 65542) {
               var2.add(var3);
            }
         }
      }

      if (var2.isEmpty()) {
         var1.a(new S_Html(this.fr(), "agnolist"));
      } else {
         var1.a(new S_AuctionBoard(this.fr(), var2));
      }
   }
}
