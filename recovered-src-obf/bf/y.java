/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.t;
import aq.aa;
import aq.aq;
import aq.f;
import be.ee;
import bf.a;
import bh.v;

public class y
extends a {
    private final int a = 23;
    private final v b = be.a().a(23);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof t)) {
            this.b(_user, 79);
            return;
        }
        t npc = (t)obj;
        int weakAttr = npc.U_().r();
        if ((weakAttr & 1) == 1) {
            npc.b(new ee(_targetId, 2169));
        } else if ((weakAttr & 2) == 2) {
            npc.b(new ee(_targetId, 2166));
        } else if ((weakAttr & 4) == 4) {
            npc.b(new ee(_targetId, 2167));
        } else if ((weakAttr & 8) == 8) {
            npc.b(new ee(_targetId, 2168));
        } else {
            this.b(_user, 79);
        }
    }

    @Override
    public void a(f cha) {
    }
}

