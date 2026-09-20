package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.bj.ClientThread;
import l1r.l1j.server.DatabaseFactory;

public class AnnounceTable {
   private static final Logger a = Logger.getLogger(AnnounceTable.class.getName());
   private static AnnounceTable b;
   private static ArrayList<String> c = new ArrayList<>();

   public static AnnounceTable a() {
      if (b == null) {
         b = new AnnounceTable();
      }

      return b;
   }

   public void a(ClientThread var1) {
      var1.a(new CopyOnWriteArrayList<>(c));
   }

   private AnnounceTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM _announce_login ORDER BY id ASC");
         var3 = var2.executeQuery();

         while (var3.next()) {
            String var4 = var3.getString("text");
            c.add(var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(CopyOnWriteArrayList<String> var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM _announce_cycle ORDER BY id ASC");
         var4 = var3.executeQuery();
         var1.clear();

         while (var4.next()) {
            String var5 = var4.getString("text");
            var1.add(var5);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }
   }
}
