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

public class ff
extends a {
    private final int a = 188;
    private final v b = be.a().a(188);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        if (!_target.bB(188)) {
            _target.cB(5);
        }
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new cm(101, tpc.fl()));
        }
        _target.j(188, timeSecs * 1000);
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        w magic = new w(_user, target);
        boolean isSeccess = magic.a(188);
        if (isSeccess && !target.bB(188)) {
            target.cB(5);
            target.j(188, this.b.v() * 1000);
            this.a(target, this.b);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new cm(101, tpc.fl()));
            }
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        cha.cB(-5);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cm(101, pc.fl()));
        }
    }
}

