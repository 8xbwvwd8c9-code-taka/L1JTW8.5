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

public class fd
extends a {
    private final int a = 186;
    private final v b = be.a().a(186);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(186, timeSecs * 1000);
        if (_target instanceof u) {
            u pc = (u)_target;
            pc.cv(1);
            pc.a(new dx(pc.fr(), 1, timeSecs));
        }
        _target.b(new dx(_target.fr(), 1, timeSecs));
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        _user.j(186, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.cv(1);
            pc.a(new dx(pc.fr(), 1, this.b.v()));
        }
        _user.b(new dx(_user.fr(), 1, this.b.v()));
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

