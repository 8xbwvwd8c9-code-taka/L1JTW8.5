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

public class br
extends cv {
    private static final String a = "[C] C_Propose";

    public br(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int c2 = this.c();
        if (c2 == 0) {
            if (pc.bN()) {
                return;
            }
            u target = g.a(pc, false);
            if (target != null) {
                if (pc.bD() != 0) {
                    pc.a(new ds(657));
                    return;
                }
                if (target.bD() != 0) {
                    pc.a(new ds(658));
                    return;
                }
                if (pc.aJ() == target.aJ()) {
                    pc.a(new ds(661));
                    return;
                }
                if (pc.fs() >= 33974 && pc.fs() <= 33976 && pc.ft() >= 33362 && pc.ft() <= 33365 && pc.fp() == 4 && target.fs() >= 33974 && target.fs() <= 33976 && target.ft() >= 33362 && target.ft() <= 33365 && target.fp() == 4) {
                    target.am(pc.fr());
                    target.a(new ca(654, pc.et()));
                }
            }
        } else if (c2 == 1) {
            if (pc.bD() == 0) {
                pc.a(new ds(662));
                return;
            }
            pc.a(new ca(653, ""));
        }
    }

    @Override
    public String a() {
        return a;
    }
}

