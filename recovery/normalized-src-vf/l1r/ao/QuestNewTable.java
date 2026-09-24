package l1r.ao;

import a.g;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL7;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Item;
import l1r.bh.L1QuestNew;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class QuestNewTable {
   private static final Logger a = Logger.getLogger(QuestNewTable.class.getName());
   private static QuestNewTable b;
   private final HashMap<Integer, L1QuestNew> c = new HashMap<>();

   public static QuestNewTable a() {
      if (b == null) {
         b = new QuestNewTable();
      }

      return b;
   }

   private QuestNewTable() {
      int[] var1 = new int[]{
         256,
         257,
         258,
         259,
         260,
         261,
         271,
         272,
         273,
         274,
         275,
         276,
         277,
         278,
         279,
         280,
         281,
         282,
         283,
         284,
         285,
         286,
         287,
         288,
         289,
         290,
         291,
         292,
         293,
         294,
         299,
         306,
         314,
         317,
         318,
         319,
         320,
         321,
         322,
         323,
         324,
         325,
         326,
         328,
         334,
         336,
         338,
         340,
         341,
         342,
         343,
         346,
         349
      };
      int[] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         int var2 = var5[var3];
         L1QuestNew var6 = new L1QuestNew(var2);
         this.c.put(var2, var6);
      }
   }

   public void a(L1PcInstance var1) {
      for (int var2 : this.c.keySet()) {
         if (!var1.dS().containsKey(var2)) {
            L1QuestNew var4 = this.c.get(var2);
            if (var1.ev() >= var4.b()
               && var1.ev() <= var4.c()
               && var1.fp() == var4.d()
               && (var4.m().equalsIgnoreCase("A") || var4.m().equalsIgnoreCase(var1.aC().h()))) {
               L1QuestNew var5 = new L1QuestNew(var2);
               var5.a(var1);
               if (var5.n() > 0) {
                  var5.a(var1.ev());
               }

               var1.dS().put(var2, var5);
               var1.a(new S_ProtoBuffers(518, var5));

               for (int objectiveIndex = 0; objectiveIndex < var5.r().length; objectiveIndex++) {
                  long inventoryCount = 0L;

                  for (L1ItemInstance inventoryItem : var1.j().d()) {
                     if (var5.r()[objectiveIndex] == inventoryItem.N()
                        && var5.t()[objectiveIndex] <= inventoryItem.G()
                        && inventoryItem.E() > 0) {
                        inventoryCount += inventoryItem.E();
                        if (inventoryCount >= var5.s()[objectiveIndex]) {
                           inventoryCount = var5.s()[objectiveIndex];
                           break;
                        }
                     }
                  }

                  var5.a(objectiveIndex, (int)inventoryCount);
               }
            }
         }
      }
   }

   public void b(L1PcInstance var1) {
      this.d(var1);
      this.a(var1);
   }

   private void d(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM character_quests_new WHERE objid=?");
         var3.setInt(1, var1.fr());
         var4 = var3.executeQuery();
         if (var4.next()) {
            byte[] var5 = var4.getBytes("data");
            PBMessageALL3.L1R_g var6 = PBMessageALL3.L1R_g.a(var5);

            for (g var7 : var6.o()) {
               PBMessageALL7.L1R_e var9 = PBMessageALL7.L1R_e.a(var7);
               L1QuestNew var10 = new L1QuestNew(var9.p());
               var10.a(var1);
               var10.a(var9.r() == 1);
               var10.b(var9.t() == 1);
               var10.d(var9.v());
               int[] var11 = new int[var9.x()];
               if (var11.length > 0) {
                  for (int var12 = 0; var12 < var11.length; var12++) {
                     var11[var12] = var9.a(var12);
                  }

                  var10.a(var11);
               }

               int[] var20 = new int[var9.z()];
               if (var20.length > 0) {
                  for (int var13 = 0; var13 < var20.length; var13++) {
                     var20[var13] = var9.b(var13);
                  }

                  var10.b(var20);
               }

               int[] var21 = new int[var9.B()];
               if (var21.length > 0) {
                  for (int var14 = 0; var14 < var21.length; var14++) {
                     var21[var14] = var9.c(var14);
                  }

                  var10.c(var21);
               }

               var1.dS().put(var10.a(), var10);
               if (!var10.w() && var1.fp() == var10.d()) {
                  var1.a(new S_ProtoBuffers(518, var10));
               }
            }
         } else {
            this.e(var1);
         }
      } catch (Exception var18) {
         a.log(Level.SEVERE, var18.getLocalizedMessage(), var18);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }
   }

   private void e(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO character_quests_new SET objid=?, data=?");
         var3.setInt(1, var1.fr());
         var3.setBytes(2, this.f(var1));
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private byte[] f(L1PcInstance var1) {
      PBMessageALL3.L1R_g.L1R_a var2 = PBMessageALL3.L1R_g.aa();

      for (L1QuestNew var3 : var1.dS().values()) {
         PBMessageALL7.L1R_e.L1R_a var5 = PBMessageALL7.L1R_e.aa();
         var5.f(var3.a());
         var5.g(var3.w() ? 1 : 0);
         var5.h(var3.x() ? 1 : 0);
         var5.i(var3.z());
         int[] var9;
         int var8 = (var9 = var3.A()).length;

         for (int var7 = 0; var7 < var8; var7++) {
            int var6 = var9[var7];
            var5.j(var6);
         }

         var8 = (var9 = var3.B()).length;

         for (int var12 = 0; var12 < var8; var12++) {
            int var10 = var9[var12];
            var5.k(var10);
         }

         var8 = (var9 = var3.C()).length;

         for (int var13 = 0; var13 < var8; var13++) {
            int var11 = var9[var13];
            var5.l(var11);
         }

         var2.e(var5.M().f());
      }

      return var2.M().g();
   }

   public void c(L1PcInstance var1) {
      synchronized (var1.dS()) {
         Connection var2 = null;
         PreparedStatement var3 = null;

         try {
            var2 = DatabaseFactory.a().b();
            var3 = var2.prepareStatement("INSERT INTO character_quests_new (objid,data) VALUES (?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
            var3.setBytes(2, this.f(var1));
            var3.setInt(1, var1.fr());
            var3.execute();
         } catch (SQLException var8) {
            a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
         } finally {
            SQLUtil.a(var3);
            SQLUtil.a(var2);
         }
      }
   }

   public boolean claimReward(L1PcInstance var1, L1QuestNew var2, int var3) {
      if (var1 == null || var2 == null) {
         return false;
      }

      synchronized (var2) {
         synchronized (var1) {
            synchronized (var1.dS()) {
               synchronized (var1.j()) {
               if (var2.w() || !var2.x()) {
                  return false;
               }

               QuestRewardPlan var4 = this.buildQuestRewardPlan(var1, var2, var3);
               if (var4 == null) {
                  return false;
               }

               int var5 = 0;
               if (var2.l() > 0) {
                  double var6 = ExpTable.d(var1.ev());
                  var5 = (int)(var2.l() * var6);
                  if (var5 < 0) {
                     return false;
                  }
               }

               int var7 = var1.m();
               int var8 = (int)Math.min(1859065562L, (long)var7 + (long)var5);
               byte[] var9;
               var2.a(true);

               try {
                  var9 = this.f(var1);
               } catch (RuntimeException var23) {
                  var2.a(false);
                  throw var23;
               }

               Connection var10 = null;
               boolean var11 = true;
               boolean var12 = false;

               try {
                  var10 = DatabaseFactory.a().b();
                  var11 = var10.getAutoCommit();
                  this.requireInnoDb(var10, "character_items");
                  this.requireInnoDb(var10, "character_quests_new");
                  this.requireInnoDb(var10, "characters");
                  var10.setAutoCommit(false);
                  CharacterItemTable var13 = CharacterItemTable.a();

                  for (QuestItemMutation var14 : var4.existing.values()) {
                     if (var14.newCount == var14.oldCount) {
                        continue;
                     }

                     if (var14.newCount < 0) {
                        throw new SQLException("BUG-850-275 negative item count");
                     }

                     if (var14.newCount == 0) {
                        var13.deleteQuestRewardItem(var10, var1.fr(), var14.item, var14.oldCount);
                     } else {
                        var13.updateQuestRewardCount(var10, var1.fr(), var14.item, var14.oldCount, var14.newCount);
                     }
                  }

                  for (L1ItemInstance var15 : var4.inserts) {
                     var13.insertQuestReward(var10, var1.fr(), var15);
                  }

                  try (PreparedStatement var16 = var10.prepareStatement(
                     "UPDATE character_quests_new SET data=? WHERE objid=?"
                  )) {
                     var16.setBytes(1, var9);
                     var16.setInt(2, var1.fr());
                     if (var16.executeUpdate() != 1) {
                        throw new SQLException("BUG-850-275 quest claimed persistence failed");
                     }
                  }

                  if (var5 > 0) {
                     try (PreparedStatement var17 = var10.prepareStatement(
                        "UPDATE characters SET Exp=? WHERE objid=?"
                     )) {
                        var17.setInt(1, var8);
                        var17.setInt(2, var1.fr());
                        if (var17.executeUpdate() != 1) {
                           throw new SQLException("BUG-850-275 EXP persistence failed");
                        }
                     }
                  }

                  var10.commit();
                  var12 = true;
               } catch (Exception var21) {
                  if (var10 != null) {
                     try {
                        var10.rollback();
                     } catch (SQLException var20) {
                        a.log(Level.SEVERE, var20.getLocalizedMessage(), var20);
                     }
                  }

                  a.log(Level.SEVERE, "BUG-850-275 quest reward transaction failed", var21);
               } finally {
                  if (var10 != null) {
                     try {
                        var10.setAutoCommit(var11);
                     } catch (SQLException var19) {
                        a.log(Level.SEVERE, var19.getLocalizedMessage(), var19);
                     }
                  }

                  SQLUtil.a(var10);
               }

               if (!var12) {
                  var2.a(false);
                  return false;
               }

               try {
                  for (QuestItemMutation var22 : var4.existing.values()) {
                     if (var22.newCount == var22.oldCount) {
                        continue;
                     }

                     if (var22.newCount == 0) {
                        var1.j().publishCommittedQuestDelete(var22.item);
                     } else {
                        var1.j().publishCommittedQuestUpdate(var22.item, var22.newCount);
                     }
                  }

                  for (L1ItemInstance var24 : var4.inserts) {
                     var1.j().publishCommittedQuestInsert(var24);
                  }

                  if (var5 > 0) {
                     var1.k(var8);
                  }

                  for (String var25 : var4.notices) {
                     var1.a(new S_ServerMessage(403, var25));
                  }

                  var1.a(new S_ProtoBuffers(525, var2.a()));
               } catch (RuntimeException var18) {
                  a.log(Level.SEVERE, "BUG-850-275 committed reward live publication failed; relog restores durable state", var18);
               }

               return true;
               }
            }
         }
      }
   }

   private QuestRewardPlan buildQuestRewardPlan(L1PcInstance var1, L1QuestNew var2, int var3) {
      QuestRewardPlan var4 = new QuestRewardPlan(var1.j().e());
      if (var3 < -1
         || var2.f() == null
         || var2.g() == null
         || var2.h() == null
         || var2.f().length != var2.g().length
         || var2.f().length != var2.h().length) {
         return null;
      }

      for (int var5 = 0; var5 < var2.f().length; var5++) {
         if (!this.addQuestReward(var1, var4, var2.f()[var5], var2.g()[var5], var2.h()[var5])) {
            return null;
         }
      }

      if (var3 >= 0) {
         if (var2.i() == null
            || var2.j() == null
            || var2.k() == null
            || var3 >= var2.i().length
            || var3 >= var2.j().length
            || var3 >= var2.k().length
            || !this.addQuestReward(var1, var4, var2.i()[var3], var2.j()[var3], var2.k()[var3])) {
            return null;
         }
      }

      if (var1.j().c() + var4.newSlots > 180 || (double)var4.projectedWeight >= var1.K()) {
         return null;
      }

      if (var2.r().length > 0 && var2.o()) {
         if (var2.r().length != var2.s().length || var2.r().length != var2.t().length) {
            return null;
         }

         for (int var6 = 0; var6 < var2.r().length; var6++) {
            if (!this.consumeQuestRequirement(var1, var4, var2.r()[var6], var2.t()[var6], var2.s()[var6])) {
               return null;
            }
         }
      }

      return var4;
   }

   private boolean addQuestReward(L1PcInstance var1, QuestRewardPlan var2, int var3, int var4, int var5) {
      if (var4 <= 0 || var3 == 40312 || var3 == 413 || var3 == 21446) {
         return false;
      }

      L1Item var6 = ItemTable.a().a(var3);
      if (var6 == null) {
         return false;
      }

      L1ItemInstance var7 = new L1ItemInstance(var6, var4);
      var7.a(var5);
      var2.notices.add(var7.s());
      long var8 = (long)var6.l() * (long)var4 / 1000L + 1L;
      var2.projectedWeight += var8;

      if (var6.aF()) {
         L1ItemInstance var10 = var1.j().d(var3, var7.F());
         if (var10 != null) {
            QuestItemMutation var11 = var2.existing.get(var10.fr());
            if (var11 == null) {
               var11 = new QuestItemMutation(var10);
               var2.existing.put(var10.fr(), var11);
            }

            long var12 = (long)var11.newCount + (long)var4;
            if (var12 > 2000000000L) {
               return false;
            }

            var11.newCount = (int)var12;
            return true;
         }

         String var15 = var3 + ":" + var7.F();
         L1ItemInstance var16 = var2.newStacks.get(var15);
         if (var16 != null) {
            long var17 = (long)var16.E() + (long)var4;
            if (var17 > 2000000000L) {
               return false;
            }

            var16.e((int)var17);
            return true;
         }

         var7.cF(IdFactory.a().d());
         var2.inserts.add(var7);
         var2.newStacks.put(var15, var7);
         var2.newSlots++;
         return true;
      }

      for (int var13 = 0; var13 < var4; var13++) {
         L1ItemInstance var14 = new L1ItemInstance(var6, 1);
         var14.cF(IdFactory.a().d());
         var14.a(var5);
         var14.n();
         var2.inserts.add(var14);
         var2.newSlots++;
      }

      return true;
   }

   private boolean consumeQuestRequirement(L1PcInstance var1, QuestRewardPlan var2, int var3, int var4, int var5) {
      if (var5 <= 0) {
         return false;
      }

      int var6 = var5;

      for (L1ItemInstance var7 : var1.j().d()) {
         if (var7.D() || var7.N() != var3 || var7.G() != var4) {
            continue;
         }

         QuestItemMutation var8 = var2.existing.get(var7.fr());
         if (var8 == null) {
            var8 = new QuestItemMutation(var7);
            var2.existing.put(var7.fr(), var8);
         }

         int var9 = Math.max(0, var8.oldCount - var8.consumed);
         if (var9 <= 0) {
            continue;
         }

         int var10 = var7.d() ? Math.min(var6, var9) : 1;
         var8.consumed += var10;
         var8.newCount -= var10;
         var6 -= var10;
         if (var6 == 0) {
            return true;
         }
      }

      return false;
   }

   private void requireInnoDb(Connection var1, String var2) throws SQLException {
      try (PreparedStatement var3 = var1.prepareStatement(
         "SELECT ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=?"
      )) {
         var3.setString(1, var2);
         try (ResultSet var4 = var3.executeQuery()) {
            if (!var4.next() || !"InnoDB".equalsIgnoreCase(var4.getString("ENGINE"))) {
               throw new SQLException("BUG-850-275 requires InnoDB table: " + var2);
            }
         }
      }
   }

   private static final class QuestRewardPlan {
      private final LinkedHashMap<Integer, QuestItemMutation> existing = new LinkedHashMap<>();
      private final List<L1ItemInstance> inserts = new ArrayList<>();
      private final LinkedHashMap<String, L1ItemInstance> newStacks = new LinkedHashMap<>();
      private final List<String> notices = new ArrayList<>();
      private int newSlots = 0;
      private long projectedWeight;

      private QuestRewardPlan(long var1) {
         this.projectedWeight = var1;
      }
   }

   private static final class QuestItemMutation {
      private final L1ItemInstance item;
      private final int oldCount;
      private int newCount;
      private int consumed = 0;

      private QuestItemMutation(L1ItemInstance var1) {
         this.item = var1;
         this.oldCount = var1.E();
         this.newCount = this.oldCount;
      }
   }

   public HashMap<Integer, L1QuestNew> b() {
      return this.c;
   }
}
