/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.q;
import ap.u;
import aq.f;
import be.cm;
import be.ds;
import bf.a;
import bh.v;

public class n
extends a {
    private final int a = 12;
    private final v b = be.a().a(12);

    @Override
    public void a(f _target, int timeSecs) {
        if (_target instanceof u) {
            u tpc = (u)_target;
            if (tpc.v() == null) {
                tpc.a(new ds(79));
                return;
            }
            q item = tpc.v();
            item.a(tpc, 12, this.b.v() * 1000);
            tpc.a(new ds(161, item.s(), "$245", "$247"));
            if (item.D()) {
                tpc.a(new cm(154, 747, this.b.v()));
            }
            this.a((f)tpc, this.b);
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            q item = pc.j().e(_targetId);
            if (item != null && item.g()) {
                item.a(pc, 12, this.b.v() * 1000);
                pc.a(new ds(161, item.s(), "$245", "$247"));
                if (item.D()) {
                    pc.a(new cm(154, 747, this.b.v()));
                }
                this.a(_user, this.b);
            } else {
                pc.a(new ds(79));
            }
        } else {
            this.a(_user, this.b);
        }
    }

    @Override
    public void a(f cha) {
    }
}

