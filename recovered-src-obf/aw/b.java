/*
 * Decompiled with CFR 0.152.
 */
package aw;

import ai.d;
import ao.au;
import ao.y;
import ap.l;
import ap.q;
import ap.u;
import aq.aa;
import aq.aq;
import aq.r;
import be.ak;
import be.ds;
import java.util.logging.Level;
import java.util.logging.Logger;

public class b {
    private static final Logger a = Logger.getLogger(b.class.getName());

    public static void a(u pc, q item) {
        l furniture;
        if (item == null) {
            pc.a(new ds(79));
            return;
        }
        if (!r.a(pc.fs(), pc.ft(), pc.fp())) {
            pc.a(new ds(563));
            return;
        }
        for (aa l1object : aq.a().b()) {
            if (!(l1object instanceof l) || (furniture = (l)l1object).f() != item.fr()) continue;
            if (y.a().deleteDurable(furniture)) {
                furniture.aa_();
            }
            return;
        }
        if (pc.fb() != 0 && pc.fb() != 2) {
            return;
        }
        int npcId = item.a().V();
        try {
            bh.l l1npc = au.a().a(npcId);
            furniture = new l(l1npc);
            furniture.cF(d.a().c());
            furniture.cE(pc.fp());
            if (pc.fb() == 0) {
                furniture.cG(pc.fs());
                furniture.cH(pc.ft() - 1);
            } else if (pc.fb() == 2) {
                furniture.cG(pc.fs() + 1);
                furniture.cH(pc.ft());
            }
            furniture.q(furniture.fs());
            furniture.r(furniture.ft());
            furniture.ct(0);
            furniture.b(item.fr());
            if (!y.a().insertDurable(furniture)) {
                return;
            }
            aq.a().a(furniture);
            aq.a().c(furniture);
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public static void a(u pc, int targetId, q item) {
        pc.a(new ak(pc.fr(), 17));
        pc.b(new ak(pc.fr(), 17));
        int chargeCount = item.I();
        if (chargeCount <= 0) {
            return;
        }
        aa target = aq.a().a(targetId);
        if (target != null && target instanceof l) {
            l furniture = (l)target;
            if (!y.a().deleteDurable(furniture)) {
                return;
            }
            furniture.aa_();
            item.g(item.I() - 1);
            pc.j().b(item);
        }
    }
}

