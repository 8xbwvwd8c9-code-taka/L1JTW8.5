package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class LuckyDrawTable {
   private static final Logger a = Logger.getLogger(LuckyDrawTable.class.getName());
   private static LuckyDrawTable b;
   private final ArrayList<LuckyDrawTable.L1R_b> c = new ArrayList<>();
   private int d = 0;
   private final HashMap<String, LuckyDrawTable.L1R_a> e = new HashMap<>();

   public static LuckyDrawTable a() {
      if (b == null) {
         b = new LuckyDrawTable();
      }

      return b;
   }

   private LuckyDrawTable() {
      this.b();
      this.c();
      this.d();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM character_luckydraw");
         var3 = var2.executeQuery();

         while (var3.next()) {
            String var4 = var3.getString("acc_name");
            if (!this.e(var4)) {
               this.d(var4);
            }
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void d(String var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM character_luckydraw WHERE acc_name=? ");
         var3.setString(1, var1);
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private boolean e(String var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         String var5 = "SELECT * FROM accounts WHERE login=? LIMIT 1";
         var3 = var2.prepareStatement("SELECT * FROM accounts WHERE login=? LIMIT 1");
         var3.setString(1, var1);
         var4 = var3.executeQuery();
         return var4.next();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return true;
   }

   public L1ItemInstance a(String var1) {
      L1ItemInstance var2 = null;
      int var3 = Random.a(this.d);

      for (LuckyDrawTable.L1R_b var4 : this.c) {
         if (var4.e <= var3 && var4.f >= var3) {
            var2 = ItemTable.a().b(var4.a);
            var2.a(var4.b);
            var2.e(var4.c);
            var2.a(true);
            if (Random.a(100) < var4.d) {
               var2.f(0);
            }

            if (var4.f - var4.e <= 5) {
               L1World.a().a(new S_ServerMessage(var1, var2));
            }

            return var2;
         }
      }

      return null;
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM luckydraw");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("itemid");
            int var5 = var3.getInt("enchant");
            int var6 = var3.getInt("count");
            int var7 = var3.getInt("prab_value");
            int var8 = var3.getInt("bless_chance");
            LuckyDrawTable.L1R_b var9 = new LuckyDrawTable.L1R_b(var4, var5, var6, var8);
            var9.e = this.d + 1;
            this.d += var7;
            var9.f = this.d;
            this.c.add(var9);
         }
      } catch (SQLException var13) {
         a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public int b(String var1) {
      return this.e.containsKey(var1) ? this.e.get(var1).b + 1 : 1;
   }

   public HashMap<Integer, L1ItemInstance> c(String var1) {
      return this.e.containsKey(var1) ? this.e.get(var1).c : new HashMap<>();
   }

   private void d() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM character_luckydraw");
         var3 = var2.executeQuery();

         while (var3.next()) {
            String var4 = var3.getString("acc_name");
            int var5 = var3.getInt("indexid");
            int var6 = var3.getInt("itemid");
            int var7 = var3.getInt("enchant");
            int var8 = var3.getInt("count");
            LuckyDrawTable.L1R_a var9 = null;
            if (this.e.containsKey(var4)) {
               var9 = this.e.get(var4);
            } else {
               var9 = new LuckyDrawTable.L1R_a(null);
               this.e.put(var4, var9);
            }

            var9.b = Math.max(var9.b, var5);
            L1ItemInstance var10 = ItemTable.a().b(var6);
            var10.e(var8);
            var10.a(var7);
            var9.c.put(var5, var10);
         }
      } catch (SQLException var14) {
         a.log(Level.SEVERE, var14.getLocalizedMessage(), var14);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(String var1, HashMap<Integer, L1ItemInstance> var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();

         for (int var5 : var2.keySet()) {
            L1ItemInstance var7 = var2.get(var5);
            LuckyDrawTable.L1R_a var8;
            if (this.e.containsKey(var1)) {
               var8 = this.e.get(var1);
            } else {
               var8 = new LuckyDrawTable.L1R_a(null);
               this.e.put(var1, var8);
            }

            var8.b = Math.max(var8.b, var5);
            var8.c.put(var5, var7);
            var4 = var3.prepareStatement("INSERT INTO character_luckydraw SET acc_name=?,itemid=?,indexid=?,enchant=?,count=?,bless=?");
            var4.setString(1, var1);
            var4.setInt(2, var7.N());
            var4.setInt(3, var5);
            var4.setInt(4, var7.G());
            var4.setInt(5, var7.E());
            var4.setInt(6, var7.F());
            var4.execute();
         }
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   public void a(String var1, int var2) {
      if (this.e.containsKey(var1)) {
         LuckyDrawTable.L1R_a var3 = this.e.get(var1);
         var3.c.remove(var2);
      } else {
         System.out.println("LuckyDrawTable has smoe error");
      }

      Connection var11 = null;
      PreparedStatement var4 = null;

      try {
         var11 = DatabaseFactory.a().b();
         var4 = var11.prepareStatement("DELETE FROM character_luckydraw WHERE acc_name=? AND indexid=?");
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
      private int b = 0;
      private final HashMap<Integer, L1ItemInstance> c = new HashMap<>();

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(LuckyDrawTable.L1R_a var2) {
         this();
      }
   }

   private class L1R_b {
      public int a;
      public int b;
      public int c;
      public int d = 0;
      public int e = 0;
      public int f = 0;

      public L1R_b(int var2, int var3, int var4, int var5) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
         this.d = var5;
      }
   }
}
