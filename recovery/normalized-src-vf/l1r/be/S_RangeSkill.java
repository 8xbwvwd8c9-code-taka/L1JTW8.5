package l1r.be;

import java.util.ArrayList;
import l1r.ai.IdFactory;
import l1r.aq.L1Character;

public class S_RangeSkill extends ServerBasePacket {
   public static final int a = 0;
   public static final int b = 8;

   public S_RangeSkill(L1Character var1, ArrayList<L1Character> var2, int var3, int var4, int var5) {
      this.c(150);
      this.c(var4);
      this.a(var1.fr());
      this.b(var1.fs());
      this.b(var1.ft());
      if (var5 == 0) {
         this.c(var1.fb());
      } else if (var5 == 8) {
         L1Character var6 = var2.get(0);
         int var7 = a(var1.fs(), var1.ft(), var6.fs(), var6.ft());
         var1.ct(var7);
         this.c(var1.fb());
      }

      this.a(IdFactory.a().b());
      this.b(var3);
      this.c(var5);
      this.b(0);
      this.b(var2.size());

      for (L1Character var8 : var2) {
         this.a(var8.fr());
         this.b(var8.fo());
      }

      this.c(0);
      this.b(0);
   }

   private static int a(int var0, int var1, int var2, int var3) {
      int var4 = 0;
      if (var2 > var0 && var3 > var1) {
         var4 = 3;
      }

      if (var2 < var0 && var3 < var1) {
         var4 = 7;
      }

      if (var2 > var0 && var3 == var1) {
         var4 = 2;
      }

      if (var2 < var0 && var3 == var1) {
         var4 = 6;
      }

      if (var2 == var0 && var3 < var1) {
         var4 = 0;
      }

      if (var2 == var0 && var3 > var1) {
         var4 = 4;
      }

      if (var2 < var0 && var3 > var1) {
         var4 = 5;
      }

      if (var2 > var0 && var3 < var1) {
         var4 = 1;
      }

      return var4;
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_RangeSkill";
   }
}
