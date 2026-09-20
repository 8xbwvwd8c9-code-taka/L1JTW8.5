/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import be.dy;
import bj.d;

public class ce
extends cv {
    private static final String a = "[C] C_SkillBuy";

    public ce(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int i2 = this.b();
        pc.a(new dy(pc));
    }

    @Override
    public String a() {
        return a;
    }
}

