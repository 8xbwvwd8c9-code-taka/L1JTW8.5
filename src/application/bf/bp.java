/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.s;
import ap.u;
import aq.aa;
import aq.ae;
import aq.aq;
import aq.f;
import aq.w;
import be.ca;
import be.ds;
import bf.a;
import bh.v;
import bi.i;

public class bp
extends a {
    private final int a = 67;
    private final v b = be.a().a(67);

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
        w _magic = new w(_user, target);
        boolean isSeccess = _magic.a(67);
        if (target instanceof u && _user instanceof u) {
            u pc = (u)_user;
            u tpc = (u)target;
            if (tpc.fr() == pc.fr()) {
                isSeccess = true;
            }
            if (tpc.aF() != 0 && tpc.aF() == pc.aF()) {
                isSeccess = true;
            }
        }
        if (isSeccess) {
            int pid = i.a(ae.f.length);
            int polyId = ae.f[pid];
            if (target instanceof u) {
                u tpc = (u)target;
                if (tpc.j().h(20281)) {
                    tpc.a(new ca(180, ""));
                    tpc.t(true);
                } else {
                    ae.a(tpc, polyId, this.b.v(), 1);
                }
                if (tpc.fr() != _user.fr()) {
                    tpc.a(new ds(241, _user.et()));
                }
            } else if (target instanceof s) {
                s mob = (s)target;
                if (mob.ev() > 60) {
                    this.b(_user, 79);
                    return;
                }
                ae.a(mob, polyId, this.b.v(), 1);
            }
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        ae.b(cha);
    }
}

