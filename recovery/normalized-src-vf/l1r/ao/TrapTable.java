package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bd.L1Trap__obf_i;
import l1r.bd.TrapStorage;
import l1r.bi.LineageUtil;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class TrapTable {
   private static final Logger a = Logger.getLogger(TrapTable.class.getName());
   private static TrapTable b;
   private final HashMap<Integer, L1Trap__obf_i> c = new HashMap<>();

   private TrapTable() {
      this.d();
   }

   private void d() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM trap");
         var3 = var2.executeQuery();

         while (var3.next()) {
            String var4 = var3.getString("type");
            L1Trap__obf_i var5 = LineageUtil.a(var4, new TrapTable.L1R_a(var3));
            this.c.put(var5.a(), var5);
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } catch (Exception var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public static TrapTable a() {
      if (b == null) {
         b = new TrapTable();
      }

      return b;
   }

   public static void b() {
      TrapTable var0 = b;
      b = new TrapTable();
      var0.c.clear();
   }

   public L1Trap__obf_i a(int var1) {
      return this.c.get(var1);
   }

   private class L1R_a implements TrapStorage {
      private final ResultSet b;

      public L1R_a(ResultSet var2) {
         this.b = var2;
      }

      @Override
      public String a(String var1) {
         try {
            return this.b.getString(var1);
         } catch (SQLException var3) {
            TrapTable.a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
            return "";
         }
      }

      @Override
      public int b(String var1) {
         try {
            return this.b.getInt(var1);
         } catch (SQLException var3) {
            TrapTable.a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
            return 0;
         }
      }

      @Override
      public boolean c(String var1) {
         try {
            return this.b.getBoolean(var1);
         } catch (SQLException var3) {
            TrapTable.a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
            return false;
         }
      }
   }
}
