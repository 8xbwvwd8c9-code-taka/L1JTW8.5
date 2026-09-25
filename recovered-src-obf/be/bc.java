/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.t;
import be.eu;
import java.io.IOException;

public class bc
extends eu {
    public bc(t npc, int price, int min, int max, String htmlId) {
        this.c(193);
        this.a(npc.fr());
        this.a(price);
        this.a(min);
        this.a(min);
        this.a(max);
        this.b(0);
        this.a(htmlId);
        this.c(0);
        this.b(2);
        this.a(npc.et());
        this.a(String.valueOf(price));
        this.b(0);
    }

    @Override
    public byte[] a() throws IOException {
        return this.d();
    }
}

