package l1r.as;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.DoorTable;
import l1r.ao.MapsTable;
import l1r.ao.SoulTowerTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1FieldObjectInstance;
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
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PacketBox;
import l1r.be.ServerBasePacket;
import l1r.bh.L1DoorGfx;
import l1r.bi.GeneralThreadPool;

public class L1SoulTower {
   private static final Logger a = Logger.getLogger(L1SoulTower.class.getName());
   private static L1SoulTower b;
   private static final int c = 4001;
   private static final int d = 50;
   private static boolean[] e = new boolean[50];

   public static L1SoulTower a() {
      if (b == null) {
         b = new L1SoulTower();
      }

      return b;
   }

   private L1SoulTower() {
      L1Map var1 = L1WorldMap.b().a(4001);

      for (int var2 = 1; var2 < 50; var2++) {
         try {
            L1Map var3 = var1.s();
            var3.a = 4001 + var2;
            MapsTable.a().a(var3);
            L1WorldMap.b().a().put(var3.a, var3);
         } catch (CloneNotSupportedException var4) {
            a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
         }
      }
   }

   public boolean a(L1PcInstance var1) {
      for (int var2 = 0; var2 < e.length; var2++) {
         if (!e[var2]) {
            e[var2] = true;
            GeneralThreadPool.a().b(new L1SoulTower.a(4001 + var2, var1, null));
            return true;
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
            var8 = SpawnTable.a(var4, var1.f(), var1.g(), var1.b(), 5, 5, true);
         } else {
            var8 = SpawnTable.a(var4, var1.f(), var1.g(), var1.b(), 5, 0, true);
         }

         var3.add(var8);
      }

      return var3;
   }

   private class a extends Thread {
      private final L1PcInstance b;
      private final int c;

      private a(int var2, L1PcInstance var3) {
         this.b = var3;
         this.c = var2;
      }

      @Override
      public void run() {
         try {
            long var1 = System.currentTimeMillis();
            L1Teleport.a(this.b, 32869, 32923, this.c, 2, true);
            this.b.a(new S_PacketBox(195, 1800));
            L1SoulTower.this.a(new L1Location(32801, 32812, this.c), 190045, 1);
            L1SoulTower.this.a(new L1Location(32756, 32872, this.c), 190045, 1);
            L1DoorInstance var3 = DoorTable.b().a(0, L1DoorGfx.a(12632), new L1Location(32843, 32878, this.c), 0, 1, false);
            L1SoulTower.this.a(new L1Location(32849, 32923, this.c), 190031, 10);
            L1SoulTower.this.a(new L1Location(32844, 32905, this.c), 190029, 6);
            L1SoulTower.this.a(new L1Location(32844, 32905, this.c), 190030, 6);
            this.a(10000L);
            this.a("\\f=$18344");
            this.a(3000L);
            this.a("\\f=$18327");
            this.a(3000L);
            this.a("\\f=$18328");
            ArrayList var4 = L1SoulTower.this.a(new L1Location(32843, 32894, this.c), 190034, 2);
            this.a(var4);
            this.a("\\f=$18329");
            L1SoulTower.this.a(new L1Location(32843, 32886, this.c), 190034, 2);
            L1SoulTower.this.a(new L1Location(32843, 32886, this.c), 190029, 6);
            L1NpcInstance var5 = L1SoulTower.this.a(new L1Location(32843, 32885, this.c), 190032, 1).get(0);

            while (!var5.eX()) {
               this.a(1000L);
            }

            var3.f();
            this.a("\\f=$18338");
            L1DoorInstance var6 = DoorTable.b().a(0, L1DoorGfx.a(6336), new L1Location(32842, 32848, this.c), 0, 1, false);
            this.a(3000L);
            this.a("\\f=$18347");
            ArrayList var7 = new ArrayList<>();
            var7.addAll(L1SoulTower.this.a(new L1Location(32844, 32862, this.c), 190034, 4));
            var7.addAll(L1SoulTower.this.a(new L1Location(32844, 32862, this.c), 190035, 4));
            var7.addAll(L1SoulTower.this.a(new L1Location(32844, 32862, this.c), 190036, 4));
            L1SoulTower.this.a(new L1Location(32844, 32862, this.c), 190029, 6);
            this.a(var7);
            this.a("\\f=$18330");
            L1SoulTower.this.a(new L1Location(32859, 32858, this.c), 190034, 2);
            L1NpcInstance var8 = L1SoulTower.this.a(new L1Location(32859, 32858, this.c), 190037, 1).get(0);

            while (!var8.eX()) {
               this.a(1000L);
            }

            this.a("\\f=$18333");
            var6.f();
            L1DoorInstance var9 = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(32820, 32812, this.c), 0, 1, false);
            L1DoorInstance var10 = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(32820, 32813, this.c), 0, 1, false);
            L1DoorInstance var11 = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(32820, 32814, this.c), 0, 1, false);
            this.a(5000L);
            this.a("\\f=$18331");
            L1SoulTower.this.a(new L1Location(32846, 32814, this.c), 190035, 2);
            L1SoulTower.this.a(new L1Location(32846, 32814, this.c), 190029, 3);
            L1SoulTower.this.a(new L1Location(32846, 32814, this.c), 190034, 2);
            L1SoulTower.this.a(new L1Location(32864, 32804, this.c), 190035, 2);
            L1SoulTower.this.a(new L1Location(32864, 32804, this.c), 190030, 3);
            L1SoulTower.this.a(new L1Location(32864, 32804, this.c), 190034, 2);
            L1SoulTower.this.a(new L1Location(32850, 32801, this.c), 190035, 2);
            L1SoulTower.this.a(new L1Location(32850, 32801, this.c), 190029, 3);
            L1SoulTower.this.a(new L1Location(32850, 32801, this.c), 190034, 2);
            L1SoulTower.this.a(new L1Location(32831, 32799, this.c), 190035, 2);
            L1SoulTower.this.a(new L1Location(32831, 32799, this.c), 190030, 3);
            L1SoulTower.this.a(new L1Location(32831, 32799, this.c), 190034, 2);
            L1NpcInstance var12 = L1SoulTower.this.a(new L1Location(32833, 32809, this.c), 190034, 1).get(0);

            while (!var12.eX()) {
               this.a(1000L);
            }

            var9.f();
            var10.f();
            var11.f();
            this.a("\\f=$18348");
            L1DoorInstance var13 = DoorTable.b().a(0, L1DoorGfx.a(12711), new L1Location(32790, 32815, this.c), 0, 1, false);
            L1SoulTower.this.a(new L1Location(32800, 32816, this.c), 190036, 3);
            L1SoulTower.this.a(new L1Location(32800, 32816, this.c), 190034, 2);
            L1NpcInstance var14 = L1SoulTower.this.a(new L1Location(32800, 32816, this.c), 190038, 1).get(0);

            while (!var14.eX()) {
               this.a(1000L);
            }

            var13.f();
            this.a("\\f=$18340");
            ArrayList var15 = new ArrayList<>();

            for (int var16 = 32769; var16 <= 32777; var16++) {
               L1DoorInstance var17 = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(var16, 32829, this.c), 0, 1, false);
               var17.c(0);
               var15.add(var17);
            }

            L1DoorInstance var31 = DoorTable.b().a(0, L1DoorGfx.a(12711), new L1Location(32760, 32819, this.c), 0, 1, false);
            this.a(3000L);
            this.a("\\f=$18349");
            L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190036, 4);
            L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190030, 4);
            L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190034, 4);
            L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190035, 4);
            ArrayList var32 = L1SoulTower.this.a(new L1Location(32776, 32818, this.c), 190029, 4);
            this.a(var32);

            for (L1DoorInstance var18 : var15) {
               var18.f();
            }

            ArrayList var33 = new ArrayList<>();

            for (int var34 = 32763; var34 <= 32776; var34++) {
               L1DoorInstance var20 = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(var34, 32843, this.c), 0, 1, false);
               var20.c(0);
               var33.add(var20);
            }

            this.a(5000L);
            this.a("\\f=$18332");
            L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190036, 4);
            L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190030, 4);
            L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190034, 4);
            L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190035, 4);
            L1SoulTower.this.a(new L1Location(32772, 32835, this.c), 190029, 4);
            this.a(15000L);

            for (L1DoorInstance var35 : var33) {
               var35.f();
            }

            this.a("\\f=$18333");
            ArrayList var36 = new ArrayList<>();

            for (int var38 = 32749; var38 <= 32751; var38++) {
               L1DoorInstance var21 = DoorTable.b().a(0, L1DoorGfx.a(12754), new L1Location(var38, 32881, this.c), 0, 1, false);
               var21.c(0);
               var36.add(var21);
            }

            L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190036, 2);
            L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190030, 2);
            L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190034, 2);
            L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190035, 2);
            L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190029, 2);
            L1NpcInstance var39 = L1SoulTower.this.a(new L1Location(32769, 32854, this.c), 190039, 1).get(0);

            while (!var39.eX()) {
               this.a(1000L);
            }

            this.a("\\f=$18341");

            for (L1DoorInstance var40 : var36) {
               var40.f();
            }

            L1DoorInstance var41 = DoorTable.b().a(0, L1DoorGfx.a(12711), new L1Location(32769, 32905, this.c), 0, 1, false);
            this.a(10000L);
            this.a("\\f=$18334");
            L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190036, 6);
            L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190030, 6);
            L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190034, 6);
            L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190035, 6);
            L1SoulTower.this.a(new L1Location(32753, 32898, this.c), 190029, 10);
            this.a(15000L);
            this.a("\\f=$18335");
            L1NpcInstance var42 = L1SoulTower.this.a(new L1Location(32765, 32906, this.c), 190033, 1).get(0);

            while (!var42.eX()) {
               this.a(1000L);
            }

            var41.f();
            this.a("\\f=$18342");
            this.a(3000L);
            this.a("\\f=$18336");
            L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190036, 4);
            L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190030, 4);
            L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190034, 4);
            L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190035, 4);
            L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190029, 4);
            this.a(10000L);
            this.a("\\f=$18337");
            L1NpcInstance var23 = L1SoulTower.this.a(new L1Location(32786, 32906, this.c), 190041, 1).get(0);

            while (!var23.eX()) {
               this.a(1000L);
            }

            int var24 = (int)((System.currentTimeMillis() - var1) / 1000L);
            this.b.a(new S_PacketBox(196, var24));
            SoulTowerTable.a().a(this.b, var24);
            this.a("\\f=$18343");
            this.a(2000L);
            this.a("\\f=$18574");
            this.a(2000L);
            this.a("\\f=$18575");
            this.a(2000L);

            for (int var25 = 0; var25 < 10; var25++) {
               this.a("$" + (18576 + var25));
               this.a(1000L);
            }
         } catch (Exception var29) {
         } finally {
            this.a();
            L1SoulTower.e[this.c - 4001] = false;
            System.out.println("[副本結束]:屍魂塔(" + this.c + ")");
         }
      }

      private void a() {
         for (L1Object var1 : L1World.a().b()) {
            if (var1.fp() == this.c) {
               if (var1 instanceof L1PcInstance) {
                  L1PcInstance var3 = (L1PcInstance)var1;
                  L1Teleport.a(var3, 33703, 32502, 4, 5, true);
               } else if (var1 instanceof L1DoorInstance) {
                  DoorTable.b().a(var1.fu());
               } else if (var1 instanceof L1NpcInstance) {
                  L1NpcInstance var5 = (L1NpcInstance)var1;
                  if (!(var5 instanceof L1FieldObjectInstance)) {
                     var5.aa_();
                  }
               } else if (var1 instanceof L1ItemInstance) {
                  L1ItemInstance var6 = (L1ItemInstance)var1;
                  L1Inventory var4 = L1World.a().a(var6.fs(), var6.ft(), var6.fp());
                  var4.f(var6);
               }
            }
         }
      }

      private void a(long var1) throws InterruptedException {
         Thread.sleep(var1);
         if (this.b.fp() == this.c && this.b.bE() != 0) {
            if (this.b.eX()) {
               this.b.a(new S_NpcChatPacket(this.b, "$18636"));
               Thread.sleep(3000L);
               this.b.a(new S_NpcChatPacket(this.b, "$18637"));
               Thread.sleep(3000L);
            }
         } else {
            throw new InterruptedException();
         }
      }

      private void a(ServerBasePacket var1) {
         for (L1PcInstance var2 : L1World.a().c()) {
            if (var2.fp() == this.c) {
               var2.a(var1);
            }
         }
      }

      private void a(String var1) {
         this.a(new S_PacketBox(84, 2, var1));
      }

      private int a(ArrayList<L1NpcInstance> var1) throws InterruptedException {
         int var2 = -1;

         while (var2++ < 900) {
            boolean var3 = false;

            for (L1NpcInstance var4 : var1) {
               if (!var4.eX()) {
                  var3 = false;
                  break;
               }

               var3 = var4.eX();
            }

            if (var3) {
               return var2;
            }

            this.a(1000L);
         }

         throw new InterruptedException();
      }

      // $VF: synthetic method
      a(int var2, L1PcInstance var3, L1SoulTower.a var4) {
         this(var2, var3);
      }
   }
}
