package l1r.be;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import l1r.ao.ShopWorldTable;
import l1r.ap.L1ItemInstance;

public class S_CharEvent extends ServerBasePacket {
   public static final int a = 1;
   public static final int b = 2;
   public static final int c = 3;
   public static final int d = 4;
   public static final int e = 10;
   public static final int f = 12;
   public static final int g = 36;
   public static final int h = 37;
   public static final int i = 42;
   public static final int j = 48;
   public static final int k = 49;
   public static final int l = 60;
   public static final int m = 64;
   public static final int n = 65;
   public static final int o = 66;
   public static final int p = 67;
   public static final int q = 68;
   public static final int r = 72;
   public static final int s = 73;

   public S_CharEvent(int var1) {
      this.c(42);
      this.c(var1);
      if (var1 == 10) {
         this.a(2);
      }
   }

   public S_CharEvent(int var1, int var2) {
      this.c(42);
      this.c(var1);
      switch (var1) {
         case 37:
            this.a(var2);
            this.a(0);
            break;
         case 48:
         case 49:
            this.a(var2);
            break;
         case 72:
            this.a(var2);
            this.a((int)(new Date().getTime() / 1000L));
            this.a((int)(new Date().getTime() / 1000L) + 31536000);
            this.b(1);
      }
   }

   public S_CharEvent(int var1, int var2, int var3) {
      this.c(42);
      this.c(var1);
      this.a(var2);
      this.a(var3);
      this.b(0);
   }

   public S_CharEvent(HashMap<Integer, ShopWorldTable.L1R_b> var1) {
      this.c(42);
      this.c(36);
      this.c(1);
      this.c(1);
      this.c(1);
      this.b(var1.size());
      ArrayList var2 = new ArrayList<>(var1.values());
      Collections.sort(var2, new Comparator<ShopWorldTable.L1R_b>() {
         public int a(ShopWorldTable.L1R_b var1, ShopWorldTable.L1R_b var2) {
            return var2.f && var1.f ? var2.a.N() - var1.a.N() : (var2.f ? 1 : 0) - (var1.f ? 1 : 0);
         }

         @Override
         public int compare(ShopWorldTable.L1R_b var1, ShopWorldTable.L1R_b var2) {
            return this.a(var1, var2);
         }
      });

      for (ShopWorldTable.L1R_b var3 : var2) {
         L1ItemInstance var5 = var3.a;
         this.a(var5.N());
         this.b(var5.e());
         this.b(var5.m());
         this.c(0);
         this.a(var3.b);
         this.a(var5.b());
         this.c(23);
         this.c(var5.a().k());
         this.c(0);
         this.c(var3.c);
         this.c(var3.f ? 1 : 0);
         byte[] var6 = new byte[20];
         this.a(var6);
         this.c(var3.d);
         this.c(var3.e ? 1 : 0);
      }

      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CharEvent";
   }
}
