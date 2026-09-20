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
import bf.a;
import bh.v;

public class dm
extends a {
    private final int a = 132;
    private final v b = be.a().a(132);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        if (_user instanceof u) {
            u pc = (u)_user;
            q weapon = pc.v();
            if (weapon == null || weapon.a().aO() != 20) {
                return;
            }
            this.a(_user, this.b);
            pc.v(true);
            int i2 = 0;
            while (i2 < 3) {
                target.c(pc);
                ++i2;
            }
            pc.v(false);
        }
    }

    @Override
    public void a(f cha) {
    }
}

