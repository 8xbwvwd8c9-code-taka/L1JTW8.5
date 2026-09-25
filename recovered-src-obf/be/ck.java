/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import at.c;
import be.eu;

public class ck
extends eu {
    public ck(u pc) {
        int time = c.a().b().c();
        time -= time % 300;
        this.c(132);
        this.a(pc.fr());
        this.c(pc.ev());
        this.a(pc.m());
        this.b(pc.ez());
        this.b(pc.eD());
        this.b(pc.eE());
        this.b(pc.eB());
        this.b(pc.eA());
        this.b(pc.eC());
        this.b(pc.ea());
        this.b(pc.ew());
        this.b(pc.eb());
        this.b(pc.ex());
        this.a(pc.ey());
        this.a(time);
        this.c(pc.fj());
        this.c(pc.j().h());
        this.b(pc.fa());
        this.b(pc.eH());
        this.b(pc.eG());
        this.b(pc.eF());
        this.b(pc.eI());
        this.a(pc.bM());
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_OwnCharStatus";
    }
}

