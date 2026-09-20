package l1r.at;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import l1r.bi.IntRange;

public class L1GameTime {
   private final int a;
   private final Calendar b;

   private Calendar b(int var1) {
      Calendar var2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
      var2.setTimeInMillis(0L);
      var2.add(13, var1);
      return var2;
   }

   private L1GameTime(int var1) {
      this.a = var1;
      this.b = this.b(var1);
   }

   private static L1GameTime a(long var0) {
      long var2 = var0;
      if (var2 < 0L) {
         throw new IllegalArgumentException();
      }

      int var4 = (int)(var2 * 6L / 1000L);
      int var5 = var4 % 3;
      return new L1GameTime(var4 - var5);
   }

   public static L1GameTime a() {
      return a(System.currentTimeMillis());
   }

   public Time b() {
      int var1 = this.a % 86400;
      return new Time(var1 * 1000L - TimeZone.getDefault().getRawOffset());
   }

   public int a(int var1) {
      return this.b.get(var1);
   }

   public int c() {
      return this.a;
   }

   public Calendar d() {
      return (Calendar)this.b.clone();
   }

   public boolean e() {
      int var1 = this.b.get(11);
      return !IntRange.a(var1, 6, 17);
   }

   @Override
   public String toString() {
      SimpleDateFormat var1 = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
      var1.setTimeZone(this.b.getTimeZone());
      return var1.format(this.b.getTime()) + "(" + this.c() + ")";
   }
}
