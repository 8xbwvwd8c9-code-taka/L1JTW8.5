/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.o;
import ao.q;
import ap.u;
import aq.i;
import be.af;
import bj.d;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;

public class ae
extends cv {
    private static final Logger a = Logger.getLogger(ae.class.getName());

    public ae(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        String name = this.g();
        try {
            i clan;
            u pc = o.a().a(name);
            if (pc != null && pc.ev() > 55 && l1j.server.a.as) {
                if (pc.ay() < 32) {
                    if (pc.x()) {
                        pc.ad(32);
                    } else if (pc.z()) {
                        pc.ad(33);
                    } else if (pc.A()) {
                        pc.ad(34);
                    } else if (pc.B()) {
                        pc.ad(35);
                    } else if (pc.C()) {
                        pc.ad(36);
                    } else if (pc.D()) {
                        pc.ad(37);
                    } else if (pc.E()) {
                        pc.ad(38);
                    } else if (pc.F()) {
                        pc.ad(39);
                    }
                    Timestamp deleteTime = new Timestamp(System.currentTimeMillis() + 604800000L);
                    pc.d(deleteTime);
                    pc.I();
                } else {
                    if (pc.x()) {
                        pc.ad(0);
                    } else if (pc.z()) {
                        pc.ad(1);
                    } else if (pc.A()) {
                        pc.ad(2);
                    } else if (pc.B()) {
                        pc.ad(3);
                    } else if (pc.C()) {
                        pc.ad(4);
                    } else if (pc.D()) {
                        pc.ad(5);
                    } else if (pc.E()) {
                        pc.ad(6);
                    } else if (pc.F()) {
                        pc.ad(7);
                    }
                    pc.d((Timestamp)null);
                    pc.I();
                }
                client.a(new af(81));
                return;
            }
            if (pc != null && (clan = q.a().a(pc.aF())) != null) {
                clan.b(name);
            }
            o.a().a(client.a(), name);
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            client.c();
            return;
        }
        client.a(new af(5));
    }

    @Override
    public String a() {
        return "C_DELETE_CHAR";
    }
}

