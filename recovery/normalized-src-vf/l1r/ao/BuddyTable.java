package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aq.L1Buddy;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class BuddyTable {
   private static final Logger a = Logger.getLogger(BuddyTable.class.getName());
   private static BuddyTable b;
   private final HashMap<Integer, L1Buddy> c = new HashMap<>();

   public static BuddyTable a() {
      if (b == null) {
         b = new BuddyTable();
      }

      return b;
   }

   private BuddyTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT distinct(char_id) as char_id FROM character_buddys");
         var3 = var2.executeQuery();

         while (var3.next()) {
            PreparedStatement var4 = null;
            ResultSet var5 = null;

            try {
               var4 = var1.prepareStatement("SELECT buddy_id, buddy_name FROM character_buddys WHERE char_id = ?");
               int var6 = var3.getInt("char_id");
               var4.setInt(1, var6);
               L1Buddy var7 = new L1Buddy(var6);
               var5 = var4.executeQuery();

               while (var5.next()) {
                  var7.a(var5.getInt("buddy_id"), var5.getString("buddy_name"));
               }

               this.c.put(var7.a(), var7);
            } catch (Exception var18) {
               a.log(Level.SEVERE, var18.getLocalizedMessage(), var18);
            } finally {
               SQLUtil.a(var5);
               SQLUtil.a(var4);
            }
         }
      } catch (SQLException var20) {
         a.log(Level.SEVERE, var20.getLocalizedMessage(), var20);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public L1Buddy a(int var1) {
      L1Buddy var2 = this.c.get(var1);
      if (var2 == null) {
         var2 = new L1Buddy(var1);
         this.c.put(var1, var2);
      }

      return var2;
   }

   public void a(int var1, int var2, String var3) {
      Connection var4 = null;
      PreparedStatement var5 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement("INSERT INTO character_buddys SET char_id=?, buddy_id=?, buddy_name=?");
         var5.setInt(1, var1);
         var5.setInt(2, var2);
         var5.setString(3, var3);
         var5.execute();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5);
         SQLUtil.a(var4);
      }
   }

   public void a(int var1, String var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;
      L1Buddy var5 = this.a(var1);
      if (var5.b(var2)) {
         try {
            var3 = DatabaseFactory.a().b();
            var4 = var3.prepareStatement("DELETE FROM character_buddys WHERE char_id=? AND buddy_name=?");
            var4.setInt(1, var1);
            var4.setString(2, var2);
            var4.execute();
            var5.a(var2);
         } catch (SQLException var10) {
            a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         } finally {
            SQLUtil.a(var4);
            SQLUtil.a(var3);
         }
      }
   }
}
