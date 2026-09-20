package l1r.al;

import java.util.List;
import l1r.ao.FurnitureSpawnTable;
import l1r.ao.LetterTable;
import l1r.ao.PetTable;
import l1r.ap.L1FurnitureInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.au.L1Inventory;

public class L1DeleteGroundItem implements L1CommandExecutor {
   private L1DeleteGroundItem() {
   }

   public static L1CommandExecutor a() {
      return new L1DeleteGroundItem();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      for (L1Object var4 : L1World.a().b()) {
         if (var4 instanceof L1ItemInstance) {
            L1ItemInstance var6 = (L1ItemInstance)var4;
            if (var6.fs() != 0 || var6.ft() != 0) {
               List var7 = L1World.a().c(var6, 0);
               if (var7.size() == 0) {
                  L1Inventory var8 = L1World.a().a(var6.fs(), var6.ft(), var6.fp());
                  int var9 = var6.N();
                  if (var9 == 40314 || var9 == 40316) {
                     PetTable.a().a(var6.fr());
                  } else if (var9 >= 49016 && var9 <= 49025) {
                     LetterTable.a().a(var6.fr());
                  } else if (var9 >= 41383 && var9 <= 41400 && var4 instanceof L1FurnitureInstance) {
                     L1FurnitureInstance var10 = (L1FurnitureInstance)var4;
                     if (var10.f() == var6.fr()) {
                        FurnitureSpawnTable.a().b(var10);
                     }
                  }

                  var8.c(var6);
                  L1World.a().d(var6);
                  L1World.a().b(var6);
               }
            }
         }
      }

      L1World.a().d("地上的垃圾被GM清除了。");
   }
}
