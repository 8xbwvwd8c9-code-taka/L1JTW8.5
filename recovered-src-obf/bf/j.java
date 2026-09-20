/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.q;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.ds;
import bf.a;
import bh.v;

public class j
extends a {
    private final int a = 8;
    private final v b = be.a().a(8);

    @Override
    public void a(f _target, int timeSecs) {
        u tpc = (u)_target;
        q item = tpc.v();
        if (item != null) {
            item.a(tpc, 8, this.b.v() * 1000);
            tpc.a(new ds(161, item.s(), "$245", "$247"));
            this.a(_target, this.b);
        } else {
            tpc.a(new ds(79));
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        this.b(_user, this.b);
        if (target instanceof u) {
            u tpc = (u)target;
            q item = tpc.v();
            if (item != null && item.g()) {
                item.a(tpc, 8, this.b.v() * 1000);
                tpc.a(new ds(161, item.s(), "$245", "$247"));
                this.a(target, this.b);
            } else {
                tpc.a(new ds(79));
            }
        } else {
            this.a(target, this.b);
        }
    }

    @Override
    public void a(f cha) {
    }
}

