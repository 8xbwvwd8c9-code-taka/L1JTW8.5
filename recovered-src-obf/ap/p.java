/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.ab;
import ao.q;
import ap.t;
import ap.u;
import aq.i;
import be.be;
import bh.l;

public class p
extends t {
    public p(l template) {
        super(template);
    }

    @Override
    public void a(u pc) {
        bh.i house2;
        int houseId;
        int objid = this.fr();
        int npcid = this.U_().b();
        String htmlid = null;
        String[] htmldata = null;
        boolean isOwner = false;
        i clan = ao.q.a().a(pc.aF());
        if (clan != null && (houseId = clan.n()) != 0 && npcid == (house2 = ab.a().a(houseId)).f()) {
            isOwner = true;
        }
        if (!isOwner) {
            bh.i targetHouse = null;
            for (bh.i house2 : ab.a().c().values()) {
                if (npcid != house2.f()) continue;
                targetHouse = house2;
                break;
            }
            if (targetHouse == null) {
                System.out.println("L1Housekeeper has some error ! npcid=" + npcid);
                return;
            }
            boolean isOccupy = false;
            String clanName = null;
            String leaderName = null;
            for (i targetClan : ao.q.a().b().values()) {
                if (targetHouse.b() != targetClan.n()) continue;
                isOccupy = true;
                clanName = targetClan.f();
                leaderName = targetClan.l();
                break;
            }
            if (isOccupy) {
                htmlid = "agname";
                htmldata = new String[]{clanName, leaderName, targetHouse.c()};
            } else {
                htmlid = "agnoname";
                htmldata = new String[]{targetHouse.c()};
            }
        }
        if (htmlid != null) {
            pc.a(new be(objid, htmlid, htmldata));
            return;
        }
        if (this.E().length() > 0 && pc.fa() < -1000) {
            pc.a(new be(objid, this.E()));
        } else if (this.D().length() > 0) {
            pc.a(new be(objid, this.D()));
        }
    }
}

