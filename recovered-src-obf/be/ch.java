/*
 * Decompiled with CFR 0.152.
 */
package be;

import am.c;
import an.b;
import ao.q;
import ap.u;
import aq.i;
import be.eu;
import bi.g;
import l1j.server.a;

public class ch
extends eu {
    public ch(u pc) {
        this.a(pc, false);
    }

    public ch(u pc, boolean isFindinvis) {
        this.a(pc, isFindinvis);
    }

    private void a(u pc, boolean isFindinvis) {
        int action;
        this.c(1);
        this.b(119);
        b.e.a builder1 = b.e.aO();
        builder1.a((pc.ft() << 16) + pc.fs());
        builder1.b(pc.fr());
        int gfxid = pc.eX() ? pc.cj() : pc.fe();
        int n2 = action = pc.eX() ? pc.eY() : pc.k();
        if (pc.cq()) {
            action = 71;
        } else if (pc.aX()) {
            action = 70;
        }
        if (!c.a().b(gfxid, action)) {
            action = 0;
        }
        builder1.c(gfxid);
        builder1.d(action);
        builder1.e(pc.fb());
        builder1.f(pc.fi());
        builder1.g(pc.fc());
        builder1.h(pc.fa());
        builder1.e(g.a(pc.et()));
        builder1.f(g.a(pc.eZ()));
        builder1.i(pc.fc());
        builder1.j(pc.fd());
        builder1.k(pc.L() ? 8 : (pc.aS() ? 1 : 0));
        builder1.l(isFindinvis ? 0 : (pc.ff() ? 1 : 0));
        builder1.m(pc.fn() == 2 ? 1 : 0);
        builder1.n(1);
        builder1.o(pc.bN() ? 1 : 0);
        builder1.p(pc.fn() == 1 ? 1 : 0);
        i clan = q.a().a(pc.aF());
        builder1.q(clan == null ? 0 : clan.i());
        builder1.g(g.a(pc.aG()));
        builder1.h(g.a(""));
        builder1.r(0);
        builder1.s(-1);
        builder1.t(0);
        builder1.i(g.a(""));
        builder1.u(-1);
        builder1.v(0);
        builder1.w(pc.aq());
        builder1.y(-1);
        builder1.A(a.a);
        if (pc.dX() > 0) {
            builder1.B(pc.dX());
        }
        this.a(builder1.M().g());
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_OtherCharPacks";
    }
}

