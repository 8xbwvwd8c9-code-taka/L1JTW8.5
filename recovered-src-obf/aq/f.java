/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.e;
import ap.k;
import ap.q;
import ap.t;
import ap.u;
import aq.aa;
import aq.ab;
import aq.ae;
import aq.aq;
import av.a;
import az.c;
import be.br;
import be.cp;
import be.cz;
import be.dh;
import be.eu;
import bi.d;
import bi.h;
import auto.hunt.AutoHuntService;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class f
extends aa {
    private c a = null;
    private boolean b;
    private boolean c;
    private final ConcurrentHashMap<Integer, t> d = new ConcurrentHashMap();
    private final ConcurrentHashMap<Integer, e> e = new ConcurrentHashMap();
    private final HashMap<Integer, bg.d> f = new HashMap();
    private final ConcurrentHashMap<Integer, a.a> g = new ConcurrentHashMap();
    private final ConcurrentHashMap<Integer, k> h = new ConcurrentHashMap();
    private int i;
    private int j;
    private ab k;
    private boolean l = false;
    private int m;
    private final CopyOnWriteArrayList<aa> n = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<u> o = new CopyOnWriteArrayList();
    private String p;
    private int q = 1;
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

    public void j(int hp) {
        if (!this.eX()) {
            return;
        }
        if (hp <= 0) {
            hp = 1;
        }
        this.a(hp);
        this.X(false);
        this.cq(0);
        aq.ae.b(this);
        for (u pc : aq.aq.a().f(this)) {
            pc.a(new dh(this));
            pc.d(this);
            pc.h();
        }
    }

    public int ea() {
        return this.i;
    }

    public void a(int i2) {
        this.i = i2;
        if (this.i >= this.ew()) {
            this.i = this.ew();
        }
    }

    public void bx(int i2) {
        this.i = i2;
    }

    public int eb() {
        return this.j;
    }

    public void i_(int i2) {
        this.j = i2;
        if (this.j >= this.ex()) {
            this.j = this.ex();
        }
    }

    public void by(int i2) {
        this.j = i2;
    }

    public boolean ec() {
        return this.c;
    }

    public void U(boolean sleeped) {
        this.c = sleeped;
    }

    public boolean ed() {
        return this.b;
    }

    public void V(boolean paralyzed) {
        this.b = paralyzed;
    }

    public ab ee() {
        return this.k;
    }

    public void a(ab p2) {
        this.k = p2;
    }

    public void ef() {
        if (this.k != null) {
            this.k.a();
        }
    }

    public void b(eu packet) {
        for (u pc : aq.aq.a().f(this)) {
            if (!pc.b((aa)this)) continue;
            pc.a(packet);
        }
    }

    public void a(eu packet, f target) {
        for (u pc : aq.aq.a().b((aa)this, target)) {
            if (!pc.b((aa)this)) continue;
            pc.a(packet);
        }
    }

    public void c(eu packet) {
        for (u pc : aq.aq.a().f(this)) {
            if (!pc.b((aa)this) || !pc.bB(26003)) continue;
            pc.a(packet);
        }
    }

    public void d(eu packet) {
        for (u pc : aq.aq.a().c(this, 50)) {
            if (!pc.b((aa)this)) continue;
            pc.a(packet);
        }
    }

    public int[] eg() {
        int[] loc = new int[2];
        int x2 = this.fs();
        int y2 = this.ft();
        int heading = this.fb();
        if (heading != 0) {
            if (heading == 1) {
                --y2;
            } else if (heading == 2) {
                ++x2;
            } else if (heading == 3) {
                ++x2;
                ++y2;
            } else if (heading == 4) {
                ++y2;
            } else if (heading == 5) {
                --x2;
                ++y2;
            } else if (heading == 6) {
                --x2;
            } else if (heading == 7) {
                --x2;
                --y2;
            }
        }
        loc[0] = ++x2;
        loc[1] = --y2;
        return loc;
    }

    public int a(aa obj) {
        return this.h(obj.fs(), obj.ft());
    }

    public int h(int tx, int ty) {
        float dis_y;
        float dis_x = Math.abs(this.fs() - tx);
        float dis = Math.max(dis_x, dis_y = (float)Math.abs(this.ft() - ty));
        if (dis == 0.0f) {
            return this.fb();
        }
        int avg_x = (int)Math.floor(dis_x / dis + 0.59f);
        int avg_y = (int)Math.floor(dis_y / dis + 0.59f);
        int dir_x = 0;
        int dir_y = 0;
        if (this.fs() < tx) {
            dir_x = 1;
        }
        if (this.fs() > tx) {
            dir_x = -1;
        }
        if (this.ft() < ty) {
            dir_y = 1;
        }
        if (this.ft() > ty) {
            dir_y = -1;
        }
        if (avg_x == 0) {
            dir_x = 0;
        }
        if (avg_y == 0) {
            dir_y = 0;
        }
        if (dir_x == 1 && dir_y == -1) {
            return 1;
        }
        if (dir_x == 1 && dir_y == 0) {
            return 2;
        }
        if (dir_x == 1 && dir_y == 1) {
            return 3;
        }
        if (dir_x == 0 && dir_y == 1) {
            return 4;
        }
        if (dir_x == -1 && dir_y == 1) {
            return 5;
        }
        if (dir_x == -1 && dir_y == 0) {
            return 6;
        }
        if (dir_x == -1 && dir_y == -1) {
            return 7;
        }
        if (dir_x == 0 && dir_y == -1) {
            return 0;
        }
        return this.fb();
    }

    public boolean i(int tx, int ty) {
        int chx = this.fs();
        int chy = this.ft();
        int side_x = Math.abs(chx - tx);
        int side_y = Math.abs(chy - ty);
        if (side_x == 0 && side_y == 0) {
            return true;
        }
        int diff_x = chx < tx ? 1 : -1;
        int diff_y = chy < ty ? 1 : -1;
        int p2 = side_x - side_y;
        while (true) {
            if (!this.fq().c(chx, chy)) {
                return false;
            }
            if (chx == tx && chy == ty) break;
            int q2 = p2 * 2;
            if (q2 >= -1 * side_y) {
                p2 -= side_y;
                chx += diff_x;
            }
            if (q2 < side_x) {
                p2 += side_x;
                chy += diff_y;
            }
            if (Math.abs(chx - this.fs()) < side_x || Math.abs(chy - this.ft()) < side_y) continue;
            chx = tx;
            chy = ty;
        }
        return true;
    }

    public boolean c(int x2, int y2, int range) {
        if (range >= 7 ? this.fu().d(new h(x2, y2)) > range : this.fu().c(new h(x2, y2)) > range) {
            return false;
        }
        return this.i(x2, y2);
    }

    public au.f y() {
        return null;
    }

    private void c(int _skillId) {
        int[][] repeatedSkills;
        int[][] nArrayArray = repeatedSkills = new int[][]{{14, 134, 218}, {4069, 4085}, {1032, 1033, 1034, 1035, 1036}, {148, 149, 156, 163, 166, 4006}, {3, 151, 159, 168}, {1000, 1016, 52, 101, 150, 1026, 1037}, {43, 54, 1001, 1037}, {1027, 1037}, {1038, 1037}, {5001, 5002, 5003}, {29, 76, 152}, {26, 110}, {185, 190, 195}, {42, 109}, {201, 106}, {60, 97}, {202, 64}, {20, 40}, {169, 176}, {4056, 4057, 4079}, {1014, 1015, 1013}, {4086, 4087, 4088, 4089, 4090, 4091}, {4001, 4002, 4003, 4004, 4005, 4007, 4070, 4078}, {4008, 4009, 4010, 4081, 4082, 4083}, {4013, 4014, 4015, 4016, 4017, 4018, 4019, 4020, 4021, 4022, 4023, 4024, 4025, 4026, 4027, 4028, 4029, 4030, 4031, 4032, 4033, 4034, 4035, 4036, 4037, 4038, 4039, 4040, 4041, 4042, 4043, 4044, 4045, 4046, 4047, 4048, 4072, 4073, 4074, 4075}, {4049, 4050, 4051, 4052, 4053, 4054, 4055}, {3000, 3001, 3002, 3003, 3004, 3005, 3006, 3008, 3009, 3010, 3011, 3012, 3013, 3014, 3016, 3017, 3018, 3019, 3020, 3021, 3022, 3024, 3025, 3026, 3027, 3028, 3029, 3030, 3032, 3033, 3034, 3035, 3036, 3037, 3038, 3040, 3041, 3042, 3043, 3044, 3045, 3046, 3049, 3050, 3051, 3053, 3054, 3055}, {3007, 3015, 3023, 3031, 3039, 3047, 3052, 3056}};
        int n2 = repeatedSkills.length;
        int n3 = 0;
        while (n3 < n2) {
            int[] skills;
            int[] nArray = skills = nArrayArray[n3];
            int n4 = skills.length;
            int n5 = 0;
            while (n5 < n4) {
                int id = nArray[n5];
                if (id == _skillId) {
                    this.a(skills, _skillId);
                }
                ++n5;
            }
            ++n3;
        }
    }

    private void a(int[] repeat_skill, int _skillId) {
        int[] nArray = repeat_skill;
        int n2 = repeat_skill.length;
        int n3 = 0;
        while (n3 < n2) {
            int skillId = nArray[n3];
            if (skillId != _skillId) {
                this.bz(skillId);
            }
            ++n3;
        }
    }

    public HashMap<Integer, bg.d> eh() {
        return this.f;
    }

    private void a(int skillId, int timeMillis) {
        bg.d timer = null;
        if (timeMillis > 0) {
            timer = bg.e.a(this, skillId, timeMillis, null);
            timer.c();
        }
        this.f.put(skillId, timer);
    }

    public void j(int skillId, int timeMillis) {
        this.c(skillId);
        if (this.bB(skillId)) {
            int remainingTimeMills = this.bC(skillId) * 1000;
            if (remainingTimeMills >= 0 && (remainingTimeMills < timeMillis || timeMillis == 0)) {
                this.bA(skillId);
                this.a(skillId, timeMillis);
            }
        } else {
            this.a(skillId, timeMillis);
        }
    }

    public void a(int skillId, int timeMillis, Timestamp limitTime) {
        this.c(skillId);
        if (this.bB(skillId)) {
            this.bz(skillId);
        }
        bg.d timer = null;
        if (timeMillis > 0) {
            timer = bg.e.a(this, skillId, timeMillis, limitTime);
            timer.c();
        }
        this.f.put(skillId, timer);
    }

    public void bz(int skillId) {
        bg.d timer = this.f.remove(skillId);
        if (timer != null) {
            timer.d();
        }
    }

    public void bA(int skillId) {
        bg.d timer = this.f.remove(skillId);
        if (timer != null) {
            timer.e();
        }
    }

    public void ei() {
        for (bg.d timer : this.f.values()) {
            if (timer == null) continue;
            timer.e();
        }
        this.f.clear();
    }

    public boolean bB(int skillId) {
        return this.f.containsKey(skillId);
    }

    public int bC(int skillId) {
        bg.d timer = this.f.get(skillId);
        if (timer == null) {
            return -1;
        }
        return timer.a();
    }

    public Timestamp bD(int skillId) {
        bg.d timer = this.f.get(skillId);
        if (timer == null) {
            return null;
        }
        return timer.b();
    }

    public void a(int skillId, boolean b2) {
        bg.d timer = this.f.get(skillId);
        if (timer == null) {
            return;
        }
        timer.a(b2);
    }

    public void W(boolean flag) {
        this.l = flag;
    }

    public boolean ej() {
        return this.l;
    }

    public void a(int delayId, a.a timer) {
        this.g.put(delayId, timer);
    }

    public void bE(int delayId) {
        this.g.remove(delayId);
    }

    public boolean bF(int delayId) {
        return this.g.containsKey(delayId);
    }

    public void e(t npc) {
        this.d.put(npc.fr(), npc);
        if (this instanceof u) {
            u pc = (u)this;
            pc.a(new cp(pc, npc, true));
        }
    }

    public ConcurrentHashMap<Integer, t> ek() {
        return this.d;
    }

    public void b(e doll) {
        this.e.put(doll.fr(), doll);
    }

    public ConcurrentHashMap<Integer, e> el() {
        return this.e;
    }

    public void a(k follower) {
        this.h.put(follower.fr(), follower);
    }

    public ConcurrentHashMap<Integer, k> em() {
        return this.h;
    }

    public void a(c poison) {
        this.a = poison;
    }

    public void en() {
        if (this.a == null) {
            return;
        }
        this.a.b();
    }

    public c eo() {
        return this.a;
    }

    public void y(int effectId) {
        this.b(new cz(this.fr(), effectId));
    }

    public int ep() {
        if (this.fq().b(this.fu())) {
            return 1;
        }
        if (this.fq().c(this.fu())) {
            return -1;
        }
        return 0;
    }

    public int m() {
        return this.m;
    }

    public void k(int exp) {
        this.m = exp;
    }

    public boolean b(aa obj) {
        return this.n.contains(obj);
    }

    public List<aa> eq() {
        return this.n;
    }

    public List<u> er() {
        return this.o;
    }

    public void c(aa obj) {
        if (!this.n.contains(obj)) {
            this.n.add(obj);
            if (obj instanceof u) {
                this.o.add((u)obj);
            }
        }
    }

    public void d(aa obj) {
        this.n.remove(obj);
        if (obj instanceof u) {
            this.o.remove(obj);
        }
    }

    public void es() {
        this.n.clear();
        this.o.clear();
    }

    public String et() {
        if (this instanceof u) {
            return ((u)this).af() ? "**\u5b88\u8b77\u8005**" : this.p;
        }
        return this.p;
    }

    public String eu() {
        return this.p;
    }

    public void e(String s2) {
        this.p = s2;
    }

    public synchronized int ev() {
        return this.q;
    }

    public synchronized void b(long level) {
        this.q = (int)level;
    }

    public short ew() {
        return this.r;
    }

    public void bG(int hp) {
        this.s = hp;
        this.r = (short)bi.f.b(this.s, 1, Short.MAX_VALUE);
        this.i = Math.min(this.i, this.r);
    }

    public void bH(int i2) {
        this.bG(this.s + i2);
    }

    public short ex() {
        return this.t;
    }

    public void bI(int mp) {
        this.u = mp;
        this.t = (short)bi.f.b(this.u, 0, Short.MAX_VALUE);
        this.j = Math.min(this.j, this.t);
    }

    public void bJ(int i2) {
        this.bI(this.u + i2);
    }

    public int ey() {
        return this.v;
    }

    public void bK(int i2) {
        this.v = bi.f.b(i2, -1280, 127);
    }

    public void bL(int i2) {
        this.bK(this.v + i2);
    }

    public byte ez() {
        return this.w;
    }

    public void bM(int i2) {
        this.y = (short)i2;
        this.w = (byte)bi.f.b(i2, 1, 127);
    }

    public void bN(int i2) {
        this.bM(this.y + i2);
    }

    public byte eA() {
        return this.z;
    }

    public void bO(int i2) {
        this.A = (short)i2;
        this.z = (byte)bi.f.b(i2, 1, 127);
    }

    public void bP(int i2) {
        this.bO(this.A + i2);
    }

    public byte eB() {
        return this.B;
    }

    public void bQ(int i2) {
        this.C = (short)i2;
        this.B = (byte)bi.f.b(i2, 1, 127);
    }

    public void bR(int i2) {
        this.bQ(this.C + i2);
    }

    public byte eC() {
        return this.D;
    }

    public void bS(int i2) {
        this.E = (short)i2;
        this.D = (byte)bi.f.b(i2, 1, 127);
    }

    public void bT(int i2) {
        this.bS(this.E + i2);
    }

    public byte eD() {
        return this.F;
    }

    public void bU(int i2) {
        this.G = (short)i2;
        this.F = (byte)bi.f.b(i2, 1, 127);
    }

    public void bV(int i2) {
        this.bU(this.G + i2);
    }

    public byte eE() {
        return this.H;
    }

    public void bW(int i2) {
        this.I = (short)i2;
        this.H = (byte)bi.f.b(i2, 1, 127);
    }

    public void bX(int i2) {
        this.bW(this.I + i2);
    }

    public int eF() {
        return this.J;
    }

    public void bY(int i2) {
        this.K += i2;
        this.J = this.K >= 127 ? 127 : (this.K <= -128 ? -128 : this.K);
    }

    public int eG() {
        return this.L;
    }

    public void bZ(int i2) {
        this.M += i2;
        this.L = this.M >= 127 ? 127 : (this.M <= -128 ? -128 : this.M);
    }

    public int eH() {
        return this.N;
    }

    public void ca(int i2) {
        this.O += i2;
        this.N = this.O >= 127 ? 127 : (this.O <= -128 ? -128 : this.O);
    }

    public int eI() {
        return this.P;
    }

    public void cb(int i2) {
        this.Q += i2;
        this.P = this.Q >= 127 ? 127 : (this.Q <= -128 ? -128 : this.Q);
    }

    public int eJ() {
        return this.R;
    }

    public void cc(int i2) {
        this.R = i2;
    }

    public int eK() {
        return this.S;
    }

    public void cd(int i2) {
        this.T += i2;
        this.S = this.T > 127 ? 127 : (this.T < -128 ? -128 : this.T);
    }

    public int eL() {
        return this.U;
    }

    public void ce(int i2) {
        this.V += i2;
        this.U = this.V > 127 ? 127 : (this.V < -128 ? -128 : this.V);
    }

    public int eM() {
        return this.W;
    }

    public void cf(int i2) {
        this.X += i2;
        this.W = this.X > 127 ? 127 : (this.X < -128 ? -128 : this.X);
    }

    public int eN() {
        return this.Y;
    }

    public void cg(int i2) {
        this.Z += i2;
        this.Y = this.Z > 127 ? 127 : (this.Z < -128 ? -128 : this.Z);
    }

    public int eO() {
        return this.aa;
    }

    public void ch(int i2) {
        this.ab += i2;
        this.aa = this.ab > 127 ? 127 : (this.ab < -128 ? -128 : this.ab);
    }

    public int eP() {
        return this.ac;
    }

    public void ci(int i2) {
        this.ad += i2;
        this.ac = this.ad > 127 ? 127 : (this.ad < -128 ? -128 : this.ad);
    }

    public int eQ() {
        return this.ae;
    }

    public void cj(int i2) {
        this.af += i2;
        this.ae = this.af > 127 ? 127 : (this.af < -128 ? -128 : this.af);
    }

    public int eR() {
        return this.ag;
    }

    public void ck(int i2) {
        this.ah += i2;
        this.ag = this.ah >= 127 ? 127 : (this.ah <= -128 ? -128 : this.ah);
    }

    public int eS() {
        return this.ai;
    }

    public void cl(int i2) {
        this.aj += i2;
        this.ai = this.aj >= 127 ? 127 : (this.aj <= -128 ? -128 : this.aj);
    }

    public int eT() {
        return this.ak;
    }

    public void cm(int i2) {
        this.al += i2;
        this.ak = this.al >= 127 ? 127 : (this.al <= -128 ? -128 : this.al);
    }

    public int eU() {
        return this.am;
    }

    public void cn(int i2) {
        this.an += i2;
        this.am = this.an >= 127 ? 127 : (this.an <= -128 ? -128 : this.an);
    }

    public int W_() {
        if (this.bB(153)) {
            return this.x * 3 / 4;
        }
        return this.x;
    }

    public void co(int i2) {
        this.x += i2;
    }

    public int eV() {
        return this.eW() + this.ao;
    }

    public void cp(int i2) {
        this.ao += i2;
    }

    public int eW() {
        return this.U() + bi.d.c(this.eD());
    }

    public int U() {
        return Math.min(this.ev(), 52) / 4;
    }

    public boolean eX() {
        return this.ap;
    }

    public void X(boolean flag) {
        if (flag && this instanceof u) {
            AutoHuntService.stop((u)this);
        }
        this.ap = flag;
    }

    public int eY() {
        return this.aq;
    }

    public void cq(int i2) {
        this.aq = i2;
    }

    public String eZ() {
        return this.ar;
    }

    public void f(String s2) {
        this.ar = s2;
    }

    public int fa() {
        return this.as;
    }

    public void cr(int i2) {
        this.as = i2;
    }

    public synchronized void cs(int i2) {
        this.as += i2;
        if (this.as > Short.MAX_VALUE) {
            this.as = Short.MAX_VALUE;
        } else if (this.as < Short.MIN_VALUE) {
            this.as = Short.MIN_VALUE;
        }
    }

    public int fb() {
        return this.at;
    }

    public void ct(int i2) {
        this.at = i2;
    }

    public int fc() {
        return this.au;
    }

    public void cu(int i2) {
        this.au = i2;
    }

    public int fd() {
        return this.av;
    }

    public void cv(int i2) {
        this.av = i2;
    }

    public int fe() {
        return this.aw;
    }

    public void cw(int i2) {
        this.aw = i2;
    }

    public boolean ff() {
        return this.bB(60) || this.bB(97);
    }

    public void z(int pt) {
        this.a(this.ea() + pt);
    }

    public int P() {
        return this.ax;
    }

    public void A(int karma) {
        this.ax = karma;
    }

    public void cx(int i2) {
        this.x = i2;
    }

    public void fg() {
        int lightSize = 0;
        if (this instanceof t) {
            t npc = (t)this;
            lightSize = npc.aa();
        }
        if (this.bB(2)) {
            lightSize = 14;
        }
        for (q item : this.y().d()) {
            int itemlightSize;
            if (!item.f() || item.a().aP() != 2 || (itemlightSize = item.a().c()) == 0 || !item.T() || itemlightSize <= lightSize) continue;
            lightSize = itemlightSize;
        }
        if (this instanceof u) {
            u pc = (u)this;
            pc.a(new br(pc.fr(), lightSize));
        }
        if (!this.ff()) {
            this.b(new br(this.fr(), lightSize));
        }
        this.cz(lightSize);
        this.cy(lightSize);
    }

    public int fh() {
        if (this.ff()) {
            return 0;
        }
        return this.ay;
    }

    public void cy(int i2) {
        this.ay = i2;
    }

    public int fi() {
        return this.az;
    }

    public void cz(int i2) {
        this.az = i2;
    }

    public int fj() {
        return this.aA;
    }

    public void c_(int i2) {
        this.aA = i2;
    }

    public int fk() {
        return this.aB;
    }

    public void cA(int i2) {
        this.aB += i2;
        this.aB = Math.max(0, Math.min(this.aB, 12));
    }

    public int fl() {
        return this.aC;
    }

    public void cB(int i2) {
        this.aC += i2;
        this.aC = Math.max(0, Math.min(this.aC, 12));
    }

    public boolean d(f cha, int npcId) {
        switch (npcId) {
            case 45912: 
            case 45913: 
            case 45914: 
            case 45915: {
                return !cha.bB(1014);
            }
            case 45916: {
                return !cha.bB(1015);
            }
            case 45941: {
                return !cha.bB(1013);
            }
            case 45752: 
            case 45753: {
                return !cha.bB(5015);
            }
            case 45625: 
            case 45674: 
            case 45675: 
            case 45685: 
            case 81082: {
                return !cha.bB(5014);
            }
            case 190026: {
                return !cha.bB(1030);
            }
        }
        if (npcId >= 46068 && npcId <= 46091 && cha.fe() == 6035) {
            return true;
        }
        return npcId >= 46092 && npcId <= 46106 && cha.fe() == 6034;
    }

    public int fm() {
        return this.aD;
    }

    public void cC(int i2) {
        this.aD = i2;
    }

    public int fn() {
        int poisonType = 0;
        if (this.eo() != null) {
            poisonType = this.eo().a();
        }
        if (this.ee() != null) {
            poisonType = this.ee().b();
        }
        return poisonType;
    }

    public int fo() {
        return this.aE;
    }

    public void cD(int skillDamageTemp) {
        this.aE = skillDamageTemp;
    }
}
