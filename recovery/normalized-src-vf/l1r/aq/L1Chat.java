package l1r.aq;

import java.util.ArrayList;
import java.util.Arrays;
import l1r.ai.GMCommands;
import l1r.ao.ClanTable;
import l1r.ao.HistoryTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_ChatPacket;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.l1j.server.Config;

public class L1Chat {
   public static void a(L1PcInstance var0, int var1, String var2, int var3, String var4) {
      if (!var0.bB(64) && !var0.bB(161) && !var0.bB(1007)) {
         if (var0.bB(1005)) {
            var0.a(new S_ServerMessage(242));
         } else {
            if (var1 != 0 && var1 != 2) {
               if (var1 == 1) {
                  if (var0.bB(1005)) {
                     var0.a(new S_ServerMessage(242));
                     return;
                  }

                  if (var0.ev() < Config.O) {
                     var0.a(new S_ServerMessage(404, String.valueOf(Config.O)));
                     return;
                  }

                  L1PcInstance var12 = L1World.a().a(var4);
                  if (var12 == null) {
                     var0.a(new S_ServerMessage(73, var4));
                     return;
                  }

                  if (var12.equals(var0)) {
                     return;
                  }

                  if (var12.cd().c(var0.et())) {
                     var0.a(new S_ServerMessage(117, var12.et()));
                     return;
                  }

                  if (!var12.ck()) {
                     var0.a(new S_ServerMessage(205, var12.et()));
                     return;
                  }

                  var0.a(new S_ProtoBuffers(var12, var2, var3));
                  var12.a(new S_ProtoBuffers(var0, var2, var1, var3));
                  HistoryTable.a().a(var0, "密語給(" + var12.et() + ")", var2);
               } else if (var1 == 3) {
                  a(var0, var2, var1, var3);
                  HistoryTable.a().a(var0, "全體", var2);
               } else if (var1 == 4) {
                  if (var0.aF() == 0) {
                     return;
                  }

                  L1Clan var13 = ClanTable.a().a(var0.aF());

                  for (L1PcInstance var20 : var13.b()) {
                     if (!var20.cd().c(var0.et()) && var20.cm()) {
                        var20.a(new S_ProtoBuffers(var0, var2, var1, var3));
                     }
                  }

                  HistoryTable.a().a(var0, "血盟", var2);
               } else if (var1 == 11) {
                  if (!var0.q()) {
                     return;
                  }

                  for (L1PcInstance var14 : var0.aL().c()) {
                     if (!var14.cd().c(var0.et()) && var14.cn()) {
                        var14.a(new S_ProtoBuffers(var0, var2, var1, var3));
                     }
                  }

                  HistoryTable.a().a(var0, "組隊", var2);
               } else if (var1 == 12) {
                  a(var0, var2, var1, var3);
                  HistoryTable.a().a(var0, "買賣", var2);
               } else if (var1 == 13) {
                  if (var0.aF() == 0) {
                     return;
                  }

                  ArrayList var15 = new ArrayList<>(Arrays.asList(9, 4, 10, 3, 6));
                  L1Clan var22 = ClanTable.a().a(var0.aF());
                  if (!var15.contains(var0.aH())) {
                     return;
                  }

                  for (L1PcInstance var28 : var22.b()) {
                     if (!var28.cd().c(var0.et()) && var15.contains(var28.aH())) {
                        var28.a(new S_ProtoBuffers(var0, var2, var1, var3));
                     }
                  }

                  HistoryTable.a().a(var0, "聯合血盟", var2);
               } else if (var1 == 14) {
                  if (!var0.r()) {
                     return;
                  }

                  L1PcInstance[] var33;
                  int var29 = (var33 = var0.aM().e()).length;

                  for (int var23 = 0; var23 < var29; var23++) {
                     L1PcInstance var16 = var33[var23];
                     if (!var16.cd().c(var0.et())) {
                        var16.a(new S_ProtoBuffers(var0, var2, var1, var3));
                     }
                  }

                  HistoryTable.a().a(var0, "聊天組隊", var2);
               } else if (var1 == 15) {
                  if (var0.aF() == 0) {
                     return;
                  }

                  L1Clan var17 = ClanTable.a().a(var0.aF());

                  for (L1PcInstance var24 : var17.b()) {
                     if (!var24.cd().c(var0.et()) && var24.cm()) {
                        var24.a(new S_ProtoBuffers(var0, var2, var1, var3));
                     }
                  }

                  HistoryTable.a().a(var0, "同盟", var2);
               } else if (var1 == 17) {
                  if (var0.aF() == 0) {
                     return;
                  }

                  if (var0.aH() == 10 || var0.aH() == 4) {
                     L1Clan var18 = ClanTable.a().a(var0.aF());

                     for (L1PcInstance var25 : var18.b()) {
                        var25.a(new S_ProtoBuffers(var0, var2, var1, var3));
                     }
                  }

                  HistoryTable.a().a(var0, "血盟王族公告", var2);
               }
            } else {
               if (var2.startsWith(".") && var2.length() > 1 && var0.l()) {
                  String var11 = var2.substring(1);
                  GMCommands.a().a(var0, var11);
                  return;
               }

               if (var0.bN()) {
                  return;
               }

               int var5 = var1 == 2 ? 50 : -1;
               if (!var0.cd().c(var0.et())) {
                  var0.a(new S_ProtoBuffers(var0, var2, var1, var3));
               }

               for (L1PcInstance var6 : L1World.a().c(var0, var5)) {
                  if (!var6.cd().c(var0.et())) {
                     var6.a(new S_ProtoBuffers(var0, var2, var1, var3));
                  }
               }

               for (L1Object var19 : var0.eq()) {
                  if (var19 instanceof L1MonsterInstance) {
                     L1MonsterInstance var8 = (L1MonsterInstance)var19;
                     if (var8.U_().X() && var8.et().equals(var0.et()) && !var8.eX()) {
                        for (L1PcInstance var9 : L1World.a().c(var8, var5)) {
                           var9.a(new S_NpcChatPacket(var8, var2, var1));
                        }
                     }
                  }
               }

               HistoryTable.a().a(var0, var1 == 2 ? "大叫" : "一般", var2);
            }

            if (!var0.l()) {
               var0.ab();
            }
         }
      }
   }

   private static void a(L1PcInstance var0, String var1, int var2, int var3) {
      if (var0.l()) {
         L1World.a().a(new S_ChatPacket(var0, var1, var2));
      } else if (var0.ev() < Config.N) {
         var0.a(new S_ServerMessage(195, String.valueOf(Config.N)));
      } else if (!L1World.a().k()) {
         var0.a(new S_ServerMessage(510));
      } else if (var0.fj() < 6) {
         var0.a(new S_ServerMessage(462));
      } else {
         for (L1PcInstance var4 : L1World.a().c()) {
            if (!var4.cd().c(var0.et()) && (var2 != 12 || var4.cl()) && (var2 != 3 || var4.co())) {
               var4.a(new S_ProtoBuffers(var0, var1, var2, var3));
            }
         }

         var0.c_(var0.fj() - 5);
         var0.a(new S_PacketBox(11, var0.fj()));
      }
   }
}
