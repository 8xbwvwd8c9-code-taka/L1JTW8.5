/*
 * Decompiled with CFR 0.152.
 */
package be;

import aq.u;
import be.eu;

public class aq
extends eu {
    public aq(u loc, int gfxId) {
        this(loc.f(), loc.g(), gfxId);
    }

    public aq(int x2, int y2, int gfxId) {
        this.c(83);
        this.b(x2);
        this.b(y2);
        this.b(gfxId);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_EffectLocation";
    }
}

