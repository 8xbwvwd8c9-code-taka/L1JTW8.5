/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.c;
import aq.aa;
import aq.aq;
import bj.d;

public class n
extends cv {
    private static final String a = "[C] C_BoardRead";

    public n(byte[] decrypt, d client) {
        super(decrypt);
        int objId = this.b();
        int topicNumber = this.b();
        aa obj = aq.a().a(objId);
        c board = (c)obj;
        board.b(client.f(), topicNumber);
    }

    @Override
    public String a() {
        return a;
    }
}

