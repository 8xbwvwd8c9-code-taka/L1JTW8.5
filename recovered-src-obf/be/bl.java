/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import be.eu;

public class bl
extends eu {
    public bl(q item) {
        this.c(198);
        this.a(item.fr());
        this.a(item.r());
        this.a(item.E());
        if (!item.C()) {
            this.c(0);
        } else {
            byte[] status = item.t();
            this.c(status.length);
            byte[] byArray = status;
            int n2 = status.length;
            int n3 = 0;
            while (n3 < n2) {
                byte b2 = byArray[n3];
                this.c(b2);
                ++n3;
            }
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ItemDesc";
    }
}

