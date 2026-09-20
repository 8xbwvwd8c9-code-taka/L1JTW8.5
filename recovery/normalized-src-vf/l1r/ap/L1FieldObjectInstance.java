package l1r.ap;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.am.ListSprReader__obf_c;
import l1r.aq.L1Character;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_AttackPacket;
import l1r.be.S_DoActionGFX;
import l1r.be.S_NPCPack;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1FieldObjectInstance extends L1NpcInstance {
   private static final Logger y = Logger.getLogger(L1FieldObjectInstance.class.getName());
   private L1FieldObjectInstance.L1R_a z = null;
   private int A = 0;

   public L1FieldObjectInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void c(L1PcInstance var1) {
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_NPCPack(this));
      if (this.z == null && this.f() != -1) {
         this.z = new L1FieldObjectInstance.L1R_a(this, null);
         this.z.a();
      }
   }

   public int f() {
      return this.A;
   }

   public void b(int var1) {
      this.A = var1;
   }

   private class L1R_a implements Runnable {
      private final L1FieldObjectInstance b;

      private L1R_a(L1FieldObjectInstance var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         int var1 = 1000;
         int var2 = 1;
         int var3 = 0;
         int var4 = 0;

         for (int var5 = 0; var5 < 5; var5++) {
            if (L1FieldObjectInstance.this.ah()) {
               return;
            }

            switch (this.b.f()) {
               case -10:
                  if (var3 == 0) {
                     var3 = Random.a(15) + 1;
                     var4 = Random.a(18);
                     if (L1FieldObjectInstance.this.X() != 0 && L1FieldObjectInstance.this.Y() != 0 && var4 < 8 && Random.a(3) == 0) {
                        var4 = L1FieldObjectInstance.this.a(L1FieldObjectInstance.this.X(), L1FieldObjectInstance.this.Y());
                     }
                  } else {
                     var3--;
                  }

                  int var8 = L1FieldObjectInstance.this.a(
                     L1FieldObjectInstance.this.fs(), L1FieldObjectInstance.this.ft(), L1FieldObjectInstance.this.fp(), var4
                  );
                  if (var8 != -1) {
                     L1FieldObjectInstance.this.g(var8);
                  }

                  var2 = 0;

                  for (L1PcInstance var9 : L1World.a().c(this.b, 2)) {
                     if (Random.a(100) < 25) {
                        int[][] var11 = new int[][]{{32718, 33128}, {32815, 33124}, {32810, 33206}, {32714, 33219}};
                        int[] var12 = var11[Random.a(var11.length)];
                        L1Teleport.a(var9, var12[0], var12[1], 4, 3, true);
                     } else {
                        L1Teleport.a(var9, 50);
                     }
                  }
                  break;
               case -2:
                  this.b.b(new S_DoActionGFX(this.b.fr(), 3));
                  break;
               case 0:
                  if (var3 == 0) {
                     var3 = Random.a(5) + 1;
                     var4 = Random.a(18);
                     if (L1FieldObjectInstance.this.X() != 0 && L1FieldObjectInstance.this.Y() != 0 && var4 < 8 && Random.a(3) == 0) {
                        var4 = L1FieldObjectInstance.this.a(L1FieldObjectInstance.this.X(), L1FieldObjectInstance.this.Y());
                     }
                  } else {
                     var3--;
                  }

                  int var7 = L1FieldObjectInstance.this.a(
                     L1FieldObjectInstance.this.fs(), L1FieldObjectInstance.this.ft(), L1FieldObjectInstance.this.fp(), var4
                  );
                  if (var7 != -1) {
                     L1FieldObjectInstance.this.g(var7);
                  }

                  var2 = 0;
                  break;
               case 1:
                  this.b.b(new S_DoActionGFX(this.b.fr(), var2));
                  break;
               case 21:
                  L1Character var6 = new L1Character();
                  var6.cG(this.b.fs());
                  var6.cH(this.b.ft() - 8);
                  this.b.b(new S_AttackPacket(this.b, var6, var2, 2349, 0, 0, 0));
                  break;
               default:
                  this.b.b(new S_DoActionGFX(this.b.fr(), this.b.f()));
                  var2 = this.b.f();
            }

            try {
               int var14 = ListSprReader__obf_c.a().a(this.b.fe(), var2);
               Thread.sleep(var14 > 0 ? var14 : 1000);
            } catch (InterruptedException var13) {
               L1FieldObjectInstance.y.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
            }
         }

         if (!L1World.a().f(this.b).isEmpty()) {
            this.a();
         } else {
            L1FieldObjectInstance.this.z = null;
         }
      }

      private void a() {
         GeneralThreadPool.a().a(this);
      }

      // $VF: synthetic method
      L1R_a(L1FieldObjectInstance var2, L1FieldObjectInstance.L1R_a var3) {
         this(var2);
      }
   }
}
