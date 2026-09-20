package l1r.aj;

import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.be.S_CharTitle;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_Title extends ClientBasePacket {
   public C_Title(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         String var4 = this.g();
         String var5 = this.g();
         if (!var4.isEmpty() && !var5.isEmpty()) {
            L1PcInstance var6 = L1World.a().a(var4);
            if (var6 != null) {
               if (var3.l()) {
                  this.a(var6, var5);
               } else {
                  if (this.a(var3)) {
                     if (var3.fr() == var6.fr()) {
                        if (var3.ev() < 10) {
                           var3.a(new S_ServerMessage(197));
                           return;
                        }

                        this.a(var3, var5);
                     } else {
                        if (var3.aF() != var6.aF()) {
                           var3.a(new S_ServerMessage(199));
                           return;
                        }

                        if (var6.ev() < 10) {
                           var3.a(new S_ServerMessage(202, var4));
                           return;
                        }

                        this.a(var6, var5);
                        L1Clan var7 = ClanTable.a().a(var3.aF());
                        if (var7 != null) {
                           for (L1PcInstance var8 : var7.b()) {
                              var8.a(new S_ServerMessage(203, var3.et(), var4, var5));
                           }
                        }
                     }
                  } else if (var3.fr() == var6.fr()) {
                     if (var3.aF() != 0 && !Config.T) {
                        var3.a(new S_ServerMessage(198));
                        return;
                     }

                     if (var6.ev() < 40) {
                        var3.a(new S_ServerMessage(200));
                        return;
                     }

                     this.a(var3, var5);
                  } else if (var3.x() && var3.aF() == var6.aF()) {
                     var3.a(new S_ServerMessage(201, var6.et()));
                     return;
                  }
               }
            }
         } else {
            var3.a(new S_ServerMessage(196));
         }
      }
   }

   private void a(L1PcInstance var1, String var2) {
      int var3 = var1.fr();
      var1.f(var2);
      var1.a(new S_CharTitle(var3, var2));
      var1.b(new S_CharTitle(var3, var2));
      var1.I();
   }

   private boolean a(L1PcInstance var1) {
      boolean var2 = false;
      if (var1.aF() != 0) {
         L1Clan var3 = ClanTable.a().a(var1.aF());
         if (var3 != null && var1.x() && var1.fr() == var3.k()) {
            var2 = true;
         }
      }

      return var2;
   }

   @Override
   public String a() {
      return "C_Title";
   }
}
