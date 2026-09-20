package l1r.aj;

import java.io.File;
import l1r.ao.CharacterTable;
import l1r.ao.ClanMembersTable;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.be.S_CharEvent;
import l1r.be.S_CharTitle;
import l1r.be.S_ClanName;
import l1r.be.S_PacketBox;
import l1r.be.S_PledgeWatch;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_LeaveClan extends ClientBasePacket {
   public C_LeaveClan(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      String var3 = this.g();
      L1PcInstance var4 = var2.f();
      if (var4 != null) {
         String var5 = var4.et();
         int var6 = var4.aF();
         if (var6 != 0) {
            L1Clan var7 = ClanTable.a().c(var3);
            if (var7 == null) {
               var4.ah(0);
               var4.c("");
               var4.ai(0);
               var4.f("");
               var4.a(new S_CharTitle(var4.fr(), ""));
               var4.b(new S_CharTitle(var4.fr(), ""));
               var4.a(new S_CharEvent(60, var4.fr(), 0));
               var4.b(new S_CharEvent(60, var4.fr(), 0));
               var4.I();
               var4.a(new S_ServerMessage(178, var5, var3));
               ClanMembersTable.a().a(var4.fr());
            } else if (var4.x() && var4.fr() == var7.k()) {
               int var13 = var7.m();
               int var14 = var7.n();
               if (var13 == 0 && var14 == 0) {
                  if (L1World.a().b(var3)) {
                     var4.a(new S_ServerMessage(302));
                  } else {
                     for (int var10 = 0; var10 < var7.p().size(); var10++) {
                        L1PcInstance var11 = L1World.a().a(var7.p().get(var10));
                        if (var11 != null) {
                           var11.a(new S_ServerMessage(269, var5, var3));
                           var11.a(new S_PacketBox(27, 11, ""));
                           var11.a(new S_CharEvent(60, var11.fr(), 0));
                           var11.a(new S_ClanName(var11, false));
                           var11.a(new S_PledgeWatch());
                           if (var11.bB(4084)) {
                              var11.bz(4084);
                              var11.a(new S_PacketBox(180, 450, 3240, 0));
                           }

                           var11.ah(0);
                           var11.c("");
                           var11.ai(0);
                           var11.f("");
                           var11.a(new S_CharTitle(var11.fr(), ""));
                           var11.b(new S_CharTitle(var11.fr(), ""));
                           var11.b(new S_CharEvent(60, var11.fr(), 0));
                           var11.I();
                        } else {
                           L1PcInstance var12 = CharacterTable.a().a(var7.p().get(var10));
                           var12.ah(0);
                           var12.c("");
                           var12.ai(0);
                           var12.f("");
                           var12.I();
                        }
                     }

                     String var15 = String.valueOf(var7.i());
                     File var16 = new File("./emblem/" + var15);
                     var16.delete();
                     ClanTable.a().b(var3);
                     ClanMembersTable.a().b(var7.e());
                  }
               } else {
                  var4.a(new S_ServerMessage(665));
               }
            } else if (L1World.a().b(var3)) {
               var4.a(new S_ServerMessage(331));
            } else {
               for (L1PcInstance var8 : var7.b()) {
                  var8.a(new S_ServerMessage(178, var5, var3));
                  if (var7.b().size() <= 3 && var8.bB(4084)) {
                     var8.bz(4084);
                     var8.a(new S_PacketBox(180, 450, 3240, 0));
                  }
               }

               if (var7.o() == var4.fr()) {
                  var7.i(0);
               }

               var4.ah(0);
               var4.c("");
               var4.ai(0);
               var4.f("");
               var4.a(new S_CharTitle(var4.fr(), ""));
               var4.b(new S_CharTitle(var4.fr(), ""));
               var4.I();
               var4.a(new S_PledgeWatch());
               var4.a(new S_PacketBox(27, 11, ""));
               var4.a(new S_CharEvent(60, var4.fr(), 0));
               var4.b(new S_CharEvent(60, var4.fr(), 0));
               var4.a(new S_ClanName(var4, false));
               var7.b(var5);
               ClanMembersTable.a().a(var4.fr());
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_LeaveClan";
   }
}
