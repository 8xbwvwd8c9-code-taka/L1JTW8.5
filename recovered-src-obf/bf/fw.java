/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.do;
import bf.a;
import bh.v;

public class fw
extends a {
    private final int a = 209;
    private final v b = be.a().a(209);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(209)) {
            _target.cp(2);
        }
        _target.j(209, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new do(tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        if (!target.bB(209)) {
            target.cp(2);
        }
        target.j(209, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(target, this.b);
        if (target instanceof u) {
            u tpc = (u)target;
            tpc.a(new do(tpc));
        }
    }

    @Override
    public void a(f cha) {
        cha.cp(-2);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new do(pc));
        }
    }
}

