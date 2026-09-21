/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;

public class ai
extends eu {
    public ai(u pc, int type, int time) {
        this.c(124);
        this.b(time);
        this.c(pc.eB());
        this.c(type);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Dexup";
    }
}

