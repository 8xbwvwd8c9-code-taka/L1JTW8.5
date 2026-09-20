/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ci;
import be.ed;
import bf.a;
import bh.v;

public class e
extends a {
    private final int a = 3;
    private final v b = be.a().a(3);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(3)) {
            _target.bL(-2);
        }
        _target.j(3, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new ed(2, timeSecs));
            tpc.a(new ci(tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(3)) {
            _user.bL(-2);
        }
        _user.j(3, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new ed(2, this.b.v()));
            pc.a(new ci(pc));
        }
    }

    @Override
    public void a(f cha) {
        cha.bL(2);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ed(2, 0));
        }
    }
}

