/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import be.db;
import bj.d;

public class cc
extends cv {
    private static final String a = "[C] C_ShopList";

    public cc(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int type = this.c();
        int objectId = this.b();
        pc.a(new db(pc, objectId, type));
    }

    @Override
    public String a() {
        return a;
    }
}

