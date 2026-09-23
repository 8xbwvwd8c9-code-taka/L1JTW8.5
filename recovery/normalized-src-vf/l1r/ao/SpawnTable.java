package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Spawn;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1World;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class SpawnTable {
   private static final Logger a = Logger.getLogger(SpawnTable.class.getName());
   private static SpawnTable b;
   private final HashMap<Integer, L1Spawn> c = new HashMap<>();
   private int d;

   public static SpawnTable a() {
      if (b == null) {
         b = new SpawnTable();
      }

      return b;
   }

   private SpawnTable() {
      long var1 = System.currentTimeMillis();
      System.out.print("spawning mob...");
      this.b();
      System.out.println("OK! " + (System.currentTimeMillis() - var1) + " ms");
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM spawnlist");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var6 = var3.getInt("npc_templateid");
            L1Npc var5 = NpcTable.a().a(var6);
            L1Spawn var4;
            if (var5 == null) {
               System.out.println("mob data for id:" + var6 + " missing in npc table");
               var4 = null;
            } else {
               if (var3.getInt("count") == 0) {
                  continue;
               }

               double var8 = MapsTable.a().a(var3.getShort("mapid"));
               int var7 = a(var5, var3.getInt("count"), var8);
               if (var7 == 0) {
                  continue;
               }

               var4 = new L1Spawn(var5);
               var4.a(var3.getInt("id"));
               var4.b(var7);
               var4.d(var3.getInt("group_id"));
               var4.e(var3.getInt("locx"));
               var4.f(var3.getInt("locy"));
               var4.g(var3.getInt("randomx"));
               var4.h(var3.getInt("randomy"));
               var4.i(var3.getInt("locx1"));
               var4.j(var3.getInt("locy1"));
               var4.k(var3.getInt("locx2"));
               var4.l(var3.getInt("locy2"));
               var4.m(var3.getInt("heading"));
               var4.n(var3.getInt("min_respawn_delay"));
               var4.o(var3.getInt("max_respawn_delay"));
               var4.p(var3.getShort("mapid"));
               var4.a(var3.getBoolean("respawn_screen"));
               var4.q(var3.getInt("movement_distance"));
               var4.b(var3.getBoolean("rest"));
               var4.r(var3.getInt("near_spawn"));
               var4.c(var3.getBoolean("night"));
               if (var7 > 1 && var4.j() == 0) {
                  int var10 = Math.min(var7 * 6, 30);
                  if (var4.h() > 0 && var4.i() > 0) {
                     var10 = (var4.h() + var4.i()) / 2;
                  }

                  var4.i(var4.f() - var10);
                  var4.j(var4.g() - var10);
                  var4.k(var4.f() + var10);
                  var4.l(var4.g() + var10);
               }

               var4.a();
            }

            if (var4 == null) {
               System.out.println("spawntable has some error");
            } else {
               this.c.put(new Integer(var4.b()), var4);
               if (var4.b() > this.d) {
                  this.d = var4.b();
               }
            }
         }
      } catch (SQLException var14) {
         a.log(Level.SEVERE, var14.getLocalizedMessage(), var14);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public L1Spawn a(int var1) {
      return this.c.get(new Integer(var1));
   }

   public static void a(L1PcInstance var0, L1Npc var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         int var4 = 1;
         int var5 = 12;
         int var6 = 60;
         int var7 = 120;
         String var8 = var1.c();
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement(
            "INSERT INTO spawnlist SET location=?,count=?,npc_templateid=?,group_id=?,locx=?,locy=?,randomx=?,randomy=?,heading=?,min_respawn_delay=?,max_respawn_delay=?,mapid=?",
            Statement.RETURN_GENERATED_KEYS
         );
         var3.setString(1, var8);
         var3.setInt(2, 1);
         var3.setInt(3, var1.b());
         var3.setInt(4, 0);
         var3.setInt(5, var0.fs());
         var3.setInt(6, var0.ft());
         var3.setInt(7, 12);
         var3.setInt(8, 12);
         var3.setInt(9, var0.fb());
         var3.setInt(10, 60);
         var3.setInt(11, 120);
         var3.setInt(12, var0.fp());
         if (var3.executeUpdate() <= 0) {
            return;
         }
         try (ResultSet var9 = var3.getGeneratedKeys()) {
            if (!var9.next()) {
               return;
            }
            int var10 = var9.getInt(1);
            L1Spawn var11 = new L1Spawn(var1);
            var11.a(var10);
            var11.b(1);
            var11.e(var0.fs());
            var11.f(var0.ft());
            var11.g(12);
            var11.h(12);
            var11.i(0);
            var11.j(0);
            var11.k(0);
            var11.l(0);
            var11.m(var0.fb());
            var11.n(60);
            var11.o(120);
            var11.p(var0.fp());
            var11.q(0);
            SpawnTable var12 = a();
            synchronized (var12.c) {
               var12.c.put(var10, var11);
               if (var10 > var12.d) {
                  var12.d = var10;
               }
            }
         }
      } catch (Exception var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private static int a(L1Npc var0, int var1, double var2) {
      if (var2 == 0.0) {
         return 0;
      } else {
         return var2 != 1.0 && !var0.af() ? a(var1 * var2) : var1;
      }
   }

   private static int a(double var0) {
      double var2 = (var0 - Math.floor(var0)) * 100.0;
      if (var2 == 0.0) {
         return (int)var0;
      }

      int var4 = Random.a(100);
      return var4 < var2 ? (int)var0 + 1 : (int)var0;
   }

   public static void a(int var0, L1Character var1, long var2) {
      a(var0, var1.fs(), var1.ft(), var1.fp(), var2);
   }

   public static void a(int var0, int var1, int var2, int var3, long var4) {
      if (var4 > 0L) {
         new SpawnTable.L1R_a(var0, var1, var2, var3, 5, var4, null, null).a();
      } else {
         b(var0, var1, var2, var3, 5, 0, 0L, null, false, 0);
      }
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, long var5, String var7) {
      if (var5 > 0L) {
         new SpawnTable.L1R_a(var0, var1, var2, var3, var4, var5, var7, null).a();
      } else {
         b(var0, var1, var2, var3, 5, 0, 0L, var7, false, 0);
      }
   }

   public static void a(int var0, int var1, int var2, int var3) {
      b(var0, var1, var2, var3, 5, 0, 0L, null, false, 0);
   }

   public static L1NpcInstance a(int var0, int var1, int var2, int var3, int var4, boolean var5) {
      return b(var0, var1, var2, var3, var4, 0, 0L, null, var5, 0);
   }

   public static L1NpcInstance a(int var0, int var1, int var2, int var3, int var4, int var5, boolean var6) {
      return b(var0, var1, var2, var3, var4, var5, 0L, null, var6, 0);
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, String var5) {
      b(var0, var1, var2, var3, var4, 0, 0L, var5, false, 0);
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, String var5, int var6) {
      b(var0, var1, var2, var3, var4, 0, 0L, var5, false, var6);
   }

   public static void a(int var0, L1Character var1, int var2, long var3) {
      b(var0, var1.fs(), var1.ft(), var1.fp(), var1.fb(), var2, var3, null, false, 0);
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, long var5) {
      b(var0, var1, var2, var3, var4, 0, var5, null, false, 0);
   }

   private static L1NpcInstance b(int var0, int var1, int var2, int var3, int var4, int var5, long var6, String var8, boolean var9, int var10) {
      try {
         L1NpcInstance var11 = NpcTable.a().b(var0);
         if (var11.U_().d().equals("L1Attacker")) {
            L1SpawnEffect.a().a(12261, 1000, var1, var2, var3);
         }

         var11.cF(IdFactory.a().c());
         var11.cE(var3);
         if (var5 == 0) {
            var11.fu().a(var1, var2, var3);
         } else {
            int var12 = 0;

            while (var12 < 50) {
               var12++;
               var11.cG(var1 + Random.a(var5) - Random.a(var5));
               var11.cH(var2 + Random.a(var5) - Random.a(var5));
               if (var11.fq().a(var11.fu()) && var11.fq().c(var11.fs(), var11.ft())) {
                  break;
               }
            }

            if (var12 >= 50) {
               var11.fu().a(var1, var2, var3);
               var11.fu().d(var4);
            }
         }

         var11.q(var11.fs());
         var11.r(var11.ft());
         var11.ct(var4);
         var11.b(var8);
         if (var11 instanceof L1MonsterInstance) {
            ((L1MonsterInstance)var11).c(var9);
         }

         if (var10 > 0) {
            var11.cw(var10);
         }

         L1World.a().a(var11);
         L1World.a().c(var11);
         if (var11 instanceof L1MonsterInstance) {
            ((L1MonsterInstance)var11).b(true);
         }

         var11.fg();
         var11.a_(0);
         if (var6 > 0L) {
            var11.a(var6);
         }

         return var11;
      } catch (Exception var13) {
         a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
         return null;
      }
   }

   public static void a(int var0, L1NpcInstance var1, int var2, L1Character var3) {
      int var4 = Random.a(8);
      int var5 = 8;

      for (int var6 = 0; var6 < var2; var6++) {
         L1NpcInstance var7 = b(var0, var1.fs(), var1.ft(), var1.fp(), var4, 8, 0L, null, true, 0);
         if (var3 != null) {
            var7.c(var3, 1);
         }
      }
   }

   private static class L1R_a extends TimerTask {
      private final int a;
      private final int b;
      private final int c;
      private final int d;
      private final long e;
      private final String f;
      private final int g;

      private L1R_a(int var1, int var2, int var3, int var4, int var5, long var6, String var8) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
         this.d = var1;
         this.e = var6;
         this.f = var8;
         this.g = var5;
      }

      @Override
      public void run() {
         SpawnTable.b(this.d, this.a, this.b, this.c, this.g, 0, 0L, this.f, false, 0);
      }

      private void a() {
         GeneralThreadPool.a().a(this, this.e);
      }

      // $VF: synthetic method
      L1R_a(int var1, int var2, int var3, int var4, int var5, long var6, String var8, SpawnTable.L1R_a var9) {
         this(var1, var2, var3, var4, var5, var6, var8);
      }
   }
}
