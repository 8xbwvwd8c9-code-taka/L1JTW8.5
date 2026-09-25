/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.cn;
import be.ee;
import bf.a;
import bh.v;

public class cp
extends a {
    private final int a = 103;
    private final v b = be.a().a(103);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            _target.b(new ee(_target.fr(), this.b.u()));
            _target.j(66, this.b.v() * 1000);
            if (_target instanceof u) {
                u tpc = (u)_target;
                tpc.a(new ee(tpc.fr(), this.b.u()));
                tpc.a(new cn(3, true));
            } else if (_target instanceof t) {
                t npc = (t)_target;
                npc.U(true);
            }
            this.b(_target, 147);
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
        w _magic = new w(_user, target);
        if (target.bB(66)) {
            return;
        }
        boolean isSuccess = _magic.a(103);
        if (isSuccess) {
            target.b(new ee(target.fr(), this.b.u()));
            target.j(66, this.b.v() * 1000);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new ee(tpc.fr(), this.b.u()));
                tpc.a(new cn(3, true));
            } else if (target instanceof t) {
                t npc = (t)target;
                npc.U(true);
            }
            this.b(target, 147);
        }
    }

    @Override
    public void a(f cha) {
    }
}

