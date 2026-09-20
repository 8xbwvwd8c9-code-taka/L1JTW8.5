/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ba;
import be.bv;
import bf.a;
import bh.v;

public class cb
extends a {
    private final int a = 79;
    private final v b = be.a().a(79);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (_target instanceof u && !_target.bB(79)) {
            u pc = (u)_target;
            pc.au(pc.bd() / 5);
            pc.aw(pc.be() / 5);
            pc.bH(pc.bw());
            pc.bJ(pc.by());
            pc.a(new bv(pc.eb(), pc.ex()));
            pc.a(new ba(pc.ea(), pc.ew()));
            if (pc.q()) {
                pc.aL().f(pc);
            }
        }
        _target.j(79, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u && !_user.bB(79)) {
            u pc = (u)_user;
            pc.au(pc.bd() / 5);
            pc.aw(pc.be() / 5);
            pc.bH(pc.bw());
            pc.bJ(pc.by());
            pc.a(new bv(pc.eb(), pc.ex()));
            pc.a(new ba(pc.ea(), pc.ew()));
            if (pc.q()) {
                pc.aL().f(pc);
            }
        }
        _user.j(79, this.b.v() * 1000);
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.bH(-pc.bw());
            pc.bJ(-pc.by());
            pc.au(0);
            pc.aw(0);
            pc.a(new ba(pc.ea(), pc.ew()));
            if (pc.q()) {
                pc.aL().f(pc);
            }
            pc.a(new bv(pc.eb(), pc.ex()));
        }
    }
}

