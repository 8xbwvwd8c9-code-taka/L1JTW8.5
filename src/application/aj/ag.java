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
        int castle_id;
        i clan;
        u player = clientthread.f();
        if (player == null) {
            return;
        }
        int objid = this.b();
        int count = this.b();
        if (objid == player.fr() && (clan = q.a().a(player.aF())) != null && (castle_id = clan.m()) != 0) {
            bh.d l1castle;
            bh.d d2 = l1castle = g.a().a(castle_id);
            synchronized (d2) {
                int money = l1castle.f();
                if (money + count >= 2000000000) {
                    player.a(new ei("\u5b58\u5165\u7684\u91d1\u5e63\u8d85\u904e\u4e862000000000\u4e0a\u9650"));
                    return;
                }
                if (player.j().b(40308, count)) {
                    l1castle.b(money + count);
                    g.a().a(l1castle);
                }
            }
        }
    }

    @Override
    public String a() {
        return "C_Deposit";
    }
}

