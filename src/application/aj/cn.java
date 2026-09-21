/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.e;
import ap.q;
import ap.t;
import ap.u;
import ap.v;
import aq.ao;
import aq.aq;
import be.ds;
import bj.d;

public class cn
extends cv {
    public cn(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int itemid = this.b();
        int itemcount = this.b();
        q item = pc.j().e(itemid);
        if (item == null) {
            pc.a(new ds(156));
            return;
        }
        if (!item.a().s()) {
            pc.a(new ds(210, item.a().h()));
            return;
        }
        if (item.F() >= 128) {
            pc.a(new ds(210, item.a().h()));
            return;
        }
        for (t petNpc : pc.ek().values()) {
            if (!(petNpc instanceof v)) continue;
            v pet = (v)petNpc;
            if (item.fr() != pet.k()) continue;
            pc.a(new ds(1187));
            return;
        }
        for (e doll : pc.el().values()) {
            if (doll.f() != item.fr()) continue;
            pc.a(new ds(1181));
            return;
        }
        u tradingPartner = (u)aq.a().a(pc.aO());
        if (tradingPartner == null) {
            return;
        }
        if (pc.aP()) {
            return;
        }
        if (tradingPartner.j().a(item, itemcount) != 0) {
            tradingPartner.a(new ds(270));
            pc.a(new ds(271));
            return;
        }
        ao.a(pc, itemid, itemcount);
    }

    @Override
    public String a() {
        return "C_TradeAddItem";
    }
}

