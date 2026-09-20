/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.b;
import ap.y;
import be.eu;
import bi.g;

public class dw
extends eu {
    public dw(y signboard) {
        this.c(1);
        this.b(119);
        b.e.a builder1 = b.e.aO();
        builder1.a((signboard.ft() << 16) + signboard.fs());
        builder1.b(signboard.fr());
        builder1.c(signboard.fe());
        builder1.d(0);
        builder1.e(this.e(signboard.fb()));
        builder1.f(0);
        builder1.g(0);
        builder1.h(0);
        builder1.e(g.a(""));
        builder1.f(g.a(signboard.T()));
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

    private int e(int heading) {
        int dir = 0;
        switch (heading) {
            case 2: {
                dir = 1;
                break;
            }
            case 3: {
                dir = 2;
                break;
            }
            case 4: {
                dir = 3;
                break;
            }
            case 6: {
                dir = 4;
                break;
            }
            case 7: {
                dir = 5;
            }
        }
        return dir;
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_SignboardPack";
    }
}

