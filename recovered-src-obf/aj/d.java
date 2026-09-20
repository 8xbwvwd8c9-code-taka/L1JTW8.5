/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.t;
import ap.u;
import aq.aa;
import aq.ak;
import aq.aq;
import aq.c;
import aq.f;
import be.ds;

public class d
extends cv {
    public d(byte[] decrypt, bj.d client) {
        super(decrypt);
        int hiddenStatus;
        if (client == null) {
            return;
        }
        u pc = client.f();
        if (pc == null) {
            return;
        }
        if (pc.bN() || pc.eX() || pc.aR() || pc.ed() || pc.ec()) {
            return;
        }
        int targetId = this.b();
        int x2 = this.d();
        int y2 = this.d();
        aa target = aq.a().a(targetId);
        if (pc.j().h() > 82) {
            pc.a(new ds(110));
            pc.bb(0);
            return;
        }
        if (pc.bB(60) || pc.N()) {
            pc.bb(0);
            return;
        }
        if (pc.ed()) {
            pc.bb(0);
            return;
        }
        if (target instanceof f) {
            int range = 1;
            if (pc.v() != null) {
                range = pc.v().a().aB();
                int n2 = range = range < 0 ? 15 : range;
            }
            if (target.fp() != pc.fp() || pc.fu().b(target.fu()) > (double)range + 1.5) {
                pc.bb(0);
                return;
            }
        }
        if (target instanceof t && ((hiddenStatus = ((t)target).ac()) == 1 || hiddenStatus == 2)) {
            pc.bb(0);
            return;
        }
        int result = pc.ce().a(ak.a.b);
        if (result == 2) {
            pc.bb(0);
            return;
        }
        if (pc.bB(78)) {
            pc.bz(78);
        }
        if (pc.bB(32)) {
            pc.bz(32);
        }
        if (pc.bB(97) && !pc.bB(233)) {
            pc.bz(97);
        }
        pc.e(1);
        if (!(target instanceof f) || ((f)target).eX()) {
            f cha = new f();
            cha.cF(targetId);
            cha.cG(x2);
            cha.cH(y2);
            c atk = new c(pc, cha);
            atk.c();
            pc.bb(0);
            return;
        }
        target.c(pc);
    }
}

