/*
 * Decompiled with CFR 0.152.
 */
package ap;

import am.c;
import ao.ah;
import ao.bg;
import ap.f;
import ap.h;
import ap.t;
import ap.u;
import ap.v;
import ap.z;
import aq.aa;
import aq.am;
import aq.aq;
import aq.p;
import as.d;
import as.j;
import be.ak;
import be.bs;
import be.cc;
import be.cf;
import be.cm;
import be.dc;
import be.ds;
import be.ee;
import be.r;
import bf.aw;
import bf.bf;
import bf.cb;
import bf.dx;
import bf.fy;
import bh.l;
import bi.b;
import bi.e;
import bi.i;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class s
extends t {
    private static final Logger y = Logger.getLogger(s.class.getName());
    private boolean z = false;
    private boolean A = false;
    private final Object B = new Object();
    private boolean C = false;

    public s(l template) {
        super(template);
    }

    @Override
    public void Y_() {
        if (!this.w && this.m != null) {
            this.e(1, 40);
            this.a(true);
        }
        if (this.ea() * 100 / this.ew() < 40) {
            this.e(0, 50);
        }
    }

    @Override
    public void a(boolean isChangeShape) {
        if (!this.U_().X()) {
            return;
        }
        boolean updateShape = false;
        if (!isChangeShape) {
            this.a(this.U_().A());
            this.cr(this.U_().p());
            this.cw(this.G());
            updateShape = true;
        } else if (!this.z && this.m instanceof u) {
            this.v(300);
            u tpc = (u)this.m;
            this.e(tpc.et());
            this.a(tpc.et());
            this.cr(tpc.fa());
            this.cw(tpc.aB());
            if (tpc.aB() != 6671 && tpc.aB() != 48) {
                this.cq(4);
            } else {
                this.cq(11);
            }
            this.z = true;
            updateShape = true;
        }
        this.m(am.c.a().a(this.fe(), this.eY()));
        this.n(am.c.a().a(this.fe(), this.eY() + 1));
        if (!updateShape) {
            return;
        }
        this.b(new r(this.fr(), this.T()));
        this.b(new cf(this.fr(), this.fe(), this.fa(), this.eY()));
    }

    @Override
    public void b(u perceivedFrom) {
        if (this.ea() <= 0 && (this.fe() == 7864 || this.fe() == 7869)) {
            return;
        }
        perceivedFrom.a(new cc(this));
        perceivedFrom.c((aa)this);
        this.Z_();
    }

    @Override
    public void c() {
        u lastTarget = null;
        if (this.m instanceof u) {
            lastTarget = (u)this.m;
            this.s();
        }
        u targetPlayer = null;
        for (u pc : aq.a().f(this)) {
            if (pc == lastTarget || pc.ea() <= 0 || pc.eX() || pc.l() || pc.bN()) continue;
            if (this.z() == 45600 && (pc.x() || pc.C() || pc.fe() != pc.aB())) {
                targetPlayer = pc;
                break;
            }
            if (this.U_().aa() < 0 && pc.Q() >= 1 || this.U_().aa() > 0 && pc.Q() <= -1 || pc.fe() == 6034 && this.U_().aa() < 0 || pc.fe() == 6035 && this.U_().aa() > 0 || pc.fe() == 6035 && this.U_().b() == 46070 || pc.fe() == 6035 && this.U_().b() == 46072) continue;
            if (!this.V_() && !this.W() && this.U_().F() < 0 && this.U_().G() < 0) {
                if (pc.fa() >= -1000) continue;
                targetPlayer = pc;
                break;
            }
            if (pc.ff() && !this.V()) continue;
            if (pc.bB(67)) {
                if (this.W()) {
                    targetPlayer = pc;
                    break;
                }
            } else if (this.V_()) {
                targetPlayer = pc;
                break;
            }
            if (pc.fe() != this.U_().F() && pc.fe() != this.U_().G()) continue;
            targetPlayer = pc;
            break;
        }
        if (targetPlayer != null) {
            this.n.a(targetPlayer, 0);
            this.m = targetPlayer;
        }
    }

    @Override
    public void a(aq.f cha) {
        if (cha == null) {
            return;
        }
        if (this.n.b()) {
            this.n.a(cha, 0);
            this.d();
        }
    }

    @Override
    public void Z_() {
        if (this.ae()) {
            return;
        }
        if (!this.A) {
            ao.v.a().a((t)this, this.y());
            this.y().f();
            this.A = true;
        }
        this.w = false;
        this.q();
    }

    @Override
    public void a(u pc, int skillId) {
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        super.a(pc, skillId);
    }

    @Override
    public void a(aq.f attacker, int mpDamage) {
        int newMp;
        if (mpDamage <= 0 || this.eX()) {
            return;
        }
        this.c(attacker, mpDamage);
        this.Z_();
        if (attacker instanceof u) {
            this.c((u)attacker, this.U_().D());
        }
        if ((newMp = this.eb() - mpDamage) < 0) {
            newMp = 0;
        }
        this.i_(newMp);
    }

    @Override
    public void b(aq.f attacker, int damage) {
        if (this.ea() <= 0) {
            if (!this.eX()) {
                this.cq(8);
                bi.e.a().a(new a(attacker));
            }
            return;
        }
        if (this.ac() == 1 || this.ac() == 2) {
            return;
        }
        if (damage >= 0) {
            if (!(attacker instanceof h)) {
                this.c(attacker, damage);
            }
            this.bz(66);
            this.bz(153);
        }
        this.Z_();
        if (attacker instanceof u) {
            if (damage > 0) {
                ((u)attacker).a(this);
            }
            this.c((u)attacker, this.U_().D());
        }
        if (this.z() >= 97044 && this.z() <= 97046 && attacker.bB(4011)) {
            damage = (int)((double)damage * 1.5);
        } else if (this.z() >= 97094 && this.z() <= 97096 && attacker.bB(4012)) {
            damage = (int)((double)damage * 1.5);
        }
        int newHp = this.ea() - damage;
        if (newHp <= 0) {
            int transformId = this.U_().ab();
            if (transformId == -1 || this.fp() == 1931 && bi.i.a(1000) > 3) {
                bi.e.a().a(new a(attacker));
            } else {
                this.g_(transformId);
            }
            return;
        }
        this.a(newHp);
        this.l();
    }

    @Override
    public synchronized void a(int i2) {
        int currentHp = i2;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
        if (this.ew() > this.ea()) {
            this.u();
        }
    }

    @Override
    public synchronized void i_(int i2) {
        int currentMp = i2;
        if (currentMp >= this.ex()) {
            currentMp = this.ex();
        }
        this.by(currentMp);
        if (this.ex() > this.eb()) {
            this.w();
        }
    }

    private void f(aq.f lastAttacker) {
        if (lastAttacker == null) {
            return;
        }
        if (lastAttacker instanceof h && !this.n.b()) {
            lastAttacker = this.n.c();
        }
        u pc = null;
        if (lastAttacker instanceof u) {
            pc = (u)lastAttacker;
        } else if (lastAttacker instanceof v) {
            pc = (u)((v)lastAttacker).M();
        } else if (lastAttacker instanceof z) {
            pc = (u)((z)lastAttacker).M();
        }
        if (pc == null) {
            return;
        }
        bi.b.a(pc, this, this.n);
        this.k();
        this.f(pc);
        this.g(pc);
    }

    private void k() {
        block8: {
            int npcId;
            block9: {
                block7: {
                    npcId = this.U_().b();
                    if (npcId != 45640 || npcId == 45640 && this.fe() == 2332) {
                        aq.p.a().a(this);
                        ao.v.a().a((t)this, this.l);
                    }
                    if (npcId != 190026) break block7;
                    for (aa obj : aq.a().b(this.fp()).values()) {
                        u tpc;
                        if (!(obj instanceof u) || !this.l.a(tpc = (u)obj) || tpc.eX()) continue;
                        ah.a(tpc, 640305, 1, this.T());
                    }
                    break block8;
                }
                if (npcId != 97008 && npcId != 97046 && npcId != 97096) break block9;
                int coinid = 640639;
                if (npcId == 97046) {
                    coinid = 640640;
                } else if (npcId == 97096) {
                    coinid = 640641;
                }
                for (aa obj : aq.a().b(this.fp()).values()) {
                    u tpc;
                    if (!(obj instanceof u) || !this.l.a(tpc = (u)obj) || tpc.eX()) continue;
                    ah.a(tpc, coinid, 1, this.T());
                }
                break block8;
            }
            if (npcId != 46123 && npcId != 46124 && npcId != 190577) break block8;
            for (aa obj : aq.a().b(this.fp()).values()) {
                u tpc;
                if (!(obj instanceof u) || !this.l.a(tpc = (u)obj)) continue;
                ah.a(tpc, 40308, 5000000, this.T());
                ah.a(tpc, 640621, 50000, this.T());
            }
        }
    }

    private void f(u pc) {
        int karma = this.P();
        if (karma != 0) {
            int karmaSign = Integer.signum(karma);
            int pcKarmaLevel = pc.Q();
            int pcKarmaLevelSign = Integer.signum(pcKarmaLevel);
            if (pcKarmaLevelSign != 0 && karmaSign != pcKarmaLevelSign) {
                karma *= 5;
            }
            pc.B((int)((double)karma * l1j.server.a.D));
        }
    }

    private void g(u pc) {
        int rnd;
        if (pc.bB(5006)) {
            if (pc.dW() < 30) {
                pc.bv(pc.dW() + 1);
            }
            pc.a(new cm(204, pc));
        } else if (pc.cC() > 0 && (rnd = bi.i.a(1000)) < 10) {
            pc.bv(1);
            pc.j(5006, 50000);
            pc.a(new cm(204, pc));
        }
        if (this.U_().an() > 0) {
            int index = this.U_().an() - 1;
            int count = pc.dQ()[index] + 1;
            int stage = am.d.a().a(index, pc.dQ()[index]);
            pc.dQ()[index] = count;
            pc.a(new dc(567, this.U_().an(), count));
            int new_stage = am.d.a().a(index, count);
            if (new_stage > stage) {
                int idx = index * 3 + new_stage - 1;
                pc.a(new dc(568, idx + 1, pc.fr()));
            }
            if (pc.dY() != null) {
                int i2 = 0;
                while (i2 < pc.dY().length) {
                    int[] data = pc.dY()[i2];
                    if (data[0] == this.U_().an()) {
                        if (data[2] >= data[1]) break;
                        data[2] = data[2] + 1;
                        pc.a(new dc(813, i2 / 3, i2 % 3, data[2]));
                        if (data[2] < data[1]) break;
                        boolean isCompleted = true;
                        int check = i2 / 3;
                        int j2 = 0;
                        while (j2 < 3) {
                            int[] data2 = pc.dY()[check * 3 + j2];
                            if (data2[2] < data2[1]) {
                                isCompleted = false;
                            }
                            ++j2;
                        }
                        if (!isCompleted) break;
                        pc.dY()[check * 3][3] = 3;
                        pc.a(new dc(814, i2 / 3, 3));
                        break;
                    }
                    ++i2;
                }
            }
        }
        for (bh.s qn : pc.dS().values()) {
            int i3 = 0;
            while (i3 < qn.p().length) {
                if (qn.p()[i3] == this.z()) {
                    qn.b(i3);
                }
                ++i3;
            }
        }
    }

    public void d(u pc) {
        if (pc.bB(60) || pc.bB(97)) {
            return;
        }
        if (this.ac() == 1) {
            if (this.ea() == this.ew() && pc.fu().c(this.fu()) <= 2) {
                this.e(pc);
            }
        } else if (this.ac() == 2) {
            if (this.ea() == this.ew()) {
                if (pc.fu().c(this.fu()) <= 1) {
                    this.e(pc);
                }
            } else {
                this.r();
            }
        } else if (this.ac() == 3 && this.ea() < this.ew()) {
            this.e(pc);
        }
    }

    public void e(u pc) {
        int hiddenType = this.ac();
        if (hiddenType == 1) {
            if (am.c.a().c(this.fe())) {
                this.b(new ak(this.fr(), 11));
            } else {
                this.b(new ak(this.fr(), 4));
            }
        } else if (hiddenType == 2) {
            this.b(new ak(this.fr(), 45));
        } else if (hiddenType == 3) {
            this.b(new ak(this.fr(), 11));
        }
        this.t(0);
        this.cq(am.c.a().a(this));
        this.b(new be.z(this, this.eY()));
        if (!(pc.bB(60) || pc.bB(97) || pc.l())) {
            this.n.a(pc, 0);
            this.m = pc;
        }
        this.Z_();
        this.a_(2);
    }

    public void b(boolean isSummon) {
        if (this.fe() == 7548 || this.fe() == 7550 || this.fe() == 7552 || this.fe() == 7554 || this.fe() == 7585 || this.fe() == 7591) {
            for (u pc : aq.a().f(this)) {
                if (pc.b((aa)this)) continue;
                pc.c((aa)this);
            }
            this.b(new cc(this));
            this.b(new ak(this.fr(), 11));
        } else if (this.fe() == 7539 || this.fe() == 7557 || this.fe() == 7558 || this.fe() == 7864 || this.fe() == 7869 || this.fe() == 7870 || this.fe() == 8036 || this.fe() == 8054 || this.fe() == 8055) {
            for (u pc : aq.a().f(this)) {
                if (pc.b((aa)this)) continue;
                pc.c((aa)this);
            }
            this.cq(4);
            this.b(new cc(this));
            this.b(new ak(this.fr(), 11));
            this.b(11, 1);
            this.cq(0);
            this.b(new be.z(this, this.eY()));
        } else if (am.c.a().b(this.fe()) && this.ac() != 1) {
            for (u pc : aq.a().f(this)) {
                if (pc.b((aa)this)) continue;
                pc.c((aa)this);
            }
            this.m(isSummon);
            this.cq(11);
            this.b(new cc(this));
            this.b(new ak(this.fr(), 4));
            this.b(4, 1);
            this.cq(0);
            this.b(new be.z(this, this.eY()));
        } else if (this.fe() == 14036 || this.fe() == 14271) {
            this.cq(0);
        } else if (this.fe() == 10071) {
            for (u pc : aq.a().f(this)) {
                if (pc.b((aa)this)) continue;
                pc.c((aa)this);
            }
            this.cq(11);
            this.b(new cc(this));
            this.b(new ak(this.fr(), 4));
            this.b(4, 1);
            this.cq(4);
            this.b(new be.z(this, this.eY()));
        }
        if (isSummon) {
            this.Z_();
        }
    }

    private void l() {
        int rnd;
        int npcid = this.U_().b();
        if (am.c.a().b(this.fe())) {
            int rnd2;
            if (this.ew() / 3 > this.ea() && 2 > (rnd2 = bi.i.a(10))) {
                this.t();
                this.t(1);
                this.b(new ak(this.fr(), 11));
                this.cq(11);
                this.b(new be.z(this, this.eY()));
            }
        } else if (this.fe() == 10071) {
            if (this.ew() / 3 > this.ea() && bi.i.a(100) < 2) {
                this.t();
                this.t(1);
                this.b(new ak(this.fr(), 11));
                this.cq(11);
                this.b(new be.z(this, this.eY()));
            }
        } else if (this.fe() == 7558) {
            if (this.ew() / 3 > this.ea() && bi.i.a(100) < 1) {
                this.t();
                this.t(1);
                this.b(new ak(this.fr(), 20));
                this.cq(20);
                this.b(new be.z(this, this.eY()));
            }
        } else if (am.c.a().a(this.fe())) {
            int rnd3;
            if (this.ew() / 3 > this.ea() && 2 > (rnd3 = bi.i.a(10))) {
                this.t();
                this.t(2);
                this.b(new ak(this.fr(), 44));
            }
        } else if ((npcid == 46107 || npcid == 46108) && this.ew() / 4 > this.ea() && 2 > (rnd = bi.i.a(10))) {
            this.t();
            this.t(1);
            this.b(new ak(this.fr(), 11));
            this.cq(11);
            this.b(new be.z(this, this.eY()));
        }
    }

    public void h() {
        int npcid = this.U_().b();
        if (am.c.a().b(this.fe()) && !this.ar()) {
            if (bi.i.a(100) < 33) {
                this.t(1);
            }
        } else if (am.c.a().c(this.fe())) {
            if (bi.i.a(100) < 33) {
                this.t(1);
                this.cq(4);
                return;
            }
        } else if (am.c.a().a(this.fe())) {
            this.t(2);
        } else if (this.fe() == 6555 || this.fe() == 6555) {
            if (bi.i.a(100) < 33) {
                this.t(1);
            }
        } else if (npcid >= 46125 && npcid <= 46128) {
            this.t(3);
        } else if (am.c.a().d(this.fe())) {
            this.t(3);
        }
        this.cq(am.c.a().a(this));
    }

    public void a(t leader) {
        int npcid = this.U_().b();
        if (leader.ac() == 1) {
            if (am.c.a().b(this.fe())) {
                this.t(1);
            } else {
                if (am.c.a().c(this.fe())) {
                    this.t(1);
                    this.cq(4);
                    return;
                }
                if (npcid == 46107 || npcid == 46108) {
                    this.t(1);
                }
            }
        } else if (leader.ac() == 2) {
            if (am.c.a().a(this.fe())) {
                this.t(2);
            }
        } else if (npcid >= 46125 && npcid <= 46128) {
            this.t(3);
        } else if (am.c.a().d(this.fe())) {
            this.t(3);
        }
        this.cq(am.c.a().a(this));
    }

    @Override
    public void g_(int transformId) {
        super.g_(transformId);
        this.y().g();
        ao.v.a().a((t)this, this.y());
        this.y().f();
    }

    public boolean i() {
        return this.A;
    }

    public void c(boolean isAllocatedDrop) {
        this.A = isAllocatedDrop;
    }

    protected class a
    implements Runnable {
        private final aq.f b;

        public a(aq.f _lastAttacker) {
            this.b = _lastAttacker;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                if (s.this.eX()) {
                    return;
                }
                s.this.X(true);
                s.this.j(true);
                s.this.bx(0);
                s.this.a_(1);
                if (s.this.z() == 190044) {
                    for (aa object : aq.a().b((aa)s.this, -1)) {
                        if (!(object instanceof s)) continue;
                        s mob = (s)object;
                        mob.b(new ak(mob.fr(), 2));
                        mob.b(this.b, 300);
                    }
                    s.this.b(new ee(s.this.fr(), 7771));
                } else if (s.this.z() == 190078) {
                    if (this.b instanceof u && this.b.fp() >= 807 && this.b.fp() <= 812) {
                        am.a((u)this.b, 32769, 32764, this.b.fp() + 1, 5, true);
                    }
                } else if (s.this.z() == 190079) {
                    if (this.b instanceof u) {
                        am.a((u)this.b, 200);
                    }
                } else if (s.this.z() == 46142 && s.this.fp() >= 2101 && s.this.fp() <= 2150) {
                    f door = ao.t.b().a(32852, 32920, s.this.fp());
                    if (door != null) {
                        door.f();
                    }
                    if (this.b instanceof u) {
                        ((u)this.b).a(new cm(84, 2, "$13383"));
                    }
                } else if (s.this.z() >= 190750 && s.this.z() <= 190762) {
                    if (this.b instanceof u && bi.i.a(1000) < 3) {
                        am.a((u)this.b, 33392, 32346, 4, 0, true);
                    }
                } else if (s.this.z() == 97006 || s.this.z() == 97007 || s.this.z() == 97044 || s.this.z() == 97045 || s.this.z() == 97094 || s.this.z() == 97095) {
                    Object door = s.this.B;
                    synchronized (door) {
                        if (!s.this.C) {
                            bg.a(s.this.z() + 1, s.this, 30000L);
                            s.this.C = true;
                        }
                    }
                } else if (s.this.z() == 97008 || s.this.z() == 97046 || s.this.z() == 97096) {
                    int skillid = 4011;
                    if (s.this.z() == 97046) {
                        skillid = 4012;
                    } else if (s.this.z() == 97096) {
                        skillid = 4077;
                    }
                    for (u pc : aq.a().c(s.this, 100)) {
                        Timestamp limit = new Timestamp(System.currentTimeMillis() + 259200000L);
                        pc.a(new ee(pc.fr(), 7783));
                        pc.b(new ee(pc.fr(), 7783));
                        aq.s.a(pc, skillid, 259200, limit);
                    }
                    as.d.a().a(s.this);
                } else if (s.this.z() >= 190574 && s.this.z() <= 190576) {
                    int camp = 0;
                    if (this.b instanceof u) {
                        u pc;
                        pc = (u)this.b;
                        camp = pc.dX();
                    }
                    as.j.a().a(camp);
                } else if (s.this.z() >= 190237 && s.this.z() <= 190242 && this.b instanceof u) {
                    switch (s.this.z()) {
                        case 190237: {
                            u pc = (u)this.b;
                            if (pc.bB(1038)) {
                                pc.a(new ds(79));
                                break;
                            }
                            pc.j(1027, 300000);
                            pc.a(new bs(pc.fr(), 8));
                            pc.b(new bs(pc.fr(), 8));
                            pc.a(new ee(pc.fr(), 8910));
                            pc.b(new ee(pc.fr(), 8910));
                            pc.a(new ds(1065));
                            pc.a(new cm(60, 300));
                            break;
                        }
                        case 190238: {
                            new bf().a(this.b, 0);
                            break;
                        }
                        case 190239: {
                            new aw().a(this.b, 0);
                            break;
                        }
                        case 190240: {
                            new fy().a(this.b, 0);
                            break;
                        }
                        case 190241: {
                            new cb().a(this.b, 0);
                            break;
                        }
                        case 190242: {
                            new dx().a(this.b, 0);
                        }
                    }
                }
                if (s.this.an() > 0 && s.this.ao() > 0) {
                    int step = -1;
                    while (step++ < 30) {
                        if (s.this.fs() == s.this.an() && s.this.ft() == s.this.ao()) break;
                        s.this.g(s.this.a(s.this.an(), s.this.ao()));
                        try {
                            Thread.sleep(s.this.f(s.this.N(), 0) * 3 / 4);
                        }
                        catch (InterruptedException e2) {
                            y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                        }
                    }
                }
                s.this.cq(8);
                s.this.fq().a(s.this.fu(), true);
                s.this.b(new ak(s.this.fr(), 8));
                s.this.a(false);
                s.this.f(this.b);
                s.this.j(false);
                s.this.k(0);
                s.this.A(0);
                s.this.A();
            }
            catch (Exception e3) {
                y.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
            }
        }
    }
}

