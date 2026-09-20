package l1r.aj;

import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1SpeedChecker;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_Attack extends ClientBasePacket {
   public C_Attack(byte[] var1, ClientThread var2) {
      super(var1);
      if (var2 != null) {
         L1PcInstance var3 = var2.f();
         if (var3 != null) {
            if (!var3.bN() && !var3.eX() && !var3.aR() && !var3.ed() && !var3.ec()) {
               int var4 = this.b();
               int var5 = this.d();
               int var6 = this.d();
               L1Object var7 = L1World.a().a(var4);
               if (var3.j().h() > 82) {
                  var3.a(new S_ServerMessage(110));
                  var3.bb(0);
               } else if (var3.bB(60) || var3.N()) {
                  var3.bb(0);
               } else if (var3.ed()) {
                  var3.bb(0);
               } else {
                  if (var7 instanceof L1Character) {
                     int var8 = 1;
                     if (var3.v() != null) {
                        var8 = var3.v().a().aB();
                        var8 = var8 < 0 ? 15 : var8;
                     }

                     if (var7.fp() != var3.fp() || var3.fu().b(var7.fu()) > var8 + 1.5) {
                        var3.bb(0);
                        return;
                     }
                  }

                  if (var7 instanceof L1NpcInstance) {
                     int var12 = ((L1NpcInstance)var7).ac();
                     if (var12 == 1 || var12 == 2) {
                        var3.bb(0);
                        return;
                     }
                  }

                  int var13 = var3.ce().a(L1SpeedChecker.a.b);
                  if (var13 == 2) {
                     var3.bb(0);
                  } else {
                     if (var3.bB(78)) {
                        var3.bz(78);
                     }

                     if (var3.bB(32)) {
                        var3.bz(32);
                     }

                     if (var3.bB(97) && !var3.bB(233)) {
                        var3.bz(97);
                     }

                     var3.e(1);
                     if (var7 instanceof L1Character && !((L1Character)var7).eX()) {
                        var7.c(var3);
                     } else {
                        L1Character var9 = new L1Character();
                        var9.cF(var4);
                        var9.cG(var5);
                        var9.cH(var6);
                        L1Attack var10 = new L1Attack(var3, var9);
                        var10.c();
                        var3.bb(0);
                     }
                  }
               }
            }
         }
      }
   }
}
