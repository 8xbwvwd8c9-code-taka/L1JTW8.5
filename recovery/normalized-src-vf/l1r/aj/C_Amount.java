package l1r.aj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import l1r.ao.CharacterItemTable;
import l1r.ao.HouseTable;
import l1r.ao.HtmlCraftTable;
import l1r.ao.InnTable;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_Html;
import l1r.be.S_ServerMessage;
import l1r.bh.L1House;
import l1r.bj.ClientThread;
import l1r.l1j.server.DatabaseFactory;

public class C_Amount extends ClientBasePacket {
   public C_Amount(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.b();
         int var6 = this.c();
         String var7 = this.g();
         L1NpcInstance var8 = (L1NpcInstance)L1World.a().a(var4);
         if (var8 != null) {
            if (!HtmlCraftTable.a().a(var7, var3, var8, var5)) {
               String var9 = "";
               String var10 = "";

               try {
                  StringTokenizer var11 = new StringTokenizer(var7);
                  var9 = var11.nextToken();
                  var10 = var11.nextToken();
               } catch (NoSuchElementException var17) {
                  var9 = "";
                  var10 = "";
               }

               if (var9.equalsIgnoreCase("agapply")) {
                  if (!var3.consumeL1rAmountContext(var4, 1)) {
                     return;
                  }

                  for (L1House var20 : HouseTable.a().c().values()) {
                     if (var3.et().equalsIgnoreCase(var20.n())) {
                        var3.a(new S_ServerMessage(523));
                        return;
                     }
                  }

                  int var21 = Integer.valueOf(var10);
                  L1Clan var32 = ClanTable.a().a(var3.aF());
                  if (var32 == null || !var3.x() || var3.fr() != var32.k() || var3.ev() < 15 || var32.n() != 0) {
                     var3.a(new S_ServerMessage(518));
                     return;
                  }

                  L1House var24 = HouseTable.a().a(var21);
                  if (var24 == null || !var24.g() || var24.j() == null || var24.j().getTime() <= System.currentTimeMillis() || var8.fu().c(var3.fu()) > 11) {
                     return;
                  }

                  long var33 = var24.o() == 0 ? (long)var24.k() : (long)var24.k() + 1L;
                  if (var5 <= 0 || var5 > 2000000000 || (long)var5 < var33) {
                     return;
                  }

                  if (!var3.j().g(40308, var5)) {
                     var3.a(new S_ServerMessage(189));
                     return;
                  }

                  if (!this.commitAuctionBidAtomic(var3, var24, var5)) {
                     return;
                  }
               } else if (var9.equalsIgnoreCase("agsell")) {
                  if (!var3.consumeL1rAmountContext(var4, 2) || var8.fu().c(var3.fu()) > 11) {
                     return;
                  }

                  int var22 = Integer.valueOf(var10);
                  L1Clan var31 = ClanTable.a().a(var3.aF());
                  if (var31 == null || var31.n() != var22 || !var3.x() || var3.fr() != var31.k()) {
                     var3.a(new S_ServerMessage(518));
                     return;
                  }

                  L1House var25 = HouseTable.a().a(var22);
                  if (var25 == null || var8.z() != var25.f() || var25.g()) {
                     return;
                  }

                  if (var5 < 100000 || var5 > 2000000000) {
                     return;
                  }

                  Timestamp var27 = new Timestamp(System.currentTimeMillis() + 432000000L);
                  var25.b(var27);
                  var25.d(var5);
                  var25.c(var3.et());
                  var25.e(var3.fr());
                  var25.d("");
                  var25.f(0);
                  var25.a(true);
                  var25.b(false);
                  HouseTable.a().a(var25);
               } else {
                  int var23 = var8.z();
                  if (var23 == 70070
                     || var23 == 70019
                     || var23 == 70075
                     || var23 == 70012
                     || var23 == 70031
                     || var23 == 70084
                     || var23 == 70065
                     || var23 == 70054
                     || var23 == 70096) {
                     if (!var3.j().g(40308, 300 * var5)) {
                        var3.a(new S_Html(var23, "inn3", var8.et()));
                        return;
                     }

                     if (!InnTable.a().a(var3.dM())) {
                        var3.a(new S_Html(var23, ""));
                        return;
                     }

                     L1ItemInstance var26 = ItemTable.a().b(40312);
                     var26.e(var5);
                     var26.j(var26.fr());
                     var3.j().b(40308, 300 * var5);
                     L1Inventory var28;
                     if (var3.j().a(var26, var5) == 0) {
                        var28 = var3.j();
                     } else {
                        var28 = L1World.a().a(var3.fu());
                     }

                     var28.d(var26);
                     InnTable.a().a(var26.M(), var5, var3.dM());
                     var3.a(new S_ServerMessage(143, var8.et(), var26.s()));
                     var3.a(new S_Html(var23, "inn4", var8.et()));
                  }
               }
            }
         }
      }
   }

   private boolean commitAuctionBidAtomic(L1PcInstance var1, L1House var2, int var3) {
      synchronized(var2) {
         L1ItemInstance var4 = var1.j().b(40308);
         if (var4 == null || var4.E() < var3) {
            return false;
         }

         int var5 = var4.E();
         int var6 = var5 - var3;
         int var7 = var2.k();
         int var8 = var2.o();
         String var9 = var2.n();
         Timestamp var10 = var2.j();
         L1PcInstance var11 = var8 == 0 ? null : (L1PcInstance)L1World.a().a(var8);
         L1ItemInstance var12 = null;
         L1ItemInstance var13 = null;
         int var14 = 0;
         int var15 = 0;

         Connection var16 = null;
         boolean var17 = true;
         boolean var18 = false;

         try {
            var16 = DatabaseFactory.a().b();
            this.requireAuctionBidInnoDb(var16);
            var17 = var16.getAutoCommit();
            var16.setAutoCommit(false);

            try (PreparedStatement var19 = var16.prepareStatement(
               "UPDATE house SET price=?, bidder=?, bidder_id=? WHERE house_id=? AND is_on_sale=1 AND price=? AND bidder_id=? AND deadline=?"
            )) {
               var19.setInt(1, var3);
               var19.setString(2, var1.et());
               var19.setInt(3, var1.fr());
               var19.setInt(4, var2.b());
               var19.setInt(5, var7);
               var19.setInt(6, var8);
               var19.setTimestamp(7, var10);
               if (var19.executeUpdate() != 1) {
                  throw new SQLException("BUG-850-142 house bid CAS failed");
               }
            }

            CharacterItemTable var28 = CharacterItemTable.a();
            if (var6 == 0) {
               var28.deleteQuestRewardItem(var16, var1.fr(), var4, var5);
            } else {
               var28.updateQuestRewardCount(var16, var1.fr(), var4, var5, var6);
            }

            if (var8 != 0 && var7 > 0) {
               if (var11 != null) {
                  var12 = var11.j().b(40308);
                  if (var12 != null) {
                     var14 = var12.E();
                     long var20 = (long)var14 + (long)var7;
                     if (var20 > 2147483647L) {
                        throw new SQLException("BUG-850-142 old bidder Adena overflow");
                     }
                     var15 = (int)var20;
                     var28.updateQuestRewardCount(var16, var8, var12, var14, var15);
                  } else {
                     var13 = ItemTable.a().b(40308);
                     if (var13 == null) {
                        throw new SQLException("BUG-850-142 refund item template unavailable");
                     }
                     var13.e(var7);
                     var28.insertQuestReward(var16, var8, var13);
                  }
               } else {
                  try (PreparedStatement var21 = var16.prepareStatement(
                     "SELECT id,count FROM character_items WHERE char_id=? AND item_id=40308 ORDER BY id LIMIT 1 FOR UPDATE"
                  )) {
                     var21.setInt(1, var8);
                     try (ResultSet var22 = var21.executeQuery()) {
                        if (var22.next()) {
                           int var23 = var22.getInt("id");
                           int var24 = var22.getInt("count");
                           long var25 = (long)var24 + (long)var7;
                           if (var25 > 2147483647L) {
                              throw new SQLException("BUG-850-142 offline old bidder Adena overflow");
                           }
                           try (PreparedStatement var26 = var16.prepareStatement(
                              "UPDATE character_items SET count=? WHERE id=? AND char_id=? AND count=?"
                           )) {
                              var26.setInt(1, (int)var25);
                              var26.setInt(2, var23);
                              var26.setInt(3, var8);
                              var26.setInt(4, var24);
                              if (var26.executeUpdate() != 1) {
                                 throw new SQLException("BUG-850-142 offline refund CAS failed");
                              }
                           }
                        } else {
                           var13 = ItemTable.a().b(40308);
                           if (var13 == null) {
                              throw new SQLException("BUG-850-142 offline refund item template unavailable");
                           }
                           var13.e(var7);
                           var28.insertQuestReward(var16, var8, var13);
                        }
                     }
                  }
               }
            }

            var16.commit();
            var18 = true;
         } catch (Exception var32) {
            if (var16 != null) {
               try {
                  var16.rollback();
               } catch (SQLException var31) {
               }
            }
         } finally {
            if (var16 != null) {
               try {
                  var16.setAutoCommit(var17);
               } catch (SQLException var30) {
               }
               try {
                  var16.close();
               } catch (SQLException var29) {
               }
            }
         }

         if (!var18) {
            return false;
         }

         var2.d(var3);
         var2.d(var1.et());
         var2.f(var1.fr());

         if (var6 == 0) {
            var1.j().publishCommittedQuestDelete(var4);
         } else {
            var1.j().publishCommittedQuestUpdate(var4, var6);
         }

         if (var11 != null && var8 != 0 && var7 > 0) {
            if (var12 != null) {
               var11.j().publishCommittedQuestUpdate(var12, var15);
            } else if (var13 != null) {
               var11.j().publishCommittedQuestInsert(var13);
            }
            var11.a(new S_ServerMessage(525, String.valueOf(var7)));
         }

         return true;
      }
   }

   private void requireAuctionBidInnoDb(Connection var1) throws SQLException {
      try (PreparedStatement var2 = var1.prepareStatement(
         "SELECT TABLE_NAME,ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('house','character_items')"
      ); ResultSet var3 = var2.executeQuery()) {
         int var4 = 0;
         while (var3.next()) {
            if (!"InnoDB".equalsIgnoreCase(var3.getString("ENGINE"))) {
               throw new SQLException("BUG-850-142 requires InnoDB auction tables");
            }
            ++var4;
         }
         if (var4 != 2) {
            throw new SQLException("BUG-850-142 missing auction transaction table");
         }
      }
   }

   @Override
   public String a() {
      return "C_Amount";
   }
}
