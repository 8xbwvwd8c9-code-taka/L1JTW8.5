/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.aa;
import aq.aq;
import aq.w;
import bf.a;
import bh.v;

public class f
extends a {
    private final int a = 4;
    private final v b = be.a().a(4);

    @Override
    public void a(aq.f _target, int timeSecs) {
    }

    @Override
    public void a(aq.f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof aq.f)) {
            return;
        }
        aq.f target = (aq.f)obj;
        w _magic = new w(_user, target);
        int dmg = _magic.b(4);
        _magic.a(dmg, 0);
        this.b(_user, target, this.b, dmg);
    }

    @Override
    public void a(aq.f cha) {
    }
}

