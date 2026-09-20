/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import be.ca;
import be.ds;
import bi.g;
import bj.d;

public class ar
extends cv {
    private static final String a = "[C] C_Fight";

    public ar(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        u pc = client.f();
        if (pc == null || pc.bN()) {
            return;
        }
        u target = g.a(pc, false);
        if (target != null && !target.ed()) {
            if (pc.cp() != 0) {
                pc.a(new ds(633));
                return;
            }
            if (target.cp() != 0) {
                target.a(new ds(634));
                return;
            }
            pc.aN(target.fr());
            target.aN(pc.fr());
            target.a(new ca(630, pc.et()));
        }
    }

    @Override
    public String a() {
        return a;
    }
}

