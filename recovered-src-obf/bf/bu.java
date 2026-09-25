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
import aq.w;
import be.ak;
import bf.a;
import bh.v;
import java.util.ArrayList;

public class bu
extends a {
    private final int a = 72;
    private final v b = be.a().a(72);

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
                if (!(obj instanceof f)) continue;
                f other = (f)obj;
                boolean isDamage = false;
                if (other instanceof u) {
                    u tpc = (u)other;
                    if (!tpc.aA() && tpc.ff()) {
                        tpc.s();
                        tpc.O();
                        if (!pc.a(pc, tpc, true)) {
                            isDamage = true;
                        }
                    }
                } else if (other instanceof s && (npc = (s)other).ac() == 1) {
                    npc.e(pc);
                    isDamage = true;
                }
                if (!isDamage) continue;
                w magic = new w(_user, other);
                int dmg = magic.b(72);
                magic.a(dmg, 0);
                if (dmg <= 0) continue;
                other.b(new ak(other.fr(), 2));
                if (!(other instanceof u)) continue;
                u tpc = (u)other;
                tpc.a(new ak(tpc.fr(), 2));
            }
            bi.a().b(pc);
        }
    }

    @Override
    public void a(f cha) {
    }
}

