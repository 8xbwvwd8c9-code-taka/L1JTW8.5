package l1r.al;

import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.be.S_SystemMessage;

public class L1Move implements L1CommandExecutor {
   private L1Move() {
   }

   public static L1CommandExecutor a() {
      return new L1Move();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         int var5 = Integer.parseInt(var4.nextToken());
         int var6 = Integer.parseInt(var4.nextToken());
         int var7;
         if (var4.hasMoreTokens()) {
            var7 = Short.parseShort(var4.nextToken());
         } else {
            var7 = var1.fp();
         }

         L1Teleport.a(var1, var5, var6, var7, 5, true);
         var1.a(new S_SystemMessage("座標 " + var5 + ", " + var6 + ", " + var7 + "已經到達。"));
      } catch (Exception var8) {
         var1.a(new S_SystemMessage(var2 + "請輸入 X座標 Y座標 [地圖編號]。"));
      }
   }
}
