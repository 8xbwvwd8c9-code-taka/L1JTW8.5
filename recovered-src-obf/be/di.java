/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;

public class di
extends eu {
    public di(u target, u use, int type) {
        this.c(12);
        this.a(target.fr());
        this.c(type);
        this.a(use.fr());
        this.a(target.aB());
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Resurrection";
    }
}

