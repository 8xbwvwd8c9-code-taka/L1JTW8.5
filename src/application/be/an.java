/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.b;
import ap.f;
import be.eu;
import bi.g;

public class an
extends eu {
    public an(f door) {
        this.c(1);
        this.b(119);
        b.e.a builder1 = b.e.aO();
        builder1.a((door.ft() << 16) + door.fs());
        builder1.b(door.fr());
        builder1.c(door.fe());
        int doorStatus = door.eY();
        int openStatus = door.o();
        if (door.eX()) {
            builder1.d(doorStatus);
        } else if (openStatus == 28) {
            builder1.d(openStatus);
        } else if (door.ew() > 1 && doorStatus != 0) {
            builder1.d(doorStatus);
        } else {
            builder1.d(openStatus);
        }
        builder1.e(0);
        builder1.f(0);
        builder1.g(1);
        builder1.h(0);
        builder1.e(g.a(""));
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
        builder1.t(4);
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
        return "S_DoorPack";
    }
}

