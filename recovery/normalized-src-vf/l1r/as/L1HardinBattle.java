package l1r.as;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import l1r.ao.DoorTable;
import l1r.ao.FieldSpawnTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_Html;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PacketBox;
import l1r.be.S_SkillSound;
import l1r.bh.L1DoorGfx;
import l1r.bi.Random;

public class L1HardinBattle {
   private L1Location c;
   public int a;
   public boolean b = true;
   private boolean d;
   private final int e;
   private int f = 0;
   private int g = 0;
   private int h = 0;
   private L1PcInstance i;
   private L1NpcInstance j;
   private L1NpcInstance k;
   private L1NpcInstance l;
   private ScheduledExecutorService m;
   private final CopyOnWriteArrayList<L1PcInstance> n = new CopyOnWriteArrayList<>();
   private final ArrayList<L1DoorInstance> o = new ArrayList<>();
   private final ArrayList<L1DoorInstance> p = new ArrayList<>();
   private static L1HardinBattle q;
   private int r = 0;
   private static final int s = 69;
   private static final int t = 66;
   private static final int u = 1;
   private static final int v = 2;
   private static final int w = 3;
   private static final int x = 4;
   private static final int y = 5;
   private static final int z = 6;
   private static final int A = 7;
   private static final int B = 10;
   private static final int C = 11;
   private static final int D = 12;
   private static final int E = 13;
   private static final int F = 14;
   private static final int G = 15;
   private static final int H = 16;
   private static final int I = 17;
   private static final int J = 18;
   private static final int K = 19;
   private static final int L = 20;
   private static final int M = 21;
   private static final int N = 22;
   private static final int O = 23;
   private static final int P = 24;
   private static final int Q = 25;
   private static final int R = 26;
   private static final int S = 101;
   private static final int T = 102;
   private static final int U = 103;
   private static final int V = 104;
   private static final int W = 105;
   private static final int X = 106;
   private static final int Y = 201;
   private static final int Z = 202;
   private static final int aa = 203;
   private static final int ab = 204;
   private static final int ac = 205;
   private static final int ad = 206;
   private static final int ae = 301;
   private static final int af = 302;
   private static final int ag = 303;
   private static final int ah = 304;
   private static final int ai = 999;
   private final L1DoorGfx aj = L1DoorGfx.a(7536);
   private final int[] ak = new int[]{91374, 91375, 91376, 91377, 91378, 91379, 91380, 91381, 91382, 91383, 91384, 91385};
   private final int[] al = new int[]{91384, 91385, 91386, 91387, 91388, 91389, 91392};
   private final int[] am = new int[]{91370, 91371, 91373, 91554, 91555, 91370, 91371, 91373, 91554, 91555};
   private final int[] an = new int[]{91440, 91441, 91442, 91440, 91441, 91442};
   private final int[][] ao = new int[][]{{32666, 32817}, {32668, 32817}, {32668, 32819}, {32666, 32819}};
   private final int[][] ap = new int[][]{{32712, 32793}, {32703, 32791}, {32710, 32803}, {32703, 32800}};
   private final int[][] aq = new int[][]{{32807, 32839}, {32809, 32837}, {32807, 32837}, {32809, 32839}};
   private final int[][] ar = new int[][]{{32806, 32863}, {32808, 32864}, {32800, 32864}, {32799, 32866}, {32806, 32872}, {32798, 32872}, {32800, 32873}};
   private final int[][] as = new int[][]{{32763, 32800}, {32758, 32801}, {32663, 32876}, {32667, 32867}, {32722, 32866}};
   private final int[][] at = new int[][]{
      {32785, 32871},
      {32725, 32789},
      {32745, 32813},
      {32686, 32790},
      {32664, 32813},
      {32669, 32850},
      {32683, 32813},
      {32715, 32810},
      {32745, 32789},
      {32784, 32795},
      {32805, 32797},
      {32792, 32828}
   };

   public static L1HardinBattle a() {
      if (q == null) {
         q = new L1HardinBattle(9000);
      }

      return q;
   }

   private L1HardinBattle(int var1) {
      this.e = (short)var1;
   }

   public void a(int var1) {
      this.c();
      new L1HardinBattle.L1R_a(var1, 0, null).a();
      new L1HardinBattle.L1R_a(999, 0, null).a();
   }

   private void a(ArrayList<L1NpcInstance> var1, int var2, int var3) throws InterruptedException {
      for (int var4 = 0; var4 < var3; var4++) {
         for (L1NpcInstance var5 : var1) {
            if (var4 == 0 && var5.fb() != var2) {
               var5.ct(var2);
            }

            var5.g(var5.fb());
         }

         Thread.sleep(800L);
      }
   }

   private void b() {
      for (L1DoorInstance var1 : this.p) {
         DoorTable.b().a(var1.fu());
      }

      for (L1DoorInstance var3 : this.o) {
         DoorTable.b().a(var3.fu());
      }

      for (L1Object var4 : L1World.a().b(this.e).values()) {
         L1World.a().d(var4);
      }

      this.b = true;
      this.m.shutdownNow();
   }

   private void c() {
      this.b = false;
      this.c = new L1Location(32707, 32846, this.e);
      this.m = Executors.newScheduledThreadPool(10);

      for (L1Object var1 : L1World.a().b(this.e).values()) {
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            if (var3.am() || var3.l()) {
               this.i = var3;
            }

            this.n.add(var3);
         }
      }

      this.k = this.a(new L1Location(32742, 32930, this.e), 91397, 1).get(0);
      this.j = this.a(new L1Location(32733, 32724, this.e), 91430, 1).get(0);
      int[][] var4 = this.at;
      int var15 = this.at.length;

      for (int var12 = 0; var12 < var15; var12++) {
         int[] var8 = var4[var12];
         this.a(new L1Location(var8[0], var8[1], this.e), this.am);
      }

      var4 = this.as;
      var15 = this.as.length;

      for (int var13 = 0; var13 < var15; var13++) {
         int[] var9 = var4[var13];
         if (Random.a(100) <= 50) {
            L1Location var5 = new L1Location(var9[0], var9[1], this.e);
            L1World.a().a(var5).a(41704 + Random.a(10), 1);
         }
      }

      L1DoorInstance[] var19;
      var15 = (var19 = DoorTable.b().c()).length;

      for (int var14 = 0; var14 < var15; var14++) {
         L1DoorInstance var10 = var19[var14];
         if (var10.fp() == 2 && (var10.fs() != 32684 || var10.ft() != 32850)) {
            L1Location var20 = new L1Location(var10.fs(), var10.ft(), this.e);
            L1DoorGfx var6 = L1DoorGfx.a(var10.fe());
            L1DoorInstance var7 = DoorTable.b().a(0, var6, var20, 0, 0, false);
            if (var7.fs() == 32673 && var7.ft() == 32820) {
               var7.f(1);
            } else if ((var7.fs() != 32741 || var7.ft() != 32804) && (var7.fs() != 32740 || var7.ft() != 32788)) {
               if (var7.fs() == 32723 && var7.ft() == 32848) {
                  var7.f(4);
               }
            } else {
               var7.f(2);
            }

            this.p.add(var7);
         }
      }

      for (int var11 = 0; var11 < 10; var11++) {
         this.o.add(DoorTable.b().a(0, this.aj, new L1Location(32702 + var11, 32866, this.e), 0, 1, false));
         this.o.add(DoorTable.b().a(0, this.aj, new L1Location(32703 + var11, 32872, this.e), 0, 1, false));
      }
   }

   private int a(int var1, int var2) throws InterruptedException {
      int var3 = -1;

      while (var3++ < var2) {
         if (this.f < 0 && var2 >= 60) {
            return var2;
         }

         if (this.f > var1) {
            return var3;
         }

         Thread.sleep(1000L);
      }

      return var2;
   }

   private void a(int var1, int[][] var2) throws InterruptedException {
      while (true) {
         int var3 = 0;

         for (L1PcInstance var4 : this.n) {
            int[][] var9 = var2;
            int var8 = var2.length;

            for (int var7 = 0; var7 < var8; var7++) {
               int[] var6 = var9[var7];
               if (var4.fs() == var6[0] && var4.ft() == var6[1]) {
                  var3++;
                  if (var6.equals(this.ap[0])) {
                     this.a(this.j, "$8720", 0);
                  }
               }

               if (var3 == 3) {
                  this.f = -1;
               } else if (var3 == var2.length) {
                  this.f = var1 + 1;
                  this.e();
                  if (var1 > 0) {
                     for (L1DoorInstance var10 : this.p) {
                        if (var10.p() == var1) {
                           var10.f();
                        }
                     }

                     this.a(new L1Location(var6[0], var6[1], this.e), 91418, 1);
                  }

                  return;
               }
            }
         }

         Thread.sleep(2000L);
      }
   }

   private void a(int[][] var1) {
      int[][] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         int[] var2 = var5[var3];
         FieldSpawnTable.a().a(1172, var2[0], var2[1], this.e);
         FieldSpawnTable.a().a(7480, var2[0], var2[1], this.e);
      }
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

   private int a(ArrayList<L1NpcInstance> var1, int var2) throws InterruptedException {
      int var3 = -1;
      boolean var4 = false;

      while (var3++ < var2) {
         boolean var5 = false;

         for (L1NpcInstance var6 : var1) {
            if (!var6.eX()) {
               var5 = false;
               break;
            }

            if ((var6.z() == 91395 || var6.z() == 91395) && !var4) {
               new L1HardinBattle.L1R_a(301, 1000, null).a();
               var4 = true;
            } else if ((var6.z() == 91390 || var6.z() == 91394) && !var4 && (var6.z() == 91395 || var6.z() == 91395)) {
               new L1HardinBattle.L1R_a(302, 1000, null).a();
               var4 = true;
            }

            var5 = var6.eX();
         }

         if (var5) {
            return var3;
         }

         Thread.sleep(1000L);
      }

      this.r = 0;

      for (L1NpcInstance var8 : var1) {
         if (!var8.eX()) {
            this.r++;
         }
      }

      return var2;
   }

   private void d() {
      for (L1PcInstance var1 : this.n) {
         int var3 = 32587 + Random.a(4);
         int var4 = 32941 + Random.a(4);
         L1Teleport.a(var1, var3, var4, 0, 5, true);
      }
   }

   private void a(L1NpcInstance var1, String var2, int var3) {
      String[] var4 = new String[]{"", "\\f=", "\\f3"};

      for (L1PcInstance var5 : this.n) {
         var5.a(new S_PacketBox(84, 2, var4[var3] + var2));
      }

      if (var1 != null) {
         var1.b(new S_NpcChatPacket(var1, var2, 0));
      }
   }

   private void a(String var1, int var2) {
      String[] var3 = new String[]{"", "\\f=", "\\f3"};

      for (L1PcInstance var4 : this.n) {
         if (var4.fr() != this.i.fr()) {
            var4.a(new S_PacketBox(84, 2, var3[var2] + var1));
         }
      }
   }

   private void b(String var1, int var2) {
      this.a = 0;
      String[] var3 = new String[]{"", "\\f=", "\\f3"};
      this.i.a(new S_PacketBox(84, 2, var3[var2] + var1));
   }

   private void e() {
      for (L1PcInstance var1 : this.n) {
         var1.a(new S_PacketBox(83, 2));
      }
   }

   private class L1R_a implements Runnable {
      int a;
      int b;

      private L1R_a(int var2, int var3) {
         this.a = var3;
         this.b = var2;
      }

      private void a() {
         L1HardinBattle.this.m.schedule(this, this.a, TimeUnit.MILLISECONDS);
      }

      @Override
      public void run() {
         int var1 = 0;
         switch (this.b) {
            case 5:
               try {
                  L1HardinBattle.this.e();
                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$7650", 0);
                  Thread.sleep(4000L);
                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$7651", 0);
                  Thread.sleep(4000L);

                  for (int var39 = 1; var39 <= 11; var39++) {
                     ArrayList var48 = new ArrayList<>();
                     Thread.sleep(1000L);
                     int[] var55 = new int[]{8708, 8709, 8710, 8704, 8711, 8712, 8713, 8706, 8714, 8715, 8716};
                     L1HardinBattle.this.a(L1HardinBattle.this.j, "$" + var55[var39 - 1], 0);
                     Thread.sleep(3000L);

                     for (int var58 = 0; var58 < 4; var58++) {
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$" + (8689 + var58), 0);
                        Thread.sleep(1000L);
                        int[] var62 = new int[5];

                        for (int var66 = 0; var66 < var62.length; var66++) {
                           var62[var66] = L1HardinBattle.this.ak[Random.a(L1HardinBattle.this.ak.length)];
                        }

                        var48.addAll(L1HardinBattle.this.a(L1HardinBattle.this.c, var62));
                        Thread.sleep(4000L);
                     }

                     if (var39 == 4) {
                        Thread.sleep(1000L);
                        L1HardinBattle.this.a((L1NpcInstance)null, "$8705", 0);
                        Thread.sleep(3000L);
                        var48.addAll(L1HardinBattle.this.a(L1HardinBattle.this.c, 91391, 1));
                     } else if (var39 == 8) {
                        Thread.sleep(1000L);
                        L1HardinBattle.this.a((L1NpcInstance)null, "$8707", 0);
                        Thread.sleep(3000L);
                        var48.addAll(L1HardinBattle.this.a(L1HardinBattle.this.c, 91393, 1));
                     }

                     int var59 = L1HardinBattle.this.a(var48, 300);
                     if (var59 >= 300) {
                        if (L1HardinBattle.this.r >= 4) {
                           L1HardinBattle.this.a(L1HardinBattle.this.j, "$7653", 0);
                           L1HardinBattle.this.g = L1HardinBattle.this.g + 10;
                           Thread.sleep(1000L);
                        }

                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7811", 0);
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7811", 0));
                        if (L1HardinBattle.this.a(var48, 60) >= 60) {
                           L1HardinBattle.this.a(L1HardinBattle.this.j, "$7681", 0);
                           L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7681", 0));
                           L1HardinBattle.this.d();
                        } else {
                           L1HardinBattle.this.a(L1HardinBattle.this.j, "$8703", 0);
                           L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$8703", 0));
                        }
                     } else if (var59 < 120) {
                        L1HardinBattle.this.g = L1HardinBattle.this.g + 100;
                     } else {
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7652", 0);
                     }
                  }

                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$7654", 0);
                  Thread.sleep(5000L);
                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$8717", 0);
                  L1HardinBattle.this.e();
                  Thread.sleep(2000L);
                  int[] var40 = new int[16];
                  int[] var49 = L1HardinBattle.this.ak;
                  if (L1HardinBattle.this.g > 1000) {
                     L1HardinBattle.this.l = L1HardinBattle.this.a(L1HardinBattle.this.c, 91394, 1).get(0);
                  } else if (L1HardinBattle.this.g > 100 && L1HardinBattle.this.g < 1000) {
                     L1HardinBattle.this.l = L1HardinBattle.this.a(L1HardinBattle.this.c, 91390, 1).get(0);
                  } else {
                     var49 = L1HardinBattle.this.al;
                  }

                  boolean var56 = true;
                  L1HardinBattle.this.l.b(new S_NpcChatPacket(L1HardinBattle.this.l, "$7656", 0));
                  Thread.sleep(1000L);
                  L1HardinBattle.this.l.b(new S_NpcChatPacket(L1HardinBattle.this.l, "$7657", 0));
                  if (!L1HardinBattle.this.d) {
                     L1HardinBattle.this.k = L1HardinBattle.this.a(new L1Location(32711, 32845, L1HardinBattle.this.e), 91396, 1).get(0);
                  } else if (L1HardinBattle.this.g % 10 == 5) {
                     L1HardinBattle.this.k = L1HardinBattle.this.a(new L1Location(32711, 32845, L1HardinBattle.this.e), 91395, 1).get(0);
                  } else {
                     var56 = false;
                  }

                  if (var56) {
                     if (L1HardinBattle.this.k.z() == 91396) {
                        Thread.sleep(1500L);
                        L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7820", 0));
                        Thread.sleep(1500L);
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7821", 0));
                        Thread.sleep(1500L);
                        L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7822", 0));
                        Thread.sleep(1500L);
                        L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7823", 0));
                        Thread.sleep(1500L);
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7824", 0));
                     } else {
                        Thread.sleep(1500L);
                        L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7815", 0));
                        Thread.sleep(1500L);
                        L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7816", 0));
                        Thread.sleep(1500L);
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7817", 0));
                        Thread.sleep(1500L);
                        L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7818", 0));
                        Thread.sleep(1500L);
                        L1HardinBattle.this.l.b(new S_NpcChatPacket(L1HardinBattle.this.l, "$7819", 0));
                        Thread.sleep(1500L);
                     }
                  }

                  for (int var60 = 0; var60 < var40.length; var60++) {
                     var40[var60] = var49[Random.a(L1HardinBattle.this.ak.length)];
                  }

                  ArrayList var61 = L1HardinBattle.this.a(new L1Location(32706, 32836, L1HardinBattle.this.e), var40);
                  if (L1HardinBattle.this.l != null) {
                     var61.add(L1HardinBattle.this.l);
                  }

                  if (var56) {
                     var61.add(L1HardinBattle.this.k);
                  }

                  if (L1HardinBattle.this.a(var61, 900) >= 900) {
                     return;
                  }

                  L1NpcInstance var63 = null;
                  L1HardinBattle.this.e();
                  if (L1HardinBattle.this.g >= 5) {
                     var63 = L1HardinBattle.this.a(new L1Location(32707, 32858, L1HardinBattle.this.e), 91443, 1).get(0);
                     var63.b(new S_NpcChatPacket(var63, "$7663", 0));
                     Thread.sleep(1000L);
                     var63.b(new S_NpcChatPacket(var63, "$7664", 0));
                     Thread.sleep(1000L);
                     var63.b(new S_NpcChatPacket(var63, "$7665", 0));
                     Thread.sleep(1000L);
                     L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7837", 0));
                  } else {
                     var63 = L1HardinBattle.this.a(new L1Location(32707, 32858, L1HardinBattle.this.e), 91444, 1).get(0);
                     var63.b(new S_NpcChatPacket(var63, "$7694", 0));
                     Thread.sleep(1000L);
                     var63.b(new S_NpcChatPacket(var63, "$7695", 0));
                     Thread.sleep(1000L);
                     var63.b(new S_NpcChatPacket(var63, "$7709", 0));
                     Thread.sleep(1000L);
                     L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7677", 0));
                  }

                  Thread.sleep(1000L);
                  L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7838", 0));
                  Thread.sleep(1000L);
                  L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7839", 0));
                  ArrayList var67 = new ArrayList<>();
                  int var8 = 3;

                  for (L1DoorInstance var9 : L1HardinBattle.this.o) {
                     if (var8 % 4 == 0) {
                        L1HardinBattle.this.e();
                     }

                     L1Location var11;
                     do {
                        int var12 = 32702 + Random.a(10);
                        int var13 = 32860 + Random.a(6);
                        var11 = new L1Location(var12, var13, L1HardinBattle.this.e);
                     } while (var67.contains(var11));

                     var67.add(var11);
                     DoorTable.b().a(var9.fu());
                     DoorTable.b().a(0, L1HardinBattle.this.aj, var11, 0, 1, false);
                     Thread.sleep(600L);
                     var8++;
                  }

                  for (int var68 = 0; var68 < L1HardinBattle.this.o.size(); var68++) {
                     DoorTable.b().a(var67.get(var68));
                     DoorTable.b().a(0, L1HardinBattle.this.aj, L1HardinBattle.this.o.get(var68).fu(), 0, 1, false);
                     Thread.sleep(600L);
                  }

                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$8718", 0);
                  int[][] var69 = new int[L1HardinBattle.this.n.size() - 1][2];

                  for (int var70 = 0; var70 < var69.length; var70++) {
                     var69[var70] = L1HardinBattle.this.ar[var70];
                     L1HardinBattle.this.a(new L1Location(var69[var70][0], var69[var70][1], L1HardinBattle.this.e), 91439, 1);
                  }

                  L1HardinBattle.this.a(0, var69);
                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$8719", 0);
                  L1HardinBattle.this.a(new L1Location(32802, 32868, L1HardinBattle.this.e), 91438, 1);
                  L1HardinBattle.this.a(0, new int[][]{{32802, 32868}});
                  int[][] var74 = var69;
                  int var73 = var69.length;

                  for (int var72 = 0; var72 < var73; var72++) {
                     int[] var71 = var74[var72];
                     L1World.a().a(new L1Location(var71[0], var71[1], L1HardinBattle.this.e)).a(41757, 1);
                  }

                  L1World.a().a(new L1Location(32802, 32868, L1HardinBattle.this.e)).a(41757, 1);
                  Thread.sleep(30000L);
                  L1HardinBattle.this.d();
               } catch (Exception var30) {
               }
               break;
            case 6:
               try {
                  FieldSpawnTable.a().a(7572, 32742, 32930, L1HardinBattle.this.e);
                  L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep003"));
                  L1HardinBattle.this.b("$7616", 0);
                  Thread.sleep(6000L);
                  if (L1HardinBattle.this.a == 69) {
                     L1HardinBattle.this.b("$7562", 1);
                  } else if (L1HardinBattle.this.a == 66) {
                     L1HardinBattle.this.b("$7570", 1);
                  } else {
                     L1HardinBattle.this.b("$7617", 1);
                     L1HardinBattle.this.h = L1HardinBattle.this.h + 1;
                  }

                  if (L1HardinBattle.this.a(1, 120) < 120) {
                     L1HardinBattle.this.b("$7622", 2);
                  } else {
                     L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep004"));
                     L1HardinBattle.this.a(L1HardinBattle.this.k, "$7560 : $7618", 2);
                     var1 = L1HardinBattle.this.a(1, 30);
                     if (var1 >= 30) {
                        L1HardinBattle.this.d();
                        return;
                     }

                     if (var1 < 5) {
                        L1HardinBattle.this.g = L1HardinBattle.this.g + 1;
                     }

                     L1HardinBattle.this.b("$7620", 0);
                     Thread.sleep(6000L);
                     if (L1HardinBattle.this.a == 69) {
                        L1HardinBattle.this.b("$7571", 1);
                     } else if (L1HardinBattle.this.a == 66) {
                        L1HardinBattle.this.b("$7563", 1);
                     } else {
                        L1HardinBattle.this.b("$7619", 1);
                        L1HardinBattle.this.h = L1HardinBattle.this.h + 1;
                     }
                  }

                  Thread.sleep(3000L);
                  L1HardinBattle.this.new L1R_a(304, 0).a();
                  L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep005"));
                  L1HardinBattle.this.b("$7623", 0);
                  Thread.sleep(6000L);
                  if (L1HardinBattle.this.a == 69) {
                     L1HardinBattle.this.b("$7564", 1);
                  } else if (L1HardinBattle.this.a == 66) {
                     L1HardinBattle.this.b("$7572", 1);
                  } else {
                     L1HardinBattle.this.b("$7624", 1);
                     L1HardinBattle.this.h = L1HardinBattle.this.h + 1;
                  }

                  Thread.sleep(3000L);
                  L1HardinBattle.this.b("$7639", 2);
                  L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), 91381, 6);
                  if (L1HardinBattle.this.a(2, 120) >= 120) {
                     L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep004"));
                     L1HardinBattle.this.a(L1HardinBattle.this.k, "$7560 : $7625", 2);
                     var1 = L1HardinBattle.this.a(2, 30);
                     if (var1 >= 30) {
                        L1HardinBattle.this.d();
                        return;
                     }

                     if (var1 < 5) {
                        L1HardinBattle.this.g = L1HardinBattle.this.g + 1;
                     }
                  }

                  L1HardinBattle.this.b("$7627", 0);
                  Thread.sleep(6000L);
                  if (L1HardinBattle.this.a == 69) {
                     L1HardinBattle.this.b("$7571", 1);
                  } else if (L1HardinBattle.this.a == 66) {
                     L1HardinBattle.this.b("$7563", 1);
                  } else {
                     L1HardinBattle.this.b("$7626", 1);
                     L1HardinBattle.this.h = L1HardinBattle.this.h + 1;
                  }

                  Thread.sleep(3000L);
                  L1HardinBattle.this.b("$7629", 2);
                  L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep006"));
                  Thread.sleep(3000L);
                  L1HardinBattle.this.b("$7639", 2);
                  L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), 91381, 6);
                  Thread.sleep(3000L);
                  L1HardinBattle.this.b("$7630", 0);
                  Thread.sleep(6000L);
                  if (L1HardinBattle.this.a == 69) {
                     L1HardinBattle.this.b("$7565", 1);
                  } else if (L1HardinBattle.this.a == 66) {
                     L1HardinBattle.this.b("$7573", 1);
                  } else {
                     L1HardinBattle.this.b("$7631", 1);
                     L1HardinBattle.this.h = L1HardinBattle.this.h + 1;
                  }

                  if (L1HardinBattle.this.a(3, 120) >= 120) {
                     L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep004"));
                     L1HardinBattle.this.a(L1HardinBattle.this.k, "$7560 : $7632", 2);
                     var1 = L1HardinBattle.this.a(3, 30);
                     if (var1 >= 30) {
                        L1HardinBattle.this.d();
                        return;
                     }

                     if (var1 < 5) {
                        L1HardinBattle.this.g = L1HardinBattle.this.g + 1;
                     }
                  }

                  L1HardinBattle.this.b("$7634", 0);
                  Thread.sleep(6000L);
                  if (L1HardinBattle.this.a == 69) {
                     L1HardinBattle.this.b("$7563", 1);
                  } else if (L1HardinBattle.this.a == 66) {
                     L1HardinBattle.this.b("$7571", 1);
                  } else {
                     L1HardinBattle.this.b("$7633", 1);
                     L1HardinBattle.this.h = L1HardinBattle.this.h + 1;
                  }

                  Thread.sleep(6000L);
                  L1HardinBattle.this.b("$7636", 2);
                  Thread.sleep(6000L);
                  L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep007"));
                  L1HardinBattle.this.b("$7637", 0);
                  Thread.sleep(6000L);
                  if (L1HardinBattle.this.a == 69) {
                     L1HardinBattle.this.b("$7566", 1);
                  } else if (L1HardinBattle.this.a == 66) {
                     L1HardinBattle.this.b("$7574", 1);
                  } else {
                     L1HardinBattle.this.b("$7638", 1);
                     L1HardinBattle.this.h = L1HardinBattle.this.h + 1;
                  }

                  Thread.sleep(3000L);
                  L1HardinBattle.this.b("$7639", 2);
                  L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), 91381, 6);
                  if (L1HardinBattle.this.a(4, 120) >= 120) {
                     L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep004"));
                     L1HardinBattle.this.a(L1HardinBattle.this.k, "$7560 : $7640", 2);
                     Thread.sleep(3000L);
                  }

                  L1HardinBattle.this.b("$7642", 0);
                  Thread.sleep(6000L);
                  if (L1HardinBattle.this.a == 69) {
                     L1HardinBattle.this.b("$7563", 1);
                  } else if (L1HardinBattle.this.a == 66) {
                     L1HardinBattle.this.b("$7571", 1);
                  } else {
                     L1HardinBattle.this.b("$7641", 1);
                     L1HardinBattle.this.h = L1HardinBattle.this.h + 1;
                  }

                  L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep008"));
                  L1HardinBattle.this.b("$7643", 0);
                  Thread.sleep(6000L);
                  if (L1HardinBattle.this.a == 69) {
                     L1HardinBattle.this.b("$7567", 1);
                  } else if (L1HardinBattle.this.a == 66) {
                     L1HardinBattle.this.b("$7575", 1);
                  } else {
                     L1HardinBattle.this.b("$7646", 1);
                     L1HardinBattle.this.h = L1HardinBattle.this.h + 1;
                  }

                  if (L1HardinBattle.this.h >= 9) {
                     L1HardinBattle.this.b("$7644", 2);
                     Thread.sleep(6000L);
                     if (L1HardinBattle.this.a == 69) {
                        L1HardinBattle.this.d = true;
                        L1HardinBattle.this.g = L1HardinBattle.this.g + 1;
                        L1HardinBattle.this.b("$7568", 1);
                     } else {
                        L1HardinBattle.this.b("$7645", 1);
                     }
                  }

                  Thread.sleep(2500L);
                  L1Teleport.a(L1HardinBattle.this.i, 32718, 32849, L1HardinBattle.this.e, 5, true);
               } catch (InterruptedException var27) {
               }
               break;
            case 7:
               try {
                  L1HardinBattle.this.a(L1HardinBattle.this.ao);
                  L1HardinBattle.this.a(1, L1HardinBattle.this.ao);
                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$7597 : $7621", 2);
                  L1HardinBattle.this.a(L1HardinBattle.this.ap);
                  L1HardinBattle.this.a(2, L1HardinBattle.this.ap);
                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$7597 : $7628", 2);
                  L1HardinBattle.this.a(L1HardinBattle.this.aq);
                  L1HardinBattle.this.a(3, L1HardinBattle.this.aq);
                  Thread.sleep(2000L);

                  for (L1PcInstance var37 : L1HardinBattle.this.n) {
                     if (var37.fr() != L1HardinBattle.this.i.fr()) {
                        L1Teleport.a(var37, 32796, 32848, L1HardinBattle.this.e, 5, true);
                     }
                  }

                  Thread.sleep(2000L);
                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$7597 : $7635", 2);
                  ArrayList var38 = new ArrayList<>();
                  var38.addAll(L1HardinBattle.this.a(new L1Location(32775, 32846, L1HardinBattle.this.e), 45107, 8));
                  var38.addAll(L1HardinBattle.this.a(new L1Location(32775, 32846, L1HardinBattle.this.e), 45130, 8));
                  if (L1HardinBattle.this.a(var38, 120) >= 120) {
                     L1HardinBattle.this.d();
                     return;
                  }

                  for (L1DoorInstance var46 : L1HardinBattle.this.p) {
                     if (var46.p() == 4) {
                        var46.f();
                     }
                  }

                  L1HardinBattle.this.g = L1HardinBattle.this.g + 1;
                  L1HardinBattle.this.f = 5;
                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$7647", 0);
                  Thread.sleep(5000L);
                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$7648", 0);
                  Thread.sleep(15000L);

                  for (L1DoorInstance var47 : L1HardinBattle.this.p) {
                     if (var47.o() == 28) {
                        var47.g();
                     }
                  }

                  L1HardinBattle.this.a(L1HardinBattle.this.j, "$7649", 0);
                  L1HardinBattle.this.new L1R_a(5, 5000).a();
               } catch (InterruptedException var31) {
               }
               break;
            case 10:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$7598", 0);
               L1HardinBattle.this.new L1R_a(11, 4000).a();
               break;
            case 11:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8693", 0);
               L1HardinBattle.this.new L1R_a(12, 8000).a();
               break;
            case 12:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8694", 0);
               if (L1HardinBattle.this.a == 69) {
                  L1HardinBattle.this.new L1R_a(20, 8000).a();
               } else {
                  L1HardinBattle.this.new L1R_a(13, 8000).a();
               }
               break;
            case 13:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8695", 0);
               L1HardinBattle.this.new L1R_a(14, 8000).a();
               break;
            case 14:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8696", 0);
               L1HardinBattle.this.new L1R_a(15, 8000).a();
               break;
            case 15:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8697", 0);
               L1HardinBattle.this.new L1R_a(16, 8000).a();
               break;
            case 16:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8698", 0);
               L1HardinBattle.this.new L1R_a(17, 8000).a();
               break;
            case 17:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8699", 0);
               L1HardinBattle.this.new L1R_a(18, 8000).a();
               break;
            case 18:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8700", 0);
               L1HardinBattle.this.new L1R_a(19, 8000).a();
               break;
            case 19:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8701", 0);
               L1HardinBattle.this.new L1R_a(20, 8000).a();
               break;
            case 20:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$8702", 0);
               L1HardinBattle.this.new L1R_a(21, 6000).a();
               break;
            case 21:
               int var2 = 4000;
               if (L1HardinBattle.this.a != 69) {
                  L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep002"));
                  var2 = 10000;
               }

               L1HardinBattle.this.new L1R_a(22, var2).a();
               break;
            case 22:
               L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep001"));
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$7599", 0);
               L1HardinBattle.this.new L1R_a(23, 8000).a();
               break;
            case 23:
               try {
                  if (L1HardinBattle.this.a != 69) {
                     L1HardinBattle.this.a(L1HardinBattle.this.j, "$7601", 0);
                     Thread.sleep(5000L);

                     for (int var44 = 0; var44 < 5 && L1HardinBattle.this.a != 69; var44++) {
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7602", 0);
                        Thread.sleep(5000L);
                     }
                  }

                  L1HardinBattle.this.new L1R_a(24, 8000).a();
               } catch (InterruptedException var29) {
               }
               break;
            case 24:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$7600", 0);
               L1HardinBattle.this.new L1R_a(25, 8000).a();
               break;
            case 25:
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$7603", 0);
               L1HardinBattle.this.new L1R_a(26, 2000).a();
               break;
            case 26:
               L1Teleport.a(L1HardinBattle.this.i, 32738, 32930, L1HardinBattle.this.e, 5, true);
               L1HardinBattle.this.a(L1HardinBattle.this.j, "$7604", 0);
               L1HardinBattle.this.new L1R_a(101, 1000).a();
               L1HardinBattle.this.new L1R_a(201, 1000).a();
               break;
            case 101:
               L1HardinBattle.this.a("$7605", 0);
               L1HardinBattle.this.new L1R_a(102, 4000).a();
               break;
            case 102:
               L1HardinBattle.this.a("$7606", 0);
               L1HardinBattle.this.new L1R_a(103, 4000).a();
               break;
            case 103:
               L1HardinBattle.this.j.a(32716, 32846, 6);
               L1HardinBattle.this.new L1R_a(104, 4000).a();
               break;
            case 104:
               for (L1PcInstance var43 : L1HardinBattle.this.n) {
                  if (var43.fr() != L1HardinBattle.this.i.fr()) {
                     L1Teleport.a(var43, 32665, 32793, L1HardinBattle.this.e, 5, true);
                  }
               }

               L1HardinBattle.this.a("$7611", 0);
               L1HardinBattle.this.new L1R_a(105, 2000).a();
               break;
            case 105:
               L1HardinBattle.this.a("$7613", 0);
               L1HardinBattle.this.new L1R_a(106, 5000).a();
               break;
            case 106:
               L1HardinBattle.this.a("$7615", 0);
               L1HardinBattle.this.e();
               break;
            case 201:
               L1HardinBattle.this.b("$7607", 0);
               L1HardinBattle.this.new L1R_a(202, 4000).a();
               break;
            case 202:
               L1HardinBattle.this.b("$7608", 0);
               L1HardinBattle.this.new L1R_a(203, 4000).a();
               break;
            case 203:
               L1HardinBattle.this.b("$7609", 0);
               L1HardinBattle.this.new L1R_a(204, 5000).a();
               break;
            case 204:
               L1HardinBattle.this.b("$7610", 0);
               L1HardinBattle.this.new L1R_a(205, 5000).a();
               break;
            case 205:
               if (L1HardinBattle.this.a == 69) {
                  L1HardinBattle.this.b("$7561", 1);
               } else if (L1HardinBattle.this.a == 66) {
                  L1HardinBattle.this.b("$7569", 1);
               } else {
                  L1HardinBattle.this.b("$7612", 1);
               }

               L1HardinBattle.this.new L1R_a(206, 6000).a();
               break;
            case 206:
               L1HardinBattle.this.b("$7614", 2);
               L1HardinBattle.this.new L1R_a(6, 3000).a();
               L1HardinBattle.this.new L1R_a(7, 3000).a();
               L1HardinBattle.this.new L1R_a(303, 3000).a();
               break;
            case 301:
               try {
                  L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7833", 0));
                  Thread.sleep(2000L);
                  L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7834", 0));
               } catch (InterruptedException var26) {
               }
               break;
            case 302:
               try {
                  L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7827", 0));
                  Thread.sleep(1000L);
                  L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7828", 0));
                  Thread.sleep(1800L);
                  L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7829", 0));
                  Thread.sleep(1800L);
                  L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7830", 0));
                  Thread.sleep(1000L);
                  L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7831", 0));
                  Thread.sleep(1500L);
                  L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7832", 0));
               } catch (InterruptedException var25) {
               }
               break;
            case 303:
               try {
                  int var42 = 0;

                  while (L1HardinBattle.this.k.z() == 91397) {
                     L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$" + (7576 + Random.a(12)), 0));
                     if (L1HardinBattle.this.f <= 3) {
                        if (var42 % 12 == 0) {
                           if (Random.a(100) <= 66) {
                              L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), L1HardinBattle.this.an);
                           } else {
                              L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), 45278, 3);
                              L1HardinBattle.this.b("$7596", 0);
                           }
                        }

                        if (var42 % (1 + Random.a(2)) == 0) {
                           for (L1Object var51 : L1World.a().b(L1HardinBattle.this.k, 6)) {
                              if (var51 instanceof L1MonsterInstance) {
                                 ((L1MonsterInstance)var51).b(new S_SkillSound(var51.fr(), 7398));
                              }
                           }
                        }
                     }

                     var42 += 10;
                     Thread.sleep(10000L);
                  }
               } catch (Exception var28) {
               }
               break;
            case 304:
               try {
                  L1NpcInstance var41 = L1HardinBattle.this.a(new L1Location(32729, 32917, L1HardinBattle.this.e), 91433, 1).get(0);
                  L1NpcInstance var50 = L1HardinBattle.this.a(new L1Location(32732, 32916, L1HardinBattle.this.e), 91436, 1).get(0);
                  L1NpcInstance var5 = L1HardinBattle.this.a(new L1Location(32730, 32920, L1HardinBattle.this.e), 91434, 1).get(0);
                  L1NpcInstance var6 = L1HardinBattle.this.a(new L1Location(32732, 32919, L1HardinBattle.this.e), 91435, 1).get(0);
                  ArrayList var7 = new ArrayList<>();
                  var7.add(var41);
                  var7.add(var50);
                  var7.add(var5);
                  var7.add(var6);
                  L1HardinBattle.this.a(var7, 3, 15);
                  Thread.sleep(2000L);
                  var6.b(new S_NpcChatPacket(var6, "$7842", 0));
                  Thread.sleep(2000L);
                  var41.b(new S_NpcChatPacket(var41, "$7848", 0));
                  Thread.sleep(2000L);
                  var50.b(new S_NpcChatPacket(var50, "$7854", 0));
                  Thread.sleep(2000L);
                  var41.b(new S_NpcChatPacket(var41, "$7849", 0));
                  Thread.sleep(2000L);
                  var50.b(new S_NpcChatPacket(var50, "$7855", 0));
                  Thread.sleep(2000L);
                  var6.b(new S_NpcChatPacket(var6, "$7843", 0));
                  Thread.sleep(2000L);
                  var50.b(new S_NpcChatPacket(var50, "$7856", 0));
                  Thread.sleep(2000L);
                  var6.b(new S_NpcChatPacket(var6, "$7844", 0));
                  Thread.sleep(2000L);
                  var50.b(new S_NpcChatPacket(var50, "$7857", 0));
                  Thread.sleep(2000L);
                  var5.b(new S_NpcChatPacket(var5, "$7851", 0));
                  Thread.sleep(2000L);
                  var50.b(new S_NpcChatPacket(var50, "$7858", 0));
                  Thread.sleep(2000L);
                  var5.b(new S_NpcChatPacket(var5, "$7852", 0));
                  Thread.sleep(2000L);
                  var50.b(new S_NpcChatPacket(var50, "$7859", 0));
                  Thread.sleep(2000L);
                  var5.b(new S_NpcChatPacket(var5, "$7853", 0));
                  Thread.sleep(2000L);
                  var41.b(new S_NpcChatPacket(var41, "$7850", 0));
                  Thread.sleep(2000L);
                  var6.b(new S_NpcChatPacket(var6, "$7845", 0));
                  Thread.sleep(4000L);
                  var6.b(new S_NpcChatPacket(var6, "$7846", 0));
                  Thread.sleep(4000L);
                  var6.b(new S_NpcChatPacket(var6, "$7847", 0));
                  Thread.sleep(4000L);
                  L1HardinBattle.this.a(var7, 1, 20);
               } catch (InterruptedException var24) {
               }
               break;
            case 999:
               try {
                  while (!L1HardinBattle.this.n.isEmpty()) {
                     for (L1PcInstance var3 : L1HardinBattle.this.n) {
                        if (var3.bE() == 0 || var3.fp() != L1HardinBattle.this.e) {
                           L1HardinBattle.this.n.remove(var3);
                           if (var3.am()) {
                              L1HardinBattle.this.d();
                           }
                        }
                     }

                     Thread.sleep(3000L);
                  }
               } catch (Exception var32) {
               } finally {
                  System.out.println("[副本結束]:哈汀副本");
                  L1HardinBattle.this.b();
               }
         }
      }

      // $VF: synthetic method
      L1R_a(int var2, int var3, L1HardinBattle.L1R_a var4) {
         this(var2, var3);
      }
   }
}
