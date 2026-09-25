/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import bf.a;
import bh.v;

public class dg
extends a {
    private final int a = 120;
    private final v b = be.a().a(120);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        _target.j(120, timeSecs * 1000);
        this.b(_target, 737);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        if (_user.bB(120)) {
            this.b(_user, 79);
            return;
        }
        _user.j(120, this.b.v() * 1000);
        this.a(_user, this.b);
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(0);
            pc.b((f)null);
        }
    }
}

