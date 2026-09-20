package l1r.aq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.au.L1GroundInventory;
import l1r.ax.L1Map;
import l1r.be.S_SystemMessage;
import l1r.be.ServerBasePacket;
import l1r.bi.Point;

public class L1World {
   private final ConcurrentHashMap<String, L1PcInstance> c;
   private final ConcurrentHashMap<Integer, L1PetInstance> d;
   private final ConcurrentHashMap<Integer, L1SummonInstance> e;
   private final ConcurrentHashMap<Integer, L1EffectInstance> f;
   private final ConcurrentHashMap<Integer, L1Object> g;
   private final ConcurrentHashMap<Integer, L1Object>[] h;
   private final CopyOnWriteArrayList<L1War> i;
   private int j = 4;
   private boolean k = true;
   private boolean l = false;
   private static final int m = 25599;
   public static final int a = 40308;
   private static L1World n;
   private Collection<L1Object> o;
   private Collection<L1PcInstance> p;
   private Collection<L1PetInstance> q;
   private Collection<L1SummonInstance> r;
   private List<L1War> s;
   public int[] b = new int[4];

   private L1World() {
      this.c = new ConcurrentHashMap<>();
      this.d = new ConcurrentHashMap<>();
      this.e = new ConcurrentHashMap<>();
      this.f = new ConcurrentHashMap<>();
      this.g = new ConcurrentHashMap<>();
      this.h = new ConcurrentHashMap[25600];
      this.i = new CopyOnWriteArrayList<>();

      for (int var1 = 0; var1 <= 25599; var1++) {
         this.h[var1] = new ConcurrentHashMap<>();
      }
   }

   public static L1World a() {
      if (n == null) {
         n = new L1World();
      }

      return n;
   }

   public void a(L1Object var1) {
      if (var1 == null) {
         throw new NullPointerException();
      }

      this.g.put(var1.fr(), var1);
      if (var1 instanceof L1PcInstance) {
         this.c.put(((L1PcInstance)var1).et(), (L1PcInstance)var1);
      }

      if (var1 instanceof L1PetInstance) {
         this.d.put(var1.fr(), (L1PetInstance)var1);
      }

      if (var1 instanceof L1SummonInstance) {
         this.e.put(var1.fr(), (L1SummonInstance)var1);
      }

      if (var1 instanceof L1EffectInstance) {
         this.f.put(var1.fr(), (L1EffectInstance)var1);
      }
   }

   public void b(L1Object var1) {
      if (var1 == null) {
         throw new NullPointerException();
      }

      this.g.remove(var1.fr());
      if (var1 instanceof L1PcInstance) {
         this.c.remove(((L1PcInstance)var1).et());
      }

      if (var1 instanceof L1PetInstance) {
         this.d.remove(var1.fr());
      }

      if (var1 instanceof L1SummonInstance) {
         this.e.remove(var1.fr());
      }

      if (var1 instanceof L1EffectInstance) {
         this.f.remove(var1.fr());
      }
   }

   public L1Object a(int var1) {
      return this.g.get(var1);
   }

   public Collection<L1Object> b() {
      Collection var1 = this.o;
      return var1 != null ? var1 : (this.o = Collections.unmodifiableCollection(this.g.values()));
   }

   public L1GroundInventory a(int var1, int var2, int var3) {
      int var4 = ((var1 - 30000) * 10000 + (var2 - 30000)) * -1;
      Object var5 = this.h[var3].get(var4);
      return var5 == null ? new L1GroundInventory(var4, var1, var2, var3) : (L1GroundInventory)var5;
   }

   public L1GroundInventory a(L1Location var1) {
      return this.a(var1.f(), var1.g(), var1.a().b());
   }

   public void c(L1Object var1) {
      if (var1.fp() <= 25599) {
         this.h[var1.fp()].put(var1.fr(), var1);
      }
   }

   public void d(L1Object var1) {
      if (var1.fp() <= 25599) {
         this.h[var1.fp()].remove(var1.fr());
      }
   }

   public void a(L1Object var1, int var2) {
      if (var1.fp() != var2) {
         if (var1.fp() <= 25599) {
            this.h[var1.fp()].remove(var1.fr());
         }

         if (var2 <= 25599) {
            this.h[var2].put(var1.fr(), var1);
         }
      }
   }

   private Map<Integer, Integer> a(Point var1, Point var2) {
      HashMap var3 = new HashMap<>();
      int var9 = var1.f();
      int var10 = var1.g();
      int var11 = var2.f();
      int var12 = var2.g();
      int var13 = var11 > var9 ? 1 : -1;
      int var14 = var11 > var9 ? var11 - var9 : var9 - var11;
      int var15 = var12 > var10 ? 1 : -1;
      int var16 = var12 > var10 ? var12 - var10 : var10 - var12;
      int var5 = var9;
      int var6 = var10;
      if (var14 >= var16) {
         int var4 = -var14;

         for (int var8 = 0; var8 <= var14; var8++) {
            int var7 = (var5 << 16) + var6;
            var3.put(var7, var7);
            var5 += var13;
            var4 += 2 * var16;
            if (var4 >= 0) {
               var6 += var15;
               var4 -= 2 * var14;
            }
         }
      } else {
         int var17 = -var16;

         for (int var19 = 0; var19 <= var16; var19++) {
            int var18 = (var5 << 16) + var6;
            var3.put(var18, var18);
            var6 += var15;
            var17 += 2 * var14;
            if (var17 >= 0) {
               var5 += var13;
               var17 -= 2 * var16;
            }
         }
      }

      return var3;
   }

   public ArrayList<L1Object> a(L1Object var1, L1Object var2) {
      Map var3 = this.a(var1.fu(), var2.fu());
      int var4 = var2.fp();
      ArrayList var5 = new ArrayList<>();
      if (var4 <= 25599) {
         for (L1Object var6 : this.h[var4].values()) {
            if (var6.fr() != var1.fr()) {
               int var8 = (var6.fs() << 16) + var6.ft();
               if (var3.containsKey(var8)) {
                  var5.add(var6);
               }
            }
         }
      }

      return var5;
   }

   public ArrayList<L1Object> a(L1Location var1, int var2) {
      ArrayList var3 = new ArrayList<>();
      if (var1.b() <= 25599) {
         for (L1Object var4 : this.h[var1.b()].values()) {
            if (var4.fp() == var1.b()) {
               if (var2 == -1) {
                  if (var1.e(var4.fu())) {
                     var3.add(var4);
                  }
               } else if (var2 == 0) {
                  if (var1.f(var4.fu())) {
                     var3.add(var4);
                  }
               } else if (var1.c(var4.fu()) <= var2) {
                  var3.add(var4);
               }
            }
         }
      }

      return var3;
   }

   public List<L1Object> e(L1Object var1) {
      return this.b(var1, -1);
   }

   public ArrayList<L1Object> b(L1Object var1, int var2) {
      L1Map var3 = var1.fq();
      Point var4 = var1.fu();
      ArrayList var5 = new ArrayList<>();
      if (var3.b() <= 25599) {
         for (L1Object var6 : this.h[var3.b()].values()) {
            if (var6.fr() != var1.fr() && var3 == var6.fq()) {
               if (var2 == -1) {
                  if (var4.e(var6.fu())) {
                     var5.add(var6);
                  }
               } else if (var2 == 0) {
                  if (var4.f(var6.fu())) {
                     var5.add(var6);
                  }
               } else if (var4.c(var6.fu()) <= var2) {
                  var5.add(var6);
               }
            }
         }
      }

      return var5;
   }

   public List<L1PcInstance> f(L1Object var1) {
      return this.c(var1, -1);
   }

   public List<L1PcInstance> c(L1Object var1, int var2) {
      int var3 = var1.fp();
      Point var4 = var1.fu();
      ArrayList var5 = new ArrayList<>();

      for (L1PcInstance var6 : this.c()) {
         if (var6.fr() != var1.fr() && var3 == var6.fp()) {
            if (var2 == -1) {
               if (var4.e(var6.fu())) {
                  var5.add(var6);
               }
            } else if (var2 == 0) {
               if (var4.f(var6.fu())) {
                  var5.add(var6);
               }
            } else if (var4.c(var6.fu()) <= var2) {
               var5.add(var6);
            }
         }
      }

      return var5;
   }

   public List<L1PcInstance> b(L1Object var1, L1Object var2) {
      int var3 = var1.fp();
      Point var4 = var1.fu();
      Point var5 = var2.fu();
      ArrayList var6 = new ArrayList<>();

      for (L1PcInstance var7 : this.c()) {
         if (var7.fr() != var1.fr() && var3 == var7.fp() && var4.e(var7.fu()) && !var5.e(var7.fu())) {
            var6.add(var7);
         }
      }

      return var6;
   }

   public Collection<L1PcInstance> c() {
      Collection var1 = this.p;
      return var1 != null ? var1 : (this.p = Collections.unmodifiableCollection(this.c.values()));
   }

   public L1PcInstance a(String var1) {
      if (this.c.containsKey(var1)) {
         return this.c.get(var1);
      }

      for (L1PcInstance var2 : this.c()) {
         if (var2.et().equalsIgnoreCase(var1)) {
            return var2;
         }
      }

      return null;
   }

   public Map<Integer, L1EffectInstance> d() {
      return this.f;
   }

   public Collection<L1PetInstance> e() {
      Collection var1 = this.q;
      return var1 != null ? var1 : (this.q = Collections.unmodifiableCollection(this.d.values()));
   }

   public Collection<L1SummonInstance> f() {
      Collection var1 = this.r;
      return var1 != null ? var1 : (this.r = Collections.unmodifiableCollection(this.e.values()));
   }

   public final Map<Integer, L1Object> g() {
      return this.g;
   }

   public final Map<Integer, L1Object>[] h() {
      return this.h;
   }

   public final Map<Integer, L1Object> b(int var1) {
      return this.h[var1];
   }

   public void a(L1War var1) {
      if (!this.i.contains(var1)) {
         this.i.add(var1);
      }
   }

   public void b(L1War var1) {
      if (this.i.contains(var1)) {
         this.i.remove(var1);
      }
   }

   public List<L1War> i() {
      List var1 = this.s;
      return var1 != null ? var1 : (this.s = Collections.unmodifiableList(this.i));
   }

   public boolean b(String var1) {
      return this.c(var1) != null;
   }

   public L1War c(String var1) {
      for (L1War var2 : this.i()) {
         if (var2.b(var1)) {
            return var2;
         }
      }

      return null;
   }

   public void c(int var1) {
      this.j = var1;
   }

   public int j() {
      return this.j;
   }

   public void a(boolean var1) {
      this.k = var1;
   }

   public boolean k() {
      return this.k;
   }

   public void b(boolean var1) {
      this.l = var1;
   }

   public boolean l() {
      return this.l;
   }

   public void a(ServerBasePacket var1) {
      for (L1PcInstance var2 : this.c()) {
         var2.a(var1);
      }
   }

   public void d(String var1) {
      this.a(new S_SystemMessage(var1));
   }
}
