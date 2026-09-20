package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class ConfigTable {
   private static final Logger a = Logger.getLogger(ConfigTable.class.getName());
   private final ConcurrentHashMap<String, String> b = new ConcurrentHashMap<>();
   private static ConfigTable c;

   public String a(String var1, String var2) {
      if (this.b.containsKey(var1)) {
         return this.b.get(var1);
      }

      System.out.println(var1 + " has no setting in DB !! Use defaultValue= " + var2);
      return var2;
   }

   public static ConfigTable a() {
      if (c == null) {
         c = new ConfigTable();
      }

      return c;
   }

   private ConfigTable() {
      this.a("_config");
      this.a("_config_other");
      this.a("_config_world");
   }

   private void a(String var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM " + var1);
         var4 = var3.executeQuery();

         while (var4.next()) {
            String var5 = var4.getString("parameter");
            String var6 = var4.getString("value");
            if (this.b.containsKey(var5)) {
               System.out.println("[Errer]" + var1 + " has repeated parameter= " + var5);
            } else {
               this.b.put(var5, var6);
            }
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }
   }

   public ConcurrentHashMap<String, String> b() {
      return this.b;
   }
}
