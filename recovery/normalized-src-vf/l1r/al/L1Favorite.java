package l1r.al;

import java.util.HashMap;
import java.util.StringTokenizer;
import l1r.ai.GMCommands;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;

public class L1Favorite implements L1CommandExecutor {
   private static final HashMap<Integer, String> a = new HashMap<>();

   private L1Favorite() {
   }

   public static L1CommandExecutor a() {
      return new L1Favorite();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         if (!a.containsKey(var1.fr())) {
            a.put(var1.fr(), "");
         }

         String var4 = a.get(var1.fr());
         if (var3.startsWith("set")) {
            StringTokenizer var5 = new StringTokenizer(var3);
            var5.nextToken();
            if (!var5.hasMoreTokens()) {
               var1.a(new S_SystemMessage("指令不存在。"));
               return;
            }

            StringBuilder var6 = new StringBuilder();
            String var7 = var5.nextToken();
            if (var7.equalsIgnoreCase(var2)) {
               var1.a(new S_SystemMessage(var2 + " 不能加入自己的名字。"));
               return;
            }

            var6.append(var7 + " ");

            while (var5.hasMoreTokens()) {
               var6.append(var5.nextToken() + " ");
            }

            var4 = var6.toString().trim();
            a.put(var1.fr(), var4);
            var1.a(new S_SystemMessage(var4 + " 被登記在好友名單。"));
         } else if (var3.startsWith("show")) {
            var1.a(new S_SystemMessage("目前登記的指令: " + var4));
         } else if (var4.isEmpty()) {
            var1.a(new S_SystemMessage("沒有被登記的名字。"));
         } else {
            StringBuilder var11 = new StringBuilder();
            StringTokenizer var12 = new StringTokenizer(var3);
            StringTokenizer var13 = new StringTokenizer(var4);

            while (var13.hasMoreTokens()) {
               String var8 = var13.nextToken();
               if (var8.startsWith("%")) {
                  var11.append(var12.nextToken() + " ");
               } else {
                  var11.append(var8 + " ");
               }
            }

            while (var12.hasMoreTokens()) {
               var11.append(var12.nextToken() + " ");
            }

            var1.a(new S_SystemMessage(var11 + " 實行。"));
            GMCommands.a().a(var1, var11.toString());
         }
      } catch (Exception var9) {
         var1.a(new S_SystemMessage("請輸入 " + var2 + " set 玩家名稱 " + "| " + var2 + " show | " + var2 + " [數量]。"));
      }
   }
}
