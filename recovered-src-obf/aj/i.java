/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import be.ds;
import bj.d;

public class i
extends cv {
    private static final String a = "[C] C_BanParty";

    public i(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        u player = client.f();
        if (player == null) {
            return;
        }
        String s2 = this.g();
        if (!player.q() || player.aL() == null) {
            player.a(new ds(427));
            return;
        }
        if (!player.aL().e(player)) {
            player.a(new ds(427));
            return;
        }
        for (u member : player.aL().c()) {
            if (!member.et().toLowerCase().equals(s2.toLowerCase())) continue;
            player.aL().c(member);
            return;
        }
        player.a(new ds(426, s2));
    }

    @Override
    public String a() {
        return a;
    }
}

