/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ck;
import bf.a;
import bh.v;

public class gd
extends a {
    private final int a = 216;
    private final v b = be.a().a(216);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(216)) {
            _target.bN(1);
            _target.bP(1);
            _target.bR(1);
            _target.bX(1);
            _target.bV(1);
        }
        _target.j(216, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new ck(tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(216)) {
            _user.bN(1);
            _user.bP(1);
            _user.bR(1);
            _user.bX(1);
            _user.bV(1);
        }
        _user.j(216, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u tpc = (u)_user;
            tpc.a(new ck(tpc));
        }
    }

    @Override
    public void a(f cha) {
        cha.bN(-1);
        cha.bP(-1);
        cha.bR(-1);
        cha.bX(-1);
        cha.bV(-1);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ck(pc));
        }
    }
}

