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

public class w
extends a {
    private final int a = 21;
    private final v b = be.a().a(21);

    @Override
    public void a(f _target, int timeSecs) {
        if (_target instanceof u) {
            u tpc = (u)_target;
            q item = tpc.j().j(2);
            if (item == null) {
                tpc.a(new ds(79));
                return;
            }
            this.a((f)tpc, this.b);
            item.b(tpc, this.b.v() * 1000);
            tpc.a(new ds(161, item.s(), "$245", "$247"));
            if (item.D()) {
                tpc.a(new cm(154, 748, this.b.v()));
            }
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            q item = pc.j().e(_targetId);
            if (item != null && item.h() && item.a().aP() == 2) {
                this.a(_user, this.b);
                item.b(pc, this.b.v() * 1000);
                pc.a(new ds(161, item.s(), "$245", "$247"));
                if (item.D()) {
                    pc.a(new cm(154, 748, this.b.v()));
                }
            } else {
                pc.a(new ds(79));
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

