package l1r.ba;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.au.L1GroundInventory;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.bi.Point;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class ElementalStoneTimer implements Runnable {
   private static final Logger a = Logger.getLogger(ElementalStoneTimer.class.getName());
   private static final int b = 4;
   private static final int c = Config.am;
   private static final int d = 3;
   private static final int e = 300;
   private static final int f = 32911;
   private static final int g = 32210;
   private static final int h = 33141;
   private static final int i = 32500;
   private static final int j = 40515;
   private final ArrayList<L1GroundInventory> k = new ArrayList<>(c);
   private static ElementalStoneTimer l = null;
   private final L1Object m = new L1Object();

   private ElementalStoneTimer() {
   }

   public static ElementalStoneTimer a() {
      if (l == null) {
         l = new ElementalStoneTimer();
      }

      return l;
   }

   private boolean a(L1Location var1) {
      this.m.a(var1.a());
      this.m.cG(var1.f());
      this.m.cH(var1.g());
      return L1World.a().f(this.m).isEmpty();
   }

   private Point b() {
      int var1 = Random.a(230) + 32911;
      int var2 = Random.a(290) + 32210;
      return new Point(var1, var2);
   }

   private void c() {
      for (int var1 = 0; var1 < this.k.size(); var1++) {
         L1GroundInventory var2 = this.k.get(var1);
         if (!var2.f(40515)) {
            this.k.remove(var1);
            var1--;
         }
      }
   }

   private void b(L1Location var1) {
      L1GroundInventory var2 = L1World.a().a(var1);
      L1ItemInstance var3 = ItemTable.a().b(40515);
      var3.a(0);
      var3.e(1);
      var2.d(var3);
      this.k.add(var2);
   }

   @Override
   public void run() {
      try {
         L1Map var1 = L1WorldMap.b().a(4);

         while (true) {
            this.c();

            while (this.k.size() < c) {
               L1Location var2 = new L1Location(this.b(), var1);
               if (this.a(var2)) {
                  this.b(var2);
                  Thread.sleep(3000L);
               }
            }

            Thread.sleep(300000L);
         }
      } catch (Throwable var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }
   }
}
