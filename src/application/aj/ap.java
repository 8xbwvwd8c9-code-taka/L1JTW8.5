/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import bj.d;

public class ap
extends cv {
    private static final String a = "[C] C_ExitGhost";

    public ap(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        u pc = client.f();
        if (pc == null || !pc.bN()) {
            return;
        }
    }

    @Override
    public String a() {
        return a;
    }
}

