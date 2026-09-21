/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ao.q;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.i;
import be.ep;
import bf.a;
import bh.v;

public class cz
extends a {
    private final int a = 113;
    private final v b = be.a().a(113);

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
        target.j(113, this.b.v() * 1000);
        this.b(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (pc.aF() != 0) {
                i clan = q.a().a(pc.aF());
                for (u menber : clan.b()) {
                    menber.a(new ep(_targetId, true));
                }
            } else {
                pc.a(new ep(_targetId, true));
            }
        }
    }

    @Override
    public void a(f cha) {
        cha.b(new ep(cha.fr(), false));
    }
}

