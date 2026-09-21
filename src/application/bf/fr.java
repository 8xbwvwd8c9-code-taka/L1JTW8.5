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

public class fr
extends a {
    private final int a = 204;
    private final v b = be.a().a(204);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        }
        if (!_target.bB(204)) {
            _target.ck(4);
            _target.cm(4);
            _target.cl(4);
            _target.cn(4);
        }
        _target.j(204, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        if (!target.bB(204)) {
            target.ck(4);
            target.cm(4);
            target.cl(4);
            target.cn(4);
        }
        target.j(204, this.b.v() * 1000);
        this.b(_user, this.b);
        this.a(target, this.b);
    }

    @Override
    public void a(f cha) {
        cha.ck(-4);
        cha.cm(-4);
        cha.cl(-4);
        cha.cn(-4);
    }
}

