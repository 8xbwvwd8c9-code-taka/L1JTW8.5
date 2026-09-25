/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.f;
import bf.a;
import bh.v;

public class cr
extends a {
    private final int a = 105;
    private final v b = be.a().a(105);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(105, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        _user.j(105, this.b.v() * 1000);
        this.b(_user, 823);
    }

    @Override
    public void a(f cha) {
    }
}

