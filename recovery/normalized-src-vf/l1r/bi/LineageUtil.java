package l1r.bi;

import a.g;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.al.L1AccountBanKick;
import l1r.al.L1Action;
import l1r.al.L1AddSkill;
import l1r.al.L1Adena;
import l1r.al.L1AllBuff;
import l1r.al.L1BanIp;
import l1r.al.L1Buff;
import l1r.al.L1CastGfx;
import l1r.al.L1ChangeWeather;
import l1r.al.L1Chat;
import l1r.al.L1ChatNG;
import l1r.al.L1CommandExecutor;
import l1r.al.L1CommandHelp;
import l1r.al.L1CreateItem;
import l1r.al.L1CreateItemSet;
import l1r.al.L1DeleteGroundItem;
import l1r.al.L1Describe;
import l1r.al.L1Echo;
import l1r.al.L1Favorite;
import l1r.al.L1FindInvis;
import l1r.al.L1GM;
import l1r.al.L1GMRoom;
import l1r.al.L1GfxId;
import l1r.al.L1GfxInvList;
import l1r.al.L1GfxNpc;
import l1r.al.L1HomeTown;
import l1r.al.L1HpBar;
import l1r.al.L1InsertSpawn;
import l1r.al.L1InvGfxId;
import l1r.al.L1Invisible;
import l1r.al.L1Kick;
import l1r.al.L1Kill;
import l1r.al.L1Level;
import l1r.al.L1LevelPresent;
import l1r.al.L1Loc;
import l1r.al.L1MapMove;
import l1r.al.L1Move;
import l1r.al.L1PartyRecall;
import l1r.al.L1Patrol;
import l1r.al.L1Poly;
import l1r.al.L1PowerKick;
import l1r.al.L1Present;
import l1r.al.L1Recall;
import l1r.al.L1ReloadTrap;
import l1r.al.L1ResetTrap;
import l1r.al.L1Ress;
import l1r.al.L1SKick;
import l1r.al.L1ShowTrap;
import l1r.al.L1Shutdown;
import l1r.al.L1SpawnCmd;
import l1r.al.L1Speed;
import l1r.al.L1Status;
import l1r.al.L1Summon;
import l1r.al.L1Tile;
import l1r.al.L1ToPC;
import l1r.al.L1ToSpawn;
import l1r.al.L1Visible;
import l1r.al.L1Who;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL5;
import l1r.ao.CastleTable;
import l1r.ao.CharacterTable;
import l1r.ao.ClanMembersTable;
import l1r.ao.ClanTable;
import l1r.ao.CraftListTable;
import l1r.ao.HouseTable;
import l1r.ap.L1AttackerInstance;
import l1r.ap.L1AuctionBoardInstance;
import l1r.ap.L1BoardInstance;
import l1r.ap.L1CrownInstance;
import l1r.ap.L1DotaInstance;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1FieldObjectInstance;
import l1r.ap.L1FishInstance;
import l1r.ap.L1FurnitureInstance;
import l1r.ap.L1GuardInstance;
import l1r.ap.L1GuardianInstance;
import l1r.ap.L1HousekeeperInstance;
import l1r.ap.L1KeeperInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1QuestInstance;
import l1r.ap.L1ScarecrowInstance;
import l1r.ap.L1SignboardInstance;
import l1r.ap.L1TowerInstance;
import l1r.aq.L1Alchemy;
import l1r.aq.L1Clan;
import l1r.aq.L1Craft;
import l1r.aq.L1World;
import l1r.ba.HouseTimer;
import l1r.bd.L1DamageTrap;
import l1r.bd.L1HealingTrap;
import l1r.bd.L1MonsterTrap;
import l1r.bd.L1PoisonTrap;
import l1r.bd.L1SkillTrap;
import l1r.bd.L1TeleportTrap;
import l1r.bd.L1Trap__obf_i;
import l1r.bd.TrapStorage;
import l1r.be.S_Html;
import l1r.be.S_ServerMessage;
import l1r.bf.L1SkillExecutor;
import l1r.bf.S_001;
import l1r.bf.S_002;
import l1r.bf.S_003;
import l1r.bf.S_004;
import l1r.bf.S_005;
import l1r.bf.S_006;
import l1r.bf.S_007;
import l1r.bf.S_008;
import l1r.bf.S_009;
import l1r.bf.S_010;
import l1r.bf.S_011;
import l1r.bf.S_012;
import l1r.bf.S_013;
import l1r.bf.S_014;
import l1r.bf.S_015;
import l1r.bf.S_016;
import l1r.bf.S_017;
import l1r.bf.S_018;
import l1r.bf.S_019;
import l1r.bf.S_020;
import l1r.bf.S_021;
import l1r.bf.S_022;
import l1r.bf.S_023;
import l1r.bf.S_025;
import l1r.bf.S_026;
import l1r.bf.S_027;
import l1r.bf.S_028;
import l1r.bf.S_029;
import l1r.bf.S_030;
import l1r.bf.S_031;
import l1r.bf.S_032;
import l1r.bf.S_033;
import l1r.bf.S_034;
import l1r.bf.S_035;
import l1r.bf.S_036;
import l1r.bf.S_037;
import l1r.bf.S_038;
import l1r.bf.S_039;
import l1r.bf.S_040;
import l1r.bf.S_041;
import l1r.bf.S_042;
import l1r.bf.S_043;
import l1r.bf.S_044;
import l1r.bf.S_045;
import l1r.bf.S_046;
import l1r.bf.S_047;
import l1r.bf.S_048;
import l1r.bf.S_049;
import l1r.bf.S_050;
import l1r.bf.S_051;
import l1r.bf.S_052;
import l1r.bf.S_053;
import l1r.bf.S_054;
import l1r.bf.S_055;
import l1r.bf.S_056;
import l1r.bf.S_057;
import l1r.bf.S_058;
import l1r.bf.S_059;
import l1r.bf.S_060;
import l1r.bf.S_061;
import l1r.bf.S_062;
import l1r.bf.S_063;
import l1r.bf.S_064;
import l1r.bf.S_065;
import l1r.bf.S_066;
import l1r.bf.S_067;
import l1r.bf.S_068;
import l1r.bf.S_069;
import l1r.bf.S_070;
import l1r.bf.S_071;
import l1r.bf.S_072;
import l1r.bf.S_073;
import l1r.bf.S_074;
import l1r.bf.S_075;
import l1r.bf.S_076;
import l1r.bf.S_077;
import l1r.bf.S_078;
import l1r.bf.S_079;
import l1r.bf.S_080;
import l1r.bf.S_087;
import l1r.bf.S_088;
import l1r.bf.S_089;
import l1r.bf.S_090;
import l1r.bf.S_091;
import l1r.bf.S_092;
import l1r.bf.S_097;
import l1r.bf.S_098;
import l1r.bf.S_099;
import l1r.bf.S_100;
import l1r.bf.S_101;
import l1r.bf.S_102;
import l1r.bf.S_103;
import l1r.bf.S_104;
import l1r.bf.S_105;
import l1r.bf.S_106;
import l1r.bf.S_107;
import l1r.bf.S_108;
import l1r.bf.S_109;
import l1r.bf.S_110;
import l1r.bf.S_111;
import l1r.bf.S_112;
import l1r.bf.S_113;
import l1r.bf.S_114;
import l1r.bf.S_115;
import l1r.bf.S_116;
import l1r.bf.S_117;
import l1r.bf.S_118;
import l1r.bf.S_119;
import l1r.bf.S_120;
import l1r.bf.S_121;
import l1r.bf.S_122;
import l1r.bf.S_129;
import l1r.bf.S_130;
import l1r.bf.S_131;
import l1r.bf.S_132;
import l1r.bf.S_133;
import l1r.bf.S_134;
import l1r.bf.S_135;
import l1r.bf.S_137;
import l1r.bf.S_138;
import l1r.bf.S_145;
import l1r.bf.S_146;
import l1r.bf.S_147;
import l1r.bf.S_148;
import l1r.bf.S_149;
import l1r.bf.S_150;
import l1r.bf.S_151;
import l1r.bf.S_152;
import l1r.bf.S_153;
import l1r.bf.S_154;
import l1r.bf.S_155;
import l1r.bf.S_156;
import l1r.bf.S_157;
import l1r.bf.S_158;
import l1r.bf.S_159;
import l1r.bf.S_160;
import l1r.bf.S_161;
import l1r.bf.S_162;
import l1r.bf.S_163;
import l1r.bf.S_164;
import l1r.bf.S_165;
import l1r.bf.S_166;
import l1r.bf.S_167;
import l1r.bf.S_168;
import l1r.bf.S_169;
import l1r.bf.S_170;
import l1r.bf.S_171;
import l1r.bf.S_172;
import l1r.bf.S_173;
import l1r.bf.S_174;
import l1r.bf.S_175;
import l1r.bf.S_176;
import l1r.bf.S_181;
import l1r.bf.S_182;
import l1r.bf.S_183;
import l1r.bf.S_184;
import l1r.bf.S_185;
import l1r.bf.S_186;
import l1r.bf.S_187;
import l1r.bf.S_188;
import l1r.bf.S_189;
import l1r.bf.S_190;
import l1r.bf.S_191;
import l1r.bf.S_192;
import l1r.bf.S_193;
import l1r.bf.S_194;
import l1r.bf.S_195;
import l1r.bf.S_196;
import l1r.bf.S_201;
import l1r.bf.S_202;
import l1r.bf.S_203;
import l1r.bf.S_204;
import l1r.bf.S_205;
import l1r.bf.S_206;
import l1r.bf.S_207;
import l1r.bf.S_208;
import l1r.bf.S_209;
import l1r.bf.S_210;
import l1r.bf.S_211;
import l1r.bf.S_212;
import l1r.bf.S_213;
import l1r.bf.S_214;
import l1r.bf.S_215;
import l1r.bf.S_216;
import l1r.bf.S_217;
import l1r.bf.S_218;
import l1r.bf.S_219;
import l1r.bf.S_220;
import l1r.bf.S_222;
import l1r.bf.S_225;
import l1r.bf.S_226;
import l1r.bf.S_228;
import l1r.bf.S_229;
import l1r.bf.S_230;
import l1r.bf.S_231;
import l1r.bf.S_233;
import l1r.bh.L1Castle;
import l1r.bh.L1House;
import l1r.bh.L1Npc;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class LineageUtil {
   private static final Logger a = Logger.getLogger(LineageUtil.class.getName());
   private static HashMap<String, String> b = new HashMap<>();

   public static void a(String var0, String var1) {
      try {
         File var2 = new File(var0);
         BufferedWriter var3 = new BufferedWriter(new FileWriter(var2, true));
         var3.write(var1);
         var3.newLine();
         var3.close();
      } catch (IOException var4) {
         a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
      }
   }

   public static void a(String var0, byte[] var1, boolean var2) {
      try {
         FileOutputStream var3 = new FileOutputStream(var0, var2);
         var3.write(var1);
         var3.close();
      } catch (IOException var4) {
         a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
      }
   }

   public static g a(String var0) {
      g var1 = g.d;

      try {
         var1 = g.a(var0.getBytes(Config.k));
      } catch (UnsupportedEncodingException var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }

      return var1;
   }

   public static byte[] b(String var0) {
      byte[] var1 = new byte[0];

      try {
         var1 = var0.getBytes(Config.k);
      } catch (UnsupportedEncodingException var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }

      return var1;
   }

   public static String a(File var0) {
      String var1 = var0.getName();
      int var2 = var1.lastIndexOf(46);
      return var2 != -1 ? var1.substring(var2 + 1, var1.length()) : "";
   }

   public static String b(File var0) {
      String var1 = var0.getName();
      int var2 = var1.lastIndexOf(46);
      return var2 != -1 ? var1.substring(0, var2) : "";
   }

   public static long a() {
      return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024L / 1024L;
   }

   public static void a(Closeable... var0) {
      Closeable[] var4 = var0;
      int var3 = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         Closeable var1 = var4[var2];

         try {
            if (var1 != null) {
               var1.close();
            }
         } catch (IOException var6) {
            a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         }
      }
   }

   public static void b() {
      try {
         String var0 = "./data/craftinfo.dat";
         a("./data/craftinfo.dat", new byte[]{8, 2}, false);

         for (L1Craft var1 : CraftListTable.a().b()) {
            PBMessageALL3.L1R_a.L1R_a var3 = PBMessageALL3.L1R_a.aa();
            var3.e(var1.i());
            byte[] var4 = var3.M().g();
            a("./data/craftinfo.dat", var4, true);
         }

         byte[] var13 = Files.readAllBytes(Paths.get("./data/craftinfo.dat"));
         MessageDigest var14 = MessageDigest.getInstance("SHA-1");
         var14.update(var13);
         System.out.println("[自動生成道具清單驗證碼]");
         String var15 = "0a 14 ";
         byte[] var7;
         int var6 = (var7 = var14.digest()).length;

         for (int var5 = 0; var5 < var6; var5++) {
            byte var16 = var7[var5];
            var15 = var15 + a(var16 & 255, 2) + " ";
         }

         Config.aR = var15.trim();
         System.out.println(Config.aR);
         String var17 = "./data/alchemyInfo.dat";
         a("./data/alchemyInfo.dat", new byte[]{8, 2}, false);

         for (int var18 = 1; var18 <= 4; var18++) {
            PBMessageALL5.L1R_g.L1R_a var22 = PBMessageALL5.L1R_g.aa();
            var22.e(L1Alchemy.a().c(var18));
            var7 = var22.M().g();
            a("./data/alchemyInfo.dat", var7, true);
         }

         for (int var19 = 1; var19 <= 5; var19++) {
            PBMessageALL5.L1R_g.L1R_a var23 = PBMessageALL5.L1R_g.aa();
            var23.f(L1Alchemy.a().b(var19));
            var7 = var23.M().g();
            a("./data/alchemyInfo.dat", var7, true);
         }

         for (int var20 = 1; var20 <= 4; var20++) {
            PBMessageALL5.L1R_g.L1R_a var24 = PBMessageALL5.L1R_g.aa();
            var24.g(L1Alchemy.a().a(var20));
            var7 = var24.M().g();
            a("./data/alchemyInfo.dat", var7, true);
         }

         byte[] var21 = Files.readAllBytes(Paths.get("./data/alchemyInfo.dat"));
         MessageDigest var25 = MessageDigest.getInstance("sha-1");
         var25.update(var21);
         System.out.println("[自動生成魔法娃娃合成清單驗證碼]");
         String var29 = "0a 14 ";
         byte[] var11;
         int var10 = (var11 = var25.digest()).length;

         for (int var9 = 0; var9 < var10; var9++) {
            byte var8 = var11[var9];
            var29 = var29 + a(var8 & 255, 2) + " ";
         }

         Config.aS = var29.trim();
         System.out.println(Config.aS);
      } catch (Exception var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      }
   }

   public static void c() {
      long var0 = System.currentTimeMillis();
      System.out.print("cleaning Accounts...");
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM accounts WHERE password = '1'");
         var4 = var3.executeQuery();

         while (var4.next()) {
            String var5 = var4.getString("login");
            e(var5);
            var3 = var2.prepareStatement("DELETE FROM character_elf_warehouse WHERE account_name=?");
            var3.setString(1, var5);
            var3.execute();
            var3 = var2.prepareStatement("DELETE FROM character_luckydraw WHERE acc_name=?");
            var3.setString(1, var5);
            var3.execute();
            var3 = var2.prepareStatement("DELETE FROM character_mobs WHERE login=?");
            var3.setString(1, var5);
            var3.execute();
            var3 = var2.prepareStatement("DELETE FROM character_mobs_week WHERE login=?");
            var3.setString(1, var5);
            var3.execute();
            var3 = var2.prepareStatement("DELETE FROM character_shop WHERE acc_name=?");
            var3.setString(1, var5);
            var3.execute();
            var3 = var2.prepareStatement("DELETE FROM character_warehouse WHERE account_name=?");
            var3.setString(1, var5);
            var3.execute();
            var3 = var2.prepareStatement("DELETE FROM accounts WHERE login=?");
            var3.setString(1, var5);
            var3.execute();
         }
      } catch (SQLException var6) {
         a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
      }

      SQLUtil.a(var4, var3, var2);
      System.out.println("OK! " + (System.currentTimeMillis() - var0) + " ms");
   }

   private static void e(String var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM characters WHERE account_name = ?");
         var2.setString(1, var0);
         var3 = var2.executeQuery();

         while (var3.next()) {
            String var4 = var3.getString("char_name");
            L1PcInstance var5 = CharacterTable.a().a(var4);
            if (var5 != null) {
               L1Clan var6 = ClanTable.a().a(var5.aF());
               if (var6 != null) {
                  var6.b(var4);
                  if (var5.x() && var6.k() == var5.fr()) {
                     a(var5, var6);
                  }
               }
            }

            CharacterTable.a().a(var0, var4);
         }
      } catch (Exception var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private static void a(L1PcInstance var0, L1Clan var1) throws Exception {
      int var2 = var1.m();
      int var3 = var1.n();
      if (var2 != 0 || var3 != 0) {
         L1Castle var4 = CastleTable.a().a(var2);
         if (var4 != null) {
            var4.c(0);
         }

         L1House var5 = HouseTable.a().a(var3);
         if (var5 != null) {
            HouseTimer.a().a(var5);
         }
      }

      for (int var6 = 0; var6 < var1.p().size(); var6++) {
         L1PcInstance var8 = CharacterTable.a().a(var1.p().get(var6));
         var8.ah(0);
         var8.c("");
         var8.ai(0);
         var8.f("");
         var8.I();
      }

      String var7 = String.valueOf(var1.i());
      File var9 = new File("./emblem/" + var7);
      var9.delete();
      ClanTable.a().b(var1.f());
      ClanMembersTable.a().b(var1.e());
   }

   public static L1PcInstance a(L1PcInstance var0, boolean var1) {
      int var2 = var0.fs();
      int var3 = var0.ft();
      int var4 = var0.fb();
      List var5 = L1World.a().c(var0, 1);
      if (var5.isEmpty()) {
         var0.a(new S_ServerMessage(93));
         return null;
      }

      for (L1PcInstance var6 : var5) {
         int var8 = var6.fs();
         int var9 = var6.ft();
         int var10 = var6.fb();
         int[][] var11 = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
         int[] var12 = new int[]{4, 5, 6, 7, 0, 1, 2, 3};
         if (var8 == var2 + var11[var4][0] && var9 == var3 + var11[var4][1]) {
            if (var10 == var12[var4]) {
               return var6;
            }

            if (var1) {
               var0.a(new S_Html(0, "noseeb", var6.et()));
            } else {
               var0.a(new S_ServerMessage(91, var6.et()));
            }

            return null;
         }
      }

      var0.a(new S_ServerMessage(93));
      return null;
   }

   public static L1NpcInstance a(L1Npc var0) {
      String var1 = var0.d();
      if (var1.equalsIgnoreCase("L1AuctionBoard")) {
         return new L1AuctionBoardInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Board")) {
         return new L1BoardInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Crown")) {
         return new L1CrownInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Dota")) {
         return new L1DotaInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Effect")) {
         return new L1EffectInstance(var0);
      } else if (var1.equalsIgnoreCase("L1FieldObject")) {
         return new L1FieldObjectInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Fish")) {
         return new L1FishInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Furniture")) {
         return new L1FurnitureInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Guard")) {
         return new L1GuardInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Guardian")) {
         return new L1GuardianInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Housekeeper")) {
         return new L1HousekeeperInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Keeper")) {
         return new L1KeeperInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Attacker")) {
         return new L1AttackerInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Monster")) {
         return new L1MonsterInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Npc")) {
         return new L1NpcInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Quest")) {
         return new L1QuestInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Scarecrow")) {
         return new L1ScarecrowInstance(var0);
      } else if (var1.equalsIgnoreCase("L1Signboard")) {
         return new L1SignboardInstance(var0);
      } else {
         return var1.equalsIgnoreCase("L1Tower") ? new L1TowerInstance(var0) : null;
      }
   }

   public static L1SkillExecutor a(int var0) {
      if (var0 == 1) {
         return new S_001();
      } else if (var0 == 2) {
         return new S_002();
      } else if (var0 == 3) {
         return new S_003();
      } else if (var0 == 4) {
         return new S_004();
      } else if (var0 == 5) {
         return new S_005();
      } else if (var0 == 6) {
         return new S_006();
      } else if (var0 == 7) {
         return new S_007();
      } else if (var0 == 8) {
         return new S_008();
      } else if (var0 == 9) {
         return new S_009();
      } else if (var0 == 10) {
         return new S_010();
      } else if (var0 == 11) {
         return new S_011();
      } else if (var0 == 12) {
         return new S_012();
      } else if (var0 == 13) {
         return new S_013();
      } else if (var0 == 14) {
         return new S_014();
      } else if (var0 == 15) {
         return new S_015();
      } else if (var0 == 16) {
         return new S_016();
      } else if (var0 == 17) {
         return new S_017();
      } else if (var0 == 18) {
         return new S_018();
      } else if (var0 == 19) {
         return new S_019();
      } else if (var0 == 20) {
         return new S_020();
      } else if (var0 == 21) {
         return new S_021();
      } else if (var0 == 22) {
         return new S_022();
      } else if (var0 == 23) {
         return new S_023();
      } else if (var0 == 25) {
         return new S_025();
      } else if (var0 == 26) {
         return new S_026();
      } else if (var0 == 27) {
         return new S_027();
      } else if (var0 == 28) {
         return new S_028();
      } else if (var0 == 29) {
         return new S_029();
      } else if (var0 == 30) {
         return new S_030();
      } else if (var0 == 31) {
         return new S_031();
      } else if (var0 == 32) {
         return new S_032();
      } else if (var0 == 33) {
         return new S_033();
      } else if (var0 == 34) {
         return new S_034();
      } else if (var0 == 35) {
         return new S_035();
      } else if (var0 == 36) {
         return new S_036();
      } else if (var0 == 37) {
         return new S_037();
      } else if (var0 == 38) {
         return new S_038();
      } else if (var0 == 39) {
         return new S_039();
      } else if (var0 == 40) {
         return new S_040();
      } else if (var0 == 41) {
         return new S_041();
      } else if (var0 == 42) {
         return new S_042();
      } else if (var0 == 43) {
         return new S_043();
      } else if (var0 == 44) {
         return new S_044();
      } else if (var0 == 45) {
         return new S_045();
      } else if (var0 == 46) {
         return new S_046();
      } else if (var0 == 47) {
         return new S_047();
      } else if (var0 == 48) {
         return new S_048();
      } else if (var0 == 49) {
         return new S_049();
      } else if (var0 == 50) {
         return new S_050();
      } else if (var0 == 51) {
         return new S_051();
      } else if (var0 == 52) {
         return new S_052();
      } else if (var0 == 53) {
         return new S_053();
      } else if (var0 == 54) {
         return new S_054();
      } else if (var0 == 55) {
         return new S_055();
      } else if (var0 == 56) {
         return new S_056();
      } else if (var0 == 57) {
         return new S_057();
      } else if (var0 == 58) {
         return new S_058();
      } else if (var0 == 59) {
         return new S_059();
      } else if (var0 == 60) {
         return new S_060();
      } else if (var0 == 61) {
         return new S_061();
      } else if (var0 == 62) {
         return new S_062();
      } else if (var0 == 63) {
         return new S_063();
      } else if (var0 == 64) {
         return new S_064();
      } else if (var0 == 65) {
         return new S_065();
      } else if (var0 == 66) {
         return new S_066();
      } else if (var0 == 67) {
         return new S_067();
      } else if (var0 == 68) {
         return new S_068();
      } else if (var0 == 69) {
         return new S_069();
      } else if (var0 == 70) {
         return new S_070();
      } else if (var0 == 71) {
         return new S_071();
      } else if (var0 == 72) {
         return new S_072();
      } else if (var0 == 73) {
         return new S_073();
      } else if (var0 == 74) {
         return new S_074();
      } else if (var0 == 75) {
         return new S_075();
      } else if (var0 == 76) {
         return new S_076();
      } else if (var0 == 77) {
         return new S_077();
      } else if (var0 == 78) {
         return new S_078();
      } else if (var0 == 79) {
         return new S_079();
      } else if (var0 == 80) {
         return new S_080();
      } else if (var0 == 87) {
         return new S_087();
      } else if (var0 == 88) {
         return new S_088();
      } else if (var0 == 89) {
         return new S_089();
      } else if (var0 == 90) {
         return new S_090();
      } else if (var0 == 91) {
         return new S_091();
      } else if (var0 == 92) {
         return new S_092();
      } else if (var0 == 97) {
         return new S_097();
      } else if (var0 == 98) {
         return new S_098();
      } else if (var0 == 99) {
         return new S_099();
      } else if (var0 == 100) {
         return new S_100();
      } else if (var0 == 101) {
         return new S_101();
      } else if (var0 == 102) {
         return new S_102();
      } else if (var0 == 103) {
         return new S_103();
      } else if (var0 == 104) {
         return new S_104();
      } else if (var0 == 105) {
         return new S_105();
      } else if (var0 == 106) {
         return new S_106();
      } else if (var0 == 107) {
         return new S_107();
      } else if (var0 == 108) {
         return new S_108();
      } else if (var0 == 109) {
         return new S_109();
      } else if (var0 == 110) {
         return new S_110();
      } else if (var0 == 111) {
         return new S_111();
      } else if (var0 == 112) {
         return new S_112();
      } else if (var0 == 113) {
         return new S_113();
      } else if (var0 == 114) {
         return new S_114();
      } else if (var0 == 115) {
         return new S_115();
      } else if (var0 == 116) {
         return new S_116();
      } else if (var0 == 117) {
         return new S_117();
      } else if (var0 == 118) {
         return new S_118();
      } else if (var0 == 119) {
         return new S_119();
      } else if (var0 == 120) {
         return new S_120();
      } else if (var0 == 121) {
         return new S_121();
      } else if (var0 == 122) {
         return new S_122();
      } else if (var0 == 129) {
         return new S_129();
      } else if (var0 == 130) {
         return new S_130();
      } else if (var0 == 131) {
         return new S_131();
      } else if (var0 == 132) {
         return new S_132();
      } else if (var0 == 133) {
         return new S_133();
      } else if (var0 == 134) {
         return new S_134();
      } else if (var0 == 135) {
         return new S_135();
      } else if (var0 == 137) {
         return new S_137();
      } else if (var0 == 138) {
         return new S_138();
      } else if (var0 == 145) {
         return new S_145();
      } else if (var0 == 146) {
         return new S_146();
      } else if (var0 == 147) {
         return new S_147();
      } else if (var0 == 148) {
         return new S_148();
      } else if (var0 == 149) {
         return new S_149();
      } else if (var0 == 150) {
         return new S_150();
      } else if (var0 == 151) {
         return new S_151();
      } else if (var0 == 152) {
         return new S_152();
      } else if (var0 == 153) {
         return new S_153();
      } else if (var0 == 154) {
         return new S_154();
      } else if (var0 == 155) {
         return new S_155();
      } else if (var0 == 156) {
         return new S_156();
      } else if (var0 == 157) {
         return new S_157();
      } else if (var0 == 158) {
         return new S_158();
      } else if (var0 == 159) {
         return new S_159();
      } else if (var0 == 160) {
         return new S_160();
      } else if (var0 == 161) {
         return new S_161();
      } else if (var0 == 162) {
         return new S_162();
      } else if (var0 == 163) {
         return new S_163();
      } else if (var0 == 164) {
         return new S_164();
      } else if (var0 == 165) {
         return new S_165();
      } else if (var0 == 166) {
         return new S_166();
      } else if (var0 == 167) {
         return new S_167();
      } else if (var0 == 168) {
         return new S_168();
      } else if (var0 == 169) {
         return new S_169();
      } else if (var0 == 170) {
         return new S_170();
      } else if (var0 == 171) {
         return new S_171();
      } else if (var0 == 172) {
         return new S_172();
      } else if (var0 == 173) {
         return new S_173();
      } else if (var0 == 174) {
         return new S_174();
      } else if (var0 == 175) {
         return new S_175();
      } else if (var0 == 176) {
         return new S_176();
      } else if (var0 == 181) {
         return new S_181();
      } else if (var0 == 182) {
         return new S_182();
      } else if (var0 == 183) {
         return new S_183();
      } else if (var0 == 184) {
         return new S_184();
      } else if (var0 == 185) {
         return new S_185();
      } else if (var0 == 186) {
         return new S_186();
      } else if (var0 == 187) {
         return new S_187();
      } else if (var0 == 188) {
         return new S_188();
      } else if (var0 == 189) {
         return new S_189();
      } else if (var0 == 190) {
         return new S_190();
      } else if (var0 == 191) {
         return new S_191();
      } else if (var0 == 192) {
         return new S_192();
      } else if (var0 == 193) {
         return new S_193();
      } else if (var0 == 194) {
         return new S_194();
      } else if (var0 == 195) {
         return new S_195();
      } else if (var0 == 196) {
         return new S_196();
      } else if (var0 == 201) {
         return new S_201();
      } else if (var0 == 202) {
         return new S_202();
      } else if (var0 == 203) {
         return new S_203();
      } else if (var0 == 204) {
         return new S_204();
      } else if (var0 == 205) {
         return new S_205();
      } else if (var0 == 206) {
         return new S_206();
      } else if (var0 == 207) {
         return new S_207();
      } else if (var0 == 208) {
         return new S_208();
      } else if (var0 == 209) {
         return new S_209();
      } else if (var0 == 210) {
         return new S_210();
      } else if (var0 == 211) {
         return new S_211();
      } else if (var0 == 212) {
         return new S_212();
      } else if (var0 == 213) {
         return new S_213();
      } else if (var0 == 214) {
         return new S_214();
      } else if (var0 == 215) {
         return new S_215();
      } else if (var0 == 216) {
         return new S_216();
      } else if (var0 == 217) {
         return new S_217();
      } else if (var0 == 218) {
         return new S_218();
      } else if (var0 == 219) {
         return new S_219();
      } else if (var0 == 220) {
         return new S_220();
      } else if (var0 == 222) {
         return new S_222();
      } else if (var0 == 225) {
         return new S_225();
      } else if (var0 == 226) {
         return new S_226();
      } else if (var0 == 228) {
         return new S_228();
      } else if (var0 == 229) {
         return new S_229();
      } else if (var0 == 230) {
         return new S_230();
      } else if (var0 == 231) {
         return new S_231();
      } else {
         return var0 == 233 ? new S_233() : null;
      }
   }

   public static L1CommandExecutor c(String var0) {
      if (var0.equalsIgnoreCase("L1Echo")) {
         return L1Echo.a();
      } else if (var0.equalsIgnoreCase("L1Status")) {
         return L1Status.a();
      } else if (var0.equalsIgnoreCase("L1Summon")) {
         return L1Summon.a();
      } else if (var0.equalsIgnoreCase("L1DeleteGroundItem")) {
         return L1DeleteGroundItem.a();
      } else if (var0.equalsIgnoreCase("L1AddSkill")) {
         return L1AddSkill.a();
      } else if (var0.equalsIgnoreCase("L1Level")) {
         return L1Level.a();
      } else if (var0.equalsIgnoreCase("L1Loc")) {
         return L1Loc.a();
      } else if (var0.equalsIgnoreCase("L1Describe")) {
         return L1Describe.a();
      } else if (var0.equalsIgnoreCase("L1Who")) {
         return L1Who.a();
      } else if (var0.equalsIgnoreCase("L1AllBuff")) {
         return L1AllBuff.a();
      } else if (var0.equalsIgnoreCase("L1Speed")) {
         return L1Speed.a();
      } else if (var0.equalsIgnoreCase("L1Adena")) {
         return L1Adena.a();
      } else if (var0.equalsIgnoreCase("L1HpBar")) {
         return L1HpBar.a();
      } else if (var0.equalsIgnoreCase("L1ResetTrap")) {
         return L1ResetTrap.a();
      } else if (var0.equalsIgnoreCase("L1ReloadTrap")) {
         return L1ReloadTrap.a();
      } else if (var0.equalsIgnoreCase("L1ShowTrap")) {
         return L1ShowTrap.a();
      } else if (var0.equalsIgnoreCase("L1CastGfx")) {
         return L1CastGfx.a();
      } else if (var0.equalsIgnoreCase("L1GfxId")) {
         return L1GfxId.a();
      } else if (var0.equalsIgnoreCase("L1InvGfxId")) {
         return L1InvGfxId.a();
      } else if (var0.equalsIgnoreCase("L1HomeTown")) {
         return L1HomeTown.a();
      } else if (var0.equalsIgnoreCase("L1GM")) {
         return L1GM.a();
      } else if (var0.equalsIgnoreCase("L1Present")) {
         return L1Present.a();
      } else if (var0.equalsIgnoreCase("L1LevelPresent")) {
         return L1LevelPresent.a();
      } else if (var0.equalsIgnoreCase("L1Shutdown")) {
         return L1Shutdown.a();
      } else if (var0.equalsIgnoreCase("L1CreateItem")) {
         return L1CreateItem.a();
      } else if (var0.equalsIgnoreCase("L1CreateItemSet")) {
         return L1CreateItemSet.a();
      } else if (var0.equalsIgnoreCase("L1Buff")) {
         return L1Buff.a();
      } else if (var0.equalsIgnoreCase("L1Patrol")) {
         return L1Patrol.a();
      } else if (var0.equalsIgnoreCase("L1BanIp")) {
         return L1BanIp.a();
      } else if (var0.equalsIgnoreCase("L1Chat")) {
         return L1Chat.a();
      } else if (var0.equalsIgnoreCase("L1ChatNG")) {
         return L1ChatNG.a();
      } else if (var0.equalsIgnoreCase("L1SKick")) {
         return L1SKick.a();
      } else if (var0.equalsIgnoreCase("L1Kick")) {
         return L1Kick.a();
      } else if (var0.equalsIgnoreCase("L1PowerKick")) {
         return L1PowerKick.a();
      } else if (var0.equalsIgnoreCase("L1AccountBanKick")) {
         return L1AccountBanKick.a();
      } else if (var0.equalsIgnoreCase("L1Poly")) {
         return L1Poly.a();
      } else if (var0.equalsIgnoreCase("L1Ress")) {
         return L1Ress.a();
      } else if (var0.equalsIgnoreCase("L1Kill")) {
         return L1Kill.a();
      } else if (var0.equalsIgnoreCase("L1GMRoom")) {
         return L1GMRoom.a();
      } else if (var0.equalsIgnoreCase("L1ToPC")) {
         return L1ToPC.a();
      } else if (var0.equalsIgnoreCase("L1Move")) {
         return L1Move.a();
      } else if (var0.equalsIgnoreCase("L1ChangeWeather")) {
         return L1ChangeWeather.a();
      } else if (var0.equalsIgnoreCase("L1ToSpawn")) {
         return L1ToSpawn.a();
      } else if (var0.equalsIgnoreCase("L1Favorite")) {
         return L1Favorite.a();
      } else if (var0.equalsIgnoreCase("L1Recall")) {
         return L1Recall.a();
      } else if (var0.equalsIgnoreCase("L1Visible")) {
         return L1Visible.a();
      } else if (var0.equalsIgnoreCase("L1PartyRecall")) {
         return L1PartyRecall.a();
      } else if (var0.equalsIgnoreCase("L1Invisible")) {
         return L1Invisible.a();
      } else if (var0.equalsIgnoreCase("L1SpawnCmd")) {
         return L1SpawnCmd.a();
      } else if (var0.equalsIgnoreCase("L1InsertSpawn")) {
         return L1InsertSpawn.a();
      } else if (var0.equalsIgnoreCase("L1CommandHelp")) {
         return L1CommandHelp.a();
      } else if (var0.equalsIgnoreCase("L1Action")) {
         return L1Action.a();
      } else if (var0.equalsIgnoreCase("L1Tile")) {
         return L1Tile.a();
      } else if (var0.equalsIgnoreCase("L1FindInvis")) {
         return L1FindInvis.a();
      } else if (var0.equalsIgnoreCase("L1MapMove")) {
         return L1MapMove.a();
      } else if (var0.equalsIgnoreCase("L1GfxNpc")) {
         return L1GfxNpc.a();
      } else {
         return var0.equalsIgnoreCase("L1GfxInvList") ? L1GfxInvList.a() : null;
      }
   }

   public static L1Trap__obf_i a(String var0, TrapStorage var1) {
      if (var0.equalsIgnoreCase("L1DamageTrap")) {
         return new L1DamageTrap(var1);
      } else if (var0.equalsIgnoreCase("L1HealingTrap")) {
         return new L1HealingTrap(var1);
      } else if (var0.equalsIgnoreCase("L1MonsterTrap")) {
         return new L1MonsterTrap(var1);
      } else if (var0.equalsIgnoreCase("L1PoisonTrap")) {
         return new L1PoisonTrap(var1);
      } else if (var0.equalsIgnoreCase("L1SkillTrap")) {
         return new L1SkillTrap(var1);
      } else {
         return var0.equalsIgnoreCase("L1TeleportTrap") ? new L1TeleportTrap(var1) : null;
      }
   }

   public static String d(String var0) throws Exception {
      if (b.isEmpty()) {
         String var1 = null;
         LineNumberReader var2 = new LineNumberReader(new FileReader(new File("./data/desc-c.tbl")));

         while ((var1 = var2.readLine()) != null) {
            if (!var1.startsWith("#") && var1.trim().length() != 0) {
               b.put(String.valueOf(var2.getLineNumber() - 2), var1);
            }
         }

         var2.close();
      }

      String var9 = "";
      if (!var0.contains("$")) {
         return var9;
      }

      if (var0.endsWith("R")) {
         var0 = var0.replace("R", "");
      }

      var0 = var0 + "◎";
      if (var0.contains(" (")) {
         var0 = var0.replace(" (", "◎(");
      }

      if (var0.contains(" ")) {
         var0 = var0.replace(" ", "◎ ");
      }

      String[] var5;
      int var4 = (var5 = var0.split(" ")).length;

      for (int var3 = 0; var3 < var4; var3++) {
         String var10 = var5[var3];
         String var6 = var10.substring(var10.indexOf("$") + 1, var10.indexOf("◎"));
         if (b.containsKey(var6.trim())) {
            var9 = var9 + b.get(var6) + " ";
         } else {
            var9 = var9 + var6 + " ";
         }
      }

      return var9.trim();
   }

   public static String a(byte[] var0) {
      StringBuffer var1 = new StringBuffer();
      int var2 = 0;

      for (int var3 = 0; var3 < var0.length; var3++) {
         if (var2 % 16 == 0) {
            var1.append(a(var3, 4) + ": ");
         }

         var1.append(a(var0[var3] & 255, 2) + " ");
         if (++var2 == 16) {
            var1.append("   ");
            int var4 = var3 - 15;

            for (int var5 = 0; var5 < 16; var5++) {
               byte var6 = var0[var4++];
               if (var6 > 31 && var6 < 128) {
                  var1.append((char)var6);
               } else {
                  var1.append('.');
               }
            }

            var1.append("\r\n");
            var2 = 0;
         }
      }

      int var7 = var0.length % 16;
      if (var7 > 0) {
         for (int var8 = 0; var8 < 17 - var7; var8++) {
            var1.append("   ");
         }

         int var9 = var0.length - var7;

         for (int var10 = 0; var10 < var7; var10++) {
            byte var11 = var0[var9++];
            if (var11 > 31 && var11 < 128) {
               var1.append((char)var11);
            } else {
               var1.append('.');
            }
         }

         var1.append("\r\n");
      }

      return var1.toString();
   }

   public static String a(int var0, int var1) {
      String var2 = Integer.toHexString(var0);

      for (int var3 = var2.length(); var3 < var1; var3++) {
         var2 = "0" + var2;
      }

      return var2;
   }
}
