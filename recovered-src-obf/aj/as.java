/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import bj.d;

public class as
extends cv {
    private static final String a = "[C] C_FishClick";

    public as(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null || pc.eX()) {
            return;
        }
        if (pc.cO() != null) {
            pc.cO().a();
        }
    }

    @Override
    public String a() {
        return a;
    }
}

