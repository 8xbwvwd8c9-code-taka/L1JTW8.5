/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aq;
import aq.f;
import be.ca;
import be.ds;
import bf.a;
import bh.v;

public class dc
extends a {
    private final int a = 116;
    private final v b = be.a().a(116);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        if (!(_user instanceof u)) {
            return;
        }
        u pc = (u)_user;
        u tpc = aq.a().a(message);
        if (tpc == null) {
            pc.a(new ds(73, message));
            return;
        }
        if (pc.aF() != tpc.aF()) {
            pc.a(new ds(414));
            return;
        }
        pc.aQ(tpc.fr());
        pc.aR(pc.fb());
        tpc.am(pc.fr());
        tpc.a(new ca(729, new String[0]));
    }

    @Override
    public void a(f cha) {
    }
}

