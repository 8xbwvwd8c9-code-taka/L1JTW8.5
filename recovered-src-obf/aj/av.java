/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.am;
import bj.d;

public class av
extends cv {
    public av(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int mapid = this.d();
        int x2 = this.d();
        int y2 = this.d();
        int heading = pc.h(x2, y2);
        am.a(pc, x2, y2, mapid, heading, true);
    }

    @Override
    public String a() {
        return "C_GMTeleport";
    }
}

