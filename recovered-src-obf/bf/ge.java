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

public class ge
extends a {
    private final int a = 217;
    private final v b = be.a().a(217);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        if (!_target.bB(217)) {
            _target.bN(-1);
            _target.bP(-1);
            _target.bR(-1);
            _target.bX(-1);
            _target.bV(-1);
            if (_target instanceof u) {
                u tpc = (u)_target;
                tpc.a(new ck(tpc));
            }
        }
        _target.j(217, timeSecs * 1000);
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
        boolean isSuccess = _magic.a(217);
        if (isSuccess) {
            if (!target.bB(217)) {
                target.bN(-1);
                target.bP(-1);
                target.bR(-1);
                target.bX(-1);
                target.bV(-1);
                if (target instanceof u) {
                    u tpc = (u)target;
                    tpc.a(new ck(tpc));
                }
            }
            target.j(217, this.b.v() * 1000);
            this.a(target, this.b);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        cha.bN(1);
        cha.bP(1);
        cha.bR(1);
        cha.bX(1);
        cha.bV(1);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ck(pc));
        }
    }
}

