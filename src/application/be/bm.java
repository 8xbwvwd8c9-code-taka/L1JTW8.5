/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import be.eu;

public class bm
extends eu {
    public bm(q item) {
        if (item == null) {
            return;
        }
        this.c(14);
        this.a(item.fr());
        this.a(item.r());
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ItemName";
    }
}

