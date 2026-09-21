/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.aa;
import aq.aq;
import aq.f;
import bf.a;
import bh.v;

public class al
extends a {
    private final int a = 37;
    private final v b = be.a().a(37);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            this.a(_target, this.b);
            this.b(_target, 155);
            _target.ef();
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        this.b(_user, this.b);
        this.a(target, this.b);
        this.b(target, 155);
        target.ef();
        target.en();
    }

    @Override
    public void a(f cha) {
    }
}

