/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.am;
import bj.d;

public class j
extends cv {
    public j(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        pc.aI(pc.fs());
        pc.aJ(pc.ft());
        pc.aK(pc.fp());
        pc.aL(pc.fb());
        am.a(pc);
    }

    @Override
    public String a() {
        return "C_Blink";
    }
}

