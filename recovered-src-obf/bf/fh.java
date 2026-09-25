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

public class fh
extends a {
    private final int a = 190;
    private final v b = be.a().a(190);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(190)) {
            _target.cg(10);
        }
        _target.j(190, timeSecs * 1000);
        if (_target instanceof u) {
            u pc = (u)_target;
            pc.a(new cm(86, 142, 189, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (!_user.bB(190)) {
            _user.cg(10);
        }
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new cm(86, 142, 189, this.b.v()));
        }
        _user.j(190, this.b.v() * 1000);
    }

    @Override
    public void a(f cha) {
        cha.cg(-10);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cm(86, 142, 189, 0));
        }
    }
}

