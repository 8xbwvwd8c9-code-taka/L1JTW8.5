/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.am;
import bj.d;

public class cj
extends cv {
    public cj(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        am.a(pc);
    }

    @Override
    public String a() {
        return "C_Teleport";
    }
}

