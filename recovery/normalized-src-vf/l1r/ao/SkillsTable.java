package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.bh.L1Skills;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class SkillsTable {
   private static final Logger a = Logger.getLogger(SkillsTable.class.getName());
   private static SkillsTable b;
   private final HashMap<Integer, L1Skills> c = new HashMap<>();
   private final boolean d = true;

   public static SkillsTable a() {
      if (b == null) {
         b = new SkillsTable();
      }

      return b;
   }

   private SkillsTable() {
      this.c();
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM skills");
         var3 = var2.executeQuery();
         this.a(var3);
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void a(ResultSet var1) throws SQLException {
      while (var1.next()) {
         L1Skills var2 = new L1Skills();
         int var3 = var1.getInt("skill_id");
         var2.a(var3);
         var2.a(var1.getString("name"));
         var2.b(var1.getInt("skill_level"));
         var2.c(var1.getInt("mpConsume"));
         var2.d(var1.getInt("hpConsume"));
         var2.e(var1.getInt("itemConsumeId"));
         var2.f(var1.getInt("itemConsumeCount"));
         var2.g(var1.getInt("reuseDelay"));
         var2.u(var1.getInt("buffDuration"));
         var2.h(var1.getInt("damage_value"));
         var2.i(var1.getInt("damage_dice"));
         var2.j(var1.getInt("damage_dice_count"));
         var2.k(var1.getInt("probability_value"));
         var2.l(var1.getInt("probability_dice"));
         var2.m(var1.getInt("attr"));
         var2.n(var1.getInt("lawful"));
         var2.o(var1.getInt("ranged"));
         var2.p(var1.getInt("area"));
         var2.q(var1.getInt("id"));
         var2.r(var1.getInt("action_id"));
         var2.s(var1.getInt("castgfx"));
         var2.t(var1.getInt("castgfx2"));
         this.c.put(new Integer(var3), var2);
      }
   }

   public void a(int var1, int var2, String var3, int var4, int var5) {
      if (this.a(var1, var2)) {
         return;
      }

      Connection var7 = null;
      PreparedStatement var8 = null;
      try {
         var7 = DatabaseFactory.a().b();
         var8 = var7.prepareStatement("INSERT INTO character_skills SET char_obj_id=?, skill_id=?, skill_name=?, is_active=?, activetimeleft=?");
         var8.setInt(1, var1);
         var8.setInt(2, var2);
         var8.setString(3, var3);
         var8.setInt(4, var4);
         var8.setInt(5, var5);
         if (var8.executeUpdate() != 1) {
            throw new SQLException("BUG-850-089 character skill insert affectedRows != 1");
         }
      } catch (Exception var13) {
         a.log(Level.SEVERE, "BUG-850-089 durable skill insert failed", var13);
         return;
      } finally {
         SQLUtil.a(var8);
         SQLUtil.a(var7);
      }

      L1PcInstance var6 = (L1PcInstance)L1World.a().a(var1);
      if (var6 != null) {
         var6.f(var2);
      }
   }

   public boolean a(int var1, int var2) {
      boolean var3 = false;
      Connection var4 = null;
      PreparedStatement var5 = null;
      ResultSet var6 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement("SELECT * FROM character_skills WHERE char_obj_id=? AND skill_id=?");
         var5.setInt(1, var1);
         var5.setInt(2, var2);
         var6 = var5.executeQuery();
         var3 = var6.next();
      } catch (Exception var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var6, var5, var4);
      }

      return var3;
   }

   public boolean b() {
      return this.d;
   }

   public L1Skills a(int var1) {
      return this.c.get(new Integer(var1));
   }
}
