/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.eb;
import bf.a;
import bh.v;

public class en
extends a {
    private final int a = 166;
    private final v b = be.a().a(166);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(166)) {
            _target.cl(6);
            _target.cn(3);
        }
        _target.j(166, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new eb(165, timeSecs, tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(166)) {
            _user.cl(6);
            _user.cn(3);
        }
        _user.j(166, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new eb(165, this.b.v(), pc));
        }
    }

    @Override
    public void a(f cha) {
        cha.cl(-6);
        cha.cn(-3);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new eb(165, 0, pc));
        }
    }
}

