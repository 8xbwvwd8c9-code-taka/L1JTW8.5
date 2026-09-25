/*
 * Decompiled with CFR 0.152.
 */
package ap;

import am.c;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import be.ak;
import be.an;
import be.dh;
import be.h;
import bh.l;

public class f
extends t {
    private final bh.f y;
    private int z = 0;
    private int A = 0;
    private int B = 0;
    private int C = 0;
    private int D = 29;
    private int E = 0;
    private final int F;
    private final int G;

    public f(l template, int doorId, bh.f gfx, aq.u loc, int hp, int keeper, boolean isOpening) {
        super(template);
        this.f_(doorId);
        this.bG(hp);
        this.a(hp);
        this.a(loc);
        this.q(loc.f());
        this.r(loc.g());
        this.c(gfx.b());
        this.F = gfx.e();
        this.G = gfx.f();
        int baseLoc = gfx.b() == 0 ? loc.f() : loc.g();
        this.d(baseLoc + gfx.d());
        this.e(baseLoc + gfx.c());
        this.f(keeper);
        if (isOpening) {
            this.f();
        }
        this.y = gfx;
    }

    public int ab_() {
        return Math.abs(this.y.d()) + Math.abs(this.y.c());
    }

    @Override
    public void a(u pc, int skillId) {
        if (this.ew() == 0) {
            return;
        }
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        super.a(pc, skillId);
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new an(this));
        this.d(perceivedFrom);
    }

    @Override
    public void aa_() {
        this.X(true);
        this.d((u)null);
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
    }

    @Override
    public void b(aq.f attacker, int damage) {
        if (this.ew() == 0) {
            return;
        }
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        int newHp = this.ea() - damage;
        if (newHp <= 0 && !this.eX()) {
            this.av();
            return;
        }
        this.bx(newHp);
        this.au();
    }

    private void au() {
        int newStatus = 0;
        if (this.ew() * 1 / 6 > this.ea()) {
            newStatus = 36;
        } else if (this.ew() * 2 / 6 > this.ea()) {
            newStatus = 35;
        } else if (this.ew() * 3 / 6 > this.ea()) {
            newStatus = 34;
        } else if (this.ew() * 4 / 6 > this.ea()) {
            newStatus = 33;
        } else if (this.ew() * 5 / 6 > this.ea()) {
            newStatus = 32;
        }
        if (this.eY() == newStatus) {
            return;
        }
        this.cq(newStatus);
        this.b(new ak(this.fr(), newStatus));
    }

    @Override
    public void a(int i2) {
        int currentHp = i2;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
    }

    private void av() {
        this.bx(0);
        this.X(true);
        int status = 36;
        if (am.c.a().b(this.fe(), 37)) {
            status = 37;
        }
        this.cq(status);
        this.fq().a(this.fu(), true);
        this.b(new ak(this.fr(), 37));
        this.d((u)null);
    }

    private void d(u pc) {
        int y2;
        int x2;
        int entranceX = this.k();
        int entranceY = this.l();
        int leftEdgeLocation = this.ac_();
        int rightEdgeLocation = this.n();
        int size = rightEdgeLocation - leftEdgeLocation;
        if (size == 0) {
            this.a(pc, entranceX, entranceY);
        } else if (this.j() == 0) {
            x2 = leftEdgeLocation;
            while (x2 <= rightEdgeLocation) {
                this.a(pc, x2, entranceY);
                ++x2;
            }
        } else {
            y2 = leftEdgeLocation;
            while (y2 <= rightEdgeLocation) {
                this.a(pc, entranceX, y2);
                ++y2;
            }
        }
        if (this.j() == 0) {
            x2 = leftEdgeLocation - 1;
            while (x2 <= rightEdgeLocation + 1) {
                this.fq().a(x2, this.ft(), this.aw());
                ++x2;
            }
        } else {
            y2 = leftEdgeLocation - 1;
            while (y2 <= rightEdgeLocation + 1) {
                this.fq().a(this.fs(), y2, this.aw());
                ++y2;
            }
        }
    }

    private boolean aw() {
        return this.eX() || this.o() == 28;
    }

    private void a(u pc, int x2, int y2) {
        this.fq().a(x2, y2, this.aw(), this.j());
        if (pc != null) {
            pc.a(new h(x2, y2, this.j(), this.aw()));
        } else {
            this.b(new h(x2, y2, this.j(), this.aw()));
        }
    }

    public void f() {
        if (!this.eX() && !this.aw()) {
            this.B(28);
            this.b(new ak(this.fr(), 28));
            this.d((u)null);
        }
    }

    public void g() {
        if (!this.eX() && this.aw()) {
            this.B(29);
            this.b(new ak(this.fr(), 29));
            this.d((u)null);
        }
    }

    public void h() {
        if (this.ew() <= 1) {
            return;
        }
        this.X(false);
        this.a(this.ew());
        this.cq(0);
        this.B(28);
        this.g();
    }

    public int i() {
        return this.z;
    }

    public void f_(int i2) {
        this.z = i2;
    }

    public int j() {
        return this.A;
    }

    public void c(int i2) {
        if (i2 != 0 && i2 != 1) {
            throw new IllegalArgumentException();
        }
        this.A = i2;
    }

    public int k() {
        return this.fs() + this.F;
    }

    public int l() {
        return this.ft() + this.G;
    }

    public int ac_() {
        return this.B;
    }

    public void d(int i2) {
        this.B = i2;
    }

    public int n() {
        return this.C;
    }

    public void e(int i2) {
        this.C = i2;
    }

    public int o() {
        return this.D;
    }

    private void B(int newStatus) {
        if (newStatus != 28 && newStatus != 29) {
            throw new IllegalArgumentException();
        }
        this.D = newStatus;
    }

    public int p() {
        return this.E;
    }

    public void f(int i2) {
        this.E = i2;
    }
}

