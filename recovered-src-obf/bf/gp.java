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

public class gp
extends a {
    private final int a = 233;
    private final v b = be.a().a(233);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        _user.j(233, this.b.v() * 1000);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new dc(233, this.b.v(), 6, 7444, 7445, 4738, 4738, 4745, 5));
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new dc(110, 233));
        }
    }
}

