/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.ai;
import ao.aw;
import ao.y;
import ap.q;
import ap.u;
import aq.aa;
import aq.aq;
import au.e;
import au.f;
import java.util.List;

public class p
implements l {
    private p() {
    }

    public static l a() {
        return new p();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        for (aa l1object : aq.a().b()) {
            ap.l furniture;
            List<u> players;
            q l1iteminstance;
            if (!(l1object instanceof q) || (l1iteminstance = (q)l1object).fs() == 0 && l1iteminstance.ft() == 0 || (players = aq.a().c(l1iteminstance, 0)).size() != 0) continue;
            e groundInventory = aq.a().a(l1iteminstance.fs(), l1iteminstance.ft(), l1iteminstance.fp());
            int itemId = l1iteminstance.N();
            if (itemId == 40314 || itemId == 40316) {
                aw.a().a(l1iteminstance.fr());
            } else if (itemId >= 49016 && itemId <= 49025) {
                ai.a().a(l1iteminstance.fr());
            } else if (itemId >= 41383 && itemId <= 41400 && l1object instanceof ap.l && (furniture = (ap.l)l1object).f() == l1iteminstance.fr()) {
                y.a().b(furniture);
            }
            ((f)groundInventory).c(l1iteminstance);
            aq.a().d(l1iteminstance);
            aq.a().b(l1iteminstance);
        }
        aq.a().d("\u5730\u4e0a\u7684\u5783\u573e\u88abGM\u6e05\u9664\u4e86\u3002");
    }
}

