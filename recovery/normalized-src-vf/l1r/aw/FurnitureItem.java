package l1r.aw;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ao.FurnitureSpawnTable;
import l1r.ao.NpcTable;
import l1r.ap.L1FurnitureInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1HouseLocation;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_DoActionGFX;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Npc;

public class FurnitureItem {
   private static final Logger a = Logger.getLogger(FurnitureItem.class.getName());

   public static void a(L1PcInstance var0, L1ItemInstance var1) {
      if (var1 == null) {
         var0.a(new S_ServerMessage(79));
      } else if (!L1HouseLocation.a(var0.fs(), var0.ft(), var0.fp())) {
         var0.a(new S_ServerMessage(563));
      } else {
         for (L1Object var2 : L1World.a().b()) {
            if (var2 instanceof L1FurnitureInstance) {
               L1FurnitureInstance var4 = (L1FurnitureInstance)var2;
               if (var4.f() == var1.fr()) {
                  if (FurnitureSpawnTable.a().deleteDurable(var4)) {
                     var4.aa_();
                  }
                  return;
               }
            }
         }

         if (var0.fb() == 0 || var0.fb() == 2) {
            int var6 = var1.a().V();

            try {
               L1Npc var7 = NpcTable.a().a(var6);
               L1FurnitureInstance var8 = new L1FurnitureInstance(var7);
               var8.cF(IdFactory.a().c());
               var8.cE(var0.fp());
               if (var0.fb() == 0) {
                  var8.cG(var0.fs());
                  var8.cH(var0.ft() - 1);
               } else if (var0.fb() == 2) {
                  var8.cG(var0.fs() + 1);
                  var8.cH(var0.ft());
               }

               var8.q(var8.fs());
               var8.r(var8.ft());
               var8.ct(0);
               var8.b(var1.fr());
               if (!FurnitureSpawnTable.a().insertDurable(var8)) {
                  return;
               }
               L1World.a().a(var8);
               L1World.a().c(var8);
            } catch (Exception var5) {
               a.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
            }
         }
      }
   }

   public static void a(L1PcInstance var0, int var1, L1ItemInstance var2) {
      var0.a(new S_DoActionGFX(var0.fr(), 17));
      var0.b(new S_DoActionGFX(var0.fr(), 17));
      int var3 = var2.I();
      if (var3 > 0) {
         L1Object var4 = L1World.a().a(var1);
         if (var4 != null && var4 instanceof L1FurnitureInstance) {
            L1FurnitureInstance var5 = (L1FurnitureInstance)var4;
            if (!FurnitureSpawnTable.a().deleteDurable(var5)) {
               return;
            }
            var5.aa_();
            var2.g(var2.I() - 1);
            var0.j().b(var2);
         }
      }
   }
}
