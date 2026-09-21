/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.br;
import be.ef;
import bf.a;
import bh.v;

public class d
extends a {
    private final int a = 2;
    private final v b = be.a().a(2);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(2, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new br(tpc.fr(), 14));
            tpc.a(new ef(145));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        _user.j(2, this.b.v() * 1000);
        this.a(_user, this.b);
        this.b(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new br(pc.fr(), 14));
            pc.a(new ef(145));
        }
        _user.b(new br(_user.fr(), 14));
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u && !cha.ff()) {
            u pc = (u)cha;
            pc.fg();
        }
    }
}

