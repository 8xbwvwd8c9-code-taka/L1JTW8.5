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

public class k
extends a {
    private final int a = 9;
    private final v b = be.a().a(9);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            _target.en();
            this.b(_target, 211);
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
        target.en();
        this.b(target, 211);
    }

    @Override
    public void a(f cha) {
    }
}

