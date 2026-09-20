package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.aq.L1Craft;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CraftListTable {
   private static final Logger a = Logger.getLogger(CraftListTable.class.getName());
   private static CraftListTable b;
   private final HashMap<Integer, L1Craft> c = new HashMap<>();

   public static CraftListTable a() {
      if (b == null) {
         b = new CraftListTable();
      }

      return b;
   }

   private CraftListTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM craft");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("id");
            String var5 = var3.getString("note");
            L1Craft var6 = new L1Craft(var4);
            String var7 = var3.getString("craft_itemid");
            String var8 = var3.getString("craft_count");
            String var9 = var3.getString("craft_enchant");
            String[] var10 = var7.split(",");
            String[] var11 = var8.split(",");
            String[] var12 = var9.split(",");

            for (int var13 = 0; var13 < var10.length; var13++) {
               try {
                  if (var10[var13].trim().length() > 0) {
                     int var14 = Integer.parseInt(var10[var13]);
                     int var15 = Integer.parseInt(var11[var13]);
                     int var16 = Integer.parseInt(var12[var13]);
                     var6.a(var14, var15, var16);
                  }
               } catch (ArrayIndexOutOfBoundsException var27) {
                  System.out.println("Craft table [" + var5 + "] errer : check craft item");
               }
            }

            String var38 = var3.getString("material");
            String var39 = var3.getString("material_count");
            String var40 = var3.getString("material_enchant");
            String var41 = var3.getString("material_bless");
            String[] var17 = var38.split(",");
            String[] var18 = var39.split(",");
            String[] var19 = var40.split(",");
            String[] var20 = var41.split(",");

            for (int var21 = 0; var21 < var17.length; var21++) {
               try {
                  if (var17[var21].trim().length() > 0) {
                     int var22 = Integer.parseInt(var17[var21]);
                     int var23 = Integer.parseInt(var18[var21]);
                     int var24 = Integer.parseInt(var19[var21]);
                     int var25 = Integer.parseInt(var20[var21]);
                     var6.a(var22, var23, var24, var25);
                  }
               } catch (ArrayIndexOutOfBoundsException var26) {
                  System.out.println("Craft table [" + var5 + "] errer : check material");
               }
            }

            var6.b(var3.getInt("min_level"), var3.getInt("max_level"));
            var6.c(var3.getInt("min_lawful"), var3.getInt("max_lawful"));
            var6.d(var3.getInt("min_karma"), var3.getInt("max_karma"));
            var6.e(var3.getInt("max_count"));
            var6.d(var3.getInt("change"));
            var6.c(var3.getInt("add_chance_itemid"));
            var6.a(var3.getInt("fail_itemid"), var3.getInt("fail_item_count"));
            var6.a(var3.getInt("perfect_chance"));
            var6.b(var3.getInt("craft_nameid"));
            if (this.c.containsKey(var6.a())) {
               System.out.println("CraftListTable : craft ID = " + var6.a() + " repeat!!");
            } else {
               this.c.put(var6.a(), var6);
            }
         }

         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM craft_exchange");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var29 = var3.getInt("craft_id");
            int var30 = var3.getInt("material_itemid");
            int var31 = var3.getInt("exchange_itemid");
            int var32 = var3.getInt("exchange_count");
            int var33 = var3.getInt("exchange_enchant");
            int var34 = var3.getInt("exchange_bless");
            if (!this.c.containsKey(var29)) {
               System.out.println("craft_exchange Table: craftID" + var29 + " is not exist");
            } else {
               L1Craft var35 = this.c.get(var29);

               for (L1ItemInstance var36 : var35.g().values()) {
                  if (var36.N() == var30) {
                     var35.a(var30, var31, var32, var33, var34);
                     break;
                  }
               }
            }
         }
      } catch (Exception var28) {
         a.log(Level.SEVERE, var28.getLocalizedMessage(), var28);
      }

      SQLUtil.a(var3, var2, var1);
   }

   public ArrayList<L1Craft> b() {
      ArrayList var1 = new ArrayList<>();

      for (L1Craft var2 : this.c.values()) {
         var1.add(var2);
      }

      return var1;
   }

   public L1Craft a(int var1) {
      return this.c.get(var1);
   }
}
