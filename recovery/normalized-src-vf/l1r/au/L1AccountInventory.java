package l1r.au;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.bh.L1Item;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1AccountInventory extends L1Inventory {
   private static final Logger g = Logger.getLogger(L1AccountInventory.class.getName());
   private final L1PcInstance h;

   public L1AccountInventory(L1PcInstance var1) {
      this.h = var1;
   }

   @Override
   public void a() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM character_warehouse WHERE account_name = ?");
         var2.setString(1, this.h.bc());
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("id");
            int var5 = var3.getInt("count");
            L1Item var6 = ItemTable.a().a(var3.getInt("item_id"));
            L1ItemInstance var7 = new L1ItemInstance(var6, var5);
            var7.cF(var4);
            var7.l(var3.getInt("super_enchant_field_1"));
            var7.m(var3.getInt("super_enchant_field_2"));
            var7.n(var3.getInt("super_enchant_field_3"));
            var7.o(var3.getInt("super_enchant_field_4"));
            var7.a(var3.getInt("enchantlvl"));
            var7.n();
            var7.b(false);
            var7.a(var3.getBoolean("is_id"));
            var7.b(var3.getInt("durability"));
            var7.g(var3.getInt("charge_count"));
            var7.j(var3.getInt("temp_value"));
            var7.a(var3.getTimestamp("last_used"));
            var7.f(var3.getInt("bless"));
            var7.h(var3.getInt("attr_enchant_kind"));
            var7.i(var3.getInt("attr_enchant_level"));
            var7.b(var3.getTimestamp("limit_time"));
            this.a.add(var7);
            L1World.a().a(var7);
         }
      } catch (SQLException var11) {
         g.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   @Override
   public void a(L1ItemInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement(
            "INSERT INTO character_warehouse SET id = ?, account_name = ?, item_id = ?, item_name = ?, count = ?, is_equipped=0, enchantlvl = ?, is_id = ?, durability = ?, charge_count = ?, temp_value = ?, last_used = ?, bless = ?, attr_enchant_kind = ?, attr_enchant_level = ?,super_enchant_field_1 = ? ,super_enchant_field_2 = ?,super_enchant_field_3 = ? ,super_enchant_field_4=?,limit_time=?"
         );
         var3.setInt(1, var1.fr());
         var3.setString(2, this.h.bc());
         var3.setInt(3, var1.N());
         var3.setString(4, var1.b());
         var3.setInt(5, var1.E());
         var3.setInt(6, var1.G());
         var3.setInt(7, var1.C() ? 1 : 0);
         var3.setInt(8, var1.H());
         var3.setInt(9, var1.I());
         var3.setInt(10, var1.M());
         var3.setTimestamp(11, var1.J());
         var3.setInt(12, var1.F());
         var3.setInt(13, var1.K());
         var3.setInt(14, var1.L());
         var3.setInt(15, var1.X());
         var3.setInt(16, var1.Y());
         var3.setInt(17, var1.Z());
         var3.setInt(18, var1.aa());
         var3.setTimestamp(19, var1.bb());
         var3.execute();
      } catch (SQLException var8) {
         g.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   @Override
   public void b(L1ItemInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE character_warehouse SET count = ? WHERE id = ?");
         var3.setInt(1, var1.E());
         var3.setInt(2, var1.fr());
         var3.execute();
      } catch (SQLException var8) {
         g.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   @Override
   public void c(L1ItemInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM character_warehouse WHERE id = ?");
         var3.setInt(1, var1.fr());
         var3.execute();
      } catch (SQLException var8) {
         g.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }

      this.a.remove(this.a.indexOf(var1));
   }

   public static void a(String var0, int var1, int var2, int var3) throws Exception {
      L1Item var4 = ItemTable.a().a(var1);
      if (var4 != null) {
         Connection var5 = null;
         PreparedStatement var6 = null;
         ResultSet var7 = null;

         try {
            var5 = DatabaseFactory.a().b();
            if (var0.compareToIgnoreCase("*") == 0) {
               var6 = var5.prepareStatement("SELECT * FROM accounts");
            } else {
               var6 = var5.prepareStatement("SELECT * FROM accounts WHERE login=?");
               var6.setString(1, var0);
            }

            var7 = var6.executeQuery();
            ArrayList var8 = new ArrayList<>();

            while (var7.next()) {
               var8.add(var7.getString("login"));
            }

            a(var8, var1, var2, var3);
         } catch (SQLException var12) {
            g.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
         } finally {
            SQLUtil.a(var7, var6, var5);
         }
      }
   }

   public static void a(int var0, int var1, int var2, int var3, int var4) throws Exception {
      L1Item var5 = ItemTable.a().a(var2);
      if (var5 != null) {
         Connection var6 = null;
         PreparedStatement var7 = null;
         ResultSet var8 = null;

         try {
            var6 = DatabaseFactory.a().b();
            var7 = var6.prepareStatement("SELECT distinct(account_name) as account_name FROM characters WHERE level between ? and ?");
            var7.setInt(1, var0);
            var7.setInt(2, var1);
            var8 = var7.executeQuery();
            ArrayList var9 = new ArrayList<>();

            while (var8.next()) {
               var9.add(var8.getString("account_name"));
            }

            a(var9, var2, var3, var4);
         } catch (SQLException var13) {
            g.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
         } finally {
            SQLUtil.a(var8, var7, var6);
         }
      }
   }

   private static void a(List<String> var0, int var1, int var2, int var3) throws Exception {
      L1Item var4 = ItemTable.a().a(var1);
      if (ItemTable.a().a(var1) == null) {
         throw new Exception("道具編號不存在。");
      }

      Connection var5 = null;
      PreparedStatement var6 = null;

      try {
         var5 = DatabaseFactory.a().b();
         var5.setAutoCommit(false);

         for (String var7 : var0) {
            if (var4.aF()) {
               L1ItemInstance var19 = ItemTable.a().b(var1);
               var19.a(var2);
               var19.e(var3);
               var6 = var5.prepareStatement(
                  "INSERT INTO character_warehouse SET id = ?,account_name = ?,item_id = ?,item_name = ?,count = ?,is_equipped=0,enchantlvl = ?,is_id = ?,durability = ?,charge_count = ?,temp_value = ?,last_used = ?,bless = ?,attr_enchant_kind = ?,attr_enchant_level = ?"
               );
               var6.setInt(1, var19.fr());
               var6.setString(2, var7);
               var6.setInt(3, var19.N());
               var6.setString(4, var19.b());
               var6.setInt(5, var19.E());
               var6.setInt(6, var19.G());
               var6.setInt(7, var19.C() ? 1 : 0);
               var6.setInt(8, var19.H());
               var6.setInt(9, var19.I());
               var6.setInt(10, var19.M());
               var6.setTimestamp(11, var19.J());
               var6.setInt(12, var19.F());
               var6.setInt(13, var19.K());
               var6.setInt(14, var19.L());
               var6.execute();
            } else {
               L1ItemInstance var9 = null;

               for (int var10 = 0; var10 < var3; var10++) {
                  var9 = ItemTable.a().b(var1);
                  var9.a(var2);
                  var6 = var5.prepareStatement(
                     "INSERT INTO character_warehouse SET id = ?,account_name = ?,item_id = ?,item_name = ?,count = ?,is_equipped=0,enchantlvl = ?,is_id = ?,durability = ?,charge_count = ?,temp_value = ?,last_used = ?,bless = ?,attr_enchant_kind = ?,attr_enchant_level = ?"
                  );
                  var6.setInt(1, var9.fr());
                  var6.setString(2, var7);
                  var6.setInt(3, var9.N());
                  var6.setString(4, var9.b());
                  var6.setInt(5, var9.E());
                  var6.setInt(6, var9.G());
                  var6.setInt(7, var9.C() ? 1 : 0);
                  var6.setInt(8, var9.H());
                  var6.setInt(9, var9.I());
                  var6.setInt(10, var9.M());
                  var6.setTimestamp(11, var9.J());
                  var6.setInt(12, var9.F());
                  var6.setInt(13, var9.K());
                  var6.setInt(14, var9.L());
                  var6.execute();
               }
            }
         }

         var5.commit();
         var5.setAutoCommit(true);
      } catch (SQLException var16) {
         try {
            if (var5 != null) {
               var5.rollback();
            }
         } catch (SQLException var15) {
            g.log(Level.SEVERE, var15.getLocalizedMessage(), var15);
         }

         throw new Exception(".present 處理時發生了例外的錯誤。");
      } finally {
         SQLUtil.a(var6);
         SQLUtil.a(var5);
      }
   }
}
