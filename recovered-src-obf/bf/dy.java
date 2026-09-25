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

public class dy
extends a {
    private final int a = 151;
    private final v b = be.a().a(151);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(151)) {
            _target.bL(-6);
        }
        _target.j(151, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new ed(6, timeSecs));
            tpc.a(new ci(tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(151)) {
            _user.bL(-6);
        }
        _user.j(151, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u tpc = (u)_user;
            tpc.a(new ed(6, this.b.v()));
            tpc.a(new ci(tpc));
        }
    }

    @Override
    public void a(f cha) {
        cha.bL(6);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ed(6, 0));
            pc.a(new ci(pc));
        }
    }
}

