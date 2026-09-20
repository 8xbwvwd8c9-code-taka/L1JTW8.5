package l1r.as;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.DoorTable;
import l1r.ao.FieldSpawnTable;
import l1r.ao.ItemTable;
import l1r.ao.MapsTable;
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
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.ServerBasePacket;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1CentralTemple {
   private static final Logger a = Logger.getLogger(L1CentralTemple.class.getName());
   private static L1CentralTemple b;
   private static final int c = 1936;
   private static final int d = 50;
   private static boolean[] e = new boolean[50];

   public static L1CentralTemple a() {
      if (b == null) {
         b = new L1CentralTemple();
      }

      return b;
   }

   private L1CentralTemple() {
      L1Map var1 = L1WorldMap.b().a(1936);

      for (int var2 = 32799; var2 <= 32800; var2++) {
         for (int var3 = 32843; var3 <= 32851; var3++) {
            var1.a(var2, var3, 511);
         }
      }

      for (int var5 = 32811; var5 <= 32817; var5++) {
         for (int var9 = 32861; var9 <= 32862; var9++) {
            var1.a(var5, var9, 511);
         }
      }

      for (int var6 = 32800; var6 <= 32801; var6++) {
         for (int var10 = 32874; var10 <= 32879; var10++) {
            var1.a(var6, var10, 511);
         }
      }

      for (int var7 = 32784; var7 <= 32788; var7++) {
         for (int var11 = 32860; var11 <= 32861; var11++) {
            var1.a(var7, var11, 511);
         }
      }

      for (int var8 = 1; var8 < 50; var8++) {
         try {
            L1Map var12 = var1.s();
            var12.a = 1936 + var8;
            MapsTable.a().a(var12);
            L1WorldMap.b().a().put(var12.a, var12);
         } catch (CloneNotSupportedException var4) {
            a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
         }
      }
   }

   public boolean a(L1PcInstance var1) {
      for (int var2 = 0; var2 < e.length; var2++) {
         if (!e[var2]) {
            e[var2] = true;
            GeneralThreadPool.a().b(new L1CentralTemple.L1R_a(1936 + var2, var1, null));
            return true;
         }
      }

      return false;
   }

   private class L1R_a extends Thread {
      private final L1PcInstance b;
      private final int c;
      private L1NpcInstance d;
      private int e = -1;

      private L1R_a(int var2, L1PcInstance var3) {
         this.b = var3;
         this.c = var2;
      }

      @Override
      public void run() {
         try {
            L1Teleport.a(this.b, 32795, 32867, this.c, 1, true);
            this.d = this.a(new L1Location(32801, 32862, this.c), 190114, 1, 4).get(0);
            L1FieldObjectInstance var1 = FieldSpawnTable.a().a(7572, 32801, 32862, this.c);
            ArrayList var2 = new ArrayList<>();
            var2.addAll(this.a(new L1Location(32798, 32862, this.c), 190110, 1, 2));
            var2.addAll(this.a(new L1Location(32801, 32865, this.c), 190110, 1, 0));
            var2.addAll(this.a(new L1Location(32804, 32859, this.c), 190110, 1, 5));
            var2.addAll(this.a(new L1Location(32798, 32861, this.c), 190111, 1, 2));
            var2.addAll(this.a(new L1Location(32802, 32866, this.c), 190111, 1, 0));
            this.a(var2);
            ItemTable.a(this.b, 640354, 1, true);
            var1.aa_();
            this.b.a(new S_PacketBox(156, 1, 3));
            this.a("$17947");
            ArrayList var3 = new ArrayList<>();
            var3.addAll(this.a(new L1Location(32800, 32845, this.c), 190098, 15, 4));
            var3.addAll(this.a(new L1Location(32800, 32845, this.c), 190099, 15, 4));
            var3.addAll(this.a(new L1Location(32817, 32862, this.c), 190101, 15, 7));
            var3.addAll(this.a(new L1Location(32817, 32862, this.c), 190100, 15, 7));
            var3.addAll(this.a(new L1Location(32801, 32878, this.c), 190102, 15, 0));
            var3.addAll(this.a(new L1Location(32801, 32878, this.c), 190103, 15, 0));
            var3.addAll(this.a(new L1Location(32785, 32861, this.c), 190104, 15, 2));
            var3.addAll(this.a(new L1Location(32785, 32861, this.c), 190105, 15, 2));
            this.a(3000L);
            this.a("$17701");
            this.a(var3);
            this.b.a(new S_PacketBox(156, 2, 3));
            ArrayList var4 = new ArrayList<>();
            this.a("$17969");
            this.e = 0;
            int var5 = 190106 + Random.a(4);
            if (var5 == 190106) {
               this.a("$17941");
               var4.addAll(this.a(new L1Location(32800, 32845, this.c), var5, 1, 4));
            } else if (var5 == 190107) {
               this.a("$17944");
               var4.addAll(this.a(new L1Location(32817, 32862, this.c), var5, 1, 7));
            } else if (var5 == 190108) {
               this.a("$17942");
               var4.addAll(this.a(new L1Location(32801, 32878, this.c), var5, 1, 0));
            } else if (var5 == 190109) {
               this.a("$17943");
               var4.addAll(this.a(new L1Location(32785, 32861, this.c), var5, 1, 2));
            }

            var4.addAll(this.a(new L1Location(32800, 32845, this.c), 190098, 15, 4));
            var4.addAll(this.a(new L1Location(32800, 32845, this.c), 190099, 15, 4));
            var4.addAll(this.a(new L1Location(32817, 32862, this.c), 190101, 15, 7));
            var4.addAll(this.a(new L1Location(32817, 32862, this.c), 190100, 15, 7));
            var4.addAll(this.a(new L1Location(32801, 32878, this.c), 190102, 15, 0));
            var4.addAll(this.a(new L1Location(32801, 32878, this.c), 190103, 15, 0));
            var4.addAll(this.a(new L1Location(32785, 32861, this.c), 190104, 15, 2));
            var4.addAll(this.a(new L1Location(32785, 32861, this.c), 190105, 15, 2));
            this.a(3000L);
            this.a("$17703");
            this.a(var4);
            this.b.a(new S_PacketBox(156, 3, 3));
            this.e = 0;
            ArrayList var6 = new ArrayList<>();
            int var7 = 190106 + Random.a(4);
            if (var7 == 190106) {
               this.a("$17941");
               var6.addAll(this.a(new L1Location(32800, 32845, this.c), var7, 1, 4));
            } else if (var7 == 190107) {
               this.a("$17944");
               var6.addAll(this.a(new L1Location(32817, 32862, this.c), var7, 1, 7));
            } else if (var7 == 190108) {
               this.a("$17942");
               var6.addAll(this.a(new L1Location(32801, 32878, this.c), var7, 1, 0));
            } else if (var7 == 190109) {
               this.a("$17943");
               var6.addAll(this.a(new L1Location(32785, 32861, this.c), var7, 1, 2));
            }

            this.a("$17969");
            var6.addAll(this.a(new L1Location(32800, 32845, this.c), 190098, 10, 4));
            var6.addAll(this.a(new L1Location(32800, 32845, this.c), 190099, 10, 4));
            var6.addAll(this.a(new L1Location(32817, 32862, this.c), 190101, 10, 7));
            var6.addAll(this.a(new L1Location(32817, 32862, this.c), 190100, 10, 7));
            var6.addAll(this.a(new L1Location(32801, 32878, this.c), 190102, 10, 0));
            var6.addAll(this.a(new L1Location(32801, 32878, this.c), 190103, 10, 0));
            var6.addAll(this.a(new L1Location(32785, 32861, this.c), 190104, 10, 2));
            var6.addAll(this.a(new L1Location(32785, 32861, this.c), 190105, 10, 2));
            this.a(3000L);
            this.a("$17703");
            this.a(var6);
            ArrayList var8 = new ArrayList<>();
            int var9 = Random.a(4);
            if (var9 == 0) {
               var8.addAll(this.a(new L1Location(32800, 32845, this.c), 190112, 1, 4));
            } else if (var9 == 1) {
               var8.addAll(this.a(new L1Location(32817, 32862, this.c), 190112, 1, 7));
            } else if (var9 == 2) {
               var8.addAll(this.a(new L1Location(32801, 32878, this.c), 190112, 1, 0));
            } else if (var9 == 3) {
               var8.addAll(this.a(new L1Location(32785, 32861, this.c), 190112, 1, 2));
            }

            this.a("$17995:$17713");
            var8.addAll(this.a(new L1Location(32800, 32845, this.c), 190098, 10, 4));
            var8.addAll(this.a(new L1Location(32800, 32845, this.c), 190099, 10, 4));
            var8.addAll(this.a(new L1Location(32817, 32862, this.c), 190101, 10, 7));
            var8.addAll(this.a(new L1Location(32817, 32862, this.c), 190100, 10, 7));
            var8.addAll(this.a(new L1Location(32801, 32878, this.c), 190102, 10, 0));
            var8.addAll(this.a(new L1Location(32801, 32878, this.c), 190103, 10, 0));
            var8.addAll(this.a(new L1Location(32785, 32861, this.c), 190104, 10, 2));
            var8.addAll(this.a(new L1Location(32785, 32861, this.c), 190105, 10, 2));
            this.a(var8);
            this.a("$17707");
            this.d.g_(190115);
            this.a(3000L);
            this.a("$17708");
            this.a(3000L);
            this.a("$17709");
            this.a(3000L);
            this.a("$17710");
            this.a(3000L);
            this.a("$17712");
            this.a(3000L);
            this.d.b(new S_SkillSound(this.d.fr(), 169));
            this.d.aa_();
            this.c();
            this.a("$17962");
            this.a(10000L);
            this.b();
         } catch (Exception var13) {
         } finally {
            this.a();
            L1CentralTemple.e[this.c - 1936] = false;
            System.out.println("[副本結束]:中央寺院(" + this.c + ")");
         }
      }

      private void a() {
         for (L1Object var1 : L1World.a().b()) {
            if (var1.fp() == this.c) {
               if (var1 instanceof L1PcInstance) {
                  L1PcInstance var3 = (L1PcInstance)var1;
                  var3.j().a(640354);
                  var3.j().a(640355);
                  L1Teleport.a(var3, 33703, 32502, 4, 5, true);
               } else if (var1 instanceof L1DoorInstance) {
                  DoorTable.b().a(var1.fu());
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
         this.a(new S_PacketBox(84, 2, "\\f=" + var1));
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

      private void c() {
         int[] var1 = new int[]{40087, 40074, 40087, 40074, 40087, 40074};
         int[] var2 = new int[]{264, 262, 260, 263, 261, 326, 337, 336, 328, 329, 21152, 21154, 21153, 21155};
         if (Random.a(100) > 10) {
            ItemTable.a(this.d, var1[Random.a(var1.length)], 1);
         } else {
            ItemTable.a(this.d, var2[Random.a(var2.length)], 1);
         }

         ItemTable.a(this.d, 640353, 1);
      }

      private ArrayList<L1NpcInstance> a(L1Location var1, int var2, int var3, int var4) {
         int[] var5 = new int[var3];

         for (int var6 = 0; var6 < var3; var6++) {
            var5[var6] = var2;
         }

         return this.a(var1, var5, var4);
      }

      private ArrayList<L1NpcInstance> a(L1Location var1, int[] var2, int var3) {
         ArrayList var4 = new ArrayList<>();
         int[] var8 = var2;
         int var7 = var2.length;

         for (int var6 = 0; var6 < var7; var6++) {
            int var5 = var8[var6];
            L1NpcInstance var9 = SpawnTable.a(var5, var1.f(), var1.g(), var1.b(), var3, true);
            var4.add(var9);
         }

         return var4;
      }

      private int a(ArrayList<L1NpcInstance> var1) throws InterruptedException {
         int var2 = -1;

         while (var2++ < 300) {
            boolean var3 = false;

            for (L1NpcInstance var4 : var1) {
               if (!var4.eX()) {
                  var3 = false;
                  break;
               }

               if (this.e == 0 && var4.z() >= 190106 && var4.z() <= 190109) {
                  this.a("$17968");
                  ItemTable.a(this.b, 640355, 1, true);
                  this.e = 1;
               }

               var3 = var4.eX();
            }

            if (var3) {
               return var2;
            }

            this.a(1000L);
         }

         this.a("$17714");
         this.a(3000L);
         this.a("$17715");
         this.d.aa_();
         this.b();
         this.a();
         return -1;
      }

      // $VF: synthetic method
      L1R_a(int var2, L1PcInstance var3, L1CentralTemple.L1R_a var4) {
         this(var2, var3);
      }
   }
}
