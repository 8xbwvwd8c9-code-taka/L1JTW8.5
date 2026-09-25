/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.dx;
import bf.a;
import bh.v;

public class cn
extends a {
    private final int a = 101;
    private final v b = be.a().a(101);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(101, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.cv(4);
            tpc.a(new dx(tpc.fr(), 4, timeSecs));
            tpc.b(new dx(tpc.fr(), 4, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        _user.j(101, this.b.v() * 1000);
        this.a(_user, this.b);
        this.b(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.cv(4);
            pc.a(new dx(pc.fr(), 4, this.b.v()));
            pc.b(new dx(pc.fr(), 4, this.b.v()));
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.cv(0);
            pc.a(new dx(pc.fr(), 0, 0));
            pc.b(new dx(pc.fr(), 0, 0));
        }
    }
}

