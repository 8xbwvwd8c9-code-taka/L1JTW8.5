package l1r.ay;

import l1r.ap.L1PcInstance;
import l1r.be.S_Karma;
import l1r.be.S_Lawful;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.bi.CalcStat;
import l1r.l1j.server.Config;

public class L1PcExpMonitor extends L1PcMonitor {
   private int a = 0;
   private int b = 0;
   private int c = 0;
   private int d = 0;
   private int e = 0;
   private int f = 0;
   private int g = 0;
   private int h = 0;
   private int i = 0;
   private int j = 0;
   private int k = 0;
   private int l = 0;
   private int m = 0;
   private int n = 0;

   public L1PcExpMonitor(int var1) {
      super(var1);
   }

   @Override
   public void a(L1PcInstance var1) {
      if (this.a != var1.fa()) {
         this.a = var1.fa();
         S_Lawful var2 = new S_Lawful(var1.fr(), this.a);
         var1.a(var2);
         var1.b(var2);
         if (Config.aN) {
            int var3 = this.a / 10000;
            if (this.n != var3) {
               var1.b(this.n, var3);
               this.n = var3;
            }
         }
      }

      if (this.c != var1.P()) {
         this.c = var1.P();
         var1.a(new S_Karma(var1));
      }

      if (this.b != var1.m()) {
         this.b = var1.m();
         var1.g();
      }

      if (this.d != var1.ez()) {
         this.d = var1.ez();
         int var8 = 2;
         if (this.i == 0) {
            this.i = var1.bf();
         } else if (this.i != var1.bf()) {
            this.i = var1.bf();
            var8 = 16;
         }

         int var13 = CalcStat.a(var1.bf(), var1.ez());
         int var4 = CalcStat.b(var1.bf(), var1.ez());
         int var5 = CalcStat.c(var1.bf(), var1.ez());
         var1.a(new S_ProtoBuffers(var8, "str", var13, var4, var5, (int)var1.K()));
      }

      if (this.e != var1.eB()) {
         this.e = var1.eB();
         int var9 = 2;
         if (this.j == 0) {
            this.j = var1.bh();
         } else if (this.j != var1.bh()) {
            this.j = var1.bh();
            var9 = 16;
         }

         var1.W();
         var1.a(new S_PacketBox(132, var1.u()));
         int var14 = CalcStat.a(var1.eB());
         int var18 = CalcStat.b(var1.eB());
         int var22 = CalcStat.d(var1.bh(), var1.eB());
         int var6 = CalcStat.e(var1.bh(), var1.eB());
         int var7 = CalcStat.f(var1.bh(), var1.eB());
         var1.a(new S_ProtoBuffers(var9, "dex", var22, var6, var7, var14, var18));
      }

      if (this.g != var1.eD()) {
         this.g = var1.eD();
         int var10 = 2;
         if (this.l == 0) {
            this.l = var1.bj();
         } else if (this.l != var1.bj()) {
            this.l = var1.bj();
            var10 = 16;
         }

         int var15 = CalcStat.g(var1.bj(), var1.eD());
         int var19 = CalcStat.h(var1.bj(), var1.eD());
         int var23 = CalcStat.i(var1.bj(), var1.eD());
         int var26 = CalcStat.c(var1.eD());
         int var28 = CalcStat.d(var1.eD());
         var1.a(new S_ProtoBuffers(var10, "int", var15, var19, var23, var26, var28));
      }

      if (this.f != var1.eA()) {
         this.f = var1.eA();
         int var11 = 2;
         if (this.k == 0) {
            this.k = var1.bg();
         } else if (this.k != var1.bg()) {
            this.k = var1.bg();
            var11 = 16;
         }

         int var16 = var1.aC().e() + CalcStat.j(var1.aC().a()[2], var1.eA());
         int var20 = CalcStat.k(var1.bg(), var1.eA());
         int var24 = CalcStat.l(var1.bg(), var1.eA());
         var1.a(new S_ProtoBuffers(var11, "con", var20, var24, (int)var1.K(), var16, var16 + 1));
      }

      if (this.h != var1.eE()) {
         this.h = var1.eE();
         int var12 = 2;
         if (this.m == 0) {
            this.m = var1.bk();
         } else if (this.m != var1.bk()) {
            this.m = var1.bk();
            var12 = 16;
         }

         int var17 = var1.aC().j(var1.eE());
         int var21 = var1.aC().k(var1.eE());
         int var25 = CalcStat.m(var1.bk(), var1.eE());
         int var27 = CalcStat.n(var1.bk(), var1.eE());
         int var29 = CalcStat.e(var1.eE());
         var1.a(new S_ProtoBuffers(var12, "wis", var25, var27, var29, var17, var17 + var21));
      }
   }
}
