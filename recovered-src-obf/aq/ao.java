/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.aa;
import ap.q;
import ap.u;
import aq.aq;
import au.f;
import be.em;
import be.en;

public class ao {
    public static void a(u pc, int itemid, int itemcount) {
        if (pc.aO() == 0) {
            return;
        }
        u tpc = (u)aq.a().a(pc.aO());
        if (tpc == null) {
            ao.b(pc);
            return;
        }
        q l1iteminstance = pc.j().e(itemid);
        if (l1iteminstance == null) {
            return;
        }
        if (l1iteminstance.D()) {
            return;
        }
        itemcount = Math.abs(itemcount);
        if ((itemcount = Math.min(itemcount, l1iteminstance.E())) < 0 || itemcount > 2000000000 || l1iteminstance.E() < 0 || l1iteminstance.E() < itemcount) {
            return;
        }
        if (l1iteminstance.E() < itemcount || itemcount < 0) {
            pc.a(new en(1));
            tpc.a(new en(1));
            pc.c(false);
            tpc.c(false);
            pc.al(0);
            tpc.al(0);
            return;
        }
        pc.j().a(l1iteminstance, itemcount, pc.ax());
        pc.a(new em(l1iteminstance, itemcount, 0));
        tpc.a(new em(l1iteminstance, itemcount, 1));
    }

    public static void a(u pc) {
        if (pc.aO() == 0) {
            return;
        }
        u tpc = (u)aq.a().a(pc.aO());
        if (tpc == null) {
            ao.b(pc);
            return;
        }
        boolean isExistTradeItems = true;
        for (q item : pc.ax().d()) {
            if (item != null && item.E() > 0) continue;
            isExistTradeItems = false;
            break;
        }
        for (q item : tpc.ax().d()) {
            if (item != null && item.E() > 0) continue;
            isExistTradeItems = false;
            break;
        }
        if (!isExistTradeItems) {
            ao.b(pc);
            ao.b(tpc);
            return;
        }
        for (q item : pc.ax().d()) {
            pc.ax().a(item, item.E(), (f)tpc.j());
            aa.a().b(pc, "\u4ea4\u6613\u7d66(" + tpc.eu() + ")", item, item.E());
        }
        for (q item : tpc.ax().d()) {
            tpc.ax().a(item, item.E(), (f)pc.j());
            aa.a().b(tpc, "\u4ea4\u6613\u7d66(" + pc.eu() + ")", item, item.E());
        }
        pc.a(new en(0));
        tpc.a(new en(0));
        pc.c(false);
        tpc.c(false);
        pc.al(0);
        tpc.al(0);
        pc.fg();
        tpc.fg();
    }

    public static void b(u pc) {
        if (pc.aO() == 0) {
            return;
        }
        for (q item : pc.ax().d()) {
            pc.ax().a(item, item.E(), (f)pc.j());
        }
        pc.a(new en(1));
        u tpc = (u)aq.a().a(pc.aO());
        if (tpc != null) {
            for (q item : tpc.ax().d()) {
                tpc.ax().a(item, item.E(), (f)tpc.j());
            }
            tpc.a(new en(1));
            tpc.c(false);
            tpc.al(0);
        }
        pc.c(false);
        pc.al(0);
    }
}

