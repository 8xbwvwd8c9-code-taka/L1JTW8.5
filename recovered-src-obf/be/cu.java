/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.b;
import ap.u;
import ap.v;
import be.eu;
import bi.g;

public class cu
extends eu {
    public cu(v pet, u pc) {
        this.c(1);
        this.b(119);
        b.e.a builder1 = b.e.aO();
        builder1.a((pet.ft() << 16) + pet.fs());
        builder1.b(pet.fr());
        builder1.c(pet.fe());
        builder1.d(pet.eY());
        builder1.e(pet.fb());
        builder1.f(pet.fh());
        builder1.g(pet.fc());
        builder1.h(0);
        builder1.e(g.a(pet.et()));
        builder1.f(g.a(""));
        builder1.i(pet.fc());
        builder1.j(pet.fd());
        builder1.k(0);
        builder1.l(0);
        builder1.m(pet.fn() == 2 ? 1 : 0);
        builder1.n(0);
        builder1.o(0);
        builder1.p(pet.fn() == 1 ? 1 : 0);
        builder1.q(0);
        builder1.g(g.a(""));
        String masterName = pet.M() == null ? "" : pet.M().et();
        builder1.h(g.a(masterName));
        builder1.r(0);
        if (pet.M() != null && pet.M().fr() == pc.fr()) {
            int percent = pet.ew() != 0 ? 100 * pet.ea() / pet.ew() : 100;
            builder1.s(percent);
        } else {
            builder1.s(255);
        }
        builder1.t(pet.ev());
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
        return "S_PetPack";
    }
}

