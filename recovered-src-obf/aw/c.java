/*
 * Decompiled with CFR 0.152.
 */
package aw;

import ao.au;
import ap.e;
import ap.q;
import ap.u;
import be.ck;
import be.cm;
import be.ds;
import be.ee;
import be.ei;
import bh.l;
import l1j.server.a;

public class c {
    public static void a(u pc, q item) {
        for (e curdoll : pc.el().values()) {
            if (curdoll.f() != item.fr()) continue;
            curdoll.e();
            return;
        }
        if (!pc.j().g(41246, 50)) {
            pc.a(new ds(337, "$5240"));
            return;
        }
        if (pc.el().size() >= a.ao) {
            pc.a(new ds(1529, String.valueOf(a.ao)));
            return;
        }
        l template = au.a().a(189998);
        if (item.br() == 0 || item.bq() == null) {
            pc.a(new ei("\u9b54\u6cd5\u5a03\u5a03\u53ec\u559a\u7570\u5e38\uff0c\u8acb\u544a\u77e5GM\u5f9e\u4f55\u53d6\u5f97\u6b64\u5a03\u5a03"));
            return;
        }
        e doll = new e(template, pc, item);
        pc.a(new ee(doll.fr(), 5935));
        pc.b(new ee(doll.fr(), 5935));
        pc.a(new cm(56, 1800));
        pc.a(new ck(pc));
        pc.j().b(41246, 50);
    }
}

