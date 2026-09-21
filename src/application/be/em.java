/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import be.eu;

public class em
extends eu {
    public em(q item, int count, int type) {
        this.c(123);
        this.c(type);
        this.b(item.a().m());
        this.a(item.c(count));
        if (!item.C()) {
            this.c(3);
            this.c(0);
        } else {
            this.c(item.F());
            byte[] status = item.t();
            this.c(status.length);
            this.a(status);
        }
        this.b(item.m());
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_TradeAddItem";
    }
}

