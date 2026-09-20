/*
 * Decompiled with CFR 0.152.
 */
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
import l1r.aq.L1Object;
import l1r.aq.L1Paralysis;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1World;
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

public class L1Character
extends L1Object {
    private L1Poison a = null;
    private boolean b;
    private boolean c;
    private final ConcurrentHashMap<Integer, L1NpcInstance> d = new ConcurrentHashMap();
    private final ConcurrentHashMap<Integer, L1DollInstance> e = new ConcurrentHashMap();
    private final HashMap<Integer, L1SkillTimer__obf_d> f = new HashMap();
    private final ConcurrentHashMap<Integer, L1ItemDelay.L1R_a> g = new ConcurrentHashMap();
    private final ConcurrentHashMap<Integer, L1FollowerInstance> h = new ConcurrentHashMap();
    private int i;
    private int j;
    private L1Paralysis k;
    private boolean l = false;
    private int m;
    private final CopyOnWriteArrayList<L1Object> n = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<L1PcInstance> o = new CopyOnWriteArrayList();
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
        L1PolyMorph.b(this);
        for (L1PcInstance pc : L1World.a().f(this)) {
            pc.a(new S_RemoveObject(this));
            pc.d(this);
            pc.h();
        }
    }

    public int ea() {
        return this.i;
    }

    public void a(int i) {
        this.i = i;
        if (this.i >= this.ew()) {
            this.i = this.ew();
        }
    }

    public void bx(int i) {
        this.i = i;
    }

    public int eb() {
        return this.j;
    }

    public void i_(int i) {
        this.j = i;
        if (this.j >= this.ex()) {
            this.j = this.ex();
        }
    }

    public void by(int i) {
        this.j = i;
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

    public L1Paralysis ee() {
        return this.k;
    }

    public void a(L1Paralysis p) {
        this.k = p;
    }

    public void ef() {
        if (this.k != null) {
            this.k.a();
        }
    }

    public void b(ServerBasePacket packet) {
        for (L1PcInstance pc : L1World.a().f(this)) {
            if (!pc.b((L1Object)this)) continue;
            pc.a(packet);
        }
    }

    public void a(ServerBasePacket packet, L1Character target) {
        for (L1PcInstance pc : L1World.a().b((L1Object)this, target)) {
            if (!pc.b((L1Object)this)) continue;
            pc.a(packet);
        }
    }

    public void c(ServerBasePacket packet) {
        for (L1PcInstance pc : L1World.a().f(this)) {
            if (!pc.b((L1Object)this) || !pc.bB(26003)) continue;
            pc.a(packet);
        }
    }

    public void d(ServerBasePacket packet) {
        for (L1PcInstance pc : L1World.a().c(this, 50)) {
            if (!pc.b((L1Object)this)) continue;
            pc.a(packet);
        }
    }

    public int[] eg() {
        int[] loc = new int[2];
        int x = this.fs();
        int y = this.ft();
        int heading = this.fb();
        if (heading != 0) {
            if (heading == 1) {
                --y;
            } else if (heading == 2) {
                ++x;
            } else if (heading == 3) {
                ++x;
                ++y;
            } else if (heading == 4) {
                ++y;
            } else if (heading == 5) {
                --x;
                ++y;
            } else if (heading == 6) {
                --x;
            } else if (heading == 7) {
                --x;
                --y;
            }
        }
        loc[0] = ++x;
        loc[1] = --y;
        return loc;
    }

    public int a(L1Object obj) {
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
        int p = side_x - side_y;
        while (true) {
            if (!this.fq().c(chx, chy)) {
                return false;
            }
            if (chx == tx && chy == ty) break;
            int q = p * 2;
            if (q >= -1 * side_y) {
                p -= side_y;
                chx += diff_x;
            }
            if (q < side_x) {
                p += side_x;
                chy += diff_y;
            }
            if (Math.abs(chx - this.fs()) < side_x || Math.abs(chy - this.ft()) < side_y) continue;
            chx = tx;
            chy = ty;
        }
        return true;
    }

    public boolean c(int x, int y, int range) {
        if (range >= 7 ? this.fu().d(new Point(x, y)) > range : this.fu().c(new Point(x, y)) > range) {
            return false;
        }
        return this.i(x, y);
    }

    public L1Inventory y() {
        return null;
    }

    private void c(int _skillId) {
        int[][] repeatedSkills;
        int[][] nArrayArray = repeatedSkills = new int[][]{{14, 134, 218}, {4069, 4085}, {1032, 1033, 1034, 1035, 1036}, {148, 149, 156, 163, 166, 4006}, {3, 151, 159, 168}, {1000, 1016, 52, 101, 150, 1026, 1037}, {43, 54, 1001, 1037}, {1027, 1037}, {1038, 1037}, {5001, 5002, 5003}, {29, 76, 152}, {26, 110}, {185, 190, 195}, {42, 109}, {201, 106}, {60, 97}, {202, 64}, {20, 40}, {169, 176}, {4056, 4057, 4079}, {1014, 1015, 1013}, {4086, 4087, 4088, 4089, 4090, 4091}, {4001, 4002, 4003, 4004, 4005, 4007, 4070, 4078}, {4008, 4009, 4010, 4081, 4082, 4083}, {4013, 4014, 4015, 4016, 4017, 4018, 4019, 4020, 4021, 4022, 4023, 4024, 4025, 4026, 4027, 4028, 4029, 4030, 4031, 4032, 4033, 4034, 4035, 4036, 4037, 4038, 4039, 4040, 4041, 4042, 4043, 4044, 4045, 4046, 4047, 4048, 4072, 4073, 4074, 4075}, {4049, 4050, 4051, 4052, 4053, 4054, 4055}, {3000, 3001, 3002, 3003, 3004, 3005, 3006, 3008, 3009, 3010, 3011, 3012, 3013, 3014, 3016, 3017, 3018, 3019, 3020, 3021, 3022, 3024, 3025, 3026, 3027, 3028, 3029, 3030, 3032, 3033, 3034, 3035, 3036, 3037, 3038, 3040, 3041, 3042, 3043, 3044, 3045, 3046, 3049, 3050, 3051, 3053, 3054, 3055}, {3007, 3015, 3023, 3031, 3039, 3047, 3052, 3056}};
        int n = repeatedSkills.length;
        int n2 = 0;
        while (n2 < n) {
            int[] skills;
            int[] nArray = skills = nArrayArray[n2];
            int n3 = skills.length;
            int n4 = 0;
            while (n4 < n3) {
                int id = nArray[n4];
                if (id == _skillId) {
                    this.a(skills, _skillId);
                }
                ++n4;
            }
            ++n2;
        }
    }

    private void a(int[] repeat_skill, int _skillId) {
        int[] nArray = repeat_skill;
        int n = repeat_skill.length;
        int n2 = 0;
        while (n2 < n) {
            int skillId = nArray[n2];
            if (skillId != _skillId) {
                this.bz(skillId);
            }
            ++n2;
        }
    }

    public HashMap<Integer, L1SkillTimer__obf_d> eh() {
        return this.f;
    }

    private void a(int skillId, int timeMillis) {
        L1SkillTimer__obf_d timer = null;
        if (timeMillis > 0) {
            timer = L1SkillTimerCreator.a(this, skillId, timeMillis, null);
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
        L1SkillTimer__obf_d timer = null;
        if (timeMillis > 0) {
            timer = L1SkillTimerCreator.a(this, skillId, timeMillis, limitTime);
            timer.c();
        }
        this.f.put(skillId, timer);
    }

    public void bz(int skillId) {
        L1SkillTimer__obf_d timer = this.f.remove(skillId);
        if (timer != null) {
            timer.d();
        }
    }

    public void bA(int skillId) {
        L1SkillTimer__obf_d timer = this.f.remove(skillId);
        if (timer != null) {
            timer.e();
        }
    }

    public void ei() {
        for (L1SkillTimer__obf_d timer : this.f.values()) {
            if (timer == null) continue;
            timer.e();
        }
        this.f.clear();
    }

    public boolean bB(int skillId) {
        return this.f.containsKey(skillId);
    }

    public int bC(int skillId) {
        L1SkillTimer__obf_d timer = this.f.get(skillId);
        if (timer == null) {
            return -1;
        }
        return timer.a();
    }

    public Timestamp bD(int skillId) {
        L1SkillTimer__obf_d timer = this.f.get(skillId);
        if (timer == null) {
            return null;
        }
        return timer.b();
    }

    public void a(int skillId, boolean b) {
        L1SkillTimer__obf_d timer = this.f.get(skillId);
        if (timer == null) {
            return;
        }
        timer.a(b);
    }

    public void W(boolean flag) {
        this.l = flag;
    }

    public boolean ej() {
        return this.l;
    }

    public void a(int delayId, L1ItemDelay.L1R_a timer) {
        this.g.put(delayId, timer);
    }

    public void bE(int delayId) {
        this.g.remove(delayId);
    }

    public boolean bF(int delayId) {
        return this.g.containsKey(delayId);
    }

    public void e(L1NpcInstance npc) {
        this.d.put(npc.fr(), npc);
        if (this instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)this;
            pc.a(new S_PetCtrlMenu(pc, npc, true));
        }
    }

    public ConcurrentHashMap<Integer, L1NpcInstance> ek() {
        return this.d;
    }

    public void b(L1DollInstance doll) {
        this.e.put(doll.fr(), doll);
    }

    public ConcurrentHashMap<Integer, L1DollInstance> el() {
        return this.e;
    }

    public void a(L1FollowerInstance follower) {
        this.h.put(follower.fr(), follower);
    }

    public ConcurrentHashMap<Integer, L1FollowerInstance> em() {
        return this.h;
    }

    public void a(L1Poison poison) {
        this.a = poison;
    }

    public void en() {
        if (this.a == null) {
            return;
        }
        this.a.b();
    }

    public L1Poison eo() {
        return this.a;
    }

    public void y(int effectId) {
        this.b(new S_Poison(this.fr(), effectId));
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

    public boolean b(L1Object obj) {
        return this.n.contains(obj);
    }

    public List<L1Object> eq() {
        return this.n;
    }

    public List<L1PcInstance> er() {
        return this.o;
    }

    public void c(L1Object obj) {
        if (!this.n.contains(obj)) {
            this.n.add(obj);
            if (obj instanceof L1PcInstance) {
                this.o.add((L1PcInstance)obj);
            }
        }
    }

    public void d(L1Object obj) {
        this.n.remove(obj);
        if (obj instanceof L1PcInstance) {
            this.o.remove(obj);
        }
    }

    public void es() {
        this.n.clear();
        this.o.clear();
    }

    public String et() {
        if (this instanceof L1PcInstance) {
            return ((L1PcInstance)this).af() ? "**\u5b88\u8b77\u8005**" : this.p;
        }
        return this.p;
    }

    public String eu() {
        return this.p;
    }

    public void e(String s) {
        this.p = s;
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
        this.r = (short)IntRange.b(this.s, 1, Short.MAX_VALUE);
        this.i = Math.min(this.i, this.r);
    }

    public void bH(int i) {
        this.bG(this.s + i);
    }

    public short ex() {
        return this.t;
    }

    public void bI(int mp) {
        this.u = mp;
        this.t = (short)IntRange.b(this.u, 0, Short.MAX_VALUE);
        this.j = Math.min(this.j, this.t);
    }

    public void bJ(int i) {
        this.bI(this.u + i);
    }

    public int ey() {
        return this.v;
    }

    public void bK(int i) {
        this.v = IntRange.b(i, -1280, 127);
    }

    public void bL(int i) {
        this.bK(this.v + i);
    }

    public byte ez() {
        return this.w;
    }

    public void bM(int i) {
        this.y = (short)i;
        this.w = (byte)IntRange.b(i, 1, 127);
    }

    public void bN(int i) {
        this.bM(this.y + i);
    }

    public byte eA() {
        return this.z;
    }

    public void bO(int i) {
        this.A = (short)i;
        this.z = (byte)IntRange.b(i, 1, 127);
    }

    public void bP(int i) {
        this.bO(this.A + i);
    }

    public byte eB() {
        return this.B;
    }

    public void bQ(int i) {
        this.C = (short)i;
        this.B = (byte)IntRange.b(i, 1, 127);
    }

    public void bR(int i) {
        this.bQ(this.C + i);
    }

    public byte eC() {
        return this.D;
    }

    public void bS(int i) {
        this.E = (short)i;
        this.D = (byte)IntRange.b(i, 1, 127);
    }

    public void bT(int i) {
        this.bS(this.E + i);
    }

    public byte eD() {
        return this.F;
    }

    public void bU(int i) {
        this.G = (short)i;
        this.F = (byte)IntRange.b(i, 1, 127);
    }

    public void bV(int i) {
        this.bU(this.G + i);
    }

    public byte eE() {
        return this.H;
    }

    public void bW(int i) {
        this.I = (short)i;
        this.H = (byte)IntRange.b(i, 1, 127);
    }

    public void bX(int i) {
        this.bW(this.I + i);
    }

    public int eF() {
        return this.J;
    }

    public void bY(int i) {
        this.K += i;
        this.J = this.K >= 127 ? 127 : (this.K <= -128 ? -128 : this.K);
    }

    public int eG() {
        return this.L;
    }

    public void bZ(int i) {
        this.M += i;
        this.L = this.M >= 127 ? 127 : (this.M <= -128 ? -128 : this.M);
    }

    public int eH() {
        return this.N;
    }

    public void ca(int i) {
        this.O += i;
        this.N = this.O >= 127 ? 127 : (this.O <= -128 ? -128 : this.O);
    }

    public int eI() {
        return this.P;
    }

    public void cb(int i) {
        this.Q += i;
        this.P = this.Q >= 127 ? 127 : (this.Q <= -128 ? -128 : this.Q);
    }

    public int eJ() {
        return this.R;
    }

    public void cc(int i) {
        this.R = i;
    }

    public int eK() {
        return this.S;
    }

    public void cd(int i) {
        this.T += i;
        this.S = this.T > 127 ? 127 : (this.T < -128 ? -128 : this.T);
    }

    public int eL() {
        return this.U;
    }

    public void ce(int i) {
        this.V += i;
        this.U = this.V > 127 ? 127 : (this.V < -128 ? -128 : this.V);
    }

    public int eM() {
        return this.W;
    }

    public void cf(int i) {
        this.X += i;
        this.W = this.X > 127 ? 127 : (this.X < -128 ? -128 : this.X);
    }

    public int eN() {
        return this.Y;
    }

    public void cg(int i) {
        this.Z += i;
        this.Y = this.Z > 127 ? 127 : (this.Z < -128 ? -128 : this.Z);
    }

    public int eO() {
        return this.aa;
    }

    public void ch(int i) {
        this.ab += i;
        this.aa = this.ab > 127 ? 127 : (this.ab < -128 ? -128 : this.ab);
    }

    public int eP() {
        return this.ac;
    }

    public void ci(int i) {
        this.ad += i;
        this.ac = this.ad > 127 ? 127 : (this.ad < -128 ? -128 : this.ad);
    }

    public int eQ() {
        return this.ae;
    }

    public void cj(int i) {
        this.af += i;
        this.ae = this.af > 127 ? 127 : (this.af < -128 ? -128 : this.af);
    }

    public int eR() {
        return this.ag;
    }

    public void ck(int i) {
        this.ah += i;
        this.ag = this.ah >= 127 ? 127 : (this.ah <= -128 ? -128 : this.ah);
    }

    public int eS() {
        return this.ai;
    }

    public void cl(int i) {
        this.aj += i;
        this.ai = this.aj >= 127 ? 127 : (this.aj <= -128 ? -128 : this.aj);
    }

    public int eT() {
        return this.ak;
    }

    public void cm(int i) {
        this.al += i;
        this.ak = this.al >= 127 ? 127 : (this.al <= -128 ? -128 : this.al);
    }

    public int eU() {
        return this.am;
    }

    public void cn(int i) {
        this.an += i;
        this.am = this.an >= 127 ? 127 : (this.an <= -128 ? -128 : this.an);
    }

    public int W_() {
        if (this.bB(153)) {
            return this.x * 3 / 4;
        }
        return this.x;
    }

    public void co(int i) {
        this.x += i;
    }

    public int eV() {
        return this.eW() + this.ao;
    }

    public void cp(int i) {
        this.ao += i;
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

    public void X(boolean flag) {
        this.ap = flag;
    }

    public int eY() {
        return this.aq;
    }

    public void cq(int i) {
        this.aq = i;
    }

    public String eZ() {
        return this.ar;
    }

    public void f(String s) {
        this.ar = s;
    }

    public int fa() {
        return this.as;
    }

    public void cr(int i) {
        this.as = i;
    }

    public synchronized void cs(int i) {
        this.as += i;
        if (this.as > Short.MAX_VALUE) {
            this.as = Short.MAX_VALUE;
        } else if (this.as < Short.MIN_VALUE) {
            this.as = Short.MIN_VALUE;
        }
    }

    public int fb() {
        return this.at;
    }

    public void ct(int i) {
        this.at = i;
    }

    public int fc() {
        return this.au;
    }

    public void cu(int i) {
        this.au = i;
    }

    public int fd() {
        return this.av;
    }

    public void cv(int i) {
        this.av = i;
    }

    public int fe() {
        return this.aw;
    }

    public void cw(int i) {
        this.aw = i;
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

    public void cx(int i) {
        this.x = i;
    }

    public void fg() {
        int lightSize = 0;
        if (this instanceof L1NpcInstance) {
            L1NpcInstance npc = (L1NpcInstance)this;
            lightSize = npc.aa();
        }
        if (this.bB(2)) {
            lightSize = 14;
        }
        for (L1ItemInstance item : this.y().d()) {
            int itemlightSize;
            if (!item.f() || item.a().aP() != 2 || (itemlightSize = item.a().c()) == 0 || !item.T() || itemlightSize <= lightSize) continue;
            lightSize = itemlightSize;
        }
        if (this instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)this;
            pc.a(new S_Light(pc.fr(), lightSize));
        }
        if (!this.ff()) {
            this.b(new S_Light(this.fr(), lightSize));
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

    public void cy(int i) {
        this.ay = i;
    }

    public int fi() {
        return this.az;
    }

    public void cz(int i) {
        this.az = i;
    }

    public int fj() {
        return this.aA;
    }

    public void c_(int i) {
        this.aA = i;
    }

    public int fk() {
        return this.aB;
    }

    public void cA(int i) {
        this.aB += i;
        this.aB = Math.max(0, Math.min(this.aB, 12));
    }

    public int fl() {
        return this.aC;
    }

    public void cB(int i) {
        this.aC += i;
        this.aC = Math.max(0, Math.min(this.aC, 12));
    }

    public boolean d(L1Character cha, int npcId) {
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

    public void cC(int i) {
        this.aD = i;
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
