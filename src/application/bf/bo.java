/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.cn;
import be.ee;
import bf.a;
import bh.v;
import java.util.ArrayList;

public class bo
extends a {
    private final int a = 66;
    private final v b = be.a().a(66);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        this.b(_user, this.b);
        this.a(target, this.b);
        int area = this.b.q();
        ArrayList<f> list = this.a(_user, target, area);
        for (f other : list) {
            w magic = new w(_user, other);
            if (magic.a(66)) {
                other.j(66, this.b.v() * 1000);
                other.U(true);
                if (other instanceof u) {
                    u tpc = (u)other;
                    tpc.a(new cn(3, true));
                    tpc.a(new ee(tpc.fr(), this.b.u()));
                }
                other.b(new ee(other.fr(), this.b.u()));
                continue;
            }
            this.b(other, 297);
        }
    }

    @Override
    public void a(f cha) {
        cha.U(false);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cn(3, false));
        }
    }
}

