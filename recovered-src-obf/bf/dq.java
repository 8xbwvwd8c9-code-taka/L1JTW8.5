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

public class dq
extends a {
    private final int a = 137;
    private final v b = be.a().a(137);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(137)) {
            _target.bX(3);
        }
        _target.j(137, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.Y();
            tpc.a(new do(tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(137)) {
            _user.bX(3);
        }
        _user.j(137, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.Y();
            pc.a(new do(pc));
        }
    }

    @Override
    public void a(f cha) {
        cha.bX(-3);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.Y();
            pc.a(new do(pc));
        }
    }
}

