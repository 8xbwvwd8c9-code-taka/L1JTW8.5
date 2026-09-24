package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PetInstance;
import l1r.bh.L1Npc;
import l1r.bh.L1Pet;
import l1r.bh.L1PetType;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class PetTable {
   private static final Logger a = Logger.getLogger(PetTable.class.getName());
   private static PetTable b;
   private final HashMap<Integer, L1Pet> c = new HashMap<>();

   public static PetTable a() {
      if (b == null) {
         b = new PetTable();
      }

      return b;
   }

   private PetTable() {
      this.c();
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM pets");
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1Pet var4 = new L1Pet();
            int var5 = var3.getInt(1);
            var4.a(var5);
            var4.b(var3.getInt(2));
            var4.c(var3.getInt(3));
            var4.a(var3.getString(4));
            var4.d(var3.getInt(5));
            var4.e(var3.getInt(6));
            var4.f(var3.getInt(7));
            var4.g(var3.getInt(8));
            var4.h(var3.getInt(9));
            var4.i(var3.getInt(10));
            this.c.put(new Integer(var5), var4);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(int var1, int var2) {
      L1Npc var3 = NpcTable.a().a(var1);
      L1Pet var4 = new L1Pet();
      var4.a(var2);
      var4.b(IdFactory.a().c());
      var4.c(var3.b());
      var4.a(var3.c());
      var4.d(var3.e());
      var4.e(var3.f());
      var4.f(var3.g());
      var4.g(750);
      var4.h(0);
      var4.i(50);
      if (this.insertDurable(var4)) {
         this.c.put(new Integer(var2), var4);
      }
   }

   public void a(L1NpcInstance var1, int var2, int var3) {
      L1Pet var4 = new L1Pet();
      var4.a(var3);
      var4.b(var2);
      var4.c(var1.U_().b());
      var4.a(var1.U_().c());
      var4.d(var1.U_().e());
      var4.e(var1.ew());
      var4.f(var1.ex());
      var4.g(750);
      var4.h(0);
      var4.i(50);
      if (this.insertDurable(var4)) {
         this.c.put(new Integer(var3), var4);
      }
   }

   public void a(L1Pet var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE pets SET objid=?,npcid=?,name=?,lvl=?,hp=?,mp=?,exp=?,lawful=?,food=? WHERE item_obj_id=?");
         var3.setInt(1, var1.b());
         var3.setInt(2, var1.c());
         var3.setString(3, var1.d());
         var3.setInt(4, var1.e());
         var3.setInt(5, var1.f());
         var3.setInt(6, var1.g());
         var3.setInt(7, var1.h());
         var3.setInt(8, var1.i());
         var3.setInt(9, var1.j());
         var3.setInt(10, var1.a());
         var3.execute();
      } catch (Exception var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void a(L1PetInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE pets SET food=? WHERE item_obj_id=?");
         var3.setInt(1, var1.fj());
         var3.setInt(2, var1.k());
         var3.execute();
      } catch (Exception var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void a(int var1) {
      if (this.deleteDurable(var1)) {
         this.c.remove(var1);
      }
   }

   public static boolean a(String var0) {
      String var1 = var0.toLowerCase();
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT item_obj_id FROM pets WHERE LOWER(name)=?");
         var3.setString(1, var1);
         var4 = var3.executeQuery();
         if (!var4.next()) {
            return false;
         }

         if (!PetTypeTable.b().a(var1)) {
            return true;
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         return true;
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return false;
   }

   public void a(int var1, int var2, int var3, int var4, int var5) {
      L1PetType var6 = PetTypeTable.b().a(var1);
      L1Pet var7 = new L1Pet();
      var7.a(var3);
      var7.b(var2);
      var7.c(var1);
      var7.a(var6.c());
      var7.d(var4);
      int var8 = var6.e().b();
      int var9 = var6.e().c();
      int var10 = var6.f().b();
      int var11 = var6.f().c();
      short var12 = (short)((var8 + var9) / 2);
      short var13 = (short)((var10 + var11) / 2);

      for (int var14 = 1; var14 < var4; var14++) {
         var12 = (short)(var12 + Random.a(var9 - var8) + var8 + 1);
         var13 = (short)(var13 + Random.a(var11 - var10) + var10 + 1);
      }

      var7.e(var12);
      var7.f(var13);
      var7.g(var5);
      var7.h(0);
      var7.i(50);
      if (this.insertDurable(var7)) {
         this.c.put(new Integer(var3), var7);
      }
   }

   private boolean insertDurable(L1Pet var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO pets SET item_obj_id=?,objid=?,npcid=?,name=?,lvl=?,hp=?,mp=?,exp=?,lawful=?,food=?");
         this.bindPet(var3, var1, false, 0);
         return var3.executeUpdate() == 1;
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
         return false;
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private boolean deleteDurable(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM pets WHERE item_obj_id=?");
         var3.setInt(1, var1);
         return var3.executeUpdate() == 1;
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
         return false;
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public boolean replaceDurable(int var1, L1Pet var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("UPDATE pets SET item_obj_id=?,objid=?,npcid=?,name=?,lvl=?,hp=?,mp=?,exp=?,lawful=?,food=? WHERE item_obj_id=?");
         this.bindPet(var4, var2, true, var1);
         if (var4.executeUpdate() != 1) {
            return false;
         }

         this.c.remove(var1);
         this.c.put(new Integer(var2.a()), var2);
         return true;
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         return false;
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   private void bindPet(PreparedStatement var1, L1Pet var2, boolean var3, int var4) throws SQLException {
      var1.setInt(1, var2.a());
      var1.setInt(2, var2.b());
      var1.setInt(3, var2.c());
      var1.setString(4, var2.d());
      var1.setInt(5, var2.e());
      var1.setInt(6, var2.f());
      var1.setInt(7, var2.g());
      var1.setInt(8, var2.h());
      var1.setInt(9, var2.i());
      var1.setInt(10, var2.j());
      if (var3) {
         var1.setInt(11, var4);
      }
   }

   public L1Pet b(int var1) {
      return this.c.get(new Integer(var1));
   }

   public L1Pet[] b() {
      return this.c.values().toArray(new L1Pet[this.c.size()]);
   }
}
