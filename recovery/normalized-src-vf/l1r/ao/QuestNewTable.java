package l1r.ao;

import a.g;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL7;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_ProtoBuffers;
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
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE character_quests_new SET data=? WHERE objid=?");
         var3.setBytes(1, this.f(var1));
         var3.setInt(2, var1.fr());
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public HashMap<Integer, L1QuestNew> b() {
      return this.c;
   }
}
