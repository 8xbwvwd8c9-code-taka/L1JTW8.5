/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.b;
import ap.ab;
import be.eu;
import bi.g;

public class eo
extends eu {
    public eo(ab trap, String name) {
        this.c(1);
        this.b(119);
        b.e.a builder1 = b.e.aO();
        builder1.a((trap.ft() << 16) + trap.fs());
        builder1.b(trap.fr());
        builder1.c(trap.g() ? trap.h() : 7);
        builder1.d(0);
        builder1.e(0);
        builder1.f(0);
        builder1.g(0);
        builder1.h(0);
        builder1.e(g.a(name));
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
        builder1.t(1);
        builder1.u(-1);
        builder1.v(0);
        builder1.w(0);
        builder1.y(-1);
        builder1.A(0);
        this.a(builder1.M().g());
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Trap";
    }
}

