package l1r.al;

import java.util.StringTokenizer;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_Lawful;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;

public class L1Status implements L1CommandExecutor {
   private L1Status() {
   }

   public static L1CommandExecutor a() {
      return new L1Status();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         String var5 = var4.nextToken();
         String var6 = var4.nextToken();
         int var7 = Integer.parseInt(var4.nextToken());
         L1PcInstance var8 = null;
         if (var5.equalsIgnoreCase("me")) {
            var8 = var1;
         } else {
            var8 = L1World.a().a(var5);
         }

         if (var8 == null) {
            var1.a(new S_ServerMessage(73, var5));
            return;
         }

         if (var6.equalsIgnoreCase("AC")) {
            var8.bL((byte)(var7 - var8.ey()));
         } else if (var6.equalsIgnoreCase("MR")) {
            var8.co((short)(var7 - var8.W_()));
         } else if (var6.equalsIgnoreCase("HIT")) {
            var8.cm((short)(var7 - var8.eT()));
         } else if (var6.equalsIgnoreCase("DMG")) {
            var8.ck((short)(var7 - var8.eR()));
         } else {
            if (var6.equalsIgnoreCase("HP")) {
               var8.m(var7 - var8.bd());
               var8.bx(var8.ew());
            } else if (var6.equalsIgnoreCase("MP")) {
               var8.n(var7 - var8.be());
               var8.by(var8.ex());
            } else if (var6.equalsIgnoreCase("LAWFUL")) {
               var8.cr(var7);
               S_Lawful var9 = new S_Lawful(var8.fr(), var8.fa());
               var8.a(var9);
               var8.b(var9);
            } else if (var6.equalsIgnoreCase("KARMA")) {
               var8.A(var7);
            } else if (var6.equalsIgnoreCase("GM")) {
               if (var7 > 200) {
                  var7 = 200;
               }

               var8.ae(var7);
               var8.a(new S_SystemMessage(var1.et() + "賦與你GM權限。"));
            } else if (var6.equalsIgnoreCase("STR")) {
               var8.o(var7 - var8.bf());
            } else if (var6.equalsIgnoreCase("CON")) {
               var8.p(var7 - var8.bg());
            } else if (var6.equalsIgnoreCase("DEX")) {
               var8.q(var7 - var8.bh());
            } else if (var6.equalsIgnoreCase("INT")) {
               var8.s(var7 - var8.bj());
            } else if (var6.equalsIgnoreCase("WIS")) {
               var8.t(var7 - var8.bk());
            } else {
               if (!var6.equalsIgnoreCase("CHA")) {
                  var1.a(new S_SystemMessage("狀態 " + var6 + " 不明。"));
                  return;
               }

               var8.r(var7 - var8.bi());
            }

            var8.I();
         }

         var8.a(new S_OwnCharStatus(var8));
         var1.a(new S_SystemMessage(var8.et() + " 的" + var6 + "值" + var7 + "被變更了。"));
      } catch (Exception var10) {
         var1.a(new S_SystemMessage("請輸入: " + var2 + " 玩家名稱|me 屬性 變更值 。"));
      }
   }
}
