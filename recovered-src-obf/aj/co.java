/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.ao;
import bj.d;

public class co
extends cv {
    public co(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u player = clientthread.f();
        if (player == null) {
            return;
        }
        ao.b(player);
    }

    @Override
    public String a() {
        return "C_TradeCancel";
    }
}

