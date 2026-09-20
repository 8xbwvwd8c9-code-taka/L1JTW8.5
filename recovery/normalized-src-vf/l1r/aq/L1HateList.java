package l1r.aq;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class L1HateList {
   private final ConcurrentHashMap<Integer, L1HateList.a> a = new ConcurrentHashMap<>();

   public synchronized void a(L1Character var1, int var2) {
      if (var1 != null) {
         if (this.a.containsKey(var1.fr())) {
            this.a.get(var1.fr()).b += var2;
         } else {
            L1HateList.a var3 = new L1HateList.a();
            var3.a = var1;
            var3.b = var2;
            this.a.put(var1.fr(), var3);
         }
      }
   }

   public synchronized boolean a(L1Character var1) {
      return this.a.containsKey(var1.fr());
   }

   public synchronized void b(L1Character var1) {
      this.a.remove(var1.fr());
   }

   public synchronized void a() {
      this.a.clear();
   }

   public synchronized boolean b() {
      return this.a.isEmpty();
   }

   public synchronized L1Character c() {
      L1Character var1 = null;
      int var2 = Integer.MIN_VALUE;

      for (L1HateList.a var3 : this.a.values()) {
         if (var3.b > var2) {
            var1 = var3.a;
            var2 = var3.b;
         }
      }

      return var1;
   }

   public synchronized CopyOnWriteArrayList<L1HateList.a> d() {
      return new CopyOnWriteArrayList<>(this.a.values());
   }

   public class a {
      public L1Character a;
      public int b = 0;
   }
}
