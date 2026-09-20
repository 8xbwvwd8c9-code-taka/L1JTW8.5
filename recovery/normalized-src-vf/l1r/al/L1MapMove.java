package l1r.al;

import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.be.S_SystemMessage;

public class L1MapMove implements L1CommandExecutor {
   private L1MapMove() {
   }

   public static L1CommandExecutor a() {
      return new L1MapMove();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         int var5 = Integer.parseInt(var4.nextToken());
         L1Map var6 = L1WorldMap.b().a(var5);
         if (var6 == null) {
            var1.a(new S_SystemMessage("地圖: " + var5 + " 找不到。"));
            return;
         }

         L1Map var7 = var6;
         int var8 = (var7.b + var7.d) / 2;
         int var9 = (var7.c + var7.e) / 2;
         if (!var7.c(var8, var9)) {
            for (int var10 = var7.b; var10 < var7.d - 1; var10 += 2) {
               for (int var11 = var7.c; var11 < var7.e - 1; var11 += 2) {
                  if (var7.c(var10, var11)) {
                     L1Teleport.a(var1, var10, var11, var5, 5, true);
                     var1.a(new S_SystemMessage("座標 " + var10 + ", " + var11 + ", " + var5 + "已經到達。"));
                     return;
                  }
               }
            }

            var1.a(new S_SystemMessage(var2 + " 找不到可移動的座標。"));
            return;
         }

         L1Teleport.a(var1, var8, var9, var5, 5, true);
         var1.a(new S_SystemMessage("座標 " + var8 + ", " + var9 + ", " + var5 + "已經到達。"));
      } catch (Exception var12) {
         var1.a(new S_SystemMessage(var2 + "請輸入 [地圖編號]。"));
      }
   }
}
