package l1r.ap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.al.L1HpBar;
import l1r.al.L1Speed;
import l1r.am.MonsterListReader;
import l1r.ao.AccountTable;
import l1r.ao.CharBuffTable;
import l1r.ao.CharacterEquipment;
import l1r.ao.CharacterMobsTable;
import l1r.ao.CharacterMobsWeekTable;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.ao.ExpTable;
import l1r.ao.ItemTable;
import l1r.ao.QuestNewTable;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
import l1r.aq.L1ChatParty;
import l1r.aq.L1Clan;
import l1r.aq.L1EquipmentSlot;
import l1r.aq.L1ExcludingList;
import l1r.aq.L1Getback;
import l1r.aq.L1Karma;
import l1r.aq.L1Magic;
import l1r.aq.L1Master;
import l1r.aq.L1Object;
import l1r.aq.L1Party;
import l1r.aq.L1PinkName;
import l1r.aq.L1Quest;
import l1r.aq.L1SpeedChecker;
import l1r.aq.L1Trade;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.ar.L1ClassFeature;
import l1r.as.L1CastleWar;
import l1r.at.L1GameTimeClock;
import l1r.au.L1AccountInventory;
import l1r.au.L1CharInventory;
import l1r.au.L1ElvenInventory;
import l1r.au.L1Inventory;
import l1r.au.L1PcInventory;
import l1r.ay.L1PcAtkMonitor;
import l1r.ay.L1PcAutoUpdate;
import l1r.ay.L1PcEinMonitor;
import l1r.ay.L1PcExpMonitor;
import l1r.ay.L1PcInvisDelay;
import l1r.ba.HellTimer;
import l1r.bc.FishingTimer;
import l1r.bc.HpRegenerationTimer;
import l1r.bc.MpRegenerationTimer;
import l1r.be.S_ChangeLevel;
import l1r.be.S_ChangeName;
import l1r.be.S_CharEvent;
import l1r.be.S_CharTitle;
import l1r.be.S_Disconnect;
import l1r.be.S_DoActionGFX;
import l1r.be.S_DoActionShop;
import l1r.be.S_EquipmentSlot;
import l1r.be.S_Extended;
import l1r.be.S_Fight;
import l1r.be.S_Fishing;
import l1r.be.S_GameTime;
import l1r.be.S_HPMeter;
import l1r.be.S_HPUpdate;
import l1r.be.S_Invis;
import l1r.be.S_Lawful;
import l1r.be.S_MPUpdate;
import l1r.be.S_Message_YN;
import l1r.be.S_OtherCharPacks;
import l1r.be.S_OwnCharAttrDef;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_PacketBox;
import l1r.be.S_Poison;
import l1r.be.S_RedMessage;
import l1r.be.S_RemoveObject;
import l1r.be.S_SPMR;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_SummonPack;
import l1r.be.S_TrueTarget;
import l1r.be.ServerBasePacket;
import l1r.bf.S_044;
import l1r.bh.L1BookMark;
import l1r.bh.L1PrivateShopBuyList;
import l1r.bh.L1PrivateShopSellList;
import l1r.bh.L1QuestNew;
import l1r.bi.CalcStat;
import l1r.bi.GeneralThreadPool;
import l1r.bi.LineageUtil;
import l1r.bi.Point;
import l1r.bi.Random;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class L1PcInstance extends L1Character {
   private static final Logger s = Logger.getLogger(L1PcInstance.class.getName());
   public static final int a = 61;
   public static final int b = 48;
   public static final int c = 138;
   public static final int d = 37;
   public static final int e = 734;
   public static final int f = 1186;
   public static final int g = 2786;
   public static final int h = 2796;
   public static final int i = 0;
   public static final int j = 1;
   public static final int k = 6658;
   public static final int l = 6661;
   public static final int m = 6671;
   public static final int n = 6650;
   public static final int o = 12490;
   public static final int p = 12494;
   private int t = 0;
   private int u = 0;
   private int v = 0;
   private int w = 0;
   public static final int q = 2;
   public static final int r = 1;
   private HpRegenerationTimer y;
   private ScheduledFuture<?> z;
   private MpRegenerationTimer A;
   private ScheduledFuture<?> B;
   private ScheduledFuture<?> C;
   private ScheduledFuture<?> D;
   private ScheduledFuture<?> E;
   private ScheduledFuture<?> F;
   private final CopyOnWriteArrayList<Integer> G = new CopyOnWriteArrayList<>();
   private final L1AccountInventory H;
   private final L1ElvenInventory I;
   private final L1CharInventory J;
   private final L1Inventory K = new L1Inventory();
   private final L1PcInventory L;
   private int M;
   private int N = 0;
   private boolean O = false;
   private int P;
   private L1ClassFeature Q = null;
   private int R = 0;
   private int S = 0;
   private int T = 0;
   private int U = 0;
   private String V = "";
   private int W = 0;
   private Timestamp X;
   private int Y = 0;
   private ClientThread Z;
   private L1Party aa;
   private L1ChatParty ab;
   private int ac;
   private int ad;
   private boolean ae;
   private int af;
   private boolean ag = false;
   private boolean ah = false;
   private boolean ai = false;
   private final CopyOnWriteArrayList<L1PrivateShopSellList> aj = new CopyOnWriteArrayList<>();
   private final ArrayList<L1PrivateShopBuyList> ak = new ArrayList<>();
   private byte[] al;
   private boolean am = false;
   private boolean an = false;
   private int ao = 0;
   private double ap = 0.0;
   private ScheduledFuture<?> aq;
   private final CopyOnWriteArrayList<L1BookMark> ar = new CopyOnWriteArrayList<>();
   private CopyOnWriteArrayList<L1ItemInstance> as = new CopyOnWriteArrayList<>();
   private final L1Quest at;
   private String au;
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
   private int aO;
   private int aP;
   private int aQ;
   private int aR = 0;
   private int aS = 0;
   private int aT = 0;
   private int aU = 0;
   private int aV = 0;
   private int aW = 0;
   private int aX = 0;
   private int aY = 0;
   private int aZ;
   private int ba;
   private boolean bb = false;
   private final L1EquipmentSlot bc;
   private long bd;
   private int be = 0;
   private int bf = 0;
   private static final long bg = 3000L;
   private ScheduledFuture<?> bh;
   private boolean bi = false;
   private final L1Karma bj = new L1Karma();
   private Timestamp bk;
   private Timestamp bl;
   private Timestamp bm;
   private Timestamp bn;
   private int bo = 0;
   private int bp = 0;
   private int bq = 0;
   private int br = 0;
   private int bs = 0;
   private int bt = 0;
   private int bu = 0;
   private int bv = 0;
   private int bw = 0;
   private boolean bx = false;
   private boolean by = true;
   private int bz = 10;
   private final L1ExcludingList bA = new L1ExcludingList();
   private final L1SpeedChecker bB = new L1SpeedChecker(this);
   private int bC = 0;
   private int bD = 0;
   private int bE = 0;
   private int bF = 0;
   private int bG;
   private boolean bH = true;
   private boolean bI = true;
   private boolean bJ = true;
   private boolean bK = true;
   private boolean bL = true;
   private int bM;
   private boolean bN = false;
   private int bO;
   private int bP;
   private byte bQ = 0;
   private long bR = 0L;
   private int bS;
   private int bT;
   private boolean bU = false;
   private int bV = 1;
   private int bW = 1;
   private boolean bX = false;
   private int bY;
   private int[] bZ = new int[3];
   private Timestamp ca;
   private int cb;
   private int cc = 0;
   private int cd = 0;
   private int ce = 0;
   private int cf = 0;
   private int cg = 0;
   private int ch = 0;
   private int ci = 0;
   private boolean cj = false;
   private int ck = 60;
   private int cl = 60;
   private int cm = 0;
   private boolean cn = false;
   private boolean co = false;
   private boolean cp = false;
   private FishingTimer cq = null;
   private int cr = 3;
   private int cs = 0;
   private boolean ct = false;
   private int cu = 21600;
   private int cv = 7200;
   private int cw = 21600;
   private int cx = 21600;
   private int cy = 7200;
   private int cz = 14400;
   private int cA = 14400;
   private int cB = 0;
   private boolean cC = false;
   private boolean cD = false;
   private boolean cE = false;
   private int cF = 0;
   private boolean cG = false;
   private boolean cH = false;
   private boolean cI = false;
   private int cJ = 0;
   private boolean cK = false;
   private boolean cL = false;
   private boolean cM = false;
   private boolean cN = false;
   private boolean cO = false;
   private boolean cP = false;
   private boolean cQ = false;
   private int cR = 0;
   private int cS = 0;
   private int cT = 0;
   private int cU = 0;
   private boolean cV = false;
   private boolean cW = false;
   private boolean cX = false;
   private boolean cY = false;
   private boolean cZ = false;
   private boolean da = false;
   private boolean db = false;
   private int[] dc = null;
   private int dd = 0;
   private int de = 0;
   private int df = 0;
   private int dg = 0;
   private int dh = 0;
   private int di = 0;
   private int dj = 0;
   private int dk = 0;
   private int dl = 0;
   private int dm = 0;
   private int dn = 0;
   private int l1r_do_field = 0;
   private boolean dp = false;
   private int dq;
   private int dr = 0;
   private int ds = 0;
   private int dt = 0;
   private int[] du = new int[MonsterListReader.a().b()];
   private int[] dv = new int[MonsterListReader.a().b() * 3];
   private final HashMap<Integer, L1QuestNew> dw = new HashMap<>();
   private ArrayList<Integer> dx = new ArrayList<>();
   private ArrayList<Integer> dy = new ArrayList<>();
   private int dz = 0;
   private int dA = 0;
   private int dB = -1;
   private int[][] dC = null;

   @Override
   public void c(int var1) {
      this.u += var1;
      this.t = Math.max(0, this.u);
   }

   public void d(int var1) {
      this.w += var1;
      this.v = Math.max(0, this.w);
   }

   public void e(int var1) {
      this.A.a(var1);
      this.y.a(var1);
   }

   public void a() {
      if (this.z == null) {
         this.y = new HpRegenerationTimer(this);
         this.z = GeneralThreadPool.a().b(this.y, 1000L, 1000L);
      }
   }

   public void b() {
      if (this.z != null) {
         this.z.cancel(true);
         this.z = null;
      }
   }

   public void c() {
      if (this.B == null) {
         this.A = new MpRegenerationTimer(this);
         this.B = GeneralThreadPool.a().b(this.A, 1000L, 1000L);
      }
   }

   public void d() {
      if (this.B != null) {
         this.B.cancel(true);
         this.B = null;
      }
   }

   public void e() {
      this.es();
      this.F = GeneralThreadPool.a().b(new L1PcAutoUpdate(this.fr()), 0L, 200L);
   }

   public void f() {
      this.C = GeneralThreadPool.a().b(new L1PcExpMonitor(this.fr()), 0L, 500L);
      this.D = GeneralThreadPool.a().b(new L1PcEinMonitor(this.fr()), 0L, 1000L);
      this.E = GeneralThreadPool.a().b(new L1PcAtkMonitor(this.fr()), 0L, 300L);
      this.bh = GeneralThreadPool.a().a(new L1PcInstance.c(null), 0L, 1000L);
   }

   private void fv() {
      if (this.F != null) {
         this.F.cancel(true);
         this.F = null;
      }

      if (this.C != null) {
         this.C.cancel(true);
         this.C = null;
      }

      if (this.D != null) {
         this.D.cancel(true);
         this.D = null;
      }

      if (this.E != null) {
         this.E.cancel(true);
         this.E = null;
      }

      if (this.bh != null) {
         this.bh.cancel(true);
         this.bh = null;
      }
   }

   public void g() {
      int var1 = ExpTable.c(this.m());
      int var2 = this.ev();
      int var3 = var1 - var2;
      if (var3 == 0) {
         this.a(new S_OwnCharStatus(this));
         if (this.bB(4076)) {
            int var6 = ExpTable.a(var2, this.m());
            int var5 = var2 <= 64 ? 10 : 5;
            if (var6 >= var5) {
               this.bz(4076);
            }
         }
      } else {
         if (var3 > 0) {
            this.cJ(var3);
         } else if (var3 < 0) {
            this.cK(var3);
         }

         int var4 = this.cL(var1) - this.cL(var2);
         if (var4 != 0) {
            this.a(new S_ChangeLevel(this));
            this.b(new S_ChangeLevel(this));
         }
      }
   }

   @Override
   public void b(L1PcInstance var1) {
      if (!this.aA() && !this.bN()) {
         if (!this.ff() || var1.bB(26003)) {
            var1.c((L1Object)this);
            var1.a(new S_OtherCharPacks(this));
            if (this.ff() && var1.bB(26003)) {
               var1.a(new S_OtherCharPacks(this, true));
               var1.a(new S_TrueTarget(this.fr(), true));
            }

            if (this.aK() != null && this.aK().e().a()) {
               var1.a(new S_CharEvent(73, this.fr(), this.aK().e().h()));
            }

            if (this.q() && this.aL().d(var1)) {
               var1.a(new S_HPMeter(this));
            }

            if (this.aX()) {
               var1.a(new S_DoActionShop(this.fr(), 70, this.aW()));
            } else if (this.cq()) {
               var1.a(new S_Fishing(this.fr(), 71, this.cr(), this.cs()));
            }
         }
      }
   }

   private void fw() {
      for (L1Object var1 : this.eq()) {
         if (var1 != null && !this.fu().e(var1.fu())) {
            this.d(var1);
            this.a(new S_RemoveObject(var1));
         }
      }
   }

   public void h() {
      this.fw();

      for (L1Object var1 : L1World.a().b(this, -1)) {
         if (!this.b(var1)) {
            var1.b(this);
         } else if (var1 instanceof L1MonsterInstance) {
            L1MonsterInstance var3 = (L1MonsterInstance)var1;
            if (var3.ac() != 0) {
               var3.d(this);
            }
         }

         boolean var5 = false;
         if (var1 instanceof L1MonsterInstance) {
            L1MonsterInstance var4 = (L1MonsterInstance)var1;
            if (var4.d(this)) {
               var5 = true;
            }
         }

         if ((this.bB(26001) || var5) && L1HpBar.a(var1)) {
            this.a(new S_HPMeter((L1Character)var1));
         }
      }
   }

   public void i() {
      if (this.bN() || this.ff() || this.aA()) {
         this.a(new S_Invis(this.fr(), 1));
      }

      if (this.aK() != null && this.aK().e().a()) {
         this.a(new S_CharEvent(73, this.fr(), this.aK().e().h()));
         this.b(new S_CharEvent(73, this.fr(), this.aK().e().h()));
      }
   }

   public void f(int var1) {
      if (!this.G.contains(var1)) {
         this.G.add(var1);
      }
   }

   public void g(int var1) {
      if (this.G.contains(var1)) {
         this.G.remove(Integer.valueOf(var1));
      }
   }

   public boolean h(int var1) {
      return this.G.contains(var1);
   }

   public L1PcInstance() {
      this.L = new L1PcInventory(this);
      this.H = new L1AccountInventory(this);
      this.I = new L1ElvenInventory(this);
      this.J = new L1CharInventory(this);
      this.at = new L1Quest(this);
      this.bc = new L1EquipmentSlot(this);
   }

   @Override
   public synchronized void a(int var1) {
      if (this.ea() != var1) {
         int var2 = var1;
         if (var2 >= this.ew()) {
            var2 = this.ew();
         }

         this.bx(var2);
         this.a(new S_HPUpdate(var2, this.ew()));
         if (this.q()) {
            this.aL().f(this);
         }
      }
   }

   @Override
   public synchronized void i_(int var1) {
      if (this.eb() != var1) {
         int var2 = var1;
         if (var2 >= this.ex()) {
            var2 = this.ex();
         }

         this.by(var2);
         this.a(new S_MPUpdate(var2, this.ex()));
      }
   }

   public L1PcInventory j() {
      return this.L;
   }

   public int k() {
      if (this.v() == null) {
         return 0;
      } else {
         return this.w() == 2 ? 88 : this.v().a().aO();
      }
   }

   public boolean l() {
      return this.N == 200;
   }

   public void i(int var1) {
      this.P = var1;
      this.Q = L1ClassFeature.a(var1);
   }

   @Override
   public synchronized int m() {
      return this.R;
   }

   @Override
   public synchronized void k(int var1) {
      this.R = var1;
   }

   public void n() {
      this.X = new Timestamp(System.currentTimeMillis());
   }

   public int o() {
      if (this.X != null) {
         SimpleDateFormat var1 = new SimpleDateFormat("yyyyMMdd");
         return Integer.parseInt(var1.format(this.X.getTime()));
      } else {
         return 0;
      }
   }

   private void a(List<L1PcInstance> var1) {
      for (L1PcInstance var2 : var1) {
         if (var2.b((L1Object)this)) {
            var2.d(this);
            var2.a(new S_RemoveObject(this));
         }
      }
   }

   public void p() {
      try {
         if (this.eX()) {
            L1World.a().d(this);
            Thread.sleep(2000L);
            int[] var1 = L1Getback.a(this);
            this.cG(var1[0]);
            this.cH(var1[1]);
            this.cE(var1[2]);
            this.a(this.ev());
            this.c_(40);
         }

         if (this.aF() != 0) {
            L1Clan var7 = ClanTable.a().a(this.aF());
            if (var7 != null) {
               if (var7.o() == this.fr()) {
                  var7.i(0);
               }

               if (var7.b().size() <= 3) {
                  for (L1PcInstance var2 : var7.b()) {
                     var2.bz(4084);
                     var2.a(new S_PacketBox(180, 450, 3240, 0));
                  }
               }
            }
         }

         if (this.aO() != 0) {
            L1Trade.b(this);
         }

         if (this.cp() != 0) {
            this.aN(0);
            L1PcInstance var8 = (L1PcInstance)L1World.a().a(this.cp());
            if (var8 != null) {
               var8.aN(0);
               var8.a(new S_PacketBox(5, 0, 0));
            }
         }

         if (this.q()) {
            this.aL().b(this);
         }

         if (this.r()) {
            this.aM().c(this);
         }

         for (L1NpcInstance var9 : this.ek().values()) {
            if (var9 instanceof L1PetInstance) {
               L1PetInstance var16 = (L1PetInstance)var9;
               var16.ax();
               var16.h();
               this.ek().remove(var16.fr());
               var16.aa_();
            } else if (var9 instanceof L1SummonInstance) {
               L1SummonInstance var15 = (L1SummonInstance)var9;

               for (L1PcInstance var4 : L1World.a().f(var15)) {
                  if (var4.fr() != this.fr()) {
                     var4.a(new S_SummonPack(var15, var4, false));
                  }
               }
            }
         }

         for (L1DollInstance var10 : this.el().values()) {
            var10.e();
         }

         for (L1FollowerInstance var11 : this.em().values()) {
            var11.V(true);
            var11.a(var11.z(), var11.fs(), var11.ft(), var11.fb(), var11.fp());
            var11.aa_();
         }

         L1BookMark.a(this.ba());
         CharBuffTable.a(this);
         this.ei();
         CharacterMobsTable.a().a(this);
         CharacterMobsWeekTable.a().c(this);
         QuestNewTable.a().c(this);
         CharacterEquipment.a().a(this);
         this.fv();
         this.aC(0);
         this.f(new Timestamp(System.currentTimeMillis()));
         L1Master.a().a(this);
         AccountTable.a().b(this.aK().e(), false);
         this.I();
         this.J();
         L1World.a().d(this);
         L1World.a().b(this);
         this.a(this.er());
         this.a(L1World.a().f(this));
         this.L.g();
         this.H.g();
         this.es();
         this.b();
         this.d();
         this.X(true);
         this.a((ClientThread)null);
      } catch (Exception var6) {
         s.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
      }
   }

   public boolean q() {
      return this.aa != null;
   }

   public boolean r() {
      return this.ab != null;
   }

   public void a(ServerBasePacket var1) {
      if (this.Z != null && this.bE() != 0) {
         try {
            if (Config.e) {
               System.out.println(this.bc() + "/" + var1.b() + "/" + var1.a().length);
               System.out.println(LineageUtil.a(var1.a()));
            }

            this.Z.a(var1);
         } catch (Exception var3) {
            s.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         }
      }
   }

   @Override
   public void c(L1PcInstance var1) {
      this.a(var1, 0);
   }

   @Override
   public void a(L1PcInstance var1, int var2) {
      if (var1 != null) {
         if (!this.aR()) {
            if (this.ep() != 1 && var1.ep() != 1 && !this.a(this, var1, false)) {
               if (this.ea() > 0 && !this.eX()) {
                  boolean var3 = false;
                  L1Attack var4 = new L1Attack(var1, this, var2);
                  if (var4.a()) {
                     if (this.bB(91)) {
                        L1Magic var5 = new L1Magic(this, var1);
                        boolean var6 = var5.a(91);
                        boolean var7 = var4.f();
                        if (var6 && var7) {
                           var3 = true;
                        }
                     }

                     if (this.bB(191) && Random.a(100) < 10) {
                        var3 = true;
                     }

                     int var8 = 0;
                     double var9 = 0.4 + this.dH() * 0.01;
                     if (this.bB(231) && this.ev() >= 80) {
                        var8 = Math.min(5 + this.ev() - 80, 10);
                     }

                     if (this.bB(606) && this.ea() < this.ew() * var9 && var4.f() && Random.a(100) < 34 + var8) {
                        var3 = true;
                     }

                     if (this.bB(607) && this.ea() < this.ew() * var9 && !var4.f() && Random.a(100) < 34 + var8) {
                        var3 = true;
                     }

                     if (!var3) {
                        var1.a(this);
                        var4.b();
                        var4.a(var1, this);
                     }
                  }

                  if (var3) {
                     var4.b();
                     var4.e();
                     var4.g();
                  } else {
                     var4.c();
                     var4.d();
                  }
               }
            } else {
               new L1Attack(var1, this, var2).c();
            }
         }
      }
   }

   public boolean a(L1PcInstance var1, L1Character var2, boolean var3) {
      L1PcInstance var4 = null;
      if (var2 instanceof L1PcInstance) {
         var4 = (L1PcInstance)var2;
      } else if (var2 instanceof L1PetInstance) {
         var4 = (L1PcInstance)((L1PetInstance)var2).M();
      } else if (var2 instanceof L1SummonInstance) {
         var4 = (L1PcInstance)((L1SummonInstance)var2).M();
      } else if (var2 instanceof L1MonsterInstance) {
         return false;
      }

      if (var4 == null) {
         return true;
      }

      for (L1War var5 : L1World.a().i()) {
         if (var1.aF() != 0 && var4.aF() != 0 && var5.c(var1.aG(), var4.aG())) {
            return false;
         }
      }

      if (var2 instanceof L1PcInstance) {
         L1PcInstance var7 = (L1PcInstance)var2;
         if (L1CastleWar.a().a((L1Character)var1) && L1CastleWar.a().a((L1Character)var7)) {
            return false;
         }
      }

      if (Config.R) {
         return var1.fu().e(new Point(33080, 33376)) && var1.fu().d() && var2.fu().e(new Point(33080, 33376)) && var2.fu().d()
            ? false
            : var1.fp() < 10500 || var1.fp() > 10502;
      }

      if (var3) {
         if (this.fq().c(this.fu())) {
            return false;
         }
      } else if (!this.fq().b(this.fu())) {
         return false;
      }

      return true;
   }

   public void a(L1Character var1) {
      for (L1NpcInstance var2 : this.ek().values()) {
         if (var2 instanceof L1PetInstance) {
            L1PetInstance var4 = (L1PetInstance)var2;
            var4.g(var1);
         } else if (var2 instanceof L1SummonInstance) {
            L1SummonInstance var5 = (L1SummonInstance)var2;
            var5.g(var1);
         }
      }
   }

   public void s() {
      this.bz(60);
      this.bz(97);
   }

   public void a(L1Character var1, int var2) {
      if (var2 > 0 && !this.eX()) {
         this.s();
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            L1PinkName.a(this, var3);
         }

         int var4 = this.eb() - var2;
         if (var4 > this.ex()) {
            var4 = this.ex();
         } else if (var4 <= 0) {
            var4 = 0;
         }

         this.i_(var4);
      }
   }

   public void a(L1Character var1, double var2, boolean var4) {
      if (this.ea() <= 0) {
         if (!this.eX()) {
            this.b(var1);
         }
      } else {
         if (!this.b(var1) && var1.fp() == this.fp() && !(var1 instanceof L1EffectInstance)) {
            var1.b(this);
         }

         if (var4) {
            double var5 = System.currentTimeMillis();
            double var7 = (20.0 - (var5 - this.ap) / 100.0) % 20.0;
            if (var2 > 0.0) {
               if (var7 > 0.0) {
                  var2 *= 1.0 - var7 / 30.0;
               }

               if (var2 < 1.0) {
                  var2 = 0.0;
               }

               this.ap = var5;
            }
         }

         if (var2 > 0.0) {
            this.s();
            if (var1 instanceof L1PcInstance) {
               L1PinkName.a(this, var1);
            }

            this.bz(66);
            this.bz(153);
         }

         if (this.cG) {
            var2 *= 1.5;
         }

         if (this.bB(219)) {
            var2 *= 1.2;
         }

         if ((var1 instanceof L1PetInstance || var1 instanceof L1SummonInstance) && (this.ep() == 1 || var1.ep() == 1 || this.a(this, var1, false))) {
            var2 = 0.0;
         }

         int var9 = this.ea() - (int)var2;
         if (var9 <= 10 && this.bB(135)) {
            int var6 = this.eb();
            if (var2 <= var6) {
               this.i_(var6 - (int)var2);
               return;
            }

            this.i_(0);
            var2 -= var6;
         }

         if (this.cI && var2 > 20.0 && Random.a(100) < 5) {
            this.a(new S_SkillSound(this.fr(), 2188));
            this.b(new S_SkillSound(this.fr(), 2188));
            int[] var10 = new int[]{15, 10, 20, 20, 20, 20, 20, 10};
            this.i_(this.eb() + var10[this.ay()]);
         }

         if (this.cH && var2 > 20.0 && Random.a(100) < 5) {
            this.a(new S_SkillSound(this.fr(), 2187));
            this.b(new S_SkillSound(this.fr(), 2187));
            int var11 = this.ew() / 10;
            if (this.bB(173)) {
               var11 /= 2;
            }

            if (this.bB(170)) {
               var11 *= 2;
            }

            var9 += var11;
         }

         if (var9 > this.ew()) {
            var9 = this.ew();
         }

         if (var9 <= 0) {
            if (this.l()) {
               this.a(this.ew());
            } else {
               this.b(var1);
            }
         } else {
            this.a(var9);
         }
      }
   }

   public void b(L1Character var1) {
      synchronized (this) {
         if (this.eX()) {
            return;
         }

         this.X(true);
         this.cq(8);
      }

      if (this.aO() != 0) {
         L1Trade.b(this);
      }

      GeneralThreadPool.a().b(new L1PcInstance.b(var1));
   }

   public void t() {
      if (this.aq != null) {
         this.aq.cancel(true);
         this.aq = null;
      }
   }

   private void cI(int var1) {
      for (int var2 = 0; var2 < var1; var2++) {
         L1ItemInstance var3 = this.j().n();
         if (var3 != null) {
            this.j().a(var3, var3.d() ? var3.E() : 1, L1World.a().a(this.fs(), this.ft(), this.fp()));
            this.a(new S_ServerMessage(638, var3.s()));
         }
      }
   }

   private boolean c(L1Character var1) {
      if (this.aF() != 0 && !Config.Y) {
         L1Clan var2 = ClanTable.a().a(this.aF());
         L1PcInstance var3 = null;
         if (var1 instanceof L1PcInstance) {
            var3 = (L1PcInstance)var1;
         } else if (var1 instanceof L1PetInstance) {
            var3 = (L1PcInstance)((L1PetInstance)var1).M();
         } else {
            if (!(var1 instanceof L1SummonInstance)) {
               return false;
            }

            var3 = (L1PcInstance)((L1SummonInstance)var1).M();
         }

         L1War var4 = L1World.a().c(var2.f());
         if (var4 == null || var4.c() != 2) {
            return false;
         }

         if (var3 == null || var3.aF() == 0) {
            return false;
         }

         if (!var4.c(this.aG(), var3.aG())) {
            return false;
         }

         if (this.fr() == var2.k()) {
            for (L1Clan var5 : var4.c(this.aG())) {
               var4.b(this.aG(), var5.f());
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void a(boolean var1) {
      int var2 = this.ev();
      int var3 = ExpTable.b(var2);
      int var4 = var1 ? 2 : 1;
      int var5 = 0;
      if (var2 < 45) {
         var5 = (int)(var3 * 0.05 * var4);
      } else if (var2 == 45) {
         var5 = (int)(var3 * 0.045 * var4);
      } else if (var2 == 46) {
         var5 = (int)(var3 * 0.04 * var4);
      } else if (var2 == 47) {
         var5 = (int)(var3 * 0.035 * var4);
      } else if (var2 == 48) {
         var5 = (int)(var3 * 0.03 * var4);
      } else if (var2 >= 49) {
         var5 = (int)(var3 * 0.025 * var4);
      }

      if (var5 != 0) {
         this.x(var5);
      }
   }

   private void fx() {
      int var1 = this.ev();
      int var2 = ExpTable.b(var1);
      int var3 = 0;
      if (var1 >= 1 && var1 < 11) {
         var3 = 0;
      } else if (var1 >= 11 && var1 < 45) {
         var3 = (int)(var2 * 0.1);
      } else if (var1 == 45) {
         var3 = (int)(var2 * 0.09);
      } else if (var1 == 46) {
         var3 = (int)(var2 * 0.08);
      } else if (var1 == 47) {
         var3 = (int)(var2 * 0.07);
      } else if (var1 == 48) {
         var3 = (int)(var2 * 0.06);
      } else if (var1 >= 49) {
         var3 = (int)(var2 * 0.05);
      }

      if (var3 != 0) {
         if (this.ca() >= 0) {
            this.aH(this.ca() + 1);
         }

         this.x(-var3);
      }
   }

   public int u() {
      if (!this.bB(174) && this.aC() != null) {
         int var1 = this.aC().e(this.ev()) + CalcStat.b(this.eB());
         if (this.bB(160)) {
            var1 += 5;
         }

         if (this.bB(111)) {
            var1 += 12;
         }

         if (this.bB(90)) {
            var1 += 15;
         }

         if (this.bB(4062)) {
            var1++;
         }

         if (this.bB(4066)) {
            var1 += 3;
         }

         if (this.bB(4085)) {
            var1 += 5;
         }

         return var1 + this.cF;
      } else {
         return 0;
      }
   }

   public L1BookMark a(String var1) {
      for (L1BookMark var2 : this.ar) {
         if (var2.c().equalsIgnoreCase(var1)) {
            return var2;
         }
      }

      return null;
   }

   public L1BookMark l(int var1) {
      for (L1BookMark var2 : this.ar) {
         if (var2.a() == var1) {
            return var2;
         }
      }

      return null;
   }

   public L1BookMark a(int var1, int var2) {
      if (var1 != 0 && var2 != 0) {
         for (L1BookMark var3 : this.ar) {
            if (var3.d() == var1 && var3.e() == var2) {
               return var3;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public L1ItemInstance v() {
      return this.as.isEmpty() ? null : this.as.get(Random.a(this.as.size()));
   }

   public boolean a(L1ItemInstance var1) {
      return this.as.contains(var1);
   }

   public int w() {
      return this.as.size();
   }

   public void a(CopyOnWriteArrayList<L1ItemInstance> var1) {
      this.as = var1;
   }

   public boolean x() {
      return this.aB() == 0 || this.aB() == 1;
   }

   public boolean z() {
      return this.aB() == 61 || this.aB() == 48;
   }

   public boolean A() {
      return this.aB() == 138 || this.aB() == 37;
   }

   public boolean B() {
      return this.aB() == 734 || this.aB() == 1186;
   }

   public boolean C() {
      return this.aB() == 2786 || this.aB() == 2796;
   }

   public boolean D() {
      return this.aB() == 6658 || this.aB() == 6661;
   }

   public boolean E() {
      return this.aB() == 6671 || this.aB() == 6650;
   }

   public boolean F() {
      return this.aB() == 12490 || this.aB() == 12494;
   }

   public void m(int var1) {
      var1 += this.av;
      if (var1 >= 32767) {
         var1 = 32767;
      } else if (var1 < 1) {
         var1 = 1;
      }

      this.bH(var1 - this.av);
      this.av = var1;
   }

   public void n(int var1) {
      var1 += this.aw;
      if (var1 >= 32767) {
         var1 = 32767;
      } else if (var1 < 0) {
         var1 = 0;
      }

      this.bJ(var1 - this.aw);
      this.aw = var1;
   }

   public void o(int var1) {
      var1 += this.ax;
      if (var1 >= 127) {
         var1 = 127;
      } else if (var1 < 1) {
         var1 = 1;
      }

      this.bN(var1 - this.ax);
      this.ax = var1;
   }

   public void p(int var1) {
      var1 += this.ay;
      if (var1 >= 127) {
         var1 = 127;
      } else if (var1 < 1) {
         var1 = 1;
      }

      this.bP(var1 - this.ay);
      this.ay = var1;
   }

   public void q(int var1) {
      var1 += this.az;
      if (var1 >= 127) {
         var1 = 127;
      } else if (var1 < 1) {
         var1 = 1;
      }

      this.bR(var1 - this.az);
      this.az = var1;
   }

   public void r(int var1) {
      var1 += this.aA;
      if (var1 >= 127) {
         var1 = 127;
      } else if (var1 < 1) {
         var1 = 1;
      }

      this.bT(var1 - this.aA);
      this.aA = var1;
   }

   public void s(int var1) {
      var1 += this.aB;
      if (var1 >= 127) {
         var1 = 127;
      } else if (var1 < 1) {
         var1 = 1;
      }

      this.bV(var1 - this.aB);
      this.aB = var1;
   }

   public void t(int var1) {
      var1 += this.aC;
      if (var1 >= 127) {
         var1 = 127;
      } else if (var1 < 1) {
         var1 = 1;
      }

      this.bX(var1 - this.aC);
      this.aC = var1;
   }

   public synchronized void u(int var1) {
      this.aY += var1;
   }

   @Override
   public void c_(int var1) {
      super.c_(var1);
      this.G();
   }

   public void G() {
      if (this.fj() >= 225) {
         this.bd = System.currentTimeMillis() / 1000L;
      }

      this.a(new S_Extended(this.fj() >= 225));
   }

   public void H() {
      this.be++;
      this.a(new S_OwnCharStatus(this));
   }

   public static L1PcInstance b(String var0) {
      L1PcInstance var1 = null;

      try {
         var1 = CharacterTable.a().b(var0);
      } catch (Exception var3) {
         s.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }

      return var1;
   }

   public void I() {
      if (!this.bN()) {
         if (!this.cv()) {
            try {
               CharacterTable.a().b(this);
            } catch (Exception var2) {
               s.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
            }
         }
      }
   }

   public void J() {
      for (L1ItemInstance var1 : this.j().d()) {
         this.j().i(var1);
      }
   }

   public double K() {
      int var1 = 0;
      if (this.bB(14) || this.bB(218)) {
         var1 = 180;
      } else if (this.bB(134)) {
         var1 = 300;
      }

      double var2 = 1.0 + this.bS() / 100;
      double var4 = 1000.0 + Math.floor((this.ez() + this.eA()) / 2) * 100.0;
      var4 *= var2;
      var4 += var1 + this.bT();
      return var4 * Config.J;
   }

   public boolean L() {
      return this.bB(1027) || this.bB(1038);
   }

   public boolean M() {
      return this.bB(167);
   }

   public boolean N() {
      return this.bf > 0;
   }

   public synchronized void w(int var1) {
      this.bf += var1;
   }

   public void O() {
      this.w(1);
      GeneralThreadPool.a().b(new L1PcInvisDelay(this.fr()), 3000L);
   }

   public synchronized void x(int var1) {
      this.R += var1;
      if (this.R > 1859065562) {
         this.R = 1859065562;
      }
   }

   private void cJ(int var1) {
      this.Z();
      if (this.ev() == 99 && Config.ag) {
         ItemTable.a(this, 43000, 1);
      } else if (this.ev() == 70 && this.cE() > 0) {
         L1Master.a().a(this.cE(), this.et());
         ItemTable.a(this, 21143, 1);
      }

      for (int var2 = 0; var2 < var1; var2++) {
         int var3 = CalcStat.a(this);
         int var4 = CalcStat.b(this);
         this.m(var3);
         this.n(var4);
      }

      this.fB();
      this.fA();
      this.W();
      this.Y();
      this.I();
      if (this.ev() >= 51 && this.ev() - 50 > this.bA() && this.bf() + this.bh() + this.bg() + this.bj() + this.bk() + this.bi() < 270) {
         int var5 = this.ev() - 50 - this.bA();
         this.a(new S_Message_YN(479, String.valueOf(var5)));
      }

      if (this.ev() >= 52) {
         int var6 = 10800;
         this.j(4076, 10800000);
         this.a(new S_PacketBox(86, 173, 1, 675));
      }

      this.a(new S_OwnCharStatus(this));
      this.ac();
      QuestNewTable.a().a(this);
   }

   private void cK(int var1) {
      this.Z();

      for (int var2 = 0; var2 > var1; var2--) {
         int var3 = CalcStat.a(this);
         int var4 = CalcStat.b(this);
         this.m(-var3);
         this.n(-var4);
      }

      this.fB();
      this.fA();
      this.W();
      this.Y();
      this.I();
      this.a(new S_OwnCharStatus(this));
      this.ac();
   }

   @Override
   public void y(int var1) {
      this.a(new S_Poison(this.fr(), var1));
      if (!this.aA() && !this.bN()) {
         if (this.ff()) {
            this.c(new S_Poison(this.fr(), var1));
         } else {
            this.b(new S_Poison(this.fr(), var1));
         }
      }
   }

   @Override
   public void z(int var1) {
      super.z(var1);
      this.a(new S_HPUpdate(this));
   }

   @Override
   public int P() {
      return this.bj.a();
   }

   @Override
   public void A(int var1) {
      this.bj.a(var1);
   }

   public void B(int var1) {
      synchronized (this.bj) {
         this.bj.b(var1);
      }
   }

   public int Q() {
      return this.bj.b();
   }

   public int R() {
      return this.bj.c();
   }

   private void fy() {
      this.bk = new Timestamp(System.currentTimeMillis());
   }

   public boolean S() {
      if (this.bk == null) {
         return false;
      } else if (System.currentTimeMillis() - this.bk.getTime() > 86400000L) {
         this.b((Timestamp)null);
         return false;
      } else {
         return true;
      }
   }

   private void fz() {
      this.bl = new Timestamp(System.currentTimeMillis());
   }

   public boolean T() {
      if (this.bl == null) {
         return false;
      } else if (System.currentTimeMillis() - this.bl.getTime() > 86400000L) {
         this.c((Timestamp)null);
         return false;
      } else {
         return true;
      }
   }

   @Override
   public int U() {
      return this.aC().c(this.ev());
   }

   public void V() {
      this.bz(29);
      this.bz(76);
      this.bz(152);
      this.bz(43);
      this.bz(54);
      this.bz(1001);
      this.bz(1037);
   }

   public void C(int var1) {
      this.bo += var1;
   }

   public void D(int var1) {
      this.bp += var1;
   }

   public void E(int var1) {
      this.bq += var1;
   }

   public void F(int var1) {
      this.br += var1;
   }

   public void G(int var1) {
      this.bs += var1;
   }

   public void H(int var1) {
      this.bt += var1;
   }

   public void I(int var1) {
      this.bu += var1;
   }

   public void J(int var1) {
      this.bv += var1;
   }

   private void fA() {
      int var1 = this.aC().f(this.ev());
      int var2 = this.aC().g(this.ev());
      this.ck(var1 - this.aD);
      this.cl(var2 - this.aE);
      this.aD = var1;
      this.aE = var2;
   }

   private void fB() {
      int var1 = this.aC().h(this.ev());
      int var2 = this.aC().i(this.ev());
      this.cm(var1 - this.aF);
      this.cn(var2 - this.aG);
      this.aF = var1;
      this.aG = var2;
   }

   public void W() {
      if (this.aC() != null) {
         int var1 = 10 + CalcStat.a(this.eB()) + this.aC().d(this.ev());
         this.bL(var1 - this.bz);
         if (this.bz != var1) {
            this.bz = var1;
            this.a(new S_OwnCharAttrDef(this));
         }
      }
   }

   @Override
   public int W_() {
      return this.bB(153) ? (this.x + this.aC().c()) / 4 : this.x + this.aC().c();
   }

   public void Y() {
      int var1 = this.ev() / 2;
      var1 += CalcStat.e(this.eE());
      this.co(var1 - this.aH);
      this.aH = var1;
   }

   public void Z() {
      this.b(ExpTable.c(this.R));

      for (L1QuestNew var1 : this.dS().values()) {
         if (var1.n() > 0) {
            var1.a(this.ev());
         }
      }

      if (this.y != null) {
         this.y.a();
      }
   }

   public void aa() {
      this.Z();
      this.fB();
      this.fA();
      this.Y();
      this.W();
      this.cc = 0;
   }

   public void ab() {
      long var1 = System.currentTimeMillis();
      if (this.bQ == 0) {
         this.bQ++;
         this.bR = var1;
      } else {
         long var3 = var1 - this.bR;
         if (var3 > 2000L) {
            this.bQ = 0;
            this.bR = 0L;
         } else {
            if (this.bQ >= 3) {
               this.j(1005, 120000);
               this.a(new S_PacketBox(36, 120));
               this.a(new S_ServerMessage(153));
               this.bQ = 0;
               this.bR = 0L;
            }

            this.bQ++;
         }
      }
   }

   public void b(int var1, int var2) {
      switch (var1) {
         case -3:
            this.ck(-5);
            this.cl(-5);
            this.cp(-3);
            this.a(new S_Fight(5, 0));
            break;
         case -2:
            this.ck(-3);
            this.cl(-3);
            this.cp(-2);
            this.a(new S_Fight(4, 0));
            break;
         case -1:
            this.ck(-1);
            this.cl(-1);
            this.cp(-1);
            this.a(new S_Fight(3, 0));
         case 0:
         default:
            break;
         case 1:
            this.bL(2);
            this.co(-3);
            this.a(new S_Fight(0, 0));
            break;
         case 2:
            this.bL(4);
            this.co(-6);
            this.a(new S_Fight(1, 0));
            break;
         case 3:
            this.bL(6);
            this.co(-9);
            this.a(new S_Fight(2, 0));
      }

      switch (var2) {
         case -3:
            this.ck(5);
            this.cl(5);
            this.cp(3);
            this.a(new S_Fight(5, 1));
            break;
         case -2:
            this.ck(3);
            this.cl(3);
            this.cp(2);
            this.a(new S_Fight(4, 1));
            break;
         case -1:
            this.ck(1);
            this.cl(1);
            this.cp(1);
            this.a(new S_Fight(3, 1));
         case 0:
         default:
            break;
         case 1:
            this.bL(-2);
            this.co(3);
            this.a(new S_Fight(0, 1));
            break;
         case 2:
            this.bL(-4);
            this.co(6);
            this.a(new S_Fight(1, 1));
            break;
         case 3:
            this.bL(-6);
            this.co(9);
            this.a(new S_Fight(2, 1));
      }

      this.a(new S_SPMR(this));
   }

   public void ac() {
      if (Config.aO) {
         if (this.ev() > Config.aP) {
            if (this.bB(8000)) {
               this.bz(8000);
               this.a(new S_Fight(6, 0));
            }
         } else if (!this.bB(8000)) {
            this.j(8000, 0);
            this.a(new S_Fight(6, 1));
         }
      }
   }

   public void ad() {
      Map var1 = new HashMap<>();

      for (L1ItemInstance var2 : this.j().d()) {
         if (var2.D()) {
            int var4 = this.j().l(var2);
            if (var4 >= 0) {
               var1.put(var2.fr(), var4);
            }
         }
      }

      this.a(new S_EquipmentSlot(var1));
   }

   public void a(int var1, int var2, int var3) {
      this.bZ = new int[]{var1, var2, var3};
   }

   public void ae() {
      if (!this.eX()) {
         if (this.j().f(640100)) {
            new L1Speed().a(this);
            this.j(25005, 600000);
         }

         if (this.j().f(640102) && !this.af()) {
            this.j(4058, 0);
            this.cp(30);
            this.a(new S_SPMR(this));
            this.bH(400);
            this.bJ(200);
            this.a(new S_MPUpdate(this.eb(), this.ex()));
            this.a(new S_HPUpdate(this.ea(), this.ew()));
            if (this.q()) {
               this.aL().f(this);
            }

            this.a(new S_PacketBox(144, 1));
            this.a(new S_ChangeName(this.fr(), this.et()));
            this.b(new S_ChangeName(this.fr(), this.et()));

            for (L1NpcInstance var1 : this.ek().values()) {
               var1.b(new S_CharTitle(var1.fr(), this.et() + "的"));
            }

            for (L1DollInstance var3 : this.el().values()) {
               var3.b(new S_CharTitle(var3.fr(), this.et() + "的"));
            }
         }
      }
   }

   public boolean af() {
      return this.bB(4058);
   }

   public void K(int var1) {
      this.cb = Math.min(Math.max(0, var1), 3850000);
      int var2 = this.cb / 7700;
      if (this.cc != var2) {
         this.cc = var2;
         this.a(new S_PacketBox(82, var2, this.cb));
      }
   }

   public int ag() {
      for (L1DollInstance var1 : this.el().values()) {
         if (var1.X_().au() > 0 && var1.X_().bs() > Random.a(100) + 1) {
            this.a(new S_SkillSound(this.fr(), 6319));
            this.b(new S_SkillSound(this.fr(), 6319));
            return var1.X_().au();
         }
      }

      return 0;
   }

   public int ah() {
      for (L1DollInstance var1 : this.el().values()) {
         if (var1.X_().ap() > 0 && var1.X_().bx() > Random.a(100) + 1) {
            this.a(new S_SkillSound(this.fr(), 6329));
            this.b(new S_SkillSound(this.fr(), 6329));
            return var1.X_().ap();
         }
      }

      return 0;
   }

   public boolean ai() {
      for (L1DollInstance var1 : this.el().values()) {
         if (var1.X_().bt() > 0 && var1.X_().bt() > Random.a(100) + 1) {
            this.a(new S_SkillSound(this.fr(), 6320));
            this.b(new S_SkillSound(this.fr(), 6320));
            return true;
         }
      }

      return false;
   }

   public void L(int var1) {
      this.ce += var1;
   }

   public void M(int var1) {
      this.cf += var1;
   }

   public boolean aj() {
      return this.cg == 0 ? false : this.cg > Random.a(100) + 1;
   }

   public boolean ak() {
      return this.ch == 0 ? false : this.ch > Random.a(100) + 1;
   }

   public boolean al() {
      return this.ci == 0 ? false : this.ci > Random.a(100) + 1;
   }

   public boolean am() {
      return !this.q() ? false : this.aL().e(this);
   }

   public void an() {
      this.ck = Math.min(this.ck + 10, 120);
   }

   public void ao() {
      this.cl = Math.min(this.cl + 20, 100);
   }

   public void ap() {
      this.cu = 21600;
      this.cv = 7200;
      this.cw = 21600;
      this.cx = 21600;
      this.cy = 7200;
      this.cz = 14400;
      this.cA = 14400;
   }

   public int aq() {
      return this.cL(this.ev());
   }

   private int cL(int var1) {
      int[] var2 = new int[]{1, 15, 30, 45, 50, 52, 55, 60, 65, 70, 75, 80, 82, 84};

      for (int var3 = 0; var3 < var2.length; var3++) {
         if (var1 < var2[var3]) {
            return var3 - 1;
         }
      }

      return var2.length - 1;
   }

   public boolean N(int var1) {
      for (L1NpcInstance var2 : this.ek().values()) {
         if (var2 instanceof L1PetInstance) {
            L1PetInstance var4 = (L1PetInstance)var2;
            if (var1 == var4.k()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean O(int var1) {
      for (L1DollInstance var2 : this.el().values()) {
         if (var1 == var2.f()) {
            return true;
         }
      }

      return false;
   }

   public void P(int var1) {
      this.cF += var1;
   }

   public void b(int var1, int var2, int var3) {
      this.dc = new int[]{var1, var2, var3};
   }

   public void Q(int var1) {
      this.dd += var1;
   }

   public void R(int var1) {
      this.de += var1;
   }

   public void S(int var1) {
      this.df += var1;
   }

   public void T(int var1) {
      this.dg += var1;
   }

   public void U(int var1) {
      this.dh += var1;
   }

   public void V(int var1) {
      this.di += var1;
   }

   public void W(int var1) {
      this.dj += var1;
   }

   public void X(int var1) {
      this.dk += var1;
   }

   public void Y(int var1) {
      this.dl += var1;
   }

   public void Z(int var1) {
      this.dm += var1;
   }

   public void aa(int var1) {
      this.dn += var1;
   }

   public void ab(int var1) {
      this.l1r_do_field += var1;
   }

   public void ac(int var1) {
      this.dr += var1;
   }

   public int ar() {
      return this.t;
   }

   public int as() {
      return this.v;
   }

   public CopyOnWriteArrayList<Integer> at() {
      return this.G;
   }

   public L1AccountInventory au() {
      return this.H;
   }

   public L1ElvenInventory av() {
      return this.I;
   }

   public L1CharInventory aw() {
      return this.J;
   }

   public L1Inventory ax() {
      return this.K;
   }

   public int ay() {
      return this.M;
   }

   public void ad(int var1) {
      this.M = var1;
   }

   public int az() {
      return this.N;
   }

   public void ae(int var1) {
      this.N = var1;
   }

   public boolean aA() {
      return this.O;
   }

   public void b(boolean var1) {
      this.O = var1;
   }

   public int aB() {
      return this.P;
   }

   public L1ClassFeature aC() {
      return this.Q;
   }

   public int aD() {
      return this.S;
   }

   public void af(int var1) {
      this.S = var1;
   }

   public int aE() {
      return this.T;
   }

   public void ag(int var1) {
      this.T = var1;
   }

   public int aF() {
      return this.U;
   }

   public void ah(int var1) {
      this.U = var1;
   }

   public String aG() {
      return this.V;
   }

   public void c(String var1) {
      this.V = var1;
   }

   public int aH() {
      return this.W;
   }

   public void ai(int var1) {
      this.W = var1;
   }

   public Timestamp aI() {
      return this.X;
   }

   public void a(Timestamp var1) {
      this.X = var1;
   }

   public int aJ() {
      return this.Y;
   }

   public void aj(int var1) {
      this.Y = var1;
   }

   public ClientThread aK() {
      return this.Z;
   }

   public void a(ClientThread var1) {
      this.Z = var1;
   }

   public L1Party aL() {
      return this.aa;
   }

   public void a(L1Party var1) {
      this.aa = var1;
   }

   public L1ChatParty aM() {
      return this.ab;
   }

   public void a(L1ChatParty var1) {
      this.ab = var1;
   }

   public int aN() {
      return this.ac;
   }

   public void ak(int var1) {
      this.ac = var1;
   }

   public int aO() {
      return this.ad;
   }

   public void al(int var1) {
      this.ad = var1;
   }

   public boolean aP() {
      return this.ae;
   }

   public void c(boolean var1) {
      this.ae = var1;
   }

   public int aQ() {
      return this.af;
   }

   public void am(int var1) {
      this.af = var1;
   }

   public boolean aR() {
      return this.ag;
   }

   public void d(boolean var1) {
      this.ag = var1;
   }

   public boolean aS() {
      return this.ah;
   }

   public void e(boolean var1) {
      this.ah = var1;
   }

   public boolean aT() {
      return this.ai;
   }

   public void f(boolean var1) {
      this.ai = var1;
   }

   public CopyOnWriteArrayList<L1PrivateShopSellList> aU() {
      return this.aj;
   }

   public ArrayList<L1PrivateShopBuyList> aV() {
      return this.ak;
   }

   public byte[] aW() {
      return this.al;
   }

   public void a(byte[] var1) {
      this.al = var1;
   }

   public boolean aX() {
      return this.am;
   }

   public void g(boolean var1) {
      this.am = var1;
   }

   public boolean aY() {
      return this.an;
   }

   public void h(boolean var1) {
      this.an = var1;
   }

   public int aZ() {
      return this.ao;
   }

   public void an(int var1) {
      this.ao = var1;
   }

   public CopyOnWriteArrayList<L1BookMark> ba() {
      return this.ar;
   }

   public L1Quest bb() {
      return this.at;
   }

   public String bc() {
      return this.au;
   }

   public void d(String var1) {
      this.au = var1;
   }

   public int bd() {
      return this.av;
   }

   public int be() {
      return this.aw;
   }

   public int bf() {
      return this.ax;
   }

   public int bg() {
      return this.ay;
   }

   public int bh() {
      return this.az;
   }

   public int bi() {
      return this.aA;
   }

   public int bj() {
      return this.aB;
   }

   public int bk() {
      return this.aC;
   }

   public int bl() {
      return this.aD;
   }

   public int bm() {
      return this.aE;
   }

   public int bn() {
      return this.aF;
   }

   public int bo() {
      return this.aG;
   }

   public int bp() {
      return this.aH;
   }

   public int bq() {
      return this.aI;
   }

   public void ao(int var1) {
      this.aI = var1;
   }

   public int br() {
      return this.aJ;
   }

   public void ap(int var1) {
      this.aJ = var1;
   }

   public int bs() {
      return this.aK;
   }

   public void aq(int var1) {
      this.aK = var1;
   }

   public int bt() {
      return this.aL;
   }

   public void ar(int var1) {
      this.aL = var1;
   }

   public int bu() {
      return this.aM;
   }

   public void as(int var1) {
      this.aM = var1;
   }

   public int bv() {
      return this.aN;
   }

   public void at(int var1) {
      this.aN = var1;
   }

   public int bw() {
      return this.aO;
   }

   public void au(int var1) {
      this.aO = var1;
   }

   public int bx() {
      return this.aP;
   }

   public void av(int var1) {
      this.aP = var1;
   }

   public int by() {
      return this.aQ;
   }

   public void aw(int var1) {
      this.aQ = var1;
   }

   public int bz() {
      return this.aR;
   }

   public void ax(int var1) {
      this.aR = var1;
   }

   public int bA() {
      return this.aS;
   }

   public void ay(int var1) {
      this.aS = var1;
   }

   public int bB() {
      return this.aT;
   }

   public void az(int var1) {
      this.aT = var1;
   }

   public int bC() {
      return this.aU;
   }

   public void aA(int var1) {
      this.aU = var1;
   }

   public int bD() {
      return this.aV;
   }

   public void aB(int var1) {
      this.aV = var1;
   }

   public int bE() {
      return this.aW;
   }

   public void aC(int var1) {
      this.aW = var1;
   }

   public int bF() {
      return this.aX;
   }

   public void aD(int var1) {
      this.aX = var1;
   }

   public int bG() {
      return this.aY;
   }

   public void aE(int var1) {
      this.aY = var1;
   }

   public int bH() {
      return this.aZ;
   }

   public void aF(int var1) {
      this.aZ = var1;
   }

   public int bI() {
      return this.ba;
   }

   public void aG(int var1) {
      this.ba = var1;
   }

   public boolean bJ() {
      return this.bb;
   }

   public void i(boolean var1) {
      this.bb = var1;
   }

   public L1EquipmentSlot bK() {
      return this.bc;
   }

   public long bL() {
      return this.bd;
   }

   public int bM() {
      return this.be;
   }

   public boolean bN() {
      return this.bi;
   }

   public void j(boolean var1) {
      this.bi = var1;
   }

   public Timestamp bO() {
      return this.bk;
   }

   public void b(Timestamp var1) {
      this.bk = var1;
   }

   public Timestamp bP() {
      return this.bl;
   }

   public void c(Timestamp var1) {
      this.bl = var1;
   }

   public Timestamp bQ() {
      return this.bm;
   }

   public void d(Timestamp var1) {
      this.bm = var1;
   }

   public Timestamp bR() {
      return this.bn;
   }

   public void e(Timestamp var1) {
      this.bn = var1;
   }

   public int bS() {
      return this.bo;
   }

   public int bT() {
      return this.bp;
   }

   public int bU() {
      return this.bq;
   }

   public int bV() {
      return this.br;
   }

   public int bW() {
      return this.bs;
   }

   public int bX() {
      return this.bt;
   }

   public int bY() {
      return this.bu;
   }

   public int bZ() {
      return this.bv;
   }

   public int ca() {
      return this.bw;
   }

   public void aH(int var1) {
      this.bw = var1;
   }

   public boolean cb() {
      return this.bx;
   }

   public void k(boolean var1) {
      this.bx = var1;
   }

   public boolean cc() {
      return this.by;
   }

   public void l(boolean var1) {
      this.by = var1;
   }

   public L1ExcludingList cd() {
      return this.bA;
   }

   public L1SpeedChecker ce() {
      return this.bB;
   }

   public int cf() {
      return this.bC;
   }

   public void aI(int var1) {
      this.bC = var1;
   }

   public int cg() {
      return this.bD;
   }

   public void aJ(int var1) {
      this.bD = var1;
   }

   public int ch() {
      return this.bE;
   }

   public void aK(int var1) {
      this.bE = var1;
   }

   public int ci() {
      return this.bF;
   }

   public void aL(int var1) {
      this.bF = var1;
   }

   public int cj() {
      return this.bG;
   }

   public void aM(int var1) {
      this.bG = var1;
   }

   public boolean ck() {
      return this.bH;
   }

   public void m(boolean var1) {
      this.bH = var1;
   }

   public boolean cl() {
      return this.bI;
   }

   public void n(boolean var1) {
      this.bI = var1;
   }

   public boolean cm() {
      return this.bJ;
   }

   public void o(boolean var1) {
      this.bJ = var1;
   }

   public boolean cn() {
      return this.bK;
   }

   public void p(boolean var1) {
      this.bK = var1;
   }

   public boolean co() {
      return this.bL;
   }

   public void q(boolean var1) {
      this.bL = var1;
   }

   public int cp() {
      return this.bM;
   }

   public void aN(int var1) {
      this.bM = var1;
   }

   public boolean cq() {
      return this.bN;
   }

   public void r(boolean var1) {
      this.bN = var1;
   }

   public int cr() {
      return this.bO;
   }

   public void aO(int var1) {
      this.bO = var1;
   }

   public int cs() {
      return this.bP;
   }

   public void aP(int var1) {
      this.bP = var1;
   }

   public int ct() {
      return this.bS;
   }

   public void aQ(int var1) {
      this.bS = var1;
   }

   public int cu() {
      return this.bT;
   }

   public void aR(int var1) {
      this.bT = var1;
   }

   public boolean cv() {
      return this.bU;
   }

   public void s(boolean var1) {
      this.bU = var1;
   }

   public int cw() {
      return this.bV;
   }

   public void aS(int var1) {
      this.bV = var1;
   }

   public int cx() {
      return this.bW;
   }

   public void aT(int var1) {
      this.bW = var1;
   }

   public boolean cy() {
      return this.bX;
   }

   public void t(boolean var1) {
      this.bX = var1;
   }

   public int cz() {
      return this.bY;
   }

   public void aU(int var1) {
      this.bY = var1;
   }

   public int[] cA() {
      return this.bZ;
   }

   public Timestamp cB() {
      return this.ca;
   }

   public void f(Timestamp var1) {
      this.ca = var1;
   }

   public int cC() {
      return this.cb;
   }

   public int cD() {
      return this.cc;
   }

   public int cE() {
      return this.cd;
   }

   public void aV(int var1) {
      this.cd = var1;
   }

   public int cF() {
      return this.ce;
   }

   public int cG() {
      return this.cf;
   }

   public void aW(int var1) {
      this.cg = var1;
   }

   public void aX(int var1) {
      this.ch = var1;
   }

   public void aY(int var1) {
      this.ci = var1;
   }

   public boolean cH() {
      return this.cj;
   }

   public void u(boolean var1) {
      this.cj = var1;
   }

   public int cI() {
      return this.ck;
   }

   public void aZ(int var1) {
      this.ck = var1;
   }

   public int cJ() {
      return this.cl;
   }

   public void ba(int var1) {
      this.cl = var1;
   }

   public int cK() {
      return this.cm;
   }

   public void bb(int var1) {
      this.cm = var1;
   }

   public boolean cL() {
      return this.cn;
   }

   public void v(boolean var1) {
      this.cn = var1;
   }

   public boolean cM() {
      return this.co;
   }

   public void w(boolean var1) {
      this.co = var1;
   }

   public boolean cN() {
      return this.cp;
   }

   public void x(boolean var1) {
      this.cp = var1;
   }

   public FishingTimer cO() {
      return this.cq;
   }

   public void a(FishingTimer var1) {
      this.cq = var1;
   }

   public int cP() {
      return this.cr;
   }

   public void bc(int var1) {
      this.cr = var1;
   }

   public int cQ() {
      return this.cs;
   }

   public void bd(int var1) {
      this.cs = var1;
   }

   public boolean cR() {
      return this.ct;
   }

   public void y(boolean var1) {
      this.ct = var1;
   }

   public int cS() {
      return this.cu;
   }

   public void be(int var1) {
      this.cu = var1;
   }

   public int cT() {
      return this.cv;
   }

   public void bf(int var1) {
      this.cv = var1;
   }

   public int cU() {
      return this.cw;
   }

   public void bg(int var1) {
      this.cw = var1;
   }

   public int cV() {
      return this.cx;
   }

   public void bh(int var1) {
      this.cx = var1;
   }

   public int cW() {
      return this.cy;
   }

   public void bi(int var1) {
      this.cy = var1;
   }

   public int cX() {
      return this.cz;
   }

   public void bj(int var1) {
      this.cz = var1;
   }

   public int cY() {
      return this.cA;
   }

   public void bk(int var1) {
      this.cA = var1;
   }

   public int cZ() {
      return this.cB;
   }

   public void bl(int var1) {
      this.cB = var1;
   }

   public boolean da() {
      return this.cC;
   }

   public void z(boolean var1) {
      this.cC = var1;
   }

   public boolean db() {
      return this.cD;
   }

   public void A(boolean var1) {
      this.cD = var1;
   }

   public boolean dc() {
      return this.cE;
   }

   public void B(boolean var1) {
      this.cE = var1;
   }

   public void C(boolean var1) {
      this.cG = var1;
   }

   public boolean dd() {
      return this.cH;
   }

   public void D(boolean var1) {
      this.cH = var1;
   }

   public boolean de() {
      return this.cI;
   }

   public void E(boolean var1) {
      this.cI = var1;
   }

   public int df() {
      return this.cJ;
   }

   public void bm(int var1) {
      this.cJ = var1;
   }

   public boolean dg() {
      return this.cK;
   }

   public void F(boolean var1) {
      this.cK = var1;
   }

   public boolean dh() {
      return this.cL;
   }

   public void G(boolean var1) {
      this.cL = var1;
   }

   public boolean di() {
      return this.cM;
   }

   public void H(boolean var1) {
      this.cM = var1;
   }

   public boolean dj() {
      return this.cN;
   }

   public void I(boolean var1) {
      this.cN = var1;
   }

   public boolean dk() {
      return this.cO;
   }

   public void J(boolean var1) {
      this.cO = var1;
   }

   public boolean dl() {
      return this.cP;
   }

   public void K(boolean var1) {
      this.cP = var1;
   }

   public boolean dm() {
      return this.cQ;
   }

   public void L(boolean var1) {
      this.cQ = var1;
   }

   public int dn() {
      return this.cR;
   }

   public void bn(int var1) {
      this.cR = var1;
   }

   public int l1r_do_effect_heal() {
      return this.cS;
   }

   public void bo(int var1) {
      this.cS = var1;
   }

   public int dp() {
      return this.cT;
   }

   public void bp(int var1) {
      this.cT = var1;
   }

   public int dq() {
      return this.cU;
   }

   public void bq(int var1) {
      this.cU = var1;
   }

   public boolean dr() {
      return this.cV;
   }

   public void M(boolean var1) {
      this.cV = var1;
   }

   public boolean ds() {
      return this.cW;
   }

   public void N(boolean var1) {
      this.cW = var1;
   }

   public boolean dt() {
      return this.cX;
   }

   public void O(boolean var1) {
      this.cX = var1;
   }

   public boolean du() {
      return this.cY;
   }

   public void P(boolean var1) {
      this.cY = var1;
   }

   public boolean dv() {
      return this.cZ;
   }

   public void Q(boolean var1) {
      this.cZ = var1;
   }

   public boolean dw() {
      return this.da;
   }

   public void R(boolean var1) {
      this.da = var1;
   }

   public boolean dx() {
      return this.db;
   }

   public void S(boolean var1) {
      this.db = var1;
   }

   public int[] dy() {
      return this.dc;
   }

   public int dz() {
      return this.dd;
   }

   public int dA() {
      return this.de;
   }

   public int dB() {
      return this.df;
   }

   public int dC() {
      return this.dg;
   }

   public int dD() {
      return this.dh;
   }

   public int dE() {
      return this.di;
   }

   public int dF() {
      return this.dj;
   }

   public int dG() {
      return this.dk;
   }

   public int dH() {
      return this.dl;
   }

   public int dI() {
      return this.dm;
   }

   public int dJ() {
      return this.dn;
   }

   public int dK() {
      return this.l1r_do_field;
   }

   public boolean dL() {
      return this.dp;
   }

   public void T(boolean var1) {
      this.dp = var1;
   }

   public int dM() {
      return this.dq;
   }

   public void br(int var1) {
      this.dq = var1;
   }

   public int dN() {
      return this.dr;
   }

   public int dO() {
      return this.ds;
   }

   public void bs(int var1) {
      this.ds = var1;
   }

   public int dP() {
      return this.dt;
   }

   public void bt(int var1) {
      this.dt = var1;
   }

   public int[] dQ() {
      return this.du;
   }

   public void a(int[] var1) {
      this.du = var1;
   }

   public int[] dR() {
      return this.dv;
   }

   public void b(int[] var1) {
      this.dv = var1;
   }

   public HashMap<Integer, L1QuestNew> dS() {
      return this.dw;
   }

   public ArrayList<Integer> dT() {
      return this.dx;
   }

   public void a(ArrayList<Integer> var1) {
      this.dx = var1;
   }

   public ArrayList<Integer> dU() {
      return this.dy;
   }

   public void b(ArrayList<Integer> var1) {
      this.dy = var1;
   }

   public int dV() {
      return this.dz;
   }

   public void bu(int var1) {
      this.dz = var1;
   }

   public int dW() {
      return this.dA;
   }

   public void bv(int var1) {
      this.dA = var1;
   }

   public int dX() {
      return this.dB;
   }

   public void bw(int var1) {
      this.dB = var1;
   }

   public int[][] dY() {
      return this.dC;
   }

   public void a(int[][] var1) {
      this.dC = var1;
   }

   // $VF: synthetic method
   @Override
   public L1Inventory y() {
      return this.j();
   }

   private class a extends TimerTask {
      private a() {
      }

      @Override
      public void run() {
         L1PcInstance.this.a(new S_Disconnect(0));
      }

      // $VF: synthetic method
      a(L1PcInstance.a var2) {
         this();
      }
   }

   private class b implements Runnable {
      private final L1Character b;

      public b(L1Character var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         try {
            L1PcInstance.this.a(0);
            L1PcInstance.this.l(false);

            while (L1PcInstance.this.aR()) {
               Thread.sleep(300L);
            }

            L1PcInstance.this.b();
            L1PcInstance.this.d();
            L1PcInstance.this.fq().a(L1PcInstance.this.fu(), true);
            L1PcInstance.this.aM(L1PcInstance.this.bB(67) ? L1PcInstance.this.fe() : L1PcInstance.this.aB());
            new S_044().a(L1PcInstance.this, 1);
            if (L1PcInstance.this.bB(4007)) {
               L1PcInstance.this.bz(4007);
            }

            if (L1PcInstance.this.bB(3048)) {
               L1PcInstance.this.bz(3048);
            }

            L1PcInstance.this.a(new S_DoActionGFX(L1PcInstance.this.fr(), 8));
            L1PcInstance.this.b(new S_DoActionGFX(L1PcInstance.this.fr(), 8));
            if (this.b != L1PcInstance.this && L1PcInstance.this.c(this.b)) {
               return;
            }

            if (this.b instanceof L1PcInstance) {
               L1PcInstance var1 = (L1PcInstance)this.b;
               if (L1PcInstance.this.cp() == var1.fr() && var1.cp() == L1PcInstance.this.fr()) {
                  L1PcInstance.this.aN(0);
                  L1PcInstance.this.a(new S_PacketBox(5, 0, 0));
                  var1.aN(0);
                  var1.a(new S_PacketBox(5, 0, 0));
                  return;
               }

               if (L1PcInstance.this.bB(8000) && var1.ev() > L1PcInstance.this.ev() + Config.aQ) {
                  L1PcInstance.this.a(new S_ServerMessage(3799));
                  return;
               }
            }

            if (L1PcInstance.this.af()) {
               return;
            }

            if (!L1PcInstance.this.fq().m()) {
               L1PcInstance.this.a(new S_ServerMessage(3888));
               return;
            }

            if (L1CastleWar.a().a((L1Character)L1PcInstance.this)) {
               L1PcInstance.this.a(new S_ServerMessage(3800));
               return;
            }

            if (!L1PcInstance.this.fu().e()) {
               L1PcInstance.this.a(new S_ServerMessage(3800));
               return;
            }

            if ((L1PcInstance.this.fp() == 1700 || L1PcInstance.this.fp() == 1703) && L1PcInstance.this.j().h(21397)) {
               L1PcInstance.this.a(new S_ServerMessage(3803));

               for (L1ItemInstance var8 : L1PcInstance.this.j().d()) {
                  if (var8.N() == 21397 && var8.D()) {
                     L1PcInstance.this.j().f(var8);
                     if (this.b instanceof L1PcInstance) {
                        L1ItemInstance var11 = ItemTable.a().b(640751);
                        L1World.a().a(L1PcInstance.this.fs() + Random.a(4) - 2, L1PcInstance.this.ft() + Random.a(4) - 2, L1PcInstance.this.fp()).d(var11);
                     }
                  }
               }

               return;
            }

            int var6 = (int)(((L1PcInstance.this.fa() + 32768.0) / 1000.0 - 65.0) * 4.0);
            if (var6 < 0) {
               var6 *= -1;
               if (L1PcInstance.this.fa() < 0) {
                  var6 *= 2;
               }

               if (var6 > Random.a(1000)) {
                  int var2 = 1;
                  if (L1PcInstance.this.fa() <= -30000) {
                     var2 = Random.a(4) + 1;
                  } else if (L1PcInstance.this.fa() <= -20000) {
                     var2 = Random.a(3) + 1;
                  } else if (L1PcInstance.this.fa() <= -10000) {
                     var2 = Random.a(2) + 1;
                  } else if (L1PcInstance.this.fa() < 0) {
                     var2 = Random.a(1) + 1;
                  }

                  L1PcInstance.this.cI(var2);
               }
            }

            L1PcInstance.this.fx();
            L1PcInstance.this.l(true);
            if (this.b instanceof L1GuardInstance) {
               if (L1PcInstance.this.aD() > 0) {
                  L1PcInstance.this.af(L1PcInstance.this.aD() - 1);
               }
            } else if (this.b instanceof L1GuardianInstance && L1PcInstance.this.aE() > 0) {
               L1PcInstance.this.ag(L1PcInstance.this.aE() - 1);
            }

            L1PcInstance.this.b((Timestamp)null);
            L1PcInstance.this.c((Timestamp)null);
            if (this.b instanceof L1PcInstance) {
               L1PcInstance var9 = (L1PcInstance)this.b;
               if (L1PcInstance.this.fa() >= 0 && !L1PcInstance.this.aT()) {
                  boolean var3 = false;
                  if (var9.fa() < 30000) {
                     var9.af(var9.aD() + 1);
                     if (var9.A() && L1PcInstance.this.A()) {
                        var9.ag(var9.aE() + 1);
                     }

                     var3 = true;
                  }

                  var9.fy();
                  if (var9.fa() == 32767) {
                     var9.b((Timestamp)null);
                  }

                  if (var9.A() && L1PcInstance.this.A()) {
                     var9.fz();
                  }

                  int var4 = -1 * (int)(Math.pow(var9.ev(), 3.0) * 0.08);
                  if (var9.ev() < 50) {
                     var4 = -1 * (int)(Math.pow(var9.ev(), 2.0) * 4.0);
                  }

                  if (var9.fa() - 1000 < var4) {
                     var4 = var9.fa() - 1000;
                  }

                  if (var4 <= -32768) {
                     var4 = -32768;
                  }

                  var9.cr(var4);
                  var9.a(new S_Lawful(var9.fr(), var9.fa()));
                  var9.b(new S_Lawful(var9.fr(), var9.fa()));
                  if (var3 && var9.aD() >= 5 && var9.aD() < 10) {
                     var9.a(new S_RedMessage(551, String.valueOf(var9.aD()), "10"));
                  } else if (var3 && var9.aD() >= 10) {
                     HellTimer.a().a(var9, true);
                  }
               } else {
                  L1PcInstance.this.f(false);
               }
            }

            L1PcInstance.this.aq = GeneralThreadPool.a().a(L1PcInstance.this.new a(null), 600000L);
         } catch (Exception var5) {
            L1PcInstance.s.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
         }
      }
   }

   private class c extends TimerTask {
      private c() {
      }

      @Override
      public void run() {
         try {
            if (L1PcInstance.this.aK() == null) {
               L1PcInstance.this.bh.cancel(true);
               return;
            }

            int var1 = L1GameTimeClock.a().b().c();
            if (var1 % 300 == 0) {
               L1PcInstance.this.a(new S_GameTime(var1));
            }
         } catch (Exception var2) {
            L1PcInstance.s.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      // $VF: synthetic method
      c(L1PcInstance.c var2) {
         this();
      }
   }
}
