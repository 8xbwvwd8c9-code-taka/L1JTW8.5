/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.a;
import be.eu;
import l1j.server.a;

public class bx
extends eu {
    public bx(int mapid, boolean isUnderwater) {
        this.c(1);
        this.b(118);
        a.a.a builder1 = a.a.aa();
        builder1.a(mapid);
        builder1.b(a.a);
        builder1.c(isUnderwater ? 1 : 0);
        builder1.d(0);
        builder1.e(0);
        builder1.f(0);
        this.a(builder1.M().g());
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_MapID";
    }
}

