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

public class dd
extends a {
    private final int a = 117;
    private final v b = be.a().a(117);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(117, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new eb(116, timeSecs, tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.a(_user, this.b);
        this.b(_user, this.b);
        _user.j(117, this.b.v() * 1000);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new eb(116, this.b.v(), pc));
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new eb(116, 0, pc));
        }
    }
}

