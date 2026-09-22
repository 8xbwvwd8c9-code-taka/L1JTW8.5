package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.bh.L1Item;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CharacterItemTable {
   private static final Logger a = Logger.getLogger(CharacterItemTable.class.getName());
   private static CharacterItemTable b;

   public static CharacterItemTable a() {
      if (b == null) {
         b = new CharacterItemTable();
      }

      return b;
   }

   public CharacterItemTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("UPDATE character_items SET item_id = ? WHERE item_id >= ? AND item_id<=?");
         var2.setInt(1, 640638);
         var2.setInt(2, 640626);
         var2.setInt(3, 640637);
         var2.execute();
      } catch (SQLException var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public List<L1ItemInstance> a(int var1) throws Exception {
      ArrayList var2 = new ArrayList<>();
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT * FROM character_items WHERE char_id = ?");
         var4.setInt(1, var1);
         var5 = var4.executeQuery();

         while (var5.next()) {
            int var7 = var5.getInt("item_id");
            int var8 = var5.getInt("count");
            L1Item var9 = ItemTable.a().a(var7);
            if (var9 == null) {
               System.out.println(String.format("item id:%d not found", var7));
            } else {
               L1ItemInstance var6 = new L1ItemInstance(var9, var8);
               var6.cF(var5.getInt("id"));
               var6.l(var5.getInt("super_enchant_field_1"));
               var6.m(var5.getInt("super_enchant_field_2"));
               var6.n(var5.getInt("super_enchant_field_3"));
               var6.o(var5.getInt("super_enchant_field_4"));
               var6.a(var5.getInt("enchantlvl"));
               var6.n();
               var6.b(var5.getBoolean("is_equipped"));
               var6.a(var5.getBoolean("is_id"));
               var6.b(var5.getInt("durability"));
               var6.g(var5.getInt("charge_count"));
               var6.j(var5.getInt("temp_value"));
               var6.a(var5.getTimestamp("last_used"));
               var6.f(var5.getInt("bless"));
               var6.h(var5.getInt("attr_enchant_kind"));
               var6.i(var5.getInt("attr_enchant_level"));
               var6.b(var5.getTimestamp("limit_time"));
               var6.q();
               if (var6.N() != 310 && var6.N() != 640354 && var6.N() != 640355) {
                  var2.add(var6);
               } else {
                  this.a(var6);
               }
            }
         }
      } catch (SQLException var13) {
         throw var13;
      } finally {
         SQLUtil.a(var5, var4, var3);
      }

      return var2;
   }

   public void a(int var1, L1ItemInstance var2) throws Exception {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement(
            "INSERT INTO character_items SET id = ?, item_id = ?, char_id = ?, item_name = ?, count = ?, enchantlvl = ?, is_id = ?, durability = ?, charge_count = ?, temp_value = ?, last_used = ?, bless = ?, attr_enchant_kind = ?, attr_enchant_level = ?,super_enchant_field_1 = ? ,super_enchant_field_2 = ?,super_enchant_field_3 = ? ,super_enchant_field_4=? ,limit_time=?,is_equipped=?"
         );
         var4.setInt(1, var2.fr());
         var4.setInt(2, var2.N());
         var4.setInt(3, var1);
         var4.setString(4, var2.a().h());
         var4.setInt(5, var2.E());
         var4.setInt(6, var2.G());
         var4.setInt(7, var2.C() ? 1 : 0);
         var4.setInt(8, var2.H());
         var4.setInt(9, var2.I());
         var4.setInt(10, var2.M());
         var4.setTimestamp(11, var2.J());
         var4.setInt(12, var2.F());
         var4.setInt(13, var2.K());
         var4.setInt(14, var2.L());
         var4.setInt(15, var2.X());
         var4.setInt(16, var2.Y());
         var4.setInt(17, var2.Z());
         var4.setInt(18, var2.aa());
         var4.setTimestamp(19, var2.bb());
         var4.setBoolean(20, var2.D());
         var4.execute();
      } catch (SQLException var9) {
         a.log(Level.SEVERE, "[重複的item_objid] itemid=" + var2.N() + " count=" + var2.E() + " enchant=" + var2.G() + " bless=" + var2.F());
         throw var9;
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }

      var2.q();
   }

   public void a(L1ItemInstance var1) throws Exception {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM character_items WHERE id = ?");
         var3.setInt(1, var1.fr());
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void b(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET item_id = ? WHERE id = ?", var1.N());
      this.a(var1.fr(), "UPDATE character_items SET item_name = ? WHERE id = ?", var1.a().h());
   }

   public void c(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET count = ? WHERE id = ?", var1.E());
   }

   public void d(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET durability = ? WHERE id = ?", var1.H());
   }

   public void e(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET charge_count = ? WHERE id = ?", var1.I());
   }

   public void f(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET temp_value = ? WHERE id = ?", var1.M());
   }

   public void g(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET limit_time = ? WHERE id = ?", var1.bb());
   }

   public void h(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET enchantlvl = ? WHERE id = ?", var1.G());
   }

   public void i(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET is_equipped = ? WHERE id = ?", var1.D() ? 1 : 0);
   }

   public void j(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET is_id = ? WHERE id = ?", var1.C() ? 1 : 0);
   }

   public void k(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET last_used = ? WHERE id = ?", var1.J());
   }

   public void l(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET bless = ? WHERE id = ?", var1.F());
   }

   public void m(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET attr_enchant_kind = ? WHERE id = ?", var1.K());
   }

   public void n(L1ItemInstance var1) throws Exception {
      this.a(var1.fr(), "UPDATE character_items SET attr_enchant_level = ? WHERE id = ?", var1.L());
   }

   public void o(L1ItemInstance var1) throws Exception {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement(
            "UPDATE character_items SET super_enchant_field_1 =?,super_enchant_field_2 = ?,super_enchant_field_3 = ?,super_enchant_field_4 = ?  WHERE id = ?"
         );
         var3.setInt(1, var1.X());
         var3.setInt(2, var1.Y());
         var3.setInt(3, var1.Z());
         var3.setInt(4, var1.aa());
         var3.setInt(5, var1.fr());
         var3.execute();
      } catch (SQLException var8) {
         throw var8;
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void insertQuestReward(Connection var1, int var2, L1ItemInstance var3) throws SQLException {
      try (PreparedStatement var4 = var1.prepareStatement(
         "INSERT INTO character_items SET id = ?, item_id = ?, char_id = ?, item_name = ?, count = ?, enchantlvl = ?, is_id = ?, durability = ?, charge_count = ?, temp_value = ?, last_used = ?, bless = ?, attr_enchant_kind = ?, attr_enchant_level = ?,super_enchant_field_1 = ? ,super_enchant_field_2 = ?,super_enchant_field_3 = ? ,super_enchant_field_4=? ,limit_time=?,is_equipped=?"
      )) {
         var4.setInt(1, var3.fr());
         var4.setInt(2, var3.N());
         var4.setInt(3, var2);
         var4.setString(4, var3.a().h());
         var4.setInt(5, var3.E());
         var4.setInt(6, var3.G());
         var4.setInt(7, var3.C() ? 1 : 0);
         var4.setInt(8, var3.H());
         var4.setInt(9, var3.I());
         var4.setInt(10, var3.M());
         var4.setTimestamp(11, var3.J());
         var4.setInt(12, var3.F());
         var4.setInt(13, var3.K());
         var4.setInt(14, var3.L());
         var4.setInt(15, var3.X());
         var4.setInt(16, var3.Y());
         var4.setInt(17, var3.Z());
         var4.setInt(18, var3.aa());
         var4.setTimestamp(19, var3.bb());
         var4.setBoolean(20, var3.D());
         if (var4.executeUpdate() != 1) {
            throw new SQLException("BUG-850-275 reward insert affected unexpected row count");
         }
      }
   }

   public void updateQuestRewardCount(Connection var1, int var2, L1ItemInstance var3, int var4, int var5) throws SQLException {
      try (PreparedStatement var6 = var1.prepareStatement(
         "UPDATE character_items SET count=? WHERE id=? AND char_id=? AND count=?"
      )) {
         var6.setInt(1, var5);
         var6.setInt(2, var3.fr());
         var6.setInt(3, var2);
         var6.setInt(4, var4);
         if (var6.executeUpdate() != 1) {
            throw new SQLException("BUG-850-275 reward stack CAS failed");
         }
      }
   }

   public void deleteQuestRewardItem(Connection var1, int var2, L1ItemInstance var3, int var4) throws SQLException {
      try (PreparedStatement var5 = var1.prepareStatement(
         "DELETE FROM character_items WHERE id=? AND char_id=? AND count=?"
      )) {
         var5.setInt(1, var3.fr());
         var5.setInt(2, var2);
         var5.setInt(3, var4);
         if (var5.executeUpdate() != 1) {
            throw new SQLException("BUG-850-275 reward delete CAS failed");
         }
      }
   }

   private void a(int var1, String var2, int var3) throws SQLException {
      Connection var4 = null;
      PreparedStatement var5 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement(var2.toString());
         var5.setInt(1, var3);
         var5.setInt(2, var1);
         var5.execute();
      } catch (SQLException var10) {
         throw var10;
      } finally {
         SQLUtil.a(var5);
         SQLUtil.a(var4);
      }
   }

   private void a(int var1, String var2, String var3) throws SQLException {
      Connection var4 = null;
      PreparedStatement var5 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement(var2.toString());
         var5.setString(1, var3);
         var5.setInt(2, var1);
         var5.execute();
      } catch (SQLException var10) {
         throw var10;
      } finally {
         SQLUtil.a(var5);
         SQLUtil.a(var4);
      }
   }

   private void a(int var1, String var2, Timestamp var3) throws SQLException {
      Connection var4 = null;
      PreparedStatement var5 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement(var2.toString());
         var5.setTimestamp(1, var3);
         var5.setInt(2, var1);
         var5.execute();
      } catch (SQLException var10) {
         throw var10;
      } finally {
         SQLUtil.a(var5);
         SQLUtil.a(var4);
      }
   }
}
