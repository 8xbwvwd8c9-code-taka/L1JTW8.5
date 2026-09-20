package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.bh.L1Town;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class TownTable {
   private static final Logger a = Logger.getLogger(TownTable.class.getName());
   private static TownTable b;
   private final ConcurrentHashMap<Integer, L1Town> c = new ConcurrentHashMap<>();

   public static TownTable a() {
      if (b == null) {
         b = new TownTable();
      }

      return b;
   }

   private TownTable() {
      this.b();
   }

   public void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;
      this.c.clear();

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM town");
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1Town var5 = new L1Town();
            int var4 = var3.getInt("town_id");
            var5.a(var4);
            var5.a(var3.getString("name"));
            var5.b(var3.getInt("leader_id"));
            var5.b(var3.getString("leader_name"));
            var5.c(var3.getInt("tax_rate"));
            var5.d(var3.getInt("tax_rate_reserved"));
            var5.e(var3.getInt("sales_money"));
            var5.f(var3.getInt("sales_money_yesterday"));
            var5.g(var3.getInt("town_tax"));
            var5.h(var3.getInt("town_fix_tax"));
            this.c.put(new Integer(var4), var5);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public L1Town[] c() {
      return this.c.values().toArray(new L1Town[this.c.size()]);
   }

   public L1Town a(int var1) {
      return this.c.get(var1);
   }

   public boolean a(L1PcInstance var1, int var2) {
      L1Town var3 = this.a(var2);
      return var3.c() == var1.fr();
   }

   public synchronized void a(int var1, int var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;
      L1Town var5 = a().a(var1);
      int var6 = var5.e();
      int var7 = var2 * var6 / 100;
      int var8 = var2 * 2 / 100;
      if (var7 <= 0 && var6 > 0) {
         var7 = 1;
      }

      if (var8 <= 0 && var6 > 0) {
         var8 = 1;
      }

      if ((long)var5.g() + var2 >= 2000000000L) {
         var2 = 2000000000 - var5.g();
      }

      if ((long)var5.i() + var7 >= 2000000000L) {
         var7 = 2000000000 - var5.i();
      }

      if ((long)var5.j() + var8 >= 2000000000L) {
         var8 = 2000000000 - var5.j();
      }

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement(
            "UPDATE town SET sales_money = sales_money + ?, town_tax = town_tax + ?, town_fix_tax = town_fix_tax + ? WHERE town_id = ?"
         );
         var4.setInt(1, var2);
         var4.setInt(2, var7);
         var4.setInt(3, var8);
         var4.setInt(4, var1);
         var4.execute();
         var5.e(var5.g() + var2);
         var5.g(var5.i() + var7);
         var5.h(var5.j() + var8);
      } catch (SQLException var13) {
         a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   public void d() {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("UPDATE town SET tax_rate = tax_rate_reserved");
         var2.execute();
      } catch (SQLException var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public void e() {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("UPDATE town SET sales_money_yesterday = sales_money, sales_money = 0");
         var2.execute();
      } catch (SQLException var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }
}
