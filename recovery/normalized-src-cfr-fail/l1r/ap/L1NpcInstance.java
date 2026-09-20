/*
 * Decompiled with CFR 0.152.
 */
package l1r.ap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.am.ListSprReader__obf_c;
import l1r.ao.MobSkillsTable;
import l1r.ao.NpcChatTable;
import l1r.ao.NpcTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
import l1r.aq.L1HateList;
import l1r.aq.L1Magic;
import l1r.aq.L1MobGroupInfo;
import l1r.aq.L1NpcTalkData;
import l1r.aq.L1Object;
import l1r.aq.L1Spawn;
import l1r.aq.L1World;
import l1r.as.L1Dragon;
import l1r.as.L1ThebesBattle;
import l1r.au.L1GroundInventory;
import l1r.au.L1Inventory;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.bb.NpcChatTimer;
import l1r.be.S_ChangeHeading;
import l1r.be.S_DoActionGFX;
import l1r.be.S_MoveCharPacket;
import l1r.be.S_NPCPack;
import l1r.be.S_NpcChangeShape;
import l1r.be.S_RemoveObject;
import l1r.be.S_SkillHaste;
import l1r.be.S_SkillSound;
import l1r.bf.S_044;
import l1r.bh.L1Npc;
import l1r.bh.L1NpcChat;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Point;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1NpcInstance
extends L1Character {
    private static final Logger y = Logger.getLogger(L1NpcInstance.class.getName());
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
    protected CopyOnWriteArrayList<L1ItemInstance> i;
    protected L1ItemInstance j;
    protected L1Character k;
    protected L1HateList l;
    protected L1Character m;
    private int E;
    protected L1HateList n;
    private ScheduledFuture<?> F;
    private ScheduledFuture<?> G;
    private ConcurrentHashMap<Integer, Integer> H;
    private boolean I;
    private int J;
    private int K;
    protected L1Inventory o;
    private L1Npc L;
    private int M;
    private L1Spawn N;
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
    private L1MobGroupInfo ag;
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
        new L1R_f().a();
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
                L1GroundInventory groundInventory = L1World.a().a(this.j.fs(), this.j.ft(), this.j.fp());
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
    public void c(L1PcInstance pc) {
        this.a(pc, 0);
    }

    @Override
    public void a(L1PcInstance pc, int skillId) {
        L1Attack attack = new L1Attack(pc, this, skillId);
        if (attack.a()) {
            attack.b();
            attack.a((L1Character)pc, (L1Character)this);
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

    public void c(L1Character cha, int hate) {
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

    public void a(L1Character cha) {
    }

    public void c(L1PcInstance tpc, int family) {
        for (L1Object obj : tpc.eq()) {
            if (!(obj instanceof L1NpcInstance)) continue;
            L1NpcInstance npc = (L1NpcInstance)obj;
            if (npc.U_().E() > 0 && (npc.U_().D() == family || npc.U_().E() > 1)) {
                npc.a((L1Character)tpc);
            }
            if (this.ag == null || this.ah == 0 || npc.ak() != this.ah) continue;
            npc.a((L1Character)tpc);
        }
    }

    public void b() {
        this.w = true;
        this.i.clear();
        this.j = null;
        L1Character target = this.m;
        if (this.O() == 0) {
            if (this.N() > 0) {
                int escapeDistance;
                int n = escapeDistance = this.bB(40) ? 1 : 15;
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
        int sleepTime = MobSkillsTable.a().a(this, target);
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
            if (Random.a(100) < 20 && this.eb() >= 10 && distance > 6 && distance < 15 && this.l(target.fs(), target.ft())) {
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

    public void b(L1Character target) {
        L1NpcInstance npc;
        L1PcInstance pc;
        L1Character cha;
        L1PcInstance pc2;
        if (this.k instanceof L1PcInstance && (pc2 = (L1PcInstance)this.k).aR()) {
            return;
        }
        if (target instanceof L1PcInstance ? (pc2 = (L1PcInstance)target).aR() : (target instanceof L1PetInstance || target instanceof L1SummonInstance) && (cha = ((L1NpcInstance)target).M()) instanceof L1PcInstance && (pc = (L1PcInstance)cha).aR()) {
            return;
        }
        if (target instanceof L1NpcInstance && (npc = (L1NpcInstance)target).ac() != 0) {
            this.t();
            return;
        }
        boolean isCounterBarrier = false;
        L1Attack attack = new L1Attack(this, target);
        if (attack.a()) {
            if (target.bB(91)) {
                L1Magic magic = new L1Magic(target, this);
                boolean isProbability = magic.a(91);
                boolean isShortDistance = attack.f();
                if (isProbability && isShortDistance) {
                    isCounterBarrier = true;
                }
            }
            if (target.bB(191) && Random.a(100) < 10) {
                isCounterBarrier = true;
            }
            int advence = 0;
            double hpRange = 0.4;
            if (target instanceof L1PcInstance) {
                L1PcInstance pc3 = (L1PcInstance)target;
                hpRange += (double)pc3.dH() * 0.01;
            }
            if (target.bB(231) && target.ev() >= 80) {
                advence = Math.min(5 + target.ev() - 80, 10);
            }
            if (target.bB(606) && (double)target.ea() < (double)target.ew() * hpRange && attack.f() && Random.a(100) < 20 + advence) {
                isCounterBarrier = true;
            }
            if (target.bB(607) && (double)target.ea() < (double)target.ew() * hpRange && !attack.f() && Random.a(100) < 20 + advence) {
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
        ArrayList<L1GroundInventory> groundInvList = new ArrayList<L1GroundInventory>();
        for (L1Object obj : L1World.a().e(this)) {
            if (!(obj instanceof L1GroundInventory)) continue;
            groundInvList.add((L1GroundInventory)obj);
        }
        if (groundInvList.isEmpty()) {
            return;
        }
        int pickupIndex = Random.a(groundInvList.size());
        for (L1ItemInstance item : ((L1GroundInventory)groundInvList.get(pickupIndex)).d()) {
            if (this.y().a(item, item.E()) != 0) continue;
            this.j = item;
            this.i.add(this.j);
        }
    }

    protected void r() {
        ArrayList<L1GroundInventory> gInventorys = new ArrayList<L1GroundInventory>();
        for (L1Object obj : L1World.a().b((L1Object)this, 3)) {
            if (!(obj instanceof L1GroundInventory)) continue;
            gInventorys.add((L1GroundInventory)obj);
        }
        if (gInventorys.isEmpty()) {
            return;
        }
        int pickupIndex = Random.a(gInventorys.size());
        for (L1ItemInstance item : ((L1GroundInventory)gInventorys.get(pickupIndex)).d()) {
            if (!item.f() || !item.k() && item.a().aP() != 7 || this.y().a(item, item.E()) != 0) continue;
            this.j = item;
            this.i.add(this.j);
        }
        if (this.j != null) {
            this.t(0);
            this.cq(ListSprReader__obf_c.a().a(this));
            this.b(new S_DoActionGFX(this.fr(), 45));
            this.Z_();
            this.a_(2);
        }
    }

    private void k() {
        if (this.fu().f(this.j.fu())) {
            L1GroundInventory ginv = L1World.a().a(this.j.fs(), this.j.ft(), this.j.fp());
            L1ItemInstance item = ginv.a(this.j, this.j.E(), this.y());
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
        if (L1World.a().f(this).isEmpty()) {
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
                    this.B = Random.a(5) + 1;
                    this.C = Random.a(20);
                    if (this.X() != 0 && this.Y() != 0 && this.C < 8 && Random.a(3) == 0) {
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
            L1NpcInstance leader = this.ag.a();
            if (this.fu().c(leader.fu()) > 2 && (dir = this.a(leader.fs(), leader.ft())) != -1) {
                this.g(dir);
                this.v(this.f(this.N(), 0));
            }
        }
        return false;
    }

    public void a(L1PcInstance pc, String s) {
    }

    public void s() {
        if (this.m != null) {
            this.n.b(this.m);
            this.m = null;
        }
    }

    public void c(L1Character target) {
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
            this.F = GeneralThreadPool.a().a(new L1R_d(hpr), hprInterval, hprInterval);
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
            this.G = GeneralThreadPool.a().a(new L1R_e(mpr), mprInterval, mprInterval);
        }
    }

    public void x() {
        if (this.G != null) {
            this.G.cancel(true);
            this.G = null;
        }
    }

    public void a(L1ItemInstance item) {
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

    private void b(L1ItemInstance item) {
        if (this.U_().I() <= 0) {
            return;
        }
        this.H.put(item.fr(), this.U_().I());
        if (!this.I) {
            this.I = true;
            GeneralThreadPool.a().a(new L1R_c());
        }
    }

    private void a(int createitem, int createcount, int[] materials, int[] counts) {
        if (!this.o.f(createitem) && this.o.a(materials, counts)) {
            int i = 0;
            while (i < materials.length) {
                this.o.b(materials[i], counts[i]);
                ++i;
            }
            L1ItemInstance item = this.o.a(createitem, createcount);
            this.b(item);
        }
    }

    public L1NpcInstance(L1Npc template) {
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
        this.l = new L1HateList();
        this.m = null;
        this.E = 0;
        this.n = new L1HateList();
        this.I = false;
        this.o = new L1Inventory();
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

    public void a(L1Npc template) {
        this.L = template;
        this.e(template.c());
        this.a(template.A());
        int maxLevelDifferent = 0;
        double growingRate = 0.0;
        if (template.P() == 0) {
            this.b(template.e());
        } else {
            int randomlevel = Random.a(template.P() - template.e() + 1);
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
        this.cq(ListSprReader__obf_c.a().a(this));
        this.x(template.s());
        this.b_(template.Z());
        this.cv(template.J() ? 1 : 0);
        this.A(template.aa());
        this.s(template.ae());
        if (template.w()) {
            this.m(ListSprReader__obf_c.a().a(this.fe(), this.eY()));
        } else {
            this.m(0);
        }
        if (template.x()) {
            int actid = 1;
            if (ListSprReader__obf_c.a().b(this.fe(), this.eY() + 1)) {
                actid = this.eY() + 1;
            }
            this.n(ListSprReader__obf_c.a().a(this.fe(), actid));
        } else {
            this.n(0);
        }
        if (template.I() > 0) {
            this.H = new ConcurrentHashMap();
        }
    }

    public void b(int i, int type) {
        this.v(this.f(ListSprReader__obf_c.a().a(this.fe(), i), type));
    }

    @Override
    public L1Inventory y() {
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
    public void b(L1PcInstance perceivedFrom) {
        perceivedFrom.c((L1Object)this);
        perceivedFrom.a(new S_NPCPack(this));
        this.Z_();
    }

    public synchronized void aa_() {
        this.k(true);
        if (this.y() != null) {
            this.y().g();
        }
        this.t();
        this.k = null;
        L1World.a().d(this);
        L1World.a().b(this);
        for (L1PcInstance pc : L1World.a().f(this)) {
            pc.d(this);
            pc.a(new S_RemoveObject(this));
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
            L1Dragon.a().a(this.z());
        } else if (this.z() == 190554) {
            L1ThebesBattle.a().b();
        }
    }

    public void a(long timeMillis) {
        GeneralThreadPool.a().a(new L1R_a(), timeMillis);
    }

    public void a(L1Character attacker, int damageMp) {
    }

    public void b(L1Character attacker, int damage) {
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
        this.b(new S_MoveCharPacket(this));
        if (this.Z > 0 && this.X() > 0 && this.Y() > 0) {
            Point point = new Point(this.X(), this.Y());
            if (this.fu().b(point) > (double)this.Z) {
                this.a(this.X(), this.Y(), this.fb());
            }
        }
    }

    protected int a(int x, int y) {
        double distance = this.fu().b(new Point(x, y));
        if (this.bB(40) && distance >= 2.0) {
            return -1;
        }
        if (distance > (double)(h * 2)) {
            return -1;
        }
        if (distance > (double)h) {
            return this.a(this.fs(), this.ft(), this.fp(), this.h(x, y));
        }
        int dir = this.d(x, y);
        if (dir == -1 && !this.h(dir = this.h(x, y))) {
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

    protected int a(int x, int y, int mapid, int dir) {
        int dir_right;
        if (dir > 7 || dir < 0) {
            return -1;
        }
        L1Map map = L1WorldMap.b().a(mapid);
        int dir_left = dir == 0 ? 7 : dir - 1;
        int n = dir_right = dir == 7 ? 0 : dir + 1;
        if (map.c(x, y, dir)) {
            return dir;
        }
        if (map.c(x, y, dir_left)) {
            return dir_left;
        }
        if (map.c(x, y, dir_right)) {
            return dir_right;
        }
        return -1;
    }

    protected boolean h(int dir) {
        if (!(this instanceof L1MonsterInstance)) {
            return false;
        }
        if (this.m == null) {
            return false;
        }
        int targetX = this.fs() + this.D[dir][0];
        int targetY = this.ft() + this.D[dir][1];
        for (L1Object obj : this.m.eq()) {
            L1Character cha;
            if (!(obj instanceof L1PcInstance) && !(obj instanceof L1SummonInstance) && !(obj instanceof L1PetInstance) || (cha = (L1Character)obj).eX() || cha.fs() != targetX || cha.ft() != targetY || cha.fp() != this.fp() || obj instanceof L1PcInstance && ((L1PcInstance)obj).bN()) continue;
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
        int j = h * 2 + 1;
        while (j > 0) {
            int i = h - Math.abs(searchRange - j);
            while (i >= 0) {
                serchMap[j][searchRange + i] = true;
                serchMap[j][searchRange - i] = true;
                --i;
            }
            --j;
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
        int n = searchDIR.length;
        int n2 = 0;
        while (n2 < n) {
            dir = nArray3[n2];
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
            ++n2;
        }
        while (!queueSerch.isEmpty()) {
            locBase = (int[])queueSerch.removeFirst();
            nArray3 = searchDIR;
            n = searchDIR.length;
            n2 = 0;
            while (n2 < n) {
                dir = nArray3[n2];
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
                ++n2;
            }
        }
        return -1;
    }

    private void k(int healHp, int effectId) {
        this.b(new S_SkillSound(this.fr(), effectId));
        if (this.bB(173)) {
            healHp /= 2;
        }
        if (this instanceof L1PetInstance) {
            ((L1PetInstance)this).a(this.ea() + healHp);
        } else if (this instanceof L1SummonInstance) {
            ((L1SummonInstance)this).a(this.ea() + healHp);
        } else {
            this.bx(this.ea() + healHp);
        }
    }

    private void d(int time) {
        this.b(new S_SkillHaste(this.fr(), 1, time));
        this.b(new S_SkillSound(this.fr(), 191));
        this.cu(1);
        this.j(1001, time * 1000);
    }

    public void e(int type, int chance) {
        if (this.bB(71)) {
            return;
        }
        if (Random.a(100) > chance) {
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
        int rdir = Random.a(8);
        int i = 0;
        while (i < 8) {
            int dir = rdir + i;
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
            ++i;
        }
        return false;
    }

    public void a(int nx, int ny, int dir) {
        for (L1PcInstance pc : L1World.a().f(this)) {
            pc.a(new S_SkillSound(this.fr(), 169));
            pc.a(new S_RemoveObject(this));
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
            this.b(new S_SkillSound(this.fr(), transformGfxId));
        }
        L1Npc npcTemplate = NpcTable.a().a(transformId);
        this.a(npcTemplate);
        this.b(new S_NpcChangeShape(this.fr(), this.fe(), this.fa(), this.eY()));
        for (L1PcInstance pc : L1World.a().f(this)) {
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
        new S_044().a((L1Character)this, 1);
    }

    protected synchronized void A() {
        if (this.af == null) {
            this.af = GeneralThreadPool.a().a(new L1R_b(), Config.at * 1000);
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
        L1NpcChat npcChat = null;
        if (chatTiming == 0) {
            npcChat = NpcChatTable.a().a(this.z());
        } else if (chatTiming == 1) {
            npcChat = NpcChatTable.a().b(this.z());
        } else if (chatTiming == 2) {
            npcChat = NpcChatTable.a().c(this.z());
        }
        if (npcChat == null) {
            return;
        }
        if (Random.a(100) > npcChat.n()) {
            return;
        }
        new NpcChatTimer(this, npcChat).a();
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
    public void a(L1PcInstance pc) {
        if (this.U_().ag()) {
            this.ct(this.h(pc.fs(), pc.ft()));
            this.b(new S_ChangeHeading(this));
            if (this.U_().w() && !this.ai()) {
                this.l(true);
                GeneralThreadPool.a().a(new L1R_g(), 10000L);
            }
        }
        L1NpcTalkData.b(this, pc);
    }

    public void g(int x, int y) {
        this.ak = x;
        this.al = y;
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

    public boolean d(L1Character cha) {
        return this.l.a(cha);
    }

    public boolean L() {
        L1PcInstance pc;
        return this.M() instanceof L1PcInstance && (pc = (L1PcInstance)this.M()).bE() == 1;
    }

    public L1Character M() {
        return this.k;
    }

    public void e(L1Character master) {
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

    public void a(L1Inventory inventory) {
        this.o = inventory;
    }

    public L1Npc U_() {
        return this.L;
    }

    public int Q() {
        return this.M;
    }

    public void o(int petcost) {
        this.M = petcost;
    }

    public L1Spawn R() {
        return this.N;
    }

    public void a(L1Spawn spawn) {
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

    public L1MobGroupInfo aj() {
        return this.ag;
    }

    public void a(L1MobGroupInfo mobGroupInfo) {
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

    private class L1R_a
    extends TimerTask {
        private L1R_a() {
        }

        @Override
        public void run() {
            if (L1NpcInstance.this.ah()) {
                return;
            }
            L1NpcInstance.this.aa_();
        }
    }

    private class L1R_b
    extends TimerTask {
        private L1R_b() {
        }

        @Override
        public void run() {
            if (!L1NpcInstance.this.eX() || L1NpcInstance.this.ah()) {
                return;
            }
            L1NpcInstance.this.aa_();
        }
    }

    private class L1R_c
    implements Runnable {
        private L1R_c() {
        }

        @Override
        public void run() {
            try {
                try {
                    while (!L1NpcInstance.this.H.isEmpty()) {
                        Thread.sleep(1000L);
                        if (L1NpcInstance.this.ah()) {
                            break;
                        }
                        Iterator iterator = ((ConcurrentHashMap.KeySetView)L1NpcInstance.this.H.keySet()).iterator();
                        while (iterator.hasNext()) {
                            int itemobjid = (Integer)iterator.next();
                            int digestCounter = (Integer)L1NpcInstance.this.H.get(itemobjid) - 1;
                            if (digestCounter > 0) {
                                L1NpcInstance.this.H.put(itemobjid, digestCounter);
                                continue;
                            }
                            L1NpcInstance.this.H.remove(itemobjid);
                            L1ItemInstance item = L1NpcInstance.this.o.e(itemobjid);
                            if (item == null) continue;
                            L1NpcInstance.this.o.f(item);
                        }
                    }
                }
                catch (Exception e) {
                    y.log(Level.SEVERE, e.getLocalizedMessage(), e);
                    L1NpcInstance.this.I = false;
                }
            }
            finally {
                L1NpcInstance.this.I = false;
            }
        }
    }

    private class L1R_d
    extends TimerTask {
        private final int b;

        @Override
        public void run() {
            try {
                if (L1NpcInstance.this.ah() || L1NpcInstance.this.eX()) {
                    L1NpcInstance.this.v();
                    return;
                }
                if (L1NpcInstance.this.ea() <= 0 || L1NpcInstance.this.ea() >= L1NpcInstance.this.ew()) {
                    L1NpcInstance.this.v();
                    return;
                }
                L1NpcInstance.this.a(L1NpcInstance.this.ea() + this.b);
            }
            catch (Exception e) {
                y.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }

        public L1R_d(int point) {
            this.b = point;
        }
    }

    private class L1R_e
    extends TimerTask {
        private final int b;

        @Override
        public void run() {
            try {
                if (L1NpcInstance.this.ah() || L1NpcInstance.this.eX()) {
                    L1NpcInstance.this.x();
                    return;
                }
                if (L1NpcInstance.this.eb() <= 0 || L1NpcInstance.this.eb() >= L1NpcInstance.this.ex()) {
                    L1NpcInstance.this.x();
                    return;
                }
                L1NpcInstance.this.i_(L1NpcInstance.this.eb() + this.b);
            }
            catch (Exception e) {
                y.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }

        private L1R_e(int point) {
            this.b = point;
        }
    }

    private class L1R_f
    extends TimerTask {
        private L1R_f() {
        }

        private void b() {
            L1NpcInstance.this.an = 0;
            L1NpcInstance.this.z = false;
            if (!L1NpcInstance.this.ag()) {
                L1NpcInstance.this.t();
            }
            L1NpcInstance.this.i(false);
        }

        public void a() {
            L1NpcInstance.this.i(true);
            GeneralThreadPool.a().a(this, 0L);
        }

        private void a(int delay) {
            GeneralThreadPool.a().a(new L1R_f(), delay);
        }

        @Override
        public void run() {
            try {
                if (this.c()) {
                    this.b();
                    return;
                }
                if (L1NpcInstance.this.E > 0) {
                    this.a(L1NpcInstance.this.E);
                    L1NpcInstance.this.E = 0;
                    return;
                }
                if (L1NpcInstance.this.ed() || L1NpcInstance.this.ec()) {
                    this.a(200);
                    return;
                }
                if (!L1NpcInstance.this.h()) {
                    this.a(L1NpcInstance.this.af());
                    return;
                }
                this.b();
            }
            catch (Exception e) {
                y.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }

        private boolean c() {
            return L1NpcInstance.this.ah() || L1NpcInstance.this.eX() || L1NpcInstance.this.ea() <= 0 || L1NpcInstance.this.ac() != 0;
        }
    }

    private class L1R_g
    extends TimerTask {
        private L1R_g() {
        }

        @Override
        public void run() {
            L1NpcInstance.this.l(false);
        }
    }
}
