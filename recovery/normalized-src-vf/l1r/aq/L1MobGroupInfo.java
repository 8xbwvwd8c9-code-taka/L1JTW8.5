package l1r.aq;

import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ap.L1NpcInstance;

public class L1MobGroupInfo {
   private final CopyOnWriteArrayList<L1NpcInstance> a = new CopyOnWriteArrayList<>();
   private L1NpcInstance b;
   private L1Spawn c;
   private boolean d;

   public void a(L1NpcInstance var1) {
      this.b = var1;
   }

   public L1NpcInstance a() {
      return this.b;
   }

   public boolean b(L1NpcInstance var1) {
      return var1.fr() == this.b.fr();
   }

   public void a(L1Spawn var1) {
      this.c = var1;
   }

   public L1Spawn b() {
      return this.c;
   }

   public void c(L1NpcInstance var1) {
      if (var1 == null) {
         throw new NullPointerException();
      }

      if (this.a.isEmpty()) {
         this.a(var1);
         if (var1.Z()) {
            this.a(var1.R());
         }
      }

      if (!this.a.contains(var1)) {
         this.a.add(var1);
      }

      var1.a(this);
      var1.w(this.b.fr());
   }

   public synchronized int d(L1NpcInstance var1) {
      if (var1 == null) {
         throw new NullPointerException();
      }

      if (this.a.contains(var1)) {
         this.a.remove(var1);
      }

      var1.a((L1MobGroupInfo)null);
      if (this.b(var1)) {
         if (this.d() && !this.a.isEmpty()) {
            for (L1NpcInstance var2 : this.a) {
               var2.a((L1MobGroupInfo)null);
               var2.a((L1Spawn)null);
               var2.g(false);
            }

            return 0;
         }

         if (!this.a.isEmpty()) {
            this.a(this.a.get(0));
         }
      }

      return this.a.size();
   }

   public int c() {
      return this.a.size();
   }

   public boolean d() {
      return this.d;
   }

   public void a(boolean var1) {
      this.d = var1;
   }
}
