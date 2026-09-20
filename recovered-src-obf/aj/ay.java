/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.am;
import bj.d;

public class ay
extends cv {
    public ay(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        am.a(pc);
    }

    @Override
    public String a() {
        return "C_GotoPortal";
    }
}

