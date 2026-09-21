/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ao.q;
import ap.t;
import ap.u;
import aq.f;
import aq.i;
import aq.w;
import bf.a;
import bh.v;

public class ax
extends a {
    private final int a = 49;
    private final v b = be.a().a(49);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (pc.aF() != 0) {
                i clan = q.a().a(pc.aF());
                for (u menber : clan.b()) {
                    if (!menber.fu().e(pc.fu()) || menber.eX() || menber.fr() == pc.fr() || !pc.i(menber.fs(), menber.ft())) continue;
                    w _magic = new w(pc, menber);
                    int heal = _magic.c(49);
                    if (menber.bB(73)) {
                        menber.a(_user, (double)heal * 0.3, true);
                        continue;
                    }
                    menber.a(menber.ea() + heal);
                }
            }
            for (t pet : pc.ek().values()) {
                if (!pet.fu().e(pc.fu()) || pet.eX() || !pc.i(pet.fs(), pet.ft())) continue;
                w _magic = new w(pc, pet);
                int heal = _magic.c(49);
                pet.a(pet.ea() + heal);
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

