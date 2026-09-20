package l1r.al;

import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;
import l1r.be.S_Weather;

public class L1ChangeWeather implements L1CommandExecutor {
   private L1ChangeWeather() {
   }

   public static L1CommandExecutor a() {
      return new L1ChangeWeather();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         int var5 = Integer.parseInt(var4.nextToken());
         L1World.a().c(var5);
         L1World.a().a(new S_Weather(var5));
      } catch (Exception var6) {
         var1.a(new S_SystemMessage("請輸入 " + var2 + " 0～3、16～19。"));
      }
   }
}
