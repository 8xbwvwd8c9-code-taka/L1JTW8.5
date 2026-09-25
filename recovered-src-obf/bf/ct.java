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

public class ct
extends a {
    private final int a = 107;
    private final v b = be.a().a(107);

    @Override
    public void a(f _target, int timeSecs) {
        u pc;
        q item;
        if (timeSecs == 0 && _target instanceof u && (item = (pc = (u)_target).v()) != null && item.g()) {
            item.a(pc, 107, this.b.v() * 1000);
            if (item.D()) {
                pc.a(new cm(154, 2951, this.b.v()));
            }
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            q item = pc.j().e(_targetId);
            if (item != null && item.g()) {
                item.a(pc, 107, this.b.v() * 1000);
                if (item.D()) {
                    pc.a(new cm(154, 2951, this.b.v()));
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

