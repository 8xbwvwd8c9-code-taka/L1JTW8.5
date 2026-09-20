/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.s;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import bf.a;
import bh.v;

public class t
extends a {
    private final int a = 18;
    private final v b = be.a().a(18);

    public void b(f _user, f _target, int timeSecs) {
        s mob;
        int type;
        if (timeSecs == -1 && _target instanceof s && ((type = (mob = (s)_target).U_().B()) == 1 || type == 3)) {
            int dmg = mob.ea();
            mob.b(_user, dmg);
            this.a((f)mob, this.b);
        }
    }

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof s)) {
            this.b(_user, 280);
            return;
        }
        s mob = (s)obj;
        int type = mob.U_().B();
        if (type != 1 && type != 3) {
            this.b(_user, 280);
            return;
        }
        w _magic = new w(_user, mob);
        boolean isSeccess = _magic.a(18);
        if (isSeccess) {
            int dmg = mob.ea();
            _magic.a(dmg, 0);
            this.a((f)mob, this.b);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
    }
}

