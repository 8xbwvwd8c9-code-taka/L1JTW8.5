package l1r.aj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.AccountTable;
import l1r.ao.CastleTable;
import l1r.ao.CharacterConfigTable;
import l1r.ao.CharacterEquipment;
import l1r.ao.CharacterGiftTable;
import l1r.ao.CharacterMobsTable;
import l1r.ao.CharacterMobsWeekTable;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.ao.GetBackRestartTable;
import l1r.ao.MobQuestWeekTable;
import l1r.ao.QuestNewTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Clan;
import l1r.aq.L1Cooking;
import l1r.aq.L1Getback;
import l1r.aq.L1ItemQuestBuff;
import l1r.aq.L1Master;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.as.L1CastleWar;
import l1r.az.L1SilencePoison;
import l1r.ba.HellTimer;
import l1r.be.S_Ability;
import l1r.be.S_ActiveSpells;
import l1r.be.S_AddSkill;
import l1r.be.S_Bookmarks;
import l1r.be.S_CastleMaster;
import l1r.be.S_CharEvent;
import l1r.be.S_ClanName;
import l1r.be.S_InitAbility;
import l1r.be.S_InvList;
import l1r.be.S_Karma;
import l1r.be.S_Liquor;
import l1r.be.S_LoginGame;
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
import l1r.be.S_SkillBrave;
import l1r.be.S_SkillHaste;
import l1r.be.S_SummonPack;
import l1r.be.S_War;
import l1r.be.S_Weather;
import l1r.bf.L1SkillExecutor;
import l1r.bf.S_014;
import l1r.bf.S_032;
import l1r.bf.S_047;
import l1r.bf.S_055;
import l1r.bf.S_056;
import l1r.bf.S_064;
import l1r.bf.S_071;
import l1r.bf.S_078;
import l1r.bf.S_089;
import l1r.bf.S_104;
import l1r.bf.S_111;
import l1r.bf.S_134;
import l1r.bf.S_153;
import l1r.bf.S_158;
import l1r.bf.S_171;
import l1r.bf.S_173;
import l1r.bf.S_174;
import l1r.bf.S_176;
import l1r.bf.S_181;
import l1r.bf.S_183;
import l1r.bf.S_188;
import l1r.bf.S_191;
import l1r.bf.S_193;
import l1r.bf.S_202;
import l1r.bf.S_206;
import l1r.bf.S_211;
import l1r.bf.S_216;
import l1r.bf.S_217;
import l1r.bf.S_218;
import l1r.bh.L1Account;
import l1r.bh.L1Castle;
import l1r.bh.L1Skills;
import l1r.bi.LineageUtil;
import l1r.bi.SQLUtil;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class C_LoginToServer extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_LoginToServer.class.getName());
   private static final String b = "[C] C_LoginToServer";

   public C_LoginToServer(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      String var3 = var2.a();
      String var4 = this.g();
      if (var2.f() != null) {
         a.log(Level.SEVERE, "同一個角色重複登入，強制切斷 " + var2.g() + ") 的連結");
         var2.c();
      } else {
         L1PcInstance var5 = L1PcInstance.b(var4);
         if (var5 != null) {
            L1Account var6 = var2.e();
            if (var6 == null) {
               a.log(Level.SEVERE, "無效的角色名稱: char=" + var4 + " account=" + var3 + " host=" + var2.g());
               var2.c();
            } else if (var6.o()) {
               a.log(Level.SEVERE, "同一個帳號雙重角色登入，強制切斷 " + var2.g() + ") 的連結");
               var2.c();
            } else if (!var3.equals(var5.bc())) {
               a.log(Level.SEVERE, "無效的角色名稱: char=" + var4 + " account=" + var3 + " host=" + var2.g());
               var2.c();
            } else {
               System.out.println("角色登入到伺服器中: char=" + var4 + " account=" + var3 + " host=" + var2.g());
               int var7 = var5.ea();
               int var8 = var5.eb();
               var5.aC(1);
               CharacterTable.a().c(var5);
               L1World.a().a(var5);
               var5.a(var2);
               var2.a(var5);
               var5.a(new S_LoginGame());
               AccountTable.a().b(var6, true);
               if (var5.fp() >= 4000 && var5.fp() <= 4050) {
                  var5.cG(32767);
                  var5.cH(32831);
                  var5.cE(2400);
               } else if (var5.fp() >= 2600 && var5.fp() <= 2797) {
                  var5.cG(33703);
                  var5.cH(32502);
                  var5.cE(4);
               }

               GetBackRestartTable.a().a(var5);
               if (Config.Z) {
                  int[] var9 = L1Getback.a(var5);
                  var5.cG(var9[0]);
                  var5.cH(var9[1]);
                  var5.cE(var9[2]);
               }

               int var14 = L1CastleLocation.a(var5);
               if (var14 > 0 && L1CastleWar.a().a(var14)) {
                  L1Clan var10 = ClanTable.a().a(var5.aF());
                  if (var10 != null) {
                     if (var10.m() != var14) {
                        int[] var11 = new int[3];
                        var11 = L1CastleLocation.e(var14);
                        var5.cG(var11[0]);
                        var5.cH(var11[1]);
                        var5.cE(var11[2]);
                     }
                  } else {
                     int[] var20 = new int[3];
                     var20 = L1CastleLocation.e(var14);
                     var5.cG(var20[0]);
                     var5.cH(var20[1]);
                     var5.cE(var20[2]);
                  }
               }

               L1World.a().c(var5);
               CharacterConfigTable.a().a(var5);
               this.e(var5);
               a(var5);
               var5.a(new S_RuneSlot(68));
               var5.a(new S_RuneSlot(67, var5.cP()));
               var5.ad();
               var5.a(new S_Bookmarks(var5));
               var5.a(new S_OwnCharStatus(var5));
               var5.a(new S_MapID(var5.fp(), var5.fq().g()));
               var5.a(new S_OwnCharPack(var5));
               var5.a(new S_ProtoBuffers(485, var5));
               L1Castle[] var13;
               int var12 = (var13 = CastleTable.a().b()).length;

               for (int var22 = 0; var22 < var12; var22++) {
                  L1Castle var15 = var13[var22];
                  var5.a(new S_CastleMaster(var15.a(), var15.h() > 0 ? var15.h() : 0));
               }

               var5.i();
               var5.a(new S_InitAbility(var5));
               var5.a(new S_ProtoBuffers(487, 1));
               var5.a(new S_ProtoBuffers(487, 2));
               var5.a(new S_ProtoBuffers(487, 3));
               var5.a(new S_ProtoBuffers(490, var5));
               var5.a(new S_ProtoBuffers(489, var5));
               var5.a(new S_Weather(L1World.a().j()));
               b(var5);
               d(var5);
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
                  int var16 = var5.ev() - 50 - var5.bA();
                  var5.a(new S_Message_YN(479, "" + var16));
               }

               c(var5);
               L1CastleWar.a().a(var5);
               if (var5.aF() != 0) {
                  L1Clan var17 = ClanTable.a().a(var5.aF());
                  if (var17 != null) {
                     var5.a(new S_ClanName(var5));
                     var5.a(new S_PacketBox(173, var17.j()));
                     var5.a(new S_PledgeWatch(var17));

                     for (L1PcInstance var23 : var17.b()) {
                        if (var23.fr() != var5.fr()) {
                           var23.a(new S_ServerMessage(843, var5.et()));
                        }

                        if (var17.b().size() >= 3 && !var23.bB(4084)) {
                           var23.j(4084, 0);
                           var23.a(new S_PacketBox(180, 450, 3240, 1));
                        }
                     }

                     L1War var24 = L1World.a().c(var17.f());
                     if (var24 != null) {
                        for (L1Clan var26 : var24.c(var17.f())) {
                           if (!var26.f().contains("安安妳好再見_")) {
                              var5.a(new S_War(8, var17.f(), var26.f()));
                           } else {
                              var5.a(new S_ServerMessage(235, var26.f()));
                           }

                           if (var24.c() == 1) {
                              var5.a(new S_ProtoBuffers(76, var5));
                           }
                        }
                     }
                  } else {
                     var5.ah(0);
                     var5.c("");
                     var5.ai(0);
                     var5.I();
                  }
               }

               if (var5.bD() != 0) {
                  L1PcInstance var18 = (L1PcInstance)L1World.a().a(var5.bD());
                  if (var18 != null && var18.bD() != 0 && var5.bD() == var18.fr() && var18.bD() == var5.fr()) {
                     var5.a(new S_ServerMessage(548));
                     var18.a(new S_ServerMessage(549));
                  }
               }

               if (var7 > var5.ea()) {
                  var5.a(var7);
               }

               if (var8 > var5.eb()) {
                  var5.i_(var8);
               }

               var5.a();
               var5.c();
               var5.e();
               var5.G();
               var5.f();
               var5.I();
               if (var5.bI() > 0) {
                  HellTimer.a().a(var5, false);
               }

               var5.ac();
               var5.ae();
               if (!var5.bB(25009) && !var5.bB(25010) && !var5.bB(25011)) {
                  var5.j(25009, 180000);
                  var5.a(new S_PacketBox(150, 1, 180));
               }

               L1Master.a().b(var5);
               var5.a(new S_CharEvent(37, var2.e().p()));
               var5.a(new S_ProtoBuffers(559, var5));
               var5.a(new S_ProtoBuffers(560, var5));
               CharacterMobsWeekTable.a().a(var5);
               if (var5.dY() == null) {
                  var5.a(MobQuestWeekTable.a().b());
                  CharacterMobsWeekTable.a().b(var5);
               }

               var5.a(new S_ProtoBuffers(810, var5.dY()));
               var5.a(new S_ProtoBuffers(126));
               var5.a(new S_PacketBox(189));
               var5.a(new S_ProtoBuffers(103, 4126));
               CharacterGiftTable.a().a(var5);
               CharacterEquipment.a().b(var5);
               QuestNewTable.a().b(var5);
               var5.a(new S_Ability(3, 1));
               if (var5.l()) {
                  var5.j(26003, 0);
               }

               var5.a(new S_ProtoBuffers(141));
            }
         }
      }
   }

   public static void a(L1PcInstance var0) {
      var0.j().a();
      var0.au().a();
      var0.av().a();
      var0.aw().a();
      if (var0.v() == null || var0.v().a().aO() == 0) {
         var0.a(new S_PacketBox(160, 1, 0));
      }

      var0.a(new S_InvList(var0.j().d()));
   }

   public static void b(L1PcInstance var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM character_skills WHERE char_obj_id=?");
         var2.setInt(1, var0.fr());
         var3 = var2.executeQuery();
         CopyOnWriteArrayList var4 = new CopyOnWriteArrayList<>();
         ArrayList var5 = new ArrayList<>();

         while (var3.next()) {
            int var6 = var3.getInt("skill_id");
            L1Skills var7 = SkillsTable.a().a(var6);
            var0.f(var6);
            if (var6 < 600) {
               var4.add(var6);
            } else {
               var5.add(var6 - 600);
               if (var6 != 609) {
                  var0.j(var6, 0);
               }
            }
         }

         if (!var4.isEmpty()) {
            var0.a(new S_AddSkill(var0, var4));
         }

         if (!var5.isEmpty()) {
            var0.a(new S_ProtoBuffers(401, var5));
         }
      } catch (SQLException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public static void c(L1PcInstance var0) {
      for (L1SummonInstance var1 : L1World.a().f()) {
         if (var1.M().fr() == var0.fr()) {
            var1.e(var0);
            var0.e(var1);

            for (L1PcInstance var3 : L1World.a().f(var1)) {
               var3.a(new S_SummonPack(var1, var3));
            }
         }
      }
   }

   public static void d(L1PcInstance var0) {
      if (var0.fp() == 10500) {
         if (!var0.bB(5018)) {
            var0.ck(10);
            var0.cl(10);
            var0.cm(10);
            var0.cn(10);
            var0.F(5);
            var0.cp(5);
         }

         var0.j(5018, 3600000);
         var0.a(new S_ProtoBuffers(5018, 3600, 8, 4470, 0, 4452, 0, 0, 3));
      }

      if (var0.aK() != null && var0.aK().e().a()) {
         int var1 = Math.min(var0.aK().e().h(), 5);
         var0.a(new S_CharEvent(72, var1));
         int[] var2 = new int[]{0, 10, 20, 30, 40, 50};
         int[] var3 = new int[]{0, 0, 1, 3, 5, 7};
         int[] var4 = new int[]{0, 0, 0, 1, 2, 3};
         var0.bH(var2[var1]);
         var0.bJ(var2[var1]);
         var0.ck(var3[var1]);
         var0.cm(var3[var1]);
         var0.cl(var3[var1]);
         var0.cn(var3[var1]);
         var0.cp(var4[var1]);
      }

      int var21 = var0.bg();
      if (var21 >= 25 && var21 <= 34) {
         var0.bH(50);
      } else if (var21 >= 35 && var21 <= 44) {
         var0.bH(150);
      } else if (var21 >= 45) {
         var0.bH(300);
      }

      int var22 = var0.bk();
      if (var22 >= 25 && var22 <= 34) {
         var0.bJ(50);
      } else if (var22 >= 35 && var22 <= 44) {
         var0.bJ(150);
      } else if (var22 >= 45) {
         var0.bJ(300);
      }

      int var23 = var0.aK().e().r();
      if (var23 > 0) {
         int var24 = var0.aK().e().s();
         int[] var5 = new int[]{4181, 4182, 4183, 4183, 4183, 4183, 4183, 4183};
         if (!var0.bB(4071)) {
            var0.bL(-var23);
         }

         var0.j(4071, 0);
         var0.a(new S_ProtoBuffers(4071, var24, 8, 6100, 0, var5[var23 - 1], var5[var23 - 1], 0, 3));
      }

      Connection var25 = null;
      PreparedStatement var26 = null;
      ResultSet var6 = null;

      try {
         HashMap var7 = new HashMap<>();
         var25 = DatabaseFactory.a().b();
         var26 = var25.prepareStatement("SELECT * FROM character_buff WHERE char_obj_id=?");
         var26.setInt(1, var0.fr());
         var6 = var26.executeQuery();

         while (var6.next()) {
            int var8 = var6.getInt("skill_id");
            int var9 = var6.getInt("remaining_time");
            Timestamp var10 = var6.getTimestamp("limit_time");
            if (var10 != null) {
               Timestamp var11 = new Timestamp(System.currentTimeMillis());
               if (var10.before(var11)) {
                  var9 = 10;
               } else {
                  var9 = (int)((var10.getTime() - var11.getTime()) / 1000L);
               }
            }

            switch (var8) {
               case 14:
                  new S_014().a(var0, var9);
                  break;
               case 32:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_032().a(var0, var9);
                  break;
               case 47:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_047().a(var0, var9);
                  break;
               case 55:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_055().a(var0, var9);
                  break;
               case 56:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_056().a(var0, var9);
                  break;
               case 64:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_064().a(var0, var9);
                  break;
               case 67:
                  int var36 = var6.getInt("poly_id");
                  L1PolyMorph.a(var0, var36, var9, 0);
                  break;
               case 71:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_071().a(var0, var9);
                  break;
               case 78:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_078().a(var0, var9);
                  break;
               case 89:
                  var7.put(var8, (var9 / 8 + 1) / 2);
                  new S_089().a(var0, var9);
                  break;
               case 104:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_104().a(var0, var9);
                  break;
               case 111:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_111().a(var0, var9);
                  break;
               case 134:
                  new S_134().a(var0, var9);
                  break;
               case 153:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_153().a(var0, var9);
                  break;
               case 158:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_158().a(var0, var9);
                  break;
               case 171:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_171().a(var0, var9);
                  break;
               case 173:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_173().a(var0, var9);
                  break;
               case 174:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_174().a(var0, var9);
                  break;
               case 176:
                  var7.put(var8, (var9 / 8 + 1) / 2);
                  new S_176().a(var0, var9);
                  break;
               case 181:
                  var7.put(var8, (var9 / 8 + 1) / 2);
                  new S_181().a(var0, var9);
                  break;
               case 183:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_183().a(var0, var9);
                  break;
               case 188:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_188().a(var0, var9);
                  break;
               case 191:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_191().a(var0, var9);
                  break;
               case 193:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_193().a(var0, var9);
                  break;
               case 202:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_202().a(var0, var9);
                  break;
               case 206:
                  var7.put(var8, (var9 / 8 + 1) / 2);
                  new S_206().a(var0, var9);
                  break;
               case 211:
                  var7.put(var8, (var9 / 2 + 1) / 2);
                  new S_211().a(var0, var9);
                  break;
               case 216:
                  var7.put(var8, (var9 / 8 + 1) / 2);
                  new S_216().a(var0, var9);
                  break;
               case 217:
                  var7.put(var8, (var9 / 8 + 1) / 2);
                  new S_217().a(var0, var9);
                  break;
               case 218:
                  new S_218().a(var0, var9);
                  break;
               case 1000:
                  var0.a(new S_SkillBrave(var0.fr(), 1, var9));
                  var0.b(new S_SkillBrave(var0.fr(), 1, 0));
                  var0.cv(1);
                  var0.j(var8, var9 * 1000);
                  break;
               case 1001:
                  var0.a(new S_SkillHaste(var0.fr(), 1, var9));
                  var0.b(new S_SkillHaste(var0.fr(), 1, 0));
                  var0.cu(1);
                  var0.j(var8, var9 * 1000);
                  break;
               case 1002:
                  var0.a(new S_PacketBox(34, var9));
                  var0.j(var8, var9 * 1000);
                  break;
               case 1005:
                  var0.a(new S_PacketBox(36, var9));
                  var0.j(var8, var9 * 1000);
                  break;
               case 1006:
                  L1PolyMorph.a(var0, 13450, var9, 0);
                  if (!var0.bB(var8)) {
                     var0.bH(100);
                     var0.bJ(100);
                     var0.cm(10);
                     var0.ck(5);
                     var0.cn(10);
                     var0.cl(5);
                     var0.cp(5);
                     var0.bN(1);
                     var0.bR(1);
                     var0.bV(1);
                     var0.a(new S_OwnCharStatus(var0));
                  }

                  var0.j(1006, var9 * 1000);
                  break;
               case 1007:
                  L1SilencePoison.b(var0, var9);
                  break;
               case 1016:
                  var0.a(new S_SkillBrave(var0.fr(), 3, var9));
                  var0.b(new S_SkillBrave(var0.fr(), 3, 0));
                  var0.cv(3);
                  var0.j(var8, var9 * 1000);
                  break;
               case 1017:
                  var0.a(new S_SkillBrave(var0.fr(), 4, var9));
                  var0.b(new S_SkillBrave(var0.fr(), 4, 0));
                  var0.cv(4);
                  var0.j(var8, var9 * 1000);
                  break;
               case 1026:
                  var0.a(new S_SkillBrave(var0.fr(), 5, var9));
                  var0.b(new S_SkillBrave(var0.fr(), 5, 0));
                  var0.cv(5);
                  var0.j(var8, var9 * 1000);
                  break;
               case 1027:
                  var0.a(new S_Liquor(var0.fr(), 8));
                  var0.b(new S_Liquor(var0.fr(), 8));
                  var0.a(new S_PacketBox(60, var9));
                  var0.j(var8, var9 * 1000);
                  break;
               case 1031:
                  L1PolyMorph.a(var0, 14491, var9, 0);
                  if (!var0.bB(var8)) {
                     var0.bH(120);
                     var0.bJ(100);
                     var0.cm(10);
                     var0.ck(7);
                     var0.cn(10);
                     var0.cl(7);
                     var0.cp(5);
                     var0.bN(1);
                     var0.bR(1);
                     var0.bV(1);
                     var0.a(new S_OwnCharStatus(var0));
                  }

                  var0.j(1031, var9 * 1000);
                  break;
               case 1037:
                  L1PolyMorph.a(var0, 12854, var9, 0);
                  if (!var0.bB(var8)) {
                     var0.bH(120);
                     var0.bJ(100);
                     var0.F(15);
                     var0.co(30);
                     var0.cm(10);
                     var0.cn(10);
                     var0.U(3);
                     var0.bN(3);
                     var0.bR(3);
                     var0.bV(3);
                     var0.a(new S_OwnCharStatus(var0));
                  }

                  var0.j(1037, var9 * 1000);

                  for (L1ItemInstance var37 : var0.j().d()) {
                     if (var37.a().S() && var37.D()) {
                        var0.j().a(var37, false);
                     }
                  }
                  break;
               case 1038:
                  var0.a(new S_ProtoBuffers(1038, var9, 0, 6546, 0, 3823, 1971, 1972, 1));
                  if (!var0.bB(var8)) {
                     var0.bH(100);
                     var0.bJ(100);
                     var0.ck(5);
                     var0.cm(10);
                     var0.cl(5);
                     var0.cn(10);
                     var0.cp(5);
                     var0.bL(-10);
                     var0.co(10);
                     var0.a(new S_OwnCharStatus(var0));
                     var0.a(new S_SPMR(var0));
                  }

                  var0.a(new S_Liquor(var0.fr(), 8));
                  var0.b(new S_Liquor(var0.fr(), 8));
                  var0.j(1038, var9 * 1000);
                  break;
               case 4001:
               case 4002:
               case 4003:
               case 4004:
               case 4005:
               case 4007:
               case 4070:
                  var9 = (var9 / 8 + 1) / 2;
                  var7.put(var8, var9);
                  var0.j(var8, (var9 * 2 - 1) * 8 * 1000);
                  break;
               case 4006:
               case 4008:
               case 4009:
               case 4010:
               case 4086:
               case 4087:
               case 4088:
               case 4089:
               case 4090:
               case 4091:
                  var9 = (var9 / 8 + 1) / 2;
                  var7.put(var8, var9);
                  L1ItemQuestBuff.b(var0, var8, (var9 * 2 - 1) * 8);
                  break;
               case 4011:
               case 4012:
               case 4077:
                  if (var9 != 0) {
                     L1ItemQuestBuff.a(var0, var8, var9, var10);
                  }
                  break;
               case 4049:
               case 4050:
               case 4051:
               case 4052:
               case 4053:
               case 4054:
               case 4055:
                  var9 = (var9 / 16 + 1) / 2;
                  var7.put(var8, var9);
                  L1ItemQuestBuff.b(var0, var8, (var9 * 2 - 1) * 16);
                  break;
               case 4056:
               case 4057:
               case 4079:
                  var9 = (var9 / 16 + 1) / 2;
                  var7.put(var8, var9);
                  L1ItemQuestBuff.a(var0, var8, (var9 * 2 - 1) * 16, var10);
                  break;
               case 4076:
                  var9 = (var9 / 8 + 1) / 2;
                  var0.a(new S_PacketBox(86, 173, 1, var9));
                  var0.j(var8, (var9 * 2 - 1) * 8 * 1000);
                  break;
               case 4078:
                  var0.j(var8, var9 * 1000);
                  var0.a(new S_PacketBox(86, 62, 1, var9));
                  break;
               case 4080:
                  if (!var0.bB(var8)) {
                     var0.bH(25);
                     var0.bJ(20);
                  }

                  var0.a(new S_OwnCharStatus(var0));
                  var0.j(4080, var9 * 1000);
                  var0.a(new S_ProtoBuffers(4080, var9, 0, 4910, 0, 4415, 0, 0, 1));
                  break;
               case 4092:
                  if (!var0.bB(var8)) {
                     var0.F(5);
                  }

                  var0.j(4092, var9 * 1000);
                  var0.a(new S_ProtoBuffers(4092, var9, 8, 6841, 0, 1426, 0, 0, 1));
                  break;
               case 25009:
               case 25010:
               case 25011:
                  int var12 = 180;
                  if (var8 == 25010) {
                     var12 = 900;
                  } else if (var8 == 25011) {
                     var12 = 3600;
                  }

                  var0.j(var8, var12 * 1000);
                  var0.a(new S_PacketBox(150, var8 - 25008, var12));
                  break;
               case 25012:
                  var0.j(var8, var9 * 1000);
                  break;
               default:
                  if (var8 >= 4013 && var8 <= 4048) {
                     var9 = (var9 / 16 + 1) / 2;
                     var7.put(var8, var9);
                     L1ItemQuestBuff.a(var0, var8, (var9 * 2 - 1) * 16);
                  } else if (var8 >= 1 && var8 <= 609) {
                     L1SkillExecutor var13 = LineageUtil.a(var8);
                     var13.a(var0, var9);
                  } else if (var8 >= 3000 && var8 <= 3056) {
                     L1Cooking.a(var0, var8, var9);
                  } else {
                     System.out.println("login buff " + var8 + " has some error");
                  }
            }
         }

         if (!var7.isEmpty()) {
            var0.a(new S_ActiveSpells(var7));
         }

         if (var0.bB(4056)) {
            var0.a(new S_PacketBox(86, 76, 0, 45));
         } else if (var0.bB(4057)) {
            var0.a(new S_PacketBox(86, 76, 0, 60));
         } else if (var0.bB(4079)) {
            var0.a(new S_PacketBox(86, 76, 0, 74));
         }
      } catch (SQLException var18) {
         a.log(Level.SEVERE, var18.getLocalizedMessage(), var18);
      } catch (Exception var19) {
         a.log(Level.SEVERE, var19.getLocalizedMessage(), var19);
      } finally {
         SQLUtil.a(var6, var26, var25);
      }

      if (!var0.bB(25012)) {
         var0.j(25012, 1800000);
      }

      if (var0.ev() >= 49) {
         Timestamp var27 = var0.cB();
         if (var27 == null) {
            var27 = new Timestamp(System.currentTimeMillis());
         }

         int var28 = (int)((System.currentTimeMillis() - var27.getTime()) / 900000L);
         int var35 = var0.cC() + var28 * 7700;
         var0.K(var35);
      }
   }

   private void e(L1PcInstance var1) {
      String[] var2 = new String[]{"79 c0 00 00 00 00 00 00 00 00", "79 b8 00 00"};
      String[] var6 = var2;
      int var5 = var2.length;

      for (int var4 = 0; var4 < var5; var4++) {
         String var3 = var6[var4];
         String[] var7 = var3.trim().split(" ");
         byte[] var8 = new byte[var7.length];

         for (int var9 = 0; var9 < var7.length; var9++) {
            var8[var9] = this.a(var7[var9])[0];
         }

         var1.a(new S_PacketBox(var8));
      }
   }

   private byte[] a(String var1) {
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
      return "[C] C_LoginToServer";
   }
}
