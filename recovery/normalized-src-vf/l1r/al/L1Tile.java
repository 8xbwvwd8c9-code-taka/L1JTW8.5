package l1r.al;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.ax.L1WorldMap;
import l1r.be.S_SystemMessage;

public class L1Tile implements L1CommandExecutor {
   private static final Logger a = Logger.getLogger(L1Tile.class.getName());

   private L1Tile() {
   }

   public static L1CommandExecutor a() {
      return new L1Tile();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         int var4 = var1.fs();
         int var5 = var1.ft();
         int var6 = var1.fp();
         int var7 = L1WorldMap.b().a(var6).a(var4, var5 - 1);
         int var8 = L1WorldMap.b().a(var6).a(var4 + 1, var5 - 1);
         int var9 = L1WorldMap.b().a(var6).a(var4 + 1, var5);
         int var10 = L1WorldMap.b().a(var6).a(var4 + 1, var5 + 1);
         int var11 = L1WorldMap.b().a(var6).a(var4, var5 + 1);
         int var12 = L1WorldMap.b().a(var6).a(var4 - 1, var5 + 1);
         int var13 = L1WorldMap.b().a(var6).a(var4 - 1, var5);
         int var14 = L1WorldMap.b().a(var6).a(var4 - 1, var5 - 1);
         String var15 = String.format("0:%d 1:%d 2:%d 3:%d 4:%d 5:%d 6:%d 7:%d", var7, var8, var9, var10, var11, var12, var13, var14);
         var1.a(new S_SystemMessage(var15));
      } catch (Exception var16) {
         a.log(Level.SEVERE, var16.getLocalizedMessage(), var16);
      }
   }
}
