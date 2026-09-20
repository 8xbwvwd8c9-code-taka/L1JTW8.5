package l1r.bc;

import java.util.Calendar;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aj.C_LoginToServer;
import l1r.ao.AccountTable;
import l1r.ao.CastleTable;
import l1r.ao.CharacterConfigTable;
import l1r.ao.CharacterEquipment;
import l1r.ao.CharacterGiftTable;
import l1r.ao.CharacterMobsTable;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.ao.QuestNewTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1Master;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.as.L1CastleWar;
import l1r.as.L1ThebesBattle;
import l1r.be.S_Bookmarks;
import l1r.be.S_CastleMaster;
import l1r.be.S_CharEvent;
import l1r.be.S_ClanName;
import l1r.be.S_InitAbility;
import l1r.be.S_Karma;
import l1r.be.S_LoginGame;
import l1r.be.S_LoginResult;
import l1r.be.S_MapID;
import l1r.be.S_Message_YN;
import l1r.be.S_OwnCharPack;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_PacketBox;
import l1r.be.S_PledgeWatch;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_RuneSlot;
import l1r.be.S_SPMR;
import l1r.be.S_ServerMessage;
import l1r.be.S_War;
import l1r.be.S_Weather;
import l1r.bh.L1Account;
import l1r.bh.L1Castle;
import l1r.bi.GeneralThreadPool;
import l1r.bj.ClientThread;

public class ShiftLoginTimer extends TimerTask {
   private static final Logger a = Logger.getLogger(ShiftLoginTimer.class.getName());
   private final ClientThread b;
   private final String c;
   private final String d;

   public ShiftLoginTimer(ClientThread var1, String var2, String var3) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
   }

   public void a(long var1) {
      System.out.println("[Shift Server]:" + this.d + "轉換伺服器中...");
      GeneralThreadPool.a().a(this, var1);
   }

   @Override
   public void run() {
      try {
         if (L1World.a().a(this.d) != null) {
            GeneralThreadPool.a().a(this, 3000L);
            return;
         }

         System.out.println("[Shift Server]:" + this.d + "轉換伺服器...OK");
         this.a(this.b, this.c, this.d);
      } catch (Exception var2) {
         a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
      }
   }

   private void a(ClientThread var1, String var2, String var3) {
      L1Account var4 = AccountTable.a().c(var2);

      try {
         var1.a(var4);
         var1.a(new S_LoginResult(0));
         L1PcInstance var5 = L1PcInstance.b(var3);
         if (var5 == null) {
            return;
         }

         int var6 = var5.ea();
         int var7 = var5.eb();
         var5.aC(1);
         CharacterTable.a().c(var5);
         L1World.a().a(var5);
         var5.a(var1);
         var1.a(var5);
         var5.a(new S_LoginGame());
         AccountTable.a().b(var4, true);
         if (var5.fp() == 4) {
            int var8 = (var5.fr() + Calendar.getInstance().get(5)) % 3;
            var5.bw(4 + var8);
            if (var5.dX() == 4) {
               var5.cG(32734);
               var5.cH(32756);
            } else if (var5.dX() == 5) {
               var5.cG(32663);
               var5.cH(32890);
            } else {
               var5.cG(32732);
               var5.cH(33040);
            }

            var5.cE(10500);
         } else if (var5.fp() >= 10500 && var5.fp() <= 10502) {
            var5.cG(32612);
            var5.cH(33186);
            var5.cE(4);
            var5.bw(-1);
         }

         L1World.a().c(var5);
         CharacterConfigTable.a().a(var5);
         C_LoginToServer.a(var5);
         var5.a(new S_RuneSlot(68));
         var5.a(new S_RuneSlot(67, var5.cP()));
         var5.ad();
         var5.a(new S_Bookmarks(var5));
         var5.a(new S_OwnCharStatus(var5));
         var5.a(new S_MapID(var5.fp(), var5.fq().g()));
         var5.a(new S_OwnCharPack(var5));
         var5.a(new S_ProtoBuffers(485, var5));
         L1Castle[] var11;
         int var10 = (var11 = CastleTable.a().b()).length;

         for (int var9 = 0; var9 < var10; var9++) {
            L1Castle var13 = var11[var9];
            var5.a(new S_CastleMaster(var13.a(), var13.h() > 0 ? var13.h() : 0));
         }

         var5.i();
         var5.a(new S_InitAbility(var5));
         var5.a(new S_ProtoBuffers(487, 1));
         var5.a(new S_ProtoBuffers(487, 2));
         var5.a(new S_ProtoBuffers(487, 3));
         var5.a(new S_ProtoBuffers(490, var5));
         var5.a(new S_ProtoBuffers(489, var5));
         var5.a(new S_Weather(L1World.a().j()));
         C_LoginToServer.b(var5);
         C_LoginToServer.d(var5);
         CharacterMobsTable.a().b(var5);
         var5.fg();
         var5.a(new S_SPMR(var5));
         var5.a(new S_Karma(var5));
         var5.a(new S_PacketBox(132, var5.u()));
         if (var5.ea() > 0) {
            var5.X(false);
            var5.cq(0);
         } else {
            var5.X(true);
            var5.cq(8);
         }

         if (var5.ev() >= 51 && var5.ev() - 50 > var5.bA() && var5.bf() + var5.bh() + var5.bg() + var5.bj() + var5.bk() + var5.bi() < 270) {
            int var14 = var5.ev() - 50 - var5.bA();
            var5.a(new S_Message_YN(479, "" + var14));
         }

         C_LoginToServer.c(var5);
         L1CastleWar.a().a(var5);
         if (var5.aF() != 0) {
            L1Clan var15 = ClanTable.a().a(var5.aF());
            if (var15 == null) {
               var5.ah(0);
               var5.c("");
               var5.ai(0);
               var5.I();
            } else {
               var5.a(new S_ClanName(var5));
               var5.a(new S_PacketBox(173, var15.j()));
               var5.a(new S_PledgeWatch(var15));

               for (L1PcInstance var17 : var15.b()) {
                  if (var17.fr() != var5.fr()) {
                     var17.a(new S_ServerMessage(843, var5.et()));
                  }

                  if (var15.b().size() >= 3 && !var17.bB(4084)) {
                     var17.j(4084, 0);
                     var17.a(new S_PacketBox(180, 450, 3240, 1));
                  }
               }

               L1War var18 = L1World.a().c(var15.f());
               if (var18 != null) {
                  for (L1Clan var20 : var18.c(var15.f())) {
                     if (!var20.f().contains("安安妳好再見_")) {
                        var5.a(new S_War(8, var15.f(), var20.f()));
                     } else {
                        var5.a(new S_ServerMessage(235, var20.f()));
                     }

                     if (var18.c() == 1) {
                        var5.a(new S_ProtoBuffers(76, var5));
                     }
                  }
               }
            }
         }

         if (var5.bD() != 0) {
            L1PcInstance var16 = (L1PcInstance)L1World.a().a(var5.bD());
            if (var16 != null && var16.bD() != 0 && var5.bD() == var16.fr() && var16.bD() == var5.fr()) {
               var5.a(new S_ServerMessage(548));
               var16.a(new S_ServerMessage(549));
            }
         }

         if (var6 > var5.ea()) {
            var5.a(var6);
         }

         if (var7 > var5.eb()) {
            var5.i_(var7);
         }

         var5.a();
         var5.c();
         var5.e();
         var5.G();
         var5.f();
         var5.I();
         var5.ac();
         var5.ae();
         if (!var5.bB(25009) && !var5.bB(25010) && !var5.bB(25011)) {
            var5.j(25009, 180000);
            var5.a(new S_PacketBox(150, 1, 180));
         }

         L1Master.a().b(var5);
         var5.a(new S_CharEvent(37, var1.e().p()));
         var5.a(new S_ProtoBuffers(559, var5));
         var5.a(new S_ProtoBuffers(560, var5));
         var5.a(new S_PacketBox(189));
         var5.a(new S_ProtoBuffers(103, 4126));
         CharacterGiftTable.a().a(var5);
         CharacterEquipment.a().b(var5);
         QuestNewTable.a().b(var5);
         if (var5.l()) {
            var5.j(26003, 0);
         }

         var5.a(new S_ProtoBuffers(141));
         if (var5.fp() == 10500) {
            L1ThebesBattle.a().a(var5);
         }
      } catch (Exception var12) {
         var12.printStackTrace();
      }
   }
}
