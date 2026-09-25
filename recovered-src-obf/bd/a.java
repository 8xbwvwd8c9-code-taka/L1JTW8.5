/*
 * Decompiled with CFR 0.152.
 */
package bd;

import bi.i;

class a {
    private final int a;

    public a(int faces) {
        this.a = faces;
    }

    public int a() {
        return this.a;
    }

    private int b() {
        return i.a(this.a) + 1;
    }

    public int a(int count) {
        int n2 = 0;
        int i2 = 0;
        while (i2 < count) {
            n2 += this.b();
            ++i2;
        }
        return n2;
    }
}

