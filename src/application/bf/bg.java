/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ai.d;
import ao.au;
import ao.be;
import ap.t;
import ap.u;
import aq.ai;
import aq.aq;
import aq.f;
import bf.a;
import bh.l;
import bh.v;

public class bg
extends a {
    private final int a = 58;
    private final v b = be.a().a(58);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (_user instanceof u) {
            u pc = (u)_user;
            t target = this.a(x2, y2, pc.fp());
            this.b(_user, target, this.b, 0);
            ai.a().a(pc, x2, y2);
        }
    }

    private t a(int x2, int y2, int mapid) {
        t temp = null;
        l l1npc = au.a().a(45001);
        if (l1npc != null) {
            temp = new t(l1npc);
            temp.cw(2510);
            temp.cF(d.a().c());
            temp.cG(x2);
            temp.cH(y2);
            temp.cE(mapid);
            aq.a().a(temp);
            aq.a().c(temp);
            temp.a(1000L);
        }
        return temp;
    }

    @Override
    public void a(f cha) {
    }
}

