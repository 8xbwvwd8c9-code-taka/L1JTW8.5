/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import bf.a;
import bh.v;

public class ft
extends a {
    private final int a = 206;
    private final v b = be.a().a(206);

    @Override
    public void a(f _target, int timeSecs) {
        u tpc;
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (_target instanceof u && !(tpc = (u)_target).bB(206)) {
            tpc.d(4);
        }
        _target.j(206, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (obj instanceof u) {
            u target = (u)obj;
            if (!target.bB(206)) {
                target.d(4);
            }
            target.j(206, this.b.v() * 1000);
            this.b(_user, this.b);
            this.a((f)target, this.b);
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.d(-4);
        }
    }
}

