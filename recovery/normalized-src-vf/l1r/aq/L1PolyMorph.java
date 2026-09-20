package l1r.aq;

import java.util.HashMap;
import l1r.am.ListSprReader__obf_c;
import l1r.ao.PolyTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_ChangeShape;
import l1r.be.S_CharVisualUpdate;
import l1r.be.S_NpcChangeShape;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;

public class L1PolyMorph {
   private static final int g = 1;
   private static final int h = 2;
   private static final int i = 4;
   private static final int j = 8;
   private static final int k = 16;
   private static final int l = 32;
   private static final int m = 64;
   private static final int n = 128;
   private static final int o = 256;
   private static final int p = 512;
   private static final int q = 1024;
   private static final int r = 1;
   private static final int s = 2;
   private static final int t = 4;
   private static final int u = 8;
   private static final int v = 16;
   private static final int w = 32;
   private static final int x = 64;
   private static final int y = 128;
   private static final int z = 256;
   private static final int A = 512;
   private static final int B = 1024;
   private static final int C = 2048;
   private static final int D = 4096;
   public static final int a = 1;
   public static final int b = 2;
   public static final int c = 4;
   public static final int d = 8;
   public static final int e = 0;
   private static final HashMap<Integer, Integer> E = new HashMap<>();
   private static final HashMap<Integer, Integer> F = new HashMap<>();
   private final int G;
   private final int H;
   private final int I;
   private final int J;
   private final boolean K;
   private final int L;
   public static int[] f = new int[]{
      29,
      945,
      947,
      979,
      1037,
      1039,
      3860,
      3861,
      3862,
      3863,
      3864,
      3865,
      3904,
      3906,
      95,
      146,
      2374,
      2376,
      2377,
      2378,
      3866,
      3867,
      3868,
      3869,
      3870,
      3871,
      3872,
      3873,
      3874,
      3875,
      3876
   };

   static {
      E.put(1, 2);
      E.put(7, 1);
      E.put(257, 4);
      E.put(259, 256);
      E.put(261, 16);
      E.put(2, 8);
      E.put(6, 32);
      E.put(9, 256);
      E.put(264, 128);
      E.put(260, 64);
      E.put(3, 256);
      E.put(5, 16);
      E.put(258, 8);
      E.put(262, 32);
      E.put(8, 512);
      E.put(266, 1024);
      E.put(264, 512);
      F.put(1, 1);
      F.put(2, 16);
      F.put(3, 8);
      F.put(4, 32);
      F.put(7, 256);
      F.put(6, 1024);
      F.put(8, 128);
      F.put(11, 2);
      F.put(9, 512);
      F.put(12, 64);
      F.put(13, 4);
      F.put(10, 2048);
      F.put(5, 4096);
   }

   public L1PolyMorph(int var1, int var2, int var3, int var4, boolean var5, int var6) {
      this.G = var1;
      this.H = var2;
      this.I = var3;
      this.J = var4;
      this.K = var5;
      this.L = var6;
   }

   public int a() {
      return this.G;
   }

   public int b() {
      return this.H;
   }

   public int c() {
      return this.I;
   }

   public int d() {
      return this.J;
   }

   public boolean e() {
      return this.K;
   }

   public int f() {
      return this.L;
   }

   public static boolean a(L1PcInstance var0, String var1, int var2) {
      if (var1.equals("") && var0.fe() != 6034 && var0.fe() != 6035) {
         b(var0);
         return true;
      } else {
         L1PolyMorph var3 = PolyTable.a().a(var1);
         if (var3 == null) {
            var0.a(new S_ServerMessage(181));
            return false;
         } else if (var0.ev() < var3.b() && !var0.l()) {
            var0.a(new S_ServerMessage(181));
            return false;
         } else {
            return a(var0, var3.a(), var2, 1);
         }
      }
   }

   public static boolean a(L1Character var0, int var1, int var2, int var3) {
      if (var0 != null && !var0.eX()) {
         if (var0 instanceof L1PcInstance) {
            L1PcInstance var4 = (L1PcInstance)var0;
            if (!var4.fq().r()) {
               var4.a(new S_ServerMessage(1170));
               return false;
            }

            if (var4.fe() == 6034 || var4.fe() == 6035 || !c(var1, var3)) {
               var4.a(new S_ServerMessage(181));
               return false;
            }

            var4.bz(1006);
            var4.bz(1031);
            var4.bz(1037);
            var4.bA(67);
            var4.j(67, var2 * 1000);
            if (var4.fe() != var1) {
               var4.cw(var1);
               var4.a(new S_ChangeShape(var4.fr(), var1, var4.k()));
               if (!var4.aA()) {
                  if (var4.ff()) {
                     var4.c(new S_ChangeShape(var4.fr(), var1, var4.k()));
                  } else {
                     var4.b(new S_ChangeShape(var4.fr(), var1, var4.k()));
                  }
               }

               var4.j().l(var1);
            }

            if (var2 > 0) {
               var4.a(new S_PacketBox(35, var2));
            }
         } else if (var0 instanceof L1MonsterInstance) {
            L1MonsterInstance var6 = (L1MonsterInstance)var0;
            var6.bA(67);
            var6.j(67, var2 * 1000);
            if (var6.fe() != var1) {
               var6.cw(var1);
               int var5 = 0;
               byte var7;
               if (ListSprReader__obf_c.a().b(var1, 21)) {
                  var7 = 20;
                  var6.x(10);
                  var6.b_(66);
               } else if (ListSprReader__obf_c.a().b(var1, 25)) {
                  var7 = 24;
                  var6.x(2);
                  var6.b_(0);
               } else if (ListSprReader__obf_c.a().b(var1, 17)) {
                  var7 = 0;
                  var6.x(2);
                  var6.b_(0);
               } else if (ListSprReader__obf_c.a().b(var1, 5)) {
                  var7 = 4;
                  var6.x(1);
                  var6.b_(0);
               } else {
                  var7 = 0;
                  var6.x(1);
                  var6.b_(0);
               }

               var6.cq(var7);
               var6.m(ListSprReader__obf_c.a().a(var1, var6.eY()));
               var6.n(ListSprReader__obf_c.a().a(var1, var6.eY() + 1));
               var6.b(new S_NpcChangeShape(var6.fr(), var1, var6.fa(), var6.eY()));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static void a(L1Character var0, int var1) {
      if (var0 != null && !var0.eX()) {
         if (var0 instanceof L1PcInstance) {
            L1PcInstance var2 = (L1PcInstance)var0;
            int[] var3 = new int[]{11479, 11427, 10047, 9688, 11322, 10069, 10034, 10032};
            if (var2.fe() != var3[var1 - 1]) {
               var2.cw(var3[var1 - 1]);
               var2.a(new S_ChangeShape(var2.fr(), var3[var1 - 1], 70));
               if (!var2.aA()) {
                  if (var2.ff()) {
                     var2.c(new S_ChangeShape(var2.fr(), var3[var1 - 1], 70));
                  } else {
                     var2.b(new S_ChangeShape(var2.fr(), var3[var1 - 1], 70));
                  }
               }

               var2.j().l(var3[var1 - 1]);
            }

            var2.a(new S_CharVisualUpdate(var2, 70));
            var2.b(new S_CharVisualUpdate(var2, 70));
         }
      }
   }

   public static void a(L1Character var0) {
      if (var0 instanceof L1PcInstance) {
         L1PcInstance var1 = (L1PcInstance)var0;
         int var2 = var1.aB();
         var1.cw(var2);
         if (!var1.eX()) {
            var1.a(new S_ChangeShape(var1.fr(), var2, var1.k()));
            var1.b(new S_ChangeShape(var1.fr(), var2, var1.k()));
            var1.a(new S_CharVisualUpdate(var1, var1.k()));
            var1.b(new S_CharVisualUpdate(var1, var1.k()));
         }
      }
   }

   public static void b(L1Character var0) {
      if (var0 instanceof L1PcInstance) {
         L1PcInstance var1 = (L1PcInstance)var0;
         int var2 = var1.aB();
         int var3 = var1.fe();
         var1.cw(var2);
         if (!var1.eX()) {
            var1.a(new S_ChangeShape(var1.fr(), var2, var1.k()));
            var1.b(new S_ChangeShape(var1.fr(), var2, var1.k()));
            var1.a(new S_CharVisualUpdate(var1));
            var1.b(new S_CharVisualUpdate(var1));
         }
      } else if (var0 instanceof L1MonsterInstance) {
         L1MonsterInstance var4 = (L1MonsterInstance)var0;
         int var5 = var4.G();
         var4.cw(var5);
         var4.cq(ListSprReader__obf_c.a().a(var4));
         var4.x(-1);
         var4.b_(0);
         var4.m(ListSprReader__obf_c.a().a(var5, var4.eY()));
         var4.n(ListSprReader__obf_c.a().a(var5, var4.eY() + 1));
         var4.b(new S_NpcChangeShape(var4.fr(), var5, var4.fa(), var4.eY()));
      }
   }

   public static boolean a(int var0, int var1) {
      L1PolyMorph var2 = PolyTable.a().a(var0);
      if (var2 == null) {
         return true;
      }

      Integer var3 = E.get(var1);
      return var3 != null ? (var2.c() & var3) != 0 : true;
   }

   public static boolean b(int var0, int var1) {
      L1PolyMorph var2 = PolyTable.a().a(var0);
      if (var2 == null) {
         return true;
      }

      Integer var3 = F.get(var1);
      return var3 != null ? (var2.d() & var3) != 0 : true;
   }

   private static boolean c(int var0, int var1) {
      L1PolyMorph var2 = PolyTable.a().a(var0);
      if (var2 == null) {
         return true;
      } else {
         return var1 == 0 ? true : (var2.f() & var1) != 0;
      }
   }
}
