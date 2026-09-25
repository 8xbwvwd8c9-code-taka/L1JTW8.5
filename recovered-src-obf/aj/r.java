/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import be.p;
import bj.d;

public class r
extends cv {
    private static final String a = "[C] C_ChangeHeading";

    public r(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int heading = this.c();
        if (heading < 0 || heading >= 8) {
            return;
        }
        pc.ct(heading);
        if (!pc.aA() && !pc.bN()) {
            if (pc.ff()) {
                pc.c(new p(pc));
            } else {
                pc.b(new p(pc));
            }
        }
    }

    @Override
    public String a() {
        return a;
    }
}

