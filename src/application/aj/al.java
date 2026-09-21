/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import be.ar;
import bj.d;

public class al
extends cv {
    public al(byte[] abyte0, d clientthread) {
        super(abyte0);
        int emblemId = this.b();
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        pc.a(new ar(emblemId));
    }

    @Override
    public String a() {
        return "C_EmblemDownload";
    }
}

