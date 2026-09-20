package l1r.be;

import java.util.Date;
import l1r.ai.GameServer;
import l1r.an.PBMessageALL9;
import l1r.ao.RankingTable;
import l1r.bi.LineageUtil;

public class S_Rank extends ServerBasePacket {
   public S_Rank(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.c(121);
      this.c(166);
      this.c(0);
      this.a((int)(new Date().getTime() / 1000L));
      this.a(var1);
      this.a(var2);
      this.a(var3);
      this.a(var4);
      this.a(var5);
      this.a(var6);
   }

   public S_Rank(int var1, RankingTable.a... var2) {
      this.c(1);
      this.b(136);
      PBMessageALL9.e.a var3 = PBMessageALL9.e.G();
      var3.b(0);
      var3.c(GameServer.a().a);
      var3.d(var1);
      var3.e(1);
      var3.f(1);

      for (int var4 = 0; var4 < var2.length; var4++) {
         RankingTable.a var5 = var2[var4];
         PBMessageALL9.a.a var6 = PBMessageALL9.a.G();
         int var7 = 1;
         if (var4 + 1 >= 31 && var4 + 1 <= 60) {
            var7++;
         } else if (var4 + 1 >= 11 && var4 + 1 <= 30) {
            var7 += 2;
         } else if (var4 + 1 >= 1 && var4 + 1 <= 10) {
            var7 += 3;
         }

         var6.b(var7);
         var6.c(var4 + 1);
         var6.d(var5.f);
         var6.e(var5.e);
         var6.e(LineageUtil.a(var5.b));
         var3.e(var6.H().f());
      }

      this.a(var3.H().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Rank";
   }
}
