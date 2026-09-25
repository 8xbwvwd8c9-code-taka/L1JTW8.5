/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import bj.d;

public class e
extends cv {
    public e(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int targetId = this.b();
        pc.bb(targetId);
    }
}

