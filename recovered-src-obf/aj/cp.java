/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.ao;
import aq.aq;
import be.ds;
import bj.d;

public class cp
extends cv {
    public cp(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        u trading_partner = (u)aq.a().a(pc.aO());
        if (trading_partner != null) {
            pc.c(true);
            if (pc.aP() && trading_partner.aP()) {
                if (pc.j().c() < 180 - trading_partner.ax().c() && trading_partner.j().c() < 180 - pc.ax().c()) {
                    ao.a(pc);
                } else {
                    pc.a(new ds(263));
                    trading_partner.a(new ds(263));
                    ao.b(pc);
                }
            }
        }
    }

    @Override
    public String a() {
        return "C_TradeOK";
    }
}

