/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.b;
import ap.t;
import be.eu;
import bi.g;

public class bo
extends eu {
    public bo(t npc) {
        this.c(1);
        this.b(119);
        b.e.a builder1 = b.e.aO();
        builder1.a((npc.ft() << 16) + npc.fs());
        builder1.b(npc.fr());
        builder1.c(npc.fe());
        builder1.d(npc.eY());
        builder1.e(npc.fb());
        builder1.f(npc.fh());
        builder1.g(npc.fc());
        builder1.h(npc.fa());
        builder1.e(g.a(npc.T()));
        builder1.f(g.a(""));
        builder1.i(npc.fc());
        builder1.j(npc.fd());
        builder1.k(0);
        builder1.l(0);
        builder1.m(npc.fn() == 2 ? 1 : 0);
        builder1.n(0);
        builder1.o(0);
        builder1.p(npc.fn() == 1 ? 1 : 0);
        builder1.q(0);
        builder1.g(g.a(npc.ap()));
        builder1.h(g.a(""));
        builder1.r(npc.ac());
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
        return "S_NPCPack";
    }
}

