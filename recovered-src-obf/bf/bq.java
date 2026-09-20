/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.cm;
import bf.a;
import bh.v;

public class bq
extends a {
    private final int a = 68;
    private final v b = be.a().a(68);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(68, timeSecs * 1000);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new cm(40, timeSecs));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        this.b(_user, this.b);
        if (_user instanceof u && obj instanceof u) {
            u pc = (u)_user;
            u tpc = (u)obj;
            if (tpc.fr() == pc.fr() || pc.aF() != 0 && pc.aF() == tpc.aF()) {
                tpc.j(68, this.b.v() * 1000);
                this.a((f)tpc, this.b);
            } else {
                this.b(_user, 79);
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

