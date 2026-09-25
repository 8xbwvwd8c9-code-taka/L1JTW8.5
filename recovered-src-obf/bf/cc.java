/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import bf.a;
import bh.v;

public class cc
extends a {
    private final int a = 80;
    private final v b = be.a().a(80);

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
        w _magic = new w(_user, target);
        int dmg = _magic.b(80);
        _magic.a(dmg, 0);
        this.b(_user, target, this.b, dmg);
    }

    @Override
    public void a(f cha) {
    }
}

