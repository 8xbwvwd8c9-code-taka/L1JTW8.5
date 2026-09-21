/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.n;
import be.cm;
import be.ds;
import bj.d;

public class ao
extends cv {
    public ao(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        String name = this.g();
        if (name.isEmpty()) {
            return;
        }
        n exList = pc.cd();
        if (exList.b()) {
            pc.a(new ds(472));
            return;
        }
        if (exList.c(name)) {
            String temp = exList.b(name);
            pc.a(new cm(19, 0, temp));
        } else {
            exList.a(name);
            pc.a(new cm(18, 0, name));
        }
    }

    @Override
    public String a() {
        return "C_Exclude";
    }
}

