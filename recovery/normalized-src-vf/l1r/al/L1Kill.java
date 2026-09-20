package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;

public class L1Kill implements L1CommandExecutor {
   private L1Kill() {
   }

   public static L1CommandExecutor a() {
      return new L1Kill();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         L1PcInstance var4 = L1World.a().a(var3);
         if (var4 != null) {
            var4.a(0);
            var4.b((L1Character)null);
         }
      } catch (Exception var5) {
         var1.a(new S_SystemMessage("請輸入 : " + var2 + " 玩家名稱。"));
      }
   }
}
