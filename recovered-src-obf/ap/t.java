/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.ar;
import ao.as;
import ao.au;
import ap.q;
import ap.s;
import ap.u;
import ap.v;
import ap.z;
import aq.aa;
import aq.ag;
import aq.aq;
import aq.w;
import aq.y;
import as.j;
import be.ak;
import be.cb;
import be.cc;
import be.cf;
import be.dh;
import be.ea;
import be.ee;
import be.p;
import bh.l;
import bh.m;
import bi.h;
import bi.i;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class t
extends aq.f {
    private static final Logger y = Logger.getLogger(t.class.getName());
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 0;
    public static final int f = 1;
    public static final int g = 2;
    private boolean z = true;
    protected static int h = 15;
    private int A = 0;
    private int B = 0;
    private int C = 0;
    private final int[][] D;
    protected CopyOnWriteArrayList<q> i;
    protected q j;
    protected aq.f k;
    protected aq.q l;
    protected aq.f m;
    private int E;
    protected aq.q n;
    private ScheduledFuture<?> F;
    private ScheduledFuture<?> G;
    private ConcurrentHashMap<Integer, Integer> H;
    private boolean I;
    private int J;
    private int K;
    protected au.f o;
    private l L;
    private int M;
    private ag N;
    private int O;
    public static final int p = 0;
    public static final int q = 1;
    public static int[] r = new int[]{40012, 40011, 40010};
    public static int[] s = new int[]{40018, 40013};
    private String P;
    private boolean Q;
    private boolean R;
    private boolean S;
    private int T;
    private int U;
    private boolean V;
    private int W;
    private boolean X;
    private int Y;
    private int Z;
    public static final int t = 0;
    public static final int u = 1;
    public static final int v = 2;
    private boolean aa;
    protected boolean w;
    private int ab;
    private boolean ac;
    private boolean ad;
    private boolean ae;
    private ScheduledFuture<?> af;
    private y ag;
    private int ah;
    private int ai;
    private int aj;
    private int ak;
    private int al;
    private String am;
    private int an;
    private boolean ao;
    private boolean ap;

    protected void q() {
        new f().a();
    }

    private boolean h() {
        this.v(300);
        this.d();
        if (this.m == null && this.k == null) {
            this.c();
        }
        this.a(true);
        this.Y_();
        if (this.m != null) {
            if (this.ac() != 0) {
                return true;
            }
            this.b();
            return false;
        }
        if (this.L.H()) {
            this.i();
            if (this.j == null) {
                this.j();
            }
            if (this.j != null) {
                au.e groundInventory = aq.a().a(this.j.fs(), this.j.ft(), this.j.fp());
                if (groundInventory.f(this.j.N())) {
                    this.k();
                } else {
                    this.i.remove(this.j);
                    this.j = null;
                    this.v(1000);
                }
                return false;
            }
        }
        return this.a();
    }

    public void a(boolean isChangeShape) {
    }

    public void Y_() {
    }

    @Override
    public void c(u pc) {
        this.a(pc, 0);
    }

    @Override
    public void a(u pc, int skillId) {
        aq.c attack = new aq.c(pc, this, skillId);
        if (attack.a()) {
            attack.b();
            attack.a((aq.f)pc, (aq.f)this);
        }
        attack.c();
        attack.d();
    }

    public void c() {
    }

    public void d() {
        if (this.m == null || this.m.fp() != this.fp() || this.m.ea() <= 0 || this.m.eX() || this.m.ff() && !this.V() && !this.n.a(this.m) || this.m.f(this) > 30) {
            this.s();
            if (!this.n.b()) {
                this.m = this.n.c();
                this.d();
            }
        }
    }

    public void c(aq.f cha, int hate) {
        if (cha != null && cha.fr() != this.fr()) {
            if (this.n.b() && hate != 0) {
                hate += this.ew() / 10;
            }
            this.n.a(cha, hate);
            this.l.a(cha, hate);
            this.m = this.n.c();
            this.d();
        }
    }

    public void a(aq.f cha) {
    }

    public void c(u tpc, int family) {
        for (aa obj : tpc.eq()) {
            if (!(obj instanceof t)) continue;
            t npc = (t)obj;
            if (npc.U_().E() > 0 && (npc.U_().D() == family || npc.U_().E() > 1)) {
                npc.a((aq.f)tpc);
            }
            if (this.ag == null || this.ah == 0 || npc.ak() != this.ah) continue;
            npc.a((aq.f)tpc);
        }
    }

    public void b() {
        this.w = true;
        this.i.clear();
        this.j = null;
        aq.f target = this.m;
        if (this.O() == 0) {
            if (this.N() > 0) {
                int escapeDistance;
                int n2 = escapeDistance = this.bB(40) ? 1 : 15;
                if (this.fu().c(target.fu()) >= escapeDistance) {
                    this.s();
                    return;
                }
                int dir = this.c(target.fs(), target.ft());
                dir = this.a(this.fs(), this.ft(), this.fp(), dir);
                this.g(dir);
                this.v(this.f(this.N(), 0));
            }
            return;
        }
        int sleepTime = ar.a().a(this, target);
        if (sleepTime > 0) {
            this.v(this.f(sleepTime, 2));
            return;
        }
        if (this.c(target.fs(), target.ft(), this.C())) {
            this.ct(this.h(target.fs(), target.ft()));
            this.b(target);
            return;
        }
        if (this.N() <= 0) {
            this.s();
            return;
        }
        if (this.U_().O()) {
            int distance = this.fu().d(target.fu());
            if (this.z && distance > 3 && distance < 15 && this.l(target.fs(), target.ft())) {
                this.z = false;
                return;
            }
            if (bi.i.a(100) < 20 && this.eb() >= 10 && distance > 6 && distance < 15 && this.l(target.fs(), target.ft())) {
                return;
            }
        }
        if (this.as()) {
            this.c();
            return;
        }
        int dir = this.a(target.fs(), target.ft());
        if (dir == -1) {
            this.c();
            return;
        }
        this.g(dir);
        this.v(this.f(this.N(), 0));
    }

    public void b(aq.f target) {
        t npc;
        u pc;
        aq.f cha;
        u pc2;
        if (this.k instanceof u && (pc2 = (u)this.k).aR()) {
            return;
        }
        if (target instanceof u ? (pc2 = (u)target).aR() : (target instanceof v || target instanceof z) && (cha = ((t)target).M()) instanceof u && (pc = (u)cha).aR()) {
            return;
        }
        if (target instanceof t && (npc = (t)target).ac() != 0) {
            this.t();
            return;
        }
        boolean isCounterBarrier = false;
        aq.c attack = new aq.c(this, target);
        if (attack.a()) {
            if (target.bB(91)) {
                w magic = new w(target, this);
                boolean isProbability = magic.a(91);
                boolean isShortDistance = attack.f();
                if (isProbability && isShortDistance) {
                    isCounterBarrier = true;
                }
            }
            if (target.bB(191) && bi.i.a(100) < 10) {
                isCounterBarrier = true;
            }
            int advence = 0;
            double hpRange = 0.4;
            if (target instanceof u) {
                u pc3 = (u)target;
                hpRange += (double)pc3.dH() * 0.01;
            }
            if (target.bB(231) && target.ev() >= 80) {
                advence = Math.min(5 + target.ev() - 80, 10);
            }
            if (target.bB(606) && (double)target.ea() < (double)target.ew() * hpRange && attack.f() && bi.i.a(100) < 20 + advence) {
                isCounterBarrier = true;
            }
            if (target.bB(607) && (double)target.ea() < (double)target.ew() * hpRange && !attack.f() && bi.i.a(100) < 20 + advence) {
                isCounterBarrier = true;
            }
            if (!isCounterBarrier) {
                attack.b();
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
        this.v(this.f(this.O(), 1));
    }

    private void i() {
        if (this.j == null || this.j.fp() != this.fp() || this.fu().d(this.j.fu()) > h) {
            if (this.i.isEmpty()) {
                this.j = null;
                return;
            }
            this.j = this.i.get(0);
            this.i.remove(0);
            this.i();
        }
    }

    private void j() {
        ArrayList<au.e> groundInvList = new ArrayList<au.e>();
        for (aa obj : aq.a().e(this)) {
            if (!(obj instanceof au.e)) continue;
            groundInvList.add((au.e)obj);
        }
        if (groundInvList.isEmpty()) {
            return;
        }
        int pickupIndex = bi.i.a(groundInvList.size());
        for (q item : ((au.e)groundInvList.get(pickupIndex)).d()) {
            if (this.y().a(item, item.E()) != 0) continue;
            this.j = item;
            this.i.add(this.j);
        }
    }

    protected void r() {
        ArrayList<au.e> gInventorys = new ArrayList<au.e>();
        for (aa obj : aq.a().b((aa)this, 3)) {
            if (!(obj instanceof au.e)) continue;
            gInventorys.add((au.e)obj);
        }
        if (gInventorys.isEmpty()) {
            return;
        }
        int pickupIndex = bi.i.a(gInventorys.size());
        for (q item : ((au.e)gInventorys.get(pickupIndex)).d()) {
            if (!item.f() || !item.k() && item.a().aP() != 7 || this.y().a(item, item.E()) != 0) continue;
            this.j = item;
            this.i.add(this.j);
        }
        if (this.j != null) {
            this.t(0);
            this.cq(am.c.a().a(this));
            this.b(new ak(this.fr(), 45));
            this.Z_();
            this.a_(2);
        }
    }

    private void k() {
        if (this.fu().f(this.j.fu())) {
            au.e ginv = aq.a().a(this.j.fs(), this.j.ft(), this.j.fp());
            q item = ginv.a(this.j, this.j.E(), this.y());
            this.i.remove(this.j);
            this.j = null;
            if (item != null) {
                this.fg();
                this.a(item);
                this.v(1000);
            }
            return;
        }
        int dir = this.a(this.j.fs(), this.j.ft());
        if (dir == -1) {
            this.i.remove(this.j);
            this.j = null;
            return;
        }
        this.g(dir);
        this.v(this.f(this.N(), 0));
    }

    public boolean a() {
        if (aq.a().f(this).isEmpty()) {
            return true;
        }
        if (this.k != null && this.k.fp() == this.fp() && this.fu().c(this.k.fu()) > 2) {
            int dir = this.a(this.k.fs(), this.k.ft());
            if (dir == -1) {
                return true;
            }
            this.g(dir);
            this.v(this.f(this.N(), 0));
            return false;
        }
        if (this.k == null && this.N() > 0 && !this.ai()) {
            int dir;
            if (this.ag == null || this.ag.b(this)) {
                if (this.B == 0) {
                    this.B = bi.i.a(5) + 1;
                    this.C = bi.i.a(20);
                    if (this.X() != 0 && this.Y() != 0 && this.C < 8 && bi.i.a(3) == 0) {
                        this.C = this.a(this.X(), this.Y());
                    }
                    return false;
                }
                --this.B;
                int dir2 = this.a(this.fs(), this.ft(), this.fp(), this.C);
                if (dir2 != -1) {
                    this.g(dir2);
                    this.v(this.f(this.N(), 0));
                }
                return false;
            }
            t leader = this.ag.a();
            if (this.fu().c(leader.fu()) > 2 && (dir = this.a(leader.fs(), leader.ft())) != -1) {
                this.g(dir);
                this.v(this.f(this.N(), 0));
            }
        }
        return false;
    }

    public void a(u pc, String s2) {
    }

    public void s() {
        if (this.m != null) {
            this.n.b(this.m);
            this.m = null;
        }
    }

    public void c(aq.f target) {
        this.n.b(target);
        if (this.m != null && this.m.equals(target)) {
            this.m = null;
        }
    }

    public void t() {
        this.n.a();
        this.l.a();
        this.m = null;
        this.i.clear();
        this.j = null;
    }

    public void Z_() {
        if (this.ae()) {
            return;
        }
        this.w = false;
        this.q();
    }

    public synchronized void u() {
        if (this.F != null) {
            return;
        }
        int hprInterval = this.U_().K();
        int hpr = this.U_().L();
        if (hpr > 0 && hprInterval >= 500) {
            this.F = bi.e.a().a(new d(hpr), hprInterval, hprInterval);
        }
    }

    public void v() {
        if (this.F != null) {
            this.F.cancel(true);
            this.F = null;
        }
    }

    public synchronized void w() {
        if (this.G != null) {
            return;
        }
        int mprInterval = this.U_().M();
        int mpr = this.U_().N();
        if (mpr > 0 && mprInterval >= 500) {
            this.G = bi.e.a().a(new e(mpr), mprInterval, mprInterval);
        }
    }

    public void x() {
        if (this.G != null) {
            this.G.cancel(true);
            this.G = null;
        }
    }

    public void a(q item) {
        if (this.m() > 0 && (this.L.b() == 45032 || this.L.b() == 190864)) {
            this.a(20, 1, new int[]{40508, 40521, 40045}, new int[]{150, 3, 3});
            this.a(19, 1, new int[]{40494, 40521}, new int[]{150, 3});
            this.a(3, 1, new int[]{40494, 40521}, new int[]{50, 1});
            this.a(100, 1, new int[]{88, 40508, 40045}, new int[]{4, 80, 3});
            this.a(89, 1, new int[]{88, 40494}, new int[]{2, 80});
        }
        this.b(item);
        this.y().f();
    }

    private void b(q item) {
        if (this.U_().I() <= 0) {
            return;
        }
        this.H.put(item.fr(), this.U_().I());
        if (!this.I) {
            this.I = true;
            bi.e.a().a(new c());
        }
    }

    private void a(int createitem, int createcount, int[] materials, int[] counts) {
        if (!this.o.f(createitem) && this.o.a(materials, counts)) {
            int i2 = 0;
            while (i2 < materials.length) {
                this.o.b(materials[i2], counts[i2]);
                ++i2;
            }
            q item = this.o.a(createitem, createcount);
            this.b(item);
        }
    }

    public t(l template) {
        int[][] nArrayArray = new int[8][];
        int[] nArray = new int[2];
        nArray[1] = -1;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{1, -1};
        int[] nArray2 = new int[2];
        nArray2[0] = 1;
        nArrayArray[2] = nArray2;
        nArrayArray[3] = new int[]{1, 1};
        int[] nArray3 = new int[2];
        nArray3[1] = 1;
        nArrayArray[4] = nArray3;
        nArrayArray[5] = new int[]{-1, 1};
        int[] nArray4 = new int[2];
        nArray4[0] = -1;
        nArrayArray[6] = nArray4;
        nArrayArray[7] = new int[]{-1, -1};
        this.D = nArrayArray;
        this.i = new CopyOnWriteArrayList();
        this.j = null;
        this.k = null;
        this.l = new aq.q();
        this.m = null;
        this.E = 0;
        this.n = new aq.q();
        this.I = false;
        this.o = new au.f();
        this.P = "";
        this.V = false;
        this.Z = 0;
        this.aa = false;
        this.w = false;
        this.ac = false;
        this.ad = false;
        this.ae = false;
        this.af = null;
        this.ag = null;
        this.ah = 0;
        this.ai = -1;
        this.aj = 0;
        this.ak = 0;
        this.al = 0;
        this.am = "";
        this.an = 0;
        this.ao = false;
        this.ap = false;
        if (template == null) {
            return;
        }
        this.a(template);
    }

    public void a(l template) {
        this.L = template;
        this.e(template.c());
        this.a(template.A());
        int maxLevelDifferent = 0;
        double growingRate = 0.0;
        if (template.P() == 0) {
            this.b(template.e());
        } else {
            int randomlevel = bi.i.a(template.P() - template.e() + 1);
            maxLevelDifferent = template.P() - template.e();
            growingRate = randomlevel / maxLevelDifferent;
            this.b(template.e() + randomlevel);
        }
        if (template.Q() == 0) {
            this.bG(template.f());
            this.bx(template.f());
        } else {
            int randomhp = (int)(growingRate * (double)(template.Q() - template.f()));
            this.bG(template.f() + randomhp);
            this.bx(template.f() + randomhp);
        }
        if (template.R() == 0) {
            this.bI(template.g());
            this.by(template.g());
        } else {
            int randommp = (int)(growingRate * (double)(template.R() - template.g()));
            this.bI(template.g() + randommp);
            this.by(template.g() + randommp);
        }
        if (template.S() == 0) {
            this.bK(template.h());
        } else {
            int randomac = (int)(growingRate * (double)(template.S() - template.h()));
            this.bK(template.h() + randomac);
        }
        if (template.P() == 0) {
            this.bM(template.i());
            this.bO(template.j());
            this.bQ(template.k());
            this.bU(template.m());
            this.bW(template.l());
            this.cx(template.n());
        } else {
            this.bM(Math.min(template.i() + maxLevelDifferent, 127));
            this.bO(Math.min(template.j() + maxLevelDifferent, 127));
            this.bQ(Math.min(template.k() + maxLevelDifferent, 127));
            this.bU(Math.min(template.m() + maxLevelDifferent, 127));
            this.bW(Math.min(template.l() + maxLevelDifferent, 127));
            this.cx(Math.min(template.n() + maxLevelDifferent, 127));
            this.cm(maxLevelDifferent * 2);
            this.ck(maxLevelDifferent * 2);
        }
        if (template.T() == 0) {
            this.k(template.o());
        } else {
            this.k(this.ev() * this.ev() + 1);
        }
        if (template.U() == 0) {
            this.cr(template.p());
        } else {
            int randomlawful = (int)(growingRate * (double)(template.U() - template.p()));
            this.cr(template.p() + randomlawful);
        }
        this.d(template.y());
        this.e(template.u());
        this.f(template.t());
        this.cw(template.z());
        this.cq(am.c.a().a(this));
        this.x(template.s());
        this.b_(template.Z());
        this.cv(template.J() ? 1 : 0);
        this.A(template.aa());
        this.s(template.ae());
        if (template.w()) {
            this.m(am.c.a().a(this.fe(), this.eY()));
        } else {
            this.m(0);
        }
        if (template.x()) {
            int actid = 1;
            if (am.c.a().b(this.fe(), this.eY() + 1)) {
                actid = this.eY() + 1;
            }
            this.n(am.c.a().a(this.fe(), actid));
        } else {
            this.n(0);
        }
        if (template.I() > 0) {
            this.H = new ConcurrentHashMap();
        }
    }

    public void b(int i2, int type) {
        this.v(this.f(am.c.a().a(this.fe(), i2), type));
    }

    @Override
    public au.f y() {
        return this.o;
    }

    public int z() {
        return this.L.b();
    }

    private void b(boolean isReuseId) {
        int id = 0;
        id = isReuseId ? this.fr() : 0;
        this.N.a(this.O, id);
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new cc(this));
        this.Z_();
    }

    public synchronized void aa_() {
        this.k(true);
        if (this.y() != null) {
            this.y().g();
        }
        this.t();
        this.k = null;
        aq.a().d(this);
        aq.a().b(this);
        for (u pc : aq.a().f(this)) {
            pc.d(this);
            pc.a(new dh(this));
        }
        this.es();
        this.fq().a(this.fu(), true);
        if (this.ag == null) {
            if (this.Z()) {
                this.b(true);
            }
        } else if (this.ag.d(this) == 0) {
            this.ag = null;
            if (this.Z()) {
                this.b(false);
            }
        }
        if (this.z() >= 190331 && this.z() <= 190342) {
            as.d.a().a(this.z());
        } else if (this.z() == 190554) {
            as.j.a().b();
        }
    }

    public void a(long timeMillis) {
        bi.e.a().a(new a(), timeMillis);
    }

    public void a(aq.f attacker, int damageMp) {
    }

    public void b(aq.f attacker, int damage) {
    }

    public void g(int dir) {
        if (dir < 0) {
            return;
        }
        this.ct(dir);
        this.fq().a(this.fu(), true);
        this.cG(this.fs() + this.D[dir][0]);
        this.cH(this.ft() + this.D[dir][1]);
        this.fq().a(this.fu(), false);
        this.b(new cb(this));
        if (this.Z > 0 && this.X() > 0 && this.Y() > 0) {
            h h2 = new h(this.X(), this.Y());
            if (this.fu().b(h2) > (double)this.Z) {
                this.a(this.X(), this.Y(), this.fb());
            }
        }
    }

    protected int a(int x2, int y2) {
        double distance = this.fu().b(new h(x2, y2));
        if (this.bB(40) && distance >= 2.0) {
            return -1;
        }
        if (distance > (double)(h * 2)) {
            return -1;
        }
        if (distance > (double)h) {
            return this.a(this.fs(), this.ft(), this.fp(), this.h(x2, y2));
        }
        int dir = this.d(x2, y2);
        if (dir == -1 && !this.h(dir = this.h(x2, y2))) {
            dir = this.a(this.fs(), this.ft(), this.fp(), dir);
        }
        return dir;
    }

    public int c(int tx, int ty) {
        int dir = this.h(tx, ty);
        if ((dir += 4) > 7) {
            dir -= 8;
        }
        return dir;
    }

    protected int a(int x2, int y2, int mapid, int dir) {
        int dir_right;
        if (dir > 7 || dir < 0) {
            return -1;
        }
        ax.b map = ax.d.b().a(mapid);
        int dir_left = dir == 0 ? 7 : dir - 1;
        int n2 = dir_right = dir == 7 ? 0 : dir + 1;
        if (map.c(x2, y2, dir)) {
            return dir;
        }
        if (map.c(x2, y2, dir_left)) {
            return dir_left;
        }
        if (map.c(x2, y2, dir_right)) {
            return dir_right;
        }
        return -1;
    }

    protected boolean h(int dir) {
        if (!(this instanceof s)) {
            return false;
        }
        if (this.m == null) {
            return false;
        }
        int targetX = this.fs() + this.D[dir][0];
        int targetY = this.ft() + this.D[dir][1];
        for (aa obj : this.m.eq()) {
            aq.f cha;
            if (!(obj instanceof u) && !(obj instanceof z) && !(obj instanceof v) || (cha = (aq.f)obj).eX() || cha.fs() != targetX || cha.ft() != targetY || cha.fp() != this.fp() || obj instanceof u && ((u)obj).bN()) continue;
            this.n.a(cha, 0);
            this.m = cha;
            return true;
        }
        return false;
    }

    protected int d(int targetX, int targetY) {
        int[] locNext;
        int dir;
        int[] searchDIR;
        int searchRange = h + 1;
        int searchBeginX = targetX - searchRange;
        int searchBeginY = targetY - searchRange;
        int[] nArray = new int[4];
        nArray[0] = this.fs() - searchBeginX;
        nArray[1] = this.ft() - searchBeginY;
        int[] locBase = nArray;
        boolean[][] serchMap = new boolean[searchRange * 2 + 1][searchRange * 2 + 1];
        int j2 = h * 2 + 1;
        while (j2 > 0) {
            int i2 = h - Math.abs(searchRange - j2);
            while (i2 >= 0) {
                serchMap[j2][searchRange + i2] = true;
                serchMap[j2][searchRange - i2] = true;
                --i2;
            }
            --j2;
        }
        LinkedList<int[]> queueSerch = new LinkedList<int[]>();
        int[] nArray2 = new int[8];
        nArray2[0] = 2;
        nArray2[1] = 4;
        nArray2[2] = 6;
        nArray2[4] = 1;
        nArray2[5] = 3;
        nArray2[6] = 5;
        nArray2[7] = 7;
        int[] nArray3 = searchDIR = nArray2;
        int n2 = searchDIR.length;
        int n3 = 0;
        while (n3 < n2) {
            dir = nArray3[n3];
            locNext = (int[])locBase.clone();
            locNext[0] = locNext[0] + this.D[dir][0];
            locNext[1] = locNext[1] + this.D[dir][1];
            locNext[2] = dir;
            if (locNext[0] + searchBeginX == targetX && locNext[1] + searchBeginY == targetY) {
                return dir;
            }
            if (serchMap[locNext[0]][locNext[1]]) {
                if (this.fq().c(this.fs(), this.ft(), dir)) {
                    locNext[2] = dir;
                    locNext[3] = dir;
                    queueSerch.add((int[])locNext.clone());
                }
                serchMap[locNext[0]][locNext[1]] = false;
            }
            ++n3;
        }
        while (!queueSerch.isEmpty()) {
            locBase = (int[])queueSerch.removeFirst();
            nArray3 = searchDIR;
            n2 = searchDIR.length;
            n3 = 0;
            while (n3 < n2) {
                dir = nArray3[n3];
                locNext = (int[])locBase.clone();
                locNext[0] = locNext[0] + this.D[dir][0];
                locNext[1] = locNext[1] + this.D[dir][1];
                locNext[2] = dir;
                if (locNext[0] + searchBeginX == targetX && locNext[1] + searchBeginY == targetY) {
                    return locNext[3];
                }
                if (serchMap[locNext[0]][locNext[1]]) {
                    int tmpX = locBase[0] + searchBeginX;
                    int tmpY = locBase[1] + searchBeginY;
                    if (this.fq().c(tmpX, tmpY, dir)) {
                        locNext[2] = dir;
                        queueSerch.add((int[])locNext.clone());
                    }
                    serchMap[locNext[0]][locNext[1]] = false;
                }
                ++n3;
            }
        }
        return -1;
    }

    private void k(int healHp, int effectId) {
        this.b(new ee(this.fr(), effectId));
        if (this.bB(173)) {
            healHp /= 2;
        }
        if (this instanceof v) {
            ((v)this).a(this.ea() + healHp);
        } else if (this instanceof z) {
            ((z)this).a(this.ea() + healHp);
        } else {
            this.bx(this.ea() + healHp);
        }
    }

    private void d(int time) {
        this.b(new ea(this.fr(), 1, time));
        this.b(new ee(this.fr(), 191));
        this.cu(1);
        this.j(1001, time * 1000);
    }

    public void e(int type, int chance) {
        if (this.bB(71)) {
            return;
        }
        if (bi.i.a(100) > chance) {
            return;
        }
        if (type == 0) {
            if (this.y().b(40012, 1)) {
                this.k(75, 197);
            } else if (this.y().b(40011, 1)) {
                this.k(45, 194);
            } else if (this.y().b(40010, 1)) {
                this.k(15, 189);
            }
        } else if (type == 1) {
            if (this.bB(1001)) {
                return;
            }
            if (this.y().b(40018, 1)) {
                this.d(1800);
            } else if (this.y().b(40013, 1)) {
                this.d(300);
            }
        }
    }

    private boolean l(int nx, int ny) {
        int rdir = bi.i.a(8);
        int i2 = 0;
        while (i2 < 8) {
            int dir = rdir + i2;
            if (dir > 7) {
                dir -= 8;
            }
            if (this.fq().c(nx += this.D[dir][0], ny += this.D[dir][1])) {
                if ((dir += 4) > 7) {
                    dir -= 8;
                }
                this.a(nx, ny, dir);
                this.i_(this.eb() - 10);
                return true;
            }
            ++i2;
        }
        return false;
    }

    public void a(int nx, int ny, int dir) {
        for (u pc : aq.a().f(this)) {
            pc.a(new ee(this.fr(), 169));
            pc.a(new dh(this));
            pc.d(this);
        }
        this.cG(nx);
        this.cH(ny);
        this.ct(dir);
    }

    protected int f(int sleepTime, int type) {
        if (this.fc() == 1) {
            sleepTime = (int)((double)sleepTime * 0.75);
        } else if (this.fc() == 2) {
            sleepTime = (int)((double)sleepTime / 0.75);
        }
        if (this.fd() == 1) {
            sleepTime = (int)((double)sleepTime * 0.75);
        }
        if (this.bB(167) && (type == 1 || type == 2)) {
            sleepTime = (int)((double)sleepTime / 0.75);
        }
        return sleepTime;
    }

    public int i(int drain) {
        int maxLeech = 40;
        if (this.A >= 40) {
            return 0;
        }
        int result = Math.min(drain, this.eb());
        if (this.A + result > 40) {
            result = 40 - this.A;
        }
        this.A += result;
        return result;
    }

    public void g_(int transformId) {
        this.v();
        this.x();
        int transformGfxId = this.U_().ac();
        if (transformGfxId != 0) {
            this.b(new ee(this.fr(), transformGfxId));
        }
        l npcTemplate = au.a().a(transformId);
        this.a(npcTemplate);
        this.b(new cf(this.fr(), this.fe(), this.fa(), this.eY()));
        for (u pc : aq.a().f(this)) {
            this.b(pc);
        }
    }

    @Override
    public synchronized void j(int hp) {
        if (this.ah()) {
            return;
        }
        if (this.af != null) {
            if (this.af.cancel(true)) {
                return;
            }
            this.af = null;
        }
        super.j(hp);
        new bf.as().a((aq.f)this, 1);
    }

    protected synchronized void A() {
        if (this.af == null) {
            this.af = bi.e.a().a(new b(), l1j.server.a.at * 1000);
        }
    }

    public boolean B() {
        return this.ag != null;
    }

    public void a_(int chatTiming) {
        if (chatTiming == 0 && this.eX()) {
            return;
        }
        if (chatTiming == 1 && !this.eX()) {
            return;
        }
        if (chatTiming == 2 && this.eX()) {
            return;
        }
        m npcChat = null;
        if (chatTiming == 0) {
            npcChat = as.a().a(this.z());
        } else if (chatTiming == 1) {
            npcChat = as.a().b(this.z());
        } else if (chatTiming == 2) {
            npcChat = as.a().c(this.z());
        }
        if (npcChat == null) {
            return;
        }
        if (bi.i.a(100) > npcChat.n()) {
            return;
        }
        new bb.b(this, npcChat).a();
    }

    public int C() {
        if (this.ai == -1) {
            return this.U_().s();
        }
        return this.ai;
    }

    public String D() {
        return this.L.ai();
    }

    public String E() {
        return this.L.aj();
    }

    public String[] F() {
        return this.L.ak();
    }

    public int G() {
        return this.L.z();
    }

    @Override
    public void a(u pc) {
        if (this.U_().ag()) {
            this.ct(this.h(pc.fs(), pc.ft()));
            this.b(new p(this));
            if (this.U_().w() && !this.ai()) {
                this.l(true);
                bi.e.a().a(new g(), 10000L);
            }
        }
        aq.z.b(this, pc);
    }

    public void g(int x2, int y2) {
        this.ak = x2;
        this.al = y2;
    }

    public boolean H() {
        return this.U_().ad();
    }

    public void I() {
        ++this.an;
    }

    public int J() {
        return this.U_().al();
    }

    public int K() {
        return this.U_().am();
    }

    public boolean d(aq.f cha) {
        return this.l.a(cha);
    }

    public boolean L() {
        u pc;
        return this.M() instanceof u && (pc = (u)this.M()).bE() == 1;
    }

    public aq.f M() {
        return this.k;
    }

    public void e(aq.f master) {
        this.k = master;
    }

    public void l(int paralysisTime) {
        this.E = paralysisTime;
    }

    public int N() {
        return this.J;
    }

    public void m(int passispeed) {
        this.J = passispeed;
    }

    public int O() {
        return this.K;
    }

    public void n(int atkspeed) {
        this.K = atkspeed;
    }

    public void a(au.f inventory) {
        this.o = inventory;
    }

    public l U_() {
        return this.L;
    }

    public int Q() {
        return this.M;
    }

    public void o(int petcost) {
        this.M = petcost;
    }

    public ag R() {
        return this.N;
    }

    public void a(ag spawn) {
        this.N = spawn;
    }

    public int S() {
        return this.O;
    }

    public void p(int spawnNumber) {
        this.O = spawnNumber;
    }

    public String T() {
        return this.P;
    }

    public void a(String nameId) {
        this.P = nameId;
    }

    public boolean V_() {
        return this.Q;
    }

    public void d(boolean isAgro) {
        this.Q = isAgro;
    }

    public boolean V() {
        return this.R;
    }

    public void e(boolean isAgrocoi) {
        this.R = isAgrocoi;
    }

    public boolean W() {
        return this.S;
    }

    public void f(boolean isAgrososc) {
        this.S = isAgrososc;
    }

    public int X() {
        return this.T;
    }

    public void q(int homeX) {
        this.T = homeX;
    }

    public int Y() {
        return this.U;
    }

    public void r(int homeY) {
        this.U = homeY;
    }

    public boolean Z() {
        return this.V;
    }

    public void g(boolean isReSpawn) {
        this.V = isReSpawn;
    }

    public int aa() {
        return this.W;
    }

    public void s(int lightSize) {
        this.W = lightSize;
    }

    public boolean ab() {
        return this.X;
    }

    public void h(boolean isWeaponBreaked) {
        this.X = isWeaponBreaked;
    }

    public int ac() {
        return this.Y;
    }

    public void t(int hiddenStatus) {
        this.Y = hiddenStatus;
    }

    public int ad() {
        return this.Z;
    }

    public void u(int maxMoveDistance) {
        this.Z = maxMoveDistance;
    }

    public boolean ae() {
        return this.aa;
    }

    public void i(boolean isAiRunning) {
        this.aa = isAiRunning;
    }

    public int af() {
        return this.ab;
    }

    public void v(int sleepTime) {
        this.ab = sleepTime;
    }

    public boolean ag() {
        return this.ac;
    }

    public void j(boolean isDeathProcessing) {
        this.ac = isDeathProcessing;
    }

    public boolean ah() {
        return this.ad;
    }

    public void k(boolean isDeleteMe) {
        this.ad = isDeleteMe;
    }

    public boolean ai() {
        return this.ae;
    }

    public void l(boolean isRest) {
        this.ae = isRest;
    }

    public y aj() {
        return this.ag;
    }

    public void a(y mobGroupInfo) {
        this.ag = mobGroupInfo;
    }

    public int ak() {
        return this.ah;
    }

    public void w(int mobGroupId) {
        this.ah = mobGroupId;
    }

    public int al() {
        return this.ai;
    }

    public void x(int polyAtkRanged) {
        this.ai = polyAtkRanged;
    }

    public int am() {
        return this.aj;
    }

    public void b_(int polyArrowGfx) {
        this.aj = polyArrowGfx;
    }

    public int an() {
        return this.ak;
    }

    public int ao() {
        return this.al;
    }

    public String ap() {
        return this.am;
    }

    public void b(String clanName) {
        this.am = clanName;
    }

    public int aq() {
        return this.an;
    }

    public void m(boolean isNpcSummon) {
        this.ao = isNpcSummon;
    }

    public boolean ar() {
        return this.ao;
    }

    public void n(boolean isBind) {
        this.ap = isBind;
    }

    public boolean as() {
        return this.ap;
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            if (t.this.ah()) {
                return;
            }
            t.this.aa_();
        }
    }

    private class b
    extends TimerTask {
        private b() {
        }

        @Override
        public void run() {
            if (!t.this.eX() || t.this.ah()) {
                return;
            }
            t.this.aa_();
        }
    }

    private class c
    implements Runnable {
        private c() {
        }

        @Override
        public void run() {
            try {
                try {
                    while (!t.this.H.isEmpty()) {
                        Thread.sleep(1000L);
                        if (t.this.ah()) {
                            break;
                        }
                        Iterator iterator = ((ConcurrentHashMap.KeySetView)t.this.H.keySet()).iterator();
                        while (iterator.hasNext()) {
                            int itemobjid = (Integer)iterator.next();
                            int digestCounter = (Integer)t.this.H.get(itemobjid) - 1;
                            if (digestCounter > 0) {
                                t.this.H.put(itemobjid, digestCounter);
                                continue;
                            }
                            t.this.H.remove(itemobjid);
                            q item = t.this.o.e(itemobjid);
                            if (item == null) continue;
                            t.this.o.f(item);
                        }
                    }
                }
                catch (Exception e2) {
                    y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    t.this.I = false;
                }
            }
            finally {
                t.this.I = false;
            }
        }
    }

    private class d
    extends TimerTask {
        private final int b;

        @Override
        public void run() {
            try {
                if (t.this.ah() || t.this.eX()) {
                    t.this.v();
                    return;
                }
                if (t.this.ea() <= 0 || t.this.ea() >= t.this.ew()) {
                    t.this.v();
                    return;
                }
                t.this.a(t.this.ea() + this.b);
            }
            catch (Exception e2) {
                y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }

        public d(int point) {
            this.b = point;
        }
    }

    private class e
    extends TimerTask {
        private final int b;

        @Override
        public void run() {
            try {
                if (t.this.ah() || t.this.eX()) {
                    t.this.x();
                    return;
                }
                if (t.this.eb() <= 0 || t.this.eb() >= t.this.ex()) {
                    t.this.x();
                    return;
                }
                t.this.i_(t.this.eb() + this.b);
            }
            catch (Exception e2) {
                y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }

        private e(int point) {
            this.b = point;
        }
    }

    private class f
    extends TimerTask {
        private f() {
        }

        private void b() {
            t.this.an = 0;
            t.this.z = false;
            if (!t.this.ag()) {
                t.this.t();
            }
            t.this.i(false);
        }

        public void a() {
            t.this.i(true);
            bi.e.a().a(this, 0L);
        }

        private void a(int delay) {
            bi.e.a().a(new f(), delay);
        }

        @Override
        public void run() {
            try {
                if (this.c()) {
                    this.b();
                    return;
                }
                if (t.this.E > 0) {
                    this.a(t.this.E);
                    t.this.E = 0;
                    return;
                }
                if (t.this.ed() || t.this.ec()) {
                    this.a(200);
                    return;
                }
                if (!t.this.h()) {
                    this.a(t.this.af());
                    return;
                }
                this.b();
            }
            catch (Exception e2) {
                y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                t.this.i(false);
            }
        }

        private boolean c() {
            return t.this.ah() || t.this.eX() || t.this.ea() <= 0 || t.this.ac() != 0;
        }
    }

    private class g
    extends TimerTask {
        private g() {
        }

        @Override
        public void run() {
            t.this.l(false);
        }
    }
}

