/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class da
extends eu {
    public da(int x2, int y2, int newMapid) {
        this.c(244);
        this.b(newMapid);
        this.b(x2 * 2);
        this.b(y2 + 32768);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Portal";
    }
}

