/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.cm;
import bf.a;
import bh.v;

public class bv
extends a {
    private final int a = 73;
    private final v b = be.a().a(73);

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
        if (_magic.a(73)) {
            target.j(73, this.b.v() * 1000);
            this.a(target, this.b);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new cm(86, 92, this.b.v() + 1));
            }
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cm(86, 92, 0));
        }
    }
}

