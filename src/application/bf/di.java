/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.dc;
import bf.a;
import bh.v;

public class di
extends a {
    private final int a = 122;
    private final v b = be.a().a(122);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(122)) {
            _target.ci(10);
            _target.cd(10);
            _target.ch(10);
            if (_target instanceof u) {
                u tpc = (u)_target;
                tpc.a(new dc(122, this.b.v(), 6, 7427, 7428, 4734, 4734, 4741, 5));
            }
        }
        _target.j(122, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (pc.q()) {
                for (u menber : pc.aL().c()) {
                    if (menber.eX() || !menber.fu().e(pc.fu())) continue;
                    menber.ci(10);
                    menber.cd(10);
                    menber.ch(10);
                    menber.j(122, this.b.v() * 1000);
                    this.a((f)menber, this.b);
                    menber.a(new dc(122, this.b.v(), 6, 7427, 7428, 4734, 4734, 4741, 5));
                }
            } else {
                pc.ci(10);
                pc.cd(10);
                pc.ch(10);
                pc.j(122, this.b.v() * 1000);
                this.a((f)pc, this.b);
                pc.a(new dc(122, this.b.v(), 6, 7427, 7428, 4734, 4734, 4741, 5));
            }
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.ci(-10);
            pc.cd(-10);
            pc.ch(-10);
            pc.a(new dc(110, 122));
        }
    }
}

