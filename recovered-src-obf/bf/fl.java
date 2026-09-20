/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ao.bi;
import ap.s;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import bf.a;
import bh.v;
import java.util.ArrayList;

public class fl
extends a {
    private final int a = 194;
    private final v b = be.a().a(194);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            if (!pc.aA() && pc.ff()) {
                pc.s();
                pc.O();
            }
            ArrayList<aa> objects = aq.a().b((aa)_user, this.b.q());
            for (aa obj : objects) {
                s npc;
                if (obj instanceof u) {
                    u tpc = (u)obj;
                    if (tpc.aA() || !tpc.ff()) continue;
                    tpc.s();
                    tpc.O();
                    continue;
                }
                if (!(obj instanceof s) || (npc = (s)obj).ac() != 1) continue;
                npc.e(pc);
            }
            bi.a().b(pc);
        }
    }

    @Override
    public void a(f cha) {
    }
}

