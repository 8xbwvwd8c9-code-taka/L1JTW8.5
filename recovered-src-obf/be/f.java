/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import bh.d;

public class f
extends eu {
    public f(d l1castle) {
        this.c(216);
        this.a(l1castle.a());
        this.b(l1castle.a());
        this.b(12);
        this.b(l1castle.l().size());
        int i2 = 0;
        while (i2 < l1castle.l().size()) {
            this.b(i2);
            this.a(l1castle.l().get((int)i2).b);
            ++i2;
        }
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ArcharArrange";
    }
}

