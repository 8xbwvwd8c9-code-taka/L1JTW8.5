package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aq.L1PolyMorph;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class PolyTable {
   private static final Logger a = Logger.getLogger(PolyTable.class.getName());
   private static PolyTable b;
   private final HashMap<String, L1PolyMorph> c = new HashMap<>();
   private final HashMap<Integer, L1PolyMorph> d = new HashMap<>();

   public static PolyTable a() {
      if (b == null) {
         b = new PolyTable();
      }

      return b;
   }

   private PolyTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM polymorphs");
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
         String var2 = var1.getString("name");
         int var3 = var1.getInt("polyid");
         int var4 = var1.getInt("minlevel");
         int var5 = var1.getInt("weaponequip");
         int var6 = var1.getInt("armorequip");
         boolean var7 = var1.getBoolean("isSkillUse");
         int var8 = var1.getInt("cause");
         L1PolyMorph var9 = new L1PolyMorph(var3, var4, var5, var6, var7, var8);
         this.c.put(var2, var9);
         this.d.put(var3, var9);
      }
   }

   public L1PolyMorph a(String var1) {
      return this.c.get(var1);
   }

   public L1PolyMorph a(int var1) {
      return this.d.get(var1);
   }
}
