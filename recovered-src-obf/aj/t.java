/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.i;
import ap.u;
import bj.d;

public class t
extends cv {
    public t(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int length = this.b();
        byte[] data = this.a(length);
        if (length <= 0) {
            return;
        }
        i.a().a(pc.fr(), data);
    }

    @Override
    public String a() {
        return "C_CharcterConfig";
    }
}

