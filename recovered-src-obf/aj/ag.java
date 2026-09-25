/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.g;
import ao.q;
import ap.u;
import aq.i;
import be.ei;
import bj.d;

public class ag
extends cv {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ag(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u player = clientthread.f();
        if (player == null) {
            return;
        }
        int objid = this.b();
        int count = this.b();
        if (objid != player.fr() || count <= 0) {
            return;
        }
        i clan = q.a().a(player.aF());
        if (clan == null) {
            return;
        }
        int castleId = clan.m();
        if (castleId == 0) {
            return;
        }
        bh.d castle = g.a().a(castleId);
        if (castle == null) {
            return;
        }
        synchronized (castle) {
            if ((long)castle.f() + (long)count >= 2000000000L) {
                player.a(new ei("\u5b58\u5165\u7684\u91d1\u5e63\u8d85\u904e\u4e862000000000\u4e0a\u9650"));
                return;
            }
            g.a().transferTreasuryAdena(player, castleId, count, true);
        }
    }

    @Override
    public String a() {
        return "C_Deposit";
    }
}

