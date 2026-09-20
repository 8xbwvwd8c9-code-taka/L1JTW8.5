/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.aa;
import aq.ab;
import aq.aq;
import aq.f;
import aq.w;
import bf.a;
import bh.v;

public class ah
extends a {
    private final int a = 33;
    private final v b = be.a().a(33);

    @Override
    public void a(f _target, int timeSecs) {
        int delay = 0;
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            delay = 5000;
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        ab.a(_target, delay, timeSecs * 1000);
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
        boolean isSeccess = _magic.a(33);
        if (isSeccess) {
            this.a(target, this.b);
            ab.a(target, 5000, this.b.v() * 1000);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        cha.ef();
    }
}

