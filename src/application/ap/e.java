/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ai.d;
import am.c;
import ao.ah;
import ap.q;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import be.ak;
import be.am;
import be.ck;
import be.cm;
import be.do;
import be.ea;
import be.ee;
import bh.l;
import bi.i;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class e
extends t {
    private static final Logger y = Logger.getLogger(e.class.getName());
    private static final int z = 1800000;
    private final q A;
    private ScheduledFuture<?> B;
    private int C;
    private int D;

    @Override
    public boolean a() {
        if (this.k != null && !this.k.eX() && this.k.fp() == this.fp()) {
            if (this.fu().c(this.k.fu()) > 2) {
                int dir = this.a(this.k.fs(), this.k.ft());
                this.g(dir);
                this.v(this.f(this.N(), 0));
            } else {
                this.i();
            }
        } else {
            this.e();
            return true;
        }
        return false;
    }

    public q X_() {
        return this.A;
    }

    public e(l template, u pc, q item) {
        super(template);
        this.cF(ai.d.a().c());
        this.A = item;
        this.e_(this.A.N());
        this.d_(this.A.fr());
        bi.e.a().a(new b(), 1800000L);
        this.a(this.A.bq());
        this.cw(this.A.br());
        this.e(pc);
        this.cG(pc.fs() + bi.i.a(5) - 2);
        this.cH(pc.ft() + bi.i.a(5) - 2);
        this.cE(pc.fp());
        this.ct(5);
        this.s(template.ae());
        this.cu(1);
        this.cv(1);
        aq.a().a(this);
        aq.a().c(this);
        for (u other : aq.a().f(this)) {
            this.b(other);
        }
        pc.b(this);
        if (!this.ae()) {
            this.q();
        }
        pc.cm(this.A.as());
        pc.cn(this.A.at());
        pc.cl(this.A.av());
        pc.bL(-this.A.ad());
        pc.co(this.A.af());
        pc.cp(this.A.ae());
        pc.bH(this.A.ab());
        pc.bJ(this.A.ac());
        pc.bN(this.A.ai());
        pc.bP(this.A.ak());
        pc.bR(this.A.aj());
        pc.bT(this.A.an());
        pc.bV(this.A.al());
        pc.bX(this.A.am());
        pc.ce(this.A.aA());
        pc.cd(this.A.aF());
        pc.ch(this.A.aE());
        pc.ci(this.A.aD());
        pc.cg(this.A.aC());
        pc.cf(this.A.aB());
        pc.C(this.A.by());
        pc.L(this.A.bD());
        pc.M(this.A.bC());
        pc.ac(this.A.bE());
        pc.Y(this.A.aY());
        pc.Z(this.A.ba());
        if (this.A.au() > 0 && this.A.bs() == 0) {
            pc.H(this.A.au());
        }
        if (this.A.ap() > 0 && this.A.bx() == 0) {
            pc.F(this.A.ap());
        }
        if (this.A.bz() > 0) {
            this.B = bi.e.a().a(new a(), 240000L, 240000L);
        }
        pc.aW(this.A.bu());
        pc.aX(this.A.bv());
        pc.aY(this.A.bw());
        pc.u(this.A.bB());
        if (this.A.bA()) {
            pc.E(1);
            pc.V();
            if (pc.fc() != 1) {
                pc.cu(1);
                pc.a(new ea(pc.fr(), 1, -1));
                pc.b(new ea(pc.fr(), 1, -1));
            }
        }
        if (this.A.ae() > 0 || this.A.af() > 0) {
            pc.a(new do(pc));
        }
        this.A.d(true);
        pc.j().b(this.A);
    }

    public void e() {
        this.b(new ee(this.fr(), 5936));
        if (this.k != null) {
            u pc = (u)this.k;
            pc.cm(-this.A.as());
            pc.cn(-this.A.at());
            pc.cl(-this.A.av());
            pc.bL(this.A.ad());
            pc.co(-this.A.af());
            pc.cp(-this.A.ae());
            pc.bH(-this.A.ab());
            pc.bJ(-this.A.ac());
            pc.bN(-this.A.ai());
            pc.bP(-this.A.ak());
            pc.bR(-this.A.aj());
            pc.bT(-this.A.an());
            pc.bV(-this.A.al());
            pc.bX(-this.A.am());
            pc.ce(-this.A.aA());
            pc.cd(-this.A.aF());
            pc.ch(-this.A.aE());
            pc.ci(-this.A.aD());
            pc.cg(-this.A.aC());
            pc.cf(-this.A.aB());
            pc.C(-this.A.by());
            pc.L(-this.A.bD());
            pc.M(-this.A.bC());
            pc.ac(-this.A.bE());
            pc.Y(-this.A.aY());
            pc.Z(-this.A.ba());
            if (this.A.au() > 0 && this.A.bs() == 0) {
                pc.H(-this.A.au());
            }
            if (this.A.ap() > 0 && this.A.bx() == 0) {
                pc.F(-this.A.ap());
            }
            if (this.B != null) {
                this.B.cancel(true);
            }
            pc.aW(0);
            pc.aX(0);
            pc.aY(0);
            pc.u(false);
            if (this.A.bA()) {
                pc.E(-1);
                if (pc.bU() == 0) {
                    pc.cu(0);
                    pc.a(new ea(pc.fr(), 0, 0));
                    pc.b(new ea(pc.fr(), 0, 0));
                }
            }
            pc.a(new cm(56, 0));
            pc.a(new ck(pc));
            if (this.A.ae() > 0 || this.A.af() > 0) {
                pc.a(new do(pc));
            }
            this.k.el().remove(this.fr());
            if (this.A != null) {
                this.A.d(false);
                pc.j().b(this.A);
            }
        }
        this.aa_();
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new am(this));
    }

    @Override
    public void a(q item) {
    }

    public int f() {
        return this.C;
    }

    public void d_(int i2) {
        this.C = i2;
    }

    public int g() {
        return this.D;
    }

    public void e_(int i2) {
        this.D = i2;
    }

    private void i() {
        int rnd = bi.i.a(100) + 1;
        if (rnd <= 10) {
            int[] actions = new int[]{67, 68, 69, 98, 99};
            int actionCode = rnd <= 5 ? 66 : 67;
            int action = actions[bi.i.a(actions.length)];
            if (am.c.a().b(this.fe(), action)) {
                actionCode = action;
            }
            this.b(new ak(this.fr(), actionCode));
            this.v(this.f(am.c.a().a(this.fe(), actionCode), 0));
        }
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                ah.a((u)e.this.M(), e.this.A.bz(), 1);
            }
            catch (Throwable e2) {
                y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class b
    implements Runnable {
        private b() {
        }

        @Override
        public void run() {
            if (e.this.ah()) {
                return;
            }
            e.this.e();
        }
    }
}

