/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.q;
import ap.u;
import aq.aq;
import aq.i;
import be.ca;
import be.cy;
import be.ds;
import bj.d;

public class bq
extends cv {
    public bq(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        if (pc.aF() == 0) {
            pc.a(new ds(518));
            return;
        }
        i clan = q.a().a(pc.aF());
        int type = this.c();
        if (type == 0) {
            String name = this.g();
            if (clan.f().equalsIgnoreCase(name)) {
                return;
            }
            i targetClan = q.a().c(name);
            if (targetClan == null) {
                pc.a(new ds(3982));
                return;
            }
            u targetClanLeader = aq.a().a(targetClan.l());
            if (targetClanLeader == null) {
                pc.a(new ds(3349));
                return;
            }
            targetClanLeader.a(new ca(3348, clan.f()));
            targetClanLeader.am(clan.e());
        } else if (type == 1) {
            String clanName = this.g();
            i targetClan = q.a().c(clanName);
            if (targetClan == null) {
                pc.a(new ds(3982));
                return;
            }
            clan.t().remove((Object)targetClan.e());
            for (u member : clan.b()) {
                member.a(new ds(3359, targetClan.f()));
                member.a(new cy(clan));
            }
            q.a().b(clan);
            targetClan.t().remove((Object)clan.e());
            for (u member : targetClan.b()) {
                member.a(new ds(3359, clan.f()));
                member.a(new cy(targetClan));
            }
            q.a().b(targetClan);
        }
    }

    @Override
    public String a() {
        return "C_PledgeWatch";
    }
}

