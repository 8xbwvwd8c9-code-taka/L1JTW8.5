/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.eb;
import bf.a;
import bh.v;

public class dw
extends a {
    private final int a = 149;
    private final v b = be.a().a(149);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(149)) {
            _target.cn(6);
        }
        _target.j(149, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new eb(148, timeSecs, tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        if (!target.bB(149)) {
            target.cn(6);
        }
        target.j(149, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(target, this.b);
        if (target instanceof u) {
            u tpc = (u)target;
            tpc.a(new eb(148, this.b.v(), tpc));
        }
    }

    @Override
    public void a(f cha) {
        cha.cn(-6);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new eb(148, 0, pc));
        }
    }
}

