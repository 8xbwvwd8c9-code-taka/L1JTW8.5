/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aq;
import aq.f;
import be.ai;
import bf.a;
import bh.v;

public class aa
extends a {
    private final int a = 26;
    private final v b = be.a().a(26);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(26)) {
            _target.bR(5);
        }
        _target.j(26, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new ai(tpc, 5, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aq.aa obj = aq.a().a(_targetId);
        this.b(_user, this.b);
        if (_user instanceof u && obj instanceof u) {
            u pc = (u)_user;
            u tpc = (u)obj;
            if (tpc.fr() == pc.fr() || pc.aF() != 0 && pc.aF() == tpc.aF()) {
                if (!tpc.bB(26)) {
                    tpc.bR(5);
                }
                tpc.j(26, this.b.v() * 1000);
                this.a((f)tpc, this.b);
                tpc.a(new ai(tpc, 5, this.b.v()));
                this.b((f)tpc, 294);
            } else {
                this.b(_user, 79);
            }
        }
    }

    @Override
    public void a(f cha) {
        cha.bR(-5);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ai(pc, 5, 0));
        }
    }
}

