/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.ac;
import be.be;
import be.ds;
import bj.d;

public class bm
extends cv {
    public bm(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null || pc.bN()) {
            return;
        }
        ac party = pc.aL();
        if (pc.q()) {
            String result = "";
            for (u member : party.c()) {
                result = String.valueOf(result) + member.et() + " ";
            }
            pc.a(new be(pc.fr(), "party", party.a().et(), result));
        } else {
            pc.a(new ds(425));
        }
    }

    @Override
    public String a() {
        return "C_Party";
    }
}

