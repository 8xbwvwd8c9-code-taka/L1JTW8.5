/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class h
extends eu {
    private static final int a = 0;
    private static final int b = 1;

    public h(int x2, int y2, int direction, boolean isPassable) {
        this.c(99);
        this.b(x2);
        this.b(y2);
        this.c(direction);
        this.c(isPassable ? 0 : 1);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Attribute";
    }
}

