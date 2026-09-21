/*
 * Decompiled with CFR 0.152.
 */
package az;

import ap.u;
import aq.f;
import az.c;
import be.cm;

public class d
extends c {
    private final f a;

    public static boolean b(f cha, int maxTime) {
        if (!c.a(cha)) {
            return false;
        }
        cha.a(new d(cha, maxTime));
        return true;
    }

    private d(f cha, int maxTime) {
        this.a = cha;
        this.a(maxTime);
    }

    private void a(int maxTime) {
        this.a.y(1);
        d.a(this.a, 310);
        this.a.j(1007, maxTime * 1000);
        if (this.a instanceof u) {
            u pc = (u)this.a;
            pc.a(new cm(161, 6, maxTime));
        }
    }

    @Override
    public int a() {
        return 1;
    }

    @Override
    public void b() {
        this.a.bz(1007);
    }
}

