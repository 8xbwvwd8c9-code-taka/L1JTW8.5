/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;

public class cd
extends eu {
    public cd(u pc) {
        this.c(134);
        this.a(pc.et());
        this.a("");
        this.c(pc.ay());
        this.c(pc.aJ());
        this.b(pc.fa());
        this.b(pc.ew());
        this.b(pc.ex());
        this.c(pc.ey());
        this.c(pc.ev());
        this.c(pc.ez());
        this.c(pc.eB());
        this.c(pc.eA());
        this.c(pc.eE());
        this.c(pc.eC());
        this.c(pc.eD());
        this.c(0);
        this.a(pc.o());
        this.c((pc.ev() ^ pc.ez() ^ pc.eB() ^ pc.eA() ^ pc.eE() ^ pc.eC() ^ pc.eD()) & 0xFF);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_NewCharPacket";
    }
}

