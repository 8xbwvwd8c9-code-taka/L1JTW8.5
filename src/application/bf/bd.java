/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.ci;
import bf.a;
import bh.v;

public class bd
extends a {
    private final int a = 55;
    private final v b = be.a().a(55);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(55)) {
            _target.bL(10);
            _target.ck(5);
            _target.cm(2);
        }
        _target.j(55, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new ci(tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        if (!target.bB(55)) {
            target.bL(10);
            target.ck(5);
            target.cm(2);
        }
        target.j(55, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(target, this.b);
        if (target instanceof u) {
            u tpc = (u)target;
            tpc.a(new ci(tpc));
        }
    }

    @Override
    public void a(f cha) {
        cha.bL(-10);
        cha.ck(-5);
        cha.cm(-2);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ci(pc));
        }
    }
}

