package l1r.be;

import l1r.ai.IdFactory;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;

public class S_AttackPacket extends ServerBasePacket {
   public static final int a = 0;
   public static final int b = 6;
   public static final int c = 8;
   public static final int d = 0;
   public static final int e = 2;
   public static final int f = 4;
   public static final int g = 8;

   public S_AttackPacket(L1Character var1, int var2, int var3, int var4, int var5) {
      this.c(163);
      this.c(var3);
      this.a(var1.fr());
      this.a(var2);
      this.b(var4);
      this.c(var1.fb());
      this.a(0);
      this.c(var5);
      if (var5 == 2) {
         this.a(this.a(var1));
      } else {
         this.a(0);
      }

      this.b(0);
   }

   private int a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         int var3 = var2.k();
         if (var3 == 0) {
            return 0;
         }

         if (var3 == 24) {
            return 13409;
         }

         if (var3 == 50) {
            return 13410;
         }

         if (var3 == 4) {
            return 13411;
         }

         if (var3 == 46) {
            return 13412;
         }

         if (var3 == 40 || var3 == 58) {
            return 13413;
         }

         if (var3 == 11) {
            return 13414;
         }

         if (var3 == 88) {
            return 13415;
         }

         if (var3 == 58) {
            return 13416;
         }

         if (var3 == 54) {
            return 13417;
         }

         if (var3 == 20) {
            return 13392;
         }

         if (var3 == 62) {
            return 13398;
         }
      }

      return 0;
   }

   public S_AttackPacket(L1Character var1, L1Character var2, int var3, int var4, int var5, int var6, int var7) {
      this.c(163);
      this.c(var3);
      this.a(var1.fr());
      this.a(var2.fr());
      this.b(var5);
      this.c(var1.fb());
      this.a(IdFactory.a().b());
      this.b(var4);
      this.c(var6);
      this.b(var1.fs());
      this.b(var1.ft());
      this.b(var2.fs());
      this.b(var2.ft());
      this.b(0);
      this.c(var7);
      if (var7 == 2) {
         this.a(this.a(var1));
      } else {
         this.a(0);
      }

      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_AttackPacket";
   }
}
