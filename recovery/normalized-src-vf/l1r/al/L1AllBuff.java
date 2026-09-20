package l1r.al;

import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bf.L1SkillExecutor;
import l1r.bi.LineageUtil;

public class L1AllBuff implements L1CommandExecutor {
   private static final Logger a = Logger.getLogger(L1AllBuff.class.getName());

   private L1AllBuff() {
   }

   public static L1CommandExecutor a() {
      return new L1AllBuff();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         String var5 = var4.nextToken();
         L1PcInstance var6 = L1World.a().a(var5);
         if (var6 == null) {
            var1.a(new S_ServerMessage(73, var5));
            return;
         }

         for (int var7 = 1; var7 <= 609; var7++) {
            if (var7 != 120 && var7 != 78 && var7 != 31) {
               L1SkillExecutor var8 = LineageUtil.a(var7);
               if (var8 != null) {
                  var8.a(var6, 0);
               }
            }
         }

         new L1Speed().a(var1);
         L1PolyMorph.a(var6, 5641, 7200, 2);
      } catch (Exception var9) {
         var1.a(new S_SystemMessage("請輸入 .allBuff 玩家名稱。"));
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      }
   }
}
