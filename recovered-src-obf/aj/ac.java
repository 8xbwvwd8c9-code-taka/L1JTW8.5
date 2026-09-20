/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.f;
import ap.u;
import bj.d;

public class ac
extends cv {
    private static final String a = "[C] C_DelBuddy";

    public ac(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        String charName = this.g();
        f.a().a(pc.fr(), charName);
    }

    @Override
    public String a() {
        return a;
    }
}

