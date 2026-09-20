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
import bi.i;

public class an
extends a {
    private final int a = 39;
    private final v b = be.a().a(39);

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
        if (target.eX()) {
            this.b(_user, 79);
            return;
        }
        w _magic = new w(_user, target);
        if (_magic.a(39)) {
            this.a(target, this.b);
            int chance = i.a(10) + 5;
            int drainMana = chance + _user.eD() / 2;
            if (target.eb() < drainMana) {
                drainMana = target.eb();
            }
            _magic.a(0, drainMana);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
    }
}

