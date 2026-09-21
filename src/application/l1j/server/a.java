/*
 * Decompiled with CFR 0.152.
 */
package l1j.server;

import ao.r;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class a {
    private static final Logger aV = Logger.getLogger(a.class.getName());
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

    public static void a() {
        try {
            r table = ao.r.a();
            String[] codes = new String[]{"UTF8", "EUCKR", "UTF8", "BIG5", "SJIS", "GBK"};
            m = Short.parseShort(table.a("ClientLanguage", "4"));
            k = codes[m];
            n = Boolean.parseBoolean(table.a("AutoCreateAccount", "true"));
            o = Integer.parseInt(table.a("MaxOnlineUser", "30"));
            p = Short.parseShort(table.a("InjusticeCount", "10"));
            q = Integer.parseInt(table.a("JusticeCount", "4"));
            r = Integer.parseInt(table.a("CheckStrictness", "102"));
            s = Integer.parseInt(table.a("AutosaveInterval", "1200"));
            t = Integer.parseInt(table.a("AutosaveInterval_Inventory", "300"));
            u = Integer.parseInt(table.a("AllowIpCount", "2"));
            v = Integer.parseInt(table.a("AnnounceCycleTime", "10"));
            w = Boolean.parseBoolean(table.a("AnnounceShowModifyDate", "True"));
            x = Boolean.parseBoolean(table.a("ShowLoginNews", "True"));
            y = Boolean.parseBoolean(table.a("ServerRestart", "True"));
            z = Short.parseShort(table.a("ServerRestartTime", "18"));
            A = table.a("ServerRestartBatName", "ServerStart.bat");
            B = Double.parseDouble(table.a("RateExp", "1.0"));
            C = Double.parseDouble(table.a("RateLawful", "1.0"));
            D = Double.parseDouble(table.a("RateKarma", "1.0"));
            E = Double.parseDouble(table.a("RateDropAdena", "1.0"));
            F = Double.parseDouble(table.a("RateDropItems", "1.0"));
            G = Integer.parseInt(table.a("RateEnchantWeapon", "68"));
            H = Integer.parseInt(table.a("RateEnchantArmor", "52"));
            I = Integer.parseInt(table.a("ChanceAttrEnchant", "10"));
            J = Double.parseDouble(table.a("RateWeightLimit", "1"));
            K = Double.parseDouble(table.a("RateWeightLimitforPet", "1"));
            L = Double.parseDouble(table.a("RateShopSellingPrice", "1.0"));
            M = Double.parseDouble(table.a("RateShopPurchasingPrice", "1.0"));
            N = Short.parseShort(table.a("GlobalChatLevel", "30"));
            O = Short.parseShort(table.a("WhisperChatLevel", "5"));
            P = Byte.parseByte(table.a("AutoLoot", "2"));
            Q = Integer.parseInt(table.a("LootingRange", "3"));
            R = Boolean.parseBoolean(table.a("NonPvP", "true"));
            S = Boolean.parseBoolean(table.a("GM_AttackMessage", "true"));
            T = Boolean.parseBoolean(table.a("ChangeTitleByOneself", "false"));
            U = Integer.parseInt(table.a("MaxClanMember", "10"));
            V = Boolean.parseBoolean(table.a("ClanAlliance", "true"));
            W = Integer.parseInt(table.a("MaxPartyMember", "8"));
            X = Integer.parseInt(table.a("MaxChatPartyMember", "8"));
            Y = Boolean.parseBoolean(table.a("SimWarPenalty", "true"));
            Z = Boolean.parseBoolean(table.a("GetBackRestart", "false"));
            aa = Integer.parseInt(table.a("GroundClearTime", "10"));
            ab = Integer.parseInt(table.a("GroundClearRange", "5"));
            ac = Boolean.parseBoolean(table.a("GM_Shop", "false"));
            ad = Integer.parseInt(table.a("GM_ShopMinID", "-1"));
            ae = Integer.parseInt(table.a("GM_ShopMaxID", "-1"));
            af = Boolean.parseBoolean(table.a("WhoCommand", "false"));
            ag = Boolean.parseBoolean(table.a("RevivalPotion", "false"));
            av = Integer.parseInt(table.a("GDropItemTime", "10"));
            aw = Integer.parseInt(table.a("InnRoomCount", "16"));
            String text = table.a("WarDuring", "2h");
            if (text.contains("d")) {
                ai = 5;
                text = text.replace("d", "");
            } else if (text.contains("h")) {
                ai = 11;
                text = text.replace("h", "");
            } else if (text.contains("m")) {
                ai = 12;
                text = text.replace("m", "");
            }
            ah = Integer.parseInt(text);
            text = table.a("WarInterval", "4d");
            if (text.contains("d")) {
                ak = 5;
                text = text.replace("d", "");
            } else if (text.contains("h")) {
                ak = 11;
                text = text.replace("h", "");
            } else if (text.contains("m")) {
                ak = 12;
                text = text.replace("m", "");
            }
            aj = Integer.parseInt(text);
            al = Boolean.parseBoolean(table.a("InitBossSpawn", "true"));
            am = Integer.parseInt(table.a("ElementalStoneAmount", "300"));
            an = Integer.parseInt(table.a("HouseTaxInterval", "10"));
            ao = Integer.parseInt(table.a("MaxDollCount", "1"));
            ap = Integer.parseInt(table.a("MaxNpcItem", "8"));
            aq = Integer.parseInt(table.a("MaxAccountWarehouseSize", "100"));
            ar = Integer.parseInt(table.a("MaxClanWarehouseSize", "200"));
            as = Boolean.parseBoolean(table.a("DeleteCharacterAfter7Days", "True"));
            at = Integer.parseInt(table.a("NpcDeathTime", "10"));
            au = Integer.parseInt(table.a("DefaultCharacterSlot", "6"));
            aN = Boolean.parseBoolean(table.a("FightIsActive", "False"));
            aO = Boolean.parseBoolean(table.a("NoviceProtectionIsActive", "False"));
            aP = Integer.parseInt(table.a("NoviceMaxLevel", "20"));
            aQ = Integer.parseInt(table.a("NoviceProtectionRange", "10"));
            ax = Integer.parseInt(table.a("PrinceMaxHP", "1000"));
            ay = Integer.parseInt(table.a("PrinceMaxMP", "800"));
            az = Integer.parseInt(table.a("KnightMaxHP", "1400"));
            aA = Integer.parseInt(table.a("KnightMaxMP", "600"));
            aB = Integer.parseInt(table.a("ElfMaxHP", "1000"));
            aC = Integer.parseInt(table.a("ElfMaxMP", "900"));
            aD = Integer.parseInt(table.a("WizardMaxHP", "800"));
            aE = Integer.parseInt(table.a("WizardMaxMP", "1200"));
            aF = Integer.parseInt(table.a("DarkelfMaxHP", "1000"));
            aG = Integer.parseInt(table.a("DarkelfMaxMP", "900"));
            aH = Integer.parseInt(table.a("DragonKnightMaxHP", "1400"));
            aI = Integer.parseInt(table.a("DragonKnightMaxMP", "600"));
            aJ = Integer.parseInt(table.a("IllusionistMaxHP", "900"));
            aK = Integer.parseInt(table.a("IllusionistMaxMP", "1100"));
            aL = Integer.parseInt(table.a("WarriorMaxHP", "1800"));
            aM = Integer.parseInt(table.a("WarriorMaxMP", "600"));
            aT = Boolean.parseBoolean(table.a("LoginTestCheck", "false"));
            aU = table.a("LoginTestVersion", "version");
        }
        catch (Exception e2) {
            aV.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            throw new Error("Failed to Load Config Setting From DB. ");
        }
    }

    public static void b() {
        aV.log(Level.CONFIG, "loading gameserver config");
        try {
            Properties serverSettings = new Properties();
            FileInputStream is = new FileInputStream(new File("./config/server.properties"));
            serverSettings.load(is);
            ((InputStream)is).close();
            g = Integer.parseInt(serverSettings.getProperty("GameserverPort", "2000"));
            h = serverSettings.getProperty("URL", "jdbc:mysql://localhost/l1jdb?useUnicode=true&characterEncoding=utf8");
            i = serverSettings.getProperty("Login", "root");
            j = serverSettings.getProperty("Password", "");
            l = serverSettings.getProperty("TimeZone", "Asia/Taipei");
        }
        catch (Exception e2) {
            aV.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            throw new Error("Failed to Load [server.properties ]File.");
        }
    }

    private a() {
    }
}

