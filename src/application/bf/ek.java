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

public class ek
extends a {
    private final int a = 163;
    private final v b = be.a().a(163);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(163)) {
            _target.ck(6);
            _target.cm(3);
        }
        _target.j(163, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new eb(162, timeSecs, tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(163)) {
            _user.ck(6);
            _user.cm(3);
        }
        _user.j(163, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new eb(162, this.b.v(), pc));
        }
    }

    @Override
    public void a(f cha) {
        cha.ck(-6);
        cha.cm(-3);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new eb(162, 0, pc));
        }
    }
}

