package l1r.as;

import java.util.TimerTask;
import l1r.ao.SpawnTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_ServerMessage;
import l1r.bi.GeneralThreadPool;

public class L1Dragon {
   private static L1Dragon a;

   public static L1Dragon a() {
      if (a == null) {
         a = new L1Dragon();
      }

      return a;
   }

   public boolean a(int var1, L1PcInstance var2) {
      if (var1 >= 190331 && var1 <= 190333 && var2.bB(4011)) {
         var2.a(new S_ServerMessage(3418));
         return true;
      }

      if (var1 >= 190334 && var1 <= 190336 && var2.bB(4012)) {
         var2.a(new S_ServerMessage(3418));
         return true;
      }

      if (var1 >= 190337 && var1 <= 190339 && var2.bB(4077)) {
         var2.a(new S_ServerMessage(3418));
         return true;
      }

      if (var1 == 190330) {
         L1Teleport.a(var2, 32795, 32665, var2.fp(), 4, true);
      } else if (var1 == 190331) {
         L1Teleport.a(var2, 32670, 32675, 1005, 4, true);
      } else if (var1 == 190332) {
         L1Teleport.a(var2, 32670, 32675, 1029, 4, true);
      } else if (var1 == 190333) {
         L1Teleport.a(var2, 32670, 32675, 1053, 4, true);
      } else if (var1 == 190343) {
         L1Teleport.a(var2, 32988, 32842, var2.fp(), 4, true);
      } else if (var1 == 190334) {
         L1Teleport.a(var2, 32927, 32667, 1011, 4, true);
      } else if (var1 == 190335) {
         L1Teleport.a(var2, 32927, 32667, 1035, 4, true);
      } else if (var1 == 190336) {
         L1Teleport.a(var2, 32927, 32667, 1059, 4, true);
      } else if (var1 == 190344) {
         L1Teleport.a(var2, 32842, 32887, var2.fp(), 4, true);
      } else if (var1 == 190337) {
         L1Teleport.a(var2, 32736, 32850, 1017, 4, true);
      } else if (var1 == 190338) {
         L1Teleport.a(var2, 32736, 32850, 1041, 4, true);
      } else {
         if (var1 != 190339) {
            return false;
         }

         L1Teleport.a(var2, 32736, 32850, 1065, 4, true);
      }

      return true;
   }

   public void a(L1ItemInstance var1, L1PcInstance var2) {
      if (var2.fp() != 4) {
         var2.a(new S_ServerMessage(1892));
      } else {
         int var3 = L1CastleLocation.a(var2);
         if (var3 > 0) {
            var2.a(new S_ServerMessage(3274));
         } else {
            int var4 = var1.a().V();

            for (L1Object var5 : L1World.a().b(4).values()) {
               if (var5 instanceof L1NpcInstance && ((L1NpcInstance)var5).z() == var4) {
                  var2.a(new S_ServerMessage(1537));
                  return;
               }
            }

            if (var1.N() == 640626) {
               SpawnTable.a(97006, 32784, 32691, 1005, 30000L);
            } else if (var1.N() == 640627) {
               SpawnTable.a(97006, 32784, 32691, 1029, 30000L);
            } else if (var1.N() == 640628) {
               SpawnTable.a(97006, 32784, 32691, 1053, 30000L);
            } else if (var1.N() == 640629) {
               SpawnTable.a(97044, 32947, 32843, 1011, 30000L);
            } else if (var1.N() == 640630) {
               SpawnTable.a(97044, 32947, 32843, 1035, 30000L);
            } else if (var1.N() == 640631) {
               SpawnTable.a(97044, 32947, 32843, 1059, 30000L);
            } else if (var1.N() == 640632) {
               SpawnTable.a(97094, 32856, 32867, 1017, 30000L);
            } else if (var1.N() == 640633) {
               SpawnTable.a(97094, 32856, 32867, 1041, 30000L);
            } else if (var1.N() == 640634) {
               SpawnTable.a(97094, 32856, 32867, 1065, 30000L);
            }

            SpawnTable.a(var4, var2, 0, 10800000L);
            var2.j().b(var1, 1);
            L1World.a().a(new S_ServerMessage(2921));
         }
      }
   }

   public void a(int var1) {
      int var2 = -1;
      if (var1 == 190331) {
         var2 = 1005;
      } else if (var1 == 190332) {
         var2 = 1029;
      } else if (var1 == 190333) {
         var2 = 1053;
      } else if (var1 == 190334) {
         var2 = 1011;
      } else if (var1 == 190335) {
         var2 = 1035;
      } else if (var1 == 190336) {
         var2 = 1059;
      } else if (var1 == 190337) {
         var2 = 1017;
      } else if (var1 == 190338) {
         var2 = 1041;
      } else if (var1 == 190339) {
         var2 = 1065;
      }

      if (var2 != -1) {
         for (L1Object var3 : L1World.a().b(var2).values()) {
            if (var3 instanceof L1PcInstance) {
               L1PcInstance var5 = (L1PcInstance)var3;
               L1Teleport.a(var5, 33703, 32502, 4, 5, true);
            } else if (var3 instanceof L1MonsterInstance) {
               L1MonsterInstance var7 = (L1MonsterInstance)var3;
               var7.aa_();
            } else if (var3 instanceof L1ItemInstance) {
               L1ItemInstance var8 = (L1ItemInstance)var3;
               L1Inventory var6 = L1World.a().a(var8.fs(), var8.ft(), var8.fp());
               var6.f(var8);
            }
         }
      }
   }

   public void a(L1NpcInstance var1) {
      GeneralThreadPool.a().a(new L1Dragon.a(var1, null), 180000L);
   }

   private class a extends TimerTask {
      private final L1NpcInstance b;

      private a(L1NpcInstance var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         int var1 = -1;
         if (this.b.fp() == 1005) {
            var1 = 190331;
         } else if (this.b.fp() == 1029) {
            var1 = 190332;
         } else if (this.b.fp() == 1053) {
            var1 = 190333;
         } else if (this.b.fp() == 1011) {
            var1 = 190334;
         } else if (this.b.fp() == 1035) {
            var1 = 190335;
         } else if (this.b.fp() == 1059) {
            var1 = 190336;
         } else if (this.b.fp() == 1017) {
            var1 = 190337;
         } else if (this.b.fp() == 1041) {
            var1 = 190338;
         } else if (this.b.fp() == 1065) {
            var1 = 190339;
         }

         for (L1Object var2 : L1World.a().b(4).values()) {
            if (var2 instanceof L1NpcInstance && ((L1NpcInstance)var2).z() == var1) {
               ((L1NpcInstance)var2).aa_();
            }
         }
      }

      // $VF: synthetic method
      a(L1NpcInstance var2, L1Dragon.a var3) {
         this(var2);
      }
   }
}
