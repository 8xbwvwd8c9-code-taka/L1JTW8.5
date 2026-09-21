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

public class dv
extends a {
    private final int a = 148;
    private final v b = be.a().a(148);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(148)) {
            _target.ck(4);
        }
        _target.j(148, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new eb(147, timeSecs, tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(148)) {
            _user.ck(4);
        }
        _user.j(148, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u tpc = (u)_user;
            tpc.a(new eb(147, this.b.v(), tpc));
        }
    }

    @Override
    public void a(f cha) {
        cha.ck(-4);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new eb(147, 0, pc));
        }
    }
}

