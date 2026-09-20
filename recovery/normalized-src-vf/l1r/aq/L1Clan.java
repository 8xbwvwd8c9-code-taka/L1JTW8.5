package l1r.aq;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.au.L1ClanInventory;

public class L1Clan {
   public static final int a = 2;
   public static final int b = 3;
   public static final int c = 4;
   public static final int d = 5;
   public static final int e = 6;
   public static final int f = 12;
   public static final int g = 7;
   public static final int h = 8;
   public static final int i = 9;
   public static final int j = 10;
   public static final int k = 13;
   private int l = 0;
   private String m;
   private Timestamp n;
   private String o;
   private int p = 0;
   private int q = 0;
   private int r = 0;
   private String s;
   private int t = 0;
   private int u = 0;
   private int v = 0;
   private final CopyOnWriteArrayList<String> w = new CopyOnWriteArrayList<>();
   private final L1ClanInventory x = new L1ClanInventory(this);
   private int y = 1;
   private int z = 0;
   private byte[] A = new byte[20];
   private final CopyOnWriteArrayList<Integer> B = new CopyOnWriteArrayList<>();

   public static boolean a(int var0) {
      return var0 >= 3 && var0 <= 13 && var0 != 7;
   }

   public static boolean b(int var0) {
      int[] var1 = new int[]{10, 9, 4, 3, 6};
      int[] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         int var2 = var5[var3];
         if (var0 == var2) {
            return true;
         }
      }

      return false;
   }

   public boolean a() {
      return this.t != 0;
   }

   public void a(String var1) {
      if (!this.w.contains(var1)) {
         this.w.add(var1);
      }
   }

   public void b(String var1) {
      if (this.w.contains(var1)) {
         this.w.remove(var1);
      }
   }

   public ArrayList<L1PcInstance> b() {
      ArrayList var1 = new ArrayList<>();

      for (String var2 : this.w) {
         L1PcInstance var4 = L1World.a().a(var2);
         if (var4 != null && !var1.contains(var4)) {
            var1.add(var4);
         }
      }

      return var1;
   }

   public L1PcInstance c(String var1) {
      return this.w.contains(var1) ? L1World.a().a(var1) : null;
   }

   public L1ClanInventory c() {
      return this.x;
   }

   public void d(String var1) {
      if (var1 != null) {
         String[] var2 = var1.split(",");
         String[] var6 = var2;
         int var5 = var2.length;

         for (int var4 = 0; var4 < var5; var4++) {
            String var3 = var6[var4];
            if (var3.trim().length() != 0) {
               int var7 = Integer.parseInt(var3);
               if (!this.B.contains(var7)) {
                  this.B.add(var7);
               }
            }
         }
      }
   }

   public String d() {
      String var1 = "";

      for (int var2 : this.B) {
         if (ClanTable.a().a(var2) != null) {
            var1 = var1 + var2 + ",";
         }
      }

      return var1;
   }

   public int e() {
      return this.l;
   }

   public void c(int var1) {
      this.l = var1;
   }

   public String f() {
      return this.m;
   }

   public void e(String var1) {
      this.m = var1;
   }

   public Timestamp g() {
      return this.n;
   }

   public void a(Timestamp var1) {
      this.n = var1;
   }

   public String h() {
      return this.o;
   }

   public void f(String var1) {
      this.o = var1;
   }

   public int i() {
      return this.p;
   }

   public void d(int var1) {
      this.p = var1;
   }

   public int j() {
      return this.q;
   }

   public void e(int var1) {
      this.q = var1;
   }

   public int k() {
      return this.r;
   }

   public void f(int var1) {
      this.r = var1;
   }

   public String l() {
      return this.s;
   }

   public void g(String var1) {
      this.s = var1;
   }

   public int m() {
      return this.t;
   }

   public void g(int var1) {
      this.t = var1;
   }

   public int n() {
      return this.u;
   }

   public void h(int var1) {
      this.u = var1;
   }

   public int o() {
      return this.v;
   }

   public void i(int var1) {
      this.v = var1;
   }

   public CopyOnWriteArrayList<String> p() {
      return this.w;
   }

   public int q() {
      return this.y;
   }

   public void j(int var1) {
      this.y = var1;
   }

   public int r() {
      return this.z;
   }

   public void k(int var1) {
      this.z = var1;
   }

   public byte[] s() {
      return this.A;
   }

   public void a(byte[] var1) {
      this.A = var1;
   }

   public CopyOnWriteArrayList<Integer> t() {
      return this.B;
   }
}
