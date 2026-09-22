package l1r.aj;

import java.util.concurrent.ConcurrentHashMap;
import l1r.ao.AccountTable;
import l1r.ao.ClanMembersTable;
import l1r.ao.ClanTable;
import l1r.ao.HistoryTable;
import l1r.ao.ItemTable;
import l1r.ao.LuckyDrawTable;
import l1r.ao.ResolventTable;
import l1r.ao.ShopWorldTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.be.S_CharEvent;
import l1r.be.S_Pledge;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_RetrieveList;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Account;
import l1r.bj.ClientThread;

public class C_ShopWorld extends ClientBasePacket {
   public C_ShopWorld(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.c();
         switch (var4) {
            case 8:
               int var14 = this.b();
               int var15 = this.b();
               int var16 = this.b();
               int var17 = this.b();
               int var18 = this.b();
               int var19 = this.b();
               int var20 = this.c();
               if (var20 == 0) {
                  ShopWorldTable.L1R_b var30 = ShopWorldTable.a().b().get(var15);
                  if (var30 == null) {
                     var3.a(new S_ServerMessage(156));
                     return;
                  }
                  L1ItemInstance var33 = var30.a;
                  L1Account var34 = var2.e();
                  if (var18 != var30.b * var17) {
                     var3.a(new S_ServerMessage(156));
                     return;
                  }

                  if (var33 == null || var33.m() != var16) {
                     var3.a(new S_ServerMessage(156));
                     return;
                  }

                  int var24 = var34.p();
                  if (var24 < var18) {
                     var3.a(new S_ServerMessage(2742));
                     return;
                  }

                  if (var3.aK().e().h() < var30.d) {
                     var3.a(new S_ServerMessage(2743));
                     return;
                  }

                  int newBalance = var24 - var18;
                  if (!ShopWorldTable.a().a(var34.d(), var24, newBalance, var33.N(), var17)) {
                     var3.a(new S_ServerMessage(156));
                     return;
                  }
                  var34.g(newBalance);
                  var3.a(new S_ServerMessage(2745));
                  var3.a(new S_CharEvent(37, var34.p()));
                  var3.bd(var3.cQ() + var18);
                  HistoryTable.a().h(var3, "購買", var33, var17);
               } else if (var20 == 1) {
                  String var31 = this.g();
               } else if (var20 == 1) {
                  String var32 = this.g();
               }
               break;
            case 9:
               ConcurrentHashMap var13 = ShopWorldTable.a().a(var2.a());
               if (var13.isEmpty()) {
                  var3.a(new S_ServerMessage(2746));
                  return;
               }

               var3.a(new S_RetrieveList(var13));
               break;
            case 10:
               int var5 = this.b();

               for (int var25 = 0; var25 < var5; var25++) {
                  int var26 = this.b();
                  int var27 = this.b();
                  ConcurrentHashMap var28 = ShopWorldTable.a().a(var2.a());
                  L1ItemInstance var29 = var28.get(var26);
                  if (var29 == null) {
                     return;
                  }
                  int authoritativeCount = var29.E();
                  if (authoritativeCount <= 0) {
                     return;
                  }
                  if (var3.j().a(var29, authoritativeCount) != 0) {
                     return;
                  }

                  var3.j().d(var29);
                  var3.a(new S_ServerMessage(403, var29.s()));
                  ShopWorldTable.a().a(var2.a(), var26);
                  HistoryTable.a().h(var3, "領取", var29, authoritativeCount);
               }
               break;
            case 11:
               if (!var3.cN()) {
                  var3.a(new S_CharEvent(ShopWorldTable.a().b()));
                  var3.a(new S_ProtoBuffers(LuckyDrawTable.a().c(var2.a()), 0));
                  var3.x(true);
               }
            case 12:
            case 14:
            default:
               break;
            case 13:
               int var6 = this.b();
               int var7 = this.b();
               int var8 = this.b();
               l1r.aq.L1Object var9Object = L1World.a().a(var6);
               if (!(var9Object instanceof L1NpcInstance)) {
                  return;
               }
               L1NpcInstance var9 = (L1NpcInstance)var9Object;
               L1ItemInstance var10 = var3.j().e(var7);
               if (var10 == null) {
                  return;
               }

               int var11 = ResolventTable.a().a(var10);
               L1ItemInstance var12 = var3.j().e(var8);
               if (var12 != null && var12.N() == 640343) {
                  var11 = (int)(var11 * 1.5);
                  var3.j().b(var12, 1);
               }

               ItemTable.a(var3, 640341, var11, var9.T());
               var3.j().b(var10, 1);
               break;
            case 15:
               String var21 = this.g();
               L1Clan var22 = ClanTable.a().a(var3.aF());
               if (var22 == null) {
                  return;
               }
               if (!var3.x() || var3.fr() != var22.k()) {
                  var3.a(new S_ServerMessage(518));
                  return;
               }
               var22.f(var21);
               ClanTable.a().b(var22);
               break;
            case 16:
               String var23 = this.g();
               if (var23 == null) {
                  var23 = "";
               }

               ClanMembersTable.a().a(var3, var23);
               var3.a(new S_Pledge(var3.et(), var23));
         }
      }
   }

   @Override
   public String a() {
      return "C_ShopWorld";
   }
}
