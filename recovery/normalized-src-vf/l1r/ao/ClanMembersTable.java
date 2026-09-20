package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aj.C_Pledge;
import l1r.ap.L1PcInstance;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class ClanMembersTable {
   private static final Logger a = Logger.getLogger(ClanMembersTable.class.getName());
   private static ClanMembersTable b;

   public static ClanMembersTable a() {
      if (b == null) {
         b = new ClanMembersTable();
      }

      return b;
   }

   public void a(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO clan_members SET clan_id=?, char_id=?, char_name=?, date=?, notes=?");
         var3.setInt(1, var1.aF());
         var3.setInt(2, var1.fr());
         var3.setString(3, var1.et());
         Date var4 = new Date();
         new java.sql.Date(var4.getTime());
         var3.setDate(4, new java.sql.Date(var4.getTime()));
         var3.setString(5, "");
         var3.execute();
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void a(C_Pledge.L1R_a var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM clan_members WHERE char_id=?");
         var3.setInt(1, var1.d);
         var4 = var3.executeQuery();
         if (var4.next()) {
            var1.f = (int)(var4.getDate("date").getTime() / 1000L);
            var1.g = var4.getString("notes");
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }
   }

   public void a(L1PcInstance var1, String var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("UPDATE clan_members SET notes=? WHERE char_id=?");
         var4.setString(1, var2);
         var4.setInt(2, var1.fr());
         var4.execute();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }
   }

   public void a(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM clan_members WHERE char_id=?");
         var3.setInt(1, var1);
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void b(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM clan_members WHERE clan_id=?");
         var3.setInt(1, var1);
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }
}
