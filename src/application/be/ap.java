/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.b;
import ao.af;
import ap.q;
import be.eu;
import bi.g;
import l1j.server.a;

public class ap
extends eu {
    public ap(q item) {
        int itemId;
        boolean isId;
        String itemName = item.a().i();
        boolean bl2 = isId = item.C();
        if (isId) {
            itemName = item.a().j();
        }
        this.c(1);
        this.b(119);
        b.e.a builder1 = b.e.aO();
        builder1.a((item.ft() << 16) + item.fs());
        builder1.b(item.fr());
        builder1.c(item.a().n());
        builder1.d(0);
        builder1.e(0);
        builder1.f(item.T() ? item.a().c() : 0);
        builder1.g(item.E());
        builder1.h(0);
        String viewName = "";
        viewName = item.E() > 1 ? (item.N() == 40312 && item.M() != 0 ? String.valueOf(itemName) + af.a(item) + " (" + item.E() + ")" : String.valueOf(itemName) + " (" + item.E() + ")") : ((itemId = item.N()) == 20383 && isId ? String.valueOf(itemName) + " [" + item.I() + "]" : (item.I() != 0 && isId ? String.valueOf(itemName) + " (" + item.I() + ")" : (item.a().c() != 0 && item.T() ? String.valueOf(itemName) + " ($10)" : (item.N() == 40312 && item.M() != 0 ? String.valueOf(itemName) + af.a(item) : itemName))));
        builder1.e(g.a(viewName));
        builder1.f(g.a(""));
        builder1.i(0);
        builder1.j(0);
        builder1.k(0);
        builder1.l(0);
        builder1.m(0);
        builder1.n(0);
        builder1.o(0);
        builder1.p(0);
        builder1.q(0);
        builder1.g(g.a(""));
        builder1.h(g.a(""));
        builder1.r(0);
        builder1.s(-1);
        builder1.t(0);
        builder1.u(-1);
        builder1.v(0);
        builder1.w(0);
        builder1.y(-1);
        builder1.A(a.a);
        this.a(builder1.M().g());
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_DropItem";
    }
}

