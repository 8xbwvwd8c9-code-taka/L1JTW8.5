package l1r.aj;

import java.util.ArrayList;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.be.S_ClanName;
import l1r.be.S_Extended;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bi.Random;
import l1r.bj.ClientThread;

public class C_Rank extends ClientBasePacket {
   public C_Rank(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         if (var4 == 1) {
            int var5 = this.c();
            String var6 = this.g();
            L1Clan var7 = ClanTable.a().a(var3.aF());
            if (var7 == null) {
               return;
            }

            L1PcInstance var8 = var7.c(var6);
            if (var8 == null) {
               var3.a(new S_ServerMessage(2069));
               return;
            }

            if (var3.aF() != var8.aF()) {
               var3.a(new S_ServerMessage(201, var6));
               return;
            }

            if (var6.equalsIgnoreCase(var3.et())) {
               var3.a(new S_ServerMessage(2068));
               return;
            }

            if (var5 < 2 || var5 > 13) {
               var3.a(new S_ServerMessage(781));
               return;
            }

            int var9 = var3.aH();
            ArrayList var10 = new ArrayList<>();
            switch (var9) {
               case 3:
                  var10.add(6);
                  var10.add(12);
                  var10.add(5);
                  var10.add(2);
                  var10.add(9);
                  var10.add(13);
                  var10.add(8);
                  var10.add(7);
                  break;
               case 4:
                  var10.add(3);
                  var10.add(6);
                  var10.add(12);
                  var10.add(5);
                  var10.add(2);
                  var10.add(9);
                  var10.add(13);
                  var10.add(8);
                  var10.add(7);
                  break;
               case 5:
               case 7:
               case 8:
               default:
                  var3.a(new S_ServerMessage(518));
                  return;
               case 6:
                  var10.add(12);
                  var10.add(5);
                  var10.add(2);
                  var10.add(13);
                  var10.add(8);
                  var10.add(7);
                  break;
               case 9:
                  var10.add(13);
                  var10.add(8);
                  var10.add(7);
                  break;
               case 10:
                  var10.add(9);
                  var10.add(13);
                  var10.add(8);
                  var10.add(7);
            }

            if (!var10.contains(var5) || !var10.contains(var8.aH())) {
               var3.a(new S_ServerMessage(2068));
               return;
            }

            if (var5 == 3) {
               if (!var8.x()) {
                  var3.a(new S_ServerMessage(2064));
                  return;
               }

               if (var8.ev() < 25) {
                  var3.a(new S_ServerMessage(2471));
                  return;
               }
            } else if ((var5 == 9 || var5 == 6) && var8.ev() < 40) {
               var3.a(new S_ServerMessage(2065));
               return;
            }

            if ((var9 == 9 || var9 == 6) && var3.ev() < 40) {
               var3.a(new S_ServerMessage(2473));
               return;
            }

            var8.ai(var5);
            var8.I();
            var8.a(new S_PacketBox(27, var5, var6));
            var8.a(new S_ClanName(var8));
            var3.a(new S_PacketBox(27, var5, var6));
         } else if (var4 != 2 && var4 != 3 && var4 != 4) {
            if (var4 == 5) {
               if (var3.v() == null) {
                  var3.a(new S_ServerMessage(1973));
                  return;
               }

               if (var3.fj() >= 225) {
                  int var12 = 0;
                  int var14 = 8683;
                  int var16 = 829;
                  long var17 = System.currentTimeMillis() / 1000L;
                  int var21 = (int)((var17 - var3.bL()) / 60L);
                  if (var21 <= 0) {
                     var3.a(new S_ServerMessage(1974));
                     return;
                  }

                  if (var21 >= 1 && var21 <= 29) {
                     var12 = (int)(var3.ew() * (var21 / 100.0));
                  } else if (var21 >= 30) {
                     int var11 = var3.v().G();
                     if (var11 <= 6) {
                        var14 = 8684;
                        var16 = 8907;
                        var12 = (int)(var3.ew() * (20.0 + Random.a(20) / 100.0));
                     } else if (var11 == 7 || var11 == 8) {
                        var14 = 8685;
                        var16 = 8909;
                        var12 = (int)(var3.ew() * ((30 + Random.a(20)) / 100.0));
                     } else if (var11 == 9 || var11 == 10) {
                        var14 = 8773;
                        var16 = 8910;
                        var12 = (int)(var3.ew() * ((50 + Random.a(10)) / 100.0));
                     } else if (var11 >= 11) {
                        var14 = 8686;
                        var16 = 8908;
                        var12 = (int)(var3.ew() * 0.7);
                     }
                  }

                  var3.a(new S_SkillSound(var3.fr(), var14));
                  var3.b(new S_SkillSound(var3.fr(), var14));
                  var3.a(new S_SkillSound(var3.fr(), var16));
                  var3.b(new S_SkillSound(var3.fr(), var16));
                  var3.c_(0);
                  var3.a(new S_PacketBox(11, 0));
                  var3.a(var3.ea() + var12);
                  var3.a(new S_Extended(false));
               }
            } else if (var4 == 6) {
               int var13 = 8683;
               long var15 = System.currentTimeMillis() / 1000L;
               int var18 = (int)((var15 - var3.bL()) / 60L);
               if (var3.v() == null) {
                  var3.a(new S_ServerMessage(1973));
                  return;
               }

               if (var18 >= 30) {
                  int var19 = var3.v().G();
                  if (var19 <= 6) {
                     var13 = 8684;
                  } else if (var19 >= 7 && var19 <= 8) {
                     var13 = 8685;
                  } else if (var19 >= 9 && var19 <= 10) {
                     var13 = 8773;
                  } else if (var19 >= 11) {
                     var13 = 8686;
                  }
               }

               S_SkillSound var20 = new S_SkillSound(var3.fr(), var13);
               var3.a(var20);
               var3.b(var20);
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_Rank";
   }
}
