/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.q;
import ap.u;
import aq.aa;
import aq.aq;
import bj.d;

public class at
extends cv {
    public at(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int itemObjectId = this.b();
        int npcObjectId = this.b();
        aa obj = aq.a().a(npcObjectId);
        if (obj == null) {
            return;
        }
        if (obj.fu().c(pc.fu()) > 11) {
            return;
        }
        q item = pc.j().e(itemObjectId);
        if (item == null) {
            return;
        }
        int cost = item.H() * 200;
        if (!pc.j().b(40308, cost)) {
            return;
        }
        item.b(0);
        pc.j().b(item);
    }

    @Override
    public String a() {
        return "C_FixWeapon";
    }
}

