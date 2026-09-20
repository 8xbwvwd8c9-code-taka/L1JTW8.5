/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.t;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import bf.a;
import bh.v;

public class bm
extends a {
    private final int a = 64;
    private final v b = be.a().a(64);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        if (_target instanceof t) {
            timeSecs = 5;
        }
        _target.j(64, timeSecs * 1000);
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
        if (_magic.a(64)) {
            int buffTime = this.b.v();
            if (target instanceof t) {
                buffTime = 5;
            }
            target.j(64, buffTime * 1000);
            this.a(target, this.b);
        }
    }

    @Override
    public void a(f cha) {
    }
}

