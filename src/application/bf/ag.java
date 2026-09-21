/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import bf.a;
import bh.v;

public class ag
extends a {
    private final int a = 32;
    private final v b = be.a().a(32);

    @Override
    public void a(f _target, int timeSecs) {
        u tpc;
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (_target instanceof u && !(tpc = (u)_target).bB(32)) {
            tpc.d(5);
        }
        _target.j(32, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (_user instanceof u) {
            u pc = (u)_user;
            if (!pc.bB(32)) {
                pc.d(5);
            }
            _user.j(32, this.b.v() * 1000);
            this.b(_user, this.b);
            this.a(_user, this.b);
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.d(-5);
        }
    }
}

