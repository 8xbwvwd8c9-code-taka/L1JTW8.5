/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.s;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.ee;
import bh.v;
import bi.e;
import bi.i;

public class gm
extends bf.a {
    private final int a = 229;
    private final v b = be.a().a(229);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        target.j(229, this.b.v() * 1000);
        this.b(_user, target, this.b, 1);
        new a(_user, target, this.b.v(), this.b.u()).a();
    }

    @Override
    public void a(f cha) {
    }

    private class a
    extends Thread {
        private final f b;
        private final f c;
        private final int d;
        private final int e;

        public a(f _attacker, f _target, int _spanTime, int _gfxid) {
            this.b = _target;
            this.d = _spanTime;
            this.c = _attacker;
            this.e = _gfxid;
        }

        @Override
        public void run() {
            int dmg = this.c.ev() * 2 / this.d;
            int i2 = 0;
            while (i2 < this.d) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e2) {
                    return;
                }
                if (!this.b.bB(229)) {
                    return;
                }
                if (this.b.eX()) {
                    return;
                }
                dmg += i.a(10);
                if (this.b instanceof u) {
                    u tpc = (u)this.b;
                    tpc.a(this.c, (double)dmg, false);
                    tpc.a(new ee(tpc.fr(), this.e));
                } else if (this.b instanceof s) {
                    s mob = (s)this.b;
                    mob.b(this.c, dmg);
                }
                this.b.b(new ee(this.b.fr(), this.e));
                ++i2;
            }
        }

        public void a() {
            bi.e.a().a(this);
        }
    }
}

