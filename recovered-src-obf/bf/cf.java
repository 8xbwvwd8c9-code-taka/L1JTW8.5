/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.f;
import bf.a;
import bh.v;

public class cf
extends a {
    private final int a = 89;
    private final v b = be.a().a(89);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(89)) {
            _target.cm(6);
        }
        _target.j(89, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (!_user.bB(89)) {
            _user.cm(6);
        }
        _user.j(89, this.b.v() * 1000);
    }

    @Override
    public void a(f cha) {
        cha.cm(-6);
    }
}

