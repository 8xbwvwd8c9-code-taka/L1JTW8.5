/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.o;
import ao.p;
import ao.q;
import ap.u;
import aq.aq;
import aq.i;
import be.ds;
import bj.d;
import java.util.logging.Level;
import java.util.logging.Logger;

public class h
extends cv {
    private static final Logger a = Logger.getLogger(h.class.getName());
    private static final String b = "[C] C_BanClan";

    public h(byte[] abyte0, d clientthread) throws Exception {
        block13: {
            super(abyte0);
            u pc = clientthread.f();
            if (pc == null) {
                return;
            }
            String s2 = this.g();
            i clan = q.a().a(pc.aF());
            if (clan != null) {
                if (pc.x() && pc.fr() == clan.k()) {
                    int i2 = 0;
                    while (i2 < clan.p().size()) {
                        if (pc.et().toLowerCase().equals(s2.toLowerCase())) {
                            return;
                        }
                        ++i2;
                    }
                    u tempPc = aq.a().a(s2);
                    if (tempPc != null) {
                        if (tempPc.aF() == pc.aF()) {
                            tempPc.ah(0);
                            tempPc.c("");
                            tempPc.ai(0);
                            tempPc.I();
                            tempPc.a(new ds(238, clan.f()));
                            pc.a(new ds(240, tempPc.et()));
                            clan.b(tempPc.et());
                            p.a().a(tempPc.fr());
                        } else {
                            pc.a(new ds(109, s2));
                        }
                    } else {
                        try {
                            u restorePc = o.a().a(s2);
                            if (restorePc != null && restorePc.aF() == pc.aF()) {
                                restorePc.ah(0);
                                restorePc.c("");
                                restorePc.ai(0);
                                restorePc.I();
                                clan.b(restorePc.et());
                                p.a().a(restorePc.fr());
                                pc.a(new ds(240, restorePc.et()));
                                break block13;
                            }
                            pc.a(new ds(109, s2));
                        }
                        catch (Exception e2) {
                            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                        }
                    }
                } else {
                    pc.a(new ds(518));
                }
            }
        }
    }

    @Override
    public String a() {
        return b;
    }
}

