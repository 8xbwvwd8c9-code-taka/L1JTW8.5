package l1r.bh;

import l1r.ao.NpcTable;
import l1r.bi.IntRange;

public class L1PetType {
   private final int a;
   private final L1Npc b;
   private final String c;
   private final int d;
   private final IntRange e;
   private final IntRange f;
   private final int g;
   private final int[] h;
   private final int i;
   private final int j;
   private final boolean k;

   public L1PetType(int var1, String var2, int var3, IntRange var4, IntRange var5, int var6, int var7, int[] var8, int var9, boolean var10) {
      this.a = var1;
      this.b = NpcTable.a().a(var1);
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.j = var6;
      this.g = var7;
      this.h = var8;
      this.i = var9;
      this.k = var10;
   }

   public int a() {
      return this.a;
   }

   public L1Npc b() {
      return this.b;
   }

   public String c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public IntRange e() {
      return this.e;
   }

   public IntRange f() {
      return this.f;
   }

   public int g() {
      return this.g;
   }

   public int a(int var1) {
      return var1 == 0 ? 0 : this.h[var1 - 1];
   }

   public static int b(int var0) {
      if (50 <= var0) {
         return 5;
      } else if (48 <= var0) {
         return 4;
      } else if (36 <= var0) {
         return 3;
      } else if (24 <= var0) {
         return 2;
      } else {
         return 12 <= var0 ? 1 : 0;
      }
   }

   public int h() {
      return this.i;
   }

   public int i() {
      return this.j;
   }

   public boolean j() {
      return this.k;
   }
}
