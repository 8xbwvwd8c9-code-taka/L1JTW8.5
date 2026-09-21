/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.aq;
import aq.h;
import be.be;
import be.ca;
import be.ds;
import bj.d;

public class w
extends cv {
    public w(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int type = this.c();
        if (type == 0) {
            String name = this.g();
            if (!pc.r()) {
                pc.a(new ds(425));
                return;
            }
            if (!pc.aM().b(pc)) {
                pc.a(new ds(427));
                return;
            }
            u targetPc = aq.a().a(name);
            if (targetPc == null) {
                pc.a(new ds(109));
                return;
            }
            if (pc.fr() == targetPc.fr()) {
                return;
            }
            u[] uArray = pc.aM().e();
            int n2 = uArray.length;
            int n3 = 0;
            while (n3 < n2) {
                u member = uArray[n3];
                if (member.et().toLowerCase().equals(name.toLowerCase())) {
                    pc.aM().d(member);
                    return;
                }
                ++n3;
            }
            pc.a(new ds(426, name));
        } else if (type == 1) {
            if (pc.r()) {
                pc.aM().c(pc);
            }
        } else if (type == 2) {
            h chatParty = pc.aM();
            if (pc.r()) {
                pc.a(new be(pc.fr(), "party", chatParty.c().et(), chatParty.d()));
            } else {
                pc.a(new ds(425));
            }
        } else if (type == 3) {
            String name = this.g();
            u targetPc = aq.a().a(name);
            if (targetPc == null) {
                pc.a(new ds(109));
                return;
            }
            if (pc.fr() == targetPc.fr()) {
                return;
            }
            if (!pc.fu().e(targetPc.fu()) || pc.fu().c(targetPc.fu()) > 7) {
                pc.a(new ds(952));
                return;
            }
            if (targetPc.r()) {
                pc.a(new ds(415));
                return;
            }
            if (pc.r()) {
                if (pc.aM().b(pc)) {
                    targetPc.ak(pc.fr());
                    targetPc.a(new ca(951, pc.et()));
                } else {
                    pc.a(new ds(416));
                }
            } else {
                targetPc.ak(pc.fr());
                targetPc.a(new ca(951, pc.et()));
            }
        }
    }

    @Override
    public String a() {
        return "C_ChatParty";
    }
}

