package l1r.al;

import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;

public class L1Chat implements L1CommandExecutor {
   private L1Chat() {
   }

   public static L1CommandExecutor a() {
      return new L1Chat();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         if (var4.hasMoreTokens()) {
            String var5 = var4.nextToken();
            String var6;
            if (var5.compareToIgnoreCase("on") == 0) {
               L1World.a().a(true);
               var6 = "開啟全體聊天。";
            } else {
               if (var5.compareToIgnoreCase("off") != 0) {
                  throw new Exception();
               }

               L1World.a().a(false);
               var6 = "關閉全體聊天。";
            }

            var1.a(new S_SystemMessage(var6));
         } else {
            String var8;
            if (L1World.a().k()) {
               var8 = "全體聊天已開啟。.chat off 能使其關閉。";
            } else {
               var8 = "全體聊天已關閉。.chat on 能使其開啟。";
            }

            var1.a(new S_SystemMessage(var8));
         }
      } catch (Exception var7) {
         var1.a(new S_SystemMessage("請輸入 " + var2 + " [on|off]"));
      }
   }
}
