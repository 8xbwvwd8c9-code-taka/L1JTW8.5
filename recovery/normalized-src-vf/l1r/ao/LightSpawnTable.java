package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1FieldObjectInstance;
import l1r.aq.L1World;
import l1r.bh.L1Npc;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class LightSpawnTable {
   private static final Logger a = Logger.getLogger(LightSpawnTable.class.getName());
   private static LightSpawnTable b;

   public static LightSpawnTable a() {
      if (b == null) {
         b = new LightSpawnTable();
      }

      return b;
   }

   private LightSpawnTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM spawnlist_light");
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1Npc var4 = NpcTable.a().a(var3.getInt(2));
            if (var4 != null) {
               L1FieldObjectInstance var5 = new L1FieldObjectInstance(var4);
               var5.cF(IdFactory.a().c());
               var5.cG(var3.getInt("locx"));
               var5.cH(var3.getInt("locy"));
               var5.cE(var3.getInt("mapid"));
               var5.q(var5.fs());
               var5.r(var5.ft());
               var5.ct(0);
               var5.s(var4.ae());
               L1World.a().a(var5);
               L1World.a().c(var5);
            }
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }
}
