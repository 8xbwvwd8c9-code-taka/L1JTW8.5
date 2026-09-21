/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.s;
import ap.t;
import ap.u;
import ap.z;
import aq.aa;
import aq.aq;
import aq.f;
import be.ds;
import bf.a;
import bh.v;

public class ap
extends a {
    private final int a = 41;
    private final v b = be.a().a(41);

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
        this.b(_user, this.b);
        this.a(target, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (!(target instanceof s) || !target.eX()) {
                this.b(_user, 79);
                return;
            }
            s npc = (s)target;
            int petcost = 0;
            for (t pet : _user.ek().values()) {
                petcost += pet.Q();
            }
            int charisma = _user.eC();
            if (pc.A()) {
                charisma = Math.min(charisma, 30) + 12;
            } else if (pc.B()) {
                charisma = Math.min(charisma, 36) + 6;
            }
            if ((charisma -= petcost) >= 6) {
                z summon = new z(npc, _user, true);
                target = summon;
            } else {
                pc.a(new ds(319));
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

