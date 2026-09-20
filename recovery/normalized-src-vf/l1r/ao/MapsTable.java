package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public final class MapsTable {
   private static final Logger a = Logger.getLogger(MapsTable.class.getName());
   private static MapsTable b;
   private final HashMap<Integer, L1Map> c = new HashMap<>();

   public void a(L1Map var1) {
      this.c.put(var1.a, var1);
   }

   private MapsTable() {
      this.b();

      for (int var1 : L1WorldMap.b().a().keySet()) {
         this.c.containsKey(var1);
      }
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM mapids");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("mapid");
            L1Map var5 = L1WorldMap.b().a(var4);
            if (var5 != null) {
               L1Map var6 = var5;
               var6.f = var3.getDouble("monster_amount");
               var6.g = var3.getDouble("drop_rate");
               var6.h = var3.getDouble("exp_rate");
               var6.i = var3.getBoolean("underwater");
               var6.j = var3.getBoolean("markable");
               var6.k = var3.getBoolean("teleportable");
               var6.l = var3.getBoolean("escapable");
               var6.m = var3.getBoolean("resurrection");
               var6.n = var3.getBoolean("painwand");
               var6.o = var3.getBoolean("penalty");
               var6.p = var3.getBoolean("take_pets");
               var6.q = var3.getBoolean("recall_pets");
               var6.r = var3.getBoolean("usable_item");
               var6.s = var3.getBoolean("usable_skill");
               var6.t = var3.getBoolean("polyable");
               this.c.put(new Integer(var4), var6);
            } else {
               System.out.println("DB->mapids 地圖編號: " + var4 + " 沒有地圖檔(.txt)");
            }
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public static MapsTable a() {
      if (b == null) {
         b = new MapsTable();
      }

      return b;
   }

   public double a(int var1) {
      L1Map var2 = this.c.get(var1);
      return var2 == null ? 0.0 : var2.f;
   }

   public double b(int var1) {
      L1Map var2 = this.c.get(var1);
      return var2 == null ? 0.0 : var2.g;
   }
}
