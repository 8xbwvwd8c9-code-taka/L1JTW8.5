package l1r.be;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import l1r.bh.L1House;

public class S_AuctionBoard extends ServerBasePacket {
   public S_AuctionBoard(int var1, ArrayList<L1House> var2) {
      this.c(227);
      this.a(var1);
      this.b(var2.size());

      for (L1House var3 : var2) {
         this.a(var3.b());
         this.a(var3.c());
         this.b(var3.d());
         Calendar var5 = Calendar.getInstance();
         var5.setTime(new Date(var3.j().getTime()));
         this.c(var5.get(2) + 1);
         this.c(var5.get(5));
         this.a(var3.k());
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_AuctionBoard";
   }
}
