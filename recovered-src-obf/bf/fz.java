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
import be.ds;
import be.ee;
import bf.a;
import bh.v;

public class fz
extends a {
    private final int a = 212;
    private final v b = be.a().a(212);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (_user instanceof u) {
            u pc = (u)_user;
            aa obj = aq.a().a(_targetId);
            if (!(obj instanceof f)) {
                pc.a(new ds(79));
                return;
            }
            f target = (f)obj;
            w _magic = new w(_user, target);
            int dmg = _magic.b(212);
            _magic.a(dmg, 0);
            this.a(_user, target, this.b, dmg);
            pc.a(new ee(target.fr(), this.b.u()));
            pc.b(new ee(target.fr(), this.b.u()));
            if (target.bB(66)) {
                return;
            }
            boolean isSuccess = _magic.a(212);
            if (isSuccess) {
                this.a(target, this.b);
                target.j(66, this.b.v() * 1000);
                if (target instanceof u) {
                    u tpc = (u)target;
                    tpc.a(new cn(3, true));
                } else if (target instanceof t) {
                    t npc = (t)target;
                    npc.U(true);
                }
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

