/*
 * Decompiled with CFR 0.152.
 */
package au;

import ap.q;
import ap.u;
import aq.aq;
import au.f;
import be.ap;
import be.dh;

public class e
extends f {
    public e(int objectId, int x2, int y2, int i2) {
        this.cF(objectId);
        this.cG(x2);
        this.cH(y2);
        this.cE(i2);
        aq.a().c(this);
    }

    @Override
    public void b(u perceivedFrom) {
        for (q item : this.d()) {
            if (perceivedFrom.b(item)) continue;
            perceivedFrom.c(item);
            perceivedFrom.a(new ap(item));
        }
    }

    @Override
    public void a(q item) {
        for (u pc : aq.a().f(item)) {
            pc.a(new ap(item));
            pc.c(item);
        }
    }

    @Override
    public void b(q item) {
        for (u pc : aq.a().f(item)) {
            pc.a(new ap(item));
        }
    }

    @Override
    public void c(q item) {
        for (u pc : aq.a().f(item)) {
            pc.a(new dh(item));
            pc.d(item);
        }
        this.a.remove(item);
        if (this.a.isEmpty()) {
            aq.a().d(this);
        }
    }
}

