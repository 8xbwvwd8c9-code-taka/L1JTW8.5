package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1ItemInstance;
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

   public L1Clan createClanAtomic(L1PcInstance var1, String var2) {
      if (var1 == null || var2 == null || var2.length() == 0) {
         return null;
      }

      synchronized (var1) {
         synchronized (var1.j()) {
            if (var1.aF() != 0) {
               return null;
            }

            for (L1Clan var3 : this.b.values()) {
               if (var3.f().equalsIgnoreCase(var2)) {
                  return null;
               }
            }

            L1ItemInstance var4 = var1.j().b(40308);
            if (var4 == null || var4.E() < 30000) {
               return null;
            }

            int var5 = var4.E();
            int var6 = var5 - 30000;
            L1Clan var7 = new L1Clan();
            var7.c(IdFactory.a().d());
            var7.e(var2);
            var7.f(var1.fr());
            var7.g(var1.et());
            var7.g(0);
            var7.h(0);
            var7.a(new Timestamp(System.currentTimeMillis()));
            var7.f("");
            var7.d(0);
            var7.e(0);

            Connection var8 = null;
            boolean var9 = true;
            boolean var10 = false;

            try {
               var8 = DatabaseFactory.a().b();
               this.requireClanCreateInnoDb(var8);
               var9 = var8.getAutoCommit();
               var8.setAutoCommit(false);

               try (PreparedStatement var11 = var8.prepareStatement(
                  "INSERT INTO clan_data SET clan_id=?, clan_name=?, leader_id=?, leader_name=?, hascastle=?, hashouse=?, found_date=?, announcement=?, emblem_id=?, emblem_status=?"
               )) {
                  var11.setInt(1, var7.e());
                  var11.setString(2, var7.f());
                  var11.setInt(3, var7.k());
                  var11.setString(4, var7.l());
                  var11.setInt(5, var7.m());
                  var11.setInt(6, var7.n());
                  var11.setTimestamp(7, var7.g());
                  var11.setString(8, "");
                  var11.setInt(9, 0);
                  var11.setInt(10, 0);
                  if (var11.executeUpdate() != 1) {
                     throw new SQLException("BUG-850-166 clan_data insert failed");
                  }
               }

               try (PreparedStatement var12 = var8.prepareStatement(
                  "INSERT INTO clan_members SET clan_id=?, char_id=?, char_name=?, date=?, notes=?"
               )) {
                  var12.setInt(1, var7.e());
                  var12.setInt(2, var1.fr());
                  var12.setString(3, var1.et());
                  var12.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                  var12.setString(5, "");
                  if (var12.executeUpdate() != 1) {
                     throw new SQLException("BUG-850-166 clan_members insert failed");
                  }
               }

               try (PreparedStatement var13 = var8.prepareStatement(
                  "UPDATE characters SET ClanID=?, Clanname=?, ClanRank=? WHERE objid=? AND ClanID=0"
               )) {
                  var13.setInt(1, var7.e());
                  var13.setString(2, var7.f());
                  var13.setInt(3, 10);
                  var13.setInt(4, var1.fr());
                  if (var13.executeUpdate() != 1) {
                     throw new SQLException("BUG-850-166 character clan CAS failed");
                  }
               }

               CharacterItemTable var14 = CharacterItemTable.a();
               if (var6 == 0) {
                  var14.deleteQuestRewardItem(var8, var1.fr(), var4, var5);
               } else {
                  var14.updateQuestRewardCount(var8, var1.fr(), var4, var5, var6);
               }

               var8.commit();
               var10 = true;
            } catch (Exception var18) {
               if (var8 != null) {
                  try {
                     var8.rollback();
                  } catch (SQLException var17) {
                     a.log(Level.SEVERE, var17.getLocalizedMessage(), var17);
                  }
               }

               a.log(Level.SEVERE, "BUG-850-166 clan creation transaction failed", var18);
            } finally {
               if (var8 != null) {
                  try {
                     var8.setAutoCommit(var9);
                  } catch (SQLException var16) {
                     a.log(Level.SEVERE, var16.getLocalizedMessage(), var16);
                  }
               }

               SQLUtil.a(var8);
            }

            if (!var10) {
               return null;
            }

            this.b.put(var7.e(), var7);
            var1.ah(var7.e());
            var1.c(var7.f());
            var1.ai(10);
            var1.a(new S_PacketBox(27, 10, var1.et()));
            var7.a(var1.et());

            if (var6 == 0) {
               var1.j().publishCommittedQuestDelete(var4);
            } else {
               var1.j().publishCommittedQuestUpdate(var4, var6);
            }

            return var7;
         }
      }
   }

   private void requireClanCreateInnoDb(Connection var1) throws SQLException {
      try (PreparedStatement var2 = var1.prepareStatement(
         "SELECT TABLE_NAME, ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('clan_data','clan_members','characters','character_items')"
      );
           ResultSet var3 = var2.executeQuery()) {
         int var4 = 0;
         while (var3.next()) {
            if (!"InnoDB".equalsIgnoreCase(var3.getString("ENGINE"))) {
               throw new SQLException("BUG-850-166 requires InnoDB clan creation tables");
            }
            var4++;
         }

         if (var4 != 4) {
            throw new SQLException("BUG-850-166 missing clan creation transaction table");
         }
      }
   }

   public boolean updateWatchRelationAtomic(L1Clan var1, L1Clan var2, boolean var3) {
      if (var1 == null || var2 == null || var1.e() == var2.e()) {
         return false;
      }

      L1Clan var4 = var1.e() < var2.e() ? var1 : var2;
      L1Clan var5 = var4 == var1 ? var2 : var1;
      synchronized (var4) {
         synchronized (var5) {
            Connection var6 = null;
            boolean var7 = true;
            boolean var8 = false;
            LinkedHashSet<Integer> var9 = null;
            LinkedHashSet<Integer> var10 = null;
            String var11 = null;
            String var12 = null;

            try {
               var6 = DatabaseFactory.a().b();
               this.requireClanWatchInnoDb(var6);
               var7 = var6.getAutoCommit();
               var6.setAutoCommit(false);

               try (PreparedStatement var13 = var6.prepareStatement(
                  "SELECT clan_id, watch_clanid FROM clan_data WHERE clan_id IN (?,?) ORDER BY clan_id FOR UPDATE"
               )) {
                  var13.setInt(1, var4.e());
                  var13.setInt(2, var5.e());
                  try (ResultSet var14 = var13.executeQuery()) {
                     int var15 = 0;
                     while (var14.next()) {
                        int var16 = var14.getInt("clan_id");
                        String var17 = var14.getString("watch_clanid");
                        if (var16 == var1.e()) {
                           var11 = var17;
                           var9 = this.parseWatchClanIds(var17);
                        } else if (var16 == var2.e()) {
                           var12 = var17;
                           var10 = this.parseWatchClanIds(var17);
                        } else {
                           throw new SQLException("BUG-850-290 unexpected clan row");
                        }
                        var15++;
                     }
                     if (var15 != 2 || var9 == null || var10 == null) {
                        throw new SQLException("BUG-850-290 missing bilateral clan row");
                     }
                  }
               }

               if (var3) {
                  if (!var9.contains(var2.e()) && var9.size() >= 10) {
                     throw new SQLException("BUG-850-290 left watch capacity exceeded");
                  }
                  if (!var10.contains(var1.e()) && var10.size() >= 10) {
                     throw new SQLException("BUG-850-290 right watch capacity exceeded");
                  }
                  var9.add(var2.e());
                  var10.add(var1.e());
               } else {
                  var9.remove(var2.e());
                  var10.remove(var1.e());
               }

               this.updateWatchRow(var6, var1.e(), var11, this.serializeWatchClanIds(var9));
               this.updateWatchRow(var6, var2.e(), var12, this.serializeWatchClanIds(var10));
               var6.commit();
               var8 = true;
            } catch (Exception var21) {
               if (var6 != null) {
                  try {
                     var6.rollback();
                  } catch (SQLException var20) {
                     a.log(Level.SEVERE, var20.getLocalizedMessage(), var20);
                  }
               }
               a.log(Level.SEVERE, "BUG-850-290 bilateral clan watch transaction failed", var21);
            } finally {
               if (var6 != null) {
                  try {
                     var6.setAutoCommit(var7);
                  } catch (SQLException var19) {
                     a.log(Level.SEVERE, var19.getLocalizedMessage(), var19);
                  }
               }
               SQLUtil.a(var6);
            }

            if (!var8) {
               return false;
            }

            var1.t().clear();
            var1.t().addAll(var9);
            var2.t().clear();
            var2.t().addAll(var10);
            return true;
         }
      }
   }

   private void requireClanWatchInnoDb(Connection var1) throws SQLException {
      try (PreparedStatement var2 = var1.prepareStatement(
         "SELECT ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='clan_data'"
      ); ResultSet var3 = var2.executeQuery()) {
         if (!var3.next() || !"InnoDB".equalsIgnoreCase(var3.getString("ENGINE")) || var3.next()) {
            throw new SQLException("BUG-850-290 requires clan_data InnoDB");
         }
      }
   }

   private LinkedHashSet<Integer> parseWatchClanIds(String var1) throws SQLException {
      LinkedHashSet<Integer> var2 = new LinkedHashSet<>();
      if (var1 == null || var1.trim().isEmpty()) {
         return var2;
      }
      for (String var3 : var1.split(",")) {
         if (var3 == null || var3.trim().isEmpty()) {
            continue;
         }
         try {
            int var4 = Integer.parseInt(var3.trim());
            if (var4 > 0) {
               var2.add(var4);
            }
         } catch (NumberFormatException var5) {
            throw new SQLException("BUG-850-290 invalid watch_clanid", var5);
         }
      }
      return var2;
   }

   private String serializeWatchClanIds(Set<Integer> var1) {
      StringBuilder var2 = new StringBuilder();
      for (int var3 : var1) {
         if (var2.length() > 0) {
            var2.append(',');
         }
         var2.append(var3);
      }
      return var2.toString();
   }

   private void updateWatchRow(Connection var1, int var2, String var3, String var4) throws SQLException {
      String var5 = var3 == null ? "" : var3;
      if (var5.equals(var4)) {
         return;
      }
      try (PreparedStatement var6 = var1.prepareStatement("UPDATE clan_data SET watch_clanid=? WHERE clan_id=?")) {
         var6.setString(1, var4);
         var6.setInt(2, var2);
         if (var6.executeUpdate() != 1) {
            throw new SQLException("BUG-850-290 watch row update failed");
         }
      }
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
