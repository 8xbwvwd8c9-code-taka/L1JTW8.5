package l1r.aq;

import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ap.L1PcInstance;
import l1r.be.S_ServerMessage;
import l1r.l1j.server.Config;

public class L1ChatParty {
   private final CopyOnWriteArrayList<L1PcInstance> a = new CopyOnWriteArrayList<>();
   private L1PcInstance b = null;

   public void a(L1PcInstance var1) {
      if (var1 == null) {
         throw new NullPointerException();
      }

      if ((this.a.size() != Config.X || this.b.l()) && !this.a.contains(var1)) {
         if (this.a.isEmpty()) {
            this.f(var1);
         }

         this.a.add(var1);
         var1.a(this);
      }
   }

   private void e(L1PcInstance var1) {
      if (this.a.contains(var1)) {
         this.a.remove(var1);
         var1.a((L1ChatParty)null);
      }
   }

   public boolean a() {
      return this.a.size() < Config.X;
   }

   public int b() {
      return Config.X - this.a.size();
   }

   private void f(L1PcInstance var1) {
      this.b = var1;
   }

   public L1PcInstance c() {
      return this.b;
   }

   public boolean b(L1PcInstance var1) {
      return var1.fr() == this.b.fr();
   }

   public String d() {
      String var1 = new String("");

      for (L1PcInstance var2 : this.a) {
         var1 = var1 + var2.et() + " ";
      }

      return var1;
   }

   private void g() {
      L1PcInstance[] var1 = this.e();
      L1PcInstance[] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         L1PcInstance var2 = var5[var3];
         this.e(var2);
         var2.a(new S_ServerMessage(418));
      }
   }

   public void c(L1PcInstance var1) {
      L1PcInstance[] var2 = this.e();
      if (this.b(var1)) {
         this.g();
      } else if (this.f() == 2) {
         this.e(var1);
         L1PcInstance var3 = this.c();
         this.e(var3);
         this.a(var1, var1);
         this.a(var3, var1);
      } else {
         this.e(var1);
         L1PcInstance[] var6 = var2;
         int var5 = var2.length;

         for (int var4 = 0; var4 < var5; var4++) {
            L1PcInstance var7 = var6[var4];
            this.a(var7, var1);
         }

         this.a(var1, var1);
      }
   }

   public void d(L1PcInstance var1) {
      if (this.f() == 2) {
         this.e(var1);
         L1PcInstance var2 = this.c();
         this.e(var2);
      } else {
         this.e(var1);
      }

      var1.a(new S_ServerMessage(419));
   }

   public L1PcInstance[] e() {
      return this.a.toArray(new L1PcInstance[this.a.size()]);
   }

   public int f() {
      return this.a.size();
   }

   private void a(L1PcInstance var1, L1PcInstance var2) {
      var1.a(new S_ServerMessage(420, var2.et()));
   }
}
