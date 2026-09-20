package l1r.ap;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ArmorSetTable;
import l1r.ao.InnTable;
import l1r.ao.ItemTable;
import l1r.ao.MagicDollTable;
import l1r.ao.NpcTable;
import l1r.ao.PetItemTable;
import l1r.ao.PetTable;
import l1r.ao.WeaponSkillTable;
import l1r.aq.L1Object;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Item;
import l1r.bh.L1Npc;
import l1r.bh.L1Pet;
import l1r.bh.L1PetItem;
import l1r.bi.BinaryOutputStream;
import l1r.bi.GeneralThreadPool;

public class L1ItemInstance extends L1Object {
   private static final Logger O = Logger.getLogger(L1ItemInstance.class.getName());
   private boolean P = false;
   private boolean Q = false;
   private int R = 1;
   private int S = 1;
   private int T = 0;
   private int U;
   private int V;
   private Timestamp W = null;
   private int X;
   private int Y;
   private int Z;
   private L1Item aa;
   private int ab = 0;
   public int[] a = new int[2];
   public int[] b = new int[2];
   public boolean[] c = new boolean[2];
   public boolean[] d = new boolean[2];
   public int[] e = new int[2];
   public boolean[] f = new boolean[2];
   public int[] g = new int[2];
   public int[] h = new int[2];
   public int[] i = new int[2];
   public Timestamp[] j = new Timestamp[2];
   public Timestamp[] k = new Timestamp[2];
   public int[] l = new int[2];
   public int[] m = new int[2];
   public int[] n = new int[2];
   public int[] o = new int[2];
   public int[] p = new int[2];
   public int[] q = new int[2];
   public int[] r = new int[2];
   private int ac = 0;
   private int ad = 0;
   private int ae = 0;
   private int af = 0;
   private ScheduledFuture<?> ag;
   private L1PcInstance ah;
   private int ai = 0;
   private ScheduledFuture<?> aj;
   private boolean ak = false;
   private boolean al = false;
   private int am = 0;
   private boolean an = false;
   public static final int s = 0;
   public static final int t = 1;
   public static final int u = 2;
   public static final int v = 4;
   public static final int w = 8;
   public static final int x = 16;
   public static final int y = 32;
   public static final int z = 64;
   public static final int A = 128;
   public static final int B = 256;
   public static final int C = 512;
   public static final int D = 1024;
   public static final int E = 2048;
   public static final int F = 4096;
   public static final int G = 8192;
   public static final int H = 16384;
   public static final int I = 32768;
   public static final int J = 65536;
   public static final int K = 131072;
   public static final int L = 262144;
   public static final int M = 524288;
   public static final int N = 1048576;
   private int ao = 0;
   private int ap = 0;
   private int aq = 0;
   private int ar = 0;
   private int as = 0;
   private int at = 0;
   private int au = 0;
   private int av = 0;
   private int aw = 0;
   private int ax = 0;
   private int ay = 0;
   private int az = 0;
   private int aA = 0;
   private int aB = 0;
   private int aC = 0;
   private int aD = 0;
   private int aE = 0;
   private int aF = 0;
   private int aG = 0;
   private int aH = 0;
   private int aI = 0;
   private int aJ = 0;
   private int aK = 0;
   private int aL = 0;
   private int aM = 0;
   private int aN = 0;
   private int aO = 0;
   private int aP = 0;
   private int aQ = 0;
   private int aR = 0;
   private int aS = 0;
   private int aT = 0;
   private int aU = 0;
   private int aV = 0;
   private int aW = 0;
   private int aX = 0;
   private int aY = 0;
   private int aZ = 0;
   private int ba = 0;
   private int bb = 0;
   private int bc = 0;
   private int bd = 0;
   private int be = 0;
   private int bf = 0;
   private int bg = 0;
   private int bh = 0;
   private int bi = 0;
   private int bj = 0;
   private int bk = 0;
   private boolean bl = false;
   private boolean bm = false;
   private boolean bn = false;
   private boolean bo = false;
   private int bp = 0;
   private int bq = 0;
   private int br = 0;
   private final boolean bs = false;
   private Timestamp bt = null;
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
   private String bI;
   private int bJ = 0;
   private int bK = 0;
   private int bL = 0;
   private int bM = 0;
   private int bN = 0;
   private int bO = 0;
   private int bP = 0;
   private int bQ = 0;
   private int bR = 0;
   private boolean bS = false;
   private boolean bT = false;
   private int bU = 0;
   private int bV = 0;
   private int bW = 0;
   private String bX;

   public void a(int var1) {
      this.T = var1;
      this.bH();
   }

   public void b(int var1) {
      this.U = Math.max(0, Math.min(var1, 127));
   }

   public L1Item a() {
      return this.aa;
   }

   public String b() {
      return this.aa.h();
   }

   public String c() {
      return this.P ? this.aa.j() : this.aa.i();
   }

   public boolean d() {
      return this.aa.aF();
   }

   public int e() {
      return this.aa.m();
   }

   public boolean f() {
      return this.a().f() == 0;
   }

   public boolean g() {
      return this.a().f() == 1;
   }

   public boolean h() {
      return this.a().f() == 2;
   }

   public boolean i() {
      return !this.h() ? false : this.a().aP() == 9 || this.a().aP() == 11 || this.a().aP() == 12 || this.a().aP() == 13;
   }

   public boolean j() {
      return !this.h() ? false : this.a().aP() == 30;
   }

   public boolean k() {
      return this.a().a();
   }

   public boolean l() {
      return this.a().b();
   }

   public int m() {
      return this.a().W();
   }

   public L1ItemInstance(L1Item var1, int var2) {
      this.aa = var1;
      this.ab = var1.g();
      this.S = var1.r();
      this.R = var2;
   }

   public void a(L1Item var1) {
      this.aa = var1;
      this.ab = var1.g();
      this.S = var1.r();
   }

   public void n() {
      if (this.ab == 21204) {
         this.A();
      } else if (this.ab >= 21152 && this.ab <= 21155) {
         this.z();
      } else if (this.ab >= 21246 && this.ab <= 21257) {
         this.w();
      } else if (this.ab == 21446) {
         this.y();
      } else if (this.aa.aP() == 17 || this.aa.aP() == 22) {
         this.x();
      } else if (this.g()) {
         if (this.ab == 404) {
            this.bo = true;
         } else if (this.ab == 403) {
            this.bp = 5;
         } else if (this.ab == 66) {
            this.bd = this.G() * 4 + 4;
            this.aY = this.G();
         }

         WeaponSkillTable.a().a(this);
      }
   }

   private void bH() {
      if (this.G() > 0) {
         if (this.ab == 21308 && this.G() >= 5) {
            this.bk = this.G() - 4;
         } else if (this.ab == 21309 && this.G() >= 5) {
            this.aJ = this.G() - 4;
         } else if (this.ab == 21384 && this.G() >= 8) {
            this.av = this.G() - 7;
         } else if (this.ab == 21472) {
            this.as = this.G() * 5;
         } else if (this.ab == 21222 && this.G() >= 7) {
            this.aG = this.G() - 6;
         } else if (this.ab == 21105 && this.G() >= 5) {
            this.aM = this.G() - 4;
         } else if (this.ab == 21106 && this.G() >= 5) {
            this.aL = this.G() - 4;
         } else if (this.ab == 20079 && this.G() >= 7) {
            this.av = this.G() - 6;
         } else if (this.ab == 21351 && this.G() >= 5) {
            this.as = (int)Math.ceil((this.G() - 4.0) / 2.0) * 25;
         } else if (this.ab == 21352 && this.G() >= 5) {
            this.av = (int)Math.ceil((this.G() - 4.0) / 2.0);
         } else if (this.ab == 21353 && this.G() >= 5) {
            this.aG = (int)Math.ceil((this.G() - 4.0) / 2.0);
         } else if (this.ab == 21516 && this.G() >= 5) {
            this.aM = this.G() - 4;
            this.aK = this.G() - 4;
         } else if (this.ab == 21518 && this.G() >= 5) {
            this.aw = (this.G() - 4) * 4;
         } else if (this.ab == 21519 && this.G() >= 5) {
            this.aJ = this.G() - 4;
         } else if (this.ab == 21525 && this.G() >= 8) {
            this.aE = this.G() - 7;
         } else if (this.ab == 21526) {
            this.aw = this.G() * 3;
         } else if (this.ab == 21527 && this.G() >= 7) {
            int var16 = Math.min(this.G() - 7, 2);
            int[] var26 = new int[]{5, 10, 20};
            int[] var36 = new int[]{3, 5, 8};
            int[] var44 = new int[]{1, 3, 6};
            int[] var50 = new int[]{0, -1, -2};
            int[] var52 = new int[]{3, 10, 15};
            this.ba = var26[var16];
            this.aY = var26[var16];
            this.aZ = var26[var16];
            this.bd = var36[var16];
            this.az = var44[var16];
            this.aA = var50[var16];
            this.aC = var50[var16];
            this.bq = var52[var16];
         } else if (this.ab == 21528 && this.G() >= 7) {
            int var15 = Math.min(this.G() - 7, 2);
            int[] var25 = new int[]{2, 5, 10};
            int[] var35 = new int[]{3, 5, 8};
            int[] var43 = new int[]{1, 3, 6};
            int[] var49 = new int[]{0, -1, -2};
            int[] var51 = new int[]{3, 10, 15};
            this.aK = var25[var15];
            this.bd = var35[var15];
            this.aA = var43[var15];
            this.az = var49[var15];
            this.aC = var49[var15];
            this.bq = var51[var15];
         } else if (this.ab == 21529 && this.G() >= 7) {
            int var14 = Math.min(this.G() - 7, 2);
            int[] var24 = new int[]{2, 5, 10};
            int[] var34 = new int[]{3, 5, 8};
            int[] var42 = new int[]{1, 3, 6};
            int[] var48 = new int[]{0, -1, -2};
            int[] var6 = new int[]{3, 10, 15};
            this.aI = var24[var14];
            this.bd = var34[var14];
            this.aC = var42[var14];
            this.az = var48[var14];
            this.aA = var48[var14];
            this.bq = var6[var14];
         } else if (this.ab == 21530 && this.G() >= 7) {
            int var13 = Math.min(this.G() - 7, 2);
            int[] var23 = new int[]{5, 10, 15};
            int[] var33 = new int[]{3, 5, 8};
            int[] var41 = new int[]{3, 10, 15};
            this.aW = var23[var13];
            this.aV = var23[var13];
            this.aX = var23[var13];
            this.aw = var23[var13];
            this.be = var33[var13];
            this.bq = var41[var13];
         } else if (this.ab == 21531 && this.G() >= 7) {
            int var12 = Math.min(this.G() - 7, 2);
            int[] var22 = new int[]{10, 20, 30};
            int[] var32 = new int[]{3, 6, 8};
            this.as = var22[var12];
            this.at = var22[var12];
            this.aL = var32[var12];
            this.aG = var32[var12];
         } else if (this.ab == 21532 && this.G() >= 7) {
            int var11 = Math.min(this.G() - 7, 2);
            int[] var21 = new int[]{10, 20, 30};
            int[] var31 = new int[]{3, 6, 8};
            this.as = var21[var11];
            this.at = var21[var11];
            this.aM = var31[var11];
            this.aG = var31[var11];
         } else if (this.ab == 21533 && this.G() >= 7) {
            int var10 = Math.min(this.G() - 7, 2);
            int[] var20 = new int[]{10, 20, 30};
            int[] var30 = new int[]{1, 2, 4};
            int[] var40 = new int[]{3, 6, 8};
            this.as = var20[var10];
            this.at = var20[var10];
            this.av = var30[var10];
            this.aG = var40[var10];
         } else if (this.ab == 21534) {
            int var1 = Math.min(this.G(), 8);
            int[] var2 = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 50};
            int[] var3 = new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3};
            int[] var4 = new int[]{0, 0, 0, 0, 0, 1, 2, 3, 4};
            int[] var5 = new int[]{0, 0, 0, 0, 0, 0, 1, 3, 5};
            this.as = var2[var1];
            this.au = var3[var1];
            this.aL = var4[var1];
            this.bg = var5[var1];
         } else if (this.ab == 21535) {
            int var7 = Math.min(this.G(), 8);
            int[] var17 = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 50};
            int[] var27 = new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3};
            int[] var37 = new int[]{0, 0, 0, 0, 0, 1, 2, 3, 4};
            int[] var45 = new int[]{0, 0, 0, 0, 0, 0, 1, 3, 5};
            this.as = var17[var7];
            this.au = var27[var7];
            this.aM = var37[var7];
            this.bh = var45[var7];
         } else if (this.ab == 21536) {
            int var8 = Math.min(this.G(), 8);
            int[] var18 = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 50};
            int[] var28 = new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3};
            int[] var38 = new int[]{0, 0, 0, 0, 0, 1, 2, 3, 4};
            int[] var46 = new int[]{0, 0, 0, 0, 0, 0, 1, 2, 3};
            this.as = var18[var8];
            this.au = var28[var8];
            this.aJ = var38[var8];
            this.bi = var46[var8];
         } else if (this.ab == 21537) {
            int var9 = Math.min(this.G(), 8);
            int[] var19 = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 50};
            int[] var29 = new int[]{0, 0, 0, 1, 2, 3, 5, 6, 7};
            int[] var39 = new int[]{0, 0, 0, 0, 0, 1, 2, 3, 4};
            int[] var47 = new int[]{0, 0, 0, 0, 0, 1, 3, 5, 7};
            this.as = var19[var9];
            this.au = var29[var9];
            this.aG = var39[var9];
            this.aw = var47[var9];
         } else if (this.ab == 124 && this.G() >= 7) {
            this.av = this.G() - 6;
         } else if (this.ab == 20107 && this.G() >= 3) {
            this.av = this.G() - 2;
         } else if (this.ab >= 393 && this.ab <= 400 && this.G() >= 8) {
            this.bd = this.G() - 8 + 2;
         } else if (this.ab >= 401 && this.ab <= 404) {
            this.bd = this.G() * 3 + 3;
         } else if (this.ab >= 21509 && this.ab <= 21511 && this.G() >= 7) {
            this.as = 10 + (this.G() - 6) * 10;
            this.be = 1 + (this.G() - 6) * 2;
         } else if (this.ab >= 21363 && this.ab <= 21365) {
            this.bK();
         } else if (this.ab >= 21406 && this.ab <= 21408) {
            this.bS();
         } else if (this.ab >= 21409 && this.ab <= 21411) {
            this.bT();
         } else if (this.ab >= 21447 && this.ab <= 21451) {
            this.bR();
         } else if (this.ab >= 21412 && this.ab <= 21414 && this.G() >= 7) {
            this.bN();
         } else if (this.ab >= 21415 && this.ab <= 21417 && this.G() >= 9) {
            this.bO();
         } else if (this.ab >= 21433 && this.ab <= 21436) {
            this.bQ();
         } else if (this.ab == 21441 && this.G() >= 7) {
            this.bP();
         } else if (this.ab == 21442 && this.G() >= 10) {
            this.bg = 7;
         } else if (this.ab == 21443 && this.G() >= 10) {
            this.bi = 7;
         } else if (this.ab == 21444 && this.G() >= 10) {
            this.bh = 7;
         } else if (this.ab == 21445 && this.G() >= 10) {
            this.bf = 100;
         } else if (this.ab == 21372) {
            this.bM();
         } else if (this.ab == 21373) {
            this.bL();
         } else if (this.aa != null && this.i()) {
            this.bI();
         }
      }
   }

   public int o() {
      int var1 = this.aa.R();
      if (this.N() == 21402 || this.N() == 20011 || this.N() == 20110 || this.N() == 21513 || this.N() >= 21123 && this.N() <= 21126) {
         var1 += this.G();
      }

      if (this.N() >= 21241 && this.N() <= 21244 || this.N() == 21383 || this.N() == 20079) {
         var1 += this.G() * 3;
      }

      if (this.N() >= 21319 && this.N() <= 21322) {
         var1 += this.G();
      }

      if (this.N() == 20056 || this.N() == 120056 || this.N() == 220056 || this.N() == 21306) {
         var1 += this.G() * 2;
      }

      return var1;
   }

   public int p() {
      return this.a().l() == 0 ? 0 : Math.max(this.E() * this.a().l() / 1000, 1);
   }

   public void q() {
      Arrays.fill(this.a, this.E());
      Arrays.fill(this.b, this.N());
      Arrays.fill(this.c, this.D());
      Arrays.fill(this.d, this.U());
      Arrays.fill(this.f, this.C());
      Arrays.fill(this.e, this.G());
      Arrays.fill(this.g, this.H());
      Arrays.fill(this.h, this.I());
      Arrays.fill(this.j, this.bb());
      Arrays.fill(this.i, this.M());
      Arrays.fill(this.k, this.J());
      Arrays.fill(this.l, this.F());
      Arrays.fill(this.m, this.K());
      Arrays.fill(this.n, this.L());
      Arrays.fill(this.o, this.X());
      Arrays.fill(this.p, this.Y());
      Arrays.fill(this.q, this.Z());
      Arrays.fill(this.r, this.aa());
   }

   public String r() {
      return this.c(this.R);
   }

   public String c(int var1) {
      StringBuilder var2 = new StringBuilder(this.aL(var1));
      int var3 = this.N();
      if (var3 == 40314 || var3 == 40316) {
         L1Pet var4 = PetTable.a().b(this.fr());
         if (var4 != null) {
            L1Npc var5 = NpcTable.a().a(var4.c());
            var2.append("[Lv." + var4.e() + " " + var4.d() + "]HP" + var4.f() + " " + var5.A());
         }
      }

      if (this.f()) {
         if (this.a().aP() == 2) {
            if (this.T()) {
               var2.append(" ($10)");
            }

            if ((var3 == 40001 || var3 == 40002) && this.M() <= 0) {
               var2.append(" ($11)");
            }
         } else if ((this.a().aP() == 17 || this.a().aP() == 22) && this.U()) {
            var2.append(" ($117)");
         }
      }

      if (this.D()) {
         if (this.g()) {
            var2.append(" ($9)");
         } else if (this.h()) {
            var2.append(" ($117)");
         }
      }

      return var2.toString();
   }

   public String s() {
      return this.aL(this.R);
   }

   private String aL(int var1) {
      StringBuilder var2 = new StringBuilder();
      if (this.C()) {
         if (this.g()) {
            int var3 = this.L();
            if (var3 > 0) {
               if (this.K() == 1) {
                  var2.append(new String[]{"", "$6124", "$6125", "$6126", "$14364", "$14368"}[var3]);
               } else if (this.K() == 2) {
                  var2.append(new String[]{"", "$6115", "$6116", "$6117", "$14361", "$14365"}[var3]);
               } else if (this.K() == 4) {
                  var2.append(new String[]{"", "$6118", "$6119", "$6120", "$14362", "$14366"}[var3]);
               } else if (this.K() == 8) {
                  var2.append(new String[]{"", "$6121", "$6122", "$6123", "$14363", "$14367"}[var3]);
               }
            }
         }

         if (this.g() || this.h()) {
            if (this.G() >= 0) {
               var2.append("+" + this.G() + " ");
            } else if (this.G() < 0) {
               var2.append(String.valueOf(this.G()) + " ");
            }
         }
      }

      if (this.N() == 21363 && this.G() >= 10) {
         var2.append("$23516");
      } else if (this.N() == 21364 && this.G() >= 10) {
         var2.append("$23515");
      } else if (this.N() == 21365 && this.G() >= 10) {
         var2.append("$23517");
      } else {
         var2.append(this.c());
      }

      if (this.C()) {
         if (this.a().aM() > 0) {
            var2.append(" (" + this.I() + ")");
         }

         if (this.N() == 20383) {
            var2.append(" (" + this.I() + ")");
         }

         if (this.a().T() > 0 && !this.f()) {
            var2.append(" [" + this.M() + "]");
         }
      }

      if (this.N() == 640615 && this.M() != 0) {
         var2.append(" -" + (this.M() - 1399));
      }

      if (this.bb() != null) {
         SimpleDateFormat var5 = new SimpleDateFormat(" [MM-dd HH:mm]");
         String var4 = var5.format(this.bb());
         var2.append(var4);
      }

      if (this.N() == 40312 && this.M() != 0) {
         var2.append(InnTable.a(this));
      }

      if (var1 > 1) {
         var2.append(" (" + var1 + ")");
      }

      return var2.toString();
   }

   public byte[] t() {
      BinaryOutputStream var1 = new BinaryOutputStream();
      L1PetItem var2 = PetItemTable.a().a(this.N());
      if (var2 != null) {
         if (var2.n() == 1) {
            var1.c(1);
            var1.c(this.a().v());
            var1.c(this.a().w());
            var1.c(this.a().k());
            var1.a(this.p());
         } else {
            var1.c(19);
            var1.c(-var2.d());
            var1.c(this.a().k());
            var1.c(-1);
            var1.a(this.p());
         }

         var1.c(62);
         var1.c(1);
         if (var2.b() != 0) {
            var1.c(5);
            var1.c(var2.b());
         }

         if (var2.c() != 0) {
            var1.c(6);
            var1.c(var2.c());
         }

         if (var2.e() != 0) {
            var1.c(8);
            var1.c(var2.e());
         }

         if (var2.g() != 0) {
            var1.c(9);
            var1.c(var2.g());
         }

         if (var2.f() != 0) {
            var1.c(10);
            var1.c(var2.f());
         }

         if (var2.i() != 0) {
            var1.c(11);
            var1.c(var2.i());
         }

         if (var2.h() != 0) {
            var1.c(12);
            var1.c(var2.h());
         }

         if (var2.j() != 0) {
            var1.c(14);
            var1.b(var2.j());
         }

         if (var2.m() != 0) {
            var1.c(15);
            var1.b(var2.m());
         }

         if (var2.l() != 0) {
            var1.c(17);
            var1.c(var2.l());
         }

         if (var2.k() != 0) {
            var1.c(32);
            var1.c(var2.k());
         }
      } else {
         if (this.f()) {
            switch (this.a().aP()) {
               case 0:
               case 15:
                  var1.c(1);
                  var1.c(this.a().v());
                  var1.c(this.a().w());
                  break;
               case 2:
                  var1.c(22);
                  var1.b(this.a().c());
                  break;
               case 7:
                  var1.c(21);
                  var1.b(this.a().V());
                  break;
               case 29:
                  var1.c(21);
                  var1.b(120);
                  break;
               default:
                  var1.c(23);
            }

            var1.c(this.a().k());
            var1.a(this.p());
         } else {
            if (this.g()) {
               var1.c(1);
               var1.c(this.a().v());
               var1.c(this.a().w());
               var1.c(this.a().k());
               var1.a(this.p());
            }

            if (this.h()) {
               var1.c(19);
               int var3 = this.a().X();
               if (var3 < 0) {
                  var3 = -var3;
               }

               var1.c(var3);
               var1.c(this.a().k());
               var1.c(43 + this.a().ap());
               var1.a(this.p());
            }

            if (this.G() != 0) {
               var1.c(2);
               if (this.i()) {
                  var1.c(0);
               } else {
                  var1.c(this.G());
               }
            }

            if (this.H() != 0) {
               var1.c(3);
               var1.c(this.H());
            }

            if (this.a().e()) {
               var1.c(4);
            }

            int var12 = 0;
            var12 |= this.a().y() ? 1 : 0;
            var12 |= this.a().z() ? 2 : 0;
            var12 |= this.a().A() ? 4 : 0;
            var12 |= this.a().B() ? 8 : 0;
            var12 |= this.a().C() ? 16 : 0;
            var12 |= this.a().D() ? 32 : 0;
            var12 |= this.a().E() ? 64 : 0;
            var12 |= this.a().F() ? 128 : 0;
            var1.c(7);
            var1.c(var12);
         }

         if (this.a().G() + this.az != 0) {
            var1.c(8);
            var1.c(this.a().G() + this.az);
         }

         if (this.a().H() + this.aA != 0) {
            var1.c(9);
            var1.c(this.a().H() + this.aA);
         }

         if (this.a().I() + this.aB != 0) {
            var1.c(10);
            var1.c(this.a().I() + this.aB);
         }

         if (this.a().K() + this.aD != 0) {
            var1.c(11);
            var1.c(this.a().K() + this.aD);
         }

         if (this.a().J() + this.aC != 0) {
            var1.c(12);
            var1.c(this.a().J() + this.aC);
         }

         if (this.a().L() + this.aE != 0) {
            var1.c(13);
            var1.c(this.a().L() + this.aE);
         }

         if (this.N() == 21204) {
            if (this.a().M() != 0) {
               var1.c(14);
               var1.b(this.a().M());
            }
         } else if (this.a().M() + this.as != 0) {
            var1.c(14);
            var1.b(this.a().M() + this.as);
         }

         if (this.o() + this.aw != 0) {
            var1.c(15);
            var1.b(this.o() + this.aw);
         }

         if (this.N() == 126 || this.N() == 127) {
            var1.c(16);
         }

         if (this.a().Q() + this.av != 0) {
            var1.c(17);
            var1.c(this.a().Q() + this.av);
         }

         if (this.a().S() || this.bS) {
            var1.c(18);
         }

         if (this.a().ab() + this.aK != 0) {
            var1.c(24);
            var1.c(this.a().ab() + this.aK);
         }

         if (this.a().ag() + this.aP != 0) {
            var1.c(27);
            var1.c(this.a().ag() + this.aP);
         }

         if (this.a().ae() + this.aN != 0) {
            var1.c(28);
            var1.c(this.a().ae() + this.aN);
         }

         if (this.a().af() + this.aO != 0) {
            var1.c(29);
            var1.c(this.a().af() + this.aO);
         }

         if (this.a().ah() + this.aQ != 0) {
            var1.c(30);
            var1.c(this.a().ah() + this.aQ);
         }

         if (this.a().N() + this.at != 0) {
            var1.c(32);
            var1.b(this.a().N() + this.at);
         }

         if (this.a().al() != 0) {
            var1.c(33);
            var1.c(1);
            var1.c(this.a().al());
         }

         if (this.a().aj() + this.aR != 0) {
            var1.c(33);
            var1.c(2);
            var1.c(this.a().aj() + this.aR);
         }

         if (this.a().ak() != 0) {
            var1.c(33);
            var1.c(3);
            var1.c(this.a().ak());
         }

         if (this.a().an() != 0) {
            var1.c(33);
            var1.c(4);
            var1.c(this.a().an());
         }

         if (this.a().ai() + this.aW != 0) {
            var1.c(33);
            var1.c(5);
            var1.c(this.a().ai() + this.aW);
         }

         if (this.a().am() + this.aV != 0) {
            var1.c(33);
            var1.c(6);
            var1.c(this.a().am() + this.aV);
         }

         if (this.a().ao() != 0) {
            var1.c(33);
            var1.c(7);
            var1.c(this.a().ao());
         }

         if (this.N() == 262) {
            var1.c(34);
         }

         if (this.a().ad() + this.aM != 0) {
            var1.c(35);
            var1.c(this.a().ad() + this.aM);
         }

         if (this.a().aw() + this.bW + this.bj != 0) {
            var1.c(36);
            var1.c(this.a().aw() + this.bW + this.bj);
         }

         if (this.a().O() + this.ax != 0) {
            var1.c(37);
            var1.c(this.a().O() + this.ax);
         }

         if (this.a().P() + this.ay != 0) {
            var1.c(38);
            var1.c(this.a().P() + this.ay);
         }

         if (this.a().as() + this.aI != 0) {
            var1.c(40);
            var1.c(this.a().as() + this.aI);
         }

         if (this.ab >= 21246 && this.ab <= 21257) {
            int var21 = this.bJ();
            if (var21 > 0) {
               var1.c(45);
               var1.c(var21);
            }
         }

         if (this.a().ac() + this.aL != 0) {
            var1.c(47);
            var1.c(this.a().ac() + this.aL);
            if (this.bK != 0) {
               var1.c(39);
               var1.a("$20274:" + this.bK + "%");
            }
         }

         if (this.a().aa() + this.aJ != 0) {
            var1.c(48);
            var1.c(this.a().aa() + this.aJ);
         }

         if (this.a().at() + this.bi != 0) {
            var1.c(50);
            var1.b(this.a().at() + this.bi);
         }

         if (this.bb != 0) {
            var1.c(51);
            var1.c(this.bb);
         }

         if (this.au != 0) {
            var1.c(56);
            var1.c(this.au);
         }

         if (this.bT) {
            var1.c(57);
            var1.a(18976);
         }

         if (this.B()) {
            var1.c(57);
            var1.a(19128);
         }

         if (this.a().aq() + this.bd != 0) {
            var1.c(59);
            var1.c(this.a().aq() + this.bd);
         }

         if (this.a().ar() + this.be != 0) {
            var1.c(60);
            var1.c(this.a().ar() + this.be);
         }

         if (this.bb() != null) {
            try {
               var1.c(61);
               Date var22 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("1997-01-01 16:00");
               var1.a(((int)(this.bb().getTime() / 1000L) - (int)(var22.getTime() / 1000L)) * 6);
            } catch (ParseException var11) {
            }
         }

         if (this.a().Y() + this.aG != 0) {
            var1.c(63);
            var1.c(this.a().Y() + this.aG);
            if (this.bP > 0) {
               var1.c(39);
               var1.a("$20274:" + this.bP + "%");
            }
         }

         if (this.a().au() + this.aF != 0) {
            var1.c(65);
            var1.c(this.a().au() + this.aF);
            var1.c(1);
         }

         if (this.i()) {
            var1.c(67);
            if (this.a().aP() == 11 || this.a().aP() == 13) {
               var1.c(43);
            } else if (this.a().aP() == 9) {
               var1.c(44);
            } else if (this.a().aP() == 12) {
               var1.c(45);
            }
         }

         if (this.bQ + this.bk != 0) {
            var1.c(68);
            var1.c(this.bQ + this.bk);
         }

         if (this.bX != null) {
            var1.c(74);
            var1.a(this.bX);
         }

         if (this.bV != 0) {
            var1.c(87);
            var1.c(this.bV);
         }

         if (this.bU != 0) {
            var1.c(88);
            var1.c(this.bU);
         }

         if (this.a().Z() != 0) {
            var1.c(90);
            var1.b(this.a().Z());
         }

         if (this.bc != 0) {
            var1.c(93);
            var1.c(this.bc);
         }

         if (this.bo) {
            var1.c(94);
         }

         if (this.a().av() + this.aH != 0) {
            var1.c(97);
            var1.c(this.a().av() + this.aH);
         }

         if (this.a().ay() + this.aY != 0) {
            var1.c(98);
            var1.a(23521);
            var1.b(this.a().ay() + this.aY);
         }

         if (this.a().az() + this.aZ != 0) {
            var1.c(98);
            var1.a(24131);
            var1.b(this.a().az() + this.aZ);
         }

         if (this.a().aA() + this.ba != 0) {
            var1.c(98);
            var1.a(11147);
            var1.b(this.a().aA() + this.ba);
         }

         if (this.bh != 0) {
            var1.c(99);
            var1.c(this.bh);
         }

         if (this.bg != 0) {
            var1.c(100);
            var1.c(this.bg);
         }

         if (this.br != 0) {
            var1.c(101);
            var1.c(this.br);
         }

         if (this.bp != 0) {
            var1.c(102);
            var1.c(this.bp);
         }

         if (this.bf != 0) {
            var1.c(104);
            var1.b(this.bf);
         }

         if (this.N() == 21204) {
            var1.c(49);
            var1.c(1);

            for (int var23 = 1; var23 <= 4; var23++) {
               String var4 = this.aM(var23);
               if (var4 != null) {
                  if (var4.contains(",")) {
                     String[] var8;
                     int var7 = (var8 = var4.split(",")).length;

                     for (int var6 = 0; var6 < var7; var6++) {
                        String var5 = var8[var6];
                        var1.c(39);
                        var1.a(var5);
                     }
                  } else {
                     var1.c(39);
                     var1.a(var4);
                  }
               }
            }

            var1.c(49);
            var1.c(0);
         }

         ArrayList var24 = ArmorSetTable.a().a(this.N());

         for (ArmorSetTable.L1R_a var26 : var24) {
            if (var26.b().length > 1) {
               var1.c(39);
               var1.a("\\aL" + var26.A());
               if (var24.size() == 1) {
                  int[] var9;
                  int var30 = (var9 = var26.b()).length;

                  for (int var29 = 0; var29 < var30; var29++) {
                     int var28 = var9[var29];
                     var1.c(39);
                     var1.a("\\fR " + ItemTable.a().a(var28).j());
                  }
               }

               var1.c(69);
               var1.c(this.W() ? 1 : 2);
            }

            if (var26.c() > 0 && var26.d() > 0) {
               var1.c(71);
               var1.b(var26.d());
            }

            if (var26.e() < 0) {
               var1.c(56);
               var1.c(-var26.e());
            }

            if (var26.f() > 0) {
               var1.c(14);
               var1.b(var26.f());
            }

            if (var26.g() > 0) {
               var1.c(32);
               var1.b(var26.g());
            }

            if (var26.h() > 0) {
               var1.c(37);
               var1.c(var26.h());
            }

            if (var26.i() > 0) {
               var1.c(38);
               var1.c(var26.i());
            }

            if (var26.j() > 0) {
               var1.c(15);
               var1.b(var26.j());
            }

            if (var26.k() > 0) {
               var1.c(8);
               var1.c(var26.k());
            }

            if (var26.l() > 0) {
               var1.c(9);
               var1.c(var26.l());
            }

            if (var26.m() > 0) {
               var1.c(10);
               var1.c(var26.m());
            }

            if (var26.n() > 0) {
               var1.c(11);
               var1.c(var26.n());
            }

            if (var26.o() > 0) {
               var1.c(13);
               var1.c(var26.o());
            }

            if (var26.p() > 0) {
               var1.c(12);
               var1.c(var26.p());
            }

            if (var26.u() > 0) {
               var1.c(48);
               var1.c(var26.u());
            }

            if (var26.v() > 0) {
               var1.c(47);
               var1.c(var26.v());
            }

            if (var26.w() > 0) {
               var1.c(24);
               var1.c(var26.w());
            }

            if (var26.x() > 0) {
               var1.c(35);
               var1.c(var26.x());
            }

            if (var26.y() > 0) {
               var1.c(17);
               var1.c(var26.y());
            }

            if (var26.q() > 0) {
               var1.c(28);
               var1.c(var26.q());
            }

            if (var26.r() > 0) {
               var1.c(29);
               var1.c(var26.r());
            }

            if (var26.s() > 0) {
               var1.c(27);
               var1.c(var26.s());
            }

            if (var26.t() > 0) {
               var1.c(30);
               var1.c(var26.t());
            }

            if (var26.z() > 0) {
               var1.c(63);
               var1.c(var26.z());
            }

            if (var26.b().length > 1) {
               var1.c(69);
               var1.c(0);
            }
         }

         if (!this.f()) {
            var1.c(39);
            var1.a("安定值:" + (this.a().x() < 0 ? "不可強化" : this.a().x()));
         }

         if (this.h()) {
            if (this.a().aP() == 15) {
               var1.c(39);
               var1.a("\\aE第1輔助欄位");
            } else if (this.a().aP() == 18) {
               var1.c(39);
               var1.a("\\aE第2輔助欄位");
            } else if (this.a().aP() == 16) {
               var1.c(39);
               var1.a("\\aE第3輔助欄位");
            }
         }

         if (this.g() && (!this.a().aE() || this.a().aO() == 0 || this.a().aO() == 40 || this.a().aO() == 58 || this.a().aO() == 58)) {
            var1.c(39);
            var1.a("不會損壞");
         }
      }

      byte[] var25 = var1.b();

      try {
         var1.close();
      } catch (IOException var10) {
         O.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      }

      return var25;
   }

   private void f(L1PcInstance var1) {
      if (this.ah != null && this.ah.bE() > 0) {
         this.ah.a(new S_ServerMessage(308, this.s()));
      }

      if (var1 != null && var1 == this.ah && var1.j().f(this.N()) && this.h() && this.D() && this.a().aP() == 2) {
         var1.bL(3);
         var1.a(new S_OwnCharStatus(var1));
      }

      this.ac = 0;
      this.ad = 0;
      this.ae = 0;
      this.af = 0;
      this.ag = null;
   }

   public void b(L1PcInstance var1, int var2) {
      if (var1 != null && this.h() && this.a().aP() == 2) {
         if (this.ag != null) {
            this.ag.cancel(true);
            this.f(var1);
         }

         if (this.D()) {
            var1.bL(-3);
            var1.a(new S_OwnCharStatus(var1));
         }

         this.ac = 3;
         this.ah = var1;
         this.ag = GeneralThreadPool.a().a(new L1ItemInstance.L1R_b(null), var2);
      }
   }

   public void a(L1PcInstance var1, int var2, int var3) {
      if (var1 != null && this.g()) {
         if (this.ag != null) {
            this.ag.cancel(true);
            this.f(var1);
         }

         switch (var2) {
            case 8:
               this.ae = 1;
               this.af = 1;
               break;
            case 12:
               this.ad = 2;
               break;
            case 48:
               this.ad = 2;
               this.af = 2;
               break;
            case 107:
               this.ad = 2;
         }

         this.ah = var1;
         this.ag = GeneralThreadPool.a().a(new L1ItemInstance.L1R_b(null), var3);
      }
   }

   public void d(L1PcInstance var1) {
      this.ai = var1.fr();
      GeneralThreadPool.a().a(new L1ItemInstance.L1R_c(null), 10000L);
   }

   public void e(L1PcInstance var1) {
      if (this.M() > 0) {
         this.aj = GeneralThreadPool.a().a(new L1ItemInstance.L1R_a(var1, null), 1000L, 1000L);
      }
   }

   public void u() {
      if (this.aj != null) {
         this.aj.cancel(true);
      }
   }

   public int v() {
      if (!this.C()) {
         return 0;
      }

      int var1 = 1;
      if (!this.a().s()) {
         var1 |= 2;
      }

      if (this.a().t()) {
         var1 |= 4;
      }

      if (this.a().x() < 0) {
         var1 |= 8;
      }

      if (this.a().x() < 0) {
         var1 |= 16;
      }

      int var2 = this.F();
      if (var2 >= 128 && var2 <= 131) {
         var1 |= 2;
         var1 |= 4;
         var1 |= 8;
         var1 |= 32;
      } else if (var2 > 131) {
         var1 |= 64;
      }

      if (this.a().aF()) {
         var1 |= 128;
      }

      return var1;
   }

   private void bI() {
      int var1 = Math.min(this.G(), 9);
      if (this.a().aP() == 11 || this.a().aP() == 13) {
         int[] var10 = new int[]{0, 5, 10, 20, 30, 40, 40, 50, 50, 100};
         int[] var12 = new int[]{0, 0, 0, 0, 1, 2, 4, 5, 6, 8};
         int[] var14 = new int[]{0, 0, 0, 0, 0, 4, 6, 10, 14, 16};
         int[] var16 = new int[]{0, 0, 0, 0, 0, 1, 2, 3, 4, 5};
         int[] var18 = new int[]{0, 0, 0, 0, 0, 0, 2, 4, 8, 10};
         int[] var20 = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 2, 5};
         int[] var21 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 10};
         this.as = var10[var1];
         this.aL = var12[var1];
         this.aM = var12[var1];
         this.aF = var14[var1];
         this.aG = var16[var1];
         this.aI = var18[var1];
         this.bd = var20[var1];
         this.aw = var21[var1];
      } else if (this.a().aP() == 9) {
         int[] var2 = new int[]{0, 5, 10, 20, 30, 40, 40, 50, 50, 100};
         int[] var3 = new int[]{0, 0, 0, 0, 1, 3, 4, 4, 5, 7};
         int[] var4 = new int[]{0, 0, 0, 0, 1, 3, 3, 4, 5, 7};
         int[] var5 = new int[]{0, 0, 0, 0, 0, 1, 3, 4, 5, 7};
         int[] var6 = new int[]{0, 0, 0, 0, 0, 0, 1, 2, 4, 5};
         int[] var7 = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 2, 5};
         int[] var8 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 5};
         this.as = var2[var1];
         this.aL = var3[var1];
         this.aM = var3[var1];
         this.aG = var4[var1];
         this.au = var5[var1];
         this.av = var6[var1];
         this.bd = var7[var1];
         this.aX = var8[var1];
      } else if (this.a().aP() == 12) {
         int[] var9 = new int[]{0, 5, 10, 20, 30, 40, 40, 50, 50, 75};
         int[] var11 = new int[]{0, 0, 0, 0, 1, 2, 4, 4, 5, 7};
         int[] var13 = new int[]{0, 0, 0, 0, 0, 3, 5, 7, 10, 15};
         int[] var15 = new int[]{0, 0, 0, 0, 0, 0, 1, 2, 3, 5};
         int[] var17 = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 2, 5};
         int[] var19 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 5};
         this.at = var9[var1];
         this.aG = var11[var1];
         this.aw = var13[var1];
         this.au = var15[var1];
         this.be = var17[var1];
         this.aX = var19[var1];
      }
   }

   private int bJ() {
      int var1 = 0;
      int var2 = this.ao;
      if ((var2 & 8) == 8) {
         var1 = 1;
      } else if ((var2 & 131072) == 131072) {
         var1 = 7;
      } else if ((var2 & 32) == 32) {
         var1 = 10;
      } else if ((var2 & 16) == 16) {
         var1 = 11;
      } else if ((var2 & 3) == 3) {
         var1 = 12;
      } else if ((var2 & 65536) == 65536) {
         var1 = 13;
      } else if ((var2 & 4) == 4) {
         var1 = 14;
      } else if ((var2 & 16384) == 16384) {
         var1 = 15;
      } else if ((var2 & 32768) == 32768) {
         var1 = 16;
      }

      return var1;
   }

   private void bK() {
      int var1 = Math.min(this.G(), 10);
      int[] var2 = new int[]{0, 0, 10, 10, 20, 20, 30, 30, 40, 50, 60};
      int[] var3 = new int[]{0, 0, 0, 0, 0, 0, 1, 2, 2, 3, 3};
      int[] var4 = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 2, 4, 5};
      int[] var5 = new int[]{0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 4};
      if (this.N() == 21363) {
         int[] var6 = new int[]{0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 4};
         this.as = var2[var1];
         this.av = var6[var1];
         this.aG = var3[var1];
         this.bd = var4[var1];
      } else if (this.N() == 21364) {
         this.as = var2[var1];
         this.aL = var5[var1];
         this.aG = var3[var1];
         this.bd = var4[var1];
      } else if (this.N() == 21365) {
         this.as = var2[var1];
         this.aM = var5[var1];
         this.aG = var3[var1];
         this.bd = var4[var1];
      }
   }

   private void bL() {
      int var1 = Math.min(this.G(), 9);
      int[] var2 = new int[]{10, 10, 10, 10, 20, 30, 40, 50, 70, 100};
      int[] var3 = new int[]{10, 10, 10, 10, 20, 20, 20, 20, 20, 30};
      int[] var4 = new int[]{0, 0, 0, 0, 0, 0, 0, 5, 15, 30};
      int[] var5 = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 3, 5};
      int[] var6 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 3};
      this.as = var2[var1];
      this.at = var3[var1];
      this.aQ = var4[var1];
      this.aP = var4[var1];
      this.aN = var4[var1];
      this.aO = var4[var1];
      this.ax = var5[var1];
      this.ay = var5[var1];
      this.aG = var6[var1];
   }

   private void bM() {
      int var1 = Math.min(this.G(), 9);
      int[] var2 = new int[]{10, 10, 10, 10, 20, 30, 40, 50, 70, 100};
      int[] var3 = new int[]{10, 10, 10, 10, 20, 20, 20, 20, 20, 30};
      int[] var4 = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 3, 5};
      int[] var5 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 2};
      int[] var6 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
      this.as = var2[var1];
      this.at = var3[var1];
      this.aL = var4[var1];
      this.aM = var4[var1];
      this.av = var5[var1];
      this.az = var6[var1];
      this.aC = var6[var1];
      this.aA = var6[var1];
   }

   private void bN() {
      this.aw = 15 + (this.G() - 7) * 2;
      this.aG = 5;
      if (this.N() == 21412) {
         this.az = 2;
         this.as = 15;
         this.ax = 3;
      } else if (this.N() == 21413) {
         this.aA = 2;
         this.as = 10;
         this.at = 10;
         this.ax = 2;
         this.ay = 1;
      } else if (this.N() == 21414) {
         this.aC = 2;
         this.as = 10;
         this.at = 10;
         this.ay = 3;
      }
   }

   private void bO() {
      int var1 = Math.min(this.G() - 9, 2);
      int[] var2 = new int[]{4, 4, 5};
      int[] var3 = new int[]{20, 22, 25};
      int[] var4 = new int[]{5, 6, 7};
      int[] var5 = new int[]{50, 70, 100};
      int[] var6 = new int[]{25, 35, 50};
      int[] var7 = new int[]{10, 12, 15};
      int[] var8 = new int[]{0, 2, 5};
      this.aG = 7;
      this.bn = true;
      this.bj = 20;
      this.aw = var3[var1];
      this.aW = var7[var1];
      this.be = var8[var1];
      if (this.N() == 21415) {
         this.az = var2[var1];
         this.ax = var4[var1];
         this.as = var5[var1];
      } else if (this.N() == 21416) {
         this.aA = var2[var1];
         this.ax = var4[var1];
         this.as = var5[var1];
      } else if (this.N() == 21417) {
         this.aC = var2[var1];
         this.ay = var4[var1];
         this.as = var6[var1];
         this.at = var6[var1];
      }
   }

   private void bP() {
      int var1 = Math.min(this.G() - 7, 3);
      int[] var2 = new int[]{1, 2, 3, 3};
      int[] var3 = new int[]{70, 80, 90, 100};
      int[] var4 = new int[]{30, 40, 50, 60};
      int[] var5 = new int[]{6, 7, 8, 9};
      int[] var6 = new int[]{3, 5, 5, 5};
      int[] var7 = new int[]{1, 3, 5, 7};
      this.az = var2[var1];
      this.aC = var2[var1];
      this.aA = var2[var1];
      this.as = var3[var1];
      this.at = var4[var1];
      this.aL = var5[var1];
      this.aM = var5[var1];
      this.av = var5[var1];
      this.aJ = var5[var1];
      this.aK = var5[var1];
      this.ax = var6[var1];
      this.ay = var6[var1];
      this.bd = var7[var1];
   }

   private void bQ() {
      int var1 = Math.min(this.G(), 5);
      int[] var2 = new int[]{0, 10, 20, 30, 40, 50};
      int[] var3 = new int[]{0, 1, 1, 2, 2, 5};
      int[] var4 = new int[]{0, 1, 1, 2, 3, 5};
      int[] var5 = new int[]{0, 0, 0, 0, 1, 3};
      int[] var6 = new int[]{0, 0, 0, 0, 0, 1};
      this.as = var2[var1];
      this.at = var2[var1];
      this.ax = var4[var1];
      this.ay = var4[var1];
      this.aG = var3[var1];
      this.av = var5[var1];
      this.az = var6[var1];
      this.aA = var6[var1];
      this.aB = var6[var1];
      this.aC = var6[var1];
      this.aD = var6[var1];
      this.aE = var6[var1];
   }

   private void bR() {
      int var1 = Math.min(this.G(), 9);
      int[] var2 = new int[]{0, 15, 20, 25, 30, 35, 40, 45, 50, 55};
      int[] var3 = new int[]{0, 0, 1, 2, 3, 3, 3, 3, 3, 3};
      int[] var4 = new int[]{0, 0, 0, 0, 0, 1, 2, 3, 4, 5};
      this.as = var2[var1];
      this.au = var3[var1];
      this.aG = var4[var1];
   }

   private void bS() {
      int var1 = Math.min(this.G(), 5);
      int[] var2 = new int[]{0, 0, 0, 0, 0, 1};
      int[] var3 = new int[]{0, 0, 3, 5, 7, 15};
      int[] var4 = new int[]{0, 0, 90, 100, 110, 120};
      int[] var5 = new int[]{0, 0, 70, 80, 90, 100};
      int[] var6 = new int[]{0, 0, 1, 1, 2, 3};
      int[] var7 = new int[]{0, 0, 3, 3, 5, 8};
      int[] var8 = new int[]{0, 0, 0, 10, 12, 16};
      int[] var9 = new int[]{0, 0, 0, 5, 10, 20};
      this.au = var2[var1];
      this.aw = var3[var1];
      this.as = var4[var1];
      this.at = var5[var1];
      this.aG = var7[var1];
      this.aF = var8[var1];
      this.bj = var9[var1];
      if (this.N() == 21406) {
         this.az = var6[var1];
         this.aL = var7[var1];
         this.aJ = var7[var1];
      } else if (this.N() == 21407) {
         this.aA = var6[var1];
         this.aM = var7[var1];
         this.aK = var7[var1];
      } else if (this.N() == 21408) {
         this.aC = var6[var1];
         this.av = var6[var1];
         this.aI = var7[var1];
         this.aJ = var7[var1];
      }
   }

   private void bT() {
      int var1 = Math.min(this.G(), 5);
      int[] var2 = new int[]{70, 80, 90, 100, 110, 120};
      int[] var3 = new int[]{50, 60, 70, 80, 90, 100};
      int[] var4 = new int[]{1, 1, 1, 2, 3, 3};
      int[] var5 = new int[]{1, 1, 1, 2, 3, 5};
      int[] var6 = new int[]{1, 1, 3, 5, 7, 10};
      int[] var7 = new int[]{3, 3, 3, 5, 7, 10};
      int[] var8 = new int[]{0, 0, 0, 1, 2, 3};
      int[] var9 = new int[]{0, 0, 0, 5, 7, 15};
      this.as = var2[var1];
      this.at = var3[var1];
      this.be = var6[var1];
      this.bd = var7[var1];
      this.aW = var9[var1];
      if (this.N() == 21409) {
         this.az = var4[var1];
         this.aL = var6[var1];
         this.aJ = var6[var1];
         this.bg = var8[var1];
      } else if (this.N() == 21410) {
         this.aA = var4[var1];
         this.aM = var6[var1];
         this.aK = var6[var1];
         this.bh = var8[var1];
      } else if (this.N() == 21411) {
         this.aC = var4[var1];
         this.av = var5[var1];
         this.aI = var6[var1];
         this.aJ = var6[var1];
         this.bi = var8[var1];
      }
   }

   public void w() {
      this.aN = 0;
      this.aQ = 0;
      this.aP = 0;
      this.aO = 0;
      this.aR = 0;
      this.aW = 0;
      this.aV = 0;
      this.ax = 0;
      this.ay = 0;
      this.au = 0;
      this.aw = 0;
      this.as = 0;
      this.at = 0;
      int var1 = this.ao;
      if ((var1 & 8) == 8) {
         this.aN = 10;
         this.aQ = 10;
         this.aP = 10;
         this.aO = 10;
      } else if ((var1 & 131072) == 131072) {
         this.aR = 10;
      } else if ((var1 & 32) == 32) {
         this.aW = 10;
      } else if ((var1 & 16) == 16) {
         this.aV = 10;
      } else if ((var1 & 1) == 1) {
         this.ax = 1;
      } else if ((var1 & 2) == 2) {
         this.ay = 1;
      } else if ((var1 & 65536) == 65536) {
         this.au = 1;
      } else if ((var1 & 4) == 4) {
         this.aw = 10;
      } else if ((var1 & 16384) == 16384) {
         this.as = 50;
      } else if ((var1 & 32768) == 32768) {
         this.at = 30;
      }
   }

   public void d(int var1) {
      if (var1 == 0) {
         this.aG = 3;
      } else if (var1 == 1) {
         this.as = 50;
      } else if (var1 == 2) {
         this.at = 50;
      } else if (var1 == 3) {
         this.ay = 3;
      } else if (var1 == 4) {
         this.au = 3;
      } else if (var1 == 5) {
         this.aJ = 3;
      } else if (var1 == 6) {
         this.bk = 5;
      } else if (var1 == 7) {
         this.as = 50;
      }

      if (this.N() >= 21345 && this.N() <= 21349) {
         if (var1 == 0) {
            this.aJ = 2;
         } else if (var1 == 1) {
            this.aL = 1;
         } else if (var1 == 2) {
            this.aM = 1;
         } else if (var1 == 3) {
            this.av = 1;
         } else if (var1 == 4) {
            this.at = 30;
         } else if (var1 == 5) {
            this.aG = 1;
         } else if (var1 == 6) {
            this.as = 50;
         } else if (var1 == 7) {
            this.aw = 5;
         }
      }
   }

   public void x() {
      this.as = 0;
      this.at = 0;
      this.bU = 0;
      this.au = 0;
      this.aw = 0;
      this.aG = 0;
      MagicDollTable.b().a(this);
      if (this.ao > 0) {
         int var1 = this.ao;
         if ((var1 & 16384) == 16384) {
            this.as += 10;
            this.bU += 3;
         } else if ((var1 & 65536) == 65536) {
            this.au++;
            this.aw++;
         } else if ((var1 & 32768) == 32768) {
            this.at += 10;
            this.as += 30;
         } else if ((var1 & 2) == 2) {
            this.bU += 8;
         } else if ((var1 & 262144) == 262144) {
            this.aG++;
         }
      } else if (this.ap > 0) {
         int var2 = this.ap;
         if ((var2 & 16384) == 16384) {
            this.as += 20;
            this.bU += 5;
         } else if ((var2 & 65536) == 65536) {
            this.au += 5;
            this.aw += 5;
         } else if ((var2 & 32768) == 32768) {
            this.at += 30;
            this.as += 50;
         } else if ((var2 & 2) == 2) {
            this.bU += 15;
         } else if ((var2 & 262144) == 262144) {
            this.aG += 2;
         }
      } else if (this.aq > 0) {
         int var3 = this.aq;
         if ((var3 & 16384) == 16384) {
            this.as += 50;
            this.bU += 10;
            this.aG++;
         } else if ((var3 & 65536) == 65536) {
            this.au += 10;
            this.aw += 10;
            this.aG++;
         } else if ((var3 & 32768) == 32768) {
            this.at += 70;
            this.as += 80;
         } else if ((var3 & 2) == 2) {
            this.bU += 21;
         } else if ((var3 & 262144) == 262144) {
            this.aG += 7;
         }
      }
   }

   public void y() {
      for (int var1 = 1; var1 <= 4; var1++) {
         int var2 = 0;
         if (var1 == 1) {
            var2 = this.ao;
         } else if (var1 == 2) {
            var2 = this.ap;
         } else if (var1 == 3) {
            var2 = this.aq;
         } else if (var1 == 4) {
            var2 = this.ar;
         }

         int var3 = 0;
         if ((var2 & 2048) == 2048) {
            var3 = 1;
         } else if ((var2 & 4096) == 4096) {
            var3 = 2;
         } else if ((var2 & 8192) == 8192) {
            var3 = 3;
         } else if ((var2 & 1048576) == 1048576) {
            var3 = 4;
         }

         if ((var2 & 1) == 1) {
            this.ax = var3;
         } else if ((var2 & 2) == 2) {
            this.ay = var3;
         } else if ((var2 & 4) == 4) {
            this.aw = var3;
         } else if ((var2 & 8) == 8) {
            this.aN = 3 + var3 * 3;
            this.aQ = 3 + var3 * 3;
            this.aP = 3 + var3 * 3;
            this.aO = 3 + var3 * 3;
         } else if ((var2 & 16) == 16) {
            this.aV = var3;
         } else if ((var2 & 32) == 32) {
            this.aW = var3;
         } else if ((var2 & 131072) == 131072) {
            this.aR = var3;
         } else if ((var2 & 64) == 64) {
            this.aL = var3;
         } else if ((var2 & 128) == 128) {
            this.aM = var3;
         } else if ((var2 & 524288) == 524288) {
            this.aJ = var3;
            this.aK = var3;
         } else if ((var2 & 256) == 256) {
            this.bb = var3;
            this.bd = var3;
         } else if ((var2 & 512) == 512) {
            this.bc = var3;
            this.be = var3;
         } else if ((var2 & 1024) == 1024) {
            this.av = var3;
         } else if ((var2 & 16384) == 16384) {
            this.as = var3 * 30;
         } else if ((var2 & 32768) == 32768) {
            this.at = var3 * 20;
         } else if ((var2 & 65536) == 65536) {
            this.au = var3;
         } else if ((var2 & 262144) == 262144) {
            this.aG = var3;
         }
      }
   }

   public void z() {
      this.ax = 0;
      this.ay = 0;
      this.aV = 0;
      this.aW = 0;
      this.as = 0;
      this.at = 0;
      this.au = 0;
      int var1 = this.ao;
      if ((var1 & 1) == 1) {
         this.ax = 1;
      } else if ((var1 & 2) == 2) {
         this.ay = 1;
      } else if ((var1 & 16) == 16) {
         this.aV = 10;
      } else if ((var1 & 32) == 32) {
         this.aW = 10;
      } else if ((var1 & 16384) == 16384) {
         this.as = 50;
      } else if ((var1 & 32768) == 32768) {
         this.at = 30;
      } else if ((var1 & 65536) == 65536) {
         this.au = 1;
      }
   }

   public void A() {
      this.bu = 0;
      this.bv = 0;
      this.bw = 0;
      this.bx = 0;
      this.bA = 0;
      this.bz = 0;
      this.by = 0;
      this.bB = 0;
      this.bC = 0;
      this.bD = 0;
      this.bE = 0;
      this.bF = 0;
      this.bG = 0;
      this.bH = 0;
      this.bl = false;
      this.bm = false;
      this.bn = false;

      for (int var1 = 1; var1 <= 4; var1++) {
         int var2 = 0;
         if (var1 == 1) {
            var2 = this.ao;
         } else if (var1 == 2) {
            var2 = this.ap;
         } else if (var1 == 3) {
            var2 = this.aq;
         } else if (var1 == 4) {
            var2 = this.ar;
         }

         if (var2 != 0) {
            if ((var2 & 1) == 1) {
               this.bu = 5;
            } else if ((var2 & 2) == 2) {
               this.bv = 5;
            } else if ((var2 & 4) == 4) {
               this.bw = 10;
            } else if ((var2 & 8) == 8) {
               this.bx = 10;
               this.bA = 10;
               this.bz = 10;
               this.by = 10;
            } else if ((var2 & 16) == 16) {
               this.bB = 10;
            } else if ((var2 & 32) == 32) {
               this.bC = 10;
            } else if ((var2 & 64) == 64) {
               this.bD = 5;
            } else if ((var2 & 128) == 128) {
               this.aM = 5;
            } else if ((var2 & 256) == 256) {
               this.bF = 1;
            } else if ((var2 & 512) == 512) {
               this.bG = 10;
            } else if ((var2 & 1024) == 1024) {
               this.bH = 5;
            } else if ((var2 & 2048) == 2048) {
               this.bl = true;
            } else if ((var2 & 4096) == 4096) {
               this.bm = true;
            } else if ((var2 & 8192) == 8192) {
               this.bn = true;
            }
         }
      }
   }

   private String aM(int var1) {
      String var2 = "";
      int var3 = 0;
      if (var1 == 1) {
         var3 = this.ao;
      } else if (var1 == 2) {
         var3 = this.ap;
      } else if (var1 == 3) {
         var3 = this.aq;
      } else if (var1 == 4) {
         var3 = this.ar;
      }

      String var4 = "$" + (21742 + var1) + ":";
      var2 = var2 + var4;
      if (var3 == 0) {
         return null;
      }

      if ((var3 & 1) == 1) {
         var2 = var2 + "$7374 +5";
      } else if ((var3 & 2) == 2) {
         var2 = var2 + "$7375 +5";
      } else if ((var3 & 4) == 4) {
         var2 = var2 + "$1150 +10";
      } else if ((var3 & 8) == 8) {
         var2 = var2 + "$1060 +10,$1062 +10,$1059 +10,$1061 +10";
      } else if ((var3 & 16) == 16) {
         var2 = var2 + "$3434 +10";
      } else if ((var3 & 32) == 32) {
         var2 = var2 + "$3433 +10";
      } else if ((var3 & 64) == 64) {
         var2 = var2 + "$5533 +5";
      } else if ((var3 & 128) == 128) {
         var2 = var2 + "$5529 +5";
      } else if ((var3 & 256) == 256) {
         var2 = var2 + "$15766 +10";
      } else if ((var3 & 512) == 512) {
         var2 = var2 + "$21756 +10";
      } else if ((var3 & 1024) == 1024) {
         var2 = var2 + "$1139 +5";
      } else if ((var3 & 2048) == 2048) {
         var2 = var2 + "$1140";
      } else if ((var3 & 4096) == 4096) {
         var2 = var2 + "$15484";
      } else if ((var3 & 8192) == 8192) {
         var2 = var2 + "$21755";
      }

      return var2;
   }

   public boolean B() {
      return this.a().ax();
   }

   @Override
   public void c(L1PcInstance var1) {
   }

   public boolean C() {
      return this.P;
   }

   public void a(boolean var1) {
      this.P = var1;
   }

   public boolean D() {
      return this.Q;
   }

   public void b(boolean var1) {
      this.Q = var1;
   }

   public int E() {
      return this.R;
   }

   public void e(int var1) {
      this.R = var1;
   }

   public int F() {
      return this.S;
   }

   public void f(int var1) {
      this.S = var1;
   }

   public int G() {
      return this.T;
   }

   public int H() {
      return this.U;
   }

   public int I() {
      return this.V;
   }

   public void g(int var1) {
      this.V = var1;
   }

   public Timestamp J() {
      return this.W;
   }

   public void a(Timestamp var1) {
      this.W = var1;
   }

   public int K() {
      return this.X;
   }

   public void h(int var1) {
      this.X = var1;
   }

   public int L() {
      return this.Y;
   }

   public void i(int var1) {
      this.Y = var1;
   }

   public int M() {
      return this.Z;
   }

   public void j(int var1) {
      this.Z = var1;
   }

   public int N() {
      return this.ab;
   }

   public int O() {
      return this.ac;
   }

   public int P() {
      return this.ad;
   }

   public int Q() {
      return this.ae;
   }

   public int R() {
      return this.af;
   }

   public int S() {
      return this.ai;
   }

   public boolean T() {
      return this.ak;
   }

   public void c(boolean var1) {
      this.ak = var1;
   }

   public boolean U() {
      return this.al;
   }

   public void d(boolean var1) {
      this.al = var1;
   }

   public int V() {
      return this.am;
   }

   public void k(int var1) {
      this.am = var1;
   }

   public boolean W() {
      return this.an;
   }

   public void e(boolean var1) {
      this.an = var1;
   }

   public int X() {
      return this.ao;
   }

   public void l(int var1) {
      this.ao = var1;
   }

   public int Y() {
      return this.ap;
   }

   public void m(int var1) {
      this.ap = var1;
   }

   public int Z() {
      return this.aq;
   }

   public void n(int var1) {
      this.aq = var1;
   }

   public int aa() {
      return this.ar;
   }

   public void o(int var1) {
      this.ar = var1;
   }

   public int ab() {
      return this.as;
   }

   public void p(int var1) {
      this.as = var1;
   }

   public int ac() {
      return this.at;
   }

   public void q(int var1) {
      this.at = var1;
   }

   public int ad() {
      return this.au;
   }

   public void r(int var1) {
      this.au = var1;
   }

   public int ae() {
      return this.av;
   }

   public void s(int var1) {
      this.av = var1;
   }

   public int af() {
      return this.aw;
   }

   public void t(int var1) {
      this.aw = var1;
   }

   public int ag() {
      return this.ax;
   }

   public void u(int var1) {
      this.ax = var1;
   }

   public int ah() {
      return this.ay;
   }

   public void v(int var1) {
      this.ay = var1;
   }

   public int ai() {
      return this.az;
   }

   public void w(int var1) {
      this.az = var1;
   }

   public int aj() {
      return this.aA;
   }

   public void x(int var1) {
      this.aA = var1;
   }

   public int ak() {
      return this.aB;
   }

   public void y(int var1) {
      this.aB = var1;
   }

   public int al() {
      return this.aC;
   }

   public void z(int var1) {
      this.aC = var1;
   }

   public int am() {
      return this.aD;
   }

   public void A(int var1) {
      this.aD = var1;
   }

   public int an() {
      return this.aE;
   }

   public void B(int var1) {
      this.aE = var1;
   }

   public int ao() {
      return this.aF;
   }

   public void C(int var1) {
      this.aF = var1;
   }

   public int ap() {
      return this.aG;
   }

   public void D(int var1) {
      this.aG = var1;
   }

   public int aq() {
      return this.aH;
   }

   public void E(int var1) {
      this.aH = var1;
   }

   public int ar() {
      return this.aI;
   }

   public void F(int var1) {
      this.aI = var1;
   }

   public int as() {
      return this.aJ;
   }

   public void G(int var1) {
      this.aJ = var1;
   }

   public int at() {
      return this.aK;
   }

   public void H(int var1) {
      this.aK = var1;
   }

   public int au() {
      return this.aL;
   }

   public void I(int var1) {
      this.aL = var1;
   }

   public int av() {
      return this.aM;
   }

   public void J(int var1) {
      this.aM = var1;
   }

   public int aw() {
      return this.aN;
   }

   public void K(int var1) {
      this.aN = var1;
   }

   public int ax() {
      return this.aO;
   }

   public void L(int var1) {
      this.aO = var1;
   }

   public int ay() {
      return this.aP;
   }

   public void M(int var1) {
      this.aP = var1;
   }

   public int az() {
      return this.aQ;
   }

   public void N(int var1) {
      this.aQ = var1;
   }

   public int aA() {
      return this.aR;
   }

   public void O(int var1) {
      this.aR = var1;
   }

   public int aB() {
      return this.aS;
   }

   public void P(int var1) {
      this.aS = var1;
   }

   public int aC() {
      return this.aT;
   }

   public void Q(int var1) {
      this.aT = var1;
   }

   public int aD() {
      return this.aU;
   }

   public void R(int var1) {
      this.aU = var1;
   }

   public int aE() {
      return this.aV;
   }

   public void S(int var1) {
      this.aV = var1;
   }

   public int aF() {
      return this.aW;
   }

   public void T(int var1) {
      this.aW = var1;
   }

   public int aG() {
      return this.aX;
   }

   public void U(int var1) {
      this.aX = var1;
   }

   public int aH() {
      return this.aY;
   }

   public void V(int var1) {
      this.aY = var1;
   }

   public int aI() {
      return this.aZ;
   }

   public void W(int var1) {
      this.aZ = var1;
   }

   public int aJ() {
      return this.ba;
   }

   public void X(int var1) {
      this.ba = var1;
   }

   public int aK() {
      return this.bb;
   }

   public void Y(int var1) {
      this.bb = var1;
   }

   public int aL() {
      return this.bc;
   }

   public void Z(int var1) {
      this.bc = var1;
   }

   public int aM() {
      return this.bd;
   }

   public void aa(int var1) {
      this.bd = var1;
   }

   public int aN() {
      return this.be;
   }

   public void ab(int var1) {
      this.be = var1;
   }

   public int aO() {
      return this.bf;
   }

   public void ac(int var1) {
      this.bf = var1;
   }

   public int aP() {
      return this.bg;
   }

   public void ad(int var1) {
      this.bg = var1;
   }

   public int aQ() {
      return this.bh;
   }

   public void ae(int var1) {
      this.bh = var1;
   }

   public int aR() {
      return this.bi;
   }

   public void af(int var1) {
      this.bi = var1;
   }

   public int aS() {
      return this.bj;
   }

   public void ag(int var1) {
      this.bj = var1;
   }

   public int aT() {
      return this.bk;
   }

   public void ah(int var1) {
      this.bk = var1;
   }

   public boolean aU() {
      return this.bl;
   }

   public void f(boolean var1) {
      this.bl = var1;
   }

   public boolean aV() {
      return this.bm;
   }

   public boolean aW() {
      return this.bn;
   }

   public boolean aX() {
      return this.bo;
   }

   public int aY() {
      return this.bp;
   }

   public void ai(int var1) {
      this.bp = var1;
   }

   public int aZ() {
      return this.bq;
   }

   public void aj(int var1) {
      this.bq = var1;
   }

   public int ba() {
      return this.br;
   }

   public void ak(int var1) {
      this.br = var1;
   }

   public Timestamp bb() {
      return this.bt;
   }

   public void b(Timestamp var1) {
      this.bt = var1;
   }

   public int bc() {
      return this.bu;
   }

   public void al(int var1) {
      this.bu = var1;
   }

   public int bd() {
      return this.bv;
   }

   public void am(int var1) {
      this.bv = var1;
   }

   public int be() {
      return this.bw;
   }

   public void an(int var1) {
      this.bw = var1;
   }

   public int bf() {
      return this.bx;
   }

   public void ao(int var1) {
      this.bx = var1;
   }

   public int bg() {
      return this.by;
   }

   public void ap(int var1) {
      this.by = var1;
   }

   public int bh() {
      return this.bz;
   }

   public void aq(int var1) {
      this.bz = var1;
   }

   public int bi() {
      return this.bA;
   }

   public void ar(int var1) {
      this.bA = var1;
   }

   public int bj() {
      return this.bB;
   }

   public void as(int var1) {
      this.bB = var1;
   }

   public int bk() {
      return this.bC;
   }

   public void at(int var1) {
      this.bC = var1;
   }

   public int bl() {
      return this.bD;
   }

   public void au(int var1) {
      this.bD = var1;
   }

   public int bm() {
      return this.bE;
   }

   public void av(int var1) {
      this.bE = var1;
   }

   public int bn() {
      return this.bF;
   }

   public void aw(int var1) {
      this.bF = var1;
   }

   public int bo() {
      return this.bG;
   }

   public void ax(int var1) {
      this.bG = var1;
   }

   public int bp() {
      return this.bH;
   }

   public void ay(int var1) {
      this.bH = var1;
   }

   public String bq() {
      return this.bI;
   }

   public void a(String var1) {
      this.bI = var1;
   }

   public int br() {
      return this.bJ;
   }

   public void az(int var1) {
      this.bJ = var1;
   }

   public int bs() {
      return this.bK;
   }

   public void aA(int var1) {
      this.bK = var1;
   }

   public int bt() {
      return this.bL;
   }

   public void aB(int var1) {
      this.bL = var1;
   }

   public int bu() {
      return this.bM;
   }

   public void aC(int var1) {
      this.bM = var1;
   }

   public int bv() {
      return this.bN;
   }

   public void aD(int var1) {
      this.bN = var1;
   }

   public int bw() {
      return this.bO;
   }

   public void aE(int var1) {
      this.bO = var1;
   }

   public int bx() {
      return this.bP;
   }

   public void aF(int var1) {
      this.bP = var1;
   }

   public int by() {
      return this.bQ;
   }

   public void aG(int var1) {
      this.bQ = var1;
   }

   public int bz() {
      return this.bR;
   }

   public void aH(int var1) {
      this.bR = var1;
   }

   public boolean bA() {
      return this.bS;
   }

   public void g(boolean var1) {
      this.bS = var1;
   }

   public boolean bB() {
      return this.bT;
   }

   public void h(boolean var1) {
      this.bT = var1;
   }

   public int bC() {
      return this.bU;
   }

   public void aI(int var1) {
      this.bU = var1;
   }

   public int bD() {
      return this.bV;
   }

   public void aJ(int var1) {
      this.bV = var1;
   }

   public int bE() {
      return this.bW;
   }

   public void aK(int var1) {
      this.bW = var1;
   }

   public String bF() {
      return this.bX;
   }

   public void b(String var1) {
      this.bX = var1;
   }

   private class L1R_a extends TimerTask {
      private final L1PcInstance b;

      private L1R_a(L1PcInstance var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         if (this.b != null && this.b.bE() != 0) {
            if (L1ItemInstance.this.M() > 1) {
               L1ItemInstance.this.j(L1ItemInstance.this.M() - 1);
               this.b.j().b(L1ItemInstance.this);
            } else {
               this.b.j().b(L1ItemInstance.this, 1);
               L1ItemInstance.this.aj.cancel(true);
            }
         } else {
            L1ItemInstance.this.aj.cancel(true);
         }
      }

      // $VF: synthetic method
      L1R_a(L1PcInstance var2, L1ItemInstance.L1R_a var3) {
         this(var2);
      }
   }

   private class L1R_b implements Runnable {
      private L1R_b() {
      }

      @Override
      public void run() {
         try {
            L1ItemInstance.this.f(L1ItemInstance.this.ah);
         } catch (Exception var2) {
            L1ItemInstance.O.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      // $VF: synthetic method
      L1R_b(L1ItemInstance.L1R_b var2) {
         this();
      }
   }

   private class L1R_c extends TimerTask {
      private L1R_c() {
      }

      @Override
      public void run() {
         L1ItemInstance.this.ai = 0;
      }

      // $VF: synthetic method
      L1R_c(L1ItemInstance.L1R_c var2) {
         this();
      }
   }
}
