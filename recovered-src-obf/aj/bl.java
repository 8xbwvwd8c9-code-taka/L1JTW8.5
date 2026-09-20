/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.aa;
import aq.aq;
import bj.d;

public class bl
extends cv {
    public bl(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int objid = this.b();
        aa obj = aq.a().a(objid);
        if (obj != null) {
            obj.a(pc);
        }
    }

    @Override
    public String a() {
        return "C_NpcTalk";
    }
}

