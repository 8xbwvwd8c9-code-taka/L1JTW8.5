/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.eb;
import bf.a;
import bh.v;

public class ed
extends a {
    private final int a = 156;
    private final v b = be.a().a(156);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(156, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.cn(2);
            tpc.cl(3);
            tpc.a(new eb(155, timeSecs, tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (pc.q()) {
                for (u menber : pc.aL().c()) {
                    if (menber.eX() || menber.ff() || !menber.fu().e(pc.fu())) continue;
                    menber.j(156, this.b.v() * 1000);
                    this.a((f)menber, this.b);
                    menber.cn(2);
                    menber.cl(3);
                    menber.a(new eb(155, this.b.v(), pc));
                }
            } else {
                pc.j(156, this.b.v() * 1000);
                this.a((f)pc, this.b);
                pc.cn(2);
                pc.cl(3);
                pc.a(new eb(155, this.b.v(), pc));
            }
        }
    }

    @Override
    public void a(f cha) {
        cha.cn(-2);
        cha.cl(-3);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new eb(155, 0, pc));
        }
    }
}

