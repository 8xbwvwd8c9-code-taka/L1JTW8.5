/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.f;
import ap.u;
import be.dc;
import bj.d;

public class p
extends cv {
    public p(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        aq.d buddy = f.a().a(pc.fr());
        pc.a(new dc(337, buddy.c()));
    }

    @Override
    public String a() {
        return "C_Buddy";
    }
}

