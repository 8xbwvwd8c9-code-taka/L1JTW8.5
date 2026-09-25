/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.q;
import ap.u;
import aq.i;
import be.ac;
import be.cm;
import be.cy;
import be.ds;
import bj.d;

public class aa
extends cv {
    private static final String a = "[C] C_CreateClan";

    public aa(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        String s2 = this.g();
        if (pc.x()) {
            if (pc.aF() == 0) {
                for (i clan : q.a().b().values()) {
                    if (!clan.f().toLowerCase().equals(s2.toLowerCase())) continue;
                    pc.a(new ds(99));
                    return;
                }
                if (pc.j().g(40308, 30000)) {
                    i clan = q.a().createClanAtomic(pc, s2);
                    if (clan != null) {
                        pc.a(new ds(84, s2));
                        pc.a(new ac(pc, true));
                        pc.a(new cm(173, clan.j()));
                        pc.a(new cy(clan));
                    }
                } else {
                    pc.a(new ds(189));
                }
            } else {
                pc.a(new ds(86));
            }
        } else {
            pc.a(new ds(85));
        }
    }

    @Override
    public String a() {
        return a;
    }
}

