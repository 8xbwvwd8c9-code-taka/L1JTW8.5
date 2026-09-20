/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.aa;
import aq.aq;
import aq.o;
import ba.g;
import be.bx;
import be.ch;
import be.cj;
import be.dh;
import be.ds;
import be.es;
import be.z;
import bj.d;

public class bw
extends cv {
    public bw(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        pc.t();
        pc.es();
        pc.b(new dh(pc));
        pc.a(pc.ev());
        pc.c_(40);
        pc.X(false);
        pc.cq(0);
        int[] loc = new int[3];
        if (pc.bI() > 0) {
            loc[0] = 32701;
            loc[1] = 32777;
            loc[2] = 666;
        } else if (pc.fp() >= 10500 && pc.fp() <= 10502) {
            if (pc.dX() == 4) {
                loc[0] = 32734;
                loc[1] = 32756;
            } else if (pc.dX() == 5) {
                loc[0] = 32663;
                loc[1] = 32890;
            } else {
                loc[0] = 32732;
                loc[1] = 33040;
            }
            loc[2] = 10500;
            pc.a(pc.ew());
        } else {
            loc = o.a(pc);
        }
        aq.a().a((aa)pc, loc[2]);
        pc.cG(loc[0]);
        pc.cH(loc[1]);
        pc.cE(loc[2]);
        pc.a(new bx(pc.fp(), pc.fq().g()));
        pc.b(new ch(pc));
        pc.a(new cj(pc));
        pc.a(new z(pc));
        pc.a();
        pc.c();
        pc.a(new es(aq.a().j()));
        if (pc.bI() > 0) {
            g.a().a(pc, false);
        }
        pc.ae();
        if ((pc.fp() != 1700 || pc.fp() != 1703) && pc.j().m(21397)) {
            pc.a(new ds(123, "\\aG$22171"));
        }
    }

    @Override
    public String a() {
        return "C_RestartDead";
    }
}

