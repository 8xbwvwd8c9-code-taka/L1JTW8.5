/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ao.q;
import ap.u;
import aq.f;
import aq.i;
import be.ca;
import bf.a;
import bh.v;

public class df
extends a {
    private final int a = 119;
    private final v b = be.a().a(119);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (pc.aF() == 0 || !pc.fq().k()) {
                return;
            }
            try {
                Thread.sleep(1300L);
                pc.a(0);
                pc.b((f)null);
            }
            catch (Exception e2) {
                return;
            }
            i clan = q.a().a(pc.aF());
            for (u menber : clan.b()) {
                if (!menber.eX() || _user.fr() == menber.fr()) continue;
                menber.k(false);
                menber.am(_user.fr());
                menber.a(new ca(322, new String[0]));
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

