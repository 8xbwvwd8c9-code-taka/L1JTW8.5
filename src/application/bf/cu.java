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

public class cu
extends a {
    private final int a = 108;
    private final v b = be.a().a(108);

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
        this.a(target, this.b);
        w magic = new w(_user, target);
        int dmg = magic.b(108);
        magic.a(dmg, 0);
        _user.a(100);
        _user.i_(1);
    }

    @Override
    public void a(f cha) {
    }
}

