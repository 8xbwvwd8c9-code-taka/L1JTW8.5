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

public class cm
extends cv {
    public cm(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null || pc.bN()) {
            return;
        }
        if (pc.ff()) {
            pc.a(new ds(334));
            return;
        }
        u target = g.a(pc, false);
        if (target == null) {
            return;
        }
        if (target.aX()) {
            pc.a(new ds(989));
            return;
        }
        if (target.aO() > 0) {
            if (target.aO() == pc.fr()) {
                pc.a(new ds(590));
            } else {
                pc.a(new ds(259));
            }
            return;
        }
        if (pc.aO() > 0) {
            pc.a(new ds(258));
            return;
        }
        if (target.ed() || target.eX()) {
            pc.a(new ds(256));
            return;
        }
        pc.al(target.fr());
        target.al(pc.fr());
        target.a(new ca(252, pc.et()));
    }

    @Override
    public String a() {
        return "C_Trade";
    }
}

