package l1r.aj;

import l1r.ap.L1DollInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.aq.L1Trade;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_TradeAddItem extends ClientBasePacket {
   public C_TradeAddItem(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.b();
         L1ItemInstance var6 = var3.j().e(var4);
         if (var6 == null) {
            var3.a(new S_ServerMessage(156));
         } else if (!var6.a().s()) {
            var3.a(new S_ServerMessage(210, var6.a().h()));
         } else if (var6.F() >= 128) {
            var3.a(new S_ServerMessage(210, var6.a().h()));
         } else {
            for (L1NpcInstance var7 : var3.ek().values()) {
               if (var7 instanceof L1PetInstance) {
                  L1PetInstance var9 = (L1PetInstance)var7;
                  if (var6.fr() == var9.k()) {
                     var3.a(new S_ServerMessage(1187));
                     return;
                  }
               }
            }

            for (L1DollInstance var10 : var3.el().values()) {
               if (var10.f() == var6.fr()) {
                  var3.a(new S_ServerMessage(1181));
                  return;
               }
            }

            L1PcInstance var11 = (L1PcInstance)L1World.a().a(var3.aO());
            if (var11 != null) {
               if (!var3.aP()) {
                  if (var11.j().a(var6, var5) != 0) {
                     var11.a(new S_ServerMessage(270));
                     var3.a(new S_ServerMessage(271));
                  } else {
                     L1Trade.a(var3, var4, var5);
                  }
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_TradeAddItem";
   }
}
