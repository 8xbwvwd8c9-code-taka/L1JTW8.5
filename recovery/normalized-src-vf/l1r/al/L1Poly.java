package l1r.al;

import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;

public class L1Poly implements L1CommandExecutor {
   private L1Poly() {
   }

   public static L1CommandExecutor a() {
      return new L1Poly();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         String var5 = var4.nextToken();
         int var6 = Integer.parseInt(var4.nextToken());
         L1PcInstance var7 = L1World.a().a(var5);
         if (var7 == null) {
            var1.a(new S_ServerMessage(73, var5));
         } else {
            try {
               L1PolyMorph.a(var7, var6, 7200, 2);
            } catch (Exception var9) {
               var1.a(new S_SystemMessage("請輸入 .poly 玩家名稱 變身代碼。"));
            }
         }
      } catch (Exception var10) {
         var1.a(new S_SystemMessage(var2 + " 請輸入  玩家名稱 變身代碼。"));
      }
   }
}
