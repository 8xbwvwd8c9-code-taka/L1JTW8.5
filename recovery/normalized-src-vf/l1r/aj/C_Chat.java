package l1r.aj;

import java.util.ArrayList;
import java.util.Arrays;
import l1r.ai.GMCommands;
import l1r.ao.ClanTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_ChatPacket;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_Chat extends ClientBasePacket {
   public C_Chat(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         if (!var3.bB(64) && !var3.bB(161) && !var3.bB(1007)) {
            if (var3.bB(1005)) {
               var3.a(new S_ServerMessage(242));
            } else {
               int var4 = this.c();
               String var5 = this.g();
               if (var4 != 0 && var4 != 2) {
                  if (var4 == 3) {
                     this.a(var3, var5, var4);
                  } else if (var4 == 4) {
                     if (var3.aF() == 0) {
                        return;
                     }

                     L1Clan var13 = ClanTable.a().a(var3.aF());

                     for (L1PcInstance var20 : var13.b()) {
                        if (!var20.cd().c(var3.et()) && var20.cm()) {
                           var20.a(new S_ChatPacket(var3, var5, var4));
                        }
                     }
                  } else if (var4 == 11) {
                     if (!var3.q()) {
                        return;
                     }

                     for (L1PcInstance var14 : var3.aL().c()) {
                        if (!var14.cd().c(var3.et()) && var14.cn()) {
                           var14.a(new S_ChatPacket(var3, var5, var4));
                        }
                     }
                  } else if (var4 == 12) {
                     this.a(var3, var5, var4);
                  } else if (var4 == 13) {
                     if (var3.aF() == 0) {
                        return;
                     }

                     ArrayList var15 = new ArrayList<>(Arrays.asList(9, 4, 10, 3, 6));
                     L1Clan var22 = ClanTable.a().a(var3.aF());
                     if (!var15.contains(var3.aH())) {
                        return;
                     }

                     for (L1PcInstance var28 : var22.b()) {
                        if (!var28.cd().c(var3.et()) && var15.contains(var28.aH())) {
                           var28.a(new S_ChatPacket(var3, var5, var4));
                        }
                     }
                  } else if (var4 == 14) {
                     if (!var3.r()) {
                        return;
                     }

                     L1PcInstance[] var33;
                     int var29 = (var33 = var3.aM().e()).length;

                     for (int var23 = 0; var23 < var29; var23++) {
                        L1PcInstance var16 = var33[var23];
                        if (!var16.cd().c(var3.et())) {
                           var16.a(new S_ChatPacket(var3, var5, var4));
                        }
                     }
                  } else if (var4 == 15) {
                     if (var3.aF() == 0) {
                        return;
                     }

                     L1Clan var17 = ClanTable.a().a(var3.aF());

                     for (L1PcInstance var24 : var17.b()) {
                        if (!var24.cd().c(var3.et()) && var24.cm()) {
                           var24.a(new S_ChatPacket(var3, var5, var4));
                        }
                     }
                  } else if (var4 == 17) {
                     if (var3.aF() == 0) {
                        return;
                     }

                     if (var3.aH() == 10 || var3.aH() == 4) {
                        L1Clan var18 = ClanTable.a().a(var3.aF());

                        for (L1PcInstance var25 : var18.b()) {
                           var25.a(new S_ChatPacket(var3, var5, var4));
                        }
                     }
                  }
               } else {
                  if (var5.startsWith(".") && var5.length() > 1 && var3.l()) {
                     String var12 = var5.substring(1);
                     GMCommands.a().a(var3, var12);
                     return;
                  }

                  if (var3.bN()) {
                     return;
                  }

                  int var6 = var4 == 2 ? 50 : -1;
                  if (!var3.cd().c(var3.et())) {
                     var3.a(new S_ChatPacket(var3, var5, var4));
                  }

                  for (L1PcInstance var7 : L1World.a().c(var3, var6)) {
                     if (!var7.cd().c(var3.et())) {
                        var7.a(new S_ChatPacket(var3, var5, var4));
                     }
                  }

                  for (L1Object var19 : var3.eq()) {
                     if (var19 instanceof L1MonsterInstance) {
                        L1MonsterInstance var9 = (L1MonsterInstance)var19;
                        if (var9.U_().X() && var9.et().equals(var3.et()) && !var9.eX()) {
                           for (L1PcInstance var10 : L1World.a().c(var9, var6)) {
                              var10.a(new S_NpcChatPacket(var9, var5, var4));
                           }
                        }
                     }
                  }
               }

               if (!var3.l()) {
                  var3.ab();
               }
            }
         }
      }
   }

   private void a(L1PcInstance var1, String var2, int var3) {
      if (var1.l()) {
         L1World.a().a(new S_ChatPacket(var1, var2, var3));
      } else if (var1.ev() < Config.N) {
         var1.a(new S_ServerMessage(195, String.valueOf(Config.N)));
      } else if (!L1World.a().k()) {
         var1.a(new S_ServerMessage(510));
      } else if (var1.fj() < 6) {
         var1.a(new S_ServerMessage(462));
      } else {
         for (L1PcInstance var4 : L1World.a().c()) {
            if (!var4.cd().c(var1.et()) && (var3 != 12 || var4.cl()) && (var3 != 3 || var4.co())) {
               var4.a(new S_ChatPacket(var1, var2, var3));
            }
         }

         var1.c_(var1.fj() - 5);
         var1.a(new S_PacketBox(11, var1.fj()));
      }
   }

   @Override
   public String a() {
      return "C_Chat";
   }
}
