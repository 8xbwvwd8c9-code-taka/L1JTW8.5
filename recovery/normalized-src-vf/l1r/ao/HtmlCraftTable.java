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
import l1r.aq.L1World;
import l1r.be.S_HowManyMake;
import l1r.be.S_Html;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Item;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class HtmlCraftTable {
   private static final Logger a = Logger.getLogger(HtmlCraftTable.class.getName());
   private static HtmlCraftTable b;
   private final HashMap<String, HtmlCraftTable.L1R_a> c = new HashMap<>();

   public static HtmlCraftTable a() {
      if (b == null) {
         b = new HtmlCraftTable();
      }

      return b;
   }

   private HtmlCraftTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM html_craft");
         var3 = var2.executeQuery();

         while (var3.next()) {
            HtmlCraftTable.L1R_a var4 = new HtmlCraftTable.L1R_a(null);
            var4.a = var3.getString("action");
            var4.b = var3.getInt("npcid");
            var4.c = this.a(var3.getString("craft_itemid"));
            var4.d = this.a(var3.getString("craft_count"));
            var4.e = this.a(var3.getString("material"));
            var4.f = this.a(var3.getString("material_count"));
            var4.g = var3.getString("success_html");
            var4.h = var3.getString("fail_html");
            var4.i = var3.getInt("isInputable") == 1;
            String var5 = var4.a + "-" + var4.b;
            this.c.put(var5, var4);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private int[] a(String var1) {
      if (var1.endsWith(",")) {
         var1 = var1.substring(0, var1.length() - 1);
      }

      String[] var2 = var1.split(",");
      int[] var3 = new int[var2.length];

      try {
         for (int var4 = 0; var4 < var2.length; var4++) {
            var3[var4] = Integer.parseInt(var2[var4]);
         }
      } catch (NumberFormatException var5) {
         System.out.println("--------DB:html_craft Error:[" + var1 + "]--------");
      }

      return var3;
   }

   public boolean a(String var1, L1PcInstance var2, L1NpcInstance var3, int var4) {
      HtmlCraftTable.L1R_a var5 = this.c.get(var1 + "-" + var3.z());
      if (var5 == null) {
         var5 = this.c.get(var1 + "-0");
      }

      if (var5 == null) {
         return false;
      }

      boolean var6 = true;

      for (int var7 = 0; var7 < var5.e.length; var7++) {
         if (!var2.j().h(var5.e[var7], var5.f[var7] * var4)) {
            L1Item var8 = ItemTable.a().a(var5.e[var7]);
            var2.a(new S_ServerMessage(337, var8.j() + "(" + var5.f[var7] * var4 + ")"));
            var6 = false;
         }
      }

      if (!var6) {
         var2.a(new S_Html(var3.fr(), var5.h));
         return true;
      }

      for (int var9 = 0; var9 < var5.e.length; var9++) {
         var2.j().b(var5.e[var9], var5.f[var9] * var4);
      }

      for (int var10 = 0; var10 < var5.c.length; var10++) {
         ItemTable.a(var2, var5.c[var10], var5.d[var10] * var4);
         if (var5.c[var10] >= 640626 && var5.c[var10] <= 640637) {
            L1World.a().a(new S_ServerMessage(2922));
         }
      }

      var2.a(new S_Html(var3.fr(), var5.g));
      return true;
   }

   public boolean a(String var1, L1PcInstance var2, L1NpcInstance var3) {
      HtmlCraftTable.L1R_a var4 = this.c.get(var1 + "-" + var3.z());
      if (var4 == null) {
         var4 = this.c.get(var1 + "-0");
      }

      if (var4 == null) {
         return false;
      }

      if (var4.i) {
         int var5 = this.a(var2, var4);
         if (var5 > 1) {
            var2.a(new S_HowManyMake(var3.fr(), var5, var1));
            return true;
         }
      }

      return this.a(var1, var2, var3, 1);
   }

   private int a(L1PcInstance var1, HtmlCraftTable.L1R_a var2) {
      int var3 = Integer.MAX_VALUE;

      for (int var4 = 0; var4 < var2.e.length; var4++) {
         int var5 = var1.j().g(var2.e[var4]) / var2.f[var4];
         var3 = Math.min(var3, var5);
      }

      return var3;
   }

   private class L1R_a {
      public String a;
      public int b;
      public int[] c;
      public int[] d;
      public int[] e;
      public int[] f;
      public String g;
      public String h;
      public boolean i;

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(HtmlCraftTable.L1R_a var2) {
         this();
      }
   }
}
