package l1r.as;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.DoorTable;
import l1r.ao.MapsTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Dungeon;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.be.ServerBasePacket;
import l1r.bh.L1DoorGfx;
import l1r.bi.GeneralThreadPool;

public class L1ValakasLair {
   private static final Logger a = Logger.getLogger(L1ValakasLair.class.getName());
   private static L1ValakasLair b;
   private static final int c = 99;
   private static boolean[] d = new boolean[99];

   public static L1ValakasLair a() {
      if (b == null) {
         b = new L1ValakasLair();
      }

      return b;
   }

   private L1ValakasLair() {
      L1Map var1 = L1WorldMap.b().a(2600);
      L1Map var2 = L1WorldMap.b().a(2699);

      for (int var3 = 1; var3 < 99; var3++) {
         try {
            L1Map var4 = var1.s();
            var4.a = 2600 + var3;
            MapsTable.a().a(var4);
            L1WorldMap.b().a().put(var4.a, var4);
            L1Map var5 = var2.s();
            var5.a = 2699 + var3;
            MapsTable.a().a(var5);
            L1WorldMap.b().a().put(var5.a, var5);
            L1Location var6 = new L1Location(32753, 32985, var4.a);
            L1Location var7 = new L1Location(32753, 32986, var4.a);
            L1Location var8 = new L1Location(32830, 32758, var4.a);
            L1Dungeon.a().a(var6, var8);
            L1Dungeon.a().a(var7, var8);
         } catch (CloneNotSupportedException var9) {
            a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         }
      }
   }

   public boolean a(L1PcInstance var1) {
      int var2 = 2699;

      for (int var3 = 0; var3 < d.length; var3++) {
         if (!d[var3]) {
            d[var3] = true;
            GeneralThreadPool.a().b(new L1ValakasLair.a(2699 + var3, var1, null));
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
            var8 = SpawnTable.a(var4, var1.f(), var1.g(), var1.b(), 5, 10, false);
         } else {
            var8 = SpawnTable.a(var4, var1.f(), var1.g(), var1.b(), 5, 0, false);
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
            L1NpcInstance var1 = L1ValakasLair.this.a(new L1Location(32624, 33057, this.c), 46164, 1).get(0);
            L1NpcInstance var2 = L1ValakasLair.this.a(new L1Location(32624, 33057, this.c - 99), 46165, 1).get(0);
            L1Teleport.a(this.b, 32608, 33055, this.c, 5, true);
            this.b.a(new S_PacketBox(153, 3600));

            while (this.b.fu().c(var1.fu()) > 5 && this.b.fp() == this.c && this.b.bE() == 1) {
               this.a(1000L);
            }

            int[] var3 = new int[]{18861, 18862, 18863, 18864, 18865, 18866, 18867, 18868};

            for (int var4 = 0; var4 < var3.length && this.b.fp() != this.c - 99; var4++) {
               if (var4 != 1 && var4 != 3 && var4 != 5) {
                  var1.b(new S_NpcChatPacket(var1, "$" + var3[var4]));
               } else {
                  var1.b(new S_NpcChatPacket(this.b, "$" + var3[var4]));
               }

               this.a(2000L);
            }

            this.a(120);
            L1ValakasLair.this.a(new L1Location(32613, 33055, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32609, 33045, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32616, 33035, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32623, 33020, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32628, 33007, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32628, 32998, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32666, 33000, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32669, 33014, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32673, 33030, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32681, 33040, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32691, 33041, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32690, 33054, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32706, 33052, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32718, 33042, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32729, 33034, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32735, 33018, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32741, 33018, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32747, 32990, this.c - 99), 46166, 1);
            L1ValakasLair.this.a(new L1Location(32747, 32983, this.c - 99), 46166, 1);
            int[] var20 = new int[]{18644, 18645, 18646, 18647};

            for (int var5 = 0; var5 < var20.length; var5++) {
               var2.b(new S_NpcChatPacket(var2, "$" + var20[var5]));
               this.a(2000L);
            }

            L1DoorInstance var21 = DoorTable.b().a(0, L1DoorGfx.a(8307), new L1Location(32654, 33002, this.c - 99), 0, 1, false);
            L1NpcInstance var6 = L1ValakasLair.this.a(new L1Location(32640, 32997, this.c - 99), 46174, 1).get(0);
            var6.g(32654, 33000);
            L1ValakasLair.this.a(new L1Location(32614, 33035, this.c - 99), 46167, 10);
            L1ValakasLair.this.a(new L1Location(32643, 32997, this.c - 99), 46167, 10);
            L1ValakasLair.this.a(new L1Location(32606, 33052, this.c - 99), 46168, 10);
            L1ValakasLair.this.a(new L1Location(32643, 32997, this.c - 99), 46168, 10);
            L1ValakasLair.this.a(new L1Location(32612, 33006, this.c - 99), 46169, 10);
            L1ValakasLair.this.a(new L1Location(32643, 32997, this.c - 99), 46169, 10);

            while (!var6.eX() || var6.ag()) {
               this.a(1000L);
            }

            var21.f();
            L1DoorInstance var7 = DoorTable.b().a(0, L1DoorGfx.a(8307), new L1Location(32694, 33054, this.c - 99), 0, 1, false);
            L1NpcInstance var8 = L1ValakasLair.this.a(new L1Location(32684, 33044, this.c - 99), 46175, 1).get(0);
            var8.g(32694, 33052);
            L1ValakasLair.this.a(new L1Location(32669, 33024, this.c - 99), 46167, 15);
            L1ValakasLair.this.a(new L1Location(32683, 33056, this.c - 99), 46167, 15);
            L1ValakasLair.this.a(new L1Location(32677, 33000, this.c - 99), 46170, 15);
            L1ValakasLair.this.a(new L1Location(32688, 32986, this.c - 99), 46171, 10);
            L1ValakasLair.this.a(new L1Location(32690, 33029, this.c - 99), 46171, 10);

            while (!var8.eX() || var8.ag()) {
               this.a(1000L);
            }

            var7.f();
            L1DoorInstance var9 = DoorTable.b().a(0, L1DoorGfx.a(8305), new L1Location(32736, 33008, this.c - 99), 0, 1, false);
            L1NpcInstance var10 = L1ValakasLair.this.a(new L1Location(32735, 33021, this.c - 99), 46176, 1).get(0);
            var10.g(32738, 33009);
            L1ValakasLair.this.a(new L1Location(32725, 33018, this.c - 99), 46167, 20);
            L1ValakasLair.this.a(new L1Location(32716, 33047, this.c - 99), 46172, 15);
            L1ValakasLair.this.a(new L1Location(32735, 33037, this.c - 99), 46173, 15);
            L1ValakasLair.this.a(new L1Location(32743, 33018, this.c - 99), 46168, 10);

            while (!var10.eX() || var10.ag()) {
               this.a(1000L);
            }

            var9.f();
            int[] var11 = new int[]{46177, 46177, 46178, 46178, 46179};
            L1NpcInstance var12 = L1ValakasLair.this.a(new L1Location(32834, 32770, this.c - 99), var11[4], 1).get(0);
            if (var12.z() == 46179) {
               while (this.b.fu().c(var12.fu()) > 5) {
                  this.a(1000L);
               }

               var12.U(true);
               int[] var13 = new int[]{18869, 18870, 18871, 18872, 18873, 18874};

               for (int var14 = 0; var14 < var13.length; var14++) {
                  if (var14 != 1 && var14 != 3) {
                     var12.b(new S_NpcChatPacket(var12, "$" + var13[var14]));
                  } else {
                     var12.b(new S_NpcChatPacket(this.b, "$" + var13[var14]));
                  }

                  this.a(2000L);
               }

               var12.U(false);
            }

            while (!var12.eX()) {
               this.a(1000L);
            }

            this.b();
         } catch (Exception var18) {
         } finally {
            this.a();
            L1ValakasLair.d[this.c - 2699] = false;
            System.out.println("[副本結束]:火龍巢穴(" + this.c + ")");
         }
      }

      private void a() {
         for (int var1 = 0; var1 < 2; var1++) {
            for (L1Object var2 : L1World.a().b()) {
               if (var2.fp() == this.c - 99 * var1) {
                  if (var2 instanceof L1PcInstance) {
                     L1PcInstance var4 = (L1PcInstance)var2;
                     var4.j().a(310);
                     L1Teleport.a(var4, 33703, 32502, 4, 5, true);
                  } else if (var2 instanceof L1DoorInstance) {
                     DoorTable.b().a(var2.fu());
                  } else if (var2 instanceof L1NpcInstance) {
                     L1NpcInstance var6 = (L1NpcInstance)var2;
                     var6.aa_();
                  } else if (var2 instanceof L1ItemInstance) {
                     L1ItemInstance var7 = (L1ItemInstance)var2;
                     L1Inventory var5 = L1World.a().a(var7.fs(), var7.ft(), var7.fp());
                     var5.f(var7);
                  }
               }
            }
         }
      }

      private void a(int var1) throws InterruptedException {
         int var2 = -1;

         while (var2++ < var1) {
            if (this.b.fp() == this.c - 99) {
               return;
            }

            this.a(1000L);
         }

         throw new InterruptedException();
      }

      private void a(long var1) throws InterruptedException {
         Thread.sleep(var1);
         if ((this.b.fp() == this.c || this.b.fp() == this.c - 99) && this.b.bE() != 0) {
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
            if (var2.fp() == this.c || var2.fp() == this.c - 99) {
               var2.a(var1);
            }
         }
      }

      private void b() throws InterruptedException {
         this.a(new S_ServerMessage(1476));
         this.a(10000L);
         this.a(new S_ServerMessage(1477));
         this.a(10000L);
         this.a(new S_ServerMessage(1478));
         this.a(5000L);
         this.a(new S_ServerMessage(1480));
         this.a(1000L);
         this.a(new S_ServerMessage(1481));
         this.a(1000L);
         this.a(new S_ServerMessage(1482));
         this.a(1000L);
         this.a(new S_ServerMessage(1483));
         this.a(1000L);
         this.a(new S_ServerMessage(1484));
         this.a(1000L);
      }

      // $VF: synthetic method
      a(int var2, L1PcInstance var3, L1ValakasLair.a var4) {
         this(var2, var3);
      }
   }
}
