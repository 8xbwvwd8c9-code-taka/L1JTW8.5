/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.ar;
import ap.h;
import ap.s;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.bo;
import be.dc;
import bh.l;
import bi.e;

public class r
extends s {
    @Override
    public boolean a() {
        return aq.a().f(this).isEmpty();
    }

    public r(l template) {
        super(template);
        this.q();
    }

    @Override
    public void b() {
        this.w = true;
        this.i.clear();
        this.j = null;
        f target = this.m;
        if (this.fu().c(new bi.h(this.X(), this.Y())) > 15) {
            this.a(this.X(), this.Y(), 1);
            this.s();
            return;
        }
        if (this.c(target.fs(), target.ft(), this.C())) {
            int sleepTime = ar.a().a(this, target);
            if (sleepTime > 0) {
                this.v(this.f(sleepTime, 2));
            } else {
                this.ct(this.h(target.fs(), target.ft()));
                this.b(target);
            }
        } else {
            int sleepTime = ar.a().a(this, target);
            if (sleepTime > 0) {
                this.v(this.f(sleepTime, 2));
                return;
            }
            if (this.N() <= 0) {
                this.s();
                return;
            }
            int dir = this.a(target.fs(), target.ft());
            if (dir == -1) {
                this.c();
            } else {
                this.g(dir);
                this.v(this.f(this.N(), 0));
            }
        }
    }

    @Override
    public void c() {
        u lastTarget = null;
        u targetPlayer = null;
        if (this.m != null && this.m instanceof u) {
            lastTarget = (u)this.m;
            this.s();
        }
        for (u pc : aq.a().f(this)) {
            if (pc == lastTarget || pc.ea() <= 0 || pc.eX() || pc.bN()) continue;
            targetPlayer = pc;
        }
        if (targetPlayer != null) {
            this.n.a(targetPlayer, 0);
            this.m = targetPlayer;
        }
    }

    @Override
    public void a(f attacker, int mpDamage) {
        if (mpDamage > 0 && !this.eX()) {
            this.c(attacker, mpDamage);
            this.Z_();
            int newMp = this.eb() - mpDamage;
            if (newMp < 0) {
                newMp = 0;
            }
            this.i_(newMp);
        }
    }

    @Override
    public void b(f attacker, int damage) {
        if (this.ea() > 0 && !this.eX()) {
            int newHp;
            if (this.ac() == 1 || this.ac() == 2) {
                return;
            }
            if (damage >= 0 && !(attacker instanceof h)) {
                this.c(attacker, damage);
            }
            if (damage > 0) {
                this.bz(66);
                this.bz(153);
            }
            this.Z_();
            if (attacker instanceof u && damage > 0) {
                u player = (u)attacker;
                player.a(this);
            }
            if ((newHp = this.ea() - damage) <= 0 && !this.eX()) {
                int transformId = this.U_().ab();
                if (transformId == -1) {
                    s.a death = new s.a(this, attacker);
                    bi.e.a().a(death);
                } else {
                    this.g_(transformId);
                }
            }
            if (newHp > 0) {
                this.a(newHp);
            }
        } else if (!this.eX()) {
            this.X(true);
            this.cq(8);
            s.a death = new s.a(this, attacker);
            bi.e.a().a(death);
        }
    }

    @Override
    public void a(u pc) {
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new bo(this));
        perceivedFrom.a(new dc(65, this.fr(), 2));
        this.Z_();
    }
}

