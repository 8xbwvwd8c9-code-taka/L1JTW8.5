/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ai;
import bf.a;
import bh.v;

public class cw
extends a {
    private final int a = 110;
    private final v b = be.a().a(110);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(110)) {
            _target.bR(3);
        }
        _target.j(110, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new ai(tpc, 2, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(110)) {
            _user.bR(3);
        }
        _user.j(110, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new ai(pc, 2, this.b.v()));
        }
        this.b(_user, 294);
    }

    @Override
    public void a(f cha) {
        cha.bR(-3);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ai(pc, 2, 0));
        }
    }
}

