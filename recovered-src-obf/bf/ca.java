/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import bf.a;
import bh.v;

public class ca
extends a {
    private final int a = 78;
    private final v b = be.a().a(78);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            this.a(_target, this.b);
            timeSecs = this.b.v();
        }
        if (_target instanceof u) {
            u pc = (u)_target;
            pc.b();
            pc.d();
        }
        _target.j(78, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        _user.j(78, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.b();
            pc.d();
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a();
            pc.c();
        }
    }
}

