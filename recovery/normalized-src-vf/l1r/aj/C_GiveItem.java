package l1r.aj;

import l1r.ao.HistoryTable;
import l1r.ao.ItemTable;
import l1r.ao.PetItemTable;
import l1r.ao.PetTable;
import l1r.ao.PetTypeTable;
import l1r.ap.L1DollInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.au.L1PcInventory;
import l1r.be.S_ItemName;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Npc;
import l1r.bh.L1PetItem;
import l1r.bh.L1PetType;
import l1r.bi.Random;
import l1r.bj.ClientThread;

public class C_GiveItem extends ClientBasePacket {
   private static final String a = "[C] C_GiveItem";
   private static final String[] b = new String[]{"L1Npc", "L1Monster", "L1Guardian", "L1Guard"};

   public C_GiveItem(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.b();
         this.d();
         this.d();
         int var5 = this.b();
         int var6 = this.b();
         L1Object var7 = L1World.a().a(var4);
         if (var7 != null && var7 instanceof L1NpcInstance) {
            L1NpcInstance var8 = (L1NpcInstance)var7;
            if (this.a(var8.U_())) {
               L1Inventory var9 = var8.y();
               L1Inventory var10 = var3.j();
               L1ItemInstance var11 = var10.e(var5);
               if (var11 != null) {
                  if (var11.D()) {
                     var3.a(new S_ServerMessage(141));
                  } else if (!var11.a().s()) {
                     var3.a(new S_ServerMessage(210, var11.a().h()));
                  } else if (var11.F() >= 128) {
                     var3.a(new S_ServerMessage(210, var11.a().h()));
                  } else {
                     for (L1NpcInstance var12 : var3.ek().values()) {
                        if (var12 instanceof L1PetInstance) {
                           L1PetInstance var14 = (L1PetInstance)var12;
                           if (var11.fr() == var14.k()) {
                              var3.a(new S_ServerMessage(1187));
                              return;
                           }
                        }
                     }

                     for (L1DollInstance var16 : var3.el().values()) {
                        if (var16.f() == var11.fr()) {
                           var3.a(new S_ServerMessage(1181));
                           return;
                        }
                     }

                     if (var9.a(var11, var6) != 0) {
                        var3.a(new S_ServerMessage(942));
                     } else {
                        var11 = var10.a(var11, var6, var9);
                        var8.a(var11);
                        var8.fg();
                        var3.fg();
                        HistoryTable.a().a(var3, "給予(" + var8.et() + ")", var11, var6);
                        L1PetType var17 = PetTypeTable.b().a(var8.U_().b());
                        if (var17 != null && !var8.eX()) {
                           if (var11.N() == var17.d()) {
                              this.a(var3, var8);
                           } else if (var11.N() == var17.i()) {
                              this.a(var3, var8, var11.N());
                           }

                           if (var11.f()) {
                              if (var11.a().aP() == 7) {
                                 this.a(var8, var11);
                              } else if (var11.a().aP() == 11 && var17.j()) {
                                 this.b(var8, var11);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void a(L1NpcInstance var1, L1ItemInstance var2) {
      if (var1 instanceof L1PetInstance && var2.a().V() != 0) {
         L1PetInstance var3 = (L1PetInstance)var1;
         if (var3.fj() < 100) {
            while (var3.fj() < 100 && var3.y().b(var2.N(), 1)) {
               int var4 = var3.fj() + var2.a().V() / 10;
               var3.c_(var4 > 100 ? 100 : var4);
            }

            PetTable.a().a(var3);
         }
      }
   }

   private void b(L1NpcInstance var1, L1ItemInstance var2) {
      if (var1 instanceof L1PetInstance) {
         L1PetInstance var3 = (L1PetInstance)var1;
         L1PetItem var4 = PetItemTable.a().a(var2.N());
         if (var4.n() == 1) {
            var3.a(var3, var2);
         } else if (var4.n() == 0) {
            var3.b(var3, var2);
         }
      }
   }

   private boolean a(L1Npc var1) {
      String[] var5 = b;
      int var4 = b.length;

      for (int var3 = 0; var3 < var4; var3++) {
         String var2 = var5[var3];
         if (var1.d().equals(var2)) {
            return true;
         }
      }

      return false;
   }

   private void a(L1PcInstance var1, L1NpcInstance var2) {
      if (!(var2 instanceof L1PetInstance) && !(var2 instanceof L1SummonInstance)) {
         int var3 = 0;

         for (L1NpcInstance var4 : var1.ek().values()) {
            var3 += var4.Q();
         }

         int var7 = var1.eC();
         if (var1.x()) {
            var7 += 6;
         } else if (var1.A()) {
            var7 += 12;
         } else if (var1.B()) {
            var7 += 6;
         } else if (var1.C()) {
            var7 += 6;
         } else if (var1.D()) {
            var7 += 6;
         } else if (var1.E()) {
            var7 += 6;
         }

         var7 -= var3;
         L1PcInventory var9 = var1.j();
         if (var7 >= 6 && var9.c() < 180) {
            if (this.b(var1, var2)) {
               L1ItemInstance var6 = ItemTable.a(var1, 40314, 1, 0, false);
               if (var6 != null) {
                  new L1PetInstance(var2, var1, var6.fr());
                  var1.a(new S_ItemName(var6));
               }
            } else {
               var1.a(new S_ServerMessage(324));
            }
         }
      }
   }

   private void a(L1PcInstance var1, L1NpcInstance var2, int var3) {
      if (var2 instanceof L1PetInstance) {
         L1PcInventory var4 = var1.j();
         L1PetInstance var5 = (L1PetInstance)var2;
         L1ItemInstance var6 = var4.e(var5.k());
         if (var6 != null) {
            if ((var5.ev() >= 30 || var3 == 41310) && var1 == var5.M()) {
               L1ItemInstance var7 = ItemTable.a(var1, 40316, 1, 0, false);
               if (var7 != null) {
                  var5.d(var7.fr());
                  var1.a(new S_ItemName(var7));
                  var4.b(var6, 1);
               }
            }
         }
      }
   }

   private boolean b(L1PcInstance var1, L1NpcInstance var2) {
      if (var1.l()) {
         return true;
      }

      boolean var3 = false;
      int var4 = var2.U_().b();
      if (var4 == 45313) {
         if (var2.ew() / 3 > var2.ea() && Random.a(16) == 15) {
            var3 = true;
         }
      } else if (var2.ew() / 3 > var2.ea()) {
         var3 = true;
      }

      return var3;
   }

   @Override
   public String a() {
      return "[C] C_GiveItem";
   }
}
