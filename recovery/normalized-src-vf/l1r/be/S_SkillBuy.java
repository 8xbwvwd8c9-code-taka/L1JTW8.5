package l1r.be;

import java.util.ArrayList;
import l1r.ap.L1PcInstance;

public class S_SkillBuy extends ServerBasePacket {
   public S_SkillBuy(L1PcInstance var1) {
      ArrayList var2 = a(var1);
      this.c(60);
      this.a(100);
      this.b(var2.size());

      for (int var3 : var2) {
         this.a(var3);
      }
   }

   public static ArrayList<Integer> a(L1PcInstance var0) {
      int var1 = 0;
      switch (var0.ay()) {
         case 0:
            if (var0.ev() > 20) {
               var1 = 16;
            } else if (var0.ev() > 10) {
               var1 = 8;
            }
            break;
         case 1:
         case 7:
            if (var0.ev() >= 50) {
               var1 = 8;
            }
            break;
         case 2:
            if (var0.ev() >= 24) {
               var1 = 23;
            } else if (var0.ev() >= 16) {
               var1 = 16;
            } else if (var0.ev() >= 8) {
               var1 = 8;
            }
            break;
         case 3:
            if (var0.ev() >= 12) {
               var1 = 23;
            } else if (var0.ev() >= 8) {
               var1 = 16;
            } else if (var0.ev() >= 4) {
               var1 = 8;
            }
            break;
         case 4:
            if (var0.ev() >= 24) {
               var1 = 16;
            } else if (var0.ev() >= 12) {
               var1 = 8;
            }
         case 5:
         case 6:
      }

      ArrayList var2 = new ArrayList<>();

      for (int var3 = 0; var3 < var1; var3++) {
         if (!var0.h(var3 + 1)) {
            var2.add(var3);
         }
      }

      return var2;
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SkillBuy";
   }
}
