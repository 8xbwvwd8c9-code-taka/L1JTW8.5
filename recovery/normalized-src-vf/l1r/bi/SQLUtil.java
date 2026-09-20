package l1r.bi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUtil {
   public static SQLException a(Connection var0) {
      try {
         if (var0 != null) {
            var0.close();
         }

         return null;
      } catch (SQLException var2) {
         return var2;
      }
   }

   public static SQLException a(Statement var0) {
      try {
         if (var0 != null) {
            var0.close();
         }

         return null;
      } catch (SQLException var2) {
         return var2;
      }
   }

   public static SQLException a(ResultSet var0) {
      try {
         if (var0 != null) {
            var0.close();
         }

         return null;
      } catch (SQLException var2) {
         return var2;
      }
   }

   public static void a(ResultSet var0, Statement var1, Connection var2) {
      a(var0);
      a(var1);
      a(var2);
   }
}
