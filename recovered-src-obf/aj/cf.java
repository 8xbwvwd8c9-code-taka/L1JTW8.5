/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import be.dz;
import bj.d;

public class cf
extends cv {
    public cf(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        t npc;
        u pc = client.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int objid = this.b();
        aa obj = aq.a().a(objid);
        if (obj instanceof t && (npc = (t)obj).z() == 70080) {
            pc.a(new dz(pc));
        }
    }

    @Override
    public String a() {
        return "C_SkillBuyItem";
    }
}

