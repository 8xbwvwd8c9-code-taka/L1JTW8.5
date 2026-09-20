package l1r.aj;

import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_RetrieveList;
import l1r.be.S_RetrieveListClan;
import l1r.be.S_RetrieveListElven;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Account;
import l1r.bj.ClientThread;

public class C_WarePassword extends ClientBasePacket {
   public C_WarePassword(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         int var5 = this.b();
         int var6 = this.b();
         this.d();
         L1Account var7 = var2.e();
         if (var4 == 0) {
            if (var5 < 0 && var6 < 0) {
               var3.a(new S_ServerMessage(79));
            } else if (var5 < 0 && var7.m() == 0) {
               var7.f(var6);
               var3.a(new S_SystemMessage("倉庫密碼設定完成，請牢記您的新密碼。"));
            } else if (var5 > 0 && var5 == var7.m()) {
               if (var5 == var6) {
                  var3.a(new S_ServerMessage(342));
                  return;
               }

               if (var6 > 0) {
                  var7.f(var6);
                  var3.a(new S_SystemMessage("倉庫密碼變更完成，請牢記您的新密碼。"));
               } else {
                  var7.f(0);
                  var3.a(new S_SystemMessage("倉庫密碼取消完成。"));
               }
            } else {
               var3.a(new S_ServerMessage(835));
            }
         } else if (var7.m() == var5) {
            int var8 = var6;
            L1Object var9 = L1World.a().a(var8);
            if (var3.ev() >= 5) {
               if (var4 == 1) {
                  if (var9 != null && var9 instanceof L1NpcInstance) {
                     L1NpcInstance var10 = (L1NpcInstance)var9;
                     switch (var10.z()) {
                        case 60028:
                           if (var3.A()) {
                              var3.a(new S_RetrieveListElven(var8, var3));
                           }
                           break;
                        default:
                           var3.a(new S_RetrieveList(var8, var3));
                     }
                  }
               } else if (var4 == 2) {
                  if (var3.aF() == 0) {
                     var3.a(new S_ServerMessage(208));
                     return;
                  }

                  int var11 = var3.aH();
                  if (var11 == 7) {
                     var3.a(new S_ServerMessage(728));
                     return;
                  }

                  if (var11 != 8 && var11 != 9 && var11 != 5 && var11 != 10 && var11 != 3 && var11 != 6 && var11 != 4) {
                     var3.a(new S_ServerMessage(728));
                     return;
                  }

                  var3.a(new S_RetrieveListClan(var8, var3));
               }
            }
         } else {
            var3.a(new S_ServerMessage(835));
         }
      }
   }
}
