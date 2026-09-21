/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import be.ds;
import bj.d;

public class y
extends cv {
    private static final String a = "[C] C_CheckPK";

    public y(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u player = clientthread.f();
        if (player == null) {
            return;
        }
        player.a(new ds(562, String.valueOf(player.aD())));
    }

    @Override
    public String a() {
        return a;
    }
}

