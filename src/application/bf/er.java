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

public class er
extends a {
    private final int a = 170;
    private final v b = be.a().a(170);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(170, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        target.j(170, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(target, this.b);
    }

    @Override
    public void a(f cha) {
    }
}

