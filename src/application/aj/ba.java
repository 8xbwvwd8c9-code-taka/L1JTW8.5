/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.q;
import ap.u;
import aq.i;
import be.ca;
import be.ds;
import bi.g;
import bj.d;

public class ba
extends cv {
    public ba(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null || pc.bN()) {
            return;
        }
        u tpc = g.a(pc, true);
        if (tpc == null) {
            return;
        }
        if (tpc.aF() == 0) {
            pc.a(new ds(90, tpc.et()));
            return;
        }
        if (pc.aF() == tpc.aF()) {
            pc.a(new ds(1201));
            return;
        }
        if (!i.b(tpc.aH())) {
            pc.a(new ds(92, tpc.et()));
            return;
        }
        if (pc.aF() != 0) {
            if (pc.aH() == 4) {
                pc.a(new ds(1206));
                return;
            }
            i clan = q.a().a(pc.aF());
            if (clan == null) {
                return;
            }
            if (clan.m() != 0 || clan.n() != 0) {
                pc.a(new ds(665));
                return;
            }
            if (pc.aH() != 10) {
                pc.a(new ds(89));
                return;
            }
            if (tpc.aH() != 4) {
                if (tpc.aH() == 3) {
                    pc.a(new ds(2504));
                } else {
                    pc.a(new ds(2498));
                }
                return;
            }
        }
        tpc.am(pc.fr());
        tpc.a(new ca(97, pc.et()));
    }

    @Override
    public String a() {
        return "C_JoinClan";
    }
}

