/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.ai;
import aq.am;
import aq.aq;
import aq.f;
import be.ca;
import be.cn;
import be.ds;
import bf.a;
import bh.c;
import bh.v;
import java.util.List;

public class br
extends a {
    private final int a = 69;
    private final v b = be.a().a(69);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (_user instanceof u) {
            u pc = (u)_user;
            boolean canTeleport = false;
            if (pc.fp() >= 101 && pc.fp() <= 110) {
                if (pc.j().f(pc.fp() + 640361)) {
                    canTeleport = true;
                } else if (pc.j().f(640472)) {
                    canTeleport = true;
                }
            }
            if (!pc.fq().i() && !canTeleport || pc.bB(230)) {
                this.b(_user, this.b);
                pc.a(new ds(276));
                pc.a(new cn(7, true));
                return;
            }
            c bookm = pc.l(_targetId);
            int mapid = 0;
            if (bookm != null) {
                x2 = bookm.d();
                y2 = bookm.e();
                mapid = bookm.h();
            } else {
                aq.u newLocation = pc.fu().a(200, true);
                x2 = newLocation.f();
                y2 = newLocation.g();
                mapid = newLocation.b();
            }
            if (pc.aF() != 0) {
                List<u> others = aq.a().c(pc, 3);
                for (u other : others) {
                    if (other.aF() != pc.aF()) continue;
                    other.a(new ca(744, new String[0]));
                    other.a(x2, y2, mapid);
                }
            }
            ai.a().a(this.b.t(), 5000, pc.fs(), pc.ft(), pc.fp());
            am.a(pc, x2, y2, mapid, 5, true);
        }
    }

    @Override
    public void a(f cha) {
    }
}

