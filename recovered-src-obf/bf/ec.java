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

public class ec
extends a {
    private final int a = 155;
    private final v b = be.a().a(155);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(155, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.cv(1);
            tpc.a(new dx(tpc.fr(), 1, timeSecs));
            tpc.b(new dx(tpc.fr(), 1, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        u pc;
        if (_user instanceof u) {
            pc = (u)_user;
            if (pc.v() == null) {
                this.b((f)pc, 79);
                return;
            }
            if (pc.v().a().aO() != 4 && pc.v().a().aO() != 46) {
                this.b((f)pc, 79);
                return;
            }
        }
        _user.j(155, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            pc = (u)_user;
            pc.cv(1);
            pc.a(new dx(pc.fr(), 1, this.b.v()));
            pc.b(new dx(pc.fr(), 1, this.b.v()));
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

