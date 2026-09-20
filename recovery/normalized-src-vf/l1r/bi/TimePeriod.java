package l1r.bi;

import java.sql.Time;
import l1r.at.L1GameTime;

public class TimePeriod {
   private final Time a;
   private final Time b;

   public TimePeriod(Time var1, Time var2) {
      if (var1.equals(var2)) {
         throw new IllegalArgumentException("timeBegin must not equals timeEnd");
      }

      this.a = var1;
      this.b = var2;
   }

   private boolean a(L1GameTime var1, Time var2, Time var3) {
      Time var4 = var1.b();
      return var2.compareTo(var4) <= 0 && var3.compareTo(var4) > 0;
   }

   public boolean a(L1GameTime var1) {
      return this.a.after(this.b) ? !this.a(var1, this.b, this.a) : this.a(var1, this.a, this.b);
   }
}
