package l1r.aj;

import a.g;
import a.s;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.am.MonsterListReader;
import l1r.an.PBMessageALL;
import l1r.an.PBMessageALL2;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL4;
import l1r.an.PBMessageALL5;
import l1r.an.PBMessageALL6;
import l1r.ao.CastleTable;
import l1r.ao.CharacterEquipment;
import l1r.ao.CharacterMobsTable;
import l1r.ao.ClanTable;
import l1r.ao.CraftListTable;
import l1r.ao.ExpTable;
import l1r.ao.ItemTable;
import l1r.ao.LuckyDrawTable;
import l1r.ao.RankingTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Alchemy;
import l1r.aq.L1Chat;
import l1r.aq.L1Clan;
import l1r.aq.L1Craft;
import l1r.aq.L1ExcludingList;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.bc.ShiftLoginTimer;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_ServerVersion;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;
import l1r.bf.L1SkillExecutor;
import l1r.bh.L1QuestNew;
import l1r.bi.CalcInitHpMp;
import l1r.bi.CalcStat;
import l1r.bi.LineageUtil;
import l1r.bi.Random;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_ProtoBuffers extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_ProtoBuffers.class.getName());

   public C_ProtoBuffers(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.d();
      L1PcInstance var4 = var2.f();

      try {
         if (var3 == 115) {
            int var49 = this.d();
            byte[] var72 = this.a(var49);
            PBMessageALL3.L1R_c var95 = PBMessageALL3.L1R_c.a(var72);
            String var116 = new String(var95.r().e(), Config.k);
            String var134 = var116.split("-")[0];
            String var151 = var116.split("-")[1];
            var2.a(new S_ProtoBuffers(116));
            new ShiftLoginTimer(var2, var134, var151).a(3000L);
         } else if (var3 == 117) {
            int var48 = this.d();
            byte[] var71 = this.a(var48);
            var2.d();
            System.out.println("[Shift Server]:切斷原本的連線");
         } else if (var3 == 143) {
            int var47 = this.d();
            byte[] var70 = this.a(var47);
            PBMessageALL.L1R_a var94 = PBMessageALL.L1R_a.a(var70);
            if (var94.p() == 12) {
               if (var4.j().b(40308, 1000)) {
                  L1Teleport.a(var4, 32630, 32776, 4, 5, true);
               } else {
                  var4.a(new S_ServerMessage(189));
               }
            }
         } else if (var3 == 135) {
            int var46 = this.d();
            byte[] var69 = this.a(var46);
            PBMessageALL.L1R_a var93 = PBMessageALL.L1R_a.a(var69);
            RankingTable.a().a(var4, var93.p());
         } else if (var3 == 514) {
            int var45 = this.d();
            byte[] var68 = this.a(var45);
            PBMessageALL5.L1R_i var92 = PBMessageALL5.L1R_i.a(var68);
            int var115 = var92.p();
            int var133 = var92.r();
            String var150 = new String(var92.t().e(), Config.k);
            String var160 = new String(var92.x().e(), Config.k);
            L1Chat.a(var4, var133, var150, var115, var160);
         } else if (var3 == 524) {
            int var44 = this.d();
            byte[] var67 = this.a(var44);
            PBMessageALL.L1R_a var91 = PBMessageALL.L1R_a.a(var67);
            int var114 = var91.p();
            L1QuestNew var132 = var4.dS().get(var114);
            if (var132 != null && !var132.w()) {
               for (int var146 = 0; var146 < var132.f().length; var146++) {
                  ItemTable.a(var4, var132.f()[var146], var132.g()[var146], var132.h()[var146]);
               }

               if (var91.q()) {
                  int var147 = var91.r();
                  ItemTable.a(var4, var132.i()[var147], var132.j()[var147], var132.k()[var147]);
               }

               if (var132.l() > 0) {
                  double var148 = ExpTable.d(var4.ev());
                  var4.x((int)(var132.l() * var148));
               }

               var132.a(true);
               var4.a(new S_ProtoBuffers(525, var114));
               if (var132.r().length > 0 && var132.o()) {
                  for (int var149 = 0; var149 < var132.r().length; var149++) {
                     var4.j().b(var132.r()[var149], var132.t()[var149], var132.s()[var149], 3);
                  }
               }
            }
         } else if (var3 == 527) {
            int var43 = this.d();
            byte[] var66 = this.a(var43);
            PBMessageALL.L1R_a var90 = PBMessageALL.L1R_a.a(var66);
            int var113 = var90.p();
            L1QuestNew var131 = var4.dS().get(var113);
            if (var131 != null && var131.e().length > 0) {
               L1Teleport.a(var4, var131.e()[0], var131.e()[1], var131.e()[2], 5, true);
            }
         } else if (var3 == 543) {
            int var42 = this.d();
            byte[] var65 = this.a(var42);
            PBMessageALL.L1R_c var89 = PBMessageALL.L1R_c.a(var65);
            int var112 = var89.p();
            int var130 = var89.r();
            int var145 = var89.t();
            L1ExcludingList var159 = var4.cd();
            if (var112 == 0) {
               var4.a(new S_PacketBox(17, var159.a(), 0));
               var4.a(new S_PacketBox(17, var159.a(), 1));
            } else if (var112 == 1) {
               for (int var166 = 0; var166 < var145; var166++) {
                  String var175 = new String(var89.a(var166).e(), Config.k);
                  if (var159.b()) {
                     var4.a(new S_ServerMessage(472));
                     break;
                  }

                  if (!var159.c(var175)) {
                     var159.a(var175);
                     var4.a(new S_PacketBox(18, 0, var175));
                     var4.a(new S_PacketBox(18, 1, var175));
                  }
               }
            } else if (var112 == 2) {
               for (int var167 = 0; var167 < var145; var167++) {
                  String var176 = new String(var89.a(var167).e(), Config.k);
                  var159.b(var176);
                  var4.a(new S_PacketBox(19, 0, var176));
                  var4.a(new S_PacketBox(19, 1, var176));
               }
            }
         } else if (var3 == 563) {
            int var41 = this.d();
            byte[] var64 = this.a(var41);
            PBMessageALL.L1R_a var88 = PBMessageALL.L1R_a.a(var64);
            int var111 = var88.p();
            PBMessageALL4.L1R_i var129 = MonsterListReader.a().a(var111);

            for (g var143 : var129.o()) {
               PBMessageALL.L1R_a var165 = PBMessageALL.L1R_a.a(var143);
               int var174 = var165.r();
               int var182 = var165.t();
               if (var174 == 0) {
                  double var189 = ExpTable.d(var4.ev());
                  var4.x((int)(var182 * var189));
               } else if (var174 == 16667) {
                  if (!var4.bB(4092)) {
                     var4.F(5);
                  }

                  var4.j(4092, 1800000);
                  var4.a(new S_ProtoBuffers(4092, 1800, 8, 6841, 0, 1426, 0, 0, 1));
                  var4.a(new S_SkillSound(var4.fr(), 14102));
                  var4.b(new S_SkillSound(var4.fr(), 14102));
               } else if (var174 == 15815) {
                  int[] var188 = new int[]{42, 79, 158, 159, 160, 175, 206, 211, 216, 115, 148};
                  int[] var204 = var188;
                  int var198 = var188.length;

                  for (int var196 = 0; var196 < var198; var196++) {
                     int var193 = var204[var196];
                     L1SkillExecutor var210 = LineageUtil.a(var193);
                     var210.a(var4, 0);
                  }
               } else {
                  int var187 = 0;
                  if (var174 == 7) {
                     var187 = 40308;
                  } else if (var174 == 14092) {
                     var187 = 640514;
                  } else if (var174 == 16764) {
                     var187 = 640819;
                  }

                  ItemTable.a(var4, var187, var182);
               }
            }

            int var144 = var111;
            if (var144 >= 1700) {
               var144 -= 30;
            }

            var4.dR()[var144 - 1] = 1;
            var4.a(new S_ProtoBuffers(564, 0, var111));
            CharacterMobsTable.a().a(var4);
         } else if (var3 == 565) {
            int var40 = this.d();
            byte[] var63 = this.a(var40);
            PBMessageALL.L1R_c var87 = PBMessageALL.L1R_c.a(var63);
            int[] var110 = MonsterListReader.a().b(var87.p());
            if (var110 == null) {
               var4.a(new S_SystemMessage("錯誤的傳送編號:0x" + LineageUtil.a(var87.p(), 4)));
               return;
            }

            if (!var4.fq().i() || var4.bB(230) || var4.eX()) {
               var4.a(new S_ServerMessage(276));
               return;
            }

            if (!var4.j().b(140100, 1)) {
               var4.a(new S_ServerMessage(4692, "$5096"));
               return;
            }

            if (var110[2] >= 100 && var110[2] <= 111) {
               return;
            }

            L1Teleport.a(var4, var110[0], var110[1], var110[2], 5, true);
         } else if (var3 != 569) {
            if (var3 == 801) {
               int var39 = this.d();
               byte[] var62 = this.a(var39);
               PBMessageALL.L1R_a var86 = PBMessageALL.L1R_a.a(var62);
               if (var4.bB(26004)) {
                  var4.a(new S_ProtoBuffers(800, var4.dV()));
                  return;
               }

               var4.j(26004, 2000);
               if (var86.o()) {
                  if (var86.p() == 0) {
                     var4.dT().clear();

                     for (L1ItemInstance var107 : var4.j().d()) {
                        if (var107.D()) {
                           var4.dT().add(var107.fr());
                        }
                     }
                  } else if (var86.p() == 1) {
                     var4.dU().clear();

                     for (L1ItemInstance var108 : var4.j().d()) {
                        if (var108.D()) {
                           var4.dU().add(var108.fr());
                        }
                     }
                  }

                  var4.bu(var86.p());
                  CharacterEquipment.a().a(var4);
               }

               if (var86.q()) {
                  var4.j().j();

                  for (int var128 : var86.r() == 0 ? var4.dT() : var4.dU()) {
                     var4.j().k(var128);
                  }

                  var4.bu(var86.r());
                  var4.a(new S_ProtoBuffers(800, var86.r()));
               }
            } else if (var3 == 811) {
               int var38 = this.d();
               byte[] var61 = this.a(var38);
               PBMessageALL.L1R_a var85 = PBMessageALL.L1R_a.a(var61);
               int var106 = var85.p();
               int var125 = var85.r();
               if (var4.dY() == null || var4.dY()[var106 * 3][3] == 5) {
                  var4.a(new S_ServerMessage(79));
                  return;
               }

               int var141 = 0;
               if (var125 == 1) {
                  var141 = 721306;
                  ItemTable.a(var4, 640941, 1);
               } else if (var125 == 2 && var4.j().b(640938, 1)) {
                  var141 = 7213060;
                  ItemTable.a(var4, 640941, 5);
               } else if (var125 == 3 && var4.j().b(640939, 1)) {
                  var141 = 16229385;
                  ItemTable.a(var4, 640769, 1);
               }

               if (var141 > 0) {
                  double var157 = ExpTable.d(var4.ev());
                  var4.x((int)(var141 * var157));
               }

               var4.dY()[var106 * 3][3] = 5;
               var4.a(new S_ProtoBuffers(814, var106, 5));
            } else if (var3 == 820) {
               var2.a(new S_ServerVersion());
            } else if (var3 != 1002) {
               if (var3 == 54) {
                  int var27 = this.d();
                  byte[] var50 = this.a(var27);
                  byte[] var73 = this.a(Config.aR);
                  if (!var4.aK().j() && !Arrays.equals(var50, var73)) {
                     System.out.println("更新道具清單驗證(" + var4.eu() + ")");
                     var4.a(new S_ProtoBuffers(55, 0));

                     for (L1Craft var117 : CraftListTable.a().b()) {
                        var4.a(new S_ProtoBuffers(55, var117, 1));
                     }

                     var4.a(new S_ProtoBuffers(55, 2));
                     var4.aK().a(true);
                  } else {
                     var4.a(new S_ProtoBuffers(55, 3));
                  }
               } else if (var3 == 56) {
                  int var28 = this.d();
                  byte[] var51 = this.a(var28);
                  int var74 = 0;
                  PBMessageALL.L1R_a var97 = PBMessageALL.L1R_a.a(var51);
                  var74 = var97.p();
                  L1Object var118 = L1World.a().a(var74);
                  if (var118 instanceof L1NpcInstance) {
                     L1NpcInstance var135 = (L1NpcInstance)var118;
                     String[] var11 = var135.F();
                     if (var11.length == 0) {
                        return;
                     }

                     var4.a(new S_ProtoBuffers(57, var11));
                  }
               } else if (var3 == 92) {
                  var4.a(new S_ProtoBuffers(93, 33));
               } else if (var3 == 58) {
                  int var29 = this.d();
                  byte[] var52 = this.a(var29);
                  PBMessageALL5.L1R_a var76 = PBMessageALL5.L1R_a.a(var52);
                  L1Craft var98 = CraftListTable.a().a(var76.r());
                  L1ItemInstance var119 = var98.f();
                  int var136 = var76.t();
                  ArrayList var152 = new ArrayList<>();

                  for (g var12 : var76.u()) {
                     PBMessageALL3.L1R_e var14 = PBMessageALL3.L1R_e.a(var12);
                     int var15 = var14.r();
                     int var16 = var14.v();
                     if (var119 == null || var119.m() != var15) {
                        for (L1ItemInstance var17 : var98.g().values()) {
                           if (var17.m() == var15 && var17.G() == var16) {
                              var152.add(var17);
                              break;
                           }

                           for (L1ItemInstance var19 : var98.h().get(var17.N())) {
                              if (var19.m() == var15 && var19.G() == var16) {
                                 var152.add(var19);
                                 break;
                              }
                           }
                        }
                     }
                  }

                  if (var152.size() != var98.g().size()) {
                     a.log(Level.SEVERE, "Item Craft has MaterialList Error with [" + var4.et() + "] craft id=" + var98.a());
                     return;
                  }

                  for (L1ItemInstance var161 : var152) {
                     if (!var4.j().b(var161.N(), var161.G(), var161.E() * var136, var161.F())) {
                        a.log(Level.SEVERE, "Item Craft has Consume Error with [" + var4.et() + "] craft id=" + var98.a());
                        return;
                     }

                     if (var161.N() == 40308) {
                        int var177 = Random.a(4) + 1;
                        CastleTable.a().a(var177, var161.E() * var136);
                     }
                  }

                  int var162 = var98.l();
                  if (var119 != null) {
                     for (g var169 : var76.u()) {
                        PBMessageALL5.L1R_a var183 = PBMessageALL5.L1R_a.a(var169);
                        if (var183.r() == var119.m()) {
                           if (var4.j().b(var119.N(), var183.t() * var136)) {
                              var162 += var183.t() * var136;
                           }
                           break;
                        }
                     }
                  }

                  if (Random.a(100) < var162) {
                     L1ItemInstance var170 = var98.b();
                     int var179 = var170.N();
                     int var184 = var170.E() * var136;
                     int var190 = var170.G();
                     int var194 = var170.F();
                     if (var98.c() > 0 && Random.a(100) < var98.c()) {
                        var194 = 0;
                        var4.a(new S_SkillSound(var4.fr(), 2047));
                        var4.b(new S_SkillSound(var4.fr(), 2047));
                        if (var98.c() < 10) {
                           L1World.a().a(new S_ServerMessage(3599, "$227 " + var170.a().j(), var190));
                        }
                     }

                     if (var98.l() <= 5) {
                        var4.a(new S_SkillSound(var4.fr(), 2047));
                        var4.b(new S_SkillSound(var4.fr(), 2047));
                        L1World.a().a(new S_ServerMessage(3599, var170.a().j(), var190));
                     }

                     ItemTable.a(var4, var179, var184, var190, var194, true);
                     var4.a(new S_ProtoBuffers(59, var98, var170, 0));
                  } else {
                     L1ItemInstance var171 = var98.e();
                     if (var171 != null) {
                        ItemTable.a(var4, var171.N(), var171.E() * var136);
                     }

                     var4.a(new S_ProtoBuffers(59, var98, var98.b(), 1));
                  }
               } else if (var3 == 317) {
                  int var30 = this.d();
                  byte[] var53 = this.a(var30);
                  PBMessageALL.L1R_a var77 = PBMessageALL.L1R_a.a(var53);
                  int var99 = var77.p();
                  if (var99 == 1) {
                     var4.a(new S_ProtoBuffers(318, CastleTable.a().b()));
                  }
               } else if (var3 == 319) {
                  int var31 = this.d();
                  byte[] var54 = this.a(var31);
                  PBMessageALL.L1R_a var78 = PBMessageALL.L1R_a.a(var54);
                  int var100 = var78.r();
                  var4.a(new S_ProtoBuffers(320, var4.fr(), var100));
                  var4.b(new S_ProtoBuffers(320, var4.fr(), var100));
               } else if (var3 == 332) {
                  if (var4.aF() > 0) {
                     var4.a(new S_ProtoBuffers(333, var4));
                  }
               } else if (var3 == 326) {
                  int var32 = this.d();
                  byte[] var55 = this.a(var32);
                  PBMessageALL4.L1R_g var79 = PBMessageALL4.L1R_g.a(var55);
                  int var101 = var79.p();
                  int var120 = var79.r();
                  byte[] var137 = var79.t().e();
                  L1Clan var153 = ClanTable.a().a(var4.aF());
                  if (var153 != null) {
                     var153.j(var101);
                     var153.k(var120);
                     var153.a(var137);
                  }

                  var4.a(new S_ProtoBuffers(327, var4));
               } else if (var3 == 338) {
                  int var33 = this.d();
                  byte[] var56 = this.a(var33);
                  PBMessageALL.L1R_a var80 = PBMessageALL.L1R_a.a(var56);
                  int var102 = var80.p();
                  int var121 = var80.r();
                  if (var4.q()) {
                     for (L1PcInstance var138 : var4.aL().c()) {
                        var138.a(new S_ProtoBuffers(339, var102, var121));
                     }
                  }
               } else if (var3 == 100) {
                  int var34 = this.d();
                  byte[] var57 = this.a(var34);
                  PBMessageALL2.L1R_c var81 = PBMessageALL2.L1R_c.a(var57);
                  ItemTable.a(var4, 640106, var81.r());

                  for (int var103 : var81.q()) {
                     LuckyDrawTable.a().a(var2.a(), var103);
                  }

                  var4.a(new S_ProtoBuffers(LuckyDrawTable.a().c(var2.a()), 0));
                  var4.a(new S_ServerMessage(3728, var81.r()));
               } else if (var3 == 122) {
                  int var35 = this.d();
                  byte[] var58 = this.a(var35);
                  byte[] var82 = this.a(Config.aS);
                  var4.a(new S_ProtoBuffers(128));
                  if (Arrays.equals(var58, var82)) {
                     var4.a(new S_ProtoBuffers(123, 3));
                  } else {
                     System.out.println("更新魔法娃娃合成清單驗證(" + var4.eu() + ")");
                     var4.a(new S_ProtoBuffers(123, 0));
                     L1Alchemy.a().a(var4);
                     L1Alchemy.a().b(var4);
                     L1Alchemy.a().c(var4);
                     var4.a(new S_ProtoBuffers(123, 2));
                  }
               } else if (var3 == 124) {
                  int var36 = this.d();
                  byte[] var59 = this.a(var36);
                  ArrayList var83 = new ArrayList<>();
                  PBMessageALL6.L1R_g var104 = PBMessageALL6.L1R_g.a(var59);
                  int var123 = var104.p();

                  for (g var139 : var104.q()) {
                     PBMessageALL.L1R_a var163 = PBMessageALL.L1R_a.a(var139);
                     int var172 = var163.p();
                     int var180 = var163.r();
                     int var185 = var163.t();
                     L1ItemInstance var191 = var4.j().e(var185);
                     if (var191 == null) {
                        a.log(Level.SEVERE, "魔法娃娃-合成:itemobjid= " + var185 + " is Null");
                        return;
                     }

                     var83.add(var191);
                  }

                  L1Alchemy.a().a(var4, var123, var83);
               } else if (var3 == 460) {
                  var4.a(new S_ProtoBuffers(461, var4));
               } else if (var3 == 484) {
                  int var37 = this.d();
                  byte[] var60 = this.a(var37);
                  PBMessageALL.L1R_a var84 = PBMessageALL.L1R_a.a(var60);
                  int var105 = var84.p();
                  int var124 = var84.r();
                  int var140 = var84.t();
                  int var156 = var84.x();
                  int var164 = var84.z();
                  int var173 = var84.B();
                  int var181 = var84.D();
                  int var186 = var84.F();
                  int var192 = var84.H();
                  int var195 = var84.L();
                  if (var2.h() == null) {
                     var2.b(new L1PcInstance());
                  }

                  L1PcInstance var197 = var2.h();
                  var197.ad(var124);
                  var197.i(C_CreateChar.a[var124]);
                  var197.o(var164 - var197.bf());
                  var197.q(var186 - var197.bh());
                  var197.p(var192 - var197.bg());
                  var197.t(var181 - var197.bk());
                  var197.s(var173 - var197.bj());
                  var197.r(var195 - var197.bi());
                  if (var164 > 0) {
                     int var199 = CalcStat.a(var197.bf(), var197.ez());
                     int var205 = CalcStat.b(var197.bf(), var197.ez());
                     int var21 = CalcStat.c(var197.bf(), var197.ez());
                     if (var140 != 16 || var156 == 16) {
                        var2.a(new S_ProtoBuffers(var140 * 2, "str", var199, var205, var21, (int)var197.K()));
                     }
                  }

                  if (var173 > 0) {
                     int var200 = CalcStat.g(var197.bj(), var197.eD());
                     int var206 = CalcStat.h(var197.bj(), var197.eD());
                     int var211 = CalcStat.i(var197.bj(), var197.eD());
                     int var22 = CalcStat.c(var197.eD());
                     int var23 = CalcStat.d(var197.eD());
                     var2.a(new S_ProtoBuffers(var140 * 2, "int", var200, var206, var211, var22, var23));
                  }

                  if (var181 > 0) {
                     int var201 = var197.aC().j(var197.eE());
                     int var207 = var197.aC().k(var197.eE());
                     int var212 = CalcStat.m(var197.bk(), var197.eE());
                     int var215 = CalcStat.n(var197.bk(), var197.eE());
                     int var217 = CalcStat.e(var197.eE());
                     int var24 = CalcInitHpMp.c(var197) - CalcInitHpMp.b(var197);
                     var2.a(new S_ProtoBuffers(var140 * 2, "wis", var212, var215, var217, var201, var201 + var207, var24));
                  }

                  if (var186 > 0) {
                     int var202 = CalcStat.a(var197.eB());
                     int var208 = CalcStat.b(var197.eB());
                     int var213 = CalcStat.d(var197.bh(), var197.eB());
                     int var216 = CalcStat.e(var197.bh(), var197.eB());
                     int var218 = CalcStat.f(var197.bh(), var197.eB());
                     var2.a(new S_ProtoBuffers(var140 * 2, "dex", var213, var216, var218, var202, var208));
                  }

                  if (var192 > 0) {
                     int var203 = var197.aC().e() + CalcStat.j(var197.aC().a()[2], var197.eA());
                     int var209 = CalcStat.k(var197.bg(), var197.eA());
                     int var214 = CalcStat.l(var197.bg(), var197.eA());
                     if (var140 != 16 || var156 == 1) {
                        var2.a(new S_ProtoBuffers(var140 * 2, "con", var209, var214, (int)var197.K(), var203, 0));
                     }
                  }

                  if (var195 > 0) {
                     var2.a(new S_ProtoBuffers(var140 * 2, "cha", 0, 0, 0, 0));
                  }
               } else if (var3 != 1002 && var3 == 802) {
                  var4.a(new S_ProtoBuffers(803, var4));
               }
            }
         } else {
            int var5 = this.d();
            byte[] var6 = this.a(var5);
            PBMessageALL.L1R_a var7 = PBMessageALL.L1R_a.a(var6);
            int var8 = var7.p();
            L1ItemInstance var9 = var4.j().e(var8);
            if (var9 == null || var9.f() && !var9.a().aN()) {
               var4.a(new S_ServerMessage(79));
               return;
            }

            if (var9.F() >= 128) {
               var4.a(new S_ServerMessage(2124));
               return;
            }

            var9.f(var9.F() + 128);
            var4.j().j(var9);
         }
      } catch (s var25) {
         a.log(Level.SEVERE, var25.getLocalizedMessage(), var25);
      } catch (UnsupportedEncodingException var26) {
         a.log(Level.SEVERE, var26.getLocalizedMessage(), var26);
      }
   }

   private byte[] a(String var1) {
      String[] var2 = var1.trim().split(" ");
      byte[] var3 = new byte[var2.length];

      for (int var4 = 0; var4 < var2.length; var4++) {
         var3[var4] = this.b(var2[var4])[0];
      }

      return var3;
   }

   private byte[] b(String var1) {
      char[] var2 = var1.toCharArray();
      int var3 = var2.length / 2;
      byte[] var4 = new byte[var3];

      for (int var5 = 0; var5 < var3; var5++) {
         int var6 = Character.digit(var2[var5 * 2], 16);
         int var7 = Character.digit(var2[var5 * 2 + 1], 16);
         int var8 = var6 << 4 | var7;
         if (var8 > 127) {
            var8 -= 256;
         }

         var4[var5] = (byte)var8;
      }

      return var4;
   }

   @Override
   public String a() {
      return "C_ProtoBuffers";
   }
}
