/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.aa;
import ap.s;
import aq.aq;
import aq.f;
import aq.w;
import be.ak;
import bf.a;
import bh.v;

public class u
extends a {
    private final int a = 19;
    private final v b = be.a().a(19);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aq.aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f) || obj instanceof aa || obj instanceof ap.f) {
            this.b(_user, 79);
            return;
        }
        f target = (f)obj;
        this.a(target, this.b);
        this.b(_user, this.b);
        w _magic = new w(_user, target);
        if (target instanceof s && ((s)target).U_().B() == 1) {
            int dmg = _magic.b(19);
            _magic.a(dmg, 0);
            target.b(new ak(target.fr(), 2));
            return;
        }
        int heal = _magic.c(19);
        if (target.bB(73) && target instanceof ap.u) {
            ap.u tpc = (ap.u)target;
            tpc.a(_user, (double)heal * 0.3, true);
            return;
        }
        target.a(target.ea() + heal);
        this.b(target, 77);
    }

    @Override
    public void a(f cha) {
    }
}

