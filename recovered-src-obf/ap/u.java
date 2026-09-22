/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.ah;
import ao.j;
import ap.e;
import ap.h;
import ap.k;
import ap.n;
import ap.o;
import ap.q;
import ap.s;
import ap.t;
import ap.v;
import ap.z;
import aq.aa;
import aq.ac;
import aq.ad;
import aq.ae;
import aq.am;
import aq.af;
import aq.ao;
import aq.ap;
import aq.aq;
import aq.i;
import aq.m;
import aq.w;
import aq.x;
import au.f;
import au.g;
import ay.d;
import be.aj;
import be.ak;
import be.al;
import be.as;
import be.at;
import be.au;
import be.av;
import be.ay;
import be.az;
import be.ba;
import be.bi;
import be.bq;
import be.bv;
import be.ca;
import be.ch;
import be.ci;
import be.ck;
import be.cm;
import be.cz;
import be.dg;
import be.dh;
import be.do;
import be.ds;
import be.ee;
import be.eh;
import be.ep;
import be.eu;
import be.r;
import be.y;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class u
extends aq.f {
    private static final Logger s = Logger.getLogger(u.class.getName());
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
    private bc.b y;
    private ScheduledFuture<?> z;
    private bc.c A;
    private ScheduledFuture<?> B;
    private ScheduledFuture<?> C;
    private ScheduledFuture<?> D;
    private ScheduledFuture<?> E;
    private ScheduledFuture<?> F;
    private final CopyOnWriteArrayList<Integer> G = new CopyOnWriteArrayList();
    private final au.a H;
    private final au.d I;
    private final au.b J;
    private final f K = new f();
    private final g L;
    private int M;
    private int N = 0;
    private boolean O = false;
    private int P;
    private ar.a Q = null;
    private int R = 0;
    private int S = 0;
    private int T = 0;
    private int U = 0;
    private String V = "";
    private int W = 0;
    private Timestamp X;
    private int Y = 0;
    private bj.d Z;
    private ac aa;
    private aq.h ab;
    private int ac;
    private int ad;
    private boolean ae;
    private int af;
    private boolean ag = false;
    private boolean ah = false;
    private boolean ai = false;
    private final CopyOnWriteArrayList<bh.r> aj = new CopyOnWriteArrayList();
    private final ArrayList<bh.q> ak = new ArrayList();
    private byte[] al;
    private boolean am = false;
    private boolean an = false;
    private int ao = 0;
    private double ap = 0.0;
    private ScheduledFuture<?> aq;
    private final CopyOnWriteArrayList<bh.c> ar = new CopyOnWriteArrayList();
    private CopyOnWriteArrayList<q> as = new CopyOnWriteArrayList();
    private final af at;
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
    private final m bc;
    private long bd;
    private int be = 0;
    private int bf = 0;
    private static final long bg = 3000L;
    private ScheduledFuture<?> bh;
    private boolean bi = false;
    private ae l1rActivePolyMorphRule;
    private int l1rGhostSaveLocX;
    private int l1rGhostSaveLocY;
    private int l1rGhostSaveMapId;
    private int l1rGhostSaveHeading;
    private boolean l1rGhostSaveValid = false;
    private boolean l1rGhostReturnPending = false;
    private final aq.t bj = new aq.t();
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
    private final aq.n bA = new aq.n();
    private final aq.ak bB = new aq.ak(this);
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
    private bc.a cq = null;
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
    private int do = 0;
    private boolean dp = false;
    private int dq;
    private int dr = 0;
    private int ds = 0;
    private int dt = 0;
    private int[] du = new int[am.d.a().b()];
    private int[] dv = new int[am.d.a().b() * 3];
    private final HashMap<Integer, bh.s> dw = new HashMap();
    private ArrayList<Integer> dx = new ArrayList();
    private ArrayList<Integer> dy = new ArrayList();
    private int dz = 0;
    private int dA = 0;
    private int dB = -1;
    private int[][] dC = null;

    public void c(int i2) {
        this.u += i2;
        this.t = Math.max(0, this.u);
    }

    public void d(int i2) {
        this.w += i2;
        this.v = Math.max(0, this.w);
    }

    public void e(int state) {
        this.A.a(state);
        this.y.a(state);
    }

    public void a() {
        if (this.z == null) {
            this.y = new bc.b(this);
            this.z = bi.e.a().b(this.y, 1000L, 1000L);
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
            this.A = new bc.c(this);
            this.B = bi.e.a().b(this.A, 1000L, 1000L);
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
        this.F = bi.e.a().b(new ay.b(this.fr()), 0L, 200L);
    }

    public void f() {
        this.C = bi.e.a().b(new d(this.fr()), 0L, 500L);
        this.D = bi.e.a().b(new ay.c(this.fr()), 0L, 1000L);
        this.E = bi.e.a().b(new ay.a(this.fr()), 0L, 300L);
        this.bh = bi.e.a().a(new c(), 0L, 1000L);
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
        int char_level;
        int level = ao.w.c(this.m());
        int gap = level - (char_level = this.ev());
        if (gap == 0) {
            this.a(new ck(this));
            if (this.bB(4076)) {
                int bouns_per;
                int current_per = ao.w.a(char_level, this.m());
                int n2 = bouns_per = char_level <= 64 ? 10 : 5;
                if (current_per >= bouns_per) {
                    this.bz(4076);
                }
            }
            return;
        }
        if (gap > 0) {
            this.cJ(gap);
        } else if (gap < 0) {
            this.cK(gap);
        }
        int polyFrameGate = this.cL(level) - this.cL(char_level);
        if (polyFrameGate != 0) {
            this.a(new be.q(this));
            this.b(new be.q(this));
        }
    }

    @Override
    public void b(u receiver) {
        if (this.aA() || this.bN()) {
            return;
        }
        if (this.ff() && !receiver.bB(26003)) {
            return;
        }
        receiver.c((aa)this);
        receiver.a(new ch(this));
        if (this.ff() && receiver.bB(26003)) {
            receiver.a(new ch(this, true));
            receiver.a(new ep(this.fr(), true));
        }
        if (this.aK() != null && this.aK().e().a()) {
            receiver.a(new be.v(73, this.fr(), this.aK().e().h()));
        }
        if (this.q() && this.aL().d(receiver)) {
            receiver.a(new az(this));
        }
        if (this.aX()) {
            receiver.a(new al(this.fr(), 70, this.aW()));
        } else if (this.cq()) {
            receiver.a(new av(this.fr(), 71, this.cr(), this.cs()));
        }
    }

    private void fw() {
        for (aa known : this.eq()) {
            if (known == null || this.fu().e(known.fu())) continue;
            this.d(known);
            this.a(new dh(known));
        }
    }

    public void h() {
        this.fw();
        for (aa visible : aq.aq.a().b((aa)this, -1)) {
            s npc;
            s npc2;
            if (!this.b(visible)) {
                visible.b(this);
            } else if (visible instanceof s && (npc2 = (s)visible).ac() != 0) {
                npc2.d(this);
            }
            boolean isHpBar = false;
            if (visible instanceof s && (npc = (s)visible).d((aq.f)this)) {
                isHpBar = true;
            }
            if (!this.bB(26001) && !isHpBar || !al.aa.a(visible)) continue;
            this.a(new az((aq.f)visible));
        }
    }

    public void i() {
        if (this.bN() || this.ff() || this.aA()) {
            this.a(new bi(this.fr(), 1));
        }
        if (this.aK() != null && this.aK().e().a()) {
            this.a(new be.v(73, this.fr(), this.aK().e().h()));
            this.b(new be.v(73, this.fr(), this.aK().e().h()));
        }
    }

    public void f(int skillid) {
        if (!this.G.contains(skillid)) {
            this.G.add(skillid);
        }
    }

    public void g(int skillid) {
        if (this.G.contains(skillid)) {
            this.G.remove((Object)skillid);
        }
    }

    public boolean h(int skillid) {
        return this.G.contains(skillid);
    }

    public u() {
        this.L = new g(this);
        this.H = new au.a(this);
        this.I = new au.d(this);
        this.J = new au.b(this);
        this.at = new af(this);
        this.bc = new m(this);
    }

    @Override
    public synchronized void a(int i2) {
        if (this.ea() == i2) {
            return;
        }
        int currentHp = i2;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
        this.a(new ba(currentHp, this.ew()));
        if (this.q()) {
            this.aL().f(this);
        }
    }

    @Override
    public synchronized void i_(int i2) {
        if (this.eb() == i2) {
            return;
        }
        int currentMp = i2;
        if (currentMp >= this.ex()) {
            currentMp = this.ex();
        }
        this.by(currentMp);
        this.a(new bv(currentMp, this.ex()));
    }

    public g j() {
        return this.L;
    }

    public int k() {
        if (this.v() == null) {
            return 0;
        }
        if (this.w() == 2) {
            return 88;
        }
        return this.v().a().aO();
    }

    public boolean l() {
        return this.N == 200;
    }

    public void i(int i2) {
        this.P = i2;
        this.Q = ar.a.a(i2);
    }

    @Override
    public synchronized int m() {
        return this.R;
    }

    @Override
    public synchronized void k(int i2) {
        this.R = i2;
    }

    public void n() {
        this.X = new Timestamp(System.currentTimeMillis());
    }

    public int o() {
        if (this.X != null) {
            SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyyMMdd");
            int BornTime = Integer.parseInt(SimpleDate.format(this.X.getTime()));
            return BornTime;
        }
        return 0;
    }

    private void a(List<u> playersArray) {
        for (u player : playersArray) {
            if (!player.b((aa)this)) continue;
            player.d(this);
            player.a(new dh(this));
        }
    }

    public void p() {
        try {
            i clan;
            if (this.eX()) {
                aq.aq.a().d(this);
                Thread.sleep(2000L);
                int[] loc = aq.o.a(this);
                this.cG(loc[0]);
                this.cH(loc[1]);
                this.cE(loc[2]);
                this.a(this.ev());
                this.c_(40);
            }
            if (this.aF() != 0 && (clan = ao.q.a().a(this.aF())) != null) {
                if (clan.o() == this.fr()) {
                    clan.i(0);
                }
                if (clan.b().size() <= 3) {
                    for (u clanMember : clan.b()) {
                        clanMember.bz(4084);
                        clanMember.a(new cm(180, 450, 3240, 0));
                    }
                }
            }
            if (this.aO() != 0) {
                aq.ao.b(this);
            }
            if (this.cp() != 0) {
                int fightId = this.cp();
                this.aN(0);
                u fightPc = (u)aq.aq.a().a(fightId);
                if (fightPc != null) {
                    fightPc.aN(0);
                    fightPc.a(new cm(5, 0, 0));
                }
            }
            if (this.q()) {
                this.aL().b(this);
            }
            if (this.r()) {
                this.aM().c(this);
            }
            for (t npc : this.ek().values()) {
                if (npc instanceof v) {
                    v pet = (v)npc;
                    pet.ax();
                    pet.h();
                    this.ek().remove(pet.fr());
                    pet.aa_();
                    continue;
                }
                if (!(npc instanceof z)) continue;
                z summon = (z)npc;
                if (summon.ah()) {
                    continue;
                }
                for (u visiblePc : aq.aq.a().f(summon)) {
                    if (visiblePc.fr() == this.fr()) continue;
                    visiblePc.a(new eh(summon, visiblePc, false));
                }
                summon.h();
            }
            for (e doll : this.el().values()) {
                doll.e();
            }
            for (k follower : this.em().values()) {
                if (follower == null || follower.ah()) {
                    continue;
                }
                follower.V(true);
                follower.a(follower.z(), follower.fs(), follower.ft(), follower.fb(), follower.fp());
                follower.aa_();
            }
            bh.c.a(this.ba());
            ao.h.a(this);
            this.ei();
            ao.m.a().a(this);
            ao.n.a().c(this);
            ao.az.a().c(this);
            ao.j.a().a(this);
            this.fv();
            this.aC(0);
            this.f(new Timestamp(System.currentTimeMillis()));
            aq.x.a().a(this);
            ao.a.a().b(this.aK().e(), false);
            this.I();
            this.J();
            aq.aq.a().d(this);
            aq.aq.a().b(this);
            this.a(this.er());
            this.a(aq.aq.a().f(this));
            this.L.g();
            this.H.g();
            this.es();
            this.b();
            this.d();
            this.X(true);
            this.a((bj.d)null);
        }
        catch (Exception e2) {
            s.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public boolean q() {
        return this.aa != null;
    }

    public boolean r() {
        return this.ab != null;
    }

    public void a(eu serverbasepacket) {
        if (this.Z == null || this.bE() == 0) {
            return;
        }
        try {
            if (l1j.server.a.e) {
                System.out.println(String.valueOf(this.bc()) + "/" + serverbasepacket.b() + "/" + serverbasepacket.a().length);
                System.out.println(bi.g.a(serverbasepacket.a()));
            }
            this.Z.a(serverbasepacket);
        }
        catch (Exception e2) {
            s.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    @Override
    public void c(u attacker) {
        this.a(attacker, 0);
    }

    @Override
    public void a(u attacker, int skillId) {
        if (attacker == null) {
            return;
        }
        if (this.aR()) {
            return;
        }
        if (this.ep() == 1 || attacker.ep() == 1 || this.a(this, attacker, false)) {
            new aq.c(attacker, this, skillId).c();
            return;
        }
        if (this.ea() > 0 && !this.eX()) {
            boolean isCounterBarrier = false;
            aq.c attack = new aq.c(attacker, this, skillId);
            if (attack.a()) {
                if (this.bB(91)) {
                    w magic = new w(this, attacker);
                    boolean isProbability = magic.a(91);
                    boolean isShortDistance = attack.f();
                    if (isProbability && isShortDistance) {
                        isCounterBarrier = true;
                    }
                }
                if (this.bB(191) && bi.i.a(100) < 10) {
                    isCounterBarrier = true;
                }
                int advence = 0;
                double hpRange = 0.4 + (double)this.dH() * 0.01;
                if (this.bB(231) && this.ev() >= 80) {
                    advence = Math.min(5 + this.ev() - 80, 10);
                }
                if (this.bB(606) && (double)this.ea() < (double)this.ew() * hpRange && attack.f() && bi.i.a(100) < 34 + advence) {
                    isCounterBarrier = true;
                }
                if (this.bB(607) && (double)this.ea() < (double)this.ew() * hpRange && !attack.f() && bi.i.a(100) < 34 + advence) {
                    isCounterBarrier = true;
                }
                if (!isCounterBarrier) {
                    attacker.a((aq.f)this);
                    attack.b();
                    attack.a((aq.f)attacker, (aq.f)this);
                }
            }
            if (isCounterBarrier) {
                attack.b();
                attack.e();
                attack.g();
            } else {
                attack.c();
                attack.d();
            }
        }
    }

    public boolean a(u pc, aq.f target, boolean isRangeSkillTarget) {
        u targetpc = null;
        if (target instanceof u) {
            targetpc = (u)target;
        } else if (target instanceof v) {
            targetpc = (u)((v)target).M();
        } else if (target instanceof z) {
            targetpc = (u)((z)target).M();
        } else if (target instanceof s) {
            return false;
        }
        if (targetpc == null) {
            return true;
        }
        for (ap war : aq.aq.a().i()) {
            if (pc.aF() == 0 || targetpc.aF() == 0 || !war.c(pc.aG(), targetpc.aG())) continue;
            return false;
        }
        if (target instanceof u) {
            u targetPc = (u)target;
            if (as.b.a().a((aq.f)pc) && as.b.a().a((aq.f)targetPc)) {
                return false;
            }
        }
        if (l1j.server.a.R) {
            if (pc.fu().e(new bi.h(33080, 33376)) && pc.fu().d() && target.fu().e(new bi.h(33080, 33376)) && target.fu().d()) {
                return false;
            }
            return pc.fp() < 10500 || pc.fp() > 10502;
        }
        return !(isRangeSkillTarget ? this.fq().c(this.fu()) : !this.fq().b(this.fu()));
    }

    public void a(aq.f target) {
        for (t pet : this.ek().values()) {
            if (pet instanceof v) {
                v pets = (v)pet;
                pets.g(target);
                continue;
            }
            if (!(pet instanceof z)) continue;
            z summon = (z)pet;
            summon.g(target);
        }
    }

    public void s() {
        this.bz(60);
        this.bz(97);
    }

    public void a(aq.f attacker, int mpDamage) {
        int newMp;
        if (mpDamage <= 0 || this.eX()) {
            return;
        }
        this.s();
        if (attacker instanceof u) {
            u atk_pc = (u)attacker;
            aq.ad.a(this, atk_pc);
        }
        if ((newMp = this.eb() - mpDamage) > this.ex()) {
            newMp = this.ex();
        } else if (newMp <= 0) {
            newMp = 0;
        }
        this.i_(newMp);
    }

    public void a(aq.f attacker, double damage, boolean isMagicDamage) {
        int newHp;
        if (this.ea() <= 0) {
            if (!this.eX()) {
                this.b(attacker);
            }
            return;
        }
        if (!this.b((aa)attacker) && attacker.fp() == this.fp() && !(attacker instanceof h)) {
            attacker.b(this);
        }
        if (isMagicDamage) {
            double nowTime = System.currentTimeMillis();
            double interval = (20.0 - (nowTime - this.ap) / 100.0) % 20.0;
            if (damage > 0.0) {
                if (interval > 0.0) {
                    damage *= 1.0 - interval / 30.0;
                }
                if (damage < 1.0) {
                    damage = 0.0;
                }
                this.ap = nowTime;
            }
        }
        if (damage > 0.0) {
            this.s();
            if (attacker instanceof u) {
                aq.ad.a(this, attacker);
            }
            this.bz(66);
            this.bz(153);
        }
        if (this.cG) {
            damage *= 1.5;
        }
        if (this.bB(219)) {
            damage *= 1.2;
        }
        if ((attacker instanceof v || attacker instanceof z) && (this.ep() == 1 || attacker.ep() == 1 || this.a(this, attacker, false))) {
            damage = 0.0;
        }
        if ((newHp = this.ea() - (int)damage) <= 10 && this.bB(135)) {
            int mp = this.eb();
            if (damage <= (double)mp) {
                this.i_(mp - (int)damage);
                return;
            }
            this.i_(0);
            damage -= (double)mp;
        }
        if (this.cI && damage > 20.0 && bi.i.a(100) < 5) {
            this.a(new ee(this.fr(), 2188));
            this.b(new ee(this.fr(), 2188));
            int[] addMp = new int[]{15, 10, 20, 20, 20, 20, 20, 10};
            this.i_(this.eb() + addMp[this.ay()]);
        }
        if (this.cH && damage > 20.0 && bi.i.a(100) < 5) {
            this.a(new ee(this.fr(), 2187));
            this.b(new ee(this.fr(), 2187));
            int healHP = this.ew() / 10;
            if (this.bB(173)) {
                healHP /= 2;
            }
            if (this.bB(170)) {
                healHP *= 2;
            }
            newHp += healHP;
        }
        if (newHp > this.ew()) {
            newHp = this.ew();
        }
        if (newHp <= 0) {
            if (this.l()) {
                this.a(this.ew());
            } else {
                this.b(attacker);
            }
            return;
        }
        this.a(newHp);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(aq.f lastAttacker) {
        u u2 = this;
        synchronized (u2) {
            if (this.eX()) {
                return;
            }
            this.X(true);
            this.cq(8);
        }
        if (this.aO() != 0) {
            aq.ao.b(this);
        }
        bi.e.a().b(new b(lastAttacker));
    }

    public void t() {
        if (this.aq != null) {
            this.aq.cancel(true);
            this.aq = null;
        }
    }

    private void cI(int count) {
        int i2 = 0;
        while (i2 < count) {
            q item = this.j().n();
            if (item != null) {
                this.j().a(item, item.d() ? item.E() : 1, (f)aq.aq.a().a(this.fs(), this.ft(), this.fp()));
                this.a(new ds(638, item.s()));
            }
            ++i2;
        }
    }

    private boolean c(aq.f lastAttacker) {
        if (this.aF() == 0 || l1j.server.a.Y) {
            return false;
        }
        i clan = ao.q.a().a(this.aF());
        u attacker = null;
        if (lastAttacker instanceof u) {
            attacker = (u)lastAttacker;
        } else if (lastAttacker instanceof v) {
            attacker = (u)((v)lastAttacker).M();
        } else if (lastAttacker instanceof z) {
            attacker = (u)((z)lastAttacker).M();
        } else {
            return false;
        }
        ap clanWar = aq.aq.a().c(clan.f());
        if (clanWar == null || clanWar.c() != 2) {
            return false;
        }
        if (attacker == null || attacker.aF() == 0) {
            return false;
        }
        if (!clanWar.c(this.aG(), attacker.aG())) {
            return false;
        }
        if (this.fr() == clan.k()) {
            for (i enemyClan : clanWar.c(this.aG())) {
                clanWar.b(this.aG(), enemyClan.f());
            }
        }
        return true;
    }

    public void a(boolean isFull) {
        int oldLevel = this.ev();
        int needExp = ao.w.b(oldLevel);
        int rate = isFull ? 2 : 1;
        int exp = 0;
        if (oldLevel < 45) {
            exp = (int)((double)needExp * 0.05 * (double)rate);
        } else if (oldLevel == 45) {
            exp = (int)((double)needExp * 0.045 * (double)rate);
        } else if (oldLevel == 46) {
            exp = (int)((double)needExp * 0.04 * (double)rate);
        } else if (oldLevel == 47) {
            exp = (int)((double)needExp * 0.035 * (double)rate);
        } else if (oldLevel == 48) {
            exp = (int)((double)needExp * 0.03 * (double)rate);
        } else if (oldLevel >= 49) {
            exp = (int)((double)needExp * 0.025 * (double)rate);
        }
        if (exp == 0) {
            return;
        }
        this.x(exp);
    }

    private void fx() {
        int oldLevel = this.ev();
        int needExp = ao.w.b(oldLevel);
        int exp = 0;
        if (oldLevel >= 1 && oldLevel < 11) {
            exp = 0;
        } else if (oldLevel >= 11 && oldLevel < 45) {
            exp = (int)((double)needExp * 0.1);
        } else if (oldLevel == 45) {
            exp = (int)((double)needExp * 0.09);
        } else if (oldLevel == 46) {
            exp = (int)((double)needExp * 0.08);
        } else if (oldLevel == 47) {
            exp = (int)((double)needExp * 0.07);
        } else if (oldLevel == 48) {
            exp = (int)((double)needExp * 0.06);
        } else if (oldLevel >= 49) {
            exp = (int)((double)needExp * 0.05);
        }
        if (exp == 0) {
            return;
        }
        if (this.ca() >= 0) {
            this.aH(this.ca() + 1);
        }
        this.x(-exp);
    }

    public int u() {
        if (this.bB(174) || this.aC() == null) {
            return 0;
        }
        int er2 = this.aC().e(this.ev()) + bi.d.b(this.eB());
        if (this.bB(160)) {
            er2 += 5;
        }
        if (this.bB(111)) {
            er2 += 12;
        }
        if (this.bB(90)) {
            er2 += 15;
        }
        if (this.bB(4062)) {
            ++er2;
        }
        if (this.bB(4066)) {
            er2 += 3;
        }
        if (this.bB(4085)) {
            er2 += 5;
        }
        return er2 += this.cF;
    }

    public bh.c a(String name) {
        for (bh.c element : this.ar) {
            if (!element.c().equalsIgnoreCase(name)) continue;
            return element;
        }
        return null;
    }

    public bh.c l(int id) {
        for (bh.c element : this.ar) {
            if (element.a() != id) continue;
            return element;
        }
        return null;
    }

    public bh.c a(int x2, int y2) {
        if (x2 == 0 || y2 == 0) {
            return null;
        }
        for (bh.c element : this.ar) {
            if (element.d() != x2 || element.e() != y2) continue;
            return element;
        }
        return null;
    }

    public q v() {
        if (this.as.isEmpty()) {
            return null;
        }
        return this.as.get(bi.i.a(this.as.size()));
    }

    public boolean a(q weapon) {
        return this.as.contains(weapon);
    }

    public int w() {
        return this.as.size();
    }

    public void a(CopyOnWriteArrayList<q> weapon) {
        this.as = weapon;
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

    public void m(int i2) {
        if ((i2 += this.av) >= Short.MAX_VALUE) {
            i2 = Short.MAX_VALUE;
        } else if (i2 < 1) {
            i2 = 1;
        }
        this.bH(i2 - this.av);
        this.av = i2;
    }

    public void n(int i2) {
        if ((i2 += this.aw) >= Short.MAX_VALUE) {
            i2 = Short.MAX_VALUE;
        } else if (i2 < 0) {
            i2 = 0;
        }
        this.bJ(i2 - this.aw);
        this.aw = i2;
    }

    public void o(int i2) {
        if ((i2 += this.ax) >= 127) {
            i2 = 127;
        } else if (i2 < 1) {
            i2 = 1;
        }
        this.bN(i2 - this.ax);
        this.ax = i2;
    }

    public void p(int i2) {
        if ((i2 += this.ay) >= 127) {
            i2 = 127;
        } else if (i2 < 1) {
            i2 = 1;
        }
        this.bP(i2 - this.ay);
        this.ay = i2;
    }

    public void q(int i2) {
        if ((i2 += this.az) >= 127) {
            i2 = 127;
        } else if (i2 < 1) {
            i2 = 1;
        }
        this.bR(i2 - this.az);
        this.az = i2;
    }

    public void r(int i2) {
        if ((i2 += this.aA) >= 127) {
            i2 = 127;
        } else if (i2 < 1) {
            i2 = 1;
        }
        this.bT(i2 - this.aA);
        this.aA = i2;
    }

    public void s(int i2) {
        if ((i2 += this.aB) >= 127) {
            i2 = 127;
        } else if (i2 < 1) {
            i2 = 1;
        }
        this.bV(i2 - this.aB);
        this.aB = i2;
    }

    public void t(int i2) {
        if ((i2 += this.aC) >= 127) {
            i2 = 127;
        } else if (i2 < 1) {
            i2 = 1;
        }
        this.bX(i2 - this.aC);
        this.aC = i2;
    }

    public synchronized void u(int i2) {
        this.aY += i2;
    }

    @Override
    public void c_(int i2) {
        super.c_(i2);
        this.G();
    }

    public void G() {
        if (this.fj() >= 225) {
            this.bd = System.currentTimeMillis() / 1000L;
        }
        this.a(new at(this.fj() >= 225));
    }

    public void H() {
        ++this.be;
        this.a(new ck(this));
    }

    public static u b(String charName) {
        u result = null;
        try {
            result = ao.o.a().b(charName);
        }
        catch (Exception e2) {
            s.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        return result;
    }

    public void I() {
        if (this.bN()) {
            return;
        }
        if (this.cv()) {
            return;
        }
        try {
            ao.o.a().b(this);
        }
        catch (Exception e2) {
            s.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public void J() {
        for (q item : this.j().d()) {
            this.j().i(item);
        }
    }

    public double K() {
        int weightReductionByMagic = 0;
        if (this.bB(14) || this.bB(218)) {
            weightReductionByMagic = 180;
        } else if (this.bB(134)) {
            weightReductionByMagic = 300;
        }
        double weightReduction = 1.0 + (double)(this.bS() / 100);
        double maxWeight = 1000.0 + Math.floor((this.ez() + this.eA()) / 2) * 100.0;
        maxWeight *= weightReduction;
        maxWeight += (double)(weightReductionByMagic + this.bT());
        return maxWeight *= l1j.server.a.J;
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

    public synchronized void w(int counter) {
        this.bf += counter;
    }

    public void O() {
        this.w(1);
        bi.e.a().b(new ay.e(this.fr()), 3000L);
    }

    public synchronized void x(int exp) {
        this.R += exp;
        if (this.R > 1859065562) {
            this.R = 1859065562;
        }
    }

    private void cJ(int gap) {
        this.Z();
        if (this.ev() == 99 && l1j.server.a.ag) {
            ao.ah.a(this, 43000, 1);
        } else if (this.ev() == 70 && this.cE() > 0) {
            aq.x.a().a(this.cE(), this.et());
            ao.ah.a(this, 21143, 1);
        }
        int i2 = 0;
        while (i2 < gap) {
            int randomHp = bi.d.a(this);
            int randomMp = bi.d.b(this);
            this.m(randomHp);
            this.n(randomMp);
            ++i2;
        }
        this.fB();
        this.fA();
        this.W();
        this.Y();
        this.I();
        if (this.ev() >= 51 && this.ev() - 50 > this.bA() && this.bf() + this.bh() + this.bg() + this.bj() + this.bk() + this.bi() < 270) {
            int bonus = this.ev() - 50 - this.bA();
            this.a(new ca(479, String.valueOf(bonus)));
        }
        if (this.ev() >= 52) {
            int bufftime = 10800;
            this.j(4076, 10800000);
            this.a(new cm(86, 173, 1, 675));
        }
        this.a(new ck(this));
        this.ac();
        ao.az.a().a(this);
    }

    private void cK(int gap) {
        this.Z();
        int i2 = 0;
        while (i2 > gap) {
            int randomHp = bi.d.a(this);
            int randomMp = bi.d.b(this);
            this.m(-randomHp);
            this.n(-randomMp);
            --i2;
        }
        this.fB();
        this.fA();
        this.W();
        this.Y();
        this.I();
        this.a(new ck(this));
        this.ac();
    }

    @Override
    public void y(int effectId) {
        this.a(new cz(this.fr(), effectId));
        if (this.aA() || this.bN()) {
            return;
        }
        if (this.ff()) {
            this.c(new cz(this.fr(), effectId));
        } else {
            this.b(new cz(this.fr(), effectId));
        }
    }

    @Override
    public void z(int pt) {
        super.z(pt);
        this.a(new ba(this));
    }

    @Override
    public int P() {
        return this.bj.a();
    }

    @Override
    public void A(int i2) {
        this.bj.a(i2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void B(int i2) {
        aq.t t2 = this.bj;
        synchronized (t2) {
            this.bj.b(i2);
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
        }
        if (System.currentTimeMillis() - this.bk.getTime() > 86400000L) {
            this.b((Timestamp)null);
            return false;
        }
        return true;
    }

    private void fz() {
        this.bl = new Timestamp(System.currentTimeMillis());
    }

    public boolean T() {
        if (this.bl == null) {
            return false;
        }
        if (System.currentTimeMillis() - this.bl.getTime() > 86400000L) {
            this.c((Timestamp)null);
            return false;
        }
        return true;
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

    public void C(int i2) {
        this.bo += i2;
    }

    public void D(int i2) {
        this.bp += i2;
    }

    public void E(int i2) {
        this.bq += i2;
    }

    public void F(int i2) {
        this.br += i2;
    }

    public void G(int i2) {
        this.bs += i2;
    }

    public void H(int i2) {
        this.bt += i2;
    }

    public void I(int i2) {
        this.bu += i2;
    }

    public void J(int i2) {
        this.bv += i2;
    }

    private void fA() {
        int newBaseDmgup = this.aC().f(this.ev());
        int newBaseBowDmgup = this.aC().g(this.ev());
        this.ck(newBaseDmgup - this.aD);
        this.cl(newBaseBowDmgup - this.aE);
        this.aD = newBaseDmgup;
        this.aE = newBaseBowDmgup;
    }

    private void fB() {
        int newBaseHitup = this.aC().h(this.ev());
        int newBaseBowHitup = this.aC().i(this.ev());
        this.cm(newBaseHitup - this.aF);
        this.cn(newBaseBowHitup - this.aG);
        this.aF = newBaseHitup;
        this.aG = newBaseBowHitup;
    }

    public void W() {
        if (this.aC() == null) {
            return;
        }
        int newAc = 10 + bi.d.a(this.eB()) + this.aC().d(this.ev());
        this.bL(newAc - this.bz);
        if (this.bz != newAc) {
            this.bz = newAc;
            this.a(new ci(this));
        }
    }

    @Override
    public int W_() {
        if (this.bB(153)) {
            return (this.x + this.aC().c()) / 4;
        }
        return this.x + this.aC().c();
    }

    public void Y() {
        int newBaseMr = this.ev() / 2;
        this.co((newBaseMr += bi.d.e(this.eE())) - this.aH);
        this.aH = newBaseMr;
    }

    public void Z() {
        this.b(ao.w.c(this.R));
        for (bh.s qn : this.dS().values()) {
            if (qn.n() <= 0) continue;
            qn.a(this.ev());
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
        long nowChatTimeInMillis = System.currentTimeMillis();
        if (this.bQ == 0) {
            this.bQ = (byte)(this.bQ + 1);
            this.bR = nowChatTimeInMillis;
            return;
        }
        long chatInterval = nowChatTimeInMillis - this.bR;
        if (chatInterval > 2000L) {
            this.bQ = 0;
            this.bR = 0L;
        } else {
            if (this.bQ >= 3) {
                this.j(1005, 120000);
                this.a(new cm(36, 120));
                this.a(new ds(153));
                this.bQ = 0;
                this.bR = 0L;
            }
            this.bQ = (byte)(this.bQ + 1);
        }
    }

    public void b(int oldType, int newType) {
        switch (oldType) {
            case 1: {
                this.bL(2);
                this.co(-3);
                this.a(new au(0, 0));
                break;
            }
            case 2: {
                this.bL(4);
                this.co(-6);
                this.a(new au(1, 0));
                break;
            }
            case 3: {
                this.bL(6);
                this.co(-9);
                this.a(new au(2, 0));
                break;
            }
            case -1: {
                this.ck(-1);
                this.cl(-1);
                this.cp(-1);
                this.a(new au(3, 0));
                break;
            }
            case -2: {
                this.ck(-3);
                this.cl(-3);
                this.cp(-2);
                this.a(new au(4, 0));
                break;
            }
            case -3: {
                this.ck(-5);
                this.cl(-5);
                this.cp(-3);
                this.a(new au(5, 0));
            }
        }
        switch (newType) {
            case 1: {
                this.bL(-2);
                this.co(3);
                this.a(new au(0, 1));
                break;
            }
            case 2: {
                this.bL(-4);
                this.co(6);
                this.a(new au(1, 1));
                break;
            }
            case 3: {
                this.bL(-6);
                this.co(9);
                this.a(new au(2, 1));
                break;
            }
            case -1: {
                this.ck(1);
                this.cl(1);
                this.cp(1);
                this.a(new au(3, 1));
                break;
            }
            case -2: {
                this.ck(3);
                this.cl(3);
                this.cp(2);
                this.a(new au(4, 1));
                break;
            }
            case -3: {
                this.ck(5);
                this.cl(5);
                this.cp(3);
                this.a(new au(5, 1));
            }
        }
        this.a(new do(this));
    }

    public void ac() {
        if (!l1j.server.a.aO) {
            return;
        }
        if (this.ev() > l1j.server.a.aP) {
            if (this.bB(8000)) {
                this.bz(8000);
                this.a(new au(6, 0));
            }
        } else if (!this.bB(8000)) {
            this.j(8000, 0);
            this.a(new au(6, 1));
        }
    }

    public void ad() {
        HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();
        for (q item : this.j().d()) {
            int index;
            if (!item.D() || (index = this.j().l(item)) < 0) continue;
            items.put(item.fr(), index);
        }
        this.a(new as(items));
    }

    public void a(int x2, int y2, int mapid) {
        this.bZ = new int[]{x2, y2, mapid};
    }

    public void ae() {
        if (this.eX()) {
            return;
        }
        if (this.j().f(640100)) {
            new al.ay().a(this);
            this.j(25005, 600000);
        }
        if (this.j().f(640102) && !this.af()) {
            this.j(4058, 0);
            this.cp(30);
            this.a(new do(this));
            this.bH(400);
            this.bJ(200);
            this.a(new bv(this.eb(), this.ex()));
            this.a(new ba(this.ea(), this.ew()));
            if (this.q()) {
                this.aL().f(this);
            }
            this.a(new cm(144, 1));
            this.a(new r(this.fr(), this.et()));
            this.b(new r(this.fr(), this.et()));
            for (t npc : this.ek().values()) {
                npc.b(new y(npc.fr(), String.valueOf(this.et()) + "\u7684"));
            }
            for (e doll : this.el().values()) {
                doll.b(new y(doll.fr(), String.valueOf(this.et()) + "\u7684"));
            }
        }
    }

    public boolean af() {
        return this.bB(4058);
    }

    public void K(int i2) {
        this.cb = Math.min(Math.max(0, i2), 3850000);
        int percent = this.cb / 7700;
        if (this.cc != percent) {
            this.cc = percent;
            this.a(new cm(82, percent, this.cb));
        }
    }

    public int ag() {
        for (e doll : this.el().values()) {
            if (doll.X_().au() <= 0 || doll.X_().bs() <= bi.i.a(100) + 1) continue;
            this.a(new ee(this.fr(), 6319));
            this.b(new ee(this.fr(), 6319));
            return doll.X_().au();
        }
        return 0;
    }

    public int ah() {
        for (e doll : this.el().values()) {
            if (doll.X_().ap() <= 0 || doll.X_().bx() <= bi.i.a(100) + 1) continue;
            this.a(new ee(this.fr(), 6329));
            this.b(new ee(this.fr(), 6329));
            return doll.X_().ap();
        }
        return 0;
    }

    public boolean ai() {
        for (e doll : this.el().values()) {
            if (doll.X_().bt() <= 0 || doll.X_().bt() <= bi.i.a(100) + 1) continue;
            this.a(new ee(this.fr(), 6320));
            this.b(new ee(this.fr(), 6320));
            return true;
        }
        return false;
    }

    public void L(int i2) {
        this.ce += i2;
    }

    public void M(int i2) {
        this.cf += i2;
    }

    public boolean aj() {
        if (this.cg == 0) {
            return false;
        }
        return this.cg > bi.i.a(100) + 1;
    }

    public boolean ak() {
        if (this.ch == 0) {
            return false;
        }
        return this.ch > bi.i.a(100) + 1;
    }

    public boolean al() {
        if (this.ci == 0) {
            return false;
        }
        return this.ci > bi.i.a(100) + 1;
    }

    public boolean am() {
        if (!this.q()) {
            return false;
        }
        return this.aL().e(this);
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

    private int cL(int level) {
        int[] levelGate = new int[]{1, 15, 30, 45, 50, 52, 55, 60, 65, 70, 75, 80, 82, 84};
        int frameLevel = 0;
        while (frameLevel < levelGate.length) {
            if (level < levelGate[frameLevel]) {
                return frameLevel - 1;
            }
            ++frameLevel;
        }
        return levelGate.length - 1;
    }

    public boolean N(int itemobjid) {
        for (t petNpc : this.ek().values()) {
            v pet;
            if (!(petNpc instanceof v) || itemobjid != (pet = (v)petNpc).k()) continue;
            return true;
        }
        return false;
    }

    public boolean O(int itemobjid) {
        for (e doll : this.el().values()) {
            if (itemobjid != doll.f()) continue;
            return true;
        }
        return false;
    }

    public void P(int i2) {
        this.cF += i2;
    }

    public void b(int itemid, int wish_count, int enchantlv) {
        this.dc = new int[]{itemid, wish_count, enchantlv};
    }

    public void Q(int i2) {
        this.dd += i2;
    }

    public void R(int i2) {
        this.de += i2;
    }

    public void S(int i2) {
        this.df += i2;
    }

    public void T(int i2) {
        this.dg += i2;
    }

    public void U(int i2) {
        this.dh += i2;
    }

    public void V(int i2) {
        this.di += i2;
    }

    public void W(int i2) {
        this.dj += i2;
    }

    public void X(int i2) {
        this.dk += i2;
    }

    public void Y(int i2) {
        this.dl += i2;
    }

    public void Z(int i2) {
        this.dm += i2;
    }

    public void aa(int i2) {
        this.dn += i2;
    }

    public void ab(int i2) {
        this.do += i2;
    }

    public void ac(int i2) {
        this.dr += i2;
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

    public au.a au() {
        return this.H;
    }

    public au.d av() {
        return this.I;
    }

    public au.b aw() {
        return this.J;
    }

    public f ax() {
        return this.K;
    }

    public int ay() {
        return this.M;
    }

    public void ad(int type) {
        this.M = type;
    }

    public int az() {
        return this.N;
    }

    public void ae(int accessLevel) {
        this.N = accessLevel;
    }

    public boolean aA() {
        return this.O;
    }

    public void b(boolean isGmInvis) {
        this.O = isGmInvis;
    }

    public int aB() {
        return this.P;
    }

    public ar.a aC() {
        return this.Q;
    }

    public int aD() {
        return this.S;
    }

    public void af(int PKcount) {
        this.S = PKcount;
    }

    public int aE() {
        return this.T;
    }

    public void ag(int PkCountForElf) {
        this.T = PkCountForElf;
    }

    public int aF() {
        return this.U;
    }

    public void ah(int clanid) {
        this.U = clanid;
    }

    public String aG() {
        return this.V;
    }

    public void c(String clanName) {
        this.V = clanName;
    }

    public int aH() {
        return this.W;
    }

    public void ai(int clanRank) {
        this.W = clanRank;
    }

    public Timestamp aI() {
        return this.X;
    }

    public void a(Timestamp birthday) {
        this.X = birthday;
    }

    public int aJ() {
        return this.Y;
    }

    public void aj(int sex) {
        this.Y = sex;
    }

    public bj.d aK() {
        return this.Z;
    }

    public void a(bj.d netConnection) {
        this.Z = netConnection;
    }

    public ac aL() {
        return this.aa;
    }

    public void a(ac party) {
        this.aa = party;
    }

    public aq.h aM() {
        return this.ab;
    }

    public void a(aq.h chatParty) {
        this.ab = chatParty;
    }

    public int aN() {
        return this.ac;
    }

    public void ak(int partyID) {
        this.ac = partyID;
    }

    public int aO() {
        return this.ad;
    }

    public void al(int tradeID) {
        this.ad = tradeID;
    }

    public boolean aP() {
        return this.ae;
    }

    public void c(boolean tradeOk) {
        this.ae = tradeOk;
    }

    public int aQ() {
        return this.af;
    }

    public void am(int tempID) {
        this.af = tempID;
    }

    public boolean aR() {
        return this.ag;
    }

    public void d(boolean isTeleport) {
        this.ag = isTeleport;
    }

    public boolean aS() {
        return this.ah;
    }

    public void e(boolean isDrink) {
        this.ah = isDrink;
    }

    public boolean aT() {
        return this.ai;
    }

    public void f(boolean isPinkName) {
        this.ai = isPinkName;
    }

    public CopyOnWriteArrayList<bh.r> aU() {
        return this.aj;
    }

    public ArrayList<bh.q> aV() {
        return this.ak;
    }

    public byte[] aW() {
        return this.al;
    }

    public void a(byte[] shopChat) {
        this.al = shopChat;
    }

    public boolean aX() {
        return this.am;
    }

    public void g(boolean isPrivateShop) {
        this.am = isPrivateShop;
    }

    public boolean aY() {
        return this.an;
    }

    public void h(boolean isTradingInPrivateShop) {
        this.an = isTradingInPrivateShop;
    }

    public int aZ() {
        return this.ao;
    }

    public void an(int partnersPrivateShopItemCount) {
        this.ao = partnersPrivateShopItemCount;
    }

    public CopyOnWriteArrayList<bh.c> ba() {
        return this.ar;
    }

    public af bb() {
        return this.at;
    }

    public String bc() {
        return this.au;
    }

    public void d(String accountName) {
        this.au = accountName;
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

    public void ao(int originalStr) {
        this.aI = originalStr;
    }

    public int br() {
        return this.aJ;
    }

    public void ap(int originalCon) {
        this.aJ = originalCon;
    }

    public int bs() {
        return this.aK;
    }

    public void aq(int originalDex) {
        this.aK = originalDex;
    }

    public int bt() {
        return this.aL;
    }

    public void ar(int originalCha) {
        this.aL = originalCha;
    }

    public int bu() {
        return this.aM;
    }

    public void as(int originalInt) {
        this.aM = originalInt;
    }

    public int bv() {
        return this.aN;
    }

    public void at(int originalWis) {
        this.aN = originalWis;
    }

    public int bw() {
        return this.aO;
    }

    public void au(int advenHp) {
        this.aO = advenHp;
    }

    public int bx() {
        return this.aP;
    }

    public void av(int advenHp2) {
        this.aP = advenHp2;
    }

    public int by() {
        return this.aQ;
    }

    public void aw(int advenMp) {
        this.aQ = advenMp;
    }

    public int bz() {
        return this.aR;
    }

    public void ax(int highDamageLevel) {
        this.aR = highDamageLevel;
    }

    public int bA() {
        return this.aS;
    }

    public void ay(int bonusStats) {
        this.aS = bonusStats;
    }

    public int bB() {
        return this.aT;
    }

    public void az(int elixirStats) {
        this.aT = elixirStats;
    }

    public int bC() {
        return this.aU;
    }

    public void aA(int elfAttr) {
        this.aU = elfAttr;
    }

    public int bD() {
        return this.aV;
    }

    public void aB(int partnerId) {
        this.aV = partnerId;
    }

    public int bE() {
        return this.aW;
    }

    public void aC(int onlineStatus) {
        this.aW = onlineStatus;
    }

    public int bF() {
        return this.aX;
    }

    public void aD(int homeTownId) {
        this.aX = homeTownId;
    }

    public int bG() {
        return this.aY;
    }

    public void aE(int contribution) {
        this.aY = contribution;
    }

    public int bH() {
        return this.aZ;
    }

    public void aF(int pay) {
        this.aZ = pay;
    }

    public int bI() {
        return this.ba;
    }

    public void aG(int hellTime) {
        this.ba = hellTime;
    }

    public boolean bJ() {
        return this.bb;
    }

    public void i(boolean isBanned) {
        this.bb = isBanned;
    }

    public m bK() {
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

    public void j(boolean isGhost) {
        if (isGhost && !this.bi) {
            this.l1rGhostSaveLocX = this.fs();
            this.l1rGhostSaveLocY = this.ft();
            this.l1rGhostSaveMapId = this.fp();
            this.l1rGhostSaveHeading = this.fb();
            this.l1rGhostSaveValid = true;
            this.l1rGhostReturnPending = false;
        }
        this.bi = isGhost;
        if (!isGhost) {
            this.l1rGhostSaveValid = false;
            this.l1rGhostReturnPending = false;
        }
    }

    public ae getActivePolyMorphRule() {
        return this.l1rActivePolyMorphRule;
    }

    public void setActivePolyMorphRule(ae rule) {
        this.l1rActivePolyMorphRule = rule;
    }

    public void makeReadyEndGhost() {
        if (!this.bi || !this.l1rGhostSaveValid || this.l1rGhostReturnPending) {
            return;
        }
        this.l1rGhostReturnPending = true;
        am.a(this, this.l1rGhostSaveLocX, this.l1rGhostSaveLocY, this.l1rGhostSaveMapId, this.l1rGhostSaveHeading, true);
    }

    public void finishGhostReturn() {
        if (!this.l1rGhostReturnPending) {
            return;
        }
        this.bi = false;
        this.l1rGhostReturnPending = false;
        this.l1rGhostSaveValid = false;
    }

    public Timestamp bO() {
        return this.bk;
    }

    public void b(Timestamp lastPk) {
        this.bk = lastPk;
    }

    public Timestamp bP() {
        return this.bl;
    }

    public void c(Timestamp lastPkForElf) {
        this.bl = lastPkForElf;
    }

    public Timestamp bQ() {
        return this.bm;
    }

    public void d(Timestamp deleteTime) {
        this.bm = deleteTime;
    }

    public Timestamp bR() {
        return this.bn;
    }

    public void e(Timestamp tamUseTime) {
        this.bn = tamUseTime;
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

    public void aH(int expRes) {
        this.bw = expRes;
    }

    public boolean cb() {
        return this.bx;
    }

    public void k(boolean isGres) {
        this.bx = isGres;
    }

    public boolean cc() {
        return this.by;
    }

    public void l(boolean gresValid) {
        this.by = gresValid;
    }

    public aq.n cd() {
        return this.bA;
    }

    public aq.ak ce() {
        return this.bB;
    }

    public int cf() {
        return this.bC;
    }

    public void aI(int teleportX) {
        this.bC = teleportX;
    }

    public int cg() {
        return this.bD;
    }

    public void aJ(int teleportY) {
        this.bD = teleportY;
    }

    public int ch() {
        return this.bE;
    }

    public void aK(int teleportMapId) {
        this.bE = teleportMapId;
    }

    public int ci() {
        return this.bF;
    }

    public void aL(int teleportHeading) {
        this.bF = teleportHeading;
    }

    public int cj() {
        return this.bG;
    }

    public void aM(int tempCharGfxAtDead) {
        this.bG = tempCharGfxAtDead;
    }

    public boolean ck() {
        return this.bH;
    }

    public void m(boolean isCanWhisper) {
        this.bH = isCanWhisper;
    }

    public boolean cl() {
        return this.bI;
    }

    public void n(boolean isShowTradeChat) {
        this.bI = isShowTradeChat;
    }

    public boolean cm() {
        return this.bJ;
    }

    public void o(boolean isShowClanChat) {
        this.bJ = isShowClanChat;
    }

    public boolean cn() {
        return this.bK;
    }

    public void p(boolean isShowPartyChat) {
        this.bK = isShowPartyChat;
    }

    public boolean co() {
        return this.bL;
    }

    public void q(boolean isShowWorldChat) {
        this.bL = isShowWorldChat;
    }

    public int cp() {
        return this.bM;
    }

    public void aN(int fightId) {
        this.bM = fightId;
    }

    public boolean cq() {
        return this.bN;
    }

    public void r(boolean isFishing) {
        this.bN = isFishing;
    }

    public int cr() {
        return this.bO;
    }

    public void aO(int fishX) {
        this.bO = fishX;
    }

    public int cs() {
        return this.bP;
    }

    public void aP(int fishY) {
        this.bP = fishY;
    }

    public int ct() {
        return this.bS;
    }

    public void aQ(int callClanId) {
        this.bS = callClanId;
    }

    public int cu() {
        return this.bT;
    }

    public void aR(int callClanHeading) {
        this.bT = callClanHeading;
    }

    public boolean cv() {
        return this.bU;
    }

    public void s(boolean isInCharReset) {
        this.bU = isInCharReset;
    }

    public int cw() {
        return this.bV;
    }

    public void aS(int tempLevel) {
        this.bV = tempLevel;
    }

    public int cx() {
        return this.bW;
    }

    public void aT(int tempMaxLevel) {
        this.bW = tempMaxLevel;
    }

    public boolean cy() {
        return this.bX;
    }

    public void t(boolean isShapeChange) {
        this.bX = isShapeChange;
    }

    public int cz() {
        return this.bY;
    }

    public void aU(int partyType) {
        this.bY = partyType;
    }

    public int[] cA() {
        return this.bZ;
    }

    public Timestamp cB() {
        return this.ca;
    }

    public void f(Timestamp logoutTime) {
        this.ca = logoutTime;
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

    public void aV(int masterID) {
        this.cd = masterID;
    }

    public int cF() {
        return this.ce;
    }

    public int cG() {
        return this.cf;
    }

    public void aW(int poisonDollChance) {
        this.cg = poisonDollChance;
    }

    public void aX(int slowDollChance) {
        this.ch = slowDollChance;
    }

    public void aY(int curseDollChance) {
        this.ci = curseDollChance;
    }

    public boolean cH() {
        return this.cj;
    }

    public void u(boolean isBreathinWater) {
        this.cj = isBreathinWater;
    }

    public int cI() {
        return this.ck;
    }

    public void aZ(int bookmarkSpace) {
        this.ck = bookmarkSpace;
    }

    public int cJ() {
        return this.cl;
    }

    public void ba(int charStoreSpace) {
        this.cl = charStoreSpace;
    }

    public int cK() {
        return this.cm;
    }

    public void bb(int currentAttackID) {
        this.cm = currentAttackID;
    }

    public boolean cL() {
        return this.cn;
    }

    public void v(boolean isTripleArrow) {
        this.cn = isTripleArrow;
    }

    public boolean cM() {
        return this.co;
    }

    public void w(boolean isFoeSlayer) {
        this.co = isFoeSlayer;
    }

    public boolean cN() {
        return this.cp;
    }

    public void x(boolean isLoadWorldShop) {
        this.cp = isLoadWorldShop;
    }

    public bc.a cO() {
        return this.cq;
    }

    public void a(bc.a fishingTimer) {
        this.cq = fishingTimer;
    }

    public int cP() {
        return this.cr;
    }

    public void bc(int runeOpenStatus) {
        this.cr = runeOpenStatus;
    }

    public int cQ() {
        return this.cs;
    }

    public void bd(int worldShopAdenaRecord) {
        this.cs = worldShopAdenaRecord;
    }

    public boolean cR() {
        return this.ct;
    }

    public void y(boolean isHasMapTimer) {
        this.ct = isHasMapTimer;
    }

    public int cS() {
        return this.cu;
    }

    public void be(int mapTime_1) {
        this.cu = mapTime_1;
    }

    public int cT() {
        return this.cv;
    }

    public void bf(int mapTime_2) {
        this.cv = mapTime_2;
    }

    public int cU() {
        return this.cw;
    }

    public void bg(int mapTime_3) {
        this.cw = mapTime_3;
    }

    public int cV() {
        return this.cx;
    }

    public void bh(int mapTime_4) {
        this.cx = mapTime_4;
    }

    public int cW() {
        return this.cy;
    }

    public void bi(int mapTime_5) {
        this.cy = mapTime_5;
    }

    public int cX() {
        return this.cz;
    }

    public void bj(int mapTime_6) {
        this.cz = mapTime_6;
    }

    public int cY() {
        return this.cA;
    }

    public void bk(int mapTime_7) {
        this.cA = mapTime_7;
    }

    public int cZ() {
        return this.cB;
    }

    public void bl(int onlineGiftID) {
        this.cB = onlineGiftID;
    }

    public boolean da() {
        return this.cC;
    }

    public void z(boolean isEffectByEnchant_1) {
        this.cC = isEffectByEnchant_1;
    }

    public boolean db() {
        return this.cD;
    }

    public void A(boolean isEffectByEnchant_2) {
        this.cD = isEffectByEnchant_2;
    }

    public boolean dc() {
        return this.cE;
    }

    public void B(boolean isEffectByEnchant_3) {
        this.cE = isEffectByEnchant_3;
    }

    public void C(boolean effect_Berserker_Axe) {
        this.cG = effect_Berserker_Axe;
    }

    public boolean dd() {
        return this.cH;
    }

    public void D(boolean effect_Heal_Guard_Fafurion) {
        this.cH = effect_Heal_Guard_Fafurion;
    }

    public boolean de() {
        return this.cI;
    }

    public void E(boolean effect_Mana_Guard_Lindvior) {
        this.cI = effect_Mana_Guard_Lindvior;
    }

    public int df() {
        return this.cJ;
    }

    public void bm(int effect_Shield_of_Rebels) {
        this.cJ = effect_Shield_of_Rebels;
    }

    public boolean dg() {
        return this.cK;
    }

    public void F(boolean effect_DemeterBless) {
        this.cK = effect_DemeterBless;
    }

    public boolean dh() {
        return this.cL;
    }

    public void G(boolean effect_DemeterMpr) {
        this.cL = effect_DemeterMpr;
    }

    public boolean di() {
        return this.cM;
    }

    public void H(boolean effect_AncientDemeterBless) {
        this.cM = effect_AncientDemeterBless;
    }

    public boolean dj() {
        return this.cN;
    }

    public void I(boolean effect_HexagramMagic) {
        this.cN = effect_HexagramMagic;
    }

    public boolean dk() {
        return this.cO;
    }

    public void J(boolean effect_AncientHexagramMagic) {
        this.cO = effect_AncientHexagramMagic;
    }

    public boolean dl() {
        return this.cP;
    }

    public void K(boolean effect_HadesCloak) {
        this.cP = effect_HadesCloak;
    }

    public boolean dm() {
        return this.cQ;
    }

    public void L(boolean effect_GuardianAmulet) {
        this.cQ = effect_GuardianAmulet;
    }

    public int dn() {
        return this.cR;
    }

    public void bn(int effect_Bind) {
        this.cR = effect_Bind;
    }

    public int do() {
        return this.cS;
    }

    public void bo(int effect_Heal) {
        this.cS = effect_Heal;
    }

    public int dp() {
        return this.cT;
    }

    public void bp(int effect_SoulBarrier) {
        this.cT = effect_SoulBarrier;
    }

    public int dq() {
        return this.cU;
    }

    public void bq(int effect_ColdAttack) {
        this.cU = effect_ColdAttack;
    }

    public boolean dr() {
        return this.cV;
    }

    public void M(boolean effect_AbsoluteImperius) {
        this.cV = effect_AbsoluteImperius;
    }

    public boolean ds() {
        return this.cW;
    }

    public void N(boolean effect_Imperius) {
        this.cW = effect_Imperius;
    }

    public boolean dt() {
        return this.cX;
    }

    public void O(boolean effect_MoonAmulet) {
        this.cX = effect_MoonAmulet;
    }

    public boolean du() {
        return this.cY;
    }

    public void P(boolean effect_EverlastingLight1) {
        this.cY = effect_EverlastingLight1;
    }

    public boolean dv() {
        return this.cZ;
    }

    public void Q(boolean effect_EverlastingLight2) {
        this.cZ = effect_EverlastingLight2;
    }

    public boolean dw() {
        return this.da;
    }

    public void R(boolean effect_EverlastingLight3) {
        this.da = effect_EverlastingLight3;
    }

    public boolean dx() {
        return this.db;
    }

    public void S(boolean effect_OnePunch) {
        this.db = effect_OnePunch;
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
        return this.do;
    }

    public boolean dL() {
        return this.dp;
    }

    public void T(boolean isAntiPoison) {
        this.dp = isAntiPoison;
    }

    public int dM() {
        return this.dq;
    }

    public void br(int innRoomNumber) {
        this.dq = innRoomNumber;
    }

    public int dN() {
        return this.dr;
    }

    public int dO() {
        return this.ds;
    }

    public void bs(int magicScrollCount) {
        this.ds = magicScrollCount;
    }

    public int dP() {
        return this.dt;
    }

    public void bt(int braveAvatarLevel) {
        this.dt = braveAvatarLevel;
    }

    public int[] dQ() {
        return this.du;
    }

    public void a(int[] monsterList) {
        this.du = monsterList;
    }

    public int[] dR() {
        return this.dv;
    }

    public void b(int[] stageList) {
        this.dv = stageList;
    }

    public HashMap<Integer, bh.s> dS() {
        return this.dw;
    }

    public ArrayList<Integer> dT() {
        return this.dx;
    }

    public void a(ArrayList<Integer> equipList_1) {
        this.dx = equipList_1;
    }

    public ArrayList<Integer> dU() {
        return this.dy;
    }

    public void b(ArrayList<Integer> equipList_2) {
        this.dy = equipList_2;
    }

    public int dV() {
        return this.dz;
    }

    public void bu(int currentEquipPage) {
        this.dz = currentEquipPage;
    }

    public int dW() {
        return this.dA;
    }

    public void bv(int currentComboLevel) {
        this.dA = currentComboLevel;
    }

    public int dX() {
        return this.dB;
    }

    public void bw(int camp) {
        this.dB = camp;
    }

    public int[][] dY() {
        return this.dC;
    }

    public void a(int[][] weekMobList) {
        this.dC = weekMobList;
    }

    @Override
    public /* synthetic */ f y() {
        return this.j();
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            u.this.a(new aj(0));
        }
    }

    private class b
    implements Runnable {
        private final aq.f b;

        public b(aq.f _lastAttacker) {
            this.b = _lastAttacker;
        }

        @Override
        public void run() {
            try {
                u.this.a(0);
                u.this.l(false);
                while (u.this.aR()) {
                    Thread.sleep(300L);
                }
                u.this.b();
                u.this.d();
                u.this.fq().a(u.this.fu(), true);
                u.this.aM(u.this.bB(67) ? u.this.fe() : u.this.aB());
                new bf.as().a((aq.f)u.this, 1);
                if (u.this.bB(4007)) {
                    u.this.bz(4007);
                }
                if (u.this.bB(3048)) {
                    u.this.bz(3048);
                }
                u.this.a(new ak(u.this.fr(), 8));
                u.this.b(new ak(u.this.fr(), 8));
                if (this.b != u.this && u.this.c(this.b)) {
                    return;
                }
                if (this.b instanceof u) {
                    u fightPc = (u)this.b;
                    if (u.this.cp() == fightPc.fr() && fightPc.cp() == u.this.fr()) {
                        u.this.aN(0);
                        u.this.a(new cm(5, 0, 0));
                        fightPc.aN(0);
                        fightPc.a(new cm(5, 0, 0));
                        return;
                    }
                    if (u.this.bB(8000) && fightPc.ev() > u.this.ev() + l1j.server.a.aQ) {
                        u.this.a(new ds(3799));
                        return;
                    }
                }
                if (u.this.af()) {
                    return;
                }
                if (!u.this.fq().m()) {
                    u.this.a(new ds(3888));
                    return;
                }
                if (as.b.a().a((aq.f)u.this)) {
                    u.this.a(new ds(3800));
                    return;
                }
                if (!u.this.fu().e()) {
                    u.this.a(new ds(3800));
                    return;
                }
                if ((u.this.fp() == 1700 || u.this.fp() == 1703) && u.this.j().h(21397)) {
                    u.this.a(new ds(3803));
                    for (q item : u.this.j().d()) {
                        if (item.N() != 21397 || !item.D()) continue;
                        u.this.j().f(item);
                        if (!(this.b instanceof u)) continue;
                        q drop = ao.ah.a().b(640751);
                        aq.aq.a().a(u.this.fs() + bi.i.a(4) - 2, u.this.ft() + bi.i.a(4) - 2, u.this.fp()).d(drop);
                    }
                    return;
                }
                int lostRate = (int)((((double)u.this.fa() + 32768.0) / 1000.0 - 65.0) * 4.0);
                if (lostRate < 0) {
                    lostRate *= -1;
                    if (u.this.fa() < 0) {
                        lostRate *= 2;
                    }
                    if (lostRate > bi.i.a(1000)) {
                        int count = 1;
                        if (u.this.fa() <= -30000) {
                            count = bi.i.a(4) + 1;
                        } else if (u.this.fa() <= -20000) {
                            count = bi.i.a(3) + 1;
                        } else if (u.this.fa() <= -10000) {
                            count = bi.i.a(2) + 1;
                        } else if (u.this.fa() < 0) {
                            count = bi.i.a(1) + 1;
                        }
                        u.this.cI(count);
                    }
                }
                u.this.fx();
                u.this.l(true);
                if (this.b instanceof n) {
                    if (u.this.aD() > 0) {
                        u.this.af(u.this.aD() - 1);
                    }
                } else if (this.b instanceof o && u.this.aE() > 0) {
                    u.this.ag(u.this.aE() - 1);
                }
                u.this.b((Timestamp)null);
                u.this.c((Timestamp)null);
                if (this.b instanceof u) {
                    u player = (u)this.b;
                    if (u.this.fa() >= 0 && !u.this.aT()) {
                        boolean isChangePkCount = false;
                        if (player.fa() < 30000) {
                            player.af(player.aD() + 1);
                            if (player.A() && u.this.A()) {
                                player.ag(player.aE() + 1);
                            }
                            isChangePkCount = true;
                        }
                        player.fy();
                        if (player.fa() == Short.MAX_VALUE) {
                            player.b((Timestamp)null);
                        }
                        if (player.A() && u.this.A()) {
                            player.fz();
                        }
                        int lawful = -1 * (int)(Math.pow(player.ev(), 3.0) * 0.08);
                        if (player.ev() < 50) {
                            lawful = -1 * (int)(Math.pow(player.ev(), 2.0) * 4.0);
                        }
                        if (player.fa() - 1000 < lawful) {
                            lawful = player.fa() - 1000;
                        }
                        if (lawful <= Short.MIN_VALUE) {
                            lawful = Short.MIN_VALUE;
                        }
                        player.cr(lawful);
                        player.a(new bq(player.fr(), player.fa()));
                        player.b(new bq(player.fr(), player.fa()));
                        if (isChangePkCount && player.aD() >= 5 && player.aD() < 10) {
                            player.a(new dg(551, String.valueOf(player.aD()), "10"));
                        } else if (isChangePkCount && player.aD() >= 10) {
                            ba.g.a().a(player, true);
                        }
                    } else {
                        u.this.f(false);
                    }
                }
                u.this.aq = bi.e.a().a(new a(), 600000L);
            }
            catch (Exception e2) {
                s.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class c
    extends TimerTask {
        private c() {
        }

        @Override
        public void run() {
            try {
                if (u.this.aK() == null) {
                    u.this.bh.cancel(true);
                    return;
                }
                int serverTime = at.c.a().b().c();
                if (serverTime % 300 == 0) {
                    u.this.a(new ay(serverTime));
                }
            }
            catch (Exception e2) {
                s.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

