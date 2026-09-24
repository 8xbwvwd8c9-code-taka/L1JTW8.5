package l1r.l1j.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ConfigTable;

public final class Config {
   private static final Logger aV = Logger.getLogger(Config.class.getName());
   public static int a = 102;
   public static boolean b = false;
   public static boolean c = false;
   public static boolean d = false;
   public static boolean e = false;
   public static String f;
   public static int g;
   public static String h;
   public static String i;
   public static String j;
   public static String k;
   public static String l;
   public static short m;
   public static boolean n;
   public static int o;
   public static short p;
   public static int q;
   public static int r;
   public static int s;
   public static int t;
   public static int u;
   public static int v;
   public static boolean w;
   public static boolean x;
   public static boolean y;
   public static short z;
   public static String A;
   public static double B;
   public static double C;
   public static double D;
   public static double E;
   public static double F;
   public static int G;
   public static int H;
   public static int I;
   public static double J;
   public static double K;
   public static double L;
   public static double M;
   public static short N;
   public static short O;
   public static byte P;
   public static int Q;
   public static boolean R;
   public static boolean S;
   public static boolean T;
   public static int U;
   public static boolean V;
   public static int W;
   public static int X;
   public static boolean Y;
   public static boolean Z;
   public static int aa;
   public static int ab;
   public static boolean ac;
   public static int ad;
   public static int ae;
   public static boolean af;
   public static boolean ag;
   public static int ah;
   public static int ai;
   public static int aj;
   public static int ak;
   public static boolean al;
   public static int am;
   public static int an;
   public static int ao;
   public static int ap;
   public static int aq;
   public static int ar;
   public static boolean as;
   public static int at;
   public static int au;
   public static int av;
   public static int aw;
   public static int ax;
   public static int ay;
   public static int az;
   public static int aA;
   public static int aB;
   public static int aC;
   public static int aD;
   public static int aE;
   public static int aF;
   public static int aG;
   public static int aH;
   public static int aI;
   public static int aJ;
   public static int aK;
   public static int aL;
   public static int aM;
   public static boolean aN;
   public static boolean aO;
   public static int aP;
   public static int aQ;
   public static String aR;
   public static String aS;
   public static boolean aT;
   public static String aU;
   public static int EliteMonsterSwitch;
   public static String EliteMonsterTimeSchedule;
   public static int MonsterDeathSpawnSwitch;
   public static int MonsterDeathSpawnGlobalMobChance;
   public static int MonsterDeathSpawnGlobalBossChance;
   public static int MapDesignatedDropSwitch;
   public static int GlobalMapDropSwitch;

   public static void a() {
      try {
         ConfigTable var0 = ConfigTable.a();
         String[] var1 = new String[]{"UTF8", "EUCKR", "UTF8", "BIG5", "SJIS", "GBK"};
         m = Short.parseShort(var0.a("ClientLanguage", "4"));
         k = var1[m];
         n = Boolean.parseBoolean(var0.a("AutoCreateAccount", "true"));
         o = Integer.parseInt(var0.a("MaxOnlineUser", "30"));
         p = Short.parseShort(var0.a("InjusticeCount", "10"));
         q = Integer.parseInt(var0.a("JusticeCount", "4"));
         r = Integer.parseInt(var0.a("CheckStrictness", "102"));
         s = Integer.parseInt(var0.a("AutosaveInterval", "1200"));
         t = Integer.parseInt(var0.a("AutosaveInterval_Inventory", "300"));
         u = Integer.parseInt(var0.a("AllowIpCount", "2"));
         v = Integer.parseInt(var0.a("AnnounceCycleTime", "10"));
         w = Boolean.parseBoolean(var0.a("AnnounceShowModifyDate", "True"));
         x = Boolean.parseBoolean(var0.a("ShowLoginNews", "True"));
         y = Boolean.parseBoolean(var0.a("ServerRestart", "True"));
         z = Short.parseShort(var0.a("ServerRestartTime", "18"));
         A = var0.a("ServerRestartBatName", "ServerStart.bat");
         B = Double.parseDouble(var0.a("RateExp", "1.0"));
         C = Double.parseDouble(var0.a("RateLawful", "1.0"));
         D = Double.parseDouble(var0.a("RateKarma", "1.0"));
         E = Double.parseDouble(var0.a("RateDropAdena", "1.0"));
         F = Double.parseDouble(var0.a("RateDropItems", "1.0"));
         G = Integer.parseInt(var0.a("RateEnchantWeapon", "68"));
         H = Integer.parseInt(var0.a("RateEnchantArmor", "52"));
         I = Integer.parseInt(var0.a("ChanceAttrEnchant", "10"));
         J = Double.parseDouble(var0.a("RateWeightLimit", "1"));
         K = Double.parseDouble(var0.a("RateWeightLimitforPet", "1"));
         L = Double.parseDouble(var0.a("RateShopSellingPrice", "1.0"));
         M = Double.parseDouble(var0.a("RateShopPurchasingPrice", "1.0"));
         N = Short.parseShort(var0.a("GlobalChatLevel", "30"));
         O = Short.parseShort(var0.a("WhisperChatLevel", "5"));
         P = Byte.parseByte(var0.a("AutoLoot", "2"));
         Q = Integer.parseInt(var0.a("LootingRange", "3"));
         R = Boolean.parseBoolean(var0.a("NonPvP", "true"));
         S = Boolean.parseBoolean(var0.a("GM_AttackMessage", "true"));
         T = Boolean.parseBoolean(var0.a("ChangeTitleByOneself", "false"));
         U = Integer.parseInt(var0.a("MaxClanMember", "10"));
         V = Boolean.parseBoolean(var0.a("ClanAlliance", "true"));
         W = Integer.parseInt(var0.a("MaxPartyMember", "8"));
         X = Integer.parseInt(var0.a("MaxChatPartyMember", "8"));
         Y = Boolean.parseBoolean(var0.a("SimWarPenalty", "true"));
         Z = Boolean.parseBoolean(var0.a("GetBackRestart", "false"));
         aa = Integer.parseInt(var0.a("GroundClearTime", "10"));
         ab = Integer.parseInt(var0.a("GroundClearRange", "5"));
         ac = Boolean.parseBoolean(var0.a("GM_Shop", "false"));
         ad = Integer.parseInt(var0.a("GM_ShopMinID", "-1"));
         ae = Integer.parseInt(var0.a("GM_ShopMaxID", "-1"));
         af = Boolean.parseBoolean(var0.a("WhoCommand", "false"));
         ag = Boolean.parseBoolean(var0.a("RevivalPotion", "false"));
         av = Integer.parseInt(var0.a("GDropItemTime", "10"));
         aw = Integer.parseInt(var0.a("InnRoomCount", "16"));
         String var2 = var0.a("WarDuring", "2h");
         if (var2.contains("d")) {
            ai = 5;
            var2 = var2.replace("d", "");
         } else if (var2.contains("h")) {
            ai = 11;
            var2 = var2.replace("h", "");
         } else if (var2.contains("m")) {
            ai = 12;
            var2 = var2.replace("m", "");
         }

         ah = Integer.parseInt(var2);
         var2 = var0.a("WarInterval", "4d");
         if (var2.contains("d")) {
            ak = 5;
            var2 = var2.replace("d", "");
         } else if (var2.contains("h")) {
            ak = 11;
            var2 = var2.replace("h", "");
         } else if (var2.contains("m")) {
            ak = 12;
            var2 = var2.replace("m", "");
         }

         aj = Integer.parseInt(var2);
         al = Boolean.parseBoolean(var0.a("InitBossSpawn", "true"));
         am = Integer.parseInt(var0.a("ElementalStoneAmount", "300"));
         an = Integer.parseInt(var0.a("HouseTaxInterval", "10"));
         ao = Integer.parseInt(var0.a("MaxDollCount", "1"));
         ap = Integer.parseInt(var0.a("MaxNpcItem", "8"));
         aq = Integer.parseInt(var0.a("MaxAccountWarehouseSize", "100"));
         ar = Integer.parseInt(var0.a("MaxClanWarehouseSize", "200"));
         as = Boolean.parseBoolean(var0.a("DeleteCharacterAfter7Days", "True"));
         at = Integer.parseInt(var0.a("NpcDeathTime", "10"));
         au = Integer.parseInt(var0.a("DefaultCharacterSlot", "6"));
         aN = Boolean.parseBoolean(var0.a("FightIsActive", "False"));
         aO = Boolean.parseBoolean(var0.a("NoviceProtectionIsActive", "False"));
         aP = Integer.parseInt(var0.a("NoviceMaxLevel", "20"));
         aQ = Integer.parseInt(var0.a("NoviceProtectionRange", "10"));
         ax = Integer.parseInt(var0.a("PrinceMaxHP", "1000"));
         ay = Integer.parseInt(var0.a("PrinceMaxMP", "800"));
         az = Integer.parseInt(var0.a("KnightMaxHP", "1400"));
         aA = Integer.parseInt(var0.a("KnightMaxMP", "600"));
         aB = Integer.parseInt(var0.a("ElfMaxHP", "1000"));
         aC = Integer.parseInt(var0.a("ElfMaxMP", "900"));
         aD = Integer.parseInt(var0.a("WizardMaxHP", "800"));
         aE = Integer.parseInt(var0.a("WizardMaxMP", "1200"));
         aF = Integer.parseInt(var0.a("DarkelfMaxHP", "1000"));
         aG = Integer.parseInt(var0.a("DarkelfMaxMP", "900"));
         aH = Integer.parseInt(var0.a("DragonKnightMaxHP", "1400"));
         aI = Integer.parseInt(var0.a("DragonKnightMaxMP", "600"));
         aJ = Integer.parseInt(var0.a("IllusionistMaxHP", "900"));
         aK = Integer.parseInt(var0.a("IllusionistMaxMP", "1100"));
         aL = Integer.parseInt(var0.a("WarriorMaxHP", "1800"));
         aM = Integer.parseInt(var0.a("WarriorMaxMP", "600"));
         aT = Boolean.parseBoolean(var0.a("LoginTestCheck", "false"));
         aU = var0.a("LoginTestVersion", "version");
         EliteMonsterSwitch = Integer.parseInt(var0.a("EliteMonsterSwitch", "0"));
         EliteMonsterTimeSchedule = var0.a("EliteMonsterTimeSchedule", "23").trim();
         MonsterDeathSpawnSwitch = Integer.parseInt(var0.a("MonsterDeathSpawnSwitch", "1"));
         MonsterDeathSpawnGlobalMobChance = Integer.parseInt(var0.a("MonsterDeathSpawnGlobalMobChance", "10"));
         MonsterDeathSpawnGlobalBossChance = Integer.parseInt(var0.a("MonsterDeathSpawnGlobalBossChance", "5"));
         MapDesignatedDropSwitch = Integer.parseInt(var0.a("MapDesignatedDropSwitch", "1"));
         GlobalMapDropSwitch = Integer.parseInt(var0.a("GlobalMapDropSwitch", "1"));
      } catch (Exception var3) {
         aV.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         throw new Error("Failed to Load Config Setting From DB. ");
      }
   }

   public static boolean isEliteMonsterTimeActive() {
      if (EliteMonsterSwitch != 1) {
         return false;
      }
      if (EliteMonsterTimeSchedule == null || EliteMonsterTimeSchedule.isEmpty() || "0".equals(EliteMonsterTimeSchedule)) {
         return true;
      }
      try {
         Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"));
         int hour = cal.get(Calendar.HOUR_OF_DAY);
         if (EliteMonsterTimeSchedule.contains("-")) {
            String[] parts = EliteMonsterTimeSchedule.split("-");
            int start = Integer.parseInt(parts[0].trim());
            int end = Integer.parseInt(parts[1].trim());
            if (start <= end) {
               return hour >= start && hour <= end;
            } else {
               return hour >= start || hour <= end;
            }
         } else {
            int target = Integer.parseInt(EliteMonsterTimeSchedule.trim());
            return hour == target;
         }
      } catch (Exception e) {
         return true;
      }
   }

   public static void b() {
      aV.log(Level.CONFIG, "loading gameserver config");

      try {
         Properties var0 = new Properties();
         InputStream var1 = new FileInputStream(new File("./config/server.properties"));
         var0.load(var1);
         var1.close();
         g = Integer.parseInt(var0.getProperty("GameserverPort", "2000"));
         h = var0.getProperty("URL", "jdbc:mysql://localhost/l1jdb?useUnicode=true&characterEncoding=utf8");
         i = var0.getProperty("Login", "root");
         j = var0.getProperty("Password", "");
         l = var0.getProperty("TimeZone", "Asia/Taipei");
      } catch (Exception var2) {
         aV.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         throw new Error("Failed to Load [server.properties ]File.");
      }
   }

   private Config() {
   }
}
