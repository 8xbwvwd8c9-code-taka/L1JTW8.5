package l1r.au;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.bh.L1Item;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1ClanInventory extends L1Inventory {
   private static final Logger g = Logger.getLogger(L1ClanInventory.class.getName());
   private final L1Clan h;

   public L1ClanInventory(L1Clan var1) {
      this.h = var1;
   }

   @Override
   public synchronized void a() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM clan_warehouse WHERE clan_name = ?");
         var2.setString(1, this.h.f());
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("item_id");
            int var5 = var3.getInt("count");
            L1Item var6 = ItemTable.a().a(var4);
            if (var6 == null) {
               throw new NullPointerException("item_id=" + var4 + " not found");
            }

            L1ItemInstance var7 = new L1ItemInstance(var6, var5);
            var7.cF(var3.getInt("id"));
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
   public synchronized void a(L1ItemInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement(
            "INSERT INTO clan_warehouse SET id = ?, clan_name = ?, item_id = ?, item_name = ?, count = ?, is_equipped=0, enchantlvl = ?, is_id= ?, durability = ?, charge_count = ?, temp_value = ?, last_used = ?, bless = ?, attr_enchant_kind = ?, attr_enchant_level = ?,super_enchant_field_1 = ? ,super_enchant_field_2 = ?,super_enchant_field_3 = ? ,super_enchant_field_4=?,limit_time=?"
         );
         var3.setInt(1, var1.fr());
         var3.setString(2, this.h.f());
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
   public synchronized void b(L1ItemInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE clan_warehouse SET count = ? WHERE id = ?");
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
   public synchronized void c(L1ItemInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM clan_warehouse WHERE id = ?");
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

   public synchronized void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("DELETE FROM clan_warehouse WHERE clan_name = ?");
         var2.setString(1, this.h.f());
         var2.execute();
      } catch (SQLException var7) {
         g.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public void a(L1PcInstance var1, L1ItemInstance var2, int var3, int var4) {
      Connection var5 = null;
      PreparedStatement var6 = null;

      try {
         var5 = DatabaseFactory.a().b();
         var6 = var5.prepareStatement("INSERT INTO clan_warehouse_history SET clan_id=?, char_name=?, type=?, item_name=?, item_count=?, record_time=?");
         var6.setInt(1, this.h.e());
         var6.setString(2, var1.et());
         var6.setInt(3, var4);
         var6.setString(4, var2.b());
         var6.setInt(5, var3);
         var6.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
         var6.execute();
      } catch (SQLException var11) {
         g.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var6);
         SQLUtil.a(var5);
      }
   }
}
