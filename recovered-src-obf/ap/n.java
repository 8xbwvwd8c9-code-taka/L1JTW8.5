/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ap.h;
import ap.t;
import ap.u;
import aq.aq;
import aq.f;
import be.ak;
import bh.l;
import bi.e;

public class n
extends t {
    @Override
    public void c() {
        u targetPlayer = null;
        for (u pc : aq.a().f(this)) {
            if (pc.ea() <= 0 || pc.eX() || pc.l() || pc.bN() || pc.ff() && !this.V() || !pc.S()) continue;
            targetPlayer = pc;
            break;
        }
        if (targetPlayer != null) {
            this.n.a(targetPlayer, 0);
            this.m = targetPlayer;
        }
    }

    public void d(u targetPlayer) {
        if (targetPlayer != null) {
            this.n.a(targetPlayer, 0);
            this.m = targetPlayer;
        }
    }

    @Override
    public boolean a() {
        if (this.fu().c(new bi.h(this.X(), this.Y())) > 0) {
            int dir = this.a(this.X(), this.Y());
            if (dir != -1) {
                this.g(dir);
                this.v(this.f(this.N(), 0));
            } else {
                this.a(this.X(), this.Y(), 1);
            }
        } else if (aq.a().f(this).isEmpty()) {
            return true;
        }
        return false;
    }

    public n(l template) {
        super(template);
    }

    @Override
    public void Z_() {
        if (this.ae()) {
            return;
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
    public void a(f cha) {
        if (cha != null && this.n.b()) {
            this.n.a(cha, 0);
            this.d();
        }
    }

    @Override
    public void b(f attacker, int damage) {
        if (this.ea() == 0 && !this.eX()) {
            return;
        }
        if (this.ea() > 0 && !this.eX()) {
            int newHp;
            if (damage >= 0 && !(attacker instanceof h)) {
                this.c(attacker, damage);
            }
            if (damage > 0) {
                this.bz(66);
                this.bz(153);
            }
            this.Z_();
            if (attacker instanceof u && damage > 0) {
                u pc = (u)attacker;
                pc.a(this);
                this.c(pc, this.U_().D());
            }
            if ((newHp = this.ea() - damage) <= 0 && !this.eX()) {
                this.bx(0);
                this.X(true);
                this.cq(8);
                bi.e.a().a(new a());
            }
            if (newHp > 0) {
                this.a(newHp);
            }
        } else if (!this.eX()) {
            this.X(true);
            this.cq(8);
            bi.e.a().a(new a());
        }
    }

    @Override
    public void a(int i2) {
        int currentHp = i2;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
        if (this.ew() > this.ea()) {
            this.u();
        }
    }

    private class a
    implements Runnable {
        private a() {
        }

        @Override
        public void run() {
            n.this.j(true);
            n.this.bx(0);
            n.this.X(true);
            n.this.cq(8);
            n.this.fq().a(n.this.fu(), true);
            n.this.b(new ak(n.this.fr(), 8));
            n.this.a_(1);
            n.this.j(false);
            n.this.t();
            n.this.A();
        }
    }
}

