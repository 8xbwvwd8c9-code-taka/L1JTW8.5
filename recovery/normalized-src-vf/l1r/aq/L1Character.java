package l1r.aq;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ap.L1DollInstance;
import l1r.ap.L1FollowerInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.au.L1Inventory;
import l1r.av.L1ItemDelay;
import l1r.az.L1Poison;
import l1r.be.S_Light;
import l1r.be.S_PetCtrlMenu;
import l1r.be.S_Poison;
import l1r.be.S_RemoveObject;
import l1r.be.ServerBasePacket;
import l1r.bg.L1SkillTimerCreator;
import l1r.bg.L1SkillTimer__obf_d;
import l1r.bi.CalcStat;
import l1r.bi.IntRange;
import l1r.bi.Point;

public class L1Character extends L1Object {
   private L1Poison a = null;
   private boolean b;
   private boolean c;
   private final ConcurrentHashMap<Integer, L1NpcInstance> d = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<Integer, L1DollInstance> e = new ConcurrentHashMap<>();
   private final HashMap<Integer, L1SkillTimer__obf_d> f = new HashMap<>();
   private final ConcurrentHashMap<Integer, L1ItemDelay.L1R_a> g = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<Integer, L1FollowerInstance> h = new ConcurrentHashMap<>();
   private int i;
   private int j;
   private L1Paralysis k;
   private boolean l = false;
   private int m;
   private final CopyOnWriteArrayList<L1Object> n = new CopyOnWriteArrayList<>();
   private final CopyOnWriteArrayList<L1PcInstance> o = new CopyOnWriteArrayList<>();
   private String p;
   private int q;
   private short r = 0;
   private int s = 0;
   private short t = 0;
   private int u = 0;
   private int v = 10;
   private byte w = 0;
   private short y = 0;
   private byte z = 0;
   private short A = 0;
   private byte B = 0;
   private short C = 0;
   private byte D = 0;
   private short E = 0;
   private byte F = 0;
   private short G = 0;
   private byte H = 0;
   private short I = 0;
   private int J = 0;
   private int K = 0;
   private int L = 0;
   private int M = 0;
   private int N = 0;
   private int O = 0;
   private int P = 0;
   private int Q = 0;
   private int R;
   private int S = 0;
   private int T = 0;
   private int U = 0;
   private int V = 0;
   private int W = 0;
   private int X = 0;
   private int Y = 0;
   private int Z = 0;
   private int aa = 0;
   private int ab = 0;
   private int ac = 0;
   private int ad = 0;
   private int ae = 0;
   private int af = 0;
   private int ag = 0;
   private int ah = 0;
   private int ai = 0;
   private int aj = 0;
   private int ak = 0;
   private int al = 0;
   private int am = 0;
   private int an = 0;
   protected int x = 0;
   private int ao = 0;
   private boolean ap = false;
   private int aq = 0;
   private String ar = "";
   private int as = 0;
   private int at = 0;
   private int au = 0;
   private int av;
   private int aw;
   private int ax;
   private int ay;
   private int az;
   private int aA;
   private int aB = 0;
   private int aC = 0;
   private int aD;
   private int aE = 0;

   public L1Character() {
      this.q = 1;
   }

   public void j(int var1) {
      if (this.eX()) {
         if (var1 <= 0) {
            var1 = 1;
         }

         this.a(var1);
         this.X(false);
         this.cq(0);
         L1PolyMorph.b(this);

         for (L1PcInstance var2 : L1World.a().f(this)) {
            var2.a(new S_RemoveObject(this));
            var2.d(this);
            var2.h();
         }
      }
   }

   public int ea() {
      return this.i;
   }

   public void a(int var1) {
      this.i = var1;
      if (this.i >= this.ew()) {
         this.i = this.ew();
      }
   }

   public void bx(int var1) {
      this.i = var1;
   }

   public int eb() {
      return this.j;
   }

   public void i_(int var1) {
      this.j = var1;
      if (this.j >= this.ex()) {
         this.j = this.ex();
      }
   }

   public void by(int var1) {
      this.j = var1;
   }

   public boolean ec() {
      return this.c;
   }

   public void U(boolean var1) {
      this.c = var1;
   }

   public boolean ed() {
      return this.b;
   }

   public void V(boolean var1) {
      this.b = var1;
   }

   public L1Paralysis ee() {
      return this.k;
   }

   public void a(L1Paralysis var1) {
      this.k = var1;
   }

   public void ef() {
      if (this.k != null) {
         this.k.a();
      }
   }

   public void b(ServerBasePacket var1) {
      for (L1PcInstance var2 : L1World.a().f(this)) {
         if (var2.b(this)) {
            var2.a(var1);
         }
      }
   }

   public void a(ServerBasePacket var1, L1Character var2) {
      for (L1PcInstance var3 : L1World.a().b(this, var2)) {
         if (var3.b(this)) {
            var3.a(var1);
         }
      }
   }

   public void c(ServerBasePacket var1) {
      for (L1PcInstance var2 : L1World.a().f(this)) {
         if (var2.b(this) && var2.bB(26003)) {
            var2.a(var1);
         }
      }
   }

   public void d(ServerBasePacket var1) {
      for (L1PcInstance var2 : L1World.a().c(this, 50)) {
         if (var2.b(this)) {
            var2.a(var1);
         }
      }
   }

   public int[] eg() {
      int[] var1 = new int[2];
      int var2 = this.fs();
      int var3 = this.ft();
      int var4 = this.fb();
      if (var4 == 0) {
         var3--;
      } else if (var4 == 1) {
         var2++;
         var3--;
      } else if (var4 == 2) {
         var2++;
      } else if (var4 == 3) {
         var2++;
         var3++;
      } else if (var4 == 4) {
         var3++;
      } else if (var4 == 5) {
         var2--;
         var3++;
      } else if (var4 == 6) {
         var2--;
      } else if (var4 == 7) {
         var2--;
         var3--;
      }

      var1[0] = var2;
      var1[1] = var3;
      return var1;
   }

   public int a(L1Object var1) {
      return this.h(var1.fs(), var1.ft());
   }

   public int h(int var1, int var2) {
      float var3 = Math.abs(this.fs() - var1);
      float var4 = Math.abs(this.ft() - var2);
      float var5 = Math.max(var3, var4);
      if (var5 == 0.0F) {
         return this.fb();
      }

      int var6 = (int)Math.floor(var3 / var5 + 0.59F);
      int var7 = (int)Math.floor(var4 / var5 + 0.59F);
      int var8 = 0;
      int var9 = 0;
      if (this.fs() < var1) {
         var8 = 1;
      }

      if (this.fs() > var1) {
         var8 = -1;
      }

      if (this.ft() < var2) {
         var9 = 1;
      }

      if (this.ft() > var2) {
         var9 = -1;
      }

      if (var6 == 0) {
         var8 = 0;
      }

      if (var7 == 0) {
         var9 = 0;
      }

      if (var8 == 1 && var9 == -1) {
         return 1;
      } else if (var8 == 1 && var9 == 0) {
         return 2;
      } else if (var8 == 1 && var9 == 1) {
         return 3;
      } else if (var8 == 0 && var9 == 1) {
         return 4;
      } else if (var8 == -1 && var9 == 1) {
         return 5;
      } else if (var8 == -1 && var9 == 0) {
         return 6;
      } else if (var8 == -1 && var9 == -1) {
         return 7;
      } else {
         return var8 == 0 && var9 == -1 ? 0 : this.fb();
      }
   }

   public boolean i(int var1, int var2) {
      int var3 = this.fs();
      int var4 = this.ft();
      int var5 = Math.abs(var3 - var1);
      int var6 = Math.abs(var4 - var2);
      if (var5 == 0 && var6 == 0) {
         return true;
      }

      int var7 = var3 < var1 ? 1 : -1;
      int var8 = var4 < var2 ? 1 : -1;
      int var9 = var5 - var6;

      while (this.fq().c(var3, var4)) {
         if (var3 == var1 && var4 == var2) {
            return true;
         }

         int var10 = var9 * 2;
         if (var10 >= -1 * var6) {
            var9 -= var6;
            var3 += var7;
         }

         if (var10 < var5) {
            var9 += var5;
            var4 += var8;
         }

         if (Math.abs(var3 - this.fs()) >= var5 && Math.abs(var4 - this.ft()) >= var6) {
            var3 = var1;
            var4 = var2;
         }
      }

      return false;
   }

   public boolean c(int var1, int var2, int var3) {
      if (var3 >= 7) {
         if (this.fu().d(new Point(var1, var2)) > var3) {
            return false;
         }
      } else if (this.fu().c(new Point(var1, var2)) > var3) {
         return false;
      }

      return this.i(var1, var2);
   }

   public L1Inventory y() {
      return null;
   }

   private void c(int var1) {
      int[][] var2 = new int[][]{
         {14, 134, 218},
         {4069, 4085},
         {1032, 1033, 1034, 1035, 1036},
         {148, 149, 156, 163, 166, 4006},
         {3, 151, 159, 168},
         {1000, 1016, 52, 101, 150, 1026, 1037},
         {43, 54, 1001, 1037},
         {1027, 1037},
         {1038, 1037},
         {5001, 5002, 5003},
         {29, 76, 152},
         {26, 110},
         {185, 190, 195},
         {42, 109},
         {201, 106},
         {60, 97},
         {202, 64},
         {20, 40},
         {169, 176},
         {4056, 4057, 4079},
         {1014, 1015, 1013},
         {4086, 4087, 4088, 4089, 4090, 4091},
         {4001, 4002, 4003, 4004, 4005, 4007, 4070, 4078},
         {4008, 4009, 4010, 4081, 4082, 4083},
         {
               4013,
               4014,
               4015,
               4016,
               4017,
               4018,
               4019,
               4020,
               4021,
               4022,
               4023,
               4024,
               4025,
               4026,
               4027,
               4028,
               4029,
               4030,
               4031,
               4032,
               4033,
               4034,
               4035,
               4036,
               4037,
               4038,
               4039,
               4040,
               4041,
               4042,
               4043,
               4044,
               4045,
               4046,
               4047,
               4048,
               4072,
               4073,
               4074,
               4075
         },
         {4049, 4050, 4051, 4052, 4053, 4054, 4055},
         {
               3000,
               3001,
               3002,
               3003,
               3004,
               3005,
               3006,
               3008,
               3009,
               3010,
               3011,
               3012,
               3013,
               3014,
               3016,
               3017,
               3018,
               3019,
               3020,
               3021,
               3022,
               3024,
               3025,
               3026,
               3027,
               3028,
               3029,
               3030,
               3032,
               3033,
               3034,
               3035,
               3036,
               3037,
               3038,
               3040,
               3041,
               3042,
               3043,
               3044,
               3045,
               3046,
               3049,
               3050,
               3051,
               3053,
               3054,
               3055
         },
         {3007, 3015, 3023, 3031, 3039, 3047, 3052, 3056}
      };
      int[][] var6 = var2;
      int var5 = var2.length;

      for (int var4 = 0; var4 < var5; var4++) {
         int[] var3 = var6[var4];
         int[] var10 = var3;
         int var9 = var3.length;

         for (int var8 = 0; var8 < var9; var8++) {
            int var7 = var10[var8];
            if (var7 == var1) {
               this.a(var3, var1);
            }
         }
      }
   }

   private void a(int[] var1, int var2) {
      int[] var6 = var1;
      int var5 = var1.length;

      for (int var4 = 0; var4 < var5; var4++) {
         int var3 = var6[var4];
         if (var3 != var2) {
            this.bz(var3);
         }
      }
   }

   public HashMap<Integer, L1SkillTimer__obf_d> eh() {
      return this.f;
   }

   private void a(int var1, int var2) {
      L1SkillTimer__obf_d var3 = null;
      if (var2 > 0) {
         var3 = L1SkillTimerCreator.a(this, var1, var2, null);
         var3.c();
      }

      this.f.put(var1, var3);
   }

   public void j(int var1, int var2) {
      this.c(var1);
      if (this.bB(var1)) {
         int var3 = this.bC(var1) * 1000;
         if (var3 >= 0 && (var3 < var2 || var2 == 0)) {
            this.bA(var1);
            this.a(var1, var2);
         }
      } else {
         this.a(var1, var2);
      }
   }

   public void a(int var1, int var2, Timestamp var3) {
      this.c(var1);
      if (this.bB(var1)) {
         this.bz(var1);
      }

      L1SkillTimer__obf_d var4 = null;
      if (var2 > 0) {
         var4 = L1SkillTimerCreator.a(this, var1, var2, var3);
         var4.c();
      }

      this.f.put(var1, var4);
   }

   public void bz(int var1) {
      L1SkillTimer__obf_d var2 = this.f.remove(var1);
      if (var2 != null) {
         var2.d();
      }
   }

   public void bA(int var1) {
      L1SkillTimer__obf_d var2 = this.f.remove(var1);
      if (var2 != null) {
         var2.e();
      }
   }

   public void ei() {
      for (L1SkillTimer__obf_d var1 : this.f.values()) {
         if (var1 != null) {
            var1.e();
         }
      }

      this.f.clear();
   }

   public boolean bB(int var1) {
      return this.f.containsKey(var1);
   }

   public int bC(int var1) {
      L1SkillTimer__obf_d var2 = this.f.get(var1);
      return var2 == null ? -1 : var2.a();
   }

   public Timestamp bD(int var1) {
      L1SkillTimer__obf_d var2 = this.f.get(var1);
      return var2 == null ? null : var2.b();
   }

   public void a(int var1, boolean var2) {
      L1SkillTimer__obf_d var3 = this.f.get(var1);
      if (var3 != null) {
         var3.a(var2);
      }
   }

   public void W(boolean var1) {
      this.l = var1;
   }

   public boolean ej() {
      return this.l;
   }

   public void a(int var1, L1ItemDelay.L1R_a var2) {
      this.g.put(var1, var2);
   }

   public void bE(int var1) {
      this.g.remove(var1);
   }

   public boolean bF(int var1) {
      return this.g.containsKey(var1);
   }

   public void e(L1NpcInstance var1) {
      this.d.put(var1.fr(), var1);
      if (this instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)this;
         var2.a(new S_PetCtrlMenu(var2, var1, true));
      }
   }

   public ConcurrentHashMap<Integer, L1NpcInstance> ek() {
      return this.d;
   }

   public void b(L1DollInstance var1) {
      this.e.put(var1.fr(), var1);
   }

   public ConcurrentHashMap<Integer, L1DollInstance> el() {
      return this.e;
   }

   public void a(L1FollowerInstance var1) {
      this.h.put(var1.fr(), var1);
   }

   public ConcurrentHashMap<Integer, L1FollowerInstance> em() {
      return this.h;
   }

   public void a(L1Poison var1) {
      this.a = var1;
   }

   public void en() {
      if (this.a != null) {
         this.a.b();
      }
   }

   public L1Poison eo() {
      return this.a;
   }

   public void y(int var1) {
      this.b(new S_Poison(this.fr(), var1));
   }

   public int ep() {
      if (this.fq().b(this.fu())) {
         return 1;
      } else {
         return this.fq().c(this.fu()) ? -1 : 0;
      }
   }

   public int m() {
      return this.m;
   }

   public void k(int var1) {
      this.m = var1;
   }

   public boolean b(L1Object var1) {
      return this.n.contains(var1);
   }

   public List<L1Object> eq() {
      return this.n;
   }

   public List<L1PcInstance> er() {
      return this.o;
   }

   public void c(L1Object var1) {
      if (!this.n.contains(var1)) {
         this.n.add(var1);
         if (var1 instanceof L1PcInstance) {
            this.o.add((L1PcInstance)var1);
         }
      }
   }

   public void d(L1Object var1) {
      this.n.remove(var1);
      if (var1 instanceof L1PcInstance) {
         this.o.remove(var1);
      }
   }

   public void es() {
      this.n.clear();
      this.o.clear();
   }

   public String et() {
      if (this instanceof L1PcInstance) {
         return ((L1PcInstance)this).af() ? "**守護者**" : this.p;
      } else {
         return this.p;
      }
   }

   public String eu() {
      return this.p;
   }

   public void e(String var1) {
      this.p = var1;
   }

   public synchronized int ev() {
      return this.q;
   }

   public synchronized void b(long var1) {
      this.q = (int)var1;
   }

   public short ew() {
      return this.r;
   }

   public void bG(int var1) {
      this.s = var1;
      this.r = (short)IntRange.b(this.s, 1, 32767);
      this.i = Math.min(this.i, this.r);
   }

   public void bH(int var1) {
      this.bG(this.s + var1);
   }

   public short ex() {
      return this.t;
   }

   public void bI(int var1) {
      this.u = var1;
      this.t = (short)IntRange.b(this.u, 0, 32767);
      this.j = Math.min(this.j, this.t);
   }

   public void bJ(int var1) {
      this.bI(this.u + var1);
   }

   public int ey() {
      return this.v;
   }

   public void bK(int var1) {
      this.v = IntRange.b(var1, -1280, 127);
   }

   public void bL(int var1) {
      this.bK(this.v + var1);
   }

   public byte ez() {
      return this.w;
   }

   public void bM(int var1) {
      this.y = (short)var1;
      this.w = (byte)IntRange.b(var1, 1, 127);
   }

   public void bN(int var1) {
      this.bM(this.y + var1);
   }

   public byte eA() {
      return this.z;
   }

   public void bO(int var1) {
      this.A = (short)var1;
      this.z = (byte)IntRange.b(var1, 1, 127);
   }

   public void bP(int var1) {
      this.bO(this.A + var1);
   }

   public byte eB() {
      return this.B;
   }

   public void bQ(int var1) {
      this.C = (short)var1;
      this.B = (byte)IntRange.b(var1, 1, 127);
   }

   public void bR(int var1) {
      this.bQ(this.C + var1);
   }

   public byte eC() {
      return this.D;
   }

   public void bS(int var1) {
      this.E = (short)var1;
      this.D = (byte)IntRange.b(var1, 1, 127);
   }

   public void bT(int var1) {
      this.bS(this.E + var1);
   }

   public byte eD() {
      return this.F;
   }

   public void bU(int var1) {
      this.G = (short)var1;
      this.F = (byte)IntRange.b(var1, 1, 127);
   }

   public void bV(int var1) {
      this.bU(this.G + var1);
   }

   public byte eE() {
      return this.H;
   }

   public void bW(int var1) {
      this.I = (short)var1;
      this.H = (byte)IntRange.b(var1, 1, 127);
   }

   public void bX(int var1) {
      this.bW(this.I + var1);
   }

   public int eF() {
      return this.J;
   }

   public void bY(int var1) {
      this.K += var1;
      if (this.K >= 127) {
         this.J = 127;
      } else if (this.K <= -128) {
         this.J = -128;
      } else {
         this.J = this.K;
      }
   }

   public int eG() {
      return this.L;
   }

   public void bZ(int var1) {
      this.M += var1;
      if (this.M >= 127) {
         this.L = 127;
      } else if (this.M <= -128) {
         this.L = -128;
      } else {
         this.L = this.M;
      }
   }

   public int eH() {
      return this.N;
   }

   public void ca(int var1) {
      this.O += var1;
      if (this.O >= 127) {
         this.N = 127;
      } else if (this.O <= -128) {
         this.N = -128;
      } else {
         this.N = this.O;
      }
   }

   public int eI() {
      return this.P;
   }

   public void cb(int var1) {
      this.Q += var1;
      if (this.Q >= 127) {
         this.P = 127;
      } else if (this.Q <= -128) {
         this.P = -128;
      } else {
         this.P = this.Q;
      }
   }

   public int eJ() {
      return this.R;
   }

   public void cc(int var1) {
      this.R = var1;
   }

   public int eK() {
      return this.S;
   }

   public void cd(int var1) {
      this.T += var1;
      if (this.T > 127) {
         this.S = 127;
      } else if (this.T < -128) {
         this.S = -128;
      } else {
         this.S = this.T;
      }
   }

   public int eL() {
      return this.U;
   }

   public void ce(int var1) {
      this.V += var1;
      if (this.V > 127) {
         this.U = 127;
      } else if (this.V < -128) {
         this.U = -128;
      } else {
         this.U = this.V;
      }
   }

   public int eM() {
      return this.W;
   }

   public void cf(int var1) {
      this.X += var1;
      if (this.X > 127) {
         this.W = 127;
      } else if (this.X < -128) {
         this.W = -128;
      } else {
         this.W = this.X;
      }
   }

   public int eN() {
      return this.Y;
   }

   public void cg(int var1) {
      this.Z += var1;
      if (this.Z > 127) {
         this.Y = 127;
      } else if (this.Z < -128) {
         this.Y = -128;
      } else {
         this.Y = this.Z;
      }
   }

   public int eO() {
      return this.aa;
   }

   public void ch(int var1) {
      this.ab += var1;
      if (this.ab > 127) {
         this.aa = 127;
      } else if (this.ab < -128) {
         this.aa = -128;
      } else {
         this.aa = this.ab;
      }
   }

   public int eP() {
      return this.ac;
   }

   public void ci(int var1) {
      this.ad += var1;
      if (this.ad > 127) {
         this.ac = 127;
      } else if (this.ad < -128) {
         this.ac = -128;
      } else {
         this.ac = this.ad;
      }
   }

   public int eQ() {
      return this.ae;
   }

   public void cj(int var1) {
      this.af += var1;
      if (this.af > 127) {
         this.ae = 127;
      } else if (this.af < -128) {
         this.ae = -128;
      } else {
         this.ae = this.af;
      }
   }

   public int eR() {
      return this.ag;
   }

   public void ck(int var1) {
      this.ah += var1;
      if (this.ah >= 127) {
         this.ag = 127;
      } else if (this.ah <= -128) {
         this.ag = -128;
      } else {
         this.ag = this.ah;
      }
   }

   public int eS() {
      return this.ai;
   }

   public void cl(int var1) {
      this.aj += var1;
      if (this.aj >= 127) {
         this.ai = 127;
      } else if (this.aj <= -128) {
         this.ai = -128;
      } else {
         this.ai = this.aj;
      }
   }

   public int eT() {
      return this.ak;
   }

   public void cm(int var1) {
      this.al += var1;
      if (this.al >= 127) {
         this.ak = 127;
      } else if (this.al <= -128) {
         this.ak = -128;
      } else {
         this.ak = this.al;
      }
   }

   public int eU() {
      return this.am;
   }

   public void cn(int var1) {
      this.an += var1;
      if (this.an >= 127) {
         this.am = 127;
      } else if (this.an <= -128) {
         this.am = -128;
      } else {
         this.am = this.an;
      }
   }

   public int W_() {
      return this.bB(153) ? this.x * 3 / 4 : this.x;
   }

   public void co(int var1) {
      this.x += var1;
   }

   public int eV() {
      return this.eW() + this.ao;
   }

   public void cp(int var1) {
      this.ao += var1;
   }

   public int eW() {
      return this.U() + CalcStat.c(this.eD());
   }

   public int U() {
      return Math.min(this.ev(), 52) / 4;
   }

   public boolean eX() {
      return this.ap;
   }

   public void X(boolean var1) {
      this.ap = var1;
   }

   public int eY() {
      return this.aq;
   }

   public void cq(int var1) {
      this.aq = var1;
   }

   public String eZ() {
      return this.ar;
   }

   public void f(String var1) {
      this.ar = var1;
   }

   public int fa() {
      return this.as;
   }

   public void cr(int var1) {
      this.as = var1;
   }

   public synchronized void cs(int var1) {
      this.as += var1;
      if (this.as > 32767) {
         this.as = 32767;
      } else if (this.as < -32768) {
         this.as = -32768;
      }
   }

   public int fb() {
      return this.at;
   }

   public void ct(int var1) {
      this.at = var1;
   }

   public int fc() {
      return this.au;
   }

   public void cu(int var1) {
      this.au = var1;
   }

   public int fd() {
      return this.av;
   }

   public void cv(int var1) {
      this.av = var1;
   }

   public int fe() {
      return this.aw;
   }

   public void cw(int var1) {
      this.aw = var1;
   }

   public boolean ff() {
      return this.bB(60) || this.bB(97);
   }

   public void z(int var1) {
      this.a(this.ea() + var1);
   }

   public int P() {
      return this.ax;
   }

   public void A(int var1) {
      this.ax = var1;
   }

   public void cx(int var1) {
      this.x = var1;
   }

   public void fg() {
      int var1 = 0;
      if (this instanceof L1NpcInstance) {
         L1NpcInstance var2 = (L1NpcInstance)this;
         var1 = var2.aa();
      }

      if (this.bB(2)) {
         var1 = 14;
      }

      for (L1ItemInstance var5 : this.y().d()) {
         if (var5.f() && var5.a().aP() == 2) {
            int var4 = var5.a().c();
            if (var4 != 0 && var5.T() && var4 > var1) {
               var1 = var4;
            }
         }
      }

      if (this instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)this;
         var6.a(new S_Light(var6.fr(), var1));
      }

      if (!this.ff()) {
         this.b(new S_Light(this.fr(), var1));
      }

      this.cz(var1);
      this.cy(var1);
   }

   public int fh() {
      return this.ff() ? 0 : this.ay;
   }

   public void cy(int var1) {
      this.ay = var1;
   }

   public int fi() {
      return this.az;
   }

   public void cz(int var1) {
      this.az = var1;
   }

   public int fj() {
      return this.aA;
   }

   public void c_(int var1) {
      this.aA = var1;
   }

   public int fk() {
      return this.aB;
   }

   public void cA(int var1) {
      this.aB += var1;
      this.aB = Math.max(0, Math.min(this.aB, 12));
   }

   public int fl() {
      return this.aC;
   }

   public void cB(int var1) {
      this.aC += var1;
      this.aC = Math.max(0, Math.min(this.aC, 12));
   }

   public boolean d(L1Character var1, int var2) {
      switch (var2) {
         case 45625:
         case 45674:
         case 45675:
         case 45685:
         case 81082:
            return !var1.bB(5014);
         case 45752:
         case 45753:
            return !var1.bB(5015);
         case 45912:
         case 45913:
         case 45914:
         case 45915:
            return !var1.bB(1014);
         case 45916:
            return !var1.bB(1015);
         case 45941:
            return !var1.bB(1013);
         case 81341:
         default:
            if (var2 >= 46068 && var2 <= 46091 && var1.fe() == 6035) {
               return true;
            } else {
               if (var2 >= 46092 && var2 <= 46106 && var1.fe() == 6034) {
                  return true;
               }

               return false;
            }
         case 190026:
            return !var1.bB(1030);
      }
   }

   public int fm() {
      return this.aD;
   }

   public void cC(int var1) {
      this.aD = var1;
   }

   public int fn() {
      int var1 = 0;
      if (this.eo() != null) {
         var1 = this.eo().a();
      }

      if (this.ee() != null) {
         var1 = this.ee().b();
      }

      return var1;
   }

   public int fo() {
      return this.aE;
   }

   public void cD(int var1) {
      this.aE = var1;
   }
}
