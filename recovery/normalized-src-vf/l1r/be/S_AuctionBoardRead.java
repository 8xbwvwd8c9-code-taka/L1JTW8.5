package l1r.be;

import java.sql.Date;
import java.util.Calendar;
import l1r.ao.HouseTable;
import l1r.bh.L1House;

public class S_AuctionBoardRead extends ServerBasePacket {
   public S_AuctionBoardRead(int var1, String var2) {
      int var3 = Integer.valueOf(var2);
      L1House var4 = HouseTable.a().a(var3);
      this.c(110);
      this.a(var1);
      this.a("agsel");
      this.a(var2);
      this.b(9);
      this.a(var4.c());
      this.a(var4.e());
      this.a("" + var4.d());
      this.a(var4.l());
      this.a(var4.n());
      this.a("" + var4.k());
      Calendar var5 = Calendar.getInstance();
      var5.setTime(new Date(var4.j().getTime()));
      this.a("" + (var5.get(2) + 1));
      this.a("" + var5.get(5));
      this.a("" + var5.get(11));
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_AuctionBoardRead";
   }
}
