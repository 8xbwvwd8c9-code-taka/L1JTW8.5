package al;

import ao.bg;
import be.ei;
import java.util.StringTokenizer;

public class ab implements l {
   private ab() {
   }

   public static l a() {
      return new ab();
   }

   @Override
   public void a(ap.u var1, String var2, String var3) {
      String var4 = null;

      try {
         try {
            StringTokenizer var5 = new StringTokenizer(var3);
            String var6 = var5.nextToken();
            int var7 = Integer.parseInt(var5.nextToken().trim());
            bh.l var8 = ao.au.a().a(var7);
            if (var8 == null) {
               var4 = "找不到符合條件的NPC。";
               return;
            }

            if (var6.equalsIgnoreCase("mob")) {
               if (!var8.d().equals("L1Monster")) {
                  var4 = "指定的NPC不是L1Monster類型。";
                  return;
               }

               bg.a(var1, var8);
            } else if (var6.equalsIgnoreCase("npc")) {
               ao.at.a().a(var1, var8);
            }

            bg.a(var7, var1, 0, 0L);
            var4 = var8.c() + " (" + var7 + ") " + "新增到資料庫中。";
         } catch (Exception var12) {
            var4 = "請輸入 : " + var2 + " mob|npc NPCID 。";
         }
      } finally {
         if (var4 != null) {
            var1.a(new ei(var4));
         }
      }
   }
}
