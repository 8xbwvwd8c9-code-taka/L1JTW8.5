/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import aq.f;
import be.eu;

public class z
extends eu {
    public z(u pc) {
        this.c(5);
        this.a(pc.fr());
        this.c(pc.k());
        this.b(65535);
        this.b(0);
    }

    public z(f cha, int status) {
        this.c(5);
        this.a(cha.fr());
        this.c(status);
        this.b(65535);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_CharVisualUpdate";
    }
}

