package l1r.bh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.be.S_Bookmarks;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1BookMark {
   private static final Logger c = Logger.getLogger(L1BookMark.class.getName());
   private int d;
   private int e;
   private String f;
   private int g;
   private int h;
   private int i;
   private int j;
   private int k;
   public static HashMap<String, L1Location> a = new HashMap<>();
   public static HashMap<String, L1Location> b = new HashMap<>();

   static {
      a.put("[神秘]冰湖水中央", new L1Location(34002, 32329, 4));
      a.put("[神秘]冰之外圍", new L1Location(33994, 32332, 4));
      a.put("[神祕]冰之絕壁", new L1Location(34212, 32343, 4));
      a.put("[神秘]亞丁小船", new L1Location(34194, 33135, 4));
      a.put("[神祕]海音警備塔", new L1Location(33503, 33505, 4));
      a.put("[神秘]龍之谷絕壁", new L1Location(33466, 32312, 4));
      a.put("[神秘]隱藏之龜", new L1Location(33343, 33163, 4));
      a.put("[神祕]精神與時間", new L1Location(32555, 32979, 4));
      b.put("沙漠綠洲", new L1Location(32864, 33252, 4));
      b.put("邪惡神殿", new L1Location(32885, 32652, 4));
      b.put("正義神殿", new L1Location(33118, 32936, 4));
      b.put("說話之島寄養處", new L1Location(32445, 32991, 0));
      b.put("說話之島─吉倫", new L1Location(32559, 33082, 0));
      b.put("奇岩", new L1Location(33430, 32815, 4));
      b.put("奇岩─地間", new L1Location(33305, 33063, 4));
      b.put("奇岩─龍一洞", new L1Location(33413, 32411, 4));
      b.put("奇岩─龍一觀戰區", new L1Location(33400, 32420, 4));
      b.put("奇岩─龍二洞", new L1Location(33365, 32386, 4));
      b.put("奇岩─龍三洞", new L1Location(33396, 32326, 4));
      b.put("奇岩─龍三黑老區", new L1Location(33387, 32347, 4));
      b.put("奇岩─龍四", new L1Location(33348, 32349, 4));
      b.put("奇岩─龍四左下", new L1Location(33312, 32357, 4));
      b.put("奇岩─龍骨頭", new L1Location(33281, 32400, 4));
      b.put("亞丁─下水道", new L1Location(34149, 33382, 4));
      b.put("亞丁─傲慢橋", new L1Location(34273, 33093, 4));
      b.put("亞丁─道具店", new L1Location(34124, 33146, 4));
      b.put("亞丁─夢幻島", new L1Location(33977, 32925, 4));
      b.put("亞丁─教堂", new L1Location(33958, 33365, 4));
      b.put("海音", new L1Location(33604, 33234, 4));
      b.put("海音道具店", new L1Location(33063, 32736, 4));
      b.put("海音─地間", new L1Location(33621, 33507, 4));
      b.put("海音─遺忘傳師", new L1Location(33445, 33476, 4));
      b.put("海音─變怪區", new L1Location(33781, 33275, 4));
      b.put("燃柳", new L1Location(32746, 32441, 4));
      b.put("燃柳─妖魔城堡", new L1Location(32940, 32281, 4));
      b.put("燃柳─邪惡神殿", new L1Location(32662, 32303, 4));
      b.put("燃柳─眠洞", new L1Location(32938, 32284, 4));
      b.put("話島", new L1Location(32576, 32945, 0));
      b.put("話島─冒洞", new L1Location(32509, 32864, 0));
      b.put("肯特", new L1Location(33072, 32799, 4));
      b.put("肯特─葡萄園", new L1Location(32876, 32797, 4));
      b.put("肯特─正義神殿", new L1Location(33118, 32936, 4));
      b.put("風木", new L1Location(32611, 33185, 4));
      b.put("風木─蟻后洞", new L1Location(32795, 33192, 4));
      b.put("風木─拉洞", new L1Location(32564, 33460, 4));
      b.put("風木─慾望洞穴", new L1Location(32760, 33456, 4));
      b.put("騎村", new L1Location(33072, 33385, 4));
      b.put("騎村─騎一洞", new L1Location(32970, 33511, 4));
      b.put("騎村─騎三洞", new L1Location(32883, 33511, 4));
      b.put("威頓", new L1Location(33724, 32488, 4));
      b.put("歐瑞", new L1Location(34057, 32285, 4));
      b.put("歐瑞─象牙塔", new L1Location(34041, 32155, 4));
      b.put("歐瑞─水晶洞二樓口", new L1Location(33969, 32337, 4));
      b.put("歐瑞─影子神殿傳送點", new L1Location(34268, 32193, 4));
      b.put("古丁", new L1Location(32610, 32766, 4));
      b.put("古丁─地間", new L1Location(32741, 32928, 4));
      b.put("古丁─肯特橋", new L1Location(32000, 32780, 4));
      b.put("古丁─燃柳橋", new L1Location(32654, 32524, 4));
   }

   public static boolean a(int var0, int var1, int var2) {
      if (var2 == 4) {
         if (var0 >= 33506 && var0 <= 33714 && var1 >= 32217 && var1 <= 32478) {
            return false;
         }

         if (var0 >= 33714 && var0 <= 33819 && var1 >= 32202 && var1 <= 32464) {
            return false;
         }

         if (var0 >= 33446 && var0 <= 33475 && var1 >= 32315 && var1 <= 32354) {
            return false;
         }

         if (var0 >= 33389 && var0 <= 33396 && var1 >= 32340 && var1 <= 32348) {
            return false;
         }

         if (var0 >= 33259 && var0 <= 33266 && var1 >= 32399 && var1 <= 32406) {
            return false;
         }

         if (var0 >= 33332 && var0 <= 33340 && var1 >= 32433 && var1 <= 32440) {
            return false;
         }

         if (var0 >= 34171 && var0 <= 34303 && var1 >= 33086 && var1 <= 33530) {
            return false;
         }

         if (var0 >= 32704 && var0 <= 32837 && var1 >= 33102 && var1 <= 33240) {
            return false;
         }
      }

      return true;
   }

   public static void a(CopyOnWriteArrayList<L1BookMark> var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("UPDATE  character_teleport  SET order_id =?,order_id_fast =?,name =? WHERE id=?");

         for (L1BookMark var3 : var0) {
            var2.setInt(1, var3.i);
            var2.setInt(2, var3.j);
            var2.setString(3, var3.f);
            var2.setInt(4, var3.e);
            var2.execute();
         }
      } catch (SQLException var8) {
         c.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public static void a(L1PcInstance var0, String var1) {
      L1BookMark var2 = var0.a(var1);
      if (var2 != null) {
         Connection var3 = null;
         PreparedStatement var4 = null;

         try {
            var3 = DatabaseFactory.a().b();
            var4 = var3.prepareStatement("DELETE FROM character_teleport WHERE id=?");
            var4.setInt(1, var2.a());
            var4.execute();
            int var5 = var2.f();
            var0.ba().remove(var2);

            for (L1BookMark var6 : var0.ba()) {
               if (var6.f() > var5) {
                  var6.e(var6.f() - 1);
               }
            }

            a(var0.ba());
         } catch (SQLException var11) {
            c.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
         } finally {
            SQLUtil.a(var4);
            SQLUtil.a(var3);
         }
      }
   }

   public static void b(L1PcInstance var0, String var1) {
      if (!var0.fq().h()) {
         var0.a(new S_ServerMessage(214));
      } else {
         int var2 = var0.ba().size();
         if (var2 <= 60) {
            if (var0.a(var1) != null) {
               var0.a(new S_ServerMessage(327));
            }

            L1BookMark var3 = new L1BookMark();
            var3.a(IdFactory.a().d());
            var3.b(var0.fr());
            var3.a(var1);
            var3.c(var0.fs());
            var3.d(var0.ft());
            var3.g(var0.fp());
            var3.e(var0.ba().size());
            Connection var4 = null;
            PreparedStatement var5 = null;

            try {
               var4 = DatabaseFactory.a().b();
               var5 = var4.prepareStatement("INSERT INTO character_teleport SET id = ?, char_id = ?, name = ?, locx = ?, locy = ?, mapid = ?,order_id=?");
               var5.setInt(1, var3.a());
               var5.setInt(2, var3.b());
               var5.setString(3, var3.c());
               var5.setInt(4, var3.d());
               var5.setInt(5, var3.e());
               var5.setInt(6, var3.h());
               var5.setInt(7, var3.f());
               var5.execute();
            } catch (SQLException var10) {
               c.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
            } finally {
               SQLUtil.a(var5);
               SQLUtil.a(var4);
            }

            var0.ba().add(var3);
            var0.a(new S_Bookmarks(var1, var3.h(), var3.a(), var3.d(), var3.e()));
         }
      }
   }

   public static void a(L1PcInstance var0, HashMap<String, L1Location> var1) {
      for (String var2 : var1.keySet()) {
         int var4 = var1.get(var2).f();
         int var5 = var1.get(var2).g();
         short var6 = (short)var1.get(var2).b();
         L1BookMark var7 = new L1BookMark();
         var7.a(IdFactory.a().d());
         var7.b(var0.fr());
         var7.a(var2);
         var7.c(var4);
         var7.d(var5);
         var7.g(var6);
         var7.e(var0.ba().size());
         Connection var8 = null;
         PreparedStatement var9 = null;

         try {
            var8 = DatabaseFactory.a().b();
            var9 = var8.prepareStatement("INSERT INTO character_teleport SET id = ?, char_id = ?, name = ?, locx = ?, locy = ?, mapid = ?,order_id=?");
            var9.setInt(1, var7.a());
            var9.setInt(2, var7.b());
            var9.setString(3, var7.c());
            var9.setInt(4, var7.d());
            var9.setInt(5, var7.e());
            var9.setInt(6, var7.h());
            var9.setInt(7, var7.f());
            var9.execute();
         } catch (SQLException var14) {
            c.log(Level.SEVERE, var14.getLocalizedMessage(), var14);
         } finally {
            SQLUtil.a(var9);
            SQLUtil.a(var8);
         }

         var0.ba().add(var7);
         var0.a(new S_Bookmarks(var2, var6, var7.a(), var4, var5));
      }
   }

   public static void a(L1PcInstance var0, L1ItemInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      for (L1BookMark var4 : var0.ba()) {
         try {
            var2 = DatabaseFactory.a().b();
            var3 = var2.prepareStatement("INSERT INTO character_teleport SET id = ?, char_id = ?, name = ?, locx = ?, locy = ?, mapid = ?,order_id=?");
            var3.setInt(1, IdFactory.a().d());
            var3.setInt(2, var1.fr());
            var3.setString(3, var4.c());
            var3.setInt(4, var4.d());
            var3.setInt(5, var4.e());
            var3.setInt(6, var4.h());
            var3.setInt(7, var4.f());
            var3.execute();
         } catch (SQLException var10) {
            c.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         } finally {
            SQLUtil.a(var3);
            SQLUtil.a(var2);
         }
      }
   }

   public static void a(L1ItemInstance var0, L1PcInstance var1) {
      HashMap var2 = new HashMap<>();
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT * FROM character_teleport WHERE char_id=? ORDER BY order_id");
         var4.setInt(1, var0.fr());
         var5 = var4.executeQuery();

         while (var5.next()) {
            String var6 = var5.getString("name");
            int var7 = var5.getInt("locx");
            int var8 = var5.getInt("locy");
            int var9 = var5.getShort("mapid");
            var2.put(var6, new L1Location(var7, var8, var9));
         }

         if (var2.size() <= 0) {
            var1.a(new S_ServerMessage(2963));
            return;
         }

         int var11 = var1.cI();
         if (var11 - var1.ba().size() < var2.size()) {
            int var12 = var2.size() - (var11 - var1.ba().size());
            var1.a(new S_ServerMessage(2961, "" + var12));
            return;
         }

         a(var1, var2);
         var4 = var3.prepareStatement("DELETE FROM character_teleport WHERE char_id=?");
         var4.setInt(1, var0.fr());
         var4.execute();
         var1.j().f(var0);
      } catch (SQLException var10) {
         c.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      }

      SQLUtil.a(var5, var4, var3);
   }

   public static void a(L1PcInstance var0) {
      var0.an();
      var0.a(new S_PacketBox(141, var0.cI()));
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("UPDATE  characters  SET BookMarkSpace =? WHERE objid=?");
         var2.setInt(1, var0.cI());
         var2.setInt(2, var0.fr());
         var2.execute();
      } catch (SQLException var7) {
         c.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public int a() {
      return this.e;
   }

   public void a(int var1) {
      this.e = var1;
   }

   public int b() {
      return this.d;
   }

   public void b(int var1) {
      this.d = var1;
   }

   public String c() {
      return this.f;
   }

   public void a(String var1) {
      this.f = var1;
   }

   public int d() {
      return this.g;
   }

   public void c(int var1) {
      this.g = var1;
   }

   public int e() {
      return this.h;
   }

   public void d(int var1) {
      this.h = var1;
   }

   public int f() {
      return this.i;
   }

   public void e(int var1) {
      this.i = var1;
   }

   public int g() {
      return this.j;
   }

   public void f(int var1) {
      this.j = var1;
   }

   public int h() {
      return this.k;
   }

   public void g(int var1) {
      this.k = var1;
   }
}
