package l1r.as;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.DoorTable;
import l1r.ao.MapsTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.bh.L1DoorGfx;
import l1r.bi.GeneralThreadPool;

public class L1IceQueen {
   private static final Logger c = Logger.getLogger(L1IceQueen.class.getName());
   public static final int a = 1;
   public static final int b = 2;
   private static L1IceQueen d;
   private final int e = 50;
   private final int f = 2101;
   private final int g = 2151;
   private final boolean[] h = new boolean[50];
   private final boolean[] i = new boolean[50];

   public static L1IceQueen a() {
      if (d == null) {
         d = new L1IceQueen();
      }

      return d;
   }

   public L1IceQueen() {
      L1Map var1 = L1WorldMap.b().a(2101);
      L1Map var2 = L1WorldMap.b().a(2151);

      try {
         for (int var3 = 1; var3 < 50; var3++) {
            L1Map var4 = var1.s();
            var4.a = 2101 + var3;
            MapsTable.a().a(var4);
            L1WorldMap.b().a().put(var4.a, var4);
            L1Map var5 = var2.s();
            var5.a = 2151 + var3;
            MapsTable.a().a(var5);
            L1WorldMap.b().a().put(var5.a, var5);
            L1DoorInstance[] var9;
            int var8 = (var9 = DoorTable.b().c()).length;

            for (int var7 = 0; var7 < var8; var7++) {
               L1DoorInstance var6 = var9[var7];
               if (var6.fp() == 2101) {
                  L1Location var10 = new L1Location(var6.fs(), var6.ft(), var4.a);
                  L1DoorGfx var11 = L1DoorGfx.a(var6.fe());
                  L1DoorInstance var12 = DoorTable.b().a(0, var11, var10, 0, 0, false);
                  if (var6.i() == 5016) {
                     var12.f(5016);
                  }
               } else if (var6.fp() == 2151) {
                  L1Location var14 = new L1Location(var6.fs(), var6.ft(), var5.a);
                  L1DoorGfx var15 = L1DoorGfx.a(var6.fe());
                  L1DoorInstance var16 = DoorTable.b().a(0, var15, var14, 0, 0, false);
                  if (var6.i() == 5021) {
                     var16.f(5021);
                  }
               }
            }
         }
      } catch (CloneNotSupportedException var13) {
         c.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      }
   }

   public boolean a(L1PcInstance var1, int var2) {
      if (var2 == 1) {
         for (int var3 = 0; var3 < this.h.length; var3++) {
            if (!this.h[var3]) {
               this.h[var3] = true;
               L1Teleport.a(var1, 32728, 32819, 2101 + var3, 0, true);
               GeneralThreadPool.a().a(new L1IceQueen.a(var2, var3, null), 30000L);
               return true;
            }
         }
      } else {
         for (int var4 = 0; var4 < this.i.length; var4++) {
            if (!this.i[var4]) {
               this.i[var4] = true;
               L1Teleport.a(var1, 32728, 32819, 2151 + var4, 0, true);
               GeneralThreadPool.a().a(new L1IceQueen.a(var2, var4, null), 30000L);
               return true;
            }
         }
      }

      return false;
   }

   private ArrayList<L1NpcInstance> a(L1Location var1, int var2, int var3) {
      int[] var4 = new int[var3];

      for (int var5 = 0; var5 < var3; var5++) {
         var4[var5] = var2;
      }

      return this.a(var1, var4);
   }

   private ArrayList<L1NpcInstance> a(L1Location var1, int[] var2) {
      ArrayList var3 = new ArrayList<>();
      int[] var7 = var2;
      int var6 = var2.length;

      for (int var5 = 0; var5 < var6; var5++) {
         int var4 = var7[var5];
         L1NpcInstance var8;
         if (var2.length > 1) {
            var8 = SpawnTable.a(var4, var1.f(), var1.g(), var1.b(), 5, 15, true);
         } else {
            var8 = SpawnTable.a(var4, var1.f(), var1.g(), var1.b(), 5, 0, true);
         }

         var3.add(var8);
      }

      return var3;
   }

   private class a extends TimerTask {
      private final int b;
      private final int c;
      private int d = 0;
      private final int e;

      private a(int var2, int var3) {
         this.b = var2;
         this.c = var3;
         this.e = (this.b == 1 ? 2101 : 2151) + this.c;
         L1IceQueen.this.a(new L1Location(32734, 32802, this.e), 81404, 1);
         L1IceQueen.this.a(new L1Location(32860, 32920, this.e), 190352, 1);
         int[] var4 = new int[]{
            190351,
            190351,
            190351,
            190351,
            190351,
            190351,
            97080,
            97080,
            97080,
            97080,
            97079,
            97079,
            97079,
            97079,
            97088,
            97088,
            97088,
            97088,
            97088,
            97088,
            97088,
            97088
         };
         L1IceQueen.this.a(new L1Location(32765, 32818, this.e), var4);
         int[] var5 = new int[]{
            190351,
            190351,
            190351,
            190351,
            190351,
            190351,
            97080,
            97080,
            97080,
            97080,
            97076,
            97076,
            97076,
            97076,
            97087,
            97087,
            97087,
            97087,
            97088,
            97088,
            97088,
            97088
         };
         L1IceQueen.this.a(new L1Location(32836, 32805, this.e), var5);
         int[] var6 = new int[]{
            190351,
            190351,
            190351,
            190351,
            190351,
            190351,
            97080,
            97080,
            97080,
            97080,
            97079,
            97079,
            97079,
            97079,
            97087,
            97087,
            97087,
            97087,
            97087,
            97087,
            97087,
            97087
         };
         L1IceQueen.this.a(new L1Location(32848, 32852, this.e), var6);
         int[] var7 = new int[]{
            190351,
            190351,
            190351,
            190351,
            190351,
            190351,
            97079,
            97079,
            97079,
            97079,
            97079,
            97079,
            97079,
            97079,
            97087,
            97087,
            97087,
            97087,
            97088,
            97088,
            97088,
            97088
         };
         L1IceQueen.this.a(new L1Location(32765, 32895, this.e), var7);
         int[] var8 = new int[]{
            190351, 190351, 190351, 190351, 190351, 190351, 190351, 190351, 190351, 97087, 97087, 97087, 97087, 97087, 97088, 97088, 97088, 97088, 97088
         };
         L1IceQueen.this.a(new L1Location(32823, 32921, this.e), var8);
         if (this.b == 1) {
            L1IceQueen.this.a(new L1Location(32823, 32921, this.e), 46142, 1);
         } else {
            L1IceQueen.this.a(new L1Location(32823, 32921, this.e), 46141, 1);
         }
      }

      @Override
      public void run() {
         try {
            if (this.b == 1 && !L1IceQueen.this.h[this.c]) {
               return;
            }

            if (this.b == 2 && !L1IceQueen.this.i[this.c]) {
               return;
            }

            if (this.d > 60) {
               throw new InterruptedException();
            }

            for (L1Object var1 : L1World.a().b(this.e).values()) {
               if (var1 instanceof L1PcInstance) {
                  this.d++;
                  this.a(30L);
                  return;
               }
            }
         } catch (Exception var3) {
            L1IceQueen.c.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         }

         this.a();
         if (this.b == 1) {
            L1IceQueen.this.h[this.c] = false;
         } else {
            L1IceQueen.this.i[this.c] = false;
         }

         System.out.println("[副本結束]:冰之女王(" + this.e + ")");
      }

      private void a() {
         for (L1Object var1 : L1World.a().b(this.e).values()) {
            if (var1 instanceof L1PcInstance) {
               L1PcInstance var3 = (L1PcInstance)var1;
               L1Teleport.a(var3, 34062, 32311, 4, 5, true);
            } else if (var1 instanceof L1DoorInstance) {
               ((L1DoorInstance)var1).g();
            } else if (var1 instanceof L1NpcInstance) {
               L1NpcInstance var5 = (L1NpcInstance)var1;
               var5.aa_();
            } else if (var1 instanceof L1ItemInstance) {
               L1ItemInstance var6 = (L1ItemInstance)var1;
               L1Inventory var4 = L1World.a().a(var6.fs(), var6.ft(), var6.fp());
               var4.f(var6);
            }
         }
      }

      private void a(long var1) {
         GeneralThreadPool.a().a(this, var1 * 1000L);
      }

      // $VF: synthetic method
      a(int var2, int var3, L1IceQueen.a var4) {
         this(var2, var3);
      }
   }
}
