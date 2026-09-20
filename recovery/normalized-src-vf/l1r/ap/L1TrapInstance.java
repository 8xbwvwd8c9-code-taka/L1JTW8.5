package l1r.ap;

import java.util.concurrent.CopyOnWriteArrayList;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.ax.L1Map;
import l1r.bd.L1Trap__obf_i;
import l1r.be.S_RemoveObject;
import l1r.be.S_TrapPack;
import l1r.bi.Point;
import l1r.bi.Random;

public class L1TrapInstance extends L1Object {
   private final L1Trap__obf_i a;
   private final Point b = new Point();
   private final Point c = new Point();
   private final int d;
   private boolean e = true;
   private final String f;
   private final CopyOnWriteArrayList<L1PcInstance> g = new CopyOnWriteArrayList<>();

   public L1TrapInstance(int var1, L1Trap__obf_i var2, L1Location var3, Point var4, int var5) {
      this.cF(var1);
      this.a = var2;
      this.fu().a(var3);
      this.b.a(var3);
      this.c.a(var4);
      this.d = var5;
      this.f = "trap";
      this.a();
   }

   public L1TrapInstance(int var1, L1Location var2) {
      this.cF(var1);
      this.a = L1Trap__obf_i.c();
      this.fu().a(var2);
      this.d = 0;
      this.f = "trap base";
   }

   public void a() {
      if (this.c.f() != 0 || this.c.g() != 0) {
         for (int var1 = 0; var1 < 50; var1++) {
            int var2 = Random.a(this.c.f() + 1) * (Random.a(2) == 1 ? 1 : -1);
            int var3 = Random.a(this.c.g() + 1) * (Random.a(2) == 1 ? 1 : -1);
            var2 += this.b.f();
            var3 += this.b.g();
            L1Map var4 = this.fu().a();
            if (var4.b(var2, var3) && var4.c(var2, var3)) {
               this.fu().a(var2, var3);
               break;
            }
         }
      }
   }

   public void b() {
      this.e = true;
   }

   public void c() {
      this.e = false;

      for (L1PcInstance var1 : this.g) {
         var1.d(this);
         var1.a(new S_RemoveObject(this));
      }

      this.g.clear();
   }

   public boolean d() {
      return this.e;
   }

   public int e() {
      return this.d;
   }

   public void d(L1PcInstance var1) {
      this.a.a(var1, this);
   }

   public void f() {
      this.a.b(this);
   }

   @Override
   public void b(L1PcInstance var1) {
      if (var1.bB(26002)) {
         var1.c(this);
         var1.a(new S_TrapPack(this, this.f));
         this.g.add(var1);
      } else if (this.a.b() == 12876) {
         var1.c(this);
         var1.a(new S_TrapPack(this, ""));
         this.g.add(var1);
      }
   }

   public boolean g() {
      return this.a.b() == 12876;
   }

   public int h() {
      return this.a.b();
   }
}
