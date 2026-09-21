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

public class gb
extends a {
    private final int a = 214;
    private final v b = be.a().a(214);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(214)) {
            _target.bL(-8);
        }
        _target.j(214, timeSecs * 1000);
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
        if (!target.bB(214)) {
            target.bL(-8);
        }
        target.j(214, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(target, this.b);
        if (target instanceof u) {
            u tpc = (u)target;
            tpc.a(new ci(tpc));
        }
    }

    @Override
    public void a(f cha) {
        cha.bL(8);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ci(pc));
        }
    }
}

