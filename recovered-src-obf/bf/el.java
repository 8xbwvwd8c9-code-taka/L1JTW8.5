/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import aq.w;
import bf.a;
import bh.v;

public class el
extends a {
    private final int a = 164;
    private final v b = be.a().a(164);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (pc.q()) {
                for (u menber : pc.aL().c()) {
                    if (menber.eX() || !menber.fu().e(pc.fu())) continue;
                    w _magic = new w(_user, menber);
                    int heal = _magic.c(164);
                    if (menber.bB(73)) {
                        menber.a(_user, (double)heal * 0.3, true);
                        continue;
                    }
                    menber.a(menber.ea() + heal);
                }
            } else {
                w _magic = new w(_user, _user);
                int heal = _magic.c(164);
                if (pc.bB(73)) {
                    pc.a(_user, (double)heal * 0.3, true);
                    return;
                }
                _user.a(_user.ea() + heal);
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

