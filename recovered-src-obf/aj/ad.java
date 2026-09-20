/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import bh.c;
import bj.d;

public class ad
extends cv {
    private static final String a = "[C] C_DeleteBookmark";

    public ad(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        String bookmarkname = this.g();
        c.a(pc, bookmarkname);
    }

    @Override
    public String a() {
        return a;
    }
}

