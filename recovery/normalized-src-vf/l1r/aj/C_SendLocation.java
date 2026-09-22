package l1r.aj;

import l1r.ao.CharacterGiftTable;
import l1r.ao.ClanTable;
import l1r.ao.ItemTable;
import l1r.ao.SoulTowerTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_PacketBox;
import l1r.be.S_SendLocation;
import l1r.be.S_ServerMessage;
import l1r.bh.L1BookMark;
import l1r.bj.ClientThread;

public class C_SendLocation extends ClientBasePacket {
   private static final String a = "[C] C_SendLocation";

   public C_SendLocation(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.c();
      if (var3 != 13) {
         if (var3 == 11) {
            String var4 = this.g();
            int var5 = this.d();
            int var6 = this.d();
            int var7 = this.d();
            int var8 = this.c();
            if (var4.isEmpty()) {
               return;
            }

            L1PcInstance var9 = L1World.a().a(var4);
            if (var9 != null) {
               L1PcInstance var10 = var2.f();
               String var11 = var10.et();
               var9.a(new S_SendLocation(var11, var5, var6, var7, var8));
               var10.a(new S_ServerMessage(1783, var4));
            }
         } else if (var3 != 6 && var3 != 32) {
            if (var3 == 34) {
               L1PcInstance var12 = var2.f();
               int var20 = this.c();

               for (int var27 = 0; var27 < var12.ba().size(); var27++) {
                  int var34 = this.c();
                  if (var34 == 255) {
                     break;
                  }

                  L1BookMark var41 = var12.ba().get(var27);
                  var41.e(var34);
               }

               for (L1BookMark var28 : var12.ba()) {
                  var28.f(-1);
               }

               for (int var29 = 0; var29 < 5; var29++) {
                  int var36 = this.c();
                  if (var36 == 255) {
                     break;
                  }

                  L1BookMark var42 = var12.ba().get(var36);
                  var42.f(var29);
               }

               L1BookMark.a(var12.ba());
            } else if (var3 == 39) {
               L1PcInstance var13 = var2.f();
               int var21 = this.b();

               for (int var30 = 0; var30 < var21; var30++) {
                  int var37 = this.b();
                  String var43 = this.g();
                  L1BookMark var47 = var13.l(var37);
                  if (var47 != null) {
                     var47.a(var43);
                  }
               }

               L1BookMark.a(var13.ba());
            } else if (var3 == 40) {
               L1PcInstance var14 = var2.f();
               int var22 = this.b();
               int var31 = this.b();
               if (var31 > 60) {
                  var14.a(new S_ServerMessage(2930));
                  return;
               }

               L1ItemInstance var38 = ItemTable.a(var14, 41762, 1);
               L1BookMark.a(var14, var38);
               L1ItemInstance var44 = var14.j().e(var22);
               var14.j().f(var44);
            } else if (var3 == 46) {
               L1PcInstance var15 = var2.f();
               if (var15.aH() != 4 && var15.aH() != 10) {
                  return;
               }

               int var23 = this.c();
               L1Clan var32 = ClanTable.a().a(var15.aF());
               if (var32 == null) {
                  return;
               }

               var32.e(var23);
               ClanTable.a().b(var32);

               for (L1PcInstance var39 : var32.b()) {
                  var39.a(new S_PacketBox(173, var23));
               }
            } else if (var3 == 48) {
               int var16 = this.d();
               int var24 = this.d();
               int var33 = 0;
               int var40 = 0;
               L1PcInstance var46 = var2.f();
               if (var16 == 1) {
                  if (var24 == 0) {
                     var33 = 34079 + (int)(Math.random() * 12.0);
                     var40 = 33136 + (int)(Math.random() * 15.0);
                  } else if (var24 == 1) {
                     var33 = 33970 + (int)(Math.random() * 10.0);
                     var40 = 33243 + (int)(Math.random() * 14.0);
                  } else if (var24 == 2) {
                     var33 = 33925 + (int)(Math.random() * 14.0);
                     var40 = 33351 + (int)(Math.random() * 9.0);
                  }
               } else if (var16 == 2) {
                  if (var24 == 0) {
                     var33 = 32615 + (int)(Math.random() * 11.0);
                     var40 = 32719 + (int)(Math.random() * 7.0);
                  } else if (var24 == 1) {
                     var33 = 32621 + (int)(Math.random() * 9.0);
                     var40 = 32788 + (int)(Math.random() * 13.0);
                  }
               } else if (var16 == 3) {
                  if (var24 == 0) {
                     var33 = 33501 + (int)(Math.random() * 11.0);
                     var40 = 32765 + (int)(Math.random() * 9.0);
                  } else if (var24 == 1) {
                     var33 = 33440 + (int)(Math.random() * 11.0);
                     var40 = 32784 + (int)(Math.random() * 11.0);
                  }
               } else if (var16 == 4) {
                  int[][] var48 = new int[][]{
                     {32838, 32886},
                     {32800, 32874},
                     {32755, 32899},
                     {32741, 32938},
                     {32740, 32964},
                     {32801, 32982},
                     {32845, 32986},
                     {32852, 32932},
                     {32799, 32927}
                  };
                  if (var24 < 0 || var24 >= var48.length) {
                     return;
                  }
                  var33 = var48[var24][0];
                  var40 = var48[var24][1];
               }

               L1Teleport.a(var46, var33, var40, var46.fp(), var46.fb(), true);
               var46.a(new S_PacketBox(176, var46));
            } else if (var3 == 54) {
               int var17 = this.b();
               L1PcInstance var25 = var2.f();
               var25.a(new S_PacketBox(185, var17));
            } else if (var3 == 55) {
               int var18 = this.c();
               L1PcInstance var26 = var2.f();
               CharacterGiftTable.a().a(var26, var18);
            } else if (var3 == 58) {
               L1PcInstance var19 = var2.f();
               SoulTowerTable.a().a(var19);
            }
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_SendLocation";
   }
}
