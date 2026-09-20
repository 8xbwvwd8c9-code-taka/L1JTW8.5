package l1r.al;

import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.IpTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;

public class L1BanIp implements L1CommandExecutor {
   private static final Logger a = Logger.getLogger(L1BanIp.class.getName());

   private L1BanIp() {
   }

   public static L1CommandExecutor a() {
      return new L1BanIp();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         String var5 = var4.nextToken();
         String var6 = null;

         try {
            var6 = var4.nextToken();
         } catch (Exception var12) {
            a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
         }

         IpTable var7 = IpTable.a();
         boolean var8 = var7.b(var5);

         for (L1PcInstance var9 : L1World.a().c()) {
            if (var5.equals(var9.aK().g())) {
               String var11 = "IP:" + var5 + " 連線中的角色名稱:" + var9.et();
               var1.a(new S_SystemMessage(var11));
            }
         }

         if ("add".equalsIgnoreCase(var6) && !var8) {
            var7.a(var5);
            String var17 = "IP:" + var5 + " 被新增到封鎖名單。";
            var1.a(new S_SystemMessage(var17));
         } else if ("del".equalsIgnoreCase(var6) && var8) {
            if (var7.c(var5)) {
               String var16 = "IP:" + var5 + " 已從封鎖名單中刪除。";
               var1.a(new S_SystemMessage(var16));
            }
         } else if (var8) {
            String var14 = "IP:" + var5 + " 已被登記在封鎖名單中。";
            var1.a(new S_SystemMessage(var14));
         } else {
            String var15 = "IP:" + var5 + " 尚未被登記在封鎖名單中。";
            var1.a(new S_SystemMessage(var15));
         }
      } catch (Exception var13) {
         var1.a(new S_SystemMessage("請輸入 " + var2 + " IP [ add | del ]。"));
      }
   }
}
