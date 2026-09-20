package l1r.as;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import l1r.ai.IdFactory;
import l1r.ao.NpcTable;
import l1r.ao.TrapSpawnTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1TrapInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.ax.L1Map;
import l1r.bd.L1TeleportTrap;
import l1r.be.S_CastleMaster;
import l1r.be.S_DoActionGFX;
import l1r.be.S_EffectLocation;
import l1r.be.S_PacketBox;
import l1r.be.S_SkillSound;
import l1r.be.S_Sound;
import l1r.be.ServerBasePacket;
import l1r.bi.Point;
import l1r.bi.Random;

public class L1OrimBattle {
   private static final int c = 5;
   private static final int d = 6;
   private static final int e = 10;
   private static final int f = 11;
   private static final int g = 12;
   private static final int h = 13;
   private static final int i = 14;
   private static final int j = 15;
   private static final int k = 16;
   private static final int l = 17;
   private static final int m = 18;
   private static final int n = 19;
   private static final int o = 20;
   private static final int p = 21;
   private static final int q = 22;
   private static final int r = 23;
   private static final int s = 24;
   private static final int t = 25;
   private static final int u = 100;
   private static final int v = 101;
   private static final int w = 102;
   private static final int x = 103;
   private static final int y = 104;
   private static final int z = 105;
   private static final int A = 106;
   public boolean a = true;
   private final int B;
   public int b;
   private int C;
   private int D;
   private int E;
   private L1Location F;
   private L1Location G;
   private L1NpcInstance H;
   private L1NpcInstance I;
   private L1NpcInstance J;
   private L1NpcInstance K;
   private L1NpcInstance L;
   private final int[] M = new int[12];
   private final int[] N = new int[12];
   private final ArrayList<L1NpcInstance> O = new ArrayList<>();
   private final CopyOnWriteArrayList<L1PcInstance> P = new CopyOnWriteArrayList<>();
   private final CopyOnWriteArrayList<L1Object> Q = new CopyOnWriteArrayList<>();
   private ScheduledExecutorService R;
   private static L1OrimBattle S;
   private final int[] T = new int[]{
      10638,
      10639,
      10640,
      10641,
      10642,
      10643,
      10644,
      10645,
      10646,
      10647,
      10648,
      10649,
      10666,
      10667,
      10668,
      10669,
      10670,
      10671,
      10672,
      10673,
      10674,
      10675,
      10676,
      10677,
      10678,
      10679,
      10680,
      10681,
      10682,
      10683,
      10684,
      10685,
      10686
   };
   private final int[] U = new int[]{
      91378, 91373, 91516, 91514, 91513, 91515, 91517, 91521, 91518, 91380, 91519, 91520, 91552, 91392, 91522, 91523, 91524, 91525, 91526, 91527
   };
   private final int[] V = new int[4];

   private void b() {
      this.a = false;

      for (L1Object var1 : L1World.a().b(this.B).values()) {
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            this.P.add(var3);
         }
      }

      for (L1PcInstance var4 : this.P) {
         if (var4.am()) {
            var4.a(new S_CastleMaster(10, var4.fr()));
            var4.b(new S_CastleMaster(10, var4.fr()));
         }
      }

      this.O.addAll(this.a(new L1Location(32803, 32809, this.B), 91507, 1));
      this.O.addAll(this.a(new L1Location(32792, 32809, this.B), 91508, 1));
      this.I = this.a(new L1Location(32799, 32806, this.B), 91506, 1).get(0);
      this.I.cq(32);
      this.F = new L1Location(32799, 32809, this.B);
      this.G = new L1Location(32799, 32803, this.B);
      this.R = Executors.newScheduledThreadPool(2);
   }

   private void c() {
      this.i();

      for (L1Object var1 : L1World.a().b(this.B).values()) {
         L1World.a().d(var1);
      }

      this.a = true;
      this.R.shutdownNow();
   }

   public static L1OrimBattle a() {
      if (S == null) {
         S = new L1OrimBattle(9101);
      }

      return S;
   }

   private L1OrimBattle(int var1) {
      this.B = var1;
   }

   public void a(int var1) {
      this.b();
      new L1OrimBattle.a(var1, 0, null).a();
      new L1OrimBattle.a(6, 0, null).a();
   }

   private void b(int var1) throws InterruptedException {
      int var2 = this.P.size() <= 1 ? 1 : this.P.size() - 1;
      ArrayList var3 = this.a(this.G, 91510, var2);
      L1Location var4 = this.b(this.G, 10);
      ArrayList var5 = this.a(var4, 91511, 1);
      int var6 = 0;
      int var7 = 0;
      int var8 = -1;
      boolean var9 = false;
      boolean var10 = false;

      while (var8++ < 10) {
         Thread.sleep(1000L);
         if (!var9) {
            for (L1NpcInstance var11 : var5) {
               var9 = this.a(var11.fu(), 2);
            }
         } else if (this.b == 66) {
            this.b = 0;

            for (L1NpcInstance var14 : this.O) {
               var14.b(new S_DoActionGFX(var14.fr(), 2));
            }

            if (var7 == 0) {
               var7 = var8;
            }
         }

         if (!var10) {
            for (L1NpcInstance var15 : var3) {
               var10 = this.a(var15.fu(), 2);
            }
         } else if (this.b == 69) {
            this.b = 0;

            for (L1PcInstance var16 : this.P) {
               if (var16.am()) {
                  var16.a(new S_SkillSound(var16.fr(), 10165));
                  var16.b(new S_SkillSound(var16.fr(), 10165));
               }
            }

            if (var6 == 0) {
               var6 = var8;
            }
         }

         if (var7 * var6 > 0) {
            break;
         }
      }

      var5.addAll(var3);

      for (L1NpcInstance var17 : var5) {
         var17.aa_();
      }

      if (var7 != 0 && Random.a(4) > 0) {
         this.a("Critical HIT!", this.M[var1]);
         this.M[var1]++;
      }

      if (var6 == 0) {
         for (int var18 = 0; var18 < 5; var18++) {
            int var23 = 32790 + Random.a(25);
            int var13 = Random.a(2) == 0 ? 32818 : 32788;
            this.a(new S_EffectLocation(var23, var13, 8233));
            this.a(new S_PacketBox(83, 2));
            Thread.sleep(1000L);
         }
      }
   }

   private boolean a(L1Location var1, int var2) {
      for (L1PcInstance var3 : this.P) {
         if (var2 == -1 && var3.fu().e(var1)) {
            L1Teleport.a(var3, 32799, 32809, this.B, 1, false);
            return true;
         }

         if (var3.fu().d(var1) < var2) {
            return true;
         }
      }

      return false;
   }

   private void d() throws InterruptedException {
      this.a("$9603", 0);
      this.a(new S_PacketBox(83, 2));
      this.a(new S_Sound(82));
      int[] var1 = new int[7];

      for (int var2 = 0; var2 < 7; var2++) {
         var1[var2] = this.U[Random.a(5)];
      }

      L1Location var6 = new L1Location(32797, 32803, this.B);
      ArrayList var3 = this.a(var6, var1);
      int var4 = 35;
      int var5 = this.a(var3, 35000);
      this.C = (35 - var5) / 5;
      Thread.sleep(3000L);
   }

   private void e() throws InterruptedException {
      this.a("$9548", 0);
      int var1 = Random.a(4);
      this.V[var1]++;
      Thread.sleep(5000L);
      new ArrayList();
      if (var1 == 0) {
         this.a(new S_EffectLocation(new L1Location(32803, 32788, this.B), 8142));
         this.a("$9541", 0);
      } else if (var1 == 1) {
         int var3 = Random.a(4) + 6;
         ArrayList var2 = this.a(this.G, 91540, var3);
         this.a(var2, -1, 40);
         this.a(var2, 0);
         this.a("$9542", 0);
      } else if (var1 == 2) {
         int var6 = Random.a(4) + 6;
         ArrayList var5 = this.a(this.G, 91539, var6);
         this.a(var5, -1, 40);
         this.a(var5, 0);
         this.a("$9543", 0);
      } else if (var1 == 3) {
         this.a(new S_EffectLocation(new L1Location(32800, 32794, this.B), 8241));
         this.a("$9544", 0);
      }

      Thread.sleep(5000L);
      if (this.V[var1] == 3) {
         if (var1 == 1) {
            ArrayList var7 = this.a(this.G, 91540, 3);
            this.a("$9549", 1);
            this.a(var7, 15000);
         } else if (var1 == 2) {
            ArrayList var8 = this.a(this.G, 91539, 3);
            this.a("$9549", 1);
            this.a(var8, 15000);
         } else if (var1 == 3) {
            ArrayList var9 = this.a(new L1Location(32800, 32794, this.B), 91538, 1);
            var9.addAll(this.a(new L1Location(32800, 32795, this.B), 91548, 1));
            var9.addAll(this.a(new L1Location(32800, 32796, this.B), 91549, 1));
            this.a("$9558", 1);
            Thread.sleep(3000L);
            this.a("$10720", 1);
            int var4 = this.a(var9, 60000);
            if (var4 < 60) {
               this.a(new L1Location(32798, 32807, this.B), 91497, 1);
            }
         }
      }

      Thread.sleep(3000L);
      this.a("$" + this.T[Math.min(this.D / 100, 33)], 1);
      Thread.sleep(3000L);
   }

   private void c(int var1) throws InterruptedException {
      L1Location var2 = new L1Location(32794, 32825, this.B);
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;

      for (int var6 = 0; var6 < 12; var6++) {
         if (var6 < 4) {
            var3 += this.M[var6];
         } else if (var6 >= 4 && 7 >= var6) {
            var4 += this.M[var6];
         } else if (var6 >= 8 && 10 >= var6) {
            var5 += this.M[var6];
         }
      }

      int var8 = 0;
      if (var1 >= 0 && var1 <= 3) {
         var8 = new int[]{91503, 91504, 91505, 91498}[(var3 - 1) / 3];
      } else if (var1 >= 4 && var1 <= 7) {
         var8 = new int[]{91503, 91504, 91505, 91498}[(var4 - 1) / 3];
      } else if (var1 >= 8 && var1 <= 11) {
         var8 = new int[]{91503, 91504, 91505, 91499}[(var5 - 1) / 3];
      }

      this.H = this.a(var2, var8, 1).get(0);
      this.b(var1);
      this.b(var1);
      this.b(var1);
      Thread.sleep(5000L);
      if (this.H != null) {
         this.a(this.H, 0, 10);
         this.f();
         if (var8 == 91498) {
            this.a("$9553", 0);
            Thread.sleep(2000L);
            this.a("$9559", 1);
            this.a(91509, this.F, new L1Location(32741, 32855, this.B));
            this.a(91509, new L1Location(32741, 32861, this.B), this.F);
         } else if (var8 == 91499) {
            this.a("$9552", 0);
            Thread.sleep(2000L);
            this.a("$9559", 1);
            this.a(91509, this.F, new L1Location(32741, 32855, this.B));
            this.a(91509, new L1Location(32741, 32861, this.B), this.F);
         } else {
            this.a("$9555", 0);
            Thread.sleep(2000L);
            if (var1 == 3 && var3 >= 12) {
               int var9 = 0;
               if (this.D <= 300) {
                  var9 = 91537;
               } else if (this.D <= 600) {
                  var9 = 91533;
               } else if (this.D > 600) {
                  var9 = 91535;
               }

               this.a(new L1Location(32671, 32802, this.B), var9, 1);
               this.a(91509, this.F, new L1Location(32677, 32795, this.B));
               this.a(91509, new L1Location(32677, 32800, this.B), this.F);
            } else if (var1 == 7 && var4 >= 12) {
               int var7 = 0;
               if (this.D <= 300) {
                  var7 = 91537;
               } else if (this.D <= 600) {
                  var7 = 91533;
               } else if (this.D > 600) {
                  var7 = 91535;
               }

               this.a(new L1Location(32671, 32866, this.B), var7, 1);
               this.a(91509, this.F, new L1Location(32677, 32859, this.B));
               this.a(91509, new L1Location(32677, 32864, this.B), this.F);
            }
         }
      }

      Thread.sleep(6000L);
   }

   private void d(int var1) throws InterruptedException {
      for (int var2 = 0; var2 < 3; var2++) {
         ArrayList var3 = this.f(var1);
         if (var2 == 0 && (var1 == 3 || var1 == 7) && this.Q.isEmpty()) {
            int var4 = 0;
            if (this.D <= 300) {
               var4 = 91537;
            } else if (this.D <= 600) {
               var4 = 91533;
            } else if (this.D > 600) {
               var4 = 91535;
            }

            var3.addAll(this.a(this.G, var4, 1));
         }

         int var9 = 45;
         int var5 = this.a(var3, 45000);
         this.N[var1] = var5 == 45 ? -1 : 25 / var5;
         if (var5 == 45) {
            this.a("$" + (9563 + var2), 1);
         } else {
            this.a("$9560", 2);
         }

         Thread.sleep(3000L);
      }

      this.a("$9608", 2);
      this.a(new S_PacketBox(83, 4));
      ArrayList var6 = this.a(new L1Location(32735, 32802, this.B), 91512, 3);
      Collections.shuffle(this.P);
      Iterator var10 = this.P.iterator();
      if (var10.hasNext()) {
         L1PcInstance var7 = (L1PcInstance)var10.next();
         L1Location var12 = new L1Location(var7.fs(), var7.ft(), var7.fp());
         Thread.sleep(3000L);
         L1Teleport.a(var7, 32737, 32800, this.B, 1, true);
         this.a(var6, 6000);
         this.a("$9606", 1);
         Thread.sleep(3000L);
         L1Teleport.a(var7, var12.f(), var12.g(), var12.b(), 4, true);
      }

      for (L1Object var8 : this.Q) {
         if (var8 instanceof L1NpcInstance && !var8.fu().f(this.F)) {
            this.a(var8.fu(), -1);
         }
      }

      this.i();
      if (this.H != null) {
         this.a(this.H, 4, 10);
         this.H.aa_();
      }
   }

   private void e(int var1) throws InterruptedException {
      ArrayList var2 = this.f(11);
      int[] var3;
      if (this.D > 3000) {
         var3 = new int[]{91550, 91530, 91531, 91532};
      } else if (this.D < 1200) {
         var3 = new int[]{91528};
      } else {
         var3 = new int[]{91529, 91530, 91531, 91532};
      }

      var2.addAll(this.a(this.G, var3));

      for (int var4 = -1; var4++ < var1 / 1000 && this.E < 10; Thread.sleep(1000L)) {
         Iterator var6 = var2.iterator();

         while (true) {
            if (var6.hasNext()) {
               L1NpcInstance var5 = (L1NpcInstance)var6.next();
               if (var5.eX()) {
                  continue;
               }
            }

            if (var4 % 30 == 0) {
               this.g();
            }
            break;
         }
      }

      ArrayList var7 = this.a(this.G, 91553, this.P.size() * 2);
      this.a(var7, 15000);
   }

   private void f() {
      int[] var1 = new int[]{32, 32, 33, 33, 34, 34, 35, 35, 36, 36};
      this.I.cq(var1[this.E]);
      this.I.b(new S_DoActionGFX(this.I.fr(), this.I.eY()));
      this.a(new S_PacketBox(83, 2));
   }

   private void g() throws InterruptedException {
      this.E++;
      this.a(new S_PacketBox(83, 2));
      L1NpcInstance var1 = this.a(this.G, 91544, 3).get(0);
      var1.b(new S_SkillSound(var1.fr(), 762));
      if (this.E == 10) {
         this.a("$9562", 1);
         Thread.sleep(10000L);
         this.h();
      } else if (this.E == 9) {
         this.a("$9587", 1);
      } else if (this.E < 9) {
         this.a("$9561", 1);
      }
   }

   private void a(L1NpcInstance var1, int var2, int var3) throws InterruptedException {
      for (int var4 = 0; var4 < var3; var4++) {
         var1.g(var2);
         Thread.sleep(1000L);
      }
   }

   private void a(ArrayList<L1NpcInstance> var1, int var2, int var3) throws InterruptedException {
      for (int var4 = 0; var4 < var3; var4++) {
         for (L1NpcInstance var5 : var1) {
            if (var4 == 0 && var5.fb() != var2) {
               var5.ct(Random.a(8));
            }

            var5.g(var5.fb());
         }

         Thread.sleep(100L);
      }
   }

   private L1TrapInstance a(int var1, L1Location var2, L1Location var3) {
      int var4 = IdFactory.a().c();
      L1TeleportTrap var5 = new L1TeleportTrap(var4, 0, var3);
      L1TrapInstance var6 = new L1TrapInstance(var5.a(), var5, var2, new Point(), 2);
      TrapSpawnTable.a().a(var6);
      this.Q.addAll(this.a(var2, var1, 1));
      this.Q.add(var6);
      return var6;
   }

   private ArrayList<L1NpcInstance> f(int var1) {
      for (int var2 = 0; var2 < var1 && this.C <= this.U.length * 2 / 3; var2++) {
         this.C = this.C + this.N[var2];
      }

      this.C = Math.max(0, this.C);
      this.C = Math.min(this.C, this.U.length - 1);
      int[] var4 = new int[7];

      for (int var3 = 0; var3 < 7; var3++) {
         var4[var3] = this.U[this.C + Random.a(this.U.length - this.C)];
      }

      return this.a(this.G, var4);
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
         if (var2.length > 1 && var4 != 91512) {
            var1 = this.b(var1, 10);
         }

         L1NpcInstance var8 = NpcTable.a().b(var4);
         if (var8 instanceof L1MonsterInstance) {
            this.a(new S_EffectLocation(var1.f(), var1.g(), 11509));
         }

         var8.cF(IdFactory.a().c());
         var8.cE((short)var1.b());
         var8.ct(5);
         var8.cG(var1.f());
         var8.cH(var1.g());
         L1World.a().a(var8);
         L1World.a().c(var8);
         var3.add(var8);
      }

      return var3;
   }

   private int a(ArrayList<L1NpcInstance> var1, int var2) throws InterruptedException {
      int var3 = -1;

      while (var3++ < var2 / 1000) {
         boolean var4 = false;

         for (L1NpcInstance var5 : var1) {
            if (!var5.eX()) {
               var4 = false;
               break;
            }

            var4 = var5.eX();
         }

         if (var4) {
            this.a(var1);
            return var3;
         }

         Thread.sleep(1000L);
      }

      this.a(var1);
      if (var2 != 0) {
         this.g();
      }

      return var2 / 1000;
   }

   private void a(ArrayList<L1NpcInstance> var1) {
      for (L1NpcInstance var2 : var1) {
         if (var2.eX()) {
            this.D = this.D + var2.ev() / 5;
         }

         var2.aa_();
      }

      for (L1PcInstance var4 : this.P) {
         var4.a(new S_PacketBox(84, this.D > 0 ? 4 : 3, "" + this.D));
      }
   }

   private void a(String var1, int var2) {
      String[] var3 = new String[]{"", "\\f=", "\\f3"};

      for (L1PcInstance var4 : this.P) {
         var4.a(new S_PacketBox(84, 2, var3[var2] + var1));
      }
   }

   private void a(ServerBasePacket var1) {
      for (L1PcInstance var2 : this.P) {
         var2.a(var1);
      }
   }

   private void h() {
      for (L1PcInstance var1 : this.P) {
         if (var1.am()) {
            var1.a(new S_CastleMaster(10, 0));
            var1.b(new S_CastleMaster(10, 0));
         }

         int var3 = 32587 + Random.a(4);
         int var4 = 32941 + Random.a(4);
         L1Teleport.a(var1, var3, var4, 0, 5, true);
      }
   }

   private void i() {
      for (L1Object var1 : this.Q) {
         if (var1 instanceof L1TrapInstance) {
            TrapSpawnTable.a().b((L1TrapInstance)var1);
         } else if (var1 instanceof L1NpcInstance) {
            ((L1NpcInstance)var1).aa_();
         }

         this.Q.remove(var1);
      }
   }

   private L1Location b(L1Location var1, int var2) {
      L1Location var3 = new L1Location();
      int var4 = 0;
      int var5 = 0;
      int var6 = var1.f();
      int var7 = var1.g();
      L1Map var8 = var1.a();
      var3.a(var8);
      int var9 = var6 - var2;
      int var10 = var6 + var2;
      int var11 = var7 - var2;
      int var12 = var7 + var2;
      var9 = Math.max(32789, var9);
      var10 = Math.min(var10, 32808);
      var11 = Math.max(32796, var11);
      var12 = Math.min(var12, 32809);
      int var13 = var10 - var9;
      int var14 = var12 - var11;
      int var15 = 0;
      int var16 = (int)Math.pow(1 + var2 * 2, 2.0);
      int var17 = 40 * var16 / var16;

      do {
         if (var15 >= var17) {
            var3.a(var6, var7);
            break;
         }

         var15++;
         var4 = var9 + Random.a(var13 + 1);
         var5 = var11 + Random.a(var14 + 1);
         var3.a(var4, var5);
      } while (!var8.b(var4, var5) || !var8.c(var4, var5));

      return var3;
   }

   private class a implements Runnable {
      int a;
      int b;

      private a(int var2, int var3) {
         this.a = var3;
         this.b = var2;
      }

      private void a() {
         L1OrimBattle.this.R.schedule(this, this.a, TimeUnit.MILLISECONDS);
      }

      @Override
      public void run() {
         switch (this.b) {
            case 5:
               try {
                  L1OrimBattle.this.a("$9540", 0);

                  for (int var11 = 0; var11 < 12; var11++) {
                     Thread.sleep(3000L);
                     if (var11 == 0) {
                        L1OrimBattle.this.d();
                     }

                     L1OrimBattle.this.e();
                     if (var11 == 11) {
                        L1OrimBattle.this.a("$9556", 0);
                     } else if ((var11 + 1) % 4 == 0) {
                        L1OrimBattle.this.a("$9554", 0);
                     } else {
                        L1OrimBattle.this.a("$9550", 0);
                     }

                     Thread.sleep(3000L);
                     if (var11 != 11) {
                        L1OrimBattle.this.a("$9551", 0);
                     }

                     L1OrimBattle.this.c(var11);
                     L1OrimBattle.this.a("$" + (9609 + var11), 1);
                     Thread.sleep(3000L);
                     L1OrimBattle.this.a(new S_PacketBox(83, 2));
                     L1OrimBattle.this.a(new S_Sound(82));
                     Thread.sleep(3000L);
                     if (var11 != 11) {
                        L1OrimBattle.this.d(var11);
                     } else {
                        L1OrimBattle.this.e(300000);
                        L1OrimBattle.this.new a(100, 0).a();
                     }
                  }
               } catch (InterruptedException var8) {
               }
               break;
            case 6:
               try {
                  try {
                     while (!L1OrimBattle.this.P.isEmpty()) {
                        for (L1PcInstance var2 : L1OrimBattle.this.P) {
                           if (var2.bE() == 0 || var2.fp() != L1OrimBattle.this.B) {
                              L1OrimBattle.this.P.remove(var2);
                           }
                        }

                        Thread.sleep(3000L);
                     }
                  } catch (Exception var9) {
                  }
                  break;
               } finally {
                  System.out.println("[副本結束]:海戰副本");
                  L1OrimBattle.this.c();
               }
            case 10:
               L1OrimBattle.this.new a(11, 5000).a();
               break;
            case 11:
               L1OrimBattle.this.a("$9529", 0);
               L1OrimBattle.this.new a(12, 5000).a();
               break;
            case 12:
               L1OrimBattle.this.a("$9530", 0);
               L1OrimBattle.this.new a(13, 5000).a();
               break;
            case 13:
               if (L1OrimBattle.this.b == 68) {
                  L1OrimBattle.this.b = 0;
                  L1OrimBattle.this.new a(5, 5000).a();
               } else {
                  L1OrimBattle.this.a("$9531", 0);
                  L1OrimBattle.this.new a(14, 5000).a();
               }
               break;
            case 14:
               L1OrimBattle.this.a("$9532", 0);
               L1OrimBattle.this.new a(15, 5000).a();
               break;
            case 15:
               L1OrimBattle.this.a("$9533", 0);
               L1OrimBattle.this.new a(16, 5000).a();
               break;
            case 16:
               L1OrimBattle.this.a("$9534", 0);
               L1Location var1 = L1OrimBattle.this.b(L1OrimBattle.this.G, 10);
               L1OrimBattle.this.J = L1OrimBattle.this.a(var1, 91511, 1).get(0);
               L1OrimBattle.this.new a(17, 5000).a();
               break;
            case 17:
               L1OrimBattle.this.a("$9535", 0);
               L1OrimBattle.this.new a(18, 5000).a();
               break;
            case 18:
               L1OrimBattle.this.a("$9536", 0);
               L1OrimBattle.this.new a(19, 5000).a();
               break;
            case 19:
               if (L1OrimBattle.this.b == 66 && L1OrimBattle.this.a(L1OrimBattle.this.J.fu(), 2)) {
                  L1OrimBattle.this.b = 0;
                  L1OrimBattle.this.J.aa_();
                  L1OrimBattle.this.new a(21, 5000).a();
               } else {
                  L1OrimBattle.this.a("$9545", 0);
                  L1OrimBattle.this.new a(20, 5000).a();
               }
               break;
            case 20:
               if (L1OrimBattle.this.b == 66 && L1OrimBattle.this.a(L1OrimBattle.this.J.fu(), 2)) {
                  L1OrimBattle.this.b = 0;
               } else {
                  L1OrimBattle.this.a("$9546", 0);
               }

               L1OrimBattle.this.J.aa_();
               L1OrimBattle.this.new a(21, 5000).a();
               break;
            case 21:
               L1OrimBattle.this.a("$9537", 0);
               L1OrimBattle.this.new a(22, 8000).a();
               break;
            case 22:
               L1OrimBattle.this.a("$9538", 0);
               L1OrimBattle.this.K = L1OrimBattle.this.a(L1OrimBattle.this.G, 91510, 2).get(0);
               L1OrimBattle.this.L = L1OrimBattle.this.a(L1OrimBattle.this.G, 91510, 2).get(1);
               L1OrimBattle.this.new a(23, 5000).a();
               break;
            case 23:
               L1OrimBattle.this.a("$9539", 0);
               L1OrimBattle.this.new a(24, 5000).a();
               break;
            case 24:
               if (L1OrimBattle.this.b == 69 && L1OrimBattle.this.a(L1OrimBattle.this.K.fu(), 2) && L1OrimBattle.this.a(L1OrimBattle.this.L.fu(), 2)) {
                  L1OrimBattle.this.b = 0;
                  L1OrimBattle.this.K.aa_();
                  L1OrimBattle.this.L.aa_();
                  L1OrimBattle.this.new a(5, 5000).a();
               } else {
                  L1OrimBattle.this.a("$9545", 0);
                  L1OrimBattle.this.new a(25, 5000).a();
               }
               break;
            case 25:
               if (L1OrimBattle.this.b == 69 && L1OrimBattle.this.a(L1OrimBattle.this.K.fu(), 2) && L1OrimBattle.this.a(L1OrimBattle.this.L.fu(), 2)) {
                  L1OrimBattle.this.b = 0;
               } else {
                  L1OrimBattle.this.a("$9546", 0);
               }

               L1OrimBattle.this.K.aa_();
               L1OrimBattle.this.L.aa_();
               L1OrimBattle.this.new a(5, 8000).a();
               break;
            case 100:
               L1OrimBattle.this.a("$9579", 0);
               L1OrimBattle.this.new a(101, 5000).a();
               break;
            case 101:
               L1OrimBattle.this.a("$9580", 0);
               L1OrimBattle.this.new a(102, 5000).a();
               break;
            case 102:
               L1OrimBattle.this.a("$9581", 0);
               L1OrimBattle.this.new a(103, 5000).a();
               break;
            case 103:
               L1OrimBattle.this.a("$9582", 0);
               L1OrimBattle.this.new a(104, 5000).a();
               break;
            case 104:
               L1OrimBattle.this.a("$9583", 0);
               L1OrimBattle.this.new a(105, 5000).a();
               break;
            case 105:
               L1OrimBattle.this.a("$9584", 0);
               L1OrimBattle.this.new a(106, 5000).a();
               break;
            case 106:
               L1OrimBattle.this.h();
         }
      }

      // $VF: synthetic method
      a(int var2, int var3, L1OrimBattle.a var4) {
         this(var2, var3);
      }
   }
}
