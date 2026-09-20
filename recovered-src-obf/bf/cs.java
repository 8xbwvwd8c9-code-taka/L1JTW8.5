/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.cm;
import be.ee;
import bf.a;
import bh.v;

public class cs
extends a {
    private final int a = 106;
    private final v b = be.a().a(106);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(106)) {
            _target.cA(5);
        }
        _target.j(106, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new cm(88, tpc.fk()));
            tpc.a(new cm(21, timeSecs / 16));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(106)) {
            _user.cA(5);
        }
        _user.j(106, this.b.v() * 1000);
        this.b(_user, this.b);
        int castGfxid = this.b.t();
        if (Math.abs(_user.ey()) >= 100) {
            castGfxid = this.b.u();
        }
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new cm(88, pc.fk()));
            pc.a(new ee(pc.fr(), castGfxid));
        }
        _user.b(new ee(_user.fr(), castGfxid));
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.cA(-5);
            pc.a(new cm(88, pc.fk()));
        }
    }
}

