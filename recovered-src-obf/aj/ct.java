/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import be.dj;
import be.dl;
import be.dm;
import be.ds;
import be.ei;
import bh.a;
import bj.d;

public class ct
extends cv {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ct(byte[] abyte0, d client) {
        super(abyte0);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int type = this.c();
        int pass1 = this.b();
        int pass2 = this.b();
        this.d();
        a account = client.e();
        if (type == 0) {
            if (pass1 < 0 && pass2 < 0) {
                pc.a(new ds(79));
                return;
            } else if (pass1 < 0 && account.m() == 0) {
                account.f(pass2);
                pc.a(new ei("\u5009\u5eab\u5bc6\u78bc\u8a2d\u5b9a\u5b8c\u6210\uff0c\u8acb\u7262\u8a18\u60a8\u7684\u65b0\u5bc6\u78bc\u3002"));
                return;
            } else if (pass1 > 0 && pass1 == account.m()) {
                if (pass1 == pass2) {
                    pc.a(new ds(342));
                    return;
                }
                if (pass2 > 0) {
                    account.f(pass2);
                    pc.a(new ei("\u5009\u5eab\u5bc6\u78bc\u8b8a\u66f4\u5b8c\u6210\uff0c\u8acb\u7262\u8a18\u60a8\u7684\u65b0\u5bc6\u78bc\u3002"));
                    return;
                } else {
                    account.f(0);
                    pc.a(new ei("\u5009\u5eab\u5bc6\u78bc\u53d6\u6d88\u5b8c\u6210\u3002"));
                }
                return;
            } else {
                pc.a(new ds(835));
            }
            return;
        } else if (account.m() == pass1) {
            int objid = pass2;
            aa obj = aq.a().a(objid);
            if (pc.ev() < 5) return;
            if (type == 1) {
                if (obj == null || !(obj instanceof t)) return;
                t npc = (t)obj;
                switch (npc.z()) {
                    case 60028: {
                        if (!pc.A()) return;
                        pc.a(new dm(objid, pc));
                        return;
                    }
                    default: {
                        pc.a(new dj(objid, pc));
                        return;
                    }
                }
            } else {
                if (type != 2) return;
                if (pc.aF() == 0) {
                    pc.a(new ds(208));
                    return;
                }
                int rank = pc.aH();
                if (rank == 7) {
                    pc.a(new ds(728));
                    return;
                }
                if (rank != 8 && rank != 9 && rank != 5 && rank != 10 && rank != 3 && rank != 6 && rank != 4) {
                    pc.a(new ds(728));
                    return;
                }
                pc.a(new dl(objid, pc));
            }
            return;
        } else {
            pc.a(new ds(835));
        }
    }
}

