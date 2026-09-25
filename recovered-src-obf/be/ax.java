/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.b;
import ap.k;
import be.eu;
import bi.g;

public class ax
extends eu {
    public ax(k follower) {
        this.c(1);
        this.b(119);
        b.e.a builder1 = b.e.aO();
        builder1.a((follower.ft() << 16) + follower.fs());
        builder1.b(follower.fr());
        builder1.c(follower.fe());
        builder1.d(follower.eY());
        builder1.e(follower.fb());
        builder1.f(follower.fh());
        builder1.g(follower.fc());
        builder1.h(follower.fa());
        builder1.e(g.a(follower.T()));
        builder1.f(g.a(""));
        builder1.i(follower.fc());
        builder1.j(follower.fd());
        builder1.k(0);
        builder1.l(0);
        builder1.m(follower.fn() == 2 ? 1 : 0);
        builder1.n(0);
        builder1.o(0);
        builder1.p(follower.fn() == 1 ? 1 : 0);
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
        return "S_FollowerPack";
    }
}

