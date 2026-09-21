/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.z;
import aq.aa;
import aq.aq;
import aq.f;
import be.ee;
import bf.a;
import bh.v;

public class ds
extends a {
    private final int a = 145;
    private final v b = be.a().a(145);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        if (target instanceof z) {
            z summon = (z)target;
            summon.b(new ee(summon.fr(), this.b.t()));
            summon.h();
        } else {
            this.b(_user, 79);
        }
    }

    @Override
    public void a(f cha) {
    }
}

