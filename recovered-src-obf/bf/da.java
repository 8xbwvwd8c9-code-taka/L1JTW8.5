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

public class da
extends a {
    private final int a = 114;
    private final v b = be.a().a(114);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(114)) {
            _target.cm(5);
            _target.cn(5);
        }
        _target.j(114, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new eb(113, timeSecs, tpc));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(114)) {
            _user.cm(5);
            _user.cn(5);
        }
        _user.j(114, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new eb(113, this.b.v(), pc));
        }
    }

    @Override
    public void a(f cha) {
        cha.cm(-5);
        cha.cn(-5);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new eb(113, 0, pc));
        }
    }
}

