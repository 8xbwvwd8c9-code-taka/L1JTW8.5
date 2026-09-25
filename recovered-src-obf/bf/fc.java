/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.cm;
import bf.a;
import bh.v;

public class fc
extends a {
    private final int a = 185;
    private final v b = be.a().a(185);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(185)) {
            _target.ch(10);
            _target.bL(-3);
        }
        _target.j(185, timeSecs * 1000);
        if (_target instanceof u) {
            u pc = (u)_target;
            pc.a(new cm(86, 142, 184, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (!_user.bB(185)) {
            _user.ch(10);
            _user.bL(-3);
        }
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new cm(86, 142, 184, this.b.v()));
        }
        _user.j(185, this.b.v() * 1000);
    }

    @Override
    public void a(f cha) {
        cha.ch(-10);
        cha.bL(3);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cm(86, 142, 184, 0));
        }
    }
}

