package l1r.aj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.AccountTable;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.ao.HistoryTable;
import l1r.ao.ItemTable;
import l1r.ao.LostPowerItemTable;
import l1r.ao.LuckyDrawTable;
import l1r.ao.NpcTable;
import l1r.ao.PetTable;
import l1r.ao.ResolventTable;
import l1r.ao.SkillsTable;
import l1r.ao.SpawnTable;
import l1r.ao.TreasureBoxTable;
import l1r.ap.L1DotaInstance;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1GuardianInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1Clan;
import l1r.aq.L1Cooking;
import l1r.aq.L1Getback;
import l1r.aq.L1HouseLocation;
import l1r.aq.L1ItemQuestBuff;
import l1r.aq.L1Location;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1Teleport;
import l1r.aq.L1TownLocation;
import l1r.aq.L1World;
import l1r.as.L1CastleWar;
import l1r.as.L1Dragon;
import l1r.au.L1PcInventory;
import l1r.av.L1ItemDelay;
import l1r.aw.Enchant;
import l1r.aw.FurnitureItem;
import l1r.aw.MagicDoll;
import l1r.aw.Potion;
import l1r.az.L1DamagePoison;
import l1r.bc.FishingTimer;
import l1r.be.S_AddSkill;
import l1r.be.S_AttackPacket;
import l1r.be.S_ChatPacket;
import l1r.be.S_DoActionGFX;
import l1r.be.S_Fishing;
import l1r.be.S_Html;
import l1r.be.S_IdentifyDesc;
import l1r.be.S_ItemName;
import l1r.be.S_Liquor;
import l1r.be.S_Message_YN;
import l1r.be.S_OwnCharAttrDef;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_OwnCharStatus2;
import l1r.be.S_PacketBox;
import l1r.be.S_Paralysis;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_RuneSlot;
import l1r.be.S_SPMR;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_Sound;
import l1r.be.S_SystemMessage;
import l1r.bf.L1SkillExecutor;
import l1r.bf.S_005;
import l1r.bf.S_061;
import l1r.bf.S_069;
import l1r.bf.S_075;
import l1r.bh.L1Account;
import l1r.bh.L1BookMark;
import l1r.bh.L1Item;
import l1r.bh.L1Npc;
import l1r.bh.L1Pet;
import l1r.bh.L1QuestNew;
import l1r.bh.L1Skills;
import l1r.bi.GeneralThreadPool;
import l1r.bi.LineageUtil;
import l1r.bi.Point;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.bj.ClientThread;
import l1r.l1j.server.DatabaseFactory;

public class C_ItemUSe extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_ItemUSe.class.getName());
   private static final String b = "[C] C_ItemUSe";

   public C_ItemUSe(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN() && !var3.eX()) {
         int var4 = this.b();
         L1ItemInstance var5 = var3.j().e(var4);
         if (var5 != null && !var3.aR() && var3.ea() > 0) {
            if (!var3.fq().p()) {
               var3.a(new S_ServerMessage(563));
            } else if ((var5.a().aP() == 6 || var5.a().aP() >= 23 && var5.a().aP() <= 27) && var3.bB(71)) {
               var3.a(new S_ServerMessage(698));
            } else {
               int var6 = 0;
               String var7 = "";
               int var8 = 0;
               int var9 = 0;
               int var10 = 0;
               int var11 = 0;
               int var12 = 0;
               int var13 = 0;
               int var14 = 0;
               int var15 = 0;
               int var16 = 0;
               int var17 = 0;
               int var18 = 0;
               int var19 = 0;
               int var20 = var5.N();
               int var21 = var5.a().U();
               int var22 = 0;
               int var23 = 0;
               switch (var21) {
                  case 0:
                     if (LostPowerItemTable.a().b().containsKey(var20)) {
                        var3.a(new S_ServerMessage(4963));
                     } else {
                        var3.a(new S_ServerMessage(74, var5.s()));
                     }

                     return;
                  case 5:
                  case 17:
                     var13 = this.b();
                     var14 = this.d();
                     var15 = this.d();
                     break;
                  case 6:
                  case 29:
                     var9 = this.d();
                     int var24 = this.d();
                     int var25 = this.d();
                     L1BookMark var26 = var3.a(var24, var25);
                     if (var26 != null) {
                        var10 = var26.a();
                     }
                     break;
                  case 7:
                  case 14:
                  case 26:
                  case 27:
                  case 46:
                  case 55:
                     var6 = this.b();
                     break;
                  case 8:
                  case 30:
                     try {
                        var13 = this.b();
                     } catch (ArrayIndexOutOfBoundsException var50) {
                        var13 = 0;
                     }
                     break;
                  case 16:
                  case 61:
                     var7 = this.g();
                     break;
                  case 28:
                     var12 = this.c() + 1;
                     break;
                  case 42:
                  case 50:
                     var18 = this.d();
                     var19 = this.d();
                     break;
                  case 52:
                     var16 = this.c();
                     var17 = this.c();
                     break;
                  case 56:
                     var11 = this.c();
                     break;
                  case 59:
                  case 60:
                     var7 = this.g();
                     var8 = this.d();
                     break;
                  case 62:
                  case 65:
                     var22 = this.b();
                     break;
                  case 68:
                     var23 = this.b();
               }

               boolean var52 = false;
               if (var5.f()) {
                  int var53 = var5.a().aJ();
                  if (var53 != 0 && var3.bF(var53)) {
                     var3.a(new S_ServerMessage(3898, "" + var5.a().aK() / 1000));
                     if (var21 == 9 || var21 == 6 || var21 == 29) {
                        var3.a(new S_Paralysis(7, false));
                     }

                     return;
                  }

                  int var55 = var5.a().aL();
                  if (var55 > 0) {
                     var52 = true;
                     Timestamp var27 = var5.J();
                     if (var27 != null) {
                        Calendar var28 = Calendar.getInstance();
                        int var29 = (int)((var28.getTimeInMillis() - var27.getTime()) / 1000L);
                        if (var29 <= var55) {
                           var3.a(new S_ServerMessage(3898, "" + (var55 - var29)));
                           if (var21 == 9 || var21 == 6 || var21 == 29) {
                              var3.a(new S_Paralysis(7, false));
                           }

                           return;
                        }
                     }
                  }
               }

               L1ItemInstance var54 = var3.j().e(var6);
               int var56 = var5.a().o();
               int var57 = var5.a().p();
               if (var56 != 0 && var56 > var3.ev()) {
                  var3.a(new S_ServerMessage(318, String.valueOf(var56)));
                  if (var21 == 9 || var21 == 6 || var21 == 29) {
                     var3.a(new S_Paralysis(7, false));
                  }
               } else if (var57 != 0 && var57 < var3.ev()) {
                  var3.a(new S_PacketBox(12, var57));
                  if (var21 == 9 || var21 == 6 || var21 == 29) {
                     var3.a(new S_Paralysis(7, false));
                  }
               } else {
                  if (!var5.g() && !var5.h()) {
                     if (var5.f()) {
                        if (var5.a().aP() == 0 || var5.a().aP() == 15) {
                           var3.j().k(var5);
                           var3.a(new S_ServerMessage(452, var5.s()));
                        } else if (var5.a().aP() == 16) {
                           if (var20 == 40576 && !var3.A() || var20 == 40577 && !var3.B() || var20 == 40578 && !var3.z()) {
                              var3.a(new S_ServerMessage(264));
                              return;
                           }

                           if (TreasureBoxTable.a().a(var20, var3)) {
                              if (var5.a().aL() > 0) {
                                 if (var5.d()) {
                                    if (var5.E() > 1) {
                                       var52 = true;
                                    }

                                    var3.j().c(var5.fr(), 1);
                                 } else {
                                    var52 = true;
                                 }
                              } else {
                                 var3.j().c(var5.fr(), 1);
                              }
                           }
                        } else if (var5.a().aP() == 2) {
                           if (var5.M() <= 0 && var20 != 40004) {
                              return;
                           }

                           if (var5.T()) {
                              var5.c(false);
                              var3.fg();
                           } else {
                              var5.c(true);
                              var3.fg();
                           }

                           var3.a(new S_ItemName(var5));
                        } else if (var5.a().aP() == 5) {
                           int var143 = var5.a().V();
                           var3.a(new S_SkillSound(var3.fr(), var143));
                           var3.b(new S_SkillSound(var3.fr(), var143));
                           var3.j().b(var5, 1);
                        } else if (var5.a().aP() == 7) {
                           int var142 = var5.a().V() / 10;
                           var3.c_(Math.min(var3.fj() + var142, 225));
                           var3.a(new S_PacketBox(11, var3.fj()));
                           var3.a(new S_ServerMessage(76, var5.b()));
                           if (var20 == 40057) {
                              var3.j(1012, 0);
                              var3.a(new S_ServerMessage(152));
                           }

                           var3.j().b(var5, 1);
                        } else if (var5.a().aP() == 17 || var5.a().aP() == 22) {
                           MagicDoll.a(var3, var5);
                        } else if (var5.a().aP() == 18) {
                           if (var20 == 41401) {
                              FurnitureItem.a(var3, var13, var5);
                           } else {
                              FurnitureItem.a(var3, var5);
                           }
                        } else if (var5.a().aP() >= 23 && var5.a().aP() <= 25) {
                           Potion.a(var3, var5);
                        } else if (var5.a().aP() == 26) {
                           Potion.c(var3, var5);
                        } else if (var5.a().aP() == 27) {
                           Potion.b(var3, var5);
                        } else if (var5.a().aP() == 28) {
                           int var141 = var5.a().V();
                           L1SkillExecutor var201 = LineageUtil.a(var141);
                           if (var201.a(var3, var13, var141)) {
                              var201.a(var3, var13, var14, var15, null);
                              var3.j().b(var5, 1);
                           } else {
                              var3.a(new S_ServerMessage(281));
                           }
                        } else if (var5.a().aP() == 29) {
                           L1Cooking.a(var3, var5);
                        } else if (var5.a().aP() == 30) {
                           this.f(var3, var5);
                        } else if (var5.a().aP() == 31) {
                           if ((var20 < 41429 || var20 > 41432) && (var20 < 640150 || var20 > 640153)) {
                              if (var20 >= 640157 && var20 <= 640160) {
                                 Enchant.d(var3, var5, var54);
                              } else {
                                 Enchant.b(var3, var5, var54);
                              }
                           } else {
                              Enchant.e(var3, var5, var54);
                           }
                        } else if (var5.a().aP() == 32) {
                           Enchant.c(var3, var5, var54);
                        } else if (var5.a().U() == 8) {
                           L1SkillExecutor var140 = var5.F() == 0 ? new S_075() : new S_061();
                           var140.a(var3, var13, 0, 0, "res");
                           var3.j().b(var5, 1);
                        } else if (var5.a().U() != 16 && var5.a().U() != 61) {
                           if (var5.a().U() == 28) {
                              this.a(var3, var5, var12);
                           } else if (var5.a().U() == 6 || var5.a().U() == 29) {
                              L1SkillExecutor var139 = var20 == 40086 ? new S_069() : new S_005();
                              var139.a(var3, var10, 0, 0, null);
                              var3.j().b(var5, 1);
                           } else if (var20 == 640105 || var20 == 640106) {
                              int var138 = var22 * (var20 == 640105 ? 1 : 3);
                              if (var3.j().b(var20, var138)) {
                                 HashMap var200 = new HashMap<>();
                                 int var225 = LuckyDrawTable.a().b(var2.a());

                                 for (int var244 = 0; var244 < var22; var244++) {
                                    var200.put(var225 + var244, LuckyDrawTable.a().a(var3.et()));
                                 }

                                 var3.a(new S_ProtoBuffers(var200, 1));
                                 LuckyDrawTable.a().a(var2.a(), var200);
                              }
                           } else if (var20 == 640382) {
                              L1Object var137 = L1World.a().a(var13);
                              if (var137 instanceof L1NpcInstance) {
                                 L1NpcInstance var199 = (L1NpcInstance)var137;
                                 Connection var224 = null;
                                 PreparedStatement var243 = null;

                                 try {
                                    var224 = DatabaseFactory.a().b();
                                    var243 = var224.prepareStatement("DELETE FROM spawnlist_npc WHERE npc_templateid=? AND locx=? AND locy=? AND mapid=?");
                                    if (var137 instanceof L1MonsterInstance) {
                                       var243 = var224.prepareStatement("DELETE FROM spawnlist WHERE npc_templateid=? AND locx=? AND locy=? AND mapid=?");
                                    }

                                    var243.setInt(1, var199.z());
                                    var243.setInt(2, var199.fs());
                                    var243.setInt(3, var199.ft());
                                    var243.setInt(4, var199.fp());
                                    var243.execute();
                                 } catch (SQLException var48) {
                                    C_ItemUSe.a.log(Level.SEVERE, var48.getLocalizedMessage(), var48);
                                 } finally {
                                    SQLUtil.a(var243);
                                    SQLUtil.a(var224);
                                 }

                                 var199.a(0, 0, 0);
                                 var3.a(new S_SystemMessage("npcid:" + var199.z() + " = " + var199.U_().c()));
                              }
                           } else if (var20 == 640104) {
                              L1Object var136 = L1World.a().a(var13);
                              if (var136 instanceof L1NpcInstance) {
                                 L1NpcInstance var198 = (L1NpcInstance)var136;
                                 Connection var223 = null;
                                 PreparedStatement var242 = null;

                                 try {
                                    var223 = DatabaseFactory.a().b();
                                    var242 = var223.prepareStatement(
                                       "UPDATE spawnlist_npc SET locx=?,locy=?,heading=? WHERE npc_templateid=? AND locx=? AND locy=?"
                                    );
                                    var242.setInt(1, var3.fs());
                                    var242.setInt(2, var3.ft());
                                    var242.setInt(3, var3.fb());
                                    var242.setInt(4, var198.z());
                                    var242.setInt(5, var198.fs());
                                    var242.setInt(6, var198.ft());
                                    var242.execute();
                                 } catch (Exception var46) {
                                    C_ItemUSe.a.log(Level.SEVERE, var46.getLocalizedMessage(), var46);
                                 } finally {
                                    SQLUtil.a(var242);
                                    SQLUtil.a(var223);
                                 }

                                 var198.a(var3.fs(), var3.ft(), var3.fb());
                                 var3.a(new S_SystemMessage("npcid:" + var198.z() + " = " + var198.U_().c()));
                              }
                           } else if (var20 == 640234) {
                              if (var54 == null) {
                                 var3.a(new S_ServerMessage(156));
                                 return;
                              }

                              if (var54.N() != 40314 && var54.N() != 40316) {
                                 var3.a(new S_ServerMessage(1164));
                                 return;
                              }

                              String var135 = null;

                              for (L1NpcInstance var197 : var3.ek().values()) {
                                 if (var197 instanceof L1PetInstance) {
                                    L1PetInstance var241 = (L1PetInstance)var197;
                                    if (var54.fr() == var241.k()) {
                                       var135 = var241.et();
                                       var3.am(var241.fr());
                                       break;
                                    }
                                 }
                              }

                              if (var135 == null) {
                                 var3.a(new S_ServerMessage(1301));
                                 return;
                              }

                              var3.a(new S_Message_YN(1322, var135 + " "));
                           } else if (var20 == 640835) {
                              L1World.a().a(new S_ChatPacket(var3.et(), var7));
                              var3.j().b(var5, 1);
                           } else if (var20 == 640233) {
                              L1World.a().a(new S_ChatPacket(var3.et(), var7, var8));
                              var3.j().b(var5, 1);
                           } else if (var20 == 640334) {
                              if (var3.cJ() >= 100) {
                                 var3.a(new S_ServerMessage(1622));
                                 return;
                              }

                              var3.ao();
                              var3.a(new S_ServerMessage(1624, "" + var3.cJ()));
                              var3.I();
                              var3.j().b(var5, 1);
                           } else if (var20 == 640144) {
                              int var134 = L1CastleLocation.a(var3);
                              if (var134 > 0) {
                                 var3.a(new S_ServerMessage(3274));
                                 return;
                              }

                              int var196 = var5.a().V();
                              SpawnTable.a(var196, var3, 0, 300000L);
                              var3.j().b(var5, 1);
                           } else if (var20 >= 640626 && var20 <= 640637) {
                              L1Dragon.a().a(var5, var3);
                           } else if (var20 == 640561) {
                              if (var54 == null) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              if (var54.N() >= 21340 && var54.N() <= 21344) {
                                 var3.j().f(var54);
                                 ItemTable.a(var3, 640562, 1);
                              } else {
                                 if (var54.N() < 21345 || var54.N() > 21349) {
                                    var3.a(new S_ServerMessage(79));
                                    return;
                                 }

                                 var3.j().f(var54);
                                 ItemTable.a(var3, 640563, 1);
                              }

                              var3.j().b(var5, 1);
                           } else if (var20 == 640841) {
                              if (var54 == null) {
                                 var3.a(new S_ServerMessage(156));
                                 return;
                              }

                              if (var54.N() < 21509 || var54.N() > 21511) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              if (var54.G() >= 9) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              Enchant.a(var3, var54, 1);
                              var3.j().b(var5, 1);
                           } else if (var20 == 640677) {
                              if (var54 == null) {
                                 var3.a(new S_ServerMessage(156));
                                 return;
                              }

                              if (var54.N() < 393 || var54.N() > 400) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              if (var54.G() >= 15) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              Enchant.a(var3, var54, 1);
                              var3.j().b(var5, 1);
                           } else if (var20 >= 640370 && var20 <= 640372) {
                              if (var54 == null) {
                                 var3.a(new S_ServerMessage(156));
                                 return;
                              }

                              if (!var54.g() || var54.G() != var5.a().V()) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              Enchant.b(var3, var5, var54);
                           } else if (var20 == 640833) {
                              if (var54 == null || var54.N() < 21495 || var54.N() > 21499) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              int var133 = 35;
                              boolean var195 = Random.a(100) + 1 < 35;
                              if (var195) {
                                 var3.a(new S_ServerMessage(161, var54.s(), "$252", "$247"));
                                 L1Item var221 = ItemTable.a().a(var54.N() + 1);
                                 boolean var240 = var54.D();
                                 var3.j().a(var54, false);
                                 var54.a(var221);
                                 var3.j().a(var54, var240);
                                 var3.j().j(var54);
                              } else {
                                 var3.a(new S_ServerMessage(164, var54.s(), "$245"));
                                 var3.j().f(var54);
                              }

                              var3.j().b(var5, 1);
                           } else if (var20 == 640946) {
                              if (var54 == null || !var54.j()) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              int var132 = 35;
                              boolean var194 = Random.a(100) + 1 < 35;
                              if (var194) {
                                 Enchant.a(var3, var54, 1);
                              } else {
                                 var3.a(new S_ServerMessage(164, var54.s(), "$245"));
                                 var3.j().f(var54);
                              }

                              var3.j().b(var5, 1);
                           } else if (var20 == 640440) {
                              if (var54 == null || var54.N() < 21261 || var54.N() > 21300) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              int[] var131 = new int[]{21270, 21280, 21290, 21300};
                              int[] var249 = var131;
                              int var238 = var131.length;

                              for (int var219 = 0; var219 < var238; var219++) {
                                 int var192 = var249[var219];
                                 if (var54.N() == var192) {
                                    var3.a(new S_ServerMessage(1453));
                                    return;
                                 }
                              }

                              int var193 = 35;
                              boolean var220 = Random.a(100) + 1 < 35;
                              if (var220) {
                                 var3.a(new S_ServerMessage(161, var54.s(), "$252", "$247"));
                                 L1Item var239 = ItemTable.a().a(var54.N() + 1);
                                 boolean var250 = var54.D();
                                 var3.j().a(var54, false);
                                 var54.a(var239);
                                 var3.j().a(var54, var250);
                                 var3.j().j(var54);
                              } else {
                                 var3.a(new S_ServerMessage(79));
                              }

                              var3.j().b(var5, 1);
                           } else if (var20 == 640346) {
                              if (var54 == null || var54.N() < 21199 || var54.N() > 21203) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              int var130 = 60 - (var54.N() - 21198) * 11;
                              boolean var191 = Random.a(100) + 1 < var130;
                              if (var191) {
                                 var3.a(new S_ServerMessage(161, var54.s(), "$252", "$247"));
                                 L1Item var218 = ItemTable.a().a(var54.N() + 1);
                                 boolean var237 = var54.D();
                                 var3.j().a(var54, false);
                                 var54.a(var218);
                                 var3.j().a(var54, var237);
                                 var3.j().j(var54);
                              } else {
                                 var3.a(new S_ServerMessage(160, var54.s(), "$252", "$248"));
                              }

                              var3.j().b(var5, 1);
                           } else if (var20 == 49142) {
                              var3.a(new S_SystemMessage("\\aH請至古魯丁洽詢相關NPC。"));
                           } else if (var20 == 640439) {
                              if (var54 == null || var54.N() < 21261 || var54.N() > 21300) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              Timestamp var129 = new Timestamp(System.currentTimeMillis() + 86400000L);
                              var54.b(var129);
                              var3.j().j(var54);
                              var3.j().b(var5, 1);
                           } else if (var20 >= 640386 && var20 <= 640391) {
                              if (var54 == null || var54.N() != 20084 && var54.N() != 20085 && var54.N() != 120085) {
                                 var3.a(new S_ServerMessage(1453));
                                 return;
                              }

                              if (var54.D()) {
                                 var3.a(new S_ServerMessage(4357));
                                 return;
                              }

                              int var128 = var5.a().V() + (var54.N() == 20084 ? 6 : 0);
                              var3.j().f(var54);
                              L1ItemInstance var190 = ItemTable.a(var3, var128, 1, var54.G(), var54.F(), var54.C(), 31);
                              var3.a(new S_ServerMessage(3299));
                              var3.j().b(var5, 1);
                           } else if (var20 >= 640392 && var20 <= 640400) {
                              if (var54 == null || var54.N() < 21246 || var54.N() > 21257) {
                                 var3.a(new S_ServerMessage(1453));
                                 return;
                              }

                              int var127 = var5.a().V();
                              boolean var189 = var54.D();
                              var3.j().a(var54, false);
                              var54.l(var127);
                              var54.w();
                              var3.j().a(var54, var189);
                              var3.j().j(var54);
                              var3.a(new S_ServerMessage(3299));
                              var3.j().b(var5, 1);
                           } else if (var20 >= 640374 && var20 <= 640379) {
                              if (var54 == null || var54.N() < 21152 || var54.N() > 21155) {
                                 var3.a(new S_ServerMessage(1453));
                                 return;
                              }

                              int var126 = var5.a().V();
                              boolean var188 = var54.D();
                              var3.j().a(var54, false);
                              var54.l(var126);
                              var54.z();
                              var3.j().a(var54, var188);
                              var3.j().j(var54);
                              var3.a(new S_ServerMessage(3299));
                              var3.j().b(var5, 1);
                           } else if (var20 == 640844) {
                              if (var54 == null || !var54.f()) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              int var125 = LostPowerItemTable.a().a(var54.N());
                              if (var125 <= 0) {
                                 var3.a(new S_ServerMessage(79));
                                 return;
                              }

                              if (Random.a(100) < 10) {
                                 ItemTable.a(var3, var125, 1, 0, false);
                                 var3.a(new S_ServerMessage(4964, var54.b()));
                              } else {
                                 var3.a(new S_ServerMessage(4965, var54.b()));
                              }

                              var3.j().b(var54, 1);
                              var3.j().b(var5, 1);
                           } else if (var20 == 640345) {
                              if (var54 == null || var54.N() != 21204) {
                                 var3.a(new S_ServerMessage(3440));
                                 return;
                              }

                              if (var54.aa() == 0) {
                                 boolean var124 = var54.D();
                                 var3.j().a(var54, false);
                                 var54.o(8192);
                                 var54.A();
                                 var3.j().a(var54, var124);
                                 var3.j().j(var54);
                              }

                              var3.a(new S_ServerMessage(3439));
                              var3.j().b(var5, 1);
                           } else if (var20 >= 640348 && var20 <= 640350) {
                              if (var54 == null || var54.N() != 21204) {
                                 var3.a(new S_ServerMessage(3440));
                                 return;
                              }

                              int var120 = 0;
                              var120 |= var54.X();
                              var120 |= var54.Y();
                              var120 |= var54.Z();
                              int[] var187 = new int[]{0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096};
                              int var217 = 0;

                              while (var217 == 0) {
                                 int var235 = Random.a(var187.length);
                                 if ((var120 & var187[var235]) != var187[var235]) {
                                    var217 = var187[var235];
                                 }
                              }

                              boolean var236 = var54.D();
                              var3.j().a(var54, false);
                              if (var20 == 640348) {
                                 var54.l(var217);
                              } else if (var20 == 640349) {
                                 var54.m(var217);
                              } else if (var20 == 640350) {
                                 var54.n(var217);
                              }

                              var54.A();
                              var3.j().a(var54, var236);
                              var3.a(new S_ServerMessage(3439));
                              var3.j().j(var54);
                              var3.j().b(var5, 1);
                           } else if (var20 == 640337) {
                              if (!var3.j().b(41246, 20)) {
                                 var3.a(new S_ServerMessage(337, "$5240"));
                                 return;
                              }

                              L1Object var119 = L1World.a().a(var13);
                              if (var119 instanceof L1Character) {
                                 L1Character var186 = (L1Character)var119;
                                 int var216 = 150 + var3.ev();
                                 int var234 = 13987;
                                 if (Random.a(100) < 25) {
                                    var216 = 200 + var3.ev() + Random.a(300);
                                    var234 = 13989;
                                 }

                                 if (var3.a(var3, var186, false)) {
                                    var216 = 0;
                                 }

                                 if (!var3.i(var186.fs(), var186.ft())) {
                                    var216 = 0;
                                 }

                                 L1Magic var248 = new L1Magic(var3, var186);
                                 var248.a(var216, 0);
                                 if (var186 instanceof L1PcInstance) {
                                    ((L1PcInstance)var186).a(new S_DoActionGFX(var186.fr(), 2));
                                 }

                                 var186.b(new S_DoActionGFX(var186.fr(), 2));
                                 var3.a(new S_SkillSound(var186.fr(), var234));
                                 var3.b(new S_SkillSound(var186.fr(), var234));
                              }
                           } else if (var20 == 640339) {
                              if (!var3.j().b(41246, 20)) {
                                 var3.a(new S_ServerMessage(337, "$5240"));
                                 return;
                              }

                              L1Object var118 = L1World.a().a(var13);
                              if (var118 instanceof L1Character) {
                                 L1Character var185 = (L1Character)var118;
                                 int var215 = 250 + var3.ev();
                                 int var233 = 13991;
                                 if (Random.a(100) < 25) {
                                    var215 = 400 + var3.ev() + Random.a(400);
                                    var233 = 13993;
                                 }

                                 if (var3.a(var3, var185, false)) {
                                    var215 = 0;
                                 }

                                 if (!var3.i(var185.fs(), var185.ft())) {
                                    var215 = 0;
                                 }

                                 L1Magic var247 = new L1Magic(var3, var185);
                                 var247.a(var215, 0);
                                 if (var185 instanceof L1PcInstance) {
                                    ((L1PcInstance)var185).a(new S_DoActionGFX(var185.fr(), 2));
                                 }

                                 var185.b(new S_DoActionGFX(var185.fr(), 2));
                                 var3.a(new S_SkillSound(var185.fr(), var233));
                                 var3.b(new S_SkillSound(var185.fr(), var233));
                              }
                           } else if (var20 == 640338) {
                              if (!var3.j().b(41246, 20)) {
                                 var3.a(new S_ServerMessage(337, "$5240"));
                                 return;
                              }

                              int var117 = 80 + var3.ev();
                              int var184 = 13995;
                              if (Random.a(100) < 25) {
                                 var117 = 160 + var3.ev() + Random.a(300);
                                 var184 = 13997;
                              }

                              for (L1Object var214 : L1World.a().b(var3, 4)) {
                                 if (var214 instanceof L1MonsterInstance && var3.i(var214.fs(), var214.ft())) {
                                    L1MonsterInstance var246 = (L1MonsterInstance)var214;
                                    L1Magic var251 = new L1Magic(var3, var246);
                                    var251.a(var117, 0);
                                    var246.b(new S_DoActionGFX(var246.fr(), 2));
                                 }
                              }

                              var3.a(new S_SkillSound(var3.fr(), var184));
                              var3.b(new S_SkillSound(var3.fr(), var184));
                           } else if (var20 == 640356) {
                              if (var23 == var3.fr()) {
                                 var3.e(new Timestamp(System.currentTimeMillis() + 2592000000L));
                              }

                              CharacterTable.a().a(var23, 2592000000L);
                              var3.a(new S_ProtoBuffers(461, var3));
                              var3.a(new S_SkillSound(var3.fr(), 2028));
                              var3.b(new S_SkillSound(var3.fr(), 2028));
                              var3.a(new S_ServerMessage(3916));
                              var3.j().b(var5, 1);
                           } else if (var20 == 640700) {
                              L1Object var116 = L1World.a().a(var13);
                              L1Character var183 = new L1Character();
                              if (var116 == null) {
                                 var183.cF(var13);
                                 var183.cG(var14);
                                 var183.cH(var15);
                              } else {
                                 var183.cF(var116.fr());
                                 var183.cG(var116.fs());
                                 var183.cH(var116.ft());
                              }

                              int var213 = 200 + Random.a(150);
                              L1Location var231 = new L1Location(var183.fs(), var183.ft(), var3.fp());

                              for (L1Object var245 : L1World.a().a(var231, 3)) {
                                 if (var245 instanceof L1MonsterInstance) {
                                    L1MonsterInstance var34 = (L1MonsterInstance)var245;
                                    L1Magic var35 = new L1Magic(var3, var34);
                                    var213 = (int)L1Magic.a(var3, var34, var213, 0);
                                    var35.a(var213, 0);
                                    var34.b(new S_DoActionGFX(var34.fr(), 2));
                                 }
                              }

                              var3.ct(var3.a(var183));
                              var3.a(new S_AttackPacket(var3, var183, 17, 762, var213, 8, 0));
                              var3.b(new S_AttackPacket(var3, var183, 17, 762, var213, 8, 0));
                              this.e(var3, var5);
                           } else if (var20 == 640354) {
                              for (L1Object var115 : L1World.a().b(var3, 5)) {
                                 if (var115 instanceof L1DotaInstance) {
                                    L1DotaInstance var212 = (L1DotaInstance)var115;
                                    var212.b(new S_DoActionGFX(var212.fr(), 2));
                                    var212.b(var3, 250);
                                 }
                              }

                              var3.a(new S_SkillSound(var3.fr(), 1819));
                              var3.b(new S_SkillSound(var3.fr(), 1819));
                              this.e(var3, var5);
                           } else if (var20 == 640355) {
                              for (L1Object var114 : L1World.a().b(var3, 3934)) {
                                 if (var114 instanceof L1DotaInstance) {
                                    L1DotaInstance var211 = (L1DotaInstance)var114;
                                    var211.b(new S_DoActionGFX(var211.fr(), 2));
                                    var211.b(var3, 700);
                                 }
                              }

                              var3.a(new S_SkillSound(var3.fr(), 3934));
                              var3.b(new S_SkillSound(var3.fr(), 3934));
                              this.e(var3, var5);
                           } else if (var20 == 640319 || var20 == 640320) {
                              int var113 = var5.a().V();

                              for (L1Object var180 : L1World.a().b(var3, 7)) {
                                 if (var180 instanceof L1MonsterInstance) {
                                    L1MonsterInstance var230 = (L1MonsterInstance)var180;
                                    var230.b(new S_DoActionGFX(var230.fr(), 2));
                                    var230.b(var3, 100);
                                 }
                              }

                              var3.a(new S_SkillSound(var3.fr(), var113));
                              var3.b(new S_SkillSound(var3.fr(), var113));
                              var3.j().b(var5, 1);
                           } else if (var20 == 640321 || var20 == 640322) {
                              int var112 = var5.a().V();
                              L1ItemQuestBuff.b(var3, var112, 120);
                              var3.a(new S_SkillSound(var3.fr(), 11101));
                              var3.b(new S_SkillSound(var3.fr(), 11101));
                              var3.j().b(var5, 1);
                           } else if (var20 == 640709 || var20 == 640710 || var20 == 640711) {
                              int var111 = var5.a().V();
                              L1ItemQuestBuff.b(var3, var111, 1200);
                              var3.a(new S_SkillSound(var3.fr(), var111 + 10751));
                              var3.b(new S_SkillSound(var3.fr(), var111 + 10751));
                              var3.j().b(var5, 1);
                           } else if (var20 >= 640810 && var20 <= 640818) {
                              int var110 = var5.a().V();
                              int var179 = 900;
                              if (var20 == 640810 || var20 == 640812 || var20 == 640814) {
                                 var179 = 300;
                              }

                              L1ItemQuestBuff.b(var3, var110, var179);
                              var3.a(new S_SkillSound(var3.fr(), var110 + 3865));
                              var3.b(new S_SkillSound(var3.fr(), var110 + 3865));
                              var3.j().b(var5, 1);
                           } else if (var20 == 640103) {
                              Enchant.a(var3, var5, var54);
                           } else if (var20 == 640110) {
                              if (var54.a().aP() != 17 && var54.a().aP() != 22) {
                                 var3.a(new S_ServerMessage(2477));
                                 return;
                              }

                              if (var54.N() != 640478 && var54.N() != 640483) {
                                 var3.a(new S_ServerMessage(3658));
                                 return;
                              }

                              if (var3.O(var54.fr())) {
                                 var3.a(new S_ServerMessage(1181));
                                 return;
                              }

                              int[] var109 = new int[]{16384, 65536, 32768, 2, 262144};
                              var54.n(var109[Random.a(var109.length)]);
                              var3.a(new S_ServerMessage(3657));
                              var54.x();
                              var3.j().j(var54);
                              var3.j().b(var5, 1);
                           } else if (var20 == 640109) {
                              if (var54.a().aP() != 17 && var54.a().aP() != 22) {
                                 var3.a(new S_ServerMessage(2477));
                                 return;
                              }

                              if (var54.N() != 640476 && var54.N() != 640477 && var54.N() != 640481 && var54.N() != 640482) {
                                 var3.a(new S_ServerMessage(3658));
                                 return;
                              }

                              if (var3.O(var54.fr())) {
                                 var3.a(new S_ServerMessage(1181));
                                 return;
                              }

                              int var108 = Random.a(3) + 1;
                              int[] var178 = new int[]{16384, 65536, 32768, 2, 262144};
                              if (var108 != 1 && var108 != 2 || var54.N() != 640476 && var54.N() != 640481) {
                                 if (var108 == 3) {
                                    var3.j().f(var54);
                                    ItemTable.a(var3, var54.a().V(), 1, var54.C());
                                 } else {
                                    var54.l(0);
                                    var54.m(0);
                                    var54.n(0);
                                 }
                              } else {
                                 var3.j().f(var54);
                                 ItemTable.a(var3, var54.N() + 1, 1, var54.C());
                              }

                              if (var108 == 1) {
                                 var54.l(var178[Random.a(var178.length)]);
                              } else if (var108 == 2) {
                                 var54.m(var178[Random.a(var178.length)]);
                              } else if (var108 == 3) {
                                 var54.n(var178[Random.a(var178.length)]);
                              }

                              var3.a(new S_ServerMessage(3657));
                              var54.x();
                              var3.j().j(var54);
                              var3.j().b(var5, 1);
                           } else if (var20 != 640574 && var20 != 640575) {
                              if (var20 == 640547) {
                                 int var107 = 1;
                                 int var177 = Random.a(100) + 1;
                                 if (var177 <= 40) {
                                    var107 = 17 + Random.a(60);
                                 } else if (var177 >= 41 && var177 <= 70) {
                                    var107 = 177 + Random.a(600);
                                 } else if (var177 >= 71 && var177 <= 90) {
                                    var107 = 1777 + Random.a(6000);
                                 } else if (var177 >= 91 && var177 <= 100) {
                                    var107 = 17777 + Random.a(60000);
                                 }

                                 ItemTable.a(var3, 40308, var107);
                                 var3.j().b(var5, 1);
                              } else if (var20 >= 640622 && var20 <= 640625) {
                                 int var106 = Random.a(100) + 1;
                                 int var176 = 0;
                                 if (var20 == 640622) {
                                    var176 = var106 * 5;
                                 } else if (var20 == 640623) {
                                    var176 = var106 * 10;
                                 } else if (var20 == 640624) {
                                    var176 = var106 * 20;
                                 } else if (var20 == 640625) {
                                    var176 = 50000;
                                 }

                                 ItemTable.a(var3, 640621, var176);
                                 var3.j().b(var5, 1);
                              } else if (var20 == 47103) {
                                 var3.a(new S_ServerMessage(452, var5.s()));
                              } else if (var20 == 40003) {
                                 for (L1ItemInstance var105 : var3.j().d()) {
                                    if (var105.N() == 40002) {
                                       var105.j(var5.a().d());
                                       var3.a(new S_ItemName(var105));
                                       var3.a(new S_ServerMessage(230));
                                       break;
                                    }
                                 }

                                 var3.j().b(var5, 1);
                              } else if (var20 == 43000) {
                                 var3.k(1);
                                 var3.Z();
                                 var3.ay(0);
                                 var3.a(new S_SkillSound(var3.fr(), 191));
                                 var3.b(new S_SkillSound(var3.fr(), 191));
                                 var3.a(new S_OwnCharStatus(var3));
                                 var3.a(new S_ServerMessage(822));
                                 var3.I();
                                 var3.ac();
                                 var3.j().b(var5, 1);
                              } else if (var20 >= 40033 && var20 <= 40038) {
                                 if (var3.bB() >= 10) {
                                    var3.a(new S_ServerMessage(939));
                                    return;
                                 }

                                 if (var20 == 40033 && var3.bf() < 45) {
                                    var3.o(1);
                                 } else if (var20 == 40034 && var3.bg() < 45) {
                                    var3.p(1);
                                 } else if (var20 == 40035 && var3.bh() < 45) {
                                    var3.q(1);
                                    var3.W();
                                 } else if (var20 == 40036 && var3.bj() < 45) {
                                    var3.s(1);
                                 } else if (var20 == 40037 && var3.bk() < 45) {
                                    var3.t(1);
                                    var3.Y();
                                 } else {
                                    if (var20 != 40038 || var3.bi() >= 45) {
                                       var3.a(new S_ServerMessage(481));
                                       return;
                                    }

                                    var3.r(1);
                                 }

                                 var3.az(var3.bB() + 1);
                                 var3.a(new S_OwnCharStatus2(var3));
                                 var3.I();
                                 var3.a(new S_ProtoBuffers(489, var3));
                                 var3.a(new S_ProtoBuffers(490, var3));
                                 var3.j().b(var5, 1);
                              } else if (var20 == 40858) {
                                 var3.e(true);
                                 var3.a(new S_Liquor(var3.fr(), 1));
                                 var3.j().b(var5, 1);
                              } else if (var20 == 40017 || var20 == 40507 || var20 == 640496) {
                                 var3.a(new S_SkillSound(var3.fr(), 192));
                                 var3.b(new S_SkillSound(var3.fr(), 192));
                                 var3.en();
                                 var3.j().b(var5, 1);
                              } else if (var20 == 40014 || var20 == 140014 || var20 == 41415 || var20 == 49305 || var20 == 640363 || var20 == 640729) {
                                 Potion.a(var3, 1000, var5, var3.z() || var3.F());
                              } else if (var20 == 40031 || var20 == 640365 || var20 == 640732) {
                                 Potion.a(var3, 1000, var5, var3.x());
                              } else if (var20 == 40733) {
                                 Potion.a(var3, 1000, var5, !var3.D() && !var3.E());
                              } else if (var20 == 40068 || var20 == 140068 || var20 == 49304 || var20 == 640364 || var20 == 640730) {
                                 Potion.a(var3, 1016, var5, var3.A());
                              } else if (var20 == 49158 || var20 == 640731) {
                                 Potion.a(var3, 1017, var5, var3.D() || var3.E());
                              } else if (var20 == 49138 || var20 == 640190) {
                                 Potion.a(var3, var5, 600);
                              } else if (var20 == 47005) {
                                 var3.a(new S_SkillSound(var3.fr(), 7321));
                                 var3.b(new S_SkillSound(var3.fr(), 7321));
                                 L1ItemQuestBuff.b(var3, 4006, 2400);
                                 var3.j().b(var5, 1);
                              } else if (var20 == 640664) {
                                 if (var3.cC() > 0) {
                                    var3.a(new S_ServerMessage(4384));
                                    return;
                                 }

                                 var3.K(3850000);
                                 var3.a(new S_SkillSound(var3.fr(), 7467));
                                 var3.b(new S_SkillSound(var3.fr(), 7467));
                                 var3.j().b(var5, 1);
                              } else if (var20 == 640665) {
                                 var3.a(new S_SkillSound(var3.fr(), 13249));
                                 var3.b(new S_SkillSound(var3.fr(), 13249));
                                 var3.j(4078, 1200000);
                                 var3.j().b(var5, 1);
                              } else if (var20 == 640336) {
                                 var3.a(new S_SkillSound(var3.fr(), 7892));
                                 var3.b(new S_SkillSound(var3.fr(), 7892));
                                 L1ItemQuestBuff.b(var3, 4070, 1800);
                                 var3.j().b(var5, 1);
                              } else if (var20 == 47006) {
                                 var3.a(new S_SkillSound(var3.fr(), 7013));
                                 var3.b(new S_SkillSound(var3.fr(), 7013));
                                 L1ItemQuestBuff.b(var3, 4007, 3600);
                                 var3.j().b(var5, 1);
                              } else if (var20 >= 47000 && var20 <= 47004) {
                                 int var104 = var20 - 42999;
                                 int var174 = var20 - 39699;
                                 L1ItemQuestBuff.b(var3, var104, 900);
                                 var3.a(new S_SkillSound(var3.fr(), var174));
                                 var3.b(new S_SkillSound(var3.fr(), var174));
                                 var3.a(new S_ServerMessage(1292));
                                 var3.j().b(var5, 1);
                              } else if (var20 >= 47007 && var20 <= 47009) {
                                 int var103 = var20 - 42999;
                                 int var173 = var20 - 40014;
                                 L1ItemQuestBuff.b(var3, var103, 3600);
                                 var3.a(new S_SkillSound(var3.fr(), var173));
                                 var3.b(new S_SkillSound(var3.fr(), var173));
                                 var3.j().b(var5, 1);
                              } else if (var20 == 40032 || var20 == 40041 || var20 == 41344 || var20 == 49303) {
                                 Potion.f(var3, var5);
                              } else if (var20 == 40015 || var20 == 140015 || var20 == 40736 || var20 == 49306 || var20 == 640495) {
                                 Potion.d(var3, var5);
                              } else if (var20 == 40016 || var20 == 140016 || var20 == 49307 || var20 == 640733) {
                                 Potion.e(var3, var5);
                              } else if (var20 == 40025) {
                                 Potion.g(var3, var5);
                              } else if (var20 == 640831) {
                                 var3.a(new S_ProtoBuffers(1038, 600, 0, 6546, 0, 3823, 1971, 1972, 1));
                                 if (!var3.bB(1038)) {
                                    var3.bH(100);
                                    var3.bJ(100);
                                    var3.ck(5);
                                    var3.cm(10);
                                    var3.cl(5);
                                    var3.cn(10);
                                    var3.cp(5);
                                    var3.bL(-10);
                                    var3.co(10);
                                    var3.a(new S_OwnCharStatus(var3));
                                    var3.a(new S_SPMR(var3));
                                 }

                                 var3.a(new S_Liquor(var3.fr(), 8));
                                 var3.b(new S_Liquor(var3.fr(), 8));
                                 var3.a(new S_SkillSound(var3.fr(), 12214));
                                 var3.b(new S_SkillSound(var3.fr(), 12214));
                                 var3.j(1038, 600000);
                              } else if (var20 == 640702) {
                                 var3.a(new S_ProtoBuffers(4080, 1800, 0, 4910, 0, 4415, 0, 0, 1));
                                 if (!var3.bB(4080)) {
                                    var3.bH(25);
                                    var3.bJ(20);
                                    var3.a(new S_OwnCharStatus(var3));
                                 }

                                 var3.a(new S_SkillSound(var3.fr(), 13391));
                                 var3.b(new S_SkillSound(var3.fr(), 13391));
                                 var3.j(4080, 1800000);
                              } else if (var20 == 640583) {
                                 int var102 = var5.a().V();
                                 boolean var172 = L1PolyMorph.a(var3, 13450, var102, 1);
                                 if (!var172) {
                                    var5.a((Timestamp)null);
                                    var3.a(new S_ServerMessage(79));
                                    return;
                                 }

                                 if (!var3.bB(1006)) {
                                    var3.bH(100);
                                    var3.bJ(100);
                                    var3.cm(10);
                                    var3.ck(5);
                                    var3.cn(10);
                                    var3.cl(5);
                                    var3.cp(5);
                                    var3.bN(1);
                                    var3.bR(1);
                                    var3.bV(1);
                                    var3.a(new S_OwnCharStatus(var3));
                                 }

                                 var3.j(1006, var102 * 1000);
                              } else if (var20 == 640827) {
                                 int var101 = var5.a().V();
                                 boolean var171 = L1PolyMorph.a(var3, 12854, var101, 1);
                                 if (!var171) {
                                    var5.a((Timestamp)null);
                                    var3.a(new S_ServerMessage(79));
                                    return;
                                 }

                                 if (!var3.bB(1037)) {
                                    var3.bH(120);
                                    var3.bJ(100);
                                    var3.F(15);
                                    var3.co(30);
                                    var3.cm(10);
                                    var3.cn(10);
                                    var3.U(3);
                                    var3.bN(3);
                                    var3.bR(3);
                                    var3.bV(3);
                                    var3.a(new S_OwnCharStatus(var3));
                                 }

                                 var3.j(1037, var101 * 1000);

                                 for (L1ItemInstance var209 : var3.j().d()) {
                                    if (var209.a().S() && var209.D()) {
                                       var3.j().a(var209, false);
                                    }
                                 }
                              } else if (var20 == 640785) {
                                 int var100 = var5.a().V();
                                 boolean var170 = L1PolyMorph.a(var3, 14491, var100, 1);
                                 if (!var170) {
                                    var5.a((Timestamp)null);
                                    var3.a(new S_ServerMessage(79));
                                    return;
                                 }

                                 if (!var3.bB(1031)) {
                                    var3.bH(120);
                                    var3.bJ(100);
                                    var3.cm(10);
                                    var3.ck(7);
                                    var3.cn(10);
                                    var3.cl(7);
                                    var3.cp(5);
                                    var3.bN(1);
                                    var3.bR(1);
                                    var3.bV(1);
                                    var3.a(new S_OwnCharStatus(var3));
                                 }

                                 var3.j(1031, var100 * 1000);
                              } else if (var20 >= 640581 && var20 <= 640582) {
                                 int var99 = var5.a().V();
                                 L1PolyMorph.a(var3, var99, 5400, 1);
                                 var3.j().b(var5, 1);
                              } else if (var20 == 640739) {
                                 int[] var98 = new int[]{12283, 12283, 12314, 12295, 12280, 12283, 12286, 12283};
                                 L1PolyMorph.a(var3, var98[var3.ay()], 1800, 1);
                                 var3.j().b(var5, 1);
                              } else if (var20 >= 640307 && var20 <= 640311) {
                                 int var97 = var5.a().V();
                                 L1PolyMorph.a(var3, var97, 3600, 1);
                                 var3.j().b(var5, 1);
                              } else if (var20 == 49220
                                 || var20 == 49139
                                 || var20 >= 41154 && var20 <= 41157
                                 || var20 >= 41143 && var20 <= 41145
                                 || var20 >= 640416 && var20 <= 640423
                                 || var20 == 640836) {
                                 int var96 = var20 == 49220 ? 1200 : 900;
                                 if (var20 >= 640416 && var20 <= 640423) {
                                    var96 = 3600;
                                 } else if (var20 == 640836) {
                                    var96 = 600;
                                 }

                                 int var169 = var5.a().V();
                                 L1PolyMorph.a(var3, var169, var96, 1);
                                 if (var20 != 640836) {
                                    var3.j().b(var5, 1);
                                 }
                              } else if (var20 >= 49149 && var20 <= 49155) {
                                 if (var3.F()) {
                                    var3.a(new S_ServerMessage(79));
                                    return;
                                 }

                                 this.a(var3, var5);
                              } else if (var20 == 40317 || var20 == 640498) {
                                 int var95 = 79;
                                 if (!var54.f() && var54.H() > 0) {
                                    var3.j().h(var54);
                                    var95 = var54.H() == 0 ? 464 : 463;
                                 }

                                 var3.a(new S_ServerMessage(var95, var54.s()));
                                 var3.j().b(var5, 1);
                              } else if (var20 >= 47017 && var20 <= 47023) {
                                 int var94 = var5.a().V();
                                 var3.a(new S_SkillSound(var3.fr(), var94));
                                 var3.b(new S_SkillSound(var3.fr(), var94));
                                 L1ItemQuestBuff.b(var3, var20 - 42968, 600);
                              } else if (var20 >= 47041 && var20 <= 47046) {
                                 int var93 = var5.a().V();
                                 if (var54.a().V() == var93) {
                                    var3.j().b(var54, 1);
                                    var3.j().b(var5, 1);
                                    ItemTable.a(var3, var93, 1);
                                 } else {
                                    var3.a(new S_ServerMessage(79));
                                 }
                              } else if (var20 >= 47049 && var20 <= 47052) {
                                 if (var54.N() >= 47053 && var54.N() <= 47062) {
                                    if (Random.a(100) + 1 > 50) {
                                       int var92 = var54.N() + (var20 - 47048) * 10;
                                       ItemTable.a(var3, var92, 1);
                                    } else {
                                       var3.a(new S_ServerMessage(1411, var54.b()));
                                    }

                                    var3.j().b(var54, 1);
                                    var3.j().b(var5, 1);
                                 } else {
                                    var3.a(new S_ServerMessage(79));
                                 }
                              } else if (var20 == 47048) {
                                 if (var54.N() >= 47053 && var54.N() <= 47102) {
                                    int var91 = var54.a().V();
                                    if (var91 == 0) {
                                       var3.a(new S_ServerMessage(79));
                                       return;
                                    }

                                    if (Random.a(100) + 1 > 50) {
                                       var3.a(new S_ServerMessage(1410, var54.b()));
                                       ItemTable.a(var3, var91, 1);
                                    } else {
                                       var3.a(new S_ServerMessage(1411, var54.b()));
                                    }

                                    var3.j().b(var54, 1);
                                    var3.j().b(var5, 1);
                                 } else {
                                    var3.a(new S_ServerMessage(79));
                                 }
                              } else if (var20 >= 640404 && var20 <= 640415) {
                                 if (var3.j().b(41246, 100)) {
                                    int var90 = var5.a().V();
                                    L1ItemQuestBuff.a(var3, var90);
                                    int[] var168 = new int[]{13160, 13161, 13162, 13163};
                                    var3.a(new S_SkillSound(var3.fr(), var168[var90 - 4072]));
                                    var3.b(new S_SkillSound(var3.fr(), var168[var90 - 4072]));
                                 } else {
                                    var3.a(new S_ServerMessage(337, "$5240"));
                                 }
                              } else if (var20 >= 47064 && var20 <= 47102) {
                                 if (var3.j().b(41246, 250)) {
                                    int var89 = 0;
                                    if (var20 >= 47064 && var20 <= 47072) {
                                       var89 = 0;
                                    } else if (var20 >= 47074 && var20 <= 47082) {
                                       var89 = 1;
                                    } else if (var20 >= 47084 && var20 <= 47092) {
                                       var89 = 2;
                                    } else if (var20 >= 47094 && var20 <= 47102) {
                                       var89 = 3;
                                    }

                                    int var167 = var20 - (43051 + var89);
                                    int var208 = var20 - (38125 + var89);
                                    var3.a(new S_SkillSound(var3.fr(), var208));
                                    var3.b(new S_SkillSound(var3.fr(), var208));
                                    L1ItemQuestBuff.a(var3, var167, 600);
                                 } else {
                                    var52 = false;
                                    var3.a(new S_ServerMessage(337, "$5240"));
                                 }
                              } else if (var20 == 40097 || var20 == 40119 || var20 == 140119 || var20 == 40329) {
                                 for (L1ItemInstance var88 : var3.j().d()) {
                                    if ((var88.F() == 2 || var88.F() == 130) && (var20 != 40119 && var20 != 40097 || var5.F() == 0 || var88.D())) {
                                       if (var88.F() == 130) {
                                          var88.f(129);
                                       } else {
                                          var88.f(1);
                                       }

                                       if (var88.d() && var3.j().d(var88.N(), 1) != null) {
                                          var3.j().b(var88, var88.E());
                                          ItemTable.a(var3, var88.N(), var88.E(), var88.G(), var88.F(), var88.C());
                                       } else {
                                          var3.j().j(var88);
                                       }
                                    }
                                 }

                                 var3.a(new S_ServerMessage(155));
                                 var3.j().b(var5, 1);
                              } else if (var20 != 40126 && var20 != 40098) {
                                 if (var20 == 640758) {
                                    int var87 = var54.a().V();
                                    if (var54.N() >= 640753 && var54.N() <= 640757 && var87 > 0) {
                                       if (Random.a(100) < 20) {
                                          ItemTable.a(var3, var87, 1);
                                       } else {
                                          var3.a(new S_ServerMessage(165, var54.b()));
                                       }

                                       var3.j().b(var54, 1);
                                       var3.j().b(var5, 1);
                                    } else {
                                       var3.a(new S_ServerMessage(79));
                                    }
                                 } else if (var20 == 41036) {
                                    int var86 = var54.N() + 10;
                                    int var165 = 67;
                                    this.a(var3, var5, var54, var86, 67, 0, 158);
                                 } else if (var20 >= 41048 && var20 <= 41057) {
                                    if (var54.N() == var20 + 8034) {
                                       ItemTable.a(var3, var54.a().V(), 1);
                                       var3.j().b(var54, 1);
                                       var3.j().b(var5, 1);
                                    } else {
                                       var3.a(new S_ServerMessage(79));
                                    }
                                 } else if (var20 >= 41738 && var20 <= 41753) {
                                    ItemTable.a(var3, 41719 + Random.a(18), 1);
                                    var3.j().b(var5, 1);
                                 } else if (var20 == 40925) {
                                    int var85 = var54.N() + 174;
                                    int var164 = 90;
                                    this.a(var3, var5, var54, var85, 90, 0, 158);
                                 } else if (var20 >= 40931 && var20 <= 40942) {
                                    int var84 = var5.a().V();
                                    int var163 = 90;
                                    this.a(var3, var5, var54, var84, 90, 0, 160);
                                 } else if (var20 >= 40926 && var20 <= 40942 && var20 != 40930) {
                                    int var83 = var54.N() + (var20 == 40926 ? 3 : 1);
                                    int var162 = 90;
                                    this.a(var3, var5, var54, var83, 90, 0, 160);
                                 } else if (var20 >= 40943 && var20 <= 40958) {
                                    int var82 = 20435 + (var54.N() - 41185);
                                    int var161 = 80;
                                    int var207 = var5.a().V() - 4;
                                    int var228 = var5.a().V();
                                    this.a(var3, var5, var54, var82, 80, var207, var228);
                                 } else if (var20 == 41029) {
                                    int var81 = var54.N() + 1;
                                    int var160 = 50;
                                    this.a(var3, var5, var54, var81, 50, 0, 158);
                                 } else if (var20 == 40964) {
                                    int var80 = var54.N() + 8;
                                    int var159 = 50;
                                    this.a(var3, var5, var54, var80, 50, 0, 158);
                                 } else if (var20 == 40314 || var20 == 40316) {
                                    this.g(var3, var5);
                                 } else if (var20 == 40315) {
                                    var3.a(new S_Sound(437));
                                    var3.b(new S_Sound(437));

                                    for (L1NpcInstance var79 : var3.ek().values()) {
                                       if (var79 instanceof L1PetInstance) {
                                          ((L1PetInstance)var79).i();
                                       }
                                    }
                                 } else if (var20 == 640472) {
                                    switch (var11) {
                                       case 0:
                                          L1Teleport.a(var3, 32732, 32798, 101, 5, true);
                                          break;
                                       case 1:
                                          L1Teleport.a(var3, 32799, 32799, 102, 5, true);
                                          break;
                                       case 2:
                                          L1Teleport.a(var3, 32799, 32799, 103, 5, true);
                                          break;
                                       case 3:
                                          L1Teleport.a(var3, 32669, 32863, 104, 5, true);
                                          break;
                                       case 4:
                                          L1Teleport.a(var3, 32671, 32863, 105, 5, true);
                                          break;
                                       case 5:
                                          L1Teleport.a(var3, 32719, 32870, 106, 5, true);
                                          break;
                                       case 6:
                                          L1Teleport.a(var3, 32670, 32863, 107, 5, true);
                                          break;
                                       case 7:
                                          L1Teleport.a(var3, 32671, 32863, 108, 5, true);
                                          break;
                                       case 8:
                                          L1Teleport.a(var3, 32671, 32863, 109, 5, true);
                                          break;
                                       case 9:
                                          L1Teleport.a(var3, 32799, 32799, 110, 5, true);
                                          break;
                                       case 10:
                                          L1Teleport.a(var3, 32622, 32799, 111, 5, true);
                                          break;
                                       case 11:
                                          L1Teleport.a(var3, 32693, 32903, 111, 5, true);
                                    }
                                 } else if (var20 == 640437) {
                                    this.e(var3, var5);
                                    if (var5.I() <= 0) {
                                       var3.a(new S_ServerMessage(79));
                                       return;
                                    }

                                    switch (var11) {
                                       case 0:
                                          L1Teleport.a(var3, 32732, 32798, 101, 5, true);
                                          break;
                                       case 1:
                                          L1Teleport.a(var3, 32761, 32833, 77, 5, true);
                                          break;
                                       case 2:
                                          L1Teleport.a(var3, 32712, 32791, 59, 5, true);
                                          break;
                                       case 3:
                                          L1Teleport.a(var3, 32802, 32734, 43, 5, true);
                                          break;
                                       case 4:
                                          L1Teleport.a(var3, 32928, 32799, 430, 5, true);
                                          break;
                                       case 5:
                                          L1Teleport.a(var3, 32925, 32995, 410, 5, true);
                                          break;
                                       case 6:
                                          L1Teleport.a(var3, 32969, 32959, 521, 5, true);
                                          break;
                                       case 7:
                                          L1Teleport.a(var3, 32789, 32799, 600, 5, true);
                                          break;
                                       case 8:
                                          L1Teleport.a(var3, 32753, 32830, 309, 5, true);
                                          break;
                                       case 9:
                                          L1Teleport.a(var3, 32429, 33015, 550, 5, true);
                                    }
                                 } else if (var20 == 640668 || var20 == 640669) {
                                    Timestamp var78 = new Timestamp(System.currentTimeMillis());
                                    if (var5.bb() != null && var5.bb().before(var78)) {
                                       var3.a(new S_ServerMessage(3081));
                                       var3.j().b(var5, 1);
                                       return;
                                    }

                                    L1Teleport.a(var3, 32780, 32833, 622, 5, true);
                                 } else if (var20 == 40493) {
                                    var3.a(new S_Sound(165));
                                    var3.b(new S_Sound(165));

                                    for (L1Object var77 : var3.eq()) {
                                       if (var77 instanceof L1GuardianInstance) {
                                          L1GuardianInstance var206 = (L1GuardianInstance)var77;
                                          if (var206.U_().b() == 70850 && ItemTable.a(var3, 88, 1) != null) {
                                             var3.j().b(var5, 1);
                                          }
                                       }
                                    }
                                 } else if (var20 >= 40325 && var20 <= 40328) {
                                    if (var3.j().b(40318, 1)) {
                                       int var76 = var20 == 40328 ? 6 : var20 - 40323;
                                       int var156 = var5.a().V() + Random.a(var76);
                                       var3.a(new S_SkillSound(var3.fr(), var156));
                                       var3.b(new S_SkillSound(var3.fr(), var156));
                                    } else {
                                       var3.a(new S_ServerMessage(79));
                                    }
                                 } else if (var20 >= 41440 && var20 <= 41672) {
                                    this.d(var3, var5);
                                 } else if (var20 == 41689) {
                                    if (!var3.C()) {
                                       var3.a(new S_ServerMessage(79));
                                       return;
                                    }

                                    int var75 = var5.a().V();
                                    L1Skills var155 = SkillsTable.a().a(var75);
                                    var3.a(new S_ProtoBuffers(402, var75 - 600));
                                    var3.a(new S_SkillSound(var3.fr(), var155.o() >= 0 ? 224 : 231));
                                    var3.b(new S_SkillSound(var3.fr(), var155.o() >= 0 ? 224 : 231));
                                    SkillsTable.a().a(var3.fr(), var155.a(), var155.b(), 0, 0);
                                    var3.j().b(var5, 1);
                                 } else if (var20 >= 41681 && var20 <= 41688) {
                                    if (!var3.F()) {
                                       var3.a(new S_ServerMessage(79));
                                       return;
                                    }

                                    int var74 = var5.a().V();
                                    L1Skills var154 = SkillsTable.a().a(var74);
                                    var3.a(new S_ProtoBuffers(402, var74 - 600));
                                    var3.a(new S_SkillSound(var3.fr(), var154.o() >= 0 ? 224 : 231));
                                    var3.b(new S_SkillSound(var3.fr(), var154.o() >= 0 ? 224 : 231));
                                    SkillsTable.a().a(var3.fr(), var154.a(), var154.b(), 0, 0);
                                    var3.j().b(var5, 1);
                                 } else if (var20 != 40079 && var20 != 40095 && var20 != 40521 && var20 != 40124 && var20 != 640497) {
                                    if (var20 == 640501) {
                                       if (L1CastleWar.a().a((L1Character)var3)) {
                                          var3.a(new S_ServerMessage(2139));
                                          return;
                                       }

                                       if (var3.fu().c(new Point(var18, var19)) <= 5 && var3.i(var18, var19)) {
                                          L1Teleport.a(var3, var18, var19, var3.fp(), var3.fb(), true);
                                          var3.j().b(var5, 1);
                                       } else {
                                          var3.a(new S_ServerMessage(626));
                                       }
                                    } else if (var20 >= 40901 && var20 <= 40908) {
                                       if (var3.bD() == 0) {
                                          var3.a(new S_ServerMessage(662));
                                          return;
                                       }

                                       L1Object var73 = L1World.a().a(var3.bD());
                                       if (var73 instanceof L1PcInstance) {
                                          L1PcInstance var153 = (L1PcInstance)var73;
                                          boolean var205 = L1CastleLocation.a(var153.fu());
                                          if (var153.fq().h() && !var205) {
                                             L1Teleport.a(var3, var153.fs(), var153.ft(), var153.fp(), 5, true);
                                          } else {
                                             var3.a(new S_ServerMessage(547));
                                          }
                                       } else {
                                          var3.a(new S_ServerMessage(546));
                                       }
                                    } else if (var20 == 40555) {
                                       if (var3.z() && var3.fu().e(new Point(32821, 32800))) {
                                          L1Teleport.a(var3, 32815, 32810, 13, 5, true);
                                       } else {
                                          var3.a(new S_ServerMessage(79));
                                       }
                                    } else if (var20 == 40417) {
                                       if (var3.fp() == 440 && var3.fu().e(new Point(32670, 32980))) {
                                          L1Teleport.a(var3, 32922, 32812, 430, 5, true);
                                       } else {
                                          var3.a(new S_ServerMessage(79));
                                       }
                                    } else if (var20 == 40700) {
                                       var3.a(new S_Sound(10));
                                       var3.b(new S_Sound(10));
                                       if (var3.fs() >= 32619 && var3.fs() <= 32623 && var3.ft() >= 33120 && var3.ft() <= 33124 && var3.fp() == 440) {
                                          this.a(var3, 45875);
                                       } else {
                                          var3.a(new S_ServerMessage(79));
                                       }
                                    } else if (var20 == 40566) {
                                       if (var3.A()
                                          && var3.fp() == 4
                                          && !var3.j().f(40548)
                                          && var3.fs() >= 33971
                                          && var3.fs() <= 33975
                                          && var3.ft() >= 32324
                                          && var3.ft() <= 32328) {
                                          this.a(var3, 45300);
                                       } else {
                                          var3.a(new S_ServerMessage(79));
                                       }
                                    } else if (var20 == 49222) {
                                       if (var3.D() && var3.fp() == 61) {
                                          this.a(var3, 46161);
                                          var3.j().b(var5, 1);
                                       } else {
                                          var3.a(new S_ServerMessage(79));
                                       }
                                    } else if (var20 == 49189) {
                                       if (var3.E() && var3.fp() == 4) {
                                          this.a(var3, 46163);
                                          var3.j().b(var5, 1);
                                       } else {
                                          var3.a(new S_ServerMessage(79));
                                       }
                                    } else if (var20 == 49201) {
                                       if (var3.E() && var3.fp() == 4) {
                                          this.a(var3, 81254);
                                          var3.j().b(var5, 1);
                                       } else {
                                          var3.a(new S_ServerMessage(79));
                                       }
                                    } else if (var20 != 49208 && var20 != 49227) {
                                       if (var20 == 49167) {
                                          if (var3.ay() <= 3 && var3.fp() == 2000 + var3.ay() && var3.fs() == 32807 && var3.ft() == 32773) {
                                             this.a(var3, 81323 + var3.ay());
                                             var3.j().b(var5, 1);
                                          } else {
                                             var3.a(new S_ServerMessage(79));
                                          }
                                       } else if (var20 >= 40557 && var20 <= 40563) {
                                          int[][] var72 = new int[][]{
                                             {32620, 32641}, {33513, 32890}, {34215, 33195}, {32580, 33260}, {33046, 32806}, {33447, 33476}, {32730, 32426}
                                          };
                                          int var152 = var20 - 40557;
                                          int var204 = var5.a().V();
                                          if (var3.fs() == var72[var152][0] && var3.ft() == var72[var152][1] && var3.fp() == 4) {
                                             for (L1Object var227 : L1World.a().b()) {
                                                if (var227 instanceof L1NpcInstance && ((L1NpcInstance)var227).z() == var204) {
                                                   var3.a(new S_ServerMessage(79));
                                                   return;
                                                }
                                             }

                                             SpawnTable.a(var204, var3, 0, 300000L);
                                          } else {
                                             var3.a(new S_ServerMessage(79));
                                          }
                                       } else if (var20 == 40572) {
                                          if (var3.fs() == 32778 && var3.ft() == 32738 && var3.fp() == 21) {
                                             L1Teleport.a(var3, 32781, 32728, 21, 5, true);
                                          } else if (var3.fs() == 32781 && var3.ft() == 32728 && var3.fp() == 21) {
                                             L1Teleport.a(var3, 32778, 32738, 21, 5, true);
                                          } else {
                                             var3.a(new S_ServerMessage(79));
                                          }
                                       } else if (var20 == 40412) {
                                          var3.a(new S_DoActionGFX(var3.fr(), 17));
                                          var3.b(new S_DoActionGFX(var3.fr(), 17));
                                          if (!var3.fq().l()) {
                                             var3.a(new S_ServerMessage(79));
                                             return;
                                          }

                                          int[] var71 = new int[]{
                                             45008,
                                             45140,
                                             45016,
                                             45021,
                                             45025,
                                             45033,
                                             45099,
                                             45147,
                                             45123,
                                             45130,
                                             45046,
                                             45092,
                                             45138,
                                             45098,
                                             45127,
                                             45143,
                                             45149,
                                             45171,
                                             45040,
                                             45155,
                                             45192,
                                             45173,
                                             45213,
                                             45079,
                                             45144
                                          };
                                          int var151 = Random.a(var71.length);
                                          SpawnTable.a(var71[var151], var3, 0, 300000L);
                                          this.e(var3, var5);
                                       } else if (var20 == 40007 || var20 == 40006) {
                                          L1Object var70 = L1World.a().a(var13);
                                          L1Character var150 = new L1Character();
                                          if (var70 == null) {
                                             var150.cF(var13);
                                             var150.cG(var14);
                                             var150.cH(var15);
                                          } else {
                                             var150.cF(var70.fr());
                                             var150.cG(var70.fs());
                                             var150.cH(var70.ft());
                                          }

                                          int var203 = this.a(var3, var70);
                                          if (var20 == 40006) {
                                             var203 *= 2;
                                          }

                                          if (var5.F() == 0) {
                                             var203 *= 2;
                                          }

                                          int var31 = var20 == 40006 ? 11737 : 10;
                                          var3.ct(var3.a(var150));
                                          var3.a(new S_AttackPacket(var3, var150, 17, var31, var203, 6, 0));
                                          var3.b(new S_AttackPacket(var3, var150, 17, var31, var203, 6, 0));
                                          this.e(var3, var5);
                                       } else if (var20 != 40008 && var20 != 40410 && var20 != 140008) {
                                          if (var20 == 41121 || var20 == 41130) {
                                             ItemTable.a(var3, var20 + 1, 1);
                                          } else if (var20 == 42501) {
                                             if (var3.eb() < 10) {
                                                var3.a(new S_ServerMessage(278));
                                             } else {
                                                var3.i_(var3.eb() - 10);
                                                L1Teleport.a(var3, var14, var15, var3.fp(), var3.fb(), true);
                                             }
                                          } else if (var20 == 41759 || var20 == 41760 || var20 == 41762) {
                                             var3.a(new S_Message_YN(2936));
                                             var3.am(var5.fr());
                                          } else if (var20 == 41763) {
                                             if (var3.cI() > 110) {
                                                var3.a(new S_ServerMessage(2962));
                                             } else {
                                                L1BookMark.a(var3);
                                                var3.j().b(var5, 1);
                                             }
                                          } else if (var20 == 41293 || var20 == 640269 || var20 == 640282) {
                                             this.a(var3, var18, var19, var5);
                                          } else if (var20 == 640272) {
                                             if (var54 == null) {
                                                var3.a(new S_ServerMessage(156));
                                                return;
                                             }

                                             if (var54.N() == 640269) {
                                                var3.j().b(var5, 1);
                                                var3.j().b(var54, 1);
                                                L1ItemInstance var69 = ItemTable.a(var3, 640282, 1);
                                                var69.a(true);
                                                var69.g(100);
                                                var3.j().b(var69);
                                             } else if (var54.N() == 640282 && var54.I() <= 300) {
                                                var3.j().b(var5, 1);
                                                var54.g(var54.I() + 100);
                                                var3.j().b(var54);
                                             } else {
                                                var3.a(new S_ServerMessage(79));
                                             }
                                          } else if (var20 == 41245) {
                                             if (var54 == null) {
                                                var3.a(new S_ServerMessage(156));
                                                return;
                                             }

                                             this.a(var3, var54, var5);
                                          } else if (var20 >= 41255 && var20 <= 41259) {
                                             if (var16 == 0) {
                                                var3.a(new S_PacketBox(52, var20 - 41255));
                                             } else {
                                                this.b(var3, var17);
                                             }
                                          } else if (var20 == 41260) {
                                             for (L1Object var67 : var3.eq()) {
                                                if (var67 instanceof L1EffectInstance && var67.f(var3) <= 3 && ((L1EffectInstance)var67).fe() == 5943) {
                                                   var3.a(new S_ServerMessage(1162));
                                                   return;
                                                }
                                             }

                                             int[] var68 = var3.eg();
                                             L1SpawnEffect.a().a(5943, 600000, var68[0], var68[1], var3.fp());
                                             var3.j().b(var5, 1);
                                          } else if (var20 == 41345) {
                                             L1DamagePoison.a(var3, var3, 3000, 5, 30);
                                             var3.j().b(var5, 1);
                                          } else if (var20 == 41315 || var20 == 41316 || var20 == 41354 || var20 == 49168) {
                                             int var66 = var5.a().V();
                                             var3.j(var66, 900000);
                                             var3.a(new S_SkillSound(var3.fr(), 190));
                                             var3.b(new S_SkillSound(var3.fr(), 190));
                                             if (var20 != 49168) {
                                                var3.a(new S_ServerMessage(var66 + 127));
                                             }

                                             var3.j().b(var5, 1);
                                          } else if (var20 == 640297 || var20 == 640298) {
                                             int var65 = var5.a().V();
                                             var3.j(1030, var65 * 1000);
                                             var3.a(new S_SkillSound(var3.fr(), var65 == 300 ? 20 : 21));
                                             var3.b(new S_SkillSound(var3.fr(), var65 == 300 ? 20 : 21));
                                             var3.j().b(var5, 1);
                                          } else if (var20 != 49092
                                             && var20 != 49094
                                             && var20 != 49098
                                             && var20 != 49317
                                             && var20 != 49321
                                             && var20 != 49198
                                             && var20 != 49199
                                             && var20 != 49188) {
                                             if (var20 == 49148 || var20 == 640145 || var20 == 640727) {
                                                Enchant.a(var3, var5, var54, false);
                                             } else if (var20 == 640614) {
                                                if (var54.N() >= 21366 && var54.N() <= 21371) {
                                                   Enchant.a(var3, var5, var54, true);
                                                } else {
                                                   var3.a(new S_ServerMessage(79));
                                                }
                                             } else if (var20 == 41426) {
                                                if (var54 == null || var54.f() && !var54.a().aN()) {
                                                   var3.a(new S_ServerMessage(79));
                                                   return;
                                                }

                                                if (var54.F() >= 128) {
                                                   var3.a(new S_ServerMessage(2124));
                                                   return;
                                                }

                                                var54.f(var54.F() + 128);
                                                var3.j().j(var54);
                                                var3.j().b(var5, 1);
                                             } else if (var20 == 41427) {
                                                if (var54.F() < 128 || var54.f() && !var54.a().aN()) {
                                                   var3.a(new S_ServerMessage(79));
                                                   return;
                                                }

                                                var54.f(var54.F() - 128);
                                                var3.j().j(var54);
                                                var3.j().b(var5, 1);
                                             } else if (var20 == 41428) {
                                                L1Account var62 = var3.aK().e();
                                                int var147 = Math.min(var62.k() + 1, 8);
                                                var62.e(var147);
                                                AccountTable.a().c(var62);
                                                var3.j().b(var5, 1);
                                             } else if (var20 == 40075) {
                                                if (var54.h()) {
                                                   int[] var63 = new int[]{167, 171, 169, 170, 168, 172, 173, 174};
                                                   if (var54.a().aP() <= 7) {
                                                      var3.a(new S_ServerMessage(var63[var54.a().aP()]));
                                                   } else {
                                                      var3.a(new S_ServerMessage(var63[0]));
                                                   }

                                                   var3.j().b(var54, 1);
                                                } else {
                                                   var3.a(new S_ServerMessage(154));
                                                }

                                                var3.j().b(var5, 1);
                                             } else if (var20 == 640941) {
                                                var3.a(new S_Html(var3.fr(), "gunterseal"));
                                             } else if (var20 == 40630) {
                                                var3.a(new S_Html(var3.fr(), "diegodiary"));
                                             } else if (var20 == 40641) {
                                                var3.a(new S_Html(var3.fr(), "tscrolla"));
                                             } else if (var20 == 40663) {
                                                var3.a(new S_Html(var3.fr(), "sonsletter"));
                                             } else if (var20 == 41007) {
                                                var3.a(new S_Html(var3.fr(), "erisscroll"));
                                             } else if (var20 == 41009) {
                                                var3.a(new S_Html(var3.fr(), "erisscroll2"));
                                             } else if (var20 == 41019) {
                                                var3.a(new S_Html(var3.fr(), "lashistory1"));
                                             } else if (var20 == 41020) {
                                                var3.a(new S_Html(var3.fr(), "lashistory2"));
                                             } else if (var20 == 41021) {
                                                var3.a(new S_Html(var3.fr(), "lashistory3"));
                                             } else if (var20 == 41022) {
                                                var3.a(new S_Html(var3.fr(), "lashistory4"));
                                             } else if (var20 == 41023) {
                                                var3.a(new S_Html(var3.fr(), "lashistory5"));
                                             } else if (var20 == 41024) {
                                                var3.a(new S_Html(var3.fr(), "lashistory6"));
                                             } else if (var20 == 41025) {
                                                var3.a(new S_Html(var3.fr(), "lashistory7"));
                                             } else if (var20 == 41026) {
                                                var3.a(new S_Html(var3.fr(), "lashistory8"));
                                             } else if (var20 == 41060) {
                                                var3.a(new S_Html(var3.fr(), "nonames"));
                                             } else if (var20 == 41061) {
                                                var3.a(new S_Html(var3.fr(), "kames"));
                                             } else if (var20 == 41062) {
                                                var3.a(new S_Html(var3.fr(), "bakumos"));
                                             } else if (var20 == 41063) {
                                                var3.a(new S_Html(var3.fr(), "bukas"));
                                             } else if (var20 == 41064) {
                                                var3.a(new S_Html(var3.fr(), "huwoomos"));
                                             } else if (var20 == 41065) {
                                                var3.a(new S_Html(var3.fr(), "noas"));
                                             } else if (var20 == 41356) {
                                                var3.a(new S_Html(var3.fr(), "rparum3"));
                                             } else if (var20 == 41340) {
                                                var3.a(new S_Html(var3.fr(), "tion"));
                                             } else if (var20 == 41317) {
                                                var3.a(new S_Html(var3.fr(), "rarson"));
                                             } else if (var20 == 41318) {
                                                var3.a(new S_Html(var3.fr(), "kuen"));
                                             } else if (var20 == 41329) {
                                                var3.a(new S_Html(var3.fr(), "anirequest"));
                                             } else if (var20 == 41346) {
                                                var3.a(new S_Html(var3.fr(), "robinscroll"));
                                             } else if (var20 == 41347) {
                                                var3.a(new S_Html(var3.fr(), "robinscroll2"));
                                             } else if (var20 == 41348) {
                                                var3.a(new S_Html(var3.fr(), "robinhood"));
                                             } else if (var20 == 49172) {
                                                var3.a(new S_Html(var3.fr(), "silrein1lt"));
                                             } else if (var20 == 49173) {
                                                var3.a(new S_Html(var3.fr(), "silrein2lt"));
                                             } else if (var20 == 49174) {
                                                var3.a(new S_Html(var3.fr(), "silrein3lt"));
                                             } else if (var20 == 49175) {
                                                var3.a(new S_Html(var3.fr(), "silrein4lt"));
                                             } else if (var20 == 49176) {
                                                var3.a(new S_Html(var3.fr(), "silrein5lt"));
                                             } else if (var20 == 49177) {
                                                var3.a(new S_Html(var3.fr(), "silrein6lt"));
                                             } else if (var20 == 49202) {
                                                var3.a(new S_Html(var3.fr(), "cot_ep1st"));
                                             } else if (var20 == 49206) {
                                                var3.a(new S_Html(var3.fr(), "bluesoul_p"));
                                             } else if (var20 == 49210) {
                                                var3.a(new S_Html(var3.fr(), "first_p"));
                                             } else if (var20 == 49211) {
                                                var3.a(new S_Html(var3.fr(), "second_p"));
                                             } else if (var20 == 49212) {
                                                var3.a(new S_Html(var3.fr(), "third_p"));
                                             } else if (var20 == 49221) {
                                                var3.a(new S_Html(var3.fr(), "spy_letter"));
                                             } else if (var20 == 49231) {
                                                var3.a(new S_Html(var3.fr(), "redsoul_p"));
                                             } else if (var20 == 49287) {
                                                var3.a(new S_Html(var3.fr(), "fourth_p"));
                                             } else if (var20 == 49288) {
                                                var3.a(new S_Html(var3.fr(), "fifth_p"));
                                             } else {
                                                int var64 = var5.a().aG();
                                                int var148 = var5.a().aH();
                                                short var30 = var5.a().aI();
                                                if (var64 == 0 || var148 == 0) {
                                                   if (var5.E() < 1) {
                                                      var3.a(new S_ServerMessage(329, var5.s()));
                                                   } else {
                                                      var3.a(new S_ServerMessage(74, var5.s()));
                                                   }
                                                } else if (!var3.fq().j() && !var3.l()) {
                                                   var3.a(new S_ServerMessage(647));
                                                   var3.a(new S_Paralysis(7, false));
                                                } else {
                                                   if (var20 >= 40103 && var20 <= 40112 && var3.fp() == var30) {
                                                      L1Teleport.a(var3, 200);
                                                   } else {
                                                      L1Teleport.a(var3, var64, var148, var30, var3.fb(), true);
                                                   }

                                                   var3.j().b(var5, 1);
                                                }
                                             }
                                          } else if (var54.a().V() == var20) {
                                             int var61 = var5.a().V();
                                             ItemTable.a(var3, var54.N() + var61, 1);
                                             var3.j().b(var54, 1);
                                             var3.j().b(var5, 1);
                                          } else {
                                             var3.a(new S_ServerMessage(79));
                                          }
                                       } else {
                                          if (var3.fq().g()) {
                                             var3.a(new S_ServerMessage(563));
                                             return;
                                          }

                                          var3.a(new S_DoActionGFX(var3.fr(), 17));
                                          var3.b(new S_DoActionGFX(var3.fr(), 17));
                                          L1Object var60 = L1World.a().a(var13);
                                          if (var60 instanceof L1Character) {
                                             this.a(var3, (L1Character)var60);
                                             this.e(var3, var5);
                                          } else {
                                             var3.a(new S_ServerMessage(79));
                                          }
                                       }
                                    } else if (var3.ay() >= 5 && var3.fp() == 2004) {
                                       this.a(var3, 81307 + var3.ay());
                                       var3.j().b(var5, 1);
                                    } else {
                                       var3.a(new S_ServerMessage(79));
                                    }
                                 } else {
                                    if (!var3.fq().j() && !var3.l()) {
                                       var3.a(new S_ServerMessage(647));
                                       var3.a(new S_Paralysis(7, false));
                                       return;
                                    }

                                    int[] var59 = L1Getback.a(var3);
                                    if (var20 == 40124 || var20 == 640497) {
                                       if (var3.bF() > 0) {
                                          var59 = L1TownLocation.a(var3.bF());
                                       }

                                       if (var3.aF() > 0) {
                                          L1Clan var146 = ClanTable.a().a(var3.aF());
                                          if (var146.m() > 0) {
                                             var59 = L1CastleLocation.d(var146.m());
                                          } else if (var146.n() > 0) {
                                             var59 = L1HouseLocation.a(var146.n());
                                          }
                                       }
                                    }

                                    L1Teleport.a(var3, var59[0], var59[1], var59[2], 5, true);
                                    var3.j().b(var5, 1);
                                 }
                              } else {
                                 if (var54 == null) {
                                    var3.a(new S_ServerMessage(79));
                                    return;
                                 }

                                 if (!var54.C()) {
                                    var54.a(true);
                                    var3.j().b(var54);
                                 }

                                 var3.a(new S_IdentifyDesc(var54));
                                 var3.j().b(var5, 1);
                              }
                           } else if (var3.fp() == 4 && var3.fu().e(new Point(33333, 32444))) {
                              int var58 = var5.a().V();
                              SpawnTable.a(var58, var3, 7, 0L);
                              var3.j().b(var5, 1);
                           } else {
                              var3.a(new S_ServerMessage(79));
                           }
                        } else if (L1PolyMorph.a(var3, var7, var5.a().V())) {
                           if (var20 == 640804) {
                              return;
                           }

                           var3.j().b(var5, 1);
                        }
                     }
                  } else if (var5.a().y() && var3.x()
                     || var5.a().z() && var3.z()
                     || var5.a().A() && var3.A()
                     || var5.a().B() && var3.B()
                     || var5.a().C() && var3.C()
                     || var5.a().D() && var3.D()
                     || var5.a().E() && var3.E()
                     || var5.a().F() && var3.F()) {
                     if (var5.g()) {
                        this.c(var3, var5);
                     } else {
                        this.b(var3, var5);
                     }
                  } else {
                     var3.a(new S_ServerMessage(264));
                  }

                  if (var52) {
                     Timestamp var144 = new Timestamp(System.currentTimeMillis());
                     var5.a(var144);
                     var3.j().j(var5);
                  }

                  L1ItemDelay.a(var2, var5);

                  for (L1QuestNew var145 : var3.dS().values()) {
                     for (int var226 = 0; var226 < var145.u().length; var226++) {
                        if (var145.u()[var226] == var20) {
                           var145.c(var226);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void a(L1PcInstance var1, L1ItemInstance var2) {
      int var3 = var2.a().V();
      if (var1.D() || var1.E()) {
         var3 = 7129 + (var2.N() - 49149) * 4;
      }

      int var4 = var3 + var1.ay() * 2 + var1.aJ();
      L1PolyMorph.a(var1, var4, 1800, 1);
      var1.j().b(var2, 1);
   }

   private void a(L1PcInstance var1, L1ItemInstance var2, L1ItemInstance var3, int var4, int var5, int var6, int var7) {
      int var8 = var2.N();
      if (var3.a().V() == var8) {
         if (Random.a(100) < var5) {
            var1.j().b(var3, 1);
            ItemTable.a(var1, var4, 1);
            if (var6 > 0) {
               var1.a(new S_ServerMessage(var6, var3.b()));
            }
         } else {
            var1.a(new S_ServerMessage(var7, var3.b()));
            if (var7 == 158) {
               var1.j().b(var3, 1);
            }
         }

         var1.j().b(var2, 1);
      } else {
         var1.a(new S_ServerMessage(79));
      }
   }

   private void a(L1PcInstance var1, L1ItemInstance var2, int var3) {
      if (!var1.B()) {
         var1.a(new S_ServerMessage(264));
      } else {
         if (var3 <= var2.a().V()) {
            L1ItemInstance var4 = ItemTable.a().b(40858 + var3);
            if (var4 != null && var1.j().a(var4, 1) == 0) {
               L1Skills var5 = SkillsTable.a().a(var3);
               if (var1.ea() + 1 < var5.e() + 1) {
                  var1.a(new S_ServerMessage(279));
                  return;
               }

               if (var1.eb() < var5.d()) {
                  var1.a(new S_ServerMessage(278));
                  return;
               }

               if (var5.f() != 0 && !var1.j().g(var5.f(), var5.g())) {
                  var1.a(new S_ServerMessage(299));
                  return;
               }

               var1.a(var1.ea() - var5.e());
               var1.i_(var1.eb() - var5.d());
               int var6 = var1.fa() + var5.o();
               var6 = Math.min(Math.max(-32767, var6), 32767);
               var1.cr(var6);
               if (var5.f() != 0) {
                  var1.j().b(var5.f(), var5.g());
               }

               var1.j().b(var2, 1);
               var1.j().d(var4);
            }
         } else {
            var1.a(new S_ServerMessage(591));
         }
      }
   }

   private void b(L1PcInstance var1, L1ItemInstance var2) {
      int var3 = var2.a().aP();
      L1PcInventory var4 = var1.j();
      boolean var5 = false;
      int var6 = 1;
      if (var3 == 9) {
         var6 = 2;
         if ((var1.cP() & S_RuneSlot.c) == S_RuneSlot.c) {
            var6++;
         }

         if ((var1.cP() & S_RuneSlot.d) == S_RuneSlot.d) {
            var6++;
         }
      } else if (var3 == 13) {
         if ((var1.cP() & S_RuneSlot.e) == S_RuneSlot.e) {
            var6++;
         }
      } else if (var3 == 23 && (var1.cP() & S_RuneSlot.f) == S_RuneSlot.f) {
         var6++;
      }

      var5 = var4.i(var3) <= var6 - 1;
      if (var5 && !var2.D()) {
         int var7 = var1.fe();
         if (!L1PolyMorph.b(var1, var3)) {
            var1.a(new S_ServerMessage(2055, var2.s()));
            return;
         }

         if (var3 == 29 && (var1.cP() & S_RuneSlot.g) != S_RuneSlot.g) {
            var1.a(new S_ServerMessage(333));
            return;
         }

         if (var3 == 30 && (var1.cP() & S_RuneSlot.h) != S_RuneSlot.h) {
            var1.a(new S_ServerMessage(333));
            return;
         }

         if (var3 == 10 && var4.i(8) >= 1 || var3 == 8 && var4.i(10) >= 1) {
            var1.a(new S_ServerMessage(124));
            return;
         }

         if ((var3 == 10 || var3 == 8) && var1.w() == 2) {
            var1.a(new S_ServerMessage(124));
            return;
         }

         if (var3 == 8 && var1.v() != null && var1.v().a().e() && !var1.F()) {
            var1.a(new S_ServerMessage(129));
            return;
         }

         if (var3 == 3 && var4.i(4) >= 1) {
            var1.a(new S_ServerMessage(126, "$224", "$225"));
            return;
         }

         if (var3 == 3 && var4.i(2) >= 1) {
            var1.a(new S_ServerMessage(126, "$224", "$226"));
            return;
         }

         if (var3 == 2 && var4.i(4) >= 1) {
            var1.a(new S_ServerMessage(126, "$226", "$225"));
            return;
         }

         if (var3 == 23 && var2.bb() == null) {
            if (var2.N() >= 21261 && var2.N() <= 21300) {
               var1.a(new S_ServerMessage(1891));
               return;
            }

            if (var2.N() == 21397 && var1.fp() != 1700 && var1.fp() != 1703) {
               var1.a(new S_ServerMessage(333));
               return;
            }
         }

         var4.a(var2, true);
      } else if (var2.D()) {
         if (var2.F() == 2) {
            var1.a(new S_ServerMessage(150));
            return;
         }

         if (var3 == 3 && var4.i(2) >= 1) {
            var1.a(new S_ServerMessage(127));
            return;
         }

         if ((var3 == 2 || var3 == 3) && var4.i(4) >= 1) {
            var1.a(new S_ServerMessage(127));
            return;
         }

         if (var3 == 23 && var2.bb() == null && var2.N() >= 21261 && var2.N() <= 21300) {
            var1.a(new S_ServerMessage(1891));
            return;
         }

         if (var3 == 8 && var1.bB(90)) {
            var1.bz(90);
         }

         var4.a(var2, false);
      } else {
         var1.a(new S_ServerMessage(124));
      }

      var1.a(var1.ea());
      var1.i_(var1.eb());
      var1.a(new S_OwnCharAttrDef(var1));
      var1.a(new S_OwnCharStatus(var1));
      var1.a(new S_SPMR(var1));
   }

   private void c(L1PcInstance var1, L1ItemInstance var2) {
      L1PcInventory var3 = var1.j();
      if (var1.v() == null || !var1.v().equals(var2)) {
         int var4 = var2.a().aP();
         int var5 = var1.fe();
         if (!L1PolyMorph.a(var1, var4)) {
            var1.a(new S_ServerMessage(2055, var2.s()));
            return;
         }

         if (var2.a().e() && var3.i(8) >= 1 && !var1.bB(603)) {
            var1.a(new S_ServerMessage(128));
            return;
         }

         if (var2.N() == 413 && var1.fp() != 6311) {
            var1.a(new S_ServerMessage(333));
            return;
         }
      }

      if (var1.v() != null) {
         if (var1.a(var2)) {
            if (var2.F() == 2) {
               var1.a(new S_ServerMessage(150));
               return;
            }

            var3.a(var2, false);
            return;
         }

         if (!var1.bB(603) || var3.i(8) >= 1 || var3.i(10) >= 1 || var1.w() == 2) {
            var3.a(var1.v(), false);
         }
      }

      if (var2.N() == 200002) {
         var1.a(new S_ServerMessage(149, var2.s()));
      }

      var3.a(var2, true);
   }

   private void d(L1PcInstance var1, L1ItemInstance var2) {
      int var3 = var2.a().V();
      L1Skills var4 = SkillsTable.a().a(var3);
      int var5 = var4.c();
      boolean var6 = true;
      if (var3 >= 1 && var3 <= 80) {
         int var7 = 0;
         if ((var1.x() || var1.C()) && var5 <= 2) {
            var7 = 10;
         } else if (var1.A() && var5 <= 6) {
            var7 = 8;
         } else if (var1.B() && var5 <= 10) {
            var7 = 4;
         } else if (var1.z() && var5 <= 1) {
            var7 = 50;
         } else if (var1.F() && var5 <= 1) {
            var7 = 50;
         }

         if (var7 == 0) {
            var1.a(new S_ServerMessage(79));
            return;
         }

         if (var1.ev() / var7 < var5) {
            var1.a(new S_ServerMessage(312));
            return;
         }
      }

      if (var3 >= 87 && var3 <= 92) {
         if (!var1.z()) {
            var1.a(new S_ServerMessage(79));
            return;
         }

         int var8 = var3 == 89 ? 60 : 50;
         if (var3 == 92) {
            var8 = 80;
         }

         if (var1.ev() < var8) {
            var1.a(new S_ServerMessage(312));
            return;
         }
      }

      if (var3 >= 97 && var3 <= 112 || var3 == 233) {
         if (!var1.C()) {
            var1.a(new S_ServerMessage(79));
            return;
         }

         int var9 = 60;
         if ((var3 < 97 || var3 > 100) && var3 != 109) {
            if ((var3 < 101 || var3 > 104) && var3 != 110) {
               if ((var3 < 105 || var3 > 108) && var3 != 111) {
                  if (var3 == 112) {
                     var9 = 60;
                  } else if (var3 == 233) {
                     var9 = 80;
                  }
               } else {
                  var9 = 45;
               }
            } else {
               var9 = 30;
            }
         } else {
            var9 = 15;
         }

         if (var1.ev() < var9) {
            var1.a(new S_ServerMessage(312));
            return;
         }
      }

      if (var3 >= 113 && var3 <= 122) {
         if (!var1.x()) {
            var1.a(new S_ServerMessage(79));
            return;
         }

         int var10 = 60;
         if (var3 == 113) {
            var10 = 15;
         } else if (var3 == 116) {
            var10 = 30;
         } else if (var3 == 114) {
            var10 = 40;
         } else if (var3 == 118) {
            var10 = 45;
         } else if (var3 == 117) {
            var10 = 50;
         } else if (var3 == 115) {
            var10 = 50;
         } else if (var3 >= 119) {
            var10 = 60;
         } else if (var3 >= 122) {
            var10 = 80;
         }

         if (var1.ev() < var10) {
            var1.a(new S_ServerMessage(312));
            return;
         }
      }

      if (var3 >= 129 && var3 <= 176) {
         if (!var1.A()) {
            var1.a(new S_ServerMessage(79));
            return;
         }

         int var11 = 50;
         byte var12;
         if (var3 >= 129 && var3 <= 131) {
            var12 = 10;
         } else if (var3 >= 137 && var3 <= 138) {
            var12 = 20;
         } else if ((var3 < 145 || var3 > 152) && var3 != 132 && var3 != 170) {
            if ((var3 < 153 || var3 > 160) && var3 != 133) {
               if (var3 == 135) {
                  var12 = 80;
               } else {
                  var12 = 50;
               }
            } else {
               var12 = 40;
            }
         } else {
            var12 = 30;
         }

         if (var1.ev() < var12) {
            var1.a(new S_ServerMessage(312));
            return;
         }
      }

      if (var3 >= 181 && var3 <= 196) {
         if (!var1.D()) {
            var1.a(new S_ServerMessage(79));
            return;
         }

         int var13 = ((var3 - 181) / 5 + 1) * 15;
         if (var3 == 196) {
            var13 = 80;
         }

         if (var1.ev() < var13) {
            var1.a(new S_ServerMessage(312));
            return;
         }
      }

      if (var3 >= 201 && var3 <= 222) {
         if (!var1.E()) {
            var1.a(new S_ServerMessage(79));
            return;
         }

         int var14 = ((var3 - 201) / 5 + 1) * 10;
         if (var3 == 222) {
            var14 = 80;
         }

         if (var1.ev() < var14) {
            var1.a(new S_ServerMessage(312));
            return;
         }
      }

      if (var3 >= 225 && var3 <= 231 && !var1.F()) {
         var1.a(new S_ServerMessage(79));
      } else {
         var1.a(new S_AddSkill(var1, var3));
         var1.a(new S_SkillSound(var1.fr(), var4.o() >= 0 ? 224 : 231));
         var1.b(new S_SkillSound(var1.fr(), 224));
         SkillsTable.a().a(var1.fr(), var4.a(), var4.b(), 0, 0);
         var1.j().b(var2, 1);
      }
   }

   private void e(L1PcInstance var1, L1ItemInstance var2) {
      if (var2.I() > 1) {
         var2.g(var2.I() - 1);
         var1.j().b(var2);
      } else {
         var1.j().b(var2, 1);
      }
   }

   private int a(L1PcInstance var1, L1Object var2) {
      if (var2 != null && var1.i(var2.fs(), var2.ft())) {
         int var3 = var1.ez() * 3 + Random.a(var1.ez());
         if (var2 instanceof L1PcInstance) {
            L1PcInstance var6 = (L1PcInstance)var2;
            if (var1.fr() == var6.fr() || var1.a(var1, var6, false)) {
               return 0;
            }

            if (L1Magic.a(var6)) {
               return 0;
            }

            int var5 = var6.ea() - var3;
            if (var5 > 0) {
               var6.a(var5);
            } else if (var5 <= 0 && var6.l()) {
               var6.a(var6.ew());
            } else {
               var6.b((L1Character)var1);
            }

            return var3;
         } else if (var2 instanceof L1MonsterInstance) {
            L1MonsterInstance var4 = (L1MonsterInstance)var2;
            var4.b(var1, var3);
            return var3;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private void a(L1PcInstance var1, L1Character var2) {
      boolean var3 = var1.fr() == var2.fr();
      int var4 = Random.a(L1PolyMorph.f.length);
      int var5 = L1PolyMorph.f[var4];
      int var6 = 3 * (var1.ev() - var2.ev()) + 100 - var2.W_();
      if (var2 instanceof L1PcInstance) {
         L1PcInstance var7 = (L1PcInstance)var2;
         if (var3 || var7.aF() != 0 && var7.aF() == var1.aF()) {
            var6 = 100;
         }

         if (var6 <= Random.a(100)) {
            var1.a(new S_ServerMessage(79));
            return;
         }

         if (var7.j().h(20281)) {
            var7.a(new S_Message_YN(180, ""));
            var7.t(true);
         } else {
            L1PolyMorph.a(var7, var5, 1800, 1);
         }

         if (!var3) {
            var7.a(new S_ServerMessage(241, var1.et()));
         }
      } else if (var2 instanceof L1MonsterInstance) {
         L1MonsterInstance var13 = (L1MonsterInstance)var2;
         if (var13.ev() < 50) {
            int[] var8 = new int[]{45338, 45370, 45456, 45464, 45473, 45488, 45497, 45516, 45529, 45458};
            int[] var12 = var8;
            int var11 = var8.length;

            for (int var10 = 0; var10 < var11; var10++) {
               int var9 = var12[var10];
               if (var13.z() == var9) {
                  return;
               }
            }

            L1PolyMorph.a(var13, var5, 1800, 1);
         }
      }
   }

   private void f(L1PcInstance var1, L1ItemInstance var2) {
      boolean var3 = false;
      if (var2.N() >= 40288 && var2.N() <= 40297) {
         if (var1.fs() >= 33924 && var1.fs() <= 33930 && var1.ft() >= 33341 && var1.ft() <= 33349) {
            var3 = true;
         }
      } else if (var2.N() >= 640462 && var2.N() <= 640471) {
         if (var1.fs() >= 33924 && var1.fs() <= 33930 && var1.ft() >= 33341 && var1.ft() <= 33349) {
            var3 = true;
         }
      } else if (var2.N() == 40615) {
         if (var1.fs() >= 32701 && var1.fs() <= 32705 && var1.ft() >= 32894 && var1.ft() <= 32898) {
            var3 = true;
         }
      } else if (var2.N() != 40616 && var2.N() != 40782 && var2.N() != 40783) {
         if (var2.N() == 40692 && var1.j().f(40621)) {
            if (var1.fs() >= 32856 && var1.fs() <= 32858 && var1.ft() >= 32857 && var1.ft() <= 32858) {
               var3 = true;
            }
         } else {
            if (var2.N() != 41208) {
               L1Teleport.a(var1, var2.a().aG(), var2.a().aH(), var2.a().aI(), 5, true);
               return;
            }

            if (var1.fs() >= 32844 && var1.fs() <= 32845 && var1.ft() >= 32693 && var1.ft() <= 32694) {
               var3 = true;
            }
         }
      } else if (var1.fs() >= 32698 && var1.fs() <= 32702 && var1.ft() >= 32894 && var1.ft() <= 32898) {
         var3 = true;
      }

      if (var1.fp() != var2.a().V()) {
         var3 = false;
      }

      if (var3) {
         L1Teleport.a(var1, var2.a().aG(), var2.a().aH(), var2.a().aI(), 5, true);
      } else {
         var1.a(new S_ServerMessage(79));
         var1.a(new S_Paralysis(7, false));
      }
   }

   private void a(L1PcInstance var1, int var2) {
      for (L1Object var3 : L1World.a().b()) {
         if (var3 instanceof L1MonsterInstance && ((L1MonsterInstance)var3).z() == var2) {
            var1.a(new S_ServerMessage(79));
            return;
         }
      }

      SpawnTable.a(var2, var1, 0, 0L);
   }

   private void g(L1PcInstance var1, L1ItemInstance var2) {
      int var3 = var2.fr();
      if (!var1.fq().n()) {
         var1.a(new S_ServerMessage(563));
      } else {
         int var4 = 0;

         for (L1NpcInstance var5 : var1.ek().values()) {
            if (var5 instanceof L1PetInstance && ((L1PetInstance)var5).k() == var3) {
               return;
            }

            var4 += var5.Q();
         }

         int var10 = var1.eC() + (var1.A() ? 12 : 6) - var4;
         int var11 = var10 / 6;
         if (var11 <= 0) {
            var1.a(new S_ServerMessage(489));
         } else if (!var1.j().b(41160, 1)) {
            var1.a(new S_ServerMessage(79));
         } else {
            L1Pet var7 = PetTable.a().b(var3);
            if (var7 != null) {
               L1Npc var8 = NpcTable.a().a(var7.c());
               L1PetInstance var9 = new L1PetInstance(var8, var1, var7);
               var9.o(6);
            }
         }
      }
   }

   private void a(L1PcInstance var1, int var2, int var3, L1ItemInstance var4) {
      if (var1.fp() != 5300 && var1.fp() != 5301 && var1.fp() != 5490) {
         var1.a(new S_ServerMessage(1138));
      } else if (var1.fe() != var1.aB()) {
         var1.a(new S_ServerMessage(1170));
      } else if (!L1World.a().c(var1, 0).isEmpty()) {
         var1.a(new S_SystemMessage("這個位置已被佔據。"));
      } else if (var1.ff() && !var1.l()) {
         var1.a(new S_SystemMessage("隱身釣魚是不科學的。"));
      } else {
         int var5 = 6;
         if (!var1.fq().g(var2, var3)) {
            var1.a(new S_ServerMessage(1138));
         } else if (var1.fq().g(var2 + 1, var3) && var1.fq().g(var2 - 1, var3) && var1.fq().g(var2, var3 + 1) && var1.fq().g(var2, var3 - 1)) {
            if (var2 > var1.fs() + 6 || var2 < var1.fs() - 6) {
               var1.a(new S_ServerMessage(1138));
            } else if (var3 > var1.ft() + 6 || var3 < var1.ft() - 6) {
               var1.a(new S_ServerMessage(1138));
            } else if (var1.j().b(640270, 1)) {
               var1.aO(var2);
               var1.aP(var3);
               var1.a(new S_Fishing(var1.fr(), 71, var2, var3));
               var1.b(new S_Fishing(var1.fr(), 71, var2, var3));
               var1.r(true);
               GeneralThreadPool.a().b(new FishingTimer(var1, var4));
            } else {
               var1.a(new S_ServerMessage(1137));
            }
         } else {
            var1.a(new S_ServerMessage(1138));
         }
      }
   }

   private void a(L1PcInstance var1, L1ItemInstance var2, L1ItemInstance var3) {
      if (!var2.g() && !var2.h() || !var2.D() && var2.G() == 0) {
         int var4 = ResolventTable.a().a(var2.N());
         if (var4 == 0) {
            var1.a(new S_ServerMessage(1161));
         } else {
            int var5 = Random.a(100);
            if (var5 < 50) {
               var4 = 0;
               var1.a(new S_ServerMessage(158, var2.b()));
            } else if (var5 >= 90) {
               var4 = (int)(var4 * 1.5);
            }

            if (var4 > 0) {
               ItemTable.a(var1, 41246, var4);
            }

            HistoryTable.a().b(var1, "獲得結晶" + var4 + "個，融掉了", var2);
            var1.j().b(var2, 1);
            var1.j().b(var3, 1);
         }
      } else {
         var1.a(new S_ServerMessage(1161));
      }
   }

   private void b(L1PcInstance var1, int var2) {
      boolean var3 = false;

      for (L1Object var4 : var1.eq()) {
         if (var4 instanceof L1EffectInstance && var4.f(var1) <= 3 && ((L1EffectInstance)var4).fe() == 5943) {
            var3 = true;
            break;
         }
      }

      if (!var3) {
         var1.a(new S_ServerMessage(1160));
      } else if (var1.K() <= var1.j().e()) {
         var1.a(new S_ServerMessage(1103));
      } else if (!var1.bB(2999)) {
         var1.j(2999, 3000);
         int var11 = Random.a(100) + 1;
         int[] var12 = new int[0];
         int[] var6 = new int[0];
         switch (var2) {
            case 0:
               var12 = new int[]{40057};
               var6 = new int[]{41277, 41285};
               break;
            case 1:
               var12 = new int[]{41275};
               var6 = new int[]{41278, 41286};
               break;
            case 2:
               var12 = new int[]{41263, 41265};
               var6 = new int[]{41279, 41287};
               break;
            case 3:
               var12 = new int[]{41274, 41267};
               var6 = new int[]{41280, 41288};
               break;
            case 4:
               var12 = new int[]{40062, 40069, 40064};
               var6 = new int[]{41281, 41289};
               break;
            case 5:
               var12 = new int[]{40056, 40060, 40061};
               var6 = new int[]{41282, 41290};
               break;
            case 6:
               var12 = new int[]{41276};
               var6 = new int[]{41283, 41291};
               break;
            case 7:
               var12 = new int[]{40499, 40060};
               var6 = new int[]{41284, 41292};
               break;
            case 8:
               var12 = new int[]{49040, 49048};
               var6 = new int[]{49049, 49057};
               break;
            case 9:
               var12 = new int[]{49041, 49048};
               var6 = new int[]{49050, 49058};
               break;
            case 10:
               var12 = new int[]{49042, 41265, 49048};
               var6 = new int[]{49051, 49059};
               break;
            case 11:
               var12 = new int[]{49043, 49048};
               var6 = new int[]{49052, 49060};
               break;
            case 12:
               var12 = new int[]{49044, 49048};
               var6 = new int[]{49053, 49061};
               break;
            case 13:
               var12 = new int[]{49045, 49048};
               var6 = new int[]{49054, 49062};
               break;
            case 14:
               var12 = new int[]{49046, 49048};
               var6 = new int[]{49055, 49063};
               break;
            case 15:
               var12 = new int[]{49047, 40499, 49048};
               var6 = new int[]{49056, 49064};
               break;
            case 16:
               var12 = new int[]{49048, 49243, 49260};
               var6 = new int[]{49244, 49252};
               break;
            case 17:
               var12 = new int[]{49048, 49243, 49261};
               var6 = new int[]{49245, 49253};
               break;
            case 18:
               var12 = new int[]{49048, 49243, 49262};
               var6 = new int[]{49246, 49254};
               break;
            case 19:
               var12 = new int[]{49048, 49243, 49263};
               var6 = new int[]{49247, 49255};
               break;
            case 20:
               var12 = new int[]{49048, 49243, 49264};
               var6 = new int[]{49248, 49256};
               break;
            case 21:
               var12 = new int[]{49048, 49243, 49265};
               var6 = new int[]{49249, 49257};
               break;
            case 22:
               var12 = new int[]{49048, 49243, 49266};
               var6 = new int[]{49250, 49258};
               break;
            case 23:
               var12 = new int[]{49048, 49243, 49267, 40499};
               var6 = new int[]{49251, 49259};
         }

         if (var1.j().a(var12)) {
            int[] var10 = var12;
            int var9 = var12.length;

            for (int var8 = 0; var8 < var9; var8++) {
               int var7 = var10[var8];
               var1.j().b(var7, 1);
            }

            int var13 = 6394;
            if (var11 <= 90) {
               ItemTable.a(var1, var6[0], 1);
               var13 = 6392;
            } else if (var11 > 95) {
               ItemTable.a(var1, var6[1], 1);
               var13 = 6390;
            } else {
               var1.a(new S_ServerMessage(1101));
            }

            var1.a(new S_SkillSound(var1.fr(), var13));
            var1.b(new S_SkillSound(var1.fr(), var13));
         } else {
            var1.a(new S_ServerMessage(1102));
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_ItemUSe";
   }
}
