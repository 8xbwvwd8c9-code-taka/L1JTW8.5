package l1r.be;

import java.util.Calendar;
import l1r.l1j.server.Config;

public class S_WarTime extends ServerBasePacket {
   public S_WarTime(Calendar var1) {
      Calendar var2 = Calendar.getInstance();
      var2.set(1997, 0, 1, 17, 0);
      long var3 = var2.getTimeInMillis();
      long var5 = var1.getTimeInMillis();
      long var7 = var5 - var3;
      var7 -= 72000000L;
      var7 /= 60000L;
      int var9 = (int)(var7 / 182L);
      this.c(214);
      this.b(6);
      this.a(Config.l);
      this.c(0);
      this.c(0);
      this.c(0);
      this.a(var9);
      this.c(0);
      this.a(var9 - 1);
      this.c(0);
      this.a(var9 - 2);
      this.c(0);
      this.a(var9 - 3);
      this.c(0);
      this.a(var9 - 4);
      this.c(0);
      this.a(var9 - 5);
      this.c(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_WarTime";
   }
}
