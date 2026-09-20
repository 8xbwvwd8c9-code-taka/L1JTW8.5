/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ao.bb;
import ap.q;
import ap.u;
import aq.aq;
import aq.f;
import be.ds;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ah {
    private static final Logger a = Logger.getLogger(ah.class.getName());
    private static final HashMap<String, Integer> b = new HashMap();
    private static final HashMap<String, Integer> c = new HashMap();
    private static final HashMap<String, Integer> d = new HashMap();
    private static final HashMap<String, Integer> e = new HashMap();
    private static final HashMap<String, Integer> f = new HashMap();
    private static final HashMap<String, Integer> g = new HashMap();
    private static ah h;
    private bh.j[] i;
    private final HashMap<Integer, bh.j> j = this.e();
    private final HashMap<Integer, bh.j> k;
    private final HashMap<Integer, bh.j> l = this.f();
    private static HashMap<Integer, Integer> m;

    static {
        f.put("none", -1);
        f.put("arrow", 0);
        f.put("wand", 1);
        f.put("light", 2);
        f.put("gem", 3);
        f.put("totem", 4);
        f.put("firecracker", 5);
        f.put("potion", 6);
        f.put("food", 7);
        f.put("scroll", 8);
        f.put("questitem", 9);
        f.put("spellbook", 10);
        f.put("petitem", 11);
        f.put("other", 12);
        f.put("material", 13);
        f.put("event", 14);
        f.put("sting", 15);
        f.put("treasure_box", 16);
        f.put("magic_doll", 17);
        f.put("furniture", 18);
        f.put("magic_doll_e", 22);
        f.put("heal_potion_r", 23);
        f.put("heal_potion_o", 24);
        f.put("heal_potion_w", 25);
        f.put("haste_potion", 26);
        f.put("mp_potion", 27);
        f.put("spell_scroll", 28);
        f.put("cooking", 29);
        f.put("tele_amulet", 30);
        f.put("weapon_scroll", 31);
        f.put("armor_scroll", 32);
        b.put("none", -1);
        b.put("helm", 1);
        b.put("armor", 2);
        b.put("T", 3);
        b.put("greaves", 5);
        b.put("cloak", 4);
        b.put("glove", 7);
        b.put("boots", 6);
        b.put("shield", 8);
        b.put("amulet", 11);
        b.put("ring", 9);
        b.put("belt", 12);
        b.put("rune", 23);
        b.put("earring", 13);
        b.put("guarder", 10);
        b.put("extend1", 15);
        b.put("extend2", 18);
        b.put("extend3", 16);
        b.put("exp", 14);
        b.put("shoulder", 29);
        b.put("badge", 30);
        c.put("none", -1);
        c.put("sword", 1);
        c.put("dagger", 7);
        c.put("tohandsword", 257);
        c.put("bow", 259);
        c.put("spear", 261);
        c.put("axe", 2);
        c.put("staff", 6);
        c.put("gauntlet", 9);
        c.put("claw", 264);
        c.put("edoryu", 260);
        c.put("singlebow", 3);
        c.put("singlespear", 5);
        c.put("tohandaxe", 258);
        c.put("tohandstaff", 262);
        c.put("kiringku", 8);
        c.put("chainsword", 266);
        c.put("tohandkiringku", 264);
        g.put("none", new Integer(0));
        g.put("weapon", new Integer(1));
        g.put("armor", new Integer(2));
        g.put("spell_long", new Integer(5));
        g.put("ntele", new Integer(6));
        g.put("identify", new Integer(7));
        g.put("res", new Integer(8));
        g.put("nts", new Integer(9));
        g.put("letter", new Integer(12));
        g.put("letter_w", new Integer(13));
        g.put("choice", new Integer(14));
        g.put("instrument", new Integer(15));
        g.put("sosc", new Integer(16));
        g.put("spell_short", new Integer(17));
        g.put("armor_T", new Integer(18));
        g.put("armor_Cloak", new Integer(19));
        g.put("armor_Glove", new Integer(20));
        g.put("armor_Boots", new Integer(21));
        g.put("armor_Helm", new Integer(22));
        g.put("armor_Ring", new Integer(23));
        g.put("armor_Amulet", new Integer(24));
        g.put("armor_Shield", new Integer(25));
        g.put("dai", new Integer(26));
        g.put("zel", new Integer(27));
        g.put("blank", new Integer(28));
        g.put("btele", new Integer(29));
        g.put("skill", new Integer(30));
        g.put("ccard", new Integer(31));
        g.put("ccard_w", new Integer(32));
        g.put("vcard", new Integer(33));
        g.put("vcard_w", new Integer(34));
        g.put("wcard", new Integer(35));
        g.put("wcard_w", new Integer(36));
        g.put("armor_Belt", new Integer(37));
        g.put("select_short", new Integer(39));
        g.put("armor_Earring", new Integer(40));
        g.put("fishing_rod", new Integer(42));
        g.put("del", new Integer(46));
        g.put("select_short", new Integer(50));
        g.put("normal", new Integer(51));
        g.put("cook_book", new Integer(52));
        g.put("doll_s", new Integer(55));
        g.put("telbook", new Integer(56));
        g.put("flute_color", new Integer(59));
        g.put("flute", new Integer(60));
        g.put("ras2", new Integer(61));
        g.put("ticket_1", new Integer(62));
        g.put("ticket_2", new Integer(65));
        g.put("armor_Rune", new Integer(66));
        g.put("tam", new Integer(68));
        g.put("armor_Greaves", new Integer(70));
        g.put("armor_Shoulder", new Integer(75));
        g.put("armor_Badge", new Integer(76));
        d.put("none", 0);
        d.put("sword", 4);
        d.put("dagger", 46);
        d.put("tohandsword", 50);
        d.put("bow", 20);
        d.put("axe", 11);
        d.put("spear", 24);
        d.put("staff", 40);
        d.put("gauntlet", 62);
        d.put("claw", 58);
        d.put("edoryu", 54);
        d.put("singlebow", 20);
        d.put("singlespear", 24);
        d.put("tohandaxe", 11);
        d.put("tohandstaff", 40);
        d.put("kiringku", 58);
        d.put("chainsword", 24);
        d.put("tohandkiringku", 58);
        e.put("none", new Integer(0));
        e.put("liquid", new Integer(1));
        e.put("web", new Integer(2));
        e.put("vegetation", new Integer(3));
        e.put("animalmatter", new Integer(4));
        e.put("paper", new Integer(5));
        e.put("cloth", new Integer(6));
        e.put("leather", new Integer(7));
        e.put("wood", new Integer(8));
        e.put("bone", new Integer(9));
        e.put("dragonscale", new Integer(10));
        e.put("iron", new Integer(11));
        e.put("steel", new Integer(12));
        e.put("copper", new Integer(13));
        e.put("silver", new Integer(14));
        e.put("gold", new Integer(15));
        e.put("platinum", new Integer(16));
        e.put("mithril", new Integer(17));
        e.put("blackmithril", new Integer(18));
        e.put("glass", new Integer(19));
        e.put("gemstone", new Integer(20));
        e.put("mineral", new Integer(21));
        e.put("oriharukon", new Integer(22));
        m = new HashMap();
        m.put(166, 40318);
        m.put(569, 40319);
        m.put(837, 40321);
        m.put(3674, 49158);
        m.put(3605, 49157);
        m.put(3606, 49156);
        m.put(2257, 41246);
        m.put(7, 40308);
        m.put(86, 40068);
        m.put(13918, 640730);
        m.put(13919, 640731);
        m.put(13961, 640734);
        m.put(13958, 640735);
        m.put(13959, 640736);
        m.put(13960, 640737);
        m.put(13962, 640738);
        m.put(14467, 640270);
        m.put(14921, 640268);
        m.put(13258, 640312);
        m.put(14046, 640326);
        m.put(6258, 640340);
        m.put(6259, 640343);
        m.put(7386, 640344);
        m.put(14996, 640368);
        m.put(14997, 640357);
        m.put(14998, 640358);
        m.put(12503, 640696);
        m.put(20222, 640621);
        m.put(316, 66);
        m.put(1752, 86);
        m.put(1754, 134);
        m.put(1819, 61);
        m.put(1820, 12);
        m.put(1753, 160);
        m.put(2076, 217);
        m.put(16425, 401);
        m.put(16424, 402);
        m.put(16426, 403);
        m.put(16427, 404);
        m.put(20487, 399);
        m.put(568, 190);
        int[] refinery = new int[]{2, 3, 6, 67, 73, 77, 78, 91, 92, 93, 104, 109, 111, 114, 123, 128, 137, 138, 141, 155, 167, 229, 232, 233, 235, 260, 273, 274, 275, 280, 281, 289, 291, 293, 365, 366, 367, 368, 369, 385, 386, 547, 548, 551, 667, 668, 669, 670, 705, 711, 743, 769, 816, 818, 829, 849, 851, 852, 857, 858, 861, 862, 863, 866, 869, 870, 871, 872, 873, 1076, 1077, 1078, 1079, 1080, 1082, 1083, 1084, 1085, 1086, 1087, 1088, 1089, 1090, 1282, 1283, 1285, 1288, 1289, 1290, 1291, 1496, 1497, 1748, 1755, 1756, 1757, 1758, 1759, 1760, 1761, 1762, 1763, 1764, 1765, 1766, 1767, 1768, 1769, 1781, 1782, 1783, 10280, 10281, 10282};
        int c2 = 0;
        for (int key : bb.a().b().keySet()) {
            if (c2 >= refinery.length) {
                System.out.println("\r\nRefineryTable sequenceID is over range, check [CrystalInfo.tbl]\r\n");
                break;
            }
            m.put(refinery[c2++], key);
        }
        m.put(14, 40010);
        m.put(15, 40011);
        m.put(130, 40012);
        m.put(391, 40019);
        m.put(392, 40020);
        m.put(393, 40021);
        m.put(915, 40022);
        m.put(916, 40023);
        m.put(917, 40024);
        m.put(664, 40043);
        m.put(279, 40506);
        m.put(802, 40026);
        m.put(803, 40027);
        m.put(804, 40028);
        m.put(1101, 41411);
        m.put(1148, 40734);
        m.put(1342, 40029);
        m.put(1424, 40058);
        m.put(1425, 40071);
        m.put(2592, 41337);
        m.put(2775, 41403);
        m.put(2798, 41298);
        m.put(2799, 41299);
        m.put(2800, 41300);
        m.put(4396, 49137);
        m.put(10063, 41141);
        m.put(7235, 640362);
        m.put(7522, 640367);
        m.put(918, 40066);
        m.put(919, 40067);
        m.put(920, 41413);
        m.put(921, 41414);
        m.put(1102, 41412);
        m.put(1149, 40735);
        m.put(1406, 40042);
        m.put(2776, 41404);
        m.put(19080, 640249);
        m.put(19079, 640248);
        m.put(19075, 640112);
        m.put(19032, 640230);
        m.put(19031, 640206);
        m.put(19030, 640205);
        m.put(19029, 640110);
        m.put(19027, 640109);
        m.put(19025, 640111);
        m.put(18378, 640183);
        m.put(18377, 640185);
        m.put(18376, 640184);
        m.put(18375, 640182);
        m.put(18370, 640162);
        m.put(18369, 640174);
        m.put(18368, 640178);
        m.put(18367, 640170);
        m.put(18366, 640173);
        m.put(18365, 640177);
        m.put(18364, 640169);
        m.put(18363, 640172);
        m.put(18362, 640176);
        m.put(18361, 640168);
        m.put(18360, 640171);
        m.put(18359, 640175);
        m.put(18127, 640247);
        m.put(18100, 640132);
        m.put(18099, 640131);
        m.put(18098, 640113);
        m.put(18097, 640130);
        m.put(18096, 640156);
        m.put(18095, 640155);
        m.put(18094, 640154);
        m.put(18093, 640143);
        m.put(18092, 640193);
        m.put(18091, 640198);
        m.put(18090, 640201);
        m.put(18089, 640200);
        m.put(18088, 640199);
        m.put(18087, 640197);
        m.put(14015, 640159);
        m.put(14014, 640158);
        m.put(14013, 640157);
        m.put(14012, 640160);
        m.put(13220, 640204);
        m.put(13218, 640116);
        m.put(12614, 640140);
        m.put(12613, 640139);
        m.put(12612, 640129);
        m.put(12611, 640138);
        m.put(12610, 640137);
        m.put(12609, 640136);
        m.put(12608, 640128);
        m.put(12607, 640127);
        m.put(12598, 640135);
        m.put(12597, 640134);
        m.put(12596, 640133);
        m.put(12595, 640126);
        m.put(12594, 640125);
        m.put(12593, 640124);
        m.put(10635, 640210);
        m.put(9976, 640218);
        m.put(9914, 640219);
        m.put(9913, 640220);
        m.put(9873, 640209);
        m.put(9870, 640207);
        m.put(9869, 640213);
        m.put(9868, 640212);
        m.put(9867, 640211);
        m.put(9743, 640103);
        m.put(7963, 640265);
        m.put(7962, 640264);
        m.put(7961, 640251);
        m.put(7960, 640263);
        m.put(7959, 640262);
        m.put(7941, 640257);
        m.put(7940, 640256);
        m.put(7922, 640145);
        m.put(7921, 640153);
        m.put(7920, 640152);
        m.put(7919, 640151);
        m.put(7918, 640150);
        m.put(7908, 640179);
        m.put(7907, 640192);
        m.put(7906, 640195);
        m.put(7905, 640196);
        m.put(7820, 640228);
        m.put(7819, 640231);
        m.put(7818, 640208);
        m.put(7817, 640216);
        m.put(7816, 640163);
        m.put(7815, 640194);
        m.put(7805, 640107);
        m.put(7804, 640108);
        m.put(7803, 640191);
        m.put(7802, 640202);
        m.put(7801, 640189);
        m.put(7800, 640188);
        m.put(7799, 640187);
        m.put(7798, 640186);
        m.put(7796, 640181);
        m.put(7795, 640180);
        m.put(7794, 640224);
        m.put(7793, 640223);
        m.put(7792, 640123);
        m.put(7791, 640122);
        m.put(7790, 640121);
        m.put(7789, 640120);
        m.put(7788, 640119);
        m.put(7787, 640118);
        m.put(7785, 640164);
        m.put(7784, 640165);
        m.put(7783, 640161);
        m.put(7782, 640222);
        m.put(7780, 640149);
        m.put(7779, 640148);
        m.put(7778, 640147);
        m.put(7777, 640146);
        m.put(7760, 640144);
        m.put(7759, 640225);
        m.put(7753, 640141);
        m.put(7752, 640142);
        m.put(7741, 640190);
        m.put(7720, 640115);
        m.put(7714, 640114);
        m.put(7583, 640261);
        m.put(7582, 640250);
        m.put(7580, 640255);
        m.put(7579, 640254);
        m.put(7578, 640253);
        m.put(7574, 640260);
        m.put(7573, 640259);
        m.put(7572, 640258);
        m.put(7569, 640252);
        m.put(7519, 640214);
        m.put(7517, 640215);
        m.put(7114, 640246);
        m.put(7113, 640245);
        m.put(7112, 640244);
        m.put(7111, 640243);
        m.put(7110, 640242);
        m.put(7109, 640241);
        m.put(6289, 640233);
        m.put(6118, 640240);
        m.put(6117, 640239);
        m.put(6115, 640238);
        m.put(6114, 640237);
        m.put(6112, 640236);
        m.put(6111, 640235);
        m.put(4337, 640221);
        m.put(4160, 640227);
        m.put(3586, 640229);
        m.put(3376, 640226);
        m.put(3357, 640167);
        m.put(3356, 640166);
        m.put(3354, 640203);
        m.put(3353, 640234);
        m.put(20161, 640585);
        m.put(20162, 640586);
        m.put(20163, 640587);
        m.put(20322, 640643);
        m.put(20323, 640644);
        m.put(20325, 640645);
        m.put(20324, 640646);
        m.put(20326, 640647);
        m.put(20457, 640663);
        m.put(20336, 640664);
        m.put(20337, 640665);
        m.put(20387, 640667);
        m.put(20389, 640666);
        m.put(20600, 640724);
        m.put(21147, 640791);
        m.put(21148, 640792);
        m.put(21149, 640793);
        m.put(21150, 640794);
        m.put(21151, 640795);
        m.put(20877, 640835);
        m.put(17531, 640947);
        m.put(17532, 640948);
        m.put(17541, 640949);
        m.put(17542, 640950);
        m.put(19328, 640804);
        m.put(15391, 640938);
        m.put(17038, 640939);
        m.put(949, 40278);
        m.put(12168, 40279);
        m.put(808, 40280);
        m.put(809, 40281);
        m.put(810, 40282);
        m.put(811, 40283);
        m.put(945, 40284);
        m.put(946, 40285);
        m.put(947, 40286);
        m.put(948, 40287);
    }

    public static ah a() {
        if (h == null) {
            h = new ah();
        }
        return h;
    }

    private ah() {
        this.k = this.g();
        this.h();
    }

    private HashMap<Integer, bh.j> e() {
        HashMap<Integer, bh.j> result;
        block7: {
            result = new HashMap<Integer, bh.j>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            bh.j item = new bh.j();
            try {
                con = l1j.server.b.a().b();
                pstm = con.prepareStatement("select * from etcitem ORDER BY item_id ASC");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    item = new bh.j();
                    item.b(rs.getInt("item_id"));
                    item.a(rs.getString("name"));
                    item.b(rs.getString("unidentified_name_id"));
                    item.c(rs.getString("identified_name_id"));
                    item.ak(f.get(rs.getString("item_type")));
                    item.u(g.get(rs.getString("use_type")));
                    item.a(0);
                    item.c(e.get(rs.getString("material")));
                    item.d(rs.getInt("weight"));
                    item.e(rs.getInt("invgfx"));
                    item.f(rs.getInt("grdgfx"));
                    item.i(rs.getInt("itemdesc_id"));
                    item.g(rs.getInt("min_lvl"));
                    item.h(rs.getInt("max_lvl"));
                    item.j(rs.getInt("bless"));
                    item.a(!rs.getBoolean("cant_trade"));
                    item.b(rs.getBoolean("cant_delete"));
                    item.p(rs.getBoolean("can_seal"));
                    item.k(rs.getInt("dmg_small"));
                    item.l(rs.getInt("dmg_large"));
                    item.o(rs.getBoolean("stackable"));
                    item.ai(rs.getInt("max_charge_count"));
                    item.ad(rs.getInt("locx"));
                    item.ae(rs.getInt("locy"));
                    item.a(rs.getShort("mapid"));
                    item.af(rs.getInt("delay_id"));
                    item.ag(rs.getInt("delay_time"));
                    item.ah(rs.getInt("delay_effect"));
                    item.v(rs.getInt("value"));
                    item.c(rs.getBoolean("save_at_once"));
                    item.w(this.c(item.g()));
                    result.put(new Integer(item.g()), item);
                }
            }
            catch (NullPointerException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                bi.j.a(rs, pstm, con);
                break block7;
            }
            catch (SQLException e3) {
                try {
                    a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                }
                catch (Throwable throwable) {
                    bi.j.a(rs, pstm, con);
                    throw throwable;
                }
                bi.j.a(rs, pstm, con);
                break block7;
            }
            bi.j.a(rs, pstm, con);
        }
        return result;
    }

    private HashMap<Integer, bh.j> f() {
        HashMap<Integer, bh.j> result;
        block9: {
            result = new HashMap<Integer, bh.j>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            bh.j weapon = new bh.j();
            try {
                con = l1j.server.b.a().b();
                pstm = con.prepareStatement("select * from weapon ORDER BY item_id ASC");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    weapon = new bh.j();
                    weapon.b(rs.getInt("item_id"));
                    weapon.a(rs.getString("name"));
                    weapon.b(rs.getString("unidentified_name_id"));
                    weapon.c(rs.getString("identified_name_id"));
                    weapon.ak(c.get(rs.getString("type")));
                    weapon.aj(d.get(rs.getString("type")));
                    weapon.a(1);
                    weapon.u(g.get("weapon"));
                    weapon.c(e.get(rs.getString("material")));
                    weapon.d(rs.getInt("weight"));
                    weapon.e(rs.getInt("invgfx"));
                    weapon.f(rs.getInt("grdgfx"));
                    weapon.i(rs.getInt("itemdesc_id"));
                    weapon.k(rs.getInt("dmg_small"));
                    weapon.l(rs.getInt("dmg_large"));
                    weapon.aa(rs.getInt("range"));
                    weapon.m(rs.getInt("safenchant"));
                    weapon.d(rs.getBoolean("use_royal"));
                    weapon.e(rs.getBoolean("use_knight"));
                    weapon.f(rs.getBoolean("use_elf"));
                    weapon.g(rs.getBoolean("use_mage"));
                    weapon.h(rs.getBoolean("use_darkelf"));
                    weapon.i(rs.getBoolean("use_dragonknight"));
                    weapon.j(rs.getBoolean("use_illusionist"));
                    weapon.k(rs.getBoolean("use_warrior"));
                    if (weapon.aO() == 20 || weapon.aO() == 62) {
                        weapon.B(rs.getInt("hitmodifier"));
                        weapon.D(rs.getInt("dmgmodifier"));
                    } else {
                        weapon.A(rs.getInt("hitmodifier"));
                        weapon.C(rs.getInt("dmgmodifier"));
                    }
                    weapon.a(rs.getByte("add_str"));
                    weapon.b(rs.getByte("add_dex"));
                    weapon.c(rs.getByte("add_con"));
                    weapon.d(rs.getByte("add_int"));
                    weapon.e(rs.getByte("add_wis"));
                    weapon.f(rs.getByte("add_cha"));
                    weapon.n(rs.getInt("add_hp"));
                    weapon.o(rs.getInt("add_mp"));
                    weapon.p(rs.getInt("add_hpr"));
                    weapon.q(rs.getInt("add_mpr"));
                    weapon.r(rs.getInt("add_sp"));
                    weapon.s(rs.getInt("m_def"));
                    weapon.ab(rs.getInt("double_dmg_chance"));
                    weapon.ac(rs.getInt("magicdmgmodifier"));
                    weapon.n(rs.getBoolean("canbedmg"));
                    weapon.g(rs.getInt("min_lvl"));
                    weapon.h(rs.getInt("max_lvl"));
                    weapon.j(rs.getInt("bless"));
                    weapon.a(!rs.getBoolean("cant_trade"));
                    weapon.b(rs.getBoolean("cant_delete"));
                    weapon.l(rs.getBoolean("haste_item"));
                    weapon.t(rs.getInt("max_use_time"));
                    weapon.V(rs.getInt("antiDamageReduction"));
                    weapon.X(rs.getInt("stunLevel"));
                    weapon.w(this.c(weapon.g()));
                    result.put(new Integer(weapon.g()), weapon);
                }
            }
            catch (NullPointerException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                bi.j.a(rs, pstm, con);
                break block9;
            }
            catch (SQLException e3) {
                try {
                    a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                }
                catch (Throwable throwable) {
                    bi.j.a(rs, pstm, con);
                    throw throwable;
                }
                bi.j.a(rs, pstm, con);
                break block9;
            }
            bi.j.a(rs, pstm, con);
        }
        return result;
    }

    private HashMap<Integer, bh.j> g() {
        HashMap<Integer, bh.j> result;
        block23: {
            result = new HashMap<Integer, bh.j>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            bh.j armor = new bh.j();
            try {
                con = l1j.server.b.a().b();
                pstm = con.prepareStatement("select * from armor ORDER BY item_id ASC");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    armor = new bh.j();
                    armor.b(rs.getInt("item_id"));
                    armor.a(rs.getString("name"));
                    armor.b(rs.getString("unidentified_name_id"));
                    armor.c(rs.getString("identified_name_id"));
                    armor.ak(b.get(rs.getString("type")));
                    armor.a(2);
                    switch (armor.aP()) {
                        case 29: {
                            armor.u(g.get("armor_Shoulder"));
                            break;
                        }
                        case 30: {
                            armor.u(g.get("armor_Badge"));
                            break;
                        }
                        case 13: {
                            armor.u(g.get("armor_Earring"));
                            break;
                        }
                        case 1: {
                            armor.u(g.get("armor_Helm"));
                            break;
                        }
                        case 3: {
                            armor.u(g.get("armor_T"));
                            break;
                        }
                        case 4: {
                            armor.u(g.get("armor_Cloak"));
                            break;
                        }
                        case 5: {
                            armor.u(g.get("armor_Greaves"));
                            break;
                        }
                        case 7: {
                            armor.u(g.get("armor_Glove"));
                            break;
                        }
                        case 6: {
                            armor.u(g.get("armor_Boots"));
                            break;
                        }
                        case 12: {
                            armor.u(g.get("armor_Belt"));
                            break;
                        }
                        case 11: {
                            armor.u(g.get("armor_Amulet"));
                            break;
                        }
                        case 14: 
                        case 15: 
                        case 16: 
                        case 18: 
                        case 23: {
                            armor.u(g.get("armor_Rune"));
                            break;
                        }
                        case 8: 
                        case 10: {
                            armor.u(g.get("armor_Shield"));
                            break;
                        }
                        case 9: {
                            armor.u(g.get("armor_Ring"));
                            break;
                        }
                        default: {
                            armor.u(g.get("armor"));
                        }
                    }
                    armor.c(e.get(rs.getString("material")));
                    armor.d(rs.getInt("weight"));
                    armor.e(rs.getInt("invgfx"));
                    armor.f(rs.getInt("grdgfx"));
                    armor.i(rs.getInt("itemdesc_id"));
                    armor.x(rs.getInt("ac"));
                    armor.m(rs.getInt("safenchant"));
                    armor.d(rs.getBoolean("use_royal"));
                    armor.e(rs.getBoolean("use_knight"));
                    armor.f(rs.getBoolean("use_elf"));
                    armor.g(rs.getBoolean("use_mage"));
                    armor.h(rs.getBoolean("use_darkelf"));
                    armor.i(rs.getBoolean("use_dragonknight"));
                    armor.j(rs.getBoolean("use_illusionist"));
                    armor.k(rs.getBoolean("use_warrior"));
                    armor.a(rs.getByte("add_str"));
                    armor.c(rs.getByte("add_con"));
                    armor.b(rs.getByte("add_dex"));
                    armor.d(rs.getByte("add_int"));
                    armor.e(rs.getByte("add_wis"));
                    armor.f(rs.getByte("add_cha"));
                    armor.n(rs.getInt("add_hp"));
                    armor.o(rs.getInt("add_mp"));
                    armor.p(rs.getInt("add_hpr"));
                    armor.q(rs.getInt("add_mpr"));
                    armor.r(rs.getInt("add_sp"));
                    armor.g(rs.getInt("min_lvl"));
                    armor.h(rs.getInt("max_lvl"));
                    armor.s(rs.getInt("m_def"));
                    armor.y(rs.getInt("damage_reduction"));
                    armor.z(rs.getInt("weight_reduction"));
                    armor.A(rs.getInt("hit_modifier"));
                    armor.C(rs.getInt("dmg_modifier"));
                    armor.B(rs.getInt("bow_hit_modifier"));
                    armor.D(rs.getInt("bow_dmg_modifier"));
                    armor.l(rs.getBoolean("haste_item"));
                    armor.j(rs.getInt("bless"));
                    armor.a(!rs.getBoolean("cant_trade"));
                    armor.b(rs.getBoolean("cant_delete"));
                    armor.H(rs.getInt("defense_earth"));
                    armor.E(rs.getInt("defense_water"));
                    armor.F(rs.getInt("defense_wind"));
                    armor.G(rs.getInt("defense_fire"));
                    armor.I(rs.getInt("regist_stun"));
                    armor.J(rs.getInt("regist_stone"));
                    armor.K(rs.getInt("regist_sleep"));
                    armor.L(rs.getInt("regist_freeze"));
                    armor.M(rs.getInt("regist_sustain"));
                    armor.N(rs.getInt("regist_blind"));
                    armor.O(rs.getInt("regist_fear"));
                    armor.t(rs.getInt("max_use_time"));
                    armor.P(rs.getInt("grade"));
                    armor.Q(rs.getInt("pvpDamage"));
                    armor.R(rs.getInt("pvpDamageReduction"));
                    armor.S(rs.getInt("magicHit"));
                    armor.T(rs.getInt("magicCritical"));
                    armor.U(rs.getInt("healPotionRegeneration"));
                    armor.V(rs.getInt("antiDamageReduction"));
                    armor.W(rs.getInt("exp"));
                    armor.m(rs.getBoolean("antiPoison"));
                    armor.X(rs.getInt("stunLevel"));
                    armor.Y(rs.getInt("fearLevel"));
                    armor.Z(rs.getInt("breakLevel"));
                    armor.w(this.c(armor.g()));
                    result.put(new Integer(armor.g()), armor);
                }
            }
            catch (NullPointerException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                bi.j.a(rs, pstm, con);
                break block23;
            }
            catch (SQLException e3) {
                try {
                    a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                }
                catch (Throwable throwable) {
                    bi.j.a(rs, pstm, con);
                    throw throwable;
                }
                bi.j.a(rs, pstm, con);
                break block23;
            }
            bi.j.a(rs, pstm, con);
        }
        return result;
    }

    private void h() {
        bh.j item;
        int highestId = 0;
        Collection<bh.j> items = this.j.values();
        for (bh.j item2 : items) {
            if (item2.g() <= highestId) continue;
            highestId = item2.g();
        }
        Collection<bh.j> weapons = this.l.values();
        for (bh.j weapon : weapons) {
            if (weapon.g() <= highestId) continue;
            highestId = weapon.g();
        }
        Collection<bh.j> armors = this.k.values();
        for (bh.j armor : armors) {
            if (armor.g() <= highestId) continue;
            highestId = armor.g();
        }
        this.i = new bh.j[highestId + 1];
        for (Integer id : this.j.keySet()) {
            this.i[id.intValue()] = item = this.j.get(id);
        }
        for (Integer id : this.l.keySet()) {
            this.i[id.intValue()] = item = this.l.get(id);
        }
        for (Integer id : this.k.keySet()) {
            this.i[id.intValue()] = item = this.k.get(id);
        }
    }

    public bh.j a(int id) {
        return this.i[id];
    }

    public q b(int itemId) {
        bh.j temp = this.a(itemId);
        if (temp == null) {
            return null;
        }
        q item = new q(temp, 1);
        item.cF(ai.d.a().d());
        if (itemId == 21446) {
            this.a(item);
        }
        aq.a().a(item);
        return item;
    }

    private void a(q item) {
        int[] bits = new int[]{1, 2, 4, 8, 16, 32, 131072, 64, 128, 524288, 256, 512, 1024, 16384, 32768, 65536, 262144};
        int[] bits2 = new int[]{2048, 4096, 8192, 0x100000};
        int i2 = 0;
        while (i2 < 4) {
            int rnd;
            int currentStatus = 0;
            currentStatus |= item.X();
            currentStatus |= item.Y();
            currentStatus |= item.Z();
            currentStatus |= item.aa();
            int newSuperEnchant = 0;
            while (newSuperEnchant == 0) {
                rnd = bi.i.a(bits.length);
                if ((currentStatus & bits[rnd]) == bits[rnd]) continue;
                newSuperEnchant = bits[rnd];
                if (i2 == 0) {
                    item.l(newSuperEnchant);
                    continue;
                }
                if (i2 == 1) {
                    item.m(newSuperEnchant);
                    continue;
                }
                if (i2 == 2) {
                    item.n(newSuperEnchant);
                    continue;
                }
                if (i2 != 3) continue;
                item.o(newSuperEnchant);
            }
            rnd = bi.i.a(bits2.length);
            if (i2 == 0) {
                item.l(item.X() | bits2[rnd]);
            } else if (i2 == 1) {
                item.m(item.Y() | bits2[rnd]);
            } else if (i2 == 2) {
                item.n(item.Z() | bits2[rnd]);
            } else if (i2 == 3) {
                item.o(item.aa() | bits2[rnd]);
            }
            ++i2;
        }
    }

    public int a(String name) {
        int itemid = 0;
        bh.j[] jArray = this.i;
        int n2 = this.i.length;
        int n3 = 0;
        while (n3 < n2) {
            bh.j item = jArray[n3];
            if (item != null && item.h().equals(name)) {
                itemid = item.g();
                break;
            }
            ++n3;
        }
        return itemid;
    }

    public int b(String name) {
        int itemid = 0;
        bh.j[] jArray = this.i;
        int n2 = this.i.length;
        int n3 = 0;
        while (n3 < n2) {
            bh.j item = jArray[n3];
            if (item != null && item.h().replace(" ", "").equals(name.replace(" ", ""))) {
                itemid = item.g();
                break;
            }
            ++n3;
        }
        return itemid;
    }

    private int c(int itemid) {
        for (int key : m.keySet()) {
            if (m.get(key) != itemid) continue;
            return key;
        }
        int i2 = 0;
        while (i2 < 20000) {
            if (!m.containsKey(i2)) {
                m.put(i2, itemid);
                return i2;
            }
            ++i2;
        }
        System.out.println("ItemTable sequenceID is over range");
        return itemid;
    }

    public static q a(f target, int item_id, int count) {
        return ah.a(target, item_id, count, 0, null, false, true, 0, 1, true);
    }

    public static q a(u pc, int item_id, int count) {
        return ah.a(pc, item_id, count, 0, null, false, false, 0, 1, true);
    }

    public static q a(u pc, int item_id, int count, boolean isIdentified) {
        return ah.a(pc, item_id, count, 0, null, isIdentified, false, 0, 1, true);
    }

    public static q a(u pc, int item_id, int count, int enchant, int bless, boolean isIdentified) {
        return ah.a(pc, item_id, count, enchant, null, isIdentified, false, 0, bless, true);
    }

    public static q a(u pc, int item_id, int count, int enchant, int bless, boolean isIdentified, int useDay) {
        return ah.a(pc, item_id, count, enchant, null, isIdentified, false, useDay, bless, true);
    }

    public static q a(u pc, int item_id, int count, int enchant) {
        return ah.a(pc, item_id, count, enchant, null, false, false, 0, 1, true);
    }

    public static q a(u pc, int item_id, int count, int enchant, boolean showMessage) {
        return ah.a(pc, item_id, count, enchant, null, false, false, 0, 1, showMessage);
    }

    public static q a(u pc, int item_id, int count, int enchant, boolean isIdentified, boolean showMessage) {
        return ah.a(pc, item_id, count, enchant, null, isIdentified, false, 0, 1, showMessage);
    }

    public static q a(u pc, int item_id, int count, int enchant, int useDay) {
        return ah.a(pc, item_id, count, enchant, null, false, false, useDay, 1, true);
    }

    public static q a(u pc, int item_id, int count, String name) {
        return ah.a(pc, item_id, count, 0, name, false, false, 0, 1, true);
    }

    public static q a(u pc, int item_id, int count, int enchant, String name) {
        return ah.a(pc, item_id, count, enchant, name, false, false, 0, 1, true);
    }

    private static q a(f target, int item_id, int count, int enchant, String npcName, boolean isIdentified, boolean isGround, int useDay, int bless, boolean isMsg) {
        u pc;
        q item;
        block33: {
            block31: {
                block32: {
                    item = ah.a().b(item_id);
                    if (item == null) {
                        a.log(Level.SEVERE, "ItemTable createNewItem item_id= [" + item_id + "] is null");
                        return null;
                    }
                    if (!item.d()) break block31;
                    item.e(count);
                    item.a(enchant);
                    item.a(isIdentified);
                    if (bless != 1) {
                        item.f(bless);
                    }
                    if (!isGround) break block32;
                    aq.a().a(target.fs(), target.ft(), target.fp()).d(item);
                    break block33;
                }
                if (!(target instanceof u)) break block33;
                pc = (u)target;
                if (pc.j().a(item, count) == 0) {
                    pc.j().d(item);
                } else {
                    aq.a().a(pc.fs(), pc.ft(), pc.fp()).d(item);
                }
                break block33;
            }
            int i2 = 0;
            while (i2 < count) {
                q each_item = i2 == 0 ? item : ah.a().b(item_id);
                if (enchant == -1) {
                    int rnd_enchant = 0;
                    int chance = bi.i.a(100) + 1;
                    if (chance <= 15) {
                        rnd_enchant = -2;
                    } else if (chance >= 16 && chance <= 30) {
                        rnd_enchant = -1;
                    } else if (chance >= 31 && chance <= 70) {
                        rnd_enchant = 0;
                    } else if (chance >= 71 && chance <= 87) {
                        rnd_enchant = bi.i.a(2) + 1;
                    } else if (chance >= 88 && chance <= 97) {
                        rnd_enchant = bi.i.a(3) + 3;
                    } else if (chance >= 98 && chance <= 99) {
                        rnd_enchant = 6;
                    } else if (chance == 100) {
                        rnd_enchant = 7;
                    }
                    each_item.a(rnd_enchant);
                } else {
                    each_item.a(enchant);
                }
                each_item.a(isIdentified);
                if (bless != 1) {
                    each_item.f(bless);
                }
                each_item.n();
                if (each_item.N() == 413) {
                    each_item.b(new Timestamp(System.currentTimeMillis() + 3600000L));
                }
                if (useDay > 0) {
                    Timestamp limit = new Timestamp(System.currentTimeMillis() + (long)(useDay * 24 * 60 * 60) * 1000L);
                    each_item.b(limit);
                }
                if (isGround) {
                    aq.a().a(target.fs(), target.ft(), target.fp()).d(each_item);
                } else if (target instanceof u) {
                    u pc2 = (u)target;
                    if (pc2.j().a(each_item, count) == 0) {
                        pc2.j().d(each_item);
                    } else {
                        aq.a().a(pc2.fs(), pc2.ft(), pc2.fp()).d(each_item);
                    }
                }
                ++i2;
            }
        }
        if (target instanceof u && isMsg) {
            pc = (u)target;
            if (npcName != null) {
                pc.a(new ds(143, npcName, item.s()));
            } else {
                pc.a(new ds(403, item.s()));
            }
        }
        return item;
    }

    public HashMap<Integer, bh.j> b() {
        return this.j;
    }

    public HashMap<Integer, bh.j> c() {
        return this.k;
    }

    public HashMap<Integer, bh.j> d() {
        return this.l;
    }
}

