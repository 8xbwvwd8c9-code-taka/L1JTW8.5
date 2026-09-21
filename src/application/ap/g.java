/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.ar;
import ap.s;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.dh;
import bh.l;
import bi.h;

public class g
extends s {
    public g(l template) {
        super(template);
    }

    @Override
    protected int a(int x2, int y2) {
        double distance = this.fu().b(new h(x2, y2));
        if (this.bB(40) && distance >= 2.0) {
            return -1;
        }
        if (distance > 30.0) {
            return -1;
        }
        if (distance > (double)h) {
            return this.a(this.fs(), this.ft(), this.fp(), this.h(x2, y2));
        }
        int dir = this.d(x2, y2);
        if (dir == -1) {
            dir = this.h(x2, y2);
        }
        return dir;
    }

    @Override
    public void b() {
        this.w = true;
        this.i.clear();
        this.j = null;
        f target = this.m;
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
            int dir = this.a(target.fs(), target.ft());
            if (dir == -1) {
                this.v(3000);
                for (u pc : aq.a().f(this)) {
                    pc.a(new dh(this));
                    pc.d(this);
                }
                this.cG(this.X());
                this.cH(this.Y());
                this.ct(this.fb());
            } else {
                this.g(dir);
                this.v(this.f(this.N(), 0));
            }
        }
    }

    @Override
    public void c() {
        for (aa obj : aq.a().e(this)) {
            t npc;
            if (!(obj instanceof t) || (npc = (t)obj).z() != 190114) continue;
            if (this.ew() > 2000) {
                this.n.a(npc, 1);
            } else {
                this.n.a(npc, this.ew());
            }
            this.m = npc;
            return;
        }
    }

    @Override
    public void d() {
        if (this.m == null || this.m.fp() != this.fp() || this.m.eX() || this.m.ff() && !this.V() && !this.n.a(this.m) || this.m.f(this) > 30) {
            this.s();
            if (!this.n.b()) {
                this.m = this.n.c();
                this.d();
            }
        }
    }
}

