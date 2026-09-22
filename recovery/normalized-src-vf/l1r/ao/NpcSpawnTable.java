package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Spawn;
import l1r.bh.L1Npc;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class NpcSpawnTable {
   private static final Logger a = Logger.getLogger(NpcSpawnTable.class.getName());
   private static NpcSpawnTable b;
   private final HashMap<Integer, L1Spawn> c = new HashMap<>();
   private int d;

   public static NpcSpawnTable a() {
      if (b == null) {
         b = new NpcSpawnTable();
      }

      return b;
   }

   private NpcSpawnTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM spawnlist_npc");
         var3 = var2.executeQuery();

         while (var3.next()) {
            if (!Config.ac) {
               int var4 = var3.getInt(1);
               if (var4 >= Config.ad && var4 <= Config.ae) {
                  continue;
               }
            }

            int var12 = var3.getInt("npc_templateid");
            L1Npc var5 = NpcTable.a().a(var12);
            if (var5 == null) {
               System.out.println("mob data for id:" + var12 + " missing in npc table");
               L1Spawn var6 = null;
            } else if (var3.getInt("count") != 0) {
               L1Spawn var13 = new L1Spawn(var5);
               var13.a(var3.getInt("id"));
               var13.b(var3.getInt("count"));
               var13.e(var3.getInt("locx"));
               var13.f(var3.getInt("locy"));
               var13.g(var3.getInt("randomx"));
               var13.h(var3.getInt("randomy"));
               var13.i(0);
               var13.j(0);
               var13.k(0);
               var13.l(0);
               var13.m(var3.getInt("heading"));
               var13.n(var3.getInt("respawn_delay"));
               var13.p(var3.getShort("mapid"));
               var13.q(var3.getInt("movement_distance"));
               var13.a();
               this.c.put(new Integer(var13.b()), var13);
               if (var13.b() > this.d) {
                  this.d = var13.b();
               }
            }
         }
      } catch (Exception var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(L1PcInstance var1, L1Npc var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         int var5 = 1;
         String var6 = var2.c();
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("INSERT INTO spawnlist_npc SET location=?,count=?,npc_templateid=?,locx=?,locy=?,heading=?,mapid=?", Statement.RETURN_GENERATED_KEYS);
         var4.setString(1, var6);
         var4.setInt(2, 1);
         var4.setInt(3, var2.b());
         var4.setInt(4, var1.fs());
         var4.setInt(5, var1.ft());
         var4.setInt(6, var1.fb());
         var4.setInt(7, var1.fp());
         if (var4.executeUpdate() <= 0) {
            return;
         }
         try (ResultSet var7 = var4.getGeneratedKeys()) {
            if (!var7.next()) {
               return;
            }
            int var8 = var7.getInt(1);
            L1Spawn var9 = new L1Spawn(var2);
            var9.a(var8);
            var9.b(1);
            var9.e(var1.fs());
            var9.f(var1.ft());
            var9.g(0);
            var9.h(0);
            var9.i(0);
            var9.j(0);
            var9.k(0);
            var9.l(0);
            var9.m(var1.fb());
            var9.n(0);
            var9.o(0);
            var9.p(var1.fp());
            var9.q(0);
            synchronized (this.c) {
               this.c.put(var8, var9);
               if (var8 > this.d) {
                  this.d = var8;
               }
            }
         }
      } catch (Exception var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   public L1Spawn a(int var1) {
      return this.c.get(var1);
   }
}
