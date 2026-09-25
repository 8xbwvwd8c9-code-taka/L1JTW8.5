/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.c;
import ap.u;
import aq.aa;
import aq.aq;
import bj.d;

public class m
extends cv {
    public m(byte[] abyte0, d client) {
        super(abyte0);
        if (client == null) {
            return;
        }
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int objId = this.b();
        int topicNumber = this.b();
        aa obj = aq.a().a(objId);
        if (!(obj instanceof c)) {
            return;
        }
        if (pc.fp() != obj.fp() || pc.f(obj) > 3) {
            return;
        }
        c board = (c)obj;
        board.a(pc, topicNumber);
    }

    @Override
    public String a() {
        return "C_BoardPage";
    }
}

