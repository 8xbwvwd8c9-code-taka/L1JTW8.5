/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.t;
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

public class fv
extends a {
    private final int a = 208;
    private final v b = be.a().a(208);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            timeSecs = this.b.v();
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        int frztime = timeSecs * 1000 - i.a(1000);
        _target.V(true);
        if (_target instanceof u) {
            u tpc = (u)_target;
            tpc.a(new cn(5, true));
        }
        _target.j(208, frztime);
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
            target.a(pc, 208);
            ai.a().a(this.b.t(), 2500, target.fs(), target.ft(), target.fp());
        }
        if (target.bB(208)) {
            this.b(_user, 280);
            return;
        }
        w _magic = new w(_user, target);
        boolean isSuccess = _magic.a(208);
        if (isSuccess) {
            int frztime = this.b.v() * 1000 - i.a(1000);
            target.j(208, frztime);
            target.V(true);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new cn(5, true));
            }
        }
    }

    @Override
    public void a(f cha) {
        cha.V(false);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cn(5, false));
        } else if (cha instanceof t) {
            t npc = (t)cha;
            npc.V(false);
        }
    }
}

