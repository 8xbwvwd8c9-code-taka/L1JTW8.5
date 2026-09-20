package l1r.bd;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.SpawnTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.ax.L1Map;
import l1r.bi.Point;

public class L1MonsterTrap extends L1Trap__obf_i {
   private static final Logger a = Logger.getLogger(L1MonsterTrap.class.getName());
   private final int b;
   private final int c;

   public L1MonsterTrap(TrapStorage var1) {
      super(var1);
      this.b = var1.b("monsterNpcId");
      this.c = var1.b("monsterCount");
   }

   private void a(List<Point> var1, L1Map var2, Point var3) {
      if (var2.c(var3.f(), var3.g())) {
         var1.add(var3);
      }
   }

   private List<Point> a(L1Location var1, int var2) {
      ArrayList var3 = new ArrayList<>();
      L1Map var4 = var1.a();
      int var5 = var1.f();
      int var6 = var1.g();

      for (int var7 = 0; var7 < var2; var7++) {
         this.a(var3, var4, new Point(var2 - var7 + var5, var7 + var6));
         this.a(var3, var4, new Point(-(var2 - var7) + var5, -var7 + var6));
         this.a(var3, var4, new Point(-var7 + var5, var2 - var7 + var6));
         this.a(var3, var4, new Point(var7 + var5, -(var2 - var7) + var6));
      }

      return var3;
   }

   @Override
   public void a(L1PcInstance var1, L1Object var2) {
      this.a(var2);
      List var3 = this.a(var2.fu(), 5);
      if (!var3.isEmpty()) {
         try {
            int var4 = 0;

            while (true) {
               for (Point var5 : var3) {
                  SpawnTable.a(this.b, var5.f(), var5.g(), var2.fp());
                  if (this.c <= ++var4) {
                     return;
                  }
               }
            }
         } catch (Exception var7) {
            a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
         }
      }
   }
}
