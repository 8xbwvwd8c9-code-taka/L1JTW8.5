package ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class aw {
   private static final Logger a = Logger.getLogger(aw.class.getName());
   private static aw b;
   private final HashMap<Integer, bh.n> c = new HashMap<>();

   public static aw a() {
      if (b == null) {
         b = new aw();
      }

      return b;
   }

   private aw() {
      this.c();
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = l1j.server.b.a().b();
         var2 = var1.prepareStatement("SELECT * FROM pets");
         var3 = var2.executeQuery();

         while (var3.next()) {
            bh.n var4 = new bh.n();
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
         bi.j.a(var3, var2, var1);
      }
   }

   public void a(int var1, int var2) {
      bh.l var3 = au.a().a(var1);
      bh.n var4 = new bh.n();
      var4.a(var2);
      var4.b(ai.d.a().c());
      var4.c(var3.b());
      var4.a(var3.c());
      var4.d(var3.e());
      var4.e(var3.f());
      var4.f(var3.g());
      var4.g(750);
      var4.h(0);
      var4.i(50);
      this.c.put(new Integer(var2), var4);
      Connection var5 = null;
      PreparedStatement var6 = null;

      try {
         var5 = l1j.server.b.a().b();
         var6 = var5.prepareStatement("INSERT INTO pets SET item_obj_id=?,objid=?,npcid=?,name=?,lvl=?,hp=?,mp=?,exp=?,lawful=?,food=?");
         var6.setInt(1, var4.a());
         var6.setInt(2, var4.b());
         var6.setInt(3, var4.c());
         var6.setString(4, var4.d());
         var6.setInt(5, var4.e());
         var6.setInt(6, var4.f());
         var6.setInt(7, var4.g());
         var6.setInt(8, var4.h());
         var6.setInt(9, var4.i());
         var6.setInt(10, var4.j());
         var6.execute();
      } catch (Exception var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         bi.j.a(var6);
         bi.j.a(var5);
      }
   }

   public void a(ap.t var1, int var2, int var3) {
      bh.n var4 = new bh.n();
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
      this.c.put(new Integer(var3), var4);
      Connection var5 = null;
      PreparedStatement var6 = null;

      try {
         var5 = l1j.server.b.a().b();
         var6 = var5.prepareStatement("INSERT INTO pets SET item_obj_id=?,objid=?,npcid=?,name=?,lvl=?,hp=?,mp=?,exp=?,lawful=?,food=?");
         var6.setInt(1, var4.a());
         var6.setInt(2, var4.b());
         var6.setInt(3, var4.c());
         var6.setString(4, var4.d());
         var6.setInt(5, var4.e());
         var6.setInt(6, var4.f());
         var6.setInt(7, var4.g());
         var6.setInt(8, var4.h());
         var6.setInt(9, var4.i());
         var6.setInt(10, var4.j());
         var6.execute();
      } catch (Exception var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         bi.j.a(var6);
         bi.j.a(var5);
      }
   }

   public void a(bh.n var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = l1j.server.b.a().b();
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
         bi.j.a(var3);
         bi.j.a(var2);
      }
   }

   public void a(ap.v var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = l1j.server.b.a().b();
         var3 = var2.prepareStatement("UPDATE pets SET food=? WHERE item_obj_id=?");
         var3.setInt(1, var1.fj());
         var3.setInt(2, var1.k());
         var3.execute();
      } catch (Exception var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         bi.j.a(var3);
         bi.j.a(var2);
      }
   }

   public void a(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = l1j.server.b.a().b();
         var3 = var2.prepareStatement("DELETE FROM pets WHERE item_obj_id=?");
         var3.setInt(1, var1);
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         bi.j.a(var3);
         bi.j.a(var2);
      }

      this.c.remove(var1);
   }

   public static boolean a(String var0) {
      String var1 = var0.toLowerCase();
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = l1j.server.b.a().b();
         var3 = var2.prepareStatement("SELECT item_obj_id FROM pets WHERE LOWER(name)=?");
         var3.setString(1, var1);
         var4 = var3.executeQuery();
         if (!var4.next()) {
            return false;
         }

         if (!ax.b().a(var1)) {
            return true;
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         return true;
      } finally {
         bi.j.a(var4, var3, var2);
      }

      return false;
   }

   public void a(int var1, int var2, int var3, int var4, int var5) {
      bh.p var6 = ax.b().a(var1);
      bh.n var7 = new bh.n();
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
         var12 = (short)(var12 + bi.i.a(var9 - var8) + var8 + 1);
         var13 = (short)(var13 + bi.i.a(var11 - var10) + var10 + 1);
      }

      var7.e(var12);
      var7.f(var13);
      var7.g(var5);
      var7.h(0);
      var7.i(50);
      this.c.put(new Integer(var3), var7);
      Connection var22 = null;
      PreparedStatement var15 = null;

      try {
         var22 = l1j.server.b.a().b();
         var15 = var22.prepareStatement("INSERT INTO pets SET item_obj_id=?,objid=?,npcid=?,name=?,lvl=?,hp=?,mp=?,exp=?,lawful=?,food=?");
         var15.setInt(1, var7.a());
         var15.setInt(2, var7.b());
         var15.setInt(3, var7.c());
         var15.setString(4, var7.d());
         var15.setInt(5, var7.e());
         var15.setInt(6, var7.f());
         var15.setInt(7, var7.g());
         var15.setInt(8, var7.h());
         var15.setInt(9, var7.i());
         var15.setInt(10, var7.j());
         var15.execute();
      } catch (SQLException var20) {
         a.log(Level.SEVERE, var20.getLocalizedMessage(), var20);
      } finally {
         bi.j.a(var15);
         bi.j.a(var22);
      }
   }

   public bh.n b(int var1) {
      return this.c.get(new Integer(var1));
   }

   public bh.n[] b() {
      return this.c.values().toArray(new bh.n[this.c.size()]);
   }
}
