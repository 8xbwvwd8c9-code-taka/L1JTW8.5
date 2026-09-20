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

public class fp
extends a {
    private final int a = 202;
    private final v b = be.a().a(202);

    @Override
    public void a(f _target, int timeSecs) {
        if (_target.bB(202) && _target.bC(202) > 6) {
            return;
        }
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        this.b(_target, 1339);
        _target.j(202, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        w _magic = new w(_user, target);
        int dmg = _magic.b(202);
        _magic.a(dmg, 0);
        this.a(_user, target, this.b, dmg);
        if (target.bB(202) && target.bC(202) > 6) {
            return;
        }
        boolean isSuccess = _magic.a(202);
        if (isSuccess) {
            this.b(target, 1339);
            this.a(target, this.b);
            target.j(202, this.b.v() * 1000);
        }
    }

    @Override
    public void a(f cha) {
    }
}

