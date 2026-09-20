package l1r.al;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Party;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;

public class L1PartyRecall implements L1CommandExecutor {
   private static final Logger a = Logger.getLogger(L1PartyRecall.class.getName());

   private L1PartyRecall() {
   }

   public static L1CommandExecutor a() {
      return new L1PartyRecall();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      L1PcInstance var4 = L1World.a().a(var3);
      if (var4 != null) {
         L1Party var5 = var4.aL();
         if (var5 != null) {
            int var6 = var1.fs();
            int var7 = var1.ft() + 2;
            int var8 = var1.fp();

            for (L1PcInstance var9 : var5.c()) {
               try {
                  L1Teleport.a(var9, var6, var7, var8, 5, true);
                  var9.a(new S_SystemMessage("您被傳喚到GM身邊。"));
               } catch (Exception var12) {
                  a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
               }
            }
         } else {
            var1.a(new S_SystemMessage("請輸入要召喚的角色名稱。"));
         }
      } else {
         var1.a(new S_SystemMessage("不再線上。"));
      }
   }
}
