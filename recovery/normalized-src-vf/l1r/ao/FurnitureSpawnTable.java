package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1FurnitureInstance;
import l1r.aq.L1World;
import l1r.bh.L1Npc;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class FurnitureSpawnTable {
   private static final Logger a = Logger.getLogger(FurnitureSpawnTable.class.getName());
   private static FurnitureSpawnTable b;

   public static FurnitureSpawnTable a() {
      if (b == null) {
         b = new FurnitureSpawnTable();
      }

      return b;
   }

   private FurnitureSpawnTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM spawnlist_furniture");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("npcid");
            L1Npc var5 = NpcTable.a().a(var4);
            if (var5 == null) {
               a.log(Level.SEVERE, "FurnitureSpawnTable npicd:" + var4 + " is Null");
            } else {
               L1FurnitureInstance var6 = new L1FurnitureInstance(var5);
               var6.cF(IdFactory.a().c());
               var6.b(var3.getInt("item_obj_id"));
               var6.cG(var3.getInt("locx"));
               var6.cH(var3.getInt("locy"));
               var6.cE(var3.getInt("mapid"));
               var6.q(var6.fs());
               var6.r(var6.ft());
               var6.ct(0);
               L1World.a().a(var6);
               L1World.a().c(var6);
            }
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public boolean insertDurable(L1FurnitureInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO spawnlist_furniture SET item_obj_id=?, npcid=?, locx=?, locy=?, mapid=?");
         var3.setInt(1, var1.f());
         var3.setInt(2, var1.U_().b());
         var3.setInt(3, var1.fs());
         var3.setInt(4, var1.ft());
         var3.setInt(5, var1.fp());
         return var3.executeUpdate() == 1;
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
         return false;
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public boolean deleteDurable(L1FurnitureInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM spawnlist_furniture WHERE item_obj_id=?");
         var3.setInt(1, var1.f());
         return var3.executeUpdate() == 1;
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
         return false;
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void a(L1FurnitureInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO spawnlist_furniture SET item_obj_id=?, npcid=?, locx=?, locy=?, mapid=?");
         var3.setInt(1, var1.f());
         var3.setInt(2, var1.U_().b());
         var3.setInt(3, var1.fs());
         var3.setInt(4, var1.ft());
         var3.setInt(5, var1.fp());
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void b(L1FurnitureInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM spawnlist_furniture WHERE item_obj_id=?");
         var3.setInt(1, var1.f());
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }
}
