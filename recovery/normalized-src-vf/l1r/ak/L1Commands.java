package l1r.ak;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bh.L1Command;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1Commands {
   private static final Logger a = Logger.getLogger(L1Commands.class.getName());

   private static L1Command a(ResultSet var0) throws SQLException {
      return new L1Command(var0.getString("name"), var0.getInt("access_level"), var0.getString("class_name"));
   }

   public static L1Command a(String var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM commands WHERE name=?");
         var2.setString(1, var0);
         var3 = var2.executeQuery();
         if (var3.next()) {
            return a(var3);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         return null;
      } finally {
         SQLUtil.a(var3, var2, var1);
      }

      return null;
   }

   public static List<L1Command> a(int var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;
      ArrayList var4 = new ArrayList<>();

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM commands WHERE access_level <= ?");
         var2.setInt(1, var0);
         var3 = var2.executeQuery();

         while (var3.next()) {
            var4.add(a(var3));
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }

      return var4;
   }
}
