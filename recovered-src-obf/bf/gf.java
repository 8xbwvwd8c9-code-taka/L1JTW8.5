/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ck;
import be.cm;
import bf.a;
import bh.v;

public class gf
extends a {
    private final int a = 218;
    private final v b = be.a().a(218);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(218, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new ck(tpc));
            tpc.a(new cm(86, 3, 2, (timeSecs / 8 + 1) / 2));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        u pc;
        _user.j(218, this.b.v() * 1000);
        if (_user instanceof u) {
            pc = (u)_user;
            pc.a(new ck(pc));
        }
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            pc = (u)_user;
            pc.a(new cm(86, 3, 2, (this.b.v() / 8 + 1) / 2));
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cm(86, 3, 2, 0));
        }
    }
}

