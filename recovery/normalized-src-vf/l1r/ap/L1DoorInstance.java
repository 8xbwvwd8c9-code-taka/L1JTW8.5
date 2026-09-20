package l1r.ap;

import l1r.am.ListSprReader__obf_c;
import l1r.aq.L1Character;
import l1r.aq.L1Location;
import l1r.aq.L1World;
import l1r.be.S_Attribute;
import l1r.be.S_DoActionGFX;
import l1r.be.S_DoorPack;
import l1r.be.S_RemoveObject;
import l1r.bh.L1DoorGfx;
import l1r.bh.L1Npc;

public class L1DoorInstance extends L1NpcInstance {
   private final L1DoorGfx y;
   private int z = 0;
   private int A = 0;
   private int B = 0;
   private int C = 0;
   private int D = 29;
   private int E = 0;
   private final int F;
   private final int G;

   public L1DoorInstance(L1Npc var1, int var2, L1DoorGfx var3, L1Location var4, int var5, int var6, boolean var7) {
      super(var1);
      this.f_(var2);
      this.bG(var5);
      this.a(var5);
      this.a(var4);
      this.q(var4.f());
      this.r(var4.g());
      this.c(var3.b());
      this.F = var3.e();
      this.G = var3.f();
      int var8 = var3.b() == 0 ? var4.f() : var4.g();
      this.d(var8 + var3.d());
      this.e(var8 + var3.c());
      this.f(var6);
      if (var7) {
         this.f();
      }

      this.y = var3;
   }

   public int ab_() {
      return Math.abs(this.y.d()) + Math.abs(this.y.c());
   }

   @Override
   public void a(L1PcInstance var1, int var2) {
      if (this.ew() != 0) {
         if (this.ea() > 0 && !this.eX()) {
            super.a(var1, var2);
         }
      }
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_DoorPack(this));
      this.d(var1);
   }

   @Override
   public void aa_() {
      this.X(true);
      this.d(null);
      this.k(true);
      if (this.y() != null) {
         this.y().g();
      }

      this.t();
      this.k = null;
      L1World.a().d(this);
      L1World.a().b(this);

      for (L1PcInstance var1 : L1World.a().f(this)) {
         var1.d(this);
         var1.a(new S_RemoveObject(this));
      }

      this.es();
   }

   @Override
   public void b(L1Character var1, int var2) {
      if (this.ew() != 0) {
         if (this.ea() > 0 && !this.eX()) {
            int var3 = this.ea() - var2;
            if (var3 <= 0 && !this.eX()) {
               this.av();
            } else {
               this.bx(var3);
               this.au();
            }
         }
      }
   }

   private void au() {
      int var1 = 0;
      if (this.ew() * 1 / 6 > this.ea()) {
         var1 = 36;
      } else if (this.ew() * 2 / 6 > this.ea()) {
         var1 = 35;
      } else if (this.ew() * 3 / 6 > this.ea()) {
         var1 = 34;
      } else if (this.ew() * 4 / 6 > this.ea()) {
         var1 = 33;
      } else if (this.ew() * 5 / 6 > this.ea()) {
         var1 = 32;
      }

      if (this.eY() != var1) {
         this.cq(var1);
         this.b(new S_DoActionGFX(this.fr(), var1));
      }
   }

   @Override
   public void a(int var1) {
      int var2 = var1;
      if (var2 >= this.ew()) {
         var2 = this.ew();
      }

      this.bx(var2);
   }

   private void av() {
      this.bx(0);
      this.X(true);
      int var1 = 36;
      if (ListSprReader__obf_c.a().b(this.fe(), 37)) {
         var1 = 37;
      }

      this.cq(var1);
      this.fq().a(this.fu(), true);
      this.b(new S_DoActionGFX(this.fr(), 37));
      this.d(null);
   }

   private void d(L1PcInstance var1) {
      int var2 = this.k();
      int var3 = this.l();
      int var4 = this.ac_();
      int var5 = this.n();
      int var6 = var5 - var4;
      if (var6 == 0) {
         this.a(var1, var2, var3);
      } else if (this.j() == 0) {
         for (int var7 = var4; var7 <= var5; var7++) {
            this.a(var1, var7, var3);
         }
      } else {
         for (int var8 = var4; var8 <= var5; var8++) {
            this.a(var1, var2, var8);
         }
      }

      if (this.j() == 0) {
         for (int var9 = var4 - 1; var9 <= var5 + 1; var9++) {
            this.fq().a(var9, this.ft(), this.aw());
         }
      } else {
         for (int var10 = var4 - 1; var10 <= var5 + 1; var10++) {
            this.fq().a(this.fs(), var10, this.aw());
         }
      }
   }

   private boolean aw() {
      return this.eX() || this.o() == 28;
   }

   private void a(L1PcInstance var1, int var2, int var3) {
      this.fq().a(var2, var3, this.aw(), this.j());
      if (var1 != null) {
         var1.a(new S_Attribute(var2, var3, this.j(), this.aw()));
      } else {
         this.b(new S_Attribute(var2, var3, this.j(), this.aw()));
      }
   }

   public void f() {
      if (!this.eX() && !this.aw()) {
         this.B(28);
         this.b(new S_DoActionGFX(this.fr(), 28));
         this.d(null);
      }
   }

   public void g() {
      if (!this.eX() && this.aw()) {
         this.B(29);
         this.b(new S_DoActionGFX(this.fr(), 29));
         this.d(null);
      }
   }

   public void h() {
      if (this.ew() > 1) {
         this.X(false);
         this.a(this.ew());
         this.cq(0);
         this.B(28);
         this.g();
      }
   }

   public int i() {
      return this.z;
   }

   public void f_(int var1) {
      this.z = var1;
   }

   public int j() {
      return this.A;
   }

   @Override
   public void c(int var1) {
      if (var1 != 0 && var1 != 1) {
         throw new IllegalArgumentException();
      }

      this.A = var1;
   }

   public int k() {
      return this.fs() + this.F;
   }

   public int l() {
      return this.ft() + this.G;
   }

   public int ac_() {
      return this.B;
   }

   @Override
   public void d(int var1) {
      this.B = var1;
   }

   public int n() {
      return this.C;
   }

   public void e(int var1) {
      this.C = var1;
   }

   public int o() {
      return this.D;
   }

   private void B(int var1) {
      if (var1 != 28 && var1 != 29) {
         throw new IllegalArgumentException();
      }

      this.D = var1;
   }

   public int p() {
      return this.E;
   }

   public void f(int var1) {
      this.E = var1;
   }
}
