/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.dc;
import bf.a;
import bh.v;

public class gi
extends a {
    private final int a = 222;
    private final v b = be.a().a(222);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(222) && _target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new dc(222, this.b.v(), 6, 7456, 7457, 4761, 4761, 4754, 5));
        }
        _target.j(222, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        _user.j(222, this.b.v() * 1000);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new dc(222, this.b.v(), 6, 7456, 7457, 4761, 4761, 4754, 5));
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new dc(110, 222));
        }
    }
}

