/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class ep
extends eu {
    public ep(int targetId, boolean isEffect) {
        this.c(121);
        this.c(194);
        this.a(targetId);
        this.a(12299);
        this.a(isEffect ? 1 : 0);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_TrueTarget";
    }
}

