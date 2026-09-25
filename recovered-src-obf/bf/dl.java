/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.am;
import aq.f;
import be.cn;
import be.ds;
import bf.a;
import bh.v;

public class dl
extends a {
    private final int a = 131;
    private final v b = be.a().a(131);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (pc.fq().j() || pc.l()) {
                am.a(pc, 33051, 32337, 4, 5, true);
            } else {
                pc.a(new ds(276));
                pc.a(new cn(7, true));
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

