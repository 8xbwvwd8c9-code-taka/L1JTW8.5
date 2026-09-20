/*
 * Decompiled with CFR 0.152.
 */
package be;

import an.e;
import ap.u;
import be.eu;
import bi.g;

public class ac
extends eu {
    public ac(u pc, boolean isTrue) {
        this.c(248);
        this.a(pc.fr());
        this.a(isTrue ? pc.aG() : "");
        this.a(0);
        this.c(0);
        this.c(isTrue ? 10 : 11);
        this.b(0);
    }

    public ac(u pc) {
        this.c(1);
        this.b(537);
        e.c.a builder = e.c.aa();
        builder.e(g.a(pc.aG()));
        builder.a(pc.aH());
        this.a(builder.M().g());
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ClanName";
    }
}

