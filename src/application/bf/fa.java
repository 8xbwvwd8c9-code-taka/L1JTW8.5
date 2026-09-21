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
import be.ci;
import bf.a;
import bh.v;

public class fa
extends a {
    private final int a = 183;
    private final v b = be.a().a(183);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        if (!_target.bB(183)) {
            _target.bL(10);
            if (_target instanceof u) {
                u tpc = (u)_target;
                tpc.a(new ci(tpc));
            }
        }
        _target.j(183, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        w _magic = new w(_user, target);
        boolean isSuccess = _magic.a(183);
        if (isSuccess && !target.bB(183)) {
            target.j(183, this.b.v() * 1000);
            this.a(target, this.b);
            target.bL(10);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new ci(tpc));
            }
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        cha.bL(-10);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ci(pc));
        }
    }
}

