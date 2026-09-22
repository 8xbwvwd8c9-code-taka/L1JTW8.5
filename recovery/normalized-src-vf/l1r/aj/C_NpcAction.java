package l1r.aj;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.ao.DoorTable;
import l1r.ao.ExpTable;
import l1r.ao.HouseTable;
import l1r.ao.HtmlCraftTable;
import l1r.ao.HtmlTable;
import l1r.ao.HtmlTeleportTable;
import l1r.ao.InnTable;
import l1r.ao.ItemTable;
import l1r.ao.PetTable;
import l1r.ao.SkillsTable;
import l1r.ao.SoulTowerTable;
import l1r.ao.TownTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1HousekeeperInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1Clan;
import l1r.aq.L1HouseLocation;
import l1r.aq.L1ItemQuestBuff;
import l1r.aq.L1Object;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1Teleport;
import l1r.aq.L1TownLocation;
import l1r.aq.L1World;
import l1r.as.L1BugBearRace;
import l1r.as.L1CastleWar;
import l1r.as.L1CentralTemple;
import l1r.as.L1HardinBattle;
import l1r.as.L1IceQueen;
import l1r.as.L1OrimBattle;
import l1r.as.L1SoulStone;
import l1r.as.L1SoulTower;
import l1r.as.L1ValakasLair;
import l1r.ba.HomeTownTimer;
import l1r.be.S_AddSkill;
import l1r.be.S_ApplyAuction;
import l1r.be.S_ArcharArrange;
import l1r.be.S_AuctionBoardRead;
import l1r.be.S_CharEvent;
import l1r.be.S_CharReset;
import l1r.be.S_Deposit;
import l1r.be.S_Drawal;
import l1r.be.S_HouseMap;
import l1r.be.S_HowManyKey;
import l1r.be.S_Html;
import l1r.be.S_ItemName;
import l1r.be.S_MercenaryArrange;
import l1r.be.S_MercenaryEmpoly;
import l1r.be.S_Message_YN;
import l1r.be.S_PacketBox;
import l1r.be.S_PetCtrlMenu;
import l1r.be.S_PetList;
import l1r.be.S_PledgeWarehouseHistory;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_RetrieveList;
import l1r.be.S_RetrieveListChar;
import l1r.be.S_RetrieveListClan;
import l1r.be.S_RetrieveListElven;
import l1r.be.S_RuneSlot;
import l1r.be.S_SelectTarget;
import l1r.be.S_SellHouse;
import l1r.be.S_ServerMessage;
import l1r.be.S_ShopBuyList;
import l1r.be.S_ShopSellList;
import l1r.be.S_SkillHaste;
import l1r.be.S_SkillIconAura;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;
import l1r.be.S_TaxRate;
import l1r.bf.L1SkillExecutor;
import l1r.bf.S_012;
import l1r.bf.S_021;
import l1r.bf.S_044;
import l1r.bf.S_054;
import l1r.bh.L1Castle;
import l1r.bh.L1House;
import l1r.bh.L1Item;
import l1r.bh.L1Skills;
import l1r.bh.L1Town;
import l1r.bi.LineageUtil;
import l1r.bi.Random;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_NpcAction extends ClientBasePacket {
   public C_NpcAction(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         String var5 = this.g();
         int[] var6 = null;
         int[] var7 = null;
         int[] var8 = null;
         int[] var9 = null;
         String var10 = null;
         String var11 = null;
         String var12 = null;
         String[] var13 = null;
         int var14 = 0;
         L1Object var15 = L1World.a().a(var4);
         if (var15 == null) {
            System.out.println("object not found: " + var5);
         } else if (var15 instanceof L1PcInstance) {
            L1PcInstance var34 = (L1PcInstance)var15;
            if (var34.cy()) {
               L1PolyMorph.a(var3, var5, 7200);
               var34.t(false);
            }
         } else if (var15 instanceof L1NpcInstance) {
            L1NpcInstance var16 = (L1NpcInstance)var15;
            int var17 = var16.z();
            if (var15 instanceof L1PetInstance || var15 instanceof L1SummonInstance || var16.fu().c(var3.fu()) <= 11) {
               var16.a(var3, var5);
               if (!HtmlTable.a().a(var5, var3, var16)) {
                  if (!HtmlTeleportTable.a().a(var5, var3, var16)) {
                     if (!HtmlCraftTable.a().a(var5, var3, var16)) {
                        if (var5.equalsIgnoreCase("buy")) {
                           if (var16.z() == 70027 || var16.z() == 70023) {
                              return;
                           }

                           if (var17 == 190005 || var17 == 190045 || var17 == 190095 || var17 == 190353) {
                              var3.a(new S_ShopBuyList(var16, S_ShopBuyList.b));
                              return;
                           }

                           if (var17 == 190142 || var17 == 190488) {
                              var3.a(new S_ShopBuyList(var16, var3, S_ShopBuyList.d));
                              return;
                           }

                           if (var17 == 70035 || var17 == 70041 || var17 == 70042) {
                              var3.a(new S_ShopBuyList(var16, S_ShopBuyList.c));
                              return;
                           }

                           var3.a(new S_ShopBuyList(var16, S_ShopBuyList.a));
                        } else if (var5.equalsIgnoreCase("sell")) {
                           if (var15 instanceof L1HousekeeperInstance) {
                              var10 = this.a(var3, var4, var17);
                           } else {
                              var3.a(new S_ShopSellList(var16, var3));
                           }
                        } else if (var5.equalsIgnoreCase("retrieve")) {
                           if (var3.ev() >= 5) {
                              if (var2.e().m() > 0) {
                                 var3.a(new S_ServerMessage(834));
                              } else {
                                 var3.a(new S_RetrieveList(var4, var3));
                              }
                           }
                        } else if (var5.equalsIgnoreCase("retrieve-elven")) {
                           if (var3.ev() >= 5 && var3.A() && var3.A() && var3.ev() > 4) {
                              if (var2.e().m() > 0) {
                                 var3.a(new S_ServerMessage(834));
                              } else {
                                 var3.a(new S_RetrieveListElven(var4, var3));
                              }
                           }
                        } else if (var5.equalsIgnoreCase("retrieve-char")) {
                           if (var3.ev() >= 5) {
                              if (var2.e().m() > 0) {
                                 var3.a(new S_ServerMessage(834));
                              } else {
                                 var3.a(new S_RetrieveListChar(var4, var3));
                              }
                           }
                        } else if (var5.equalsIgnoreCase("retrieve-pledge")) {
                           if (var3.ev() >= 5) {
                              if (var3.aF() == 0) {
                                 var3.a(new S_ServerMessage(208));
                                 return;
                              }

                              int var105 = var3.aH();
                              if (!L1Clan.a(var105)) {
                                 var3.a(new S_ServerMessage(728));
                                 return;
                              }

                              if (var2.e().m() > 0) {
                                 var3.a(new S_ServerMessage(834));
                              } else {
                                 var3.a(new S_RetrieveListClan(var4, var3));
                              }
                           }
                        } else if (var5.equalsIgnoreCase("history")) {
                           var3.a(new S_PledgeWarehouseHistory(var3.aF()));
                        } else if (var5.equalsIgnoreCase("get")) {
                           if (var17 == 70099 || var17 == 70796) {
                              ItemTable.a(var3, 20081, 1, var16.T());
                              var3.bb().b(11);
                              var10 = "";
                           } else if (var17 == 70528
                              || var17 == 70546
                              || var17 == 70567
                              || var17 == 70594
                              || var17 == 70654
                              || var17 == 70748
                              || var17 == 70774
                              || var17 == 70799
                              || var17 == 70815
                              || var17 == 70860) {
                              int var104 = var3.bF();
                              int var152 = var3.bH();
                              int var186 = var3.bG();
                              var10 = "";
                              if (var152 < 1) {
                                 var3.a(new S_ServerMessage(767));
                              } else if (var152 > 0 && var186 < 500) {
                                 var3.a(new S_ServerMessage(766));
                              } else if (var104 > 0) {
                                 double var206 = 1.0;
                                 boolean var224 = TownTable.a().a(var3, var104);
                                 if (var186 > 999 && var186 < 1500) {
                                    var206 = 1.5;
                                 } else if (var186 > 1499 && var186 < 2000) {
                                    var206 = 2.0;
                                 } else if (var186 > 1999 && var186 < 2500) {
                                    var206 = 2.5;
                                 } else if (var186 > 2499 && var186 < 3000) {
                                    var206 = 3.0;
                                 } else if (var186 > 2999) {
                                    var206 = 4.0;
                                 }

                                 if (var224) {
                                    var206++;
                                 }

                                 long var227 = var3.j().g(40308);
                                 if (var227 + var152 * var206 > 2.0E9) {
                                    var3.a(new S_SystemMessage("\\aG所持有的金幣將會超過2000000000上限"));
                                    var10 = "";
                                 } else {
                                    var152 = (int)(HomeTownTimer.a(var3.fr()) * var206);
                                    ItemTable.a(var3, 40308, var152, 0, false);
                                    var3.a(new S_ServerMessage(761, "" + var152));
                                    var3.aF(0);
                                 }
                              }
                           }
                        } else if (var5.equalsIgnoreCase("townscore")) {
                           if ((
                                 var17 == 70528
                                    || var17 == 70546
                                    || var17 == 70567
                                    || var17 == 70594
                                    || var17 == 70654
                                    || var17 == 70748
                                    || var17 == 70774
                                    || var17 == 70799
                                    || var17 == 70815
                                    || var17 == 70860
                              )
                              && var3.bF() > 0) {
                              var3.a(new S_ServerMessage(1569, String.valueOf(var3.bG())));
                           }
                        } else if (!var5.equalsIgnoreCase("fix")) {
                           if (var5.equalsIgnoreCase("openigate")) {
                              this.a(var3, var17, true);
                              var10 = "";
                           } else if (var5.equalsIgnoreCase("closeigate")) {
                              this.a(var3, var17, false);
                              var10 = "";
                           } else if (var5.equalsIgnoreCase("askwartime")) {
                              if (var17 == 60514) {
                                 var13 = this.d(1);
                                 var10 = "ktguard7";
                              } else if (var17 == 60560) {
                                 var13 = this.d(2);
                                 var10 = "orcguard7";
                              } else if (var17 == 60552) {
                                 var13 = this.d(3);
                                 var10 = "wdguard7";
                              } else if (var17 == 60524 || var17 == 60525 || var17 == 60529) {
                                 var13 = this.d(4);
                                 var10 = "grguard7";
                              } else if (var17 == 70857) {
                                 var13 = this.d(5);
                                 var10 = "heguard7";
                              } else if (var17 == 60530 || var17 == 60531) {
                                 var13 = this.d(6);
                                 var10 = "dcguard7";
                              } else if (var17 == 60533 || var17 == 60534) {
                                 var13 = this.d(7);
                                 var10 = "adguard7";
                              } else if (var17 == 81156) {
                                 var13 = this.d(8);
                                 var10 = "dfguard3";
                              }
                           } else if (var5.equalsIgnoreCase("inex")) {
                              L1Clan var103 = ClanTable.a().a(var3.aF());
                              if (var103 != null) {
                                 int var151 = var103.m();
                                 if (var151 != 0) {
                                    L1Castle var185 = CastleTable.a().a(var151);
                                    var3.a(new S_ServerMessage(309, var185.b(), String.valueOf(var185.f())));
                                    var10 = "";
                                 }
                              }
                           } else if (var5.equalsIgnoreCase("tax")) {
                              var3.a(new S_TaxRate(var3.fr()));
                           } else if (var5.equalsIgnoreCase("withdrawal")) {
                              L1Clan var102 = ClanTable.a().a(var3.aF());
                              if (var102 != null) {
                                 int var150 = var102.m();
                                 if (var150 != 0) {
                                    L1Castle var184 = CastleTable.a().a(var150);
                                    var3.a(new S_Drawal(var3.fr(), var184.f()));
                                 }
                              }
                           } else if (var5.equalsIgnoreCase("cdeposit")) {
                              var3.a(new S_Deposit(var3.fr()));
                           } else if (var5.equalsIgnoreCase("employ")) {
                              L1Clan var101 = ClanTable.a().a(var3.aF());
                              if (var101 == null) {
                                 return;
                              }

                              int var149 = var101.m();
                              if (var149 > 0) {
                                 L1Castle var183 = CastleTable.a().a(var149);
                                 if (var3.fr() == var183.h()) {
                                    var3.a(new S_MercenaryEmpoly(var183));
                                 }
                              }
                           } else if (var5.equalsIgnoreCase("arrange")) {
                              L1Clan var100 = ClanTable.a().a(var3.aF());
                              if (var100 == null) {
                                 return;
                              }

                              int var148 = var100.m();
                              if (var148 == 0) {
                                 return;
                              }

                              L1Castle var182 = CastleTable.a().a(var148);
                              if (var182.i() > 0) {
                                 var13 = new String[]{var100.f(), "" + var182.i()};
                                 var10 = "fisher15";
                              } else {
                                 var10 = "fisher9";
                              }
                           } else if (var5.equalsIgnoreCase("archer")) {
                              L1Clan var99 = ClanTable.a().a(var3.aF());
                              if (var99 == null) {
                                 return;
                              }

                              int var147 = var99.m();
                              if (var147 > 0) {
                                 L1Castle var181 = CastleTable.a().a(var147);
                                 if (var3.fr() == var181.h()) {
                                    var3.a(new S_ArcharArrange(var181));
                                 }
                              }
                           } else if (var5.equalsIgnoreCase("castlegate")) {
                              this.a(var3);
                              var10 = "";
                           } else if (var5.equalsIgnoreCase("demand")) {
                              L1Clan var98 = ClanTable.a().a(var3.aF());
                              if (var98 == null) {
                                 return;
                              }

                              int var146 = var98.m();
                              if (var146 > 0) {
                                 L1Castle var180 = CastleTable.a().a(var146);
                                 var3.a(new S_MercenaryArrange(var3, var180));
                              }
                           } else if (var5.equalsIgnoreCase("encw")) {
                              new S_012().a(var3, 0);
                              var10 = "";
                           } else if (var5.equalsIgnoreCase("enca")) {
                              new S_021().a(var3, 0);
                              var10 = "";
                           } else if (var5.equalsIgnoreCase("depositnpc")) {
                              for (L1NpcInstance var97 : var3.ek().values()) {
                                 if (var97 instanceof L1PetInstance) {
                                    L1PetInstance var179 = (L1PetInstance)var97;
                                    var3.a(new S_PetCtrlMenu(var3, var97, false));
                                    var179.ax();
                                    var179.b(true);
                                    var3.ek().remove(var179.fr());
                                    var179.aa_();
                                 }
                              }

                              var10 = "";
                           } else if (var5.equalsIgnoreCase("withdrawnpc")) {
                              var3.a(new S_PetList(var4, var3));
                           } else if (var5.equalsIgnoreCase("aggressive")) {
                              if (var15 instanceof L1PetInstance) {
                                 L1PetInstance var96 = (L1PetInstance)var15;
                                 var96.e(1);
                              }
                           } else if (var5.equalsIgnoreCase("defensive")) {
                              if (var15 instanceof L1PetInstance) {
                                 L1PetInstance var95 = (L1PetInstance)var15;
                                 var95.e(2);
                              }
                           } else if (var5.equalsIgnoreCase("stay")) {
                              if (var15 instanceof L1PetInstance) {
                                 L1PetInstance var94 = (L1PetInstance)var15;
                                 var94.e(3);
                              }
                           } else if (var5.equalsIgnoreCase("extend")) {
                              if (var15 instanceof L1PetInstance) {
                                 L1PetInstance var93 = (L1PetInstance)var15;
                                 var93.e(4);
                              }
                           } else if (var5.equalsIgnoreCase("alert")) {
                              if (var15 instanceof L1PetInstance) {
                                 L1PetInstance var92 = (L1PetInstance)var15;
                                 var92.e(5);
                              }
                           } else if (var5.equalsIgnoreCase("dismiss")) {
                              if (var15 instanceof L1PetInstance) {
                                 L1PetInstance var91 = (L1PetInstance)var15;
                                 var91.e(6);
                              }
                           } else if (var5.equalsIgnoreCase("changename")) {
                              var3.am(var4);
                              var3.a(new S_Message_YN(325));
                           } else if (var5.equalsIgnoreCase("attackchr")) {
                              var3.a(new S_SelectTarget(var16.fr()));
                           } else if (var5.equalsIgnoreCase("select")) {
                              String var90 = this.g();
                              var3.a(new S_AuctionBoardRead(var4, var90));
                           } else if (var5.equalsIgnoreCase("map")) {
                              String var89 = this.g();
                              var3.a(new S_HouseMap(var4, var89));
                           } else if (var5.equalsIgnoreCase("apply")) {
                              String var88 = this.g();
                              L1Clan var144 = ClanTable.a().a(var3.aF());
                              if (var144 != null) {
                                 if (!var3.x() || var3.fr() != var144.k()) {
                                    var3.a(new S_ServerMessage(518));
                                    var10 = "";
                                 } else if (var3.ev() >= 15) {
                                    if (var144.n() == 0) {
                                       var3.setL1rAmountContext(var4, 1);
                                       var3.a(new S_ApplyAuction(var4, var88));
                                    } else {
                                       var3.a(new S_ServerMessage(521));
                                       var10 = "";
                                    }
                                 } else {
                                    var3.a(new S_ServerMessage(519));
                                    var10 = "";
                                 }
                              } else {
                                 var3.a(new S_ServerMessage(518));
                                 var10 = "";
                              }
                           } else if (var5.equalsIgnoreCase("open") || var5.equalsIgnoreCase("close")) {
                              this.a(var3, var16, var5);
                              var10 = "";
                           } else if (var5.equalsIgnoreCase("expel")) {
                              this.a(var3, var17);
                              var10 = "";
                           } else if (var5.equalsIgnoreCase("pay")) {
                              var13 = this.b(var3, var16);
                              var10 = "agpay";
                           } else if (var5.equalsIgnoreCase("payfee")) {
                              var13 = new String[]{var16.U_().c(), "2000"};
                              var10 = "";
                              if (this.a(var3, var16)) {
                                 var10 = "agpayfee";
                              }
                           } else if (var5.equalsIgnoreCase("name")) {
                              L1Clan var87 = ClanTable.a().a(var3.aF());
                              if (var87 != null) {
                                 int var143 = var87.n();
                                 if (var143 != 0) {
                                    L1House var178 = HouseTable.a().a(var143);
                                    int var205 = var178.f();
                                    if (var17 == var205) {
                                       var3.am(var143);
                                       var3.a(new S_Message_YN(512, ""));
                                    }
                                 }
                              }

                              var10 = "";
                           } else if (!var5.equalsIgnoreCase("rem")) {
                              if (var5.equalsIgnoreCase("tel0")
                                 || var5.equalsIgnoreCase("tel1")
                                 || var5.equalsIgnoreCase("tel2")
                                 || var5.equalsIgnoreCase("tel3")) {
                                 L1Clan var86 = ClanTable.a().a(var3.aF());
                                 if (var86 != null) {
                                    int var142 = var86.n();
                                    if (var142 != 0) {
                                       L1House var177 = HouseTable.a().a(var142);
                                       int var204 = var177.f();
                                       if (var17 == var204) {
                                          int[] var217 = new int[3];
                                          if (var5.equalsIgnoreCase("tel0")) {
                                             var217 = L1HouseLocation.a(var142, 0);
                                          } else if (var5.equalsIgnoreCase("tel1")) {
                                             var217 = L1HouseLocation.a(var142, 1);
                                          } else if (var5.equalsIgnoreCase("tel2")) {
                                             var217 = L1HouseLocation.a(var142, 2);
                                          } else if (var5.equalsIgnoreCase("tel3")) {
                                             var217 = L1HouseLocation.a(var142, 3);
                                          }

                                          L1Teleport.a(var3, var217[0], var217[1], var217[2], 5, true);
                                       }
                                    }
                                 }

                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("upgrade")) {
                                 L1Clan var85 = ClanTable.a().a(var3.aF());
                                 if (var85 != null) {
                                    int var141 = var85.n();
                                    if (var141 != 0) {
                                       L1House var176 = HouseTable.a().a(var141);
                                       int var203 = var176.f();
                                       if (var17 == var203) {
                                          if (!var3.x() || var3.fr() != var85.k()) {
                                             var3.a(new S_ServerMessage(518));
                                          } else if (var176.h()) {
                                             var3.a(new S_ServerMessage(1135));
                                          } else if (var3.j().b(40308, 5000000)) {
                                             var176.b(true);
                                             HouseTable.a().a(var176);
                                             var3.a(new S_ServerMessage(1099));
                                          } else {
                                             var3.a(new S_ServerMessage(189));
                                          }
                                       }
                                    }
                                 }

                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("hall") && var15 instanceof L1HousekeeperInstance) {
                                 L1Clan var84 = ClanTable.a().a(var3.aF());
                                 if (var84 != null) {
                                    int var140 = var84.n();
                                    if (var140 != 0) {
                                       L1House var175 = HouseTable.a().a(var140);
                                       int var202 = var175.f();
                                       if (var17 == var202) {
                                          if (var175.h()) {
                                             int[] var216 = L1HouseLocation.b(var140);
                                             L1Teleport.a(var3, var216[0], var216[1], var216[2], 5, true);
                                          } else {
                                             var3.a(new S_ServerMessage(1098));
                                          }
                                       }
                                    }
                                 }

                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("fire")) {
                                 if (var3.A()) {
                                    if (var3.bC() != 0) {
                                       var3.a(new S_ServerMessage(684));
                                       return;
                                    }

                                    var3.aA(2);
                                    var3.I();
                                    var3.a(new S_PacketBox(15, 1));
                                    var3.a(new S_AddSkill(var3, var3.at()));
                                    var10 = "";
                                 }
                              } else if (var5.equalsIgnoreCase("water")) {
                                 if (var3.A()) {
                                    if (var3.bC() != 0) {
                                       var3.a(new S_ServerMessage(684));
                                       return;
                                    }

                                    var3.aA(4);
                                    var3.I();
                                    var3.a(new S_PacketBox(15, 2));
                                    var3.a(new S_AddSkill(var3, var3.at()));
                                    var10 = "";
                                 }
                              } else if (var5.equalsIgnoreCase("air")) {
                                 if (var3.A()) {
                                    if (var3.bC() != 0) {
                                       var3.a(new S_ServerMessage(684));
                                       return;
                                    }

                                    var3.aA(8);
                                    var3.I();
                                    var3.a(new S_PacketBox(15, 3));
                                    var3.a(new S_AddSkill(var3, var3.at()));
                                    var10 = "";
                                 }
                              } else if (var5.equalsIgnoreCase("earth")) {
                                 if (var3.A()) {
                                    if (var3.bC() != 0) {
                                       var3.a(new S_ServerMessage(684));
                                       return;
                                    }

                                    var3.aA(1);
                                    var3.I();
                                    var3.a(new S_PacketBox(15, 4));
                                    var3.a(new S_AddSkill(var3, var3.at()));
                                    var10 = "";
                                 }
                              } else if (var5.equalsIgnoreCase("init")) {
                                 if (var3.A()) {
                                    if (var3.bC() == 0) {
                                       var3.a(new S_ServerMessage(79));
                                       return;
                                    }

                                    if (var3.bB(147)) {
                                       var3.bz(147);
                                    }

                                    var3.aA(0);
                                    var3.I();
                                    var3.a(new S_ServerMessage(678));
                                    var3.a(new S_AddSkill(var3, var3.at()));
                                    var10 = "";
                                 }
                              } else if (var5.equalsIgnoreCase("exp")) {
                                 if (var3.ca() >= 1) {
                                    int var82 = 0;
                                    int var139 = var3.ev();
                                    int var174 = var3.fa();
                                    if (var139 < 45) {
                                       var82 = var139 * var139 * 100;
                                    } else {
                                       var82 = var139 * var139 * 200;
                                    }

                                    if (var174 >= 0) {
                                       var82 /= 2;
                                    }

                                    var3.a(new S_Message_YN(738, String.valueOf(var82)));
                                 } else {
                                    var3.a(new S_ServerMessage(739));
                                    var10 = "";
                                 }
                              } else if (var5.equalsIgnoreCase("pk")) {
                                 if (var3.fa() < 30000) {
                                    var3.a(new S_ServerMessage(559));
                                 } else if (var3.aD() < 5) {
                                    var3.a(new S_ServerMessage(560));
                                 } else if (var3.j().b(40308, 700000)) {
                                    var3.af(var3.aD() - 5);
                                    var3.a(new S_ServerMessage(561, String.valueOf(var3.aD())));
                                 } else {
                                    var3.a(new S_ServerMessage(189));
                                 }

                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("ent")) {
                                 if (var17 == 71251) {
                                    if (!var3.j().f(49142)) {
                                       var3.a(new S_ServerMessage(1290));
                                       return;
                                    }

                                    new S_044().a(var3, 1);
                                    var3.j().j();
                                    L1Teleport.a(var3, 32737, 32789, 997, 4, true);
                                    int var81 = 75 + var3.bB();
                                    int var138 = var3.bf() + var3.bj() + var3.bk() + var3.bh() + var3.bg() + var3.bi();
                                    if (var3.ev() > 50) {
                                       var138 += var3.ev() - 50 - var3.bA();
                                    }

                                    int var173 = var138 - var81;
                                    int var200 = 1;
                                    if (var173 > 0) {
                                       var200 = Math.min(50 + var173, 99);
                                    } else {
                                       var200 = var3.ev();
                                    }

                                    var3.aT(var200);
                                    var3.aS(1);
                                    var3.s(true);
                                    var3.a(new S_CharReset(var3));
                                 }
                              } else if (var5.equalsIgnoreCase("haste")) {
                                 if (var17 == 70514) {
                                    var3.a(new S_ServerMessage(183));
                                    var3.a(new S_SkillHaste(var3.fr(), 1, 1600));
                                    var3.b(new S_SkillHaste(var3.fr(), 1, 0));
                                    var3.a(new S_SkillSound(var3.fr(), 755));
                                    var3.b(new S_SkillSound(var3.fr(), 755));
                                    var3.cu(1);
                                    var3.j(1001, 1600000);
                                    var10 = "";
                                 }
                              } else if (var5.equalsIgnoreCase("skeleton nbmorph")) {
                                 this.a(var2, 2374);
                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("lycanthrope nbmorph")) {
                                 this.a(var2, 3874);
                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("shelob nbmorph")) {
                                 this.a(var2, 95);
                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("ghoul nbmorph")) {
                                 this.a(var2, 3873);
                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("ghast nbmorph")) {
                                 this.a(var2, 3875);
                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("atuba orc nbmorph")) {
                                 this.a(var2, 3868);
                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("skeleton axeman nbmorph")) {
                                 this.a(var2, 2376);
                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("troll nbmorph")) {
                                 this.a(var2, 3878);
                                 var10 = "";
                              } else if (var5.equalsIgnoreCase("teleport mutant-dungen")) {
                                 for (L1PcInstance var80 : L1World.a().c(var3, 3)) {
                                    if (var80.aF() == var3.aF() && var80.fr() != var3.fr()) {
                                       L1Teleport.a(var80, 32740, 32800, 217, 5, true);
                                    }
                                 }

                                 L1Teleport.a(var3, 32740, 32800, 217, 5, true);
                              } else if (var5.equalsIgnoreCase("teleport mage-quest-dungen")) {
                                 L1Teleport.a(var3, 32791, 32788, 201, 5, true);
                              } else if (var5.equalsIgnoreCase("request blood of evil")) {
                                 if (var3.aF() == 0) {
                                    var3.a(new S_ServerMessage(2498));
                                    return;
                                 }

                                 L1Clan var79 = ClanTable.a().a(var3.aF());
                                 if (var79 == null || var3.fr() != var79.k()) {
                                    var3.a(new S_ServerMessage(2498));
                                    return;
                                 }

                                 if (var3.aH() == 4) {
                                    var3.a(new S_ServerMessage(3258));
                                    return;
                                 }

                                 if (var3.ev() < 45) {
                                    var3.a(new S_ServerMessage(2738));
                                    return;
                                 }

                                 if (!var3.j().b(40308, 50000)) {
                                    var3.a(new S_ServerMessage(189));
                                    return;
                                 }

                                 var3.ai(4);
                                 var3.a(new S_PacketBox(27, 4, var3.et()));
                                 var3.I();
                              } else if (var17 == 81279) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    L1ItemQuestBuff.a(var3, 4056, 2400, null);
                                    var3.a(new S_SkillSound(var3.fr(), 7681));
                                    var3.b(new S_SkillSound(var3.fr(), 7681));
                                    var3.a(new S_SkillSound(var3.fr(), 7783));
                                    var3.b(new S_SkillSound(var3.fr(), 7783));
                                    var10 = "grayknight2";
                                 }
                              } else if (var17 == 81292) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    L1ItemQuestBuff.a(var3, 4057, 2400, null);
                                    var3.a(new S_SkillSound(var3.fr(), 7680));
                                    var3.b(new S_SkillSound(var3.fr(), 7680));
                                    var3.a(new S_SkillSound(var3.fr(), 7852));
                                    var3.b(new S_SkillSound(var3.fr(), 7852));
                                    var10 = "";
                                 }
                              } else if (var17 == 81407) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    L1ItemQuestBuff.a(var3, 4079, 2400, null);
                                    var3.a(new S_SkillSound(var3.fr(), 7683));
                                    var3.b(new S_SkillSound(var3.fr(), 7683));
                                    var3.a(new S_SkillSound(var3.fr(), 7853));
                                    var3.b(new S_SkillSound(var3.fr(), 7853));
                                    var10 = "";
                                 }
                              } else if (var17 == 71038) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    ItemTable.a(var3, 41060, 1, 0, var16.T());
                                    var10 = "orcfnoname9";
                                 }
                              } else if (var17 == 71040) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    ItemTable.a(var3, 41065, 1, 0, var16.T());
                                    var10 = "orcfnoa4";
                                 }
                              } else if (var17 == 71041) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    ItemTable.a(var3, 41064, 1, 0, var16.T());
                                    var10 = "orcfhuwoomo4";
                                 }
                              } else if (var17 == 71042) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    ItemTable.a(var3, 41062, 1, 0, var16.T());
                                    var10 = "orcfbakumo4";
                                 }
                              } else if (var17 == 71043) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    ItemTable.a(var3, 41063, 1, 0, var16.T());
                                    var10 = "orcfbuka4";
                                 }
                              } else if (var17 == 71044) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    ItemTable.a(var3, 41061, 1, 0, var16.T());
                                    var10 = "orcfkame4";
                                 }
                              } else if (var17 == 80049) {
                                 if (var5.equalsIgnoreCase("1") && var3.P() <= -10000000) {
                                    var3.A(1000000);
                                    var3.a(new S_ServerMessage(1078));
                                    var10 = "betray13";
                                 }
                              } else if (var17 == 80050) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    if (var3.j().b(40718, 1)) {
                                       var3.B((int)(-100.0 * Config.D));
                                       var3.a(new S_ServerMessage(1079));
                                       var10 = "meet107";
                                    } else {
                                       var10 = "meet104";
                                    }
                                 } else if (var5.equalsIgnoreCase("b")) {
                                    if (var3.j().b(40718, 10)) {
                                       var3.B((int)(-1000.0 * Config.D));
                                       var3.a(new S_ServerMessage(1079));
                                       var10 = "meet108";
                                    } else {
                                       var10 = "meet104";
                                    }
                                 } else if (var5.equalsIgnoreCase("c")) {
                                    if (var3.j().b(40718, 100)) {
                                       var3.B((int)(-10000.0 * Config.D));
                                       var3.a(new S_ServerMessage(1079));
                                       var10 = "meet109";
                                    } else {
                                       var10 = "meet104";
                                    }
                                 } else if (var5.equalsIgnoreCase("d")) {
                                    if (!var3.j().f(40615) && !var3.j().f(40616)) {
                                       L1Teleport.a(var3, 32683, 32895, 608, 5, true);
                                    } else {
                                       var10 = "";
                                    }
                                 }
                              } else if (var17 == 80052) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    if (var3.bB(5015)) {
                                       var3.bA(5015);
                                    }

                                    var3.a(new S_SkillSound(var3.fr(), 750));
                                    var3.b(new S_SkillSound(var3.fr(), 750));
                                    var3.a(new S_SkillIconAura(221, 1020, 2));
                                    var3.j(5015, 1020000);
                                    var3.a(new S_ServerMessage(1127));
                                    var10 = "";
                                 }
                              } else if (var17 == 80053) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    int var78 = 0;
                                    int var136 = 0;
                                    int[] var172 = null;
                                    int[] var199 = null;
                                    int var215 = 0;
                                    String var223 = null;
                                    String var226 = null;
                                    int[] var229 = new int[]{40991, 196, 197, 198, 199, 200, 201, 202};
                                    int[] var231 = new int[]{-1, -2, -3, -4, -5, -6, -7, -8};
                                    int[][] var233 = new int[][]{
                                       {40995, 40718, 40991},
                                       {40997, 40718, 196},
                                       {40990, 40718, 197},
                                       {40994, 40718, 198},
                                       {40993, 40718, 199},
                                       {40998, 40718, 200},
                                       {40996, 40718, 201},
                                       {40992, 40718, 202}
                                    };
                                    int[][] var234 = new int[][]{
                                       {100, 100, 1}, {100, 100, 1}, {100, 100, 1}, {50, 100, 1}, {50, 100, 1}, {50, 100, 1}, {10, 100, 1}, {10, 100, 1}
                                    };
                                    int[] var235 = new int[]{196, 197, 198, 199, 200, 201, 202, 203};
                                    String[] var236 = new String[]{"alice_1", "alice_2", "alice_3", "alice_4", "alice_5", "alice_6", "alice_7", "alice_8"};
                                    String[] var237 = new String[]{"aliceyet", "alice_1", "alice_2", "alice_3", "alice_4", "alice_5", "alice_5", "alice_7"};

                                    for (int var238 = 0; var238 < var229.length; var238++) {
                                       if (var3.j().f(var229[var238])) {
                                          var78 = var229[var238];
                                          var136 = var231[var238];
                                          var172 = var233[var238];
                                          var199 = var234[var238];
                                          var215 = var235[var238];
                                          var223 = var236[var238];
                                          var226 = var237[var238];
                                          break;
                                       }
                                    }

                                    if (var78 == 0) {
                                       var10 = "alice_no";
                                    } else if (var78 == 203) {
                                       var10 = "alice_8";
                                    } else if (var3.Q() <= var136) {
                                       var6 = var172;
                                       var7 = var199;
                                       var8 = new int[]{var215};
                                       var9 = new int[]{1};
                                       var11 = var223;
                                       var12 = "alice_no";
                                    } else {
                                       var10 = var226;
                                    }
                                 }
                              } else if (var17 == 80055) {
                                 this.b(var3, var16, var5);
                                 var10 = "";
                              } else if (var17 == 80056) {
                                 if (var3.P() <= -10000000) {
                                    this.e(var3, var16, var5);
                                 }

                                 var10 = "";
                              } else if (var17 == 80064) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    if (var3.j().b(40678, 1)) {
                                       var3.B((int)(100.0 * Config.D));
                                       var3.a(new S_ServerMessage(1078));
                                       var10 = "meet007";
                                    } else {
                                       var10 = "meet004";
                                    }
                                 } else if (var5.equalsIgnoreCase("b")) {
                                    if (var3.j().b(40678, 10)) {
                                       var3.B((int)(1000.0 * Config.D));
                                       var3.a(new S_ServerMessage(1078));
                                       var10 = "meet008";
                                    } else {
                                       var10 = "meet004";
                                    }
                                 } else if (var5.equalsIgnoreCase("c")) {
                                    if (var3.j().b(40678, 100)) {
                                       var3.B((int)(10000.0 * Config.D));
                                       var3.a(new S_ServerMessage(1078));
                                       var10 = "meet009";
                                    } else {
                                       var10 = "meet004";
                                    }
                                 } else if (var5.equalsIgnoreCase("d")) {
                                    if (!var3.j().f(40909)
                                       && !var3.j().f(40910)
                                       && !var3.j().f(40911)
                                       && !var3.j().f(40912)
                                       && !var3.j().f(40913)
                                       && !var3.j().f(40914)
                                       && !var3.j().f(40915)
                                       && !var3.j().f(40916)
                                       && !var3.j().f(40917)
                                       && !var3.j().f(40918)
                                       && !var3.j().f(40919)
                                       && !var3.j().f(40920)
                                       && !var3.j().f(40921)) {
                                       L1Teleport.a(var3, 32674, 32832, 602, 2, true);
                                    } else {
                                       var10 = "";
                                    }
                                 }
                              } else if (var17 == 80066) {
                                 if (var5.equalsIgnoreCase("1") && var3.P() >= 10000000) {
                                    var3.A(-1000000);
                                    var3.a(new S_ServerMessage(1079));
                                    var10 = "betray03";
                                 }
                              } else if (var17 == 80071) {
                                 this.c(var3, var16, var5);
                                 var10 = "";
                              } else if (var17 == 80073) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    if (var3.bB(5014)) {
                                       var3.bA(5014);
                                    }

                                    var3.a(new S_SkillSound(var3.fr(), 750));
                                    var3.b(new S_SkillSound(var3.fr(), 750));
                                    var3.a(new S_SkillIconAura(221, 1020, 1));
                                    var3.j(5014, 1020000);
                                    var3.a(new S_ServerMessage(1127));
                                    var10 = "";
                                 }
                              } else if (var17 == 80072) {
                                 String var77 = null;
                                 int var135 = 0;
                                 int[] var171 = null;
                                 int[] var198 = null;
                                 int var214 = 0;
                                 String var222 = null;
                                 String var225 = null;
                                 String[] var228 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "a", "b", "c", "d", "e", "f", "g", "h"};
                                 String[] var230 = new String[]{"lsmitha", "lsmithb", "lsmithc", "lsmithd", "lsmithe", "", "lsmithf", "lsmithg", "lsmithh"};
                                 int[] var232 = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
                                 int[][] var28 = new int[][]{
                                    {20158, 40669, 40678},
                                    {20144, 40672, 40678},
                                    {20075, 40671, 40678},
                                    {20183, 40674, 40678},
                                    {20190, 40674, 40678},
                                    {20078, 40674, 40678},
                                    {20078, 40670, 40678},
                                    {40719, 40673, 40678}
                                 };
                                 int[][] var29 = new int[][]{
                                    {1, 50, 100}, {1, 50, 100}, {1, 50, 100}, {1, 20, 100}, {1, 40, 100}, {1, 5, 100}, {1, 1, 100}, {1, 1, 100}
                                 };
                                 int[] var30 = new int[]{20083, 20131, 20069, 20179, 20209, 20290, 20261, 20031};
                                 String[] var31 = new String[]{"lsmithaa", "lsmithbb", "lsmithcc", "lsmithdd", "lsmithee", "lsmithff", "lsmithgg", "lsmithhh"};

                                 for (int var32 = 0; var32 < var228.length; var32++) {
                                    if (var5.equalsIgnoreCase(var228[var32])) {
                                       var77 = var228[var32];
                                       if (var32 <= 8) {
                                          var225 = var230[var32];
                                       } else if (var32 > 8) {
                                          var135 = var232[var32 - 9];
                                          var171 = var28[var32 - 9];
                                          var198 = var29[var32 - 9];
                                          var214 = var30[var32 - 9];
                                          var222 = var31[var32 - 9];
                                       }
                                       break;
                                    }
                                 }

                                 if (var5.equalsIgnoreCase(var77)) {
                                    if (var135 != 0 && var3.Q() >= var135) {
                                       var6 = var171;
                                       var7 = var198;
                                       var8 = new int[]{var214};
                                       var9 = new int[]{1};
                                       var11 = "";
                                       var12 = var222;
                                    } else {
                                       var10 = var225;
                                    }
                                 }
                              } else if (var17 == 80074) {
                                 if (var3.P() >= 10000000) {
                                    this.f(var3, var16, var5);
                                 }

                                 var10 = "";
                              } else if (var17 == 80057) {
                                 var10 = this.b(var3.Q());
                                 var13 = new String[]{String.valueOf(var3.R())};
                              } else if (var17 == 80059 || var17 == 80060 || var17 == 80061 || var17 == 80062) {
                                 var10 = this.d(var3, (L1NpcInstance)var15, var5);
                              } else if (var17 == 81124) {
                                 if (var5.equalsIgnoreCase("1")) {
                                    this.a(var2, 4002);
                                    var10 = "";
                                 } else if (var5.equalsIgnoreCase("2")) {
                                    this.a(var2, 4004);
                                    var10 = "";
                                 } else if (var5.equalsIgnoreCase("3")) {
                                    this.a(var2, 4950);
                                    var10 = "";
                                 }
                              } else if (var17 == 70811) {
                                 if (var5.equalsIgnoreCase("contract1")) {
                                    var3.bb().a(10, 1);
                                    var10 = "lyraev2";
                                 }
                              } else if (var5.equalsIgnoreCase("pandora6")
                                 || var5.equalsIgnoreCase("cold6")
                                 || var5.equalsIgnoreCase("balsim3")
                                 || var5.equalsIgnoreCase("arieh6")
                                 || var5.equalsIgnoreCase("andyn3")
                                 || var5.equalsIgnoreCase("ysorya3")
                                 || var5.equalsIgnoreCase("luth3")
                                 || var5.equalsIgnoreCase("catty3")
                                 || var5.equalsIgnoreCase("mayer3")
                                 || var5.equalsIgnoreCase("vergil3")
                                 || var5.equalsIgnoreCase("stella6")
                                 || var5.equalsIgnoreCase("ralf6")
                                 || var5.equalsIgnoreCase("berry6")
                                 || var5.equalsIgnoreCase("jin6")
                                 || var5.equalsIgnoreCase("defman3")
                                 || var5.equalsIgnoreCase("mellisa3")
                                 || var5.equalsIgnoreCase("mandra3")
                                 || var5.equalsIgnoreCase("bius3")
                                 || var5.equalsIgnoreCase("momo6")
                                 || var5.equalsIgnoreCase("ashurEv7")
                                 || var5.equalsIgnoreCase("elmina3")
                                 || var5.equalsIgnoreCase("glen3")
                                 || var5.equalsIgnoreCase("mellin3")
                                 || var5.equalsIgnoreCase("orcm6")
                                 || var5.equalsIgnoreCase("jackson3")
                                 || var5.equalsIgnoreCase("britt3")
                                 || var5.equalsIgnoreCase("old6")
                                 || var5.equalsIgnoreCase("shivan3")) {
                                 var10 = var5;
                                 int var76 = L1CastleLocation.b(var16);
                                 var13 = new String[]{String.valueOf(var76)};
                              } else if (var5.equalsIgnoreCase("set")) {
                                 int var18 = L1TownLocation.a(var16.fs(), var16.ft(), var16.fp());
                                 if (var18 >= 1 && var18 <= 10) {
                                    if (var3.bF() == -1) {
                                       var3.a(new S_ServerMessage(759));
                                    } else if (var3.bF() > 0) {
                                       if (var3.bF() != var18) {
                                          L1Town var19 = TownTable.a().a(var3.bF());
                                          if (var19 != null) {
                                             var3.a(new S_ServerMessage(758, var19.b()));
                                          }
                                       }
                                    } else if (var3.bF() == 0) {
                                       if (var3.ev() < 10) {
                                          var3.a(new S_ServerMessage(757));
                                       } else {
                                          int var107 = var3.ev();
                                          int var20 = var107 * var107 * 10;
                                          if (var3.j().b(40308, var20)) {
                                             var3.aD(var18);
                                             var3.aE(0);
                                             var3.I();
                                          } else {
                                             var3.a(new S_ServerMessage(337, "$4"));
                                          }
                                       }
                                    }

                                    var10 = "";
                                 }
                              } else if (var5.equalsIgnoreCase("clear")) {
                                 int var35 = L1TownLocation.a((L1Character)var16);
                                 if (var35 > 0) {
                                    if (var3.bF() > 0) {
                                       if (var3.bF() == var35) {
                                          var3.aD(-1);
                                          var3.aE(0);
                                          var3.I();
                                       } else {
                                          var3.a(new S_ServerMessage(756));
                                       }
                                    }

                                    var10 = "";
                                 }
                              } else if (var5.equalsIgnoreCase("ask")) {
                                 int var36 = L1TownLocation.a((L1Character)var16);
                                 if (var36 >= 1 && var36 <= 10) {
                                    L1Town var108 = TownTable.a().a(var36);
                                    String var156 = var108.d();
                                    if (var156 != null && var156.length() != 0) {
                                       var10 = "owner";
                                       var13 = new String[]{var156};
                                    } else {
                                       var10 = "noowner";
                                    }
                                 }
                              } else if (var17 == 71198) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    if (var3.bb().a(71198) != 0 || var3.j().g(21059, 1)) {
                                       return;
                                    }

                                    if (var3.j().b(41339, 5)) {
                                       ItemTable.a(var3, 41340, 1, 0, var16.T());
                                       var3.bb().a(71198, 1);
                                       var10 = "tion4";
                                    } else {
                                       var10 = "tion9";
                                    }
                                 } else if (var5.equalsIgnoreCase("B")) {
                                    if (var3.bb().a(71198) != 1 || var3.j().g(21059, 1)) {
                                       return;
                                    }

                                    if (var3.j().b(41341, 1)) {
                                       var3.bb().a(71198, 2);
                                       var10 = "tion5";
                                    } else {
                                       var10 = "tion10";
                                    }
                                 } else if (var5.equalsIgnoreCase("C")) {
                                    if (var3.bb().a(71198) != 2 || var3.j().g(21059, 1)) {
                                       return;
                                    }

                                    if (var3.j().b(41343, 1)) {
                                       ItemTable.a(var3, 21057, 1, 0, var16.T());
                                       var3.bb().a(71198, 3);
                                       var10 = "tion6";
                                    } else {
                                       var10 = "tion12";
                                    }
                                 } else if (var5.equalsIgnoreCase("D")) {
                                    if (var3.bb().a(71198) != 3 || var3.j().g(21059, 1)) {
                                       return;
                                    }

                                    if (var3.j().b(41344, 1)) {
                                       ItemTable.a(var3, 21058, 1, 0, var16.T());
                                       var3.j().b(21057, 1);
                                       var3.bb().a(71198, 4);
                                       var10 = "tion7";
                                    } else {
                                       var10 = "tion13";
                                    }
                                 } else if (var5.equalsIgnoreCase("E")) {
                                    if (var3.bb().a(71198) != 4 || var3.j().g(21059, 1)) {
                                       return;
                                    }

                                    if (var3.j().b(41345, 1)) {
                                       ItemTable.a(var3, 21059, 1, 0, var16.T());
                                       var3.j().b(21058, 1);
                                       var3.bb().a(71198, 0);
                                       var3.bb().a(71199, 0);
                                       var10 = "tion8";
                                    } else {
                                       var10 = "tion15";
                                    }
                                 }
                              } else if (var17 == 71199) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    if (var3.bb().a(71199) != 0 || var3.j().g(21059, 1)) {
                                       return;
                                    }

                                    if (var3.j().g(41340, 1)) {
                                       var3.bb().a(71199, 1);
                                       var10 = "jeron2";
                                    } else {
                                       var10 = "jeron10";
                                    }
                                 } else if (var5.equalsIgnoreCase("B")) {
                                    if (var3.bb().a(71199) != 1 || var3.j().g(21059, 1)) {
                                       return;
                                    }

                                    if (var3.j().b(40308, 1000000)) {
                                       ItemTable.a(var3, 41341, 1, 0, var16.T());
                                       var3.j().b(41340, 1);
                                       var3.bb().a(71199, 255);
                                       var10 = "jeron6";
                                    } else {
                                       var10 = "jeron8";
                                    }
                                 } else if (var5.equalsIgnoreCase("C")) {
                                    if (var3.bb().a(71199) != 1 || var3.j().g(21059, 1)) {
                                       return;
                                    }

                                    if (var3.j().b(41342, 1)) {
                                       ItemTable.a(var3, 41341, 1, 0, var16.T());
                                       var3.j().b(41340, 1);
                                       var3.bb().a(71199, 255);
                                       var10 = "jeron5";
                                    } else {
                                       var10 = "jeron9";
                                    }
                                 }
                              } else if (var17 == 80079) {
                                 if (var5.equalsIgnoreCase("0")) {
                                    if (!var3.j().f(41312)) {
                                       ItemTable.a(var3, 41312, 1, 0, var16.T());
                                       var3.bb().a(35, 255);
                                       var10 = "keplisha7";
                                    }
                                 } else if (var5.equalsIgnoreCase("1")) {
                                    if (!var3.j().f(41314)) {
                                       if (var3.j().g(40308, 1000)) {
                                          var6 = new int[]{40308, 41313};
                                          var7 = new int[]{1000, 1};
                                          var8 = new int[]{41314};
                                          var9 = new int[]{1};
                                          int var37 = Random.a(3) + 1;
                                          int var109 = Random.a(100) + 1;
                                          switch (var37) {
                                             case 1:
                                                var10 = "horosa" + var109;
                                                break;
                                             case 2:
                                                var10 = "horosb" + var109;
                                                break;
                                             case 3:
                                                var10 = "horosc" + var109;
                                          }
                                       } else {
                                          var10 = "keplisha8";
                                       }
                                    }
                                 } else if (var5.equalsIgnoreCase("2")) {
                                    if (var3.fe() != var3.aB()) {
                                       var10 = "keplisha9";
                                    } else if (var3.j().f(41314)) {
                                       var3.j().b(41314, 1);
                                       int var38 = Random.a(9) + 1;
                                       int var110 = 6180 + Random.a(64);
                                       this.b(var2, var110);
                                       switch (var38) {
                                          case 1:
                                             var10 = "horomon11";
                                             break;
                                          case 2:
                                             var10 = "horomon12";
                                             break;
                                          case 3:
                                             var10 = "horomon13";
                                             break;
                                          case 4:
                                             var10 = "horomon21";
                                             break;
                                          case 5:
                                             var10 = "horomon22";
                                             break;
                                          case 6:
                                             var10 = "horomon23";
                                             break;
                                          case 7:
                                             var10 = "horomon31";
                                             break;
                                          case 8:
                                             var10 = "horomon32";
                                             break;
                                          case 9:
                                             var10 = "horomon33";
                                       }
                                    }
                                 } else if (var5.equalsIgnoreCase("3")) {
                                    var3.j().b(41312, 1);
                                    var3.j().b(41313, 1);
                                    var3.j().b(41314, 1);
                                    var10 = "";
                                 }
                              } else if (var17 == 80082) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    if (var3.ev() < 15) {
                                       var10 = "fk_in_lv";
                                    } else if (var3.j().b(40308, 1000)) {
                                       L1PolyMorph.b(var3);
                                       L1Teleport.a(var3, 32742, 32799, 5490, 4, true);
                                    } else {
                                       var10 = "fk_in_0";
                                    }
                                 }
                              } else if (var17 == 80084) {
                                 if (var5.equalsIgnoreCase("q")) {
                                    if (var3.j().g(41356, 1)) {
                                       var10 = "rparum4";
                                    } else {
                                       ItemTable.a(var3, 41356, 1, 0, var16.T());
                                       var10 = "rparum3";
                                    }
                                 }
                              } else if (var17 == 80105) {
                                 if (var5.equalsIgnoreCase("c") && var3.x() && var3.j().g(20383, 1)) {
                                    if (var3.j().g(40308, 100000)) {
                                       L1ItemInstance var39 = var3.j().b(20383);
                                       if (var39 != null && var39.I() != 50) {
                                          var39.g(50);
                                          var3.j().b(var39);
                                          var3.j().b(40308, 100000);
                                          var10 = "";
                                       }
                                    } else {
                                       var3.a(new S_ServerMessage(337, "$4"));
                                    }
                                 }
                              } else if (var17 == 71126) {
                                 if (var5.equalsIgnoreCase("B")) {
                                    if (var3.j().g(41007, 1)) {
                                       var10 = "eris10";
                                    } else {
                                       ItemTable.a(var3, 41007, 1, 0, var16.T());
                                       var10 = "eris6";
                                    }
                                 } else if (var5.equalsIgnoreCase("C")) {
                                    if (var3.j().g(41009, 1)) {
                                       var10 = "eris10";
                                    } else {
                                       ItemTable.a(var3, 41009, 1, 0, var16.T());
                                       var10 = "eris8";
                                    }
                                 } else if (var5.equalsIgnoreCase("A")) {
                                    if (var3.j().g(41007, 1)) {
                                       if (var3.j().g(40969, 20)) {
                                          var10 = "eris18";
                                          var6 = new int[]{40969, 41007};
                                          var7 = new int[]{20, 1};
                                          var8 = new int[]{41008};
                                          var9 = new int[]{1};
                                       } else {
                                          var10 = "eris5";
                                       }
                                    } else {
                                       var10 = "eris2";
                                    }
                                 } else if (var5.equalsIgnoreCase("E")) {
                                    if (var3.j().g(41010, 1)) {
                                       var10 = "eris19";
                                    } else {
                                       var10 = "eris7";
                                    }
                                 } else if (var5.equalsIgnoreCase("D")) {
                                    if (var3.j().g(41010, 1)) {
                                       var10 = "eris19";
                                    } else if (var3.j().g(41009, 1)) {
                                       if (var3.j().g(40959, 1)) {
                                          var10 = "eris17";
                                          var6 = new int[]{40959, 41009};
                                          var7 = new int[]{1, 1};
                                          var8 = new int[]{41010};
                                          var9 = new int[]{1};
                                       } else if (var3.j().g(40960, 1)) {
                                          var10 = "eris16";
                                          var6 = new int[]{40960, 41009};
                                          var7 = new int[]{1, 1};
                                          var8 = new int[]{41010};
                                          var9 = new int[]{1};
                                       } else if (var3.j().g(40961, 1)) {
                                          var10 = "eris15";
                                          var6 = new int[]{40961, 41009};
                                          var7 = new int[]{1, 1};
                                          var8 = new int[]{41010};
                                          var9 = new int[]{1};
                                       } else if (var3.j().g(40962, 1)) {
                                          var10 = "eris14";
                                          var6 = new int[]{40962, 41009};
                                          var7 = new int[]{1, 1};
                                          var8 = new int[]{41010};
                                          var9 = new int[]{1};
                                       } else if (var3.j().g(40635, 10)) {
                                          var10 = "eris12";
                                          var6 = new int[]{40635, 41009};
                                          var7 = new int[]{10, 1};
                                          var8 = new int[]{41010};
                                          var9 = new int[]{1};
                                       } else if (var3.j().g(40638, 10)) {
                                          var10 = "eris11";
                                          var6 = new int[]{40638, 41009};
                                          var7 = new int[]{10, 1};
                                          var8 = new int[]{41010};
                                          var9 = new int[]{1};
                                       } else if (var3.j().g(40642, 10)) {
                                          var10 = "eris13";
                                          var6 = new int[]{40642, 41009};
                                          var7 = new int[]{10, 1};
                                          var8 = new int[]{41010};
                                          var9 = new int[]{1};
                                       } else if (var3.j().g(40667, 10)) {
                                          var10 = "eris13";
                                          var6 = new int[]{40667, 41009};
                                          var7 = new int[]{10, 1};
                                          var8 = new int[]{41010};
                                          var9 = new int[]{1};
                                       } else {
                                          var10 = "eris8";
                                       }
                                    } else {
                                       var10 = "eris7";
                                    }
                                 }
                              } else if (var17 == 80076) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    int[] var40 = new int[]{49082, 49083};
                                    int var111 = Random.a(var40.length);
                                    int var157 = var40[var111];
                                    if (var157 == 49082) {
                                       var10 = "voyager6a";
                                    } else if (var157 == 49083) {
                                       var10 = "voyager6b";
                                    }

                                    ItemTable.a(var3, var157, 1, 0, var16.T());
                                 }
                              } else if (var17 == 71128) {
                                 if (var5.equals("A")) {
                                    if (var3.j().g(41010, 1)) {
                                       var10 = "perita2";
                                    } else {
                                       var10 = "perita3";
                                    }
                                 } else if (var5.equals("p")) {
                                    if (var3.j().g(40987, 1) && var3.j().g(40988, 1) && var3.j().g(40989, 1)) {
                                       var10 = "perita43";
                                    } else if (var3.j().g(40987, 1) && var3.j().g(40989, 1)) {
                                       var10 = "perita44";
                                    } else if (var3.j().g(40987, 1) && var3.j().g(40988, 1)) {
                                       var10 = "perita45";
                                    } else if (var3.j().g(40988, 1) && var3.j().g(40989, 1)) {
                                       var10 = "perita47";
                                    } else if (var3.j().g(40987, 1)) {
                                       var10 = "perita46";
                                    } else if (var3.j().g(40988, 1)) {
                                       var10 = "perita49";
                                    } else if (var3.j().g(40987, 1)) {
                                       var10 = "perita48";
                                    } else {
                                       var10 = "perita50";
                                    }
                                 } else if (var5.equals("q")) {
                                    if (var3.j().g(41173, 1) && var3.j().g(41174, 1) && var3.j().g(41175, 1)) {
                                       var10 = "perita54";
                                    } else if (var3.j().g(41173, 1) && var3.j().g(41175, 1)) {
                                       var10 = "perita55";
                                    } else if (var3.j().g(41173, 1) && var3.j().g(41174, 1)) {
                                       var10 = "perita56";
                                    } else if (var3.j().g(41174, 1) && var3.j().g(41175, 1)) {
                                       var10 = "perita58";
                                    } else if (var3.j().g(41174, 1)) {
                                       var10 = "perita57";
                                    } else if (var3.j().g(41175, 1)) {
                                       var10 = "perita60";
                                    } else if (var3.j().g(41176, 1)) {
                                       var10 = "perita59";
                                    } else {
                                       var10 = "perita61";
                                    }
                                 } else if (var5.equals("s")) {
                                    if (var3.j().g(41161, 1) && var3.j().g(41162, 1) && var3.j().g(41163, 1)) {
                                       var10 = "perita62";
                                    } else if (var3.j().g(41161, 1) && var3.j().g(41163, 1)) {
                                       var10 = "perita63";
                                    } else if (var3.j().g(41161, 1) && var3.j().g(41162, 1)) {
                                       var10 = "perita64";
                                    } else if (var3.j().g(41162, 1) && var3.j().g(41163, 1)) {
                                       var10 = "perita66";
                                    } else if (var3.j().g(41161, 1)) {
                                       var10 = "perita65";
                                    } else if (var3.j().g(41162, 1)) {
                                       var10 = "perita68";
                                    } else if (var3.j().g(41163, 1)) {
                                       var10 = "perita67";
                                    } else {
                                       var10 = "perita69";
                                    }
                                 } else if (var5.equals("B")) {
                                    if (var3.j().g(40651, 10)
                                       && var3.j().g(40643, 10)
                                       && var3.j().g(40618, 10)
                                       && var3.j().g(40645, 10)
                                       && var3.j().g(40676, 10)
                                       && var3.j().g(40442, 5)
                                       && var3.j().g(40051, 1)) {
                                       var10 = "perita7";
                                       var6 = new int[]{40651, 40643, 40618, 40645, 40676, 40442, 40051};
                                       var7 = new int[]{10, 10, 10, 10, 20, 5, 1};
                                       var8 = new int[]{40925};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita8";
                                    }
                                 } else if (var5.equals("G") || var5.equals("h") || var5.equals("i")) {
                                    if (var3.j().g(40651, 5)
                                       && var3.j().g(40643, 5)
                                       && var3.j().g(40618, 5)
                                       && var3.j().g(40645, 5)
                                       && var3.j().g(40676, 5)
                                       && var3.j().g(40675, 5)
                                       && var3.j().g(40049, 3)
                                       && var3.j().g(40051, 1)) {
                                       var10 = "perita27";
                                       var6 = new int[]{40651, 40643, 40618, 40645, 40676, 40675, 40049, 40051};
                                       var7 = new int[]{5, 5, 5, 5, 10, 10, 3, 1};
                                       var8 = new int[]{40926};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita28";
                                    }
                                 } else if (var5.equals("H") || var5.equals("j") || var5.equals("k")) {
                                    if (var3.j().g(40651, 10)
                                       && var3.j().g(40643, 10)
                                       && var3.j().g(40618, 10)
                                       && var3.j().g(40645, 10)
                                       && var3.j().g(40676, 20)
                                       && var3.j().g(40675, 10)
                                       && var3.j().g(40048, 3)
                                       && var3.j().g(40051, 1)) {
                                       var10 = "perita29";
                                       var6 = new int[]{40651, 40643, 40618, 40645, 40676, 40675, 40048, 40051};
                                       var7 = new int[]{10, 10, 10, 10, 20, 10, 3, 1};
                                       var8 = new int[]{40927};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita30";
                                    }
                                 } else if (var5.equals("I") || var5.equals("l") || var5.equals("m")) {
                                    if (var3.j().g(40651, 20)
                                       && var3.j().g(40643, 20)
                                       && var3.j().g(40618, 20)
                                       && var3.j().g(40645, 20)
                                       && var3.j().g(40676, 30)
                                       && var3.j().g(40675, 10)
                                       && var3.j().g(40050, 3)
                                       && var3.j().g(40051, 1)) {
                                       var10 = "perita31";
                                       var6 = new int[]{40651, 40643, 40618, 40645, 40676, 40675, 40050, 40051};
                                       var7 = new int[]{20, 20, 20, 20, 30, 10, 3, 1};
                                       var8 = new int[]{40928};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita32";
                                    }
                                 } else if (var5.equals("J") || var5.equals("n") || var5.equals("o")) {
                                    if (var3.j().g(40651, 30)
                                       && var3.j().g(40643, 30)
                                       && var3.j().g(40618, 30)
                                       && var3.j().g(40645, 30)
                                       && var3.j().g(40676, 30)
                                       && var3.j().g(40675, 20)
                                       && var3.j().g(40052, 1)
                                       && var3.j().g(40051, 1)) {
                                       var10 = "perita33";
                                       var6 = new int[]{40651, 40643, 40618, 40645, 40676, 40675, 40052, 40051};
                                       var7 = new int[]{30, 30, 30, 30, 30, 20, 1, 1};
                                       var8 = new int[]{40928};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita34";
                                    }
                                 } else if (var5.equals("K")) {
                                    int var41 = 0;
                                    int var112 = 0;
                                    if (var3.j().h(21014) || var3.j().h(21006) || var3.j().h(21007)) {
                                       var10 = "perita36";
                                    } else if (var3.j().g(21014, 1)) {
                                       var41 = 21014;
                                       var112 = 41176;
                                    } else if (var3.j().g(21006, 1)) {
                                       var41 = 21006;
                                       var112 = 41177;
                                    } else if (var3.j().g(21007, 1)) {
                                       var41 = 21007;
                                       var112 = 41178;
                                    } else {
                                       var10 = "perita36";
                                    }

                                    if (var41 > 0) {
                                       var6 = new int[]{var41};
                                       var7 = new int[]{1};
                                       var8 = new int[]{var112};
                                       var9 = new int[]{1};
                                    }
                                 } else if (var5.equals("L")) {
                                    if (var3.j().h(21015)) {
                                       var10 = "perita22";
                                    } else if (var3.j().g(21015, 1)) {
                                       var6 = new int[]{21015};
                                       var7 = new int[]{1};
                                       var8 = new int[]{41179};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita22";
                                    }
                                 } else if (var5.equals("M")) {
                                    if (var3.j().h(21016)) {
                                       var10 = "perita26";
                                    } else if (var3.j().g(21016, 1)) {
                                       var6 = new int[]{21016};
                                       var7 = new int[]{1};
                                       var8 = new int[]{41182};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita26";
                                    }
                                 } else if (var5.equals("b")) {
                                    if (var3.j().h(21009)) {
                                       var10 = "perita39";
                                    } else if (var3.j().g(21009, 1)) {
                                       var6 = new int[]{21009};
                                       var7 = new int[]{1};
                                       var8 = new int[]{41180};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita39";
                                    }
                                 } else if (var5.equals("d")) {
                                    if (var3.j().h(21012)) {
                                       var10 = "perita41";
                                    } else if (var3.j().g(21012, 1)) {
                                       var6 = new int[]{21012};
                                       var7 = new int[]{1};
                                       var8 = new int[]{41183};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita41";
                                    }
                                 } else if (var5.equals("a")) {
                                    if (var3.j().h(21008)) {
                                       var10 = "perita38";
                                    } else if (var3.j().g(21008, 1)) {
                                       var6 = new int[]{21008};
                                       var7 = new int[]{1};
                                       var8 = new int[]{41181};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita38";
                                    }
                                 } else if (var5.equals("c")) {
                                    if (var3.j().h(21010)) {
                                       var10 = "perita40";
                                    } else if (var3.j().g(21010, 1)) {
                                       var6 = new int[]{21010};
                                       var7 = new int[]{1};
                                       var8 = new int[]{41184};
                                       var9 = new int[]{1};
                                    } else {
                                       var10 = "perita40";
                                    }
                                 }
                              } else if (var17 == 71129) {
                                 if (var5.equals("Z")) {
                                    var10 = "rumtis2";
                                 } else if (var5.equals("Y")) {
                                    if (var3.j().g(41010, 1)) {
                                       var10 = "rumtis3";
                                    } else {
                                       var10 = "rumtis4";
                                    }
                                 } else if (var5.equals("q")) {
                                    var10 = "rumtis92";
                                 } else if (var5.equals("A")) {
                                    if (var3.j().g(41161, 1)) {
                                       var10 = "rumtis6";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("B")) {
                                    if (var3.j().g(41164, 1)) {
                                       var10 = "rumtis7";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("C")) {
                                    if (var3.j().g(41167, 1)) {
                                       var10 = "rumtis8";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("T")) {
                                    if (var3.j().g(41167, 1)) {
                                       var10 = "rumtis9";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("w")) {
                                    if (var3.j().g(41162, 1)) {
                                       var10 = "rumtis14";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("x")) {
                                    if (var3.j().g(41165, 1)) {
                                       var10 = "rumtis15";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("y")) {
                                    if (var3.j().g(41168, 1)) {
                                       var10 = "rumtis16";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("z")) {
                                    if (var3.j().g(41171, 1)) {
                                       var10 = "rumtis17";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("U")) {
                                    if (var3.j().g(41163, 1)) {
                                       var10 = "rumtis10";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("V")) {
                                    if (var3.j().g(41166, 1)) {
                                       var10 = "rumtis11";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("W")) {
                                    if (var3.j().g(41169, 1)) {
                                       var10 = "rumtis12";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("X")) {
                                    if (var3.j().g(41172, 1)) {
                                       var10 = "rumtis13";
                                    } else {
                                       var10 = "rumtis101";
                                    }
                                 } else if (var5.equals("D") || var5.equals("E") || var5.equals("F") || var5.equals("G")) {
                                    int var42 = 0;
                                    int var113 = 0;
                                    int var158 = 0;
                                    int var21 = 0;
                                    int var22 = 0;
                                    int var23 = 0;
                                    int var24 = 0;
                                    int var25 = 0;
                                    int var26 = 0;
                                    int var27 = 0;
                                    if (var3.j().g(40959, 1) && var3.j().g(40960, 1) && var3.j().g(40961, 1) && var3.j().g(40962, 1)) {
                                       var42 = 1;
                                       var158 = 40959;
                                       var21 = 40960;
                                       var22 = 40961;
                                       var23 = 40962;
                                       var24 = 1;
                                       var25 = 1;
                                       var26 = 1;
                                       var27 = 1;
                                    } else if (var3.j().g(40642, 10) && var3.j().g(40635, 10) && var3.j().g(40638, 10) && var3.j().g(40667, 10)) {
                                       var113 = 1;
                                       var158 = 40642;
                                       var21 = 40635;
                                       var22 = 40638;
                                       var23 = 40667;
                                       var24 = 10;
                                       var25 = 10;
                                       var26 = 10;
                                       var27 = 10;
                                    }

                                    if (var3.j().g(40046, 1)
                                       && var3.j().g(40618, 5)
                                       && var3.j().g(40643, 5)
                                       && var3.j().g(40645, 5)
                                       && var3.j().g(40651, 5)
                                       && var3.j().g(40676, 5)) {
                                       if (var42 != 1 && var113 != 1) {
                                          var10 = "rumtis18";
                                       } else {
                                          var10 = "rumtis60";
                                          var6 = new int[]{var158, var21, var22, var23, 40046, 40618, 40643, 40651, 40676};
                                          var7 = new int[]{var24, var25, var26, var27, 1, 5, 5, 5, 5, 5};
                                          var8 = new int[]{40926};
                                          var9 = new int[]{1};
                                       }
                                    }
                                 }
                              } else if (var17 == 190554) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    var3.a(new S_ProtoBuffers(113, var3));
                                 }
                              } else if (var17 >= 190571 && var17 <= 190573) {
                                 if (var5.equalsIgnoreCase("a")) {
                                    var3.a(new S_ProtoBuffers(113, var3));
                                 }
                              } else if (var17 == 81208) {
                                 if (var5.equalsIgnoreCase("k")) {
                                    if (var3.j().f(41135) || var3.j().f(41136) || var3.j().f(41137)) {
                                       var6 = new int[]{41135, 41136, 41137};
                                       var7 = new int[]{1, 1, 1};
                                       var8 = new int[]{41138};
                                       var9 = new int[]{1};
                                       var10 = "minibrob03";
                                    } else if (var3.j().f(41126) || var3.j().f(41127) || var3.j().f(41128)) {
                                       var6 = new int[]{41126, 41127, 41128};
                                       var7 = new int[]{1, 1, 1};
                                       var8 = new int[]{41129};
                                       var9 = new int[]{1};
                                       var10 = "minibrob03";
                                    }
                                 }
                              } else if (var17 == 80067) {
                                 if (var5.equalsIgnoreCase("n")) {
                                    var10 = "";
                                    if (var3.j().b(41131, 1)) {
                                       this.a(var2, 6034);
                                       int[] var43 = new int[]{41132, 41133, 41134};

                                       for (int var114 = 0; var114 < var43.length; var114++) {
                                          ItemTable.a(var3, var43[var114], 1, var16.T());
                                       }

                                       var3.bb().a(36, 1);
                                    }
                                 } else if (var5.equalsIgnoreCase("d")) {
                                    var10 = "minicod09";
                                    var3.j().b(41130, 1);
                                    var3.j().b(41131, 1);
                                 } else if (var5.equalsIgnoreCase("k")) {
                                    var10 = "";
                                    var3.j().b(41132, 1);
                                    var3.j().b(41133, 1);
                                    var3.j().b(41134, 1);
                                    var3.j().b(41135, 1);
                                    var3.j().b(41136, 1);
                                    var3.j().b(41137, 1);
                                    var3.j().b(41138, 1);
                                    var3.bb().a(36, 0);
                                 } else if (var5.equalsIgnoreCase("e")) {
                                    if (var3.bb().a(36) == 255 || var3.Q() >= 1) {
                                       var10 = "";
                                    } else if (var3.j().f(41138)) {
                                       var10 = "";
                                       var3.B((int)(1600.0 * Config.D));
                                       var3.j().b(41130, 1);
                                       var3.j().b(41131, 1);
                                       var3.j().b(41138, 1);
                                       var3.bb().a(36, 255);
                                    } else {
                                       var10 = "minicod04";
                                    }
                                 } else if (var5.equalsIgnoreCase("g")) {
                                    ItemTable.a(var3, 41130, 1, 0, var16.T());
                                    var10 = "";
                                 }
                              } else if (var17 == 81202) {
                                 if (var5.equalsIgnoreCase("n")) {
                                    var10 = "";
                                    if (var3.j().b(41122, 1)) {
                                       this.a(var2, 6035);
                                       int[] var44 = new int[]{41123, 41124, 41125};

                                       for (int var115 = 0; var115 < var44.length; var115++) {
                                          ItemTable.a(var3, var44[var115], 1, var16.T());
                                       }

                                       var3.bb().a(37, 1);
                                    }
                                 } else if (var5.equalsIgnoreCase("d")) {
                                    var10 = "minitos09";
                                    var3.j().b(41121, 1);
                                    var3.j().b(41122, 1);
                                 } else if (var5.equalsIgnoreCase("k")) {
                                    var10 = "";
                                    var3.j().b(41123, 1);
                                    var3.j().b(41124, 1);
                                    var3.j().b(41125, 1);
                                    var3.j().b(41126, 1);
                                    var3.j().b(41127, 1);
                                    var3.j().b(41128, 1);
                                    var3.j().b(41129, 1);
                                    var3.bb().a(37, 0);
                                 } else if (var5.equalsIgnoreCase("e")) {
                                    if (var3.bb().a(37) == 255 || var3.Q() >= 1) {
                                       var10 = "";
                                    } else if (var3.j().f(41129)) {
                                       var10 = "";
                                       var3.B((int)(-1600.0 * Config.D));
                                       var3.j().b(41121, 1);
                                       var3.j().b(41122, 1);
                                       var3.j().b(41129, 1);
                                       var3.bb().a(37, 255);
                                    } else {
                                       var10 = "minitos04";
                                    }
                                 } else if (var5.equalsIgnoreCase("g")) {
                                    ItemTable.a(var3, 41121, 1, 0, var16.T());
                                    var10 = "";
                                 }
                              } else if (var17 == 71253) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    if (var3.j().g(49101, 100)) {
                                       var6 = new int[]{49101};
                                       var7 = new int[]{100};
                                       var8 = new int[]{49092};
                                       var9 = new int[]{1};
                                       var10 = "joegolem18";
                                    } else {
                                       var10 = "joegolem19";
                                    }
                                 }
                              } else if (var17 == 71256) {
                                 if (var5.equalsIgnoreCase("E")) {
                                    if (var3.bb().a(40) == 8
                                       && var3.j().g(40491, 30)
                                       && var3.j().g(40495, 40)
                                       && var3.j().g(100, 1)
                                       && var3.j().g(40509, 12)
                                       && var3.j().g(40052, 1)
                                       && var3.j().g(40053, 1)
                                       && var3.j().g(40054, 1)
                                       && var3.j().g(40055, 1)
                                       && var3.j().g(41347, 1)
                                       && var3.j().g(41350, 1)) {
                                       var3.j().b(40491, 30);
                                       var3.j().b(40495, 40);
                                       var3.j().b(100, 1);
                                       var3.j().b(40509, 12);
                                       var3.j().b(40052, 1);
                                       var3.j().b(40053, 1);
                                       var3.j().b(40054, 1);
                                       var3.j().b(40055, 1);
                                       var3.j().b(41347, 1);
                                       var3.j().b(41350, 1);
                                       var10 = "robinhood12";
                                       ItemTable.a(var3, 205, 1, 0, var16.T());
                                       var3.bb().a(40, 255);
                                    }
                                 } else if (var5.equalsIgnoreCase("C")) {
                                    if (var3.bb().a(40) == 7
                                       && var3.j().g(41352, 4)
                                       && var3.j().g(40618, 30)
                                       && var3.j().g(40643, 30)
                                       && var3.j().g(40645, 30)
                                       && var3.j().g(40651, 30)
                                       && var3.j().g(40676, 30)
                                       && var3.j().g(40514, 20)
                                       && var3.j().g(41351, 1)
                                       && var3.j().g(41346, 1)) {
                                       var3.j().b(41352, 4);
                                       var3.j().b(40618, 30);
                                       var3.j().b(40643, 30);
                                       var3.j().b(40645, 30);
                                       var3.j().b(40651, 30);
                                       var3.j().b(40676, 30);
                                       var3.j().b(40514, 20);
                                       var3.j().b(41351, 1);
                                       var3.j().b(41346, 1);
                                       ItemTable.a(var3, 41347, 1, 0, var16.T());
                                       ItemTable.a(var3, 41350, 1, 0, var16.T());
                                       var10 = "robinhood10";
                                       var3.bb().a(40, 8);
                                    }
                                 } else if (var5.equalsIgnoreCase("B")) {
                                    if (var3.j().f(41348) && var3.j().f(41346)) {
                                       var10 = "robinhood13";
                                    } else {
                                       ItemTable.a(var3, 41348, 1, 0, var16.T());
                                       ItemTable.a(var3, 41346, 1, 0, var16.T());
                                       var10 = "robinhood13";
                                       var3.bb().a(40, 2);
                                    }
                                 } else if (var5.equalsIgnoreCase("A")) {
                                    if (var3.j().f(40028)) {
                                       var3.j().b(40028, 1);
                                       var10 = "robinhood4";
                                       var3.bb().a(40, 1);
                                    } else {
                                       var10 = "robinhood19";
                                    }
                                 }
                              } else if (var17 == 71257) {
                                 if (var5.equalsIgnoreCase("D")) {
                                    if (var3.j().f(41349)) {
                                       var10 = "zybril10";
                                       var3.j().b(41349, 1);
                                       ItemTable.a(var3, 41351, 1, 0, var16.T());
                                       var3.bb().a(40, 7);
                                    } else {
                                       var10 = "zybril14";
                                    }
                                 } else if (var5.equalsIgnoreCase("C")) {
                                    if (var3.j().g(40514, 10) && var3.j().f(41353)) {
                                       var3.j().b(40514, 10);
                                       var3.j().b(41353, 1);
                                       ItemTable.a(var3, 41354, 1, 0, var16.T());
                                       var10 = "zybril9";
                                       var3.bb().a(40, 6);
                                    }
                                 } else if (var3.j().f(41353) && var3.j().g(40514, 10)) {
                                    var10 = "zybril8";
                                 } else if (var5.equalsIgnoreCase("B")) {
                                    if (var3.j().g(40048, 10) && var3.j().g(40049, 10) && var3.j().g(40050, 10) && var3.j().g(40051, 10)) {
                                       var3.j().b(40048, 10);
                                       var3.j().b(40049, 10);
                                       var3.j().b(40050, 10);
                                       var3.j().b(40051, 10);
                                       ItemTable.a(var3, 41353, 1, 0, var16.T());
                                       var10 = "zybril15";
                                       var3.bb().a(40, 5);
                                    } else {
                                       var10 = "zybril12";
                                       var3.bb().a(40, 4);
                                    }
                                 } else if (var5.equalsIgnoreCase("A")) {
                                    if (var3.j().f(41348) && var3.j().f(41346)) {
                                       var10 = "zybril3";
                                       var3.bb().a(40, 3);
                                    } else {
                                       var10 = "zybril11";
                                    }
                                 }
                              } else if (var17 == 71258) {
                                 if (var3.j().f(40665)) {
                                    var10 = "marba17";
                                    if (var5.equalsIgnoreCase("B")) {
                                       var10 = "marba7";
                                       if (var3.j().f(214)
                                          && var3.j().f(20389)
                                          && var3.j().f(20393)
                                          && var3.j().f(20401)
                                          && var3.j().f(20406)
                                          && var3.j().f(20409)) {
                                          var10 = "marba15";
                                       }
                                    }
                                 } else if (var5.equalsIgnoreCase("A")) {
                                    if (var3.j().f(40637)) {
                                       var10 = "marba20";
                                    } else {
                                       ItemTable.a(var3, 40637, 1, 0, var16.T());
                                       var10 = "marba6";
                                    }
                                 }
                              } else if (var17 == 71259) {
                                 if (var3.j().f(40665)) {
                                    var10 = "aras8";
                                 } else if (var3.j().f(40637)) {
                                    var10 = "aras1";
                                    if (var5.equalsIgnoreCase("A")) {
                                       if (var3.j().f(40664)) {
                                          var10 = "aras6";
                                          if (!var3.j().f(40679)
                                             && !var3.j().f(40680)
                                             && !var3.j().f(40681)
                                             && !var3.j().f(40682)
                                             && !var3.j().f(40683)
                                             && !var3.j().f(40684)
                                             && !var3.j().f(40693)
                                             && !var3.j().f(40694)
                                             && !var3.j().f(40695)
                                             && !var3.j().f(40697)
                                             && !var3.j().f(40698)
                                             && !var3.j().f(40699)) {
                                             var10 = "aras6";
                                          } else {
                                             var10 = "aras3";
                                          }
                                       } else {
                                          ItemTable.a(var3, 40664, 1, 0, var16.T());
                                          var10 = "aras6";
                                       }
                                    } else if (var5.equalsIgnoreCase("B")) {
                                       if (var3.j().f(40664)) {
                                          var3.j().b(40664, 1);
                                          ItemTable.a(var3, 40665, 1, 0, var16.T());
                                          var10 = "aras13";
                                       } else {
                                          var10 = "aras14";
                                          ItemTable.a(var3, 40665, 1, 0, var16.T());
                                       }
                                    } else if (var5.equalsIgnoreCase("7")) {
                                       if (var3.j().f(40693)
                                          && var3.j().f(40694)
                                          && var3.j().f(40695)
                                          && var3.j().f(40697)
                                          && var3.j().f(40698)
                                          && var3.j().f(40699)) {
                                          var10 = "aras10";
                                       } else {
                                          var10 = "aras9";
                                       }
                                    }
                                 } else {
                                    var10 = "aras7";
                                 }
                              } else if (var17 == 80099) {
                                 if (var5.equalsIgnoreCase("A")) {
                                    if (var3.j().g(40308, 300)) {
                                       var3.j().b(40308, 300);
                                       ItemTable.a(var3, 41315, 1, 0, var16.T());
                                       var3.bb().a(41, 1);
                                       var10 = "rarson16";
                                    } else if (!var3.j().g(40308, 300)) {
                                       var10 = "rarson7";
                                    }
                                 } else if (var5.equalsIgnoreCase("B")) {
                                    if (var3.bb().a(41) == 1 && var3.j().g(41325, 1)) {
                                       var3.j().b(41325, 1);
                                       ItemTable.a(var3, 40308, 2000, 0, var16.T());
                                       ItemTable.a(var3, 41317, 1, 0, var16.T());
                                       var3.bb().a(41, 2);
                                       var10 = "rarson9";
                                    } else {
                                       var10 = "rarson10";
                                    }
                                 } else if (var5.equalsIgnoreCase("C")) {
                                    if (var3.bb().a(41) == 4 && var3.j().g(41326, 1)) {
                                       ItemTable.a(var3, 40308, 30000, 0, var16.T());
                                       var3.j().b(41326, 1);
                                       var10 = "rarson12";
                                       var3.bb().a(41, 5);
                                    } else {
                                       var10 = "rarson17";
                                    }
                                 } else if (var5.equalsIgnoreCase("D")) {
                                    if (var3.bb().a(41) > 1 && var3.bb().a(41) != 5) {
                                       if (var3.bb().a(41) >= 2 && var3.bb().a(41) <= 4) {
                                          if (var3.j().g(40308, 300)) {
                                             var3.j().b(40308, 300);
                                             ItemTable.a(var3, 41315, 1, 0, var16.T());
                                             var10 = "rarson16";
                                          } else if (!var3.j().g(40308, 300)) {
                                             var10 = "rarson7";
                                          }
                                       }
                                    } else if (var3.j().g(40308, 300)) {
                                       var3.j().b(40308, 300);
                                       ItemTable.a(var3, 41315, 1, 0, var16.T());
                                       var3.bb().a(41, 1);
                                       var10 = "rarson16";
                                    } else if (!var3.j().g(40308, 300)) {
                                       var10 = "rarson7";
                                    }
                                 }
                              } else if (var17 == 80101) {
                                 if (var5.equalsIgnoreCase("request letter of kuen")) {
                                    if (var3.bb().a(41) == 2 && var3.j().g(41317, 1)) {
                                       var3.j().b(41317, 1);
                                       ItemTable.a(var3, 41318, 1, 0, var16.T());
                                       var3.bb().a(41, 3);
                                       var10 = "";
                                    } else {
                                       var10 = "";
                                    }
                                 } else if (var5.equalsIgnoreCase("request holy mithril dust")) {
                                    if (var3.bb().a(41) == 3 && var3.j().g(41315, 1) && var3.j().g(40494, 30) && var3.j().g(41318, 1)) {
                                       var3.j().b(41315, 1);
                                       var3.j().b(41318, 1);
                                       var3.j().b(40494, 30);
                                       ItemTable.a(var3, 41316, 1, 0, var16.T());
                                       var3.bb().a(41, 4);
                                       var10 = "";
                                    } else {
                                       var10 = "";
                                    }
                                 }
                              } else if (var17 == 80135) {
                                 if (var3.D() && var5.equalsIgnoreCase("a")) {
                                    if (var3.j().g(49220, 1)) {
                                       var10 = "elas5";
                                    } else {
                                       ItemTable.a(var3, 49220, 1, 0, var16.T());
                                       var10 = "elas4";
                                    }
                                 }
                              } else if (var17 == 81245) {
                                 if (var3.D() && var5.equalsIgnoreCase("request flute of spy")) {
                                    if (var3.j().g(49223, 1)) {
                                       var3.j().b(49223, 1);
                                       ItemTable.a(var3, 49222, 1, 0, var16.T());
                                       var10 = "";
                                    } else {
                                       var10 = "";
                                    }
                                 }
                              } else if (var17 == 81246) {
                                 if (var5.getBytes()[0] >= "0".getBytes()[0] && var5.getBytes()[0] <= "0".getBytes()[0] + 19) {
                                    int var45 = var5.getBytes()[0] - "0".getBytes()[0] + 1;
                                    var6 = new int[]{40308};
                                    var7 = new int[]{2500 * var45};
                                    if (var3.ev() < 30) {
                                       var10 = "sharna4";
                                    } else if (var3.ev() >= 30 && var3.ev() <= 39) {
                                       var8 = new int[]{49149};
                                       var9 = new int[]{var45};
                                    } else if (var3.ev() >= 40 && var3.ev() <= 51) {
                                       var8 = new int[]{49150};
                                       var9 = new int[]{var45};
                                    } else if (var3.ev() >= 52 && var3.ev() <= 54) {
                                       var8 = new int[]{49151};
                                       var9 = new int[]{var45};
                                    } else if (var3.ev() >= 55 && var3.ev() <= 59) {
                                       var8 = new int[]{49152};
                                       var9 = new int[]{var45};
                                    } else if (var3.ev() >= 60 && var3.ev() <= 64) {
                                       var8 = new int[]{49153};
                                       var9 = new int[]{var45};
                                    } else if (var3.ev() >= 65 && var3.ev() <= 69) {
                                       var8 = new int[]{49154};
                                       var9 = new int[]{var45};
                                    } else if (var3.ev() >= 70) {
                                       var8 = new int[]{49155};
                                       var9 = new int[]{var45};
                                    }

                                    var11 = "sharna3";
                                    var12 = "sharna5";
                                 }
                              } else if (var17 != 70035 && var17 != 70041 && var17 != 70042) {
                                 if (var17 == 81334) {
                                    if (var5.equalsIgnoreCase("a")) {
                                       if (var3.j().g(49239, 1)) {
                                          var10 = "rtf06";
                                       } else {
                                          int[] var46 = new int[]{49239};
                                          int[] var116 = new int[]{1};

                                          for (int var159 = 0; var159 < var46.length; var159++) {
                                             ItemTable.a(var3, var46[var159], var116[var159], var16.T());
                                          }
                                       }
                                    }
                                 } else if (var17 == 190346) {
                                    if (var5.equalsIgnoreCase("a")) {
                                       int[] var47 = new int[]{42, 26, 48};
                                       if (!var3.j().b(640621, 10)) {
                                          var3.a(new S_Html(var4, "pbs_03"));
                                          return;
                                       }

                                       int[] var210 = var47;
                                       int var189 = var47.length;

                                       for (int var160 = 0; var160 < var189; var160++) {
                                          int var117 = var210[var160];
                                          L1SkillExecutor var219 = LineageUtil.a(var117);
                                          var219.a(var3, 0);
                                       }

                                       var10 = "pbs_02";
                                    }
                                 } else if ((var17 < 81353 || var17 > 81363) && var17 != 190493 && var17 != 190578) {
                                    if (var17 == 81296) {
                                       int var50 = 0;
                                       if (var5.equalsIgnoreCase("0")) {
                                          var50 = 1;
                                       } else if (var5.equalsIgnoreCase("1")) {
                                          var50 = 3;
                                       } else if (var5.equalsIgnoreCase("2")) {
                                          var50 = 5;
                                       } else if (var5.equalsIgnoreCase("3")) {
                                          var50 = 10;
                                       }

                                       if (var50 > 0 && var3.j().b(640438, var50)) {
                                          var3.cs(3000 * var50);
                                          var3.a(new S_SkillSound(var3.fr(), 7353));
                                          var3.b(new S_SkillSound(var3.fr(), 7353));
                                          var10 = "yuris2";
                                       } else {
                                          var10 = "yuris3";
                                       }
                                    } else if (var17 == 70701) {
                                       if (var5.equalsIgnoreCase("material")) {
                                          if (var3.ca() >= 1) {
                                             if (!var3.j().b(640381, 1) && !var3.j().b(640225, 1)) {
                                                var3.a(new S_ServerMessage(739));
                                             } else {
                                                var3.a(true);
                                                var3.aH(0);
                                                var3.a(new S_SkillSound(var3.fr(), 10418));
                                                var3.b(new S_SkillSound(var3.fr(), 10418));
                                             }
                                          } else {
                                             var3.a(new S_ServerMessage(2985));
                                             var10 = "";
                                          }
                                       }
                                    } else if (var17 == 81260) {
                                       int var51 = var3.bF();
                                       char var120 = var5.charAt(0);
                                       if (var3.ev() > 9 && var51 > 0 && var51 < 11) {
                                          switch (var120) {
                                             case '0':
                                                var8 = new int[]{49305};
                                                var9 = new int[]{1};
                                                var6 = new int[]{40308, 40014};
                                                var7 = new int[]{1000, 3};
                                                var14 = 2;
                                                var10 = "";
                                                break;
                                             case '1':
                                                var8 = new int[]{49304};
                                                var9 = new int[]{1};
                                                var6 = new int[]{40308, 40068};
                                                var7 = new int[]{1000, 3};
                                                var14 = 4;
                                                var10 = "";
                                                break;
                                             case '2':
                                                var8 = new int[]{49307};
                                                var9 = new int[]{1};
                                                var6 = new int[]{40308, 40016};
                                                var7 = new int[]{500, 3};
                                                var14 = 2;
                                                var10 = "";
                                                break;
                                             case '3':
                                                var8 = new int[]{49306};
                                                var9 = new int[]{1};
                                                var6 = new int[]{40308, 40015};
                                                var7 = new int[]{1000, 3};
                                                var14 = 2;
                                                var10 = "";
                                                break;
                                             case '4':
                                                var8 = new int[]{49302};
                                                var9 = new int[]{1};
                                                var6 = new int[]{40308, 40013};
                                                var7 = new int[]{500, 3};
                                                var14 = 1;
                                                var10 = "";
                                                break;
                                             case '5':
                                                var8 = new int[]{49303};
                                                var9 = new int[]{1};
                                                var6 = new int[]{40308, 40032};
                                                var7 = new int[]{500, 3};
                                                var14 = 1;
                                                var10 = "";
                                                break;
                                             case '6':
                                                var8 = new int[]{49308};
                                                var9 = new int[]{1};
                                                var6 = new int[]{40308, 40088};
                                                var7 = new int[]{1000, 3};
                                                var14 = 3;
                                                var10 = "";
                                                break;
                                             case 'A':
                                             case 'a':
                                                switch (var51) {
                                                   case 1:
                                                      var8 = new int[]{49292};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                      break;
                                                   case 2:
                                                      var8 = new int[]{49297};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                      break;
                                                   case 3:
                                                      var8 = new int[]{49293};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                      break;
                                                   case 4:
                                                      var8 = new int[]{49296};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                      break;
                                                   case 5:
                                                      var8 = new int[]{49295};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                      break;
                                                   case 6:
                                                      var8 = new int[]{49294};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                      break;
                                                   case 7:
                                                      var8 = new int[]{49298};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                      break;
                                                   case 8:
                                                      var8 = new int[]{49299};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                      break;
                                                   case 9:
                                                      var8 = new int[]{49301};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                      break;
                                                   case 10:
                                                      var8 = new int[]{49300};
                                                      var9 = new int[]{1};
                                                      var6 = new int[]{40308};
                                                      var7 = new int[]{400};
                                                      var10 = "";
                                                }
                                          }
                                       }
                                    } else if (var17 == 81278) {
                                       if (var5.equalsIgnoreCase("0")) {
                                          if (var3.j().g(46000, 1)) {
                                             var10 = "veil3";
                                          } else if (var3.j().g(40308, 1000000)) {
                                             var3.j().b(40308, 1000000);
                                             ItemTable.a(var3, 46000, 1, var16.T());
                                             var10 = "veil7";
                                          } else if (!var3.j().g(40308, 1000000)) {
                                             var10 = "veil4";
                                          }
                                       } else if (var5.equalsIgnoreCase("1")) {
                                          var10 = "veil9";
                                       }
                                    } else if (var17 == 81277) {
                                       int var52 = var3.ev();
                                       char var121 = var5.charAt(0);
                                       if (var5.equalsIgnoreCase("0")) {
                                          if (var52 >= 30 && var52 <= 51) {
                                             L1Teleport.a(var3, 32820, 32904, 1002, 5, true);
                                             var10 = "";
                                          } else {
                                             var10 = "dsecret3";
                                          }
                                       } else if (var52 >= 52) {
                                          switch (var121) {
                                             case '1':
                                                L1Teleport.a(var3, 32904, 32627, 1002, 5, true);
                                                break;
                                             case '2':
                                                L1Teleport.a(var3, 32793, 32593, 1002, 5, true);
                                                break;
                                             case '3':
                                                L1Teleport.a(var3, 32874, 32785, 1002, 5, true);
                                                break;
                                             case '4':
                                                L1Teleport.a(var3, 32993, 32716, 1002, 4, true);
                                                break;
                                             case '5':
                                                L1Teleport.a(var3, 32698, 32664, 1002, 6, true);
                                                break;
                                             case '6':
                                                L1Teleport.a(var3, 32710, 32759, 1002, 6, true);
                                                break;
                                             case '7':
                                                L1Teleport.a(var3, 32986, 32630, 1002, 4, true);
                                          }

                                          var10 = "";
                                       } else {
                                          var10 = "dsecret3";
                                       }
                                    } else if (var17 == 46164) {
                                       if (var5.equalsIgnoreCase("enter")) {
                                          L1ItemInstance var53 = ItemTable.a().b(310);
                                          if (var3.j().a(var53, 1) != 0) {
                                             return;
                                          }

                                          var53.f(2);
                                          var3.j().d(var53);
                                          var3.a(new S_ServerMessage(403, var53.b()));
                                          L1Teleport.a(var3, 32624, 33057, var3.fp() - 99, 5, true);
                                       }
                                    } else if (var17 == 46181) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          if (var3.j().b(640294, 1)) {
                                             if (!L1ValakasLair.a().a(var3)) {
                                                ItemTable.a(var3, 640294, 1, 0, var16.T());
                                                var3.a(new S_ServerMessage(3903));
                                             }
                                          } else {
                                             var3.a(new S_ServerMessage(337, "$18617"));
                                          }
                                       }
                                    } else if (var17 == 46180) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          if (var3.bb().a(44) == 255) {
                                             return;
                                          }

                                          if (var3.ev() >= 60) {
                                             var8 = new int[]{640293};
                                             var9 = new int[]{1};
                                             var10 = "";
                                             var3.bb().b(44);
                                          } else {
                                             var3.a(new S_ServerMessage(2738));
                                          }
                                       }
                                    } else if (var17 == 190022) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          L1ItemInstance var54 = var3.j().b(640299);
                                          int var122 = var54 == null ? 1 : var54.E();
                                          var8 = new int[]{40308};
                                          var9 = new int[]{1000 * var122};
                                          var6 = new int[]{640299};
                                          var7 = new int[]{var122};
                                       } else if (var5.equalsIgnoreCase("b")) {
                                          L1ItemInstance var55 = var3.j().b(640300);
                                          int var123 = var55 == null ? 1 : var55.E();
                                          var8 = new int[]{40308};
                                          var9 = new int[]{10000 * var123};
                                          var6 = new int[]{640300};
                                          var7 = new int[]{var123};
                                       } else if (var5.equalsIgnoreCase("c")) {
                                          L1ItemInstance var56 = var3.j().b(640301);
                                          int var124 = var56 == null ? 1 : var56.E();
                                          var8 = new int[]{40308};
                                          var9 = new int[]{150000 * var124};
                                          var6 = new int[]{640301};
                                          var7 = new int[]{var124};
                                       }

                                       var10 = "";
                                    } else if (var17 == 190019) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          L1ItemInstance var57 = var3.j().b(640295);
                                          int var125 = var57 == null ? 0 : var57.E() / 108;
                                          if (var125 == 0) {
                                             var125 = 1;
                                          }

                                          var8 = new int[]{640303};
                                          var9 = new int[]{1 * var125};
                                          var6 = new int[]{640295};
                                          var7 = new int[]{108 * var125};
                                          var10 = "";
                                       } else if (var5.equalsIgnoreCase("e")) {
                                          var8 = new int[]{640306};
                                          var9 = new int[]{1};
                                          var6 = new int[]{640295};
                                          var7 = new int[]{108};
                                       }
                                    } else if (var17 == 190018) {
                                       if (var5.equalsIgnoreCase("teleport jp yamato p1")) {
                                          L1Teleport.a(var3, 32817, 32798, 8000, 2, true);
                                       }
                                    } else if (var17 == 190023) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          L1Teleport.a(var3, 32931, 32867, 8001, 2, true);
                                       } else if (var5.equalsIgnoreCase("b")) {
                                          L1Teleport.a(var3, 32931, 32867, 8002, 2, true);
                                       } else if (var5.equalsIgnoreCase("c")) {
                                          L1Teleport.a(var3, 32931, 32867, 8003, 2, true);
                                       }
                                    } else if (var17 == 190024) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          var8 = new int[]{640297};
                                          var9 = new int[]{1};
                                          var6 = new int[]{640296};
                                          var7 = new int[]{108};
                                       } else if (var5.equalsIgnoreCase("b")) {
                                          var8 = new int[]{640298};
                                          var9 = new int[]{1};
                                          var6 = new int[]{40308};
                                          var7 = new int[]{500000};
                                       } else if (var5.equalsIgnoreCase("c")) {
                                          L1Teleport.a(var3, 32935, 32867, 8000, 6, true);
                                       } else if (var5.equalsIgnoreCase("D")) {
                                          if ((var3.cP() & S_RuneSlot.f) == S_RuneSlot.f) {
                                             var10 = "tw_sanojo3";
                                          } else if (var3.j().b(40308, 20000000)) {
                                             var3.bc(var3.cP() | S_RuneSlot.f);
                                             var3.a(new S_RuneSlot(67, var3.cP()));
                                             var10 = "tw_sanojo4";
                                          } else {
                                             var10 = "tw_sanojo3";
                                          }
                                       }
                                    } else if (var17 == 190020) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          L1Teleport.a(var3, 33416, 32826, 4, 5, true);
                                       }
                                    } else if (var17 == 190027) {
                                       if (var5.equalsIgnoreCase("r")) {
                                          SoulTowerTable.a().a(var3);
                                       }
                                    } else if (var17 == 190028) {
                                       if (var5.equalsIgnoreCase("enter") && !L1SoulTower.a().a(var3)) {
                                          var3.a(new S_ServerMessage(3903));
                                       }
                                    } else if (var17 == 190046) {
                                       int var58 = 0;
                                       int var126 = 0;
                                       int var163 = 0;
                                       if (var5.equalsIgnoreCase("C")) {
                                          var58 = 59;
                                          var126 = S_RuneSlot.e;
                                          var163 = 20000000;
                                       } else if (var5.equalsIgnoreCase("A")) {
                                          var58 = 76;
                                          var126 = S_RuneSlot.c;
                                          var163 = 10000000;
                                       } else if (var5.equalsIgnoreCase("B")) {
                                          var58 = 81;
                                          var126 = S_RuneSlot.d;
                                          var163 = 30000000;
                                       } else if (var5.equalsIgnoreCase("E")) {
                                          var58 = 83;
                                          var126 = S_RuneSlot.g;
                                          var163 = 30000000;
                                       } else if (var5.equalsIgnoreCase("F")) {
                                          var58 = 70;
                                          var126 = S_RuneSlot.h;
                                          var163 = 2000000;
                                       }

                                       if ((var3.cP() & var126) == var126) {
                                          var10 = "slot5";
                                       } else if (var3.ev() < var58) {
                                          var10 = "slot3";
                                       } else if (var3.j().b(40308, var163)) {
                                          if (var126 == S_RuneSlot.g) {
                                             ItemTable.a(var3, 21542, 1, var16.et());
                                          }

                                          var3.bc(var3.cP() | var126);
                                          var3.a(new S_RuneSlot(67, var3.cP()));
                                          var10 = "slot9";
                                       } else {
                                          var10 = "slot6";
                                       }
                                    } else if (var17 == 190081) {
                                       if (var5.equals("A") || var5.equals("B") || var5.equals("C") || var5.equals("D")) {
                                          if (var3.ev() >= 10 && var3.ev() <= 44) {
                                             int var59 = 25 + var3.ev() / 10 - 1;
                                             L1Teleport.a(var3, 32799, 32799, var59, 5, true);
                                          } else {
                                             var10 = "newbiegate3";
                                          }
                                       }
                                    } else if (var17 == 190080) {
                                       if (var5.equalsIgnoreCase("B")) {
                                          if (var3.j().f(640804)) {
                                             if (!var3.bB(4085)) {
                                                var3.bH(100);
                                                var3.bJ(100);
                                                var3.c(5);
                                                var3.d(5);
                                                var3.bN(1);
                                                var3.bR(1);
                                                var3.bP(1);
                                                var3.bX(1);
                                                var3.bV(1);
                                                var3.bT(1);
                                                var3.cA(5);
                                                var3.ck(5);
                                                var3.cl(5);
                                                var3.cp(3);
                                                var3.F(5);
                                                var3.bL(-5);
                                                var3.a(new S_PacketBox(132, var3.u()));
                                             }

                                             var3.j(4085, 1800000);
                                             var3.a(new S_ProtoBuffers(4085, 1800, 8, 4470, 0, 4385, 0, 0, 1));
                                             var3.a(new S_SkillSound(var3.fr(), 14102));
                                             var3.b(new S_SkillSound(var3.fr(), 14102));
                                          } else {
                                             if (!var3.bB(4069)) {
                                                var3.bH(10);
                                                var3.bJ(10);
                                                var3.c(1);
                                                var3.d(1);
                                                var3.bL(-3);
                                             }

                                             var3.j(4069, 1800000);
                                             var3.a(new S_ProtoBuffers(4069, 1800, 8, 4470, 0, 4387, 0, 0, 1));
                                             var3.a(new S_SkillSound(var3.fr(), 10299));
                                             var3.b(new S_SkillSound(var3.fr(), 10299));
                                          }

                                          var10 = "tw_tnbuff1";
                                       }
                                    } else if (var17 == 190084) {
                                       if (var5.equalsIgnoreCase("itemresolve")) {
                                          var3.a(new S_CharEvent(48, var15.fr()));
                                       }
                                    } else if (var17 == 190096) {
                                       if (var3.ev() < 52) {
                                          var10 = "ekins3";
                                       } else if (var5.equalsIgnoreCase("a")) {
                                          var8 = new int[]{640373};
                                          var9 = new int[]{3};
                                          var6 = new int[]{640368, 640357};
                                          var7 = new int[]{5, 1};
                                          var11 = "ekins4";
                                          var12 = "ekins5";
                                          if (var3.j().g(640368, 5) && var3.j().g(640357, 1)) {
                                             int var60 = (int)(ExpTable.b(64) * 0.02);
                                             var3.x(var60);
                                             var3.a(new S_SkillSound(var3.fr(), 10418));
                                             var3.b(new S_SkillSound(var3.fr(), 10418));
                                          }
                                       } else if (var5.equalsIgnoreCase("b")) {
                                          var8 = new int[]{640373};
                                          var9 = new int[]{3};
                                          var6 = new int[]{640368, 640358};
                                          var7 = new int[]{5, 1};
                                          var11 = "ekins4";
                                          var12 = "ekins5";
                                          if (var3.j().g(640368, 5) && var3.j().g(640358, 1)) {
                                             int var61 = (int)(ExpTable.b(64) * 0.06);
                                             var3.x(var61);
                                             var3.a(new S_SkillSound(var3.fr(), 10418));
                                             var3.b(new S_SkillSound(var3.fr(), 10418));
                                          }
                                       }
                                    } else if (var17 == 190093) {
                                       boolean var62 = false;
                                       L1ItemInstance var127 = var3.j().b(640368);
                                       if (var127 != null && var127.E() >= 5) {
                                          int var164 = 0;

                                          for (L1ItemInstance var192 : var3.j().d()) {
                                             if (var192.N() == 640357 || var192.N() == 640358) {
                                                var164 += var192.E();
                                             }
                                          }

                                          if (var127.E() / 5 >= var164) {
                                             var62 = true;
                                          }
                                       }

                                       if (var3.ev() < 52) {
                                          var10 = "edlen5";
                                       } else if (!var3.j().f(640357) && !var3.j().f(640358)) {
                                          var10 = "edlen3";
                                       } else if (var62) {
                                          var10 = "edlen2";
                                       } else if (var5.equalsIgnoreCase("a")) {
                                          L1Teleport.a(var3, 32835, 32771, 1931, 5, true);
                                       } else if (var5.equalsIgnoreCase("b")) {
                                          L1Teleport.a(var3, 32703, 32645, 1931, 5, true);
                                       } else if (var5.equalsIgnoreCase("c")) {
                                          L1Teleport.a(var3, 32759, 32714, 1931, 5, true);
                                       } else if (var5.equalsIgnoreCase("d")) {
                                          L1Teleport.a(var3, 32644, 32833, 1931, 5, true);
                                       }
                                    } else if (var17 == 190113) {
                                       if (var5.equalsIgnoreCase("enter") && !L1CentralTemple.a().a(var3)) {
                                          var3.a(new S_ServerMessage(3903));
                                       }
                                    } else if (var17 == 81402) {
                                       if (var5.equalsIgnoreCase("enter") && !L1IceQueen.a().a(var3, 1)) {
                                          var3.a(new S_ServerMessage(3903));
                                       }
                                    } else if (var17 == 81403) {
                                       if (var5.equalsIgnoreCase("enter") && !L1IceQueen.a().a(var3, 2)) {
                                          var3.a(new S_ServerMessage(3903));
                                       }
                                    } else if (var17 == 81404) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          var3.j().a(640700);
                                          ItemTable.a(var3, 640700, 1, true);
                                          var10 = "icqwand2";
                                       } else if (var5.equalsIgnoreCase("b")) {
                                          var3.j().a(640699);
                                          ItemTable.a(var3, 640699, 100, true);
                                          var10 = "icqwand3";
                                       }
                                    } else if (var17 == 190352) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          ItemTable.a(var3, 49031, 1);
                                          ItemTable.a(var3, 21081, 1);
                                          L1Teleport.a(var3, 34062, 32311, 4, 5, true);
                                       }
                                    } else if (var17 == 190166) {
                                       if (var5.equalsIgnoreCase("C")) {
                                          var3.b(640385, 30, 0);
                                          var10 = "twf_oldwishe";
                                          var13 = new String[]{"$19543"};
                                       } else if (var5.equalsIgnoreCase("D")) {
                                          var3.b(21372, 10, 9);
                                          var10 = "twf_oldwishe";
                                          var13 = new String[]{"+9 $19665"};
                                       } else if (var5.equalsIgnoreCase("E")) {
                                          var3.b(21373, 10, 9);
                                          var10 = "twf_oldwishe";
                                          var13 = new String[]{"+9 $19666"};
                                       } else if (var5.equalsIgnoreCase("F")) {
                                          var3.b(41682, 10, 0);
                                          var10 = "twf_oldwishe";
                                          var13 = new String[]{"$17824"};
                                       } else if (var5.equalsIgnoreCase("G")) {
                                          var3.b(21226, 10, 0);
                                          var10 = "twf_oldwishe";
                                          var13 = new String[]{"$16747"};
                                       } else if (var5.equalsIgnoreCase("H")) {
                                          var3.b(21227, 10, 0);
                                          var10 = "twf_oldwishe";
                                          var13 = new String[]{"$15611"};
                                       } else if (var5.equalsIgnoreCase("I")) {
                                          var3.b(21228, 10, 0);
                                          var10 = "twf_oldwishe";
                                          var13 = new String[]{"$15621"};
                                       } else if (var5.equalsIgnoreCase("J")) {
                                          var3.b(21229, 10, 0);
                                          var10 = "twf_oldwishe";
                                          var13 = new String[]{"$15631"};
                                       } else if (var5.equalsIgnoreCase("Z") && var3.dy() != null) {
                                          if (var3.j().b(640384, var3.dy()[1])) {
                                             ItemTable.a(var3, var3.dy()[0], 1, var3.dy()[2], var16.U_().A());
                                          } else {
                                             var10 = "twf_oldwish4";
                                             var3.a(new S_ServerMessage(337, "$19085 (" + var3.dy()[1] + ")"));
                                          }
                                       }
                                    } else if (var17 == 190272) {
                                       if (var5.equalsIgnoreCase("A")) {
                                          if (!var3.j().f(640556) && !var3.j().f(21339)) {
                                             ItemTable.a(var3, 640556, 1, var16.T());
                                             ItemTable.a(var3, 21330, 1, var16.T());
                                             var10 = "twf_earring4";
                                          } else {
                                             var10 = "twf_earring3";
                                          }
                                       } else if (var5.equalsIgnoreCase("B")) {
                                          L1ItemInstance var63 = null;
                                          int var128 = 21330;

                                          while (var128 <= 21339 && (var63 = var3.j().b(var128)) == null) {
                                             var128++;
                                          }

                                          if (var63 == null) {
                                             var10 = "twf_earring2";
                                          } else if (var63.D()) {
                                             var10 = "twf_earring7";
                                          } else {
                                             var10 = "twf_earring1";
                                             var13 = new String[]{"" + (var63.N() - 21330)};
                                          }
                                       } else if (var5.equalsIgnoreCase("C")) {
                                          L1ItemInstance var64 = null;
                                          int var129 = 21330;

                                          while (var129 <= 21339 && (var64 = var3.j().b(var129)) == null) {
                                             var129++;
                                          }

                                          if (var64 == null) {
                                             var10 = "twf_earring2";
                                          } else if (var64.D()) {
                                             var10 = "twf_earring7";
                                          } else if (var64.N() == 21339) {
                                             var10 = "twf_earring5";
                                          } else {
                                             var129 = var64.N();
                                             switch (var129) {
                                                case 21330:
                                                   var6 = new int[]{var129, 640266};
                                                   var7 = new int[]{1, 5};
                                                   break;
                                                case 21331:
                                                   var6 = new int[]{var129, 640266};
                                                   var7 = new int[]{1, 10};
                                                   break;
                                                case 21332:
                                                   var6 = new int[]{var129, 640266};
                                                   var7 = new int[]{1, 15};
                                                   break;
                                                case 21333:
                                                   var6 = new int[]{var129, 640266};
                                                   var7 = new int[]{1, 15};
                                                   break;
                                                case 21334:
                                                   var6 = new int[]{var129, 640266};
                                                   var7 = new int[]{1, 15};
                                                   break;
                                                case 21335:
                                                   var6 = new int[]{var129, 640266};
                                                   var7 = new int[]{1, 30};
                                                   break;
                                                case 21336:
                                                   var6 = new int[]{var129, 640266, 40308};
                                                   var7 = new int[]{1, 30, 500000};
                                                   break;
                                                case 21337:
                                                   var6 = new int[]{var129, 640266, 40308};
                                                   var7 = new int[]{1, 30, 750000};
                                                   break;
                                                case 21338:
                                                   var6 = new int[]{var129, 640266, 40308, 640556};
                                                   var7 = new int[]{1, 30, 1000000, 1};
                                             }

                                             var8 = new int[]{var129 + 1};
                                             var9 = new int[]{1};
                                             var10 = "";
                                          }
                                       }
                                    } else if (var17 == 190168) {
                                       int[] var65 = new int[0];
                                       if (var5.equalsIgnoreCase("4")) {
                                          var65 = new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115, 149};
                                       } else if (var5.equalsIgnoreCase("5")) {
                                          var65 = new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115, 148};
                                       } else if (var5.equalsIgnoreCase("6")) {
                                          var65 = new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115};
                                       }

                                       int[] var213 = var65;
                                       int var193 = var65.length;

                                       for (int var165 = 0; var165 < var193; var165++) {
                                          int var131 = var213[var165];
                                          L1SkillExecutor var221 = LineageUtil.a(var131);
                                          var221.a(var3, 0);
                                       }

                                       var10 = "";
                                    } else if (var17 == 190274) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          if (!var3.j().f(640557)
                                             && !var3.j().f(640558)
                                             && !var3.j().f(640559)
                                             && !var3.j().f(640564)
                                             && !var3.j().f(640565)
                                             && !var3.j().f(640566)
                                             && !var3.j().f(640567)
                                             && !var3.j().f(640568)
                                             && !var3.j().f(640569)
                                             && !var3.j().f(640570)
                                             && !var3.j().f(640571)
                                             && !var3.j().f(640572)
                                             && !var3.j().f(640573)) {
                                             ItemTable.a(var3, 640557, 1);
                                             var10 = "oldbook2";
                                          } else {
                                             var10 = "oldbook3";
                                          }
                                       }
                                    } else if (var17 == 190328) {
                                       L1SoulStone.a().a(var3, var5, (L1NpcInstance)var15);
                                    } else if (var17 == 190327) {
                                       if (var5.equalsIgnoreCase("1")) {
                                          L1SoulStone.a().a(var3, (L1NpcInstance)var15);
                                       } else if (var5.equalsIgnoreCase("2")) {
                                          L1SoulStone.a().a(var3, 4, (L1NpcInstance)var15);
                                       } else if (var5.equalsIgnoreCase("3")) {
                                          L1SoulStone.a().a(var3, 8, (L1NpcInstance)var15);
                                       } else if (var5.equalsIgnoreCase("4")) {
                                          L1SoulStone.a().a(var3, 16, (L1NpcInstance)var15);
                                       } else if (var5.equalsIgnoreCase("6")) {
                                          L1SoulStone.a().b(var3, (L1NpcInstance)var15);
                                       }
                                    } else if (var17 == 81401) {
                                       if (var5.equalsIgnoreCase("b")) {
                                          var3.bb().a(45, 1);
                                          if (!var3.j().f(640694)) {
                                             var8 = new int[]{640694};
                                             var9 = new int[]{100};
                                             var10 = "marbinquest2";
                                          } else {
                                             var10 = "marbinquest3";
                                          }
                                       } else if (var5.equalsIgnoreCase("c")) {
                                          L1ItemInstance var66 = var3.j().b(640694);
                                          if (var66 == null && var3.bb().a(45) > 0) {
                                             var3.bb().b(45);
                                             var10 = "marbinquest6";
                                          } else {
                                             var10 = "marbinquest7";
                                          }
                                       }
                                    } else if (var17 == 190492) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          if (var3.j().f(640786)) {
                                             if (!var3.bB(1032)) {
                                                var3.bH(15);
                                                var3.bJ(15);
                                                var3.F(1);
                                             }

                                             var3.j(1032, 7200000);
                                             var3.a(new S_SkillSound(var3.fr(), 14483));
                                             var3.b(new S_SkillSound(var3.fr(), 14483));
                                             var3.a(new S_ProtoBuffers(1032, 7200, 8, 7225, 0, 4621, 0, 0, 3));
                                          } else {
                                             var10 = "tw_vip6";
                                          }
                                       } else if (var5.equalsIgnoreCase("b")) {
                                          if (var3.j().f(640787)) {
                                             if (!var3.bB(1033)) {
                                                var3.bH(30);
                                                var3.bJ(30);
                                                var3.F(2);
                                             }

                                             var3.j(1033, 7200000);
                                             var3.a(new S_SkillSound(var3.fr(), 14483));
                                             var3.b(new S_SkillSound(var3.fr(), 14483));
                                             var3.a(new S_ProtoBuffers(1033, 7200, 8, 7899, 0, 5063, 0, 0, 3));
                                          } else {
                                             var10 = "tw_vip6";
                                          }
                                       } else if (var5.equalsIgnoreCase("c")) {
                                          if (var3.j().f(640788)) {
                                             if (!var3.bB(1034)) {
                                                var3.bH(45);
                                                var3.bJ(45);
                                                var3.F(3);
                                             }

                                             var3.j(1034, 7200000);
                                             var3.a(new S_SkillSound(var3.fr(), 14483));
                                             var3.b(new S_SkillSound(var3.fr(), 14483));
                                             var3.a(new S_ProtoBuffers(1034, 7200, 8, 7904, 0, 5064, 0, 0, 3));
                                          } else {
                                             var10 = "tw_vip6";
                                          }
                                       } else if (var5.equalsIgnoreCase("d")) {
                                          if (var3.j().f(640789)) {
                                             if (!var3.bB(1035)) {
                                                var3.bH(65);
                                                var3.bJ(65);
                                                var3.F(4);
                                             }

                                             var3.j(1035, 7200000);
                                             var3.a(new S_SkillSound(var3.fr(), 14483));
                                             var3.b(new S_SkillSound(var3.fr(), 14483));
                                             var3.a(new S_ProtoBuffers(1035, 7200, 8, 7901, 0, 5065, 0, 0, 3));
                                          } else {
                                             var10 = "tw_vip6";
                                          }
                                       } else if (var5.equalsIgnoreCase("e")) {
                                          if (var3.j().f(640790)) {
                                             if (!var3.bB(1036)) {
                                                var3.bH(100);
                                                var3.bJ(75);
                                                var3.F(5);
                                             }

                                             var3.j(1036, 7200000);
                                             var3.a(new S_SkillSound(var3.fr(), 14483));
                                             var3.b(new S_SkillSound(var3.fr(), 14483));
                                             var3.a(new S_ProtoBuffers(1036, 7200, 8, 7903, 0, 5066, 0, 0, 3));
                                          } else {
                                             var10 = "tw_vip6";
                                          }
                                       }
                                    } else if (var17 == 81290) {
                                       if (var5.equalsIgnoreCase("buy 7")) {
                                          if (var3.j().b(47011, 1)) {
                                             L1ItemInstance var67 = ItemTable.a(var3, 40314, 1, 0, true);
                                             if (var67 != null) {
                                                PetTable.a().a(97023, var67.fr());
                                                var3.a(new S_ItemName(var67));
                                             }
                                          } else {
                                             var3.a(new S_ServerMessage(337, "$7779(1)"));
                                          }
                                       } else if (var5.equalsIgnoreCase("buy 8")) {
                                          if (var3.j().b(47012, 1)) {
                                             L1ItemInstance var68 = ItemTable.a(var3, 40314, 1, 0, true);
                                             if (var68 != null) {
                                                PetTable.a().a(97022, var68.fr());
                                                var3.a(new S_ItemName(var68));
                                             }
                                          } else {
                                             var3.a(new S_ServerMessage(337, "$7780(1)"));
                                          }
                                       }
                                    } else if (var17 == 190886) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          if (var3.j().f(413)) {
                                             L1PolyMorph.b(var3);
                                             L1Teleport.a(var3, 32873, 32799, 6311, 5, true);
                                          } else {
                                             var10 = "tw_170308b1";
                                          }
                                       }
                                    } else if (var17 == 190926) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          if (!var3.j().f(40029)) {
                                             ItemTable.a(var3, 40029, 200, var16.T());
                                             var10 = "clgunter41";
                                          } else {
                                             var10 = "clgunter42";
                                          }
                                       }
                                    } else if (var17 == 190927) {
                                       if (var5.equalsIgnoreCase("a")) {
                                          var3.a(var3.ew());
                                          var3.i_(var3.ex());
                                          var3.a(new S_ServerMessage(77));
                                          var3.a(new S_SkillSound(var3.fr(), 830));
                                          new S_054().a(var3, 0);
                                          var10 = "";
                                       }
                                    } else if (var17 == 81371) {
                                       if (var5.equalsIgnoreCase("c")) {
                                          if (!var3.j().f(41701)) {
                                             var8 = new int[]{41701};
                                             var9 = new int[]{1};
                                             var10 = "j_html00";
                                          } else {
                                             var10 = "j_html03";
                                          }
                                       } else if (var5.equalsIgnoreCase("a")) {
                                          if (var3.j().f(41703) && var3.j().g(40308, 10000)) {
                                             var3.j().b(40308, 10000);
                                             L1ItemInstance var69 = var3.j().b(41703);
                                             if (var69.E() > 3) {
                                                var3.j().b(41703, var69.E() - 3);
                                             } else {
                                                var3.j().b(41703, 1);
                                             }

                                             var3.bb().a(43, 1);
                                             L1Teleport.a(var3, 32735, 32865, 9100, 5, true);
                                          } else {
                                             var10 = "j_html02";
                                          }
                                       } else if (var5.equalsIgnoreCase("b")) {
                                          if (var3.j().f(41702) && var3.j().g(40308, 10000)) {
                                             var3.j().b(40308, 10000);
                                             L1ItemInstance var70 = var3.j().b(41702);
                                             if (var70.E() > 3) {
                                                var3.j().b(41702, var70.E() - 3);
                                             } else {
                                                var3.j().b(41702, 1);
                                             }

                                             var3.bb().a(42, 1);
                                             L1Teleport.a(var3, 32735, 32865, 9100, 5, true);
                                          } else {
                                             var10 = "j_html02";
                                          }
                                       }
                                    } else if (var17 == 81372) {
                                       if (var5.equalsIgnoreCase("enter")) {
                                          if (var3.l()) {
                                             int var72 = 43;

                                             for (L1PcInstance var134 : var3.aL().c()) {
                                                L1Teleport.a(var134, 32727, 32724, 9000, 5, true);
                                             }

                                             L1HardinBattle.a().a(10);
                                             return;
                                          }

                                          if (var3.am()) {
                                             int var71 = 43;
                                             if (var3.bb().a(42) > 0) {
                                                var71 = 42;
                                             }

                                             if (var71 == 42 && L1OrimBattle.a().a) {
                                                boolean var133 = true;

                                                for (L1PcInstance var168 : var3.aL().c()) {
                                                   if (var168.fp() != 9100 || var168.bb().a(var71) == 0) {
                                                      var133 = false;
                                                      break;
                                                   }
                                                }

                                                if (var133 && var3.aL().b() >= 3) {
                                                   for (L1PcInstance var169 : var3.aL().c()) {
                                                      L1Teleport.a(var169, 32799, 32803, 9101, 5, true);
                                                   }

                                                   L1OrimBattle.a().a(10);
                                                } else {
                                                   var10 = "id1_1";
                                                }
                                             } else if (var71 == 43 && L1HardinBattle.a().b) {
                                                boolean var132 = true;

                                                for (L1PcInstance var166 : var3.aL().c()) {
                                                   if (var166.fp() != 9100 || var166.bb().a(var71) == 0) {
                                                      var132 = false;
                                                      break;
                                                   }
                                                }

                                                if (var132 && var3.aL().b() >= 5) {
                                                   for (L1PcInstance var167 : var3.aL().c()) {
                                                      L1Teleport.a(var167, 32727, 32724, 9000, 5, true);
                                                   }

                                                   L1HardinBattle.a().a(10);
                                                } else {
                                                   var10 = "id0_1";
                                                }
                                             } else {
                                                var10 = "id14";
                                             }
                                          } else {
                                             var10 = "id1_2";
                                          }
                                       }
                                    } else if (var5.equalsIgnoreCase("room")) {
                                       int var73 = InnTable.a().a(var3, var17, false);
                                       if (var73 == -3) {
                                          var10 = "inn6";
                                       } else if (var73 == -1) {
                                          var10 = "inn5";
                                       } else if (var73 == -2) {
                                          var10 = "inn15";
                                       } else {
                                          var3.br(var73);
                                          var3.a(new S_HowManyKey(var16, 300, 1, 8, "inn2"));
                                       }
                                    } else if (var5.equalsIgnoreCase("hall")) {
                                       if (var3.x()) {
                                          int var74 = InnTable.a().a(var3, var17, true);
                                          if (var74 == -3) {
                                             var10 = "inn6";
                                          } else if (var74 == -1) {
                                             var10 = "inn5";
                                          } else if (var74 == -2) {
                                             var10 = "inn15";
                                          } else {
                                             var3.br(var74);
                                             var3.a(new S_HowManyKey(var16, 1760, 10, 40, "inn12"));
                                          }
                                       } else {
                                          var10 = "inn10";
                                       }
                                    } else if (var5.equalsIgnoreCase("return")) {
                                       int var75 = InnTable.a().b(var3);
                                       if (var75 > 0) {
                                          var13 = new String[]{var16.et(), String.valueOf(var75)};
                                          var10 = "inn20";
                                          ItemTable.a(var3, 40308, var75, var16.T());
                                       } else {
                                          var10 = "";
                                       }
                                    } else if (var5.equalsIgnoreCase("enter")) {
                                       InnTable.a().a(var3);
                                    }
                                 } else if (!var5.equals("a") && !var5.equals("b")) {
                                    if (var5.equalsIgnoreCase("0")) {
                                       var10 = "bs_01";
                                    } else if (var5.equalsIgnoreCase("1")) {
                                       var3.bs(1);
                                       var10 = "bs_m4";
                                       var13 = new String[]{"500", "1000", "1000", "2000", "2000", "1"};
                                    } else if (var5.equalsIgnoreCase("2")) {
                                       var3.bs(5);
                                       var10 = "bs_m4";
                                       var13 = new String[]{"2500", "5000", "5000", "10000", "10000", "5"};
                                    } else if (var5.equalsIgnoreCase("3")) {
                                       var3.bs(10);
                                       var10 = "bs_m4";
                                       var13 = new String[]{"5000", "10000", "10000", "20000", "20000", "10"};
                                    } else if (var5.equalsIgnoreCase("4")) {
                                       var3.bs(100);
                                       var10 = "bs_m4";
                                       var13 = new String[]{"50000", "100000", "100000", "200000", "200000", "100"};
                                    } else if (var5.equalsIgnoreCase("5")) {
                                       var3.bs(500);
                                       var10 = "bs_m4";
                                       var13 = new String[]{"250000", "500000", "500000", "1000000", "1000000", "500"};
                                    } else if (var5.getBytes()[0] >= "A".getBytes()[0] && var5.getBytes()[0] <= "Y".getBytes()[0] + 19) {
                                       int[] var49 = new int[]{2, 3, 4, 8, 1, 14, 13, 12, 9, 15, 17, 21, 19, 22, 18, 32, 25, 26, 31, 29, 35, 37, 39, 38, 34};
                                       int var119 = var49[var5.getBytes()[0] - 65];
                                       L1Skills var162 = SkillsTable.a().a(var119);
                                       if (var162.f() != 0 && !var3.j().g(var162.f(), var162.g() * var3.dO())) {
                                          var3.a(new S_Html(var4, "bs_m6"));
                                          return;
                                       }

                                       if (!var3.j().g(40089 + var162.c(), var3.dO())) {
                                          var3.a(new S_Html(var4, "bs_m6"));
                                          return;
                                       }

                                       int[] var191 = new int[]{0, 500, 1000, 1000, 2000, 2000};
                                       if (!var3.j().g(40308, var191[var162.c()] * var3.dO())) {
                                          var3.a(new S_Html(var4, "bs_m6"));
                                          return;
                                       }

                                       ItemTable.a(var3, 40858 + var119, var3.dO());
                                       var3.j().b(40308, var191[var162.c()] * var3.dO());
                                       var3.j().b(40089 + var162.c(), var3.dO());
                                       if (var162.f() != 0) {
                                          var3.j().b(var162.f(), var162.g() * var3.dO());
                                       }

                                       var10 = "bs_m1";
                                    }
                                 } else {
                                    int[] var48;
                                    if (var5.equals("b")) {
                                       var48 = new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115, 149};
                                    } else {
                                       var48 = new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115, 148};
                                    }

                                    if (!var3.j().b(40308, 3000)) {
                                       var3.a(new S_Html(var4, "bs_adena"));
                                       return;
                                    }

                                    int[] var211 = var48;
                                    int var190 = var48.length;

                                    for (int var161 = 0; var161 < var190; var161++) {
                                       int var118 = var211[var161];
                                       L1SkillExecutor var220 = LineageUtil.a(var118);
                                       var220.a(var3, 0);
                                    }

                                    var10 = "bs_done";
                                 }
                              } else if (var5.equalsIgnoreCase("status")) {
                                 var13 = L1BugBearRace.a().c();
                                 var10 = "maeno4";
                              }
                           }
                        }

                        if (var8 != null) {
                           boolean var106 = true;
                           if (var6 != null && var7 != null) {
                              for (int var154 = 0; var154 < var6.length; var154++) {
                                 if (!var3.j().h(var6[var154], var7[var154])) {
                                    L1Item var187 = ItemTable.a().a(var6[var154]);
                                    var3.a(new S_ServerMessage(337, var187.h() + "(" + var7[var154] + ")"));
                                    var106 = false;
                                 }
                              }
                           }

                           if (var106 && var9 != null) {
                              int var155 = 0;
                              int var188 = 0;

                              for (int var207 = 0; var207 < var8.length; var207++) {
                                 if (var8[var207] > 0 && var9[var207] > 0) {
                                    L1Item var218 = ItemTable.a().a(var8[var207]);
                                    if (var218 != null) {
                                       if (var218.aF()) {
                                          if (!var3.j().f(var8[var207])) {
                                             var155++;
                                          }
                                       } else {
                                          var155 += var9[var207];
                                       }

                                       var188 += var218.l() * var9[var207] / 1000;
                                    }
                                 }
                              }

                              if (var3.j().c() + var155 > 180) {
                                 var3.a(new S_ServerMessage(263));
                                 return;
                              }

                              if (var3.K() < var3.j().e() + var188) {
                                 var3.a(new S_ServerMessage(82));
                                 return;
                              }

                              if (var6 != null && var7 != null) {
                                 for (int var208 = 0; var208 < var6.length; var208++) {
                                    var3.j().b(var6[var208], var7[var208]);
                                 }
                              }

                              for (int var209 = 0; var209 < var8.length; var209++) {
                                 if (var8[var209] > 0 && var9[var209] > 0) {
                                    ItemTable.a(var3, var8[var209], var9[var209], var16.T());
                                 }
                              }

                              if (var11 != null) {
                                 var3.a(new S_Html(var4, var11, var13));
                              }

                              if (var14 > 0) {
                                 var3.u(var14);
                              }
                           } else if (var12 != null) {
                              var3.a(new S_Html(var4, var12, var13));
                           }
                        }

                        if (var10 != null) {
                           var3.a(new S_Html(var4, var10, var13));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private String b(int var1) {
      if (var1 != 0 && var1 >= -7 && 7 >= var1) {
         String var2 = "";
         if (var1 > 0) {
            var2 = "vbk" + var1;
         } else if (var1 < 0) {
            var2 = "vyk" + Math.abs(var1);
         }

         return var2;
      } else {
         return "";
      }
   }

   private void a(ClientThread var1, int var2) {
      L1PcInstance var3 = var1.f();
      if (var3.j().g(40308, 100)) {
         var3.j().b(40308, 100);
         L1PolyMorph.a(var3, var2, 1800, 4);
      } else {
         var3.a(new S_ServerMessage(337, "$4"));
      }
   }

   private void b(ClientThread var1, int var2) {
      L1PcInstance var3 = var1.f();
      if (var3.j().g(40308, 100)) {
         var3.j().b(40308, 100);
         L1PolyMorph.a(var3, var2, 1800, 8);
      } else {
         var3.a(new S_ServerMessage(337, "$4"));
      }
   }

   private String a(L1PcInstance var1, int var2, int var3) {
      L1Clan var4 = ClanTable.a().a(var1.aF());
      if (var4 == null) {
         return "";
      }

      int var5 = var4.n();
      if (var5 == 0) {
         return "";
      }

      L1House var6 = HouseTable.a().a(var5);
      int var7 = var6.f();
      if (var3 != var7) {
         return "";
      }

      if (!var1.x()) {
         var1.a(new S_ServerMessage(518));
         return "";
      }

      if (var1.fr() != var4.k()) {
         var1.a(new S_ServerMessage(518));
         return "";
      }

      if (var6.g()) {
         return "agonsale";
      }

      var1.setL1rAmountContext(var2, 2);
      var1.a(new S_SellHouse(var2, String.valueOf(var5)));
      return null;
   }

   private void a(L1PcInstance var1, L1NpcInstance var2, String var3) {
      L1Clan var4 = ClanTable.a().a(var1.aF());
      if (var4 != null) {
         int var5 = var4.n();
         if (var5 != 0) {
            L1House var6 = HouseTable.a().a(var5);
            int var7 = var6.f();
            if (var2.z() == var7) {
               L1DoorInstance var8 = null;
               L1DoorInstance var9 = null;
               L1DoorInstance var10 = null;
               L1DoorInstance var11 = null;
               L1DoorInstance[] var15;
               int var14 = (var15 = DoorTable.b().c()).length;

               for (int var13 = 0; var13 < var14; var13++) {
                  L1DoorInstance var12 = var15[var13];
                  if (var12.p() == var7) {
                     if (var8 == null) {
                        var8 = var12;
                     } else if (var9 == null) {
                        var9 = var12;
                     } else {
                        if (var10 != null) {
                           var11 = var12;
                           break;
                        }

                        var10 = var12;
                     }
                  }
               }

               if (var8 != null) {
                  if (var3.equalsIgnoreCase("open")) {
                     var8.f();
                  } else if (var3.equalsIgnoreCase("close")) {
                     var8.g();
                  }
               }

               if (var9 != null) {
                  if (var3.equalsIgnoreCase("open")) {
                     var9.f();
                  } else if (var3.equalsIgnoreCase("close")) {
                     var9.g();
                  }
               }

               if (var10 != null) {
                  if (var3.equalsIgnoreCase("open")) {
                     var10.f();
                  } else if (var3.equalsIgnoreCase("close")) {
                     var10.g();
                  }
               }

               if (var11 != null) {
                  if (var3.equalsIgnoreCase("open")) {
                     var11.f();
                  } else if (var3.equalsIgnoreCase("close")) {
                     var11.g();
                  }
               }
            }
         }
      }
   }

   private void a(L1PcInstance var1, int var2, boolean var3) {
      boolean var4 = false;
      int var5 = 0;
      if (var1.aF() != 0) {
         L1Clan var6 = ClanTable.a().a(var1.aF());
         if (var6 != null) {
            var5 = var6.m();
         }
      }

      if (var2 != 70656 && var2 != 70549 && var2 != 70985) {
         if (var2 == 70600) {
            if (this.c(2) && var5 != 2) {
               return;
            }

            var4 = L1CastleWar.a().a(2);
         } else if (var2 != 70778 && var2 != 70987 && var2 != 70687) {
            if (var2 != 70817 && var2 != 70800 && var2 != 70988 && var2 != 70990 && var2 != 70989 && var2 != 70991) {
               if (var2 != 70863 && var2 != 70992 && var2 != 70862) {
                  if (var2 != 70995 && var2 != 70994 && var2 != 70993) {
                     if (var2 == 70996) {
                        if (this.c(7) && var5 != 7) {
                           return;
                        }

                        var4 = L1CastleWar.a().a(7);
                     }
                  } else {
                     if (this.c(6) && var5 != 6) {
                        return;
                     }

                     var4 = L1CastleWar.a().a(6);
                  }
               } else {
                  if (this.c(5) && var5 != 5) {
                     return;
                  }

                  var4 = L1CastleWar.a().a(5);
               }
            } else {
               if (this.c(4) && var5 != 4) {
                  return;
               }

               var4 = L1CastleWar.a().a(4);
            }
         } else {
            if (this.c(3) && var5 != 3) {
               return;
            }

            var4 = L1CastleWar.a().a(3);
         }
      } else {
         if (this.c(1) && var5 != 1) {
            return;
         }

         var4 = L1CastleWar.a().a(1);
      }

      L1DoorInstance[] var9;
      int var8 = (var9 = DoorTable.b().c()).length;

      for (int var7 = 0; var7 < var8; var7++) {
         L1DoorInstance var10 = var9[var7];
         if (var10.p() == var2 && (!var4 || var10.ew() <= 1)) {
            if (var3) {
               var10.f();
            } else {
               var10.g();
            }
         }
      }
   }

   private boolean c(int var1) {
      boolean var2 = false;

      for (L1Clan var3 : ClanTable.a().b().values()) {
         if (var1 == var3.m()) {
            var2 = true;
            break;
         }
      }

      return var2;
   }

   private void a(L1PcInstance var1, int var2) {
      int var3 = 0;

      for (L1House var4 : HouseTable.a().c().values()) {
         if (var4.f() == var2) {
            var3 = var4.b();
         }
      }

      if (var3 != 0) {
         int[] var8 = new int[3];

         for (L1Object var10 : L1World.a().b()) {
            if (var10 instanceof L1PcInstance) {
               L1PcInstance var7 = (L1PcInstance)var10;
               if (L1HouseLocation.a(var3, var7.fs(), var7.ft(), var7.fp()) && var1.aF() != var7.aF()) {
                  var8 = L1HouseLocation.a(var3, 0);
                  L1Teleport.a(var7, var8[0], var8[1], var8[2], 5, true);
               }
            }
         }
      }
   }

   private void a(L1PcInstance var1) {
      L1Clan var2 = ClanTable.a().a(var1.aF());
      if (var2 != null) {
         int var3 = var2.m();
         if (var3 != 0) {
            if (!L1CastleWar.a().a(var3)) {
               L1DoorInstance[] var7;
               int var6 = (var7 = DoorTable.b().c()).length;

               for (int var5 = 0; var5 < var6; var5++) {
                  L1DoorInstance var4 = var7[var5];
                  if (L1CastleLocation.a(var3, var4)) {
                     var4.h();
                  }
               }

               var1.a(new S_ServerMessage(990));
            } else {
               var1.a(new S_ServerMessage(991));
            }
         }
      }
   }

   private boolean a(L1PcInstance var1, L1NpcInstance var2) {
      L1Clan var3 = ClanTable.a().a(var1.aF());
      if (var3 != null) {
         int var4 = var3.n();
         if (var4 != 0) {
            L1House var5 = HouseTable.a().a(var4);
            int var6 = var5.f();
            if (var2.z() == var6) {
               int var7 = (int)((var5.i().getTime() - System.currentTimeMillis()) / 86400000L);
               if (var7 >= Config.an / 2) {
                  var1.a(new S_ServerMessage(1729));
               } else {
                  if (var1.j().g(40308, 2000)) {
                     var1.j().b(40308, 2000);
                     Timestamp var8 = new Timestamp(System.currentTimeMillis() + Config.an * 24 * 60 * 60 * 1000L);
                     var5.a(var8);
                     HouseTable.a().a(var5);
                     return true;
                  }

                  var1.a(new S_ServerMessage(189));
               }
            }
         }
      }

      return false;
   }

   private String[] b(L1PcInstance var1, L1NpcInstance var2) {
      String var3 = var2.U_().c();
      String[] var4 = new String[]{var3, "2000", "1", "1", "00"};
      L1Clan var5 = ClanTable.a().a(var1.aF());
      if (var5 != null) {
         int var6 = var5.n();
         if (var6 != 0) {
            L1House var7 = HouseTable.a().a(var6);
            int var8 = var7.f();
            if (var2.z() == var8) {
               Calendar var9 = Calendar.getInstance();
               var9.setTime(new Date(var7.i().getTime()));
               int var10 = var9.get(2) + 1;
               int var11 = var9.get(5);
               int var12 = var9.get(11);
               var4 = new String[]{var3, "2000", String.valueOf(var10), String.valueOf(var11), String.valueOf(var12)};
            }
         }
      }

      return var4;
   }

   private String[] d(int var1) {
      L1Castle var2 = CastleTable.a().a(var1);
      if (var2 == null) {
         return null;
      }

      Calendar var3 = var2.c();
      int var4 = var3.get(1);
      int var5 = var3.get(2) + 1;
      int var6 = var3.get(5);
      int var7 = var3.get(11);
      int var8 = var3.get(12);
      String[] var9;
      if (var1 == 2) {
         var9 = new String[]{String.valueOf(var4), String.valueOf(var5), String.valueOf(var6), String.valueOf(var7), String.valueOf(var8)};
      } else {
         var9 = new String[]{"", String.valueOf(var4), String.valueOf(var5), String.valueOf(var6), String.valueOf(var7), String.valueOf(var8)};
      }

      return var9;
   }

   private void b(L1PcInstance var1, L1NpcInstance var2, String var3) {
      int[] var4 = new int[]{20358, 20359, 20360, 20361, 20362, 20363, 20364, 20365};
      int var5 = 0;
      if (var3.equalsIgnoreCase("1")) {
         var5 = var4[0];
      } else if (var3.equalsIgnoreCase("2")) {
         var5 = var4[1];
      } else if (var3.equalsIgnoreCase("3")) {
         var5 = var4[2];
      } else if (var3.equalsIgnoreCase("4")) {
         var5 = var4[3];
      } else if (var3.equalsIgnoreCase("5")) {
         var5 = var4[4];
      } else if (var3.equalsIgnoreCase("6")) {
         var5 = var4[5];
      } else if (var3.equalsIgnoreCase("7")) {
         var5 = var4[6];
      } else if (var3.equalsIgnoreCase("8")) {
         var5 = var4[7];
      }

      if (var5 != 0) {
         ItemTable.a(var1, var5, 1, var2.T());
         int[] var9 = var4;
         int var8 = var4.length;

         for (int var7 = 0; var7 < var8; var7++) {
            int var6 = var9[var7];
            if (var6 != var5 && var1.j().f(var6)) {
               var1.j().b(var6, 1);
            }
         }
      }
   }

   private void c(L1PcInstance var1, L1NpcInstance var2, String var3) {
      int[] var4 = new int[]{21020, 21021, 21022, 21023, 21024, 21025, 21026, 21027};
      int var5 = 0;
      if (var3.equalsIgnoreCase("1")) {
         var5 = var4[0];
      } else if (var3.equalsIgnoreCase("2")) {
         var5 = var4[1];
      } else if (var3.equalsIgnoreCase("3")) {
         var5 = var4[2];
      } else if (var3.equalsIgnoreCase("4")) {
         var5 = var4[3];
      } else if (var3.equalsIgnoreCase("5")) {
         var5 = var4[4];
      } else if (var3.equalsIgnoreCase("6")) {
         var5 = var4[5];
      } else if (var3.equalsIgnoreCase("7")) {
         var5 = var4[6];
      } else if (var3.equalsIgnoreCase("8")) {
         var5 = var4[7];
      }

      if (var5 != 0) {
         ItemTable.a(var1, var5, 1, var2.T());
         int[] var9 = var4;
         int var8 = var4.length;

         for (int var7 = 0; var7 < var8; var7++) {
            int var6 = var9[var7];
            if (var6 != var5 && var1.j().f(var6)) {
               var1.j().b(var6, 1);
            }
         }
      }
   }

   private String d(L1PcInstance var1, L1NpcInstance var2, String var3) {
      String var4 = "";
      int var5 = 0;
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;
      short var9 = 0;
      if (var2.z() == 80059) {
         var5 = 40909;
         var6 = 40913;
         var7 = 32773;
         var8 = 32835;
         var9 = 607;
      } else if (var2.z() == 80060) {
         var5 = 40912;
         var6 = 40916;
         var7 = 32757;
         var8 = 32842;
         var9 = 606;
      } else if (var2.z() == 80061) {
         var5 = 40910;
         var6 = 40914;
         var7 = 32830;
         var8 = 32822;
         var9 = 604;
      } else if (var2.z() == 80062) {
         var5 = 40911;
         var6 = 40915;
         var7 = 32835;
         var8 = 32822;
         var9 = 605;
      }

      if (var3.equalsIgnoreCase("a")) {
         L1Teleport.a(var1, var7, var8, var9, 5, true);
         var4 = "";
      } else if (var3.equalsIgnoreCase("b")) {
         ItemTable.a(var1, var5, 1, var2.T());
         var4 = "";
      } else if (var3.equalsIgnoreCase("c")) {
         var4 = "wpass07";
      } else if (var3.equalsIgnoreCase("d")) {
         if (var1.j().f(var6)) {
            L1ItemInstance var10 = var1.j().b(var6);
            var1.j().b(var6, var10.E());
         }
      } else if (var3.equalsIgnoreCase("e")) {
         var4 = "";
      } else if (var3.equalsIgnoreCase("f")) {
         if (var1.j().f(var5)) {
            var1.j().b(var5, 1);
         }

         if (var1.j().f(var6)) {
            L1ItemInstance var11 = var1.j().b(var6);
            var1.j().b(var6, var11.E());
         }

         var4 = "";
      }

      return var4;
   }

   private void e(L1PcInstance var1, L1NpcInstance var2, String var3) {
      if (var3.equalsIgnoreCase("1")) {
         var1.B((int)(500.0 * Config.D));
         ItemTable.a(var1, 40718, 1, var2.T());
         var1.a(new S_ServerMessage(1081));
      } else if (var3.equalsIgnoreCase("2")) {
         var1.B((int)(5000.0 * Config.D));
         ItemTable.a(var1, 40718, 10, var2.T());
         var1.a(new S_ServerMessage(1081));
      } else if (var3.equalsIgnoreCase("3")) {
         var1.B((int)(50000.0 * Config.D));
         ItemTable.a(var1, 40718, 100, var2.T());
         var1.a(new S_ServerMessage(1081));
      }
   }

   private void f(L1PcInstance var1, L1NpcInstance var2, String var3) {
      if (var3.equalsIgnoreCase("1")) {
         var1.B((int)(-500.0 * Config.D));
         ItemTable.a(var1, 40678, 1, var2.T());
         var1.a(new S_ServerMessage(1080));
      } else if (var3.equalsIgnoreCase("2")) {
         var1.B((int)(-5000.0 * Config.D));
         ItemTable.a(var1, 40678, 10, var2.T());
         var1.a(new S_ServerMessage(1080));
      } else if (var3.equalsIgnoreCase("3")) {
         var1.B((int)(-50000.0 * Config.D));
         ItemTable.a(var1, 40678, 100, var2.T());
         var1.a(new S_ServerMessage(1080));
      }
   }

   @Override
   public String a() {
      return "C_NpcAction";
   }
}
