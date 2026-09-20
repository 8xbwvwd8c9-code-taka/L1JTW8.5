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
import be.ck;
import bf.a;
import bh.v;

public class fk
extends a {
    private final int a = 193;
    private final v b = be.a().a(193);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        if (!_target.bB(193)) {
            _target.bN(-5);
            _target.bV(-5);
            if (_target instanceof u) {
                u tpc = (u)_target;
                tpc.a(new ck(tpc));
            }
        }
        _target.j(193, timeSecs * 1000);
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
        boolean isSeccess = magic.a(193);
        if (isSeccess && !target.bB(193)) {
            target.j(193, this.b.v() * 1000);
            this.a(target, this.b);
            target.bN(-5);
            target.bV(-5);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new ck(tpc));
            }
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        cha.bN(5);
        cha.bV(5);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ck(pc));
        }
    }
}

