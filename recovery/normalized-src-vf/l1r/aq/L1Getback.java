package l1r.aq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1Getback {
   private static final Logger a = Logger.getLogger(L1Getback.class.getName());
   private static HashMap<Integer, ArrayList<L1Getback>> b = new HashMap<>();
   private int c;
   private int d;
   private int e;
   private int f;
   private int g;
   private int h;
   private int i;
   private int j;
   private int k;
   private int l;
   private int m;
   private int n;
   private int o;
   private int p;
   private int q;

   private L1Getback() {
   }

   private boolean b() {
      return this.c != 0 && this.d != 0 && this.e != 0 && this.f != 0;
   }

   public static void a() {
      b.clear();
      Connection var0 = null;
      PreparedStatement var1 = null;
      ResultSet var2 = null;

      try {
         var0 = DatabaseFactory.a().b();
         String var3 = "SELECT * FROM getback ORDER BY area_mapid,area_x1 DESC ";
         var1 = var0.prepareStatement("SELECT * FROM getback ORDER BY area_mapid,area_x1 DESC ");
         var2 = var1.executeQuery();

         while (var2.next()) {
            L1Getback var4 = new L1Getback();
            var4.c = var2.getInt("area_x1");
            var4.d = var2.getInt("area_y1");
            var4.e = var2.getInt("area_x2");
            var4.f = var2.getInt("area_y2");
            var4.g = var2.getInt("area_mapid");
            var4.h = var2.getInt("getback_x1");
            var4.i = var2.getInt("getback_y1");
            var4.j = var2.getInt("getback_x2");
            var4.k = var2.getInt("getback_y2");
            var4.l = var2.getInt("getback_x3");
            var4.m = var2.getInt("getback_y3");
            var4.n = var2.getInt("getback_mapid");
            var4.o = var2.getInt("getback_townid");
            var4.p = var2.getInt("getback_townid_elf");
            var4.q = var2.getInt("getback_townid_darkelf");
            var2.getBoolean("scrollescape");
            ArrayList var5 = b.get(var4.g);
            if (var5 == null) {
               var5 = new ArrayList<>();
               b.put(var4.g, var5);
            }

            var5.add(var4);
         }
      } catch (Exception var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var2, var1, var0);
      }
   }

   public static int[] a(L1PcInstance var0) {
      int[] var1 = new int[]{33082, 33399, 4};
      int var2 = Random.a(3);
      int var3 = var0.fs();
      int var4 = var0.ft();
      int var5 = var0.fp();
      List var6 = b.get(var5);
      if (var6 != null) {
         L1Getback var7 = null;

         for (L1Getback var8 : var6) {
            if (!var8.b()) {
               var7 = var8;
               break;
            }

            if (var8.c <= var3 && var3 <= var8.e && var8.d <= var4 && var4 <= var8.f) {
               var7 = var8;
               break;
            }
         }

         if (var7 == null) {
            return var1;
         }

         var1 = a(var7, var2);
         if (var0.A() && var7.p > 0) {
            var1 = L1TownLocation.a(var7.p);
         } else if (var0.C() && var7.q > 0) {
            var1 = L1TownLocation.a(var7.q);
         } else if (var7.o > 0) {
            var1 = L1TownLocation.a(var7.o);
         }
      }

      return var1;
   }

   private static int[] a(L1Getback var0, int var1) {
      int[] var2 = new int[3];
      switch (var1) {
         case 0:
            var2[0] = var0.h;
            var2[1] = var0.i;
            break;
         case 1:
            var2[0] = var0.j;
            var2[1] = var0.k;
            break;
         case 2:
            var2[0] = var0.l;
            var2[1] = var0.m;
      }

      var2[2] = var0.n;
      return var2;
   }
}
