/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import be.eu;

public class bk
extends eu {
    public bk(q item) {
        if (item == null) {
            return;
        }
        this.a(item);
    }

    private void a(q item) {
        this.c(18);
        this.a(item.fr());
        this.c(item.F());
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ItemColor";
    }
}

