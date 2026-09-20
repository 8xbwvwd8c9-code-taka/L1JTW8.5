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

public class bt
extends a {
    private final int a = 71;
    private final v b = be.a().a(71);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        _target.j(71, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        this.b(_user, this.b);
        w _magic = new w(_user, target);
        if (_magic.a(71)) {
            target.j(71, this.b.v() * 1000);
            this.a(target, this.b);
        }
    }

    @Override
    public void a(f cha) {
    }
}

