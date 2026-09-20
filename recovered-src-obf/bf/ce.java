/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.f;
import bf.a;
import bh.v;

public class ce
extends a {
    private final int a = 88;
    private final v b = be.a().a(88);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(88, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        _user.j(88, this.b.v() * 1000);
        this.a(_user, this.b);
        this.b(_user, this.b);
    }

    @Override
    public void a(f cha) {
    }
}

