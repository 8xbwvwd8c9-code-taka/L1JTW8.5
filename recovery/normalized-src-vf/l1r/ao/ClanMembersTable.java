package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

   public synchronized boolean mergeClanAtomic(int var1, int var2, String var3, int var4, String var5) {
      if (var1 <= 0 || var2 <= 0 || var4 <= 0 || var2 == var4 || var3 == null || var3.length() == 0 || var5 == null || var5.length() == 0) {
         return false;
      }

      Connection var6 = null;
      boolean var7 = true;
      boolean var8 = false;
      boolean var9 = false;
      ArrayList<Integer> var10 = new ArrayList<>();
      try {
         var6 = DatabaseFactory.a().b();
         this.requireClanMergeInnoDb(var6);
         var7 = var6.getAutoCommit();
         var6.setAutoCommit(false);

         boolean var11 = false;
         boolean var12 = false;
         try (PreparedStatement var13 = var6.prepareStatement("SELECT clan_id, clan_name FROM clan_data WHERE clan_id IN (?,?) ORDER BY clan_id FOR UPDATE")) {
            var13.setInt(1, var2);
            var13.setInt(2, var4);
            try (ResultSet var14 = var13.executeQuery()) {
               int var15 = 0;
               while (var14.next()) {
                  int var16 = var14.getInt("clan_id");
                  String var17 = var14.getString("clan_name");
                  if (var16 == var2 && var3.equalsIgnoreCase(var17)) {
                     var11 = true;
                  } else if (var16 == var4 && var5.equalsIgnoreCase(var17)) {
                     var12 = true;
                  } else {
                     throw new SQLException("BUG-850-087 clan row mismatch");
                  }
                  var15++;
               }
               if (var15 != 2 || !var11 || !var12) {
                  throw new SQLException("BUG-850-087 missing clan row");
               }
            }
         }

         try (PreparedStatement var18 = var6.prepareStatement("SELECT ClanID FROM characters WHERE objid=? FOR UPDATE")) {
            var18.setInt(1, var1);
            try (ResultSet var19 = var18.executeQuery()) {
               if (!var19.next() || var19.getInt("ClanID") != var2 || var19.next()) {
                  throw new SQLException("BUG-850-087 target leader authority mismatch");
               }
            }
         }

         try (PreparedStatement var20 = var6.prepareStatement("SELECT objid FROM characters WHERE ClanID=? ORDER BY objid FOR UPDATE")) {
            var20.setInt(1, var4);
            try (ResultSet var21 = var20.executeQuery()) {
               while (var21.next()) {
                  var10.add(var21.getInt("objid"));
               }
            }
         }
         if (var10.isEmpty()) {
            throw new SQLException("BUG-850-087 source clan has no durable members");
         }

         ArrayList<Integer> var22 = new ArrayList<>();
         try (PreparedStatement var23 = var6.prepareStatement("SELECT char_id FROM clan_members WHERE clan_id=? ORDER BY char_id FOR UPDATE")) {
            var23.setInt(1, var4);
            try (ResultSet var24 = var23.executeQuery()) {
               while (var24.next()) {
                  var22.add(var24.getInt("char_id"));
               }
            }
         }
         if (var22.size() != var10.size() || !var22.containsAll(var10) || !var10.containsAll(var22)) {
            throw new SQLException("BUG-850-087 characters/clan_members source membership mismatch");
         }

         try (PreparedStatement var25 = var6.prepareStatement("UPDATE characters SET ClanRank=4 WHERE objid=? AND ClanID=?")) {
            var25.setInt(1, var1);
            var25.setInt(2, var2);
            if (var25.executeUpdate() != 1) {
               throw new SQLException("BUG-850-087 target leader rank CAS failed");
            }
         }
         try (PreparedStatement var26 = var6.prepareStatement("UPDATE characters SET ClanID=?, Clanname=?, ClanRank=2 WHERE ClanID=?")) {
            var26.setInt(1, var2);
            var26.setString(2, var3);
            var26.setInt(3, var4);
            if (var26.executeUpdate() != var10.size()) {
               throw new SQLException("BUG-850-087 character migration affected-row mismatch");
            }
         }
         try (PreparedStatement var27 = var6.prepareStatement("UPDATE clan_members SET clan_id=? WHERE clan_id=?")) {
            var27.setInt(1, var2);
            var27.setInt(2, var4);
            if (var27.executeUpdate() != var10.size()) {
               throw new SQLException("BUG-850-087 clan_members migration affected-row mismatch");
            }
         }
         try (PreparedStatement var28 = var6.prepareStatement("DELETE FROM clan_warehouse_history WHERE clan_id=?")) {
            var28.setInt(1, var4);
            var28.executeUpdate();
         }
         try (PreparedStatement var29 = var6.prepareStatement("DELETE FROM clan_data WHERE clan_id=? AND clan_name=?")) {
            var29.setInt(1, var4);
            var29.setString(2, var5);
            if (var29.executeUpdate() != 1) {
               throw new SQLException("BUG-850-087 source clan delete CAS failed");
            }
         }

         var8 = true;
         var6.commit();
         return true;
      } catch (Exception var34) {
         if (var8) {
            var9 = true;
            a.log(Level.SEVERE, "BUG-850-087 clan merge commit outcome unknown; reconciling", var34);
         } else if (var6 != null) {
            try {
               var6.rollback();
            } catch (SQLException var33) {
               a.log(Level.SEVERE, var33.getLocalizedMessage(), var33);
            }
         }
         if (!var8) {
            a.log(Level.SEVERE, "BUG-850-087 clan merge transaction failed", var34);
         }
      } finally {
         if (var6 != null) {
            try {
               var6.setAutoCommit(var7);
            } catch (SQLException var32) {
               a.log(Level.SEVERE, var32.getLocalizedMessage(), var32);
            }
         }
         SQLUtil.a(var6);
      }

      return var9 && this.isClanMergeCommitted(var1, var2, var4, var10);
   }

   public synchronized boolean kickClanMemberAtomic(L1PcInstance var1, int var2) {
      if (var1 == null || var1.fr() <= 0 || var2 <= 0) {
         return false;
      }
      Connection var3 = null;
      boolean var4 = true;
      boolean var5 = false;
      boolean var6 = false;
      try {
         var3 = DatabaseFactory.a().b();
         this.requireClanMembershipInnoDb(var3);
         var4 = var3.getAutoCommit();
         var3.setAutoCommit(false);
         try (PreparedStatement var7 = var3.prepareStatement("SELECT ClanID FROM characters WHERE objid=? FOR UPDATE")) {
            var7.setInt(1, var1.fr());
            try (ResultSet var8 = var7.executeQuery()) {
               if (!var8.next() || var8.getInt("ClanID") != var2 || var8.next()) {
                  throw new SQLException("BUG-850-095 authoritative character clan mismatch");
               }
            }
         }
         try (PreparedStatement var9 = var3.prepareStatement("SELECT clan_id FROM clan_members WHERE char_id=? FOR UPDATE")) {
            var9.setInt(1, var1.fr());
            try (ResultSet var10 = var9.executeQuery()) {
               if (!var10.next() || var10.getInt("clan_id") != var2 || var10.next()) {
                  throw new SQLException("BUG-850-095 authoritative clan_members mismatch");
               }
            }
         }
         try (PreparedStatement var11 = var3.prepareStatement("DELETE FROM clan_members WHERE char_id=? AND clan_id=?")) {
            var11.setInt(1, var1.fr());
            var11.setInt(2, var2);
            if (var11.executeUpdate() != 1) {
               throw new SQLException("BUG-850-095 clan_members delete CAS failed");
            }
         }
         try (PreparedStatement var12 = var3.prepareStatement("UPDATE characters SET ClanID=0, Clanname='', ClanRank=0, Title='' WHERE objid=? AND ClanID=?")) {
            var12.setInt(1, var1.fr());
            var12.setInt(2, var2);
            if (var12.executeUpdate() != 1) {
               throw new SQLException("BUG-850-095 character clan CAS failed");
            }
         }
         var5 = true;
         var3.commit();
         return true;
      } catch (Exception var16) {
         if (var5) {
            var6 = true;
            a.log(Level.SEVERE, "BUG-850-095 clan kick commit outcome unknown; reconciling", var16);
         } else if (var3 != null) {
            try {
               var3.rollback();
            } catch (SQLException var15) {
               a.log(Level.SEVERE, var15.getLocalizedMessage(), var15);
            }
         }
         if (!var5) {
            a.log(Level.SEVERE, "BUG-850-095 clan kick transaction failed", var16);
         }
      } finally {
         if (var3 != null) {
            try {
               var3.setAutoCommit(var4);
            } catch (SQLException var14) {
               a.log(Level.SEVERE, var14.getLocalizedMessage(), var14);
            }
         }
         SQLUtil.a(var3);
      }
      return var6 && this.isClanKickCommitted(var1.fr());
   }

   private boolean isClanMergeCommitted(int var1, int var2, int var3, ArrayList<Integer> var4) {
      Connection var5 = null;
      try {
         var5 = DatabaseFactory.a().b();
         try (PreparedStatement var6 = var5.prepareStatement("SELECT ClanID, ClanRank FROM characters WHERE objid=?")) {
            var6.setInt(1, var1);
            try (ResultSet var7 = var6.executeQuery()) {
               if (!var7.next() || var7.getInt("ClanID") != var2 || var7.getInt("ClanRank") != 4 || var7.next()) {
                  return false;
               }
            }
         }
         try (PreparedStatement var8 = var5.prepareStatement("SELECT COUNT(*) FROM clan_data WHERE clan_id=?")) {
            var8.setInt(1, var3);
            try (ResultSet var9 = var8.executeQuery()) {
               if (!var9.next() || var9.getInt(1) != 0) {
                  return false;
               }
            }
         }
         try (PreparedStatement var10 = var5.prepareStatement("SELECT ClanID FROM characters WHERE objid=?"); PreparedStatement var11 = var5.prepareStatement("SELECT clan_id FROM clan_members WHERE char_id=?")) {
            for (Integer var12 : var4) {
               var10.setInt(1, var12);
               try (ResultSet var13 = var10.executeQuery()) {
                  if (!var13.next() || var13.getInt("ClanID") != var2 || var13.next()) {
                     return false;
                  }
               }
               var11.setInt(1, var12);
               try (ResultSet var14 = var11.executeQuery()) {
                  if (!var14.next() || var14.getInt("clan_id") != var2 || var14.next()) {
                     return false;
                  }
               }
            }
         }
         return true;
      } catch (SQLException var15) {
         a.log(Level.SEVERE, "BUG-850-087 authoritative reconcile failed", var15);
         return false;
      } finally {
         SQLUtil.a(var5);
      }
   }

   private boolean isClanKickCommitted(int var1) {
      Connection var2 = null;
      try {
         var2 = DatabaseFactory.a().b();
         try (PreparedStatement var3 = var2.prepareStatement("SELECT ClanID FROM characters WHERE objid=?")) {
            var3.setInt(1, var1);
            try (ResultSet var4 = var3.executeQuery()) {
               if (!var4.next() || var4.getInt("ClanID") != 0 || var4.next()) {
                  return false;
               }
            }
         }
         try (PreparedStatement var5 = var2.prepareStatement("SELECT COUNT(*) FROM clan_members WHERE char_id=?")) {
            var5.setInt(1, var1);
            try (ResultSet var6 = var5.executeQuery()) {
               return var6.next() && var6.getInt(1) == 0;
            }
         }
      } catch (SQLException var7) {
         a.log(Level.SEVERE, "BUG-850-095 authoritative reconcile failed", var7);
         return false;
      } finally {
         SQLUtil.a(var2);
      }
   }

   private void requireClanMergeInnoDb(Connection var1) throws SQLException {
      try (PreparedStatement var2 = var1.prepareStatement("SELECT TABLE_NAME, ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('characters','clan_members','clan_data','clan_warehouse_history')"); ResultSet var3 = var2.executeQuery()) {
         int var4 = 0;
         while (var3.next()) {
            if (!"InnoDB".equalsIgnoreCase(var3.getString("ENGINE"))) {
               throw new SQLException("BUG-850-087 requires InnoDB clan merge tables");
            }
            var4++;
         }
         if (var4 != 4) {
            throw new SQLException("BUG-850-087 missing clan merge transaction table");
         }
      }
   }

   private void requireClanMembershipInnoDb(Connection var1) throws SQLException {
      try (PreparedStatement var2 = var1.prepareStatement(
         "SELECT TABLE_NAME, ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('characters','clan_members','clan_data')"
      ); ResultSet var3 = var2.executeQuery()) {
         int var4 = 0;
         while (var3.next()) {
            if (!"InnoDB".equalsIgnoreCase(var3.getString("ENGINE"))) {
               throw new SQLException("BUG-850-095 requires InnoDB clan membership tables");
            }
            var4++;
         }
         if (var4 != 3) {
            throw new SQLException("BUG-850-095 missing clan membership transaction table");
         }
      }
   }

}
