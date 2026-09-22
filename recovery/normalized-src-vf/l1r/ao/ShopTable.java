package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1TaxCalculator;
import l1r.aq.L1TownLocation;
import l1r.aq.L1World;
import l1r.as.L1BugBearRace;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Account;
import l1r.bh.L1Castle;
import l1r.bh.L1Item;
import l1r.bh.L1Npc;
import l1r.bh.L1Shop;
import l1r.bh.L1ShopItem;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class ShopTable {
   private static final Logger a = Logger.getLogger(ShopTable.class.getName());
   private static ShopTable b;
   private final Map<Integer, L1Shop> c = new HashMap<>();

   public static ShopTable a() {
      if (b == null) {
         b = new ShopTable();
      }

      return b;
   }

   private ShopTable() {
      this.e();
      this.c();
   }

   private void c() {
      this.a(81002, 2, new int[]{13});
      this.a(81012, 2, new int[]{1});
      this.a(81013, 2, new int[]{8, 10});
      this.a(81014, 2, new int[]{7});
      this.a(81015, 2, new int[]{6});
      this.a(81016, 2, new int[]{3});
      this.a(81017, 2, new int[]{2});
      this.a(81018, 2, new int[]{4});
      this.a(81019, 2, new int[]{11});
      this.a(81020, 2, new int[]{9});
      this.a(81021, 2, new int[]{12});
      this.a(81031, 2, new int[]{5});
      this.a(81032, 2, new int[]{23});
      this.a(81033, 2, new int[]{15, 18, 16});
      this.a(81003, 1, new int[]{7});
      this.a(81004, 1, new int[]{1});
      this.a(81005, 1, new int[]{257});
      this.a(81006, 1, new int[]{5, 261});
      this.a(81007, 1, new int[]{2, 258});
      this.a(81008, 1, new int[]{260});
      this.a(81009, 1, new int[]{264});
      this.a(81010, 1, new int[]{6, 262});
      this.a(81011, 1, new int[]{3, 259, 9});
      this.a(81241, 1, new int[]{266});
      this.a(81242, 1, new int[]{8});
      this.a(81034, 0, new int[]{17, 22});
      this.a(81027, 0, new int[]{6, 23, 24, 25, 26, 27});
      this.a(81028, 0, new int[]{8, 28, 31, 32});
   }

   private void a(int var1, int var2, int[] var3) {
      HashMap var4 = ItemTable.a().c();
      if (var2 == 1) {
         var4 = ItemTable.a().d();
      } else if (var2 == 0) {
         var4 = ItemTable.a().b();
      }

      ArrayList var5 = new ArrayList<>();

      for (L1Item var6 : var4.values()) {
         int[] var11 = var3;
         int var10 = var3.length;

         for (int var9 = 0; var9 < var10; var9++) {
            int var8 = var11[var9];
            if (var6.aP() == var8) {
               L1ShopItem var12 = new L1ShopItem(var6.g(), 1, 1);
               var5.add(var12);
            }
         }
      }

      Collections.sort(var5, new Comparator<L1ShopItem>() {
         public int a(L1ShopItem var1, L1ShopItem var2x) {
            return var1.a() - var2x.a();
         }

         // $VF: synthetic method
         @Override
         public int compare(Object var1, Object var2) {
            return this.a((L1ShopItem)var1, (L1ShopItem)var2);
         }
      });
      this.c.put(var1, new L1Shop(var1, var5, new ArrayList<>()));
   }

   public void b() {
      for (L1Shop var1 : this.c.values()) {
         int var3 = var1.a();
         L1Npc var4 = NpcTable.a().a(var3);
         if (var4 == null) {
            System.out.println("npcid: " + var3 + " = null");
         } else {
            String var5 = var4.c();
            List var6 = var1.b();
            var6.addAll(var1.c());

            for (L1ShopItem var7 : var6) {
               int var9 = var7.a();
               L1Item var10 = ItemTable.a().a(var9);
               if (var10 == null) {
                  System.out.println("itemid: " + var9 + " = null");
               } else {
                  String var11 = var10.h();
                  Connection var12 = null;
                  PreparedStatement var13 = null;

                  try {
                     var12 = DatabaseFactory.a().b();
                     var13 = var12.prepareStatement("UPDATE shop SET item_name=? WHERE item_id=?");
                     var13.setString(1, var11);
                     var13.setInt(2, var9);
                     var13.execute();
                     var13 = var12.prepareStatement("UPDATE shop SET npc_name=? WHERE npc_id=?");
                     var13.setString(1, var5);
                     var13.setInt(2, var3);
                     var13.execute();
                  } catch (SQLException var15) {
                     a.log(Level.SEVERE, var15.getLocalizedMessage(), var15);
                  }

                  SQLUtil.a(var13);
                  SQLUtil.a(var12);
               }
            }
         }
      }
   }

   private List<Integer> d() {
      ArrayList var1 = new ArrayList<>();
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT DISTINCT npc_id FROM shop");
         var4 = var3.executeQuery();

         while (var4.next()) {
            var1.add(var4.getInt("npc_id"));
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return var1;
   }

   private L1Shop a(int var1, ResultSet var2) throws SQLException {
      ArrayList var3 = new ArrayList<>();
      ArrayList var4 = new ArrayList<>();

      while (var2.next()) {
         int var5 = var2.getInt("item_id");
         int var6 = var2.getInt("selling_price");
         int var7 = var2.getInt("purchasing_price");
         int var8 = var2.getInt("pack_count");
         var8 = var8 == 0 ? 1 : var8;
         if (var6 >= 0) {
            L1ShopItem var9 = new L1ShopItem(var5, var6, var8);
            var3.add(var9);
         }

         if (var7 >= 0) {
            L1ShopItem var11 = new L1ShopItem(var5, var7, var8);
            var4.add(var11);
         }
      }

      return new L1Shop(var1, var3, var4);
   }

   private void e() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM shop WHERE npc_id=? ORDER BY order_id");

         for (int var4 : this.d()) {
            var2.setInt(1, var4);
            var3 = var2.executeQuery();
            L1Shop var6 = this.a(var4, var3);
            this.c.put(var4, var6);
            var3.close();
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public L1Shop a(int var1) {
      return this.c.get(var1);
   }

   private L1ShopItem a(int var1, List<L1ShopItem> var2) {
      for (L1ShopItem var3 : var2) {
         if (var3.a() == var1) {
            return var3;
         }
      }

      return null;
   }

   public void a(L1PcInstance var1, ArrayList<int[]> var2, L1NpcInstance var3, int var4) {
      L1Shop var5 = this.c.get(var3.z());
      if (var5 == null) {
         a.log(Level.SEVERE, "不存在的商店,npcid=" + var3.z());
      } else {
         long var6 = 0L;
         ArrayList<int[]> validatedOrderList = new ArrayList<>();

         for (int[] var8 : var2) {
            int var10 = var8[0];
            int var11 = var8[1];
            L1ItemInstance var12 = var1.j().e(var10);
            if (var12 == null) {
               var1.a(new S_ServerMessage(156));
            } else {
               L1ShopItem var13 = this.a(var12.N(), var5.c());
               if (var13 == null) {
                  a.log(Level.SEVERE, "收購道具-不存在的shop_item!! npcid=" + var3.z() + "itemid=" + var12.N());
               } else {
                  int var14 = var12.E() - var11 >= 0 ? var11 : var12.E();
                  int var15 = (int)(var13.c() * var14 * Config.M / var13.d());
                  if (var12.N() == 40309) {
                     var15 = L1BugBearRace.a().a(var12.fr()) * var14;
                  }

                  var6 += var15;
                  validatedOrderList.add(new int[]{var10, var14});
                  HistoryTable.a().d(var1, "賣給商店", var12, var14);
               }
            }
         }

         if (var6 > 2000000000L) {
            var1.a(new S_SystemMessage("總金額無法超過2000000000金幣。"));
         } else if (var6 <= 0L) {
            var1.a(new S_SystemMessage("總金額" + var6 + "金幣，交易失敗。"));
         } else {
            int var16 = var1.j().g(var4);
            if (var6 + var16 > 2000000000L) {
               var1.a(new S_SystemMessage("總共販賣價格無法超過" + (2000000000 - var16) + "金幣。"));
            } else {
               for (int[] var17 : validatedOrderList) {
                  int var19 = var17[0];
                  int var20 = var17[1];
                  var1.j().c(var19, var20);
               }

               ItemTable.a(var1, var4, (int)var6, 0, var3.T());
            }
         }
      }
   }

   public void b(L1PcInstance var1, ArrayList<int[]> var2, L1NpcInstance var3, int var4) {
      L1Shop var5 = this.c.get(var3.z());
      if (var5 == null) {
         a.log(Level.SEVERE, "不存在的商店,npcid=" + var3.z());
      } else {
         L1TaxCalculator var6 = new L1TaxCalculator(var3);
         long var7 = 0L;
         long var9 = 0L;
         int var11 = 0;
         int var12 = var1.j().c();

         for (int[] var13 : var2) {
            int var15 = var13[0];
            int var16 = var13[1];
            L1ShopItem var17 = this.a(var15, var5.b());
            if (var17 == null) {
               a.log(Level.SEVERE, "販賣道具-不存在的shop_item!! npcid=" + var3.z() + "itemid=" + var15);
            } else {
               int var18 = (int)(var17.c() * Config.L);
               var7 += var18 * var16;
               var9 += var6.a(var18) * var16;
               var11 += var17.b().l() * var16 * var17.d();
               L1Item var19 = var17.b();
               if (var19.aF() && !var1.j().f(var19.g())) {
                  var12++;
               } else {
                  var12++;
               }
            }
         }

         if (var4 != 40308) {
            var9 = var7;
         }

         if (var9 > 2000000000L) {
            var1.a(new S_ServerMessage(904, 2000000000));
         } else if (!var1.j().g(var4, (int)var9)) {
            if (var4 == 40308) {
               var1.a(new S_ServerMessage(189));
            } else if (var4 == 640268) {
               var1.a(new S_ServerMessage(3429));
            } else {
               var1.a(new S_ServerMessage(2742));
            }
         } else {
            int var26 = var1.j().e() * 1000;
            if (var26 + var11 > var1.K() * 1000.0) {
               var1.a(new S_ServerMessage(82));
            } else if (var12 > 180) {
               var1.a(new S_ServerMessage(263));
            } else if (!var1.j().b(var4, (int)var9)) {
               var1.a(new S_ServerMessage(1752));
            } else {
               for (int[] var27 : var2) {
                  int var31 = var27[0];
                  int var35 = var27[1];
                  L1ShopItem var37 = this.a(var31, var5.b());
                  boolean var39 = true;
                  int var20 = 0;
                  int var21 = var35 * var37.d();
                  if (var3.z() == 70068 || var3.z() == 70020) {
                     var39 = false;
                     int var22 = Random.a(100) + 1;
                     var20 = -1;
                  }

                  ItemTable.a(var1, var31, var21, var20, var39, false);
               }

               if (var4 == 40308) {
                  int var28 = L1CastleLocation.a(var3);
                  int var30 = var6.b((int)var7);
                  if (var28 != 0 && var30 > 0) {
                     L1Castle var32 = CastleTable.a().a(var28);
                     synchronized (var32) {
                        if (var32.f() < 2000000000) {
                           var32.b(var32.f() + var30);
                           CastleTable.a().a(var32);
                        }
                     }
                  }

                  if (!L1World.a().l()) {
                     int var33 = L1TownLocation.a((L1Character)var3);
                     if (var33 >= 1 && var33 <= 10) {
                        TownTable.a().a(var33, (int)var7);
                     }
                  }

                  int var34 = var6.d((int)var7);
                  L1Castle var36 = CastleTable.a().a(7);
                  L1Castle var38 = CastleTable.a().a(8);
                  if (var34 > 0) {
                     synchronized (var36) {
                        if (var36.f() < 2000000000) {
                           var36.b(var36.f() + var34 / 2);
                           CastleTable.a().a(var36);
                        }
                     }

                     synchronized (var38) {
                        if (var38.f() < 2000000000) {
                           var38.b(var38.f() + var34 / 2);
                           CastleTable.a().a(var38);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void a(L1PcInstance var1, ArrayList<int[]> var2, L1NpcInstance var3) {
      L1Shop var4 = this.c.get(var3.z());
      if (var4 == null) {
         a.log(Level.SEVERE, "不存在的商店,npcid=" + var3.z());
      } else {
         L1TaxCalculator var5 = new L1TaxCalculator(var3);
         long var6 = 0L;
         int var8 = 0;
         int var9 = var1.j().c();

         for (int[] var10 : var2) {
            int var12 = var10[0];
            int var13 = var10[1];
            L1ShopItem var14 = var4.b().get(var12);
            if (var14 == null) {
               a.log(Level.SEVERE, "販賣道具-不存在的shop_item!! npcid=" + var3.z());
            } else {
               int var15 = (int)(var14.c() * Config.L);
               var6 += var15 * var13;
               var8 += var14.b().l() * var13 * var14.d();
               L1Item var16 = var14.b();
               if (var16.aF() && !var1.j().f(var16.g())) {
                  var9++;
               } else {
                  var9++;
               }
            }
         }

         if (var6 > 2000000000L) {
            var1.a(new S_ServerMessage(904, 2000000000));
         } else if (!var1.j().g(40308, (int)var6)) {
            var1.a(new S_ServerMessage(189));
         } else {
            int var20 = var1.j().e() * 1000;
            if (var20 + var8 > var1.K() * 1000.0) {
               var1.a(new S_ServerMessage(82));
            } else if (var9 > 180) {
               var1.a(new S_ServerMessage(263));
            } else if (!var1.j().b(40308, (int)var6)) {
               var1.a(new S_ServerMessage(1752));
            } else {
               for (int[] var21 : var2) {
                  int var25 = var21[0];
                  int var29 = var21[1];
                  L1ShopItem var31 = var4.b().get(var25);
                  L1ItemInstance var33 = ItemTable.a().b(var31.a());
                  var33.e(var29 * var31.d());
                  var33.a(true);
                  var33.a(var31.b());
                  var1.j().d(var33);
               }

               int var22 = L1CastleLocation.a(var3);
               int var24 = var5.b((int)var6);
               if (var22 != 0 && var24 > 0) {
                  L1Castle var26 = CastleTable.a().a(var22);
                  synchronized (var26) {
                     if (var26.f() < 2000000000) {
                        var26.b(var26.f() + var24);
                        CastleTable.a().a(var26);
                     }
                  }
               }

               if (!L1World.a().l()) {
                  int var27 = L1TownLocation.a((L1Character)var3);
                  if (var27 >= 1 && var27 <= 10) {
                     TownTable.a().a(var27, (int)var6);
                  }
               }

               int var28 = var5.d((int)var6);
               L1Castle var30 = CastleTable.a().a(7);
               L1Castle var32 = CastleTable.a().a(8);
               if (var28 > 0) {
                  synchronized (var30) {
                     if (var30.f() < 2000000000) {
                        var30.b(var30.f() + var28 / 2);
                        CastleTable.a().a(var30);
                     }
                  }

                  synchronized (var32) {
                     if (var32.f() < 2000000000) {
                        var32.b(var32.f() + var28 / 2);
                        CastleTable.a().a(var32);
                     }
                  }
               }
            }
         }
      }
   }

   public void b(L1PcInstance var1, ArrayList<int[]> var2, L1NpcInstance var3) {
      L1Shop var4 = this.c.get(var3.z());
      if (var4 == null) {
         a.log(Level.SEVERE, "不存在的商店,npcid=" + var3.z());
      } else {
         long var5 = 0L;
         int var7 = 0;
         int var8 = var1.j().c();

         for (int[] var9 : var2) {
            int var11 = var9[0];
            int var12 = var9[1];
            L1ShopItem var13 = this.a(var11, var4.b());
            if (var13 == null) {
               a.log(Level.SEVERE, "Tam-不存在的shop_item!! npcid=" + var3.z() + "itemid=" + var11);
            } else {
               int var14 = (int)(var13.c() * Config.L);
               var5 += var14 * var12;
               var7 += var13.b().l() * var12 * var13.d();
               L1Item var15 = var13.b();
               if (var15.aF() && !var1.j().f(var15.g())) {
                  var8++;
               } else {
                  var8++;
               }
            }
         }

         L1Account var16 = var1.aK().e();
         if (var16.q() < var5) {
            var1.a(new S_ServerMessage(3901));
         } else {
            int var17 = var1.j().e() * 1000;
            if (var17 + var7 > var1.K() * 1000.0) {
               var1.a(new S_ServerMessage(82));
            } else if (var8 > 180) {
               var1.a(new S_ServerMessage(263));
            } else {
               var16.h(var16.q() - (int)var5);
               AccountTable.a().d(var16);
               var1.a(new S_ProtoBuffers(450, var16.q()));

               for (int[] var18 : var2) {
                  int var20 = var18[0];
                  int var21 = var18[1];
                  ItemTable.a(var1, var20, var21, 0, true, false);
               }
            }
         }
      }
   }
}
