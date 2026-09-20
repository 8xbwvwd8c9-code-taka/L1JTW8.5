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
import l1r.aq.L1Teleport;
import l1r.be.S_Html;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Item;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class HtmlTeleportTable {
   private static final Logger a = Logger.getLogger(HtmlTeleportTable.class.getName());
   private static HtmlTeleportTable b;
   private final HashMap<String, HtmlTeleportTable.a> c = new HashMap<>();

   public static HtmlTeleportTable a() {
      if (b == null) {
         b = new HtmlTeleportTable();
      }

      return b;
   }

   private HtmlTeleportTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM html_teleport");
         var3 = var2.executeQuery();

         while (var3.next()) {
            HtmlTeleportTable.a var4 = new HtmlTeleportTable.a(null);
            var4.a = var3.getInt("npcid");
            var4.b = var3.getString("action");
            var4.c = var3.getInt("x");
            var4.d = var3.getInt("y");
            var4.e = var3.getInt("mapid");
            var4.f = var3.getInt("heading");
            var4.g = var3.getInt("itemid");
            var4.h = var3.getInt("count");
            var4.i = var3.getInt("min_level");
            var4.j = var3.getInt("max_level");
            var4.k = var3.getString("fail_html");
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
      } else {
         HtmlTeleportTable.a var4 = this.c.get(var3.z() + "-" + var1);
         if (!var2.j().g(var4.g, var4.h)) {
            L1Item var5 = ItemTable.a().a(var4.g);
            var2.a(new S_ServerMessage(337, var5.i()));
            var2.a(new S_Html(var3.fr(), var4.k));
            return true;
         } else if (var2.ev() < var4.i) {
            var2.a(new S_ServerMessage(2738));
            var2.a(new S_Html(var3.fr(), var4.k));
            return true;
         } else if (var2.ev() > var4.j) {
            var2.a(new S_ServerMessage(2797));
            var2.a(new S_Html(var3.fr(), var4.k));
            return true;
         } else {
            var2.j().b(var4.g, var4.h);
            L1Teleport.a(var2, var4.c, var4.d, var4.e, var4.f, true, var3.D().equals(""));
            return true;
         }
      }
   }

   private class a {
      public int a;
      public String b;
      public int c;
      public int d;
      public int e;
      public int f;
      public int g;
      public int h;
      public int i;
      public int j;
      public String k;

      private a() {
      }

      // $VF: synthetic method
      a(HtmlTeleportTable.a var2) {
         this();
      }
   }
}
