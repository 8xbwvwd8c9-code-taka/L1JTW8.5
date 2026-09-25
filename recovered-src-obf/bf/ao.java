/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.ad;
import bf.a;
import bh.v;

public class ao
extends a {
    private final int a = 40;
    private final v b = be.a().a(40);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        if (_target instanceof u) {
            u pc = (u)_target;
            pc.a(new ad(1));
        }
        _target.j(40, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        w _magic = new w(_user, target);
        if (!target.bB(40) && _magic.a(40)) {
            target.j(40, this.b.v() * 1000);
            if (target instanceof u) {
                u pc = (u)target;
                pc.a(new ad(1));
            }
            this.a(target, this.b);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ad(0));
        }
    }
}

