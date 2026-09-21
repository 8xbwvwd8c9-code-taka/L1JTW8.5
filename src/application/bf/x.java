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
import java.util.ArrayList;

public class x
extends a {
    private final int a = 22;
    private final v b = be.a().a(22);

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
        ArrayList<f> list = this.a(_user, target, this.b.q());
        for (f other : list) {
            w _magic = new w(_user, other);
            int dmg = _magic.b(22);
            _magic.a(dmg, 0);
        }
        this.a(_user, this.b, list);
    }

    @Override
    public void a(f cha) {
    }
}

