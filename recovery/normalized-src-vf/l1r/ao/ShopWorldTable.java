package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class ShopWorldTable {
   private static final Logger a = Logger.getLogger(ShopWorldTable.class.getName());
   private static ShopWorldTable b;
   private final HashMap<Integer, ShopWorldTable.b> c = new HashMap<>();
   private final ConcurrentHashMap<String, ShopWorldTable.a> d = new ConcurrentHashMap<>();

   public static ShopWorldTable a() {
      if (b == null) {
         b = new ShopWorldTable();
      }

      return b;
   }

   private ShopWorldTable() {
      this.d();
      this.c();
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM shop_world");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("itemid");
            L1ItemInstance var5 = ItemTable.a().b(var4);
            ShopWorldTable.b var6 = new ShopWorldTable.b(var5, null);
            var6.b = var3.getInt("price");
            var6.c = var3.getInt("type");
            var6.d = var3.getInt("vip");
            var6.e = var3.getBoolean("isHot");
            var6.f = var3.getBoolean("isNew");
            this.c.put(var5.N(), var6);
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public HashMap<Integer, ShopWorldTable.b> b() {
      return this.c;
   }

   public ConcurrentHashMap<Integer, L1ItemInstance> a(String var1) {
      return this.d.containsKey(var1) ? this.d.get(var1).b : new ConcurrentHashMap<>();
   }

   private void d() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM character_shop");
         var3 = var2.executeQuery();

         while (var3.next()) {
            String var4 = var3.getString("acc_name");
            ShopWorldTable.a var5 = null;
            if (this.d.containsKey(var4)) {
               var5 = this.d.get(var4);
            } else {
               var5 = new ShopWorldTable.a(null);
               this.d.put(var4, var5);
            }

            int var6 = var3.getInt("indexid");
            int var7 = var3.getInt("itemid");
            L1ItemInstance var8 = ItemTable.a().b(var7);
            var5.b.put(var6, var8);
         }
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(String var1, int var2, int var3) {
      Connection var4 = null;
      PreparedStatement var5 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement("INSERT INTO character_shop SET acc_name=?,itemid=?,indexid=?");

         for (int var6 = 0; var6 < var3; var6++) {
            L1ItemInstance var7 = ItemTable.a().b(var2);
            int var9 = 1;
            ShopWorldTable.a var8;
            if (this.d.containsKey(var1)) {
               var8 = this.d.get(var1);

               for (int var10 = 1; var10 < Integer.MAX_VALUE; var10++) {
                  if (!var8.b.containsKey(var10)) {
                     var9 = var10;
                     break;
                  }
               }
            } else {
               var8 = new ShopWorldTable.a(null);
               this.d.put(var1, var8);
            }

            var8.b.put(var9, var7);
            var5.setString(1, var1);
            var5.setInt(2, var2);
            var5.setInt(3, var9);
            var5.execute();
         }
      } catch (SQLException var14) {
         a.log(Level.SEVERE, var14.getLocalizedMessage(), var14);
      } finally {
         SQLUtil.a(var5);
         SQLUtil.a(var4);
      }
   }

   public void a(String var1, int var2) {
      if (this.d.containsKey(var1)) {
         ShopWorldTable.a var3 = this.d.get(var1);
         var3.b.remove(var2);
      } else {
         System.out.println("ShopWorldTable has smoe error");
      }

      Connection var11 = null;
      PreparedStatement var4 = null;

      try {
         var11 = DatabaseFactory.a().b();
         var4 = var11.prepareStatement("DELETE FROM character_shop WHERE acc_name=? AND indexid=?");
         var4.setString(1, var1);
         var4.setInt(2, var2);
         var4.execute();
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var11);
      }
   }

   private class a {
      private final ConcurrentHashMap<Integer, L1ItemInstance> b = new ConcurrentHashMap<>();

      private a() {
      }

      // $VF: synthetic method
      a(ShopWorldTable.a var2) {
         this();
      }
   }

   public class b {
      public L1ItemInstance a;
      public int b;
      public int c;
      public int d;
      public boolean e;
      public boolean f;

      private b(L1ItemInstance var2) {
         this.a = var2;
      }

      // $VF: synthetic method
      b(L1ItemInstance var2, ShopWorldTable.b var3) {
         this(var2);
      }
   }
}
