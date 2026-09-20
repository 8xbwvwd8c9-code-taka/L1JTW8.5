package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public final class ResolventTable {
   private static final Logger a = Logger.getLogger(ResolventTable.class.getName());
   private static ResolventTable b;
   private final HashMap<Integer, Integer> c = new HashMap<>();
   private final HashMap<Integer, ResolventTable.L1R_a> d = new HashMap<>();

   public static ResolventTable a() {
      if (b == null) {
         b = new ResolventTable();
      }

      return b;
   }

   private ResolventTable() {
      this.d();
      this.c();
   }

   public HashMap<Integer, ResolventTable.L1R_a> b() {
      return this.d;
   }

   public int a(L1ItemInstance var1) {
      if (!this.d.containsKey(var1.N())) {
         return 1;
      }

      ResolventTable.L1R_a var2 = this.d.get(var1.N());
      int var3 = var1.G() - var1.a().x();
      var3 = Math.max(0, Math.min(var3, 8));
      return var2.b[var3];
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM resolvent_refinery");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("itemid");
            ResolventTable.L1R_a var5 = new ResolventTable.L1R_a(null);
            var5.b[0] = var3.getInt("base");
            var5.b[1] = var3.getInt("base+1");
            var5.b[2] = var3.getInt("base+2");
            var5.b[3] = var3.getInt("base+3");
            var5.b[4] = var3.getInt("base+4");
            var5.b[5] = var3.getInt("base+5");
            var5.b[6] = var3.getInt("base+6");
            var5.b[7] = var3.getInt("base+7");
            var5.b[8] = var3.getInt("base+8");
            this.d.put(var4, var5);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void d() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM resolvent");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("item_id");
            int var5 = var3.getInt("crystal_count");
            this.c.put(new Integer(var4), var5);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public int a(int var1) {
      int var2 = 0;
      if (this.c.containsKey(var1)) {
         var2 = this.c.get(var1);
      }

      return var2;
   }

   private class L1R_a {
      private final int[] b = new int[9];

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(ResolventTable.L1R_a var2) {
         this();
      }
   }
}
