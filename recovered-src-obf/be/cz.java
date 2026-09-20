/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class cz
extends eu {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;

    public cz(int objId, int type) {
        this.c(175);
        this.a(objId);
        if (type == 0) {
            this.c(0);
            this.c(0);
        } else if (type == 1) {
            this.c(1);
            this.c(0);
        } else if (type == 2) {
            this.c(0);
            this.c(1);
        } else {
            throw new IllegalArgumentException("\u4e0d\u6b63\u306a\u5f15\u6570\u3067\u3059\u3002type = " + type);
        }
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Poison";
    }
}

