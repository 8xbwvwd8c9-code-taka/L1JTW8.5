/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.ci;
import bf.a;
import bh.v;

public class be
extends a {
    private final int a = 56;
    private final v b = ao.be.a().a(56);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            this.a(_target, this.b);
            timeSecs = this.b.v();
        } else if (timeSecs == 0) {
            return;
        }
        if (!_target.bB(56)) {
            _target.ck(-6);
            _target.bL(12);
        }
        _target.j(56, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new ci(tpc));
        }
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
        if (_magic.a(56)) {
            if (!target.bB(56)) {
                target.ck(-6);
                target.bL(12);
            }
            target.j(56, this.b.v() * 1000);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new ci(tpc));
            }
            this.a(target, this.b);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        cha.ck(6);
        cha.bL(-12);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ci(pc));
        }
    }
}

