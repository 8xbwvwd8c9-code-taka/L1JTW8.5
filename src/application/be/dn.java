/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class dn
extends eu {
    public static int a = 1;
    public static int b = 2;
    public static int c = 4;
    public static int d = 8;
    public static int e = 16;
    public static int f = 32;
    public static int g = 64;
    public static int h = 128;

    public dn(int type) {
        this.c(42);
        this.c(type);
        this.a(1);
        this.c(60);
        this.b(0);
    }

    public dn(int type, int value) {
        this.c(42);
        this.c(type);
        this.a(1);
        this.c(value);
        this.a(new byte[32]);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_RuneSlot";
    }
}

