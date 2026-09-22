package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bh.L1House;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class HouseTable {
   private static final Logger a = Logger.getLogger(HouseTable.class.getName());
   private static HouseTable b;
   private final HashMap<Integer, L1House> c = new HashMap<>();

   public static HouseTable a() {
      if (b == null) {
         b = new HouseTable();
      }

      return b;
   }

   public HouseTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM house ORDER BY house_id");
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1House var4 = new L1House();
            var4.a(var3.getInt("house_id"));
            var4.a(var3.getString("house_name"));
            var4.b(var3.getInt("house_area"));
            var4.b(var3.getString("location"));
            var4.c(var3.getInt("keeper_id"));
            var4.a(var3.getBoolean("is_on_sale"));
            var4.b(var3.getBoolean("is_purchase_basement"));
            var4.a(var3.getTimestamp("tax_deadline"));
            var4.b(var3.getTimestamp("deadline"));
            var4.d(var3.getInt("price"));
            var4.c(var3.getString("old_owner"));
            var4.e(var3.getInt("old_owner_id"));
            var4.d(var3.getString("bidder"));
            var4.f(var3.getInt("bidder_id"));
            this.c.put(var4.b(), var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public L1House a(int var1) {
      return this.c.get(var1);
   }

   public boolean a(L1House var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement(
            "UPDATE house SET house_name=?, house_area=?, location=?, keeper_id=?, is_on_sale=?, is_purchase_basement=?, tax_deadline=?,  deadline=?, price=?, old_owner=?, old_owner_id=?, bidder=?, bidder_id=? WHERE house_id=?"
         );
         var3.setString(1, var1.c());
         var3.setInt(2, var1.d());
         var3.setString(3, var1.e());
         var3.setInt(4, var1.f());
         var3.setBoolean(5, var1.g());
         var3.setBoolean(6, var1.h());
         var3.setTimestamp(7, var1.i());
         var3.setTimestamp(8, var1.j());
         var3.setInt(9, var1.k());
         var3.setString(10, var1.l());
         var3.setInt(11, var1.m());
         var3.setString(12, var1.n());
         var3.setInt(13, var1.o());
         var3.setInt(14, var1.b());
         var3.execute();
         return true;
      } catch (SQLException var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
         return false;
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public static List<Integer> b() {
      ArrayList var0 = new ArrayList<>();
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT house_id FROM house ORDER BY house_id");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("house_id");
            var0.add(var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }

      return var0;
   }

   public HashMap<Integer, L1House> c() {
      return this.c;
   }
}
