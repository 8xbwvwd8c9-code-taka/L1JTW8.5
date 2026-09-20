/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import bf.a;
import bh.v;

public class m
extends a {
    private final int a = 11;
    private final v b = be.a().a(11);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        az.a.a(_target, _target, 3000, 5, timeSecs);
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
        boolean isSeccess = _magic.a(11);
        if (isSeccess) {
            this.a(target, this.b);
            az.a.a(_user, target, 3000, 5, this.b.v());
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        cha.en();
    }
}

