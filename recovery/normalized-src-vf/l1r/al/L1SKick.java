package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;
import l1r.bj.ClientThread;

public class L1SKick implements L1CommandExecutor {
   private L1SKick() {
   }

   public static L1CommandExecutor a() {
      return new L1SKick();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         L1PcInstance var4 = L1World.a().a(var3);
         if (var4 != null) {
            var1.a(new S_SystemMessage(var4.et() + "已被您強制踢除遊戲。"));
            var4.cG(33080);
            var4.cH(33392);
            var4.cE(4);
            ClientThread var5 = var4.aK();
            var4.aK().c();
            System.out.println("GM的踢除指令使得(" + var5.a() + ":" + var5.g() + ")的連線被強制中斷。");
         } else {
            var1.a(new S_SystemMessage("指定的ID不存在。"));
         }
      } catch (Exception var6) {
         var1.a(new S_SystemMessage("請輸入: " + var2 + " 玩家名稱。"));
      }
   }
}
