/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import bf.a;
import bh.v;
import java.util.ArrayList;

public class ae
extends a {
    private final int a = 30;
    private final v b = be.a().a(30);

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
        int area = this.b.q();
        ArrayList<f> list = this.a(_user, target, area);
        for (f other : list) {
            w magic = new w(_user, other);
            int dmg = magic.b(30);
            magic.a(dmg, 0);
        }
        this.a(_user, this.b, list);
    }

    @Override
    public void a(f cha) {
    }
}

