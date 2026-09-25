/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.q;
import ap.u;
import aq.aq;
import aq.i;
import be.ds;
import be.y;
import bj.d;
import l1j.server.a;

public class cl
extends cv {
    public cl(byte[] abyte0, d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        String charName = this.g();
        String title = this.g();
        if (charName.isEmpty() || title.isEmpty()) {
            pc.a(new ds(196));
            return;
        }
        u target = aq.a().a(charName);
        if (target == null) {
            return;
        }
        if (pc.l()) {
            this.a(target, title);
            return;
        }
        if (this.a(pc)) {
            if (pc.fr() == target.fr()) {
                if (pc.ev() < 10) {
                    pc.a(new ds(197));
                    return;
                }
                this.a(pc, title);
            } else {
                if (pc.aF() != target.aF()) {
                    pc.a(new ds(199));
                    return;
                }
                if (target.ev() < 10) {
                    pc.a(new ds(202, charName));
                    return;
                }
                this.a(target, title);
                i clan = q.a().a(pc.aF());
                if (clan != null) {
                    for (u clanPc : clan.b()) {
                        clanPc.a(new ds(203, pc.et(), charName, title));
                    }
                }
            }
        } else if (pc.fr() == target.fr()) {
            if (pc.aF() != 0 && !a.T) {
                pc.a(new ds(198));
                return;
            }
            if (target.ev() < 40) {
                pc.a(new ds(200));
                return;
            }
            this.a(pc, title);
        } else if (pc.x() && pc.aF() == target.aF()) {
            pc.a(new ds(201, target.et()));
            return;
        }
    }

    private void a(u pc, String title) {
        int objectId = pc.fr();
        pc.f(title);
        pc.a(new y(objectId, title));
        pc.b(new y(objectId, title));
        pc.I();
    }

    private boolean a(u pc) {
        i clan;
        boolean isClanLeader = false;
        if (pc.aF() != 0 && (clan = q.a().a(pc.aF())) != null && pc.x() && pc.fr() == clan.k()) {
            isClanLeader = true;
        }
        return isClanLeader;
    }

    @Override
    public String a() {
        return "C_Title";
    }
}

