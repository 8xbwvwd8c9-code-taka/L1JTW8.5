/*
 * Decompiled with CFR 0.152.
 */
package aj;

import ai.b;
import aj.cv;
import ao.q;
import ap.s;
import aq.aa;
import aq.aq;
import aq.i;
import be.ab;
import be.cg;
import be.cm;
import be.ds;
import bj.d;
import java.util.ArrayList;
import java.util.Arrays;
import l1j.server.a;

public class u
extends cv {
    public u(byte[] abyte0, d clientthread) {
        super(abyte0);
        ap.u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        if (pc.bB(64) || pc.bB(161) || pc.bB(1007)) {
            return;
        }
        if (pc.bB(1005)) {
            pc.a(new ds(242));
            return;
        }
        int chatType = this.c();
        String chatText = this.g();
        if (chatType == 0 || chatType == 2) {
            int range;
            if (chatText.startsWith(".") && chatText.length() > 1 && pc.l()) {
                String cmd = chatText.substring(1);
                b.a().a(pc, cmd);
                return;
            }
            if (pc.bN()) {
                return;
            }
            int n2 = range = chatType == 2 ? 50 : -1;
            if (!pc.cd().c(pc.et())) {
                pc.a(new ab(pc, chatText, chatType));
            }
            for (ap.u listner : aq.a().c(pc, range)) {
                if (listner.cd().c(pc.et())) continue;
                listner.a(new ab(pc, chatText, chatType));
            }
            for (aa obj : pc.eq()) {
                s mob;
                if (!(obj instanceof s) || !(mob = (s)obj).U_().X() || !mob.et().equals(pc.et()) || mob.eX()) continue;
                for (ap.u listner : aq.a().c(mob, range)) {
                    listner.a(new cg(mob, chatText, chatType));
                }
            }
        } else if (chatType == 3) {
            this.a(pc, chatText, chatType);
        } else if (chatType == 4) {
            if (pc.aF() == 0) {
                return;
            }
            i clan = q.a().a(pc.aF());
            for (ap.u listner : clan.b()) {
                if (listner.cd().c(pc.et()) || !listner.cm()) continue;
                listner.a(new ab(pc, chatText, chatType));
            }
        } else if (chatType == 11) {
            if (!pc.q()) {
                return;
            }
            for (ap.u listner : pc.aL().c()) {
                if (listner.cd().c(pc.et()) || !listner.cn()) continue;
                listner.a(new ab(pc, chatText, chatType));
            }
        } else if (chatType == 12) {
            this.a(pc, chatText, chatType);
        } else if (chatType == 13) {
            if (pc.aF() == 0) {
                return;
            }
            ArrayList<Integer> access = new ArrayList<Integer>(Arrays.asList(9, 4, 10, 3, 6));
            i clan = q.a().a(pc.aF());
            if (!access.contains(pc.aH())) {
                return;
            }
            for (ap.u listner : clan.b()) {
                if (listner.cd().c(pc.et()) || !access.contains(listner.aH())) continue;
                listner.a(new ab(pc, chatText, chatType));
            }
        } else if (chatType == 14) {
            if (!pc.r()) {
                return;
            }
            ap.u[] uArray = pc.aM().e();
            int n3 = uArray.length;
            int clan = 0;
            while (clan < n3) {
                ap.u listner = uArray[clan];
                if (!listner.cd().c(pc.et())) {
                    listner.a(new ab(pc, chatText, chatType));
                }
                ++clan;
            }
        } else if (chatType == 15) {
            if (pc.aF() == 0) {
                return;
            }
            i clan = q.a().a(pc.aF());
            for (ap.u listner : clan.b()) {
                if (listner.cd().c(pc.et()) || !listner.cm()) continue;
                listner.a(new ab(pc, chatText, chatType));
            }
        } else if (chatType == 17) {
            if (pc.aF() == 0) {
                return;
            }
            if (pc.aH() == 10 || pc.aH() == 4) {
                i clan = q.a().a(pc.aF());
                for (ap.u listner : clan.b()) {
                    listner.a(new ab(pc, chatText, chatType));
                }
            }
        }
        if (!pc.l()) {
            pc.ab();
        }
    }

    private void a(ap.u pc, String chatText, int chatType) {
        if (pc.l()) {
            aq.a().a(new ab(pc, chatText, chatType));
            return;
        }
        if (pc.ev() < a.N) {
            pc.a(new ds(195, String.valueOf(a.N)));
            return;
        }
        if (!aq.a().k()) {
            pc.a(new ds(510));
            return;
        }
        if (pc.fj() < 6) {
            pc.a(new ds(462));
            return;
        }
        for (ap.u listner : aq.a().c()) {
            if (listner.cd().c(pc.et()) || chatType == 12 && !listner.cl() || chatType == 3 && !listner.co()) continue;
            listner.a(new ab(pc, chatText, chatType));
        }
        pc.c_(pc.fj() - 5);
        pc.a(new cm(11, pc.fj()));
    }

    @Override
    public String a() {
        return "C_Chat";
    }
}

