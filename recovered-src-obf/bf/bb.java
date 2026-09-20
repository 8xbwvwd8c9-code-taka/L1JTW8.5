/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import aq.w;
import be.ak;
import bf.a;
import bh.v;
import java.util.ArrayList;

public class bb
extends a {
    private final int a = 53;
    private final v b = be.a().a(53);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        int area = this.b.q();
        ArrayList<f> list = this.a(_user, _user, area);
        for (f other : list) {
            w magic = new w(_user, other);
            int dmg = magic.b(53);
            magic.a(dmg, 0);
            if (dmg <= 0) continue;
            other.b(new ak(other.fr(), 2));
            if (!(other instanceof u)) continue;
            u tpc = (u)other;
            tpc.a(new ak(tpc.fr(), 2));
        }
    }

    @Override
    public void a(f cha) {
    }
}

