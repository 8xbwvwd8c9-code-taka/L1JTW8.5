/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.b;
import ap.c;
import aq.aa;
import aq.aq;
import bj.d;

public class k
extends cv {
    private static final String a = "[C] C_Board";

    private boolean a(aa obj) {
        return obj instanceof c || obj instanceof b;
    }

    public k(byte[] abyte0, d client) {
        super(abyte0);
        int objectId = this.b();
        aa obj = aq.a().a(objectId);
        if (!this.a(obj)) {
            return;
        }
        obj.c(client.f());
    }

    @Override
    public String a() {
        return a;
    }
}

