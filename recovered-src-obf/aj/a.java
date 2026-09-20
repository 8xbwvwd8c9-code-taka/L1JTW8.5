/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.e;
import aq.r;
import be.ds;
import bh.c;
import bj.d;

public class a
extends cv {
    public a(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null || pc.bN()) {
            return;
        }
        String s2 = this.g();
        if (!pc.fq().h()) {
            pc.a(new ds(214));
            return;
        }
        if (!c.a(pc.fs(), pc.ft(), pc.fp())) {
            pc.a(new ds(214));
            return;
        }
        if (e.b(pc.fs(), pc.ft(), pc.fp()) || r.a(pc.fs(), pc.ft(), pc.fp())) {
            pc.a(new ds(214));
            return;
        }
        c.b(pc, s2);
    }

    @Override
    public String a() {
        return "C_AddBookmark";
    }
}

