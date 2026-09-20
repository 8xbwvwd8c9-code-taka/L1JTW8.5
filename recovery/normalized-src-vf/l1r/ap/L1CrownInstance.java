package l1r.ap;

import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.ao.DoorTable;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Clan;
import l1r.aq.L1Object;
import l1r.aq.L1SpawnWar;
import l1r.aq.L1Teleport;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.be.S_CastleMaster;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Castle;
import l1r.bh.L1Npc;

public class L1CrownInstance extends L1NpcInstance {
   public L1CrownInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void c(L1PcInstance var1) {
      if (var1.aF() != 0) {
         L1Clan var2 = ClanTable.a().a(var1.aF());
         if (var2 != null) {
            if (var1.x()) {
               if (var1.fe() == 0 || var1.fe() == 1) {
                  if (var1.fr() == var2.k()) {
                     if (this.d(var1)) {
                        if (var2.m() != 0) {
                           var1.a(new S_ServerMessage(474));
                        } else {
                           int var3 = L1CastleLocation.a(this.fs(), this.ft(), this.fp());
                           boolean var4 = false;
                           L1Clan var5 = null;

                           for (L1Clan var6 : ClanTable.a().b().values()) {
                              if (var3 == var6.m()) {
                                 var5 = ClanTable.a().c(var6.f());
                                 var4 = true;
                                 break;
                              }
                           }

                           boolean var14 = L1World.a().b(var2.f());
                           if (!var4 || var14) {
                              if (var4 && var5 != null) {
                                 var5.g(0);
                                 ClanTable.a().b(var5);
                              }

                              var2.g(var3);
                              ClanTable.a().b(var2);
                              L1Castle var15 = CastleTable.a().a(var3);
                              var15.c(var2.e());
                              L1World.a().a(new S_CastleMaster(var3, var1.fr()));
                              int[] var8 = new int[3];

                              for (L1PcInstance var9 : L1World.a().c()) {
                                 if (var9.aF() != var1.aF() && L1CastleLocation.a(var3, var9)) {
                                    var8 = L1CastleLocation.e(var3);
                                    int var11 = var8[0];
                                    int var12 = var8[1];
                                    short var13 = (short)var8[2];
                                    L1Teleport.a(var9, var11, var12, var13, 5, true);
                                 }
                              }

                              L1War var17 = L1World.a().c(var2.f());
                              if (var17 != null && var4) {
                                 var17.a(var2.f());
                              }

                              if (!var2.b().isEmpty()) {
                                 S_ServerMessage var18 = new S_ServerMessage(643);

                                 for (L1PcInstance var21 : var2.b()) {
                                    var21.a(var18);
                                 }
                              }

                              this.aa_();

                              for (L1Object var19 : L1World.a().b()) {
                                 if (var19 instanceof L1TowerInstance) {
                                    L1TowerInstance var25 = (L1TowerInstance)var19;
                                    if (L1CastleLocation.a(var3, var25)) {
                                       var25.aa_();
                                    }
                                 }
                              }

                              L1SpawnWar.a().a(var3);
                              L1DoorInstance[] var27;
                              int var26 = (var27 = DoorTable.b().c()).length;

                              for (int var23 = 0; var23 < var26; var23++) {
                                 L1DoorInstance var20 = var27[var23];
                                 if (L1CastleLocation.a(var3, var20)) {
                                    var20.h();
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
   }

   private boolean d(L1PcInstance var1) {
      return this.fs() - 1 <= var1.fs() && var1.fs() <= this.fs() + 1 && this.ft() - 1 <= var1.ft() && var1.ft() <= this.ft() + 1;
   }
}
