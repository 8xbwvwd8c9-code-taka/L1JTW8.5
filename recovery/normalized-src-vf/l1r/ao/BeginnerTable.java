package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1PcInstance;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class BeginnerTable {
   private static final Logger a = Logger.getLogger(BeginnerTable.class.getName());
   private static BeginnerTable b;
   private final ArrayList<BeginnerTable.a> c = new ArrayList<>();

   public static BeginnerTable a() {
      if (b == null) {
         b = new BeginnerTable();
      }

      return b;
   }

   private BeginnerTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM beginner");
         var3 = var2.executeQuery();

         while (var3.next()) {
            BeginnerTable.a var4 = new BeginnerTable.a(null);
            var4.a = var3.getInt("item_id");
            var4.b = var3.getInt("count");
            var4.c = var3.getInt("charge_count");
            var4.d = var3.getInt("enchantlvl");
            var4.e = var3.getString("item_name");
            var4.f = var3.getString("activate");
            var4.g = var3.getInt("bless");
            this.c.add(var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();

         for (BeginnerTable.a var4 : this.c) {
            if (var4.f.contains(var1.aC().h()) || var4.f.contains("A")) {
               var3 = var2.prepareStatement(
                  "INSERT INTO character_items SET id=?, item_id=?, char_id=?, item_name=?, count=?, is_equipped=?, enchantlvl=?, is_id=?, durability=?, charge_count=?, temp_value=?, last_used=?, bless=?"
               );
               var3.setInt(1, IdFactory.a().d());
               var3.setInt(2, var4.a);
               var3.setInt(3, var1.fr());
               var3.setString(4, var4.e);
               var3.setInt(5, var4.b);
               var3.setInt(6, 0);
               var3.setInt(7, var4.d);
               var3.setInt(8, 0);
               var3.setInt(9, 0);
               var3.setInt(10, var4.c);
               var3.setInt(11, 0);
               var3.setTimestamp(12, null);
               var3.setInt(13, var4.g);
               var3.execute();
            }
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3);
      }
   }

   private class a {
      public int a;
      public int b = 1;
      public int c = 0;
      public int d = 0;
      public String e = "";
      public String f = "";
      public int g = 1;

      private a() {
      }

      // $VF: synthetic method
      a(BeginnerTable.a var2) {
         this();
      }
   }
}
