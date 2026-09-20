package l1r.bh;

public class L1Item implements Cloneable {
   private int aD;
   private int aE;
   private String aF;
   private String aG;
   private String aH;
   private int aI;
   private int aJ;
   private int aK;
   private int aL;
   private int aM;
   private int aN;
   private int aO;
   private int aP;
   private boolean aQ;
   private boolean aR;
   private boolean aS;
   private int aT = 0;
   private int aU = 0;
   private int aV = -1;
   private boolean aW = false;
   private boolean aX = false;
   private boolean aY = false;
   private boolean aZ = false;
   private boolean ba = false;
   private boolean bb = false;
   private boolean bc = false;
   private boolean bd = false;
   private byte be = 0;
   private byte bf = 0;
   private byte bg = 0;
   private byte bh = 0;
   private byte bi = 0;
   private byte bj = 0;
   private int bk = 0;
   private int bl = 0;
   private int bm = 0;
   private int bn = 0;
   private int bo = 0;
   private int bp = 0;
   private boolean bq = false;
   private int br = 0;
   private int bs;
   private int bt;
   private int bu = 0;
   private int bv = 0;
   private int bw = 0;
   private int bx = 0;
   private int by = 0;
   private int bz = 0;
   private int bA = 0;
   private int bB = 0;
   private int bC = 0;
   private int bD = 0;
   private int bE = 0;
   private int bF = 0;
   private int bG = 0;
   private int bH = 0;
   private int bI = 0;
   private int bJ = 0;
   private int bK = 0;
   private int bL = 0;
   private int bM = 0;
   private int bN = -1;
   private int bO = 0;
   private int bP = 0;
   private int bQ = 0;
   private int bR = 0;
   private int bS = 0;
   private int bT = 0;
   private int bU = 0;
   private boolean bV = false;
   private int bW = 0;
   private int bX = 0;
   private int bY = 0;
   private int bZ = 0;
   private int ca = 0;
   private int cb = 0;
   private boolean cc = true;
   private boolean cd = false;
   private int ce = 0;
   private int cf = 0;
   private short cg = 0;
   private int ch = 0;
   private int ci = 0;
   private int cj = 0;
   private int ck = 0;
   private boolean cl = false;
   private int cm;
   public static final int a = 0;
   public static final int b = 4;
   public static final int c = 11;
   public static final int d = 20;
   public static final int e = 24;
   public static final int f = 24;
   public static final int g = 40;
   public static final int h = 46;
   public static final int i = 50;
   public static final int j = 54;
   public static final int k = 58;
   public static final int l = 58;
   public static final int m = 62;
   private int cn;
   public static final int n = -1;
   public static final int o = 1;
   public static final int p = 2;
   public static final int q = 3;
   public static final int r = 5;
   public static final int s = 6;
   public static final int t = 7;
   public static final int u = 8;
   public static final int v = 9;
   public static final int w = 257;
   public static final int x = 258;
   public static final int y = 259;
   public static final int z = 260;
   public static final int A = 261;
   public static final int B = 262;
   public static final int C = 264;
   public static final int D = 264;
   public static final int E = 266;
   public static final int F = 1;
   public static final int G = 2;
   public static final int H = 3;
   public static final int I = 4;
   public static final int J = 5;
   public static final int K = 6;
   public static final int L = 7;
   public static final int M = 11;
   public static final int N = 12;
   public static final int O = 13;
   public static final int P = 14;
   public static final int Q = 15;
   public static final int R = 18;
   public static final int S = 16;
   public static final int T = 8;
   public static final int U = 9;
   public static final int V = 10;
   public static final int W = 23;
   public static final int X = 29;
   public static final int Y = 30;
   public static final int Z = 0;
   public static final int aa = 1;
   public static final int ab = 2;
   public static final int ac = 3;
   public static final int ad = 4;
   public static final int ae = 5;
   public static final int af = 6;
   public static final int ag = 7;
   public static final int ah = 8;
   public static final int ai = 9;
   public static final int aj = 10;
   public static final int ak = 11;
   public static final int al = 12;
   public static final int am = 13;
   public static final int an = 14;
   public static final int ao = 15;
   public static final int ap = 16;
   public static final int aq = 17;
   public static final int ar = 18;
   public static final int as = 22;
   public static final int at = 23;
   public static final int au = 24;
   public static final int av = 25;
   public static final int aw = 26;
   public static final int ax = 27;
   public static final int ay = 28;
   public static final int az = 29;
   public static final int aA = 30;
   public static final int aB = 31;
   public static final int aC = 32;

   @Override
   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public boolean a() {
      return this.f() == 0 ? this.aP() == 6 || this.aP() == 26 || this.aP() == 24 || this.aP() == 23 || this.aP() == 25 || this.aP() == 27 : false;
   }

   public boolean b() {
      return this.f() == 0 ? this.aP() == 8 || this.aP() == 32 || this.aP() == 28 || this.aP() == 31 : false;
   }

   public int c() {
      if (this.aE == 40001) {
         return 11;
      } else if (this.aE == 40002) {
         return 14;
      } else if (this.aE == 40004) {
         return 14;
      } else if (this.aE == 40005) {
         return 8;
      } else if (this.aE == 640499) {
         return 14;
      } else {
         return this.aE == 640511 ? 20 : 0;
      }
   }

   public int d() {
      if (this.aE == 40001) {
         return 6000;
      } else if (this.aE == 40002) {
         return 12000;
      } else if (this.aE == 40003) {
         return 12000;
      } else if (this.aE == 40004) {
         return 0;
      } else if (this.aE == 40005) {
         return 600;
      } else if (this.aE == 640499) {
         return 12000;
      } else {
         return this.aE == 640511 ? 12000 : 0;
      }
   }

   public boolean e() {
      return this.f() == 1 && this.aP() > 256;
   }

   public int f() {
      return this.aD;
   }

   public void a(int var1) {
      this.aD = var1;
   }

   public int g() {
      return this.aE;
   }

   public void b(int var1) {
      this.aE = var1;
   }

   public String h() {
      return this.aF;
   }

   public void a(String var1) {
      this.aF = var1;
   }

   public String i() {
      return this.aG;
   }

   public void b(String var1) {
      this.aG = var1;
   }

   public String j() {
      return this.aH;
   }

   public void c(String var1) {
      this.aH = var1;
   }

   public int k() {
      return this.aI;
   }

   public void c(int var1) {
      this.aI = var1;
   }

   public int l() {
      return this.aJ;
   }

   public void d(int var1) {
      this.aJ = var1;
   }

   public int m() {
      return this.aK;
   }

   public void e(int var1) {
      this.aK = var1;
   }

   public int n() {
      return this.aL;
   }

   public void f(int var1) {
      this.aL = var1;
   }

   public int o() {
      return this.aM;
   }

   public void g(int var1) {
      this.aM = var1;
   }

   public int p() {
      return this.aN;
   }

   public void h(int var1) {
      this.aN = var1;
   }

   public int q() {
      return this.aO;
   }

   public void i(int var1) {
      this.aO = var1;
   }

   public int r() {
      return this.aP;
   }

   public void j(int var1) {
      this.aP = var1;
   }

   public boolean s() {
      return this.aQ;
   }

   public void a(boolean var1) {
      this.aQ = var1;
   }

   public boolean t() {
      return this.aR;
   }

   public void b(boolean var1) {
      this.aR = var1;
   }

   public boolean u() {
      return this.aS;
   }

   public void c(boolean var1) {
      this.aS = var1;
   }

   public int v() {
      return this.aT;
   }

   public void k(int var1) {
      this.aT = var1;
   }

   public int w() {
      return this.aU;
   }

   public void l(int var1) {
      this.aU = var1;
   }

   public int x() {
      return this.aV;
   }

   public void m(int var1) {
      this.aV = var1;
   }

   public boolean y() {
      return this.aW;
   }

   public void d(boolean var1) {
      this.aW = var1;
   }

   public boolean z() {
      return this.aX;
   }

   public void e(boolean var1) {
      this.aX = var1;
   }

   public boolean A() {
      return this.aY;
   }

   public void f(boolean var1) {
      this.aY = var1;
   }

   public boolean B() {
      return this.aZ;
   }

   public void g(boolean var1) {
      this.aZ = var1;
   }

   public boolean C() {
      return this.ba;
   }

   public void h(boolean var1) {
      this.ba = var1;
   }

   public boolean D() {
      return this.bb;
   }

   public void i(boolean var1) {
      this.bb = var1;
   }

   public boolean E() {
      return this.bc;
   }

   public void j(boolean var1) {
      this.bc = var1;
   }

   public boolean F() {
      return this.bd;
   }

   public void k(boolean var1) {
      this.bd = var1;
   }

   public byte G() {
      return this.be;
   }

   public void a(byte var1) {
      this.be = var1;
   }

   public byte H() {
      return this.bf;
   }

   public void b(byte var1) {
      this.bf = var1;
   }

   public byte I() {
      return this.bg;
   }

   public void c(byte var1) {
      this.bg = var1;
   }

   public byte J() {
      return this.bh;
   }

   public void d(byte var1) {
      this.bh = var1;
   }

   public byte K() {
      return this.bi;
   }

   public void e(byte var1) {
      this.bi = var1;
   }

   public byte L() {
      return this.bj;
   }

   public void f(byte var1) {
      this.bj = var1;
   }

   public int M() {
      return this.bk;
   }

   public void n(int var1) {
      this.bk = var1;
   }

   public int N() {
      return this.bl;
   }

   public void o(int var1) {
      this.bl = var1;
   }

   public int O() {
      return this.bm;
   }

   public void p(int var1) {
      this.bm = var1;
   }

   public int P() {
      return this.bn;
   }

   public void q(int var1) {
      this.bn = var1;
   }

   public int Q() {
      return this.bo;
   }

   public void r(int var1) {
      this.bo = var1;
   }

   public int R() {
      return this.bp;
   }

   public void s(int var1) {
      this.bp = var1;
   }

   public boolean S() {
      return this.bq;
   }

   public void l(boolean var1) {
      this.bq = var1;
   }

   public int T() {
      return this.br;
   }

   public void t(int var1) {
      this.br = var1;
   }

   public int U() {
      return this.bs;
   }

   public void u(int var1) {
      this.bs = var1;
   }

   public int V() {
      return this.bt;
   }

   public void v(int var1) {
      this.bt = var1;
   }

   public int W() {
      return this.bu;
   }

   public void w(int var1) {
      this.bu = var1;
   }

   public int X() {
      return this.bv;
   }

   public void x(int var1) {
      this.bv = var1;
   }

   public int Y() {
      return this.bw;
   }

   public void y(int var1) {
      this.bw = var1;
   }

   public int Z() {
      return this.bx;
   }

   public void z(int var1) {
      this.bx = var1;
   }

   public int aa() {
      return this.by;
   }

   public void A(int var1) {
      this.by = var1;
   }

   public int ab() {
      return this.bz;
   }

   public void B(int var1) {
      this.bz = var1;
   }

   public int ac() {
      return this.bA;
   }

   public void C(int var1) {
      this.bA = var1;
   }

   public int ad() {
      return this.bB;
   }

   public void D(int var1) {
      this.bB = var1;
   }

   public int ae() {
      return this.bC;
   }

   public void E(int var1) {
      this.bC = var1;
   }

   public int af() {
      return this.bD;
   }

   public void F(int var1) {
      this.bD = var1;
   }

   public int ag() {
      return this.bE;
   }

   public void G(int var1) {
      this.bE = var1;
   }

   public int ah() {
      return this.bF;
   }

   public void H(int var1) {
      this.bF = var1;
   }

   public int ai() {
      return this.bG;
   }

   public void I(int var1) {
      this.bG = var1;
   }

   public int aj() {
      return this.bH;
   }

   public void J(int var1) {
      this.bH = var1;
   }

   public int ak() {
      return this.bI;
   }

   public void K(int var1) {
      this.bI = var1;
   }

   public int al() {
      return this.bJ;
   }

   public void L(int var1) {
      this.bJ = var1;
   }

   public int am() {
      return this.bK;
   }

   public void M(int var1) {
      this.bK = var1;
   }

   public int an() {
      return this.bL;
   }

   public void N(int var1) {
      this.bL = var1;
   }

   public int ao() {
      return this.bM;
   }

   public void O(int var1) {
      this.bM = var1;
   }

   public int ap() {
      return this.bN;
   }

   public void P(int var1) {
      this.bN = var1;
   }

   public int aq() {
      return this.bO;
   }

   public void Q(int var1) {
      this.bO = var1;
   }

   public int ar() {
      return this.bP;
   }

   public void R(int var1) {
      this.bP = var1;
   }

   public int as() {
      return this.bQ;
   }

   public void S(int var1) {
      this.bQ = var1;
   }

   public int at() {
      return this.bR;
   }

   public void T(int var1) {
      this.bR = var1;
   }

   public int au() {
      return this.bS;
   }

   public void U(int var1) {
      this.bS = var1;
   }

   public int av() {
      return this.bT;
   }

   public void V(int var1) {
      this.bT = var1;
   }

   public int aw() {
      return this.bU;
   }

   public void W(int var1) {
      this.bU = var1;
   }

   public boolean ax() {
      return this.bV;
   }

   public void m(boolean var1) {
      this.bV = var1;
   }

   public int ay() {
      return this.bW;
   }

   public void X(int var1) {
      this.bW = var1;
   }

   public int az() {
      return this.bX;
   }

   public void Y(int var1) {
      this.bX = var1;
   }

   public int aA() {
      return this.bY;
   }

   public void Z(int var1) {
      this.bY = var1;
   }

   public int aB() {
      return this.bZ;
   }

   public void aa(int var1) {
      this.bZ = var1;
   }

   public int aC() {
      return this.ca;
   }

   public void ab(int var1) {
      this.ca = var1;
   }

   public int aD() {
      return this.cb;
   }

   public void ac(int var1) {
      this.cb = var1;
   }

   public boolean aE() {
      return this.cc;
   }

   public void n(boolean var1) {
      this.cc = var1;
   }

   public boolean aF() {
      return this.cd;
   }

   public void o(boolean var1) {
      this.cd = var1;
   }

   public int aG() {
      return this.ce;
   }

   public void ad(int var1) {
      this.ce = var1;
   }

   public int aH() {
      return this.cf;
   }

   public void ae(int var1) {
      this.cf = var1;
   }

   public short aI() {
      return this.cg;
   }

   public void a(short var1) {
      this.cg = var1;
   }

   public int aJ() {
      return this.ch;
   }

   public void af(int var1) {
      this.ch = var1;
   }

   public int aK() {
      return this.ci;
   }

   public void ag(int var1) {
      this.ci = var1;
   }

   public int aL() {
      return this.cj;
   }

   public void ah(int var1) {
      this.cj = var1;
   }

   public int aM() {
      return this.ck;
   }

   public void ai(int var1) {
      this.ck = var1;
   }

   public boolean aN() {
      return this.cl;
   }

   public void p(boolean var1) {
      this.cl = var1;
   }

   public int aO() {
      return this.cm;
   }

   public void aj(int var1) {
      this.cm = var1;
   }

   public int aP() {
      return this.cn;
   }

   public void ak(int var1) {
      this.cn = var1;
   }
}
