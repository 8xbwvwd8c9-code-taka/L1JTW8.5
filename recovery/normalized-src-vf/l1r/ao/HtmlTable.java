package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_Html;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Item;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class HtmlTable {
   private static final Logger a = Logger.getLogger(HtmlTable.class.getName());
   private static HtmlTable b;
   private final HashMap<String, HtmlTable.L1R_a> c = new HashMap<>();

   public static HtmlTable a() {
      if (b == null) {
         b = new HtmlTable();
      }

      return b;
   }

   private HtmlTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM html");
         var3 = var2.executeQuery();

         while (var3.next()) {
            HtmlTable.L1R_a var4 = new HtmlTable.L1R_a(null);
            var4.a = var3.getInt("npcid");
            var4.b = var3.getString("action");
            var4.c = var3.getInt("itemid");
            var4.d = var3.getInt("count");
            var4.e = var3.getString("html");
            var4.f = var3.getString("var");
            if (var4.f.endsWith(",")) {
               var4.f = var4.f.substring(0, var4.f.length() - 1);
            }

            var4.g = var3.getString("fail_html");
            String var5 = var4.a + "-" + var4.b;
            this.c.put(var5, var4);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public boolean a(String var1, L1PcInstance var2, L1NpcInstance var3) {
      if (!this.c.containsKey(var3.z() + "-" + var1)) {
         return false;
      }

      HtmlTable.L1R_a var4 = this.c.get(var3.z() + "-" + var1);
      String[] var5 = var4.f.split(",");
      if (var2.j().g(var4.c, var4.d)) {
         var2.a(new S_Html(var3.fr(), var4.e, var5));
      } else {
         L1Item var6 = ItemTable.a().a(var4.c);
         var2.a(new S_ServerMessage(337, var6.i()));
         var2.a(new S_Html(var3.fr(), var4.g));
      }

      return true;
   }

   private class L1R_a {
      public int a;
      public String b;
      public int c;
      public int d;
      public String e;
      public String f;
      public String g;

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(HtmlTable.L1R_a var2) {
         this();
      }
   }
}
