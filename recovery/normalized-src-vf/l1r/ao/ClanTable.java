package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.be.S_PacketBox;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class ClanTable {
   private static final Logger a = Logger.getLogger(ClanTable.class.getName());
   private final ConcurrentHashMap<Integer, L1Clan> b = new ConcurrentHashMap<>();
   private static ClanTable c;

   public static ClanTable a() {
      if (c == null) {
         c = new ClanTable();
      }

      return c;
   }

   private ClanTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM clan_data ORDER BY clan_id");
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1Clan var4 = new L1Clan();
            int var5 = var3.getInt("clan_id");
            var4.c(var5);
            var4.e(var3.getString("clan_name"));
            var4.f(var3.getInt("leader_id"));
            var4.g(var3.getString("leader_name"));
            var4.g(var3.getInt("hascastle"));
            var4.h(var3.getInt("hashouse"));
            var4.a(var3.getTimestamp("found_date"));
            var4.f(var3.getString("announcement"));
            var4.d(var3.getInt("emblem_id"));
            var4.e(var3.getInt("emblem_status"));
            var4.d(var3.getString("watch_clanid"));
            this.b.put(var5, var4);
         }
      } catch (SQLException var18) {
         a.log(Level.SEVERE, var18.getLocalizedMessage(), var18);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }

      for (L1Clan var20 : this.b.values()) {
         Connection conx = null;
         PreparedStatement pstmx = null;
         ResultSet rsx = null;

         try {
            conx = DatabaseFactory.a().b();
            pstmx = conx.prepareStatement("SELECT char_name FROM characters WHERE ClanID = ?");
            pstmx.setInt(1, var20.e());
            rsx = pstmx.executeQuery();

            while (rsx.next()) {
               var20.a(rsx.getString(1));
            }
         } catch (SQLException var16) {
            a.log(Level.SEVERE, var16.getLocalizedMessage(), var16);
         } finally {
            SQLUtil.a(rsx, pstmx, conx);
         }
      }

      for (L1Clan var21 : this.b.values()) {
         var21.c().a();
      }
   }

   public void a(L1Clan var1) {
      for (L1Clan var2 : this.b.values()) {
         if (var2.f().equalsIgnoreCase(var1.f())) {
            return;
         }
      }

      this.b.put(var1.e(), var1);
   }

   public L1Clan a(L1PcInstance var1, String var2) {
      for (L1Clan var3 : this.b.values()) {
         if (var3.f().equalsIgnoreCase(var2)) {
            return null;
         }
      }

      L1Clan var12 = new L1Clan();
      var12.c(IdFactory.a().d());
      var12.e(var2);
      var12.f(var1.fr());
      var12.g(var1.et());
      var12.g(0);
      var12.h(0);
      var12.a(new Timestamp(System.currentTimeMillis()));
      var12.f("");
      var12.d(0);
      var12.e(0);
      Connection var13 = null;
      PreparedStatement var5 = null;

      try {
         var13 = DatabaseFactory.a().b();
         var5 = var13.prepareStatement(
            "INSERT INTO clan_data SET clan_id=?, clan_name=?, leader_id=?, leader_name=?, hascastle=?, hashouse=?, found_date=?, announcement=?, emblem_id=?, emblem_status=?"
         );
         var5.setInt(1, var12.e());
         var5.setString(2, var12.f());
         var5.setInt(3, var12.k());
         var5.setString(4, var12.l());
         var5.setInt(5, var12.m());
         var5.setInt(6, var12.n());
         var5.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
         var5.setString(8, "");
         var5.setInt(9, 0);
         var5.setInt(10, 0);
         var5.execute();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5);
         SQLUtil.a(var13);
      }

      this.b.put(var12.e(), var12);
      var1.ah(var12.e());
      var1.c(var12.f());
      var1.ai(10);
      var1.a(new S_PacketBox(27, 10, var1.et()));
      var12.a(var1.et());
      var1.I();
      return var12;
   }

   public void b(L1Clan var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement(
            "UPDATE clan_data SET clan_id=?, leader_id=?, leader_name=?, hascastle=?, hashouse=?, found_date=?, announcement=?, emblem_id=?, emblem_status=?, watch_clanid=? WHERE clan_name=?"
         );
         var3.setInt(1, var1.e());
         var3.setInt(2, var1.k());
         var3.setString(3, var1.l());
         var3.setInt(4, var1.m());
         var3.setInt(5, var1.n());
         var3.setTimestamp(6, var1.g());
         var3.setString(7, var1.h());
         var3.setInt(8, var1.i());
         var3.setInt(9, var1.j());
         var3.setString(10, var1.d());
         var3.setString(11, var1.f());
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void a(String var1) {
      L1Clan var2 = this.c(var1);
      if (var2 != null) {
         this.b.remove(var2.e());
      }
   }

   public void b(String var1) {
      L1Clan var2 = this.c(var1);
      if (var2 == null) {
         return;
      }

      Connection var3 = null;
      try {
         var3 = DatabaseFactory.a().b();
         var3.setAutoCommit(false);
         try (PreparedStatement var4 = var3.prepareStatement("DELETE FROM clan_warehouse_history WHERE clan_id=?")) {
            var4.setInt(1, var2.e());
            var4.executeUpdate();
         }
         try (PreparedStatement var5 = var3.prepareStatement("DELETE FROM clan_data WHERE clan_name=?")) {
            var5.setString(1, var1);
            var5.executeUpdate();
         }
         var3.commit();
      } catch (SQLException var9) {
         if (var3 != null) {
            try {
               var3.rollback();
            } catch (SQLException ignored) {
            }
         }
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         return;
      } finally {
         if (var3 != null) {
            try {
               var3.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
         }
         SQLUtil.a(var3);
      }

      var2.c().g();
      var2.c().b();
      this.b.remove(var2.e());
   }


   public L1Clan a(int var1) {
      return this.b.get(var1);
   }

   public L1Clan c(String var1) {
      for (L1Clan var2 : this.b.values()) {
         if (var2.f().equals(var1)) {
            return var2;
         }
      }

      return null;
   }

   public ConcurrentHashMap<Integer, L1Clan> b() {
      return this.b;
   }
}
