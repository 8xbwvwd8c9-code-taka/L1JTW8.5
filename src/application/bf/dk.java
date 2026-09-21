/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ck;
import bf.a;
import bh.v;

public class dk
extends a {
    private final int a = 130;
    private final v b = be.a().a(130);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.a(_user, this.b);
        this.b(_user, this.b);
        _user.i_(_user.eb() + 2);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new ck(pc));
        }
        this.b(_user, 702);
    }

    @Override
    public void a(f cha) {
    }
}

