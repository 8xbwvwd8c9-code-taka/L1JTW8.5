/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import bj.d;

public class bd
extends cv {
    private static final String a = "[C] C_LeaveParty";

    public bd(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        u player = client.f();
        if (player == null) {
            return;
        }
        if (player.q()) {
            player.aL().b(player);
        }
    }

    @Override
    public String a() {
        return a;
    }
}

