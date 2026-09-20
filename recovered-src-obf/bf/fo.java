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

public class fo
extends a {
    private final int a = 201;
    private final v b = be.a().a(201);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(201)) {
            _target.cA(5);
        }
        _target.j(201, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new cm(88, tpc.fk()));
            tpc.a(new cm(21, timeSecs / 16));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (!_user.bB(201)) {
            _user.cA(5);
        }
        _user.j(201, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new cm(88, pc.fk()));
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.cA(-5);
            pc.a(new cm(88, pc.fk()));
        }
    }
}

