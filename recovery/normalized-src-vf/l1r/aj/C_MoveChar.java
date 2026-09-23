package l1r.aj;

import l1r.ao.TrapSpawnTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Dungeon;
import l1r.aq.L1SpeedChecker;
import l1r.aq.L1Trade;
import l1r.be.S_Blink;
import l1r.be.S_MoveCharPacket;
import l1r.be.S_SystemMessage;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_MoveChar extends ClientBasePacket {
   private final int[][] a = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

   public C_MoveChar(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.aR()) {
         int var4 = this.d();
         int var5 = this.d();
         int var6 = this.c();
         int var7 = var3.ce().a(L1SpeedChecker.L1R_a.a);
         if (var7 != 2) {
            if (var3.aO() != 0) {
               L1Trade.b(var3);
            }

            if (var3.cO() != null) {
               var3.cO().a();
            }

            if (var3.bB(32)) {
               var3.bz(32);
            }

            var3.aQ(0);
            if (!var3.bB(78)) {
               var3.e(2);
            }

            if (Config.m == 3) {
               boolean var8 = (var4 | var5 << 16) == ((var3.fs() | var3.ft() << 16) ^ 1140367772) * (var3.fr() | 1);
               if (!var8) {
                  var3.a(new S_SystemMessage("char move has some error"));
                  var3.a(new S_Blink());
                  return;
               }

               var6 ^= 73;
               var4 = var3.fs();
               var5 = var3.ft();
            }

            if (var6 < 0 || var6 >= this.a.length) {
               var3.a(new S_Blink());
               return;
            }

            var4 += this.a[var6][0];
            var5 += this.a[var6][1];
            if (!var3.fq().b(var3.fs(), var3.ft(), var6)) {
               var3.a(new S_SystemMessage("Lineage map has some error"));
               var3.a(new S_Blink());
            } else if (!L1Dungeon.a().a(var4, var5, var3.fq().b(), var3)) {
               var3.fq().a(var3.fu(), true);
               var3.fu().a(var4, var5);
               var3.ct(var6);
               var3.fq().a(var3.fu(), false);
               TrapSpawnTable.a().a(var3);
               if (!var3.aA() && !var3.bN()) {
                  if (var3.ff()) {
                     var3.c(new S_MoveCharPacket(var3));
                  } else {
                     var3.b(new S_MoveCharPacket(var3));
                  }
               }
            }
         }
      }
   }

   public void a(L1PcInstance var1) {
      var1.a(new S_SystemMessage(var1.fq().e(var1.fu())));
   }
}
