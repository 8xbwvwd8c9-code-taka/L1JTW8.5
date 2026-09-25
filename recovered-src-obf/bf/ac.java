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

public class ac
extends a {
    private final int a = 28;
    private final v b = be.a().a(28);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        w _magic = new w(_user, target);
        int dmg = _magic.b(28);
        _magic.a(dmg, 0);
        if (dmg > 0) {
            _user.a(dmg + _user.ea());
        }
        if (_magic.b()) {
            this.a(_user, target, this.b.s(), this.b.u(), dmg);
        } else {
            this.b(_user, target, this.b, dmg);
        }
    }

    @Override
    public void a(f cha) {
    }
}

