package l1r.aj;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.CharacterTable;
import l1r.ao.ClanMembersTable;
import l1r.ao.ClanTable;
import l1r.ao.HouseTable;
import l1r.ao.ItemTable;
import l1r.ao.NpcTable;
import l1r.ao.PetTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.aq.L1Character;
import l1r.aq.L1ChatParty;
import l1r.aq.L1Clan;
import l1r.aq.L1Location;
import l1r.aq.L1Master;
import l1r.aq.L1Object;
import l1r.aq.L1Party;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1Teleport;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.as.L1CastleWar;
import l1r.ax.L1Map;
import l1r.be.S_ChangeName;
import l1r.be.S_CharEvent;
import l1r.be.S_CharTitle;
import l1r.be.S_CharVisualUpdate;
import l1r.be.S_ClanName;
import l1r.be.S_Message_YN;
import l1r.be.S_OwnCharStatus2;
import l1r.be.S_PacketBox;
import l1r.be.S_PledgeWatch;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_Resurrection;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_Trade;
import l1r.bh.L1BookMark;
import l1r.bh.L1House;
import l1r.bh.L1Npc;
import l1r.bh.L1Pet;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_Attr extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_Attr.class.getName());
   private static final int[] b = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
   private static final int[] c = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
   private static final int d = 0;
   private static final int e = 1;

   public C_Attr(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.d();
         if (var4 == 0) {
            int var5 = this.b();
            var4 = this.d();
         }

         int var6 = 0;
         switch (var4) {
            case 97:
               var6 = this.d();
               L1PcInstance var7 = (L1PcInstance)L1World.a().a(var3.aQ());
               var3.am(0);
               if (var7 != null) {
                  if (var6 == 0) {
                     var7.a(new S_ServerMessage(96, var3.et()));
                  } else if (var6 == 1) {
                     int var46 = var3.aF();
                     L1Clan var47 = ClanTable.a().a(var3.aF());
                     if (var47 == null) {
                        return;
                     }

                     int var48 = var3.eC() * 3 * (2 + var3.ev() / 50);
                     if (var3.ev() < 45) {
                        var48 /= 3;
                     }

                     if (Config.U > 0) {
                        var48 = Config.U;
                     }

                     if (var7.aF() == 0) {
                        if (var48 <= var47.p().size()) {
                           var7.a(new S_ServerMessage(188, var3.et()));
                           return;
                        }

                        if (var7.x() && var3.ev() < 45) {
                           var3.a(new S_ServerMessage(1530));
                           return;
                        }

                        for (L1PcInstance var50 : var47.b()) {
                           var50.a(new S_ServerMessage(94, var7.et()));
                        }

                        var7.ah(var46);
                        var7.c(var47.f());
                        var7.ai(7);
                        var7.f("");
                        var7.I();
                        var47.a(var7.eu());
                        ClanMembersTable.a().a(var7);
                        var7.a(new S_ClanName(var7));
                        var7.a(new S_CharTitle(var7.fr(), ""));
                        var7.b(new S_CharTitle(var7.fr(), ""));
                        var7.a(new S_ClanName(var7, true));
                        var7.a(new S_CharEvent(60, var7.fr(), var47.e()));
                        var7.a(new S_PledgeWatch(var47));
                        var7.a(new S_PacketBox(173, var47.j()));
                        var7.a(new S_ServerMessage(95, var47.f()));

                        for (L1PcInstance var51 : var47.b()) {
                           var51.a(new S_CharEvent(60, var7.fr(), var47.i()));
                           var7.a(new S_CharEvent(60, var51.fr(), var47.i()));
                           if (var47.b().size() >= 3 && !var51.bB(4084)) {
                              var51.j(4084, 0);
                              var51.a(new S_PacketBox(180, 450, 3240, 1));
                           }
                        }
                     } else if (Config.V && var3.ev() >= 45) {
                        this.a(var3, var7, var48);
                     } else {
                        var7.a(new S_ServerMessage(89));
                     }
                  }
               }
               break;
            case 180:
               var6 = this.c();
               String var8 = this.g();
               if (var3.cy()) {
                  L1PolyMorph.a(var3, var8, 7200);
                  var3.t(false);
               }
               break;
            case 217:
            case 221:
            case 222:
               var6 = this.d();
               L1PcInstance var9 = (L1PcInstance)L1World.a().a(var3.aQ());
               if (var9 == null || var3.aF() == 0) {
                  return;
               }

               var3.am(0);
               String var10 = var9.aG();
               if (var6 == 0) {
                  if (var4 == 217) {
                     var9.a(new S_ServerMessage(236, var3.aG()));
                  } else if (var4 == 221 || var4 == 222) {
                     var9.a(new S_ServerMessage(237, var3.aG()));
                  }
               } else if (var6 == 1) {
                  if (var4 == 217) {
                     new L1War(2, var10, var3.aG());
                  } else if (var4 == 221 || var4 == 222) {
                     L1War var49 = L1World.a().c(var3.aG());
                     if (var49 != null) {
                        if (var4 == 221) {
                           var49.a(var10, var3.aG());
                        } else if (var4 == 222) {
                           var49.b(var10, var3.aG());
                        }
                     }
                  }
               }
               break;
            case 252:
               var6 = this.d();
               L1PcInstance var11 = (L1PcInstance)L1World.a().a(var3.aO());
               if (var11 != null) {
                  if (var6 == 0) {
                     var11.a(new S_ServerMessage(253, var3.et()));
                     var3.al(0);
                     var11.al(0);
                  } else if (var6 == 1) {
                     var3.a(new S_Trade(var11.et()));
                     var11.a(new S_Trade(var3.et()));
                  }
               }
               break;
            case 321:
               var6 = this.d();
               L1PcInstance var12 = (L1PcInstance)L1World.a().a(var3.aQ());
               var3.am(0);
               if (var12 != null && var6 == 1) {
                  this.a(var3, var12, (short)(var3.ew() / 2));
               }
               break;
            case 322:
               var6 = this.d();
               L1PcInstance var13 = (L1PcInstance)L1World.a().a(var3.aQ());
               var3.am(0);
               if (var13 != null && var6 == 1) {
                  this.a(var3, var13, var3.ew());
                  if (var3.ca() >= 1 && var3.cb() && var3.cc()) {
                     var3.a(true);
                     var3.aH(0);
                     var3.k(false);
                  }
               }

               var3.bA(165);
               break;
            case 325: {
               var6 = this.c();
               String var28 = this.g();
               L1Object var14Object = L1World.a().a(var3.aQ());
               var3.am(0);
               if (!(var14Object instanceof L1PetInstance)) {
                  return;
               }
               L1PetInstance var14 = (L1PetInstance)var14Object;
               a(var14, var28);
               break;
            }
            case 479:
               if (this.c() == 1) {
                  String var54 = this.g();
                  if (var3.ev() - 50 <= var3.bA()) {
                     return;
                  }

                  if (var54.equalsIgnoreCase("str") && var3.bf() < 45) {
                     var3.o(1);
                     var3.ay(var3.bA() + 1);
                     var3.a(new S_OwnCharStatus2(var3));
                     var3.I();
                  } else if (var54.equalsIgnoreCase("dex") && var3.bh() < 45) {
                     var3.q(1);
                     var3.W();
                     var3.ay(var3.bA() + 1);
                     var3.a(new S_OwnCharStatus2(var3));
                     var3.I();
                  } else if (var54.equalsIgnoreCase("con") && var3.bg() < 45) {
                     var3.p(1);
                     var3.ay(var3.bA() + 1);
                     var3.a(new S_OwnCharStatus2(var3));
                     var3.I();
                  } else if (var54.equalsIgnoreCase("int") && var3.bj() < 45) {
                     var3.s(1);
                     var3.ay(var3.bA() + 1);
                     var3.a(new S_OwnCharStatus2(var3));
                     var3.I();
                  } else if (var54.equalsIgnoreCase("wis") && var3.bk() < 45) {
                     var3.t(1);
                     var3.Y();
                     var3.ay(var3.bA() + 1);
                     var3.a(new S_OwnCharStatus2(var3));
                     var3.I();
                  } else if (var54.equalsIgnoreCase("cha") && var3.bi() < 45) {
                     var3.r(1);
                     var3.ay(var3.bA() + 1);
                     var3.a(new S_OwnCharStatus2(var3));
                     var3.I();
                  } else {
                     var3.a(new S_ServerMessage(481));
                  }

                  var3.a(new S_ProtoBuffers(490, var3));
                  if (var3.ev() - 50 > var3.bA() && var3.bf() + var3.bh() + var3.bg() + var3.bj() + var3.bk() + var3.bi() < 270) {
                     int var56 = var3.ev() - 50 - var3.bA();
                     var3.a(new S_Message_YN(479, "" + var56));
                  }
               }
               break;
            case 512: {
               var6 = this.d();
               String var27 = this.g();
               int var15 = var3.aQ();
               var3.am(0);
               if (var27.length() <= 16) {
                  L1House var55 = HouseTable.a().a(var15);
                  if (var55 == null) {
                     return;
                  }
                  var55.a(var27);
                  HouseTable.a().a(var55);
               } else {
                  var3.a(new S_ServerMessage(513));
               }
               break;
            }
            case 630:
               var6 = this.d();
               L1PcInstance var16 = (L1PcInstance)L1World.a().a(var3.cp());
               if (var16 == null) {
                  var3.aN(0);
                  return;
               }
               if (var6 == 0) {
                  var3.aN(0);
                  var16.aN(0);
                  var16.a(new S_ServerMessage(631, var3.et()));
               } else if (var6 == 1) {
                  var16.a(new S_PacketBox(5, var16.cp(), var16.fr()));
                  var3.a(new S_PacketBox(5, var3.cp(), var3.fr()));
               }
               break;
            case 653:
               var6 = this.d();
               int partnerId653 = var3.bD();
               L1PcInstance var17 = (L1PcInstance)L1World.a().a(partnerId653);
               if (var6 == 0) {
                  return;
               }
               if (var6 == 1) {
                  if (!CharacterTable.a().clearPartnerRelationAtomic(var3, partnerId653, var17)) {
                     return;
                  }
                  if (var17 != null) {
                     var17.a(new S_ServerMessage(662));
                  }
                  var3.a(new S_ServerMessage(662));
               }
               break;
            case 654:
               var6 = this.d();
               L1PcInstance var18 = (L1PcInstance)L1World.a().a(var3.aQ());
               var3.am(0);
               if (var18 != null) {
                  if (var6 == 0) {
                     var18.a(new S_ServerMessage(656, var3.et()));
                  } else if (var6 == 1) {
                     if (!CharacterTable.a().updatePartnerRelationAtomic(var3, var18)) {
                        return;
                     }
                     var3.a(new S_ServerMessage(790));
                     var3.a(new S_ServerMessage(655, var18.et()));
                     var18.a(new S_ServerMessage(790));
                     var18.a(new S_ServerMessage(655, var3.et()));
                  }
               }
               break;
            case 729:
               var6 = this.d();
               if (var6 == 0) {
                  var3.a(new S_ServerMessage(79));
               } else if (var6 == 1) {
                  this.a(var3);
               }
               break;
            case 738:
               var6 = this.d();
               if (var6 == 1 && var3.ca() >= 1) {
                  int var58 = 0;
                  int var61 = var3.ev();
                  int var63 = var3.fa();
                  if (var61 < 45) {
                     var58 = var61 * var61 * 100;
                  } else {
                     var58 = var61 * var61 * 200;
                  }

                  if (var63 >= 0) {
                     var58 /= 2;
                  }

                  if (var3.j().b(40308, var58)) {
                     var3.a(false);
                     var3.aH(0);
                  } else {
                     var3.a(new S_ServerMessage(189));
                  }
               }
               break;
            case 744:
               var6 = this.d();
               if (var6 == 0) {
                  var3.a(new S_ServerMessage(79));
               } else if (var6 == 1) {
                  int[] var57 = var3.cA();
                  L1Teleport.a(var3, var57[0], var57[1], var57[2], 5, true);
               }
               break;
            case 951:
               var6 = this.d();
               L1PcInstance var19 = (L1PcInstance)L1World.a().a(var3.aN());
               if (var19 != null) {
                  if (var6 == 0) {
                     var19.a(new S_ServerMessage(423, var3.et()));
                     var3.ak(0);
                  } else if (var6 == 1) {
                     if (var19.r()) {
                        if (!var19.aM().a() && !var19.l()) {
                           var19.a(new S_ServerMessage(417));
                        } else {
                           var19.aM().a(var3);
                        }
                     } else {
                        L1ChatParty var60 = new L1ChatParty();
                        var60.a(var19);
                        var60.a(var3);
                        var19.a(new S_ServerMessage(424, var3.et()));
                     }
                  }
               }
               break;
            case 953:
               var6 = this.d();
               L1PcInstance var20 = (L1PcInstance)L1World.a().a(var3.aN());
               if (var20 != null) {
                  if (var6 == 0) {
                     var20.a(new S_ServerMessage(423, var3.et()));
                     var3.ak(0);
                  } else if (var6 == 1) {
                     if (var20.q()) {
                        if (var20.aL().b() >= Config.W && !var20.l()) {
                           var20.a(new S_ServerMessage(417));
                        } else {
                           var20.aL().a(var3);
                           L1Master.a().e(var3);
                        }
                     } else {
                        L1Party var62 = new L1Party(var20);
                        var62.a(var3);
                        L1Master.a().d(var20);
                        L1Master.a().d(var3);
                        var20.a(new S_ServerMessage(424, var3.et()));
                     }
                  }
               }
               break;
            case 954:
               var6 = this.d();
               L1PcInstance var21 = (L1PcInstance)L1World.a().a(var3.aN());
               if (var21 != null) {
                  if (var6 == 0) {
                     var21.a(new S_ServerMessage(423, var3.et()));
                     var3.ak(0);
                  } else if (var6 == 1) {
                     if (var21.q()) {
                        if (var21.aL().b() >= Config.W && !var21.l()) {
                           var21.a(new S_ServerMessage(417));
                        } else {
                           var21.aL().a(var3);
                           L1Master.a().e(var3);
                        }
                     } else {
                        L1Party var68 = new L1Party(var21);
                        var68.a(var3);
                        L1Master.a().d(var21);
                        L1Master.a().d(var3);
                        var21.a(new S_ServerMessage(424, var3.et()));
                     }
                  }
               }
               break;
            case 1322:
               if (this.c() == 1) {
                  if (var3.j().b(640234, 1)) {
                     L1PetInstance var67 = (L1PetInstance)L1World.a().a(var3.aQ());
                     L1Npc var73 = NpcTable.a().a(var67.z());
                     var67.e(var73.c());
                     var3.a(new S_ChangeName(var67.fr(), var73.c()));
                     var3.b(new S_ChangeName(var67.fr(), var73.c()));
                     var3.a(new S_Message_YN(325));
                  } else {
                     var3.am(0);
                     var3.a(new S_ServerMessage(337, "$5843"));
                  }
               }
               break;
            case 2935:
               if (this.c() == 1) {
                  L1ItemInstance var66 = ItemTable.a(var3, 41762, 1);
                  L1BookMark.a(var3, var66);
                  L1ItemInstance var72 = var3.j().e(var3.aQ());
                  if (var72 == null) {
                     var3.a(new S_ServerMessage(156));
                     return;
                  }

                  var3.j().f(var72);
               } else {
                  var3.a(new S_ServerMessage(79));
               }
               break;
            case 2936:
               if (this.c() == 1) {
                  L1ItemInstance var65 = var3.j().e(var3.aQ());
                  if (var65 == null) {
                     var3.a(new S_ServerMessage(156));
                     return;
                  }

                  HashMap var71 = new HashMap<>();
                  if (var65.N() == 41762) {
                     L1BookMark.a(var65, var3);
                     return;
                  }

                  if (var65.N() == 41759) {
                     var71 = L1BookMark.b;
                  } else if (var65.N() == 41760) {
                     var71 = L1BookMark.a;
                  }

                  int var74 = var3.cI();
                  if (var74 - var3.ba().size() < var71.size()) {
                     int var76 = var71.size() - (var74 - var3.ba().size());
                     var3.a(new S_ServerMessage(2961, "" + var76));
                  } else {
                     L1BookMark.a(var3, var71);
                     var3.j().f(var65);
                  }
               } else {
                  var3.a(new S_ServerMessage(79));
               }
               break;
            case 2967:
               if (this.c() == 1) {
                  L1Object var64 = L1World.a().a(var3.aQ());
                  if (var64 instanceof L1PcInstance) {
                     L1PcInstance var70 = (L1PcInstance)var64;
                     var70.a(new S_Message_YN(2968, var3.et()));
                     var70.am(var3.fr());
                  }
               } else {
                  var3.a(new S_ServerMessage(2965));
               }

               var3.am(0);
               break;
            case 2968:
               L1Object var22 = L1World.a().a(var3.aQ());
               if (var22 instanceof L1PcInstance) {
                  L1PcInstance var69 = (L1PcInstance)var22;
                  if (this.c() == 1) {
                     var69.aV(var3.fr());
                     var3.aV(-1);
                     L1Master.a().a(var3.fr(), var69);
                     var3.a(new S_ServerMessage(2964));
                     var69.a(new S_ServerMessage(2964));
                  } else {
                     var3.a(new S_ServerMessage(2965));
                     var69.a(new S_ServerMessage(2965));
                  }
               }

               var3.am(0);
               break;
            case 3348:
               L1Clan var23 = ClanTable.a().a(var3.aQ());
               if (var23 != null && this.c() == 1) {
                  L1Clan var24 = ClanTable.a().a(var3.aF());
                  if (var24 != null && ClanTable.a().updateWatchRelationAtomic(var24, var23, true)) {
                     for (L1PcInstance var25 : var24.b()) {
                        var25.a(new S_ServerMessage(3360, var23.f()));
                        var25.a(new S_PledgeWatch(var24));
                     }

                     for (L1PcInstance var75 : var23.b()) {
                        var75.a(new S_ServerMessage(3360, var24.f()));
                        var75.a(new S_PledgeWatch(var23));
                     }
                  }
               }

               var3.am(0);
         }
      }
   }

   private void a(L1PcInstance var1, L1PcInstance var2, short var3) {
      int var4 = 230;
      if (var1.bB(165)) {
         var1.i_(0);
      }

      var1.a(new S_SkillSound(var1.fr(), 230));
      var1.b(new S_SkillSound(var1.fr(), 230));
      var1.j(var3);
      var1.a(var3);
      var1.a();
      var1.c();
      var1.t();
      var1.a(new S_Resurrection(var1, var2, 0));
      var1.b(new S_Resurrection(var1, var2, 0));
      var1.a(new S_CharVisualUpdate(var1));
      var1.b(new S_CharVisualUpdate(var1));
   }

   private void a(L1PcInstance var1, L1PcInstance var2, int var3) {
      L1Clan var4 = ClanTable.a().a(var1.aF());
      L1Clan var5 = ClanTable.a().a(var2.aF());
      if (var4 != null && var5 != null && var2.x() && var2.fr() == var5.k()) {
         if (var3 < var4.p().size() + var5.p().size()) {
            var2.a(new S_ServerMessage(188, var1.et()));
            return;
         }
         if (!ClanMembersTable.a().mergeClanAtomic(var1.fr(), var4.e(), var4.f(), var5.e(), var5.f())) {
            return;
         }
         for (L1PcInstance var6 : var4.b()) {
            var6.a(new S_ServerMessage(94, var2.et()));
         }
         var1.ai(4);
         var1.a(new S_PacketBox(27, 4, var1.et()));
         String[] var7 = var5.p().toArray(new String[var5.p().size()]);
         for (String var12 : var7) {
            L1PcInstance var8 = L1World.a().a(var12);
            if (var8 == null) {
               try {
                  var8 = CharacterTable.a().a(var12);
               } catch (Exception var11) {
                  a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
                  continue;
               }
            }
            if (var8 == null) {
               continue;
            }
            var8.ah(var4.e());
            var8.c(var4.f());
            var8.ai(2);
            var4.a(var8.et());
            if (L1World.a().a(var8.et()) != null) {
               var8.a(new S_PacketBox(27, 7, var8.et()));
               var8.a(new S_ServerMessage(95, var4.f()));
               var8.a(new S_ClanName(var8, true));
               var8.a(new S_CharEvent(60, var8.fr(), var4.e()));
               var8.a(new S_PacketBox(173, var4.j()));
               var8.a(new S_PledgeWatch(var4));
               for (L1PcInstance var16 : var4.b()) {
                  var16.a(new S_CharEvent(60, var8.fr(), var4.i()));
                  var8.a(new S_CharEvent(60, var16.fr(), var4.i()));
                  if (var4.b().size() >= 3 && !var16.bB(4084)) {
                     var16.j(4084, 0);
                     var16.a(new S_PacketBox(180, 450, 3240, 1));
                  }
               }
            }
         }
         String var13 = String.valueOf(var5.i());
         File var15 = new File("./emblem/" + var13);
         var15.delete();
         ClanTable.a().a(var5.f());
      }
   }

   private static void a(L1PetInstance var0, String var1) {
      if (var0 != null && var1 != null) {
         int var2 = var0.k();
         L1Pet var3 = PetTable.a().b(var2);
         if (var3 == null) {
            throw new NullPointerException();
         }

         L1PcInstance var4 = (L1PcInstance)var0.M();
         if (PetTable.a(var1)) {
            var4.a(new S_ServerMessage(327));
         } else {
            L1Npc var5 = NpcTable.a().a(var0.z());
            if (!var0.et().equalsIgnoreCase(var5.c())) {
               var4.a(new S_ServerMessage(326));
            } else {
               var0.e(var1);
               var3.a(var1);
               PetTable.a().a(var3);
               L1ItemInstance var6 = var4.j().e(var0.k());
               var4.j().b(var6);
               var4.a(new S_ChangeName(var0.fr(), var1));
               var4.b(new S_ChangeName(var0.fr(), var1));
            }
         }
      } else {
         throw new NullPointerException();
      }
   }

   private void a(L1PcInstance var1) {
      L1PcInstance var2 = (L1PcInstance)L1World.a().a(var1.aQ());
      var1.am(0);
      if (var2 != null) {
         if (var1.fr() == var2.ct()) {
            if (var2.fq().h() && !L1CastleWar.a().a((L1Character)var1)) {
               L1Map var3 = var2.fq();
               int var4 = var2.fs();
               int var5 = var2.ft();
               int var6 = var2.cu();
               var4 += b[var6];
               var5 += c[var6];
               var6 = (var6 + 4) % 4;
               boolean var7 = false;

               for (L1Object var8 : L1World.a().b(var2, 1)) {
                  if (var8 instanceof L1Character) {
                     L1Character var10 = (L1Character)var8;
                     if (var10.fs() == var4 && var10.ft() == var5 && var10.fp() == var2.fp()) {
                        var7 = true;
                        break;
                     }
                  }
               }

               if ((var4 != 0 || var5 != 0) && var3.c(var4, var5) && !var7) {
                  L1SpawnEffect.a().a(12938, 5000, var1.fs(), var1.ft(), var1.fp());
                  L1Teleport.a(var1, var4, var5, var2.fp(), var6, true);
               } else {
                  var1.a(new S_ServerMessage(627));
               }
            } else {
               var1.a(new S_ServerMessage(3675));
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_Attr";
   }
}
