package l1r.be;

import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ap.L1PcInstance;

public class S_Race extends ServerBasePacket {
   public static final int a = 64;
   public static final int b = 65;
   public static final int c = 66;
   public static final int d = 67;
   public static final int e = 68;
   public static final int f = 69;
   public static final int g = 70;

   public S_Race(int var1) {
      this.c(121);
      this.c(var1);
      if (var1 == 64) {
         this.c(5);
      }
   }

   public S_Race(CopyOnWriteArrayList<L1PcInstance> var1, L1PcInstance var2) {
      this.c(121);
      this.c(66);
      this.b(var1.size());
      this.b(var1.indexOf(var2));

      for (L1PcInstance var3 : var1) {
         if (var3 != null) {
            this.a(var3.et());
         }
      }
   }

   public S_Race(int var1, int var2) {
      this.c(121);
      this.c(67);
      this.b(var1);
      this.b(var2);
   }

   public S_Race(String var1, int var2) {
      this.c(121);
      this.c(68);
      this.a(var1);
      this.a(var2 * 1000);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Race";
   }
}
