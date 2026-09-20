package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1PcInstance;
import l1r.ap.L1TrapInstance;
import l1r.aq.L1Location;
import l1r.aq.L1World;
import l1r.bd.L1Trap__obf_i;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Point;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class TrapSpawnTable {
   private static final Logger a = Logger.getLogger(TrapSpawnTable.class.getName());
   private final CopyOnWriteArrayList<L1TrapInstance> b = new CopyOnWriteArrayList<>();
   private final ArrayList<L1TrapInstance> c = new ArrayList<>();
   private Timer d = new Timer();
   private static TrapSpawnTable e;

   private TrapSpawnTable() {
      this.d();
   }

   public static TrapSpawnTable a() {
      if (e == null) {
         e = new TrapSpawnTable();
      }

      return e;
   }

   private void d() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM spawnlist_trap");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("trapId");
            L1Trap__obf_i var5 = TrapTable.a().a(var4);
            L1Location var6 = new L1Location();
            var6.a(var3.getInt("mapId"));
            var6.b(var3.getInt("locX"));
            var6.c(var3.getInt("locY"));
            Point var7 = new Point();
            var7.b(var3.getInt("locRndX"));
            var7.c(var3.getInt("locRndY"));
            int var8 = var3.getInt("count");
            int var9 = var3.getInt("span");

            for (int var10 = 0; var10 < var8; var10++) {
               L1TrapInstance var11 = new L1TrapInstance(IdFactory.a().c(), var5, var6, var7, var9);
               L1World.a().c(var11);
               this.b.add(var11);
            }

            L1TrapInstance var17 = new L1TrapInstance(IdFactory.a().c(), var6);
            L1World.a().c(var17);
            this.c.add(var17);
         }
      } catch (SQLException var15) {
         a.log(Level.SEVERE, var15.getLocalizedMessage(), var15);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(L1TrapInstance var1) {
      L1World.a().c(var1);
      this.b.add(var1);
   }

   public void b(L1TrapInstance var1) {
      L1World.a().d(var1);
      this.b.remove(var1);
   }

   public static void b() {
      TrapTable.b();
      TrapSpawnTable var0 = e;
      e = new TrapSpawnTable();
      var0.e();
      a(var0.b);
      a(var0.c);
   }

   private static void a(List<L1TrapInstance> var0) {
      for (L1TrapInstance var1 : var0) {
         var1.c();
         L1World.a().d(var1);
      }
   }

   private void e() {
      synchronized (this) {
         this.d.cancel();
         this.d = new Timer();
      }
   }

   private void c(L1TrapInstance var1) {
      var1.c();
      synchronized (this) {
         GeneralThreadPool.a().a(new TrapSpawnTable.L1R_a(var1), var1.e());
      }
   }

   public void c() {
      for (L1TrapInstance var1 : this.b) {
         var1.a();
         var1.b();
      }
   }

   public void a(L1PcInstance var1) {
      L1Location var2 = var1.fu();

      for (L1TrapInstance var3 : this.b) {
         if (var3.d() && var2.equals(var3.fu())) {
            var3.d(var1);
            this.c(var3);
         }
      }
   }

   public void b(L1PcInstance var1) {
      L1Location var2 = var1.fu();

      for (L1TrapInstance var3 : this.b) {
         if (var3.d() && var2.e(var3.fu())) {
            var3.f();
            this.c(var3);
         }
      }
   }

   private class L1R_a extends TimerTask {
      private final L1TrapInstance b;

      public L1R_a(L1TrapInstance var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         this.b.a();
         this.b.b();
      }
   }
}
