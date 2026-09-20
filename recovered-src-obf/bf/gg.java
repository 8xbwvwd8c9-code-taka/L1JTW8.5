/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.aa;
import aq.aq;
import aq.f;
import bf.a;
import bh.v;

public class gg
extends a {
    private final int a = 219;
    private final v b = be.a().a(219);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(219)) {
            _target.ck(10);
            _target.cl(10);
        }
        _target.j(219, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        if (!target.bB(219)) {
            target.ck(10);
            target.cl(10);
        }
        target.j(219, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(target, this.b);
    }

    @Override
    public void a(f cha) {
        cha.ck(-10);
        cha.cl(-10);
    }
}

