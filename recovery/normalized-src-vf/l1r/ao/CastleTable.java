package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.bh.L1Castle;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CastleTable {
   private static final Logger a = Logger.getLogger(CastleTable.class.getName());
   private static CastleTable b;
   private final ConcurrentHashMap<Integer, L1Castle> c = new ConcurrentHashMap<>();

   public static CastleTable a() {
      if (b == null) {
         b = new CastleTable();
      }

      return b;
   }

   private CastleTable() {
      this.d();
   }

   private Calendar a(Timestamp var1) {
      Calendar var2 = Calendar.getInstance();
      var2.setTimeInMillis(var1.getTime());
      return var2;
   }

   private void d() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM castle ORDER BY castle_id ASC");
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1Castle var4 = new L1Castle(var3.getInt("castle_id"), var3.getString("name"));
            var4.a(this.a((Timestamp)var3.getObject("war_time")));
            var4.a(var3.getInt("tax_rate"));
            var4.b(var3.getInt("public_money"));
            if (!var4.k().isEmpty()) {
               var4.k().get(0).c = var3.getInt("mercenary_count_0");
               var4.k().get(1).c = var3.getInt("mercenary_count_1");
               var4.k().get(2).c = var3.getInt("mercenary_count_2");
               var4.k().get(3).c = var3.getInt("mercenary_count_3");
            }

            this.b(var4);
            this.c.put(var4.a(), var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void b(L1Castle var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT clan_id FROM clan_data WHERE hascastle = ?");
         var3.setInt(1, var1.a());
         var4 = var3.executeQuery();
         if (var4.next()) {
            var1.c(var4.getInt("clan_id"));
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }
   }

   public L1Castle[] b() {
      return this.c.values().toArray(new L1Castle[this.c.size()]);
   }

   public L1Castle a(int var1) {
      return this.c.get(var1);
   }

   public void a(L1Castle var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement(
            "UPDATE castle SET name=?, war_time=?, tax_rate=?, public_money=? ,mercenary_count_0=? ,mercenary_count_1=? ,mercenary_count_2=?,mercenary_count_3=? WHERE castle_id=?"
         );
         var3.setString(1, var1.b());
         SimpleDateFormat var4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         String var5 = var4.format(var1.c().getTime());
         var3.setString(2, var5);
         var3.setInt(3, var1.e());
         var3.setInt(4, var1.f());
         var3.setInt(5, var1.k().isEmpty() ? 0 : var1.k().get(0).c);
         var3.setInt(6, var1.k().isEmpty() ? 0 : var1.k().get(1).c);
         var3.setInt(7, var1.k().isEmpty() ? 0 : var1.k().get(2).c);
         var3.setInt(8, var1.k().isEmpty() ? 0 : var1.k().get(3).c);
         var3.setInt(9, var1.a());
         var3.execute();
         this.c.put(var1.a(), var1);
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public boolean transferTreasuryAdena(L1PcInstance var1, int var2, int var3, boolean var4) {
      if (var1 == null || var3 <= 0) {
         return false;
      }

      L1Castle var5 = this.c.get(var2);
      if (var5 == null) {
         return false;
      }

      synchronized (var5) {
         L1ItemInstance var6 = var1.j().b(40308);
         int var7 = var6 == null ? 0 : var6.E();
         int var8 = var5.f();
         long var9 = var4 ? (long)var8 + (long)var3 : (long)var8 - (long)var3;
         long var11 = var4 ? (long)var7 - (long)var3 : (long)var7 + (long)var3;
         if (var9 < 0L || var9 > 2000000000L || var11 < 0L || var11 > 2000000000L) {
            return false;
         }

         if (var4 && (var6 == null || var7 < var3)) {
            return false;
         }

         if (!var4 && var6 == null && var1.j().c() >= 180) {
            return false;
         }

         L1ItemInstance var13 = null;
         if (!var4 && var6 == null) {
            if (ItemTable.a().a(40308) == null) {
               return false;
            }

            var13 = new L1ItemInstance(ItemTable.a().a(40308), var3);
            var13.cF(IdFactory.a().d());
         }

         Connection var14 = null;
         PreparedStatement var15 = null;
         boolean var16 = true;
         boolean var17 = false;

         try {
            var14 = DatabaseFactory.a().b();
            this.requireTreasuryInnoDb(var14);
            var16 = var14.getAutoCommit();
            var14.setAutoCommit(false);

            var15 = var14.prepareStatement("UPDATE castle SET public_money=? WHERE castle_id=? AND public_money=?");
            var15.setInt(1, (int)var9);
            var15.setInt(2, var2);
            var15.setInt(3, var8);
            if (var15.executeUpdate() != 1) {
               throw new SQLException("BUG-850-245 castle treasury CAS failed");
            }
            SQLUtil.a(var15);
            var15 = null;

            CharacterItemTable var18 = CharacterItemTable.a();
            if (var4) {
               if (var11 == 0L) {
                  var18.deleteQuestRewardItem(var14, var1.fr(), var6, var7);
               } else {
                  var18.updateQuestRewardCount(var14, var1.fr(), var6, var7, (int)var11);
               }
            } else if (var6 != null) {
               var18.updateQuestRewardCount(var14, var1.fr(), var6, var7, (int)var11);
            } else {
               var18.insertQuestReward(var14, var1.fr(), var13);
            }

            var14.commit();
            var17 = true;
         } catch (Exception var22) {
            if (var14 != null) {
               try {
                  var14.rollback();
               } catch (SQLException var21) {
                  a.log(Level.SEVERE, var21.getLocalizedMessage(), var21);
               }
            }

            a.log(Level.SEVERE, "BUG-850-245 castle treasury transfer failed", var22);
         } finally {
            SQLUtil.a(var15);
            if (var14 != null) {
               try {
                  var14.setAutoCommit(var16);
               } catch (SQLException var20) {
                  a.log(Level.SEVERE, var20.getLocalizedMessage(), var20);
               }
            }
            SQLUtil.a(var14);
         }

         if (!var17) {
            return false;
         }

         var5.b((int)var9);
         this.c.put(var2, var5);
         if (var4) {
            if (var11 == 0L) {
               var1.j().publishCommittedQuestDelete(var6);
            } else {
               var1.j().publishCommittedQuestUpdate(var6, (int)var11);
            }
         } else if (var6 != null) {
            var1.j().publishCommittedQuestUpdate(var6, (int)var11);
         } else {
            var1.j().publishCommittedQuestInsert(var13);
         }

         return true;
      }
   }

   private void requireTreasuryInnoDb(Connection var1) throws SQLException {
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var2 = var1.prepareStatement("SELECT TABLE_NAME, ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('castle','character_items')");
         var3 = var2.executeQuery();
         int var4 = 0;

         while (var3.next()) {
            if (!"InnoDB".equalsIgnoreCase(var3.getString("ENGINE"))) {
               throw new SQLException("BUG-850-245 requires castle and character_items InnoDB migration");
            }
            var4++;
         }

         if (var4 != 2) {
            throw new SQLException("BUG-850-245 missing treasury transaction table");
         }
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void a(int var1, int var2) {
      L1Castle var3 = a().a(var1);
      int var4 = var2 * var3.e() / 100;
      synchronized (var3) {
         if (var3.f() < 2000000000) {
            var3.b(var3.f() + var4);
            a().a(var3);
         }
      }
   }

   public ConcurrentHashMap<Integer, L1Castle> c() {
      return this.c;
   }
}
