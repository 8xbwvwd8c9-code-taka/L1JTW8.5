/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import be.eu;

public class ag
extends eu {
    public ag(q item) {
        if (item != null) {
            this.c(112);
            this.a(item.fr());
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_DeleteInventoryItem";
    }
}

