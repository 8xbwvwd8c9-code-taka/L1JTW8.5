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

public class v
extends a {
    private final int a = 20;
    private final bh.v b = be.a().a(20);

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
            int type = 1;
            if (pc.bB(1012)) {
                type = 2;
            }
            pc.a(new ad(type));
        }
        _target.j(20, timeSecs * 1000);
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
        boolean isSeccess = _magic.a(20);
        if (isSeccess) {
            target.j(20, this.b.v() * 1000);
            if (target instanceof u) {
                u pc = (u)target;
                int type = 1;
                if (pc.bB(1012)) {
                    type = 2;
                }
                pc.a(new ad(type));
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

