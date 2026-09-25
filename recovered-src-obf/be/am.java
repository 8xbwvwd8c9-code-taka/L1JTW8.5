/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.b;
import ap.e;
import be.eu;
import bi.g;

public class am
extends eu {
    public am(e doll) {
        this.c(1);
        this.b(119);
        b.e.a builder1 = b.e.aO();
        builder1.a((doll.ft() << 16) + doll.fs());
        builder1.b(doll.fr());
        builder1.c(doll.fe());
        builder1.d(doll.eY());
        builder1.e(doll.fb());
        builder1.f(0);
        builder1.g(doll.fc());
        builder1.h(0);
        builder1.e(g.a(doll.T()));
        builder1.f(g.a(""));
        builder1.i(doll.fc());
        builder1.j(doll.fd());
        builder1.k(0);
        builder1.l(0);
        builder1.m(0);
        builder1.n(0);
        builder1.o(0);
        builder1.p(0);
        builder1.q(0);
        builder1.g(g.a(""));
        String masterName = doll.M() == null ? "" : doll.M().et();
        builder1.h(g.a(masterName));
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
        return "S_DollPack";
    }
}

