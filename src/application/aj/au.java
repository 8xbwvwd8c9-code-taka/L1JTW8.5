/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import be.aw;
import bj.d;

public class au
extends cv {
    private static final String a = "[C] C_FixWeaponList";

    public au(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        pc.a(new aw(pc));
    }

    @Override
    public String a() {
        return a;
    }
}

