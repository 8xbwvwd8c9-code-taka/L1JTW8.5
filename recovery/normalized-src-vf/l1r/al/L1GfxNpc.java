package l1r.al;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.am.ListSprReader__obf_c;
import l1r.ao.NpcTable;
import l1r.ap.L1GfxInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1SpeedChecker;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Npc;

public class L1GfxNpc implements L1CommandExecutor {
   private static final Logger a = Logger.getLogger(L1GfxNpc.class.getName());

   private L1GfxNpc() {
   }

   public static L1CommandExecutor a() {
      return new L1GfxNpc();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         String var4 = var3;
         short var5 = 4;
         int var6 = 32000;
         int var7 = 33553;
         int var8 = 6;

         for (L1Object var9 : L1World.a().b(4).values()) {
            if (var9 instanceof L1NpcInstance) {
               L1NpcInstance var11 = (L1NpcInstance)var9;
               if (var11.z() == 45001) {
                  L1World.a().d(var11);
               }
            }
         }

         ArrayList var20 = new ArrayList<>();

         for (int var21 = 33553; var21 < 34053; var21 += 6) {
            for (int var23 = 32000; var23 < 32500; var23 += 6) {
               var20.add(new int[]{var23, var21});
            }
         }

         int var22 = 0;
         ArrayList var24 = new ArrayList<>();
         if (var4.equals("effect")) {
            var24 = ListSprReader__obf_c.a().a(true);
         } else if (var4.equals("effect2")) {
            var24 = ListSprReader__obf_c.a().a(false);
         } else if (var4.equals("mob")) {
            var24 = ListSprReader__obf_c.a().b();
         } else if (var4.equals("poly")) {
            int[] var15 = L1SpeedChecker.e;
            int var14 = L1SpeedChecker.e.length;

            for (int var13 = 0; var13 < var14; var13++) {
               int var12 = var15[var13];
               var24.add(var12);
            }
         } else {
            var24 = ListSprReader__obf_c.a().e(Integer.parseInt(var4));
         }

         Collections.sort(var24);

         for (int var25 : var24) {
            if (var22 < var20.size()) {
               int[] var27 = var20.get(var22);
               int var28 = var27[0];
               int var16 = var27[1];
               L1Npc var17 = NpcTable.a().a(45001);
               if (var17 != null) {
                  L1NpcInstance var18 = new L1NpcInstance(var17);
                  var18.cw(var25);
                  if (var4.equals("effect") || var4.equals("effect2")) {
                     var18 = new L1GfxInstance(var17);
                     var18.cw(var17.z());
                     ((L1GfxInstance)var18).b(var25);
                  }

                  var18.cF(IdFactory.a().c());
                  var18.a("編號: " + var25);
                  var18.cE(4);
                  var18.ct(5);
                  var18.cG(var28);
                  var18.cH(var16);
                  L1World.a().a(var18);
                  L1World.a().c(var18);
                  var22++;
               } else {
                  var1.a(new S_SystemMessage("地圖沒空位放了!!"));
               }
            }
         }

         L1Teleport.a(var1, 32000, 33553, 4, 5, true);
      } catch (Exception var19) {
         var1.a(new S_SystemMessage(var2 + " + 要尋找的type。"));
         a.log(Level.SEVERE, var19.getLocalizedMessage(), var19);
      }
   }

   public void a(int var1, int var2) {
      L1Npc var3 = NpcTable.a().a(45060);
      if (var3 != null) {
         L1NpcInstance var4 = new L1NpcInstance(var3);
         var4.cF(IdFactory.a().c());
         var4.a(" ");
         var4.cE(4);
         var4.ct(5);
         var4.cG(var1);
         var4.cH(var2);
         L1World.a().a(var4);
         L1World.a().c(var4);
      }
   }
}
