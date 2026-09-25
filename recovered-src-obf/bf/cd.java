/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.ai;
import aq.aq;
import aq.f;
import aq.w;
import be.cn;
import bf.a;
import bh.v;
import bi.i;

public class cd
extends a {
    private final int a = 87;
    private final v b = be.a().a(87);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            int time = 3000;
            if (_target.bB(87)) {
                return;
            }
            _target.j(87, 3000);
            ai.a().a(11727, 3000, _target.fs(), _target.ft(), _target.fp());
            _target.V(true);
            if (_target instanceof u) {
                u pc = (u)_target;
                pc.a(new cn(5, true));
            }
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        w magic = new w(_user, target);
        boolean isSuccess = magic.a(87);
        if (isSuccess) {
            int[] stunTimeArray = new int[]{1000, 1500, 2000, 2500, 3000, 3500};
            int rnd = i.a(stunTimeArray.length);
            int time = this.b.v() * 1000 + stunTimeArray[rnd];
            if (target.bB(87)) {
                time += target.bC(87) * 1000;
            }
            target.j(87, time);
            ai.a().a(this.b.t(), time, target.fs(), target.ft(), target.fp());
            target.V(true);
            if (target instanceof u) {
                u pc = (u)target;
                pc.a(new cn(5, true));
            }
            if (_user instanceof u) {
                target.c((u)_user);
            }
        }
    }

    @Override
    public void a(f cha) {
        cha.V(false);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cn(5, false));
        }
    }
}

