package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
   private final HashMap<Integer, ShopWorldTable.L1R_b> c = new HashMap<>();
   private final ConcurrentHashMap<String, ShopWorldTable.L1R_a> d = new ConcurrentHashMap<>();

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
            ShopWorldTable.L1R_b var6 = new ShopWorldTable.L1R_b(var5, null);
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

   public HashMap<Integer, ShopWorldTable.L1R_b> b() {
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
            ShopWorldTable.L1R_a var5 = null;
            if (this.d.containsKey(var4)) {
               var5 = this.d.get(var4);
            } else {
               var5 = new ShopWorldTable.L1R_a(null);
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

   public synchronized boolean a(String accountName, int expectedBalance, int newBalance, int itemId, int count) {
      if (accountName == null || count <= 0 || newBalance < 0 || newBalance > expectedBalance) {
         return false;
      }

      ShopWorldTable.L1R_a data = this.d.get(accountName);
      ArrayList<Integer> indexes = new ArrayList<>();
      ArrayList<L1ItemInstance> items = new ArrayList<>();

      for (int n = 0; n < count; n++) {
         L1ItemInstance item = ItemTable.a().b(itemId);
         if (item == null) {
            return false;
         }

         int index = 1;
         while (index < Integer.MAX_VALUE) {
            if ((data == null || !data.b.containsKey(index)) && !indexes.contains(index)) {
               break;
            }
            index++;
         }
         if (index == Integer.MAX_VALUE) {
            return false;
         }

         indexes.add(index);
         items.add(item);
      }

      Connection con = null;
      PreparedStatement engineCheck = null;
      PreparedStatement balanceUpdate = null;
      PreparedStatement insert = null;
      ResultSet rs = null;
      boolean previousAutoCommit = true;

      try {
         con = DatabaseFactory.a().b();
         previousAutoCommit = con.getAutoCommit();
         engineCheck = con.prepareStatement(
            "SELECT TABLE_NAME, ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('accounts','character_shop')"
         );
         rs = engineCheck.executeQuery();
         boolean accountsInnoDb = false;
         boolean characterShopInnoDb = false;

         while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            String engine = rs.getString("ENGINE");
            if ("accounts".equalsIgnoreCase(tableName)) {
               accountsInnoDb = "InnoDB".equalsIgnoreCase(engine);
            } else if ("character_shop".equalsIgnoreCase(tableName)) {
               characterShopInnoDb = "InnoDB".equalsIgnoreCase(engine);
            }
         }

         if (!accountsInnoDb || !characterShopInnoDb) {
            a.log(Level.SEVERE, "ShopWorld purchase requires InnoDB accounts + character_shop; apply BUG-850-283 migration");
            return false;
         }

         con.setAutoCommit(false);

         balanceUpdate = con.prepareStatement(
            "UPDATE accounts SET WorldShopAdena=? WHERE login=? AND WorldShopAdena=?"
         );
         balanceUpdate.setInt(1, newBalance);
         balanceUpdate.setString(2, accountName);
         balanceUpdate.setInt(3, expectedBalance);
         if (balanceUpdate.executeUpdate() != 1) {
            con.rollback();
            return false;
         }

         insert = con.prepareStatement(
            "INSERT INTO character_shop SET acc_name=?,itemid=?,indexid=?"
         );
         for (int n = 0; n < indexes.size(); n++) {
            insert.setString(1, accountName);
            insert.setInt(2, itemId);
            insert.setInt(3, indexes.get(n));
            if (insert.executeUpdate() != 1) {
               throw new SQLException("character_shop insert affected unexpected row count");
            }
         }

         con.commit();

         if (data == null) {
            data = new ShopWorldTable.L1R_a(null);
            this.d.put(accountName, data);
         }
         for (int n = 0; n < indexes.size(); n++) {
            data.b.put(indexes.get(n), items.get(n));
         }
         return true;
      } catch (Exception e) {
         if (con != null) {
            try {
               con.rollback();
            } catch (SQLException rollbackError) {
               a.log(Level.SEVERE, rollbackError.getLocalizedMessage(), rollbackError);
            }
         }
         a.log(Level.SEVERE, e.getLocalizedMessage(), e);
         return false;
      } finally {
         SQLUtil.a(rs);
         SQLUtil.a(engineCheck);
         SQLUtil.a(balanceUpdate);
         SQLUtil.a(insert);
         if (con != null) {
            try {
               con.setAutoCommit(previousAutoCommit);
            } catch (SQLException e) {
               a.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
         }
         SQLUtil.a(con);
      }
   }

   public void requireShopWorldClaimTables(Connection con) throws SQLException {
      PreparedStatement pstm = null;
      ResultSet rs = null;
      try {
         pstm = con.prepareStatement("SELECT TABLE_NAME, ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('character_shop','character_items')");
         rs = pstm.executeQuery();
         boolean characterShopInnoDb = false;
         boolean characterItemsInnoDb = false;
         while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            String engine = rs.getString("ENGINE");
            if ("character_shop".equalsIgnoreCase(tableName)) {
               characterShopInnoDb = "InnoDB".equalsIgnoreCase(engine);
            } else if ("character_items".equalsIgnoreCase(tableName)) {
               characterItemsInnoDb = "InnoDB".equalsIgnoreCase(engine);
            }
         }
         if (!characterShopInnoDb || !characterItemsInnoDb) {
            throw new SQLException("BUG-850-032 requires InnoDB character_shop + character_items");
         }
      } finally {
         SQLUtil.a(rs);
         SQLUtil.a(pstm);
      }
   }

   public boolean lockShopWorldPending(Connection con, String accountName, int index, int itemId) throws SQLException {
      PreparedStatement pstm = null;
      ResultSet rs = null;
      try {
         pstm = con.prepareStatement("SELECT itemid FROM character_shop WHERE acc_name=? AND indexid=? FOR UPDATE");
         pstm.setString(1, accountName);
         pstm.setInt(2, index);
         rs = pstm.executeQuery();
         if (!rs.next() || rs.getInt("itemid") != itemId) {
            return false;
         }
         return !rs.next();
      } finally {
         SQLUtil.a(rs);
         SQLUtil.a(pstm);
      }
   }

   public void deleteShopWorldPending(Connection con, String accountName, int index, int itemId) throws SQLException {
      PreparedStatement pstm = null;
      try {
         pstm = con.prepareStatement("DELETE FROM character_shop WHERE acc_name=? AND indexid=? AND itemid=?");
         pstm.setString(1, accountName);
         pstm.setInt(2, index);
         pstm.setInt(3, itemId);
         if (pstm.executeUpdate() != 1) {
            throw new SQLException("BUG-850-032 pending delete CAS failed");
         }
      } finally {
         SQLUtil.a(pstm);
      }
   }

   public boolean shopWorldPendingExists(String accountName, int index, int itemId) throws SQLException {
      Connection con = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      try {
         con = DatabaseFactory.a().b();
         pstm = con.prepareStatement("SELECT 1 FROM character_shop WHERE acc_name=? AND indexid=? AND itemid=?");
         pstm.setString(1, accountName);
         pstm.setInt(2, index);
         pstm.setInt(3, itemId);
         rs = pstm.executeQuery();
         return rs.next();
      } finally {
         SQLUtil.a(rs, pstm, con);
      }
   }

   public void publishShopWorldPendingClaim(String accountName, int index) {
      ShopWorldTable.L1R_a data = this.d.get(accountName);
      if (data != null) {
         data.b.remove(index);
         if (data.b.isEmpty()) {
            this.d.remove(accountName, data);
         }
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
            ShopWorldTable.L1R_a var8;
            if (this.d.containsKey(var1)) {
               var8 = this.d.get(var1);

               for (int var10 = 1; var10 < Integer.MAX_VALUE; var10++) {
                  if (!var8.b.containsKey(var10)) {
                     var9 = var10;
                     break;
                  }
               }
            } else {
               var8 = new ShopWorldTable.L1R_a(null);
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
         ShopWorldTable.L1R_a var3 = this.d.get(var1);
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

   private class L1R_a {
      private final ConcurrentHashMap<Integer, L1ItemInstance> b = new ConcurrentHashMap<>();

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(ShopWorldTable.L1R_a var2) {
         this();
      }
   }

   public class L1R_b {
      public L1ItemInstance a;
      public int b;
      public int c;
      public int d;
      public boolean e;
      public boolean f;

      private L1R_b(L1ItemInstance var2) {
         this.a = var2;
      }

      // $VF: synthetic method
      L1R_b(L1ItemInstance var2, ShopWorldTable.L1R_b var3) {
         this(var2);
      }
   }
}
