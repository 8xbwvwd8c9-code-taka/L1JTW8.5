/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.ar;
import ap.aa;
import ap.f;
import ap.h;
import ap.s;
import ap.u;
import aq.aq;
import aq.e;
import be.bo;
import be.dc;
import bh.l;

public class a
extends s {
    private int y = 0;
    private aa z = null;

    @Override
    public boolean a() {
        this.y = aq.e.a((aq.f)this);
        for (aq.aa l1object : aq.a().b()) {
            aa tower;
            if (!(l1object instanceof aa) || !aq.e.a(this.y, tower = (aa)l1object)) continue;
            this.z = tower;
            break;
        }
        if (this.z == null) {
            return true;
        }
        this.m = this.z;
        return false;
    }

    public a(l template) {
        super(template);
        h = 100;
        this.q();
    }

    @Override
    public void b() {
        this.w = true;
        aq.f target = this.m;
        if (aq.e.a((aq.f)this) <= 0) {
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
            if (this.m instanceof u && this.e((aq.aa)this.m) >= 5.0) {
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
    protected int a(int x2, int y2) {
        double distance = this.fu().b(new bi.h(x2, y2));
        if (this.bB(40) && distance >= 2.0) {
            return -1;
        }
        if (distance > 100.0) {
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

    @Override
    public void c() {
        aq.f lastTarget = null;
        f findTarget = null;
        if (this.m != null && (this.m instanceof u || this.m instanceof f)) {
            lastTarget = this.m;
            this.s();
        }
        for (aq.aa obj : aq.a().e(this)) {
            f door;
            if (!(obj instanceof f) || (door = (f)obj) == lastTarget || door.o() == 28 || door.eX() || door.ab_() < 2) continue;
            findTarget = door;
        }
        if (findTarget != null) {
            this.n.a(findTarget, 0);
            this.m = findTarget;
        } else {
            this.m = this.z;
        }
    }

    @Override
    public void a(aq.f attacker, int mpDamage) {
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
    public void b(aq.f attacker, int damage) {
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
        perceivedFrom.c((aq.aa)this);
        perceivedFrom.a(new bo(this));
        perceivedFrom.a(new dc(65, this.fr(), 1));
        this.Z_();
    }
}

