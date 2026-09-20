package l1r.as;

import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ItemTable;
import l1r.ao.MapsTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.be.S_Html;
import l1r.be.S_PacketBox;
import l1r.bi.GeneralThreadPool;

public class L1SoulStone {
   private static final Logger a = Logger.getLogger(L1SoulStone.class.getName());
   private static L1SoulStone b;
   private static final int c = 1400;
   private static final int d = 99;
   private static final int e = 14400000;
   private final ConcurrentHashMap<Integer, L1SoulStone.L1R_a> f = new ConcurrentHashMap<>();

   public static L1SoulStone a() {
      if (b == null) {
         b = new L1SoulStone();
      }

      return b;
   }

   private L1SoulStone() {
      L1Map var1 = L1WorldMap.b().a(1400);

      for (int var2 = 1; var2 < 99; var2++) {
         try {
            L1Map var3 = var1.s();
            var3.a = 1400 + var2;
            MapsTable.a().a(var3);
            L1WorldMap.b().a().put(var3.a, var3);
         } catch (CloneNotSupportedException var4) {
            a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
         }
      }
   }

   public int b() {
      for (int var1 = 1400; var1 < 1499; var1++) {
         if (!this.f.containsKey(var1)) {
            return var1;
         }
      }

      return -1;
   }

   public void a(L1PcInstance var1, L1NpcInstance var2) {
      boolean var3 = false;

      for (L1ItemInstance var4 : var1.j().d()) {
         if (var4.N() == 640615) {
            if (var4.bb() != null && System.currentTimeMillis() <= var4.bb().getTime()) {
               var3 = true;
            } else {
               var1.j().f(var4);
            }
         }
      }

      if (var3) {
         var1.a(new S_Html(var2.fr(), "bosskey6"));
      } else {
         var1.a(new S_Html(var2.fr(), "bosskey4"));
      }
   }

   public void b(L1PcInstance var1, L1NpcInstance var2) {
      L1ItemInstance var3 = var1.j().b(640615);
      if (var3 != null && var3.bb() != null) {
         if (System.currentTimeMillis() > var3.bb().getTime()) {
            var1.a(new S_Html(var2.fr(), "bosskey2"));
         } else {
            int var4 = var3.M();
            if (var4 == 0) {
               var1.a(new S_Html(var2.fr(), "bosskey2"));
            } else {
               L1Teleport.a(var1, 32901, 32814, var4, 5, true);
            }
         }
      } else {
         var1.a(new S_Html(var2.fr(), "bosskey2"));
      }
   }

   public void a(L1PcInstance var1, int var2, L1NpcInstance var3) {
      if (var1.j().f(640615)) {
         var1.a(new S_Html(var3.fr(), "bosskey6"));
      } else if (!var1.j().b(40308, 200 * var2)) {
         var1.a(new S_Html(var3.fr(), "bosskey5"));
      } else {
         int var4 = this.b();
         if (var4 < 0) {
            var1.a(new S_Html(var3.fr(), "bosskey3"));
         } else {
            SpawnTable.a(190328, 32902, 32818, var4);
            L1SoulStone.L1R_a var5 = new L1SoulStone.L1R_a(var4, null);
            var5.a();
            this.f.put(var4, var5);

            for (int var6 = 0; var6 < var2; var6++) {
               L1ItemInstance var7 = ItemTable.a().b(640615);
               var7.b(new Timestamp(System.currentTimeMillis() + 14400000L));
               var7.j(var4);
               if (var1.j().a(var7, var2) == 0) {
                  var1.j().d(var7);
               } else {
                  L1World.a().a(var1.fs(), var1.ft(), var1.fp()).d(var7);
               }
            }

            var1.a(new S_Html(var3.fr(), "bosskey7"));
         }
      }
   }

   public void a(L1PcInstance var1, String var2, L1NpcInstance var3) {
      int var4 = 0;
      int var5 = 0;
      String var6 = "";
      if (var2.equals("A")) {
         var4 = 640520;
         var5 = 190839;
         var6 = "$8473";
      } else if (var2.equals("B")) {
         var4 = 640521;
         var5 = 190833;
         var6 = "$8474";
      } else if (var2.equals("C")) {
         var4 = 640522;
         var5 = 45649;
         var6 = "$8475";
      } else if (var2.equals("D")) {
         var4 = 640523;
         var5 = 45685;
         var6 = "$8476";
      } else if (var2.equals("d")) {
         var4 = 640616;
         var5 = 45600;
         var6 = "$18977";
      } else if (var2.equals("E")) {
         var4 = 640524;
         var5 = 97374;
         var6 = "$9267";
      } else if (var2.equals("F")) {
         var4 = 640252;
         var5 = 97373;
         var6 = "$9268";
      } else if (var2.equals("G")) {
         var4 = 640526;
         var5 = 97376;
         var6 = "$9269";
      } else if (var2.equals("H")) {
         var4 = 640527;
         var5 = 97371;
         var6 = "$9270";
      } else if (var2.equals("I")) {
         var4 = 640528;
         var5 = 97375;
         var6 = "$9271";
      } else if (var2.equals("J")) {
         var4 = 640529;
         var5 = 97377;
         var6 = "$9272";
      } else if (var2.equals("K")) {
         var4 = 640530;
         var5 = 97372;
         var6 = "$9273";
      } else if (var2.equals("L")) {
         var4 = 640531;
         var5 = 97370;
         var6 = "$9274";
      } else if (var2.equals("M")) {
         var4 = 640532;
         var5 = 97367;
         var6 = "$9275";
      } else if (var2.equals("N")) {
         var4 = 640533;
         var5 = 97358;
         var6 = "$9276";
      } else if (var2.equals("O")) {
         var4 = 640534;
         var5 = 97359;
         var6 = "$9277";
      } else if (var2.equals("P")) {
         var4 = 640535;
         var5 = 97360;
         var6 = "$9278";
      } else if (var2.equals("Q")) {
         var4 = 640332;
         var5 = 190800;
         var6 = "$15707";
      } else if (var2.equals("S")) {
         var4 = 640333;
         var5 = 45601;
         var6 = "$15708";
      } else {
         var2.equals("e");
      }

      if (var4 * var5 != 0) {
         if (var1.j().b(var4, 1)) {
            SpawnTable.a(var5, 32878, 32816, var3.fp());
            var1.a(new S_Html(var3.fr(), ""));

            for (L1Object var7 : L1World.a().b(var3.fp()).values()) {
               if (var7 instanceof L1PcInstance) {
                  L1PcInstance var9 = (L1PcInstance)var7;
                  var9.a(new S_PacketBox(84, 2, var6));
               }
            }
         } else {
            var1.a(new S_Html(var3.fr(), "bosskey10"));
         }
      }
   }

   private class L1R_a extends TimerTask {
      private int b = 0;

      private L1R_a(int var2) {
         this.b = var2;
      }

      private void a() {
         GeneralThreadPool.a().a(this, 14400000L);
      }

      @Override
      public void run() {
         try {
            for (L1Object var1 : L1World.a().b()) {
               if (var1.fp() == this.b) {
                  if (var1 instanceof L1PcInstance) {
                     L1PcInstance var3 = (L1PcInstance)var1;
                     L1Teleport.a(var3, 33486, 32765, 4, 5, true);
                  } else if (var1 instanceof L1NpcInstance) {
                     L1NpcInstance var6 = (L1NpcInstance)var1;
                     var6.aa_();
                  } else if (var1 instanceof L1ItemInstance) {
                     L1ItemInstance var7 = (L1ItemInstance)var1;
                     L1Inventory var4 = L1World.a().a(var7.fs(), var7.ft(), var7.fp());
                     var4.f(var7);
                  }
               }
            }

            if (L1SoulStone.this.f.containsKey(this.b)) {
               L1SoulStone.this.f.remove(this.b);
               System.out.println("room=" + this.b + " is released");
            }
         } catch (Exception var5) {
            L1SoulStone.a.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
         }
      }

      // $VF: synthetic method
      L1R_a(int var2, L1SoulStone.L1R_a var3) {
         this(var2);
      }
   }
}
