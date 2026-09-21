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
        t npc = (t)aq.a().a(objectId);
        if (npc == null) {
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
            for (i house : ab.a().c().values()) {
                if (!pc.et().equalsIgnoreCase(house.n())) continue;
                pc.a(new ds(523));
                return;
            }
            int houseId = Integer.valueOf(s22);
            i house = ab.a().a(houseId);
            if (!pc.j().b(40308, amount)) {
                pc.a(new ds(189));
                return;
            }
            int oldPrice = house.k();
            int oldBidderId = house.o();
            house.d(amount);
            house.d(pc.et());
            house.f(pc.fr());
            ab.a().a(house);
            if (oldBidderId != 0) {
                u bidPc = (u)aq.a().a(oldBidderId);
                if (bidPc != null) {
                    ah.a(bidPc, 40308, oldPrice, 0, false);
                    bidPc.a(new ds(525, String.valueOf(oldPrice)));
                } else {
                    q item = ah.a().b(40308);
                    item.e(oldPrice);
                    l.a().a(oldBidderId, item);
                }
            }
        } else if (s1.equalsIgnoreCase("agsell")) {
            int houseId = Integer.valueOf(s22);
            i house = ab.a().a(houseId);
            Timestamp ts = new Timestamp(System.currentTimeMillis() + 432000000L);
            house.b(ts);
            house.d(amount);
            house.c(pc.et());
            house.e(pc.fr());
            house.d("");
            house.f(0);
            house.a(true);
            house.b(false);
            ab.a().a(house);
        } else {
            int npcId = npc.z();
            if (npcId == 70070 || npcId == 70019 || npcId == 70075 || npcId == 70012 || npcId == 70031 || npcId == 70084 || npcId == 70065 || npcId == 70054 || npcId == 70096) {
                if (!pc.j().g(40308, 300 * amount)) {
                    pc.a(new be(npcId, "inn3", npc.et()));
                    return;
                }
                if (!af.a().a(pc.dM())) {
                    pc.a(new be(npcId, ""));
                    return;
                }
                q item = ah.a().b(40312);
                item.e(amount);
                item.j(item.fr());
                pc.j().b(40308, 300 * amount);
                f inventory = pc.j().a(item, amount) == 0 ? pc.j() : aq.a().a(pc.fu());
                ((f)inventory).d(item);
                af.a().a(item.M(), amount, pc.dM());
                pc.a(new ds(143, npc.et(), item.s()));
                pc.a(new be(npcId, "inn4", npc.et()));
            }
        }
    }

    @Override
    public String a() {
        return "C_Amount";
    }
}

