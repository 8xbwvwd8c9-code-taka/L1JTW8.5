/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;
import bi.f;

public class ba
extends eu {
    private static final f a = new f(1, Short.MAX_VALUE);

    public ba(int currentHp, int maxHp) {
        this.a(currentHp, maxHp);
    }

    public ba(u pc) {
        this.a(pc.ea(), pc.ew());
    }

    private void a(int currentHp, int maxHp) {
        this.c(9);
        this.b(a.a(currentHp));
        this.b(a.a(maxHp));
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_HPUpdate";
    }
}

