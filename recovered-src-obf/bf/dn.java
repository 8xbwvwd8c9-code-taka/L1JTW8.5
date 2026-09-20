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
import be.ci;
import be.ds;
import bf.a;
import bh.v;

public class dn
extends a {
    private final int a = 133;
    private final v b = be.a().a(133);

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
        if (_user instanceof u) {
            u pc = (u)_user;
            w _magic = new w(_user, target);
            boolean isSuccess = _magic.a(133);
            if (isSuccess && !target.bB(133)) {
                int value = -50;
                if (pc.bC() == 1) {
                    target.cb(-50);
                } else if (pc.bC() == 2) {
                    target.ca(-50);
                } else if (pc.bC() == 4) {
                    target.bZ(-50);
                } else if (pc.bC() == 8) {
                    target.bY(-50);
                } else {
                    pc.a(new ds(79));
                    return;
                }
                target.cc(pc.bC());
                target.j(133, this.b.v() * 1000);
                if (target instanceof u) {
                    u tpc = (u)target;
                    tpc.a(new ci(tpc));
                }
                this.a(target, this.b);
            } else {
                pc.a(new ds(280));
            }
        }
    }

    @Override
    public void a(f cha) {
        int value = 50;
        if (cha.eJ() == 1) {
            cha.cb(50);
        } else if (cha.eJ() == 2) {
            cha.ca(50);
        } else if (cha.eJ() == 4) {
            cha.bZ(50);
        } else if (cha.eJ() == 8) {
            cha.bY(50);
        }
        cha.cc(0);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ci(pc));
        }
    }
}

