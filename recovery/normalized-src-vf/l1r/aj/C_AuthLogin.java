package l1r.aj;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.GameServer;
import l1r.ao.AccountTable;
import l1r.ao.AnnounceTable;
import l1r.ao.ItemTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1LoginCharList;
import l1r.aq.L1Master;
import l1r.aq.L1World;
import l1r.be.S_LoginResult;
import l1r.be.S_Message_YN;
import l1r.be.S_News;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Account;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_AuthLogin extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_AuthLogin.class.getName());

   public C_AuthLogin(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.c();
      switch (var3) {
         case 6:
            String var4 = this.g().toLowerCase();
            String var5 = this.g();
            String var6 = var2.g();
            String var7 = "";
            String var8 = "";
            String var9 = "";
            if (var4.contains(":")) {
               var7 = var4.split(":")[0];
               var8 = var4.split(":")[1];
            } else {
               var7 = var4;
            }

            if (var5.contains(":")) {
               String[] var10 = var5.split(":");
               var5 = var10[0];
               var9 = var10[1];
            }

            if (Config.aT && !Config.aU.equalsIgnoreCase(var9)) {
               a.log(Level.SEVERE, "登入器版本錯誤。目前版本=" + Config.aU + " 使用版本=" + var9);
               var2.a(new S_LoginResult(39));
               return;
            }

            if (!var7.matches("[0-9a-zA-Z]*")) {
               a.log(Level.SEVERE, "不合法的帳號字元。account=" + var7 + " ip=" + var6);
               var2.a(new S_LoginResult(9));
               return;
            }

            L1Account var20 = AccountTable.a().c(var7);
            if (var20 == null) {
               if (!Config.n) {
                  a.log(Level.SEVERE, "目前不可創帳號。account=" + var7 + " ip=" + var6);
                  var2.a(new S_LoginResult(155));
                  return;
               }

               if (!var8.equals("")) {
                  if (!AccountTable.a().b(var8)) {
                     a.log(Level.SEVERE, "創帳號數量超過限制。account=" + var7 + " mac=" + var8);
                     var2.a(new S_LoginResult(156));
                     return;
                  }
               } else if (!AccountTable.a().a(var6)) {
                  a.log(Level.SEVERE, "創帳號數量超過限制。account=" + var7 + " ip=" + var6);
                  var2.a(new S_LoginResult(156));
                  return;
               }

               var20 = AccountTable.a().a(var7, var5, var6, var8);
            }

            if (!var20.a(var5)) {
               a.log(Level.SEVERE, "密碼錯誤。account=" + var7 + " ip=" + var6);
               var2.a(new S_LoginResult(149));
               return;
            }

            if (var20.n()) {
               boolean var24 = false;

               for (ClientThread var26 : GameServer.a().c()) {
                  if (var26.a() != null && var26.a().equals(var7)) {
                     a.log(Level.SEVERE, "已使用中-踢掉重複的連線及帳號。account=" + var7 + " ip=" + var6);
                     var24 = true;
                     var26.a(0);
                  }
               }

               if (!var24) {
                  AccountTable.a().a(var20, false);
                  AccountTable.a().b(var20, false);
                  AccountTable.a().e(var20.d());
                  a.log(Level.SEVERE, "已使用中-踢掉重複的帳號。account=" + var7 + " ip=" + var6);
               }

               var2.a(new S_LoginResult(22));
               return;
            }

            if (var20.j()) {
               a.log(Level.SEVERE, "禁止登入的帳號嘗試登入。account=" + var7 + " ip=" + var6);
               var2.a(new S_LoginResult(153));
               return;
            }

            if (Config.u > 0) {
               int var23 = 0;

               for (ClientThread var25 : GameServer.a().c()) {
                  if (var6.equalsIgnoreCase(var25.g())) {
                     var23++;
                  }
               }

               if (var23 > Config.u) {
                  a.log(Level.SEVERE, "超過多開設定-拒絕登入。account=" + var7 + " ip=" + var6);
                  var2.a(new S_LoginResult(38));
                  return;
               }
            }

            try {
               var20.d(var6);
               var20.f(var8);
               AccountTable.a().a(var20, var2);
               var2.a(var20);
               var2.a(new S_LoginResult(0));
               if (Config.x) {
                  AnnounceTable.a().a(var2);
                  var2.a(new S_News(var2.b()));
               } else {
                  L1LoginCharList.a(var2);
               }
            } catch (Exception var18) {
               a.log(Level.SEVERE, "登入帳號時異常。account=" + var7 + " ip=" + var6);
               a.log(Level.SEVERE, var18.getLocalizedMessage(), var18);
               var2.a(new S_LoginResult(158));
               var2.c();
            }
         case 11:
         case 28:
         default:
            break;
         case 13: {
            L1PcInstance var22 = var2.f();
            int var29 = this.c();
            if (var29 == 0) {
               String var16 = this.g();
               L1PcInstance var17 = L1World.a().a(var16);
               if (var17 == null || var22.et().equals(var16)) {
                  var22.a(new S_ServerMessage(2965));
                  return;
               }

               if (var17.ev() < 70 || var17.bB(25007)) {
                  var22.a(new S_ServerMessage(2974));
                  return;
               }

               if (var22.ev() >= 70 || var22.cE() == -1 || var22.bB(25007)) {
                  var22.a(new S_ServerMessage(2975));
                  return;
               }

               if (L1Master.a().c(var17.fr()) >= 4) {
                  var22.a(new S_ServerMessage(2972));
                  return;
               }

               var22.a(new S_Message_YN(2967, var16));
               var22.am(var17.fr());
            } else if (var29 == 1) {
               String var30 = this.g();
               int var32 = var22.cE() == -1 ? var22.fr() : var22.cE();
               L1Master.a().a(var32, var30);
               var22.j(25007, 28800000);
            } else if (var29 == 2) {
               int var31 = var22.cE();
               if (var31 == -1) {
                  var22.a(new S_PacketBox(146, var22.fr()));
               } else if (var22.cE() > 0) {
                  var22.a(new S_PacketBox(146, var31));
               } else {
                  var22.a(new S_ServerMessage(2979));
               }
            }
            break;
         }
         case 14:
            L1PcInstance var21 = var2.f();
            int var12 = var21.cZ();
            ItemTable.a(var21, 615318 + var12, 1);
            int var13 = var12 == 25011 ? 25009 : var12 + 1;
            int var14 = 180;
            if (var13 == 25010) {
               var14 = 900;
            } else if (var13 == 25011) {
               var14 = 3600;
            }

            var21.j(var13, var14 * 1000);
            var21.a(new S_PacketBox(150, var13 - 25008, var14));
            break;
         case 15: {
            int var15 = this.c();
            L1PcInstance var11 = var2.f();
         }
      }
   }

   @Override
   public String a() {
      return "C_AuthLogin";
   }
}
