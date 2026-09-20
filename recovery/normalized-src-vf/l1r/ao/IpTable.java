package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class IpTable {
   private static final Logger a = Logger.getLogger(IpTable.class.getName());
   private static CopyOnWriteArrayList<String> b;
   private static boolean c;
   private static IpTable d;

   public static IpTable a() {
      if (d == null) {
         d = new IpTable();
      }

      return d;
   }

   private IpTable() {
      if (!c) {
         b = new CopyOnWriteArrayList<>();
         this.b();
      }
   }

   public void a(String var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO ban_ip SET ip=?");
         var3.setString(1, var1);
         var3.execute();
         b.add(var1);
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public boolean b(String var1) {
      for (String var2 : b) {
         if (var2.endsWith("*")) {
            int var4 = var2.indexOf("*");
            String var5 = var2.substring(0, var4);
            String var6 = var1.substring(0, var4);
            if (var6.equalsIgnoreCase(var5)) {
               return true;
            }
         } else if (var1.equalsIgnoreCase(var2)) {
            return true;
         }
      }

      return false;
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM ban_ip");
         var3 = var2.executeQuery();

         while (var3.next()) {
            b.add(var3.getString(1));
         }

         c = true;
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public boolean c(String var1) {
      boolean var2 = false;
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("DELETE FROM ban_ip WHERE ip=?");
         var4.setString(1, var1);
         var4.execute();
         var2 = b.remove(var1);
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }

      return var2;
   }
}
