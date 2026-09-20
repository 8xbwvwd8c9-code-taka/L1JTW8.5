package l1r.al;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.ax.L1WorldMap;
import l1r.be.S_SystemMessage;

public class L1Loc implements L1CommandExecutor {
   private static final Logger a = Logger.getLogger(L1Loc.class.getName());

   private L1Loc() {
   }

   public static L1CommandExecutor a() {
      return new L1Loc();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         int var4 = var1.fs();
         int var5 = var1.ft();
         int var6 = var1.fp();
         int var7 = L1WorldMap.b().a(var6).a(var4, var5);
         String var8 = String.format("座標 (%d, %d, %d) %d", var4, var5, var6, var7);
         var1.a(new S_SystemMessage(var8));
      } catch (Exception var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      }
   }
}
