/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.cn;
import be.cz;
import bf.a;
import bh.v;

public class ee
extends a {
    private final int a = 157;
    private final v b = be.a().a(157);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            _target.j(157, this.b.v() * 1000);
            this.a(_target, this.b);
            _target.V(true);
            if (_target instanceof u) {
                u tpc = (u)_target;
                tpc.a(new cz(tpc.fr(), 2));
                tpc.b(new cz(tpc.fr(), 2));
                tpc.a(new cn(4, true));
            } else if (_target instanceof t) {
                t npc = (t)_target;
                npc.b(new cz(npc.fr(), 2));
            }
        }
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
        boolean isSeccess = magic.a(157);
        if (isSeccess) {
            target.j(157, this.b.v() * 1000);
            this.a(target, this.b);
            target.V(true);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new cz(tpc.fr(), 2));
                tpc.b(new cz(tpc.fr(), 2));
                tpc.a(new cn(4, true));
            } else if (target instanceof t) {
                t npc = (t)target;
                npc.b(new cz(npc.fr(), 2));
            }
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        cha.V(false);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cz(pc.fr(), 0));
            pc.b(new cz(pc.fr(), 0));
            pc.a(new cn(4, false));
        } else if (cha instanceof t) {
            t npc = (t)cha;
            npc.b(new cz(npc.fr(), 0));
            npc.V(false);
        }
    }
}

