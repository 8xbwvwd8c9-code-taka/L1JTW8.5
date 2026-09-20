package l1r.al;

import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;

public class L1ChatNG implements L1CommandExecutor {
   private L1ChatNG() {
   }

   public static L1CommandExecutor a() {
      return new L1ChatNG();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         String var5 = var4.nextToken();
         int var6 = Integer.parseInt(var4.nextToken());
         L1PcInstance var7 = L1World.a().a(var5);
         if (var7 != null) {
            var7.j(1005, var6 * 60 * 1000);
            var7.a(new S_PacketBox(36, var6 * 60));
            var7.a(new S_ServerMessage(286, String.valueOf(var6)));
            var1.a(new S_ServerMessage(287, var5));
         }
      } catch (Exception var8) {
         var1.a(new S_SystemMessage("請輸入 " + var2 + " 玩家名稱 時間(分)。"));
      }
   }
}
