/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import bh.d;

public class bz
extends eu {
    public bz(d l1castle) {
        this.c(71);
        this.a(l1castle.a());
        this.a(l1castle.f());
        this.b(l1castle.k().size());
        int i2 = 0;
        while (i2 < l1castle.k().size()) {
            this.b(i2);
            this.a(l1castle.k().get((int)i2).b);
            this.b(4000);
            ++i2;
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_MercenaryEmpoly";
    }
}

