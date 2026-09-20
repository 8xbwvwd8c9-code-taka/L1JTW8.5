package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Item;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class ItemTable {
   private static final Logger a = Logger.getLogger(ItemTable.class.getName());
   private static final HashMap<String, Integer> b = new HashMap<>();
   private static final HashMap<String, Integer> c = new HashMap<>();
   private static final HashMap<String, Integer> d = new HashMap<>();
   private static final HashMap<String, Integer> e = new HashMap<>();
   private static final HashMap<String, Integer> f = new HashMap<>();
   private static final HashMap<String, Integer> g = new HashMap<>();
   private static ItemTable h;
   private L1Item[] i;
   private final HashMap<Integer, L1Item> j = this.e();
   private final HashMap<Integer, L1Item> k;
   private final HashMap<Integer, L1Item> l = this.f();
   private static HashMap<Integer, Integer> m = new HashMap<>();

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
      ItemTable.c.put("none", -1);
      ItemTable.c.put("sword", 1);
      ItemTable.c.put("dagger", 7);
      ItemTable.c.put("tohandsword", 257);
      ItemTable.c.put("bow", 259);
      ItemTable.c.put("spear", 261);
      ItemTable.c.put("axe", 2);
      ItemTable.c.put("staff", 6);
      ItemTable.c.put("gauntlet", 9);
      ItemTable.c.put("claw", 264);
      ItemTable.c.put("edoryu", 260);
      ItemTable.c.put("singlebow", 3);
      ItemTable.c.put("singlespear", 5);
      ItemTable.c.put("tohandaxe", 258);
      ItemTable.c.put("tohandstaff", 262);
      ItemTable.c.put("kiringku", 8);
      ItemTable.c.put("chainsword", 266);
      ItemTable.c.put("tohandkiringku", 264);
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
      int[] var0 = new int[]{
         2,
         3,
         6,
         67,
         73,
         77,
         78,
         91,
         92,
         93,
         104,
         109,
         111,
         114,
         123,
         128,
         137,
         138,
         141,
         155,
         167,
         229,
         232,
         233,
         235,
         260,
         273,
         274,
         275,
         280,
         281,
         289,
         291,
         293,
         365,
         366,
         367,
         368,
         369,
         385,
         386,
         547,
         548,
         551,
         667,
         668,
         669,
         670,
         705,
         711,
         743,
         769,
         816,
         818,
         829,
         849,
         851,
         852,
         857,
         858,
         861,
         862,
         863,
         866,
         869,
         870,
         871,
         872,
         873,
         1076,
         1077,
         1078,
         1079,
         1080,
         1082,
         1083,
         1084,
         1085,
         1086,
         1087,
         1088,
         1089,
         1090,
         1282,
         1283,
         1285,
         1288,
         1289,
         1290,
         1291,
         1496,
         1497,
         1748,
         1755,
         1756,
         1757,
         1758,
         1759,
         1760,
         1761,
         1762,
         1763,
         1764,
         1765,
         1766,
         1767,
         1768,
         1769,
         1781,
         1782,
         1783,
         10280,
         10281,
         10282
      };
      int var1 = 0;

      for (int var2 : ResolventTable.a().b().keySet()) {
         if (var1 >= var0.length) {
            System.out.println("\r\nRefineryTable sequenceID is over range, check [CrystalInfo.tbl]\r\n");
            break;
         }

         m.put(var0[var1++], var2);
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

   public static ItemTable a() {
      if (h == null) {
         h = new ItemTable();
      }

      return h;
   }

   private ItemTable() {
      this.k = this.g();
      this.h();
   }

   private HashMap<Integer, L1Item> e() {
      HashMap var1 = new HashMap<>();
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;
      new L1Item();

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("select * from etcitem ORDER BY item_id ASC");
         var4 = var3.executeQuery();

         while (var4.next()) {
            L1Item var5 = new L1Item();
            var5.b(var4.getInt("item_id"));
            var5.a(var4.getString("name"));
            var5.b(var4.getString("unidentified_name_id"));
            var5.c(var4.getString("identified_name_id"));
            var5.ak(f.get(var4.getString("item_type")));
            var5.u(g.get(var4.getString("use_type")));
            var5.a(0);
            var5.c(ItemTable.e.get(var4.getString("material")));
            var5.d(var4.getInt("weight"));
            var5.e(var4.getInt("invgfx"));
            var5.f(var4.getInt("grdgfx"));
            var5.i(var4.getInt("itemdesc_id"));
            var5.g(var4.getInt("min_lvl"));
            var5.h(var4.getInt("max_lvl"));
            var5.j(var4.getInt("bless"));
            var5.a(!var4.getBoolean("cant_trade"));
            var5.b(var4.getBoolean("cant_delete"));
            var5.p(var4.getBoolean("can_seal"));
            var5.k(var4.getInt("dmg_small"));
            var5.l(var4.getInt("dmg_large"));
            var5.o(var4.getBoolean("stackable"));
            var5.ai(var4.getInt("max_charge_count"));
            var5.ad(var4.getInt("locx"));
            var5.ae(var4.getInt("locy"));
            var5.a(var4.getShort("mapid"));
            var5.af(var4.getInt("delay_id"));
            var5.ag(var4.getInt("delay_time"));
            var5.ah(var4.getInt("delay_effect"));
            var5.v(var4.getInt("value"));
            var5.c(var4.getBoolean("save_at_once"));
            var5.w(this.c(var5.g()));
            var1.put(new Integer(var5.g()), var5);
         }
      } catch (NullPointerException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return var1;
   }

   private HashMap<Integer, L1Item> f() {
      HashMap var1 = new HashMap<>();
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;
      new L1Item();

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("select * from weapon ORDER BY item_id ASC");
         var4 = var3.executeQuery();

         while (var4.next()) {
            L1Item var5 = new L1Item();
            var5.b(var4.getInt("item_id"));
            var5.a(var4.getString("name"));
            var5.b(var4.getString("unidentified_name_id"));
            var5.c(var4.getString("identified_name_id"));
            var5.ak(c.get(var4.getString("type")));
            var5.aj(d.get(var4.getString("type")));
            var5.a(1);
            var5.u(g.get("weapon"));
            var5.c(ItemTable.e.get(var4.getString("material")));
            var5.d(var4.getInt("weight"));
            var5.e(var4.getInt("invgfx"));
            var5.f(var4.getInt("grdgfx"));
            var5.i(var4.getInt("itemdesc_id"));
            var5.k(var4.getInt("dmg_small"));
            var5.l(var4.getInt("dmg_large"));
            var5.aa(var4.getInt("range"));
            var5.m(var4.getInt("safenchant"));
            var5.d(var4.getBoolean("use_royal"));
            var5.e(var4.getBoolean("use_knight"));
            var5.f(var4.getBoolean("use_elf"));
            var5.g(var4.getBoolean("use_mage"));
            var5.h(var4.getBoolean("use_darkelf"));
            var5.i(var4.getBoolean("use_dragonknight"));
            var5.j(var4.getBoolean("use_illusionist"));
            var5.k(var4.getBoolean("use_warrior"));
            if (var5.aO() != 20 && var5.aO() != 62) {
               var5.A(var4.getInt("hitmodifier"));
               var5.C(var4.getInt("dmgmodifier"));
            } else {
               var5.B(var4.getInt("hitmodifier"));
               var5.D(var4.getInt("dmgmodifier"));
            }

            var5.a(var4.getByte("add_str"));
            var5.b(var4.getByte("add_dex"));
            var5.c(var4.getByte("add_con"));
            var5.d(var4.getByte("add_int"));
            var5.e(var4.getByte("add_wis"));
            var5.f(var4.getByte("add_cha"));
            var5.n(var4.getInt("add_hp"));
            var5.o(var4.getInt("add_mp"));
            var5.p(var4.getInt("add_hpr"));
            var5.q(var4.getInt("add_mpr"));
            var5.r(var4.getInt("add_sp"));
            var5.s(var4.getInt("m_def"));
            var5.ab(var4.getInt("double_dmg_chance"));
            var5.ac(var4.getInt("magicdmgmodifier"));
            var5.n(var4.getBoolean("canbedmg"));
            var5.g(var4.getInt("min_lvl"));
            var5.h(var4.getInt("max_lvl"));
            var5.j(var4.getInt("bless"));
            var5.a(!var4.getBoolean("cant_trade"));
            var5.b(var4.getBoolean("cant_delete"));
            var5.l(var4.getBoolean("haste_item"));
            var5.t(var4.getInt("max_use_time"));
            var5.V(var4.getInt("antiDamageReduction"));
            var5.X(var4.getInt("stunLevel"));
            var5.w(this.c(var5.g()));
            var1.put(new Integer(var5.g()), var5);
         }
      } catch (NullPointerException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return var1;
   }

   private HashMap<Integer, L1Item> g() {
      HashMap var1 = new HashMap<>();
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;
      new L1Item();

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("select * from armor ORDER BY item_id ASC");
         var4 = var3.executeQuery();

         while (var4.next()) {
            L1Item var5 = new L1Item();
            var5.b(var4.getInt("item_id"));
            var5.a(var4.getString("name"));
            var5.b(var4.getString("unidentified_name_id"));
            var5.c(var4.getString("identified_name_id"));
            var5.ak(b.get(var4.getString("type")));
            var5.a(2);
            switch (var5.aP()) {
               case 1:
                  var5.u(g.get("armor_Helm"));
                  break;
               case 2:
               case 17:
               case 19:
               case 20:
               case 21:
               case 22:
               case 24:
               case 25:
               case 26:
               case 27:
               case 28:
               default:
                  var5.u(g.get("armor"));
                  break;
               case 3:
                  var5.u(g.get("armor_T"));
                  break;
               case 4:
                  var5.u(g.get("armor_Cloak"));
                  break;
               case 5:
                  var5.u(g.get("armor_Greaves"));
                  break;
               case 6:
                  var5.u(g.get("armor_Boots"));
                  break;
               case 7:
                  var5.u(g.get("armor_Glove"));
                  break;
               case 8:
               case 10:
                  var5.u(g.get("armor_Shield"));
                  break;
               case 9:
                  var5.u(g.get("armor_Ring"));
                  break;
               case 11:
                  var5.u(g.get("armor_Amulet"));
                  break;
               case 12:
                  var5.u(g.get("armor_Belt"));
                  break;
               case 13:
                  var5.u(g.get("armor_Earring"));
                  break;
               case 14:
               case 15:
               case 16:
               case 18:
               case 23:
                  var5.u(g.get("armor_Rune"));
                  break;
               case 29:
                  var5.u(g.get("armor_Shoulder"));
                  break;
               case 30:
                  var5.u(g.get("armor_Badge"));
            }

            var5.c(ItemTable.e.get(var4.getString("material")));
            var5.d(var4.getInt("weight"));
            var5.e(var4.getInt("invgfx"));
            var5.f(var4.getInt("grdgfx"));
            var5.i(var4.getInt("itemdesc_id"));
            var5.x(var4.getInt("ac"));
            var5.m(var4.getInt("safenchant"));
            var5.d(var4.getBoolean("use_royal"));
            var5.e(var4.getBoolean("use_knight"));
            var5.f(var4.getBoolean("use_elf"));
            var5.g(var4.getBoolean("use_mage"));
            var5.h(var4.getBoolean("use_darkelf"));
            var5.i(var4.getBoolean("use_dragonknight"));
            var5.j(var4.getBoolean("use_illusionist"));
            var5.k(var4.getBoolean("use_warrior"));
            var5.a(var4.getByte("add_str"));
            var5.c(var4.getByte("add_con"));
            var5.b(var4.getByte("add_dex"));
            var5.d(var4.getByte("add_int"));
            var5.e(var4.getByte("add_wis"));
            var5.f(var4.getByte("add_cha"));
            var5.n(var4.getInt("add_hp"));
            var5.o(var4.getInt("add_mp"));
            var5.p(var4.getInt("add_hpr"));
            var5.q(var4.getInt("add_mpr"));
            var5.r(var4.getInt("add_sp"));
            var5.g(var4.getInt("min_lvl"));
            var5.h(var4.getInt("max_lvl"));
            var5.s(var4.getInt("m_def"));
            var5.y(var4.getInt("damage_reduction"));
            var5.z(var4.getInt("weight_reduction"));
            var5.A(var4.getInt("hit_modifier"));
            var5.C(var4.getInt("dmg_modifier"));
            var5.B(var4.getInt("bow_hit_modifier"));
            var5.D(var4.getInt("bow_dmg_modifier"));
            var5.l(var4.getBoolean("haste_item"));
            var5.j(var4.getInt("bless"));
            var5.a(!var4.getBoolean("cant_trade"));
            var5.b(var4.getBoolean("cant_delete"));
            var5.H(var4.getInt("defense_earth"));
            var5.E(var4.getInt("defense_water"));
            var5.F(var4.getInt("defense_wind"));
            var5.G(var4.getInt("defense_fire"));
            var5.I(var4.getInt("regist_stun"));
            var5.J(var4.getInt("regist_stone"));
            var5.K(var4.getInt("regist_sleep"));
            var5.L(var4.getInt("regist_freeze"));
            var5.M(var4.getInt("regist_sustain"));
            var5.N(var4.getInt("regist_blind"));
            var5.O(var4.getInt("regist_fear"));
            var5.t(var4.getInt("max_use_time"));
            var5.P(var4.getInt("grade"));
            var5.Q(var4.getInt("pvpDamage"));
            var5.R(var4.getInt("pvpDamageReduction"));
            var5.S(var4.getInt("magicHit"));
            var5.T(var4.getInt("magicCritical"));
            var5.U(var4.getInt("healPotionRegeneration"));
            var5.V(var4.getInt("antiDamageReduction"));
            var5.W(var4.getInt("exp"));
            var5.m(var4.getBoolean("antiPoison"));
            var5.X(var4.getInt("stunLevel"));
            var5.Y(var4.getInt("fearLevel"));
            var5.Z(var4.getInt("breakLevel"));
            var5.w(this.c(var5.g()));
            var1.put(new Integer(var5.g()), var5);
         }
      } catch (NullPointerException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return var1;
   }

   private void h() {
      int var1 = 0;

      for (L1Item var3 : this.j.values()) {
         if (var3.g() > var1) {
            var1 = var3.g();
         }
      }

      for (L1Item var9 : this.l.values()) {
         if (var9.g() > var1) {
            var1 = var9.g();
         }
      }

      for (L1Item var11 : this.k.values()) {
         if (var11.g() > var1) {
            var1 = var11.g();
         }
      }

      this.i = new L1Item[var1 + 1];

      for (Integer var12 : this.j.keySet()) {
         L1Item var7 = this.j.get(var12);
         this.i[var12] = var7;
      }

      for (Integer var13 : this.l.keySet()) {
         L1Item var18 = this.l.get(var13);
         this.i[var13] = var18;
      }

      for (Integer var14 : this.k.keySet()) {
         L1Item var19 = this.k.get(var14);
         this.i[var14] = var19;
      }
   }

   public L1Item a(int var1) {
      return this.i[var1];
   }

   public L1ItemInstance b(int var1) {
      L1Item var2 = this.a(var1);
      if (var2 == null) {
         return null;
      }

      L1ItemInstance var3 = new L1ItemInstance(var2, 1);
      var3.cF(IdFactory.a().d());
      if (var1 == 21446) {
         this.a(var3);
      }

      L1World.a().a(var3);
      return var3;
   }

   private void a(L1ItemInstance var1) {
      int[] var2 = new int[]{1, 2, 4, 8, 16, 32, 131072, 64, 128, 524288, 256, 512, 1024, 16384, 32768, 65536, 262144};
      int[] var3 = new int[]{2048, 4096, 8192, 1048576};

      for (int var4 = 0; var4 < 4; var4++) {
         int var5 = 0;
         var5 |= var1.X();
         var5 |= var1.Y();
         var5 |= var1.Z();
         var5 |= var1.aa();
         int var6 = 0;

         while (var6 == 0) {
            int var7 = Random.a(var2.length);
            if ((var5 & var2[var7]) != var2[var7]) {
               var6 = var2[var7];
               if (var4 == 0) {
                  var1.l(var6);
               } else if (var4 == 1) {
                  var1.m(var6);
               } else if (var4 == 2) {
                  var1.n(var6);
               } else if (var4 == 3) {
                  var1.o(var6);
               }
            }
         }

         int var12 = Random.a(var3.length);
         if (var4 == 0) {
            var1.l(var1.X() | var3[var12]);
         } else if (var4 == 1) {
            var1.m(var1.Y() | var3[var12]);
         } else if (var4 == 2) {
            var1.n(var1.Z() | var3[var12]);
         } else if (var4 == 3) {
            var1.o(var1.aa() | var3[var12]);
         }
      }
   }

   public int a(String var1) {
      int var2 = 0;
      L1Item[] var6 = this.i;
      int var5 = this.i.length;

      for (int var4 = 0; var4 < var5; var4++) {
         L1Item var3 = var6[var4];
         if (var3 != null && var3.h().equals(var1)) {
            var2 = var3.g();
            break;
         }
      }

      return var2;
   }

   public int b(String var1) {
      int var2 = 0;
      L1Item[] var6 = this.i;
      int var5 = this.i.length;

      for (int var4 = 0; var4 < var5; var4++) {
         L1Item var3 = var6[var4];
         if (var3 != null && var3.h().replace(" ", "").equals(var1.replace(" ", ""))) {
            var2 = var3.g();
            break;
         }
      }

      return var2;
   }

   private int c(int var1) {
      for (int var2 : m.keySet()) {
         if (m.get(var2) == var1) {
            return var2;
         }
      }

      for (int var4 = 0; var4 < 20000; var4++) {
         if (!m.containsKey(var4)) {
            m.put(var4, var1);
            return var4;
         }
      }

      System.out.println("ItemTable sequenceID is over range");
      return var1;
   }

   public static L1ItemInstance a(L1Character var0, int var1, int var2) {
      return a(var0, var1, var2, 0, null, false, true, 0, 1, true);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2) {
      return a(var0, var1, var2, 0, null, false, false, 0, 1, true);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2, boolean var3) {
      return a(var0, var1, var2, 0, null, var3, false, 0, 1, true);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2, int var3, int var4, boolean var5) {
      return a(var0, var1, var2, var3, null, var5, false, 0, var4, true);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2, int var3, int var4, boolean var5, int var6) {
      return a(var0, var1, var2, var3, null, var5, false, var6, var4, true);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2, int var3) {
      return a(var0, var1, var2, var3, null, false, false, 0, 1, true);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2, int var3, boolean var4) {
      return a(var0, var1, var2, var3, null, false, false, 0, 1, var4);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2, int var3, boolean var4, boolean var5) {
      return a(var0, var1, var2, var3, null, var4, false, 0, 1, var5);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2, int var3, int var4) {
      return a(var0, var1, var2, var3, null, false, false, var4, 1, true);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2, String var3) {
      return a(var0, var1, var2, 0, var3, false, false, 0, 1, true);
   }

   public static L1ItemInstance a(L1PcInstance var0, int var1, int var2, int var3, String var4) {
      return a(var0, var1, var2, var3, var4, false, false, 0, 1, true);
   }

   private static L1ItemInstance a(L1Character var0, int var1, int var2, int var3, String var4, boolean var5, boolean var6, int var7, int var8, boolean var9) {
      L1ItemInstance var10 = a().b(var1);
      if (var10 == null) {
         a.log(Level.SEVERE, "ItemTable createNewItem item_id= [" + var1 + "] is null");
         return null;
      }

      if (var10.d()) {
         var10.e(var2);
         var10.a(var3);
         var10.a(var5);
         if (var8 != 1) {
            var10.f(var8);
         }

         if (var6) {
            L1World.a().a(var0.fs(), var0.ft(), var0.fp()).d(var10);
         } else if (var0 instanceof L1PcInstance) {
            L1PcInstance var11 = (L1PcInstance)var0;
            if (var11.j().a(var10, var2) == 0) {
               var11.j().d(var10);
            } else {
               L1World.a().a(var11.fs(), var11.ft(), var11.fp()).d(var10);
            }
         }
      } else {
         for (int var15 = 0; var15 < var2; var15++) {
            L1ItemInstance var12;
            if (var15 == 0) {
               var12 = var10;
            } else {
               var12 = a().b(var1);
            }

            if (var3 == -1) {
               int var13 = 0;
               int var14 = Random.a(100) + 1;
               if (var14 <= 15) {
                  var13 = -2;
               } else if (var14 >= 16 && var14 <= 30) {
                  var13 = -1;
               } else if (var14 >= 31 && var14 <= 70) {
                  var13 = 0;
               } else if (var14 >= 71 && var14 <= 87) {
                  var13 = Random.a(2) + 1;
               } else if (var14 >= 88 && var14 <= 97) {
                  var13 = Random.a(3) + 3;
               } else if (var14 >= 98 && var14 <= 99) {
                  var13 = 6;
               } else if (var14 == 100) {
                  var13 = 7;
               }

               var12.a(var13);
            } else {
               var12.a(var3);
            }

            var12.a(var5);
            if (var8 != 1) {
               var12.f(var8);
            }

            var12.n();
            if (var12.N() == 413) {
               var12.b(new Timestamp(System.currentTimeMillis() + 3600000L));
            }

            if (var7 > 0) {
               Timestamp var17 = new Timestamp(System.currentTimeMillis() + var7 * 24 * 60 * 60 * 1000L);
               var12.b(var17);
            }

            if (var6) {
               L1World.a().a(var0.fs(), var0.ft(), var0.fp()).d(var12);
            } else if (var0 instanceof L1PcInstance) {
               L1PcInstance var18 = (L1PcInstance)var0;
               if (var18.j().a(var12, var2) == 0) {
                  var18.j().d(var12);
               } else {
                  L1World.a().a(var18.fs(), var18.ft(), var18.fp()).d(var12);
               }
            }
         }
      }

      if (var0 instanceof L1PcInstance && var9) {
         L1PcInstance var16 = (L1PcInstance)var0;
         if (var4 != null) {
            var16.a(new S_ServerMessage(143, var4, var10.s()));
         } else {
            var16.a(new S_ServerMessage(403, var10.s()));
         }
      }

      return var10;
   }

   public HashMap<Integer, L1Item> b() {
      return this.j;
   }

   public HashMap<Integer, L1Item> c() {
      return this.k;
   }

   public HashMap<Integer, L1Item> d() {
      return this.l;
   }
}
