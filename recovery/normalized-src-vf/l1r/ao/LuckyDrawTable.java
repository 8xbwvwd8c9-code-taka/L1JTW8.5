package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Item;
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

   public boolean redeemTickets(L1PcInstance var1, String var2, Set<Integer> var3) {
      if (var1 == null || var2 == null || var3 == null || var3.isEmpty()) {
         return false;
      }

      LuckyDrawTable.L1R_a var4 = this.e.get(var2);
      if (var4 == null) {
         return false;
      }

      HashMap<Integer, L1ItemInstance> var5 = var4.c;
      synchronized (var5) {
         for (int var6 : var3) {
            if (!var5.containsKey(var6)) {
               return false;
            }
         }

         int var7 = var3.size();
         L1Item var8 = ItemTable.a().a(640106);
         if (var8 == null || !var8.aF()) {
            return false;
         }

         L1ItemInstance var9 = var1.j().b(640106);
         int var10 = var9 == null ? 0 : var9.E();
         long var11 = (long)var10 + (long)var7;
         if (var11 <= 0L || var11 > 2000000000L) {
            return false;
         }

         L1ItemInstance var12 = null;
         if (var9 == null) {
            var12 = new L1ItemInstance(var8, var7);
            var12.cF(IdFactory.a().d());
            var12.g(var8.aM());
            var12.j(var8.T());
            var12.n();
         }

         try (Connection var13 = DatabaseFactory.a().b()) {
            boolean var14 = var13.getAutoCommit();
            boolean var15 = false;
            boolean var16 = false;

            try (PreparedStatement var17 = var13.prepareStatement(
               "SELECT TABLE_NAME, ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('character_luckydraw','character_items')"
            ); ResultSet var18 = var17.executeQuery()) {
               while (var18.next()) {
                  String var19 = var18.getString("TABLE_NAME");
                  String var20 = var18.getString("ENGINE");
                  if ("character_luckydraw".equalsIgnoreCase(var19)) {
                     var15 = "InnoDB".equalsIgnoreCase(var20);
                  } else if ("character_items".equalsIgnoreCase(var19)) {
                     var16 = "InnoDB".equalsIgnoreCase(var20);
                  }
               }
            }

            if (!var15 || !var16) {
               a.log(Level.SEVERE, "BUG-850-048 requires InnoDB character_luckydraw + character_items");
               return false;
            }

            var13.setAutoCommit(false);

            try {
               try (PreparedStatement var21 = var13.prepareStatement(
                  "DELETE FROM character_luckydraw WHERE acc_name=? AND indexid=?"
               )) {
                  for (int var22 : var3) {
                     var21.setString(1, var2);
                     var21.setInt(2, var22);
                     if (var21.executeUpdate() != 1) {
                        throw new SQLException("BUG-850-048 lucky draw key CAS failed");
                     }
                  }
               }

               CharacterItemTable var23 = CharacterItemTable.a();
               if (var9 == null) {
                  var23.insertQuestReward(var13, var1.fr(), var12);
               } else {
                  var23.updateQuestRewardCount(var13, var1.fr(), var9, var10, (int)var11);
               }

               var13.commit();
            } catch (Exception var26) {
               try {
                  var13.rollback();
               } catch (SQLException var25) {
               }
               try {
                  var13.setAutoCommit(var14);
               } catch (SQLException var24) {
               }
               return false;
            }

            try {
               var13.setAutoCommit(var14);
            } catch (SQLException var27) {
            }

            for (int var28 : var3) {
               var5.remove(var28);
            }

            if (var9 == null) {
               var1.j().publishCommittedQuestInsert(var12);
            } else {
               var1.j().publishCommittedQuestUpdate(var9, (int)var11);
            }
            return true;
         } catch (SQLException var29) {
            a.log(Level.SEVERE, var29.getLocalizedMessage(), var29);
            return false;
         }
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
