/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import be.eu;

public class bj
extends eu {
    public bj(q item) {
        this.c(121);
        this.c(149);
        this.a(item.fr());
        this.c(item.v());
        this.c(24);
        this.c(0);
        this.b(0);
        this.b(0);
        if (item.a().aP() == 28) {
            this.c(item.a().V() - 1);
        } else {
            this.c(item.G());
        }
        this.a(item.fr());
        this.a(8);
        this.a(0);
        this.c(item.F() >= 128 ? 3 : (item.a().s() ? 7 : 2));
        if (item.a().aP() == 15) {
            this.a(7738);
        } else if (item.a().aP() == 18) {
            this.a(7739);
        } else if (item.a().aP() == 16) {
            this.a(7740);
        } else if (item.G() > 0) {
            int img = item.G() + 8042;
            this.a(Math.max(8042, Math.min(img, 8056)));
        } else {
            this.a(0);
        }
        this.c(0);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ItemAttribute";
    }
}

