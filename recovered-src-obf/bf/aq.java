/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.f;
import be.eg;
import bf.a;
import bh.v;

public class aq
extends a {
    private final int a = 42;
    private final v b = be.a().a(42);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(42)) {
            _target.bN(5);
        }
        _target.j(42, timeSecs * 1000);
        this.b(_target, 292);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new eg(tpc, 5, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.aq.a().a(_targetId);
        this.b(_user, this.b);
        if (_user instanceof u && obj instanceof u) {
            u pc = (u)_user;
            u tpc = (u)obj;
            if (tpc.fr() == pc.fr() || pc.aF() != 0 && pc.aF() == tpc.aF()) {
                if (!tpc.bB(42)) {
                    tpc.bN(5);
                }
                tpc.j(42, this.b.v() * 1000);
                this.a((f)tpc, this.b);
                tpc.a(new eg(tpc, 5, this.b.v()));
                this.b((f)tpc, 292);
            } else {
                this.b(_user, 79);
            }
        }
    }

    @Override
    public void a(f cha) {
        cha.bN(-5);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new eg(pc, 5, 0));
        }
    }
}

