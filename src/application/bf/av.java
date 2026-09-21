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

public class av
extends a {
    private final int a = 47;
    private final v b = be.a().a(47);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        if (!_target.bB(47)) {
            _target.ck(-5);
            _target.cm(-1);
        }
        _target.j(47, timeSecs * 1000);
        this.b(_target, 692);
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
        if (_magic.a(47)) {
            if (!target.bB(47)) {
                target.ck(-5);
                target.cm(-1);
            }
            target.j(47, this.b.v() * 1000);
            this.a(target, this.b);
            this.b(target, 692);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        cha.ck(5);
        cha.cm(1);
    }
}

