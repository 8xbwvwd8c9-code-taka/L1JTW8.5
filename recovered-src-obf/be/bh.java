/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.c;
import an.i;
import ap.q;
import be.eu;
import bi.g;
import java.util.List;

public class bh
extends eu {
    public bh(List<q> items) {
        this.c(1);
        this.b(588);
        c.g.a builder = c.g.aa();
        for (q item : items) {
            i.c.a builder9 = i.c.ak();
            builder9.a(item.fr());
            builder9.b(item.m());
            builder9.c(item.fr());
            builder9.d(item.E());
            builder9.e(item.a().U());
            builder9.g(item.e());
            builder9.h(item.F());
            builder9.i(item.v());
            builder9.j(8);
            builder9.k(0);
            if (item.a().aP() == 28) {
                builder9.f(item.a().V() - 1);
                builder9.m(item.a().V() - 1);
                if (item.a().V() == 1) {
                    builder9.f(18);
                    builder9.m(18);
                }
            } else {
                builder9.f(item.I());
                builder9.m(item.G());
            }
            builder9.n(item.F() >= 128 ? 3 : (item.a().s() ? 7 : 2));
            builder9.e(g.a(item.r()));
            if (item.C()) {
                builder9.f(a.g.a(item.t()));
            }
            builder.e(builder9.M().f());
        }
        builder.b(1);
        this.a(builder.M().g());
        this.b(0);
    }

    public bh(List<q> items, int i2) {
        this.c(237);
        this.c(items.size());
        for (q item : items) {
            this.a(item.fr());
            this.b(item.m());
            this.c(item.a().U());
            if (item.a().aP() == 28) {
                this.c(item.a().V() - 1);
            } else {
                this.c(item.I());
            }
            this.b(item.e());
            this.c(item.F());
            this.a(item.E());
            this.c(item.v());
            this.a(item.r());
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
        }
    }

    @Override
    public byte[] a() {
        return this.aW.toByteArray();
    }

    @Override
    public String b() {
        return "S_InvList";
    }
}

