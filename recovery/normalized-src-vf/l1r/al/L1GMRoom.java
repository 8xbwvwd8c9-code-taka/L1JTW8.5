package l1r.al;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.be.S_SystemMessage;

public class L1GMRoom implements L1CommandExecutor {
   private static final Logger a = Logger.getLogger(L1GMRoom.class.getName());

   private L1GMRoom() {
   }

   public static L1CommandExecutor a() {
      return new L1GMRoom();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         int var4 = 0;

         try {
            var4 = Integer.parseInt(var3);
         } catch (NumberFormatException var6) {
            a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         }

         if (var4 == 1) {
            L1Teleport.a(var1, 32737, 32796, 99, 5, true);
         } else if (var4 == 2) {
            L1Teleport.a(var1, 32734, 32799, 17100, 5, true);
         } else if (var4 == 3) {
            L1Teleport.a(var1, 32644, 32955, 0, 5, true);
         } else if (var4 == 4) {
            L1Teleport.a(var1, 33429, 32814, 4, 5, true);
         } else if (var4 == 5) {
            L1Teleport.a(var1, 32894, 32535, 300, 5, true);
         }
      } catch (Exception var7) {
         var1.a(new S_SystemMessage("請輸入 .gmroom1～.gmroom5 or .gmroom name 。"));
      }
   }
}
