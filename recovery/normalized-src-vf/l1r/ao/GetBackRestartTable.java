package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class GetBackRestartTable {
   private static final Logger a = Logger.getLogger(GetBackRestartTable.class.getName());
   private static GetBackRestartTable b;
   private final HashMap<Integer, GetBackRestartTable.a> c = new HashMap<>();

   public static GetBackRestartTable a() {
      if (b == null) {
         b = new GetBackRestartTable();
      }

      return b;
   }

   public GetBackRestartTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM getback_restart");
         var3 = var2.executeQuery();

         while (var3.next()) {
            GetBackRestartTable.a var4 = new GetBackRestartTable.a(null);
            int var5 = var3.getInt("area");
            var4.a(var3.getInt("locx"));
            var4.b(var3.getInt("locy"));
            var4.c(var3.getShort("mapid"));
            this.c.put(var5, var4);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(int var1, int var2) {
      if (!this.c.containsKey(var1)) {
         System.out.println("[CopyGetBackRestart]: " + var1 + " error");
      } else {
         GetBackRestartTable.a var3 = this.c.get(var1);
         GetBackRestartTable.a var4 = new GetBackRestartTable.a(null);
         var4.a(var3.a());
         var4.b(var3.b());
         var4.c(var3.c());
         if (!this.c.containsKey(var2)) {
            this.c.put(var2, var4);
         }
      }
   }

   public void a(L1PcInstance var1) {
      if (this.c.containsKey(var1.fp())) {
         GetBackRestartTable.a var2 = this.c.get(var1.fp());
         var1.cG(var2.a());
         var1.cH(var2.b());
         var1.cE(var2.c());
      }
   }

   public void b(L1PcInstance var1) {
      if (this.c.containsKey(var1.fp())) {
         GetBackRestartTable.a var2 = this.c.get(var1.fp());
         L1Teleport.a(var1, var2.a(), var2.b(), var2.c(), 5, true);
      }
   }

   private class a {
      private int b;
      private int c;
      private int d;

      private a() {
      }

      public int a() {
         return this.b;
      }

      public void a(int var1) {
         this.b = var1;
      }

      public int b() {
         return this.c;
      }

      public void b(int var1) {
         this.c = var1;
      }

      public int c() {
         return this.d;
      }

      public void c(int var1) {
         this.d = var1;
      }

      // $VF: synthetic method
      a(GetBackRestartTable.a var2) {
         this();
      }
   }
}
