/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class ei
extends eu {
    public ei(String msg) {
        this.c(107);
        this.c(9);
        this.a(msg);
    }

    public ei(String msg, boolean nameid) {
        this.c(27);
        this.c(2);
        this.a(0);
        this.a(msg);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_SystemMessage";
    }
}

