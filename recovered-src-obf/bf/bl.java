/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.ai;
import aq.f;
import bf.a;
import bh.v;

public class bl
extends a {
    private final int a = 63;
    private final v b = be.a().a(63);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        ai.a().a(this.b.t(), this.b.v() * 1000, x2, y2, _user.fp());
    }

    @Override
    public void a(f cha) {
    }
}

