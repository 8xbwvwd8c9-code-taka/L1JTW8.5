/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ci;
import bf.a;
import bh.v;

public class dr
extends a {
    private final int a = 138;
    private final v b = be.a().a(138);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(138)) {
            _target.bY(10);
            _target.bZ(10);
            _target.ca(10);
            _target.cb(10);
        }
        _target.j(138, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new ci(tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(138)) {
            _user.bY(10);
            _user.bZ(10);
            _user.ca(10);
            _user.cb(10);
        }
        _user.j(138, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new ci(pc));
        }
    }

    @Override
    public void a(f cha) {
        cha.bY(-10);
        cha.bZ(-10);
        cha.ca(-10);
        cha.cb(-10);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ci(pc));
        }
    }
}

