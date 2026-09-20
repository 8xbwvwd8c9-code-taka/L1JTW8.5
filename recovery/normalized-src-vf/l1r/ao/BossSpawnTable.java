package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aq.L1SpawnBoss;
import l1r.bh.L1Npc;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class BossSpawnTable {
   private static final Logger a = Logger.getLogger(BossSpawnTable.class.getName());

   private BossSpawnTable() {
   }

   public static void a() {
      int var0 = 0;
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM spawnlist_boss");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var6 = var3.getInt("npc_id");
            L1Npc var5 = NpcTable.a().a(var6);
            if (var5 == null) {
               System.out.println("mob data for id:" + var6 + " missing in npc table");
               L1SpawnBoss var4 = null;
            } else {
               L1SpawnBoss var12 = new L1SpawnBoss(var5);
               var12.a(var3.getInt("id"));
               var12.c(var6);
               var12.a(var3.getString("cycle_type"));
               var12.b(var3.getString("weeks"));
               var12.a(var3.getTime("time"));
               var12.b(var3.getInt("count"));
               var12.d(var3.getInt("group_id"));
               var12.e(var3.getInt("locx"));
               var12.f(var3.getInt("locy"));
               var12.g(var3.getInt("randomx"));
               var12.h(var3.getInt("randomy"));
               var12.i(var3.getInt("locx1"));
               var12.j(var3.getInt("locy1"));
               var12.k(var3.getInt("locx2"));
               var12.l(var3.getInt("locy2"));
               var12.m(var3.getInt("heading"));
               var12.p(var3.getShort("mapid"));
               var12.a(var3.getBoolean("respawn_screen"));
               var12.q(var3.getInt("movement_distance"));
               var12.b(var3.getBoolean("rest"));
               var12.r(var3.getInt("spawn_type"));
               var12.s(var3.getInt("percentage"));
               var12.a();
               var0 += var12.c();
            }
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }
}
