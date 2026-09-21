/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.do;
import bf.a;
import bh.v;

public class dj
extends a {
    private final int a = 129;
    private final v b = be.a().a(129);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(129)) {
            _target.co(10);
        }
        _target.j(129, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new do(tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(129)) {
            _user.co(10);
        }
        _user.j(129, this.b.v() * 1000);
        this.a(_user, this.b);
        this.b(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new do(pc));
        }
    }

    @Override
    public void a(f cha) {
        cha.co(-10);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new do(pc));
        }
    }
}

