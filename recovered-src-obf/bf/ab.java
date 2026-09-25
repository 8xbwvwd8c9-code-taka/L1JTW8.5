/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.q;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.ds;
import bf.a;
import bh.v;
import bi.i;

public class ab
extends a {
    private final int a = 27;
    private final v b = be.a().a(27);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        if (_target instanceof u) {
            u tpc = (u)_target;
            q weapon = tpc.v();
            if (weapon != null) {
                int weaponDamage = i.a(3) + 1;
                tpc.a(new ds(268, weapon.s()));
                tpc.j().c(weapon, weaponDamage);
            }
        } else if (_target instanceof t) {
            t npc = (t)_target;
            npc.h(true);
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
        boolean isSeccess = _magic.a(27);
        if (isSeccess) {
            this.a(target, this.b);
            if (target instanceof u) {
                u tpc = (u)target;
                q weapon = tpc.v();
                if (weapon != null) {
                    int weaponDamage = i.a(_user.eD() / 3) + 1;
                    tpc.a(new ds(268, weapon.s()));
                    tpc.j().c(weapon, weaponDamage);
                }
            } else if (target instanceof t) {
                t npc = (t)target;
                npc.h(true);
                npc.j(27, 3000);
            }
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof t) {
            t npc = (t)cha;
            npc.h(false);
        }
    }
}

