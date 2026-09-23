package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.be.S_ProtoBuffers;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class SoulTowerTable {
   private static final Logger a = Logger.getLogger(SoulTowerTable.class.getName());
   private static SoulTowerTable b;
   private final ArrayList<SoulTowerTable.L1R_a> c = new ArrayList<>();

   public static SoulTowerTable a() {
      if (b == null) {
         b = new SoulTowerTable();
      }

      return b;
   }

   private SoulTowerTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM soul_tower");
         var3 = var2.executeQuery();

         while (var3.next()) {
            SoulTowerTable.L1R_a var4 = new SoulTowerTable.L1R_a();
            var4.a = var3.getString("name");
            var4.b = var3.getInt("class");
            var4.c = var3.getInt("time");
            var4.d = var3.getDate("date").getTime();
            this.c.add(var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public synchronized void a(L1PcInstance var1) {
      var1.a(new S_ProtoBuffers(this.c.toArray(new SoulTowerTable.L1R_a[0])));
   }

   public synchronized void a(L1PcInstance var1, int var2) {
      boolean var3 = this.c.size() < 10;

      for (SoulTowerTable.L1R_a var4 : this.c) {
         if (var2 < var4.c) {
            var3 = true;
            break;
         }
      }

      if (!var3) {
         return;
      }

      SoulTowerTable.L1R_a var5 = new SoulTowerTable.L1R_a();
      var5.a = var1.et();
      var5.b = var1.ay();
      var5.c = var2;
      Date var6 = new Date();
      java.sql.Date var7 = new java.sql.Date(var6.getTime());
      var5.d = var7.getTime();

      ArrayList<SoulTowerTable.L1R_a> var8 = new ArrayList<>(this.c);
      var8.add(var5);
      Collections.sort(var8, new Comparator<SoulTowerTable.L1R_a>() {
         @Override
         public int compare(SoulTowerTable.L1R_a var1, SoulTowerTable.L1R_a var2x) {
            return Integer.compare(var1.c, var2x.c);
         }
      });

      while (var8.size() > 10) {
         var8.remove(var8.size() - 1);
      }

      if (!this.a(var8)) {
         return;
      }

      this.c.clear();
      this.c.addAll(var8);
   }

   private boolean a(List<SoulTowerTable.L1R_a> var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      boolean var4 = true;

      try {
         var2 = DatabaseFactory.a().b();
         var4 = var2.getAutoCommit();
         var2.setAutoCommit(false);

         var3 = var2.prepareStatement("DELETE FROM soul_tower WHERE rank >0");
         var3.executeUpdate();
         SQLUtil.a(var3);
         var3 = var2.prepareStatement("INSERT INTO soul_tower SET rank=?,name=?,class=?,time=?,date=?");

         for (int var5 = 0; var5 < var1.size(); var5++) {
            SoulTowerTable.L1R_a var6 = var1.get(var5);
            var3.setInt(1, var5 + 1);
            var3.setString(2, var6.a);
            var3.setInt(3, var6.b);
            var3.setInt(4, var6.c);
            Date var7 = new Date();
            var7.setTime(var6.d);
            var3.setDate(5, new java.sql.Date(var7.getTime()));
            if (var3.executeUpdate() != 1) {
               throw new SQLException("BUG-850-251 SoulTower insert affected unexpected row count");
            }
         }

         var2.commit();
         return true;
      } catch (SQLException var10) {
         if (var2 != null) {
            try {
               var2.rollback();
            } catch (SQLException var9) {
               a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
            }
         }

         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         return false;
      } finally {
         SQLUtil.a(var3);
         if (var2 != null) {
            try {
               var2.setAutoCommit(var4);
            } catch (SQLException var8) {
               a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
            }
         }
         SQLUtil.a(var2);
      }
   }

   public class L1R_a {
      public String a;
      public int b;
      public int c;
      public long d;
   }
}
