/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.q;
import ap.u;
import aq.ap;
import aq.aq;
import aq.e;
import aq.i;
import as.b;
import be.ca;
import be.ds;
import bj.d;

public class cs
extends cv {
    public cs(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int type = this.c();
        String targetClanName = this.g();
        if (!pc.x()) {
            pc.a(new ds(478));
            return;
        }
        if (pc.aF() == 0) {
            pc.a(new ds(272));
            return;
        }
        i clan = q.a().a(pc.aF());
        if (clan == null) {
            return;
        }
        String clanName = clan.f();
        if (pc.fr() != clan.k()) {
            pc.a(new ds(478));
            return;
        }
        if (clanName.toLowerCase().equals(targetClanName.toLowerCase())) {
            return;
        }
        if (clan.a()) {
            if (type == 0) {
                pc.a(new ds(474));
            }
            return;
        }
        i enemyClan = q.a().c(targetClanName);
        if (enemyClan == null) {
            pc.a(new ds(3982));
            return;
        }
        if (pc.ev() < 25) {
            pc.a(new ds(enemyClan.a() ? 475 : 232));
        }
        if (type == 0 && aq.a().b(clanName)) {
            pc.a(new ds(234));
            return;
        }
        if (!(type != 2 && type != 3 || aq.a().b(clanName))) {
            return;
        }
        if (enemyClan.a()) {
            if (type == 0 && !b.a().a(enemyClan.m())) {
                pc.a(new ds(476));
                return;
            }
            for (u element : clan.b()) {
                if (!e.a(enemyClan.m(), element)) continue;
                pc.a(new ds(477));
                return;
            }
            ap enemyClanWar = aq.a().c(targetClanName);
            if (type == 0) {
                if (enemyClanWar == null) {
                    ap ap2 = new ap(1, clanName, targetClanName);
                } else {
                    enemyClanWar.a(clan);
                }
            } else if (type == 2 || type == 3) {
                if (enemyClanWar == null) {
                    return;
                }
                if (!enemyClanWar.c(clanName, targetClanName)) {
                    return;
                }
                if (type == 2) {
                    enemyClanWar.a(clanName, targetClanName);
                } else if (type == 3) {
                    enemyClanWar.b(clanName, targetClanName);
                }
            }
        } else {
            if (type == 0 && aq.a().b(targetClanName)) {
                pc.a(new ds(236, targetClanName));
                return;
            }
            if (type == 2 || type == 3) {
                if (!aq.a().b(targetClanName)) {
                    return;
                }
                ap enemyClanWar = aq.a().c(targetClanName);
                if (!enemyClanWar.c(clanName, targetClanName)) {
                    return;
                }
            }
            if (enemyClan.l() == null) {
                pc.a(new ds(218, targetClanName));
                return;
            }
            u enemyLeader = aq.a().a(enemyClan.l());
            if (enemyLeader == null) {
                pc.a(new ds(218, targetClanName));
                return;
            }
            enemyLeader.am(pc.fr());
            if (type == 0) {
                enemyLeader.a(new ca(217, clanName, pc.et()));
            } else if (type == 2) {
                enemyLeader.a(new ca(221, clanName));
            } else if (type == 3) {
                enemyLeader.a(new ca(222, clanName));
            }
        }
    }

    @Override
    public String a() {
        return "C_War";
    }
}

