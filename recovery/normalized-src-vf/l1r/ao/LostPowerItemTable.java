package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.au.L1Inventory;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class LostPowerItemTable {
   private static final Logger a = Logger.getLogger(LostPowerItemTable.class.getName());
   private final HashMap<Integer, Integer> b = new HashMap<>();
   private final HashMap<Integer, Integer> c = new HashMap<>();
   private static LostPowerItemTable d;

   public static LostPowerItemTable a() {
      if (d == null) {
         d = new LostPowerItemTable();
      }

      return d;
   }

   public LostPowerItemTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM lost_power_item");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("itemid");
            int var5 = var3.getInt("change_itemid");
            if (var4 > 0 && var5 > 0) {
               if (!this.b.containsKey(var4)) {
                  this.b.put(var4, var5);
               }

               if (!this.c.containsKey(var5)) {
                  this.c.put(var5, var4);
               }
            }
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public int a(int var1) {
      return this.b.containsKey(var1) ? this.b.get(var1) : 0;
   }

   public void a(int var1, double var2, L1Inventory var4) {
      if (this.c.containsKey(var1)) {
         int var5 = this.c.get(var1);
         if (var2 * 5.0 > Random.a(1000000) + 1) {
            L1ItemInstance var6 = ItemTable.a().b(var5);
            var4.d(var6);
         }
      }
   }

   public HashMap<Integer, Integer> b() {
      return this.b;
   }
}
