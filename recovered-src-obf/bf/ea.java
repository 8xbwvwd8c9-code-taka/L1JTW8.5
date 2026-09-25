/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.do;
import bf.a;
import bh.v;

public class ea
extends a {
    private final int a = 153;
    private final v b = be.a().a(153);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        _target.j(153, timeSecs * 1000);
        if (_target instanceof u) {
            u pc = (u)_target;
            pc.a(new do(pc));
        }
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
        boolean isSuccess = _magic.a(153);
        if (isSuccess && !target.bB(153)) {
            target.j(153, this.b.v() * 1000);
            this.a(target, this.b);
            if (target instanceof u) {
                u pc = (u)target;
                pc.a(new do(pc));
            }
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new do(pc));
        }
    }
}

