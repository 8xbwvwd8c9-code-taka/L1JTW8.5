/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.aa;
import ap.t;
import ap.u;
import aq.aq;
import aq.f;
import be.ca;
import bf.a;
import bh.v;

public class bj
extends a {
    private final int a = 61;
    private final v b = be.a().a(61);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        t npc;
        aq.aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        if (message == null) {
            this.b(_user, this.b);
        }
        this.a(target, this.b);
        if (!target.eX() || target.bB(61) || _user.fr() == target.fr() || target instanceof aa) {
            this.b(_user, 79);
            return;
        }
        for (u other : aq.a().c(target, 0)) {
            if (other.eX()) continue;
            this.b(_user, 592);
            return;
        }
        if (target instanceof u) {
            u tpc = (u)target;
            if (tpc.fq().k()) {
                tpc.k(false);
                tpc.am(_user.fr());
                tpc.a(new ca(322, new String[0]));
            }
        } else if (target instanceof t && (!(npc = (t)target).U_().ah() || npc instanceof ap.v)) {
            npc.j(npc.ew());
            npc.i_(0);
            if (npc instanceof ap.v) {
                ap.v pet = (ap.v)npc;
                pet.aw();
                pet.u();
                pet.w();
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

