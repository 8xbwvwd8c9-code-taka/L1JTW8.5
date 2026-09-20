package l1r.aj;

import l1r.ao.CharacterTable;
import l1r.ao.ExpTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.be.S_CharReset;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_OwnCharStatus2;
import l1r.bi.CalcInitHpMp;
import l1r.bi.CalcStat;
import l1r.bj.ClientThread;

public class C_CharReset extends ClientBasePacket {
   private static final String a = "[C] C_CharReset";

   public C_CharReset(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         if (var4 == 1) {
            int var5 = this.c();
            int var6 = this.c();
            int var7 = this.c();
            int var8 = this.c();
            int var9 = this.c();
            int var10 = this.c();
            int var11 = CalcInitHpMp.a(var3);
            int var12 = CalcInitHpMp.c(var3);
            var3.a(new S_OwnCharStatus2(var3));
            var3.a(new S_CharReset(var3, 1, var11, var12, 10, var5, var6, var7, var8, var9, var10));
            this.a(var3, var11, var12, var5, var6, var7, var8, var9, var10);
            CharacterTable.a().d(var3);
         } else if (var4 == 2) {
            int var13 = this.c();
            if (var13 == 0) {
               this.a(var3, 1);
            } else if (var13 == 7) {
               if (var3.cx() - var3.cw() < 10) {
                  return;
               }

               if (var3.cw() >= 40) {
                  return;
               }

               this.a(var3, 10);
            } else if (var13 == 1) {
               var3.o(1);
               this.a(var3, 1);
            } else if (var13 == 2) {
               var3.s(1);
               this.a(var3, 1);
            } else if (var13 == 3) {
               var3.t(1);
               this.a(var3, 1);
            } else if (var13 == 4) {
               var3.q(1);
               this.a(var3, 1);
            } else if (var13 == 5) {
               var3.p(1);
               this.a(var3, 1);
            } else if (var13 == 6) {
               var3.r(1);
               this.a(var3, 1);
            } else if (var13 == 8) {
               switch (this.c()) {
                  case 1:
                     var3.o(1);
                     break;
                  case 2:
                     var3.s(1);
                     break;
                  case 3:
                     var3.t(1);
                     break;
                  case 4:
                     var3.q(1);
                     break;
                  case 5:
                     var3.p(1);
                     break;
                  case 6:
                     var3.r(1);
               }

               if (var3.bB() > 0) {
                  var3.a(new S_CharReset(var3.bB()));
                  return;
               }

               this.a(var3);
            }
         } else if (var4 == 3) {
            var3.o(this.c() - var3.bf());
            var3.s(this.c() - var3.bj());
            var3.t(this.c() - var3.bk());
            var3.q(this.c() - var3.bh());
            var3.p(this.c() - var3.bg());
            var3.r(this.c() - var3.bi());
            this.a(var3);
         }
      }
   }

   private void a(L1PcInstance var1) {
      var1.s(false);
      var1.aa();
      var1.a(var1.ew());
      var1.i_(var1.ex());
      if (var1.cx() != var1.ev()) {
         var1.b(var1.cx());
         var1.k(ExpTable.a(var1.cx()));
      }

      if (var1.ev() > 50) {
         var1.ay(var1.ev() - 50);
      } else {
         var1.ay(0);
      }

      var1.a(new S_OwnCharStatus(var1));
      L1ItemInstance var2 = var1.j().b(49142);
      if (var2 != null) {
         var1.j().b(var2, 1);
         var1.I();
      }

      L1Teleport.a(var1, 32628, 32772, 4, 4, true);
   }

   private void a(L1PcInstance var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      var1.m(var2 - var1.bd());
      var1.n(var3 - var1.be());
      var1.o(var4 - var1.bf());
      var1.s(var5 - var1.bj());
      var1.t(var6 - var1.bk());
      var1.q(var7 - var1.bh());
      var1.p(var8 - var1.bg());
      var1.r(var9 - var1.bi());
   }

   private void a(L1PcInstance var1, int var2) {
      var1.aS(var1.cw() + var2);

      for (int var3 = 0; var3 < var2; var3++) {
         int var4 = CalcStat.a(var1);
         int var5 = CalcStat.b(var1);
         var1.m(var4);
         var1.n(var5);
      }

      int var6 = 10 + CalcStat.a(var1.bh()) + var1.aC().d(var1.cw());
      var1.a(new S_CharReset(var1, var1.cw(), var1.bd(), var1.be(), var6, var1.bf(), var1.bj(), var1.bk(), var1.bh(), var1.bg(), var1.bi()));
   }

   @Override
   public String a() {
      return "[C] C_CharReset";
   }
}
