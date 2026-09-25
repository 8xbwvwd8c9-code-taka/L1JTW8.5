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
import bf.a;
import bh.v;

public class fu
extends a {
    private final int a = 207;
    private final v b = be.a().a(207);

    public void b(f _user, f _target, int timeSecs) {
        if (timeSecs == -1) {
            if (_target.eb() >= 5) {
                _target.i_(_target.eb() - 5);
                int damage = _user.eE() * 5;
                if (_target instanceof u) {
                    ((u)_target).a(_user, (double)damage, true);
                } else if (_target instanceof t) {
                    ((t)_target).b(_user, damage);
                }
            }
            this.a(_target, this.b);
        }
    }

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        int dmg = 0;
        if (target.eb() >= 5) {
            w magic = new w(_user, target);
            dmg = magic.b(207);
            magic.a(dmg, 0);
            this.a(target, this.b);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
    }
}

