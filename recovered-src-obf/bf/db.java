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

public class db
extends a {
    private final int a = 115;
    private final v b = be.a().a(115);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(115)) {
            _target.bL(-8);
        }
        _target.j(115, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new eb(114, timeSecs, tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(115)) {
            _user.bL(-8);
        }
        _user.j(115, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new eb(114, this.b.v(), pc));
        }
    }

    @Override
    public void a(f cha) {
        cha.bL(8);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new eb(114, 0, pc));
        }
    }
}

