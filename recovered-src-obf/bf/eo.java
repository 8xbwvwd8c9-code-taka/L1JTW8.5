/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.cm;
import bf.a;
import bh.v;

public class eo
extends a {
    private final int a = 167;
    private final v b = be.a().a(167);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(167, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new cm(44, tpc.fr(), timeSecs));
        }
        _target.b(new cm(44, _target.fr(), timeSecs));
        this.b(_target, 1001);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        w magic = new w(_user, target);
        boolean isSeccess = magic.a(167);
        if (isSeccess) {
            target.j(167, this.b.v() * 1000);
            this.a(target, this.b);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new cm(44, tpc.fr(), this.b.v()));
            }
            target.b(new cm(44, target.fr(), this.b.v()));
            this.b(target, 1001);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cm(44, pc.fr(), 0));
            pc.b(new cm(44, pc.fr(), 0));
        }
    }
}

