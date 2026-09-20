package l1r.ap;

import java.util.ArrayList;
import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1Clan;
import l1r.aq.L1Object;
import l1r.aq.L1SpawnWar;
import l1r.aq.L1Teleport;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.as.L1CastleWar;
import l1r.be.S_CastleMaster;
import l1r.be.S_DoActionGFX;
import l1r.be.S_NPCPack;
import l1r.bh.L1Castle;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;

public class L1TowerInstance extends L1NpcInstance {
   private L1Character y;
   private int z;
   private int A;
   private final ArrayList<L1KeeperInstance> B = new ArrayList<>();

   public L1TowerInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_NPCPack(this));
   }

   @Override
   public void a(L1PcInstance var1, int var2) {
      if (this.ea() > 0 && !this.eX()) {
         super.a(var1, var2);
      }
   }

   @Override
   public void b(L1Character var1, int var2) {
      if (this.B.isEmpty()) {
         for (L1Object var3 : L1World.a().b(this, 20)) {
            if (var3 instanceof L1KeeperInstance) {
               this.B.add((L1KeeperInstance)var3);
            }
         }
      }

      for (L1KeeperInstance var8 : this.B) {
         var8.c((L1Character)var1, var2);
      }

      if (this.z == 0) {
         if (this.h()) {
            this.z = 7;
         } else {
            this.z = L1CastleLocation.a(this.fs(), this.ft(), this.fp());
         }
      }

      if (this.z > 0 && L1CastleWar.a().a(this.z)) {
         if (this.z == 7 && !this.h()) {
            int var9 = 0;

            for (L1Object var12 : L1World.a().b()) {
               if (var12 instanceof L1TowerInstance) {
                  L1TowerInstance var6 = (L1TowerInstance)var12;
                  if (var6.h() && var6.eX()) {
                     if (++var9 == 4) {
                        break;
                     }
                  }
               }
            }

            if (var9 < 3) {
               return;
            }
         }

         L1Character var10 = null;
         if (var1 instanceof L1PcInstance) {
            var10 = var1;
         } else if (var1 instanceof L1PetInstance) {
            var10 = ((L1PetInstance)var1).M();
         } else if (var1 instanceof L1SummonInstance) {
            var10 = ((L1SummonInstance)var1).M();
         } else if (var1 instanceof L1AttackerInstance) {
            var10 = var1;
         }

         if (var10 == null) {
            return;
         }

         boolean var13 = false;

         for (L1Clan var14 : ClanTable.a().b().values()) {
            int var7 = var14.m();
            if (var7 == this.z) {
               var13 = true;
               break;
            }
         }

         if (var10 instanceof L1PcInstance) {
            L1PcInstance var15 = (L1PcInstance)var10;
            L1War var19 = L1World.a().c(var15.aG());
            boolean var21 = var19 != null && var19.d() == this.z;
            if (var13 && !var21) {
               return;
            }
         }

         if (this.ea() > 0 && !this.eX()) {
            int var17 = this.ea() - var2;
            if (var17 <= 0 && !this.eX()) {
               this.bx(0);
               this.X(true);
               this.cq(35);
               this.y = var1;
               this.A = 0;
               L1TowerInstance.a var20 = new L1TowerInstance.a(null);
               GeneralThreadPool.a().a(var20);
            }

            if (var17 > 0) {
               this.a(var17);
               if (this.ew() * 1 / 4 > this.ea()) {
                  if (this.A != 3) {
                     this.b(new S_DoActionGFX(this.fr(), 34));
                     this.cq(34);
                     this.A = 3;
                  }
               } else if (this.ew() * 2 / 4 > this.ea()) {
                  if (this.A != 2) {
                     this.b(new S_DoActionGFX(this.fr(), 33));
                     this.cq(33);
                     this.A = 2;
                  }
               } else if (this.ew() * 3 / 4 > this.ea() && this.A != 1) {
                  this.b(new S_DoActionGFX(this.fr(), 32));
                  this.cq(32);
                  this.A = 1;
               }
            }
         } else if (!this.eX()) {
            this.X(true);
            this.cq(35);
            this.y = var1;
            L1TowerInstance.a var16 = new L1TowerInstance.a(null);
            GeneralThreadPool.a().a(var16);
         }
      }
   }

   @Override
   public void a(int var1) {
      int var2 = var1;
      if (var2 >= this.ew()) {
         var2 = this.ew();
      }

      this.bx(var2);
   }

   @Override
   public boolean h() {
      return this.U_().b() == 81190 || this.U_().b() == 81191 || this.U_().b() == 81192 || this.U_().b() == 81193;
   }

   private class a implements Runnable {
      private a() {
      }

      @Override
      public void run() {
         L1TowerInstance.this.bx(0);
         L1TowerInstance.this.X(true);
         L1TowerInstance.this.cq(35);
         L1TowerInstance.this.fq().a(L1TowerInstance.this.fu(), true);
         L1TowerInstance.this.b(new S_DoActionGFX(L1TowerInstance.this.fr(), 35));
         if (!(L1TowerInstance.this.y instanceof L1AttackerInstance)) {
            if (!L1TowerInstance.this.h()) {
               L1SpawnWar.a().b(L1TowerInstance.this.z);
            }
         } else {
            int var1 = L1CastleLocation.a(L1TowerInstance.this.fs(), L1TowerInstance.this.ft(), L1TowerInstance.this.fp());

            for (L1Clan var2 : ClanTable.a().b().values()) {
               if (var1 == var2.m()) {
                  var2.g(0);
                  ClanTable.a().b(var2);
                  break;
               }
            }

            L1Castle var10 = CastleTable.a().a(var1);
            var10.c(0);
            L1World.a().a(new S_CastleMaster(var1, 0));

            for (L1Object var11 : L1World.a().b(L1TowerInstance.this.fp()).values()) {
               if (var11 instanceof L1PcInstance) {
                  L1PcInstance var5 = (L1PcInstance)var11;
                  if (L1CastleLocation.a(var1, var5)) {
                     int[] var6 = L1CastleLocation.e(var1);
                     int var7 = var6[0];
                     int var8 = var6[1];
                     int var9 = var6[2];
                     L1Teleport.a(var5, var7, var8, var9, 5, true);
                  }
               } else if (var11 instanceof L1AttackerInstance) {
                  L1AttackerInstance var14 = (L1AttackerInstance)var11;
                  if (L1CastleLocation.a(var1, var14)) {
                     var14.aa_();
                  }
               }
            }

            String var12 = ((L1AttackerInstance)L1TowerInstance.this.y).ap();
            L1War var13 = L1World.a().c(var12);
            if (var13 != null) {
               var13.a(var12);
            }

            L1CastleWar.a().a(var10);
         }
      }

      // $VF: synthetic method
      a(L1TowerInstance.a var2) {
         this();
      }
   }
}
