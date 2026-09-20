package l1r.au;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ai.IdFactory;
import l1r.ao.FurnitureSpawnTable;
import l1r.ao.ItemTable;
import l1r.ao.LetterTable;
import l1r.ao.PetTable;
import l1r.ap.L1FurnitureInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.as.L1BugBearRace;
import l1r.bh.L1Item;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1Inventory extends L1Object {
   protected CopyOnWriteArrayList<L1ItemInstance> a = new CopyOnWriteArrayList<>();
   public static final int b = 2000000000;
   private static final int g = 1500000000;
   private static final int h = 1500;
   public static final int c = 0;
   public static final int d = 1;
   public static final int e = 2;
   public static final int f = 3;

   public int c() {
      return this.a.size();
   }

   public List<L1ItemInstance> d() {
      return this.a;
   }

   public int e() {
      int var1 = 0;

      for (L1ItemInstance var2 : this.a) {
         var1 += var2.p();
      }

      return var1;
   }

   public int a(L1ItemInstance var1, int var2) {
      if (var1 == null) {
         return -1;
      }

      if (var1.E() > 0 && var2 > 0) {
         if (this.c() <= Config.ap && (this.c() != Config.ap || var1.d() && this.f(var1.N()))) {
            int var3 = this.e() + var1.a().l() * var2 / 1000 + 1;
            if (var3 < 0 || var1.a().l() * var2 / 1000 < 0) {
               return 2;
            }

            if (var3 > 1500.0 * Config.K) {
               return 2;
            }

            L1ItemInstance var4 = this.b(var1.N());
            return var4 != null && var4.E() + var2 > 2000000000 ? 3 : 0;
         } else {
            return 1;
         }
      } else {
         return -1;
      }
   }

   public int a(L1PcInstance var1, L1ItemInstance var2, int var3) {
      if (var2 == null) {
         return -1;
      }

      if (var2.E() > 0 && var3 > 0) {
         int var4 = 100;
         if (this instanceof L1ClanInventory) {
            var4 = Config.ar;
         } else if (this instanceof L1CharInventory) {
            var4 = var1.cJ();
         } else {
            var4 = Config.aq;
         }

         return this.c() <= var4 && (this.c() != var4 || var2.d() && this.f(var2.N())) ? 0 : 1;
      } else {
         return -1;
      }
   }

   public synchronized L1ItemInstance a(int var1, int var2) {
      if (var2 <= 0) {
         return null;
      }

      L1Item var3 = ItemTable.a().a(var1);
      if (var3 == null) {
         return null;
      }

      if (var1 == 40312) {
         L1ItemInstance var8 = new L1ItemInstance(var3, var2);
         if (this.c(var1) == null) {
            var8.cF(IdFactory.a().d());
            L1World.a().a(var8);
         }

         return this.d(var8);
      } else if (var3.aF()) {
         L1ItemInstance var7 = new L1ItemInstance(var3, var2);
         if (this.b(var1) == null) {
            var7.cF(IdFactory.a().d());
            L1World.a().a(var7);
         }

         return this.d(var7);
      } else {
         L1ItemInstance var4 = null;

         for (int var5 = 0; var5 < var2; var5++) {
            L1ItemInstance var6 = new L1ItemInstance(var3, 1);
            var6.cF(IdFactory.a().d());
            L1World.a().a(var6);
            this.d(var6);
            var4 = var6;
         }

         return var4;
      }
   }

   public synchronized L1ItemInstance d(L1ItemInstance var1) {
      if (var1.E() <= 0) {
         return null;
      }

      int var2 = var1.N();
      if (var1.d()) {
         L1ItemInstance var3 = this.d(var2, var1.F());
         if (var3 != null && var3.F() == var1.F()) {
            var3.e(var3.E() + var1.E());
            this.b(var3);
            return var3;
         }
      }

      var1.cG(this.fs());
      var1.cH(this.ft());
      var1.cE(this.fp());
      int var4 = var1.a().aM();
      if (var2 == 41401) {
         var4 -= Random.a(5);
      } else if (var2 == 20383) {
         var4 = 50;
      }

      var1.g(var4);
      if (var1.f() && var1.a().aP() == 2) {
         var1.j(var1.a().d());
      } else if (var1.N() != 40312) {
         var1.j(var1.a().T());
      }

      var1.n();
      this.a.add(var1);
      this.a(var1);
      return var1;
   }

   public synchronized L1ItemInstance e(L1ItemInstance var1) {
      if (var1.N() == 40312) {
         L1ItemInstance var2 = this.c(var1.M());
         if (var2 != null) {
            var2.e(var2.E() + var1.E());
            this.b(var2);
            return var2;
         }
      }

      if (var1.d()) {
         L1ItemInstance var6 = this.e(var1.N(), var1.F());
         if (var6 != null && var6.F() == var1.F()) {
            int var3 = Math.max(1500000000 - var6.E(), 0);
            if (var1.E() <= var3) {
               var6.e(var6.E() + var1.E());
               this.b(var6);
               return var6;
            }

            var6.e(1500000000);
            this.b(var6);
            var1.e(var1.E() - var3);
         }
      }

      if (var1.bb() != null) {
         Timestamp var7 = new Timestamp(System.currentTimeMillis());
         if (var1.bb().before(var7)) {
            if (var1.N() >= 21246 && var1.N() <= 21251) {
               int var9 = var1.G();
               int var10 = var1.F();
               boolean var11 = var1.C();
               var1 = ItemTable.a().b(20085);
               var1.a(var9);
               var1.a(var11);
               var1.f(var10);
            } else if (var1.N() >= 21252 && var1.N() <= 21257) {
               int var8 = var1.G();
               int var4 = var1.F();
               boolean var5 = var1.C();
               var1 = ItemTable.a().b(20084);
               var1.a(var8);
               var1.a(var5);
               var1.f(var4);
            } else {
               if (var1.N() < 21261 || var1.N() > 21300) {
                  this.c(var1);
                  return null;
               }

               var1.b((Timestamp)null);
            }
         }
      }

      var1.cG(this.fs());
      var1.cH(this.ft());
      var1.cE(this.fp());
      this.a.add(var1);
      this.a(var1);
      return var1;
   }

   public boolean b(int var1, int var2) {
      if (var2 <= 0) {
         return false;
      }

      if (ItemTable.a().a(var1).aF()) {
         L1ItemInstance var3 = this.b(var1);
         if (var3 != null && var3.E() >= var2) {
            this.b(var3, var2);
            return true;
         }
      } else {
         L1ItemInstance[] var6 = this.d(var1);
         if (var6.length == var2) {
            for (int var7 = 0; var7 < var2; var7++) {
               this.b(var6[var7], 1);
            }

            return true;
         }

         if (var6.length > var2) {
            L1Inventory.a var4 = new L1Inventory.a<>(null);
            Arrays.sort(var6, var4);

            for (int var5 = 0; var5 < var2; var5++) {
               this.b(var6[var5], 1);
            }

            return true;
         }
      }

      return false;
   }

   public void a(int var1) {
      for (L1ItemInstance var2 : this.a) {
         if (var2.N() == var1) {
            this.f(var2);
         }
      }
   }

   public int c(int var1, int var2) {
      L1ItemInstance var3 = this.e(var1);
      return this.b(var3, var2);
   }

   public int f(L1ItemInstance var1) {
      return this.b(var1, var1.E());
   }

   public int b(L1ItemInstance var1, int var2) {
      if (var1 == null) {
         return 0;
      }

      if (var1.E() > 0 && var2 > 0) {
         if (var1.E() < var2) {
            var2 = var1.E();
         }

         if (var1.E() == var2) {
            int var3 = var1.N();
            if (var3 == 40314 || var3 == 40316) {
               PetTable.a().a(var1.fr());
            } else if (var3 >= 49016 && var3 <= 49025) {
               LetterTable.a().a(var1.fr());
            } else if (var3 >= 41383 && var3 <= 41400) {
               for (L1Object var4 : L1World.a().b()) {
                  if (var4 instanceof L1FurnitureInstance) {
                     L1FurnitureInstance var6 = (L1FurnitureInstance)var4;
                     if (var6.f() == var1.fr()) {
                        FurnitureSpawnTable.a().b(var6);
                     }
                  }
               }
            } else if (var1.N() == 40309) {
               L1BugBearRace.a().b(var1.fr());
            }

            if (this instanceof L1PcInventory && ((L1PcInventory)this).k() == var1.fr()) {
               ((L1PcInventory)this).k(null);
            }

            this.c(var1);
            L1World.a().b(var1);
         } else {
            var1.e(var1.E() - var2);
            this.b(var1);
         }

         return var2;
      } else {
         return 0;
      }
   }

   public void c(L1ItemInstance var1) {
      this.a.remove(var1);
   }

   public synchronized L1ItemInstance a(int var1, int var2, L1Inventory var3) {
      L1ItemInstance var4 = this.e(var1);
      return this.a(var4, var2, var3);
   }

   public synchronized L1ItemInstance a(L1ItemInstance var1, int var2, L1Inventory var3) {
      if (var1 == null) {
         return null;
      }

      if (var1.E() <= 0 || var2 <= 0) {
         return null;
      }

      if (var1.D()) {
         return null;
      }

      if (!this.a(var1.N(), var2, var1.F())) {
         return null;
      }

      L1ItemInstance var4;
      if (var1.E() <= var2) {
         this.c(var1);
         var4 = var1;
         if (this instanceof L1PcInventory && ((L1PcInventory)this).k() == var1.fr()) {
            ((L1PcInventory)this).k(null);
         }
      } else {
         var1.e(var1.E() - var2);
         this.b(var1);
         var4 = ItemTable.a().b(var1.N());
         var4.e(var2);
         var4.a(var1.G());
         var4.a(var1.C());
         var4.b(var1.H());
         var4.g(var1.I());
         var4.j(var1.M());
         var4.a(var1.J());
         var4.f(var1.F());
      }

      return var3.e(var4);
   }

   public L1ItemInstance g(L1ItemInstance var1) {
      return this.c(var1, 1);
   }

   public L1ItemInstance c(L1ItemInstance var1, int var2) {
      int var3 = var1.H();
      if ((var3 != 0 || !var1.f()) && var3 >= 0) {
         int var4 = var1.G() + 5;
         int var5 = var3 + var2;
         if (var5 > var4) {
            var5 = var4;
         }

         if (var3 < var5) {
            var1.b(var5);
         }

         this.b(var1);
         return var1;
      } else {
         var1.b(0);
         return null;
      }
   }

   public L1ItemInstance h(L1ItemInstance var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = var1.H();
         if ((var2 != 0 || var1.f()) && var2 >= 0) {
            var1.b(var2 - 1);
            this.b(var1);
            return var1;
         } else {
            var1.b(0);
            return null;
         }
      }
   }

   public L1ItemInstance b(int var1) {
      for (L1ItemInstance var2 : this.a) {
         if (var2.N() == var1) {
            return var2;
         }
      }

      return null;
   }

   public L1ItemInstance d(int var1, int var2) {
      for (L1ItemInstance var3 : this.a) {
         if (var3.N() == var1 && (var2 == 3 || var3.F() == var2)) {
            return var3;
         }
      }

      return null;
   }

   public L1ItemInstance e(int var1, int var2) {
      for (L1ItemInstance var3 : this.a) {
         if (var3.E() < 1500000000 && var3.N() == var1 && var3.F() == var2) {
            return var3;
         }
      }

      return null;
   }

   public L1ItemInstance c(int var1) {
      for (L1ItemInstance var2 : this.a) {
         if (var2.M() == var1) {
            return var2;
         }
      }

      return null;
   }

   public L1ItemInstance[] d(int var1) {
      return this.f(var1, 3);
   }

   public L1ItemInstance[] f(int var1, int var2) {
      ArrayList var3 = new ArrayList<>();

      for (L1ItemInstance var4 : this.a) {
         if (var4.N() == var1 && (var2 == 3 || var4.F() == var2)) {
            var3.add(var4);
         }
      }

      return var3.toArray(new L1ItemInstance[var3.size()]);
   }

   private L1ItemInstance[] h(int var1) {
      ArrayList var2 = new ArrayList<>();

      for (L1ItemInstance var3 : this.a) {
         if (var3.N() == var1 && !var3.D()) {
            var2.add(var3);
         }
      }

      return var2.toArray(new L1ItemInstance[var2.size()]);
   }

   public L1ItemInstance e(int var1) {
      if (var1 == 0) {
         return null;
      }

      for (Object var2 : this.a) {
         L1ItemInstance var4 = (L1ItemInstance)var2;
         if (var4.fr() == var1) {
            return var4;
         }
      }

      return null;
   }

   public boolean f(int var1) {
      return this.a(var1, 1, 3);
   }

   public boolean g(int var1, int var2) {
      return this.a(var1, var2, 3);
   }

   public boolean a(int var1, int var2, int var3) {
      if (var2 == 0) {
         return true;
      }

      if (ItemTable.a().a(var1).aF()) {
         L1ItemInstance var4 = this.d(var1, var3);
         if (var4 != null && var4.E() >= var2) {
            return true;
         }
      } else {
         Object[] var5 = this.f(var1, var3);
         if (var5.length >= var2) {
            return true;
         }
      }

      return false;
   }

   public boolean h(int var1, int var2) {
      return var2 == 0 ? true : var2 <= this.g(var1);
   }

   public boolean a(int var1, int var2, int var3, int var4) {
      int var5 = 0;

      for (L1ItemInstance var6 : this.a) {
         if (!var6.D() && (var4 >= 3 || var6.F() == var4) && var6.N() == var1 && var6.G() == var2) {
            if (var6.d()) {
               if (var6.E() >= var3) {
                  return true;
               }

               return false;
            }

            if (++var5 == var3) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean b(int var1, int var2, int var3, int var4) {
      int var5 = 0;

      for (L1ItemInstance var6 : this.a) {
         if (!var6.D() && (var4 >= 3 || var6.F() == var4) && var6.N() == var1 && var6.G() == var2) {
            if (var6.d()) {
               var5 += this.b(var6, var3);
            } else {
               var5 += this.f(var6);
            }

            if (var5 == var3) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean a(int[] var1) {
      int var2 = var1.length;
      int[] var3 = new int[var2];

      for (int var4 = 0; var4 < var2; var4++) {
         var3[var4] = 1;
      }

      return this.a(var1, var3);
   }

   public boolean a(int[] var1, int[] var2) {
      for (int var3 = 0; var3 < var1.length; var3++) {
         if (!this.a(var1[var3], var2[var3], 3)) {
            return false;
         }
      }

      return true;
   }

   public int g(int var1) {
      if (ItemTable.a().a(var1).aF()) {
         L1ItemInstance var2 = this.b(var1);
         return var2 != null ? var2.E() : 0;
      } else {
         return this.h(var1).length;
      }
   }

   public void f() {
      Collections.shuffle(this.a);
   }

   public void g() {
      for (Object var1 : this.a) {
         L1ItemInstance var3 = (L1ItemInstance)var1;
         L1World.a().b(var3);
      }

      this.a.clear();
   }

   public L1ItemInstance a(String var1) {
      for (L1ItemInstance var2 : this.a) {
         if (var1.equals(var2.a().j())) {
            return var2;
         }
      }

      return null;
   }

   public void a() {
   }

   public void a(L1ItemInstance var1) {
   }

   public void b(L1ItemInstance var1) {
   }

   private class a<T> implements Comparator<L1ItemInstance> {
      private a() {
      }

      public int a(L1ItemInstance var1, L1ItemInstance var2) {
         return var1.G() - var2.G();
      }

      // $VF: synthetic method
      @Override
      public int compare(Object var1, Object var2) {
         return this.a((L1ItemInstance)var1, (L1ItemInstance)var2);
      }

      // $VF: synthetic method
      a(L1Inventory.a var2) {
         this();
      }
   }
}
