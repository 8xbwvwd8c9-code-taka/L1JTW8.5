/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.g;
import ao.q;
import ap.u;
import aq.i;
import bj.d;

public class ci
extends cv {
    private static final String a = "[C] C_TaxRate";

    public ci(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        int castle_id;
        i clan;
        u player = clientthread.f();
        if (player == null) {
            return;
        }
        int i2 = this.b();
        int j2 = this.c();
        if (i2 == player.fr() && (clan = q.a().a(player.aF())) != null && (castle_id = clan.m()) != 0) {
            bh.d l1castle = g.a().a(castle_id);
            if (j2 >= 10 && j2 <= 50) {
                l1castle.a(j2);
                g.a().a(l1castle);
            }
        }
    }

    @Override
    public String a() {
        return a;
    }
}

