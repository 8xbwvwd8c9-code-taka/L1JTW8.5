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
import bf.a;
import bh.v;

public class fq
extends a {
    private final int a = 203;
    private final v b = be.a().a(203);

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
        if (_user instanceof u) {
            u pc = (u)_user;
            target.a(pc, 203);
        }
        w _magic = new w(_user, target);
        _magic.a(_magic.b(203), 0);
        this.a(target, this.b);
    }

    @Override
    public void a(f cha) {
    }
}

