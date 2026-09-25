/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;

public class eb
extends eu {
    public static final int a = 113;
    public static final int b = 114;
    public static final int c = 116;
    public static final int d = 147;
    public static final int e = 148;
    public static final int f = 154;
    public static final int g = 155;
    public static final int h = 162;
    public static final int i = 165;
    public static final int j = 221;

    public eb(int type, int time, u pc) {
        this.c(121);
        this.c(22);
        this.c(type);
        this.b(time);
        this.a(pc.ey());
    }

    public eb(int type, int time, int curse) {
        this.c(121);
        this.c(22);
        this.c(type);
        this.b(time);
        this.c(curse);
    }

    @Override
    public byte[] a() {
        return this.d();
    }
}

