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
import aq.w;
import be.ds;
import bf.a;
import bh.v;

public class ak
extends a {
    private final int a = 36;
    private final v b = be.a().a(36);

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
        w _magic = new w(_user, target);
        boolean isSuccess = _magic.a(36);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (!(target instanceof s)) {
                this.b(_user, 79);
                return;
            }
            s npc = (s)target;
            if (!npc.U_().v()) {
                this.b(_user, 79);
                return;
            }
            if (!isSuccess) {
                this.b(_user, 280);
                return;
            }
            this.a(target, this.b);
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
                z summon = new z(npc, _user, false);
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

