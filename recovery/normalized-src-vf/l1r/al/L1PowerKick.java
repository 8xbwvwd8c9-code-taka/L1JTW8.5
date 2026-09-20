package l1r.al;

import l1r.ao.IpTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_Disconnect;
import l1r.be.S_SystemMessage;

public class L1PowerKick implements L1CommandExecutor {
   private L1PowerKick() {
   }

   public static L1CommandExecutor a() {
      return new L1PowerKick();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         L1PcInstance var4 = L1World.a().a(var3);
         IpTable var5 = IpTable.a();
         if (var4 != null) {
            var5.a(var4.aK().g());
            var1.a(new S_SystemMessage(var4.et() + "被您強制踢除遊戲並封鎖IP。"));
            var4.a(new S_Disconnect(0));
         } else {
            var1.a(new S_SystemMessage("您指定的腳色名稱不存在。"));
         }
      } catch (Exception var6) {
         var1.a(new S_SystemMessage("請輸入 : " + var2 + " 玩家名稱。"));
      }
   }
}
