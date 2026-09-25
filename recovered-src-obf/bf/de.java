/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.am;
import aq.aq;
import aq.e;
import aq.f;
import be.ds;
import bf.a;
import bh.v;

public class de
extends a {
    private final int a = 118;
    private final v b = be.a().a(118);

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
        if (!pc.fq().j()) {
            pc.a(new ds(647));
            return;
        }
        boolean isCastle = e.b(tpc.fs(), tpc.ft(), tpc.fp());
        if (tpc.fq().h() && !isCastle) {
            am.a(pc, tpc.fs(), tpc.ft(), tpc.fp(), 5, true);
        } else {
            pc.a(new ds(79));
        }
    }

    @Override
    public void a(f cha) {
    }
}

