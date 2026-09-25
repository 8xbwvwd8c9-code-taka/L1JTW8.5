/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.do;
import be.ed;
import bf.a;
import bh.v;

public class cl
extends a {
    private final int a = 99;
    private final v b = be.a().a(99);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(99)) {
            _target.co(5);
        }
        _target.j(99, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new do(tpc));
            tpc.a(new ed(3, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.a(_user, this.b);
        this.b(_user, this.b);
        if (!_user.bB(99)) {
            _user.co(5);
        }
        _user.j(99, this.b.v() * 1000);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new do(pc));
            pc.a(new ed(3, this.b.v()));
        }
    }

    @Override
    public void a(f cha) {
        cha.co(-5);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new do(pc));
            pc.a(new ed(3, 0));
        }
    }
}

