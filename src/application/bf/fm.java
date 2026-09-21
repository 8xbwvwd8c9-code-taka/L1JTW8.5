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

public class fm
extends a {
    private final int a = 195;
    private final v b = be.a().a(195);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(195)) {
            _target.cd(10);
            _target.cm(5);
        }
        _target.j(195, timeSecs * 1000);
        if (_target instanceof u) {
            u pc = (u)_target;
            pc.a(new cm(86, 142, 194, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (!_user.bB(195)) {
            _user.cd(10);
            _user.cm(5);
        }
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new cm(86, 142, 194, this.b.v()));
        }
        _user.j(195, this.b.v() * 1000);
    }

    @Override
    public void a(f cha) {
        cha.cd(-10);
        cha.cm(-5);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cm(86, 142, 194, 0));
        }
    }
}

