package l1r.as;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.am.ListSprReader__obf_c;
import l1r.ao.DoorTable;
import l1r.ao.ItemTable;
import l1r.ao.NpcTable;
import l1r.ao.ShopTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_DoActionGFX;
import l1r.be.S_NpcChatPacket;
import l1r.bh.L1Item;
import l1r.bh.L1Shop;
import l1r.bh.L1ShopItem;
import l1r.bi.GeneralThreadPool;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1BugBearRace {
   private static final Logger a = Logger.getLogger(L1BugBearRace.class.getName());
   private ScheduledExecutorService b;
   private static L1BugBearRace c;
   private L1NpcInstance d;
   private static final int e = 0;
   private static final int f = 1;
   private static final int g = 2;
   private int h = 5000;
   private int i = 0;
   private int j;
   private int k = 0;
   private HashMap<Integer, L1NpcInstance> l = new HashMap<>();
   private final ConcurrentHashMap<Integer, L1BugBearRace.a> m = new ConcurrentHashMap<>();
   private HashMap<Integer, L1BugBearRace.a> n = new HashMap<>();
   private final HashMap<Integer, L1BugBearRace.b> o = new HashMap<>();
   private final int[][] p = new int[][]{{6, 46}, {7, 3}, {0, 5}, {1, 11}, {2, 18}, {1, 1}, {2, 10}, {1, 2}, {2, 9}};
   private final int[][] q = new int[][]{{6, 44}, {7, 3}, {0, 8}, {1, 10}, {2, 32}, {1, 1}, {2, 7}};
   private final int[][] r = new int[][]{{6, 34}, {7, 1}, {6, 6}, {7, 5}, {0, 6}, {1, 7}, {0, 1}, {1, 2}, {3, 1}, {2, 6}, {1, 1}, {2, 35}};
   private final int[][] s = new int[][]{{6, 35}, {7, 4}, {6, 3}, {7, 3}, {0, 2}, {1, 2}, {0, 4}, {1, 4}, {0, 1}, {1, 2}, {2, 45}};
   private final int[][] t = new int[][]{{6, 34}, {7, 10}, {0, 1}, {1, 1}, {0, 4}, {1, 3}, {2, 1}, {1, 3}, {2, 48}};

   public static L1BugBearRace a() {
      if (c == null) {
         c = new L1BugBearRace();
      }

      return c;
   }

   private L1BugBearRace() {
      this.g();
      this.h();
      new L1BugBearRace.c(null).a();
   }

   private void e() {
      int var1 = 0;

      while (this.l.size() < 5) {
         int var2 = 91350 + new Random().nextInt(20);
         if (!this.l.containsKey(var2)) {
            L1Location var3 = new L1Location(33522 - var1 * 2, 32861 + var1 * 2, 4);
            this.l.put(var2, this.a(var3, var2));
            var1++;
            int var4 = var2 - 91350 + 1;
            L1BugBearRace.a var5 = new L1BugBearRace.a(null);
            this.n.put(var4, var5);
         }
      }
   }

   private void f() {
      this.b.shutdownNow();

      for (L1NpcInstance var1 : this.l.values()) {
         var1.aa_();
      }

      this.l = new HashMap<>();
      this.n = new HashMap<>();
      this.h = 5000;
      this.k = 0;
      L1DoorInstance[] var4;
      int var3 = (var4 = DoorTable.b().c()).length;

      for (int var6 = 0; var6 < var3; var6++) {
         L1DoorInstance var5 = var4[var6];
         if (var5.i() <= 812 && var5.i() >= 808) {
            var5.g();
         }
      }
   }

   private void g() {
      for (int var1 = 1; var1 <= 20; var1++) {
         this.o.put(var1, new L1BugBearRace.b(null));
      }

      for (L1Object var3 : L1World.a().b()) {
         if (var3 instanceof L1NpcInstance && ((L1NpcInstance)var3).z() == 70041) {
            this.d = (L1NpcInstance)var3;
         }
      }
   }

   private void b(String var1) {
      this.d.d(new S_NpcChatPacket(this.d, var1, 2));
   }

   private L1NpcInstance a(L1Location var1, int var2) {
      L1NpcInstance var3 = NpcTable.a().b(var2);
      var3.cF(IdFactory.a().c());
      var3.a("#" + (var3.z() - 91350 + 1) + " " + var3.T());
      var3.ct(6);
      var3.cG(var1.f());
      var3.cH(var1.g());
      var3.cE(var1.b());
      L1World.a().a(var3);
      L1World.a().c(var3);
      return var3;
   }

   private double c(int var1) {
      double var2 = this.o.get(var1).b;
      double var4 = this.o.get(var1).c;
      return var4 == 0.0 ? 0.0 : var2 / var4 * 100.0;
   }

   public int b() {
      return this.i;
   }

   public String[] c() {
      String[] var1 = new String[15];
      int var2 = -1;

      for (L1NpcInstance var3 : this.l.values()) {
         int var5 = var3.z() - 91350 + 1;
         var1[++var2] = "#" + String.format("%02d", var5);
         var1[++var2] = new String[]{"$368$368", "$369", "$370"}[new Random().nextInt(3)];
         var1[++var2] = String.format("%.2f", this.c(var5)).toString();
      }

      return var1;
   }

   public void a(L1ItemInstance var1) {
      L1BugBearRace.a var2 = this.m.get(var1.fr());
      if (var2 != null) {
         L1Item var3 = (L1Item)var1.a().clone();
         String var4 = var3.j() + " " + var2.d + "-" + var2.g;
         var3.a(var4);
         var3.b(var4);
         var3.c(var4);
         var1.a(var3);
      }
   }

   public void b(L1ItemInstance var1) {
      String[] var2 = var1.a().j().split("-");
      int var3 = Integer.parseInt(var2[1]);
      L1BugBearRace.a var10000 = this.n.get(var3);
      var10000.b = var10000.b + var1.E();
      this.h = this.h + var1.E() * 500;
      L1BugBearRace.a var4 = new L1BugBearRace.a(null);
      var4.c = var1.fr();
      var4.d = this.j;
      var4.g = var3;
      this.m.put(new Integer(var4.c), var4);
      this.a(var4);
   }

   public int a(int var1) {
      L1BugBearRace.a var2 = this.m.get(var1);
      return var2 == null ? 0 : (int)(var2.e * var2.f) * 500;
   }

   public String a(String var1) {
      String[] var2 = var1.split(" ");
      String var3 = var2[var2.length - 1];
      var2 = var3.split("-");
      return var3 + " $" + (1212 + Integer.parseInt(var2[var2.length - 1]));
   }

   private void h() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM race_ticket");
         int var4 = 0;
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1BugBearRace.a var5 = new L1BugBearRace.a(null);
            int var6 = var3.getInt(1);
            int var7 = var3.getInt(2);
            int var8 = var3.getInt(5);
            if (var6 > 0) {
               var5.c = var6;
               var5.d = var7;
               var5.e = var3.getInt(3);
               var5.f = var3.getInt(4);
               var5.g = var8;
               this.m.put(var6, var5);
            } else if (var8 > 0) {
               L1BugBearRace.b var10000 = this.o.get(var8);
               var10000.b = var10000.b + 1;

               for (int var9 = 6; var9 <= 10; var9++) {
                  var10000 = this.o.get(var3.getInt(var9));
                  var10000.c = var10000.c + 1;
               }
            }

            if (var7 > var4) {
               var4 = var7;
            }
         }

         this.j = var4;
      } catch (SQLException var13) {
         a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void a(L1BugBearRace.a var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO race_ticket SET item_objid=?,round=?,runner_num=?");
         var3.setInt(1, var1.c);
         var3.setInt(2, var1.d);
         var3.setInt(3, var1.g);
         var3.execute();
      } catch (Exception var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void b(int var1) {
      if (this.m.containsKey(var1)) {
         this.m.remove(var1);
      }

      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("delete from race_ticket WHERE item_objid=?");
         var3.setInt(1, var1);
         var3.execute();
      } catch (Exception var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private void d(int var1) {
      int var2 = var1 - 91350 + 1;
      double var3 = this.n.get(var2).e;
      Connection var5 = null;
      PreparedStatement var6 = null;

      try {
         var5 = DatabaseFactory.a().b();
         var6 = var5.prepareStatement("UPDATE race_ticket SET victory=? ,odds=? WHERE round=? and runner_num=?");
         var6.setInt(1, 1);
         var6.setDouble(2, var3);
         var6.setInt(3, this.j);
         var6.setInt(4, var2);
         var6.execute();
         int var7 = 0;
         var6 = var5.prepareStatement(
            "UPDATE race_ticket SET odds=?,runner_num=?,runner_1=?,runner_2=?,runner_3=?,runner_4=?,runner_5=? WHERE round=? AND item_objid=?"
         );
         var6.setDouble(++var7, var3);
         var6.setInt(++var7, var2);
         L1BugBearRace.b var10000 = this.o.get(var2);
         var10000.b = var10000.b + 1;

         for (L1NpcInstance var8 : this.l.values()) {
            int var10 = var8.z() - 91350 + 1;
            var6.setInt(++var7, var10);
            var10000 = this.o.get(var10);
            var10000.c = var10000.c + 1;
         }

         var6.setInt(++var7, this.j);
         var6.setInt(++var7, 0);
         var6.execute();
      } catch (Exception var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      }

      SQLUtil.a(var6);
      SQLUtil.a(var5);

      for (L1BugBearRace.a var16 : this.m.values()) {
         if (var16.d == this.j && var16.g == var2) {
            var16.e = var3;
            var16.f = 1;
         }
      }
   }

   private int[][] e(int var1) {
      int[][] var2 = new int[0][0];
      if (var1 == 33514) {
         var2 = this.t;
      } else if (var1 == 33516) {
         var2 = this.s;
      } else if (var1 == 33518) {
         var2 = this.r;
      } else if (var1 == 33520) {
         var2 = this.q;
      } else {
         var2 = this.p;
      }

      return var2;
   }

   private class a {
      private int b = 0;
      private int c = 0;
      private int d = 0;
      private double e = 0.0;
      private int f = 0;
      private int g = 0;

      private a() {
      }

      // $VF: synthetic method
      a(L1BugBearRace.a var2) {
         this();
      }
   }

   private class b {
      private int b = 0;
      private int c = 0;

      private b() {
      }

      // $VF: synthetic method
      b(L1BugBearRace.b var2) {
         this();
      }
   }

   private class c extends TimerTask {
      private c() {
      }

      private void a() {
         GeneralThreadPool.a().a(this, 3000L);
      }

      @Override
      public void run() {
         while (true) {
            try {
               L1BugBearRace.this.b = Executors.newScheduledThreadPool(5);
               L1BugBearRace.this.i = 0;
               L1BugBearRace.this.j = L1BugBearRace.this.j + 1;
               L1BugBearRace.a var1 = L1BugBearRace.this.new a(null);
               var1.d = L1BugBearRace.this.j;
               L1BugBearRace.this.a(var1);
               L1BugBearRace.this.b("$376 10 $377");
               Thread.sleep(600000L);
               L1BugBearRace.this.e();
               L1Shop var2 = ShopTable.a().a(70035);
               L1Shop var3 = ShopTable.a().a(70041);
               L1Shop var4 = ShopTable.a().a(70042);

               for (int var5 : L1BugBearRace.this.l.keySet()) {
                  L1Item var7 = (L1Item)ItemTable.a().a(40309).clone();
                  int var8 = var5 - 91350 + 1;
                  String var9 = var7.j() + " " + L1BugBearRace.this.j + "-" + var8;
                  var7.a(var9);
                  var7.b(var9);
                  var7.c(var9);
                  L1ShopItem var10 = new L1ShopItem(var7, 500);
                  var2.b().add(var10);
                  var3.b().add(var10);
                  var4.b().add(var10);
               }

               L1BugBearRace.this.i = 1;

               for (int var12 = 5; var12 > 0; var12--) {
                  L1BugBearRace.this.b("$376 " + var12 + " $377");
                  Thread.sleep(60000L);
               }

               L1BugBearRace.this.b("$363");
               Thread.sleep(1000L);

               for (int var13 = 10; var13 > 0; var13--) {
                  L1BugBearRace.this.b("" + var13);
                  Thread.sleep(1000L);
               }

               L1BugBearRace.this.b("$364");
               L1BugBearRace.this.i = 2;
               var2.b().clear();
               var3.b().clear();
               var4.b().clear();
               L1DoorInstance[] var22;
               int var20 = (var22 = DoorTable.b().c()).length;

               for (int var17 = 0; var17 < var20; var17++) {
                  L1DoorInstance var14 = var22[var17];
                  if (var14.i() <= 812 && var14.i() >= 808) {
                     var14.f();
                  }
               }

               for (L1NpcInstance var15 : L1BugBearRace.this.l.values()) {
                  L1BugBearRace.this.b.schedule(L1BugBearRace.this.new d(var15, L1BugBearRace.this.e(var15.fs()), null), 0L, TimeUnit.MILLISECONDS);
               }

               Thread.sleep(3000L);

               for (L1NpcInstance var16 : L1BugBearRace.this.l.values()) {
                  var20 = var16.z() - 91350 + 1;
                  L1BugBearRace.a var23 = L1BugBearRace.this.n.get(var20);
                  if (var23.b > 0) {
                     var23.e = L1BugBearRace.this.h / var23.b / 500.0;
                  } else {
                     var23.e = 1.0;
                  }

                  L1BugBearRace.this.b(var16.T() + " $402 " + var23.e);
                  Thread.sleep(500L);
               }

               while (L1BugBearRace.this.k == 0) {
                  Thread.sleep(1000L);
               }

               L1BugBearRace.this.d(L1BugBearRace.this.k);
               L1BugBearRace.this.f();
            } catch (InterruptedException var11) {
               L1BugBearRace.a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
            }
         }
      }

      // $VF: synthetic method
      c(L1BugBearRace.c var2) {
         this();
      }
   }

   private class d implements Runnable {
      L1NpcInstance a;
      int[][] b;

      private d(L1NpcInstance var2, int[][] var3) {
         this.a = var2;
         this.b = var3;
      }

      @Override
      public void run() {
         try {
            int[][] var4 = this.b;
            int var3 = this.b.length;

            for (int var2 = 0; var2 < var3; var2++) {
               int[] var1 = var4[var2];

               for (int var5 = 0; var5 < var1[1]; var5++) {
                  if (var5 == 0 && this.a.fb() != var1[0]) {
                     this.a.ct(var1[0]);
                  }

                  this.a.g(this.a.fb());
                  int var6 = new Random().nextInt(30);
                  if (var6 >= new Random().nextInt(2000)) {
                     this.a.b(new S_DoActionGFX(this.a.fr(), 30));
                     Thread.sleep(ListSprReader__obf_c.a().a(this.a.fe(), 30));
                  }

                  Thread.sleep(this.a.N() * 30 / (16 + var6));
               }
            }

            if (L1BugBearRace.this.k == 0) {
               L1BugBearRace.this.k = this.a.z();
               L1BugBearRace.this.b("$375 " + L1BugBearRace.this.j + "$366 " + this.a.T() + "$367");
            }
         } catch (InterruptedException var7) {
         }
      }

      // $VF: synthetic method
      d(L1NpcInstance var2, int[][] var3, L1BugBearRace.d var4) {
         this(var2, var3);
      }
   }
}
