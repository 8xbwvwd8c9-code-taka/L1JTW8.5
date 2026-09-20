package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bh.L1PetItem;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class PetItemTable {
   private static final Logger a = Logger.getLogger(PetItemTable.class.getName());
   private static PetItemTable b;
   private final HashMap<Integer, L1PetItem> c = new HashMap<>();
   private static final HashMap<String, Integer> d = new HashMap<>();

   static {
      d.put("armor", new Integer(0));
      d.put("tooth", new Integer(1));
   }

   public static PetItemTable a() {
      if (b == null) {
         b = new PetItemTable();
      }

      return b;
   }

   private PetItemTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM petitem");
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
         L1PetItem var2 = new L1PetItem();
         var2.a(var1.getInt("item_id"));
         var2.n(d.get(var1.getString("use_type")));
         var2.b(var1.getInt("hitmodifier"));
         var2.c(var1.getInt("dmgmodifier"));
         var2.d(var1.getInt("ac"));
         var2.e(var1.getInt("add_str"));
         var2.f(var1.getInt("add_con"));
         var2.g(var1.getInt("add_dex"));
         var2.h(var1.getInt("add_int"));
         var2.i(var1.getInt("add_wis"));
         var2.j(var1.getInt("add_hp"));
         var2.k(var1.getInt("add_mp"));
         var2.l(var1.getInt("add_sp"));
         var2.m(var1.getInt("m_def"));
         this.c.put(var2.a(), var2);
      }
   }

   public L1PetItem a(int var1) {
      return this.c.get(var1);
   }
}
