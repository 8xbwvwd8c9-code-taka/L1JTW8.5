package l1r.al;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;
import l1r.bf.L1SkillExecutor;
import l1r.bi.LineageUtil;

public class L1Buff implements L1CommandExecutor {
   private L1Buff() {
   }

   public static L1CommandExecutor a() {
      return new L1Buff();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         Collection var5 = null;
         String var6 = var4.nextToken();
         if (var6.equalsIgnoreCase("me")) {
            var5 = new ArrayList<>();
            var5.add(var1);
            var6 = var4.nextToken();
         } else if (var6.equalsIgnoreCase("all")) {
            var5 = L1World.a().c();
            var6 = var4.nextToken();
         } else {
            var5 = L1World.a().f(var1);
         }

         int var7 = Integer.parseInt(var6);
         int var8 = 0;
         if (var4.hasMoreTokens()) {
            var8 = Integer.parseInt(var4.nextToken());
         }

         L1SkillExecutor var9 = LineageUtil.a(var7);

         for (L1PcInstance var10 : var5) {
            var9.a(var10, var8);
         }
      } catch (Exception var12) {
         var1.a(new S_SystemMessage("請輸入 " + var2 + " [all|me] skillId time。"));
      }
   }
}
