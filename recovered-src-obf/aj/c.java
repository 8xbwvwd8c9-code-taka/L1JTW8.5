/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.ab;
import ao.ac;
import ao.af;
import ao.ah;
import ao.l;
import ap.q;
import ap.t;
import ap.u;
import aq.aq;
import au.f;
import be.be;
import be.ds;
import bh.i;
import bj.d;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class c
extends cv {
    public c(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int objectId = this.b();
        int amount = this.b();
        int type = this.c();
        String s2 = this.g();

        aq.aa object = aq.a().a(objectId);
        if (!(object instanceof t)) {
            return;
        }
        t npc = (t)object;
        if (npc.fu().c(pc.fu()) > 11) {
            return;
        }
        if (amount <= 0) {
            return;
        }
        if (ac.a().a(s2, pc, npc, amount)) {
            return;
        }

        String s1 = "";
        String s22 = "";
        try {
            StringTokenizer stringtokenizer = new StringTokenizer(s2);
            s1 = stringtokenizer.nextToken();
            s22 = stringtokenizer.nextToken();
        }
        catch (NoSuchElementException e2) {
            s1 = "";
            s22 = "";
        }

        if (s1.equalsIgnoreCase("agapply")) {
            if (!(npc instanceof ap.b)) {
                return;
            }
            int houseId;
            try {
                houseId = Integer.parseInt(s22);
            }
            catch (NumberFormatException e3) {
                return;
            }
            i house = ab.a().a(houseId);
            if (house == null || !house.g() || house.j() == null || !house.j().after(new Timestamp(System.currentTimeMillis()))) {
                return;
            }
            aq.i clan = ao.q.a().a(pc.aF());
            if (clan == null || !pc.x() || pc.fr() != clan.k() || pc.ev() < 15 || clan.n() != 0) {
                return;
            }
            if (amount <= house.k()) {
                return;
            }
            for (i other : ab.a().c().values()) {
                if (pc.fr() == other.o()) {
                    pc.a(new ds(523));
                    return;
                }
            }
            if (!pc.j().b(40308, amount)) {
                pc.a(new ds(189));
                return;
            }

            int oldPrice = house.k();
            String oldBidder = house.n();
            int oldBidderId = house.o();
            house.d(amount);
            house.d(pc.et());
            house.f(pc.fr());
            if (!ab.a().a(house)) {
                house.d(oldPrice);
                house.d(oldBidder);
                house.f(oldBidderId);
                ao.ah.a(pc, 40308, amount, 0, false);
                return;
            }

            if (oldBidderId != 0 && oldPrice > 0) {
                boolean refundOk = true;
                try {
                    u bidPc = (u)aq.a().a(oldBidderId);
                    if (bidPc != null) {
                        refundOk = ah.a(bidPc, 40308, oldPrice, 0, false) != null;
                        if (refundOk) {
                            bidPc.a(new ds(525, String.valueOf(oldPrice)));
                        }
                    } else {
                        q item = ah.a().b(40308);
                        if (item == null) {
                            refundOk = false;
                        } else {
                            item.e(oldPrice);
                            l.a().a(oldBidderId, item);
                        }
                    }
                }
                catch (Exception refundFailure) {
                    refundOk = false;
                }
                if (!refundOk) {
                    house.d(oldPrice);
                    house.d(oldBidder);
                    house.f(oldBidderId);
                    ab.a().a(house);
                    ao.ah.a(pc, 40308, amount, 0, false);
                }
            }
            return;
        }

        if (s1.equalsIgnoreCase("agsell")) {
            if (!(npc instanceof ap.p)) {
                return;
            }
            if (amount < 100000 || amount > 2000000000) {
                return;
            }
            int houseId;
            try {
                houseId = Integer.parseInt(s22);
            }
            catch (NumberFormatException e4) {
                return;
            }
            i house = ab.a().a(houseId);
            aq.i clan = ao.q.a().a(pc.aF());
            if (house == null || clan == null || !pc.x() || pc.fr() != clan.k() || clan.n() != houseId || npc.z() != house.f() || house.g()) {
                return;
            }

            Timestamp oldDeadline = house.j();
            int oldPrice = house.k();
            String oldOwner = house.l();
            int oldOwnerId = house.m();
            String oldBidder = house.n();
            int oldBidderId = house.o();
            boolean oldSale = house.g();
            boolean oldBasement = house.h();

            Timestamp ts = new Timestamp(System.currentTimeMillis() + 432000000L);
            house.b(ts);
            house.d(amount);
            house.c(pc.et());
            house.e(pc.fr());
            house.d("");
            house.f(0);
            house.a(true);
            house.b(false);
            if (!ab.a().a(house)) {
                house.b(oldDeadline);
                house.d(oldPrice);
                house.c(oldOwner);
                house.e(oldOwnerId);
                house.d(oldBidder);
                house.f(oldBidderId);
                house.a(oldSale);
                house.b(oldBasement);
            }
            return;
        }

        int npcId = npc.z();
        if (npcId == 70070 || npcId == 70019 || npcId == 70075 || npcId == 70012 || npcId == 70031 || npcId == 70084 || npcId == 70065 || npcId == 70054 || npcId == 70096) {
            long charge = 300L * (long)amount;
            if (charge <= 0L || charge > 2000000000L) {
                return;
            }
            int chargeInt = (int)charge;
            if (!pc.j().g(40308, chargeInt)) {
                pc.a(new be(npcId, "inn3", npc.et()));
                return;
            }
            if (!af.a().a(pc.dM())) {
                pc.a(new be(npcId, ""));
                return;
            }
            q item = ah.a().b(40312);
            if (item == null) {
                return;
            }
            item.e(amount);
            item.j(item.fr());
            if (pc.j().a(item, amount) != 0) {
                return;
            }
            if (!pc.j().b(40308, chargeInt)) {
                return;
            }
            pc.j().d(item);
            if (!af.a().a(item.M(), amount, pc.dM())) {
                pc.j().f(item);
                ao.ah.a(pc, 40308, chargeInt, 0, false);
                return;
            }
            pc.a(new ds(143, npc.et(), item.s()));
            pc.a(new be(npcId, "inn4", npc.et()));
        }
    }

    @Override
    public String a() {
        return "C_Amount";
    }
}

