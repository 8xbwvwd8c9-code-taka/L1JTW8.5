/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.aq;
import be.ds;
import be.et;
import bj.d;
import l1j.server.a;

public class cu
extends cv {
    public cu(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        String s2 = this.g();
        u find = aq.a().a(s2);
        if (find != null) {
            pc.a(new et(find));
        } else if (a.af) {
            String amount = String.valueOf(aq.a().c().size());
            pc.a(new ds(81, amount));
        }
    }

    @Override
    public String a() {
        return "C_Who";
    }
}

