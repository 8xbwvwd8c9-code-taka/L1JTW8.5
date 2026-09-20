/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import as.e;
import as.g;
import be.ak;
import bj.d;

public class aq
extends cv {
    private static final String a = "[C] C_ExtraCommand";

    public aq(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        int gfxId;
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int actionId = this.c();
        if (pc.bN()) {
            return;
        }
        if (pc.ff()) {
            return;
        }
        if (pc.aR()) {
            return;
        }
        if (pc.fp() == 9101 && (pc.l() || pc.am())) {
            g.a().b = actionId;
            return;
        }
        if (pc.fp() == 9000 && (pc.l() || pc.am())) {
            e.a().a = actionId;
            return;
        }
        if (pc.bB(67) && (gfxId = pc.fe()) != 6080 && gfxId != 6094) {
            return;
        }
        ak gfx = new ak(pc.fr(), actionId);
        pc.b(gfx);
    }

    @Override
    public String a() {
        return a;
    }
}

