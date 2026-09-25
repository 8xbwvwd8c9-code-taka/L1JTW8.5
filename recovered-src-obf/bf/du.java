/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ci;
import bf.a;
import bh.v;

public class du
extends a {
    private final int a = 147;
    private final v b = be.a().a(147);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u && !_user.bB(147)) {
            u pc = (u)_user;
            int attr = pc.bC();
            if (attr == 1) {
                pc.cb(50);
            } else if (attr == 2) {
                pc.ca(50);
            } else if (attr == 4) {
                pc.bZ(50);
            } else if (attr == 8) {
                pc.bY(50);
            }
            pc.a(new ci(pc));
        }
        _user.j(147, this.b.v() * 1000);
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            int attr = pc.bC();
            if (attr == 1) {
                cha.cb(-50);
            } else if (attr == 2) {
                cha.ca(-50);
            } else if (attr == 4) {
                cha.bZ(-50);
            } else if (attr == 8) {
                cha.bY(-50);
            }
            pc.a(new ci(pc));
        }
    }
}

