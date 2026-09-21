/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ba;
import bf.a;
import bh.v;

public class gk
extends a {
    private final int a = 226;
    private final v b = be.a().a(226);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (_target instanceof u && !_target.bB(226)) {
            u pc = (u)_target;
            pc.av(pc.bd() * (pc.ev() / 2) / 100);
            pc.bH(pc.bx());
            pc.a(new ba(pc.ea(), pc.ew()));
            if (pc.q()) {
                pc.aL().f(pc);
            }
        }
        _target.j(226, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u && !_user.bB(226)) {
            u pc = (u)_user;
            pc.av(pc.bd() * (pc.ev() / 2) / 100);
            pc.bH(pc.bx());
            pc.a(new ba(pc.ea(), pc.ew()));
            if (pc.q()) {
                pc.aL().f(pc);
            }
        }
        _user.j(226, this.b.v() * 1000);
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.bH(-pc.bx());
            pc.av(0);
            pc.a(new ba(pc.ea(), pc.ew()));
            if (pc.q()) {
                pc.aL().f(pc);
            }
        }
    }
}

