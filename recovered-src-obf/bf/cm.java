/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.ah;
import ao.be;
import ap.q;
import ap.u;
import aq.f;
import be.ds;
import bf.a;
import bh.v;
import bi.i;

public class cm
extends a {
    private final int a = 100;
    private final v b = be.a().a(100);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        u pc;
        q item;
        this.a(_user, this.b);
        this.b(_user, this.b);
        if (_user instanceof u && (item = (pc = (u)_user).j().e(_targetId)) != null) {
            int itemid = item.N();
            int dark = (int)(10.0 + (double)pc.ev() * 0.8 + (double)(pc.eE() - 6) * 1.2);
            int brave = (int)((double)dark / 2.1);
            int wise = (int)((double)brave / 2.0);
            int kayser = (int)((double)wise / 1.9);
            int run = i.a(100) + 1;
            int probability = 0;
            String msg = null;
            if (itemid == 40320) {
                probability = dark;
                msg = "$2475";
            } else if (itemid == 40321) {
                probability = brave;
                msg = "$2476";
            } else if (itemid == 40322) {
                probability = wise;
                msg = "$2477";
            } else if (itemid == 40323) {
                probability = kayser;
                msg = "$2478";
            } else {
                pc.a(new ds(79));
                return;
            }
            if (probability >= run) {
                ah.a(pc, itemid + 1, 1);
            } else {
                pc.a(new ds(280));
            }
            pc.j().b(item, 1);
        }
    }

    @Override
    public void a(f cha) {
    }
}

