/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class bv
extends eu {
    public bv(int currentmp, int maxmp) {
        this.c(48);
        if (currentmp < 0) {
            this.b(0);
        } else if (currentmp > Short.MAX_VALUE) {
            this.b(Short.MAX_VALUE);
        } else {
            this.b(currentmp);
        }
        if (maxmp < 1) {
            this.b(1);
        } else if (maxmp > Short.MAX_VALUE) {
            this.b(Short.MAX_VALUE);
        } else {
            this.b(maxmp);
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_MPUpdate";
    }
}

